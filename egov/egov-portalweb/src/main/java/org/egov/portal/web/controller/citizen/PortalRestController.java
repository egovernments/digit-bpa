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

package org.egov.portal.web.controller.citizen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.egov.portal.entity.InboxRenderResponse;
import org.egov.portal.entity.PortalInbox;
import org.egov.portal.entity.PortalInboxHelper;
import org.egov.portal.entity.PortalInboxUser;
import org.egov.portal.service.PortalInboxUserService;
import org.egov.portal.util.PortalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;

@RestController
public class PortalRestController {

    @Autowired
    private PortalInboxUserService portalInboxUserService;
    @Autowired
    private PortalUtils portalUtils;

    @PostMapping(value = "/rest/fetch/servicesapplied", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public InboxRenderResponse fetchServicesApplied(@RequestParam final Long id) {
        List<PortalInboxUser> totalServicesAppliedList = enrichPortalInboxUser(portalInboxUserService.getPortalInboxByUserId(id));
        InboxRenderResponse inboxRenderResponse = new InboxRenderResponse();
        inboxRenderResponse.setPortalInboxHelper(getPortalInboxHelperList(totalServicesAppliedList));
        inboxRenderResponse.setTotalServices((long) totalServicesAppliedList.size());
        inboxRenderResponse.setCompletedServices(portalInboxUserService.getPortalInboxUserCountByResolved(id, true));
        inboxRenderResponse.setUnderScrutiny(portalInboxUserService.getPortalInboxUserCountByResolved(id, false));

        return inboxRenderResponse;
    }

    @PostMapping(value = "/rest/fetch/servicescompleted", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public InboxRenderResponse fetchServicesCompleted(@RequestParam final Long id) {
        List<PortalInboxUser> totalServicesCompletedList = enrichPortalInboxUser(
                portalInboxUserService.getPortalInboxByResolved(id, true));
        InboxRenderResponse inboxRenderResponse = new InboxRenderResponse();
        inboxRenderResponse.setPortalInboxHelper(getPortalInboxHelperList(totalServicesCompletedList));
        inboxRenderResponse.setTotalServices(portalInboxUserService.getPortalInboxUserCount(id));
        inboxRenderResponse.setCompletedServices((long) totalServicesCompletedList.size());
        inboxRenderResponse.setUnderScrutiny(portalInboxUserService.getPortalInboxUserCountByResolved(id, false));

        return inboxRenderResponse;
    }

    @PostMapping(value = "/rest/fetch/servicespending", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public InboxRenderResponse fetchServicesUnderScrutiny(@RequestParam final Long id) {
        List<PortalInboxUser> totalServicesPendingList = enrichPortalInboxUser(
                portalInboxUserService.getPortalInboxByResolved(id, false));
        InboxRenderResponse inboxRenderResponse = new InboxRenderResponse();
        inboxRenderResponse.setPortalInboxHelper(getPortalInboxHelperList(totalServicesPendingList));
        inboxRenderResponse.setTotalServices(portalInboxUserService.getPortalInboxUserCount(id));
        inboxRenderResponse.setCompletedServices(portalInboxUserService.getPortalInboxUserCountByResolved(id, true));
        inboxRenderResponse.setUnderScrutiny((long) totalServicesPendingList.size());

        return inboxRenderResponse;
    }

    @PostMapping(value = "/rest/fetch/services/count/by-servicegroup", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void fetchCountOfApplicationsByUserAndServiceGroup(@RequestParam final Long id,
            @RequestParam final String serviceContextRoot, final HttpServletResponse response) throws IOException {
        Long underScrutiny;
        Long completed;
        if ("all".equalsIgnoreCase(serviceContextRoot)) {
            underScrutiny = portalInboxUserService.getPortalInboxUserCountByResolved(id, false);
            completed = portalInboxUserService.getPortalInboxUserCountByResolved(id, true);
        } else {
            underScrutiny = portalInboxUserService.getPortalInboxUserCountByResolvedAndModule(id, false, serviceContextRoot);
            completed = portalInboxUserService.getPortalInboxUserCountByResolvedAndModule(id, true, serviceContextRoot);
        }
        Long total = underScrutiny + completed;
        final JsonObject jsonObj = new JsonObject();
        jsonObj.addProperty("underScrutiny", underScrutiny);
        jsonObj.addProperty("completedServices", completed);
        jsonObj.addProperty("totalServices", total);
        IOUtils.write(jsonObj.toString(), response.getWriter());
    }

    public List<PortalInboxHelper> getPortalInboxHelperList(List<PortalInboxUser> servicesList) {
        List<PortalInboxHelper> portalInboxHelperList = new ArrayList<>();
        for (PortalInboxUser totalServicesApplied : servicesList) {
            PortalInboxHelper portalInboxHelper = new PortalInboxHelper();
            PortalInbox portalInbox = totalServicesApplied.getPortalInbox();
            portalInboxHelper.setUlbName(portalInbox.getTenantId());
            portalInboxHelper.setApplicantName(portalInbox.getApplicantName());
            portalInboxHelper.setServiceRequestNo(portalInbox.getApplicationNumber());
            portalInboxHelper.setServiceRequestDate(portalInbox.getApplicationDate());
            portalInboxHelper.setServiceGroup(portalInbox.getModule().getName());
            portalInboxHelper.setServiceName(portalInbox.getServiceType());
            portalInboxHelper.setLink(portalInbox.getLink());
            portalInboxHelper.setStatus(portalInbox.getStatus());
            portalInboxHelper.setPendingAction(portalInbox.getState() != null ? portalInbox.getState().getNextAction() : null);
            portalInboxHelper.setResolved(portalInbox.isResolved());
            portalInboxHelper.setTenantId(portalInbox.getTenantId());
            portalInboxHelper.setDomainUrl(portalInbox.getDomainUrl());
            portalInboxHelperList.add(portalInboxHelper);
        }
        return portalInboxHelperList;
    }

    private List<PortalInboxUser> enrichPortalInboxUser(List<PortalInboxUser> portalInboxUsers) {
        Map<String, String> allTenants = portalUtils.tenantsMap();
        portalInboxUsers.stream().forEach((portalInboxUser) -> {
            portalInboxUser.getPortalInbox()
                    .setDomainUrl(allTenants.get(portalInboxUser.getPortalInbox().getTenantId()));
        });
        return portalInboxUsers;
    }
}