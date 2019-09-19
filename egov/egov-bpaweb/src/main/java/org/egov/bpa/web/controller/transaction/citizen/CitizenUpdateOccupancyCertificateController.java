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

package org.egov.bpa.web.controller.transaction.citizen;

import static org.egov.bpa.utils.BpaConstants.APPLICATION_HISTORY;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_APPROVED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_CREATED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_DOC_VERIFIED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_RESCHEDULED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_SCHEDULED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_SUBMITTED;
import static org.egov.bpa.utils.BpaConstants.AUTH_TO_SUBMIT_PLAN;
import static org.egov.bpa.utils.BpaConstants.CREATE_ADDITIONAL_RULE_CREATE_OC;
import static org.egov.bpa.utils.BpaConstants.DISCLIMER_MESSAGE_ONSAVE;
import static org.egov.bpa.utils.BpaConstants.ENABLEONLINEPAYMENT;
import static org.egov.bpa.utils.BpaConstants.WF_CANCELAPPLICATION_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_LBE_SUBMIT_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_NEW_STATE;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.egov.bpa.config.properties.BpaApplicationSettings;
import org.egov.bpa.master.entity.NocConfiguration;
import org.egov.bpa.master.service.NocConfigurationService;
import org.egov.bpa.transaction.entity.ApplicationFloorDetail;
import org.egov.bpa.transaction.entity.BuildingDetail;
import org.egov.bpa.transaction.entity.WorkflowBean;
import org.egov.bpa.transaction.entity.enums.AppointmentSchedulePurpose;
import org.egov.bpa.transaction.entity.enums.NocIntegrationInitiationEnum;
import org.egov.bpa.transaction.entity.enums.NocIntegrationTypeEnum;
import org.egov.bpa.transaction.entity.oc.OCAppointmentSchedule;
import org.egov.bpa.transaction.entity.oc.OCBuilding;
import org.egov.bpa.transaction.entity.oc.OCFloor;
import org.egov.bpa.transaction.entity.oc.OCLetterToParty;
import org.egov.bpa.transaction.entity.oc.OCNocDocuments;
import org.egov.bpa.transaction.entity.oc.OCSlot;
import org.egov.bpa.transaction.entity.oc.OccupancyCertificate;
import org.egov.bpa.transaction.entity.oc.OccupancyNocApplication;
import org.egov.bpa.transaction.service.collection.GenericBillGeneratorService;
import org.egov.bpa.transaction.service.oc.OCAppointmentScheduleService;
import org.egov.bpa.transaction.service.oc.OCLetterToPartyService;
import org.egov.bpa.transaction.service.oc.OcInspectionService;
import org.egov.bpa.transaction.service.oc.OccupancyCertificateNocService;
import org.egov.bpa.transaction.service.oc.OccupancyCertificateService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.bpa.utils.BpaUtils;
import org.egov.bpa.utils.OccupancyCertificateUtils;
import org.egov.bpa.web.controller.transaction.BpaGenericApplicationController;
import org.egov.commons.service.SubOccupancyService;
import org.egov.eis.service.PositionMasterService;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.custom.CustomImplProvider;
import org.egov.infra.persistence.entity.enums.UserType;
import org.egov.infra.utils.DateUtils;
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
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/application/citizen")
public class CitizenUpdateOccupancyCertificateController extends BpaGenericApplicationController {

