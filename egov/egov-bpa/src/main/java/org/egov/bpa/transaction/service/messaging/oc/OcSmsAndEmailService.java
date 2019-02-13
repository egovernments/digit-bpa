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
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.BpaAppointmentSchedule;
import org.egov.bpa.transaction.entity.common.AppointmentScheduleCommon;
import org.egov.bpa.transaction.entity.enums.AppointmentSchedulePurpose;
import org.egov.bpa.transaction.entity.oc.OCAppointmentSchedule;
import org.egov.bpa.transaction.entity.oc.OCSlot;
import org.egov.bpa.transaction.entity.oc.OccupancyCertificate;
import org.egov.bpa.utils.BpaConstants;
import org.egov.bpa.utils.BpaUtils;
import org.egov.collection.integration.models.BillReceiptInfo;
import org.egov.infra.admin.master.entity.AppConfigValues;
import org.egov.infra.admin.master.service.AppConfigValueService;
import org.egov.infra.config.core.ApplicationThreadLocals;
import org.egov.infra.notification.service.NotificationService;
import org.egov.infra.reporting.engine.ReportOutput;
import org.egov.infra.utils.DateUtils;
import org.egov.infra.workflow.entity.StateHistory;
import org.egov.pims.commons.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.jasperreports.engine.part.FinalFillingPrintPart;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_MODULE_TYPE;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_CANCELLED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_REGISTERED;
import static org.egov.bpa.utils.BpaConstants.BUILDINGPERMITFILENAME;
import static org.egov.bpa.utils.BpaConstants.CREATEDLETTERTOPARTY;
import static org.egov.bpa.utils.BpaConstants.EGMODULE_NAME;
import static org.egov.bpa.utils.BpaConstants.NO;
import static org.egov.bpa.utils.BpaConstants.SENDEMAILFORBPA;
import static org.egov.bpa.utils.BpaConstants.SENDSMSFORBPA;
import static org.egov.bpa.utils.BpaConstants.SMSEMAILTYPELETTERTOPARTY;
import static org.egov.bpa.utils.BpaConstants.SMSEMAILTYPENEWBPAREGISTERED;
import static org.egov.bpa.utils.BpaConstants.YES;
import static org.egov.bpa.utils.BpaConstants.OCDEMANDFILENAME;

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

    ///////////////////////////////////////////////////////////////////////////////////////////////

    private static final String MSG_KEY_SMS_OC_APPLN_NEW_CZN = "msg.oc.submit.sms.citizen";
    private static final String BODY_KEY_EMAIL_OC_APPLN_NEW_CZN = "msg.oc.submit.mail.body.citizen";
    private static final String SUBJECT_KEY_EMAIL_OC_APPLN_NEW = "msg.oc.submit.mail.subject";
    private static final String MSG_KEY_SMS_OC_APPLN_NEW = "msg.oc.submit.sms";
    private static final String BODY_KEY_EMAIL_OC_APPLN_NEW = "msg.oc.submit.mail.body";

    private static final String SMS_KEY_CLCTN = "msg.oc.collection.sms";

    private static final String SMS_STK_RJCT = "msg.stakeholder.rejection.sms";
    private static final String EMLB_STK_RJCT = "msg.stakeholder.rejection.email.body";
    private static final String EMLS_STK_RJCT = "msg.stakeholder.rejection.email.subject";

    private static final String MSG_KEY_SMS_LETTERTOPARTY = "msg.oc.lettertoparty.sms";
    private static final String BODY_KEY_EMAIL_LETTERTOPARTY = "msg.oc.lettertoparty.email.body";
    private static final String SUBJECT_KEY_EMAIL_LETTERTOPARTY = "msg.oc.lettertoparty.email.subject";

    private static final String MSG_KEY_SMS_CANCELL_APPLN = "msg.oc.cancel.appln.sms";
    private static final String BODY_KEY_EMAIL_CANCELL_APPLN = "msg.oc.cancel.appln.email.body";
    private static final String SUBJECT_KEY_EMAIL_CANCELL_APPLN = "msg.oc.cancel.appln.email.subject";

    private static final String APP_PDF = "application/pdf";
    private static final String PDFEXTN = ".pdf";
    private static final String SMS_KEY_APRVL = "msg.oc.application.approval.sms";
    private static final String EMLB_KEY_APRVL = "msg.oc.application.approval.email.body";
    private static final String EMLS_KEY_APRVL = "msg.oc.application.approval.email.subject";
    private static final String EMLS_KEY_PO = "msg.oc.permitorder.generation.email.subject";
    private static final String SMS_KEY_PO = "msg.oc.permitorder.generation.sms";
    private static final String EMLB_KEY_PO = "msg.oc.permitorder.generation.email.body";

    private static final String MSG_KEY_SMS_OC_FIELD_INS = "msg.oc.field.ins.schedule.sms";
    private static final String BODY_KEY_EMAIL_OC_FIELD_INS = "msg.oc.field.ins.schedule.email.body";
    private static final String SUBJECT_KEY_EMAIL_OC_FIELD_INS_RESCHE = "msg.oc.field.ins.reschedule.email.subject";
    private static final String SUBJECT_KEY_EMAIL_OC_FIELD_INS = "msg.oc.field.ins.schedule.email.subject";
    private static final String MSG_KEY_SMS_OC_FIELD_INS_RESCHE = "msg.oc.field.ins.reschedule.sms";
    private static final String BODY_KEY_EMAIL_OC_FIELD_INS_RESCHE = "msg.oc.field.ins.reschedule.email.body";

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private AppConfigValueService appConfigValuesService;
    @Autowired
    @Qualifier("parentMessageSource")
    private MessageSource bpaMessageSource;
    @Autowired
    private BpaUtils bpaUtils;

    // @Autowired
    // private AppointmentScheduleCommon appointmentScheduleCommon;

    public String getMunicipalityName() {
        return ApplicationThreadLocals.getMunicipalityName();
    }

    public void sendSMSAndEmailForOcDocumentScrutiny(OCSlot ocSlot) {
        if (isSmsEnabled() || isEmailEnabled()) {
            ApplicationStakeHolder applnStakeHolder = ocSlot.getOc().getParent().getStakeHolder().get(0);
            if (applnStakeHolder.getApplication() != null && applnStakeHolder.getApplication().getOwner() != null) {
                Applicant owner = applnStakeHolder.getApplication().getOwner();
                buildSmsAndEmailForDocumentScrutinyForOc(ocSlot, owner.getName(), owner.getUser().getMobileNumber(),
                        owner.getEmailId());
            }
            if (applnStakeHolder.getStakeHolder() != null && applnStakeHolder.getStakeHolder().getIsActive()) {
                StakeHolder stakeHolder = applnStakeHolder.getStakeHolder();
                buildSmsAndEmailForDocumentScrutinyForOc(ocSlot, stakeHolder.getName(), stakeHolder.getMobileNumber(),
                        stakeHolder.getEmailId());
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
        return bpaMessageSource.getMessage(msgKey, new String[] { getMunicipalityName() }, null);
    }

    private String buildMessageDetailsForSchAppForDocumentScrutinyForOc(OCSlot ocSlot,
            String name, String msgKey) {
        String message;
        if (ocSlot.getOc().getStatus().getCode().equals(BpaConstants.APPLICATION_STATUS_SCHEDULED)) {
            message = bpaMessageSource.getMessage(msgKey,
                    new String[] { name,
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
                    new String[] { name, ocSlot.getOc().getApplicationNumber(),
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
        return bpaMessageSource.getMessage(msgKey, new String[] { name, ocSlot.getOc().getApplicationNumber(),
                getMunicipalityName() }, null);
    }

    private String buildMessageDtlsFrCancellationForOc(
            OCSlot ocSlot, String name, String msgKey) {
        return bpaMessageSource.getMessage(msgKey, new String[] { name, ocSlot.getOc().getApplicationNumber(),
                ApplicationThreadLocals.getDomainURL(), getMunicipalityName() }, null);
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

    private String emailSubjectforEmailByCodeAndArgs(String code, String applicationNumber) {
        final Locale locale = LocaleContextHolder.getLocale();
        return bpaMessageSource.getMessage(code, new String[] { applicationNumber }, locale);
    }

    public void sendSMSAndEmail(final OccupancyCertificate occupancyCertificate, ReportOutput reportOutput, String fileName) {
        String mobileNo;
        String email;
        String applicantName;
        String loginUserName;
        String password;
        if (isSmsEnabled() || isEmailEnabled()) {
            ApplicationStakeHolder applnStakeHolder = occupancyCertificate.getParent().getStakeHolder().get(0);
            if (applnStakeHolder.getApplication() != null && applnStakeHolder.getApplication().getOwner() != null) {
                applicantName = applnStakeHolder.getApplication().getOwner().getName();
                email = applnStakeHolder.getApplication().getOwner().getEmailId();
                mobileNo = applnStakeHolder.getApplication().getOwner().getUser().getMobileNumber();
                loginUserName = applnStakeHolder.getApplication().getOwner().getUser().getUsername();
                password = mobileNo;
                buildSmsAndEmailForOCAppln(occupancyCertificate, applicantName, mobileNo, email, loginUserName, password,
                        reportOutput, fileName);
            }
            if (applnStakeHolder.getStakeHolder() != null && applnStakeHolder.getStakeHolder().getIsActive()) {
                applicantName = applnStakeHolder.getStakeHolder().getName();
                email = applnStakeHolder.getStakeHolder().getEmailId();
                mobileNo = applnStakeHolder.getStakeHolder().getMobileNumber();
                loginUserName = applnStakeHolder.getStakeHolder().getUsername();
                password = EMPTY;
                buildSmsAndEmailForOCAppln(occupancyCertificate, applicantName, mobileNo, email, loginUserName, password,
                        reportOutput, fileName);
            }
        }
    }

    // AFTER COLLECTION OF PERMIT Fee Collection
    public void sendSmsForCollection(BigDecimal totalAmount, OccupancyCertificate occupancyCertificate,
            BillReceiptInfo billRcptInfo) {
        if (isSmsEnabled()) {
            String msg = buildSmsMsgDetailsForCollection(totalAmount, occupancyCertificate, billRcptInfo);
            ApplicationStakeHolder applnStakeHolder = occupancyCertificate.getParent().getStakeHolder().get(0);
            if (applnStakeHolder.getApplication() != null && applnStakeHolder.getApplication().getOwner() != null) {
                Applicant owner = applnStakeHolder.getApplication().getOwner();
                if (isNotBlank(owner.getUser().getMobileNumber()) && isNotBlank(msg)) {
                    notificationService.sendSMS(owner.getUser().getMobileNumber(), msg);
                }
            }
            if (applnStakeHolder.getStakeHolder() != null && applnStakeHolder.getStakeHolder().getIsActive()) {
                StakeHolder stakeHolder = applnStakeHolder.getStakeHolder();
                if (isNotBlank(stakeHolder.getMobileNumber()) && isNotBlank(msg)) {
                    notificationService.sendSMS(stakeHolder.getMobileNumber(), msg);
                }
                if (isEmailEnabled() && isNotBlank(stakeHolder.getEmailId()))
                    notificationService.sendEmail(stakeHolder.getEmailId(),
                            bpaMessageSource.getMessage("msg.oc.email.subject.collection",
                                    new String[] { billRcptInfo.getServiceName() }, null),
                            buildEmailBodyForCollection(totalAmount, occupancyCertificate, billRcptInfo));
            }
        }
    }

    private String buildSmsMsgDetailsForCollection(BigDecimal totalAmount, OccupancyCertificate occupancyCertificate,
            BillReceiptInfo billRcptInfo) {
        String msg = bpaMessageSource.getMessage(SMS_KEY_CLCTN,
                new String[] { totalAmount.toString(), billRcptInfo.getServiceName(),
                        occupancyCertificate.getApplicationNumber(), billRcptInfo.getReceiptDate().toString() },
                null);
        return msg;
    }

    private String buildEmailBodyForCollection(BigDecimal totalAmount, OccupancyCertificate occupancyCertificate,
            BillReceiptInfo billRcptInfo) {
        String msg = bpaMessageSource.getMessage("msg.oc.email.body.permit.fee.collection",
                new String[] { getMunicipalityName(), totalAmount.toString(),
                        billRcptInfo.getServiceName(), occupancyCertificate.getApplicationNumber(),
                        billRcptInfo.getReceiptDate().toString(), getMunicipalityName() },
                null);
        return msg;
    }

    // FOR LETTER TO PARTY
    public void sendSMSAndEmailToApplicantForLettertoparty(final OccupancyCertificate occupancyCertificate) {
        if (isSmsEnabled() || isEmailEnabled()) {
            ApplicationStakeHolder applnStakeHolder = occupancyCertificate.getParent().getStakeHolder().get(0);
            if (applnStakeHolder.getApplication() != null && applnStakeHolder.getApplication().getOwner() != null) {
                Applicant owner = applnStakeHolder.getApplication().getOwner();
                buildSmsAndEmailForOCAppln(occupancyCertificate, owner.getName(),
                        owner.getUser().getMobileNumber(), owner.getEmailId(), EMPTY, EMPTY, null, null);
            }
            if (applnStakeHolder.getStakeHolder() != null && applnStakeHolder.getStakeHolder().getIsActive()) {
                StakeHolder stakeHolder = applnStakeHolder.getStakeHolder();
                buildSmsAndEmailForOCAppln(occupancyCertificate, stakeHolder.getName(),
                        stakeHolder.getMobileNumber(), stakeHolder.getEmailId(), EMPTY, EMPTY, null, null);
            }
        }
    }

    private void buildSmsAndEmailForOCAppln(final OccupancyCertificate occupancyCertificate, final String applicantName,
            final String mobileNo, final String email, final String loginUserName, final String password,
            ReportOutput reportOutput, String fileName) {

        String smsMsg = EMPTY;
        String body = EMPTY;
        String subject = EMPTY;
        String smsCode;
        String mailCode;
        if ((APPLICATION_STATUS_REGISTERED).equalsIgnoreCase(occupancyCertificate.getStatus().getCode())) {
            if (isNotBlank(password)) {
                smsCode = MSG_KEY_SMS_OC_APPLN_NEW_CZN;
                mailCode = BODY_KEY_EMAIL_OC_APPLN_NEW_CZN;
            } else {
                smsCode = MSG_KEY_SMS_OC_APPLN_NEW;
                mailCode = BODY_KEY_EMAIL_OC_APPLN_NEW;
            }
            smsMsg = smsBodyByCodeAndArgsWithType(smsCode, applicantName, occupancyCertificate,
                    SMSEMAILTYPENEWBPAREGISTERED, loginUserName, password);
            body = emailBodyByCodeAndArgsWithType(mailCode, applicantName,
                    occupancyCertificate, SMSEMAILTYPENEWBPAREGISTERED, loginUserName, password);
            subject = emailSubjectforEmailByCodeAndArgs(SUBJECT_KEY_EMAIL_OC_APPLN_NEW,
                    occupancyCertificate.getApplicationNumber());

        } else if (CREATEDLETTERTOPARTY.equalsIgnoreCase(occupancyCertificate.getStatus().getCode())) {
            smsMsg = smsBodyByCodeAndArgsWithType(MSG_KEY_SMS_LETTERTOPARTY, applicantName,
                    occupancyCertificate, SMSEMAILTYPELETTERTOPARTY, EMPTY, EMPTY);
            body = emailBodyByCodeAndArgsWithType(BODY_KEY_EMAIL_LETTERTOPARTY, applicantName,
                    occupancyCertificate, SMSEMAILTYPELETTERTOPARTY, EMPTY, EMPTY);
            subject = emailSubjectforEmailByCodeAndArgs(SUBJECT_KEY_EMAIL_LETTERTOPARTY,
                    occupancyCertificate.getApplicationNumber());
        } else if (APPLICATION_STATUS_CANCELLED.equalsIgnoreCase(occupancyCertificate.getStatus().getCode())) {
            smsMsg = smsBodyByCodeAndArgsWithType(MSG_KEY_SMS_CANCELL_APPLN, applicantName,
                    occupancyCertificate, APPLICATION_STATUS_CANCELLED, EMPTY, EMPTY);
            body = emailBodyByCodeAndArgsWithType(BODY_KEY_EMAIL_CANCELL_APPLN, applicantName,
                    occupancyCertificate, APPLICATION_STATUS_CANCELLED, EMPTY, EMPTY);
            subject = emailSubjectforEmailByCodeAndArgs(SUBJECT_KEY_EMAIL_CANCELL_APPLN,
                    occupancyCertificate.getApplicationNumber());
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

    private String smsBodyByCodeAndArgsWithType(String code, String applicantName, OccupancyCertificate occupancyCertificate,
            String type, String loginUserName, String password) {
        String smsMsg = EMPTY;
        if (SMSEMAILTYPENEWBPAREGISTERED.equalsIgnoreCase(type)) {
            if (isNotBlank(password))
                smsMsg = bpaMessageSource.getMessage(code,
                        new String[] { applicantName, occupancyCertificate.getApplicationNumber(), loginUserName, password,
                                getMunicipalityName() },
                        null);
            else
                smsMsg = bpaMessageSource.getMessage(code,
                        new String[] { applicantName, occupancyCertificate.getApplicationNumber(), loginUserName,
                                getMunicipalityName() },
                        null);
        } else if (SMSEMAILTYPELETTERTOPARTY.equalsIgnoreCase(type))
            smsMsg = bpaMessageSource.getMessage(code,
                    new String[] { applicantName, occupancyCertificate.getApplicationNumber(), getMunicipalityName() }, null);
        else if (APPLICATION_STATUS_CANCELLED.equalsIgnoreCase(type)) {

            smsMsg = getCancelApplnMessage(code, applicantName, occupancyCertificate);
        }
        return smsMsg;
    }

    private String getCancelApplnMessage(String code, String applicantName, OccupancyCertificate oc) {
        StateHistory<Position> stateHistory = bpaUtils.getRejectionComments(oc.getStateHistory());
        return bpaMessageSource.getMessage(code,
                new String[] { applicantName, oc.getApplicationNumber(),
                        stateHistory != null && isNotBlank(stateHistory.getComments()) ? stateHistory.getComments() : EMPTY,
                        getMunicipalityName() },
                null);
    }

    private String emailBodyByCodeAndArgsWithType(String code, String applicantName, OccupancyCertificate occupancyCertificate,
            String type, String loginUserName, String password) {
        String body = EMPTY;
        if (SMSEMAILTYPENEWBPAREGISTERED.equalsIgnoreCase(type)) {
            if (password != EMPTY)
                body = bpaMessageSource.getMessage(code,
                        new String[] { applicantName, occupancyCertificate.getApplicationNumber(), loginUserName, password,
                                getMunicipalityName() },
                        null);
            else
                body = bpaMessageSource.getMessage(code,
                        new String[] { applicantName, occupancyCertificate.getApplicationNumber(), loginUserName,
                                getMunicipalityName() },
                        null);
        } else if (SMSEMAILTYPELETTERTOPARTY.equalsIgnoreCase(type))
            body = bpaMessageSource.getMessage(code,
                    new String[] { applicantName, occupancyCertificate.getApplicationNumber(), getMunicipalityName() }, null);
        else if (APPLICATION_STATUS_CANCELLED.equalsIgnoreCase(type)) {
            body = getCancelApplnMessage(code, applicantName, occupancyCertificate);
        }
        return body;
    }

    // FOR ONCE APPROVED
    public void sendSmsAndEmailOnApproval(OccupancyCertificate oc, ReportOutput reportOutput) {
        String mobileNumber = oc.getParent().getOwner().getUser().getMobileNumber();
        String emailId = oc.getParent().getOwner().getEmailId();
        String name = oc.getParent().getOwner().getName();
        String smsMsg = buildMsgDetailsOnApplicationApproval(oc, name, SMS_KEY_APRVL);
        String body = buildMsgDetailsOnApplicationApproval(oc, name, EMLB_KEY_APRVL);
        String subject = buildMsgDetailsForEmailSubjectOnApplicationApproval(oc, EMLS_KEY_APRVL);
        if (isNotBlank(mobileNumber) && isNotBlank(smsMsg)) {
            notificationService.sendSMS(mobileNumber, smsMsg);
        }
        if (isNotBlank(emailId) && isNotBlank(body)) {
            notificationService.sendEmailWithAttachment(emailId, subject, body, APP_PDF, OCDEMANDFILENAME + PDFEXTN,
                    reportOutput.getReportOutputData());
        }
    }

    private String buildMsgDetailsForEmailSubjectOnApplicationApproval(OccupancyCertificate occupancyCertificate, String msgKey) {
        return bpaMessageSource.getMessage(msgKey, new String[] { occupancyCertificate.getApplicationNumber() }, null);
    }

    private String buildMsgDetailsOnApplicationApproval(OccupancyCertificate occupancyCertificate, String name, String msgKey) {
        BigDecimal amtToRemit = occupancyCertificate.getDemand().getBaseDemand()
                .subtract(occupancyCertificate.getDemand().getAmtCollected());
        String msg = bpaMessageSource.getMessage(msgKey,
                new String[] { name, occupancyCertificate.getApplicationNumber(), amtToRemit.toString(), getMunicipalityName() },
                null);
        return msg;
    }

    //// OC Permit GENERATION
    public void sendSmsAndEmailOnPermitOrderGeneration(OccupancyCertificate occupancyCertificate, ReportOutput reportOutput) {

        if (isSmsEnabled() || isEmailEnabled()) {
            ApplicationStakeHolder applnStakeHolder = occupancyCertificate.getParent().getStakeHolder().get(0);
            if (applnStakeHolder.getApplication() != null && applnStakeHolder.getApplication().getOwner() != null) {
                Applicant owner = applnStakeHolder.getApplication().getOwner();
                buildSmsAndEmailOnPermitOrderGeneration(occupancyCertificate, owner.getName(), owner.getUser().getMobileNumber(),
                        owner.getEmailId(), reportOutput);
            }
            if (applnStakeHolder.getStakeHolder() != null && applnStakeHolder.getStakeHolder().getIsActive()) {
                StakeHolder stakeHolder = applnStakeHolder.getStakeHolder();
                buildSmsAndEmailOnPermitOrderGeneration(occupancyCertificate, stakeHolder.getName(),
                        stakeHolder.getMobileNumber(),
                        stakeHolder.getEmailId(), reportOutput);
            }
        }
    }

    private void buildSmsAndEmailOnPermitOrderGeneration(OccupancyCertificate occupancyCertificate, String name,
            String mobileNumber,
            String emailId, ReportOutput reportOutput) {
        String smsMsg = buildMsgDetailsOnPermitOrderGeneration(occupancyCertificate, name, SMS_KEY_PO);
        String body = buildMsgDetailsOnPermitOrderGeneration(occupancyCertificate, name, EMLB_KEY_PO);
        String subject = buildEmailSubjectOnPermitOrderGeneration(occupancyCertificate, EMLS_KEY_PO);
        if (isNotBlank(mobileNumber) && isNotBlank(smsMsg)) {
            notificationService.sendSMS(mobileNumber, smsMsg);
        }
        if (isNotBlank(emailId) && isNotBlank(body)) {
            notificationService.sendEmailWithAttachment(emailId, subject, body, APP_PDF,
                    "occupancycertificate" + "order" + PDFEXTN, reportOutput.getReportOutputData());
        }
    }

    private String buildEmailSubjectOnPermitOrderGeneration(OccupancyCertificate occupancyCertificate, String msgKey) {
        return bpaMessageSource.getMessage(msgKey, new String[] { occupancyCertificate.getApplicationNumber() }, null);
    }

    private String buildMsgDetailsOnPermitOrderGeneration(OccupancyCertificate occupancyCertificate, String name, String msgKey) {
        String msg = bpaMessageSource.getMessage(msgKey,
                new String[] { name, occupancyCertificate.getApplicationNumber(), getMunicipalityName() }, null);
        return msg;
    }

    // FOR FIELD INSPECTION
    private void buildSmsAndEmailForScheduleAppointment(final OCAppointmentSchedule scheduleDetails,
            final OccupancyCertificate occupancyCertificate, final String applicantName, final String mobileNo,
            final String email) {

        String smsMsg = EMPTY;
        String body = EMPTY;
        String subject = EMPTY;
        if (AppointmentSchedulePurpose.INSPECTION.equals(scheduleDetails.getAppointmentScheduleCommon().getPurpose())) {
            if (!scheduleDetails.getAppointmentScheduleCommon().isPostponed()) {
                smsMsg = buildMessageDetailsForScheduleAppointment(scheduleDetails, occupancyCertificate, applicantName,
                        MSG_KEY_SMS_OC_FIELD_INS);
                body = buildMessageDetailsForScheduleAppointment(scheduleDetails, occupancyCertificate, applicantName,
                        BODY_KEY_EMAIL_OC_FIELD_INS);
                subject = emailSubjectforEmailForScheduleAppointmentForInspection(scheduleDetails, occupancyCertificate,
                        SUBJECT_KEY_EMAIL_OC_FIELD_INS);
            } else {
                smsMsg = buildMessageDetailsForScheduleAppointment(scheduleDetails, occupancyCertificate, applicantName,
                        MSG_KEY_SMS_OC_FIELD_INS_RESCHE);
                body = buildMessageDetailsForScheduleAppointment(scheduleDetails, occupancyCertificate, applicantName,
                        BODY_KEY_EMAIL_OC_FIELD_INS_RESCHE);
                subject = emailSubjectforEmailForScheduleAppointmentForInspection(scheduleDetails, occupancyCertificate,
                        SUBJECT_KEY_EMAIL_OC_FIELD_INS_RESCHE);
            }
        }
        if (isNotBlank(mobileNo) && isNotBlank(smsMsg))
            notificationService.sendSMS(mobileNo, smsMsg);
        if (isNotBlank(email) && isNotBlank(body))
            notificationService.sendEmail(email, subject, body);
    }

    private String buildMessageDetailsForScheduleAppointment(final OCAppointmentSchedule scheduleDetails,
            final OccupancyCertificate occupancyCertificate, String applicantName, String msgKey) {

        String mesg = EMPTY;
        if (AppointmentSchedulePurpose.INSPECTION.equals(scheduleDetails.getAppointmentScheduleCommon().getPurpose())) {

            if (!scheduleDetails.getAppointmentScheduleCommon().isPostponed()) {
                mesg = bpaMessageSource.getMessage(msgKey,
                        new String[] { applicantName,
                                DateUtils
                                        .toDefaultDateFormat(scheduleDetails.getAppointmentScheduleCommon().getAppointmentDate()),
                                scheduleDetails.getAppointmentScheduleCommon().getAppointmentTime(),
                                occupancyCertificate.getApplicationNumber(), getMunicipalityName() },
                        null);
            } else {
                mesg = bpaMessageSource.getMessage(msgKey,
                        new String[] { applicantName,
                                DateUtils
                                        .toDefaultDateFormat(scheduleDetails.getAppointmentScheduleCommon().getAppointmentDate()),
                                scheduleDetails.getAppointmentScheduleCommon().getAppointmentTime(),
                                occupancyCertificate.getApplicationNumber(),
                                scheduleDetails.getAppointmentScheduleCommon().getPostponementReason(), getMunicipalityName() },
                        null);
            }
        }
        return mesg;
    }

    private String emailSubjectforEmailForScheduleAppointmentForInspection(final OCAppointmentSchedule scheduleDetails,
            final OccupancyCertificate occupancyCertificate, String msgKey) {
        final Locale locale = LocaleContextHolder.getLocale();
        return bpaMessageSource.getMessage(msgKey,
                new String[] { DateUtils.toDefaultDateFormat(scheduleDetails.getAppointmentScheduleCommon().getAppointmentDate()),
                        scheduleDetails.getAppointmentScheduleCommon().getAppointmentTime(),
                        occupancyCertificate.getApplicationNumber() },
                locale);
    }

    public void sendSMSAndEmailToscheduleAppointment(final OCAppointmentSchedule scheduleDetails,
            final OccupancyCertificate occupancyCertificate) {
        if (isSmsEnabled() || isEmailEnabled()) {
            ApplicationStakeHolder applnStakeHolder = occupancyCertificate.getParent().getStakeHolder().get(0);
            if (applnStakeHolder.getApplication() != null && applnStakeHolder.getApplication().getOwner() != null) {
                Applicant owner = applnStakeHolder.getApplication().getOwner();
                buildSmsAndEmailForScheduleAppointment(scheduleDetails, occupancyCertificate, owner.getName(),
                        owner.getUser().getMobileNumber(), owner.getEmailId());
            }
            if (applnStakeHolder.getStakeHolder() != null && applnStakeHolder.getStakeHolder().getIsActive()) {
                StakeHolder stakeHolder = applnStakeHolder.getStakeHolder();
                buildSmsAndEmailForScheduleAppointment(scheduleDetails, occupancyCertificate, stakeHolder.getName(),
                        stakeHolder.getMobileNumber(), stakeHolder.getEmailId());
            }

        }
    }

}
