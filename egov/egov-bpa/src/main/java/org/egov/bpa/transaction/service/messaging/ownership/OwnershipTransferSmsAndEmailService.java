package org.egov.bpa.transaction.service.messaging.ownership;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_REJECTED;
import static org.egov.bpa.utils.BpaConstants.APPROVED;
import static org.egov.bpa.utils.BpaConstants.EGMODULE_NAME;
import static org.egov.bpa.utils.BpaConstants.NO;
import static org.egov.bpa.utils.BpaConstants.SENDEMAILFORBPA;
import static org.egov.bpa.utils.BpaConstants.SENDSMSFORBPA;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_ORDER_ISSUED;
import static org.egov.bpa.utils.BpaConstants.YES;

import java.util.List;
import java.util.Locale;

import org.egov.bpa.transaction.entity.Applicant;
import org.egov.bpa.transaction.entity.ApplicationStakeHolder;
import org.egov.bpa.transaction.entity.OwnershipTransfer;
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
public class OwnershipTransferSmsAndEmailService {

    private static final String APP_PDF = "application/pdf";
    private static final String MSG_KEY_SMS_OWNERSHIP_REJECT = "msg.ownership.reject.appln.sms";
    private static final String SUBJECT_KEY_EMAIL_OWNERSHIP_REJECT = "msg.ownership.reject.appln.email.subject";
    private static final String BODY_KEY_EMAIL_OWNERSHIP_REJECT = "msg.ownership.reject.appln.email.body";

    private static final String MSG_KEY_SMS_OWNERSHIP_APPROVAL = "msg.ownership.approval.appln.sms";
    private static final String SUBJECT_KEY_EMAIL_OWNERSHIP_APPROVAL = "msg.ownership.approval.appln.email.subject";
    private static final String BODY_KEY_EMAIL_OWNERSHIP_APPROVAL = "msg.ownership.approval.appln.email.body";

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

    public void sendSMSAndEmail(final OwnershipTransfer ownership, ReportOutput reportOutput, String fileName) {
        String mobileNo;
        String email;
        String applicantName;
        if (isSmsEnabled() || isEmailEnabled()) {
            ApplicationStakeHolder applnStakeHolder = ownership.getParent().getStakeHolder().get(0);
            if (applnStakeHolder.getApplication() != null && applnStakeHolder.getApplication().getOwner() != null) {
                Applicant applicant = ownership.getOwner(); 
                applicantName = applicant.getName();        
                email = applicant.getEmailId();
                mobileNo = applicant.getUser().getMobileNumber();
                buildSmsAndEmailForOwnershipTransfer(ownership, applicantName, mobileNo, email,
                        reportOutput, fileName);
            }
            if (applnStakeHolder.getApplication() != null && applnStakeHolder.getApplication().getOwner() != null 
                    && ownership.getParent() != null ){
                Applicant applicant = ownership.getParent().getOwner();
                applicantName = applicant.getName();         
                email = applicant.getEmailId();
                mobileNo = applicant.getUser().getMobileNumber();
                buildSmsAndEmailForOwnershipTransfer(ownership, applicantName, mobileNo, email,
                     reportOutput, fileName);
            }
                else {
                    Applicant applicant = applnStakeHolder.getApplication().getOwner();
                    applicantName = applicant.getName();          
                    email = applicant.getEmailId();
                    mobileNo = applicant.getUser().getMobileNumber();
                    buildSmsAndEmailForOwnershipTransfer(ownership, applicantName, mobileNo, email,
                                        reportOutput, fileName);
            }
            if (applnStakeHolder.getStakeHolder() != null && applnStakeHolder.getStakeHolder().isActive()) {
                applicantName = applnStakeHolder.getStakeHolder().getName();           //stakeholder 
                email = applnStakeHolder.getStakeHolder().getEmailId();
                mobileNo = applnStakeHolder.getStakeHolder().getMobileNumber();
                buildSmsAndEmailForOwnershipTransfer(ownership, applicantName, mobileNo, email,
                        reportOutput, fileName);
            }
        }
    }

    private void buildSmsAndEmailForOwnershipTransfer(final OwnershipTransfer ownership, final String applicantName,
            final String mobileNo, final String email, ReportOutput reportOutput, String fileName) {
        String smsMsg = EMPTY;
        String body = EMPTY;
        String subject = EMPTY;
        if (APPLICATION_STATUS_ORDER_ISSUED.equalsIgnoreCase(ownership.getStatus().getCode())) {
            smsMsg = smsBodyByCodeAndArgsWithType(MSG_KEY_SMS_OWNERSHIP_APPROVAL, applicantName,
                    ownership, APPLICATION_STATUS_ORDER_ISSUED, EMPTY);
            body = emailBodyByCodeAndArgsWithType(BODY_KEY_EMAIL_OWNERSHIP_APPROVAL, applicantName,
                    ownership, APPLICATION_STATUS_ORDER_ISSUED);
            subject = emailSubjectforEmailByCodeAndArgs(SUBJECT_KEY_EMAIL_OWNERSHIP_APPROVAL, ownership.getApplicationNumber());
        } else if (APPLICATION_STATUS_REJECTED.equalsIgnoreCase(ownership.getStatus().getCode())) {
            smsMsg = smsBodyByCodeAndArgsWithType(MSG_KEY_SMS_OWNERSHIP_REJECT, applicantName,
                    ownership, APPLICATION_STATUS_REJECTED, EMPTY);
            body = emailBodyByCodeAndArgsWithType(BODY_KEY_EMAIL_OWNERSHIP_REJECT, applicantName,
                    ownership, APPLICATION_STATUS_REJECTED);
            subject = emailSubjectforEmailByCodeAndArgs(SUBJECT_KEY_EMAIL_OWNERSHIP_REJECT, ownership.getApplicationNumber());
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

    private String smsBodyByCodeAndArgsWithType(String code, String applicantName, OwnershipTransfer ownership,
            String type, String initiator) {
        String smsMsg = EMPTY;
        if (APPLICATION_STATUS_ORDER_ISSUED.equalsIgnoreCase(type)) {
            smsMsg = bpaMessageSource.getMessage(code,
                    new String[] { ownership.getApplicationNumber(),
                            DateUtils.toDefaultDateFormat(ownership.getApplicationDate()),
                            getMunicipalityName() },
                    null);
        } else if (APPLICATION_STATUS_REJECTED.equalsIgnoreCase(type)) {
            smsMsg = bpaMessageSource.getMessage(code,
                    new String[] { ownership.getApplicationNumber(),
                            getMunicipalityName() },
                    null);
        }
        return smsMsg;
    }

    private String emailBodyByCodeAndArgsWithType(String code, String applicantName, OwnershipTransfer ownership,
            String type) {
        String body = EMPTY;
        if (APPLICATION_STATUS_ORDER_ISSUED.equalsIgnoreCase(type)) {
            body = bpaMessageSource.getMessage(code,
                    new String[] { applicantName, ownership.getApplicationNumber(),
                            DateUtils.toDefaultDateFormat(ownership.getApplicationDate()),
                            getMunicipalityName(), ApplicationThreadLocals.getDomainURL() },
                    null);
        } else if (APPLICATION_STATUS_REJECTED.equalsIgnoreCase(type)) {
            body = bpaMessageSource.getMessage(code,
                    new String[] { applicantName, ownership.getApplicationNumber(),
                            isNotBlank(ownership.getState().getComments()) ? ownership.getState().getComments() : EMPTY,
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
