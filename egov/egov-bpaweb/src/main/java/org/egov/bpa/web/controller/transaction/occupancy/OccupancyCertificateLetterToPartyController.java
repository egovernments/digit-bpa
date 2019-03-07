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
package org.egov.bpa.web.controller.transaction.occupancy;

import static org.egov.bpa.utils.BpaConstants.BPA_APPLICATION;
import static org.egov.bpa.utils.OcConstants.OCCUPANCY_CERTIFICATE;
import static org.egov.bpa.utils.OcConstants.OC_LTP_CHECKLIST;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.egov.bpa.master.entity.ChecklistServiceTypeMapping;
import org.egov.bpa.master.entity.LpReason;
import org.egov.bpa.master.service.ChecklistServicetypeMappingService;
import org.egov.bpa.master.service.LpReasonService;
import org.egov.bpa.transaction.entity.common.LetterToPartyDocumentCommon;
import org.egov.bpa.transaction.entity.oc.OCLetterToParty;
import org.egov.bpa.transaction.entity.oc.OccupancyCertificate;
import org.egov.bpa.transaction.notice.OccupancyLetterToPartyFormat;
import org.egov.bpa.transaction.notice.impl.OccupancyLetterToPartyFormatImpl;
import org.egov.bpa.transaction.notice.impl.OccupancyLetterToPartyReplyFormatImpl;
import org.egov.bpa.transaction.service.ApplicationBpaService;
import org.egov.bpa.transaction.service.WorkflowHistoryService;
import org.egov.bpa.transaction.service.oc.OCLetterToPartyService;
import org.egov.bpa.transaction.service.oc.OccupancyCertificateService;
import org.egov.bpa.transaction.workflow.BpaWorkFlowService;
import org.egov.bpa.utils.BpaUtils;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.custom.CustomImplProvider;
import org.egov.infra.filestore.service.FileStoreService;
import org.egov.infra.reporting.engine.ReportOutput;
import org.egov.infra.security.utils.SecurityUtils;
import org.egov.infra.workflow.entity.StateHistory;
import org.egov.pims.commons.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/occupancy-certificate/letter-to-party")
public class OccupancyCertificateLetterToPartyController {
    private static final String PDFEXTN = ".pdf";
    private static final String INLINE_FILENAME = "inline;filename=";
    private static final String CONTENT_DISPOSITION = "content-disposition";
    static final String LPCHK = "lp";
    static final String LPREPLYCHK = "lpreply";
    private static final String MSG_LP_REPLY_SUCCESS = "msg.lettertoparty.reply.success";
    private static final String MSG_LP_FORWARD_CREATE = "msg.lp.forward.create";
    private static final String MSG_LP_UPDATE_SUCCESS = "msg.lettertoparty.update.success";
    private static final String LP_REPLY = "oc-ltp-reply";
    private static final String LP_CAPTURE_SENT_DATE = "oc-ltp-capture-sent-date";
    private static final String LETTER_TO_PARTY_VIEW = "oc-ltp-view";
    private static final String LETTER_TO_PARTY_RESULT = "oc-ltp-result";
    private static final String LETTER_TO_PARTY_UPDATE = "oc-ltp-update";
    private static final String LETTER_TO_PARTY_CREATE = "oc-ltp-create";
    private static final String REDIRECT_LETTER_TO_PARTY_RESULT = "redirect:/occupancy-certificate/letter-to-party/result/";
    private static final String CHECK_LIST_DETAIL_LIST = "checkListDetailList";
    private static final String LETTER_TO_PARTY_DOC_LIST = "letterToPartyDocList";
    private static final String OC_LETTER_TO_PARTY = "ocLetterToParty";
    private static final String MESSAGE = "message";
    @Autowired
    @Qualifier("fileStoreService")
    protected FileStoreService fileStoreService;
    @Autowired
    protected ResourceBundleMessageSource messageSource;
    @Autowired
    protected WorkflowHistoryService workflowHistoryService;
    @Autowired
    private ApplicationBpaService applicationBpaService;
    @Autowired
    private LpReasonService lpReasonService;
    @Autowired
    private ChecklistServicetypeMappingService checklistServicetypeService;
    @Autowired
    private SecurityUtils securityUtils;
    @Autowired
    private BpaUtils bpaUtils;
    @Autowired
    private BpaWorkFlowService bpaWorkFlowService;
    @Autowired
    private OCLetterToPartyService ocLetterToPartyService;
    @Autowired
    private OccupancyCertificateService occupancyCertificateService;
    @Autowired
    private CustomImplProvider specificNoticeService;

