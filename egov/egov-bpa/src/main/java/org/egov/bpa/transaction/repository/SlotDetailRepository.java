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
package org.egov.bpa.transaction.repository;

import org.egov.bpa.transaction.entity.Slot;
import org.egov.bpa.transaction.entity.SlotDetail;
import org.egov.infra.admin.master.entity.Boundary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SlotDetailRepository extends JpaRepository<SlotDetail, Long>, JpaSpecificationExecutor<SlotDetail> {

    @Query("select detail from SlotDetail detail where detail.maxScheduledSlots - detail.utilizedScheduledSlots > 0 order by detail.slot.appointmentDate , detail.id asc")
    List<SlotDetail> findSlotDetailOrderByAppointmentDate();

    @Query("select detail from SlotDetail detail where detail.appointmentTime  = :appointmentTime and detail.slot.appointmentDate = :rescheduleAppointmentDate and detail.slot.zoneId = :zone and detail.slot.type = :type")
    SlotDetail findByAppointmentDateTimeAndZone(@Param("rescheduleAppointmentDate") Date rescheduleAppointmentDate,
            @Param("appointmentTime") String appointmentTime,
            @Param("zone") Long zone,@Param("type") String type);

    @Query("select slotdetail from SlotDetail slotdetail"
            + " where slotdetail.slot.appointmentDate >= :appointmentDate and (slotdetail.maxScheduledSlots"
            + " - slotdetail.utilizedScheduledSlots >0 or slotdetail.maxRescheduledSlots -"
            + " slotdetail.utilizedRescheduledSlots >0 or (slotdetail.maxScheduledSlots -"
            + " slotdetail.utilizedScheduledSlots >0 and slotdetail.maxRescheduledSlots -"
            + " slotdetail.utilizedRescheduledSlots >0)) and slotdetail.slot.zoneId = :zone and slotdetail.slot.type = :type order by slotdetail.slot.appointmentDate ,"
            + " slotdetail.id")
    List<SlotDetail> findSlotsByAppointmentDateAndZone(@Param("appointmentDate") Date appointmentDate,
            @Param("zone") Long zone,@Param("type") String type);

    @Query("select detail from SlotDetail detail where detail.slot.appointmentDate = :rescheduleAppointmentDate and detail.slot.zoneId = :zone"
            + " and (detail.maxScheduledSlots" + " - detail.utilizedScheduledSlots >0 or detail.maxRescheduledSlots -"
            + " detail.utilizedRescheduledSlots >0 or (detail.maxScheduledSlots -"
            + " detail.utilizedScheduledSlots >0 and detail.maxRescheduledSlots -"
            + " detail.utilizedRescheduledSlots >0)) and detail.slot.type = :type order by detail.id asc")
    List<SlotDetail> findOneByAppointmentDateAndZoneId(
            @Param("rescheduleAppointmentDate") Date rescheduleAppointmentDate, @Param("zone") Long zone, @Param("type") String type);

    @Query("select slotdetail from SlotDetail slotdetail where slotdetail.slot =:slot and (slotdetail.maxScheduledSlots"
            + " - slotdetail.utilizedScheduledSlots >0 or slotdetail.maxRescheduledSlots -"
            + " slotdetail.utilizedRescheduledSlots >0 or (slotdetail.maxScheduledSlots -"
            + " slotdetail.utilizedScheduledSlots >0 and slotdetail.maxRescheduledSlots -"
            + " slotdetail.utilizedRescheduledSlots >0)) order by slotdetail.id asc")
    List<SlotDetail> findBySlot(@Param("slot") Slot slot);

    @Query("select slotdetail from SlotDetail slotdetail where slotdetail.slot =:slot and                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        slotdetail.maxScheduledSlots"
            + " - slotdetail.utilizedScheduledSlots >0 order by slotdetail.id asc")
    List<SlotDetail> findBySlotForOneDayPermit(@Param("slot") Slot slot);
}