    public static final String OCCUPANCY_CERTIFICATE_UPDATE = "citizen-occupancy-certificate-update";
    public static final String CITIZEN_OCCUPANCY_CERTIFICATE_VIEW = "citizen-occupancy-certificate-view";
    private static final String ONLINE_PAYMENT_ENABLE = "onlinePaymentEnable";
    private static final String WORK_FLOW_ACTION = "workFlowAction";
    private static final String TRUE = "TRUE";
    private static final String CITIZEN_OR_BUSINESS_USER = "citizenOrBusinessUser";
    private static final String OFFICIAL_NOT_EXISTS = "No officials assigned to process this application.";
    private static final String MSG_PORTAL_FORWARD_REGISTRATION = "msg.portal.forward.registration";
    private static final String MESSAGE = "message";
    private static final String BPAAPPLICATION_CITIZEN = "citizen_suceess";
    private static final String ADDITIONALRULE = "additionalRule";
    private static final String COLLECT_FEE_VALIDATE = "collectFeeValidate";
    @Autowired
    private GenericBillGeneratorService genericBillGeneratorService;
    @Autowired
    private PositionMasterService positionMasterService;
    @Autowired
    private OccupancyCertificateService occupancyCertificateService;
    @Autowired
    private OCAppointmentScheduleService ocAppointmentScheduleService;
    @Autowired
    private OCLetterToPartyService ocLetterToPartyService;
    @Autowired
    protected SubOccupancyService subOccupancyService;
    @Autowired
    private BpaUtils bpaUtils;
    @Autowired
    private CustomImplProvider specificNoticeService;
    @Autowired
    private OccupancyCertificateNocService ocNocService;
    @Autowired
    private NocConfigurationService nocConfigurationService;
    @Autowired
    private OccupancyCertificateUtils occupancyCertificateUtils;
    @Autowired
    private BpaApplicationSettings bpaApplicationSettings;

    @GetMapping("/occupancy-certificate/update/{applicationNumber}")
    public String showOCUpdateForm(@PathVariable final String applicationNumber, final Model model,
            final HttpServletRequest request) {
        OccupancyCertificate oc = occupancyCertificateService.findByApplicationNumber(applicationNumber);
        prepareFormData(model);
        setCityName(model, request);
        prepareFormData(oc, model);
        prepareCommonModelAttribute(model, oc.isCitizenAccepted());
        loadData(oc, model);
        model.addAttribute("occupancyCertificate", oc);
        bpaUtils.loadBoundary(oc.getParent());
        if (APPLICATION_STATUS_CREATED.equals(oc.getStatus().getCode()))
            return OCCUPANCY_CERTIFICATE_UPDATE;
        else {
            model.addAttribute("bpaApplication", oc.getParent());
            return CITIZEN_OCCUPANCY_CERTIFICATE_VIEW;
        }
    }

    @RequestMapping(value = "/occupancy-certificate/comparison-report/{applicationNumber}", method = RequestMethod.GET)
    public String viewApplicationByPermitNumber(final Model model, @PathVariable final String applicationNumber) {
        OccupancyCertificate oc = occupancyCertificateService.findByApplicationNumber(applicationNumber);
        List<OCBuilding> ocBuildings = oc.getBuildings();
        List<BuildingDetail> bpaBuildingDetails = oc.getParent().getBuildingDetail();
        // List<OCComparisonReport> list = OccupancyCertificateUtils.getOcComparisonReport(ocBuildings,bpaBuildingDetails);
        Map<Integer, HashMap<Integer, OCFloor>> ocMap = new HashMap<>();
        Map<Integer, HashMap<Integer, ApplicationFloorDetail>> bpaMap = new HashMap<>();
        for (OCBuilding ocBuilding : ocBuildings) {
            HashMap<Integer, OCFloor> map = new HashMap<Integer, OCFloor>();
            for (OCFloor ocFloor : ocBuilding.getFloorDetails())
                map.put(ocFloor.getFloorNumber(), ocFloor);
            ocMap.put(ocBuilding.getBuildingNumber(), map);
        }
        for (BuildingDetail bpaBuilding : bpaBuildingDetails) {
            HashMap<Integer, ApplicationFloorDetail> map = new HashMap<Integer, ApplicationFloorDetail>();
            for (ApplicationFloorDetail bpaFloor : bpaBuilding.getApplicationFloorDetails())
                map.put(bpaFloor.getFloorNumber(), bpaFloor);
            bpaMap.put(bpaBuilding.getNumber(), map);
        }
        Map<Integer, BigDecimal> ocBuildHeightMap = new HashMap<>();
        Map<Integer, BigDecimal> bpaBuildHeightMap = new HashMap<>();
        for (OCBuilding ocBuilding : ocBuildings)
            ocBuildHeightMap.put(ocBuilding.getBuildingNumber(), ocBuilding.getHeightFromGroundWithOutStairRoom());

        for (BuildingDetail bpaBuilding : bpaBuildingDetails)
            bpaBuildHeightMap.put(bpaBuilding.getNumber(), bpaBuilding.getHeightFromGroundWithOutStairRoom());

        model.addAttribute("ocMap", ocMap);
        model.addAttribute("bpaMap", bpaMap);
        model.addAttribute("ocBuildingHeightMap", ocBuildHeightMap);
        model.addAttribute("bpaBuildingHeightMap", bpaBuildHeightMap);
        return "view-oc-comparison-report";
    }

