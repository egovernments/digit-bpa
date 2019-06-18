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
package org.egov.bpa.web.controller.transaction;

import java.util.List;

import javax.validation.Valid;

import org.egov.bpa.master.service.AppointmentLocationsService;
import org.egov.bpa.transaction.entity.InspectionAppointmentSchedule;
import org.egov.bpa.transaction.entity.PermitInspectionApplication;
import org.egov.bpa.transaction.entity.common.AppointmentScheduleCommon;
import org.egov.bpa.transaction.entity.enums.AppointmentSchedulePurpose;
import org.egov.bpa.transaction.service.InspectionApplicationService;
import org.egov.bpa.transaction.service.InspectionAppointmentScheduleService;
import org.egov.bpa.transaction.service.common.AppointmentScheduleCommonService;
import org.egov.bpa.utils.BpaConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/inspection")
public class InspectionAppointmentScheduleController extends BpaGenericApplicationController {

    private static final String MSG = "message";

    private static final String APPOINTMENT_LOCATIONS_LIST = "appointmentLocationsList";

    private static final String APPOINTMENT_SCHEDULED_LIST = "appointmentScheduledList";

    private static final String SCHEDULE_APPIONTMENT_RESULT = "inspection-scheduled-appointment-result";

    private static final String MESSAGE = MSG;

    private static final String APPLICATION_NUMBER = "applicationNumber";

    private static final String REDIRECT_APPLICATION_VIEW_APPOINTMENT = "redirect:/inspection/appointment/view-details/";

    private static final String INS_RESCHEDULE_APPOINTMENT = "inspection-reschedule-appointment";

    private static final String INS_APPOINTMENT_SCHEDULE = "inspectionAppointmentSchedule";

    private static final String INS_SCHEDULE_APPOINTMENT_NEW = "inspection-schedule-appointment-new";

    @Autowired
    private InspectionAppointmentScheduleService insAppointmentScheduleService;
    @Autowired
    private AppointmentLocationsService appointmentLocationsService;
    @Autowired
    private InspectionApplicationService inspectionAppService;
    @Autowired
    private AppointmentScheduleCommonService appointmentScheduleCommonService;

    @GetMapping("/schedule-appointment/{applicationNumber}")
    public String showScheduleAppointmentForInspection(@PathVariable final String applicationNumber, final Model model) {
        final PermitInspectionApplication permitInspection = inspectionAppService.findByInspectionApplicationNumber(applicationNumber);
        InspectionAppointmentSchedule inspectionAppointmentSchedule = new InspectionAppointmentSchedule();
        AppointmentScheduleCommon appointmentSchedule = new AppointmentScheduleCommon();
        
        if (BpaConstants.FWD_TO_OVRSR_FOR_FIELD_INS.equalsIgnoreCase(permitInspection.getInspectionApplication().getCurrentState().getNextAction())) {
            appointmentSchedule.setPurpose(AppointmentSchedulePurpose.INSPECTION);
        }
        inspectionAppointmentSchedule.setAppointmentScheduleCommon(appointmentSchedule);
        inspectionAppointmentSchedule.setInspectionApplication(permitInspection.getInspectionApplication());
        model.addAttribute(APPOINTMENT_LOCATIONS_LIST, appointmentLocationsService.findAllOrderByOrderNumber());
        model.addAttribute(INS_APPOINTMENT_SCHEDULE, inspectionAppointmentSchedule);
        model.addAttribute(APPLICATION_NUMBER, applicationNumber);
        return INS_SCHEDULE_APPOINTMENT_NEW;
    }

    @PostMapping("/schedule-appointment/{applicationNumber}")
    public String scheduleAppointmentForInspection(@Valid @ModelAttribute final InspectionAppointmentSchedule inspectionAppointmentSchedule,
            @PathVariable final String applicationNumber, final Model model, final RedirectAttributes redirectAttributes) {
    	InspectionAppointmentSchedule schedule = buildAndSaveNewAppointment(inspectionAppointmentSchedule, applicationNumber);
        if (AppointmentSchedulePurpose.INSPECTION.equals(inspectionAppointmentSchedule.getAppointmentScheduleCommon().getPurpose())) {
            redirectAttributes.addFlashAttribute(MESSAGE,
                    messageSource.getMessage("msg.new.appoimt.fieldins", null, null));
        }
        return REDIRECT_APPLICATION_VIEW_APPOINTMENT + schedule.getId();
    }

