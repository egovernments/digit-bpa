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
package org.egov.bpa.transaction.service.oc;

import org.egov.bpa.service.es.OccupancyCertificateIndexService;
import org.egov.bpa.transaction.entity.BpaStatus;
import org.egov.bpa.transaction.entity.SlotDetail;
import org.egov.bpa.transaction.entity.enums.ScheduleAppointmentType;
import org.egov.bpa.transaction.entity.oc.OCSlot;
import org.egov.bpa.transaction.entity.oc.OccupancyCertificate;
import org.egov.bpa.transaction.repository.BpaStatusRepository;
import org.egov.bpa.transaction.repository.OcSlotRepository;
import org.egov.bpa.transaction.repository.SlotDetailRepository;
import org.egov.bpa.transaction.repository.oc.OccupancyCertificateRepository;
import org.egov.bpa.transaction.service.messaging.oc.OcSmsAndEmailService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.infra.admin.master.entity.Boundary;
import org.egov.infra.admin.master.service.BoundaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class RescheduleAppointmentForOcService {

    @Autowired
    private OccupancyCertificateService occupancyCertificateService;

    @Autowired
    private OcSlotRepository ocSlotRepository;

    @Autowired
    private SlotDetailRepository slotDetailRepository;

    @Autowired
    private BpaStatusRepository bpaStatusRepository;

    @Autowired
    private OcSmsAndEmailService ocSmsAndEmailService;

    @Autowired
    private BoundaryService boundaryService;

    @Autowired
    private OccupancyCertificateRepository occupancyCertificateRepository;

    @Autowired
    private OccupancyCertificateIndexService occupancyCertificateIndexService;


    @Transactional
    public OCSlot rescheduleAppointmentsForOcByCitizen(Long applicationId, Date rescheduleAppointmentDate, String appointmentTime) {
        OccupancyCertificate occupancyCertificate = occupancyCertificateService.findById(applicationId);
        occupancyCertificate.setRescheduledByCitizen(true);
        List<OCSlot> ocSlots = ocSlotRepository.findByOcOrderByIdDesc(occupancyCertificate);
        // free up previous slot
        SlotDetail slotDetail = ocSlots.get(0).getSlotDetail();
        if (ocSlots.get(0).getScheduleAppointmentType().toString()
                .equals(ScheduleAppointmentType.SCHEDULE.toString()))
            slotDetail.setUtilizedScheduledSlots(slotDetail.getUtilizedScheduledSlots() - 1);
        else
            slotDetail.setUtilizedRescheduledSlots(slotDetail.getUtilizedRescheduledSlots() - 1);
        updateParentInActive(ocSlots.get(0));
        slotDetailRepository.save(slotDetail);
        // build new slot application and reschedule appointment
        OCSlot ocSlot = new OCSlot();
        ocSlot.setOc(occupancyCertificate);
        ocSlot.setActive(true);
        ocSlot.setScheduleAppointmentType(ScheduleAppointmentType.RESCHEDULE);
        SlotDetail slotDet = slotDetailRepository.findByAppointmentDateTimeAndZone(rescheduleAppointmentDate,
                appointmentTime, occupancyCertificate.getParent().getSiteDetail().get(0).getAdminBoundary().getParent().getId(), "Occupancy Certificate");
        if (slotDet.getMaxRescheduledSlots() - slotDet.getUtilizedRescheduledSlots() > 0)
            slotDet.setUtilizedRescheduledSlots(slotDet.getUtilizedRescheduledSlots() + 1);
        else
            slotDet.setUtilizedScheduledSlots(slotDet.getUtilizedScheduledSlots() + 1);
        ocSlot.setSlotDetail(slotDet);
        occupancyCertificateRepository.save(occupancyCertificate);
        occupancyCertificateIndexService.updateOccupancyIndex(occupancyCertificate);
        ocSmsAndEmailService.sendSMSAndEmailForOcDocumentScrutiny(ocSlot);
        ocSlotRepository.save(ocSlot);
        return ocSlot;
    }


    @Transactional
    public OCSlot rescheduleAppointmentsForOcByEmployee(Long applicationId,
                                                        Date rescheduleAppointmentDate, String appointmentTime) {
        OccupancyCertificate occupancyCertificate = occupancyCertificateService.findById(applicationId);
        occupancyCertificate.setRescheduledByEmployee(true);
        List<OCSlot> ocSlots = ocSlotRepository
                .findByOcOrderByIdDesc(occupancyCertificate);
        // free up previous slot
        SlotDetail slotDetail = ocSlots.get(0).getSlotDetail();
        if (ocSlots.get(0).getScheduleAppointmentType().toString()
                .equals(ScheduleAppointmentType.SCHEDULE.toString()))
            slotDetail.setUtilizedScheduledSlots(slotDetail.getUtilizedScheduledSlots() - 1);
        else
            slotDetail.setUtilizedRescheduledSlots(slotDetail.getUtilizedRescheduledSlots() - 1);
        slotDetailRepository.save(slotDetail);
        updateParentInActive(ocSlots.get(0));
        // build new slot application and reschedule appointment
        OCSlot ocSlot = new OCSlot();
        ocSlot.setOc(occupancyCertificate);
        ocSlot.setActive(true);
        ocSlot.setScheduleAppointmentType(ScheduleAppointmentType.RESCHEDULE);
        SlotDetail slotDet = slotDetailRepository.findByAppointmentDateTimeAndZone(rescheduleAppointmentDate,
                appointmentTime, occupancyCertificate.getParent().getSiteDetail().get(0).getAdminBoundary().getParent().getId(), "Occupancy Certificate");
        if (slotDet.getMaxRescheduledSlots() - slotDet.getUtilizedRescheduledSlots() > 0)
            slotDet.setUtilizedRescheduledSlots(slotDet.getUtilizedRescheduledSlots() + 1);
        else
            slotDet.setUtilizedScheduledSlots(slotDet.getUtilizedScheduledSlots() + 1);
        ocSlot.setSlotDetail(slotDet);
        occupancyCertificateRepository.save(occupancyCertificate);
        occupancyCertificateIndexService.updateOccupancyIndex(occupancyCertificate);
        ocSmsAndEmailService.sendSMSAndEmailForOcDocumentScrutiny(ocSlot);
        ocSlotRepository.save(ocSlot);
        return ocSlot;
    }

    private void updateParentInActive(final OCSlot ocSlot) {
        ocSlot.setActive(false);
        ocSlotRepository.save(ocSlot);
    }

    public List<SlotDetail> searchAvailableSlotsForOcReschedule(Long applicationId) {
        OccupancyCertificate occupancyCertificate = occupancyCertificateService.findById(applicationId);
        Boundary zone = occupancyCertificate.getParent().getSiteDetail().get(0).getAdminBoundary().getParent();
        List<OCSlot> ocSlots = ocSlotRepository
                .findByOcOrderByIdDesc(occupancyCertificate);
        Date appointmentDate = ocSlots.get(0).getSlotDetail().getSlot().getAppointmentDate();
        return slotDetailRepository.findSlotsByAppointmentDateAndZone(appointmentDate, zone.getId(), "Occupancy Certificate");

    }

    @Transactional
    public void rescheduleAppointmentWhenSlotsNotAvailableForOc(Long id, String scheduleBy) {
        OccupancyCertificate occupancyCertificate = occupancyCertificateService.findById(id);
        if ("EMPLOYEE".equals(scheduleBy)) {
            occupancyCertificate.setRescheduledByEmployee(true);
        } else if ("CITIZENPORTAL".equals(scheduleBy)) {
            occupancyCertificate.setRescheduledByCitizen(true);
        }
        List<OCSlot> ocSlots = ocSlotRepository
                .findByOcOrderByIdDesc(occupancyCertificate);
        SlotDetail slotDetail = ocSlots.get(0).getSlotDetail();
        if (ocSlots.get(0).getScheduleAppointmentType().toString()
                .equals(ScheduleAppointmentType.SCHEDULE.toString()))
            slotDetail.setUtilizedScheduledSlots(slotDetail.getUtilizedScheduledSlots() - 1);
        else
            slotDetail.setUtilizedRescheduledSlots(slotDetail.getUtilizedRescheduledSlots() - 1);
        slotDetailRepository.save(slotDetail);
        BpaStatus bpaStatus = bpaStatusRepository.findByCode(BpaConstants.APPLICATION_STATUS_PENDING_FOR_RESCHEDULING);
        occupancyCertificate.setStatus(bpaStatus);
        occupancyCertificateRepository.save(occupancyCertificate);
        occupancyCertificateIndexService.updateOccupancyIndex(occupancyCertificate);
        ocSmsAndEmailService.sendSMSAndEmailForOcDocumentScrutiny(ocSlots.get(0));
    }

    public List<SlotDetail> getSlotDetailsByAppointmentDateAndZoneIdForOc(final Date appointmentDate, final Long zoneId) {
        return slotDetailRepository.findOneByAppointmentDateAndZoneId(appointmentDate, zoneId, "Occupancy Certificate");
    }

}
