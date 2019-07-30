/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2017>  eGovernments Foundation
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
package org.egov.bpa.transaction.service.messaging.renewal;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_REGISTERED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_REJECTED;
import static org.egov.bpa.utils.BpaConstants.CREATEDLETTERTOPARTY;
import static org.egov.bpa.utils.BpaConstants.EGMODULE_NAME;
import static org.egov.bpa.utils.BpaConstants.NO;
import static org.egov.bpa.utils.BpaConstants.SENDEMAILFORBPA;
import static org.egov.bpa.utils.BpaConstants.SENDSMSFORBPA;
import static org.egov.bpa.utils.BpaConstants.SMSEMAILTYPELETTERTOPARTY;
import static org.egov.bpa.utils.BpaConstants.SMSEMAILTYPENEWBPAREGISTERED;
import static org.egov.bpa.utils.BpaConstants.YES;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import org.egov.bpa.master.entity.StakeHolder;
import org.egov.bpa.transaction.entity.Applicant;
import org.egov.bpa.transaction.entity.ApplicationStakeHolder;
import org.egov.bpa.transaction.entity.PermitRenewal;
import org.egov.collection.integration.models.BillReceiptInfo;
import org.egov.infra.admin.master.entity.AppConfigValues;
import org.egov.infra.admin.master.service.AppConfigValueService;
import org.egov.infra.config.core.ApplicationThreadLocals;
import org.egov.infra.notification.service.NotificationService;
import org.egov.infra.reporting.engine.ReportOutput;
import org.egov.infra.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RenewalSmsAndEmailService {
    private static final String SUBJECT_KEY_EMAIL_RENEWAL_NEW = "msg.renewal.app.email.sub";
    private static final String BODY_KEY_EMAIL_RENEWAL_NEW = "msg.renewal.submit.email.body";
    private static final String MSG_KEY_SMS_RENEWAL_NEW = "msg.renewal.submit.sms.body";   

    private static final String MSG_KEY_SMS_RENEWAL_LETTERTOPARTY = "msg.renewal.lettertoparty.sms";
    private static final String SUBJECT_KEY_EMAIL_RENEWAL_LETTERTOPARTY = "msg.renewal.lettertoparty.email.subject";
    private static final String BODY_KEY_EMAIL_RENEWAL_LETTERTOPARTY = "msg.renewal.lettertoparty.email.body";
    private static final String MSG_KEY_SMS_RENEWAL_REJECT = "msg.renewal.reject.appln.sms";
    private static final String SUBJECT_KEY_EMAIL_RENEWAL_REJECT = "msg.renewal.reject.appln.email.subject";
    private static final String BODY_KEY_EMAIL_RENEWAL_REJECT = "msg.renewal.reject.appln.email.body";
 
    private static final String SMS_KEY_APRVL = "msg.renewal.approval.sms";
    private static final String EMLB_KEY_APRVL = "msg.renewal.approval.email.body";

    private static final String SMS_KEY_APRVL_WITHOUT_FEE = "msg.renewal.approval.sms.without.fee";
    private static final String EMLB_KEY_APRVL_WITHOUT_FEE = "msg.renewal.approval.email.body.without.fee";

    private static final String EMLS_KEY_APRVL = "msg.renewal.approval.email.subject";
    private static final String EMLB_KEY_RO = "msg.renewalorder.generation.email.body";
    private static final String SMS_KEY_RO = "msg.renewalorder.generation.sms";
    private static final String EMLS_KEY_RO = "msg.renewalorder.generation.email.subject";

    private static final String APP_PDF = "application/pdf";
    private static final String PDFEXTN = ".pdf";
    private static final String SMS_KEY_CLCTN = "msg.collection.sms";

    @Autowired
    private NotificationService notificationService;
    @Autowired
    @Qualifier("parentMessageSource")
    private MessageSource bpaMessageSource;
    @Autowired
    private AppConfigValueService appConfigValuesService;
    
    public String getMunicipalityName() {
        return ApplicationThreadLocals.getMunicipalityName();
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
    
    public void sendSMSAndEmail(final PermitRenewal renewal, ReportOutput reportOutput, String fileName) {
        String mobileNo;
        String email;
        String applicantName;
        if (isSmsEnabled() || isEmailEnabled()) {
            ApplicationStakeHolder applnStakeHolder = renewal.getParent().getStakeHolder().get(0);
            if (applnStakeHolder.getApplication() != null && applnStakeHolder.getApplication().getOwner() != null) {
                Applicant applicant = applnStakeHolder.getApplication().getOwner();
                applicantName = applicant.getName();
                email = applicant.getEmailId();
                mobileNo = applicant.getUser().getMobileNumber();
                buildSmsAndEmailForRenewalAppln(renewal, applicantName, mobileNo, email,  
                        reportOutput, fileName);
            }
            if (applnStakeHolder.getStakeHolder() != null && applnStakeHolder.getStakeHolder().isActive()) {
                applicantName = applnStakeHolder.getStakeHolder().getName();
                email = applnStakeHolder.getStakeHolder().getEmailId();
                mobileNo = applnStakeHolder.getStakeHolder().getMobileNumber();
                buildSmsAndEmailForRenewalAppln(renewal, applicantName, mobileNo, email, 
                        reportOutput, fileName);
            }
        }
    }
    
    private void buildSmsAndEmailForRenewalAppln(final PermitRenewal renewal, final String applicantName,
            final String mobileNo, final String email, 
            ReportOutput reportOutput, String fileName) {
        String smsMsg = EMPTY;
        String body = EMPTY;
        String subject = EMPTY;
        String smsCode;
        String mailCode;
        if ((APPLICATION_STATUS_REGISTERED).equalsIgnoreCase(renewal.getStatus().getCode())) {
                smsCode = MSG_KEY_SMS_RENEWAL_NEW;
                mailCode = BODY_KEY_EMAIL_RENEWAL_NEW;
           
            smsMsg = smsBodyByCodeAndArgsWithType(smsCode, applicantName, renewal,
                    SMSEMAILTYPENEWBPAREGISTERED, applicantName);
            body = emailBodyByCodeAndArgsWithType(mailCode, applicantName,
            		renewal, SMSEMAILTYPENEWBPAREGISTERED);
            subject = emailSubjectforEmailByCodeAndArgs(SUBJECT_KEY_EMAIL_RENEWAL_NEW, renewal.getApplicationNumber());
        } else if (CREATEDLETTERTOPARTY.equalsIgnoreCase(renewal.getStatus().getCode())) {
            smsMsg = smsBodyByCodeAndArgsWithType(MSG_KEY_SMS_RENEWAL_LETTERTOPARTY, applicantName,
            		renewal, SMSEMAILTYPELETTERTOPARTY, EMPTY);
            body = emailBodyByCodeAndArgsWithType(BODY_KEY_EMAIL_RENEWAL_LETTERTOPARTY, applicantName,
            		renewal, SMSEMAILTYPELETTERTOPARTY);
            subject = emailSubjectforEmailByCodeAndArgs(SUBJECT_KEY_EMAIL_RENEWAL_LETTERTOPARTY, renewal.getApplicationNumber());
        } else if (APPLICATION_STATUS_REJECTED.equalsIgnoreCase(renewal.getStatus().getCode())) {
            smsMsg = smsBodyByCodeAndArgsWithType(MSG_KEY_SMS_RENEWAL_REJECT, applicantName,
            		renewal, APPLICATION_STATUS_REJECTED, EMPTY);
            body = emailBodyByCodeAndArgsWithType(BODY_KEY_EMAIL_RENEWAL_REJECT, applicantName,
            		renewal, APPLICATION_STATUS_REJECTED);
            subject = emailSubjectforEmailByCodeAndArgs(SUBJECT_KEY_EMAIL_RENEWAL_REJECT, renewal.getApplicationNumber());
        } 
        if (mobileNo != null && smsMsg != null)
            notificationService.sendSMS(mobileNo, smsMsg);
        if (email != null && body != null && reportOutput != null && fileName != null) {
            notificationService.sendEmailWithAttachment(email, subject, body, APP_PDF, fileName,
                    reportOutput.getReportOutputData());
        } else if (email != null && body != null) {
            notificationService.sendEmail(email, subject, body);
        }
    }

    public void sendSmsAndEmailOnRenewalOrderGeneration(PermitRenewal renewal, ReportOutput reportOutput) {
        if (isSmsEnabled() || isEmailEnabled()) {
            ApplicationStakeHolder applnStakeHolder = renewal.getParent().getStakeHolder().get(0);
            if (applnStakeHolder.getApplication() != null && applnStakeHolder.getApplication().getOwner() != null) {
                Applicant owner = applnStakeHolder.getApplication().getOwner();
                buildSmsAndEmailOnRenewalOrderGeneration(renewal, owner.getName(), owner.getUser().getMobileNumber(),
                        owner.getEmailId(), reportOutput);
            }
            if (applnStakeHolder.getStakeHolder() != null && applnStakeHolder.getStakeHolder().isActive()) {
                StakeHolder stakeHolder = applnStakeHolder.getStakeHolder();
                buildSmsAndEmailOnRenewalOrderGeneration(renewal, stakeHolder.getName(), stakeHolder.getMobileNumber(),
                        stakeHolder.getEmailId(), reportOutput);
            }
        }
    }
    
    private void buildSmsAndEmailOnRenewalOrderGeneration(PermitRenewal renewal, String name, String mobileNumber,
            String emailId, ReportOutput reportOutput) {
        String smsMsg = buildMsgDetailsOnRenewalOrderGeneration(renewal, name, SMS_KEY_RO);
        String body = buildMsgDetailsOnRenewalOrderGeneration(renewal, name, EMLB_KEY_RO);
        String subject = buildEmailSubjectOnRenewalOrderGeneration(renewal, EMLS_KEY_RO);
        if (isNotBlank(mobileNumber) && isNotBlank(smsMsg)) {
            notificationService.sendSMS(mobileNumber, smsMsg);
        }
        if (isNotBlank(emailId) && isNotBlank(body)) {
            notificationService.sendEmailWithAttachment(emailId, subject, body, APP_PDF,
                    "permitrenewalorder" + PDFEXTN, reportOutput.getReportOutputData());
        }
    }
    
    private String buildEmailSubjectOnRenewalOrderGeneration(PermitRenewal renewal, String msgKey) {
        return bpaMessageSource.getMessage(msgKey, new String[] { renewal.getApplicationNumber() }, null);
    }

    private String buildMsgDetailsOnRenewalOrderGeneration(PermitRenewal renewal, String name, String msgKey) {
        String msg = bpaMessageSource.getMessage(msgKey,
                new String[] { name, renewal.getApplicationNumber(), getMunicipalityName() }, null);
        return msg;
    }
    
    public void sendSmsForCollection(BigDecimal totalAmount, PermitRenewal renewal, BillReceiptInfo billRcptInfo) {
        if (isSmsEnabled()) {
            String msg = buildSmsMsgDetailsForCollection(totalAmount, renewal, billRcptInfo);
            ApplicationStakeHolder applnStakeHolder = renewal.getParent().getStakeHolder().get(0);
            if (applnStakeHolder.getApplication() != null && applnStakeHolder.getApplication().getOwner() != null) {
                Applicant owner = applnStakeHolder.getApplication().getOwner();
                if (isNotBlank(owner.getUser().getMobileNumber()) && isNotBlank(msg)) {
                    notificationService.sendSMS(owner.getUser().getMobileNumber(), msg);
                }
            }
            if (applnStakeHolder.getStakeHolder() != null && applnStakeHolder.getStakeHolder().isActive()) {
                StakeHolder stakeHolder = applnStakeHolder.getStakeHolder();
                if (isNotBlank(stakeHolder.getMobileNumber()) && isNotBlank(msg)) {
                    notificationService.sendSMS(stakeHolder.getMobileNumber(), msg);
                }
                if (isEmailEnabled() && isNotBlank(stakeHolder.getEmailId()))
                    notificationService.sendEmail(stakeHolder.getEmailId(),
                            bpaMessageSource.getMessage("msg.renewal.email.subject.collection",
                                    new String[] { billRcptInfo.getServiceName() }, null),
                            buildEmailBodyForCollection(totalAmount, renewal, billRcptInfo));
            }
        }
    }
    
    public void sendSmsAndEmailOnRenewalApproval(PermitRenewal renewal, ReportOutput reportOutput) {
        if (isSmsEnabled() || isEmailEnabled()) {
            ApplicationStakeHolder applnStakeHolder = renewal.getParent().getStakeHolder().get(0);
            if (applnStakeHolder.getApplication() != null && applnStakeHolder.getApplication().getOwner() != null) {
                Applicant owner = applnStakeHolder.getApplication().getOwner();
                buildSmsAndEmailOnRenewalApproval(renewal, owner.getName(), owner.getUser().getMobileNumber(),
                        owner.getEmailId(), reportOutput);
            }
            if (applnStakeHolder.getStakeHolder() != null && applnStakeHolder.getStakeHolder().isActive()) {
                StakeHolder stakeHolder = applnStakeHolder.getStakeHolder();
                buildSmsAndEmailOnRenewalApproval(renewal, stakeHolder.getName(), stakeHolder.getMobileNumber(),
                        stakeHolder.getEmailId(), reportOutput);
            }
            buildSmsAndEmailOnRenewalApproval(renewal, renewal.getParent().getOwner().getName(),
            		renewal.getParent().getOwner().getUser().getMobileNumber(), renewal.getParent().getOwner().getEmailId(),
                    reportOutput);
        }

    }
    
    private void buildSmsAndEmailOnRenewalApproval(PermitRenewal renewal, String name, String mobileNumber,
            String emailId, ReportOutput reportOutput) {
        String smsMsg = "";
        String body = "";

        Boolean feeExists = renewal.getDemand().getBaseDemand().subtract(renewal.getDemand().getAmtCollected())
                .compareTo(BigDecimal.ZERO) > 0 ? Boolean.TRUE : Boolean.FALSE;

        if (feeExists) {
            smsMsg = buildMsgDetailsOnRenewalApproval(renewal, name, SMS_KEY_APRVL);
            body = buildMsgDetailsOnRenewalApproval(renewal, name, EMLB_KEY_APRVL);
        } else {
            smsMsg = buildMsgDetailsOnRenewalApproval(renewal, name, SMS_KEY_APRVL_WITHOUT_FEE);
            body = buildMsgDetailsOnRenewalApproval(renewal, name, EMLB_KEY_APRVL_WITHOUT_FEE);
        }
        String subject = buildMsgDetailsForEmailSubjectOnRenewalApproval(renewal, EMLS_KEY_APRVL);
        if (isNotBlank(mobileNumber) && isNotBlank(smsMsg)) {
            notificationService.sendSMS(mobileNumber, smsMsg);
        }
        if (isNotBlank(emailId) && isNotBlank(body))
                notificationService.sendEmail(emailId, subject, body);
    }
    
    private String buildSmsMsgDetailsForCollection(BigDecimal totalAmount, PermitRenewal renewal,
            BillReceiptInfo billRcptInfo) {
        String msg = bpaMessageSource.getMessage(SMS_KEY_CLCTN,
                new String[] { totalAmount.toString(), billRcptInfo.getServiceName(),
                        renewal.getApplicationNumber(), billRcptInfo.getReceiptDate().toString() },
                null);
        return msg;
    }

    private String buildEmailBodyForCollection(BigDecimal totalAmount, PermitRenewal renewal,
            BillReceiptInfo billRcptInfo) {
        String msg = bpaMessageSource.getMessage("msg.renewal.email.body.fee.collection",
                new String[] { getMunicipalityName(), totalAmount.toString(), billRcptInfo.getServiceName(),
                        renewal.getApplicationNumber(), billRcptInfo.getReceiptDate().toString(), getMunicipalityName() },
                null);
        return msg;
    }
    
    private String buildMsgDetailsOnRenewalApproval(PermitRenewal renewal, String name, String msgKey) {
        BigDecimal amtToRemit = renewal.getDemand().getBaseDemand()
                .subtract(renewal.getDemand().getAmtCollected());
        String msg = bpaMessageSource.getMessage(msgKey,
                new String[] { name, renewal.getApplicationNumber(), amtToRemit.toString(), getMunicipalityName() },
                null);
        return msg;
    }
    
    private String buildMsgDetailsForEmailSubjectOnRenewalApproval(PermitRenewal renewal, String msgKey) {
        return bpaMessageSource.getMessage(msgKey, new String[] { renewal.getApplicationNumber() }, null);
    }
    
    private String smsBodyByCodeAndArgsWithType(String code, String applicantName, PermitRenewal renewal,
            String type, String initiator) {
        String smsMsg = EMPTY;
        if (SMSEMAILTYPENEWBPAREGISTERED.equalsIgnoreCase(type)) {
                smsMsg = bpaMessageSource.getMessage(code,
                        new String[] {renewal.getApplicationNumber(), DateUtils.toDefaultDateFormat(renewal.getApplicationDate()),
                                getMunicipalityName() },
                        null);
        } else if (SMSEMAILTYPELETTERTOPARTY.equalsIgnoreCase(type))
            smsMsg = bpaMessageSource.getMessage(code,
                    new String[] { applicantName, renewal.getApplicationNumber(), getMunicipalityName() }, null);
        else if (APPLICATION_STATUS_REJECTED.equalsIgnoreCase(type)) {

            smsMsg = bpaMessageSource.getMessage(code,
                    new String[] { renewal.getApplicationNumber(),
                            getMunicipalityName() },
                    null);
        }
        return smsMsg;
    }
    
    private String emailBodyByCodeAndArgsWithType(String code, String applicantName, PermitRenewal renewal,
            String type) {
        String body = EMPTY;
        if (SMSEMAILTYPENEWBPAREGISTERED.equalsIgnoreCase(type)) {
                body = bpaMessageSource.getMessage(code,
                        new String[] {applicantName,  renewal.getApplicationNumber(), DateUtils.toDefaultDateFormat(renewal.getApplicationDate()), 
                                getMunicipalityName(), ApplicationThreadLocals.getDomainURL() },
                        null);
        } else if (SMSEMAILTYPELETTERTOPARTY.equalsIgnoreCase(type))
            body = bpaMessageSource.getMessage(code,
                    new String[] { applicantName, renewal.getApplicationNumber(), getMunicipalityName() }, null);
        else if (APPLICATION_STATUS_REJECTED.equalsIgnoreCase(type)) {
            body = bpaMessageSource.getMessage(code,
                    new String[] { applicantName, renewal.getApplicationNumber(),
                             isNotBlank(renewal.getState().getComments()) ? renewal.getState().getComments() : EMPTY,
                            getMunicipalityName() },
                    null);
        }
        return body;
    }
    
    private String emailSubjectforEmailByCodeAndArgs(String code, String applicationNumber) {
        final Locale locale = LocaleContextHolder.getLocale();
        return bpaMessageSource.getMessage(code, new String[] { applicationNumber }, locale);
    }



}