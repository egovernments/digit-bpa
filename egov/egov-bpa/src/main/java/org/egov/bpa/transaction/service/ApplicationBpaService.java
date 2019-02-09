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
package org.egov.bpa.transaction.service;

import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_APPROVED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_CREATED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_DIGI_SIGNED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_DOC_VERIFIED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_FIELD_INS;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_NOCUPDATED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_REJECTED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_TS_INS_INITIATED;
import static org.egov.bpa.utils.BpaConstants.BPAFEETYPE;
import static org.egov.bpa.utils.BpaConstants.BPAREGISTRATIONFEETYPE;
import static org.egov.bpa.utils.BpaConstants.BPASTATUS_MODULETYPE;
import static org.egov.bpa.utils.BpaConstants.FILESTORE_MODULECODE;
import static org.egov.bpa.utils.BpaConstants.FORWARDED_TO_CLERK;
import static org.egov.bpa.utils.BpaConstants.FWDINGTOLPINITIATORPENDING;
import static org.egov.bpa.utils.BpaConstants.FWD_TO_OVRSR_FOR_FIELD_INS;
import static org.egov.bpa.utils.BpaConstants.MESSAGE;
import static org.egov.bpa.utils.BpaConstants.RECENT_DCRRULE_AMENDMENTDAYS;
import static org.egov.bpa.utils.BpaConstants.ROLE_CITIZEN;
import static org.egov.bpa.utils.BpaConstants.WF_APPROVE_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_CREATED_STATE;
import static org.egov.bpa.utils.BpaConstants.WF_INITIATE_REJECTION_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_LBE_SUBMIT_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_NEW_STATE;
import static org.egov.bpa.utils.BpaConstants.WF_REJECT_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_SAVE_BUTTON;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.egov.bpa.autonumber.PlanPermissionNumberGenerator;
import org.egov.bpa.master.entity.BpaFeeDetail;
import org.egov.bpa.master.entity.ServiceType;
import org.egov.bpa.master.service.BpaSchemeLandUsageService;
import org.egov.bpa.master.service.CheckListDetailService;
import org.egov.bpa.master.service.PostalAddressService;
import org.egov.bpa.master.service.RegistrarOfficeVillageService;
import org.egov.bpa.service.es.BpaIndexService;
import org.egov.bpa.transaction.entity.Applicant;
import org.egov.bpa.transaction.entity.ApplicationDocument;
import org.egov.bpa.transaction.entity.ApplicationFee;
import org.egov.bpa.transaction.entity.ApplicationNocDocument;
import org.egov.bpa.transaction.entity.ApplicationPermitConditions;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.BpaStatus;
import org.egov.bpa.transaction.entity.BuildingSubUsage;
import org.egov.bpa.transaction.entity.BuildingSubUsageDetails;
import org.egov.bpa.transaction.entity.DCRDocument;
import org.egov.bpa.transaction.entity.PermitFee;
import org.egov.bpa.transaction.entity.StoreDCRFiles;
import org.egov.bpa.transaction.notice.PermitApplicationNoticesFormat;
import org.egov.bpa.transaction.notice.impl.DemandDetailsFormatImpl;
import org.egov.bpa.transaction.repository.ApplicationBpaRepository;
import org.egov.bpa.transaction.repository.DcrDocumentRepository;
import org.egov.bpa.transaction.repository.PermitFeeRepository;
import org.egov.bpa.transaction.service.collection.ApplicationBpaBillService;
import org.egov.bpa.transaction.service.collection.BpaDemandService;
import org.egov.bpa.transaction.service.collection.GenericBillGeneratorService;
import org.egov.bpa.transaction.service.messaging.BPASmsAndEmailService;
import org.egov.bpa.utils.BpaUtils;
import org.egov.commons.entity.Source;
import org.egov.demand.model.EgDemand;
import org.egov.infra.admin.master.entity.Boundary;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.admin.master.service.AppConfigValueService;
import org.egov.infra.admin.master.service.RoleService;
import org.egov.infra.admin.master.service.UserService;
import org.egov.infra.config.core.EnvironmentSettings;
import org.egov.infra.custom.CustomImplProvider;
import org.egov.infra.exception.ApplicationRuntimeException;
import org.egov.infra.filestore.entity.FileStoreMapper;
import org.egov.infra.filestore.service.FileStoreService;
import org.egov.infra.persistence.entity.PermanentAddress;
import org.egov.infra.persistence.entity.enums.UserType;
import org.egov.infra.reporting.engine.ReportOutput;
import org.egov.infra.security.utils.SecurityUtils;
import org.egov.infra.utils.ApplicationNumberGenerator;
import org.egov.infra.utils.DateUtils;
import org.egov.infra.utils.FileStoreUtils;
import org.egov.infra.utils.autonumber.AutonumberServiceBeanResolver;
import org.egov.infra.workflow.matrix.entity.WorkFlowMatrix;
import org.egov.portal.entity.Citizen;
import org.egov.portal.service.CitizenService;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly = true)
public class ApplicationBpaService extends GenericBillGeneratorService {

    private static final Logger LOG = getLogger(BpaUtils.class);
    private static final String APPLICATION_STATUS = "application.status";
    private static final String NOC_UPDATION_IN_PROGRESS = "NOC updation in progress";
    public static final String UNCHECKED = "unchecked";
    public static final String ERROR_OCCURRED_WHILE_GETTING_INPUTSTREAM = "Error occurred while getting inputstream";
    private static final String MODULE_NAME = "BPA";
    private static final String PDF_QR_ENBLD = "DCRPDFQRCODEENABLED";

