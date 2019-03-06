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
package org.egov.bpa.web.controller.transaction.citizen;

import static org.egov.bpa.utils.BpaConstants.APPLICATION_MODULE_TYPE;
import static org.egov.bpa.utils.BpaConstants.AUTH_TO_SUBMIT_PLAN;
import static org.egov.bpa.utils.BpaConstants.BPA_APPLICATION;
import static org.egov.bpa.utils.BpaConstants.CHECKLIST_TYPE;
import static org.egov.bpa.utils.BpaConstants.CHECKLIST_TYPE_NOC;
import static org.egov.bpa.utils.BpaConstants.DCR_CHECKLIST;
import static org.egov.bpa.utils.BpaConstants.DISCLIMER_MESSAGE_ONEDAYPERMIT_ONSAVE;
import static org.egov.bpa.utils.BpaConstants.DISCLIMER_MESSAGE_ONSAVE;
import static org.egov.bpa.utils.BpaConstants.ST_CODE_01;
import static org.egov.bpa.utils.BpaConstants.ST_CODE_02;
import static org.egov.bpa.utils.BpaConstants.ST_CODE_03;
import static org.egov.bpa.utils.BpaConstants.ST_CODE_04;
import static org.egov.bpa.utils.BpaConstants.ST_CODE_05;
import static org.egov.bpa.utils.BpaConstants.ST_CODE_06;
import static org.egov.bpa.utils.BpaConstants.ST_CODE_07;
import static org.egov.bpa.utils.BpaConstants.ST_CODE_08;
import static org.egov.bpa.utils.BpaConstants.ST_CODE_09;
import static org.egov.bpa.utils.BpaConstants.ST_CODE_14;
import static org.egov.bpa.utils.BpaConstants.ST_CODE_15;
import static org.egov.bpa.utils.BpaConstants.WF_LBE_SUBMIT_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_NEW_STATE;
import static org.egov.infra.persistence.entity.enums.UserType.BUSINESS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.egov.bpa.master.entity.CheckListDetail;
import org.egov.bpa.master.entity.StakeHolder;
import org.egov.bpa.master.entity.enums.StakeHolderStatus;
import org.egov.bpa.transaction.entity.ApplicationDocument;
import org.egov.bpa.transaction.entity.ApplicationFloorDetail;
import org.egov.bpa.transaction.entity.ApplicationNocDocument;
import org.egov.bpa.transaction.entity.ApplicationStakeHolder;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.BuildingDetail;
import org.egov.bpa.transaction.entity.DCRDocument;
import org.egov.bpa.transaction.entity.ExistingBuildingDetail;
import org.egov.bpa.transaction.entity.ExistingBuildingFloorDetail;
import org.egov.bpa.transaction.entity.enums.ApplicantMode;
import org.egov.bpa.transaction.entity.enums.StakeHolderType;
import org.egov.bpa.transaction.service.BpaDcrService;
import org.egov.bpa.transaction.service.BuildingFloorDetailsService;
import org.egov.bpa.transaction.service.SearchBpaApplicationService;
import org.egov.bpa.transaction.service.collection.GenericBillGeneratorService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.bpa.web.controller.transaction.BpaGenericApplicationController;
import org.egov.commons.entity.Source;
import org.egov.commons.service.SubOccupancyService;
import org.egov.eis.entity.Assignment;
import org.egov.infra.admin.master.entity.AppConfigValues;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.config.core.ApplicationThreadLocals;
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

@Controller
@RequestMapping("/application/citizen")
public class CitizenApplicationController extends BpaGenericApplicationController {

    private static final String ONLINE_PAYMENT_ENABLE = "onlinePaymentEnable";

    private static final String WORK_FLOW_ACTION = "workFlowAction";

    private static final String TRUE = "TRUE";

    private static final String CITIZEN_OR_BUSINESS_USER = "citizenOrBusinessUser";

    private static final String IS_CITIZEN = "isCitizen";

    private static final String MSG_PORTAL_FORWARD_REGISTRATION = "msg.portal.forward.registration";

    private static final String MESSAGE = "message";

    private static final String BPAAPPLICATION_CITIZEN = "citizen_suceess";

    private static final String COMMON_ERROR = "common-error";

