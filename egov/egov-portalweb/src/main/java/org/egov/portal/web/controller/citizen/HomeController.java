/*
 *    eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) 2017  eGovernments Foundation
 *
 *     The updated version of eGov suite of products as by eGovernments Foundation
 *     is available at http://www.egovernments.org
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program. If not, see http://www.gnu.org/licenses/ or
 *     http://www.gnu.org/licenses/gpl.html .
 *
 *     In addition to the terms of the GPL license to be adhered to in using this
 *     program, the following additional terms are to be complied with:
 *
 *         1) All versions of this program, verbatim or modified must carry this
 *            Legal Notice.
 *            Further, all user interfaces, including but not limited to citizen facing interfaces,
 *            Urban Local Bodies interfaces, dashboards, mobile applications, of the program and any
 *            derived works should carry eGovernments Foundation logo on the top right corner.
 *
 *            For the logo, please refer http://egovernments.org/html/logo/egov_logo.png.
 *            For any further queries on attribution, including queries on brand guidelines,
 *            please contact contact@egovernments.org
 *
 *         2) Any misrepresentation of the origin of the material is prohibited. It
 *            is required that all modified versions of this material be marked in
 *            reasonable ways as different from the original version.
 *
 *         3) This license does not grant any rights to any user of the program
 *            with regards to rights under trademark law for use of the trade names
 *            or trademarks of eGovernments Foundation.
 *
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 *
 */
package org.egov.portal.web.controller.citizen;

import static org.egov.infra.persistence.entity.enums.UserType.BUSINESS;
import static org.egov.infra.persistence.entity.enums.UserType.CITIZEN;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.egov.infra.admin.master.entity.Role;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.admin.master.service.CityService;
import org.egov.infra.config.core.ApplicationThreadLocals;
import org.egov.infra.security.utils.SecurityUtils;
import org.egov.infra.utils.ApplicationConstant;
import org.egov.portal.entity.CitizenInbox;
import org.egov.portal.entity.PortalInboxUser;
import org.egov.portal.service.CitizenInboxService;
import org.egov.portal.service.PortalInboxUserService;
import org.egov.portal.service.PortalServiceTypeService;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/home")
public class HomeController {

	public static final String BPA_FIRE_NOC_ROLE = "BPA_FIRE_NOC_ROLE";
	public static final String BPA_AIPORT_AUTH_NOC_ROLE = "BPA_AIPORT_AUTH_NOC_ROLE";
	public static final String BPA_NMA_NOC_ROLE = "BPA_NMA_NOC_ROLE";
	public static final String BPA_ENVIRONMENT_NOC_ROLE = "BPA_ENVIRONMENT_NOC_ROLE";

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CitizenInboxService citizenInboxService;

	@Autowired
	private SecurityUtils securityUtils;

	@Autowired
	private PortalInboxUserService portalInboxUserService;

	@Autowired
	private PortalServiceTypeService portalServiceTypeService;

	@Autowired
	private CityService cityService;

	@Value("${dev.mode}")
	private boolean devMode;

	@Value("${client.id}")
	private String clientId;

	@RequestMapping(method = RequestMethod.GET)
	public String showHomePage(ModelMap modelData) {
		return setupHomePage(modelData);
	}

	@RequestMapping(value = "/refreshInbox", method = RequestMethod.GET)
	public @ResponseBody Integer refreshInbox(@RequestParam final Long citizenInboxId) {
		final CitizenInbox citizenInbox = citizenInboxService.getInboxMessageById(citizenInboxId);
		citizenInbox.setRead(true);
		citizenInboxService.updateMessage(citizenInbox);
		return citizenInboxService.findUnreadMessagesCount(securityUtils.getCurrentUser());
	}

