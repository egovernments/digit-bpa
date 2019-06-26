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

import org.egov.bpa.master.entity.ChecklistServiceTypeMapping;
import org.egov.bpa.master.entity.LpReason;
import org.egov.bpa.master.service.ChecklistServicetypeMappingService;
import org.egov.bpa.master.service.LpReasonService;
import org.egov.bpa.transaction.entity.InspectionLetterToParty;
import org.egov.bpa.transaction.entity.PermitInspectionApplication;
import org.egov.bpa.transaction.entity.PermitLetterToParty;
import org.egov.bpa.transaction.entity.common.LetterToPartyCommon;
import org.egov.bpa.transaction.entity.common.LetterToPartyDocumentCommon;
import org.egov.bpa.transaction.notice.LetterToPartyFormat;
import org.egov.bpa.transaction.notice.impl.LetterToPartyCreateFormatImpl;
import org.egov.bpa.transaction.notice.impl.LetterToPartyReplyFormatImpl;
import org.egov.bpa.transaction.service.InspectionApplicationService;
import org.egov.bpa.transaction.service.InspectionLetterToPartyService;
import org.egov.bpa.transaction.service.LettertoPartyDocumentService;
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
@RequestMapping(value = "/inspection/lettertoparty")
public class InspectionLetterToPartyController extends BpaGenericApplicationController {

	public static final String COMMON_ERROR = "common-error";
    private static final String PDFEXTN = ".pdf";
    private static final String INLINE_FILENAME = "inline;filename=";
    private static final String CONTENT_DISPOSITION = "content-disposition";
    private static final String MSG_LETTERTOPARTY_REPLY_SUCCESS = "msg.lettertoparty.reply.success";
    private static final String MSG_LP_FORWARD_CREATE = "msg.lp.forward.create";
    private static final String MSG_LETTERTOPARTY_UPDATE_SUCCESS = "msg.lettertoparty.update.success";
    private static final String LETTERTOPARTY_LPREPLY = "ins-lettertoparty-lpreply";
    private static final String LETTERTOPARTY_CAPTURESENTDATE = "ins-lettertoparty-capturesentdate";
    private static final String LETTERTOPARTY_VIEW = "ins-lettertoparty-view";
    private static final String LETTERTOPARTYLIST = "lettertopartylist";
    private static final String LETTERTOPARTY_RESULT = "ins-lettertoparty-result";
    private static final String LETTERTOPARTY_UPDATE = "ins-lettertoparty-update";
    private static final String LETTERTOPARTY_CREATE = "ins-lettertoparty-create";
    private static final String REDIRECT_LETTERTOPARTY_RESULT = "redirect:/inspection/lettertoparty/result/";
    private static final String CHECK_LIST_DETAIL_LIST = "checkListDetailList";
    private static final String LETTERTOPARTYDOC_LIST = "lettertopartydocList";
    private static final String LETTERTO_PARTY = "inspectionLetterToParty";
    private static final String MESSAGE = "message";

    @Autowired
    private LpReasonService lpReasonService;
    @Autowired
    private LettertoPartyDocumentService lettertoPartyDocumentService;

    @Autowired
    private ChecklistServicetypeMappingService checklistServiceTypeService;
    @Autowired
    private InspectionApplicationService inspectionAppService;
    @Autowired
    private InspectionLetterToPartyService insLettertoPartyService;
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
    public String createLetterToParty(@ModelAttribute final InspectionLetterToParty lettertoParty,
            @PathVariable final String applicationNumber, final Model model, final HttpServletRequest request) {
        final PermitInspectionApplication permitInspection = inspectionAppService.findByInspectionApplicationNumber(applicationNumber);
        Position ownerPosition = permitInspection.getInspectionApplication().getCurrentState().getOwnerPosition();
        if (validateLoginUserAndOwnerIsSame(model, securityUtils.getCurrentUser(), ownerPosition))
            return COMMON_ERROR;
        prepareInspectionData(lettertoParty, permitInspection, model);
        return LETTERTOPARTY_CREATE;
    }
    
    private void prepareInspectionData(final InspectionLetterToParty lettertoParty, final PermitInspectionApplication permitInspection, final Model model) {
        model.addAttribute("mode", "new");
        model.addAttribute("inspectionApplication", permitInspection.getInspectionApplication());
        model.addAttribute(CHECK_LIST_DETAIL_LIST, getCheckListDetailList(permitInspection.getApplication().getServiceType().getId()));
        lettertoParty.setInspectionApplication(permitInspection.getInspectionApplication());
    }
    
