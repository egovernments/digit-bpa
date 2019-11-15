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

package org.egov.bpa.web.controller.transaction;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_REJECTED;
import static org.egov.bpa.utils.BpaConstants.GENERATEREJECTNOTICE;
import static org.egov.bpa.utils.BpaConstants.OWNERSHIPSTATUS_MODULETYPE;
import static org.egov.bpa.utils.BpaConstants.WF_APPROVE_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_ASST_ENG_APPROVED;
import static org.egov.bpa.utils.BpaConstants.WF_REJECT_BUTTON;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.egov.bpa.master.entity.ChecklistServiceTypeMapping;
import org.egov.bpa.transaction.entity.OwnershipTransfer;
import org.egov.bpa.transaction.entity.OwnershipTransferConditions;
import org.egov.bpa.transaction.entity.WorkflowBean;
import org.egov.bpa.transaction.entity.common.NoticeCondition;
import org.egov.bpa.transaction.entity.dto.SearchBpaApplicationForm;
import org.egov.bpa.transaction.entity.enums.ConditionType;
import org.egov.bpa.transaction.notice.impl.OwnershipTransferNoticeService;
import org.egov.bpa.transaction.service.OwnershipTransferConditionsService;
import org.egov.bpa.transaction.service.OwnershipTransferService;
import org.egov.bpa.transaction.service.messaging.ownership.OwnershipTransferSmsAndEmailService;
import org.egov.bpa.utils.BpaAppConfigUtil;
import org.egov.bpa.utils.BpaConstants;
import org.egov.bpa.utils.PushBpaApplicationToPortalUtil;
import org.egov.bpa.web.controller.adaptor.SearchBpaApplicationAdaptor;
import org.egov.eis.entity.Assignment;
import org.egov.eis.web.contract.WorkflowContainer;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.custom.CustomImplProvider;
import org.egov.infra.reporting.engine.ReportOutput;
import org.egov.infra.web.support.ui.DataTable;
import org.egov.pims.commons.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author Seema
 *
 */
@Controller
@RequestMapping(value = "/application/ownership/transfer")
public class OwnershipTransferController extends BpaGenericApplicationController {

    private static final String APPLICATION_SUCCESS = "application-success";
    private static final String OWNERSHIP_TRANSFER = "ownershipTransfer";
    private static final String MESSAGE = "message";
    public static final String COMMON_ERROR = "common-error";
    private static final String WORK_FLOW_ACTION = "workFlowAction";
    private static final String APPROVAL_COMENT = "approvalComent";
    private static final String APPRIVALPOSITION = "approvalPosition";
    private static final String APPLICATION_HISTORY = "applicationHistory";
    private static final String ADDITIONALRULE = "additionalRule";
    private static final String AMOUNT_RULE = "amountRule";
    private static final String MSG_APPROVE_FORWARD_REGISTRATION = "msg.approve.success";
    private static final String MSG_UPDATE_FORWARD_REGISTRATION = "msg.update.forward.registration";
    private static final String PDFEXTN = ".pdf";
    private static final String SEARCH_BPA_APPLICATION_FORM = "searchBpaApplicationForm";

    @Autowired
    private OwnershipTransferService ownershipTransferService;
    @Autowired
    private PushBpaApplicationToPortalUtil pushBpaApplicationToPortal;
    @Autowired
    private OwnershipTransferConditionsService ownershipConditionsService;
    @Autowired
    private CustomImplProvider specificNoticeService;
    @Autowired
    private BpaAppConfigUtil bpaAppConfigUtil;
    @Autowired
    private OwnershipTransferSmsAndEmailService ownershipTransferSmsAndEmailService;

    @GetMapping("/update/{applicationNumber}")
    public String updateApplicationForm(final Model model, @PathVariable final String applicationNumber) {
        final OwnershipTransfer ownershipTransfer = ownershipTransferService.findByApplicationNumber(applicationNumber);
        return updateForm(model, ownershipTransfer);
    }

