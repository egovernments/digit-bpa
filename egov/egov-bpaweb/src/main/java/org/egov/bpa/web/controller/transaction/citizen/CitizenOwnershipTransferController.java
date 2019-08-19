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

import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_CREATED;
import static org.egov.bpa.utils.BpaConstants.WF_LBE_SUBMIT_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_NEW_STATE;
import static org.egov.bpa.utils.BpaConstants.WF_SAVE_BUTTON;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.egov.bpa.transaction.entity.OwnershipTransfer;
import org.egov.bpa.transaction.entity.WorkflowBean;
import org.egov.bpa.transaction.service.OwnershipTransferService;
import org.egov.bpa.utils.PushBpaApplicationToPortalUtil;
import org.egov.bpa.web.controller.transaction.BpaGenericApplicationController;
import org.egov.commons.entity.Source;
import org.egov.eis.web.contract.WorkflowContainer;
import org.egov.infra.workflow.matrix.entity.WorkFlowMatrix;
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

    private static final String OWNERSHIP_CITIZEN_NEW = "ownership-transfer-citizen-new";
    private static final String APPLICATION_SUCCESS = "application-success";
    private static final String OWNERSHIP_TRANSFER = "ownershipTransfer";
    private static final String MESSAGE = "message";
    public static final String COMMON_ERROR = "common-error";
    private static final String WORK_FLOW_ACTION = "workFlowAction";
    private static final String APPLICATION_HISTORY = "applicationHistory";


    @Autowired
    private OwnershipTransferService ownershipTransferService;
    @Autowired
    private PushBpaApplicationToPortalUtil pushBpaApplicationToPortal;

    @GetMapping("/ownership/transfer/apply")
    public String showPermitRenewalForm(final Model model) {
        OwnershipTransfer ownershipTransfer = new OwnershipTransfer();
        ownershipTransfer.setApplicationDate(new Date());
        ownershipTransfer.setSource(Source.CITIZENPORTAL);
        model.addAttribute(OWNERSHIP_TRANSFER, ownershipTransfer);
        return OWNERSHIP_CITIZEN_NEW;
    }

    @PostMapping("/ownership/transfer/create")
    public String submitPermitRenewal(@ModelAttribute OwnershipTransfer ownershipTransfer, final HttpServletRequest request,
            final Model model, final BindingResult errors,
            final RedirectAttributes redirectAttributes) {

     
        if (ownershipTransfer.getSource() == null)
        	ownershipTransfer.setSource(Source.CITIZENPORTAL);
        Long approvalPosition = null;
        WorkflowBean wfBean = new WorkflowBean();
        wfBean.setWorkFlowAction(request.getParameter(WORK_FLOW_ACTION));
        if (WF_LBE_SUBMIT_BUTTON.equalsIgnoreCase(wfBean.getWorkFlowAction())) {
            final WorkFlowMatrix wfMatrix = bpaUtils.getWfMatrixByCurrentState(
                    false, ownershipTransfer.getStateType(), WF_NEW_STATE,
                    ownershipTransfer.getParent().getApplicationType().getName());
            if (wfMatrix != null)
                approvalPosition = bpaUtils.getUserPositionIdByZone(wfMatrix.getNextDesignation(),
                        bpaUtils.getBoundaryForWorkflow(ownershipTransfer.getParent().getSiteDetail().get(0)).getId());
            wfBean.setApproverPositionId(approvalPosition);
        }
        ownershipTransferService.processAndStoreOwnershipDocuments(ownershipTransfer);
        if (ownershipTransfer.getOwner().getUser() != null && ownershipTransfer.getOwner().getUser().getId() == null)
        	ownershipTransferService.buildOwnerDetails(ownershipTransfer);
        OwnershipTransfer ownershipres = ownershipTransferService.save(ownershipTransfer, wfBean);
        pushBpaApplicationToPortal.createPortalUserinbox(ownershipres,
                Arrays.asList(ownershipres.getParent().getOwner().getUser(),
                		ownershipres.getParent().getStakeHolder().get(0).getStakeHolder()),
                wfBean.getWorkFlowAction());
        
        if (WF_SAVE_BUTTON.equalsIgnoreCase(wfBean.getWorkFlowAction()))
            model.addAttribute(MESSAGE, messageSource.getMessage("msg.ownership.transfer.save",
                    new String[] { ownershipres.getApplicationNumber() }, LocaleContextHolder.getLocale()));
        else
            model.addAttribute(MESSAGE, messageSource.getMessage("msg.ownership.transfer.submit",
                    new String[] { ownershipres.getApplicationNumber() }, LocaleContextHolder.getLocale()));
        return APPLICATION_SUCCESS;
    }
    
    @GetMapping("/ownership/transfer/update/{applicationNumber}")
    public String updateOrViewPermitRenewalDetails(@PathVariable String applicationNumber, final Model model) {
        OwnershipTransfer ownershipTransfer = ownershipTransferService.findByApplicationNumber(applicationNumber);
        List<OwnershipTransfer> ownershipTransfers = ownershipTransferService.findByBpaApplication(ownershipTransfer.getParent());
        if(ownershipTransfers.size()>1) {
        	model.addAttribute("applicants",ownershipTransfers.get(ownershipTransfers.size()-1).getOwner().getName());
        	model.addAttribute("applicantAddress",ownershipTransfers.get(ownershipTransfers.size()-1).getOwner().getAddress());
        }
        loadFormData(ownershipTransfer, model);
        model.addAttribute(OWNERSHIP_TRANSFER, ownershipTransfer);
        if (APPLICATION_STATUS_CREATED.equalsIgnoreCase(ownershipTransfer.getStatus().getCode()))
            return "ownership-transfer-citizen-update";
        else
            return "ownership-transfer-view";
    }
    
    @PostMapping("/ownership/transfer/update/{applicationNumber}")
    public String updatePermitRenewalDetails(@ModelAttribute OwnershipTransfer ownershipTransfer, @PathVariable String applicationNumber,
            final HttpServletRequest request,
            final Model model, final BindingResult errors,
            final RedirectAttributes redirectAttributes) {
        Long approvalPosition = null;
        WorkflowBean wfBean = new WorkflowBean();
        wfBean.setWorkFlowAction(request.getParameter(WORK_FLOW_ACTION));
        if (WF_LBE_SUBMIT_BUTTON.equalsIgnoreCase(wfBean.getWorkFlowAction())) {
            final WorkFlowMatrix wfMatrix = bpaUtils.getWfMatrixByCurrentState(
                    false, ownershipTransfer.getStateType(), WF_NEW_STATE,
                    ownershipTransfer.getParent().getApplicationType().getName());
            if (wfMatrix != null)
                approvalPosition = bpaUtils.getUserPositionIdByZone(wfMatrix.getNextDesignation(),
                        bpaUtils.getBoundaryForWorkflow(ownershipTransfer.getParent().getSiteDetail().get(0)).getId());
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
        model.addAttribute("owner", ownershipTransfer.getOwner());
        model.addAttribute("coApplicants", ownershipTransfer.getCoApplicants());
        prepareWorkflow(model, ownershipTransfer, workflowContainer);
        model.addAttribute("feePending", bpaUtils.checkAnyTaxIsPendingToCollect(ownershipTransfer.getDemand()));
        model.addAttribute(APPLICATION_HISTORY,
                workflowHistoryService.getHistory(Collections.emptyList(), ownershipTransfer.getCurrentState(),
                		ownershipTransfer.getStateHistory()));
    }

}
