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

import static org.egov.bpa.utils.BpaConstants.ADMINISTRATION_HIERARCHY_TYPE;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_MODULE_TYPE;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_DOC_VERIFIED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_TYPE_ONEDAYPERMIT;
import static org.egov.bpa.utils.BpaConstants.BPASTATUS_MODULETYPE;
import static org.egov.bpa.utils.BpaConstants.BPA_CITIZENACCEPTANCE_CHECK;
import static org.egov.bpa.utils.BpaConstants.DCR_DOC_AUTO_POPULATE_AND_MANUAL_UPLOAD;
import static org.egov.bpa.utils.BpaConstants.DCR_DOC_AUTO_POPULATE_UPLOAD;
import static org.egov.bpa.utils.BpaConstants.DCR_DOC_MANUAL_UPLOAD;
import static org.egov.bpa.utils.BpaConstants.ENABLEONLINEPAYMENT;
import static org.egov.bpa.utils.BpaConstants.LOCALITY;
import static org.egov.bpa.utils.BpaConstants.LOCATION_HIERARCHY_TYPE;
import static org.egov.bpa.utils.BpaConstants.MESSAGE;
import static org.egov.bpa.utils.BpaConstants.OCCUPANCY_CERTIFICATE_NOTICE_TYPE;
import static org.egov.bpa.utils.BpaConstants.REVENUE_HIERARCHY_TYPE;
import static org.egov.bpa.utils.BpaConstants.STREET;
import static org.egov.bpa.utils.BpaConstants.WARD;
import static org.egov.bpa.utils.BpaConstants.WF_REJECT_STATE;
import static org.egov.bpa.utils.BpaConstants.YES;
import static org.egov.bpa.utils.BpaConstants.ZONE;
import static org.egov.bpa.utils.BpaConstants.getBuildingFloorsList;
import static org.egov.bpa.utils.OcConstants.OCCUPANCY_CERTIFICATE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.egov.bpa.master.entity.ApplicationSubType;
import org.egov.bpa.master.service.ApplicationSubTypeService;
import org.egov.bpa.master.service.BpaSchemeService;
import org.egov.bpa.master.service.ChecklistServicetypeMappingService;
import org.egov.bpa.master.service.ConstructionStagesService;
import org.egov.bpa.master.service.ServiceTypeService;
import org.egov.bpa.master.service.StakeHolderService;
import org.egov.bpa.master.service.StakeholderTypeService;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.BpaStatus;
import org.egov.bpa.transaction.entity.InspectionApplication;
import org.egov.bpa.transaction.entity.enums.ApplicantMode;
import org.egov.bpa.transaction.entity.enums.BpaUom;
import org.egov.bpa.transaction.entity.enums.GovernmentType;
import org.egov.bpa.transaction.entity.enums.NocStatus;
import org.egov.bpa.transaction.entity.enums.OneDayPermitLandType;
import org.egov.bpa.transaction.entity.oc.OccupancyCertificate;
import org.egov.bpa.transaction.service.ApplicationBpaService;
import org.egov.bpa.transaction.service.BpaApplicationValidationService;
import org.egov.bpa.transaction.service.BpaStatusService;
import org.egov.bpa.transaction.service.BuildingFloorDetailsService;
import org.egov.bpa.transaction.service.ExistingBuildingFloorDetailsService;
import org.egov.bpa.transaction.service.WorkflowHistoryService;
import org.egov.bpa.transaction.service.collection.BpaDemandService;
import org.egov.bpa.transaction.service.messaging.BPASmsAndEmailService;
import org.egov.bpa.transaction.service.messaging.oc.OcSmsAndEmailService;
import org.egov.bpa.transaction.workflow.BpaWorkFlowService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.bpa.utils.BpaUtils;
import org.egov.commons.service.OccupancyService;
import org.egov.dcb.bean.Receipt;
import org.egov.demand.model.EgDemandDetails;
import org.egov.demand.model.EgdmCollectedReceipt;
import org.egov.eis.entity.Assignment;
import org.egov.eis.web.contract.WorkflowContainer;
import org.egov.eis.web.controller.workflow.GenericWorkFlowController;
import org.egov.infra.admin.master.entity.AppConfigValues;
import org.egov.infra.admin.master.entity.Boundary;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.admin.master.service.AppConfigValueService;
import org.egov.infra.admin.master.service.BoundaryService;
import org.egov.infra.admin.master.service.UserService;
import org.egov.infra.filestore.service.FileStoreService;
import org.egov.infra.persistence.entity.enums.UserType;
import org.egov.infra.security.utils.SecurityUtils;
import org.egov.infra.utils.DateUtils;
import org.egov.infra.utils.FileStoreUtils;
import org.egov.infra.workflow.entity.StateAware;
import org.egov.pims.commons.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

