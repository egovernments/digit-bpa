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

package org.egov.bpa.web.controller.transaction.citizen;

import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_APPROVED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_CREATED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_SUBMITTED;
import static org.egov.bpa.utils.BpaConstants.WF_LBE_SUBMIT_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_NEW_STATE;
import static org.egov.bpa.utils.BpaConstants.WF_SAVE_BUTTON;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.egov.bpa.transaction.entity.OwnershipTransfer;
import org.egov.bpa.transaction.entity.WorkflowBean;
import org.egov.bpa.transaction.service.OwnershipFeeCalculationService;
import org.egov.bpa.transaction.service.OwnershipTransferService;
import org.egov.bpa.transaction.service.collection.GenericBillGeneratorService;
import org.egov.bpa.utils.BpaAppConfigUtil;
import org.egov.bpa.utils.BpaConstants;
import org.egov.bpa.utils.BpaWorkflowRedirectUtility;
import org.egov.bpa.utils.PushBpaApplicationToPortalUtil;
import org.egov.bpa.web.controller.transaction.BpaGenericApplicationController;
import org.egov.commons.entity.Source;
import org.egov.eis.entity.Assignment;
import org.egov.eis.web.contract.WorkflowContainer;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.custom.CustomImplProvider;
import org.egov.infra.workflow.matrix.entity.WorkFlowMatrix;
import org.egov.pims.commons.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author Seema
 *
 */
@Controller
@RequestMapping(value = "/citizen/application")
public class CitizenOwnershipTransferController extends BpaGenericApplicationController {

    private static final String ONLINE_PAYMENT_ENABLE2 = "onlinePaymentEnable";
    private static final String MSG_PORTAL_FORWARD_REGISTRATION = "msg.portal.forward.registration";
    private static final String COLLECT_FEE_VALIDATE = "collectFeeValidate";
    private static final String OWNERSHIP_CITIZEN_NEW = "ownership-transfer-citizen-new";
    private static final String APPLICATION_SUCCESS = "application-success";
    private static final String OWNERSHIP_TRANSFER = "ownershipTransfer";
    private static final String MESSAGE = "message";
    public static final String COMMON_ERROR = "common-error";
    private static final String WORK_FLOW_ACTION = "workFlowAction";
    private static final String APPLICATION_HISTORY = "applicationHistory";
    private static final String ONLINE_PAYMENT_ENABLE = ONLINE_PAYMENT_ENABLE2;
    private static final String TRUE = "TRUE";

    @Autowired
    private OwnershipTransferService ownershipTransferService;
    @Autowired
    private PushBpaApplicationToPortalUtil pushBpaApplicationToPortal;
    @Autowired
    private GenericBillGeneratorService genericBillGeneratorService;
    @Autowired
    private BpaWorkflowRedirectUtility bpaWorkflowRedirectUtility;
    @Autowired
    private BpaAppConfigUtil bpaAppConfigUtil;
    @Autowired
    private CustomImplProvider specificNoticeService;

    @GetMapping("/ownership/transfer/apply")
    public String showOwnerShipTransferForm(final Model model) {
        OwnershipTransfer ownershipTransfer = new OwnershipTransfer();
        ownershipTransfer.setApplicationDate(new Date());
        ownershipTransfer.setSource(Source.CITIZENPORTAL);
        model.addAttribute(OWNERSHIP_TRANSFER, ownershipTransfer);
        String enableOrDisablePayOnline = bpaUtils.getAppconfigValueByKeyName(BpaConstants.ENABLEONLINEPAYMENT);
        model.addAttribute("onlinePaymentEnable",
                (enableOrDisablePayOnline.equalsIgnoreCase("YES") ? Boolean.TRUE : Boolean.FALSE));
        return OWNERSHIP_CITIZEN_NEW;
    }

