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

import org.egov.bpa.transaction.entity.enums.ScheduleAppointmentType;
import org.egov.infra.persistence.entity.AbstractAuditable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "egbpa_slotapplication")
@SequenceGenerator(name = SlotApplication.SEQ, sequenceName = SlotApplication.SEQ, allocationSize = 1)
public class SlotApplication extends AbstractAuditable {

	private static final long serialVersionUID = 1L;
	public static final String SEQ = "seq_egbpa_slotapplication";
	@Id
	@GeneratedValue(generator = SEQ, strategy = GenerationType.SEQUENCE)
	private Long id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "application", nullable = false)
	private BpaApplication application;

	@Enumerated(EnumType.STRING)
	@NotNull
	private ScheduleAppointmentType scheduleAppointmentType;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "slotdetailid", nullable = false)
	private SlotDetail slotDetail;

	private Boolean isActive;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public BpaApplication getApplication() {
		return application;
	}

	public void setApplication(BpaApplication application) {
		this.application = application;
	}

	public ScheduleAppointmentType getScheduleAppointmentType() {
		return scheduleAppointmentType;
	}

	public void setScheduleAppointmentType(ScheduleAppointmentType scheduleAppointmentType) {
		this.scheduleAppointmentType = scheduleAppointmentType;
	}

	public SlotDetail getSlotDetail() {
		return slotDetail;
	}

	public void setSlotDetail(SlotDetail slotDetail) {
		this.slotDetail = slotDetail;
	}

	public Boolean getActive() {
		return isActive;
	}

	public void setActive(Boolean active) {
		isActive = active;
	}
}
