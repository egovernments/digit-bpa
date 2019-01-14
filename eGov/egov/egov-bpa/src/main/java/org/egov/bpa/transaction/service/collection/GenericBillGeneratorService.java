/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2015>  eGovernments Foundation
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
package org.egov.bpa.transaction.service.collection;

import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.oc.OccupancyCertificate;
import org.egov.bpa.utils.BpaConstants;
import org.egov.bpa.utils.BpaUtils;
import org.egov.infra.admin.master.entity.Role;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.admin.master.service.UserService;
import org.egov.infra.config.core.ApplicationThreadLocals;
import org.egov.infra.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

@Service
@Transactional(readOnly = true)
public class GenericBillGeneratorService {
    @Autowired
    private SecurityUtils securityUtils;
    @Autowired
    private UserService userService;
    @Autowired
    private BpaUtils bpaUtils;
    @Autowired
    private ApplicationBpaBillService applicationBpaBillService;
    @Autowired
    private OccupancyCertificateBillService occupancyCertificateBillService;

    @Transactional
    public String generateBillAndRedirectToCollection(final BpaApplication application, final Model model) {
        return buildAndRedirectToCollection(model, applicationBpaBillService.generateBill(application));
    }

    @Transactional
    public String generateBillAndRedirectToCollection(final OccupancyCertificate oc, final Model model) {
        return buildAndRedirectToCollection(model, occupancyCertificateBillService.generateBill(oc));
    }

    private String buildAndRedirectToCollection(Model model, String s) {
        if (ApplicationThreadLocals.getUserId() == null)
            if (securityUtils.getCurrentUser().getUsername().equals(BpaConstants.USERNAME_ANONYMOUS))
                ApplicationThreadLocals.setUserId(userService.getUserByUsername(BpaConstants.USERNAME_ANONYMOUS).getId());
        model.addAttribute("collectxml", s);
        model.addAttribute("citizenrole", getCitizenUserRole());
        String enableOrDisablePayOnline = bpaUtils.getAppconfigValueByKeyName(BpaConstants.ENABLEONLINEPAYMENT);
        model.addAttribute("onlinePaymentEnable", (enableOrDisablePayOnline.equalsIgnoreCase("YES") ? Boolean.TRUE : Boolean.FALSE));
        return "collecttax-redirection";
    }

    //NOTE: as off now Giving pay online For Architect and Citizen as Per Bpa Requirement
    public Boolean getCitizenUserRole() {
        Boolean citizenrole = Boolean.FALSE;
        if (ApplicationThreadLocals.getUserId() != null) {
            final User currentUser = userService.getUserById(ApplicationThreadLocals.getUserId());
            if (currentUser.getRoles().isEmpty()
                    && securityUtils.getCurrentUser().getUsername().equals(BpaConstants.USERNAME_ANONYMOUS))
                citizenrole = Boolean.TRUE;
            for (final Role userrole : currentUser.getRoles())
                if (userrole != null && (userrole.getName().equals(BpaConstants.ROLE_CITIZEN)||
                		userrole.getName().equals(BpaConstants.ROLE_BUSINESS_USER))) {
                    citizenrole = Boolean.TRUE;
                    break;
                }
        } else
            citizenrole = Boolean.TRUE;
        return citizenrole;
    }

}
