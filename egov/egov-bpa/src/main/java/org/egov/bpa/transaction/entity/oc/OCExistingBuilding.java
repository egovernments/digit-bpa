/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2015>  eGovernments Foundation
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

package org.egov.bpa.transaction.entity.oc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.egov.infra.persistence.entity.AbstractAuditable;

@Entity
@Table(name = "EGBPA_OC_EXISTING_BUILDING")
@SequenceGenerator(name = OCExistingBuilding.SEQ_EXISTING_BUILDING, sequenceName = OCExistingBuilding.SEQ_EXISTING_BUILDING, allocationSize = 1)
public class OCExistingBuilding extends AbstractAuditable {
    private static final long serialVersionUID = 3078684328383202788L;
    public static final String SEQ_EXISTING_BUILDING = "SEQ_EGBPA_OC_EXISTING_BUILDING";
    @Id
    @GeneratedValue(generator = SEQ_EXISTING_BUILDING, strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private Integer buildingNumber;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Valid
    @NotNull
    @JoinColumn(name = "oc", nullable = false)
    private OccupancyCertificate oc;
    private BigDecimal totalPlinthArea;
    @OneToMany(mappedBy = "existingBuildingDetail", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("orderOfFloor")
    private List<OCExistingBuildingFloor> existingBuildingFloorDetails = new ArrayList<>(0);
    private transient List<OCExistingBuildingFloor> existingBuildingFloorDetailsUpdate = new ArrayList<>(0);
    private transient List<OCExistingBuildingFloor> existingBldgFloorDetailsFromEdcr = new ArrayList<>(0);
    private transient Long[] deletedFloorIds;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(Integer buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public OccupancyCertificate getOc() {
        return oc;
    }

    public void setOc(OccupancyCertificate oc) {
        this.oc = oc;
    }

    public BigDecimal getTotalPlinthArea() {
        return totalPlinthArea;
    }

    public void setTotalPlinthArea(BigDecimal totalPlinthArea) {
        this.totalPlinthArea = totalPlinthArea;
    }

    public List<OCExistingBuildingFloor> getExistingBuildingFloorDetails() {
        return existingBuildingFloorDetails;
    }

    public void setExistingBuildingFloorDetails(List<OCExistingBuildingFloor> existingBuildingFloorDetails) {
        this.existingBuildingFloorDetails = existingBuildingFloorDetails;
    }

    public List<OCExistingBuildingFloor> getExistingBuildingFloorDetailsUpdate() {
        return existingBuildingFloorDetailsUpdate;
    }

    public void setExistingBuildingFloorDetailsUpdate(List<OCExistingBuildingFloor> existingBuildingFloorDetailsUpdate) {
        this.existingBuildingFloorDetailsUpdate = existingBuildingFloorDetailsUpdate;
    }

    public List<OCExistingBuildingFloor> getExistingBldgFloorDetailsFromEdcr() {
        return existingBldgFloorDetailsFromEdcr;
    }

    public void setExistingBldgFloorDetailsFromEdcr(List<OCExistingBuildingFloor> existingBldgFloorDetailsFromEdcr) {
        this.existingBldgFloorDetailsFromEdcr = existingBldgFloorDetailsFromEdcr;
    }

    public Long[] getDeletedFloorIds() {
        return deletedFloorIds;
    }

    public void setDeletedFloorIds(Long[] deletedFloorIds) {
        this.deletedFloorIds = deletedFloorIds;
    }

    public void delete(final List<OCExistingBuildingFloor> existingBuildingFloorDetails) {
        if (existingBuildingFloorDetails != null)
            this.existingBuildingFloorDetails.removeAll(existingBuildingFloorDetails);
    }

}
