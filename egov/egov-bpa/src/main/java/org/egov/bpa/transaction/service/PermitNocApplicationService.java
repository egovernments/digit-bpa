/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2017>  eGovernments Foundation
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
 */
package org.egov.bpa.transaction.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.egov.bpa.autonumber.NocNumberGenerator;
import org.egov.bpa.master.entity.Holiday;
import org.egov.bpa.master.entity.NocConfiguration;
import org.egov.bpa.master.service.HolidayListService;
import org.egov.bpa.master.service.NocConfigurationService;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.BpaNocApplication;
import org.egov.bpa.transaction.entity.BpaStatus;
import org.egov.bpa.transaction.entity.PermitNocApplication;
import org.egov.bpa.transaction.entity.PermitNocDocument;
import org.egov.bpa.transaction.entity.enums.NocIntegrationInitiationEnum;
import org.egov.bpa.transaction.entity.enums.NocIntegrationTypeEnum;
import org.egov.bpa.transaction.repository.PermitNocApplicationRepository;
import org.egov.bpa.utils.BpaConstants;
import org.egov.bpa.utils.BpaUtils;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.admin.master.service.UserService;
import org.egov.infra.config.core.ApplicationThreadLocals;
import org.egov.infra.persistence.entity.enums.UserType;
import org.egov.infra.utils.ApplicationConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class PermitNocApplicationService {
	
	@Autowired
	private PermitNocApplicationRepository permitNocRepository;
	@Autowired
	private BpaStatusService statusService;
	@Autowired
	private BpaUtils bpaUtils;
	@Autowired
    private NocConfigurationService nocConfigurationService;
	@Autowired
    private UserService userService;
	@Autowired
	private NocNumberGenerator nocNumberGenerator;
	@Autowired
	public HolidayListService holidayListService;

	
	@Transactional
	public PermitNocApplication save(final PermitNocApplication permitNoc) {
		return permitNocRepository.save(permitNoc);
	}
	
	@Transactional
	public List<PermitNocApplication> save(final List<PermitNocApplication> permitNoc) {
		return permitNocRepository.save(permitNoc);
	}
	
	public PermitNocApplication findByNocApplicationNumber(String appNo) {
		return permitNocRepository.findByNocApplicationNumber(appNo);		
	}
	
	public List<PermitNocApplication> findByPermitApplicationNumber(String appNo) {
		return permitNocRepository.findByPermitApplicationNumber(appNo);		
	}
	
	public List<PermitNocApplication> findInitiatedAppByType(final String nocType) {
		return permitNocRepository.findInitiatedAppByType(nocType);		
	}
	
	public PermitNocApplication findByApplicationNumberAndType(String appNo, final String nocType) {
		return permitNocRepository.findByApplicationNumberAndType(appNo, nocType);		
	}
	
	public PermitNocApplication createNocApplication(PermitNocApplication permitNoc, NocConfiguration nocConfig) {
		BpaStatus status = statusService.findByModuleTypeAndCode(BpaConstants.CHECKLIST_TYPE_NOC, BpaConstants.NOC_INITIATED);
		permitNoc.getBpaNocApplication().setNocApplicationNumber(nocNumberGenerator.generateNocNumber(nocConfig.getDepartment()));
		permitNoc.getBpaNocApplication().setNocType(nocConfig.getDepartment());
		permitNoc.getBpaNocApplication().setStatus(status);	
		addSlaEndDate(permitNoc.getBpaNocApplication(), nocConfig);
		return permitNocRepository.save(permitNoc);	
	}
	
	public void initiateNoc(BpaApplication application) {
    for (PermitNocDocument nocDocument : application.getPermitNocDocuments()) {
		PermitNocApplication permitNoc = new PermitNocApplication();
		BpaNocApplication nocApplication = new BpaNocApplication();

		List<User> nocUser = new ArrayList<>();
		List<User> userList = new ArrayList<>();
		NocConfiguration nocConfig = nocConfigurationService
				.findByDepartment(nocDocument.getNocDocument().getServiceChecklist().getChecklist().getCode());
		if (nocConfig.getApplicationType().trim().equalsIgnoreCase(BpaConstants.PERMIT) && nocConfig.getIntegrationType().equalsIgnoreCase(NocIntegrationTypeEnum.SEMI_AUTO.toString())
				&& nocConfig.getIntegrationInitiation().equalsIgnoreCase(NocIntegrationInitiationEnum.AUTO.toString())) {
			List<User> nocUsers = new ArrayList<User>(userService.getUsersByTypeAndTenantId(UserType.BUSINESS, ApplicationThreadLocals.getTenantID()));
			userList = nocUsers.stream()
		    	      .filter(usr -> usr.getRoles().stream()
		    	        .anyMatch(usrrl -> 
		    	          usrrl.getName().equals(BpaConstants.getNocRole().get(nocConfig.getDepartment()))))
		    	        .collect(Collectors.toList());	
			if(userList.isEmpty()) {
				nocUsers = userService.getUsersByTypeAndTenantId(UserType.BUSINESS, ApplicationConstant.STATE_TENANTID);
				userList = nocUsers.stream()
			    	      .filter(usr -> usr.getRoles().stream()
			    	        .anyMatch(usrrl -> 
			    	          usrrl.getName().equals(BpaConstants.getNocRole().get(nocConfig.getDepartment()))))
			    	        .collect(Collectors.toList());	
			}	
		     nocUser.add(userList.get(0));
		     permitNoc.setBpaApplication(application);
		     permitNoc.setBpaNocApplication(nocApplication);
		     permitNoc = createNocApplication(permitNoc, nocConfig);	
	        bpaUtils.createNocPortalUserinbox(permitNoc, nocUser, permitNoc.getBpaNocApplication().getStatus().getCode());
		}
      }
	}
	
	public PermitNocApplication createNoc(BpaApplication application, String nocType) {
		PermitNocApplication permitNoc = new PermitNocApplication();
		BpaNocApplication nocApplication = new BpaNocApplication();
		List<User> nocUser = new ArrayList<>();
		List<User> userList = new ArrayList<>();
		NocConfiguration nocConfig = nocConfigurationService
				.findByDepartment(nocType);
		if (nocConfig.getApplicationType().trim().equalsIgnoreCase(BpaConstants.PERMIT) && nocConfig.getIntegrationType().equalsIgnoreCase(NocIntegrationTypeEnum.SEMI_AUTO.toString())
				&& nocConfig.getIntegrationInitiation().equalsIgnoreCase(NocIntegrationInitiationEnum.MANUAL.toString())) {
			List<User> nocUsers = new ArrayList<User>(userService.getUsersByTypeAndTenantId(UserType.BUSINESS, ApplicationThreadLocals.getTenantID()));
			userList = nocUsers.stream()
		    	      .filter(usr -> usr.getRoles().stream()
		    	        .anyMatch(usrrl -> 
		    	          usrrl.getName().equals(BpaConstants.getNocRole().get(nocConfig.getDepartment()))))
		    	        .collect(Collectors.toList());	
			if(userList.isEmpty()) {
				nocUsers = userService.getUsersByTypeAndTenantId(UserType.BUSINESS, ApplicationConstant.STATE_TENANTID);
				userList = nocUsers.stream()
			    	      .filter(usr -> usr.getRoles().stream()
			    	        .anyMatch(usrrl -> 
			    	          usrrl.getName().equals(BpaConstants.getNocRole().get(nocConfig.getDepartment()))))
			    	        .collect(Collectors.toList());	
			}	
		     nocUser.add(userList.get(0));
		     permitNoc.setBpaApplication(application);
		     permitNoc.setBpaNocApplication(nocApplication);
		     permitNoc = createNocApplication(permitNoc, nocConfig);	
			 
		     permitNoc.getBpaNocApplication().setOwnerUser(nocUser.get(0));

	        bpaUtils.createNocPortalUserinbox(permitNoc, nocUser, permitNoc.getBpaNocApplication().getStatus().getCode());
		}
		return permitNoc;
	}
	
	public void addSlaEndDate(BpaNocApplication nocApplication,NocConfiguration nocConfig ) {
		
		Calendar c = Calendar.getInstance();
		c.setTime(new Date()); // todays date.
		c.add(Calendar.DATE, Integer.parseInt(nocConfig.getSla().toString())); 
		
		List<Holiday> holiday = holidayListService.findByFromAndToDate(new Date(), c.getTime());
		c.add(Calendar.DATE, holiday.size()); 

		nocApplication.setSlaEndDate(c.getTime());
	}
}