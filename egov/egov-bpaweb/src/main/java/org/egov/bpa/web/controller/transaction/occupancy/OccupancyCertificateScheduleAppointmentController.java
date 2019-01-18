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
package org.egov.bpa.web.controller.transaction.occupancy;

import org.apache.commons.io.IOUtils;
import org.egov.bpa.master.service.AppointmentLocationsService;
import org.egov.bpa.transaction.entity.SlotDetail;
import org.egov.bpa.transaction.entity.WorkflowBean;
import org.egov.bpa.transaction.entity.common.AppointmentScheduleCommon;
import org.egov.bpa.transaction.entity.dto.ScheduleScrutiny;
import org.egov.bpa.transaction.entity.enums.AppointmentSchedulePurpose;
import org.egov.bpa.transaction.entity.oc.OCAppointmentSchedule;
import org.egov.bpa.transaction.entity.oc.OCSlot;
import org.egov.bpa.transaction.entity.oc.OccupancyCertificate;
import org.egov.bpa.transaction.service.common.AppointmentScheduleCommonService;
import org.egov.bpa.transaction.service.oc.OCAppointmentScheduleService;
import org.egov.bpa.transaction.service.oc.OccupancyCertificateService;
import org.egov.bpa.transaction.service.oc.RescheduleAppointmentForOcService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.bpa.web.controller.transaction.BpaGenericApplicationController;
import org.egov.commons.entity.Source;
import org.egov.infra.persistence.entity.enums.UserType;
import org.egov.infra.utils.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_DOC_VERIFIED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_SCHEDULED;

@Controller
@RequestMapping(value = "/application/occupancy-certificate")
public class OccupancyCertificateScheduleAppointmentController extends BpaGenericApplicationController {

    private static final String OCCUPANCY_CERTIFICATE = "occupancyCertificate";

    private static final String MSG = "message";

    private static final String APPOINTMENT_LOCATIONS_LIST = "appointmentLocationsList";

    private static final String APPOINTMENT_SCHEDULED_LIST = "appointmentScheduledList";

    private static final String SCHEDULE_APPIONTMENT_RESULT = "oc-scheduled-appointment-result";

    private static final String MESSAGE = MSG;

    private static final String APPLICATION_NUMBER = "applicationNumber";

    private static final String REDIRECT_APPLICATION_VIEW_APPOINTMENT = "redirect:/application/occupancy-certificate/appointment/view-details/";

    private static final String OC_RESCHEDULE_APPOINTMENT = "oc-reschedule-appointment";

    private static final String OC_APPOINTMENT_SCHEDULE = "ocAppointmentSchedule";

    private static final String OC_SCHEDULE_APPOINTMENT_NEW = "oc-schedule-appointment-new";

    public static final String SCHEDULED_SCRUTINY_DETAILS_RESULT = "view-scheduled-scrutiny-details-result-oc";

    public static final String CITIZEN_SUCEESS = "citizen_suceess";

    private static final String RESCHEDULE_DOC_SCRUTINY = "reschedule-document-scrutiny-oc";

    public static final String COMMON_ERROR = "common-error";

    @Autowired
    private OCAppointmentScheduleService ocAppointmentScheduleService;
    @Autowired
    private AppointmentLocationsService appointmentLocationsService;
    @Autowired
    private RescheduleAppointmentForOcService rescheduleAppointmentForOcService;
    @Autowired
    private OccupancyCertificateService occupancyCertificateService;
    @Autowired
    private AppointmentScheduleCommonService appointmentScheduleCommonService;