public abstract class BpaGenericApplicationController extends GenericWorkFlowController {

    protected static final String COMMON_ERROR = "common-error";

    @Autowired
    protected ChecklistServicetypeMappingService checklistServiceTypeService;
    @Autowired
    @Qualifier("fileStoreService")
    protected FileStoreService fileStoreService;
    @Autowired
    protected ApplicationBpaService applicationBpaService;
    @Autowired
    protected WorkflowHistoryService workflowHistoryService;
    @Autowired
    protected FileStoreUtils fileStoreUtils;
    @Autowired
    protected BpaDemandService bpaDemandService;
    @Autowired
    protected BpaWorkFlowService bpaWorkFlowService;
    @Autowired
    protected ResourceBundleMessageSource messageSource;
    @Autowired
    protected BpaStatusService bpaStatusService;
    @Autowired
    protected BpaSchemeService bpaSchemeService;
    @Autowired
    protected BpaUtils bpaUtils;
    @Autowired
    protected SecurityUtils securityUtils;
    @Autowired
    protected BpaApplicationValidationService bpaApplicationValidationService;
    @Autowired
    protected BuildingFloorDetailsService proposedBuildingFloorDetailsService;
    @Autowired
    protected ExistingBuildingFloorDetailsService existingBuildingFloorDetailsService;
    @Autowired
    protected BPASmsAndEmailService bpaSmsAndEmailService;
    @Autowired
    protected BoundaryService boundaryService;
    @Autowired
    protected ServiceTypeService serviceTypeService;
    @Autowired
    protected OccupancyService occupancyService;
    @Autowired
    protected ConstructionStagesService constructionStagesService;
    @Autowired
    protected AppConfigValueService appConfigValueService;
    @Autowired
    protected UserService userService;
    @Autowired
    protected StakeHolderService stakeHolderService;
    @Autowired
    protected OcSmsAndEmailService ocSmsAndEmailService;
    @Autowired
    protected StakeholderTypeService stakeholderTypeService;
    @Autowired
    protected ApplicationSubTypeService applicationTypeService;

    protected void prepareFormData(Model model) {
        model.addAttribute("occupancyList", occupancyService.findAllOrderByOrderNumber());
        model.addAttribute("zones", boundaryService.getActiveBoundariesByBndryTypeNameAndHierarchyTypeName(ZONE,
                REVENUE_HIERARCHY_TYPE));
        model.addAttribute("serviceTypeList", serviceTypeService.getAllActiveMainServiceTypes());
        model.addAttribute("amenityTypeList", serviceTypeService.getAllActiveAmenities());
        model.addAttribute("stakeHolderTypeList", stakeholderTypeService.findAllIsActive());
        model.addAttribute("governmentTypeList", Arrays.asList(GovernmentType.values()));
        model.addAttribute("constStages", constructionStagesService.findByRequiredForRegularization());
        model.addAttribute("appTypes", applicationTypeService.getAllEnabledApplicationTypes());
        model.addAttribute("electionwards", getElectionWards());
        model.addAttribute("wards", getRevenueWards());
        model.addAttribute("street", boundaryService.getActiveBoundariesByBndryTypeNameAndHierarchyTypeName(STREET,
                REVENUE_HIERARCHY_TYPE));
        model.addAttribute("localitys", boundaryService
                .getActiveBoundariesByBndryTypeNameAndHierarchyTypeName(LOCALITY,
                        LOCATION_HIERARCHY_TYPE));
        model.addAttribute("applicationModes", getApplicationModeMap());
        model.addAttribute("buildingFloorList", getBuildingFloorsList());
        model.addAttribute("uomList", BpaUom.values());
        List<ApplicationSubType> slotMappingApplTypes = new ArrayList<>();
        List<ApplicationSubType> appTypes = applicationTypeService.getAllEnabledApplicationTypes();
        for (ApplicationSubType applType : appTypes)
            if (!bpaUtils.isOneDayPermitApplicationIntegrationRequired() && applType.equals(APPLICATION_TYPE_ONEDAYPERMIT))
                break;
            else
                slotMappingApplTypes.add(applType);

        model.addAttribute("slotMappingApplTypes", slotMappingApplTypes);
        model.addAttribute("applnStatusList", bpaStatusService.findAllByModuleType(BPASTATUS_MODULETYPE));
        model.addAttribute("schemesList", bpaSchemeService.findAll());
        model.addAttribute("oneDayPermitLandTypeList", Arrays.asList(OneDayPermitLandType.values()));
        List<ApplicationSubType> appTyps = applicationTypeService.getAllEnabledApplicationTypes();
        List<ApplicationSubType> applicationTypes = new ArrayList<>();
        for (ApplicationSubType applType : appTyps)
            if (applType.getName().equals(OCCUPANCY_CERTIFICATE_NOTICE_TYPE))
                continue;
            else
                applicationTypes.add(applType);
        model.addAttribute("applicationTypes", applicationTypes);
        model.addAttribute("userList", userService.getUserByTypeInOrder(UserType.EMPLOYEE));
    }

