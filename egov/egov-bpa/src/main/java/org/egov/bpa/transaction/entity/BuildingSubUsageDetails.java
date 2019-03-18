/*
 *    eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) 2018  eGovernments Foundation
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
 *            Further, all user interfaces, including but not limited to citizen facing interfaces,
 *            Urban Local Bodies interfaces, dashboards, mobile applications, of the program and any
 *            derived works should carry eGovernments Foundation logo on the top right corner.
 *
 *            For the logo, please refer http://egovernments.org/html/logo/egov_logo.png.
 *            For any further queries on attribution, including queries on brand guidelines,
 *            please contact contact@egovernments.org
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
 *
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.egov.common.entity.bpa.Occupancy;
import org.egov.common.entity.bpa.Usage;
import org.egov.infra.persistence.entity.AbstractAuditable;

@Entity
@Table(name = "egbpa_building_sub_usage_details")
@SequenceGenerator(name = BuildingSubUsageDetails.SEQ_SUB_USAGE, sequenceName = BuildingSubUsageDetails.SEQ_SUB_USAGE, allocationSize = 1)
public class BuildingSubUsageDetails extends AbstractAuditable {

	public static final String SEQ_SUB_USAGE = "seq_egbpa_building_sub_usage_details";
	private static final long serialVersionUID = -5785039918517422291L;

	@Id
	@GeneratedValue(generator = SEQ_SUB_USAGE, strategy = GenerationType.SEQUENCE)
	private Long id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "mainUsage")
	private Occupancy mainUsage;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "buildingSubUsage")
	private BuildingSubUsage buildingSubUsage;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "egbpa_builing_usage", joinColumns = @JoinColumn(name = "subUsageDetails"), inverseJoinColumns = @JoinColumn(name = "usage"))
	private List<Usage> subUsages = new ArrayList<>();
	
/*	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "egbpa_sub_usage", joinColumns = @JoinColumn(name = "subUsageDetails"), inverseJoinColumns = @JoinColumn(name = "suboccupancy"))
	private List<SubOccupancy> subUsages;*/

	private transient List<Usage> subUsagesTemp = new ArrayList<>();

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Occupancy getMainUsage() {
		return mainUsage;
	}

	public void setMainUsage(Occupancy mainUsage) {
		this.mainUsage = mainUsage;
	}

	public BuildingSubUsage getBuildingSubUsage() {
		return buildingSubUsage;
	}

	public void setBuildingSubUsage(BuildingSubUsage buildingSubUsage) {
		this.buildingSubUsage = buildingSubUsage;
	}

	public List<Usage> getSubUsagesTemp() {
		return subUsagesTemp;
	}

	public List<Usage> getSubUsages() {
		return subUsages;
	}

	public void setSubUsages(List<Usage> subUsages) {
		this.subUsages = subUsages;
	}

	public void setSubUsagesTemp(List<Usage> subUsagesTemp) {
		this.subUsagesTemp = subUsagesTemp;
	}

}