    @ModelAttribute(name = "lpReasonList")
    public List<LpReason> getLpReasonList() {
        return lpReasonService.findAll();
    }

    public List<ChecklistServiceTypeMapping> getCheckListDetailList(final Long serviceTypeId) {
        return checklistServicetypeService.findByActiveByServiceTypeAndChecklist(serviceTypeId, OC_LTP_CHECKLIST);
    }

    @GetMapping("/create/{applicationNumber}")
    public String newLetterToParty(@ModelAttribute final OCLetterToParty ocLetterToParty,
            @PathVariable final String applicationNumber, final Model model) {
        prepareData(ocLetterToParty, applicationNumber, model);
        return LETTER_TO_PARTY_CREATE;
    }

    private void prepareData(final OCLetterToParty ocLetterToParty, final String applicationNumber, final Model model) {
        model.addAttribute("mode", "new");
        OccupancyCertificate oc = occupancyCertificateService.findByApplicationNumber(applicationNumber);
        model.addAttribute(OC_LETTER_TO_PARTY, ocLetterToParty);
        model.addAttribute(BPA_APPLICATION, oc.getParent());
        model.addAttribute(OCCUPANCY_CERTIFICATE, oc);
        model.addAttribute(CHECK_LIST_DETAIL_LIST, getCheckListDetailList(oc.getParent().getServiceType().getId()));
        ocLetterToParty.setOc(oc);
    }

    public void validateCreateLetterToParty(OCLetterToParty ocLetterToParty, BindingResult errors) {
        if (ocLetterToParty.getLetterToParty().getLpReason() == null)
            errors.rejectValue("lpReason", "lbl.lp.reason.required");
    }

    @PostMapping("/create/{applicationNumber}")
    public String createLetterToParty(@ModelAttribute final OCLetterToParty ocLetterToParty,
            final Model model, @PathVariable final String applicationNumber,
            final BindingResult errors, final RedirectAttributes redirectAttributes) {
        validateCreateLetterToParty(ocLetterToParty, errors);
        if (errors.hasErrors()) {
            prepareData(ocLetterToParty, ocLetterToParty.getOc().getApplicationNumber(), model);
            return LETTER_TO_PARTY_CREATE;
        }
        processAndStoreLetterToPartyDocuments(ocLetterToParty);
        ocLetterToParty.getLetterToParty().setCurrentApplnStatus(ocLetterToParty.getOc().getStatus());
        ocLetterToParty.getLetterToParty().setCurrentStateValueOfLP(getStateHistoryObjByDesc(ocLetterToParty).getValue());
        ocLetterToParty.getLetterToParty().setStateForOwnerPosition(ocLetterToParty.getOc().getState().getValue());
        ocLetterToParty.getLetterToParty().setPendingAction(getStateHistoryObjByDesc(ocLetterToParty).getNextAction());
        List<OCLetterToParty> existingLetterToParties = ocLetterToPartyService.findAllByOC(ocLetterToParty.getOc());
        if (!existingLetterToParties.isEmpty()) {
            OCLetterToParty existingLpParty = existingLetterToParties.get(0);
            if (existingLpParty.getCreatedBy().equals(securityUtils.getCurrentUser())
                    && existingLpParty.getLetterToParty().getCurrentApplnStatus()
                            .equals(ocLetterToParty.getLetterToParty().getCurrentApplnStatus())) {
                ocLetterToParty.getLetterToParty()
                        .setCurrentStateValueOfLP(existingLpParty.getLetterToParty().getCurrentStateValueOfLP());
                ocLetterToParty.getLetterToParty()
                        .setStateForOwnerPosition(existingLpParty.getLetterToParty().getStateForOwnerPosition());
                ocLetterToParty.getLetterToParty().setPendingAction(existingLpParty.getLetterToParty().getPendingAction());
            }
        }
        Position pos = bpaWorkFlowService.getApproverPositionOfElectionWardByCurrentStateForOC(ocLetterToParty.getOc(),
                "LP Initiated");
        OCLetterToParty ocLtpRes = ocLetterToPartyService.save(ocLetterToParty, pos.getId());
        User user = workflowHistoryService.getUserPositionByPassingPosition(pos.getId());
        String message = messageSource.getMessage(MSG_LP_FORWARD_CREATE, new String[] {
                user != null ? user.getUsername().concat("~")
                        .concat(getApproverDesigName(pos))
                        : "",
                ocLtpRes.getLetterToParty().getLpNumber(), ocLtpRes.getOc().getApplicationNumber() },
                LocaleContextHolder.getLocale());
        redirectAttributes.addFlashAttribute(MESSAGE, message);
        return REDIRECT_LETTER_TO_PARTY_RESULT + applicationNumber + "/" + ocLtpRes.getLetterToParty().getLpNumber();
    }

