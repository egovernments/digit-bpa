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

import org.egov.bpa.master.entity.CheckListDetail;
import org.egov.bpa.master.entity.LpReason;
import org.egov.bpa.master.service.LpReasonService;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.LettertoParty;
import org.egov.bpa.transaction.entity.LettertoPartyDocument;
import org.egov.bpa.transaction.service.LettertoPartyDocumentService;
import org.egov.bpa.transaction.service.LettertoPartyService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.bpa.web.controller.transaction.BpaGenericApplicationController;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.filestore.service.FileStoreService;
import org.egov.infra.workflow.entity.StateHistory;
import org.egov.pims.commons.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping(value = "/lettertoparty")
public class LetterToPartyController extends BpaGenericApplicationController {
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
    private static final String LETTERTO_PARTY = "lettertoParty";
    private static final String BPA_APPLICATION = "bpaApplication";
    private static final String MESSAGE = "message";

    @Autowired
    private LpReasonService lpReasonService;
    @Autowired
    private LettertoPartyService lettertoPartyService;
    @Autowired
    @Qualifier("fileStoreService")
    protected FileStoreService fileStoreService;
    @Autowired
    protected ResourceBundleMessageSource messageSource;
    @Autowired
    private LettertoPartyDocumentService lettertoPartyDocumentService;

    @ModelAttribute("lpReasonList")
    public List<LpReason> getLpReasonList() {
        return lpReasonService.findAll();
    }

    public List<CheckListDetail> getCheckListDetailList(final Long serviceTypeId) {
        return checkListDetailService.findActiveCheckListByServiceType(serviceTypeId, BpaConstants.LP_CHECKLIST);
    }

    @RequestMapping(value = "/create/{applicationNumber}", method = GET)
    public String createLetterToParty(@ModelAttribute final LettertoParty lettertoParty,
            @PathVariable final String applicationNumber, final Model model, final HttpServletRequest request) {
        BpaApplication application = applicationBpaService.findByApplicationNumber(applicationNumber);
        Position ownerPosition = application.getCurrentState().getOwnerPosition();
        if (validateLoginUserAndOwnerIsSame(model, securityUtils.getCurrentUser(), ownerPosition))
            return COMMON_ERROR;
        prepareData(lettertoParty, application, model);
        return LETTERTOPARTY_CREATE;
    }

    private void prepareData(final LettertoParty lettertoParty, final BpaApplication bpaApplication, final Model model) {
        model.addAttribute("mode", "new");
        model.addAttribute(BPA_APPLICATION, bpaApplication);
        model.addAttribute(CHECK_LIST_DETAIL_LIST, getCheckListDetailList(bpaApplication.getServiceType().getId()));
        lettertoParty.setApplication(bpaApplication);
    }

