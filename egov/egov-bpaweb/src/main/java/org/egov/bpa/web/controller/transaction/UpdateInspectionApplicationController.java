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
package org.egov.bpa.web.controller.transaction;

import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_DOC_VERIFIED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_FIELD_INS;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_TS_INS;
import static org.egov.bpa.utils.BpaConstants.DESIGNATION_AE;
import static org.egov.bpa.utils.BpaConstants.DESIGNATION_OVERSEER;
import static org.egov.bpa.utils.BpaConstants.FIELD_INSPECTION_COMPLETED;
import static org.egov.bpa.utils.BpaConstants.FWDINGTOLPINITIATORPENDING;
import static org.egov.bpa.utils.BpaConstants.FWD_TO_AE_AFTER_TS_INSP;
import static org.egov.bpa.utils.BpaConstants.FWD_TO_AE_FOR_APPROVAL;
import static org.egov.bpa.utils.BpaConstants.FWD_TO_OVERSEER_AFTER_TS_INSPN;
import static org.egov.bpa.utils.BpaConstants.FWD_TO_OVRSR_FOR_FIELD_INS;
import static org.egov.bpa.utils.BpaConstants.MESSAGE;
import static org.egov.bpa.utils.BpaConstants.WF_APPROVE_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_REJECT_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_TS_APPROVAL_PENDING;
import static org.egov.bpa.utils.BpaConstants.WF_TS_INSPECTION_INITIATED;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.egov.bpa.master.entity.PermitRevocation;
import org.egov.bpa.transaction.entity.InspectionApplication;
import org.egov.bpa.transaction.entity.InspectionAppointmentSchedule;
import org.egov.bpa.transaction.entity.InspectionLetterToParty;
import org.egov.bpa.transaction.entity.PermitInspectionApplication;
import org.egov.bpa.transaction.entity.WorkflowBean;
import org.egov.bpa.transaction.entity.enums.AppointmentSchedulePurpose;
import org.egov.bpa.transaction.service.InConstructionInspectionService;
import org.egov.bpa.transaction.service.InspectionApplicationService;
import org.egov.bpa.transaction.service.InspectionLetterToPartyService;
import org.egov.bpa.transaction.service.PermitFeeService;
import org.egov.bpa.transaction.service.PermitRevocationService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.eis.entity.Assignment;
import org.egov.eis.service.PositionMasterService;
import org.egov.eis.web.contract.WorkflowContainer;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.workflow.entity.State;
import org.egov.infra.workflow.entity.StateHistory;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/inspection")
public class UpdateInspectionApplicationController extends BpaGenericApplicationController {

    public static final String COMMON_ERROR = "common-error";
    private static final String WORK_FLOW_ACTION = "workFlowAction";
    private static final String APPRIVALPOSITION = "approvalPosition";
    private static final String APPLICATION_HISTORY = "applicationHistory";
    private static final String ADDITIONALRULE = "additionalRule";
    private static final String AMOUNT_RULE = "amountRule";

    @Autowired
    private InConstructionInspectionService inspectionService;
    @Autowired
    private PositionMasterService positionMasterService;
    @Autowired
    protected PermitFeeService permitFeeService;
    @Autowired
    private InspectionApplicationService inspectionAppService;
    @Autowired
    private InspectionLetterToPartyService letterToPartyService;
    @Autowired
    private PermitRevocationService permitRevocationService;
    
    @ModelAttribute("inspectionApplication")
    public InspectionApplication getInspectionApplication(@PathVariable final String applicationNumber) {
    	return inspectionAppService.findByInspectionApplicationNumber(applicationNumber).getInspectionApplication();
    }
	