    private String getApproverDesigName(Position pos) {
        return pos.getDeptDesig() != null && pos.getDeptDesig().getDesignation() != null
                ? pos.getDeptDesig().getDesignation().getName()
                : "";
    }

    private StateHistory<Position> getStateHistoryObjByDesc(final OCLetterToParty ocLetterToParty) {
        return ocLetterToParty.getOc().getStateHistory().stream()
                .sorted(Comparator.comparing(StateHistory<Position>::getId).reversed()).collect(Collectors.toList()).get(0);
    }

    @GetMapping("/update/{applicationNumber}/{lpNumber}")
    public String editLetterToParty(@PathVariable final String applicationNumber, @PathVariable final String lpNumber,
            final Model model) {
        prepareLetterToParty(applicationNumber, lpNumber, model);
        return LETTER_TO_PARTY_UPDATE;
    }

    private void prepareLetterToParty(String applicationNumber, String lpNumber, Model model) {
        OCLetterToParty ocLetterToParty = ocLetterToPartyService.findByOcApplicationNoAndInspectionNo(applicationNumber,
                lpNumber);
        if (ocLetterToParty != null) {
            model.addAttribute(OC_LETTER_TO_PARTY, ocLetterToParty);
            model.addAttribute(LETTER_TO_PARTY_DOC_LIST, ocLetterToParty.getLetterToParty().getLetterToPartyDocuments());
        }
        model.addAttribute(CHECK_LIST_DETAIL_LIST,
                getCheckListDetailList(ocLetterToParty.getOc().getParent().getServiceType().getId()));
        model.addAttribute(BPA_APPLICATION, ocLetterToParty.getOc().getParent());
        model.addAttribute(OCCUPANCY_CERTIFICATE, ocLetterToParty.getOc());
    }

    @PostMapping("/update/{applicationNumber}/{lpNumber}")
    public String updateLetterToParty(@Valid @ModelAttribute("ocLetterToParty") final OCLetterToParty ocLetterToParty,
            @PathVariable final String applicationNumber,
            @PathVariable final String lpNumber, final RedirectAttributes redirectAttributes) {
        processAndStoreLetterToPartyDocuments(ocLetterToParty);
        OCLetterToParty ocLtpRes = ocLetterToPartyService.save(ocLetterToParty,
                ocLetterToParty.getOc().getState().getOwnerPosition().getId());
        redirectAttributes.addFlashAttribute(MESSAGE,
                messageSource.getMessage(MSG_LP_UPDATE_SUCCESS, null, null));
        return REDIRECT_LETTER_TO_PARTY_RESULT + applicationNumber + "/" + ocLtpRes.getLetterToParty().getLpNumber();
    }

    @GetMapping("/result/{applicationNumber}/{lpNumber}")
    public String resultLetterToParty(@PathVariable final String applicationNumber, @PathVariable final String lpNumber,
            final Model model) {
        prepareLetterToParty(applicationNumber, lpNumber, model);
        return LETTER_TO_PARTY_RESULT;
    }

    protected void processAndStoreLetterToPartyDocuments(final OCLetterToParty ocLetterToParty) {
        if (!ocLetterToParty.getLetterToParty().getLetterToPartyDocuments().isEmpty())
            for (final LetterToPartyDocumentCommon lpDocumentCommon : ocLetterToParty.getLetterToParty()
                    .getLetterToPartyDocuments()) {
                lpDocumentCommon.setLetterToParty(ocLetterToParty.getLetterToParty());
                if (lpDocumentCommon.getFiles() != null && lpDocumentCommon.getFiles().length > 0) {
                    lpDocumentCommon.setSupportDocs(applicationBpaService.addToFileStore(lpDocumentCommon.getFiles()));
                    lpDocumentCommon.setIsSubmitted(true);
                }
            }
    }