    @Autowired
    private GenericBillGeneratorService genericBillGeneratorService;
    @Autowired
    private BuildingFloorDetailsService buildingFloorDetailsService;
    @Autowired
    private SearchBpaApplicationService searchBpaApplicationService;
    @Autowired
    private BpaDcrService bpaDcrService;
    @Autowired
    protected SubOccupancyService subOccupancyService;

    @GetMapping("/newconstruction-form")
    public String showNewApplicationForm(@ModelAttribute final BpaApplication bpaApplication, final Model model,
            final HttpServletRequest request) {
        setCityName(model, request);
        model.addAttribute("currentuser", securityUtils.getCurrentUser().getName());
        return loadNewForm(bpaApplication, model, ST_CODE_01);
    }

    private void setCityName(final Model model, final HttpServletRequest request) {
        if (request.getSession().getAttribute("cityname") != null)
            model.addAttribute("cityName", request.getSession().getAttribute("cityname"));
    }

    private String loadNewForm(final BpaApplication bpaApplication, final Model model, String serviceCode) {
        User user = securityUtils.getCurrentUser();
        StakeHolder stkHldr = stakeHolderService.findById(user.getId());
        if (validateStakeholderIsEligibleSubmitAppln(model, serviceCode, stkHldr))
            return COMMON_ERROR;
        if (user.getType().equals(BUSINESS) && stkHldr.getDemand() != null) {
            List<AppConfigValues> appConfigValueList = appConfigValueService
                    .getConfigValuesByModuleAndKey(APPLICATION_MODULE_TYPE, "BUILDING_LICENSEE_REG_FEE_REQUIRED");
            if ((appConfigValueList.isEmpty() ? "" : appConfigValueList.get(0).getValue()).equalsIgnoreCase("YES")) {
                if (stkHldr.getStatus() != null
                        && BpaConstants.APPLICATION_STATUS_PENDNING.equalsIgnoreCase(stkHldr.getStatus().toString())) {
                    return genericBillGeneratorService.generateBillAndRedirectToCollection(stkHldr, model);
                }
            }
        }
        model.addAttribute("stakeHolderType", stkHldr.getStakeHolderType().getStakeHolderTypeVal());
        prepareFormData(model);
        bpaApplication.setApplicationDate(new Date());
        prepareCommonModelAttribute(model, bpaApplication.isCitizenAccepted());
        model.addAttribute("mode", "new");
        bpaApplication.setSource(Source.CITIZENPORTAL);
        bpaApplication.setApplicantMode(ApplicantMode.NEW);
        bpaApplication.setServiceType(serviceTypeService.getServiceTypeByCode(serviceCode));
        model.addAttribute("isEDCRIntegrationRequire",
                bpaDcrService.isEdcrIntegrationRequireByService(serviceCode));
        model.addAttribute("loadingFloorDetailsFromEdcrRequire", true);
        model.addAttribute("checkListDetailList",
                checkListDetailService.findActiveCheckListByServiceType(bpaApplication.getServiceType().getId(),
                        CHECKLIST_TYPE));
        List<CheckListDetail> checkListDetail = checkListDetailService.findActiveCheckListByServiceType(
                bpaApplication.getServiceType().getId(),
                CHECKLIST_TYPE);
        model.addAttribute("subOccupancyList", subOccupancyService.findAllOrderByOrderNumber());
        List<ApplicationDocument> appDocList = new ArrayList<>();
        for (CheckListDetail checkdet : checkListDetail) {
            ApplicationDocument appdoc = new ApplicationDocument();
            appdoc.setChecklistDetail(checkdet);
            appDocList.add(appdoc);
        }
        List<CheckListDetail> dcrCheckListDetail = checkListDetailService
                .findActiveCheckListByServiceType(bpaApplication.getServiceType().getId(), DCR_CHECKLIST);
        if (bpaApplication.getDcrDocuments().isEmpty()) {
            for (CheckListDetail dcrChkDetails : dcrCheckListDetail) {
                DCRDocument dcrDocument = new DCRDocument();
                dcrDocument.setApplication(bpaApplication);
                dcrDocument.setChecklistDtl(dcrChkDetails);
                bpaApplication.getDcrDocuments().add(dcrDocument);
            }
        }

        if (bpaApplication.getApplicationNOCDocument().isEmpty()) {
            for (CheckListDetail chckListDetail : checkListDetailService
                    .findActiveCheckListByServiceType(bpaApplication.getServiceType().getId(), CHECKLIST_TYPE_NOC)) {
                ApplicationNocDocument nocDocument = new ApplicationNocDocument();
                nocDocument.setChecklist(chckListDetail);
                nocDocument.setApplication(bpaApplication);
                bpaApplication.getApplicationNOCDocument().add(nocDocument);
            }
        }
        model.addAttribute("applicationDocumentList", appDocList);
        getDcrDocumentsUploadMode(model);
        if(!bpaDcrService.isEdcrIntegrationRequireByService(serviceCode)) {
            BuildingDetail bldg = new BuildingDetail();
            bldg.setName("0");
            bldg.setNumber(0);
            bpaApplication.getBuildingDetail().add(bldg);
            ExistingBuildingDetail exstBldg = new ExistingBuildingDetail();
            exstBldg.setName("0");
            exstBldg.setNumber(0);
            bpaApplication.getExistingBuildingDetails().add(exstBldg);
        }
        return "citizenApplication-form";
    }

