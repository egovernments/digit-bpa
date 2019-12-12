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

package org.egov.bpa.transaction.service;

import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_CREATED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_REJECTED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_SUBMITTED;
import static org.egov.bpa.utils.BpaConstants.WF_APPROVE_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_LBE_SUBMIT_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_REJECT_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_SAVE_BUTTON;
import static org.egov.infra.validation.constants.ValidationRegex.NUMERIC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.egov.bpa.autonumber.PlanPermissionNumberGenerator;
import org.egov.bpa.autonumber.impl.PlanPermissionNumberGeneratorImpl;
import org.egov.bpa.config.properties.BpaApplicationSettings;
import org.egov.bpa.master.service.ChecklistServicetypeMappingService;
import org.egov.bpa.transaction.entity.Applicant;
import org.egov.bpa.transaction.entity.ApplicationFee;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.BpaStatus;
import org.egov.bpa.transaction.entity.OwnershipFee;
import org.egov.bpa.transaction.entity.OwnershipTransfer;
import org.egov.bpa.transaction.entity.OwnershipTransferCoApplicant;
import org.egov.bpa.transaction.entity.OwnershipTransferConditions;
import org.egov.bpa.transaction.entity.OwnershipTransferDocument;
import org.egov.bpa.transaction.entity.WorkflowBean;
import org.egov.bpa.transaction.entity.common.GeneralDocument;
import org.egov.bpa.transaction.entity.dto.SearchBpaApplicationForm;
import org.egov.bpa.transaction.repository.OwnershipFeeRepository;
import org.egov.bpa.transaction.repository.OwnershipTransferRepository;
import org.egov.bpa.transaction.repository.specs.SearchOwnershipTransferFormSpec;
import org.egov.bpa.transaction.service.collection.BpaDemandService;
import org.egov.bpa.utils.BpaAppConfigUtil;
import org.egov.bpa.utils.BpaConstants;
import org.egov.bpa.utils.BpaUtils;
import org.egov.bpa.utils.BpaWorkflowRedirectUtility;
import org.egov.demand.model.EgDemand;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.admin.master.service.UserService;
import org.egov.infra.config.persistence.datasource.routing.annotation.ReadOnly;
import org.egov.infra.custom.CustomImplProvider;
import org.egov.infra.filestore.entity.FileStoreMapper;
import org.egov.infra.persistence.entity.enums.UserType;
import org.egov.infra.security.utils.SecurityUtils;
import org.egov.infra.utils.ApplicationNumberGenerator;
import org.egov.portal.entity.Citizen;
import org.egov.portal.service.CitizenService;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly = true)
public class OwnershipTransferService {
    private static final String MSG_INVALID_VALUE = "msg.invalid.value";

    @Autowired
    private OwnershipTransferRepository ownershipTransferRepository;
    @Autowired
    private ApplicationNumberGenerator applicationNumberGenerator;
    @Autowired
    private ApplicationBpaService applicationBpaService;
    @Autowired
    private BpaStatusService bpaStatusService;
    @Autowired
    private BpaWorkflowRedirectUtility bpaWorkflowRedirectUtility;
    @Autowired
    private CustomImplProvider specificNoticeService;
    @Autowired
    private ApplicantService applicantService;
    @Autowired
    private CitizenService citizenService;
    @Autowired
    private UserService userService;
    @Autowired
    private ChecklistServicetypeMappingService checklistServicetypeMappingService;
    @Autowired
    private SecurityUtils securityUtils;
    @Autowired
    private BpaAppConfigUtil bpaAppConfigUtil;
    @Autowired
    private BpaDemandService bpaDemandService;
    @Autowired
    private ApplicationFeeService applicationFeeService;
    @Autowired
    private OwnershipFeeRepository ownershipFeeRepository;
    @Autowired
    private OwnershipCoApplicantService coApplicantService;
    @Autowired
    private WorkflowHistoryService workflowHistoryService;
    @Autowired
    private OwnershipTransferRepository ownershipRepository;
    @Autowired
    private BpaUtils bpaUtils;
    @Autowired
    private BpaApplicationSettings bpaApplicationSettings;
    @Autowired
    private OwnershipTransferConditionsService ownershipConditionsService;