    @GetMapping("/update/{applicationNumber}")
    public String updateApplicationForm(final Model model, @PathVariable final String applicationNumber) {
        final PermitInspectionApplication permitInspection = inspectionAppService.findByInspectionApplicationNumber(applicationNumber);
       // model.addAttribute("inspectionApplication",permitInspection.getInspectionApplication());
        model.addAttribute("eDcrNumber",permitInspection.getApplication().geteDcrNumber());
        model.addAttribute("planPermissionNumber",permitInspection.getApplication().getPlanPermissionNumber());
        prepareActions(model, permitInspection.getInspectionApplication());
        loadFormData(model, permitInspection);
        loadCommonApplicationDetails(model, permitInspection.getInspectionApplication());
        model.addAttribute("citizenOrBusinessUser", bpaUtils.logedInuseCitizenOrBusinessUser());       
        return "inspection-update-form";
    }

    
    @PostMapping("/update-submit/{applicationNumber}")
    public String updateApplication(@ModelAttribute InspectionApplication inspectionApplication,
            @PathVariable final String applicationNumber,
            final BindingResult resultBinder,
            final HttpServletRequest request,
            final Model model,
            final RedirectAttributes redirectAttributes,
            @RequestParam final BigDecimal amountRule) throws IOException {
    	
    	if (resultBinder.hasErrors())
            return "inspection-update-form";

        Position ownerPosition = inspectionApplication.getCurrentState().getOwnerPosition();
        if (validateLoginUserAndOwnerIsSame(model, securityUtils.getCurrentUser(), ownerPosition))
            return COMMON_ERROR;

        WorkflowBean wfBean = new WorkflowBean();
        wfBean.setWorkFlowAction(request.getParameter(WORK_FLOW_ACTION));
        wfBean.setAmountRule(amountRule);
        Position pos = null;
        Long approvalPosition = null;

        if (StringUtils.isNotBlank(request.getParameter(APPRIVALPOSITION))
                && !WF_REJECT_BUTTON.equalsIgnoreCase(wfBean.getWorkFlowAction())) {
            approvalPosition = Long.valueOf(request.getParameter(APPRIVALPOSITION));
        }else if (FWDINGTOLPINITIATORPENDING.equalsIgnoreCase(inspectionApplication.getState().getNextAction())) {
            List<InspectionLetterToParty> lettertoParties = letterToPartyService.findByInspectionApplicationOrderByIdDesc(inspectionApplication);
            StateHistory<Position> stateHistory = bpaWorkFlowService.getStateHistoryToGetLPInitiator(
            		inspectionApplication.getStateHistory(),
                    lettertoParties.get(0).getLetterToParty().getStateForOwnerPosition());
            approvalPosition = stateHistory.getOwnerPosition().getId();
        }

        if (WF_TS_INSPECTION_INITIATED.equalsIgnoreCase(inspectionApplication.getStatus().getCode())) {
            pos = positionMasterService.getPositionById(bpaWorkFlowService.getTownSurveyorInspnInitiator(
            		inspectionApplication.getStateHistory(), inspectionApplication.getCurrentState()));
            approvalPosition = positionMasterService
                    .getPositionById(bpaWorkFlowService.getTownSurveyorInspnInitiator(inspectionApplication.getStateHistory(),
                    		inspectionApplication.getCurrentState()))
                    .getId();
        } 
        if (validateLoginUserAndOwnerIsSame(model, securityUtils.getCurrentUser(), ownerPosition))
            return COMMON_ERROR;
        wfBean.setApproverPositionId(approvalPosition);
        wfBean.setApproverComments(inspectionApplication.getApprovalComent());
        if (inspectionApplication.getState().getValue() != null)
            wfBean.setCurrentState(inspectionApplication.getState().getValue());
        
        final PermitInspectionApplication permitInspection = inspectionAppService.findByInspectionApplicationNumber(applicationNumber);
        permitInspection.setInspectionApplication(inspectionApplication);
        PermitInspectionApplication inspectionResponse = inspectionAppService.update(permitInspection, wfBean);
        
        bpaUtils.updatePortalUserinbox(inspectionResponse, null);
        if (null != approvalPosition) {
            pos = positionMasterService.getPositionById(approvalPosition);
        }
        if (null == approvalPosition) {
            pos = positionMasterService.getPositionById(inspectionResponse.getInspectionApplication().getCurrentState().getOwnerPosition().getId());
        }
        User user = workflowHistoryService
                .getUserPositionByPassingPosition(approvalPosition == null ? pos.getId() : approvalPosition);
        String message;
       if (WF_APPROVE_BUTTON.equalsIgnoreCase(wfBean.getWorkFlowAction()))
            message = messageSource.getMessage("msg.approve.inspection", new String[] {
                    user == null ? ""
                            : user.getUsername().concat("~")
                                    .concat(getDesinationNameByPosition(pos)),
                                    inspectionResponse.getInspectionApplication().getApplicationNumber() }, LocaleContextHolder.getLocale());
      
       else if(BpaConstants.WF_REVOKE_STATE.equalsIgnoreCase(wfBean.getWorkFlowAction())){
    	   PermitRevocation permitRevocation = new PermitRevocation();
    	   permitRevocation.setApplication(permitInspection.getApplication());
    	   permitRevocation.setInitiateRemarks(inspectionApplication.getApprovalComent());
    	   PermitRevocation permitRevocationRes = permitRevocationService.save(permitRevocation);
    	   bpaUtils.updatePortalUserinbox(permitRevocationRes.getApplication(), null);
    	   message = messageSource.getMessage("msg.permit.revoke.initiate",
                   new String[] { permitRevocationRes.getApplicationNumber() }, LocaleContextHolder.getLocale());
    
       } else {
            message = messageSource.getMessage("msg.update.forward.inspection", new String[] {
                    user == null ? ""
                            : user.getUsername().concat("~")
                                    .concat(getDesinationNameByPosition(pos)),
                                    inspectionResponse.getInspectionApplication().getApplicationNumber() }, LocaleContextHolder.getLocale());
        }

        redirectAttributes.addFlashAttribute(MESSAGE, message);
        bpaUtils.sendSmsEmailForInspection(inspectionResponse.getInspectionApplication(), inspectionResponse.getApplication());

        return "redirect:/inspection/success/" + inspectionResponse.getInspectionApplication().getApplicationNumber();
    }

