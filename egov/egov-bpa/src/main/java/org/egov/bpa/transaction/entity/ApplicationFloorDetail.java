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
package org.egov.bpa.transaction.entity;

import java.math.BigDecimal;

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
import javax.validation.constraints.PositiveOrZero;

import org.egov.common.entity.bpa.SubOccupancy;
import org.egov.common.entity.bpa.Usage;
import org.egov.infra.persistence.entity.AbstractAuditable;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Table(name = "EGBPA_APPLICATION_FLOORDETAIL")
@SequenceGenerator(name = ApplicationFloorDetail.SEQ_EGBPA_REGN_FLOORDETAIL, sequenceName = ApplicationFloorDetail.SEQ_EGBPA_REGN_FLOORDETAIL, allocationSize = 1)
public class ApplicationFloorDetail extends AbstractAuditable {

    private static final long serialVersionUID = 1L;
    public static final String SEQ_EGBPA_REGN_FLOORDETAIL = "SEQ_EGBPA_APPLICATION_FLOORDETAIL";
    @Id
    @GeneratedValue(generator = SEQ_EGBPA_REGN_FLOORDETAIL, strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buildingDetail", nullable = false)
    private BuildingDetail buildingDetail;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "suboccupancy")
    private SubOccupancy subOccupancy;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "usage")
    private Usage usage;
    @Range(min = -50, max = 500)
    private Integer floorNumber;
    @SafeHtml
    private String floorDescription;
    @PositiveOrZero
    private BigDecimal carpetArea;
    @PositiveOrZero
    private BigDecimal plinthArea;
    @PositiveOrZero
    private BigDecimal floorArea;
    @PositiveOrZero
    private Integer orderOfFloor;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(final Long id) {
        this.id = id;
    }

    public BuildingDetail getBuildingDetail() {
        return buildingDetail;
    }

    public void setBuildingDetail(BuildingDetail buildingDetail) {
        this.buildingDetail = buildingDetail;
    }

    public SubOccupancy getSubOccupancy() {
        return subOccupancy;
    }

    public void setSubOccupancy(SubOccupancy subOccupancy) {
        this.subOccupancy = subOccupancy;
    }

    public Usage getUsage() {
        return usage;
    }

    public void setUsage(Usage usage) {
        this.usage = usage;
    }

    public Integer getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(Integer floorNumber) {
        this.floorNumber = floorNumber;
    }

    public String getFloorDescription() {
        return floorDescription;
    }

    public void setFloorDescription(String floorDescription) {
        this.floorDescription = floorDescription;
    }

    public BigDecimal getCarpetArea() {
        return carpetArea;
    }

    public void setCarpetArea(BigDecimal carpetArea) {
        this.carpetArea = carpetArea;
    }

    public BigDecimal getPlinthArea() {
        return plinthArea;
    }

    public void setPlinthArea(BigDecimal plinthArea) {
        this.plinthArea = plinthArea;
    }

    public BigDecimal getFloorArea() {
        return floorArea;
    }

    public void setFloorArea(BigDecimal floorArea) {
        this.floorArea = floorArea;
    }

    public Integer getOrderOfFloor() {
        return orderOfFloor;
    }

    public void setOrderOfFloor(Integer orderOfFloor) {
        this.orderOfFloor = orderOfFloor;
    }

}