    @Transactional
    public OwnershipTransfer createNewApplication(final OwnershipTransfer ownershipTransfer, WorkflowBean wfBean) {
        if (ownershipTransfer.getApplicationNumber() == null) {
            ownershipTransfer.setApplicationNumber(applicationNumberGenerator.generate());
            ownershipTransfer.setApplicationDate(new Date());
            ownershipTransfer.setIsActive(false);
        }
        if (wfBean.getWorkFlowAction() != null && wfBean.getWorkFlowAction().equals(WF_LBE_SUBMIT_BUTTON)) {
            final BpaStatus bpaStatus = bpaStatusService
                    .findByModuleTypeAndCode(BpaConstants.OWNERSHIPSTATUS_MODULETYPE, APPLICATION_STATUS_SUBMITTED);
            ownershipTransfer.setStatus(bpaStatus);
        } else {
            final BpaStatus bpaStatus = bpaStatusService
                    .findByModuleTypeAndCode(BpaConstants.OWNERSHIPSTATUS_MODULETYPE, APPLICATION_STATUS_CREATED);
            ownershipTransfer.setStatus(bpaStatus);
        }
        ownershipTransfer.setCoApplicants(buildCoApplicantDetails(ownershipTransfer));
        OwnershipFeeCalculationService feeCalculation = (OwnershipFeeCalculationService) specificNoticeService
                .find(OwnershipFeeCalculationService.class, specificNoticeService.getCityDetails());
        if (bpaAppConfigUtil.ownershipApplicationFeeCollectionRequired())
            ownershipTransfer.setDemand(feeCalculation.createDemand(ownershipTransfer));
        else
            ownershipTransfer.setDemand(feeCalculation.createDemandWhenFeeCollectionNotRequire(ownershipTransfer));
        OwnershipTransfer ownershipRes = ownershipTransferRepository.saveAndFlush(ownershipTransfer);

        return ownershipRes;
    }

    @Transactional
    public OwnershipTransfer save(final OwnershipTransfer ownershipTransfer, final WorkflowBean wfBean) {
        if (ownershipTransfer.getApplicationNumber() == null) {
            ownershipTransfer.setApplicationNumber(applicationNumberGenerator.generate());
            ownershipTransfer.setApplicationDate(new Date());
            ownershipTransfer.setIsActive(false);
        }
        buildDocuments(ownershipTransfer);
        ownershipTransfer.setStatus(bpaStatusService.findByModuleTypeAndCode(BpaConstants.OWNERSHIPSTATUS_MODULETYPE,
                APPLICATION_STATUS_CREATED));
        if (!WF_SAVE_BUTTON.equalsIgnoreCase(wfBean.getWorkFlowAction()))
            bpaWorkflowRedirectUtility.redirectToBpaWorkFlow(ownershipTransfer, wfBean);
        ownershipTransfer.setCoApplicants(buildCoApplicantDetails(ownershipTransfer));
        OwnershipTransfer ownershipResponse = ownershipTransferRepository.saveAndFlush(ownershipTransfer);
        return ownershipResponse;
    }

    @Transactional
    public void saveOwnership(OwnershipTransfer ownershipTransfer) {
        ownershipTransferRepository.saveAndFlush(ownershipTransfer);
    }

    public OwnershipTransfer findByApplicationNumber(final String applicationNumber) {
        return ownershipTransferRepository.findByApplicationNumber(applicationNumber);
    }

    public List<OwnershipTransfer> findByBpaApplication(final BpaApplication bpaApplication) {
        return ownershipTransferRepository.findByApplicationOrderByIdDesc(bpaApplication);
    }

    public List<OwnershipTransfer> findByBpaApplicationAndDate(final BpaApplication bpaApplication, Date createdDate) {
        return ownershipTransferRepository.findByApplicationAndCreatedDateLessThanOrderByIdDesc(bpaApplication, createdDate);
    }

    public OwnershipTransfer findByDemand(final EgDemand demand) {
        return ownershipTransferRepository.findByDemand(demand);
    }

    public List<OwnershipTransfer> findByOwnershipNumber(final String ownershipNumber) {
        return ownershipTransferRepository.findByOwnershipNumberOrderByIdDesc(ownershipNumber);
    }

    public List<OwnershipTransfer> findByPlanPermissionNumber(final String permitNumber) {
        return ownershipTransferRepository.findByApplicationPlanPermissionNumberOrderByIdDesc(permitNumber);
    }

    private void buildDocuments(final OwnershipTransfer ownershipTransfer) {
        if (ownershipTransfer.getFiles() != null && ownershipTransfer.getFiles().length > 0) {
            Set<FileStoreMapper> docs = new HashSet<>();
            for (MultipartFile file : ownershipTransfer.getFiles())
                docs.add(applicationBpaService.addToFileStore(file));
            ownershipTransfer.setOwnershipTransferDocs(docs);
        }
    }