    @GetMapping("/schedule-appointment/{applicationNumber}")
    public String showScheduleAppointmentForOC(@PathVariable final String applicationNumber, final Model model) {
        OccupancyCertificate oc = occupancyCertificateService.findByApplicationNumber(applicationNumber);
        OCAppointmentSchedule ocAppointmentSchedule = new OCAppointmentSchedule();
        AppointmentScheduleCommon appointmentSchedule = new AppointmentScheduleCommon();
        // It require if want to schedule appointment for document scrutiny manually from UI instead through scheduler
        /*if (BpaConstants.APPLICATION_STATUS_REGISTERED.equalsIgnoreCase(oc.getStatus().getCode())
            && !BpaConstants.FWD_TO_OVRSR_FOR_FIELD_INS.equalsIgnoreCase(oc.getCurrentState().getNextAction())) {
            appointmentSchedule.setPurpose(AppointmentSchedulePurpose.DOCUMENTSCRUTINY);
        } else*/
        if ((APPLICATION_STATUS_DOC_VERIFIED.equalsIgnoreCase(oc.getStatus().getCode())
                    || BpaConstants.APPLICATION_STATUS_REGISTERED.equalsIgnoreCase(oc.getStatus().getCode()))
                   && BpaConstants.FWD_TO_OVRSR_FOR_FIELD_INS.equalsIgnoreCase(oc.getCurrentState().getNextAction())) {
            appointmentSchedule.setPurpose(AppointmentSchedulePurpose.INSPECTION);
        }
        ocAppointmentSchedule.setAppointmentScheduleCommon(appointmentSchedule);
        ocAppointmentSchedule.setOc(oc);
        model.addAttribute(APPOINTMENT_LOCATIONS_LIST, appointmentLocationsService.findAllOrderByOrderNumber());
        model.addAttribute(OC_APPOINTMENT_SCHEDULE, ocAppointmentSchedule);
        model.addAttribute(APPLICATION_NUMBER, applicationNumber);
        return OC_SCHEDULE_APPOINTMENT_NEW;
    }

    @PostMapping("/schedule-appointment/{applicationNumber}")
    public String scheduleAppointmentForOC(@Valid @ModelAttribute final OCAppointmentSchedule ocAppointmentSchedule,
            @PathVariable final String applicationNumber, final Model model, final RedirectAttributes redirectAttributes) {
        OCAppointmentSchedule schedule = buildAndSaveNewAppointment(ocAppointmentSchedule, applicationNumber);
        if (AppointmentSchedulePurpose.DOCUMENTSCRUTINY.equals(ocAppointmentSchedule.getAppointmentScheduleCommon().getPurpose())) {
            redirectAttributes.addFlashAttribute(MESSAGE,
                    messageSource.getMessage("msg.new.appoimt", null, null));
        } else if (AppointmentSchedulePurpose.INSPECTION.equals(ocAppointmentSchedule.getAppointmentScheduleCommon().getPurpose())) {
            redirectAttributes.addFlashAttribute(MESSAGE,
                    messageSource.getMessage("msg.new.appoimt.fieldins", null, null));
        }
        return REDIRECT_APPLICATION_VIEW_APPOINTMENT + schedule.getId();
    }

    private OCAppointmentSchedule buildAndSaveNewAppointment(final OCAppointmentSchedule ocAppointmentSchedule, final String applicationNumber) {
        OCAppointmentSchedule schedule = ocAppointmentScheduleService.save(ocAppointmentSchedule);
        //bpaSmsAndEmailService.sendSMSAndEmailToscheduleAppointment(schedule, bpaApplication);
        return schedule;
    }

    @GetMapping("/reschedule-appointment/{scheduleType}/{applicationNumber}")
    public String showReScheduleAppointmentForOc(@PathVariable final AppointmentSchedulePurpose scheduleType,
            @PathVariable final String applicationNumber, final Model model) {
        OccupancyCertificate oc = occupancyCertificateService.findByApplicationNumber(applicationNumber);
        List<OCAppointmentSchedule> appointmentScheduledList = ocAppointmentScheduleService.findByApplication(oc, scheduleType);
        OCAppointmentSchedule appointmentSchedule = new OCAppointmentSchedule();
        AppointmentScheduleCommon appointmentScheduleCommon = new AppointmentScheduleCommon();
        appointmentScheduleCommon.setPurpose(appointmentScheduledList.get(0).getAppointmentScheduleCommon().getPurpose());
        appointmentSchedule.setAppointmentScheduleCommon(appointmentScheduleCommon);
        model.addAttribute(APPOINTMENT_LOCATIONS_LIST, appointmentLocationsService.findAllOrderByOrderNumber());
        model.addAttribute(OC_APPOINTMENT_SCHEDULE, appointmentSchedule);
        model.addAttribute(APPLICATION_NUMBER, applicationNumber);
        model.addAttribute(APPOINTMENT_SCHEDULED_LIST, appointmentScheduledList);
        model.addAttribute("mode", "postponeappointment");
        return OC_RESCHEDULE_APPOINTMENT;
    }

