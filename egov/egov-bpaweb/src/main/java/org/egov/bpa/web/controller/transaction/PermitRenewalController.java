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

import static org.egov.bpa.utils.BpaConstants.WF_APPROVE_BUTTON;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.egov.bpa.transaction.entity.PermitRenewal;
import org.egov.bpa.transaction.entity.WorkflowBean;
import org.egov.bpa.transaction.notice.util.BpaNoticeUtil;
import org.egov.bpa.transaction.service.PermitRenewalService;
import org.egov.bpa.utils.PushBpaApplicationToPortalUtil;
import org.egov.eis.entity.Assignment;
import org.egov.eis.web.contract.WorkflowContainer;
import org.egov.infra.admin.master.entity.User;
import org.egov.pims.commons.Position;
import org.joda.time.DateTime;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author vinoth
 *
 */
@Controller
@RequestMapping(value = "/application/permit/renewal")
public class PermitRenewalController extends BpaGenericApplicationController {

    private static final String PERMIT_RENEWAL = "permitRenewal";
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

    @Autowired
    private BpaNoticeUtil bpaNoticeUtil;
    @Autowired
    private PermitRenewalService permitRenewalService;
    @Autowired
    private PushBpaApplicationToPortalUtil pushBpaApplicationToPortal;

    @GetMapping("/update/{applicationNumber}")
    public String updateOrViewPermitRenewalDetails(@PathVariable String applicationNumber, final Model model) {
        PermitRenewal permitRenewal = permitRenewalService.findByApplicationNumber(applicationNumber);
        model.addAttribute("permitExpiryDate", bpaNoticeUtil.calculateCertExpryDate(
                new DateTime(permitRenewal.getParent().getPlanPermissionDate()),
                permitRenewal.getParent().getServiceType().getValidity()));
        model.addAttribute(APPLICATION_HISTORY,
                workflowHistoryService.getHistory(Collections.emptyList(), permitRenewal.getCurrentState(),
                        permitRenewal.getStateHistory()));
        model.addAttribute(PERMIT_RENEWAL, permitRenewal);
        loadFormData(permitRenewal, model);
        return "permit-renewal-update";
    }

    private void loadFormData(final PermitRenewal renewal, final Model model) {
        final WorkflowContainer workflowContainer = new WorkflowContainer();
        workflowContainer.setAdditionalRule(renewal.getParent().getApplicationType().getName());
        workflowContainer.setPendingActions(renewal.getState().getNextAction());
        prepareWorkflow(model, renewal, workflowContainer);
        model.addAttribute("stateType", renewal.getClass().getSimpleName());
        model.addAttribute("pendingActions", workflowContainer.getPendingActions());
        model.addAttribute("currentState", renewal.getCurrentState().getValue());
        model.addAttribute(AMOUNT_RULE, workflowContainer.getAmountRule());
        model.addAttribute(ADDITIONALRULE, workflowContainer.getAdditionalRule());
        model.addAttribute("bpaPrimaryDept", bpaUtils.getAppconfigValueByKeyNameForDefaultDept());
        model.addAttribute("workFlowBoundary",
                bpaUtils.getBoundaryForWorkflow(renewal.getParent().getSiteDetail().get(0)).getId());
        model.addAttribute("electionBoundary", renewal.getParent().getSiteDetail().get(0).getElectionBoundary() != null
                ? renewal.getParent().getSiteDetail().get(0).getElectionBoundary().getId()
                : null);
        model.addAttribute("electionBoundaryName", renewal.getParent().getSiteDetail().get(0).getElectionBoundary() != null
                ? renewal.getParent().getSiteDetail().get(0).getElectionBoundary().getName()
                : "");
        model.addAttribute("revenueBoundaryName", renewal.getParent().getSiteDetail().get(0).getAdminBoundary() != null
                ? renewal.getParent().getSiteDetail().get(0).getAdminBoundary().getName()
                : "");
    }

    @PostMapping("/update-submit/{applicationNumber}")
    public String submitPermitRevocationInitiation(@ModelAttribute PermitRenewal permitRenewal,
            @PathVariable final String applicationNumber, @RequestParam final BigDecimal amountRule,
            final HttpServletRequest request,
            final Model model, final BindingResult errors,
            final RedirectAttributes redirectAttributes) {

        Position ownerPosition = permitRenewal.getCurrentState().getOwnerPosition();
        if (validateLoginUserAndOwnerIsSame(model, securityUtils.getCurrentUser(), ownerPosition))
            return COMMON_ERROR;

        WorkflowBean wfBean = new WorkflowBean();
        wfBean.setApproverPositionId(Long.valueOf(request.getParameter(APPRIVALPOSITION)));
        wfBean.setApproverComments(request.getParameter(APPROVAL_COMENT));
        wfBean.setWorkFlowAction(request.getParameter(WORK_FLOW_ACTION));
        wfBean.setAmountRule(amountRule);
        permitRenewalService.update(permitRenewal, wfBean);
        pushBpaApplicationToPortal.updatePortalUserinbox(permitRenewal, null);
        List<Assignment> assignments;
        if (null == wfBean.getApproverPositionId())
            assignments = bpaWorkFlowService
                    .getAssignmentsByPositionAndDate(permitRenewal.getCurrentState().getOwnerPosition().getId(), new Date());
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
                    permitRenewal.getApplicationNumber() }, LocaleContextHolder.getLocale());
        else {
            message = messageSource.getMessage(MSG_UPDATE_FORWARD_REGISTRATION, new String[] {
                    user == null ? ""
                            : user.getUsername().concat("~")
                                    .concat(getDesinationNameByPosition(pos)),
                    permitRenewal.getApplicationNumber() }, LocaleContextHolder.getLocale());
        }
        redirectAttributes.addFlashAttribute(MESSAGE, message);
        return "redirect:/application/permit/renewal/success/" + permitRenewal.getApplicationNumber();
    }

    @GetMapping("/success/{applicationNumber}")
    public String success(@PathVariable final String applicationNumber, final Model model) {
        PermitRenewal permitRenewal = permitRenewalService.findByApplicationNumber(applicationNumber);
        model.addAttribute("permitExpiryDate", bpaNoticeUtil.calculateCertExpryDate(
                new DateTime(permitRenewal.getParent().getPlanPermissionDate()),
                permitRenewal.getParent().getServiceType().getValidity()));
        model.addAttribute(PERMIT_RENEWAL, permitRenewal);
        model.addAttribute(APPLICATION_HISTORY,
                workflowHistoryService.getHistory(Collections.emptyList(), permitRenewal.getCurrentState(),
                        permitRenewal.getStateHistory()));
        return "permit-renewal-result";
    }

}
