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

package org.egov.bpa.service.es;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.egov.bpa.entity.es.PermitRenewalIndex;
import org.egov.bpa.repository.es.PermitRenewalIndexRepository;
import org.egov.bpa.transaction.entity.PermitRenewal;
import org.egov.bpa.transaction.workflow.BpaWorkFlowService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.commons.entity.Source;
import org.egov.eis.entity.Assignment;
import org.egov.infra.admin.master.entity.AppConfigValues;
import org.egov.infra.admin.master.entity.City;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.admin.master.service.AppConfigValueService;
import org.egov.infra.admin.master.service.CityService;
import org.egov.infra.admin.master.service.UserService;
import org.egov.infra.config.core.ApplicationThreadLocals;
import org.egov.infra.elasticsearch.entity.ApplicationIndex;
import org.egov.infra.elasticsearch.entity.enums.ApprovalStatus;
import org.egov.infra.elasticsearch.entity.enums.ClosureStatus;
import org.egov.infra.elasticsearch.service.ApplicationIndexService;
import org.egov.infra.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author vinoth
 *
 */
@Service
@Transactional(readOnly = true)
public class PermitRenewalIndexService {

    private static final String SLA_BPA_APPLICATION = "SLA_BPA_APPLICATION";

    @Autowired
    private CityService cityService;

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityUtils securityUtils;

    @Autowired
    private PermitRenewalIndexRepository permitRenewalIndexRepository;

    @Autowired
    private BpaWorkFlowService bpaWorkFlowService;

    @Autowired
    private AppConfigValueService appConfigValuesService;

    @Autowired
    private ApplicationIndexService applicationIndexService;

    public PermitRenewalIndex createPermitRenewalIndex(final PermitRenewal renewal) {
        final City cityWebsite = cityService.getCityByURL(ApplicationThreadLocals.getDomainName());
        PermitRenewalIndex prIndex = new PermitRenewalIndex();
        buildUlbDetails(cityWebsite, prIndex);
        prIndex.setId(cityWebsite.getCode() + "-" + renewal.getApplicationNumber());
        prIndex.setPermitApplication(String.valueOf(renewal.getParent().getId()));
        prIndex.setApplicationNumber(renewal.getApplicationNumber());
        Date applicationDate = renewal.getApplicationDate();
        prIndex.setApplicationDate(applicationDate);
        prIndex.setRenewalNumber(
                renewal.getRenewalNumber() == null ? EMPTY : renewal.getRenewalNumber());
        Date renewalApproveDate = renewal.getRenewalApprovalDate();
        if (renewalApproveDate != null) {
            prIndex.setRenewalApprovalDate(renewalApproveDate);
            int daysBetween = org.egov.infra.utils.DateUtils.daysBetween(applicationDate, renewalApproveDate);
            prIndex.setNoOfDaysToProcess(daysBetween);
        }
        prIndex.setStatus(renewal.getStatus() == null ? EMPTY : renewal.getStatus().getCode());
        permitRenewalIndexRepository.save(prIndex);
        return prIndex;
    }

    private void buildUlbDetails(final City cityWebsite, PermitRenewalIndex prIndex) {
        prIndex.setUlbName(cityWebsite.getName());
        prIndex.setDistrictName(cityWebsite.getDistrictName());
        prIndex.setRegionName(cityWebsite.getRegionName());
        prIndex.setUlbGrade(cityWebsite.getGrade());
        prIndex.setUlbCode(cityWebsite.getCode());
    }