    @GetMapping("/success/{applicationNumber}")
    public String success(@PathVariable final String applicationNumber, final Model model) {
        final PermitInspectionApplication permitInspection = inspectionAppService.findByInspectionApplicationNumber(applicationNumber);
        model.addAttribute("eDcrNumber",permitInspection.getApplication().geteDcrNumber());
        model.addAttribute("planPermissionNumber",permitInspection.getApplication().getPlanPermissionNumber());
        loadCommonApplicationDetails(model, permitInspection.getInspectionApplication());
        return "inspection-view";
    }

    private void prepareActions(final Model model, final InspectionApplication inspectionApplication) {
        State<Position> currentState = inspectionApplication.getCurrentState();
        String currentStatus = inspectionApplication.getStatus().getCode();
        String currentStateValue = currentState.getValue();
        String pendingAction = currentState.getNextAction();
        String mode = StringUtils.EMPTY;
        AppointmentSchedulePurpose scheduleType = null;
        List<String> purposeInsList = new ArrayList<>();
        for (InspectionAppointmentSchedule inspectionSchedule : inspectionApplication.getAppointmentSchedules()) {
            if (AppointmentSchedulePurpose.INSPECTION.equals(inspectionSchedule.getAppointmentScheduleCommon().getPurpose())) {
                purposeInsList.add(inspectionSchedule.getAppointmentScheduleCommon().getPurpose().name());
            }
        }
        Assignment appvrAssignment = bpaWorkFlowService.getApproverAssignment(currentState.getOwnerPosition());
        if (currentState.getOwnerUser() != null) {
            List<Assignment> assignments = bpaWorkFlowService.getAssignmentByPositionAndUserAsOnDate(
                    currentState.getOwnerPosition().getId(), currentState.getOwnerUser().getId(),
                    currentState.getLastModifiedDate());
            if (!assignments.isEmpty())
                appvrAssignment = assignments.get(0);
        }
        if (appvrAssignment == null)
            appvrAssignment = bpaWorkFlowService
                    .getAssignmentsByPositionAndDate(currentState.getOwnerPosition().getId(), new Date()).get(0);

        boolean hasInspectionStatus = hasInspectionStatus(currentStatus);
        boolean hasInspectionPendingAction = FWD_TO_OVRSR_FOR_FIELD_INS.equalsIgnoreCase(pendingAction);
        boolean isAfterTSInspection = DESIGNATION_OVERSEER.equals(appvrAssignment.getDesignation().getName())
                && APPLICATION_STATUS_TS_INS.equalsIgnoreCase(currentStatus);


        if (hasInspectionStatus && hasInspectionPendingAction && purposeInsList.isEmpty())
            mode = "newappointment";
        else if (hasInspectionPendingAction && hasInspectionStatus && inspectionApplication.getInspections().isEmpty()) {
            mode = "captureInspection";
            model.addAttribute("isInspnRescheduleEnabled",true);
            scheduleType = AppointmentSchedulePurpose.INSPECTION;
        } else if ((hasInspectionPendingAction && hasInspectionStatus)
                || isAfterTSInspection && !inspectionApplication.getInspections().isEmpty())
            mode = "modifyInspection";
        else if(BpaConstants.FORWARDED_TO_CLERK.equals(inspectionApplication.getState().getNextAction()))
            model.addAttribute("createlettertoparty", true);
        else if (FWD_TO_AE_FOR_APPROVAL.equalsIgnoreCase(pendingAction)
                && !inspectionApplication.getInspections().isEmpty())
            mode = "initiatedForApproval";

        // To show/hide TS inspection required checkbox
        if (!inspectionApplication.getInspections().isEmpty()
                && ((hasInspectionStatus && hasInspectionPendingAction)
                        || (FIELD_INSPECTION_COMPLETED.equalsIgnoreCase(currentStateValue)
                                && APPLICATION_STATUS_FIELD_INS.equalsIgnoreCase(currentStatus))))
            model.addAttribute("isTSInspectionRequired", false);

        if (mode == null)
            mode = "edit";

        model.addAttribute("scheduleType", scheduleType);
        model.addAttribute("mode", mode);
    }