    @Autowired
    protected ApplicationFeeService applicationFeeService;
    @Autowired
    protected BpaDemandService bpaDemandService;
    @Autowired
    private ApplicationBpaRepository applicationBpaRepository;
    @Autowired
    private BpaStatusService bpaStatusService;
    @Autowired
    private BpaUtils bpaUtils;
    @Autowired
    private ApplicationBpaBillService applicationBpaBillService;
    @Autowired
    private GenericBillGeneratorService genericBillGeneratorService;
    @Autowired
    private ApplicationNumberGenerator applicationNumberGenerator;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private FileStoreService fileStoreService;
    @Autowired
    private CheckListDetailService checkListDetailService;
    @Autowired
    private SecurityUtils securityUtils;
    @Autowired
    private AutonumberServiceBeanResolver beanResolver;
    @Autowired
    private ApplicationBpaFeeCalculationService applicationBpaFeeCalculationService;
    @Autowired
    private EnvironmentSettings environmentSettings;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleService roleService;
    @Autowired
    private CitizenService citizenService;
    @Autowired
    private PostalAddressService postalAddressService;
    @Autowired
    private BpaSchemeLandUsageService bpaSchemeLandUsageService;
    @Autowired
    private BuildingFloorDetailsService buildingFloorDetailsService;
    @Autowired
    private RegistrarOfficeVillageService registrarOfficeVillageService;
    @Autowired
    private ExistingBuildingFloorDetailsService existingBuildingFloorDetailsService;
    @Autowired
    private BpaApplicationPermitConditionsService bpaApplicationPermitConditionsService;
    @Autowired
    private BpaIndexService bpaIndexService;
    @Autowired
    private UserService userService;
    @Autowired
    private ApplicantService applicantService;
    @Autowired
    private DcrDocumentRepository dcrDocumentRepository;
    @Autowired
    private AppConfigValueService appConfigValuesService;
    @Autowired
    private BPASmsAndEmailService bpaSmsAndEmailService;
    @Autowired
    private FileStoreUtils fileStoreUtils;
    @Autowired
    private CustomImplProvider specificNoticeService;
    @Autowired
	@Qualifier("parentMessageSource")
	private MessageSource bpaMessageSource;
    @Autowired
    private DcrRestService dcrRestService;
    @Autowired
    private PermitFeeRepository permitFeeRepository;
    
    public Session getCurrentSession() {
        return entityManager.unwrap(Session.class);
    }

    @Transactional
    public BpaApplication createNewApplication(final BpaApplication application, String workFlowAction) {
        final Boundary boundaryObj = bpaUtils.getBoundaryById(application.getWardId() != null ? application.getWardId()
                : getZone(application));
        buildSiteDetails(application, boundaryObj);
        buildExistingAndProposedBuildingDetails(application);
        application.getApplicationAmenity().clear();
        application.setApplicationAmenity(application.getApplicationAmenityTemp());
        application.setApplicationNumber(applicationNumberGenerator.generate());
        //buildRegistrarOfficeForVillage(application);
        persistBpaNocDocuments(application);
        application.setDcrDocuments(persistApplnDCRDocuments(application));
        buildBuildingSubUsage(application);
        /*
         * if (bpaApplicationValidationService.isEdcrInetgrationRequired(application.getServiceType().getCode(),
         * application.getOccupancy().getDescription())) { buildApplicationDcrDocs(application, request); }
         */
        if (!application.getBuildingDetail().isEmpty())
            application.setTotalBuiltUpArea(bpaUtils.getBlockWiseOccupancyAndBuiltupArea(application.getBuildingDetail())
                    .entrySet().stream().map(Map.Entry::getValue).reduce(BigDecimal.ZERO, BigDecimal::add));
        final BpaStatus bpaStatus = getStatusByCodeAndModuleType(APPLICATION_STATUS_CREATED);
        application.setStatus(bpaStatus);
        setSource(application);
        Long approvalPosition = null;
        application.setDemand(applicationBpaBillService.createDemand(application));
        if (!bpaUtils.logedInuseCitizenOrBusinessUser()) {
            WorkFlowMatrix wfMatrix = bpaUtils.getWfMatrixByCurrentState(application.getIsOneDayPermitApplication(),
                    application.getStateType(), WF_CREATED_STATE);
            String currentState = WF_CREATED_STATE;
            if (application.getAdmissionfeeAmount() != null
                    && application.getAdmissionfeeAmount().compareTo(BigDecimal.ZERO) == 0) {
                wfMatrix = bpaUtils.getWfMatrixByCurrentState(application.getIsOneDayPermitApplication(),
                        application.getStateType(), WF_NEW_STATE);
                currentState = WF_NEW_STATE;
            }
            if (wfMatrix != null)
                approvalPosition = bpaUtils.getUserPositionIdByZone(wfMatrix.getNextDesignation(),
                        application.getSiteDetail().get(0) != null
                                && application.getSiteDetail().get(0).getElectionBoundary() != null
                                        ? application.getSiteDetail().get(0).getElectionBoundary().getId()
                                        : null);
            bpaUtils.redirectToBpaWorkFlow(approvalPosition, application, currentState, null, null,
                    null);
        }
        BpaApplication bpaApplicationResponse = applicationBpaRepository.saveAndFlush(application);
        bpaIndexService.updateIndexes(bpaApplicationResponse);
        return bpaApplicationResponse;
    }