    @PostMapping("/reschedule-appointment/{scheduleType}/{applicationNumber}")
    public String reScheduleAppointmentForOC(@Valid @ModelAttribute final OCAppointmentSchedule ocAppointmentSchedule,
            @PathVariable final AppointmentSchedulePurpose scheduleType, @PathVariable final String applicationNumber,
            @RequestParam Long parentAppointmentScheduleId, final RedirectAttributes redirectAttributes) {
        OccupancyCertificate oc = occupancyCertificateService.findByApplicationNumber(applicationNumber);
        AppointmentScheduleCommon parent = appointmentScheduleCommonService.findById(parentAppointmentScheduleId);
        ocAppointmentSchedule.getAppointmentScheduleCommon().setPostponed(true);
        ocAppointmentSchedule.getAppointmentScheduleCommon().setParent(parent);
        ocAppointmentSchedule.setOc(oc);
        OCAppointmentSchedule schedule = ocAppointmentScheduleService.save(ocAppointmentSchedule);
        //bpaSmsAndEmailService.sendSMSAndEmailToscheduleAppointment(schedule, bpaApplication);
        if (AppointmentSchedulePurpose.DOCUMENTSCRUTINY.equals(schedule.getAppointmentScheduleCommon().getPurpose())) {
            redirectAttributes.addFlashAttribute(MESSAGE,
                    messageSource.getMessage("msg.rescheduled.appoimt", null, null));
        } else if (AppointmentSchedulePurpose.INSPECTION.equals(schedule.getAppointmentScheduleCommon().getPurpose())) {
            redirectAttributes.addFlashAttribute(MESSAGE,
                    messageSource.getMessage("msg.update.appoimt.fieldins", null, null));
        }
        return REDIRECT_APPLICATION_VIEW_APPOINTMENT + schedule.getId();
    }

    @GetMapping("/scrutiny/schedule/{applicationNumber}")
    public String showScheduleAppointmentForDocScrutinyOfOC(@PathVariable final String applicationNumber, final Model model) {
        OCAppointmentSchedule ocAppointmentSchedule = new OCAppointmentSchedule();
        AppointmentScheduleCommon appointmentScheduleCommon = new AppointmentScheduleCommon();
        appointmentScheduleCommon.setPurpose(AppointmentSchedulePurpose.DOCUMENTSCRUTINY);
        ocAppointmentSchedule.setAppointmentScheduleCommon(appointmentScheduleCommon);
        model.addAttribute(APPOINTMENT_LOCATIONS_LIST, appointmentLocationsService.findAllOrderByOrderNumber());
        model.addAttribute(OC_APPOINTMENT_SCHEDULE, ocAppointmentSchedule);
        model.addAttribute(APPLICATION_NUMBER, applicationNumber);
        return OC_SCHEDULE_APPOINTMENT_NEW;
    }

    @PostMapping("/scrutiny/schedule/{applicationNumber}")
    public String scheduleAppointmentForDocScrutinyOfOC(@Valid @ModelAttribute final OCAppointmentSchedule ocAppointmentSchedule,
                                                    @PathVariable final String applicationNumber, final Model model, final RedirectAttributes redirectAttributes) {
        OccupancyCertificate oc = occupancyCertificateService.findByApplicationNumber(applicationNumber);
        ocAppointmentSchedule.setOc(oc);
        WorkflowBean wfBean = new WorkflowBean();
        wfBean.setApproverPositionId(oc.getCurrentState().getOwnerPosition().getId());
        wfBean.setApproverComments(BpaConstants.APPLICATION_STATUS_SCHEDULED);
        wfBean.setWorkFlowAction("Forward");
        bpaUtils.redirectToBpaWorkFlowForOC(oc, wfBean);
        OCAppointmentSchedule schedule = ocAppointmentScheduleService.save(ocAppointmentSchedule);
        //bpaSmsAndEmailService.sendSMSAndEmailToscheduleAppointment(schedule, bpaApplication);
        redirectAttributes.addFlashAttribute(MESSAGE,
                messageSource.getMessage("msg.new.appoimt", null, null));

        return REDIRECT_APPLICATION_VIEW_APPOINTMENT + schedule.getId();
    }
   
