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
package org.egov.bpa.web.controller.lettertoparty;

import static org.egov.infra.utils.StringUtils.append;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.egov.bpa.master.entity.ChecklistServiceTypeMapping;
import org.egov.bpa.master.entity.LpReason;
import org.egov.bpa.master.service.LpReasonService;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.PermitLetterToParty;
import org.egov.bpa.transaction.entity.common.LetterToPartyCommon;
import org.egov.bpa.transaction.entity.common.LetterToPartyDocumentCommon;
import org.egov.bpa.transaction.notice.LetterToPartyFormat;
import org.egov.bpa.transaction.notice.impl.LetterToPartyCreateFormatImpl;
import org.egov.bpa.transaction.notice.impl.LetterToPartyReplyFormatImpl;
import org.egov.bpa.transaction.service.LettertoPartyDocumentService;
import org.egov.bpa.transaction.service.LettertoPartyService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.bpa.web.controller.transaction.BpaGenericApplicationController;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.custom.CustomImplProvider;
import org.egov.infra.reporting.engine.ReportOutput;
import org.egov.infra.workflow.entity.StateHistory;
import org.egov.pims.commons.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
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
@RequestMapping(value = "/lettertoparty")
public class LetterToPartyController extends BpaGenericApplicationController {

    private static final String PDFEXTN = ".pdf";
    private static final String INLINE_FILENAME = "inline;filename=";
    private static final String CONTENT_DISPOSITION = "content-disposition";
    public static final String COMMON_ERROR = "common-error";
    private static final String MSG_LETTERTOPARTY_REPLY_SUCCESS = "msg.lettertoparty.reply.success";
    private static final String MSG_LP_FORWARD_CREATE = "msg.lp.forward.create";
    private static final String MSG_LETTERTOPARTY_UPDATE_SUCCESS = "msg.lettertoparty.update.success";
    private static final String LETTERTOPARTY_LPREPLY = "lettertoparty-lpreply";
    private static final String LETTERTOPARTY_CAPTURESENTDATE = "lettertoparty-capturesentdate";
    private static final String LETTERTOPARTY_VIEW = "lettertoparty-view";
    private static final String LETTERTOPARTYLIST = "lettertopartylist";
    private static final String LETTERTOPARTY_RESULT = "lettertoparty-result";
    private static final String LETTERTOPARTY_UPDATE = "lettertoparty-update";
    private static final String LETTERTOPARTY_CREATE = "lettertoparty-create";
    private static final String REDIRECT_LETTERTOPARTY_RESULT = "redirect:/lettertoparty/result/";
    private static final String CHECK_LIST_DETAIL_LIST = "checkListDetailList";
    private static final String LETTERTOPARTYDOC_LIST = "lettertopartydocList";
    private static final String LETTERTO_PARTY = "permitLetterToParty";
    private static final String BPA_APPLICATION = "bpaApplication";
    private static final String MESSAGE = "message";

    @Autowired
    private LpReasonService lpReasonService;
    @Autowired
    private LettertoPartyService lettertoPartyService;
    @Autowired
    private LettertoPartyDocumentService lettertoPartyDocumentService;
    @Autowired
    private CustomImplProvider specificNoticeService;

    @ModelAttribute("lpReasonList")
    public List<LpReason> getLpReasonList() {
        return lpReasonService.findAll();
    }

    public List<ChecklistServiceTypeMapping> getCheckListDetailList(final Long serviceTypeId) {
        return checklistServiceTypeService.findByActiveByServiceTypeAndChecklist(serviceTypeId, BpaConstants.LP_CHECKLIST);
    }

    @GetMapping("/create/{applicationNumber}")
    public String createLetterToParty(@PathVariable final String applicationNumber,
            @Valid @ModelAttribute final PermitLetterToParty lettertoParty,
            final Model model, final HttpServletRequest request) {
        BpaApplication application = applicationBpaService.findByApplicationNumber(applicationNumber);
        Position ownerPosition = application.getCurrentState().getOwnerPosition();
        if (validateLoginUserAndOwnerIsSame(model, securityUtils.getCurrentUser(), ownerPosition))
            return COMMON_ERROR;
        prepareData(lettertoParty, application, model);
        return LETTERTOPARTY_CREATE;
    }

    private void prepareData(final PermitLetterToParty lettertoParty, final BpaApplication bpaApplication, final Model model) {
        model.addAttribute("mode", "new");
        model.addAttribute(BPA_APPLICATION, bpaApplication);
        model.addAttribute(CHECK_LIST_DETAIL_LIST, getCheckListDetailList(bpaApplication.getServiceType().getId()));
        lettertoParty.setApplication(bpaApplication);
    }

