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
package org.egov.bpa.master.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.egov.bpa.master.entity.enums.WorkingDays;
import org.egov.infra.admin.master.entity.Boundary;
import org.egov.infra.persistence.entity.AbstractAuditable;

@Entity
@Table(name = "egbpa_mstr_slotmapping")
@SequenceGenerator(name = SlotMapping.SEQ, sequenceName = SlotMapping.SEQ, allocationSize = 1)
public class SlotMapping extends AbstractAuditable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String SEQ = "seq_egbpa_mstr_slotmapping";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ)
	private Long id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "zone")
	private Boundary zone;
	
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "revenueward")
	private Boundary revenueWard; 

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "electionward")
	private Boundary electionWard;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "slotApplicationType")
	private ApplicationType applicationType;

	@Transient
	private WorkingDays days;

	private String day;

	@NotNull
	private Integer maxSlotsAllowed;

	private Integer maxRescheduledSlotsAllowed;

	public Boundary getRevenueWard() {
		return revenueWard;
	}

	public void setRevenueWard(Boundary revenueWard) {
		this.revenueWard = revenueWard;
	}

	public Boundary getElectionWard() {
		return electionWard;
	}

	public void setElectionWard(Boundary electionWard) {
		this.electionWard = electionWard;
	}

	public String getDay() {
		return day;
	}

	public WorkingDays getDays() {
		return days;
	}

	public void setDays(WorkingDays days) {
		this.days = days;
	}

	public void setDay(String day) {
		this.day = day;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Boundary getZone() {
		return zone;
	}

	public void setZone(Boundary zone) {
		this.zone = zone;
	}

	public Integer getMaxSlotsAllowed() {
		return maxSlotsAllowed;
	}

	public void setMaxSlotsAllowed(Integer maxSlotsAllowed) {
		this.maxSlotsAllowed = maxSlotsAllowed;
	}

	public Integer getMaxRescheduledSlotsAllowed() {
		return maxRescheduledSlotsAllowed;
	}

	public void setMaxRescheduledSlotsAllowed(Integer maxRescheduledSlotsAllowed) {
		this.maxRescheduledSlotsAllowed = maxRescheduledSlotsAllowed;
	}

	public ApplicationType getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(ApplicationType applicationType) {
		this.applicationType = applicationType;
	}

}
