/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2018>  eGovernments Foundation
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

package org.egov.bpa.transaction.service.oc;

import static org.springframework.util.ObjectUtils.isEmpty;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.egov.bpa.autonumber.LettertoPartyNumberGenerator;
import org.egov.bpa.autonumber.LettertoPartyReplyAckNumberGenerator;
import org.egov.bpa.master.entity.ChecklistServiceTypeMapping;
import org.egov.bpa.master.entity.LpReason;
import org.egov.bpa.transaction.entity.WorkflowBean;
import org.egov.bpa.transaction.entity.common.LetterToPartyDocumentCommon;
import org.egov.bpa.transaction.entity.oc.OCLetterToParty;
import org.egov.bpa.transaction.entity.oc.OccupancyCertificate;
import org.egov.bpa.transaction.repository.oc.OCLetterToPartyRepository;
import org.egov.bpa.transaction.service.BpaStatusService;
import org.egov.bpa.transaction.service.messaging.oc.OcSmsAndEmailService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.bpa.utils.BpaUtils;
import org.egov.commons.service.FinancialYearService;
import org.egov.infra.admin.master.service.CityService;
import org.egov.infra.reporting.engine.ReportOutput;
import org.egov.infra.reporting.engine.ReportRequest;
import org.egov.infra.reporting.engine.ReportService;
import org.egov.infra.utils.autonumber.AutonumberServiceBeanResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class OCLetterToPartyService {

    static final String LPCHK = "lp";
    static final String LPREPLYCHK = "lpreply";
    private static final String LETTER_TO_PARTY_INITIATE = "Letter to party initiate";
    public static final String CONTENT_DISPOSITION = "content-disposition";
    @Autowired
    BpaUtils bpaUtils;
    @Autowired
    private OCLetterToPartyRepository letterToPartyRepository;
    @Autowired
    private FinancialYearService financialYearService;
    @Autowired
    private AutonumberServiceBeanResolver beanResolver;
    @Autowired
    private ReportService reportService;
    @Autowired
    private CityService cityService;
    @Autowired
    private BpaStatusService bpaStatusService;
    @Autowired
    private OcSmsAndEmailService ocSmsAndEmailService;

    public List<OCLetterToParty> findAllByOC(final OccupancyCertificate oc) {
        return letterToPartyRepository.findByOcOrderByIdDesc(oc);
    }

    public OCLetterToParty findByOcApplicationNoAndInspectionNo(String applicationNo, String inspectionNo) {
        return letterToPartyRepository.findByOc_ApplicationNumberAndAndLetterToParty_LpNumber(applicationNo, inspectionNo);
    }

    public OCLetterToParty findById(Long id) {
        return letterToPartyRepository.findOne(id);
    }

    @Transactional
    public OCLetterToParty save(final OCLetterToParty ocLetterToParty, Long approvalPosition) {
        if (ocLetterToParty.getLetterToParty().getLpNumber() == null
                || "".equals(ocLetterToParty.getLetterToParty().getLpNumber())) {
            ocLetterToParty.getLetterToParty().setLetterDate(new Date());
            ocLetterToParty.getLetterToParty().setLpNumber(generateLetterToPartyNumber());
            bpaUtils.redirectToBpaWorkFlowForOC(ocLetterToParty.getOc(),
                    getWorkflowBean(approvalPosition, BpaConstants.LETTERTOPARTYINITIATE, LETTER_TO_PARTY_INITIATE));
           /* ocSmsAndEmailService.sendSMSAndEmailToApplicantForLettertoparty(ocLetterToParty.getOc());*/
        }

        if (ocLetterToParty.getLetterToParty().getReplyDate() != null) {
            ocLetterToParty.getLetterToParty().setAcknowledgementNumber(generateLetterToPartyReplyAck());
            bpaUtils.redirectToBpaWorkFlowForOC(ocLetterToParty.getOc(),
                    getWorkflowBean(ocLetterToParty.getOc().getState().getOwnerPosition().getId(), BpaConstants.LPCREATED,
                            BpaConstants.LPREPLYRECEIVED));
            ocLetterToParty.getOc().setStatus(bpaStatusService
                    .findByModuleTypeAndCode(BpaConstants.BPASTATUS_MODULETYPE, BpaConstants.LETTERTOPARTY_REPLY_RECEIVED));
        }
        return letterToPartyRepository.save(ocLetterToParty);
    }

    private WorkflowBean getWorkflowBean(Long approvalPosition, String currentState, String comments) {
        WorkflowBean wfBean = new WorkflowBean();
        wfBean.setApproverPositionId(approvalPosition);
        wfBean.setCurrentState(currentState);
        wfBean.setApproverComments(comments);
        wfBean.setWorkFlowAction(currentState);
        return wfBean;
    }

    public String generateLetterToPartyReplyAck() {
        final String financialYearRange = financialYearService
                .getCurrentFinancialYear().getFinYearRange();
        final LettertoPartyReplyAckNumberGenerator lettertoPartyReplyAckNumberGenerator = beanResolver
                .getAutoNumberServiceFor(LettertoPartyReplyAckNumberGenerator.class);
        return lettertoPartyReplyAckNumberGenerator.generateLettertoPartyReplyAckNumber(financialYearRange);
    }

    public String generateLetterToPartyNumber() {
        final String financialYearRange = financialYearService
                .getCurrentFinancialYear().getFinYearRange();
        final LettertoPartyNumberGenerator lettertoPartyNumberGenerator = beanResolver
                .getAutoNumberServiceFor(LettertoPartyNumberGenerator.class);
        return lettertoPartyNumberGenerator.generateLettertoPartyNumber(financialYearRange);
    }

    public ResponseEntity<byte[]> generateReport(final OCLetterToParty ocLetterToParty, String type,
            final HttpServletRequest request) {
        ReportRequest reportInput = null;
        ReportOutput reportOutput;
        if (ocLetterToParty != null) {
            if (LPCHK.equals(type))
                reportInput = new ReportRequest("oclettertoparty", ocLetterToParty,
                        buildReportParameters(ocLetterToParty, request));
            else if (LPREPLYCHK.equals(type))
                reportInput = new ReportRequest("oclettertopartyreply", ocLetterToParty,
                        buildReportParameters(ocLetterToParty, request));
            reportInput.setPrintDialogOnOpenReport(true);
        }
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        if (LPCHK.equals(type))
            headers.add(CONTENT_DISPOSITION, "inline;filename=lettertoparty.pdf");
        else if (LPREPLYCHK.equals(type))
            headers.add(CONTENT_DISPOSITION, "inline;filename=lettertopartyreply.pdf");
        reportOutput = reportService.createReport(reportInput);
        return new ResponseEntity<>(reportOutput.getReportOutputData(), headers, HttpStatus.CREATED);
    }

    public Map<String, Object> buildReportParameters(final OCLetterToParty ocLetterToParty, HttpServletRequest request) {
        final Map<String, Object> reportParams = new HashMap<>();
        Boolean checkListPresent = Boolean.FALSE;
        List<ChecklistServiceTypeMapping> chkList = new ArrayList<>();
        for (LetterToPartyDocumentCommon document : ocLetterToParty.getLetterToParty().getLetterToPartyDocuments()) {
            if (!isEmpty(document.getServiceChecklist()) && document.getIsRequested() == Boolean.TRUE
                    && document.getServiceChecklist().getChecklist().getDescription() != null) {
                chkList.add(document.getServiceChecklist());
            }
            checkListPresent = !chkList.isEmpty() ? Boolean.TRUE : Boolean.FALSE;
        }
        reportParams.put("checkListPresent", checkListPresent);
        reportParams.put("logoPath", cityService.getCityLogoAsStream());
        reportParams.put("cityName", request.getSession().getAttribute("cityname").toString());
        reportParams.put("ulbName", request.getSession().getAttribute("citymunicipalityname").toString());
        reportParams.put("lpReason",
                ocLetterToParty.getLetterToParty().getLpReason().stream().map(LpReason::getDescription)
                        .collect(Collectors.joining(",")));
        return reportParams;
    }
}