    private void loadData(OccupancyCertificate oc, Model model) {
        List<OCLetterToParty> ocLetterToParties = ocLetterToPartyService.findAllByOC(oc);
        if (!ocLetterToParties.isEmpty() && ocLetterToParties.get(0).getLetterToParty().getSentDate() != null)
            model.addAttribute("mode", "showLPDetails");
        model.addAttribute("letterToPartyList", ocLetterToParties);
        if (APPLICATION_STATUS_SCHEDULED.equals(oc.getStatus().getCode())
                || APPLICATION_STATUS_RESCHEDULED.equals(oc.getStatus().getCode())
                        && !oc.getRescheduledByCitizen()) {
            model.addAttribute("mode", "showRescheduleToCitizen");
        }
        final OcInspectionService ocInspectionService = (OcInspectionService) specificNoticeService
                .find(OcInspectionService.class, specificNoticeService.getCityDetails());
        model.addAttribute("inspectionList", ocInspectionService.findByOcOrderByIdAsc(oc));
        model.addAttribute("isFeeCollected", bpaUtils.checkAnyTaxIsPendingToCollect(oc.getDemand()));
        if (APPLICATION_STATUS_SUBMITTED.equals(oc.getStatus().getCode())
                || APPLICATION_STATUS_APPROVED.equals(oc.getStatus().getCode())
                        && bpaUtils.checkAnyTaxIsPendingToCollect(oc.getDemand())) {
            model.addAttribute(COLLECT_FEE_VALIDATE, "Please Pay Fees to Process Application");
            String enableOrDisablePayOnline = bpaUtils.getAppconfigValueByKeyName(ENABLEONLINEPAYMENT);
            model.addAttribute(ONLINE_PAYMENT_ENABLE,
                    (enableOrDisablePayOnline.equalsIgnoreCase("YES") ? Boolean.TRUE : Boolean.FALSE));
        } else {
            model.addAttribute(COLLECT_FEE_VALIDATE, "");
        }
        model.addAttribute("subOccupancyList", subOccupancyService.findAll());
        buildAppointmentDetailsOfScrutinyAndInspection(model, oc);
        buildReceiptDetails(oc.getDemand().getEgDemandDetails(), oc.getReceipts());
        model.addAttribute(APPLICATION_HISTORY,
                workflowHistoryService.getHistoryForOC(oc.getAppointmentSchedules(), oc.getCurrentState(), oc.getStateHistory()));

        Map<String, String> nocConfigMap = new HashMap<String, String>();
        Map<String, String> nocAutoMap = new HashMap<String, String>();
        Map<String, String> nocTypeApplMap = new HashMap<String, String>();
        int nocAutoCount = 0;
        List<User> nocAutoUsers = new ArrayList<>();
        List<User> nocUsers = userService.getUsersByTypeAndTenants(UserType.BUSINESS);
        List<OccupancyNocApplication> ocNoc = ocNocService.findByOCApplicationNumber(oc.getApplicationNumber());
        model.addAttribute("isOcApplFeeReq", "NO");
        model.addAttribute("ocApplFeeCollected", "NO");
        if (occupancyCertificateUtils.isApplicationFeeCollectionRequired()) {
            model.addAttribute("isOcApplFeeReq", "YES");
        }
        if (oc.getDemand() != null && oc.getDemand().getAmtCollected().compareTo(oc.getAdmissionfeeAmount()) >= 0) {
            model.addAttribute("ocApplFeeCollected", "YES");
        }
        Map<String, String> edcrNocMandatory = ocNocService.getEdcrNocMandatory(oc.geteDcrNumber());
        for (OCNocDocuments nocDocument : oc.getNocDocuments()) {
            String code = nocDocument.getNocDocument().getServiceChecklist().getChecklist().getCode();
            NocConfiguration nocConfig = nocConfigurationService
                    .findByDepartmentAndType(code, BpaConstants.OC);

            if (ocNocService.findByApplicationNumberAndType(oc.getApplicationNumber(), code) != null)
                nocTypeApplMap.put(code, "initiated");
            if (nocConfig != null && nocConfig.getApplicationType().trim().equalsIgnoreCase(BpaConstants.OC)
                    && nocConfig.getIntegrationType().equalsIgnoreCase(NocIntegrationTypeEnum.SEMI_AUTO.toString())
                    && nocConfig.getIntegrationInitiation().equalsIgnoreCase(NocIntegrationInitiationEnum.MANUAL.toString())
                    && edcrNocMandatory.get(nocConfig.getDepartment()).equalsIgnoreCase("YES")) {
                nocConfigMap.put(nocConfig.getDepartment(), "initiate");
            }
            if (nocConfig != null && nocConfig.getIntegrationType().equalsIgnoreCase(NocIntegrationTypeEnum.SEMI_AUTO.toString())
                    && nocConfig.getIntegrationInitiation().equalsIgnoreCase(NocIntegrationInitiationEnum.AUTO.toString())
                    && edcrNocMandatory.get(nocConfig.getDepartment()).equalsIgnoreCase("YES")) {
                nocAutoMap.put(nocConfig.getDepartment(), "autoinitiate");
                nocAutoCount++;
                List<User> userList = nocUsers.stream()
                        .filter(usr -> usr.getRoles().stream()
                                .anyMatch(usrrl -> usrrl.getName()
                                        .equals(BpaConstants.getNocRole().get(nocConfig.getDepartment()))))
                        .collect(Collectors.toList());
                if (!userList.isEmpty())
                    nocAutoUsers.add(userList.get(0));
            }
            model.addAttribute("nocTypeApplMap", nocTypeApplMap);
            model.addAttribute("nocConfigMap", nocConfigMap);
            model.addAttribute("nocAutoMap", nocConfigMap);
            for (OccupancyNocApplication ona : ocNoc) {
                if (nocDocument.getNocDocument().getServiceChecklist().getChecklist().getCode()
                        .equalsIgnoreCase(ona.getBpaNocApplication().getNocType())) {
                    nocDocument.setOcNoc(ona);
                }
            }
            if (nocAutoUsers.size() == nocAutoCount)
                model.addAttribute("nocUserExists", true);
            else
                model.addAttribute("nocUserExists", false);
        }
        model.addAttribute("appDocAllowedExtenstions",
                bpaApplicationSettings.getValue("bpa.citizen.app.docs.allowed.extenstions"));
        model.addAttribute("appDocMaxSize", bpaApplicationSettings.getValue("bpa.citizen.dcr.docs.max.size"));

        model.addAttribute("dcrDocAllowedExtenstions",
                bpaApplicationSettings.getValue("bpa.citizen.dcr.docs.allowed.extenstions"));
        model.addAttribute("dcrDocMaxSize", bpaApplicationSettings.getValue("bpa.citizen.dcr.docs.max.size"));

        model.addAttribute("nocDocAllowedExtenstions",
                bpaApplicationSettings.getValue("bpa.citizen.noc.docs.allowed.extenstions"));
        model.addAttribute("nocDocMaxSize", bpaApplicationSettings.getValue("bpa.citizen.noc.docs.max.size"));
    }