    private boolean hasInspectionStatus(final String status) {
        return APPLICATION_STATUS_DOC_VERIFIED.equalsIgnoreCase(status)
                || BpaConstants.INITIATEINSPECTION.equalsIgnoreCase(status);
    }
    
    
    
    private void loadCommonApplicationDetails(Model model, InspectionApplication inspectionApplication) {
    	model.addAttribute("inspectionList", inspectionService.findByInspectionApplicationOrderByIdAsc(inspectionApplication));
        model.addAttribute("lettertopartylist", letterToPartyService.findByInspectionApplicationOrderByIdDesc(inspectionApplication));
        model.addAttribute(APPLICATION_HISTORY,
                workflowHistoryService.getHistoryForInspection(inspectionApplication.getAppointmentSchedules(), inspectionApplication.getCurrentState(), inspectionApplication.getStateHistory()));
   }
    
    private void loadFormData(final Model model, final PermitInspectionApplication permitInspectionApplication) {
        InspectionApplication inspectionApplication = permitInspectionApplication.getInspectionApplication();
        model.addAttribute("stateType", inspectionApplication.getClass().getSimpleName());
        final WorkflowContainer workflowContainer = new WorkflowContainer();
       
            model.addAttribute(ADDITIONALRULE, BpaConstants.INSPECTIONAPPLICATION);
            workflowContainer.setAdditionalRule(BpaConstants.INSPECTIONAPPLICATION);

            if (inspectionApplication.getState() != null
                    && inspectionApplication.getState().getValue().equalsIgnoreCase(BpaConstants.INITIATEINSPECTION)) {
                workflowContainer.setPendingActions(inspectionApplication.getState().getNextAction());
            }

            // Town surveyor workflow
            if (WF_TS_INSPECTION_INITIATED.equalsIgnoreCase(inspectionApplication.getStatus().getCode())) {
                workflowContainer.setPendingActions(WF_TS_APPROVAL_PENDING);
                model.addAttribute("captureTSRemarks", true);
            } else if (APPLICATION_STATUS_TS_INS.equalsIgnoreCase(inspectionApplication.getStatus().getCode())) {
                Assignment approverAssignment = bpaWorkFlowService
                        .getApproverAssignment(inspectionApplication.getCurrentState().getOwnerPosition());
                if (inspectionApplication.getCurrentState().getOwnerUser() != null) {
                    List<Assignment> assignments = bpaWorkFlowService.getAssignmentByPositionAndUserAsOnDate(
                    		inspectionApplication.getCurrentState().getOwnerPosition().getId(),
                    		inspectionApplication.getCurrentState().getOwnerUser().getId(),
                    		inspectionApplication.getCurrentState().getLastModifiedDate());
                    if (!assignments.isEmpty())
                        approverAssignment = assignments.get(0);
                }
                if (approverAssignment == null)
                    approverAssignment = bpaWorkFlowService
                            .getAssignmentsByPositionAndDate(inspectionApplication.getCurrentState().getOwnerPosition().getId(), new Date())
                            .get(0);
                if (DESIGNATION_AE.equals(approverAssignment.getDesignation().getName())) {
                    workflowContainer.setPendingActions(FWD_TO_AE_AFTER_TS_INSP);
                } else if (DESIGNATION_OVERSEER.equals(approverAssignment.getDesignation().getName())) {
                    workflowContainer.setPendingActions(FWD_TO_OVERSEER_AFTER_TS_INSPN);
                }
                model.addAttribute("captureTSRemarks", false);
            }
        
        prepareWorkflow(model, inspectionApplication, workflowContainer);
        model.addAttribute("pendingActions", workflowContainer.getPendingActions());
        model.addAttribute(AMOUNT_RULE, workflowContainer.getAmountRule());
        model.addAttribute("currentState", inspectionApplication.getCurrentState().getValue());
        model.addAttribute("inspectionApplication", inspectionApplication);
        model.addAttribute("workFlowBoundary", bpaUtils.getBoundaryForWorkflow(permitInspectionApplication.getApplication().getSiteDetail().get(0)).getId());
        model.addAttribute("electionBoundary", permitInspectionApplication.getApplication().getSiteDetail().get(0).getElectionBoundary() != null
                ? permitInspectionApplication.getApplication().getSiteDetail().get(0).getElectionBoundary().getId()
                : null);
        model.addAttribute("electionBoundaryName", permitInspectionApplication.getApplication().getSiteDetail().get(0).getElectionBoundary() != null
                ? permitInspectionApplication.getApplication().getSiteDetail().get(0).getElectionBoundary().getName()
                : "");
        model.addAttribute("revenueBoundaryName", permitInspectionApplication.getApplication().getSiteDetail().get(0).getAdminBoundary() != null
                ? permitInspectionApplication.getApplication().getSiteDetail().get(0).getAdminBoundary().getName()
                : "");
        model.addAttribute("bpaPrimaryDept", bpaUtils.getAppconfigValueByKeyNameForDefaultDept());
        Map<String, Object> attributes = model.asMap();
        List<String> actions = Collections.emptyList();
        if (!attributes.isEmpty())
            actions = attributes.get("validActionList") == null ? Collections.emptyList()
                    : (List<String>) attributes.get("validActionList");       
        	
        prepareActions(model, inspectionApplication);
    }
}