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
package org.egov.bpa.transaction.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.egov.infra.persistence.entity.AbstractAuditable;

@Entity
@Table(name = "egbpa_slotdetail")
@SequenceGenerator(name = SlotDetail.SEQ, sequenceName = SlotDetail.SEQ, allocationSize = 1)
public class SlotDetail extends AbstractAuditable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String SEQ = "seq_egbpa_slotdetail";

	@Id
	@GeneratedValue(generator = SEQ, strategy = GenerationType.SEQUENCE)
	private Long id;

	@NotNull
	private String appointmentTime;

	@NotNull
	private Integer maxScheduledSlots;

	@NotNull
	private Integer maxRescheduledSlots;

	@NotNull
	private Integer utilizedScheduledSlots;

	@NotNull
	private Integer utilizedRescheduledSlots;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "slotid", nullable = false)
	private Slot slot;

	@OneToMany(mappedBy = "slotDetail", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<SlotApplication> slotApplication = new ArrayList<>();

	public Slot getSlot() {
		return slot;
	}

	public void setSlot(Slot slot) {
		this.slot = slot;
	}

	public List<SlotApplication> getSlotApplication() {
		return slotApplication;
	}

	public void setSlotApplication(List<SlotApplication> slotApplication) {
		this.slotApplication = slotApplication;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getAppointmentTime() {
		return appointmentTime;
	}

	public void setAppointmentTime(String appointmentTime) {
		this.appointmentTime = appointmentTime;
	}

	public Integer getMaxScheduledSlots() {
		return maxScheduledSlots;
	}

	public void setMaxScheduledSlots(Integer maxScheduledSlots) {
		this.maxScheduledSlots = maxScheduledSlots;
	}

	public Integer getMaxRescheduledSlots() {
		return maxRescheduledSlots;
	}

	public void setMaxRescheduledSlots(Integer maxRescheduledSlots) {
		this.maxRescheduledSlots = maxRescheduledSlots;
	}

	public Integer getUtilizedScheduledSlots() {
		return utilizedScheduledSlots;
	}

	public void setUtilizedScheduledSlots(Integer utilizedScheduledSlots) {
		this.utilizedScheduledSlots = utilizedScheduledSlots;
	}

	public Integer getUtilizedRescheduledSlots() {
		return utilizedRescheduledSlots;
	}

	public void setUtilizedRescheduledSlots(Integer utilizedRescheduledSlots) {
		this.utilizedRescheduledSlots = utilizedRescheduledSlots;
	}

	public SlotDetail() {

	}

	public SlotDetail(String appointmentTime, Integer maxScheduledSlots, Integer maxRescheduledSlots,
			Integer utilizedScheduledSlots, Integer utilizedRescheduledSlots, Slot slot) {
		this.appointmentTime = appointmentTime;
		this.maxScheduledSlots = maxScheduledSlots;
		this.maxRescheduledSlots = maxRescheduledSlots;
		this.utilizedScheduledSlots = utilizedScheduledSlots;
		this.utilizedRescheduledSlots = utilizedRescheduledSlots;
		this.slot = slot;
	}

}