    @Transactional
    public OwnershipTransfer update(OwnershipTransfer ownershipTransfer, final WorkflowBean wfBean) {
        PlanPermissionNumberGenerator planPermissionNumber = (PlanPermissionNumberGenerator) specificNoticeService
                .find(PlanPermissionNumberGeneratorImpl.class, specificNoticeService.getCityDetails());
        if (WF_APPROVE_BUTTON.equalsIgnoreCase(wfBean.getWorkFlowAction())) {
            ownershipTransfer.setOwnershipApprovalDate(new Date());
            if (bpaAppConfigUtil.autogenerateOwnershipNumber())
                ownershipTransfer.setOwnershipNumber(
                        planPermissionNumber.generatePlanPermissionNumber(ownershipTransfer.getApplication()));
            else
                ownershipTransfer.setOwnershipNumber(ownershipTransfer.getApplication().getPlanPermissionNumber());
            ownershipTransfer.setApproverPosition(ownershipTransfer.getState().getOwnerPosition());
            ownershipTransfer.setApproverUser(
                    ownershipTransfer.getState().getOwnerUser() == null ? securityUtils.getCurrentUser()
                            : ownershipTransfer.getState().getOwnerUser());
        }
        if (BpaConstants.WF_ASST_ENG_APPROVED.equalsIgnoreCase(ownershipTransfer.getCurrentState().getValue())
                && bpaAppConfigUtil.ownershipFeeCollectionRequired()) {
            calculateOwnershipFee(ownershipTransfer);
        }

        if (WF_REJECT_BUTTON.equalsIgnoreCase(wfBean.getWorkFlowAction())
                || APPLICATION_STATUS_REJECTED.equalsIgnoreCase(ownershipTransfer.getStatus().getCode())) {
            buildRejectionReasons(ownershipTransfer);
        }
        OwnershipTransfer ownershipRes = ownershipTransferRepository.save(ownershipTransfer);
        bpaWorkflowRedirectUtility.redirectToBpaWorkFlow(ownershipTransfer, wfBean);
        return ownershipRes;
    }

    private void calculateOwnershipFee(final OwnershipTransfer ownershipTransfer) {
        OwnershipFeeCalculationService feeCalculation = (OwnershipFeeCalculationService) specificNoticeService
                .find(OwnershipFeeCalculationService.class, specificNoticeService.getCityDetails());
        OwnershipFee ownershipFee = feeCalculation.calculateOwnershipSanctionFees(ownershipTransfer);
        ApplicationFee applicationFee = applicationFeeService.saveApplicationFee(ownershipFee.getApplicationFee());
        ownershipFee.setApplicationFee(applicationFee);
        EgDemand demand = bpaDemandService.generateDemandUsingSanctionFeeList(ownershipFee.getApplicationFee(),
                ownershipFee.getOwnershipTransfer().getDemand());
        if (ownershipFee.getOwnershipTransfer().getDemand() == null) {
            ownershipFee.getOwnershipTransfer().setDemand(demand);
        }
        ownershipFeeRepository.save(ownershipFee);
    }

    private List<OwnershipTransferCoApplicant> buildCoApplicantDetails(final OwnershipTransfer ownershipTransfer) {
        List<OwnershipTransferCoApplicant> coApplicants = new LinkedList<>();
        List<OwnershipTransferCoApplicant> deleteCoApplicants = new LinkedList<>();
        for (OwnershipTransferCoApplicant applicant : ownershipTransfer.getCoApplicants()) {
            if (applicant.getCoApplicant().getName() != null) {
                applicant.setOwnershipTransfer(ownershipTransfer);
                coApplicants.add(applicant);
            } else if (applicant.getId() != null) {
                deleteCoApplicants.add(applicant);
            }
        }
        if (coApplicants.isEmpty())
            ownershipTransfer.getCoApplicants().clear();
        if (!deleteCoApplicants.isEmpty())
            coApplicantService.delete(deleteCoApplicants);
        return coApplicants;
    }

