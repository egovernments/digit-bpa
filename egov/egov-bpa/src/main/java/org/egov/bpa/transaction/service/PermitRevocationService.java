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

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_INIT_REVOKE;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_REVOKED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_REVOKE_CANCELED;
import static org.egov.bpa.utils.BpaConstants.EGMODULE_NAME;
import static org.egov.bpa.utils.BpaConstants.NO;
import static org.egov.bpa.utils.BpaConstants.SENDEMAILFORBPA;
import static org.egov.bpa.utils.BpaConstants.SENDSMSFORBPA;
import static org.egov.bpa.utils.BpaConstants.YES;
import static org.egov.infra.utils.DateUtils.toDefaultDateFormat;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.egov.bpa.autonumber.RevocationNumberGenerator;
import org.egov.bpa.master.entity.PermitRevocation;
import org.egov.bpa.master.entity.PermitRevocationDetail;
import org.egov.bpa.service.es.BpaIndexService;
import org.egov.bpa.transaction.entity.ApplicationStakeHolder;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.repository.PermitRevocationRepository;
import org.egov.infra.admin.master.entity.AppConfigValues;
import org.egov.infra.admin.master.service.AppConfigValueService;
import org.egov.infra.config.core.ApplicationThreadLocals;
import org.egov.infra.filestore.entity.FileStoreMapper;
import org.egov.infra.notification.service.NotificationService;
import org.egov.infra.reporting.engine.ReportOutput;
import org.egov.infra.utils.ApplicationNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PermitRevocationService {

    private static final String APP_PDF = "application/pdf";
    private static final String PDFEXTN = ".pdf";
    @Autowired
    private NotificationService notificationService;
    @Autowired
    @Qualifier("parentMessageSource")
    private MessageSource bpaMessageSource;
    @Autowired
    private AppConfigValueService appConfigValuesService;
    @Autowired
    private PermitRevocationRepository permitRevocationRepository;
    @Autowired
    private ApplicationNumberGenerator applicationNumberGenerator;
    @Autowired
    private ApplicationBpaService applicationBpaService;
    @Autowired
    private RevocationNumberGenerator revocationNumberGenerator;
    @Autowired
    private BpaIndexService bpaIndexService;

    @Transactional
    public PermitRevocation save(PermitRevocation permitRevocation) {
        if (permitRevocation.getApplicationNumber() == null) {
            permitRevocation.setApplicationNumber(applicationNumberGenerator.generate());
            permitRevocation.setApplicationDate(new Date());
        }
        permitRevocation.getApplication()
                .setStatus(applicationBpaService.getStatusByCodeAndModuleType(APPLICATION_STATUS_INIT_REVOKE));
        PermitRevocation revokeResponse = permitRevocationRepository.saveAndFlush(permitRevocation);
        bpaIndexService.updateIndexes(revokeResponse.getApplication());
        return revokeResponse;
    }

    @Transactional
    public PermitRevocation update(PermitRevocation permitRevocation) {
        if (!permitRevocation.getRevocationDetails().isEmpty())
            for (PermitRevocationDetail prd : permitRevocation.getRevocationDetails()) {
                prd.setRevocation(permitRevocation);
                if (prd.getFiles() != null && prd.getFiles().length > 0) {
                    Set<FileStoreMapper> revokeDocs = new HashSet<>();
                    revokeDocs.addAll(prd.getRevokeSupportDocs());
                    revokeDocs.addAll(applicationBpaService.addToFileStore(prd.getFiles()));
                    prd.setRevokeSupportDocs(revokeDocs);
                }
            }
        if ("Cancel Revocation".equalsIgnoreCase(permitRevocation.getWorkflowAction())) {
            permitRevocation.getApplication()
                    .setStatus(
                            applicationBpaService.getStatusByCodeAndModuleType(APPLICATION_STATUS_REVOKE_CANCELED));
        } else if ("Approve Revocation".equalsIgnoreCase(permitRevocation.getWorkflowAction())) {
            permitRevocation.setRevocationDate(new Date());
            permitRevocation.setRevocationNumber(revocationNumberGenerator.generatePermitRevocationNumber());
            permitRevocation.getApplication()
                    .setStatus(
                            applicationBpaService.getStatusByCodeAndModuleType(APPLICATION_STATUS_REVOKED));
        }
        PermitRevocation revokeResponse = permitRevocationRepository.save(permitRevocation);
        if (!"Save Revocation".equalsIgnoreCase(permitRevocation.getWorkflowAction()))
            bpaIndexService.updateIndexes(revokeResponse.getApplication());
        return revokeResponse;
    }

    public PermitRevocation findByPlanPermissionNumberAndRevocationApplnDate(final String permitNumber) {
        List<PermitRevocation> permitRevocations = permitRevocationRepository
                .findByApplicationPlanPermissionNumberOrderByIdDesc(permitNumber);
        return permitRevocations.isEmpty() ? null : permitRevocations.get(0);
    }

    public PermitRevocation findByApplicationNumber(final String applicationNumber) {
        return permitRevocationRepository.findByApplicationNumber(applicationNumber);
    }

    public PermitRevocation findByRevocationNumber(final String revocationNumber) {
        return permitRevocationRepository.findByRevocationNumber(revocationNumber);
    }

    public void sendSmsAndEmail(final PermitRevocation permitRevocation, ReportOutput reportOutput) {
        String mobileNo;
        String email;
        String applicantName;
        if (isSmsEnabled() || isEmailEnabled()) {
            ApplicationStakeHolder applnStakeHolder = permitRevocation.getApplication().getStakeHolder().get(0);
            if (applnStakeHolder.getApplication() != null && applnStakeHolder.getApplication().getOwner() != null) {
                applicantName = applnStakeHolder.getApplication().getOwner().getName();
                email = applnStakeHolder.getApplication().getOwner().getEmailId();
                mobileNo = applnStakeHolder.getApplication().getOwner().getUser().getMobileNumber();
                buildSmsAndEmail(permitRevocation, mobileNo, email, applicantName, reportOutput);
            }
            if (applnStakeHolder.getStakeHolder() != null && applnStakeHolder.getStakeHolder().isActive()) {
                applicantName = applnStakeHolder.getStakeHolder().getName();
                email = applnStakeHolder.getStakeHolder().getEmailId();
                mobileNo = applnStakeHolder.getStakeHolder().getMobileNumber();
                buildSmsAndEmail(permitRevocation, mobileNo, email, applicantName, reportOutput);
            }
        }

    }

    private void buildSmsAndEmail(PermitRevocation permitRevocation, String mobileNo, String email, String applicantName,
            ReportOutput reportOutput) {
        String smsMsg = "";
        String body = "";
        String subject = "";
        BpaApplication application = permitRevocation.getApplication();
        if (APPLICATION_STATUS_INIT_REVOKE.equals(application.getStatus().getCode())) {
            smsMsg = bpaMessageSource.getMessage("msg.permit.revoke.initiate.sms",
                    new String[] { application.getPlanPermissionNumber(),
                            toDefaultDateFormat(application.getPlanPermissionDate()),
                            permitRevocation.getApplicationNumber() },
                    null);
            body = bpaMessageSource.getMessage("msg.permit.revoke.initiate.email.body",
                    new String[] { applicantName, application.getPlanPermissionNumber(),
                            toDefaultDateFormat(application.getPlanPermissionDate()), permitRevocation.getApplicationNumber(),
                            permitRevocation.getInitiateRemarks(), ApplicationThreadLocals.getMunicipalityName() },
                    null);
            subject = bpaMessageSource.getMessage("msg.permit.revoke.initiate.email.subject",
                    new String[] { application.getPlanPermissionNumber() }, null);

        } else if (APPLICATION_STATUS_REVOKED
                .equals(application.getStatus().getCode())) {
            smsMsg = bpaMessageSource.getMessage("msg.permit.revoke.approve.sms",
                    new String[] { application.getPlanPermissionNumber(),
                            toDefaultDateFormat(application.getPlanPermissionDate()),
                            permitRevocation.getRevocationNumber(), toDefaultDateFormat(permitRevocation.getRevocationDate()) },
                    null);
            body = bpaMessageSource.getMessage("msg.permit.revoke.approve.email.body",
                    new String[] { applicantName, application.getPlanPermissionNumber(),
                            toDefaultDateFormat(application.getPlanPermissionDate()), permitRevocation.getRevocationNumber(),
                            toDefaultDateFormat(permitRevocation.getRevocationDate()), permitRevocation.getApproveCancelRemarks(),
                            ApplicationThreadLocals.getMunicipalityName() },
                    null);
            subject = bpaMessageSource.getMessage("msg.permit.revoke.approve.email.subject",
                    new String[] { application.getPlanPermissionNumber(), permitRevocation.getRevocationNumber() }, null);
        } else if (APPLICATION_STATUS_REVOKE_CANCELED
                .equals(application.getStatus().getCode())) {
            smsMsg = bpaMessageSource.getMessage("msg.permit.revoke.cancel.sms",
                    new String[] { application.getPlanPermissionNumber(),
                            toDefaultDateFormat(application.getPlanPermissionDate()),
                            toDefaultDateFormat(permitRevocation.getLastModifiedDate()) },
                    null);
            body = bpaMessageSource.getMessage("msg.permit.revoke.cancel.email.body",
                    new String[] { applicantName, application.getPlanPermissionNumber(),
                            toDefaultDateFormat(application.getPlanPermissionDate()),
                            toDefaultDateFormat(permitRevocation.getLastModifiedDate()),
                            permitRevocation.getApproveCancelRemarks(), ApplicationThreadLocals.getMunicipalityName() },
                    null);
            subject = bpaMessageSource.getMessage("msg.permit.revoke.cancel.email.subject",
                    new String[] { application.getPlanPermissionNumber() }, null);
        }
        if (isNotBlank(mobileNo) && isNotBlank(smsMsg))
            notificationService.sendSMS(mobileNo, smsMsg);
        if (isNotBlank(email) && isNotBlank(body) && reportOutput != null)
            notificationService.sendEmailWithAttachment(email, subject, body, APP_PDF, "permit_revocation" + PDFEXTN,
                    reportOutput.getReportOutputData());
        else if (isNotBlank(email) && isNotBlank(body))
            notificationService.sendEmail(email, subject, body);
    }

    public Boolean isSmsEnabled() {
        return getAppConfigValueByPassingModuleAndType(EGMODULE_NAME, SENDSMSFORBPA);
    }

    public Boolean getAppConfigValueByPassingModuleAndType(String moduleName, String sendsmsoremail) {
        final List<AppConfigValues> appConfigValue = appConfigValuesService.getConfigValuesByModuleAndKey(moduleName,
                sendsmsoremail);
        return YES.equalsIgnoreCase(
                appConfigValue != null && !appConfigValue.isEmpty() ? appConfigValue.get(0).getValue() : NO);
    }

    public Boolean isEmailEnabled() {
        return getAppConfigValueByPassingModuleAndType(EGMODULE_NAME, SENDEMAILFORBPA);
    }

}
