/*
 * eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 * accountability and the service delivery of the government  organizations.
 *
 *  Copyright (C) <2019>  eGovernments Foundation
 *
 *  The updated version of eGov suite of products as by eGovernments Foundation
 *  is available at http://www.egovernments.org
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see http://www.gnu.org/licenses/ or
 *  http://www.gnu.org/licenses/gpl.html .
 *
 *  In addition to the terms of the GPL license to be adhered to in using this
 *  program, the following additional terms are to be complied with:
 *
 *      1) All versions of this program, verbatim or modified must carry this
 *         Legal Notice.
 *      Further, all user interfaces, including but not limited to citizen facing interfaces,
 *         Urban Local Bodies interfaces, dashboards, mobile applications, of the program and any
 *         derived works should carry eGovernments Foundation logo on the top right corner.
 *
 *      For the logo, please refer http://egovernments.org/html/logo/egov_logo.png.
 *      For any further queries on attribution, including queries on brand guidelines,
 *         please contact contact@egovernments.org
 *
 *      2) Any misrepresentation of the origin of the material is prohibited. It
 *         is required that all modified versions of this material be marked in
 *         reasonable ways as different from the original version.
 *
 *      3) This license does not grant any rights to any user of the program
 *         with regards to rights under trademark law for use of the trade names
 *         or trademarks of eGovernments Foundation.
 *
 *  In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */

package org.egov.bpa.utils;

import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_REJECTED;
import static org.egov.bpa.utils.BpaConstants.EGMODULE_NAME;

import java.util.Date;
import java.util.List;

import org.egov.bpa.transaction.entity.OwnershipTransfer;
import org.egov.bpa.transaction.entity.PermitRenewal;
import org.egov.infra.admin.master.entity.Module;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.admin.master.service.ModuleService;
import org.egov.portal.entity.PortalInbox;
import org.egov.portal.entity.PortalInboxBuilder;
import org.egov.portal.service.PortalInboxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author vinoth
 *
 */
@Service
@Transactional(readOnly = true)
public class PushBpaApplicationToPortalUtil {

    private static final String SUCCESS = "Success";
    private static final String CLOSED = "Closed";
    private static final String WF_END_ACTION = "END";
    private static final String SERVICETYPE = "Permit Renewal - ";
    private static final String OWNERSHIPTRANSFER = "Ownership Transfer - ";

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private PortalInboxService portalInboxService;

    @Transactional
    public void updatePortalUserinbox(final PermitRenewal renewal, final User additionalPortalInboxUser) {
        Module module = moduleService.getModuleByName(EGMODULE_NAME);
        boolean isResolved = false;
        String status = renewal.getStatus().getDescription();

        if (renewal.getState() != null && (CLOSED.equals(renewal.getState().getValue())
                || WF_END_ACTION.equals(renewal.getState().getValue()))
                || renewal.getStatus() != null
                        && renewal.getStatus().getCode().equals(APPLICATION_STATUS_REJECTED))
            isResolved = true;
        String url = "/bpa/citizen/application/permit/renewal/update/" + renewal.getApplicationNumber();
        if (renewal.getStatus() != null)
            portalInboxService.updateInboxMessage(renewal.getApplicationNumber(), module.getId(),
                    status, isResolved, new Date(), renewal.getState(),
                    additionalPortalInboxUser, renewal.getRenewalNumber(), url);
    }

    @Transactional
    public void createPortalUserinbox(final PermitRenewal renewal, final List<User> portalInboxUser,
            final String workFlowAction) {
        String status = renewal.getStatus().getDescription();

        Module module = moduleService.getModuleByName(EGMODULE_NAME);
        boolean isResolved = false;
        String url = "/bpa/citizen/application/permit/renewal/update/" + renewal.getApplicationNumber();
        final PortalInboxBuilder portalInboxBuilder = new PortalInboxBuilder(module, renewal.getParent().getOwner().getName(),
                SERVICETYPE.concat(renewal.getParent().getServiceType().getDescription()), renewal.getApplicationNumber(),
                renewal.getRenewalNumber(), renewal.getId(), SUCCESS, SUCCESS, url, isResolved,
                status, new Date(), renewal.getState(), portalInboxUser);

        final PortalInbox portalInbox = portalInboxBuilder.build();
        portalInboxService.pushInboxMessage(portalInbox);
    }
    
    
    @Transactional
    public void updatePortalUserinbox(final OwnershipTransfer ownershipTransfer, final User additionalPortalInboxUser) {
        Module module = moduleService.getModuleByName(EGMODULE_NAME);
        boolean isResolved = false;
        String status = ownershipTransfer.getStatus().getDescription();

        if (ownershipTransfer.getState() != null && (CLOSED.equals(ownershipTransfer.getState().getValue())
                || WF_END_ACTION.equals(ownershipTransfer.getState().getValue()))
                || ownershipTransfer.getStatus() != null
                        && ownershipTransfer.getStatus().getCode().equals(APPLICATION_STATUS_REJECTED))
            isResolved = true;
        String url = "/bpa/citizen/application/ownership/transfer/update/" + ownershipTransfer.getApplicationNumber();
        if (ownershipTransfer.getStatus() != null)
            portalInboxService.updateInboxMessage(ownershipTransfer.getApplicationNumber(), module.getId(),
                    status, isResolved, new Date(), ownershipTransfer.getState(),
                    additionalPortalInboxUser, ownershipTransfer.getOwnershipNumber(), url);
    }

    @Transactional
    public void createPortalUserinbox(final OwnershipTransfer ownershipTransfer, final List<User> portalInboxUser,
            final String workFlowAction) {
        String status = ownershipTransfer.getStatus().getDescription();

        Module module = moduleService.getModuleByName(EGMODULE_NAME);
        boolean isResolved = false;
        String url = "/bpa/citizen/application/ownership/transfer/update/" + ownershipTransfer.getApplicationNumber();
        final PortalInboxBuilder portalInboxBuilder = new PortalInboxBuilder(module, ownershipTransfer.getParent().getOwner().getName(),
        		OWNERSHIPTRANSFER.concat(ownershipTransfer.getParent().getServiceType().getDescription()), ownershipTransfer.getApplicationNumber(),
                ownershipTransfer.getOwnershipNumber(), ownershipTransfer.getId(), SUCCESS, SUCCESS, url, isResolved,
                status, new Date(), ownershipTransfer.getState(), portalInboxUser);

        final PortalInbox portalInbox = portalInboxBuilder.build();
        portalInboxService.pushInboxMessage(portalInbox);
    }

}