    public void buildOwnerDetails(final OwnershipTransfer ownershipTransfer) {
        Applicant existApplicant = applicantService.findByNameAndMobileNumberAndGenderAndType(
                ownershipTransfer.getOwner().getName(), ownershipTransfer.getOwner().getUser().getMobileNumber(),
                ownershipTransfer.getOwner().getGender(), UserType.CITIZEN);
        if (existApplicant == null) {
            Applicant applicant = new Applicant();
            applicant.setName(ownershipTransfer.getOwner().getName());
            applicant.setAddress(ownershipTransfer.getOwner().getAddress());
            applicant.setGender(ownershipTransfer.getOwner().getGender());
            applicant.setAadhaarNumber(ownershipTransfer.getOwner().getAadhaarNumber());
            applicant.setEmailId(ownershipTransfer.getOwner().getEmailId());
            applicant.setUser(getCitizen(ownershipTransfer));
            ownershipTransfer.setOwner(applicant);
        } else {
            ownershipTransfer.setOwner(existApplicant);
        }
        if (!ownershipTransfer.getOwner().getUser().isActive())
            ownershipTransfer.getOwner().getUser().setActive(true);
    }

    private Citizen getCitizen(OwnershipTransfer ownershipTransfer) {
        Citizen citizen;
        List<Citizen> citizensWithMobNo = citizenService.getCitizenByMobileNumberAndType(
                ownershipTransfer.getOwner().getUser().getMobileNumber(), UserType.CITIZEN);
        Citizen existingCitizen = null;
        if (!citizensWithMobNo.isEmpty())
            existingCitizen = citizensWithMobNo.get(0);
        if (existingCitizen == null) {
            List<User> busUsersWithAadhaar = userService
                    .getUserByAadhaarNumberAndType(ownershipTransfer.getOwner().getAadhaarNumber(), UserType.BUSINESS);
            List<User> citizensWithAadhaar = userService
                    .getUserByAadhaarNumberAndType(ownershipTransfer.getOwner().getAadhaarNumber(), UserType.CITIZEN);
            if (!busUsersWithAadhaar.isEmpty() || !citizensWithAadhaar.isEmpty()) {
                ownershipTransfer.getOwner().setAadhaarNumber(StringUtils.EMPTY);
            }
            List<User> citizensWithEmail = userService
                    .getUsersByTypeAndEmailId(ownershipTransfer.getOwner().getEmailId(), UserType.CITIZEN);
            List<User> busUsersWithEmail = userService
                    .getUsersByTypeAndEmailId(ownershipTransfer.getOwner().getEmailId(), UserType.BUSINESS);
            if (!busUsersWithEmail.isEmpty() || !citizensWithEmail.isEmpty()) {
                ownershipTransfer.getOwner().setEmailId(StringUtils.EMPTY);
            }
            citizen = applicationBpaService.createApplicantAsCitizen(ownershipTransfer.getOwner());
            ownershipTransfer.setMailPwdRequired(true);
        } else {
            citizen = existingCitizen;
            ownershipTransfer.setMailPwdRequired(false);
        }
        return citizen;
    }

    public void processAndStoreOwnershipDocuments(final OwnershipTransfer ownershipTransfer) {
        if (!ownershipTransfer.getOwnershipTransferDocuments().isEmpty()
                && null == ownershipTransfer.getOwnershipTransferDocuments().get(0).getId())
            ownershipTransfer.getOwnershipTransferDocuments().forEach(document -> {
                document.setOwnershipTransfer(ownershipTransfer);
                GeneralDocument documentsCommon = document.getDocument();
                documentsCommon.setServiceChecklist(
                        checklistServicetypeMappingService.load(document.getDocument().getServiceChecklist().getId()));
                documentsCommon.setCreatedUser(securityUtils.getCurrentUser());
                buildGeneralFiles(documentsCommon);
                document.setDocument(documentsCommon);
            });
        else
            for (final OwnershipTransferDocument otDocuments : ownershipTransfer.getOwnershipTransferDocuments())
                buildGeneralFiles(otDocuments.getDocument());
    }

    private void buildGeneralFiles(final GeneralDocument commonDoc) {
        if (commonDoc.getFiles() != null && commonDoc.getFiles().length > 0) {
            Set<FileStoreMapper> existingFiles = new HashSet<>();
            existingFiles.addAll(commonDoc.getSupportDocs());
            existingFiles.addAll(applicationBpaService.addToFileStore(commonDoc.getFiles()));
            commonDoc.setSupportDocs(existingFiles);
            commonDoc.setSubmitted(true);
        }
    }

