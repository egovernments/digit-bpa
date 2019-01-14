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
package org.egov.bpa.transaction.service.messaging.oc;

import org.egov.bpa.master.entity.StakeHolder;
import org.egov.bpa.transaction.entity.Applicant;
import org.egov.bpa.transaction.entity.ApplicationStakeHolder;
import org.egov.bpa.transaction.entity.oc.OCSlot;
import org.egov.bpa.utils.BpaConstants;
import org.egov.infra.admin.master.entity.AppConfigValues;
import org.egov.infra.admin.master.service.AppConfigValueService;
import org.egov.infra.config.core.ApplicationThreadLocals;
import org.egov.infra.notification.service.NotificationService;
import org.egov.infra.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_MODULE_TYPE;
import static org.egov.bpa.utils.BpaConstants.EGMODULE_NAME;
import static org.egov.bpa.utils.BpaConstants.NO;
import static org.egov.bpa.utils.BpaConstants.SENDEMAILFORBPA;
import static org.egov.bpa.utils.BpaConstants.SENDSMSFORBPA;
import static org.egov.bpa.utils.BpaConstants.YES;

@Service
@Transactional(readOnly = true)
public class OcSmsAndEmailService {
    private static final String MSG_KEY_SMS_BPA_DOC_SCRUTINY = "msg.bpa.oc.doc.scruty.schedule.sms";
    private static final String MSG_KEY_SMS_BPA_DOC_SCRUTINY_RESCHE = "msg.bpa.oc.doc.scruty.reschedule.sms";
    private static final String MSG_KEY_SMS_BPA_DOC_SCRUTINY_PENDING_FOR_RESCHEDULING = "msg.bpa.oc.doc.scruty.reschedule.pending.sms";
    private static final String MSG_KEY_SMS_BPA_DOC_SCRUTINY_CANCELLED = "msg.bpa.oc.doc.scruty.cancel.sms";

    private static final String SUB_KEY_EMAIL_BPA_DOCUMENT_SCRUTINY = "msg.bpa.oc.doc.scruty.email.sub";

    private static final String BODY_KEY_EMAIL_BPA_DOC_SCRUTINY = "msg.bpa.oc.doc.scruty.schedule.email.body";
    private static final String BODY_KEY_EMAIL_BPA_DOC_SCRUTINY_RESCHE = "msg.bpa.oc.doc.scruty.reschedule.email.body";
    private static final String BODY_KEY_EMAIL_BPA_DOC_SCRUTINY_PENDING_FOR_RESCHEDULING = "msg.bpa.oc.doc.scruty.reschedule.pending.email.body";
    private static final String BODY_KEY_EMAIL_BPA_DOC_SCRUTINY_CANCELLED = "msg.bpa.oc.doc.scruty.cancel.email.body";


    @Autowired
    private NotificationService notificationService;
    @Autowired
    private AppConfigValueService appConfigValuesService;
    @Autowired
    @Qualifier("parentMessageSource")
    private MessageSource bpaMessageSource;

    public String getMunicipalityName() {
        return ApplicationThreadLocals.getMunicipalityName();
    }


    public void sendSMSAndEmailForOcDocumentScrutiny(OCSlot ocSlot) {
        if (isSmsEnabled() || isEmailEnabled()) {
            ApplicationStakeHolder applnStakeHolder = ocSlot.getOc().getParent().getStakeHolder().get(0);
            if (applnStakeHolder.getApplication() != null && applnStakeHolder.getApplication().getOwner() != null) {
                Applicant owner = applnStakeHolder.getApplication().getOwner();
                buildSmsAndEmailForDocumentScrutinyForOc(ocSlot, owner.getName(), owner.getUser().getMobileNumber(), owner.getEmailId());
            }
            if (applnStakeHolder.getStakeHolder() != null && applnStakeHolder.getStakeHolder().getIsActive()) {
                StakeHolder stakeHolder = applnStakeHolder.getStakeHolder();
                buildSmsAndEmailForDocumentScrutinyForOc(ocSlot, stakeHolder.getName(), stakeHolder.getMobileNumber(), stakeHolder.getEmailId());
            }
        }
    }


