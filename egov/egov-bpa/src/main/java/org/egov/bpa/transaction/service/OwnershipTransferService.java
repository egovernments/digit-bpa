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
import static org.egov.bpa.utils.BpaConstants.AUTOGENERATE_OWNERSHIP_NUMBER;
import static org.egov.bpa.utils.BpaConstants.EGMODULE_NAME;
import static org.egov.bpa.utils.BpaConstants.WF_APPROVE_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_SAVE_BUTTON;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.egov.bpa.autonumber.PlanPermissionNumberGenerator;
import org.egov.bpa.autonumber.impl.PlanPermissionNumberGeneratorImpl;
import org.egov.bpa.master.service.ChecklistServicetypeMappingService;
import org.egov.bpa.transaction.entity.Applicant;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.OwnershipTransfer;
import org.egov.bpa.transaction.entity.OwnershipTransferCoApplicant;
import org.egov.bpa.transaction.entity.OwnershipTransferDocument;
import org.egov.bpa.transaction.entity.WorkflowBean;
import org.egov.bpa.transaction.entity.common.GeneralDocument;
import org.egov.bpa.transaction.repository.OwnershipTransferRepository;
import org.egov.bpa.utils.BpaConstants;
import org.egov.bpa.utils.BpaWorkflowRedirectUtility;
import org.egov.demand.model.EgDemand;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.admin.master.service.AppConfigValueService;
import org.egov.infra.admin.master.service.UserService;
import org.egov.infra.custom.CustomImplProvider;
import org.egov.infra.filestore.entity.FileStoreMapper;
import org.egov.infra.persistence.entity.enums.UserType;
import org.egov.infra.security.utils.SecurityUtils;
import org.egov.infra.utils.ApplicationNumberGenerator;
import org.egov.portal.entity.Citizen;
import org.egov.portal.service.CitizenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;



@Service
@Transactional(readOnly = true)
public class OwnershipTransferService {

    @Autowired
    private OwnershipTransferRepository ownershipTransferRepository;
    @Autowired
    private ApplicationNumberGenerator applicationNumberGenerator;
    @Autowired
    private ApplicationBpaService applicationBpaService;
    @Autowired
    private BpaStatusService bpaStatusService;
    @Autowired
    private AppConfigValueService appConfigValueService;
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

    @Transactional
    public OwnershipTransfer save(final OwnershipTransfer ownershipTransfer, final WorkflowBean wfBean) {
        if (ownershipTransfer.getApplicationNumber() == null) {
        	ownershipTransfer.setApplicationNumber(applicationNumberGenerator.generate());
        	ownershipTransfer.setApplicationDate(new Date());
        	ownershipTransfer.setIsActive(true);
        }
        buildDocuments(ownershipTransfer);
        ownershipTransfer.setStatus(bpaStatusService.findByModuleTypeAndCode(BpaConstants.OWNERSHIPSTATUS_MODULETYPE, APPLICATION_STATUS_CREATED));
        if (!WF_SAVE_BUTTON.equalsIgnoreCase(wfBean.getWorkFlowAction()))
            bpaWorkflowRedirectUtility.redirectToBpaWorkFlow(ownershipTransfer, wfBean);
        OwnershipTransfer ownershipResponse = ownershipTransferRepository.saveAndFlush(ownershipTransfer);
        return ownershipResponse;
    }

    @Transactional
    public void saveOwnership(OwnershipTransfer ownershipTransfer) {
    	ownershipTransfer.setCoApplicants(buildCoApplicantDetails(ownershipTransfer));
    	ownershipTransferRepository.saveAndFlush(ownershipTransfer);
     }   
    
    public OwnershipTransfer findByApplicationNumber(final String applicationNumber) {
        return ownershipTransferRepository.findByApplicationNumber(applicationNumber);
    }
    
    public List<OwnershipTransfer> findByBpaApplication(final BpaApplication bpaAPplication) {
        return ownershipTransferRepository.findByParentOrderByIdDesc(bpaAPplication);
    }
    
    public List<OwnershipTransfer> findByBpaApplicationAndDate(final BpaApplication bpaApplication, Date createdDate) {
        return ownershipTransferRepository.findByParentAndCreatedDateLessThanOrderByIdDesc(bpaApplication, createdDate);
    }

    public OwnershipTransfer findByDemand(final EgDemand demand) {
        return ownershipTransferRepository.findByDemand(demand);
    }
    
    public OwnershipTransfer findActiveOwnershipNumber(final String renewalNumber) {
        return ownershipTransferRepository.findByOwnershipNumberAndIsActiveTrue(renewalNumber);
    }
    
    public OwnershipTransfer findByPlanPermissionNumber(final String permitNumber) {
        List<OwnershipTransfer> ownershipTransfers = ownershipTransferRepository
                .findByParentPlanPermissionNumberOrderByIdDesc(permitNumber);
        ownershipTransfers = ownershipTransfers.stream().filter(ot -> ot.getIsActive().equals(true)).collect(Collectors.toList());
        return ownershipTransfers.isEmpty() ? null : ownershipTransfers.get(0);
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
        	if (Boolean.valueOf(appConfigValueService.getConfigValuesByModuleAndKey(EGMODULE_NAME,
        			AUTOGENERATE_OWNERSHIP_NUMBER).get(0).getValue())) {
               ownershipTransfer.setOwnershipNumber(ownershipTransfer.getParent().getPlanPermissionNumber());
            }
        	else
        	   ownershipTransfer.setOwnershipNumber(planPermissionNumber.generatePlanPermissionNumber(ownershipTransfer.getParent()));
        }
        
        OwnershipTransfer permitRenewalRes = ownershipTransferRepository.save(ownershipTransfer);
        bpaWorkflowRedirectUtility.redirectToBpaWorkFlow(ownershipTransfer, wfBean);
        return permitRenewalRes;
    }
    
    private List<OwnershipTransferCoApplicant> buildCoApplicantDetails(final OwnershipTransfer ownershipTransfer) {
        List<OwnershipTransferCoApplicant> coApplicants = new LinkedList<>();
        for (OwnershipTransferCoApplicant applicant : ownershipTransfer.getCoApplicants()) {
            if (applicant != null) {
            	applicant.setOwnershipTransfer(ownershipTransfer);
                coApplicants.add(applicant);
            }
        }
        if (coApplicants.isEmpty())
        	ownershipTransfer.getCoApplicants().clear();
    return coApplicants;
    }
    
    public void buildOwnerDetails(final OwnershipTransfer ownershipTransfer) {
        Applicant existApplicant = applicantService.findByNameAndMobileNumberAndGenderAndType(ownershipTransfer.getOwner().getName(),
        		ownershipTransfer.getOwner().getUser().getMobileNumber(), ownershipTransfer.getOwner().getGender(),
                UserType.CITIZEN);
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
        List<Citizen> citizensWithMobNo = citizenService
                .getCitizenByMobileNumberAndType(ownershipTransfer.getOwner().getUser().getMobileNumber(), UserType.CITIZEN);
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
            List<User> citizensWithEmail = userService.getUsersByTypeAndEmailId(ownershipTransfer.getOwner().getEmailId(),
                    UserType.CITIZEN);
            List<User> busUsersWithEmail = userService.getUsersByTypeAndEmailId(ownershipTransfer.getOwner().getEmailId(),
                    UserType.BUSINESS);
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

}