    private String updateForm(final Model model, final OwnershipTransfer ownershipTransfer) {
        List<OwnershipTransfer> ownershipTransfers = ownershipTransferService
                .findByBpaApplication(ownershipTransfer.getApplication());
        if (ownershipTransfers.size() > 1) {
            model.addAttribute("applicants", ownershipTransfers.get(ownershipTransfers.size() - 1).getOwner().getName());
            model.addAttribute("applicantAddress", ownershipTransfers.get(ownershipTransfers.size() - 1).getOwner().getAddress());
        }
        loadFormData(ownershipTransfer, model);
        model.addAttribute(OWNERSHIP_TRANSFER, ownershipTransfer);
        model.addAttribute("citizenOrBusinessUser", bpaUtils.logedInuseCitizenOrBusinessUser());
        return "ownership-transfer-update";

    }

    @PostMapping("/update-submit/{applicationNumber}")
    public String submitTransferOwnership(@PathVariable final String applicationNumber, @RequestParam final BigDecimal amountRule,
            @Valid @ModelAttribute OwnershipTransfer ownershipTransfer, final BindingResult result,
            final Model model,
            final HttpServletRequest request,
            final RedirectAttributes redirectAttributes) throws IOException {

        if (result.hasErrors()) {
            return updateForm(model, ownershipTransfer);
        }
        Position ownerPosition = ownershipTransfer.getCurrentState().getOwnerPosition();
        if (validateLoginUserAndOwnerIsSame(model, securityUtils.getCurrentUser(), ownerPosition))
            return COMMON_ERROR;

        WorkflowBean wfBean = new WorkflowBean();
        if (request.getParameter(APPRIVALPOSITION) != null)
            wfBean.setApproverPositionId(Long.valueOf(request.getParameter(APPRIVALPOSITION)));
        wfBean.setApproverComments(request.getParameter(APPROVAL_COMENT));
        wfBean.setWorkFlowAction(request.getParameter(WORK_FLOW_ACTION));
        wfBean.setAmountRule(amountRule);
        if (isNotBlank(wfBean.getWorkFlowAction())
                && BpaConstants.WF_GENERATE_OWNERSHIP_ORDER.equalsIgnoreCase(wfBean.getWorkFlowAction())) {
            ownershipTransfer.setIsActive(true);
            List<OwnershipTransfer> ownerTransfers = ownershipTransferService
                    .findByBpaApplicationAndDate(ownershipTransfer.getApplication(), ownershipTransfer.getCreatedDate());
            if (!ownerTransfers.isEmpty()) {
                ownerTransfers.get(0).setIsActive(false);
                ownershipTransferService.saveOwnership(ownerTransfers.get(0));
            }
        }
        OwnershipTransfer ownershipres = ownershipTransferService.update(ownershipTransfer, wfBean);
        pushBpaApplicationToPortal.updatePortalUserinbox(ownershipTransfer, null);
        List<Assignment> assignments;
        if (null == wfBean.getApproverPositionId())
            assignments = bpaWorkFlowService
                    .getAssignmentsByPositionAndDate(ownershipTransfer.getCurrentState().getOwnerPosition().getId(), new Date());
        else
            assignments = bpaWorkFlowService.getAssignmentsByPositionAndDate(wfBean.getApproverPositionId(), new Date());
        Position pos = assignments.get(0).getPosition();
        User user = assignments.get(0).getEmployee();
        String message;
        if (WF_APPROVE_BUTTON.equalsIgnoreCase(wfBean.getWorkFlowAction()))
            message = messageSource.getMessage(MSG_APPROVE_FORWARD_REGISTRATION, new String[] {
                    user == null ? ""
                            : user.getUsername().concat("~")
                                    .concat(getDesinationNameByPosition(pos)),
                    ownershipTransfer.getApplicationNumber() }, LocaleContextHolder.getLocale());
        else if (WF_REJECT_BUTTON.equalsIgnoreCase(wfBean.getWorkFlowAction())) {
            OwnershipTransferNoticeService ownershipTransferNotice = (OwnershipTransferNoticeService) specificNoticeService
                    .find(OwnershipTransferNoticeService.class, specificNoticeService.getCityDetails());
            ReportOutput reportOutput = ownershipTransferNotice.generateNotice(ownershipres);
            ownershipTransferSmsAndEmailService.sendSMSAndEmail(ownershipTransfer, null, null);
            return "redirect:/application/ownership/transfer/rejectionnotice/" + ownershipTransfer.getApplicationNumber();
        } else {
            message = messageSource.getMessage(MSG_UPDATE_FORWARD_REGISTRATION, new String[] {
                    user == null ? ""
                            : user.getUsername().concat("~")
                                    .concat(getDesinationNameByPosition(pos)),
                    ownershipTransfer.getApplicationNumber() }, LocaleContextHolder.getLocale());
        }
        if (isNotBlank(wfBean.getWorkFlowAction())
                && BpaConstants.WF_GENERATE_OWNERSHIP_ORDER.equalsIgnoreCase(wfBean.getWorkFlowAction())) {
            OwnershipTransferNoticeService ownershipTransferNotice = (OwnershipTransferNoticeService) specificNoticeService
                    .find(OwnershipTransferNoticeService.class, specificNoticeService.getCityDetails());
            ReportOutput reportOutput = ownershipTransferNotice
                    .generateOwnershipOrder(ownershipTransferService.findByApplicationNumber(applicationNumber));
            ownershipTransferSmsAndEmailService.sendSMSAndEmail(ownershipTransfer, reportOutput,
                    "ownershiptransferorder" + PDFEXTN);
            return "redirect:/application/ownership/transfer/generateorder/" + ownershipres.getApplicationNumber();
        }
        if (APPLICATION_STATUS_REJECTED.equalsIgnoreCase(ownershipTransfer.getStatus().getCode())) {
            if (isNotBlank(wfBean.getWorkFlowAction()) && GENERATEREJECTNOTICE.equalsIgnoreCase(wfBean.getWorkFlowAction())) {

            }
        }

        redirectAttributes.addFlashAttribute(MESSAGE, message);
        return "redirect:/application/ownership/transfer/success/" + ownershipTransfer.getApplicationNumber();
    }