    protected List<ApplicationSubType> getApplicationTypes() {
        List<ApplicationSubType> applicationTypeList = new ArrayList<>();
        List<ApplicationSubType> appTypes = applicationTypeService.findAll();
        for (ApplicationSubType appType : appTypes)
            if (!bpaUtils.isOneDayPermitApplicationIntegrationRequired()
                    && appType.getName().equals(APPLICATION_TYPE_ONEDAYPERMIT))
                break;
            else
                applicationTypeList.add(appType);
        return applicationTypeList;
    }

    @ModelAttribute("isOneDayPermitApplicationIntegrationRequired")
    public Boolean isOneDayPermitApplicationIntegrationRequired() {
        return bpaUtils.isOneDayPermitApplicationIntegrationRequired();
    }

    protected void getApplicationConfigurations(final Model model) {
        model.addAttribute("regularPermitInspectionSchedulingIntegrationRequired",
                bpaUtils.isRegularPermitInspectionSchedulingIntegrationRequired());
        model.addAttribute("oneDayPermitInspectionSchedulingIntegrationRequired",
                bpaUtils.isOneDayPermitInspectionSchedulingIntegrationRequired());
    }

    @ModelAttribute("nocStatusList")
    public NocStatus[] getNocStatusList() {
        return NocStatus.values();
    }

    public List<Boundary> getElectionWards() {
        List<Boundary> boundaries = boundaryService
                .getActiveBoundariesByBndryTypeNameAndHierarchyTypeName(WARD, ADMINISTRATION_HIERARCHY_TYPE);
        sortBoundaryByBndryNumberAsc(boundaries);
        return boundaries;
    }

    public List<Boundary> getRevenueWards() {
        List<Boundary> boundaries = boundaryService.getActiveBoundariesByBndryTypeNameAndHierarchyTypeName(WARD,
                REVENUE_HIERARCHY_TYPE);
        sortBoundaryByBndryNumberAsc(boundaries);
        return boundaries;
    }

    private void sortBoundaryByBndryNumberAsc(List<Boundary> boundaries) {
        boundaries.sort(Comparator.comparing(Boundary::getBoundaryNum));
    }

    public Map<String, String> getApplicationModeMap() {
        final Map<String, String> applicationModeMap = new LinkedHashMap<>(0);
        applicationModeMap.put(ApplicantMode.NEW.toString(), ApplicantMode.NEW.name());
        applicationModeMap.put(ApplicantMode.OTHERS.name(), ApplicantMode.OTHERS.name());
        return applicationModeMap;
    }

