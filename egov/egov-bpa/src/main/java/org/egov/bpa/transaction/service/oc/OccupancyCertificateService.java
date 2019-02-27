/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2018>  eGovernments Foundation
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

package org.egov.bpa.transaction.service.oc;

import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_CANCELLED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_CREATED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_FIELD_INS;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_NOCUPDATED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_REJECTED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_TS_INS_INITIATED;
import static org.egov.bpa.utils.BpaConstants.FILESTORE_MODULECODE;
import static org.egov.bpa.utils.BpaConstants.FORWARDED_TO_CLERK;
import static org.egov.bpa.utils.BpaConstants.FWDINGTOLPINITIATORPENDING;
import static org.egov.bpa.utils.BpaConstants.FWD_TO_OVRSR_FOR_FIELD_INS;
import static org.egov.bpa.utils.BpaConstants.WF_APPROVE_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_CANCELAPPLICATION_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_CREATED_STATE;
import static org.egov.bpa.utils.BpaConstants.WF_INITIATE_REJECTION_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_LBE_SUBMIT_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_NEW_STATE;
import static org.egov.bpa.utils.BpaConstants.WF_REJECT_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_SAVE_BUTTON;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.egov.bpa.autonumber.OccupancyCertificateNumberGenerator;
import org.egov.bpa.master.service.CheckListDetailService;
import org.egov.bpa.service.es.OccupancyCertificateIndexService;
import org.egov.bpa.transaction.entity.ApplicationFee;
import org.egov.bpa.transaction.entity.BpaStatus;
import org.egov.bpa.transaction.entity.WorkflowBean;
import org.egov.bpa.transaction.entity.common.DCRDocumentCommon;
import org.egov.bpa.transaction.entity.common.DocumentsCommon;
import org.egov.bpa.transaction.entity.common.NocDocumentsCommon;
import org.egov.bpa.transaction.entity.common.StoreDCRFilesCommon;
import org.egov.bpa.transaction.entity.oc.OCBuilding;
import org.egov.bpa.transaction.entity.oc.OCDcrDocuments;
import org.egov.bpa.transaction.entity.oc.OCDocuments;
import org.egov.bpa.transaction.entity.oc.OCExistingBuilding;
import org.egov.bpa.transaction.entity.oc.OCExistingBuildingFloor;
import org.egov.bpa.transaction.entity.oc.OCFloor;
import org.egov.bpa.transaction.entity.oc.OCNocDocuments;
import org.egov.bpa.transaction.entity.oc.OCNoticeConditions;
import org.egov.bpa.transaction.entity.oc.OccupancyCertificate;
import org.egov.bpa.transaction.entity.oc.OccupancyFee;
import org.egov.bpa.transaction.notice.OccupancyCertificateNoticesFormat;
import org.egov.bpa.transaction.notice.impl.OccupancyCertificateDemandFormatImpl;
import org.egov.bpa.transaction.repository.OCDcrDocumentRepository;
import org.egov.bpa.transaction.repository.oc.OccupancyCertificateRepository;
import org.egov.bpa.transaction.repository.oc.OccupancyFeeRepository;
import org.egov.bpa.transaction.service.ApplicationBpaService;
import org.egov.bpa.transaction.service.ApplicationFeeService;
import org.egov.bpa.transaction.service.OccupancyCertificateFeeCalculation;
import org.egov.bpa.transaction.service.collection.BpaDemandService;
import org.egov.bpa.transaction.service.collection.OccupancyCertificateBillService;
import org.egov.bpa.transaction.service.messaging.oc.OcSmsAndEmailService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.bpa.utils.BpaUtils;
import org.egov.bpa.utils.OccupancyCertificateUtils;
import org.egov.commons.entity.Source;
import org.egov.demand.model.EgDemand;
import org.egov.infra.admin.master.entity.Boundary;
import org.egov.infra.custom.CustomImplProvider;
import org.egov.infra.exception.ApplicationRuntimeException;
import org.egov.infra.filestore.entity.FileStoreMapper;
import org.egov.infra.filestore.service.FileStoreService;
import org.egov.infra.reporting.engine.ReportOutput;
import org.egov.infra.security.utils.SecurityUtils;
import org.egov.infra.utils.ApplicationNumberGenerator;
import org.egov.infra.utils.FileStoreUtils;
import org.egov.infra.utils.autonumber.AutonumberServiceBeanResolver;
import org.egov.infra.workflow.matrix.entity.WorkFlowMatrix;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class OccupancyCertificateService {
	
    private static final String NOC_UPDATION_IN_PROGRESS = "NOC updation in progress";

    @Autowired
    private BpaUtils bpaUtils;
    @Autowired
    private ApplicationNumberGenerator applicationNumberGenerator;
    @Autowired
    private OccupancyCertificateRepository occupancyCertificateRepository;
    @Autowired
    private ApplicationBpaService applicationBpaService;
    @Autowired
    private OccupancyCertificateBillService ocBillService;
    @Autowired
    private SecurityUtils securityUtils;
    @Autowired
    private CheckListDetailService checkListDetailService;
    @Autowired
    private OccupancyCertificateUtils occupancyCertificateUtils;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private OccupancyCertificateIndexService occupancyCertificateIndexService;
    @Autowired
    private AutonumberServiceBeanResolver beanResolver;
    @Autowired
    private OCBuildingFloorDetailsService proposedBuildingFloorDetailsService;
    @Autowired
    private OCExistingBuildingFloorDetailsService exsitingBuildingFloorDetailsService;
    @Autowired
    private FileStoreUtils fileStoreUtils;
    @Autowired
    private FileStoreService fileStoreService;
    @Autowired
    private OCNoticeConditionsService  ocNoticeConditionsService;
    @Autowired
    private OccupancyCertificateFeeCalculation occupancyCertificateFeeCalculation;
    @Autowired
    private ApplicationFeeService applicationFeeService;
    @Autowired
    private BpaDemandService bpaDemandService;
    @Autowired
    private OccupancyFeeRepository ocFeeRepository;
    @Autowired
    private OcSmsAndEmailService ocSmsAndEmailService;
    @Autowired
    private CustomImplProvider specificNoticeService;
    @Autowired
    private OCDcrDocumentRepository ocDcrDocumentRepository;
    
    public List<OccupancyCertificate> findByEdcrNumber(String edcrNumber) {
        return occupancyCertificateRepository.findByEDcrNumber(edcrNumber);
    }
    
    public OccupancyCertificate findByPermitNumber(String permitNumber) {
        return occupancyCertificateRepository.findByPermitNumber(permitNumber);
    }
    @Transactional
    public OccupancyCertificate saveOrUpdate(final OccupancyCertificate oc, final WorkflowBean wfBean) {
        buildProposedAndExistingBuildings(oc);
        if (oc.getApplicationDate() == null)
            oc.setApplicationDate(new Date());
        setSource(oc);
        if (oc.getApplicationNumber() == null)
            oc.setApplicationNumber(applicationNumberGenerator.generate());
        oc.setApplicationType("Occupancy Certificate");
        final BpaStatus bpaStatus = applicationBpaService.getStatusByCodeAndModuleType(APPLICATION_STATUS_CREATED);
        oc.setStatus(bpaStatus);
        if (occupancyCertificateUtils.isApplicationFeeCollectionRequired())
            oc.setDemand(ocBillService.createDemand(oc));
        else
            oc.setDemand(ocBillService.createDemandWhenFeeCollectionNotRequire());
        oc.setAdmissionfeeAmount(oc.getDemand().getBaseDemand());
        processAndStoreGeneralDocuments(oc);
        List<OCDcrDocuments> ocDcrDocuments; 
        if (oc.getId() == null) {
            ocDcrDocuments = oc.getDcrDocuments();
            oc.getDcrDocuments().forEach(dcrDocument -> dcrDocument.setOc(oc));
        } else {
            ocDcrDocuments = oc.getDcrDocuments();
        }
        processAndStoreNocDocuments(oc);
        if (wfBean.getWorkFlowAction() != null && wfBean.getWorkFlowAction().equals(WF_LBE_SUBMIT_BUTTON)
                && (!bpaUtils.logedInuseCitizenOrBusinessUser())) {
        	
        	WorkFlowMatrix wfMatrix = null;
            String currentState = WF_CREATED_STATE;
            if (oc.getAdmissionfeeAmount() != null
                    && oc.getAdmissionfeeAmount().compareTo(BigDecimal.ZERO) == 0) {
                wfMatrix = bpaUtils.getWfMatrixByCurrentState(
                        oc.getStateType(), WF_NEW_STATE);
                currentState = WF_NEW_STATE;
            }if (wfMatrix != null)
                wfBean.setApproverPositionId(bpaUtils.getUserPositionIdByZone(wfMatrix.getNextDesignation(),
                        oc.getParent().getSiteDetail().get(0) != null
                                && oc.getParent().getSiteDetail().get(0).getElectionBoundary() != null
                                        ? oc.getParent().getSiteDetail().get(0).getElectionBoundary().getId()
                                        : null));
            wfBean.setCurrentState(currentState);
            bpaUtils.redirectToBpaWorkFlowForOC(oc, wfBean);
            
        }else if (wfBean.getWorkFlowAction() != null
                && WF_CANCELAPPLICATION_BUTTON.equalsIgnoreCase(wfBean.getWorkFlowAction())) {
            oc.setStatus(
                    applicationBpaService.getStatusByCodeAndModuleType(APPLICATION_STATUS_CANCELLED));
        }
        OccupancyCertificate ocResult = occupancyCertificateRepository.saveAndFlush(oc);
        ocResult.setDcrDocuments(persistApplnDCRDocuments(ocResult, ocDcrDocuments));
        occupancyCertificateRepository.save(ocResult);
        occupancyCertificateIndexService.updateOccupancyIndex(oc);
        return ocResult;
    }

    @Transactional
    public OccupancyCertificate update(final OccupancyCertificate oc, final WorkflowBean wfBean) {
        if (WF_APPROVE_BUTTON.equals(wfBean.getWorkFlowAction())) {
            oc.setApprovalDate(new Date());
            
            /*TODO: To be configurable,
             * Need to generate new number
             * or permit application plan permission number
             * to be used as occupancy certificate number
             */
            oc.setOccupancyCertificateNumber(generateOccupancyCertificateNumber());
            OccupancyCertificateNoticesFormat ocNoticeFeature = (OccupancyCertificateNoticesFormat) specificNoticeService
                    .find(OccupancyCertificateDemandFormatImpl.class, specificNoticeService.getCityDetails());
            ReportOutput reportOutput = ocNoticeFeature
                    .generateNotice(findByApplicationNumber(oc.getApplicationNumber()));
            ocSmsAndEmailService.sendSmsAndEmailOnApproval(oc, reportOutput);
            appendQrCodeWithDcrDocumentsForOc(oc);
        }
        oc.setSentToPreviousOwner(false);
        processAndStoreGeneralDocuments(oc);
        oc.setDcrDocuments(persistApplnDCRDocuments(oc, oc.getDcrDocuments()));
        processAndStoreNocDocuments(oc);
        if (!WF_SAVE_BUTTON.equalsIgnoreCase(wfBean.getWorkFlowAction())
                && APPLICATION_STATUS_FIELD_INS.equalsIgnoreCase(oc.getStatus().getCode())
                && NOC_UPDATION_IN_PROGRESS.equalsIgnoreCase(oc.getState().getValue())) {
        	String feeCalculationMode = bpaUtils.getAppConfigValueForFeeCalculation(BpaConstants.EGMODULE_NAME, BpaConstants.OCFEECALULATION);
            if (feeCalculationMode.equalsIgnoreCase(BpaConstants.AUTOFEECAL) ||
            		feeCalculationMode.equalsIgnoreCase(BpaConstants.AUTOFEECALEDIT)) {
        	OccupancyFee ocFee = new OccupancyFee();
			try {
				ocFee = occupancyCertificateFeeCalculation.calculateOCSanctionFees(oc);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(!ocFee.getApplicationFee().getApplicationFeeDetail().isEmpty()) {
        	ApplicationFee applicationFee = applicationFeeService.saveApplicationFee(ocFee.getApplicationFee());
            ocFee.setApplicationFee(applicationFee);
            ocFeeRepository.save(ocFee);
        	oc.setDemand(bpaDemandService.generateDemandUsingSanctionFeeList(ocFee.getApplicationFee(), ocFee.getOc().getDemand()));
       
			}
		  }
        }
        if (WF_REJECT_BUTTON.equalsIgnoreCase(wfBean.getWorkFlowAction())
                || WF_INITIATE_REJECTION_BUTTON.equalsIgnoreCase(wfBean.getWorkFlowAction())
                || APPLICATION_STATUS_REJECTED.equalsIgnoreCase(oc.getStatus().getCode())
                || APPLICATION_STATUS_NOCUPDATED.equals(oc.getStatus().getCode())) {
            buildRejectionReasons(oc);
        }
        oc.setLPRequestInitiated(FWDINGTOLPINITIATORPENDING.equalsIgnoreCase(oc.getState().getNextAction()));
        if (APPLICATION_STATUS_TS_INS_INITIATED.equals(oc.getStatus().getCode())) {
            oc.setTownSurveyorInspectionRequire(false);
        }
        if (!WF_SAVE_BUTTON.equalsIgnoreCase(wfBean.getWorkFlowAction()))
            bpaUtils.redirectToBpaWorkFlowForOC(oc, wfBean);
        occupancyCertificateRepository.saveAndFlush(oc);
        return oc;
    }

    private String generateOccupancyCertificateNumber() {
        final OccupancyCertificateNumberGenerator occupancyCertificateNumberGenerator = beanResolver
                .getAutoNumberServiceFor(OccupancyCertificateNumberGenerator.class);
        return occupancyCertificateNumberGenerator.generateOccupancyCertificateNumber();
    }

    private void setSource(final OccupancyCertificate oc) {
        if (bpaUtils.logedInuseCitizenOrBusinessUser())
            oc.setSource(Source.CITIZENPORTAL);
        else
            oc.setSource(Source.SYSTEM);
    }

    private void buildProposedAndExistingBuildings(final OccupancyCertificate oc) {
        proposedBuildingFloorDetailsService.buildProposedBuildingFloorDetails(oc);
        exsitingBuildingFloorDetailsService.buildExistingBuildingFloorDetails(oc);
    }

    private void buildRejectionReasons(final OccupancyCertificate oc) {
        ocNoticeConditionsService.delete(oc.getRejectionReasons());
        ocNoticeConditionsService.delete(oc.getAdditionalNoticeConditions());
        oc.getAdditionalNoticeConditions().clear();
        oc.getRejectionReasons().clear();
        List<OCNoticeConditions> additionalRejectReasons = new ArrayList<>();
        for (OCNoticeConditions addnlReason : oc.getAdditionalRejectReasonsTemp()) {
            addnlReason.setOc(oc);
            if (addnlReason != null && addnlReason.getAdditionalCondition() != null)
                additionalRejectReasons.add(addnlReason);
        }
        oc.setRejectionReasons(oc.getRejectionReasonsTemp());
        oc.setAdditionalNoticeConditions(additionalRejectReasons);
    }
    
    public void validateProposedAndExistingBuildings(final OccupancyCertificate oc) {
        for (OCBuilding bldg : oc.getBuildingDetailFromEdcr()) {
            List<OCBuilding> bldgDetails = new ArrayList<>();
            List<OCFloor> floorDetails = new ArrayList<>();
            for (OCFloor floor : bldg.getFloorDetailsByEdcr())
                floorDetails.add(floor);
            bldg.setFloorDetailsForUpdate(floorDetails);
            bldgDetails.add(bldg);
            oc.getBuildings().addAll(bldgDetails);
        }
        if (!oc.getExistingBldgDetailFromEdcr().isEmpty()) {
            oc.getExistingBuildings().clear();
            for (OCExistingBuilding existBldg : oc.getExistingBldgDetailFromEdcr()) {
                List<OCExistingBuilding> existBldgDetails = new ArrayList<>();
                List<OCExistingBuildingFloor> floorDetails = new ArrayList<>();
                for (OCExistingBuildingFloor floor : existBldg.getExistingBldgFloorDetailsFromEdcr())
                    floorDetails.add(floor);
                existBldg.setExistingBuildingFloorDetailsUpdate(floorDetails);
                existBldgDetails.add(existBldg);
                oc.getExistingBuildings().addAll(existBldgDetails);
            }
        }
    }

    private void processAndStoreGeneralDocuments(final OccupancyCertificate oc) {
        if (!oc.getDocuments().isEmpty()
                && null == oc.getDocuments().get(0).getId())
            oc.getDocuments().forEach(ocDocuments -> {
                ocDocuments.setOc(oc);
                DocumentsCommon documentsCommon = ocDocuments.getDocument();
                documentsCommon.setChecklistDetail(
                        checkListDetailService.load(ocDocuments.getDocument().getChecklistDetail().getId()));
                documentsCommon.setCreatedUser(securityUtils.getCurrentUser());
                buildGeneralFiles(documentsCommon);
                ocDocuments.setDocument(documentsCommon);
            });
        else
            for (final OCDocuments ocDocuments : oc.getDocuments())
                buildGeneralFiles(ocDocuments.getDocument());
    }

    private void buildGeneralFiles(final DocumentsCommon commonDoc) {
        if (commonDoc.getFiles() != null && commonDoc.getFiles().length > 0) {
            Set<FileStoreMapper> existingFiles = new HashSet<>();
            existingFiles.addAll(commonDoc.getSupportDocs());
            existingFiles.addAll(applicationBpaService.addToFileStore(commonDoc.getFiles()));
            commonDoc.setSupportDocs(existingFiles);
            commonDoc.setSubmitted(true);
        }
    }

    private void processAndStoreNocDocuments(final OccupancyCertificate oc) {
        if (!oc.getNocDocuments().isEmpty()
                && null == oc.getNocDocuments().get(0).getId())
            oc.getNocDocuments().forEach(nocDocument -> {
                nocDocument.setOc(oc);
                NocDocumentsCommon nocDocumentsCommon = nocDocument.getNocDocument();
                nocDocumentsCommon.setChecklist(
                        checkListDetailService.load(nocDocumentsCommon.getChecklist().getId()));
                nocDocumentsCommon.setCreatedUser(securityUtils.getCurrentUser());
                buildNocFiles(nocDocumentsCommon);
                nocDocument.setNocDocument(nocDocumentsCommon);
            });
        else
            for (final OCNocDocuments nocDocument : oc.getNocDocuments())
                buildNocFiles(nocDocument.getNocDocument());
    }

    private void buildNocFiles(final NocDocumentsCommon nocDoc) {
        if (nocDoc.getFiles() != null && nocDoc.getFiles().length > 0) {
            Set<FileStoreMapper> existingFiles = new HashSet<>();
            existingFiles.addAll(nocDoc.getNocSupportDocs());
            existingFiles.addAll(applicationBpaService.addToFileStore(nocDoc.getFiles()));
            nocDoc.setNocSupportDocs(existingFiles);
            nocDoc.setSubmitted(true);
        }
    }
    
    private List<OCDcrDocuments> persistApplnDCRDocuments(final OccupancyCertificate oc, List<OCDcrDocuments> ocDcrDocuments) {
        List<OCDcrDocuments> dcrDocuments = new ArrayList<>();
        if (!ocDcrDocuments.isEmpty() && null == ocDcrDocuments.get(0).getId())
            for (final OCDcrDocuments ocDcrDocument : ocDcrDocuments) {
                ocDcrDocument.setOc(oc);
                DCRDocumentCommon dcrDocumentCommon = ocDcrDocument.getDcrDocument();
                dcrDocumentCommon.setChecklistDtl(
                        checkListDetailService.load(dcrDocumentCommon.getChecklistDtl().getId()));
                DCRDocumentCommon dcrDocRes = buildAutoPopulatedDCRFiles(dcrDocumentCommon);
                buildDCRFiles(dcrDocRes);
                ocDcrDocument.setDcrDocument(dcrDocRes);
                dcrDocuments.add(ocDcrDocument);
            }
        else
            for (final OCDcrDocuments dcrDocument : ocDcrDocuments) {
                DCRDocumentCommon dcrDocumentRes = buildAutoPopulatedDCRFiles(dcrDocument.getDcrDocument());
                buildDCRFiles(dcrDocumentRes);
                dcrDocument.setDcrDocument(dcrDocumentRes);
                dcrDocuments.add(dcrDocument);
            }
        return dcrDocuments;
    }

    // Will save manually uploaded dcr document pdf's
    private DCRDocumentCommon buildDCRFiles(DCRDocumentCommon dcrDocument) {
        Set<StoreDCRFilesCommon> storeDCRFiles = new HashSet<>();
        storeDCRFiles.addAll(dcrDocument.getDcrAttachments());
        if (dcrDocument.getFiles() != null && dcrDocument.getFiles().length > 0) {
            for (MultipartFile file : dcrDocument.getFiles()) {
                if (!file.isEmpty()) {
                    StoreDCRFilesCommon storeDCRFile = new StoreDCRFilesCommon();
                    storeDCRFile.setDcrDocument(dcrDocument);
                    storeDCRFile.setFileStoreMapper(applicationBpaService.addToFileStore(file));
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
    private DCRDocumentCommon buildAutoPopulatedDCRFiles(DCRDocumentCommon dcrDocumentCommon) {
        Set<StoreDCRFilesCommon> storeDCRFiles = new HashSet<>();
        storeDCRFiles.addAll(dcrDocumentCommon.getDcrAttachments());
        if (dcrDocumentCommon.getFileStoreIds() != null && dcrDocumentCommon.getFileStoreIds().length > 0) {
            for (String fileStoreId : dcrDocumentCommon.getFileStoreIds()) {
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
                    StoreDCRFilesCommon storeDCRFile = new StoreDCRFilesCommon();
                    storeDCRFile.setDcrDocument(dcrDocumentCommon);
                    storeDCRFile.setFileStoreMapper(savedFileStoreMapper);
                    storeDCRFile.setAutoPopulated(true);
                    storeDCRFiles.add(storeDCRFile);
                }
            }
        }
        if (!storeDCRFiles.isEmpty())
            dcrDocumentCommon.setDcrAttachments(storeDCRFiles);
        return dcrDocumentCommon;
    }

    public OccupancyCertificate findByApplicationNumber(final String applicationNumber) {
        return occupancyCertificateRepository.findByApplicationNumber(applicationNumber);
    }

    public OccupancyCertificate findByDemand(final EgDemand demand) {
        return occupancyCertificateRepository.findByDemand(demand);
    }

    @Transactional
    public void saveOccupancyCertificate(OccupancyCertificate occupancyCertificate) {
        occupancyCertificateRepository.saveAndFlush(occupancyCertificate);
    }

    public List<OccupancyCertificate> getOcApplicationsForScheduleAndReSchedule(List<BpaStatus> bpaStatusList,
            List<Boundary> boundaryList, Integer totalAvailableSlots) {
        final Criteria criteria = entityManager.unwrap(Session.class)
                .createCriteria(OccupancyCertificate.class, "oc")
                .createAlias("oc.parent", "parent")
                .createAlias("parent.siteDetail", "siteDetail")
                .createAlias("oc.demand", "demand");
        criteria.add(Restrictions.in("oc.status", bpaStatusList));
        criteria.createAlias("oc.state", "state")
                .add(Restrictions.not(Restrictions.in("state.nextAction", FORWARDED_TO_CLERK, FWD_TO_OVRSR_FOR_FIELD_INS)));
        criteria.add(Restrictions.in("siteDetail.adminBoundary", boundaryList));
        criteria.add(Restrictions.eq("oc.failureInScheduler", false));
        criteria.add(Restrictions.leProperty("demand.baseDemand", "demand.amtCollected"));
        criteria.addOrder(Order.desc("oc.status"));
        criteria.addOrder(Order.asc("oc.applicationDate"));
        criteria.addOrder(Order.asc("oc.createdDate"));
        criteria.setMaxResults(totalAvailableSlots);
        return criteria.list();
    }

    public OccupancyCertificate findById(Long id) {
        return occupancyCertificateRepository.findOne(id);
    }

    public List<OccupancyCertificate> findByStatusListOrderByCreatedDate(List<BpaStatus> listOfBpaStatus) {
        return occupancyCertificateRepository.findByStatusListOrderByCreatedDateAsc(listOfBpaStatus);
    }
    
    private void appendQrCodeWithDcrDocumentsForOc(OccupancyCertificate oc) {
        List<OCDcrDocuments> dcrDocuments = ocDcrDocumentRepository.findByOc(oc);
        for (OCDcrDocuments dcrDocument : dcrDocuments) {
            DCRDocumentCommon ocDcrDocument = dcrDocument.getDcrDocument();
			if(ocDcrDocument != null) {
            for (StoreDCRFilesCommon file : ocDcrDocument.getDcrAttachments()) {
                bpaUtils.addQrCodeToOcPdfDocuments(file.getFileStoreMapper(), oc);
            }
        }
        }
    }
}
