/*
 * eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 * accountability and the service delivery of the government  organizations.
 *
 *  Copyright (C) <2017>  eGovernments Foundation
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
package org.egov.bpa.transaction.notice.util;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_MODULE_TYPE;
import static org.egov.infra.security.utils.SecureCodeUtils.generatePDF417Code;
import static org.egov.infra.utils.DateUtils.currentDateToDefaultDateFormat;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.egov.bpa.transaction.entity.common.NoticeCommon;
import org.egov.bpa.transaction.entity.oc.OCNotice;
import org.egov.bpa.transaction.entity.oc.OccupancyCertificate;
import org.egov.bpa.transaction.repository.oc.OcNoticeRepository;
import org.egov.bpa.transaction.service.oc.OccupancyCertificateService;
import org.egov.bpa.transaction.workflow.BpaWorkFlowService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.infra.admin.master.service.CityService;
import org.egov.infra.config.core.ApplicationThreadLocals;
import org.egov.infra.filestore.service.FileStoreService;
import org.egov.infra.reporting.engine.ReportOutput;
import org.egov.infra.utils.DateUtils;
import org.egov.infra.workflow.entity.StateHistory;
import org.egov.pims.commons.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class OCNoticeUtil {
    public static final String ONE_NEW_LINE = "\n";
    private static final String N_A = "N/A";
    private static final String APPLICATION_PDF = "application/pdf";

    @Autowired
    private BpaNoticeUtil bpaNoticeUtil;
    @Autowired
    private BpaWorkFlowService bpaWorkFlowService;
    @Autowired
    private CityService cityService;
    @Autowired
    private OcNoticeRepository ocNoticeRepository;
    @Autowired
    private FileStoreService fileStoreService;
    @Autowired
    private OccupancyCertificateService occupancyCertificateService;

    public OCNotice findByOcAndNoticeType(OccupancyCertificate oc, String noticeType) {
        return ocNoticeRepository.findByOcAndNoticeType(oc, noticeType);
    }

    public OCNotice saveOcNotice(OccupancyCertificate oc, ReportOutput reportOutput, String noticeType) {
        OCNotice ocNotice = new OCNotice();
        ocNotice.setOc(oc);
        NoticeCommon noticeCommon = new NoticeCommon();
        noticeCommon.setNoticeGeneratedDate(new Date());
        noticeCommon.setNoticeType(noticeType);
        noticeCommon.setNoticeFileStore(
                fileStoreService.store(new ByteArrayInputStream(reportOutput.getReportOutputData()), oc.getOccupancyCertificateNumber(), APPLICATION_PDF,
                        APPLICATION_MODULE_TYPE));
        ocNotice.setNoticeCommon(noticeCommon);
        oc.addNotice(ocNotice);
        occupancyCertificateService.saveOccupancyCertificate(oc);
        return ocNotice;
    }

    public Map<String, Object> buildParametersForOc(OccupancyCertificate oc) {
        Map<String, Object> reportParams = new HashMap<>();
        reportParams.put("cityName", ApplicationThreadLocals.getCityName());
        reportParams.put("logoPath", cityService.getCityLogoAsStream());
        reportParams.put("stateLogo", BpaConstants.STATE_LOGO_PATH);
        reportParams.put("ulbName", ApplicationThreadLocals.getMunicipalityName());
        reportParams.put("permitNumber", oc.getParent().getPlanPermissionNumber() == null ? EMPTY : oc.getParent().getPlanPermissionNumber());
        reportParams.put("approvalDate", DateUtils.getDefaultFormattedDate(oc.getApprovalDate()));
        reportParams.put("currentDate", currentDateToDefaultDateFormat());
        reportParams.put("applicantName", oc.getParent().getOwner().getName());
        reportParams.put("approverName", getApproverName(oc));
        reportParams.put("approverDesignation", bpaNoticeUtil.getApproverDesignation(bpaWorkFlowService.getAmountRuleByServiceType(oc.getParent()).intValue()));
        reportParams.put("serviceType", oc.getParent().getServiceType().getDescription());
        reportParams.put("applicationDate", DateUtils.getDefaultFormattedDate(oc.getApplicationDate()));
        reportParams.put("applicationNumber", oc.getApplicationNumber());
        reportParams.put("noticeGenerationDate", currentDateToDefaultDateFormat());
        reportParams.put("lawAct", "[See Rule 22 (3) and 142 (2)]");
        reportParams.put("qrCode", generatePDF417Code(buildQRCodeDetails(oc)));
        return reportParams;
    }

    public String getApproverName(final OccupancyCertificate occupancyCertificate) {
        StateHistory<Position> stateHistory = occupancyCertificate.getStateHistory().stream()
                .filter(history -> history.getOwnerPosition().getDeptDesig().getDesignation().getName().equalsIgnoreCase(bpaNoticeUtil.getApproverDesignation(bpaWorkFlowService.getAmountRuleByServiceType(occupancyCertificate.getParent()).intValue())))
                .findAny().orElse(null);
        return stateHistory == null ? N_A : bpaWorkFlowService.getApproverAssignmentByDate(stateHistory.getOwnerPosition(), stateHistory.getLastModifiedDate()).getEmployee().getName();
    }

    public String buildQRCodeDetails(final OccupancyCertificate oc) {
        StringBuilder qrCodeValue = new StringBuilder();
        qrCodeValue = isBlank(ApplicationThreadLocals.getMunicipalityName()) ? qrCodeValue.append("") : qrCodeValue.append(ApplicationThreadLocals.getMunicipalityName()).append(ONE_NEW_LINE);
        qrCodeValue = oc.getParent().getOwner() == null || isBlank(oc.getParent().getOwner().getName()) ? qrCodeValue.append("Applicant Name : ").append(N_A).append(ONE_NEW_LINE) : qrCodeValue.append("Applicant Name : ").append(oc.getParent().getOwner().getName()).append(ONE_NEW_LINE);
        qrCodeValue = isBlank(oc.getApplicationNumber()) ? qrCodeValue.append("Application number : ").append(N_A).append(ONE_NEW_LINE) : qrCodeValue.append("Application number : ").append(oc.getApplicationNumber()).append(ONE_NEW_LINE);
        if (!isBlank(oc.getParent().geteDcrNumber())) {
            qrCodeValue = qrCodeValue.append("Edcr number : ").append(oc.getParent().geteDcrNumber()).append(ONE_NEW_LINE);
        }
        qrCodeValue = isBlank(oc.getParent().getPlanPermissionNumber()) ? qrCodeValue.append("Permit number : ").append(N_A).append(ONE_NEW_LINE) : qrCodeValue.append("Permit number : ").append(oc.getParent().getPlanPermissionNumber()).append(ONE_NEW_LINE);
        qrCodeValue = bpaWorkFlowService.getAmountRuleByServiceType(oc.getParent()) == null ? qrCodeValue.append("Approved by : ").append(N_A).append(ONE_NEW_LINE) : qrCodeValue.append("Approved by : ").append(bpaNoticeUtil.getApproverDesignation(bpaWorkFlowService.getAmountRuleByServiceType(oc.getParent()).intValue())).append(ONE_NEW_LINE);
        qrCodeValue = oc.getApprovalDate() == null ? qrCodeValue.append("Date of approval of oc : ").append(N_A).append(ONE_NEW_LINE) : qrCodeValue.append("Date of approval of oc : ").append(DateUtils.getDefaultFormattedDate(oc.getApprovalDate())).append(ONE_NEW_LINE);
        qrCodeValue = isBlank(getApproverName(oc)) ? qrCodeValue.append("Name of approver : ").append(N_A).append(ONE_NEW_LINE) : qrCodeValue.append("Name of approver : ").append(getApproverName(oc)).append(ONE_NEW_LINE);
        return qrCodeValue.toString();
    }
}