    @GetMapping("/scrutiny/reschedule/{applicationNumber}")
    public String showReScheduleDcoumentScrutinyOfOC(@PathVariable final String applicationNumber, final Model model) {
        OccupancyCertificate oc = occupancyCertificateService.findByApplicationNumber(applicationNumber);
        if (oc.getRescheduledByEmployee() && UserType.EMPLOYEE.equals(securityUtils.getCurrentUser().getType())) {
            model.addAttribute(MESSAGE,
            		messageSource.getMessage("msg.emp.reschedule.appintment.onlyonce", null, null));
            return COMMON_ERROR;
        }
        if (validateOnDocumentScrutiny(model, oc.getStatus())){
            return COMMON_ERROR;
        }
        List<SlotDetail> slotDetails = rescheduleAppointmentForOcService
                .searchAvailableSlotsForOcReschedule(oc.getId());
        Set<Date> appointmentDates = new LinkedHashSet<>();
        Optional<OCSlot> actvSltApp = oc.getOcSlots().stream()
                .reduce((ocSlot1, ocSlot2) -> ocSlot2);
        for (SlotDetail slotDetail : slotDetails) {
            if (actvSltApp.isPresent()
                    && slotDetail.getSlot().getAppointmentDate()
                            .after(actvSltApp.get().getSlotDetail().getSlot().getAppointmentDate()))
                appointmentDates.add(slotDetail.getSlot().getAppointmentDate());
        }
        if (slotDetails.isEmpty() || appointmentDates.isEmpty()) {
            model.addAttribute("slotsNotAvailableMsg", messageSource.getMessage("msg.slot.not.available", null, null));
        }
        ScheduleScrutiny scheduleScrutiny = new ScheduleScrutiny();
        scheduleScrutiny.setApplicationId(oc.getId());
        if (bpaUtils.logedInuseCitizenOrBusinessUser()) {
            scheduleScrutiny.setReScheduledBy(Source.CITIZENPORTAL.name());
        } else {
            scheduleScrutiny.setReScheduledBy(Source.SYSTEM.name());
        }
        model.addAttribute("appointmentDates", appointmentDates);
        model.addAttribute(OCCUPANCY_CERTIFICATE, oc);
        model.addAttribute("scheduleScrutiny", scheduleScrutiny);
        model.addAttribute("slotDetailList", slotDetails);
        return RESCHEDULE_DOC_SCRUTINY;
    }