    /**
     * @param prepareModel
     * @param model
     * @param container This method we are calling In GET Method..
     */
    @Override
    protected void prepareWorkflow(final Model prepareModel, final StateAware model, final WorkflowContainer container) {
        prepareModel.addAttribute("approverDepartmentList", addAllDepartments());
        prepareModel.addAttribute("validActionList", bpaWorkFlowService.getValidActions(model, container));
        prepareModel.addAttribute("nextAction", bpaWorkFlowService.getNextAction(model, container));
    }

    protected void prepareCommonModelAttribute(final Model model, final Boolean isCitizenAccepted) {
        Boolean citizenUser = bpaUtils.logedInuserIsCitizen();
        model.addAttribute("isCitizen", citizenUser);
        List<AppConfigValues> appConfigValueList = appConfigValueService.getConfigValuesByModuleAndKey(
                APPLICATION_MODULE_TYPE, BPA_CITIZENACCEPTANCE_CHECK);
        String validateCitizenAcceptance = appConfigValueList.isEmpty() ? "" : appConfigValueList.get(0).getValue();
        model.addAttribute("validateCitizenAcceptance",
                (validateCitizenAcceptance.equalsIgnoreCase("YES") ? Boolean.TRUE : Boolean.FALSE));
        if (StringUtils.isNotBlank(validateCitizenAcceptance)) {
            model.addAttribute("citizenDisclaimerAccepted", isCitizenAccepted);
        }
        String enableOrDisablePayOnline = bpaUtils.getAppconfigValueByKeyName(ENABLEONLINEPAYMENT);
        model.addAttribute("onlinePaymentEnable",
                (enableOrDisablePayOnline.equalsIgnoreCase("YES") ? Boolean.TRUE : Boolean.FALSE));
        model.addAttribute("citizenOrBusinessUser", bpaUtils.logedInuseCitizenOrBusinessUser());
    }

    protected void prepareWorkflowDataForInspection(final Model model, final BpaApplication application) {
        model.addAttribute("stateType", application.getClass().getSimpleName());
        final WorkflowContainer workflowContainer = new WorkflowContainer();
        model.addAttribute(BpaConstants.ADDITIONALRULE, BpaConstants.CREATE_ADDITIONAL_RULE_CREATE);
        workflowContainer.setAdditionalRule(BpaConstants.CREATE_ADDITIONAL_RULE_CREATE);
        prepareWorkflow(model, application, workflowContainer);
        model.addAttribute("currentState", application.getCurrentState().getValue());
        model.addAttribute(BpaConstants.BPA_APPLICATION, application);
    }

    protected void prepareWorkflowDataForInspection(final Model model, final OccupancyCertificate occupancyCertificate) {
        model.addAttribute("stateType", occupancyCertificate.getClass().getSimpleName());
        final WorkflowContainer workflowContainer = new WorkflowContainer();
        model.addAttribute(BpaConstants.ADDITIONALRULE, BpaConstants.CREATE_ADDITIONAL_RULE_CREATE);
        workflowContainer.setAdditionalRule(BpaConstants.CREATE_ADDITIONAL_RULE_CREATE);
        prepareWorkflow(model, occupancyCertificate, workflowContainer);
        model.addAttribute("currentState", occupancyCertificate.getCurrentState().getValue());
        model.addAttribute(OCCUPANCY_CERTIFICATE, occupancyCertificate);
    }
    
    protected void prepareWorkflowDataForInspection(final Model model, final InspectionApplication application) {
        model.addAttribute("stateType", application.getClass().getSimpleName());
        final WorkflowContainer workflowContainer = new WorkflowContainer();
        model.addAttribute(BpaConstants.ADDITIONALRULE, BpaConstants.INSPECTIONAPPLICATION);
        workflowContainer.setAdditionalRule(BpaConstants.INSPECTIONAPPLICATION);
        prepareWorkflow(model, application, workflowContainer);
        model.addAttribute("currentState", application.getCurrentState().getValue());
        model.addAttribute(BpaConstants.WFINSPECTIONAPPLICATION , application);
    }

