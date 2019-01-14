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
package org.egov.bpa.transaction.service;

import java.util.Date;
import java.util.List;

import org.egov.bpa.service.es.BpaIndexService;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.BpaStatus;
import org.egov.bpa.transaction.entity.SlotApplication;
import org.egov.bpa.transaction.entity.SlotDetail;
import org.egov.bpa.transaction.entity.enums.ScheduleAppointmentType;
import org.egov.bpa.transaction.repository.ApplicationBpaRepository;
import org.egov.bpa.transaction.repository.BpaStatusRepository;
import org.egov.bpa.transaction.repository.SlotApplicationRepository;
import org.egov.bpa.transaction.repository.SlotDetailRepository;
import org.egov.bpa.transaction.service.messaging.BPASmsAndEmailService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.infra.admin.master.entity.Boundary;
import org.egov.infra.admin.master.service.BoundaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RescheduleAppointmentsForDocumentScrutinyService {

    @Autowired
    private ApplicationBpaRepository applicationBpaRepository;

    @Autowired
    private SlotApplicationRepository slotApplicationRepository;

    @Autowired
    private SlotDetailRepository slotDetailRepository;

    @Autowired
    private BpaStatusRepository bpaStatusRepository;

    @Autowired
    private BPASmsAndEmailService bpaSmsAndEmailService;
    
    @Autowired
    private BoundaryService boundaryService;
    
    @Autowired
    private BpaIndexService bpaIndexService;

	@Transactional
	public SlotApplication rescheduleAppointmentsForDocumentScrutinyByCitizen(Long applicationId,
			Date rescheduleAppointmentDate, String appointmentTime) {
        BpaApplication application = applicationBpaRepository.findById(applicationId);
        application.setIsRescheduledByCitizen(true);
        List<SlotApplication> slotApplication = slotApplicationRepository.findByApplicationOrderByIdDesc(application);
        // free up previous slot
        SlotDetail slotDetail = slotApplication.get(0).getSlotDetail();
        if (slotApplication.get(0).getScheduleAppointmentType().toString()
                .equals(ScheduleAppointmentType.SCHEDULE.toString()))
            slotDetail.setUtilizedScheduledSlots(slotDetail.getUtilizedScheduledSlots() - 1);
        else
            slotDetail.setUtilizedRescheduledSlots(slotDetail.getUtilizedRescheduledSlots() - 1);
        updateParentInActive(slotApplication.get(0));
        slotDetailRepository.save(slotDetail);
        // build new slot application and reschedule appointment
        SlotApplication slotApp = new SlotApplication();
        slotApp.setApplication(application);
        slotApp.setActive(true);
        slotApp.setScheduleAppointmentType(ScheduleAppointmentType.RESCHEDULE);
        SlotDetail slotDet = slotDetailRepository.findByAppointmentDateTimeAndZone(rescheduleAppointmentDate,
                appointmentTime, application.getSiteDetail().get(0).getAdminBoundary().getParent(),"Normal");
        if (slotDet.getMaxRescheduledSlots() - slotDet.getUtilizedRescheduledSlots() > 0)
            slotDet.setUtilizedRescheduledSlots(slotDet.getUtilizedRescheduledSlots() + 1);
        else
            slotDet.setUtilizedScheduledSlots(slotDet.getUtilizedScheduledSlots() + 1);
        slotApp.setSlotDetail(slotDet);
        applicationBpaRepository.save(application);
        bpaIndexService.updateIndexes(application);
        bpaSmsAndEmailService.sendSMSAndEmailForDocumentScrutiny(slotApp);
        slotApplicationRepository.save(slotApp);
        return slotApp;
	}


	@Transactional
	public SlotApplication rescheduleAppointmentsForDocumentScrutinyByEmployee(Long applicationId,
			Date rescheduleAppointmentDate, String appointmentTime) {
        BpaApplication bpaApplication = applicationBpaRepository.findById(applicationId);
        bpaApplication.setIsRescheduledByEmployee(true);
        List<SlotApplication> slotApplication = slotApplicationRepository
                .findByApplicationOrderByIdDesc(bpaApplication);
        // free up previous slot
        SlotDetail slotDetail = slotApplication.get(0).getSlotDetail();
        if (slotApplication.get(0).getScheduleAppointmentType().toString()
                .equals(ScheduleAppointmentType.SCHEDULE.toString()))
            slotDetail.setUtilizedScheduledSlots(slotDetail.getUtilizedScheduledSlots() - 1);
        else
            slotDetail.setUtilizedRescheduledSlots(slotDetail.getUtilizedRescheduledSlots() - 1);
        slotDetailRepository.save(slotDetail);
        updateParentInActive(slotApplication.get(0));
        // build new slot application and reschedule appointment
        SlotApplication slotApp = new SlotApplication();
        slotApp.setApplication(bpaApplication);
        slotApp.setActive(true);
        slotApp.setScheduleAppointmentType(ScheduleAppointmentType.RESCHEDULE);
        SlotDetail slotDet = slotDetailRepository.findByAppointmentDateTimeAndZone(rescheduleAppointmentDate,
                appointmentTime, bpaApplication.getSiteDetail().get(0).getAdminBoundary().getParent(),"Normal");
        if (slotDet.getMaxRescheduledSlots() - slotDet.getUtilizedRescheduledSlots() > 0)
            slotDet.setUtilizedRescheduledSlots(slotDet.getUtilizedRescheduledSlots() + 1);
        else
            slotDet.setUtilizedScheduledSlots(slotDet.getUtilizedScheduledSlots() + 1);
        slotApp.setSlotDetail(slotDet);
        applicationBpaRepository.save(bpaApplication);
        bpaIndexService.updateIndexes(bpaApplication);
        bpaSmsAndEmailService.sendSMSAndEmailForDocumentScrutiny(slotApp);
        slotApplicationRepository.save(slotApp);
        return slotApp;
	}

	private void updateParentInActive(final SlotApplication slotApplication) {
		slotApplication.setActive(false);
		slotApplicationRepository.save(slotApplication);
	}

	public List<SlotDetail> searchAvailableSlotsForReschedule(Long applicationId) {
		BpaApplication bpaApplication = applicationBpaRepository.findById(applicationId);
		Boundary zone = bpaApplication.getSiteDetail().get(0).getAdminBoundary().getParent();
		List<SlotApplication> slotApplication = slotApplicationRepository
				.findByApplicationOrderByIdDesc(bpaApplication);
		Date appointmentDate = slotApplication.get(0).getSlotDetail().getSlot().getAppointmentDate();
		return slotDetailRepository.findSlotsByAppointmentDateAndZone(appointmentDate,zone,"Normal");
		
	}

	@Transactional
	public void rescheduleAppointmentWhenSlotsNotAvailable(Long id, String scheduleBy) {
        BpaApplication bpaApplication = applicationBpaRepository.findById(id);
        if ("EMPLOYEE".equals(scheduleBy)) {
            bpaApplication.setIsRescheduledByEmployee(true);
        } else if ("CITIZENPORTAL".equals(scheduleBy)) {
            bpaApplication.setIsRescheduledByCitizen(true);
        }
        List<SlotApplication> slotApplication = slotApplicationRepository
                .findByApplicationOrderByIdDesc(bpaApplication);
        SlotDetail slotDetail = slotApplication.get(0).getSlotDetail();
        if (slotApplication.get(0).getScheduleAppointmentType().toString()
                .equals(ScheduleAppointmentType.SCHEDULE.toString()))
            slotDetail.setUtilizedScheduledSlots(slotDetail.getUtilizedScheduledSlots() - 1);
        else
            slotDetail.setUtilizedRescheduledSlots(slotDetail.getUtilizedRescheduledSlots() - 1);
        slotDetailRepository.save(slotDetail);
        BpaStatus bpaStatus = bpaStatusRepository.findByCode(BpaConstants.APPLICATION_STATUS_PENDING_FOR_RESCHEDULING);
        bpaApplication.setStatus(bpaStatus);
        applicationBpaRepository.save(bpaApplication);
        bpaIndexService.updateIndexes(bpaApplication);
        bpaSmsAndEmailService.sendSMSAndEmailForDocumentScrutiny(slotApplication.get(0));
	}

	public List<SlotDetail> getOneSlotDetailsByAppointmentDateAndZoneId(final Date appointmentDate, final Long zoneId) {
		return slotDetailRepository.findOneByAppointmentDateAndZoneId(appointmentDate, boundaryService.getBoundaryById(zoneId),"Normal");
	}

}
