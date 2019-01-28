/**
 * eGov suite of products aim to improve the internal efficiency,transparency,
   accountability and the service delivery of the government  organizations.
    Copyright (C) <2015>  eGovernments Foundation
    The updated version of eGov suite of products as by eGovernments Foundation
    is available at http://www.egovernments.org
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    any later version.
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
    You should have received a copy of the GNU General Public License
    along with this program. If not, see http://www.gnu.org/licenses/ or
    http://www.gnu.org/licenses/gpl.html .
    In addition to the terms of the GPL license to be adhered to in using this
    program, the following additional terms are to be complied with:
        1) All versions of this program, verbatim or modified must carry this
           Legal Notice.
        2) Any misrepresentation of the origin of the material is prohibited. It
           is required that all modified versions of this material be marked in
           reasonable ways as different from the original version.
        3) This license does not grant any rights to any user of the program
           with regards to rights under trademark law for use of the trade names
           or trademarks of eGovernments Foundation.
  In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.egov.infra.persistence.entity.AbstractAuditable;

@Entity
@Table(name = "EGBPA_OC_BUILDING")
@SequenceGenerator(name = OCBuilding.SEQEGBPABUILDINGDETAIL, sequenceName = OCBuilding.SEQEGBPABUILDINGDETAIL, allocationSize = 1)
public class OCBuilding extends AbstractAuditable {
    private static final long serialVersionUID = 3078684328383202788L;
    public static final String SEQEGBPABUILDINGDETAIL = "SEQ_EGBPA_OC_BUILDING";
    @Id
    @GeneratedValue(generator = SEQEGBPABUILDINGDETAIL, strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private Integer buildingNumber;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Valid
    @NotNull
    @JoinColumn(name = "oc", nullable = false)
    private OccupancyCertificate oc;
    private Integer floorCount;
    private BigDecimal totalPlinthArea;
    private BigDecimal heightFromGroundWithStairRoom;
    private BigDecimal heightFromGroundWithOutStairRoom;
    private BigDecimal fromStreetLevelWithStairRoom;
    private BigDecimal fromStreetLevelWithOutStairRoom;
    @OneToMany(mappedBy = "buildingDetails", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("orderOfFloor")
    private List<OCFloor> floorDetails = new ArrayList<>(0);
    private transient List<OCFloor> floorDetailsForUpdate = new ArrayList<>(0);
    private transient List<OCFloor> floorDetailsByEdcr = new ArrayList<>(0);

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Integer getFloorCount() {
        return floorCount;
    }

    public void setFloorCount(Integer floorCount) {
        this.floorCount = floorCount;
    }

    public BigDecimal getTotalPlinthArea() {
        return totalPlinthArea;
    }

    public void setTotalPlinthArea(BigDecimal totalPlinthArea) {
        this.totalPlinthArea = totalPlinthArea;
    }

    public BigDecimal getHeightFromGroundWithStairRoom() {
        return heightFromGroundWithStairRoom;
    }

    public void setHeightFromGroundWithStairRoom(BigDecimal heightFromGroundWithStairRoom) {
        this.heightFromGroundWithStairRoom = heightFromGroundWithStairRoom;
    }

    public BigDecimal getHeightFromGroundWithOutStairRoom() {
        return heightFromGroundWithOutStairRoom;
    }

    public void setHeightFromGroundWithOutStairRoom(BigDecimal heightFromGroundWithOutStairRoom) {
        this.heightFromGroundWithOutStairRoom = heightFromGroundWithOutStairRoom;
    }

    public BigDecimal getFromStreetLevelWithStairRoom() {
        return fromStreetLevelWithStairRoom;
    }

    public void setFromStreetLevelWithStairRoom(BigDecimal fromStreetLevelWithStairRoom) {
        this.fromStreetLevelWithStairRoom = fromStreetLevelWithStairRoom;
    }

    public BigDecimal getFromStreetLevelWithOutStairRoom() {
        return fromStreetLevelWithOutStairRoom;
    }

    public void setFromStreetLevelWithOutStairRoom(BigDecimal fromStreetLevelWithOutStairRoom) {
        this.fromStreetLevelWithOutStairRoom = fromStreetLevelWithOutStairRoom;
    }

    public List<OCFloor> getFloorDetails() {
        return floorDetails;
    }

    public void setFloorDetails(List<OCFloor> floorDetails) {
        this.floorDetails = floorDetails;
    }

    public List<OCFloor> getFloorDetailsForUpdate() {
        return floorDetailsForUpdate;
    }

    public void setFloorDetailsForUpdate(List<OCFloor> floorDetailsForUpdate) {
        this.floorDetailsForUpdate = floorDetailsForUpdate;
    }

    public List<OCFloor> getFloorDetailsByEdcr() {
        return floorDetailsByEdcr;
    }

    public void setFloorDetailsByEdcr(List<OCFloor> floorDetailsByEdcr) {
        this.floorDetailsByEdcr = floorDetailsByEdcr;
    }

}