    protected void buildReceiptDetails(Set<EgDemandDetails> egDemandDetails, Set<Receipt> receipts) {
        for (final EgDemandDetails demandDtl : egDemandDetails)
            for (final EgdmCollectedReceipt collRecpt : demandDtl.getEgdmCollectedReceipts())
                if (!collRecpt.isCancelled()) {
                    Receipt receipt = new Receipt();
                    receipt.setReceiptNumber(collRecpt.getReceiptNumber());
                    receipt.setReceiptDate(collRecpt.getReceiptDate());
                    receipt.setReceiptAmt(collRecpt.getAmount());
                    receipts.add(receipt);
                }
    }

    protected String getDesinationNameByPosition(Position pos) {
        return pos.getDeptDesig() != null && pos.getDeptDesig().getDesignation() == null
                ? ""
                : pos.getDeptDesig().getDesignation().getName();
    }

    protected void getAppointmentMsgForOnedayPermit(final BpaApplication bpaApplication, Model model) {
        if (bpaApplication.getIsOneDayPermitApplication() && !bpaApplication.getSlotApplications().isEmpty()) {
            String appmntDetailsMsg = messageSource.getMessage("msg.one.permit.schedule", new String[] {
                    bpaApplication.getOwner().getName(),
                    DateUtils.getDefaultFormattedDate(
                            bpaApplication.getSlotApplications().get(0).getSlotDetail().getSlot().getAppointmentDate()),
                    bpaApplication.getSlotApplications().get(0).getSlotDetail().getAppointmentTime() },
                    LocaleContextHolder.getLocale());
            model.addAttribute("appmntDetailsMsg", appmntDetailsMsg);
        }
    }

    protected boolean validateOnDocumentScrutiny(Model model, BpaStatus status) {
        if (APPLICATION_STATUS_DOC_VERIFIED.equals(status.getCode())) {
            model.addAttribute(MESSAGE, messageSource.getMessage("msg.docverification.already.completed", null, null));
            return true;
        } else if (WF_REJECT_STATE.equals(status.getCode())) {
            model.addAttribute(MESSAGE, messageSource.getMessage("msg.appln.already.intiated.rejection", null, null));
            return true;
        }
        return false;
    }

    protected boolean validateLoginUserAndOwnerIsSame(Model model, User loginUser, Position ownerPosition) {
        List<Assignment> loginUserAssignments = bpaWorkFlowService.getAllActiveAssignmentsForUser(loginUser.getId());
        List<Position> loginUserPositions = Collections.emptyList();
        if (!loginUserAssignments.isEmpty())
            loginUserPositions = bpaWorkFlowService.getAllActiveAssignmentsForUser(securityUtils.getCurrentUser().getId())
                    .stream().map(Assignment::getPosition).collect(Collectors.toList());
        if (!loginUserPositions.contains(ownerPosition)) {
            String designation = getDesinationNameByPosition(ownerPosition);
            User user = workflowHistoryService.getUserPositionByPositionAndDate(ownerPosition.getId(), new Date());
            model.addAttribute(MESSAGE, messageSource.getMessage("msg.position.mismatch",
                    new String[] { user.getUsername().concat("~").concat(designation) }, LocaleContextHolder.getLocale()));
            return true;
        }
        return false;
    }

    protected void getDcrDocumentsUploadMode(final Model model) {
        /*
         * Either one App Config value only must be configured as YES. If all three configured with either YES or NO then manual
         * upload will be enabled by default. If DCR_DOC_AUTO_POPULATE_UPLOAD and either DCR_DOC_MANUAL_UPLOAD or
         * DCR_DOC_AUTO_POPULATE_AND_MANUAL_UPLOAD is configured as YES then will enable manual upload mode.
         */
        model.addAttribute("dcrDocsAutoPopulate",
                bpaUtils.getAppconfigValueByKeyName(DCR_DOC_AUTO_POPULATE_UPLOAD).equalsIgnoreCase(YES));
        model.addAttribute("dcrDocsManuallyUpload",
                bpaUtils.getAppconfigValueByKeyName(DCR_DOC_MANUAL_UPLOAD).equalsIgnoreCase(YES));
        model.addAttribute("dcrDocsAutoPopulateAndManuallyUpload",
                bpaUtils.getAppconfigValueByKeyName(DCR_DOC_AUTO_POPULATE_AND_MANUAL_UPLOAD).equalsIgnoreCase(YES));
    }

}