    private void buildSmsAndEmailForDocumentScrutinyForOc(OCSlot ocSlot,
                                                          String name, String mobileNumber, String emailId) {
        String smsMsg = EMPTY;
        String body = EMPTY;
        String subject = EMPTY;
        if (ocSlot.getOc().getStatus().getCode().equals(BpaConstants.APPLICATION_STATUS_SCHEDULED)) {
            smsMsg = buildMessageDetailsForSchAppForDocumentScrutinyForOc(ocSlot, name,
                    MSG_KEY_SMS_BPA_DOC_SCRUTINY);
            body = buildMessageDetailsForSchAppForDocumentScrutinyForOc(ocSlot, name,
                    BODY_KEY_EMAIL_BPA_DOC_SCRUTINY);
            subject = emailSubjectforEmailForScheduleAppointmentForScrutiny(SUB_KEY_EMAIL_BPA_DOCUMENT_SCRUTINY);
        } else if (ocSlot.getOc().getStatus().getCode().equals(BpaConstants.APPLICATION_STATUS_RESCHEDULED)) {
            smsMsg = buildMessageDetailsForSchAppForDocumentScrutinyForOc(ocSlot, name,
                    MSG_KEY_SMS_BPA_DOC_SCRUTINY_RESCHE);
            body = buildMessageDetailsForSchAppForDocumentScrutinyForOc(ocSlot, name,
                    BODY_KEY_EMAIL_BPA_DOC_SCRUTINY_RESCHE);
            subject = emailSubjectforEmailForScheduleAppointmentForScrutiny(SUB_KEY_EMAIL_BPA_DOCUMENT_SCRUTINY);
        } else if (ocSlot.getOc().getStatus().getCode()
                .equals(BpaConstants.APPLICATION_STATUS_PENDING_FOR_RESCHEDULING)) {
            smsMsg = buildMessageDtlsFrPendingForReschedulingForOc(ocSlot,
                    name, MSG_KEY_SMS_BPA_DOC_SCRUTINY_PENDING_FOR_RESCHEDULING);
            body = buildMessageDtlsFrPendingForReschedulingForOc(ocSlot,
                    name, BODY_KEY_EMAIL_BPA_DOC_SCRUTINY_PENDING_FOR_RESCHEDULING);
            subject = emailSubjectforEmailForScheduleAppointmentForScrutiny(SUB_KEY_EMAIL_BPA_DOCUMENT_SCRUTINY);
        } else if (ocSlot.getOc().getStatus().getCode().equals(BpaConstants.APPLICATION_STATUS_CANCELLED)) {
            smsMsg = buildMessageDtlsFrCancellationForOc(ocSlot,
                    name, MSG_KEY_SMS_BPA_DOC_SCRUTINY_CANCELLED);
            body = buildMessageDtlsFrCancellationForOc(ocSlot,
                    name, BODY_KEY_EMAIL_BPA_DOC_SCRUTINY_CANCELLED);
            subject = emailSubjectforEmailForScheduleAppointmentForScrutiny(SUB_KEY_EMAIL_BPA_DOCUMENT_SCRUTINY);
        }
        if (isNotBlank(mobileNumber) && isNotBlank(smsMsg))
            notificationService.sendSMS(mobileNumber, smsMsg);
        if (isNotBlank(emailId) && isNotBlank(body))
            notificationService.sendEmail(emailId, subject, body);
    }

    private String emailSubjectforEmailForScheduleAppointmentForScrutiny(String msgKey) {
        return bpaMessageSource.getMessage(msgKey, new String[]{getMunicipalityName()}, null);
    }

    private String buildMessageDetailsForSchAppForDocumentScrutinyForOc(OCSlot ocSlot,
                                                                        String name, String msgKey) {
        String message;
        if (ocSlot.getOc().getStatus().getCode().equals(BpaConstants.APPLICATION_STATUS_SCHEDULED)) {
            message = bpaMessageSource.getMessage(msgKey,
                    new String[]{name,
                            DateUtils.toDefaultDateFormat(
                                    ocSlot.getSlotDetail().getSlot().getAppointmentDate()),
                            ocSlot.getSlotDetail().getAppointmentTime(),
                            ocSlot.getSlotDetail().getSlot().getZone().getName(),
                            getAppconfigValueByKeyNameForHelpLineNumber(),
                            ocSlot.getOc().getApplicationNumber(),
                            ApplicationThreadLocals.getDomainURL(),
                            getMunicipalityName()
                    },
                    null);
        } else {
            message = bpaMessageSource.getMessage(msgKey,
                    new String[]{name, ocSlot.getOc().getApplicationNumber(),
                            DateUtils.toDefaultDateFormat(
                                    ocSlot.getSlotDetail().getSlot().getAppointmentDate()),
                            ocSlot.getSlotDetail().getAppointmentTime(),
                            ocSlot.getSlotDetail().getSlot().getZone().getName(),
                            getAppconfigValueByKeyNameForHelpLineNumber(),
                            ApplicationThreadLocals.getDomainURL(),
                            getMunicipalityName()
                    },
                    null);
        }
        return message;
    }

    private String buildMessageDtlsFrPendingForReschedulingForOc(OCSlot ocSlot,
                                                                 String name, String msgKey) {
        return bpaMessageSource.getMessage(msgKey, new String[]{name, ocSlot.getOc().getApplicationNumber(),
                getMunicipalityName()}, null);
    }

    private String buildMessageDtlsFrCancellationForOc(
            OCSlot ocSlot, String name, String msgKey) {
        return bpaMessageSource.getMessage(msgKey, new String[]{name, ocSlot.getOc().getApplicationNumber(),
                ApplicationThreadLocals.getDomainURL(), getMunicipalityName()}, null);
    }

    public Boolean isSmsEnabled() {
        return getAppConfigValueByPassingModuleAndType(EGMODULE_NAME, SENDSMSFORBPA);
    }

    public Boolean isEmailEnabled() {
        return getAppConfigValueByPassingModuleAndType(EGMODULE_NAME, SENDEMAILFORBPA);
    }

    public Boolean getAppConfigValueByPassingModuleAndType(String moduleName, String sendsmsoremail) {
        final List<AppConfigValues> appConfigValue = appConfigValuesService.getConfigValuesByModuleAndKey(moduleName,
                sendsmsoremail);
        return YES.equalsIgnoreCase(
                appConfigValue != null && !appConfigValue.isEmpty() ? appConfigValue.get(0).getValue() : NO);
    }

    public String getAppconfigValueByKeyNameForHelpLineNumber() {
        List<AppConfigValues> appConfigValueList = appConfigValuesService
                .getConfigValuesByModuleAndKey(APPLICATION_MODULE_TYPE, "HELPLINENUMBER");
        return !appConfigValueList.isEmpty() ? appConfigValueList.get(0).getValue() : "";
    }
}