    public void updateIndexes(final PermitRenewal renewal) {
        User user = getCurrentUser(renewal);
        ApplicationIndex applicationIndex = applicationIndexService
                .findByApplicationNumber(renewal.getApplicationNumber());
        if (applicationIndex == null || renewal.getId() == null) {
            String viewUrl = "/bpa/application/permit/renewal/view/%s";
            List<AppConfigValues> appConfigValue = appConfigValuesService
                    .getConfigValuesByModuleAndKey(BpaConstants.APPLICATION_MODULE_TYPE, SLA_BPA_APPLICATION);
            Date disposalDate = calculateDisposalDate(appConfigValue);
            applicationIndex = ApplicationIndex.builder().withModuleName(BpaConstants.APPL_INDEX_MODULE_NAME)
                    .withApplicationNumber(renewal.getApplicationNumber())
                    .withApplicationDate(renewal.getApplicationDate())
                    .withApplicationType("Permit Renewal")
                    .withOwnername(user == null ? EMPTY : user.getUsername() + "::" + user.getName())
                    .withApplicantName(renewal.getParent().getOwner().getName())
                    .withApplicantAddress(renewal.getParent().getOwner().getAddress())
                    .withStatus(renewal.getStatus().getCode())
                    .withChannel(renewal.getSource() == null ? Source.SYSTEM.toString()
                            : renewal.getSource().name())
                    .withConsumerCode(renewal.getApplicationNumber())
                    .withMobileNumber(renewal.getParent().getOwner().getUser().getMobileNumber())
                    .withAadharNumber(StringUtils.isBlank(renewal.getParent().getOwner().getAadhaarNumber()) ? EMPTY
                            : renewal.getParent().getOwner().getAadhaarNumber())
                    .withUrl(String.format(viewUrl, renewal.getApplicationNumber())).withClosed(ClosureStatus.NO)
                    .withSla(appConfigValue.get(0).getValue() == null ? 0
                            : Integer.valueOf(appConfigValue.get(0).getValue()))
                    .withDisposalDate(disposalDate).withApproved(ApprovalStatus.INPROGRESS).build();
            applicationIndexService.createApplicationIndex(applicationIndex);
            createPermitRenewalIndex(renewal);
        } else {
            buildApplicationIndexForUpdate(renewal, user, applicationIndex);
        }
    }

    private void buildApplicationIndexForUpdate(final PermitRenewal renewal, User user,
            ApplicationIndex applicationIndex) {
        applicationIndex.setStatus(renewal.getStatus().getCode());
        applicationIndex.setOwnerName(user == null ? "" : user.getUsername() + "::" + user.getName());
        if (renewal.getStatus().getCode().equals(BpaConstants.APPLICATION_STATUS_CANCELLED)) {
            applicationIndex.setApproved(ApprovalStatus.REJECTED);
            applicationIndex.setClosed(ClosureStatus.YES);
        } else if (renewal.getStatus().getCode().equals(BpaConstants.APPLICATION_STATUS_ORDER_ISSUED)) {
            applicationIndex.setApproved(ApprovalStatus.APPROVED);
            applicationIndex.setClosed(ClosureStatus.YES);
        }

        if (renewal.getRenewalNumber() != null)
            applicationIndex.setConsumerCode(renewal.getRenewalNumber());
        applicationIndexService.updateApplicationIndex(applicationIndex);
        createPermitRenewalIndex(renewal);
    }

    private User getCurrentUser(final PermitRenewal renewal) {
        User user = null;
        if (renewal.getState() == null || renewal.getState().getOwnerPosition() == null) {
            user = securityUtils.getCurrentUser();
        } else {
            Assignment assignment = bpaWorkFlowService
                    .getApproverAssignmentByDate(renewal.getState().getOwnerPosition(), new Date());
            List<Assignment> assignments;
            if (assignment == null) {
                assignments = bpaWorkFlowService.getAssignmentsByPositionAndDate(
                        renewal.getState().getOwnerPosition().getId(), new Date());
            } else {
                assignments = new ArrayList<>();
                assignments.add(assignment);
            }

            if (!assignments.isEmpty())
                user = userService.getUserById(assignments.get(0).getEmployee().getId());
        }

        return user;
    }

    private Date calculateDisposalDate(List<AppConfigValues> appConfigValue) {
        return DateUtils.addDays(new Date(),
                appConfigValue.get(0) == null || appConfigValue.get(0).getValue() == null ? 0
                        : Integer.valueOf(appConfigValue.get(0).getValue()));
    }
}