    public void buildBuildingSubUsage(BpaApplication application) {
        application.getBuildingSubUsages().forEach(subUsage -> {
            subUsage.setApplication(application);
            subUsage.getSubUsageDetails().forEach(subUsageDtl -> {
                subUsageDtl.setBuildingSubUsage(subUsage);
            });
        });
    }

    private void buildSiteDetails(final BpaApplication application, final Boundary boundaryObj) {
        application.getSiteDetail().get(0).setAdminBoundary(boundaryObj);
        application.getSiteDetail().get(0).setApplication(application);
        if (application.getSiteDetail().get(0).getLandUsageId() != null)
            application.getSiteDetail().get(0)
                    .setLandUsage((bpaSchemeLandUsageService.findById(application.getSiteDetail().get(0).getLandUsageId())));
        /*
         * application.getSiteDetail().get(0)
         * .setPostalAddress(postalAddressService.findById(application.getSiteDetail().get(0).getPostalId()));
         */
    }

    private void setSource(final BpaApplication application) {
        if (bpaUtils.logedInuseCitizenOrBusinessUser())
            application.setSource(Source.CITIZENPORTAL);
        else
            application.setSource(Source.SYSTEM);
    }

    private void buildRegistrarOfficeForVillage(final BpaApplication application) {
        if (application.getSiteDetail().get(0).getRegistrarVillageId() != null)
            application.getSiteDetail().get(0).setRegistrarOffice(
                    registrarOfficeVillageService.findById(application.getSiteDetail().get(0).getRegistrarVillageId()));
    }

    private Long getZone(final BpaApplication application) {
        return application.getZoneId();
    }

    private void buildPermitConditions(final BpaApplication application) {
        if (!application.getDynamicPermitConditionsTemp().isEmpty() || !application.getStaticPermitConditionsTemp().isEmpty()) {
            bpaApplicationPermitConditionsService.delete(application.getDynamicPermitConditions());
            bpaApplicationPermitConditionsService.delete(application.getStaticPermitConditions());
            bpaApplicationPermitConditionsService.delete(application.getAdditionalPermitConditions());
            application.getDynamicPermitConditions().clear();
            application.getStaticPermitConditions().clear();
            application.getAdditionalPermitConditions().clear();
            List<ApplicationPermitConditions> additionalPermitConditions = new ArrayList<>();
            for (ApplicationPermitConditions addnlCondition : application.getAdditionalPermitConditionsTemp()) {
                if (addnlCondition != null && addnlCondition.getAdditionalPermitCondition() != null)
                    additionalPermitConditions.add(addnlCondition);
            }
            application.setDynamicPermitConditions(application.getDynamicPermitConditionsTemp());
            application.setStaticPermitConditions(application.getStaticPermitConditionsTemp());
            application.setAdditionalPermitConditions(additionalPermitConditions);
        }
    }

    private void buildRejectionReasons(final BpaApplication application) {
        bpaApplicationPermitConditionsService.delete(application.getRejectionReasons());
        bpaApplicationPermitConditionsService.delete(application.getAdditionalPermitConditions());
        application.getAdditionalPermitConditions().clear();
        application.getRejectionReasons().clear();
        List<ApplicationPermitConditions> additionalRejectReasons = new ArrayList<>();
        for (ApplicationPermitConditions addnlReason : application.getAdditionalRejectReasonsTemp()) {
            addnlReason.setApplication(application);
            if (addnlReason != null && addnlReason.getAdditionalPermitCondition() != null)
                additionalRejectReasons.add(addnlReason);
        }
        application.setRejectionReasons(application.getRejectionReasonsTemp());
        application.setAdditionalPermitConditions(additionalRejectReasons);
    }

    private void persistBpaNocDocuments(final BpaApplication application) {

        processAndStoreNocDocuments(application);
    }

    public BpaStatus getStatusByCodeAndModuleType(final String code) {
        return bpaStatusService.findByModuleTypeAndCode(BPASTATUS_MODULETYPE, code);
    }

    @Transactional
    public void saveAndFlushApplication(final BpaApplication application, String workFlowAction) {

        if (!application.getBuildingSubUsages().isEmpty())
            for (BuildingSubUsage subUsage : application.getBuildingSubUsages())
                for (BuildingSubUsageDetails subUsageDetails : subUsage.getSubUsageDetails()) {
                    subUsageDetails.getSubUsages().clear();
                    subUsageDetails.setSubUsages(subUsageDetails.getSubUsagesTemp());
                }

        buildBuildingSubUsage(application);
        persistBpaNocDocuments(application);
        buildPermitConditions(application);
        application.setDcrDocuments(persistApplnDCRDocuments(application));
        //persistPostalAddress(application);
        //buildRegistrarOfficeForVillage(application);
        buildSchemeLandUsage(application);
        applicationBpaRepository.saveAndFlush(application);
        if (workFlowAction != null && workFlowAction.equals(WF_LBE_SUBMIT_BUTTON)
                && (bpaUtils.logedInuseCitizenOrBusinessUser())) {
            bpaIndexService.updateIndexes(application);
        }
    }

    @Transactional
    public void saveAndFlushApplication(final BpaApplication application) {
        if (Boolean.valueOf(appConfigValuesService.getConfigValuesByModuleAndKey(MODULE_NAME,
                PDF_QR_ENBLD).get(0).getValue()) && application.getStatus().getCode().equals(APPLICATION_STATUS_APPROVED)
                && !bpaDemandService.checkAnyTaxIsPendingToCollect(application)) {
            appendQrCodeWithDcrDocuments(application);
        }
        persistBpaNocDocuments(application);
        buildPermitConditions(application);
        //persistPostalAddress(application);
        //buildRegistrarOfficeForVillage(application);
        buildSchemeLandUsage(application);
        applicationBpaRepository.saveAndFlush(application);
    }

