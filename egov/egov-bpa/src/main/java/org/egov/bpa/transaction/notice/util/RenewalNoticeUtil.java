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

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_MODULE_TYPE;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.egov.bpa.config.reports.properties.BpaApplicationReportProperties;
import org.egov.bpa.transaction.entity.PermitRenewal;
import org.egov.bpa.transaction.entity.PermitRenewalConditions;
import org.egov.bpa.transaction.entity.PermitRenewalNotice;
import org.egov.bpa.transaction.entity.common.NoticeCommon;
import org.egov.bpa.transaction.entity.enums.ConditionType;
import org.egov.bpa.transaction.repository.PermitRenewalNoticeRepository;
import org.egov.bpa.transaction.service.PermitRenewalConditionsService;
import org.egov.bpa.transaction.service.PermitRenewalService;
import org.egov.bpa.transaction.workflow.BpaWorkFlowService;
import org.egov.bpa.utils.BpaUtils;
import org.egov.infra.admin.master.service.CityService;
import org.egov.infra.config.core.ApplicationThreadLocals;
import org.egov.infra.filestore.entity.FileStoreMapper;
import org.egov.infra.filestore.service.FileStoreService;
import org.egov.infra.reporting.engine.ReportOutput;
import org.egov.infra.reporting.engine.ReportRequest;
import org.egov.infra.reporting.engine.ReportService;
import org.egov.infra.workflow.entity.StateHistory;
import org.egov.pims.commons.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RenewalNoticeUtil {

    public static final String TWO_NEW_LINE = "\n\n";
    public static final String N_A = "N/A";
    public static final String ONE_NEW_LINE = "\n";
    private static final String APPLICATION_PDF = "application/pdf";
    private static final String APPLICATION_REJECTION_REASON = "applctn.reject.reason";
    private static final String APPLICATION_AUTO_REJECTION_REASON = "applctn.auto.reject.reason";
    public static final String SECTION_CLERK = "SECTION CLERK";

    @Autowired
    @Qualifier("fileStoreService")
    protected FileStoreService fileStoreService;
    @Autowired
    private ReportService reportService;
    @Autowired
    private BpaUtils bpaUtils;
    @Autowired
    @Qualifier("parentMessageSource")
    private MessageSource bpaMessageSource;
    @Autowired
    private PermitRenewalNoticeRepository renewalNoticeRepository;
    @Autowired
    private PermitRenewalConditionsService renewalConditionsService;
    @Autowired
    private CityService cityService;
    @Autowired
    private PermitRenewalService permitRenewalService;
    @Autowired
    private BpaNoticeUtil bpaNoticeUtil;
    @Autowired
    private BpaApplicationReportProperties bpaApplicationReportProperties;
    @Autowired
    private BpaWorkFlowService bpaWorkFlowService;

    public PermitRenewalNotice findByPermitRenewalAndNoticeType(final PermitRenewal permitRenewal, final String noticeType) {
        return renewalNoticeRepository.findByRenewAndNoticeType(permitRenewal, noticeType);
    }    

    public ReportOutput getReportOutput(PermitRenewal permitRenewal, String fileName, PermitRenewalNotice permitRenewalNotice,
            String rejectionfilename, String rejectionNoticeType, HttpServletRequest request) throws IOException {
        ReportOutput reportOutput = new ReportOutput();
        if (permitRenewalNotice == null || permitRenewalNotice.getNoticeCommon().getNoticeFileStore() == null) {
            final Map<String, Object> reportParams = bpaNoticeUtil.buildParametersForReport(permitRenewal.getParent());
            reportParams.putAll(getUlbDetails());
            reportParams.put("refusalFormat", bpaApplicationReportProperties.getRefusalFormat());
            reportParams.put("applicationNumber", permitRenewal.getApplicationNumber());
            reportParams.put("rejectionReasons", buildRejectionReasons(permitRenewal));
            reportParams.put("approverName", getRejector(permitRenewal));
            ReportRequest reportInput = new ReportRequest(rejectionfilename, permitRenewal, reportParams);
            reportOutput = reportService.createReport(reportInput);
            saveRenewalNotice(permitRenewal, fileName, reportOutput, rejectionNoticeType);
        } else {
            final FileStoreMapper fmp = permitRenewalNotice.getNoticeCommon().getNoticeFileStore();
            Path path = fileStoreService.fetchAsPath(fmp.getFileStoreId(), APPLICATION_MODULE_TYPE);
            reportOutput.setReportOutputData(Files.readAllBytes(path));
        }
        return reportOutput;
    }

    public PermitRenewalNotice saveRenewalNotice(PermitRenewal permitRenewal, String fileName, ReportOutput reportOutput, String noticeType) {
    	PermitRenewalNotice renewalNotice = new PermitRenewalNotice();
    	renewalNotice.setPermitRenewal(permitRenewal);
        NoticeCommon noticeCommon = new NoticeCommon();
        noticeCommon.setNoticeGeneratedDate(new Date());
        noticeCommon.setNoticeType(noticeType);
        noticeCommon.setNoticeFileStore(
                fileStoreService.store(new ByteArrayInputStream(reportOutput.getReportOutputData()), fileName, APPLICATION_PDF,
                        APPLICATION_MODULE_TYPE));
        renewalNotice.setNoticeCommon(noticeCommon);
        permitRenewal.addRenewalNotice(renewalNotice);
        permitRenewalService.savePermitRenewal(permitRenewal);
        return renewalNotice;
    }

    public Map<String, Object> getUlbDetails() {
        final Map<String, Object> ulbDetailsReportParams = new HashMap<>();
        ulbDetailsReportParams.put("cityName", ApplicationThreadLocals.getCityName());
        ulbDetailsReportParams.put("logoPath", cityService.getCityLogoAsStream());
        ulbDetailsReportParams.put("ulbName", ApplicationThreadLocals.getMunicipalityName());
        ulbDetailsReportParams.put("ulbGrade", cityService.getCityGrade());
        ulbDetailsReportParams.put("cityNameLocal", ApplicationThreadLocals.getCityNameLocal());
        return ulbDetailsReportParams;
    }    


    public String buildRejectionReasons(final PermitRenewal permitRenewal) {
        StringBuilder rejectReasons = new StringBuilder();
        if (!permitRenewal.getRejectionReasons().isEmpty()) {
            List<PermitRenewalConditions> additionalRenewalConditions = renewalConditionsService
                    .findAllConditionsByRenewalAndType(permitRenewal, ConditionType.ADDITIONALREJECTIONREASONS);
            int order = buildPredefinedRejectReasons(permitRenewal, rejectReasons);
            int additionalOrder = buildAdditionalRenewaltConditionsOrRejectionReason(rejectReasons, additionalRenewalConditions,
                    order);
            StateHistory<Position> stateHistory = bpaUtils.getRejectionComments(permitRenewal.getStateHistory());
            if (stateHistory != null && isNotBlank(stateHistory.getComments()))
                rejectReasons.append(additionalOrder + ") "
                        + stateHistory.getComments() + TWO_NEW_LINE);
        } else {
            rejectReasons.append(permitRenewal.getState().getComments() != null
                    && permitRenewal.getState().getComments().equalsIgnoreCase("Application cancelled by citizen")
                            ? getMessageFromPropertyFile(APPLICATION_REJECTION_REASON)
                            : getMessageFromPropertyFile(APPLICATION_AUTO_REJECTION_REASON));
        }
        return rejectReasons.toString();
    }
    
    public String getMessageFromPropertyFile(String key) {
        return bpaMessageSource.getMessage(key, null, null);
    }

    private int buildPredefinedRejectReasons(final PermitRenewal permitRenewal,
            StringBuilder permitConditions) {
        int order = 1;
        for (PermitRenewalConditions rejectReason : permitRenewal.getRejectionReasons()) {
            if (rejectReason.getNoticeCondition().isRequired()
                    && ConditionType.REJECTIONREASONS.equals(rejectReason.getNoticeCondition().getType())) {
                permitConditions
                        .append(order + ") "
                                + rejectReason.getNoticeCondition().getChecklistServicetype().getChecklist().getDescription()
                                + TWO_NEW_LINE);
                order++;
            }
        }
        return order;
    }
    
    private int buildAdditionalRenewaltConditionsOrRejectionReason(StringBuilder permitConditions,
            List<PermitRenewalConditions> additionalConditions, int order) {
        int additionalOrder = order;
        if (!additionalConditions.isEmpty()
                && isNotBlank(additionalConditions.get(0).getNoticeCondition().getAdditionalCondition())) {
            for (PermitRenewalConditions addnlNoticeCondition : additionalConditions) {
                if (isNotBlank(addnlNoticeCondition.getNoticeCondition().getAdditionalCondition())) {
                    permitConditions.append(
                            String.valueOf(additionalOrder) + ") "
                                    + addnlNoticeCondition.getNoticeCondition().getAdditionalCondition()
                                    + TWO_NEW_LINE);
                    additionalOrder++;
                }
            }
        }
        return additionalOrder;
    }
    
    private String getRejector(final PermitRenewal permitRenewal) {
        return bpaWorkFlowService.getApproverAssignmentByDate(permitRenewal.getState().getOwnerPosition(), permitRenewal.getState().getLastModifiedDate()).getEmployee().getName();
    }

}
