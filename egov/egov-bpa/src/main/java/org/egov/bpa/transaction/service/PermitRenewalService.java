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
import static org.egov.bpa.utils.BpaConstants.RENEWALSTATUS_MODULETYPE;
import static org.egov.bpa.utils.BpaConstants.WF_APPROVE_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_GENERATE_RENEWAL_ORDER;
import static org.egov.bpa.utils.BpaConstants.WF_REJECT_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_SAVE_BUTTON;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.egov.bpa.service.es.PermitRenewalIndexService;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.PermitRenewal;
import org.egov.bpa.transaction.entity.PermitRenewalConditions;
import org.egov.bpa.transaction.entity.WorkflowBean;
import org.egov.bpa.transaction.notice.util.BpaNoticeUtil;
import org.egov.bpa.transaction.repository.PermitRenewalRepository;
import org.egov.bpa.transaction.service.collection.BpaDemandService;
import org.egov.bpa.transaction.service.messaging.renewal.RenewalSmsAndEmailService;
import org.egov.bpa.utils.BpaAppConfigUtil;
import org.egov.bpa.utils.BpaWorkflowRedirectUtility;
import org.egov.demand.model.EgDemand;
import org.egov.infra.filestore.entity.FileStoreMapper;
import org.egov.infra.utils.ApplicationNumberGenerator;
import org.egov.infra.utils.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author vinoth
 *
 */
@Service
@Transactional(readOnly = true)
public class PermitRenewalService {

    @Autowired
    private PermitRenewalRepository permitRenewalRepository;
    @Autowired
    private ApplicationNumberGenerator applicationNumberGenerator;
    @Autowired
    private ApplicationBpaService applicationBpaService;
    @Autowired
    private PermitRenewalIndexService permitRenewalIndexService;
    @Autowired
    private BpaWorkflowRedirectUtility bpaWorkflowRedirectUtility;
    @Autowired
    private BpaStatusService bpaStatusService;
    @Autowired
    private BpaNoticeUtil bpaNoticeUtil;
    @Autowired
    private BpaAppConfigUtil bpaAppConfigUtil;
    @Autowired
    private PermitRenewalFeeCalculationService feeCalculationService;
    @Autowired
    private BpaDemandService bpaDemandService;
    @Autowired
    private PermitRenewalConditionsService renewalConditionsService;
    @Autowired
    private RenewalSmsAndEmailService renewalSmsAndEmailService;
    @Autowired
    @Qualifier("parentMessageSource")
    private MessageSource bpaMessageSource;

    @Transactional
    public PermitRenewal save(final PermitRenewal permitRenewal, final WorkflowBean wfBean) {
        if (permitRenewal.getApplicationNumber() == null) {
            permitRenewal.setApplicationNumber(applicationNumberGenerator.generate());
            permitRenewal.setApplicationDate(new Date());
        }
        buildDocuments(permitRenewal);
        permitRenewal.setStatus(bpaStatusService.findByModuleTypeAndCode(RENEWALSTATUS_MODULETYPE, APPLICATION_STATUS_CREATED));
        if (!WF_SAVE_BUTTON.equalsIgnoreCase(wfBean.getWorkFlowAction()))
            bpaWorkflowRedirectUtility.redirectToBpaWorkFlow(permitRenewal, wfBean);
        PermitRenewal renewalResponse = permitRenewalRepository.saveAndFlush(permitRenewal);
        permitRenewalIndexService.createPermitRenewalIndex(renewalResponse);
        return renewalResponse;
    }

    @Transactional
    public void savePermitRenewal(PermitRenewal permitRenewal) {
        permitRenewalRepository.saveAndFlush(permitRenewal);
    }

    private void buildDocuments(final PermitRenewal permitRenewal) {
        if (permitRenewal.getFiles() != null && permitRenewal.getFiles().length > 0) {
            Set<FileStoreMapper> docs = new HashSet<>();
            docs.addAll(permitRenewal.getPermitRenewalDocs());
            for (MultipartFile file : permitRenewal.getFiles())
                if (!file.isEmpty())
                    docs.add(applicationBpaService.addToFileStore(file));
            permitRenewal.setPermitRenewalDocs(docs);
        }
    }