    private InspectionAppointmentSchedule buildAndSaveNewAppointment(final InspectionAppointmentSchedule insAppointmentSchedule, final String applicationNumber) {
    	InspectionAppointmentSchedule schedule = insAppointmentScheduleService.save(insAppointmentSchedule);
        return schedule;
    }

    @GetMapping("/reschedule-appointment/{scheduleType}/{applicationNumber}")
    public String showReScheduleAppointmentForInspection(@PathVariable final AppointmentSchedulePurpose scheduleType,
            @PathVariable final String applicationNumber, final Model model) {
        final PermitInspectionApplication permitInspection = inspectionAppService.findByInspectionApplicationNumber(applicationNumber);
        List<InspectionAppointmentSchedule> appointmentScheduledList = insAppointmentScheduleService.findByApplication(permitInspection.getInspectionApplication(), scheduleType);
        InspectionAppointmentSchedule appointmentSchedule = new InspectionAppointmentSchedule();
        AppointmentScheduleCommon appointmentScheduleCommon = new AppointmentScheduleCommon();
        appointmentScheduleCommon.setPurpose(appointmentScheduledList.get(0).getAppointmentScheduleCommon().getPurpose());
        appointmentSchedule.setAppointmentScheduleCommon(appointmentScheduleCommon);
        model.addAttribute(APPOINTMENT_LOCATIONS_LIST, appointmentLocationsService.findAllOrderByOrderNumber());
        model.addAttribute(INS_APPOINTMENT_SCHEDULE, appointmentSchedule);
        model.addAttribute(APPLICATION_NUMBER, applicationNumber);
        model.addAttribute(APPOINTMENT_SCHEDULED_LIST, appointmentScheduledList);
        model.addAttribute("mode", "postponeappointment");
        return INS_RESCHEDULE_APPOINTMENT;
    }

    @PostMapping("/reschedule-appointment/{scheduleType}/{applicationNumber}")
    public String reScheduleAppointmentForOC(@Valid @ModelAttribute final InspectionAppointmentSchedule insAppointmentSchedule,
            @PathVariable final AppointmentSchedulePurpose scheduleType, @PathVariable final String applicationNumber,
            @RequestParam Long parentAppointmentScheduleId, final RedirectAttributes redirectAttributes) {
        final PermitInspectionApplication permitInspection = inspectionAppService.findByInspectionApplicationNumber(applicationNumber);
        AppointmentScheduleCommon parent = appointmentScheduleCommonService.findById(parentAppointmentScheduleId);
        insAppointmentSchedule.getAppointmentScheduleCommon().setPostponed(true);
        insAppointmentSchedule.getAppointmentScheduleCommon().setParent(parent);
        insAppointmentSchedule.setInspectionApplication(permitInspection.getInspectionApplication());
        InspectionAppointmentSchedule schedule = insAppointmentScheduleService.save(insAppointmentSchedule);
        if (AppointmentSchedulePurpose.INSPECTION.equals(schedule.getAppointmentScheduleCommon().getPurpose())) {
            redirectAttributes.addFlashAttribute(MESSAGE,
                    messageSource.getMessage("msg.update.appoimt.fieldins", null, null));
        }
        return REDIRECT_APPLICATION_VIEW_APPOINTMENT + schedule.getId();
    }

    @GetMapping("/appointment/view-details/{id}")
    public String viewScheduledAppointment(@PathVariable final Long id, final Model model) {
        List<InspectionAppointmentSchedule> appointmentScheduledList = insAppointmentScheduleService
                .findByIdAsList(id);
        model.addAttribute(APPOINTMENT_SCHEDULED_LIST, appointmentScheduledList);
        return SCHEDULE_APPIONTMENT_RESULT;
    }

  }