    public void validateCreateLetterToParty(PermitLetterToParty permitLTP, BindingResult errors) {
        if (permitLTP.getLetterToParty().getLpReason() == null)
            errors.rejectValue("lpReason", "lbl.lp.reason.required");
    }

    @PostMapping("/create")
    public String createLetterToParty(@Valid @ModelAttribute("permitLetterToParty") final PermitLetterToParty permitLTP,
            final BindingResult resultBinder, final Model model, final HttpServletRequest request,
            final BindingResult errors, final RedirectAttributes redirectAttributes) {

        validateCreateLetterToParty(permitLTP, errors);
        if (errors.hasErrors()) {
            prepareData(permitLTP, permitLTP.getApplication(), model);
            return LETTERTOPARTY_CREATE;
        }

        if (permitLTP.getApplication().getStatus().getCode().equals(BpaConstants.CREATEDLETTERTOPARTY)) {
            model.addAttribute(MESSAGE,
                    messageSource.getMessage("msg.lp.already.created", null, LocaleContextHolder.getLocale()));
            return COMMON_ERROR;
        }
        Position ownerPosition = permitLTP.getApplication().getCurrentState().getOwnerPosition();
        if (validateLoginUserAndOwnerIsSame(model, securityUtils.getCurrentUser(), ownerPosition))
            return COMMON_ERROR;
        processAndStoreLetterToPartyDocuments(permitLTP);
        LetterToPartyCommon ltp = permitLTP.getLetterToParty();
        ltp.setCurrentApplnStatus(permitLTP.getApplication().getStatus());

        String value = "";
        String nextAction = "";
        if (!permitLTP.getApplication().getStateHistory().isEmpty()) {
            value = getStateHistoryObjByDesc(permitLTP).getValue();
            nextAction = getStateHistoryObjByDesc(permitLTP).getNextAction();
        } else {
            value = permitLTP.getApplication().getState().getValue();
            nextAction = permitLTP.getApplication().getState().getNextAction();
        }
        ltp.setCurrentStateValueOfLP(value);
        ltp.setStateForOwnerPosition(permitLTP.getApplication().getState().getValue());
        ltp.setPendingAction(nextAction);
        List<PermitLetterToParty> existingLettertoParties = lettertoPartyService
                .findByBpaApplicationOrderByIdDesc(permitLTP.getApplication());
        if (!existingLettertoParties.isEmpty()) {
            LetterToPartyCommon existingLpParty = existingLettertoParties.get(0).getLetterToParty();
            if (existingLpParty.getCreatedBy().equals(securityUtils.getCurrentUser())
                    && existingLpParty.getCurrentApplnStatus().equals(ltp.getCurrentApplnStatus())) {
                ltp.setCurrentStateValueOfLP(existingLpParty.getCurrentStateValueOfLP());
                ltp.setStateForOwnerPosition(existingLpParty.getStateForOwnerPosition());
                ltp.setPendingAction(existingLpParty.getPendingAction());
            }
        }
        Position pos = bpaWorkFlowService.getApproverPositionOfElectionWardByCurrentState(permitLTP.getApplication(),
                "LP Initiated");
        lettertoPartyService.save(permitLTP, pos.getId());
        User user = workflowHistoryService.getUserPositionByPassingPosition(pos.getId());
        String message = messageSource.getMessage(MSG_LP_FORWARD_CREATE, new String[] {
                user != null ? user.getUsername().concat("~")
                        .concat(getApproverDesigName(pos))
                        : "",
                ltp.getLpNumber(), permitLTP.getApplication().getApplicationNumber() },
                LocaleContextHolder.getLocale());
        redirectAttributes.addFlashAttribute(MESSAGE, message);
        return REDIRECT_LETTERTOPARTY_RESULT + permitLTP.getId();
    }

    private String getApproverDesigName(Position pos) {
        return pos.getDeptDesig() != null && pos.getDeptDesig().getDesignation() != null
                ? pos.getDeptDesig().getDesignation().getName()
                : "";
    }

    private StateHistory<Position> getStateHistoryObjByDesc(final PermitLetterToParty lettertoParty) {
        return lettertoParty.getApplication().getStateHistory().stream()
                .sorted(Comparator.comparing(StateHistory<Position>::getId).reversed()).collect(Collectors.toList()).get(0);
    }