    @PostMapping("/ownership/transfer/create")
    public String submitForOwnerShipTransfer(@Valid @ModelAttribute OwnershipTransfer ownershipTransfer,
            final BindingResult errors, final Model model, final HttpServletRequest request,
            final RedirectAttributes redirectAttributes) {
        ownershipTransferService.validateDocs(ownershipTransfer, errors);
        ownershipTransferService.validateOwnershipTransfer(ownershipTransfer, errors);
        if (errors.hasErrors()) {
            model.addAttribute(OWNERSHIP_TRANSFER, ownershipTransfer);
            return OWNERSHIP_CITIZEN_NEW;
        }
        if (ownershipTransfer.getSource() == null)
            ownershipTransfer.setSource(Source.CITIZENPORTAL);
        Long approvalPosition = null;
        WorkflowBean wfBean = new WorkflowBean();
        wfBean.setWorkFlowAction(request.getParameter(WORK_FLOW_ACTION));
        if (WF_LBE_SUBMIT_BUTTON.equalsIgnoreCase(wfBean.getWorkFlowAction())) {
            final WorkFlowMatrix wfMatrix = bpaUtils.getWfMatrixByCurrentState(
                    false, ownershipTransfer.getStateType(), WF_NEW_STATE,
                    ownershipTransfer.getApplication().getApplicationType().getName());
            if (wfMatrix != null)
                approvalPosition = bpaUtils.getUserPositionIdByZone(wfMatrix.getNextDesignation(),
                        bpaUtils.getBoundaryForWorkflow(ownershipTransfer.getApplication().getSiteDetail().get(0)).getId());
            wfBean.setApproverPositionId(approvalPosition);
        }
        Boolean onlinePaymentEnable = request.getParameter(ONLINE_PAYMENT_ENABLE) != null
                && request.getParameter(ONLINE_PAYMENT_ENABLE).equalsIgnoreCase(TRUE) ? Boolean.TRUE : Boolean.FALSE;
        OwnershipFeeCalculationService feeCalculation = (OwnershipFeeCalculationService) specificNoticeService
                .find(OwnershipFeeCalculationService.class, specificNoticeService.getCityDetails());
        if (bpaAppConfigUtil.ownershipApplicationFeeCollectionRequired())
            ownershipTransfer.setAdmissionfeeAmount(
                    feeCalculation.calculateAdmissionFeeAmount(ownershipTransfer.getApplication().getServiceType().getId()));
        else
            ownershipTransfer.setAdmissionfeeAmount(BigDecimal.valueOf(0));
        ownershipTransferService.processAndStoreOwnershipDocuments(ownershipTransfer);
        if (ownershipTransfer.getOwner().getUser() != null && ownershipTransfer.getOwner().getUser().getId() == null)
            ownershipTransferService.buildOwnerDetails(ownershipTransfer);

        OwnershipTransfer ownershipres = ownershipTransferService.createNewApplication(ownershipTransfer, wfBean);

        pushBpaApplicationToPortal.createPortalUserinbox(ownershipres,
                Arrays.asList(ownershipres.getApplication().getOwner().getUser(),
                        ownershipres.getApplication().getStakeHolder().get(0).getStakeHolder()),
                wfBean.getWorkFlowAction());

        if (wfBean.getWorkFlowAction() != null && wfBean.getWorkFlowAction().equals(WF_LBE_SUBMIT_BUTTON) && onlinePaymentEnable
                && bpaUtils.checkAnyTaxIsPendingToCollect(ownershipres.getDemand()))
            return genericBillGeneratorService.generateBillAndRedirectToCollection(ownershipres, model);
        else if (wfBean.getWorkFlowAction() != null && wfBean.getWorkFlowAction().equals(WF_LBE_SUBMIT_BUTTON)
                && !bpaUtils.checkAnyTaxIsPendingToCollect(ownershipres.getDemand())) {
            bpaWorkflowRedirectUtility.redirectToBpaWorkFlow(ownershipres, wfBean);
            List<Assignment> assignments;
            if (null == approvalPosition)
                assignments = bpaWorkFlowService.getAssignmentsByPositionAndDate(
                        ownershipres.getCurrentState().getOwnerPosition().getId(), new Date());
            else
                assignments = bpaWorkFlowService.getAssignmentsByPositionAndDate(approvalPosition, new Date());
            Position pos = assignments.get(0).getPosition();
            User wfUser = assignments.get(0).getEmployee();
            String message = messageSource.getMessage(MSG_PORTAL_FORWARD_REGISTRATION,
                    new String[] {
                            wfUser == null ? ""
                                    : wfUser.getUsername().concat("~").concat(getDesinationNameByPosition(pos)),
                            ownershipres.getApplicationNumber() },
                    LocaleContextHolder.getLocale());
            model.addAttribute(MESSAGE, message);
        } else if (WF_SAVE_BUTTON.equalsIgnoreCase(wfBean.getWorkFlowAction())) {
            model.addAttribute(MESSAGE, messageSource.getMessage("msg.ownership.transfer.save",
                    new String[] { ownershipres.getApplicationNumber() }, LocaleContextHolder.getLocale()));
        } else {
            model.addAttribute(MESSAGE, messageSource.getMessage("msg.ownership.transfer.submit",
                    new String[] { ownershipres.getApplicationNumber() }, LocaleContextHolder.getLocale()));
        }
        return APPLICATION_SUCCESS;
    }

    @GetMapping("/ownership/transfer/update/{applicationNumber}")
    public String updateOrViewPermitRenewalDetails(@PathVariable String applicationNumber, final Model model) {
        OwnershipTransfer ownershipTransfer = ownershipTransferService.findByApplicationNumber(applicationNumber);
        return updateFormData(model, ownershipTransfer);
    }