    private void prepareFormData(final OccupancyCertificate oc, final Model model) {
        model.addAttribute("isEDCRIntegrationRequire", true);
        model.addAttribute("loadingFloorDetailsFromEdcrRequire", true);
        model.addAttribute("stateType", oc.getClass().getSimpleName());
        model.addAttribute(ADDITIONALRULE, CREATE_ADDITIONAL_RULE_CREATE_OC);
        getDcrDocumentsUploadMode(model);
        model.addAttribute("currentState", oc.getCurrentState() == null ? "" : oc.getCurrentState().getValue());
    }

    private void setCityName(final Model model, final HttpServletRequest request) {
        if (request.getSession().getAttribute("cityname") != null)
            model.addAttribute("cityName", request.getSession().getAttribute("cityname"));
    }

    @PostMapping("/occupancy-certificate/update-submit")
    public String updateOCDetails(@Valid @ModelAttribute("occupancyCertificate") final OccupancyCertificate occupancyCertificate,
            final HttpServletRequest request, final Model model,
            final BindingResult errors) {
        occupancyCertificateService.validateDocs(occupancyCertificate, errors);
        if (errors.hasErrors())
            return OCCUPANCY_CERTIFICATE_UPDATE;
        occupancyCertificateService.validateProposedAndExistingBuildings(occupancyCertificate);
        WorkflowBean wfBean = new WorkflowBean();
        Long userPosition = null;
        String workFlowAction = request.getParameter(WORK_FLOW_ACTION);
        Boolean citizenOrBusinessUser = request.getParameter(CITIZEN_OR_BUSINESS_USER) != null
                && request.getParameter(CITIZEN_OR_BUSINESS_USER)
                        .equalsIgnoreCase(TRUE) ? Boolean.TRUE : Boolean.FALSE;
        Boolean onlinePaymentEnable = request.getParameter(ONLINE_PAYMENT_ENABLE) != null
                && request.getParameter(ONLINE_PAYMENT_ENABLE)
                        .equalsIgnoreCase(TRUE) ? Boolean.TRUE : Boolean.FALSE;
        final WorkFlowMatrix wfMatrix = bpaUtils.getWfMatrixByCurrentState(occupancyCertificate.getStateType(), WF_NEW_STATE,
                CREATE_ADDITIONAL_RULE_CREATE_OC);
        if (wfMatrix != null)
            userPosition = bpaUtils.getUserPositionIdByZone(wfMatrix.getNextDesignation(),
                    bpaUtils.getBoundaryForWorkflow(occupancyCertificate.getParent().getSiteDetail().get(0)).getId());
        if (citizenOrBusinessUser && workFlowAction != null
                && workFlowAction.equals(WF_LBE_SUBMIT_BUTTON)
                && (userPosition == 0 || userPosition == null)) {
            model.addAttribute("noJAORSAMessage", OFFICIAL_NOT_EXISTS);
            return OCCUPANCY_CERTIFICATE_UPDATE;
        }

        wfBean.setWorkFlowAction(request.getParameter(WORK_FLOW_ACTION));
        OccupancyCertificate ocResponse = occupancyCertificateService.saveOrUpdate(occupancyCertificate, wfBean);
        bpaUtils.updatePortalUserinbox(ocResponse, null);
        if (workFlowAction != null
                && workFlowAction
                        .equals(WF_LBE_SUBMIT_BUTTON)
                && onlinePaymentEnable && bpaUtils.checkAnyTaxIsPendingToCollect(occupancyCertificate.getDemand())) {
            return genericBillGeneratorService
                    .generateBillAndRedirectToCollection(occupancyCertificate, model);
        } else if (workFlowAction != null && workFlowAction.equals(WF_LBE_SUBMIT_BUTTON)
                && !bpaUtils.checkAnyTaxIsPendingToCollect(occupancyCertificate.getDemand())) {
            if (occupancyCertificate.getAuthorizedToSubmitPlan())
                wfBean.setApproverComments(AUTH_TO_SUBMIT_PLAN);
            wfBean.setCurrentState(WF_NEW_STATE);
            bpaUtils.redirectToBpaWorkFlowForOC(occupancyCertificate, wfBean);
            ocSmsAndEmailService.sendSMSAndEmail(occupancyCertificate, null, null);
            ocNocService.initiateNoc(ocResponse);
            Position pos = positionMasterService.getPositionById(ocResponse.getCurrentState().getOwnerPosition().getId());
            User wfUser = workflowHistoryService.getUserPositionByPassingPosition(pos.getId());
            String message = messageSource.getMessage(MSG_PORTAL_FORWARD_REGISTRATION, new String[] {
                    wfUser == null ? ""
                            : wfUser.getUsername().concat("~")
                                    .concat(getDesinationNameByPosition(pos)),
                    ocResponse.getApplicationNumber() }, LocaleContextHolder.getLocale());

            message = message.concat(DISCLIMER_MESSAGE_ONSAVE);
            model.addAttribute(MESSAGE, message);
        } else if (workFlowAction != null && workFlowAction.equals(WF_CANCELAPPLICATION_BUTTON)) {
            model.addAttribute(MESSAGE,
                    "Occupancy Certificate  Application is cancelled by applicant itself successfully with application number "
                            + ocResponse.getApplicationNumber());
        } else {
            model.addAttribute(MESSAGE,
                    "Occupancy Certificate Application is successfully saved with ApplicationNumber "
                            + ocResponse.getApplicationNumber());
        }
        return BPAAPPLICATION_CITIZEN;
    }

