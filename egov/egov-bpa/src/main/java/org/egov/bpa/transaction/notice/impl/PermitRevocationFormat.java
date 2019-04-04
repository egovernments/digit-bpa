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

package org.egov.bpa.transaction.notice.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.egov.bpa.master.entity.PermitRevocation;
import org.egov.bpa.master.entity.ServiceType;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.notice.util.BpaNoticeUtil;
import org.egov.bpa.transaction.workflow.BpaWorkFlowService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.infra.admin.master.service.CityService;
import org.egov.infra.config.core.ApplicationThreadLocals;
import org.egov.infra.reporting.engine.ReportFormat;
import org.egov.infra.reporting.engine.ReportOutput;
import org.egov.infra.reporting.engine.ReportRequest;
import org.egov.infra.reporting.engine.ReportService;
import org.egov.infra.reporting.util.ReportUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PermitRevocationFormat {

    @Autowired
    private BpaNoticeUtil bpaNoticeUtil;
    @Autowired
    private BpaWorkFlowService bpaWorkFlowService;
    @Autowired
    protected ReportService reportService;
    @Autowired
    private CityService cityService;

    public ReportOutput generateNotice(final PermitRevocation revocation) {
        ReportRequest reportInput = new ReportRequest("permit_revocation", revocation, buildParametersForReport(revocation));
        ReportOutput reportOutput = reportService.createReport(reportInput);
        reportOutput.setReportFormat(ReportFormat.PDF);
        return reportOutput;
    }

    public Map<String, Object> buildParametersForReport(final PermitRevocation revocation) {
        StringBuilder serviceTypeDesc = new StringBuilder();
        BpaApplication appln = revocation.getApplication();
        final Map<String, Object> reportParams = new HashMap<>();
        String approverName = bpaNoticeUtil.getApproverName(appln);
        String approverDesignation = bpaNoticeUtil
                .getApproverDesignation(bpaWorkFlowService.getAmountRuleByServiceType(appln).intValue());
        reportParams.put("lawAct", "[See Rule 16]");
        String amenities = appln.getApplicationAmenity().stream().map(ServiceType::getDescription)
                .collect(Collectors.joining(", "));
        if (appln.getApplicationAmenity().isEmpty()) {
            serviceTypeDesc.append(appln.getServiceType().getDescription());
        } else {
            serviceTypeDesc.append(appln.getServiceType().getDescription()).append(", ")
                    .append(amenities);
        }
        reportParams.put("serviceTypeDesc", serviceTypeDesc.toString());
        reportParams.put("designation", approverDesignation);
        reportParams.put("approverName", approverName);
        reportParams.put("cityName", ApplicationThreadLocals.getCityName());
        reportParams.put("logoPath", cityService.getCityLogoAsStream());
        reportParams.put("stateLogo", ReportUtil.getImageURL(BpaConstants.STATE_LOGO_PATH));
        reportParams.put("ulbName", ApplicationThreadLocals.getMunicipalityName());

        return reportParams;

    }
}