    public void validateOwnershipTransfer(final OwnershipTransfer ownershipTransfer, final BindingResult resultBinder) {
        Pattern pattern;
        Matcher matcher;
        Applicant applicant = ownershipTransfer.getOwner();
        User user = applicant.getUser();
        if (StringUtils.isNotBlank(user.getMobileNumber()) && !Jsoup.isValid(user.getMobileNumber(), Whitelist.basic())) {
            resultBinder.rejectValue("owner.user.mobileNumber", MSG_INVALID_VALUE);
        } else {
            pattern = Pattern.compile(NUMERIC);
            matcher = pattern.matcher(user.getMobileNumber());
            if (!matcher.matches())
                resultBinder.rejectValue("owner.user.mobileNumber", "invalid.mobile.number");
        }
        if (user.getMobileNumber().length() < 10 || user.getMobileNumber().length() > 10) {
            resultBinder.rejectValue("owner.user.mobileNumber", "invalid.mobile.no");
        }
    }

    @ReadOnly
    public Page<SearchBpaApplicationForm> pagedSearch(SearchBpaApplicationForm searchRequest) {

        final Pageable pageable = new PageRequest(searchRequest.pageNumber(),
                searchRequest.pageSize(), searchRequest.orderDir(), searchRequest.orderBy());

        Page<OwnershipTransfer> ownershipApplications = ownershipRepository
                .findAll(SearchOwnershipTransferFormSpec.searchSpecification(searchRequest), pageable);
        List<SearchBpaApplicationForm> searchResults = new ArrayList<>();
        for (OwnershipTransfer ownership : ownershipApplications) {
            String pendingAction = ownership.getState() == null ? "N/A" : ownership.getState().getNextAction();
            Boolean hasCollectionPending = bpaUtils.checkAnyTaxIsPendingToCollect(ownership.getDemand());
            searchResults.add(
                    new SearchBpaApplicationForm(ownership, getProcessOwner(ownership), pendingAction, bpaUtils.feeCollector(),
                            hasCollectionPending));
        }
        return new PageImpl<>(searchResults, pageable, ownershipApplications.getTotalElements());
    }

    private String getProcessOwner(OwnershipTransfer ownershipTransfer) {
        return ownershipTransfer.getState() != null && ownershipTransfer.getState().getOwnerPosition() != null
                ? workflowHistoryService.getUserPositionByPositionAndDate(ownershipTransfer.getState().getOwnerPosition().getId(),
                        ownershipTransfer.getState().getLastModifiedDate()).getName()
                : ownershipTransfer.getLastModifiedBy().getName();
    }

    private void buildRejectionReasons(final OwnershipTransfer ownershipTransfer) {
        ownershipConditionsService.delete(ownershipTransfer.getRejectionReasons());
        ownershipConditionsService.delete(ownershipTransfer.getAdditionalOwnershipConditions());
        ownershipTransfer.getAdditionalOwnershipConditions().clear();
        ownershipTransfer.getRejectionReasons().clear();
        List<OwnershipTransferConditions> additionalRejectReasons = new ArrayList<>();
        for (OwnershipTransferConditions addnlReason : ownershipTransfer.getAdditionalRejectReasonsTemp()) {
            addnlReason.setOwnershipTransfer(ownershipTransfer);
            addnlReason.getNoticeCondition().setChecklistServicetype(
                    ownershipTransfer.getAdditionalRejectReasonsTemp().get(0).getNoticeCondition().getChecklistServicetype());
            if (addnlReason != null && addnlReason.getNoticeCondition().getAdditionalCondition() != null)
                additionalRejectReasons.add(addnlReason);
        }
        ownershipTransfer.setRejectionReasons(ownershipTransfer.getRejectionReasonsTemp());
        ownershipTransfer.setAdditionalOwnershipConditions(additionalRejectReasons);
    }
    
    public void validateDocs(final OwnershipTransfer ownershipTransfer, final BindingResult errors) {
        List<String> appDocAllowedExtenstions = new ArrayList<>(
                Arrays.asList(bpaApplicationSettings.getValue("bpa.citizen.app.docs.allowed.extenstions").split(",")));

        List<String> appDocMimeTypes = new ArrayList<>(
                Arrays.asList(bpaApplicationSettings.getValue("bpa.citizen.app.docs.allowed.mime.types").split(",")));

        Integer i = 0;
        for (OwnershipTransferDocument document : ownershipTransfer.getOwnershipTransferDocuments()) {
            bpaUtils.validateFiles(errors, appDocAllowedExtenstions, appDocMimeTypes, document.getDocument().getFiles(),
                    "ownershipTransferDocuments[" + i + "].document.files",
                    bpaApplicationSettings.getValue("bpa.citizen.app.docs.max.size"));
            i++;
        }
    }
}