    private void buildAppointmentDetailsOfScrutinyAndInspection(Model model, OccupancyCertificate oc) {
        if (APPLICATION_STATUS_SCHEDULED.equals(oc.getStatus().getCode())
                || APPLICATION_STATUS_RESCHEDULED.equals(oc.getStatus().getCode())) {
            Optional<OCSlot> activeSlotApplication = oc.getOcSlots().stream().reduce((slotAppln1, slotAppln2) -> slotAppln2);
            if (activeSlotApplication.isPresent()) {
                model.addAttribute("appointmentDateRes", DateUtils
                        .toDefaultDateFormat(activeSlotApplication.get().getSlotDetail().getSlot().getAppointmentDate()));
                model.addAttribute("appointmentTimeRes", activeSlotApplication.get().getSlotDetail().getAppointmentTime());
                model.addAttribute("appointmentTitle", "Scheduled Appointment Details For Document Scrutiny");
            }
        } else if (APPLICATION_STATUS_DOC_VERIFIED.equals(oc.getStatus().getCode()) && oc.getInspections().isEmpty()) {
            List<OCAppointmentSchedule> appointmentScheduledList = ocAppointmentScheduleService.findByApplication(oc,
                    AppointmentSchedulePurpose.INSPECTION);
            if (!appointmentScheduledList.isEmpty()) {
                model.addAttribute("appointmentDateRes", DateUtils.toDefaultDateFormat(
                        appointmentScheduledList.get(0).getAppointmentScheduleCommon().getAppointmentDate()));
                model.addAttribute("appointmentTimeRes",
                        appointmentScheduledList.get(0).getAppointmentScheduleCommon().getAppointmentTime());
                model.addAttribute("appmntInspnRemarks",
                        appointmentScheduledList.get(0).getAppointmentScheduleCommon().isPostponed()
                                ? appointmentScheduledList.get(0).getAppointmentScheduleCommon().getPostponementReason()
                                : appointmentScheduledList.get(0).getAppointmentScheduleCommon().getRemarks());
                model.addAttribute("appointmentTitle", "Scheduled Appointment Details For Field Inspection");
            }
        }
    }
}