    @Transactional
    public PermitRenewal update(PermitRenewal permitRenewal, final WorkflowBean wfBean) {
        if (WF_APPROVE_BUTTON.equalsIgnoreCase(wfBean.getWorkFlowAction())) {
            permitRenewal.setRenewalApprovalDate(new Date());
            permitRenewal.setRenewalNumber(permitRenewal.getParent().getPlanPermissionNumber());
            renewalSmsAndEmailService.sendSmsAndEmailOnRenewalApproval(permitRenewal, null);
        }

        if (WF_GENERATE_RENEWAL_ORDER.equalsIgnoreCase(wfBean.getWorkFlowAction())) {
            String permitExpiryDateStr = bpaNoticeUtil.calculateCertExpryDate(
                    new DateTime(permitRenewal.getParent().getPlanPermissionDate()),
                    permitRenewal.getParent().getServiceType().getValidity());
            String permitRenewalExpiryDateStr = bpaNoticeUtil.calculateCertExpryDate(
                    new DateTime(DateUtils.toDateUsingDefaultPattern(permitExpiryDateStr)),
                    permitRenewal.getParent().getServiceType().getRenewalValidity());
            permitRenewal.setPermitRenewalExpiryDate(DateUtils.toDateUsingDefaultPattern(permitRenewalExpiryDateStr));
        }
        if ("Initiated for permit renewal".equalsIgnoreCase(permitRenewal.getCurrentState().getValue())) {
            permitRenewal.setDemand(
                    bpaDemandService.createDemandUsingDemandReasonCodes(permitRenewal.getDemand(),
                            feeCalculationService.calculateRenewalFee(permitRenewal)));
        }
        if (WF_REJECT_BUTTON.equalsIgnoreCase(wfBean.getWorkFlowAction())
                || APPLICATION_STATUS_REJECTED.equalsIgnoreCase(permitRenewal.getStatus().getCode())) {
            buildRejectionReasons(permitRenewal);
        }
        PermitRenewal permitRenewalRes = permitRenewalRepository.save(permitRenewal);
        bpaWorkflowRedirectUtility.redirectToBpaWorkFlow(permitRenewal, wfBean);
        permitRenewalIndexService.updateIndexes(permitRenewalRes);
        return permitRenewalRes;
    }

    private void buildRejectionReasons(final PermitRenewal permitRenewal) {
        renewalConditionsService.delete(permitRenewal.getRejectionReasons());
        renewalConditionsService.delete(permitRenewal.getAdditionalRenewalConditions());
        permitRenewal.getAdditionalRenewalConditions().clear();
        permitRenewal.getRejectionReasons().clear();
        List<PermitRenewalConditions> additionalRejectReasons = new ArrayList<>();
        for (PermitRenewalConditions addnlReason : permitRenewal.getAdditionalRejectReasonsTemp()) {
            addnlReason.setPermitRenewal(permitRenewal);
            addnlReason.getNoticeCondition().setChecklistServicetype(
                    permitRenewal.getAdditionalRejectReasonsTemp().get(0).getNoticeCondition().getChecklistServicetype());
            if (addnlReason != null && addnlReason.getNoticeCondition().getAdditionalCondition() != null)
                additionalRejectReasons.add(addnlReason);
        }
        permitRenewal.setRejectionReasons(permitRenewal.getRejectionReasonsTemp());
        permitRenewal.setAdditionalRenewalConditions(additionalRejectReasons);
    }

    public PermitRenewal findByPlanPermissionNumberAndRevocationApplnDate(final String permitNumber) {
        List<PermitRenewal> permitRenewals = permitRenewalRepository
                .findByParentPlanPermissionNumberOrderByIdDesc(permitNumber);
        return permitRenewals.isEmpty() ? null : permitRenewals.get(0);
    }

    public PermitRenewal findByApplicationNumber(final String applicationNumber) {
        return permitRenewalRepository.findByApplicationNumber(applicationNumber);
    }

    public List<PermitRenewal> findByRenewalNumber(final String renewalNumber) {
        return permitRenewalRepository.findByRenewalNumber(renewalNumber);
    }