    @GetMapping("/success/{applicationNumber}")
    public String success(@PathVariable final String applicationNumber, final Model model) {
        OwnershipTransfer ownershipTransfer = ownershipTransferService.findByApplicationNumber(applicationNumber);
        model.addAttribute(OWNERSHIP_TRANSFER, ownershipTransfer);
        model.addAttribute(APPLICATION_HISTORY,
                workflowHistoryService.getHistory(Collections.emptyList(), ownershipTransfer.getCurrentState(),
                        ownershipTransfer.getStateHistory()));
        return APPLICATION_SUCCESS;
    }

    @GetMapping("/view/{applicationNumber}")
    public String view(@PathVariable final String applicationNumber, final Model model) {
        OwnershipTransfer ownershipTransfer = ownershipTransferService.findByApplicationNumber(applicationNumber);

        if (ownershipTransfer.getParent() != null) {
            model.addAttribute("ownershipNumber", ownershipTransfer.getParent().getOwnershipNumber());
            model.addAttribute("applicationNo", ownershipTransfer.getParent().getApplicationNumber());
            model.addAttribute("applicants", ownershipTransfer.getParent().getOwner().getName());
            model.addAttribute("applicantAddress", ownershipTransfer.getParent().getOwner().getAddress());
        }
        model.addAttribute(OWNERSHIP_TRANSFER, ownershipTransfer);
        loadFormData(ownershipTransfer, model);
        model.addAttribute(APPLICATION_HISTORY,
                workflowHistoryService.getHistory(Collections.emptyList(), ownershipTransfer.getCurrentState(),
                        ownershipTransfer.getStateHistory()));
        return "ownership-transfer-view";
    }