    private boolean validateStakeholderIsEligibleSubmitAppln(Model model, String serviceCode, StakeHolder stkHldr) {
        if (stkHldr != null && StakeHolderStatus.BLOCKED.equals(stkHldr.getStatus())) {
            model.addAttribute(MESSAGE,
                    messageSource.getMessage("msg.stakeholder.license.blocked",
                            new String[] { ApplicationThreadLocals.getMunicipalityName() }, null));
            return true;
        }
        if (stkHldr != null && stkHldr.getBuildingLicenceExpiryDate()
                .before(searchBpaApplicationService.resetFromDateTimeStamp(new Date()))) {
            model.addAttribute(MESSAGE,
                    messageSource.getMessage("msg.stakeholder.expiry.reached", new String[] { stkHldr.getName() }, null));
            return true;
        }
        if ((StakeHolderType.TOWN_PLANNER_A.equals(stkHldr.getStakeHolderType())
                || StakeHolderType.TOWN_PLANNER_B.equals(stkHldr.getStakeHolderType()))
                && !ST_CODE_05.equals(serviceCode)) {
            model.addAttribute(MESSAGE,
                    messageSource.getMessage("msg.invalid.stakeholder", new String[] { stkHldr.getName() }, null));
            return true;
        }
        return false;
    }

    @GetMapping("/demolition-form")
    public String showDemolition(@ModelAttribute final BpaApplication bpaApplication, final Model model,
            final HttpServletRequest request) {
        setCityName(model, request);
        return loadNewForm(bpaApplication, model, ST_CODE_02);
    }

    @GetMapping("/reconstruction-form")
    public String showReconstruction(@ModelAttribute final BpaApplication bpaApplication, final Model model,
            final HttpServletRequest request) {
        setCityName(model, request);
        return loadNewForm(bpaApplication, model, ST_CODE_03);
    }

    @GetMapping("/alteration-form")
    public String showAlteration(@ModelAttribute final BpaApplication bpaApplication, final Model model,
            final HttpServletRequest request) {
        setCityName(model, request);
        return loadNewForm(bpaApplication, model, ST_CODE_04);
    }

    @GetMapping("/subdevland-form")
    public String showSubDevlisionOfLand(@ModelAttribute final BpaApplication bpaApplication, final Model model,
            final HttpServletRequest request) {
        setCityName(model, request);
        return loadNewForm(bpaApplication, model, ST_CODE_05);
    }

    @GetMapping("/addextnew-form")
    public String loadAddOfExtection(@ModelAttribute final BpaApplication bpaApplication, final Model model,
            final HttpServletRequest request) {
        setCityName(model, request);
        return loadNewForm(bpaApplication, model, ST_CODE_06);
    }

    @GetMapping("/changeofoccupancy-form")
    public String showChangeOfOccupancy(@ModelAttribute final BpaApplication bpaApplication, final Model model,
            final HttpServletRequest request) {
        setCityName(model, request);
        return loadNewForm(bpaApplication, model, ST_CODE_07);
    }

    @GetMapping("/permissionhutorshud-form")
    public String loadPermOfHutOrShud(@ModelAttribute final BpaApplication bpaApplication, final Model model,
            final HttpServletRequest request) {
        setCityName(model, request);
        return loadNewForm(bpaApplication, model, ST_CODE_09);
    }