    @PostMapping("/scrutiny/reschedule/{applicationNumber}")
    public String reScheduleDocumentScrutinyOfOC(@Valid @ModelAttribute final ScheduleScrutiny scheduleScrutiny, @PathVariable final String applicationNumber, final Model model, final HttpServletRequest request, final RedirectAttributes redirectAttributes) {
        String reScheduleAction = request.getParameter("reScheduleAction");
        OccupancyCertificate occupancyCertificate = occupancyCertificateService.findByApplicationNumber(applicationNumber);
        if ("Re-Schedule".equals(reScheduleAction)) {
            if (APPLICATION_STATUS_SCHEDULED.equals(occupancyCertificate.getStatus().getCode())) {
                WorkflowBean workflowBean = new WorkflowBean();
                workflowBean.setApproverPositionId(occupancyCertificate.getCurrentState().getOwnerPosition().getId());
                workflowBean.setCurrentState(null);
                workflowBean.setApproverComments("document scrutiny re-scheduled");
                workflowBean.setWorkFlowAction(BpaConstants.WF_RESCHDLE_APPMNT_BUTTON);
                workflowBean.setAmountRule(null);
                bpaUtils.redirectToBpaWorkFlowForOC(occupancyCertificate,workflowBean);
            } if (Source.SYSTEM.name().equals(scheduleScrutiny.getReScheduledBy())) {
                rescheduleAppointmentForOcService.rescheduleAppointmentsForOcByEmployee(scheduleScrutiny.getApplicationId(), scheduleScrutiny.getAppointmentDate(), scheduleScrutiny.getAppointmentTime());
            } else if (Source.CITIZENPORTAL.name().equals(scheduleScrutiny.getReScheduledBy())) {
                rescheduleAppointmentForOcService.rescheduleAppointmentsForOcByCitizen(scheduleScrutiny.getApplicationId(), scheduleScrutiny.getAppointmentDate(), scheduleScrutiny.getAppointmentTime());
            }
            model.addAttribute(OCCUPANCY_CERTIFICATE, occupancyCertificate);
        } else if ("Auto Re-Schedule".equals(reScheduleAction)) {
            WorkflowBean workflowBean = new WorkflowBean();
            workflowBean.setApproverPositionId(occupancyCertificate.getCurrentState().getOwnerPosition().getId());
            workflowBean.setCurrentState(null);
            workflowBean.setApproverComments(BpaConstants.APPLICATION_STATUS_PENDING_FOR_RESCHEDULING);
            workflowBean.setWorkFlowAction(BpaConstants.WF_AUTO_RESCHDLE_APPMNT_BUTTON);
            workflowBean.setAmountRule(null);
            bpaUtils.redirectToBpaWorkFlowForOC(occupancyCertificate,workflowBean);
            String scheduleBy = StringUtils.EMPTY;
            if (Source.SYSTEM.name().equals(scheduleScrutiny.getReScheduledBy())) {
                scheduleBy = "EMPLOYEE";
            } else if (Source.CITIZENPORTAL.name().equals(scheduleScrutiny.getReScheduledBy())) {
                scheduleBy = "CITIZENPORTAL";
            }
            rescheduleAppointmentForOcService.rescheduleAppointmentWhenSlotsNotAvailableForOc(occupancyCertificate.getId(), scheduleBy);
            model.addAttribute(MSG, messageSource.getMessage("msg.auto.schedule", new String[]{occupancyCertificate.getApplicationNumber()}, null));
            return CITIZEN_SUCEESS;
        } else if ("Cancel Application".equals(reScheduleAction)) {
            WorkflowBean workflowBean = new WorkflowBean();
            workflowBean.setApproverPositionId(occupancyCertificate.getCurrentState().getOwnerPosition().getId());
            workflowBean.setCurrentState(null);
            workflowBean.setApproverComments("Application cancelled by citizen");
            workflowBean.setWorkFlowAction(BpaConstants.WF_CANCELAPPLICATION_BUTTON);
            workflowBean.setAmountRule(null);
            bpaUtils.redirectToBpaWorkFlowForOC(occupancyCertificate,workflowBean);
            model.addAttribute(MSG, messageSource.getMessage("msg.cancel.appln", new String[]{occupancyCertificate.getApplicationNumber()}, null));
            return CITIZEN_SUCEESS;
        }
        return SCHEDULED_SCRUTINY_DETAILS_RESULT;
    }

    @GetMapping("/scrutiny/view/{applicationNumber}")
    public String viewDocumentScrutinyDetailsForOC(@PathVariable final String applicationNumber, final Model model) {
        OccupancyCertificate oc = occupancyCertificateService.findByApplicationNumber(applicationNumber);
        model.addAttribute(OCCUPANCY_CERTIFICATE, oc);
        return SCHEDULED_SCRUTINY_DETAILS_RESULT;
    }

    @GetMapping("/appointment/view-details/{id}")
    public String viewScheduledAppointment(@PathVariable final Long id, final Model model) {
        List<OCAppointmentSchedule> appointmentScheduledList = ocAppointmentScheduleService
                .findByIdAsList(id);
        model.addAttribute(APPOINTMENT_SCHEDULED_LIST, appointmentScheduledList);
        return SCHEDULE_APPIONTMENT_RESULT;
    }

    @RequestMapping(value = {"/scrutiny/slot-details"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public void registrarOfficeVillageMapping(@RequestParam Date appointmentDate, @RequestParam Long zoneId, HttpServletResponse response) throws IOException {
        List<SlotDetail> slotDetails = rescheduleAppointmentForOcService.getSlotDetailsByAppointmentDateAndZoneIdForOc(appointmentDate, zoneId);
        final List<JSONObject> jsonObjects = new ArrayList<>();
        if (!slotDetails.isEmpty()) {
            for (final SlotDetail slotDetail : slotDetails) {
                final JSONObject jsonObj = new JSONObject();
                jsonObj.put("appointmentTime", slotDetail.getAppointmentTime());
                jsonObjects.add(jsonObj);
            }
        }
        IOUtils.write(jsonObjects.toString(), response.getWriter());
}


    }