    private void persistPostalAddress(final BpaApplication application) {
        if (application.getSiteDetail().get(0).getPostalId() != null) {
            application.getSiteDetail().get(0)
                    .setPostalAddress(postalAddressService.findById(application.getSiteDetail().get(0).getPostalId()));
        }
    }

    private void buildSchemeLandUsage(final BpaApplication application) {
        if (application.getSiteDetail() != null && application.getSiteDetail().get(0) != null
                && application.getSiteDetail().get(0).getLandUsageId() != null) {
            application.getSiteDetail().get(0)
                    .setLandUsage(bpaSchemeLandUsageService.findById(application.getSiteDetail().get(0).getLandUsageId()));
        }
    }

    @Transactional
    public String redirectToCollectionOnForward(final BpaApplication application, Model model) {
        persistBpaNocDocuments(application);
        buildExistingAndProposedBuildingDetails(application);
        return genericBillGeneratorService.generateBillAndRedirectToCollection(application, model);
    }

    public void buildExistingAndProposedBuildingDetails(final BpaApplication application) {
        existingBuildingFloorDetailsService.buildExistingBuildingFloorDetails(application);
        buildingFloorDetailsService.buildProposedBuildingFloorDetails(application);
    }

    @Transactional
    public BpaApplication updateApplication(final BpaApplication application, Long approvalPosition,
            String workFlowAction, BigDecimal amountRule) throws IOException {
        application.setSentToPreviousOwner(false);
        application.setDcrDocuments(persistApplnDCRDocuments(application));
        persistBpaNocDocuments(application);
        buildExistingAndProposedBuildingDetails(application);
        //persistPostalAddress(application);
        buildSchemeLandUsage(application);
        // For one day permit
        if (application.getIsOneDayPermitApplication()
                && APPLICATION_STATUS_DOC_VERIFIED.equalsIgnoreCase(application.getStatus().getCode())) {
        	PermitFee permitFee = applicationBpaFeeCalculationService.calculateBpaSanctionFees(application);
		
        	ApplicationFee applicationFee = applicationFeeService.saveApplicationFee(permitFee.getApplicationFee());
            permitFee.setApplicationFee(applicationFee);
            permitFeeRepository.save(permitFee);
        	application.setDemand(bpaDemandService.generateDemandUsingSanctionFeeList(permitFee.getApplicationFee(), permitFee.getApplication().getDemand()));
        }
        if (!WF_SAVE_BUTTON.equalsIgnoreCase(workFlowAction)
                && APPLICATION_STATUS_FIELD_INS.equalsIgnoreCase(application.getStatus().getCode())
                && NOC_UPDATION_IN_PROGRESS.equalsIgnoreCase(application.getState().getValue())) {
			PermitFee permitFee = applicationBpaFeeCalculationService.calculateBpaSanctionFees(application);

        	ApplicationFee applicationFee = applicationFeeService.saveApplicationFee(permitFee.getApplicationFee());
            permitFee.setApplicationFee(applicationFee);
            permitFeeRepository.save(permitFee);
        	application.setDemand(bpaDemandService.generateDemandUsingSanctionFeeList(permitFee.getApplicationFee(), permitFee.getApplication().getDemand()));
         }
        if (WF_APPROVE_BUTTON.equals(workFlowAction)) {
            application.setPlanPermissionNumber(generatePlanPermissionNumber(application));
            application.setPlanPermissionDate(new Date());

            PermitApplicationNoticesFormat bpaNoticeFeature = (PermitApplicationNoticesFormat) specificNoticeService.find(
                    DemandDetailsFormatImpl.class,
                    specificNoticeService.getCityDetails());
            ReportOutput reportOutput = bpaNoticeFeature
                    .generateNotice(findByApplicationNumber(application.getApplicationNumber()));
            bpaSmsAndEmailService.sendSmsAndEmailOnApplicationApproval(application, reportOutput);

        }
        if (APPLICATION_STATUS_APPROVED.equals(application.getStatus().getCode())
                || APPLICATION_STATUS_DIGI_SIGNED.equalsIgnoreCase(application.getStatus().getCode())
                || WF_APPROVE_BUTTON.equals(workFlowAction)) {
            buildPermitConditions(application);
        }
        if (APPLICATION_STATUS_TS_INS_INITIATED.equals(application.getStatus().getCode())) {
            application.setTownSurveyorInspectionRequire(false);
        }
        if (application.getFiles() != null && application.getFiles().length > 0) {
            Set<FileStoreMapper> tsInspnDocs = new HashSet<>();
            tsInspnDocs.addAll(application.getTsInspnSupportDocs());
            tsInspnDocs.addAll(addToFileStore(application.getFiles()));
            application.setTsInspnSupportDocs(tsInspnDocs);
        }
        if (WF_REJECT_BUTTON.equalsIgnoreCase(workFlowAction)
                || WF_INITIATE_REJECTION_BUTTON.equalsIgnoreCase(workFlowAction)
                || APPLICATION_STATUS_REJECTED.equalsIgnoreCase(application.getStatus().getCode())
                || (!WF_APPROVE_BUTTON.equals(workFlowAction)
                        && APPLICATION_STATUS_NOCUPDATED.equals(application.getStatus().getCode()))) {
            buildRejectionReasons(application);
        }
        application.setLPRequestInitiated(FWDINGTOLPINITIATORPENDING.equalsIgnoreCase(application.getState().getNextAction()));
        final BpaApplication updatedApplication = applicationBpaRepository.save(application);
        if (!WF_SAVE_BUTTON.equalsIgnoreCase(workFlowAction) && updatedApplication.getCurrentState() != null
                && !updatedApplication.getCurrentState().getValue().equals(WF_NEW_STATE)) {
            bpaUtils.redirectToBpaWorkFlow(approvalPosition, application, application.getCurrentState().getValue(),
                    application.getApprovalComent(), workFlowAction, amountRule);
            bpaIndexService.updateIndexes(application);
        }
        return updatedApplication;
    }

