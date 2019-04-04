/*
 * eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 * accountability and the service delivery of the government  organizations.
 *
 *  Copyright (C) <2018>  eGovernments Foundation
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

package org.egov.bpa.web.controller.transaction.occupancy;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_HISTORY;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_APPROVED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_DOC_VERIFIED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_FIELD_INS;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_NOCUPDATED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_REGISTERED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_REJECTED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_RESCHEDULED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_SCHEDULED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_TS_INS;
import static org.egov.bpa.utils.BpaConstants.CREATE_ADDITIONAL_RULE_CREATE_OC;
import static org.egov.bpa.utils.BpaConstants.DESIGNATION_AE;
import static org.egov.bpa.utils.BpaConstants.DESIGNATION_OVERSEER;
import static org.egov.bpa.utils.BpaConstants.DISCLIMER_MESSAGE_ONSAVE;
import static org.egov.bpa.utils.BpaConstants.FIELD_INSPECTION_COMPLETED;
import static org.egov.bpa.utils.BpaConstants.FORWARDED_TO_CLERK;
import static org.egov.bpa.utils.BpaConstants.FORWARDED_TO_NOC_UPDATE;
import static org.egov.bpa.utils.BpaConstants.FWDINGTOLPINITIATORPENDING;
import static org.egov.bpa.utils.BpaConstants.FWD_TO_AE_AFTER_TS_INSP;
import static org.egov.bpa.utils.BpaConstants.FWD_TO_AE_FOR_APPROVAL;
import static org.egov.bpa.utils.BpaConstants.FWD_TO_AE_FOR_FIELD_ISPECTION;
import static org.egov.bpa.utils.BpaConstants.FWD_TO_OVERSEER_AFTER_TS_INSPN;
import static org.egov.bpa.utils.BpaConstants.FWD_TO_OVRSR_FOR_FIELD_INS;
import static org.egov.bpa.utils.BpaConstants.GENERATEREJECTNOTICE;
import static org.egov.bpa.utils.BpaConstants.GENERATE_OCCUPANCY_CERTIFICATE;
import static org.egov.bpa.utils.BpaConstants.OCREJECTIONFILENAME;
import static org.egov.bpa.utils.BpaConstants.WF_APPROVE_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_DOC_SCRUTINY_SCHEDLE_PEND;
import static org.egov.bpa.utils.BpaConstants.WF_DOC_VERIFY_PEND;
import static org.egov.bpa.utils.BpaConstants.WF_INITIATE_REJECTION_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_INIT_AUTO_RESCHDLE;
import static org.egov.bpa.utils.BpaConstants.WF_REJECT_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_REVERT_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_SAVE_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_TS_INSPECTION_INITIATED;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.egov.bpa.master.service.PermitConditionsService;
import org.egov.bpa.transaction.entity.WorkflowBean;
import org.egov.bpa.transaction.entity.enums.AppointmentSchedulePurpose;
import org.egov.bpa.transaction.entity.enums.ChecklistValues;
import org.egov.bpa.transaction.entity.enums.ConditionType;
import org.egov.bpa.transaction.entity.oc.OCAppointmentSchedule;
import org.egov.bpa.transaction.entity.oc.OCInspection;
import org.egov.bpa.transaction.entity.oc.OCLetterToParty;
import org.egov.bpa.transaction.entity.oc.OCSlot;
import org.egov.bpa.transaction.entity.oc.OccupancyCertificate;
import org.egov.bpa.transaction.entity.oc.OccupancyFee;
import org.egov.bpa.transaction.notice.OccupancyCertificateNoticesFormat;
import org.egov.bpa.transaction.notice.impl.OccupancyCertificateFormatImpl;
import org.egov.bpa.transaction.notice.impl.OccupancyRejectionFormatImpl;
import org.egov.bpa.transaction.service.BpaDcrService;
import org.egov.bpa.transaction.service.oc.OCInspectionService;
import org.egov.bpa.transaction.service.oc.OCLetterToPartyService;
import org.egov.bpa.transaction.service.oc.OCNoticeConditionsService;
import org.egov.bpa.transaction.service.oc.OccupancyCertificateService;
import org.egov.bpa.transaction.service.oc.OccupancyFeeService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.bpa.utils.OccupancyCertificateUtils;
import org.egov.bpa.web.controller.transaction.BpaGenericApplicationController;
import org.egov.eis.entity.Assignment;
import org.egov.eis.service.PositionMasterService;
import org.egov.eis.web.contract.WorkflowContainer;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.custom.CustomImplProvider;
import org.egov.infra.reporting.engine.ReportOutput;
import org.egov.infra.utils.DateUtils;
import org.egov.infra.workflow.entity.State;
import org.egov.infra.workflow.entity.StateHistory;
import org.egov.pims.commons.Designation;
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
@RequestMapping(value = "/application/occupancy-certificate")
public class UpdateOccupancyCertificateController extends BpaGenericApplicationController {

    public static final String OCCUPANCY_CERTIFICATE_VIEW = "occupancy-certificate-view";
    public static final String OC_CREATE_DOCUMENT_SCRUTINY_FORM = "oc-create-document-scrutiny-form";
    public static final String OCCUPANCY_CERTIFICATE_RESULT = "occupancy-certificate-result";
    public static final String BPA_APPLICATION = "bpaApplication";
    public static final String OCCUPANCY_CERTIFICATE = "occupancyCertificate";
    public static final String REDIRECT_APPLICATION_OC_SUCCESS = "redirect:/application/occupancy-certificate/success/";
    private static final String AMOUNT_RULE = "amountRule";
    private static final String WORK_FLOW_ACTION = "workFlowAction";
    private static final String MSG_UPDATE_FORWARD_REGISTRATION = "msg.update.forward.registration";
    private static final String MSG_REJECT_FORWARD_REGISTRATION = "msg.reject.forward.registration";
    private static final String MSG_INITIATE_REJECTION = "msg.initiate.reject";
    private static final String MESSAGE = "message";
    private static final String COMMON_ERROR = "common-error";
    private static final String ADDITIONALRULE = "additionalRule";
    private static final String APPRIVALPOSITION = "approvalPosition";
    private static final String PDFEXTN = ".pdf";

    @Autowired
    private PositionMasterService positionMasterService;
    @Autowired
    private OccupancyCertificateService occupancyCertificateService;
    @Autowired
    private OCInspectionService ocInspectionService;
    @Autowired
    private OCLetterToPartyService ocLetterToPartyService;
    @Autowired
    private OccupancyCertificateUtils occupancyCertificateUtils;
    @Autowired
    private PermitConditionsService permitConditionsService;
    @Autowired
    private OCNoticeConditionsService ocNoticeConditionsService;
    @Autowired
    private CustomImplProvider specificNoticeService;
    @Autowired
    private BpaDcrService bpaDcrService;
    @Autowired
    protected OccupancyFeeService occupancyFeeService;

    @GetMapping("/update/{applicationNumber}")
    public String editOccupancyCertificateApplication(@PathVariable final String applicationNumber, final Model model,
            final HttpServletRequest request) {
        OccupancyCertificate oc = occupancyCertificateService.findByApplicationNumber(applicationNumber);
        setCityName(model, request);
        prepareFormData(oc, model);
        loadData(oc, model);
        getActionsForOCApplication(model, oc);
        if (oc.getState().getNextAction().equalsIgnoreCase(FORWARDED_TO_CLERK))
            return OCCUPANCY_CERTIFICATE_VIEW;
        else if (oc.getState() != null
                && oc.getState().getNextAction().equalsIgnoreCase(WF_DOC_SCRUTINY_SCHEDLE_PEND)
                || oc.getState().getNextAction().equalsIgnoreCase(WF_DOC_VERIFY_PEND)
                || oc.getState().getNextAction().equalsIgnoreCase(WF_INIT_AUTO_RESCHDLE))
            return "oc-document-scrutiny-form";

        return OCCUPANCY_CERTIFICATE_VIEW;
    }

    @GetMapping("/document-scrutiny/{applicationNumber}")
    public String documentScrutinyForm(final Model model, @PathVariable final String applicationNumber) {
        OccupancyCertificate oc = occupancyCertificateService.findByApplicationNumber(applicationNumber);
        if (validateOnDocumentScrutiny(model, oc.getStatus()) || checkIsRescheduledOnScrutiny(model, oc)) {
            return COMMON_ERROR;
        }
        prepareFormData(oc, model);
        prepareCommonModelAttribute(model, oc.isCitizenAccepted());
        loadData(oc, model);
        getActionsForOCApplication(model, oc);
        buildRejectionReasons(model, oc);
        model.addAttribute("showDcrDocuments",
                bpaDcrService.isEdcrIntegrationRequireByService(oc.getParent().getServiceType().getCode()));
        model.addAttribute("documentScrutinyValues", ChecklistValues.values());
        model.addAttribute("loginUser", securityUtils.getCurrentUser());
        getDcrDocumentsUploadMode(model);
        model.addAttribute(APPLICATION_HISTORY,
                workflowHistoryService.getHistoryForOC(oc.getAppointmentSchedules(), oc.getCurrentState(), oc.getStateHistory()));
        return OC_CREATE_DOCUMENT_SCRUTINY_FORM;
    }

    private Boolean checkIsRescheduledOnScrutiny(final Model model, final OccupancyCertificate oc) {
        Optional<OCSlot> actvSltApp = oc.getOcSlots().stream()
                .reduce((slotApp1, slotApp2) -> slotApp2);
        if (actvSltApp.isPresent()
                && actvSltApp.get().getSlotDetail().getSlot().getAppointmentDate().after(new Date())) {
            model.addAttribute(MESSAGE, messageSource.getMessage("msg.validate.doc.scrutiny", new String[] {
                    oc.getApplicationNumber(), DateUtils.getDefaultFormattedDate(
                            actvSltApp.get().getSlotDetail().getSlot().getAppointmentDate()) },
                    LocaleContextHolder.getLocale()));
            return true;
        } else
            return false;
    }

    private void getActionsForOCApplication(Model model, OccupancyCertificate oc) {
        State<Position> currentState = oc.getCurrentState();
        String currentStatus = oc.getStatus().getCode();
        String currentStateValue = currentState.getValue();
        String pendingAction = currentState.getNextAction();
        String mode = StringUtils.EMPTY;
        AppointmentSchedulePurpose scheduleType = null;
        List<String> purposeInsList = new ArrayList<>();
        for (OCAppointmentSchedule ocSchedule : oc.getAppointmentSchedules()) {
            if (AppointmentSchedulePurpose.INSPECTION.equals(ocSchedule.getAppointmentScheduleCommon().getPurpose())) {
                purposeInsList.add(ocSchedule.getAppointmentScheduleCommon().getPurpose().name());
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

        if (occupancyCertificateUtils.isOCInspectionSchedulingIntegrationRequired()
                && hasInspectionStatus && hasInspectionPendingAction && purposeInsList.isEmpty())
            mode = "newappointment";
        else if (hasInspectionPendingAction && hasInspectionStatus && oc.getInspections().isEmpty()) {
            mode = "captureInspection";
            model.addAttribute("isInspnRescheduleEnabled",
                    occupancyCertificateUtils.isOCInspectionSchedulingIntegrationRequired());
            scheduleType = AppointmentSchedulePurpose.INSPECTION;
        } else if ((hasInspectionPendingAction && hasInspectionStatus)
                || isAfterTSInspection && !oc.getInspections().isEmpty())
            mode = "captureAdditionalInspection";
        else if (FORWARDED_TO_NOC_UPDATE.equalsIgnoreCase(pendingAction)
                && APPLICATION_STATUS_FIELD_INS.equalsIgnoreCase(currentStatus))
            model.addAttribute("showUpdateNoc", true);
        else if (FWD_TO_AE_FOR_APPROVAL.equalsIgnoreCase(pendingAction)
                && !oc.getInspections().isEmpty())
            mode = "initiatedForApproval";

        // To show/hide TS inspection required checkbox
        if (!oc.getInspections().isEmpty()
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
                || APPLICATION_STATUS_REGISTERED.equalsIgnoreCase(status);
    }

    private void loadData(OccupancyCertificate oc, Model model) {
        List<OCInspection> inspectionList = ocInspectionService.findByOcOrderByIdAsc(oc);
        int i = 0;
        for (OCInspection ocInspection : inspectionList) {
            if (ocInspection.getInspection().getInspectedBy().equals(securityUtils.getCurrentUser())) {
                model.addAttribute("showEditInspection", true);
                model.addAttribute("showEditInspectionIndex", String.valueOf(i));
                break;
            }
            i++;
        }
        model.addAttribute("inspectionList", inspectionList);
        model.addAttribute("letterToPartyList", ocLetterToPartyService.findAllByOC(oc));
        if ((FWD_TO_AE_FOR_FIELD_ISPECTION.equals(oc.getState().getNextAction())
                || APPLICATION_STATUS_FIELD_INS.equals(oc.getStatus().getCode())
                || APPLICATION_STATUS_NOCUPDATED.equalsIgnoreCase(oc.getStatus().getCode()))) {
            model.addAttribute("createlettertoparty", true);
        }

        final WorkflowContainer workflowContainer = new WorkflowContainer();
        if (APPLICATION_STATUS_NOCUPDATED.equals(oc.getStatus().getCode())
                || APPLICATION_STATUS_APPROVED.equals(oc.getStatus().getCode())) {
            workflowContainer.setAmountRule(bpaWorkFlowService.getAmountRuleByServiceTypeForOc(oc));
        }
        model.addAttribute(ADDITIONALRULE, CREATE_ADDITIONAL_RULE_CREATE_OC);
        workflowContainer.setAdditionalRule(CREATE_ADDITIONAL_RULE_CREATE_OC);
        workflowContainer.setPendingActions(oc.getState().getNextAction());

        // Town surveyor workflow
        if (WF_TS_INSPECTION_INITIATED.equalsIgnoreCase(oc.getStatus().getCode())) {
            model.addAttribute("captureTSRemarks", true);
        } else if (APPLICATION_STATUS_TS_INS.equalsIgnoreCase(oc.getStatus().getCode())) {
            State<Position> currentState = oc.getCurrentState();
            Assignment approverAssignment = bpaWorkFlowService.getApproverAssignment(currentState.getOwnerPosition());
            if (oc.getCurrentState().getOwnerUser() != null) {
                List<Assignment> assignments = bpaWorkFlowService.getAssignmentByPositionAndUserAsOnDate(
                        currentState.getOwnerPosition().getId(), currentState.getOwnerUser().getId(),
                        currentState.getLastModifiedDate());
                if (!assignments.isEmpty())
                    approverAssignment = assignments.get(0);
            }
            if (approverAssignment == null)
                approverAssignment = bpaWorkFlowService
                        .getAssignmentsByPositionAndDate(currentState.getOwnerPosition().getId(), new Date()).get(0);
            if (DESIGNATION_AE.equals(approverAssignment.getDesignation().getName())) {
                workflowContainer.setPendingActions(FWD_TO_AE_AFTER_TS_INSP);
            } else if (DESIGNATION_OVERSEER.equals(approverAssignment.getDesignation().getName())) {
                workflowContainer.setPendingActions(FWD_TO_OVERSEER_AFTER_TS_INSPN);
            }
            model.addAttribute("captureTSRemarks", false);
        }

        prepareWorkflow(model, oc, workflowContainer);
        model.addAttribute("pendingActions", workflowContainer.getPendingActions());
        model.addAttribute("currentState", oc.getCurrentState().getValue());
        model.addAttribute(AMOUNT_RULE, workflowContainer.getAmountRule());
        model.addAttribute("workFlowBoundary", bpaUtils.getBoundaryForWorkflow(oc.getParent().getSiteDetail().get(0)).getId());
        model.addAttribute("electionBoundary", oc.getParent().getSiteDetail().get(0).getElectionBoundary() != null
                ? oc.getParent().getSiteDetail().get(0).getElectionBoundary().getId()
                : null);
        model.addAttribute("electionBoundaryName", oc.getParent().getSiteDetail().get(0).getElectionBoundary() != null
                ? oc.getParent().getSiteDetail().get(0).getElectionBoundary().getName()
                : "");
        model.addAttribute("revenueBoundaryName", oc.getParent().getSiteDetail().get(0).getAdminBoundary() != null
                ? oc.getParent().getSiteDetail().get(0).getAdminBoundary().getName()
                : "");
        model.addAttribute("bpaPrimaryDept", bpaUtils.getAppconfigValueByKeyNameForDefaultDept());
        model.addAttribute("isFeeCollected", bpaUtils.checkAnyTaxIsPendingToCollect(oc.getDemand()));
        model.addAttribute("loginUser", securityUtils.getCurrentUser());
        buildReceiptDetails(oc.getDemand().getEgDemandDetails(), oc.getReceipts());
        model.addAttribute(APPLICATION_HISTORY,
                workflowHistoryService.getHistoryForOC(oc.getAppointmentSchedules(), oc.getCurrentState(), oc.getStateHistory()));
        model.addAttribute(BPA_APPLICATION, oc.getParent());
        model.addAttribute(OCCUPANCY_CERTIFICATE, oc);
        buildRejectionReasons(model, oc);
    }

    private void prepareFormData(final OccupancyCertificate oc, final Model model) {
        model.addAttribute("stateType", oc.getClass().getSimpleName());
        model.addAttribute(ADDITIONALRULE, CREATE_ADDITIONAL_RULE_CREATE_OC);
        model.addAttribute("currentState", oc.getCurrentState() == null ? "" : oc.getCurrentState().getValue());
        model.addAttribute("feeCalculationMode",bpaUtils.getOCFeeCalculationMode());

    }

    private void setCityName(final Model model, final HttpServletRequest request) {
        if (request.getSession().getAttribute("cityname") != null)
            model.addAttribute("cityName", request.getSession().getAttribute("cityname"));
    }

    @PostMapping("/document-scrutiny/{applicationNumber}")
    public String createDocumentScrutinyForOC(@Valid @ModelAttribute final OccupancyCertificate occupancyCertificate,
            @PathVariable final String applicationNumber, final HttpServletRequest request,
            final Model model, final BindingResult errors, final RedirectAttributes redirectAttributes) {
        if (errors.hasErrors())
            return OC_CREATE_DOCUMENT_SCRUTINY_FORM;

        List<Designation> loginUserDesignations = Collections.emptyList();
        List<Assignment> loginUserAssignments = bpaWorkFlowService
                .getAllActiveAssignmentsForUser(securityUtils.getCurrentUser().getId());
        if (!loginUserAssignments.isEmpty())
            loginUserDesignations = bpaWorkFlowService.getAllActiveAssignmentsForUser(securityUtils.getCurrentUser().getId())
                    .stream().map(Assignment::getDesignation).collect(Collectors.toList());
        if (!loginUserDesignations.isEmpty() && !loginUserDesignations
                .contains(occupancyCertificate.getCurrentState().getOwnerPosition().getDeptDesig().getDesignation())) {
            model.addAttribute(MESSAGE, messageSource.getMessage("msg.application.scrutiny.processed", new String[] {},
                    LocaleContextHolder.getLocale()));
            return COMMON_ERROR;
        }

        WorkflowBean wfBean = new WorkflowBean();
        wfBean.setWorkFlowAction(request.getParameter(WORK_FLOW_ACTION));
        Position pos = null;
        Long approvalPosition = Long.valueOf(request.getParameter(APPRIVALPOSITION));
        wfBean.setApproverPositionId(approvalPosition);
        occupancyCertificate.getDocumentScrutinies().get(0).getDocScrutiny().getDocumentScrutinyChecklists().forEach(
                docScrutiny -> docScrutiny
                        .setDocumentScrutiny(occupancyCertificate.getDocumentScrutinies().get(0).getDocScrutiny()));
        OccupancyCertificate ocResponse = occupancyCertificateService.update(occupancyCertificate, wfBean);
        bpaUtils.updatePortalUserinbox(ocResponse, null);

        if (null != approvalPosition)
            pos = positionMasterService.getPositionById(approvalPosition);

        if (null == approvalPosition)
            pos = positionMasterService.getPositionById(ocResponse.getCurrentState().getOwnerPosition().getId());

        User user = workflowHistoryService
                .getUserPositionByPassingPosition(approvalPosition == null ? pos.getId() : approvalPosition);
        String message;
        if (WF_INITIATE_REJECTION_BUTTON.equalsIgnoreCase(wfBean.getWorkFlowAction())) {
            User userObj = workflowHistoryService
                    .getUserPositionByPassingPosition(occupancyCertificate.getCurrentState().getOwnerPosition().getId());
            message = getMessageOnRejectionInitiation(wfBean.getApproverComments(), occupancyCertificate, userObj,
                    MSG_INITIATE_REJECTION, occupancyCertificate.getCurrentState().getOwnerPosition());
        } else {
            message = messageSource.getMessage("msg.update.forward.documentscrutiny", new String[] {
                    user == null ? ""
                            : user.getUsername().concat("~")
                                    .concat(getDesinationNameByPosition(pos)),
                    ocResponse.getApplicationNumber() }, LocaleContextHolder.getLocale());
            message = message.concat(DISCLIMER_MESSAGE_ONSAVE);
        }

        redirectAttributes.addFlashAttribute(MESSAGE, message);
        return REDIRECT_APPLICATION_OC_SUCCESS + ocResponse.getApplicationNumber();
    }

    @PostMapping("/update-submit")
    public String updateOccupancyCertificateApplication(@Valid @ModelAttribute final OccupancyCertificate occupancyCertificate,
            final HttpServletRequest request, final Model model,
            final BindingResult errors, final RedirectAttributes redirectAttributes,
            @RequestParam final BigDecimal amountRule) {
        if (errors.hasErrors())
            return OCCUPANCY_CERTIFICATE_VIEW;

        Position ownerPosition = occupancyCertificate.getCurrentState().getOwnerPosition();
        if (validateLoginUserAndOwnerIsSame(model, securityUtils.getCurrentUser(), ownerPosition))
            return COMMON_ERROR;

        WorkflowBean wfBean = new WorkflowBean();
        wfBean.setWorkFlowAction(request.getParameter(WORK_FLOW_ACTION));
        wfBean.setAmountRule(amountRule);
        Position pos = null;
        Long approvalPosition = null;
        String feeCalculationMode = bpaUtils.getOCFeeCalculationMode();

        if (WF_APPROVE_BUTTON.equalsIgnoreCase(wfBean.getWorkFlowAction())
                && feeCalculationMode.equalsIgnoreCase(BpaConstants.MANUAL)) {
            List<OccupancyFee> permitFeeList = occupancyFeeService
                    .getOCFeeListByApplicationId(occupancyCertificate.getId());
            if (permitFeeList.size() == 0 || permitFeeList == null) {
                model.addAttribute("feeNotDefined", "Set the minimal fee using modify fee button and proceed further");
                setCityName(model, request);
                prepareFormData(occupancyCertificate, model);
                loadData(occupancyCertificate, model);
                getActionsForOCApplication(model, occupancyCertificate);
                return OCCUPANCY_CERTIFICATE_VIEW;
            }
        }
        if (StringUtils.isNotBlank(request.getParameter(APPRIVALPOSITION))
                && !WF_REJECT_BUTTON.equalsIgnoreCase(wfBean.getWorkFlowAction())
                && !GENERATEREJECTNOTICE.equalsIgnoreCase(wfBean.getWorkFlowAction())) {
            approvalPosition = Long.valueOf(request.getParameter(APPRIVALPOSITION));
        }

        if (WF_TS_INSPECTION_INITIATED.equalsIgnoreCase(occupancyCertificate.getStatus().getCode())) {
            pos = positionMasterService.getPositionById(bpaWorkFlowService.getTownSurveyorInspnInitiator(
                    occupancyCertificate.getStateHistory(), occupancyCertificate.getCurrentState()));
            approvalPosition = positionMasterService
                    .getPositionById(bpaWorkFlowService.getTownSurveyorInspnInitiator(occupancyCertificate.getStateHistory(),
                            occupancyCertificate.getCurrentState()))
                    .getId();
        } else if (WF_REVERT_BUTTON.equalsIgnoreCase(wfBean.getWorkFlowAction())) {
            pos = occupancyCertificate.getCurrentState().getPreviousOwner();
            approvalPosition = occupancyCertificate.getCurrentState().getPreviousOwner().getId();
        } else if (FWDINGTOLPINITIATORPENDING.equalsIgnoreCase(occupancyCertificate.getState().getNextAction())) {
            List<OCLetterToParty> letterToParties = ocLetterToPartyService.findAllByOC(occupancyCertificate);
            StateHistory<Position> stateHistory = bpaWorkFlowService.getStateHistoryToGetLPInitiator(
                    occupancyCertificate.getStateHistory(),
                    letterToParties.get(0).getLetterToParty().getStateForOwnerPosition());
            approvalPosition = stateHistory.getOwnerPosition().getId();
        } else if (StringUtils.isNotBlank(request.getParameter(APPRIVALPOSITION))
                && !WF_REJECT_BUTTON.equalsIgnoreCase(wfBean.getWorkFlowAction())
                && !GENERATEREJECTNOTICE.equalsIgnoreCase(wfBean.getWorkFlowAction())) {
            approvalPosition = Long.valueOf(request.getParameter(APPRIVALPOSITION));
        } else if (WF_REJECT_BUTTON.equalsIgnoreCase(wfBean.getWorkFlowAction())) {
            pos = bpaWorkFlowService.getApproverPositionOfElectionWardByCurrentStateForOC(occupancyCertificate,
                    BpaConstants.REJECT_BY_CLERK);
            approvalPosition = pos.getId();
        }

        wfBean.setApproverPositionId(approvalPosition);
        wfBean.setApproverComments(occupancyCertificate.getApprovalComent());
        if (occupancyCertificate.getState().getValue() != null)
            wfBean.setCurrentState(occupancyCertificate.getState().getValue());
        OccupancyCertificate ocResponse = occupancyCertificateService.update(occupancyCertificate, wfBean);
        bpaUtils.updatePortalUserinbox(ocResponse, null);
        if (null != approvalPosition) {
            pos = positionMasterService.getPositionById(approvalPosition);
        }
        if (null == approvalPosition) {
            pos = positionMasterService.getPositionById(ocResponse.getCurrentState().getOwnerPosition().getId());
        }
        User user = workflowHistoryService
                .getUserPositionByPassingPosition(approvalPosition == null ? pos.getId() : approvalPosition);
        String message;
        if (WF_INITIATE_REJECTION_BUTTON.equalsIgnoreCase(wfBean.getWorkFlowAction())) {
            User userObj = workflowHistoryService
                    .getUserPositionByPassingPosition(occupancyCertificate.getCurrentState().getOwnerPosition().getId());
            message = getMessageOnRejectionInitiation(wfBean.getApproverComments(), occupancyCertificate, userObj,
                    MSG_INITIATE_REJECTION, occupancyCertificate.getCurrentState().getOwnerPosition());
        } else if (WF_REJECT_BUTTON.equalsIgnoreCase(wfBean.getWorkFlowAction())) {
            message = getMessageOnRejectionInitiation(wfBean.getApproverComments(), occupancyCertificate, user,
                    MSG_REJECT_FORWARD_REGISTRATION, pos);
        } else if (WF_SAVE_BUTTON.equalsIgnoreCase(wfBean.getWorkFlowAction())) {
            message = messageSource.getMessage("msg.noc.update.success", new String[] {}, LocaleContextHolder.getLocale());
        } else {
            message = messageSource.getMessage(MSG_UPDATE_FORWARD_REGISTRATION, new String[] {
                    user == null ? ""
                            : user.getUsername().concat("~")
                                    .concat(getDesinationNameByPosition(pos)),
                    ocResponse.getApplicationNumber() }, LocaleContextHolder.getLocale());
        }

        redirectAttributes.addFlashAttribute(MESSAGE, message);
        if (isNotBlank(wfBean.getWorkFlowAction())
                && GENERATE_OCCUPANCY_CERTIFICATE.equalsIgnoreCase(wfBean.getWorkFlowAction())) {
            OccupancyCertificateNoticesFormat ocNoticeFeature = (OccupancyCertificateNoticesFormat) specificNoticeService
                    .find(OccupancyCertificateFormatImpl.class, specificNoticeService.getCityDetails());
            ReportOutput reportOutput = ocNoticeFeature
                    .generateNotice(
                            occupancyCertificateService.findByApplicationNumber(occupancyCertificate.getApplicationNumber()));
            ocSmsAndEmailService.sendSmsAndEmailOnPermitOrderGeneration(occupancyCertificate, reportOutput);

            return "redirect:/application/occupancy-certificate/generate-occupancy-certificate/"
                    + occupancyCertificate.getApplicationNumber();
        } else if (isNotBlank(wfBean.getWorkFlowAction()) && GENERATEREJECTNOTICE.equalsIgnoreCase(wfBean.getWorkFlowAction())) {
            OccupancyCertificateNoticesFormat ocNoticeFeature = (OccupancyCertificateNoticesFormat) specificNoticeService
                    .find(OccupancyRejectionFormatImpl.class, specificNoticeService.getCityDetails());
            ReportOutput reportOutput = ocNoticeFeature
                    .generateNotice(
                            occupancyCertificateService.findByApplicationNumber(occupancyCertificate.getApplicationNumber()));
            ocSmsAndEmailService.sendSMSAndEmail(occupancyCertificate, reportOutput, OCREJECTIONFILENAME + PDFEXTN);
            return "redirect:/application/occupancy-certificate/rejectionnotice/" + occupancyCertificate.getApplicationNumber();
        }

        return REDIRECT_APPLICATION_OC_SUCCESS + ocResponse.getApplicationNumber();
    }

    @GetMapping("/success/{applicationNumber}")
    public String success(@PathVariable final String applicationNumber, final Model model, final HttpServletRequest request) {
        OccupancyCertificate oc = occupancyCertificateService.findByApplicationNumber(applicationNumber);
        buildReceiptDetails(oc.getDemand().getEgDemandDetails(), oc.getReceipts());
        model.addAttribute(APPLICATION_HISTORY,
                workflowHistoryService.getHistoryForOC(oc.getAppointmentSchedules(), oc.getCurrentState(), oc.getStateHistory()));
        model.addAttribute(BPA_APPLICATION, oc.getParent());
        model.addAttribute(OCCUPANCY_CERTIFICATE, oc);
        return OCCUPANCY_CERTIFICATE_RESULT;
    }

    private String getMessageOnRejectionInitiation(String approvalComent, OccupancyCertificate oc, User userObj,
            String msgInitiateRjctn, Position ownerPosition) {
        return messageSource.getMessage(msgInitiateRjctn, new String[] {
                userObj == null ? ""
                        : userObj.getUsername().concat("~")
                                .concat(getDesinationNameByPosition(ownerPosition)),
                oc.getApplicationNumber(), approvalComent }, LocaleContextHolder.getLocale());
    }

    private void buildRejectionReasons(Model model, OccupancyCertificate oc) {
        if (APPLICATION_STATUS_NOCUPDATED.equals(oc.getStatus().getCode())
                || APPLICATION_STATUS_REJECTED.equalsIgnoreCase(oc.getStatus().getCode())
                || APPLICATION_STATUS_SCHEDULED.equalsIgnoreCase(oc.getStatus().getCode())
                || APPLICATION_STATUS_RESCHEDULED.equalsIgnoreCase(oc.getStatus().getCode())
                || (APPLICATION_STATUS_REGISTERED.equalsIgnoreCase(oc.getStatus().getCode())
                        && FORWARDED_TO_CLERK.equalsIgnoreCase(oc.getCurrentState().getNextAction()))) {
            model.addAttribute("showRejectionReasons", true);
			model.addAttribute("additionalRejectionReasons",
					checklistServiceTypeService.findByActiveChecklistAndServiceType(
							oc.getParent().getServiceType().getDescription(), "ADDITIONALREJECTIONREASONS"));
            
            model.addAttribute("rejectionReasons", checklistServiceTypeService.findByActiveChecklistAndServiceType(
							oc.getParent().getServiceType().getDescription(), "OCREJECTIONREASONS"));
            oc.setRejectionReasonsTemp(ocNoticeConditionsService
                    .findAllOcConditionsByOcAndType(oc, ConditionType.OCREJECTIONREASONS));
            oc.setAdditionalRejectReasonsTemp(ocNoticeConditionsService
                    .findAllOcConditionsByOcAndType(oc, ConditionType.ADDITIONALREJECTIONREASONS));
        }
    }
}