    @PostMapping("/create/{applicationNumber}")
    public String createLetterToParty(@ModelAttribute("inspectionLetterToParty") final InspectionLetterToParty inspectionLetterToParty,
    		@PathVariable final String applicationNumber,final BindingResult resultBinder, final Model model, final HttpServletRequest request,
            final BindingResult errors, final RedirectAttributes redirectAttributes) {
        final PermitInspectionApplication permitInspection = inspectionAppService.findByInspectionApplicationNumber(applicationNumber);
        inspectionLetterToParty.setInspectionApplication(permitInspection.getInspectionApplication());
        if (inspectionLetterToParty.getInspectionApplication().getStatus().getCode().equals(BpaConstants.CREATEDLETTERTOPARTY)) {
            model.addAttribute(MESSAGE,
                    messageSource.getMessage("msg.lp.already.created", null, LocaleContextHolder.getLocale()));
            return COMMON_ERROR;
        }
        Position ownerPosition = inspectionLetterToParty.getInspectionApplication().getCurrentState().getOwnerPosition();
        if (validateLoginUserAndOwnerIsSame(model, securityUtils.getCurrentUser(), ownerPosition))
            return COMMON_ERROR;
        if (inspectionLetterToParty.getLetterToParty().getLpReason() == null)
            errors.rejectValue("lpReason", "lbl.lp.reason.required");
        if (errors.hasErrors()) {
        	prepareInspectionData(inspectionLetterToParty, permitInspection, model);
            return "ins-lettertoparty-create";
        }
        processAndStoreInsLetterToPartyDocuments(inspectionLetterToParty);
        LetterToPartyCommon ltp = inspectionLetterToParty.getLetterToParty();
        ltp.setCurrentApplnStatus(inspectionLetterToParty.getInspectionApplication().getStatus());
        
        String value ="";
        String nextAction="";
        if(!inspectionLetterToParty.getInspectionApplication().getStateHistory().isEmpty()){
        	value = getInsStateHistoryObjByDesc(inspectionLetterToParty).getValue();
        	nextAction = getInsStateHistoryObjByDesc(inspectionLetterToParty).getNextAction();
        } else {
        	value = inspectionLetterToParty.getInspectionApplication().getState().getValue();
        	nextAction = inspectionLetterToParty.getInspectionApplication().getState().getNextAction();
        }
        ltp.setCurrentStateValueOfLP(value);
        ltp.setStateForOwnerPosition(inspectionLetterToParty.getInspectionApplication().getState().getValue());
        ltp.setPendingAction(nextAction);
        List<InspectionLetterToParty> existingLettertoParties = insLettertoPartyService
                .findByInspectionApplicationOrderByIdDesc(inspectionLetterToParty.getInspectionApplication());
        if (!existingLettertoParties.isEmpty()) {
            LetterToPartyCommon existingLpParty = existingLettertoParties.get(0).getLetterToParty();
            if (existingLpParty.getCreatedBy().equals(securityUtils.getCurrentUser())
                    && existingLpParty.getCurrentApplnStatus().equals(ltp.getCurrentApplnStatus())) {
                ltp.setCurrentStateValueOfLP(existingLpParty.getCurrentStateValueOfLP());
                ltp.setStateForOwnerPosition(existingLpParty.getStateForOwnerPosition());
                ltp.setPendingAction(existingLpParty.getPendingAction());
            }
        }
        Position pos = bpaWorkFlowService.getApproverPositionOfElectionWardByCurrentState(permitInspection,
                "LP Initiated");
        insLettertoPartyService.save(inspectionLetterToParty, permitInspection, pos.getId());
        User user = workflowHistoryService.getUserPositionByPassingPosition(pos.getId());
        String message = messageSource.getMessage(MSG_LP_FORWARD_CREATE, new String[] {
                user != null ? user.getUsername().concat("~")
                        .concat(getApproverDesigName(pos))
                        : "",
                ltp.getLpNumber(), inspectionLetterToParty.getInspectionApplication().getApplicationNumber() },
                LocaleContextHolder.getLocale());
        redirectAttributes.addFlashAttribute(MESSAGE, message);
        return REDIRECT_LETTERTOPARTY_RESULT + inspectionLetterToParty.getId();
    }
    