    public PermitRenewal findByDemand(final EgDemand demand) {
        return permitRenewalRepository.findByDemand(demand);
    }

    public String permitExtensionAndRenewalNotAllowed(final String permitNumber) {
        String message = StringUtils.EMPTY;
        if (!isPermitRenewalRequest(permitNumber) && !isPermitExtensionRequest(permitNumber)) {
            final BpaApplication permit = applicationBpaService.findByPermitNumber(permitNumber);
            Date permitExpiryDate = DateUtils.toDateUsingDefaultPattern(bpaNoticeUtil.calculateCertExpryDate(
                    new DateTime(permit.getPlanPermissionDate()), permit.getServiceType().getValidity()));
            String minAllowed = getMinAllowedDateForRenewalPriorExpiry(permit);
            String maxAllowed = getMaxAllowedDateForRenewalAfterExpiry(permit);
            if (new Date().compareTo(permitExpiryDate) <= 0)
                message = bpaMessageSource.getMessage("msg.permit.extension.not.allowed", new String[] { minAllowed,
                        DateUtils.toDefaultDateFormat(permitExpiryDate), DateUtils.toDefaultDateFormat(permitExpiryDate),
                        maxAllowed }, null);
            else
                message = bpaMessageSource.getMessage("msg.permit.renewal.not.allowed", new String[] { maxAllowed }, null);
        }
        return message;
    }

    public boolean isPermitRenewalRequest(final String permitNumber) {
        final BpaApplication permit = applicationBpaService.findByPermitNumber(permitNumber);
        Date permitExpiryDate = DateUtils.toDateUsingDefaultPattern(bpaNoticeUtil.calculateCertExpryDate(
                new DateTime(permit.getPlanPermissionDate()), permit.getServiceType().getValidity()));
        Date maxAllowedRenewalDate = DateUtils.toDateUsingDefaultPattern(getMaxAllowedDateForRenewalAfterExpiry(permit));
        return new Date().after(permitExpiryDate) && new Date().compareTo(maxAllowedRenewalDate) <= 0;
    }

    public String getMaxAllowedDateForRenewalAfterExpiry(final BpaApplication permit) {
        String permitExpiryDateStr = bpaNoticeUtil.calculateCertExpryDate(
                new DateTime(permit.getPlanPermissionDate()), permit.getServiceType().getValidity());
        Integer noOfDaysAllowed = bpaAppConfigUtil.getDaysMaxAllowedAfterPermitRenewalExpired();
        DateTime permitExpiryDate = new DateTime(DateUtils.toDateUsingDefaultPattern(permitExpiryDateStr));
        DateTimeFormatter fmt = DateUtils.defaultDateFormatter();
        return fmt.print(permitExpiryDate.plusDays(noOfDaysAllowed));
    }

    public boolean isPermitExtensionRequest(final String permitNumber) {
        final BpaApplication permit = applicationBpaService.findByPermitNumber(permitNumber);
        Date permitExpiryDate = DateUtils.toDateUsingDefaultPattern(bpaNoticeUtil.calculateCertExpryDate(
                new DateTime(permit.getPlanPermissionDate()), permit.getServiceType().getValidity()));
        Date minAllowedRenewalDate = DateUtils.toDateUsingDefaultPattern(getMinAllowedDateForRenewalPriorExpiry(permit));
        return new Date().compareTo(minAllowedRenewalDate) >= 0 && new Date().compareTo(permitExpiryDate) <= 0;
    }

    public String getMinAllowedDateForRenewalPriorExpiry(final BpaApplication permit) {
        String permitExpiryDateStr = bpaNoticeUtil.calculateCertExpryDate(
                new DateTime(permit.getPlanPermissionDate()), permit.getServiceType().getValidity());
        Integer noOfDaysPriorAllowed = bpaAppConfigUtil.getDaysPriorPermitRenewalCanApply();
        DateTime permitExpiryDate = new DateTime(DateUtils.toDateUsingDefaultPattern(permitExpiryDateStr));
        DateTimeFormatter fmt = DateUtils.defaultDateFormatter();
        return fmt.print(permitExpiryDate.minusDays(noOfDaysPriorAllowed));
    }

}