    @GetMapping("/amenity-form")
    public String loadAmenity(@ModelAttribute final BpaApplication bpaApplication, final Model model,
            final HttpServletRequest request) {
        setCityName(model, request);
        return loadNewForm(bpaApplication, model, ST_CODE_08);
    }

    @GetMapping("/towerconstruction-form")
    public String loadTowerConstruction(@ModelAttribute final BpaApplication bpaApplication, final Model model,
            final HttpServletRequest request) {
        setCityName(model, request);
        return loadNewForm(bpaApplication, model, ST_CODE_14);
    }

    @GetMapping("/polestructures-form")
    public String loadPoleStruture(@ModelAttribute final BpaApplication bpaApplication, final Model model,
            final HttpServletRequest request) {
        setCityName(model, request);
        return loadNewForm(bpaApplication, model, ST_CODE_15);
    }

    @PostMapping("/application-create")
    public String createNewConnection(@Valid @ModelAttribute final BpaApplication bpaApplication,
            final HttpServletRequest request, final Model model,
            final BindingResult errors, final RedirectAttributes redirectAttributes) {
        
        if (errors.hasErrors()) {
            buildingFloorDetailsService.buildNewlyAddedFloorDetails(bpaApplication);
            applicationBpaService.buildExistingAndProposedBuildingDetails(bpaApplication);
            prepareCommonModelAttribute(model, bpaApplication.isCitizenAccepted());
            return loadNewForm(bpaApplication, model, bpaApplication.getServiceType().getCode());
        }
        Map<String, String> eDcrApplDetails = bpaDcrService.checkIsEdcrUsedInBpaApplication(bpaApplication.geteDcrNumber());
        if(eDcrApplDetails.get("isExists") == "true"){
        	model.addAttribute("eDcrApplExistsMessage", eDcrApplDetails.get(BpaConstants.MESSAGE));
        	return loadNewForm(bpaApplication, model, bpaApplication.getServiceType().getCode());
        }
        boolean isEdcrIntegrationRequire = bpaDcrService.isEdcrIntegrationRequireByService(bpaApplication.getServiceType().getCode());
        if (isEdcrIntegrationRequire && !eDcrApplDetails.isEmpty() && eDcrApplDetails.get("isExists").equals("true")) {
            buildingFloorDetailsService.buildNewlyAddedFloorDetails(bpaApplication);
            applicationBpaService.buildExistingAndProposedBuildingDetails(bpaApplication);
            prepareCommonModelAttribute(model, bpaApplication.isCitizenAccepted());
            model.addAttribute("eDcrApplExistsMessage", eDcrApplDetails.get(BpaConstants.MESSAGE));
            return loadNewForm(bpaApplication, model, bpaApplication.getServiceType().getCode());
        }
        
        if(isEdcrIntegrationRequire) {
            bpaApplication.getBuildingDetail().clear();
            for (BuildingDetail bldg : bpaApplication.getBuildingDetailFromEdcr()) {
                List<BuildingDetail> bldgDetails = new ArrayList<>();
                List<ApplicationFloorDetail> floorDetails = new ArrayList<>();
                for (ApplicationFloorDetail floor : bldg.getBuildingFloorDetailsByEdcr())
                    floorDetails.add(floor);
                bldg.setApplicationFloorDetailsForUpdate(floorDetails);
                bldgDetails.add(bldg);
                bpaApplication.getBuildingDetail().addAll(bldgDetails);
            }
            if (!bpaApplication.getExistingBldgDetailFromEdcr().isEmpty()) {
                bpaApplication.getExistingBuildingDetails().clear();
                for (ExistingBuildingDetail existBldg : bpaApplication.getExistingBldgDetailFromEdcr()) {
                    List<ExistingBuildingDetail> existBldgDetails = new ArrayList<>();
                    List<ExistingBuildingFloorDetail> floorDetails = new ArrayList<>();
                    for (ExistingBuildingFloorDetail floor : existBldg.getExistingBldgFloorDetailsFromEdcr())
                        floorDetails.add(floor);
                    existBldg.setExistingBuildingFloorDetailsUpdate(floorDetails);
                    existBldgDetails.add(existBldg);
                    bpaApplication.getExistingBuildingDetails().addAll(existBldgDetails);
                }
            }
        }
        
        applicationBpaService.buildExistingAndProposedBuildingDetails(bpaApplication);
        bpaUtils.saveOrUpdateBoundary(bpaApplication);
        /*
         * if (bpaApplicationValidationService.validateBuildingDetails(bpaApplication, model)) {
         * applicationBpaService.buildExistingAndProposedBuildingDetails(bpaApplication); prepareCommonModelAttribute(model,
         * bpaApplication.isCitizenAccepted()); return loadNewForm(bpaApplication, model,
         * bpaApplication.getServiceType().getCode()); }
         */
        Long approvalPosition = null;
        String workFlowAction = request.getParameter(WORK_FLOW_ACTION);
        Boolean isCitizen = request.getParameter(IS_CITIZEN) != null
                && request.getParameter(IS_CITIZEN)
                        .equalsIgnoreCase(TRUE) ? Boolean.TRUE : Boolean.FALSE;
        Boolean citizenOrBusinessUser = request.getParameter(CITIZEN_OR_BUSINESS_USER) != null
                && request.getParameter(CITIZEN_OR_BUSINESS_USER)
                        .equalsIgnoreCase(TRUE) ? Boolean.TRUE : Boolean.FALSE;
        Boolean onlinePaymentEnable = request.getParameter(ONLINE_PAYMENT_ENABLE) != null
                && request.getParameter(ONLINE_PAYMENT_ENABLE)
                        .equalsIgnoreCase(TRUE) ? Boolean.TRUE : Boolean.FALSE;
        final WorkFlowMatrix wfMatrix = bpaUtils.getWfMatrixByCurrentState(bpaApplication.getIsOneDayPermitApplication(),
                bpaApplication.getStateType(), WF_NEW_STATE);
        if (wfMatrix != null)
			approvalPosition = bpaUtils.getUserPositionIdByZone(wfMatrix.getNextDesignation(),
					bpaUtils.getBoundaryForWorkflow(bpaApplication.getSiteDetail().get(0)).getId());
        if (citizenOrBusinessUser && workFlowAction != null
                && workFlowAction.equals(WF_LBE_SUBMIT_BUTTON)
                && (approvalPosition == 0 || approvalPosition == null)) {
            applicationBpaService.buildExistingAndProposedBuildingDetails(bpaApplication);
            model.addAttribute("noJAORSAMessage", messageSource.getMessage("msg.official.not.exist", new String[] {
                    ApplicationThreadLocals.getMunicipalityName() }, LocaleContextHolder.getLocale()));
            return loadNewForm(bpaApplication, model, bpaApplication.getServiceType().getCode());
        }
        if (citizenOrBusinessUser) {
            if (isCitizen) {
                List<ApplicationStakeHolder> applicationStakeHolders = new ArrayList<>();
                ApplicationStakeHolder applicationStakeHolder = new ApplicationStakeHolder();
                applicationStakeHolder.setApplication(bpaApplication);
                applicationStakeHolder.setStakeHolder(bpaApplication
                        .getStakeHolder().get(0).getStakeHolder());
                applicationStakeHolders.add(applicationStakeHolder);
                bpaApplication.setStakeHolder(applicationStakeHolders);
            } else {
                User user = securityUtils.getCurrentUser();
                StakeHolder stakeHolder = stakeHolderService.findById(user.getId());
                ApplicationStakeHolder applicationStakeHolder = new ApplicationStakeHolder();
                applicationStakeHolder.setApplication(bpaApplication);
                applicationStakeHolder.setStakeHolder(stakeHolder);
                bpaApplication.getStakeHolder().add(applicationStakeHolder);
                if (!bpaApplicationValidationService
                        .checkStakeholderIsValid(bpaApplication)) {
                    String message = bpaApplicationValidationService
                            .getValidationMessageForBusinessResgistration(bpaApplication);
                    model.addAttribute("invalidStakeholder", message);
                    applicationBpaService.buildExistingAndProposedBuildingDetails(bpaApplication);
                    return loadNewForm(bpaApplication, model, bpaApplication
                            .getServiceType().getCode());
                }
            }
        }
        applicationBpaService.persistOrUpdateApplicationDocument(bpaApplication);
        bpaApplication.setAdmissionfeeAmount(applicationBpaService.setAdmissionFeeAmountForRegistrationWithAmenities(
                bpaApplication.getServiceType().getId(), new ArrayList<>()));
        if (bpaApplication.getOwner().getUser() != null && bpaApplication.getOwner().getUser().getId() == null)
            applicationBpaService.buildOwnerDetails(bpaApplication);

        BpaApplication bpaApplicationRes = applicationBpaService.createNewApplication(bpaApplication, workFlowAction);

        if (citizenOrBusinessUser)
            if (isCitizen)
                bpaUtils.createPortalUserinbox(bpaApplicationRes, Arrays.asList(bpaApplicationRes.getOwner().getUser(),
                        bpaApplicationRes.getStakeHolder().get(0).getStakeHolder()), workFlowAction);
            else
                bpaUtils.createPortalUserinbox(bpaApplicationRes,
                        Arrays.asList(bpaApplicationRes.getOwner().getUser(), securityUtils.getCurrentUser()), workFlowAction);

        // Will redirect to collection, then after collection success will forward to official
        if (workFlowAction != null
                && workFlowAction
                        .equals(WF_LBE_SUBMIT_BUTTON)
                && onlinePaymentEnable && bpaUtils.checkAnyTaxIsPendingToCollect(bpaApplicationRes.getDemand()))
            return genericBillGeneratorService
                    .generateBillAndRedirectToCollection(bpaApplication, model);
        // When fee collection not require then directly will forward to official
        else if (workFlowAction != null && workFlowAction.equals(WF_LBE_SUBMIT_BUTTON)
                && !bpaUtils.checkAnyTaxIsPendingToCollect(bpaApplication.getDemand())) {
            String remarks = null;
            if (bpaApplication.getAuthorizedToSubmitPlan())
                remarks = AUTH_TO_SUBMIT_PLAN;
            bpaUtils.redirectToBpaWorkFlow(approvalPosition, bpaApplication, WF_NEW_STATE,
                    remarks == null ? bpaApplication.getApprovalComent() : remarks, null, null);
            bpaUtils.sendSmsEmailOnCitizenSubmit(bpaApplication);
            List<Assignment> assignments;
            if (null == approvalPosition)
                assignments = bpaWorkFlowService
                        .getAssignmentsByPositionAndDate(bpaApplication.getCurrentState().getOwnerPosition().getId(), new Date());
            else
                assignments = bpaWorkFlowService.getAssignmentsByPositionAndDate(approvalPosition, new Date());
            Position pos = assignments.get(0).getPosition();
            User wfUser = assignments.get(0).getEmployee();
            String message = messageSource.getMessage(MSG_PORTAL_FORWARD_REGISTRATION, new String[] {
                    wfUser == null ? ""
                            : wfUser.getUsername().concat("~")
                                    .concat(getDesinationNameByPosition(pos)),
                    bpaApplication.getApplicationNumber() }, LocaleContextHolder.getLocale());
            if (bpaApplication.getIsOneDayPermitApplication()) {
                message = message.concat(DISCLIMER_MESSAGE_ONEDAYPERMIT_ONSAVE);
                getAppointmentMsgForOnedayPermit(bpaApplication, model);
            } else
                message = message.concat(DISCLIMER_MESSAGE_ONSAVE);

            redirectAttributes.addFlashAttribute(MESSAGE, message);
        } else
            redirectAttributes.addFlashAttribute(MESSAGE,
                    "Successfully saved with application number " + bpaApplicationRes.getApplicationNumber() + ".");
        if (bpaUtils.isCitizenAcceptanceRequired() && !bpaApplicationRes.isCitizenAccepted())
            bpaSmsAndEmailService.sendSMSAndEmail(bpaApplicationRes, null, null);

        return "redirect:/application/citizen/success/" + bpaApplicationRes.getApplicationNumber();
    }

    @GetMapping("/success/{applicationNumber}")
    public String success(@PathVariable final String applicationNumber, final Model model) {
        BpaApplication application = applicationBpaService.findByApplicationNumber(applicationNumber);
        model.addAttribute(BPA_APPLICATION, application);
        return BPAAPPLICATION_CITIZEN;
    }

    @GetMapping("/check-status/{applicationNumber}")
    public String viewApplicationStatus(final Model model, @PathVariable final String applicationNumber,
            final HttpServletRequest request) {
        BpaApplication application = applicationBpaService.findByApplicationNumber(applicationNumber);
        if (application == null)
            model.addAttribute(MESSAGE, messageSource.getMessage("msg.validate.building.plan.approval.no", null, null));
        model.addAttribute("bpaApplication", application);
        return "application-status";
    }
    
}