    @GetMapping("/print/lp/{applicationNumber}/{lpNumber}")
    @ResponseBody
    public ResponseEntity<InputStreamResource> generateLettertoPartyCreate(@PathVariable final String applicationNumber,
            @PathVariable final String lpNumber, final HttpServletRequest request) {
        final OCLetterToParty ocLetterToParty = ocLetterToPartyService.findByOcApplicationNoAndInspectionNo(applicationNumber,
                lpNumber);
        OccupancyLetterToPartyFormat ocLetterToPartyFormat = (OccupancyLetterToPartyFormat) specificNoticeService
                .find(OccupancyLetterToPartyFormatImpl.class, specificNoticeService.getCityDetails());
        return getFileAsResponseEntity(ocLetterToPartyFormat.generateNotice(ocLetterToParty), "oclettertoparty");
    }

    @GetMapping("/print/lpreply/{applicationNumber}/{lpNumber}")
    @ResponseBody
    public ResponseEntity<InputStreamResource> generateLettertoPartyReply(@PathVariable final String applicationNumber,
            @PathVariable final String lpNumber, final HttpServletRequest request) {
        final OCLetterToParty ocLetterToParty = ocLetterToPartyService.findByOcApplicationNoAndInspectionNo(applicationNumber,
                lpNumber);
        OccupancyLetterToPartyFormat ocLetterToPartyFormat = (OccupancyLetterToPartyFormat) specificNoticeService
                .find(OccupancyLetterToPartyReplyFormatImpl.class, specificNoticeService.getCityDetails());
        return getFileAsResponseEntity(ocLetterToPartyFormat.generateNotice(ocLetterToParty), "oclettertopartyreply");
    }

    @GetMapping("/view-details/{type}/{applicationNumber}/{lpNumber}")
    public String viewChecklist(@PathVariable final String applicationNumber, @PathVariable final String lpNumber,
            @PathVariable final String type, final Model model) {
        prepareLetterToParty(applicationNumber, lpNumber, model);
        return LETTER_TO_PARTY_VIEW;
    }

    @GetMapping(value = "/capture-sent-date/{applicationNumber}/{lpNumber}")
    public String captureSentDate(@PathVariable final String applicationNumber, @PathVariable final String lpNumber,
            final Model model) {
        prepareLetterToParty(applicationNumber, lpNumber, model);
        return LP_CAPTURE_SENT_DATE;
    }

    @GetMapping("/reply/{applicationNumber}/{lpNumber}")
    public String showLetterToPartyReply(@PathVariable final String applicationNumber, @PathVariable final String lpNumber,
            final Model model) {
        prepareLetterToParty(applicationNumber, lpNumber, model);
        return LP_REPLY;
    }

    @PostMapping("/reply/{applicationNumber}/{lpNumber}")
    public String createLetterToPartyReply(@ModelAttribute("ocLetterToParty") final OCLetterToParty ocLetterToParty,
            @PathVariable final String applicationNumber,
            @PathVariable final String lpNumber, final RedirectAttributes redirectAttributes) {
        processAndStoreLetterToPartyDocuments(ocLetterToParty);
        OCLetterToParty ocLetterToPartyRes = ocLetterToPartyService.save(ocLetterToParty,
                ocLetterToParty.getOc().getState().getOwnerPosition().getId());
        bpaUtils.updatePortalUserinbox(ocLetterToPartyRes.getOc(), null);
        redirectAttributes.addFlashAttribute(MESSAGE,
                messageSource.getMessage(MSG_LP_REPLY_SUCCESS, null, null));
        return REDIRECT_LETTER_TO_PARTY_RESULT + applicationNumber + "/" + lpNumber;
    }

    private ResponseEntity<InputStreamResource> getFileAsResponseEntity(final ReportOutput reportOutput,
            final String prefixFileName) {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_PDF)
                .cacheControl(CacheControl.noCache())
                .contentLength(reportOutput.getReportOutputData().length)
                .header(CONTENT_DISPOSITION, String.format(INLINE_FILENAME, prefixFileName + PDFEXTN))
                .body(new InputStreamResource(reportOutput.asInputStream()));
    }
}