    private String updateFormData(final Model model, OwnershipTransfer ownershipTransfer) {
        List<OwnershipTransfer> ownershipTransfers = ownershipTransferService
                .findByBpaApplication(ownershipTransfer.getApplication());
        if (ownershipTransfers.size() > 1) {
            model.addAttribute("ownershipNumber", ownershipTransfer.getParent().getOwnershipNumber());
            model.addAttribute("applicationNo", ownershipTransfer.getParent().getApplicationNumber());
            model.addAttribute("applicants", ownershipTransfer.getParent().getOwner().getName());
            model.addAttribute("applicantAddress", ownershipTransfer.getParent().getOwner().getAddress());
        }

        model.addAttribute(OWNERSHIP_TRANSFER, ownershipTransfer);
        loadFormData(ownershipTransfer, model);
        if (APPLICATION_STATUS_CREATED.equalsIgnoreCase(ownershipTransfer.getStatus().getCode()))
            return "ownership-transfer-citizen-update";
        else
            return "ownership-transfer-citizen-view";
    }

    @PostMapping("/ownership/transfer/update/{applicationNumber}")
    public String updatePermitRenewalDetails(@ModelAttribute OwnershipTransfer ownershipTransfer,
            @PathVariable String applicationNumber, final HttpServletRequest request, final Model model,
            final BindingResult errors, final RedirectAttributes redirectAttributes) {

        ownershipTransferService.validateDocs(ownershipTransfer, errors);
        if (errors.hasErrors()) {
            return updateFormData(model, ownershipTransfer);
        }

        Long approvalPosition = null;
        WorkflowBean wfBean = new WorkflowBean();
        wfBean.setWorkFlowAction(request.getParameter(WORK_FLOW_ACTION));
        if (WF_LBE_SUBMIT_BUTTON.equalsIgnoreCase(wfBean.getWorkFlowAction())) {
            final WorkFlowMatrix wfMatrix = bpaUtils.getWfMatrixByCurrentState(
                    false, ownershipTransfer.getStateType(), WF_NEW_STATE,
                    ownershipTransfer.getApplication().getApplicationType().getName());
            if (wfMatrix != null)
                approvalPosition = bpaUtils.getUserPositionIdByZone(wfMatrix.getNextDesignation(),
                        bpaUtils.getBoundaryForWorkflow(ownershipTransfer.getApplication().getSiteDetail().get(0)).getId());
            wfBean.setApproverPositionId(approvalPosition);
        }
        if (!ownershipTransfer.getOwnershipTransferDocuments().isEmpty())
            ownershipTransferService.processAndStoreOwnershipDocuments(ownershipTransfer);
        OwnershipTransfer ownershipRes = ownershipTransferService.save(ownershipTransfer, wfBean);
        pushBpaApplicationToPortal.updatePortalUserinbox(ownershipTransfer, null);
        if (WF_SAVE_BUTTON.equalsIgnoreCase(wfBean.getWorkFlowAction()))
            model.addAttribute(MESSAGE, messageSource.getMessage("msg.ownership.transfer.save",
                    new String[] { ownershipRes.getApplicationNumber() }, LocaleContextHolder.getLocale()));
        else
            model.addAttribute(MESSAGE, messageSource.getMessage("msg.ownership.transfer.submit",
                    new String[] { ownershipRes.getApplicationNumber() }, LocaleContextHolder.getLocale()));
        return APPLICATION_SUCCESS;
    }

    private void loadFormData(final OwnershipTransfer ownershipTransfer, final Model model) {
        final WorkflowContainer workflowContainer = new WorkflowContainer();
        model.addAttribute("isFeeCollected", bpaUtils.checkAnyTaxIsPendingToCollect(ownershipTransfer.getDemand()));
        prepareWorkflow(model, ownershipTransfer, workflowContainer);
        if (APPLICATION_STATUS_SUBMITTED.equals(ownershipTransfer.getStatus().getCode())
                || APPLICATION_STATUS_APPROVED.equals(ownershipTransfer.getStatus().getCode())) {
            if (bpaUtils.checkAnyTaxIsPendingToCollect(ownershipTransfer.getDemand())) {
                model.addAttribute(COLLECT_FEE_VALIDATE,
                        messageSource.getMessage("msg.payfees.toprocess.appln", null, null));
                String enableOrDisablePayOnline = bpaUtils.getAppconfigValueByKeyName(BpaConstants.ENABLEONLINEPAYMENT);
                model.addAttribute(ONLINE_PAYMENT_ENABLE2,
                        (enableOrDisablePayOnline.equalsIgnoreCase("YES") ? Boolean.TRUE : Boolean.FALSE));
            } else {
                model.addAttribute(COLLECT_FEE_VALIDATE, "");
            }
        }
        model.addAttribute(APPLICATION_HISTORY, workflowHistoryService.getHistory(Collections.emptyList(),
                ownershipTransfer.getCurrentState(), ownershipTransfer.getStateHistory()));

    }

}