    private void appendQrCodeWithDcrDocuments(BpaApplication application) {
        List<DCRDocument> dcrDocuments = dcrDocumentRepository.findByApplication(application);
        for (DCRDocument dcrDocument : dcrDocuments) {
            if (LOG.isInfoEnabled())
                LOG.info("#### Dcr Document ####", dcrDocument.getId());
            for (StoreDCRFiles file : dcrDocument.getDcrAttachments()) {
                if (LOG.isInfoEnabled())
                    LOG.info("#### file ####", file.getId());
                bpaUtils.addQrCodeToPdfDocuments(file.getFileStoreMapper(), application);
            }
        }
    }

    public void persistOrUpdateApplicationDocument(final BpaApplication bpaApplication) {
        processAndStoreApplicationDocuments(bpaApplication);
    }

    public BigDecimal setAdmissionFeeAmountForRegistrationWithAmenities(final Long serviceType, List<ServiceType> amenityList) {
        BigDecimal admissionfeeAmount;
        if (serviceType != null)
            admissionfeeAmount = getTotalFeeAmountByPassingServiceTypeandArea(serviceType, amenityList,
                    BPAFEETYPE);
        else
            admissionfeeAmount = BigDecimal.ZERO;
        return admissionfeeAmount;
    }
    
    public BigDecimal setRegistrationAmountForRegistrationWithAmenities(final Long serviceType, List<ServiceType> amenityList) {
        BigDecimal admissionfeeAmount;
        if (serviceType != null)
            admissionfeeAmount = getTotalFeeAmountByPassingServiceTypeandArea(serviceType, amenityList,
            		BPAREGISTRATIONFEETYPE);
        else
            admissionfeeAmount = BigDecimal.ZERO;
        return admissionfeeAmount;
    }

    private BigDecimal getTotalFeeAmountByPassingServiceTypeandArea(final Long serviceTypeId, List<ServiceType> amenityList,
            final String feeType) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<Long> serviceTypeList = new ArrayList<>();
        serviceTypeList.add(serviceTypeId);
        for (ServiceType temp : amenityList) {
            serviceTypeList.add(temp.getId());
        }
        if (serviceTypeId != null) {
            final Criteria feeCrit = applicationBpaBillService.getBpaFeeCriteria(serviceTypeList, feeType);
            @SuppressWarnings(UNCHECKED)
            final List<BpaFeeDetail> bpaFeeDetails = feeCrit.list();
            for (final BpaFeeDetail feeDetail : bpaFeeDetails)
                totalAmount = totalAmount.add(BigDecimal.valueOf(feeDetail.getAmount()));
        } else
            throw new ApplicationRuntimeException("Service Type Id is mandatory.");