    @GetMapping("/search")
    public String showSearchApprovedforFee(final Model model) {
        prepareFormData(model);
        model.addAttribute("applnStatusList", bpaStatusService.findAllByModuleType(OWNERSHIPSTATUS_MODULETYPE));
        model.addAttribute(SEARCH_BPA_APPLICATION_FORM, new SearchBpaApplicationForm());
        return "search-ownership-transfer";
    }

    @PostMapping(value = "/search", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String searchRegisterStatusMarriageRecords(@ModelAttribute final SearchBpaApplicationForm searchBpaApplicationForm) {
        return new DataTable<>(ownershipTransferService.pagedSearch(searchBpaApplicationForm),
                searchBpaApplicationForm.draw())
                        .toJson(SearchBpaApplicationAdaptor.class);
    }

    private void buildRejectionReasons(Model model, OwnershipTransfer ownershipTransfer) {
        if (APPLICATION_STATUS_REJECTED.equalsIgnoreCase(ownershipTransfer.getStatus().getCode())
                || WF_ASST_ENG_APPROVED.equalsIgnoreCase(ownershipTransfer.getCurrentState().getValue())) {
            model.addAttribute("showRejectionReasons", true);
            List<ChecklistServiceTypeMapping> additionalRejectionReasonList = checklistServiceTypeService
                    .findByActiveChecklistAndServiceType(ownershipTransfer.getApplication().getServiceType().getDescription(),
                            "ADDITIONALREJECTIONREASONS");

            List<ChecklistServiceTypeMapping> rejectionReasonList = checklistServiceTypeService
                    .findByActiveChecklistAndServiceType(
                            ownershipTransfer.getApplication().getServiceType().getDescription(), "OWNERSHIPREJECTIONREASONS");

            List<OwnershipTransferConditions> rejectionApplnConditions = new ArrayList<>();
            List<OwnershipTransferConditions> rejectionReasons = ownershipConditionsService
                    .findAllConditionsByOwnershipAndType(ownershipTransfer, ConditionType.OWNERSHIPREJECTIONREASONS);
            if (rejectionReasons == null || rejectionReasons.isEmpty()) {
                for (ChecklistServiceTypeMapping checklistServicetype : rejectionReasonList) {
                    OwnershipTransferConditions condition = new OwnershipTransferConditions();
                    NoticeCondition noticeCondtion = new NoticeCondition();
                    noticeCondtion.setChecklistServicetype(checklistServicetype);
                    condition.setNoticeCondition(noticeCondtion);
                    condition.setOwnershipTransfer(ownershipTransfer);
                    rejectionApplnConditions.add(condition);
                }
                ownershipTransfer.setRejectionReasonsTemp(rejectionApplnConditions);
            } else {
                for (OwnershipTransferConditions otc : ownershipTransfer.getRejectionReasons()) {
                    if (otc.getNoticeCondition().getType().name().equals("OWNERSHIPREJECTIONREASONS"))
                        rejectionApplnConditions.add(otc);
                }
                ownershipTransfer.setRejectionReasonsTemp(rejectionApplnConditions);
            }

            List<OwnershipTransferConditions> additionalRejectionRenewalConditions = new ArrayList<>();
            List<OwnershipTransferConditions> additionalRejectionReasons = ownershipConditionsService
                    .findAllConditionsByOwnershipAndType(ownershipTransfer, ConditionType.ADDITIONALREJECTIONREASONS);
            if (additionalRejectionReasons == null || additionalRejectionReasons.isEmpty()) {
                for (ChecklistServiceTypeMapping checklistServicetype : additionalRejectionReasonList) {
                    OwnershipTransferConditions condition = new OwnershipTransferConditions();
                    NoticeCondition noticeCondtion = new NoticeCondition();
                    noticeCondtion.setChecklistServicetype(checklistServicetype);
                    condition.setNoticeCondition(noticeCondtion);
                    condition.setOwnershipTransfer(ownershipTransfer);
                    additionalRejectionRenewalConditions.add(condition);
                }
                ownershipTransfer.setAdditionalRejectReasonsTemp(additionalRejectionRenewalConditions);
            } else {
                for (OwnershipTransferConditions otc : ownershipTransfer.getAdditionalOwnershipConditions()) {
                    if (otc.getNoticeCondition().getType().name().equals("ADDITIONALREJECTIONREASONS"))
                        additionalRejectionRenewalConditions.add(otc);
                }
                ownershipTransfer.setAdditionalRejectReasonsTemp(additionalRejectionRenewalConditions);
            }
        }
    }

    private void loadFormData(final OwnershipTransfer ownershipTransfer, final Model model) {
        final WorkflowContainer workflowContainer = new WorkflowContainer();
        model.addAttribute("isOwnershipApplFeeReq", "NO");
        model.addAttribute("ownershipApplFeeCollected", "NO");
        if (bpaAppConfigUtil.ownershipApplicationFeeCollectionRequired()) {
            model.addAttribute("isOwnershipApplFeeReq", "YES");
        }
        if (ownershipTransfer.getDemand() != null
                && ownershipTransfer.getDemand().getAmtCollected().compareTo(ownershipTransfer.getAdmissionfeeAmount()) >= 0) {
            model.addAttribute("ownershipApplFeeCollected", "YES");
        }
        model.addAttribute("isFeeCollected", bpaUtils.checkAnyTaxIsPendingToCollect(ownershipTransfer.getDemand()));
        prepareWorkflow(model, ownershipTransfer, workflowContainer);
        model.addAttribute("bpaPrimaryDept", bpaUtils.getAppconfigValueByKeyNameForDefaultDept());
        model.addAttribute("feePending", bpaUtils.checkAnyTaxIsPendingToCollect(ownershipTransfer.getDemand()));
        model.addAttribute("workFlowBoundary",
                bpaUtils.getBoundaryForWorkflow(ownershipTransfer.getApplication().getSiteDetail().get(0)).getId());
        model.addAttribute("electionBoundary",
                ownershipTransfer.getApplication().getSiteDetail().get(0).getElectionBoundary() != null
                        ? ownershipTransfer.getApplication().getSiteDetail().get(0).getElectionBoundary().getId()
                        : null);
        model.addAttribute("electionBoundaryName",
                ownershipTransfer.getApplication().getSiteDetail().get(0).getElectionBoundary() != null
                        ? ownershipTransfer.getApplication().getSiteDetail().get(0).getElectionBoundary().getName()
                        : "");
        model.addAttribute("revenueBoundaryName",
                ownershipTransfer.getApplication().getSiteDetail().get(0).getAdminBoundary() != null
                        ? ownershipTransfer.getApplication().getSiteDetail().get(0).getAdminBoundary().getName()
                        : "");
        if (ownershipTransfer.getState() != null) {
            workflowContainer.setAdditionalRule(ownershipTransfer.getApplication().getApplicationType().getName());
            workflowContainer.setPendingActions(ownershipTransfer.getState().getNextAction());
            model.addAttribute(APPLICATION_HISTORY,
                    workflowHistoryService.getHistory(Collections.emptyList(), ownershipTransfer.getCurrentState(),
                            ownershipTransfer.getStateHistory()));
            model.addAttribute("stateType", ownershipTransfer.getClass().getSimpleName());
            model.addAttribute("pendingActions", workflowContainer.getPendingActions());
            model.addAttribute("currentState", ownershipTransfer.getCurrentState().getValue());
            model.addAttribute(AMOUNT_RULE, workflowContainer.getAmountRule());
            model.addAttribute(ADDITIONALRULE, workflowContainer.getAdditionalRule());
            buildRejectionReasons(model, ownershipTransfer);
        }
        if (ownershipTransfer.getDemand() != null)
            buildReceiptDetails(ownershipTransfer.getDemand().getEgDemandDetails(), ownershipTransfer.getReceipts());
    }

}