    public void validateCreateLetterToParty(LettertoParty lettertoParty, BindingResult errors) {
        if (lettertoParty.getLpReason() == null)
            errors.rejectValue("lpReason", "lbl.lp.reason.required");
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createLetterToParty(@ModelAttribute final LettertoParty lettertoParty,
            final BindingResult resultBinder, final Model model, final HttpServletRequest request,
            final BindingResult errors, final RedirectAttributes redirectAttributes) {
        if(lettertoParty.getApplication().getStatus().getCode().equals(BpaConstants.CREATEDLETTERTOPARTY)) {
            model.addAttribute(MESSAGE,messageSource.getMessage("msg.lp.already.created", null, LocaleContextHolder.getLocale()));
            return COMMON_ERROR;
        }
        Position ownerPosition = lettertoParty.getApplication().getCurrentState().getOwnerPosition();
        if (validateLoginUserAndOwnerIsSame(model, securityUtils.getCurrentUser(), ownerPosition))
            return COMMON_ERROR;
        validateCreateLetterToParty(lettertoParty, errors);
        if (errors.hasErrors()) {
            prepareData(lettertoParty, lettertoParty.getApplication(), model);
            return LETTERTOPARTY_CREATE;
        }
        processAndStoreLetterToPartyDocuments(lettertoParty);
        lettertoParty.setCurrentApplnStatus(lettertoParty.getApplication().getStatus());
        lettertoParty.setCurrentStateValueOfLP(getStateHistoryObjByDesc(lettertoParty).getValue());
        lettertoParty.setStateForOwnerPosition(lettertoParty.getApplication().getState().getValue());
        lettertoParty.setPendingAction(getStateHistoryObjByDesc(lettertoParty).getNextAction());
        List<LettertoParty> existingLettertoParties = lettertoPartyService
                .findByBpaApplicationOrderByIdDesc(lettertoParty.getApplication());
        if (!existingLettertoParties.isEmpty()) {
            LettertoParty existingLpParty = existingLettertoParties.get(0);
            if (existingLpParty.getCreatedBy().equals(securityUtils.getCurrentUser())
                    && existingLpParty.getCurrentApplnStatus().equals(lettertoParty.getCurrentApplnStatus())) {
                lettertoParty.setCurrentStateValueOfLP(existingLpParty.getCurrentStateValueOfLP());
                lettertoParty.setStateForOwnerPosition(existingLpParty.getStateForOwnerPosition());
                lettertoParty.setPendingAction(existingLpParty.getPendingAction());
            }
        }
        Position pos = bpaWorkFlowService.getApproverPositionOfElectionWardByCurrentState(lettertoParty.getApplication(), "LP Initiated");
        lettertoPartyService.save(lettertoParty, pos.getId());
        User user = workflowHistoryService.getUserPositionByPassingPosition(pos.getId());
        String message = messageSource.getMessage(MSG_LP_FORWARD_CREATE, new String[] {
                user != null ? user.getUsername().concat("~")
                        .concat(getApproverDesigName(pos))
                        : "",
                lettertoParty.getLpNumber(), lettertoParty.getApplication().getApplicationNumber() },
                LocaleContextHolder.getLocale());
        redirectAttributes.addFlashAttribute(MESSAGE, message);
        return REDIRECT_LETTERTOPARTY_RESULT + lettertoParty.getId();
    }

    private String getApproverDesigName(Position pos) {
        return pos.getDeptDesig() != null && pos.getDeptDesig().getDesignation() != null
                ? pos.getDeptDesig().getDesignation().getName() : "";
    }

    private StateHistory<Position> getStateHistoryObjByDesc(final LettertoParty lettertoParty) {
        return lettertoParty.getApplication().getStateHistory().stream()
                .sorted(Comparator.comparing(StateHistory<Position>::getId).reversed()).collect(Collectors.toList()).get(0);
    }

    @RequestMapping(value = "/update/{applicationNumber}", method = RequestMethod.GET)
    public String editLetterToParty(@PathVariable final String applicationNumber, final Model model) {
        prepareLetterToParty(applicationNumber, model);
        return LETTERTOPARTY_UPDATE;
    }

    private void prepareLetterToParty(String applicationNumber, Model model) {
        final BpaApplication bpaApplication = applicationBpaService.findByApplicationNumber(applicationNumber);
        final List<LettertoParty> lettertoPartyList = lettertoPartyService
                .findByBpaApplicationOrderByIdDesc(bpaApplication);
        LettertoParty lettertoParty = null;
        if (!lettertoPartyList.isEmpty())
            lettertoParty = lettertoPartyList.get(0);
        if (lettertoParty != null) {
            model.addAttribute(LETTERTO_PARTY, lettertoParty);
            model.addAttribute(LETTERTOPARTYDOC_LIST, lettertoParty.getLettertoPartyDocument());
        }
        model.addAttribute(CHECK_LIST_DETAIL_LIST, getCheckListDetailList(bpaApplication.getServiceType().getId()));
        model.addAttribute(BPA_APPLICATION, bpaApplication);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateLettertoparty(@ModelAttribute final LettertoParty lettertoparty, final Model model,
                                      final HttpServletRequest request, final BindingResult errors, final RedirectAttributes redirectAttributes) {
        processAndStoreLetterToPartyDocuments(lettertoparty);
        lettertoPartyService.save(lettertoparty, lettertoparty.getApplication().getState().getOwnerPosition().getId());
        redirectAttributes.addFlashAttribute(MESSAGE,
                messageSource.getMessage(MSG_LETTERTOPARTY_UPDATE_SUCCESS, null, null));
        return REDIRECT_LETTERTOPARTY_RESULT + lettertoparty.getId();
    }

    @RequestMapping(value = "/result/{id}", method = RequestMethod.GET)
    public String resultLettertoParty(@PathVariable final Long id, final Model model) {
        model.addAttribute(LETTERTO_PARTY, lettertoPartyService.findById(id));
        LettertoParty lettertoParty = lettertoPartyService.findById(id);
        model.addAttribute(LETTERTOPARTYDOC_LIST, lettertoParty.getLettertoPartyDocument());
        model.addAttribute(LETTERTOPARTYLIST,
                lettertoPartyService.findByBpaApplicationOrderByIdDesc(lettertoParty.getApplication()));
        return LETTERTOPARTY_RESULT;
    }


    protected void processAndStoreLetterToPartyDocuments(final LettertoParty lettertoParty) {
        if (!lettertoParty.getLettertoPartyDocument().isEmpty())
            for (final LettertoPartyDocument lettertoPartyDocument : lettertoParty.getLettertoPartyDocument()) {
                lettertoPartyDocument.setChecklistDetail(
                        checkListDetailService.load(lettertoPartyDocument.getChecklistDetail().getId()));
                lettertoPartyDocument.setLettertoParty(lettertoParty);
                if(lettertoPartyDocument.getFiles() != null && lettertoPartyDocument.getFiles().length > 0) {
                    lettertoPartyDocument.setSupportDocs(applicationBpaService.addToFileStore(lettertoPartyDocument.getFiles()));
                    lettertoPartyDocument.setIssubmitted(true);
                }
            }
    }

    @RequestMapping(value = "/lettertopartyprint/{type}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<byte[]> generateLettertoParty(@PathVariable final String type,
            final HttpServletRequest request, final HttpSession session) {
        final LettertoParty lettertoParty = lettertoPartyService.findById(new Long(request.getParameter("pathVar")));
        return lettertoPartyService.generateReport(lettertoParty, type, request);
    }

    @RequestMapping(value = "/viewdetails/{type}/{id}", method = RequestMethod.GET)
    public String viewchecklist(@PathVariable final Long id, @PathVariable final String type, final Model model) {
        LettertoParty lettertoParty = lettertoPartyService.findById(id);
        model.addAttribute(LETTERTOPARTYDOC_LIST, lettertoPartyDocumentService
                .findByIsrequestedTrueAndLettertoPartyOrderByIdAsc(lettertoPartyService.findById(id)));
        model.addAttribute(LETTERTO_PARTY, lettertoParty);
        return LETTERTOPARTY_VIEW;
    }

    @RequestMapping(value = "/capturesentdate/{id}", method = RequestMethod.GET)
    public String capturesentdate(@PathVariable final Long id, final Model model) {
        LettertoParty lettertoParty = lettertoPartyService.findById(id);
        model.addAttribute(LETTERTO_PARTY, lettertoParty);
        model.addAttribute(LETTERTOPARTYDOC_LIST, lettertoParty.getLettertoPartyDocument());
        model.addAttribute(BPA_APPLICATION, lettertoParty.getApplication());
        return LETTERTOPARTY_CAPTURESENTDATE;
    }

    @RequestMapping(value = "/lettertopartyreply/{id}", method = RequestMethod.GET)
    public String createLettertoPartyReply(@PathVariable final Long id, final Model model) {
        LettertoParty lettertoParty = lettertoPartyService.findById(id);
        model.addAttribute(LETTERTO_PARTY, lettertoParty);
        model.addAttribute(LETTERTOPARTYDOC_LIST, lettertoParty.getLettertoPartyDocument());
        model.addAttribute(BPA_APPLICATION, lettertoParty.getApplication());
        model.addAttribute(CHECK_LIST_DETAIL_LIST,
                getCheckListDetailList(lettertoParty.getApplication().getServiceType().getId()));
        return LETTERTOPARTY_LPREPLY;
    }

    @RequestMapping(value = "/lettertopartyreply", method = RequestMethod.POST)
    public String createLettertoPartyReply(@ModelAttribute final LettertoParty lettertoparty, final Model model,
                                           final HttpServletRequest request, final BindingResult errors, final RedirectAttributes redirectAttributes) {
        processAndStoreLetterToPartyDocuments(lettertoparty);
        LettertoParty lettertopartyRes = lettertoPartyService.save(lettertoparty, lettertoparty.getApplication().getState().getOwnerPosition().getId());
        bpaUtils.updatePortalUserinbox(lettertopartyRes.getApplication(), null);
        redirectAttributes.addFlashAttribute(MESSAGE,
                messageSource.getMessage(MSG_LETTERTOPARTY_REPLY_SUCCESS, null, null));
        return REDIRECT_LETTERTOPARTY_RESULT + lettertoparty.getId();
    }
}