    protected void processAndStoreInsLetterToPartyDocuments(final InspectionLetterToParty lettertoParty) {
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
    		
    private StateHistory<Position> getInsStateHistoryObjByDesc(final InspectionLetterToParty lettertoParty) {
        return lettertoParty.getInspectionApplication().getStateHistory().stream()
                .sorted(Comparator.comparing(StateHistory<Position>::getId).reversed()).collect(Collectors.toList()).get(0);
    }
    

    private String getApproverDesigName(Position pos) {
        return pos.getDeptDesig() != null && pos.getDeptDesig().getDesignation() != null
                ? pos.getDeptDesig().getDesignation().getName()
                : "";
    }


    @GetMapping("/update/{applicationNumber}")
    public String editLetterToParty(@PathVariable final String applicationNumber, final Model model) {
        prepareLetterToParty(applicationNumber, model);
        return LETTERTOPARTY_UPDATE;
    }

    private void prepareLetterToParty(String applicationNumber, Model model) {
        final PermitInspectionApplication permitInspection = inspectionAppService.findByInspectionApplicationNumber(applicationNumber);
        final List<InspectionLetterToParty> lettertoPartyList = insLettertoPartyService
                .findByInspectionApplicationOrderByIdDesc(permitInspection.getInspectionApplication());
        InspectionLetterToParty lettertoParty = null;
        if (!lettertoPartyList.isEmpty())
            lettertoParty = lettertoPartyList.get(0);
        if (lettertoParty != null) {
            model.addAttribute(LETTERTO_PARTY, lettertoParty);
            model.addAttribute(LETTERTOPARTYDOC_LIST, lettertoParty.getLetterToParty().getLetterToPartyDocuments());
        }
        model.addAttribute(CHECK_LIST_DETAIL_LIST, getCheckListDetailList(permitInspection.getApplication().getServiceType().getId()));
        model.addAttribute("inspectionApplication", permitInspection.getInspectionApplication());
    }

    @PostMapping("/update/{applicationNumber}")
    public String updateLettertoparty(@ModelAttribute("inspectionLetterToParty") final InspectionLetterToParty lettertoparty, final Model model,
    		@PathVariable final String applicationNumber, final HttpServletRequest request, final BindingResult errors, final RedirectAttributes redirectAttributes) {
        final PermitInspectionApplication permitInspection = inspectionAppService.findByInspectionApplicationNumber(applicationNumber);

    	processAndStoreLetterToPartyDocuments(lettertoparty);
        insLettertoPartyService.save(lettertoparty, permitInspection, lettertoparty.getInspectionApplication().getState().getOwnerPosition().getId());
        redirectAttributes.addFlashAttribute(MESSAGE,
                messageSource.getMessage(MSG_LETTERTOPARTY_UPDATE_SUCCESS, null, null));
        return REDIRECT_LETTERTOPARTY_RESULT + lettertoparty.getId();
    }

    @GetMapping("/result/{id}")
    public String resultLettertoParty(@PathVariable final Long id, final Model model) {
        InspectionLetterToParty lettertoParty = insLettertoPartyService.findById(id);
        model.addAttribute(LETTERTO_PARTY, lettertoParty);

        model.addAttribute(LETTERTOPARTYDOC_LIST, lettertoParty.getLetterToParty().getLetterToPartyDocuments());
        model.addAttribute(LETTERTOPARTYLIST,
        		insLettertoPartyService.findByInspectionApplicationOrderByIdDesc(lettertoParty.getInspectionApplication()));
        return LETTERTOPARTY_RESULT;
    }

    protected void processAndStoreLetterToPartyDocuments(final InspectionLetterToParty lettertoParty) {
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

    @GetMapping("/viewdetails/{type}/{id}")
    public String viewchecklist(@PathVariable final Long id, @PathVariable final String type, final Model model) {
        InspectionLetterToParty lettertoParty = insLettertoPartyService.findById(id);
        model.addAttribute(LETTERTOPARTYDOC_LIST, lettertoPartyDocumentService
                .findByIsrequestedTrueAndLettertoPartyOrderByIdAsc(lettertoParty));
        model.addAttribute(LETTERTO_PARTY, lettertoParty);
        return LETTERTOPARTY_VIEW;
    }

    @GetMapping("/capturesentdate/{id}")
    public String capturesentdate(@PathVariable final Long id, final Model model) {
        InspectionLetterToParty lettertoParty = insLettertoPartyService.findById(id);
        model.addAttribute(LETTERTO_PARTY, lettertoParty);
        model.addAttribute(LETTERTOPARTYDOC_LIST, lettertoParty.getLetterToParty().getLetterToPartyDocuments());
        model.addAttribute("inspectionApplication", lettertoParty.getInspectionApplication());
        return LETTERTOPARTY_CAPTURESENTDATE;
    }

    @GetMapping("/lettertopartyreply/{id}")
    public String createLettertoPartyReply(@PathVariable final Long id, final Model model) {

        InspectionLetterToParty lettertoParty = insLettertoPartyService.findById(id);
        final PermitInspectionApplication permitInspection = inspectionAppService.findByInspectionApplicationNumber(lettertoParty.getInspectionApplication().getApplicationNumber());

        model.addAttribute(LETTERTO_PARTY, lettertoParty);
        model.addAttribute(LETTERTOPARTYDOC_LIST, lettertoParty.getLetterToParty().getLetterToPartyDocuments());
        model.addAttribute("inspectionApplication", lettertoParty.getInspectionApplication());
        model.addAttribute(CHECK_LIST_DETAIL_LIST,
                getCheckListDetailList(permitInspection.getApplication().getServiceType().getId()));
        return LETTERTOPARTY_LPREPLY;
    }

    @PostMapping("/lettertopartyreply")
    public String createLettertoPartyReply(@ModelAttribute final InspectionLetterToParty lettertoparty, final Model model,
            final HttpServletRequest request, final BindingResult errors, final RedirectAttributes redirectAttributes) {
        final PermitInspectionApplication permitInspection = inspectionAppService.findByInspectionApplicationNumber(lettertoparty.getInspectionApplication().getApplicationNumber());

    	processAndStoreLetterToPartyDocuments(lettertoparty);
        InspectionLetterToParty lettertopartyRes = insLettertoPartyService.save(lettertoparty,permitInspection,
        		permitInspection.getApplication().getState().getOwnerPosition().getId());
        permitInspection.setInspectionApplication(lettertopartyRes.getInspectionApplication());
        bpaUtils.updatePortalUserinbox(permitInspection, null);
        redirectAttributes.addFlashAttribute(MESSAGE,
                messageSource.getMessage(MSG_LETTERTOPARTY_REPLY_SUCCESS, null, null));
        return REDIRECT_LETTERTOPARTY_RESULT + lettertoparty.getId();
    }  
    
    @GetMapping("/lettertopartyprint/lp")
    @ResponseBody
    public ResponseEntity<InputStreamResource> generateLettertoPartyCreate(final HttpServletRequest request,
            final HttpSession session) {
        final InspectionLetterToParty lettertoParty = insLettertoPartyService.findById(new Long(request.getParameter("pathVar")));
        LetterToPartyCreateFormatImpl letterToPartyFormat = (LetterToPartyCreateFormatImpl) specificNoticeService
                .find(LetterToPartyCreateFormatImpl.class, specificNoticeService.getCityDetails());
        return getFileAsResponseEntity(lettertoParty.getLetterToParty().getLpNumber(),
                letterToPartyFormat.generateInsNotice(lettertoParty),
                "inslettertoparty");
    }

    @GetMapping("/lettertopartyprint/lpreply")
    @ResponseBody
    public ResponseEntity<InputStreamResource> generateLettertoPartyReply(final HttpServletRequest request,
            final HttpSession session) {
        final InspectionLetterToParty lettertoParty = insLettertoPartyService.findById(new Long(request.getParameter("pathVar")));
        LetterToPartyReplyFormatImpl letterToPartyFormat = (LetterToPartyReplyFormatImpl) specificNoticeService
                .find(LetterToPartyReplyFormatImpl.class, specificNoticeService.getCityDetails());
        return getFileAsResponseEntity(lettertoParty.getLetterToParty().getLpNumber(),
                letterToPartyFormat.generateInsNotice(lettertoParty),
                "inslettertopartyreply");
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
    
    
    
}