        return totalAmount;
    }

    public BigDecimal getTotalFeeAmountByPassingServiceTypeAndAmenities(List<Long> serviceTypeIds) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        if (!serviceTypeIds.isEmpty()) {
            final Criteria feeCrit = applicationBpaBillService.getBpaFeeCriteria(serviceTypeIds, BPAFEETYPE);
            @SuppressWarnings(UNCHECKED)
            final List<BpaFeeDetail> bpaFeeDetails = feeCrit.list();
            for (final BpaFeeDetail feeDetail : bpaFeeDetails)
                totalAmount = totalAmount.add(BigDecimal.valueOf(feeDetail.getAmount()));
        } else
            throw new ApplicationRuntimeException("Service Type Id is mandatory.");

        return totalAmount;
    }

    public BpaApplication getApplicationByDemand(final EgDemand demand) {
        return applicationBpaRepository.findByDemand(demand);
    }

    public BpaApplication findByApplicationNumber(final String applicationNumber) {
        return applicationBpaRepository.findByApplicationNumber(applicationNumber);
    }

    public BpaApplication findById(final Long applicationId) {
        return applicationBpaRepository.findOne(applicationId);
    }

    public List<BpaApplication> findApplicationByEDCRNumber(final String eDcrNumber) {
        return applicationBpaRepository.findApplicationByEDcrNumberOrderByIdDesc(eDcrNumber);
    }

    public BpaApplication findByPermitNumber(final String permitNumber) {
        return applicationBpaRepository.findByPlanPermissionNumber(permitNumber);
    }

    private void processAndStoreNocDocuments(final BpaApplication bpaApplication) {
        final User user = securityUtils.getCurrentUser();
        if (!bpaApplication.getApplicationNOCDocument().isEmpty()
                && null == bpaApplication.getApplicationNOCDocument().get(0).getId())
            for (final ApplicationNocDocument nocDocument : bpaApplication.getApplicationNOCDocument()) {
                nocDocument.setChecklist(
                        checkListDetailService.load(nocDocument.getChecklist().getId()));
                nocDocument.setApplication(bpaApplication);
                nocDocument.setCreateduser(user);
                buildNocFiles(nocDocument);
            }
        else
            for (final ApplicationNocDocument nocDocument : bpaApplication.getApplicationNOCDocument())
                buildNocFiles(nocDocument);
    }

    private void buildNocFiles(ApplicationNocDocument nocDocument) {
        if (nocDocument.getFiles() != null && nocDocument.getFiles().length > 0) {
            Set<FileStoreMapper> existingFiles = new HashSet<>();
            existingFiles.addAll(nocDocument.getNocSupportDocs());
            existingFiles.addAll(addToFileStore(nocDocument.getFiles()));
            nocDocument.setNocSupportDocs(existingFiles);
            nocDocument.setIssubmitted(true);
        }
    }

    private void processAndStoreApplicationDocuments(final BpaApplication bpaApplication) {
        if (!bpaApplication.getApplicationDocument().isEmpty() && null == bpaApplication.getApplicationDocument().get(0).getId())
            for (final ApplicationDocument applicationDocument : bpaApplication.getApplicationDocument()) {
                applicationDocument.setChecklistDetail(
                        checkListDetailService.load(applicationDocument.getChecklistDetail().getId()));
                applicationDocument.setApplication(bpaApplication);
                buildApplicationDocFiles(applicationDocument);
            }
        else
            for (final ApplicationDocument applicationDocument : bpaApplication.getApplicationDocument()) {
                buildApplicationDocFiles(applicationDocument);
            }
    }

    private void buildApplicationDocFiles(ApplicationDocument applicationDocument) {
        if (applicationDocument.getFiles() != null && applicationDocument.getFiles().length > 0) {
            Set<FileStoreMapper> existingFiles = new HashSet<>();
            existingFiles.addAll(applicationDocument.getSupportDocs());
            existingFiles.addAll(addToFileStore(applicationDocument.getFiles()));
            applicationDocument.setSupportDocs(existingFiles);
            applicationDocument.setIssubmitted(true);
        }
    }

    private List<DCRDocument> persistApplnDCRDocuments(final BpaApplication application) {
        List<DCRDocument> dcrDocuments = new ArrayList<>();
        if (!application.getDcrDocuments().isEmpty() && null == application.getDcrDocuments().get(0).getId())
            for (final DCRDocument dcrDocument : application.getDcrDocuments()) {
                dcrDocument.setApplication(application);
                DCRDocument dcrDocumentRes = buildAutoPopualtedDCRFiles(dcrDocument);
                buildDCRFiles(dcrDocumentRes);
                dcrDocuments.add(dcrDocumentRes);
            }
        else
            for (final DCRDocument dcrDocument : application.getDcrDocuments()) {
                DCRDocument dcrDocumentRes = buildAutoPopualtedDCRFiles(dcrDocument);
                buildDCRFiles(dcrDocumentRes);
                dcrDocuments.add(dcrDocumentRes);
            }
        return dcrDocuments;
    }

    // Will save manually uploaded dcr document pdf's
    private DCRDocument buildDCRFiles(DCRDocument dcrDocument) {
        Set<StoreDCRFiles> storeDCRFiles = new HashSet<>();
        storeDCRFiles.addAll(dcrDocument.getDcrAttachments());
        if (dcrDocument.getFiles() != null && dcrDocument.getFiles().length > 0) {
            for (MultipartFile file : dcrDocument.getFiles()) {
                if (!file.isEmpty()) {
                    StoreDCRFiles storeDCRFile = new StoreDCRFiles();
                    storeDCRFile.setDcrDocument(dcrDocument);
                    storeDCRFile.setFileStoreMapper(addToFileStore(file));
                    storeDCRFile.setAutoPopulated(false);
                    storeDCRFiles.add(storeDCRFile);
                }
            }
        }
        if (!storeDCRFiles.isEmpty())
            dcrDocument.setDcrAttachments(storeDCRFiles);
        return dcrDocument;
    }

    // Will save auto populated dcr document pdf's from dcr system
    private DCRDocument buildAutoPopualtedDCRFiles(DCRDocument dcrDocument) {
        Set<StoreDCRFiles> storeDCRFiles = new HashSet<>();
        storeDCRFiles.addAll(dcrDocument.getDcrAttachments());
        if (dcrDocument.getFileStoreIds() != null && dcrDocument.getFileStoreIds().length > 0) {
            for (String fileStoreId : dcrDocument.getFileStoreIds()) {
                if (!fileStoreId.isEmpty()) {
                    Path file = fileStoreService.fetchAsPath(fileStoreId, "Digit DCR");
                    Optional<FileStoreMapper> fileStoreMapper = fileStoreUtils.getFileStoreMapper(fileStoreId);
                    FileStoreMapper savedFileStoreMapper = null;
                    try {
                        savedFileStoreMapper = fileStoreService.store(new ByteArrayInputStream(Files.readAllBytes(file)),
                                fileStoreMapper.isPresent() ? fileStoreMapper.get().getFileName() : file.toFile().getName(),
                                "application/pdf", FILESTORE_MODULECODE);
                    } catch (IOException e) {
                        throw new ApplicationRuntimeException("Error occurred, while saving dcr documents!!!!!!", e);
                    }
                    StoreDCRFiles storeDCRFile = new StoreDCRFiles();
                    storeDCRFile.setDcrDocument(dcrDocument);
                    storeDCRFile.setFileStoreMapper(savedFileStoreMapper);
                    storeDCRFile.setAutoPopulated(true);
                    storeDCRFiles.add(storeDCRFile);
                }
            }
        }
        if (!storeDCRFiles.isEmpty())
            dcrDocument.setDcrAttachments(storeDCRFiles);
        return dcrDocument;
    }

    public Set<FileStoreMapper> addToFileStore(final MultipartFile... files) {
        if (ArrayUtils.isNotEmpty(files))
            return Arrays.asList(files).stream().filter(file -> !file.isEmpty()).map(file -> {
                try {
                    return fileStoreService.store(file.getInputStream(), file.getOriginalFilename(),
                            file.getContentType(), FILESTORE_MODULECODE);
                } catch (final Exception e) {
                    throw new ApplicationRuntimeException(ERROR_OCCURRED_WHILE_GETTING_INPUTSTREAM, e);
                }
            }).collect(Collectors.toSet());
        else
            return Collections.emptySet();
    }

    public FileStoreMapper addToFileStore(final MultipartFile file) {
        FileStoreMapper fileStoreMapper = null;
        try {
            fileStoreMapper = fileStoreService.store(file.getInputStream(), file.getOriginalFilename(),
                    file.getContentType(), FILESTORE_MODULECODE);
        } catch (final IOException e) {
            throw new ApplicationRuntimeException(ERROR_OCCURRED_WHILE_GETTING_INPUTSTREAM, e);
        }
        return fileStoreMapper;
    }

    public String generatePlanPermissionNumber(final BpaApplication application) {
        final PlanPermissionNumberGenerator planPermissionNumber = beanResolver
                .getAutoNumberServiceFor(PlanPermissionNumberGenerator.class);
        return planPermissionNumber.generatePlanPermissionNumber(application.getServiceType());
    }

    public Boolean checkAnyTaxIsPendingToCollect(BpaApplication bpaApplication) {
        return bpaUtils.checkAnyTaxIsPendingToCollect(bpaApplication.getDemand());
    }

    public Boolean applicationinitiatedByNonEmployee(BpaApplication bpaApplication) {
        return bpaUtils.applicationInitiatedByNonEmployee(bpaApplication.getCreatedBy());
    }

    public void buildOwnerDetails(final BpaApplication bpaApplication) {
        Applicant existApplicant = applicantService.findByNameAndMobileNumberAndGenderAndType(bpaApplication.getOwner().getName(),
                bpaApplication.getOwner().getUser().getMobileNumber(), bpaApplication.getOwner().getGender(),
                UserType.CITIZEN);
        if (existApplicant == null) {
            Applicant applicant = new Applicant();
            applicant.setName(bpaApplication.getOwner().getName());
            applicant.setAddress(bpaApplication.getOwner().getAddress());
            applicant.setGender(bpaApplication.getOwner().getGender());
            applicant.setAadhaarNumber(bpaApplication.getOwner().getAadhaarNumber());
            applicant.setEmailId(bpaApplication.getOwner().getEmailId());
            applicant.setUser(getCitizen(bpaApplication));
            bpaApplication.setOwner(applicant);
        } else {
            bpaApplication.setOwner(existApplicant);
        }
        if (!bpaApplication.getOwner().getUser().isActive())
            bpaApplication.getOwner().getUser().setActive(true);
    }

    private Citizen getCitizen(BpaApplication bpaApplication) {
        Citizen citizen;
        List<Citizen> citizensWithMobNo = citizenService
                .getCitizenByMobileNumberAndType(bpaApplication.getOwner().getUser().getMobileNumber(), UserType.CITIZEN);
        Citizen existingCitizen = null;
        if (!citizensWithMobNo.isEmpty())
            existingCitizen = citizensWithMobNo.get(0);
        if (existingCitizen == null) {
            List<User> busUsersWithAadhaar = userService
                    .getUserByAadhaarNumberAndType(bpaApplication.getOwner().getAadhaarNumber(), UserType.BUSINESS);
            List<User> citizensWithAadhaar = userService
                    .getUserByAadhaarNumberAndType(bpaApplication.getOwner().getAadhaarNumber(), UserType.CITIZEN);
            if (!busUsersWithAadhaar.isEmpty() || !citizensWithAadhaar.isEmpty()) {
                bpaApplication.getOwner().setAadhaarNumber(StringUtils.EMPTY);
            }
            List<User> citizensWithEmail = userService.getUsersByTypeAndEmailId(bpaApplication.getOwner().getEmailId(),
                    UserType.CITIZEN);
            List<User> busUsersWithEmail = userService.getUsersByTypeAndEmailId(bpaApplication.getOwner().getEmailId(),
                    UserType.BUSINESS);
            if (!busUsersWithEmail.isEmpty() || !citizensWithEmail.isEmpty()) {
                bpaApplication.getOwner().setEmailId(StringUtils.EMPTY);
            }
            citizen = createApplicantAsCitizen(bpaApplication);
            bpaApplication.setMailPwdRequired(true);
        } else {
            citizen = existingCitizen;
        }
        return citizen;
    }

    /**
     * @param bpaApplication
     * @return citizen
     */
    public Citizen createApplicantAsCitizen(BpaApplication bpaApplication) {
        Citizen citizen = new Citizen();
        citizen.setMobileNumber(bpaApplication.getOwner().getUser().getMobileNumber());
        citizen.setEmailId(bpaApplication.getOwner().getEmailId());
        citizen.setGender(bpaApplication.getOwner().getGender());
        citizen.setName(bpaApplication.getOwner().getName());
        String userName = bpaUtils.generateUserName(bpaApplication.getOwner().getName());
        User isUserExist = userService.getUserByUsername(userName);
        if (isUserExist == null)
            citizen.setUsername(userName);
        else
            citizen.setUsername(bpaUtils.generateUserName(bpaApplication.getOwner().getName()));
        citizen.setPassword(passwordEncoder.encode(bpaApplication.getOwner().getUser().getMobileNumber()));
        PermanentAddress address = new PermanentAddress();
        address.setStreetRoadLine(bpaApplication.getOwner().getAddress());
        citizen.addAddress(address);
        citizen.updateNextPwdExpiryDate(environmentSettings.userPasswordExpiryInDays());
        citizen.setAadhaarNumber(bpaApplication.getOwner().getAadhaarNumber());
        citizen.setActive(true);
        citizen.addRole(roleService.getRoleByName(ROLE_CITIZEN));
        return citizen;
    }

    @Transactional
    public void saveBpaApplication(BpaApplication bpaApp) {
        applicationBpaRepository.saveAndFlush(bpaApp);
    }

    public void saveApplicationForScheduler(BpaApplication bpaApp) {
        applicationBpaRepository.save(bpaApp);
    }

    public List<BpaApplication> findByStatusListOrderByCreatedDate(List<BpaStatus> listOfBpaStatus) {
        return applicationBpaRepository.findByStatusListOrderByCreatedDateAsc(listOfBpaStatus);
    }

    @SuppressWarnings(UNCHECKED)
    public List<BpaApplication> getBpaApplicationsForScheduleAndReSchedule(List<BpaStatus> bpaStatusList,
            List<Boundary> boundaryList,
            Integer totalAvailableSlots) {
        final Criteria criteria = entityManager.unwrap(Session.class)
                .createCriteria(BpaApplication.class, "application")
                .createAlias("application.siteDetail", "siteDetail")
                .createAlias("application.demand", "demand");
        criteria.add(Restrictions.in(APPLICATION_STATUS, bpaStatusList));
        criteria.createAlias("application.state", "state")
                .add(Restrictions.not(Restrictions.in("state.nextAction", FORWARDED_TO_CLERK, FWD_TO_OVRSR_FOR_FIELD_INS)));
        criteria.add(Restrictions.in("siteDetail.adminBoundary", boundaryList));
        criteria.add(Restrictions.eq("application.isOneDayPermitApplication", false));
        criteria.add(Restrictions.eq("application.failureInScheduler", false));

        criteria.add(Restrictions.leProperty("demand.baseDemand", "demand.amtCollected"));
        criteria.addOrder(Order.desc(APPLICATION_STATUS));
        criteria.addOrder(Order.asc("application.applicationDate"));
        criteria.addOrder(Order.asc("application.createdDate"));
        criteria.setMaxResults(totalAvailableSlots);
        return criteria.list();

    }

    @SuppressWarnings(UNCHECKED)
    public List<BpaApplication> getOneDayPermitAppForAppointment(BpaStatus bpaStatus, Boundary ward, List<Boundary> boundaryList,
            Integer totalAvailableSlots) {
        final Criteria criteria = entityManager.unwrap(Session.class)
                .createCriteria(BpaApplication.class, "application")
                .createAlias("application.siteDetail", "siteDetail")
                .createAlias("application.demand", "demand");
        criteria.add(Restrictions.in("siteDetail.adminBoundary", boundaryList));
        criteria.add(Restrictions.eq(APPLICATION_STATUS, bpaStatus));
        criteria.add(Restrictions.eq("application.isOneDayPermitApplication", true));
        criteria.add(Restrictions.eq("application.failureInScheduler", false));
        criteria.add(Restrictions.eq("siteDetail.electionBoundary", ward));
        criteria.add(Restrictions.leProperty("demand.baseDemand", "demand.amtCollected"));
        criteria.addOrder(Order.desc(APPLICATION_STATUS));
        criteria.addOrder(Order.asc("application.applicationDate"));
        criteria.addOrder(Order.asc("application.createdDate"));
        criteria.setMaxResults(totalAvailableSlots);
        return criteria.list();

    }
    /***
     * Validate the dcr number within the specified date range.Configuration value used to decide the validity.
     * Based on number of days configured, DCR plan will be compared.
     * @param eDcrNumber
     * @param request
     * @return
     */
    public Map<String, String> checkEdcrExpiry(final String eDcrNumber, HttpServletRequest request) {
    	Map<String, String> eDcrExpiryDetails = new HashMap<>();
		int expirydays = Integer.parseInt(bpaUtils.getAppconfigValueByKeyName(RECENT_DCRRULE_AMENDMENTDAYS));
	    Date dcrCreatedDate = dcrRestService.getDcrCreatedDate(eDcrNumber,request);
	    
	    eDcrExpiryDetails.put("isExpired", "false");
		eDcrExpiryDetails.put(MESSAGE, "Not expired");
		
		if (dcrCreatedDate != null) {
			int diffInDays = DateUtils.daysBetween(dcrCreatedDate, new Date());
			if (diffInDays <= expirydays) {
				eDcrExpiryDetails.put("isExpired", "false");
				eDcrExpiryDetails.put(MESSAGE, "Not expired");
			} else {
				String message = bpaMessageSource.getMessage("msg.dcr.expiry", new String[] {
						securityUtils.getCurrentUser().getName(), eDcrNumber, Integer.toString(expirydays) }, null);
				eDcrExpiryDetails.put("isExpired", "true");
				eDcrExpiryDetails.put(MESSAGE, message);
			}
		}
		return eDcrExpiryDetails;
	}

}