    @GetMapping("/update/{applicationNumber}")
    public String editLetterToParty(@PathVariable final String applicationNumber, final Model model) {
        prepareLetterToParty(applicationNumber, model);
        return LETTERTOPARTY_UPDATE;
    }

    private void prepareLetterToParty(String applicationNumber, Model model) {
        final BpaApplication bpaApplication = applicationBpaService.findByApplicationNumber(applicationNumber);
        final List<PermitLetterToParty> lettertoPartyList = lettertoPartyService
                .findByBpaApplicationOrderByIdDesc(bpaApplication);
        PermitLetterToParty lettertoParty = null;
        if (!lettertoPartyList.isEmpty())
            lettertoParty = lettertoPartyList.get(0);
        if (lettertoParty != null) {
            model.addAttribute(LETTERTO_PARTY, lettertoParty);
            model.addAttribute(LETTERTOPARTYDOC_LIST, lettertoParty.getLetterToParty().getLetterToPartyDocuments());
        }
        model.addAttribute(CHECK_LIST_DETAIL_LIST, getCheckListDetailList(bpaApplication.getServiceType().getId()));
        model.addAttribute(BPA_APPLICATION, bpaApplication);
    }

    @PostMapping("/update")
    public String updateLettertoparty(@Valid @ModelAttribute("permitLetterToParty") final PermitLetterToParty lettertoparty,
            final BindingResult errors,
            final Model model,
            final HttpServletRequest request, final RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            prepareLetterToParty(lettertoparty.getApplication().getApplicationNumber(), model);
            return LETTERTOPARTY_UPDATE;
        }
        processAndStoreLetterToPartyDocuments(lettertoparty);
        lettertoPartyService.save(lettertoparty, lettertoparty.getApplication().getState().getOwnerPosition().getId());
        bpaSmsAndEmailService.sendSMSAndEmailToApplicantForLettertoparty(lettertoparty.getApplication());
        redirectAttributes.addFlashAttribute(MESSAGE,
                messageSource.getMessage(MSG_LETTERTOPARTY_UPDATE_SUCCESS, null, null));
        return REDIRECT_LETTERTOPARTY_RESULT + lettertoparty.getId();
    }

    @GetMapping("/result/{id}")
    public String resultLettertoParty(@PathVariable final Long id, final Model model) {
        model.addAttribute(LETTERTO_PARTY, lettertoPartyService.findById(id));
        PermitLetterToParty lettertoParty = lettertoPartyService.findById(id);
        model.addAttribute(LETTERTOPARTYDOC_LIST, lettertoParty.getLetterToParty().getLetterToPartyDocuments());
        model.addAttribute(LETTERTOPARTYLIST,
                lettertoPartyService.findByBpaApplicationOrderByIdDesc(lettertoParty.getApplication()));
        return LETTERTOPARTY_RESULT;
    }

    protected void processAndStoreLetterToPartyDocuments(final PermitLetterToParty lettertoParty) {
        if (!lettertoParty.getLetterToParty().getLetterToPartyDocuments().isEmpty())
            for (final LetterToPartyDocumentCommon lettertoPartyDocument : lettertoParty.getLetterToParty()
                    .getLetterToPartyDocuments()) {
                lettertoPartyDocument.setServiceChecklist(
                        checklistServiceTypeService.load(lettertoPartyDocument.getServiceChecklist().getId()));
                lettertoPartyDocument.setLetterToParty(lettertoParty.getLetterToParty());
                if (lettertoPartyDocument.getFiles() != null && lettertoPartyDocument.getFiles().length > 0) {
                    lettertoPartyDocument.setSupportDocs(applicationBpaService.addToFileStore(lettertoPartyDocument.getFiles()));
                    lettertoPartyDocument.setIsSubmitted(true);
                }
            }
    }

    @GetMapping("/lettertopartyprint/lp")
    @ResponseBody
    public ResponseEntity<InputStreamResource> generateLettertoPartyCreate(final HttpServletRequest request,
            final HttpSession session) {
        final PermitLetterToParty lettertoParty = lettertoPartyService.findById(new Long(request.getParameter("pathVar")));
        LetterToPartyFormat letterToPartyFormat = (LetterToPartyFormat) specificNoticeService
                .find(LetterToPartyCreateFormatImpl.class, specificNoticeService.getCityDetails());
        return getFileAsResponseEntity(lettertoParty.getLetterToParty().getLpNumber(),
                letterToPartyFormat.generateNotice(lettertoParty),
                "lettertoparty");
    }

    @GetMapping("/lettertopartyprint/lpreply")
    @ResponseBody
    public ResponseEntity<InputStreamResource> generateLettertoPartyReply(final HttpServletRequest request,
            final HttpSession session) {
        final PermitLetterToParty lettertoParty = lettertoPartyService.findById(new Long(request.getParameter("pathVar")));
        LetterToPartyFormat letterToPartyFormat = (LetterToPartyFormat) specificNoticeService
                .find(LetterToPartyReplyFormatImpl.class, specificNoticeService.getCityDetails());
        return getFileAsResponseEntity(lettertoParty.getLetterToParty().getLpNumber(),
                letterToPartyFormat.generateNotice(lettertoParty),
                "lettertopartyreply");
    }

    private ResponseEntity<InputStreamResource> getFileAsResponseEntity(String lpNumber, ReportOutput reportOutput,
            String prefixFileName) {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_PDF)
                .cacheControl(CacheControl.noCache())
                .contentLength(reportOutput.getReportOutputData().length)
                .header(CONTENT_DISPOSITION, String.format(INLINE_FILENAME,
                        append(prefixFileName, lpNumber) + PDFEXTN))
                .body(new InputStreamResource(reportOutput.asInputStream()));
    }

    @GetMapping("/viewdetails/{type}/{id}")
    public String viewchecklist(@PathVariable final Long id, @PathVariable final String type, final Model model) {
        PermitLetterToParty lettertoParty = lettertoPartyService.findById(id);
        model.addAttribute(LETTERTOPARTYDOC_LIST, lettertoPartyDocumentService
                .findByIsrequestedTrueAndLettertoPartyOrderByIdAsc(lettertoPartyService.findById(id)));
        model.addAttribute(LETTERTO_PARTY, lettertoParty);
        return LETTERTOPARTY_VIEW;
    }

    @GetMapping("/capturesentdate/{id}")
    public String capturesentdate(@PathVariable final Long id, final Model model) {
        PermitLetterToParty lettertoParty = lettertoPartyService.findById(id);
        model.addAttribute(LETTERTO_PARTY, lettertoParty);
        model.addAttribute(LETTERTOPARTYDOC_LIST, lettertoParty.getLetterToParty().getLetterToPartyDocuments());
        model.addAttribute(BPA_APPLICATION, lettertoParty.getApplication());
        return LETTERTOPARTY_CAPTURESENTDATE;
    }

    @GetMapping("/lettertopartyreply/{id}")
    public String createLettertoPartyReply(@PathVariable final Long id, final Model model) {
        PermitLetterToParty lettertoParty = lettertoPartyService.findById(id);
        return getLPReplyForm(model, lettertoParty);

    }

    private String getLPReplyForm(final Model model, PermitLetterToParty lettertoParty) {
        model.addAttribute(LETTERTO_PARTY, lettertoParty);
        model.addAttribute(LETTERTOPARTYDOC_LIST, lettertoParty.getLetterToParty().getLetterToPartyDocuments());
        model.addAttribute(BPA_APPLICATION, lettertoParty.getApplication());
        model.addAttribute(CHECK_LIST_DETAIL_LIST,
                getCheckListDetailList(lettertoParty.getApplication().getServiceType().getId()));
        return LETTERTOPARTY_LPREPLY;
    }

    @PostMapping("/lettertopartyreply")
    public String createLettertoPartyReply(@Valid @ModelAttribute("permitLetterToParty") final PermitLetterToParty lettertoparty,
            final BindingResult errors,
            final Model model,
            final HttpServletRequest request, final RedirectAttributes redirectAttributes) {
        lettertoPartyService.validateDocs(lettertoparty, errors);
        if (errors.hasErrors())
            return getLPReplyForm(model, lettertoparty);
        processAndStoreLetterToPartyDocuments(lettertoparty);
        PermitLetterToParty lettertopartyRes = lettertoPartyService.save(lettertoparty,
                lettertoparty.getApplication().getState().getOwnerPosition().getId());
        bpaUtils.updatePortalUserinbox(lettertopartyRes.getApplication(), null);
        redirectAttributes.addFlashAttribute(MESSAGE,
                messageSource.getMessage(MSG_LETTERTOPARTY_REPLY_SUCCESS, null, null));
        return REDIRECT_LETTERTOPARTY_RESULT + lettertoparty.getId();
    }
}