	private String setupHomePage(final ModelMap modelData) {
		String moduleName = "moduleNames";
		String services = "services";
		final User user = securityUtils.getCurrentUser();
		modelData.addAttribute("currentUser", user);
		modelData.addAttribute("unreadMessageCount", getUnreadMessageCount());
		modelData.addAttribute("inboxMessages", getAllInboxMessages());
		modelData.addAttribute("myAccountMessages", getMyAccountMessages());
		modelData.addAttribute("cityLogo", cityService.getCityLogoURL());
		modelData.addAttribute("cityName", cityService.getMunicipalityName());
		modelData.addAttribute("userName", user.getName() == null ? "Anonymous" : user.getName());
		// modelData.addAttribute("userType", user.getType());
		boolean isState = false;
		if (ApplicationConstant.STATE_TENANTID.equalsIgnoreCase(ApplicationThreadLocals.getTenantID())) {
			isState = true;
		}
		modelData.addAttribute("isState", isState);

		if (!devMode) {
			modelData.addAttribute("dflt_pwd_reset_req", checkDefaultPasswordResetRequired(user));
			int daysToExpirePwd = daysToExpirePassword(user);
			modelData.addAttribute("pwd_expire_in_days", daysToExpirePwd);
			modelData.addAttribute("warn_pwd_expire", daysToExpirePwd <= 5);
		}

		if (null != user) {

			if (isNOCDepartmentUser(user)) {
				modelData.addAttribute(moduleName, new ArrayList<String>());
				modelData.addAttribute(services, portalServiceTypeService.getAllPortalService());
			} else if (user.getType().equals(BUSINESS)) {

				List<String> businessUserModules = portalServiceTypeService.getDistinctModuleNamesForBusinessUser()
						.stream().map(md -> md.getDisplayName()).collect(Collectors.toList());
				modelData.addAttribute(moduleName, businessUserModules);
				modelData.addAttribute(services, portalServiceTypeService.findAllServiceTypesForBusinessUser());
			} else if (user.getType().equals(CITIZEN)) {
				List<String> citizenUserModules = portalServiceTypeService.getDistinctModuleNamesForCitizen().stream()
						.map(md -> md.getDisplayName()).collect(Collectors.toList());

				modelData.addAttribute(moduleName, citizenUserModules);
				modelData.addAttribute(services, portalServiceTypeService.findAllServiceTypesForCitizenUser());
			} else {
				modelData.addAttribute(moduleName, portalServiceTypeService.getDistinctModuleNames());
				modelData.addAttribute(services, portalServiceTypeService.getAllPortalService());
			}
		}

		modelData.addAttribute("distinctModuleNames", portalServiceTypeService.getAllModules());
		modelData.addAttribute("userId", user.getId());

		List<PortalInboxUser> totalServicesApplied = portalInboxUserService.getPortalInboxByUserId(user.getId());
		List<PortalInboxUser> totalServicesCompleted = portalInboxUserService.getPortalInboxByResolved(user.getId(),
				true);
		List<PortalInboxUser> totalServicesPending = portalInboxUserService.getPortalInboxByResolved(user.getId(),
				false);

		modelData.addAttribute("totalServicesPending", totalServicesPending);
		modelData.addAttribute("totalServicesApplied", totalServicesApplied);
		modelData.addAttribute("totalServicesCompleted", totalServicesCompleted);

		modelData.addAttribute("totalServicesPendingSize", totalServicesPending.size());
		modelData.addAttribute("totalServicesAppliedSize", totalServicesApplied.size());
		modelData.addAttribute("totalServicesCompletedSize", totalServicesCompleted.size());
		modelData.addAttribute("clientId", clientId);
		return "citizen-home";
	}

	private boolean isNOCDepartmentUser(User user) {
		Boolean check = false;
		for (Role r : user.getRoles()) {
			if (BPA_FIRE_NOC_ROLE.equals(r.getName()) || BPA_AIPORT_AUTH_NOC_ROLE.equals(r.getName())
					|| BPA_NMA_NOC_ROLE.equals(r.getName()) || BPA_ENVIRONMENT_NOC_ROLE.equals(r.getName())) {
				check = true;
			}
		}
		return check;
	}

	private List<CitizenInbox> getMyAccountMessages() {
		return citizenInboxService.findMyAccountMessages(securityUtils.getCurrentUser());
	}

	private List<CitizenInbox> getAllInboxMessages() {
		return citizenInboxService.findAllInboxMessage(securityUtils.getCurrentUser());
	}

	private Integer getUnreadMessageCount() {
		return citizenInboxService.findUnreadMessagesCount(securityUtils.getCurrentUser());
	}

	private boolean checkDefaultPasswordResetRequired(User user) {
		return passwordEncoder.matches("12345678", user.getPassword())
				|| passwordEncoder.matches("demo", user.getPassword());
	}

	private int daysToExpirePassword(User user) {
		return Days.daysBetween(new LocalDate(), user.getPwdExpiryDate().toLocalDate()).getDays();
	}

}
