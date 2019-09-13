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

package org.egov.common.entity;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.egov.infra.persistence.entity.AbstractAuditable;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "EG_OCCUPANCY")
@SequenceGenerator(name = Occupancy.SEQ_OCCUPANCY, sequenceName = Occupancy.SEQ_OCCUPANCY, allocationSize = 1)
public class Occupancy extends AbstractAuditable {

    public static final String SEQ_OCCUPANCY = "SEQ_EG_OCCUPANCY";
    private static final long serialVersionUID = -1928622582218032380L;

    @Id
    @GeneratedValue(generator = SEQ_OCCUPANCY, strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent")
    private Occupancy parent;

    @NotNull
    @Length(min = 1, max = 128)
    @Column(name = "code", unique = true)
    private String code;

    @NotNull
    @Length(min = 1, max = 256)
    private String description;

    @Length(min = 1, max = 1024)
    private String additionalDescription;

    private Boolean isactive;

    private BigDecimal occupantDoors;

    private BigDecimal noofOccupancy;

    private BigDecimal occupantLoad;

    private Double permissibleAreaInPercentage;

    private Double numOfTimesAreaPermissible;

    private Double numOfTimesAreaPermWitAddnlFee;

    private Integer orderNumber;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Occupancy getParent() {
        return parent;
    }

    public void setParent(Occupancy parent) {
        this.parent = parent;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdditionalDescription() {
        return additionalDescription;
    }

    public void setAdditionalDescription(String additionalDescription) {
        this.additionalDescription = additionalDescription;
    }

    public Boolean getIsactive() {
        return isactive;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
    }

    public BigDecimal getOccupantDoors() {
        return occupantDoors;
    }

    public void setOccupantDoors(BigDecimal occupantDoors) {
        this.occupantDoors = occupantDoors;
    }

    public BigDecimal getNoofOccupancy() {
        return noofOccupancy;
    }

    public void setNoofOccupancy(BigDecimal noofOccupancy) {
        this.noofOccupancy = noofOccupancy;
    }

    public BigDecimal getOccupantLoad() {
        return occupantLoad;
    }

    public void setOccupantLoad(BigDecimal occupantLoad) {
        this.occupantLoad = occupantLoad;
    }

    public Double getPermissibleAreaInPercentage() {
        return permissibleAreaInPercentage;
    }

    public void setPermissibleAreaInPercentage(Double permissibleAreaInPercentage) {
        this.permissibleAreaInPercentage = permissibleAreaInPercentage;
    }

    public Double getNumOfTimesAreaPermissible() {
        return numOfTimesAreaPermissible;
    }

    public void setNumOfTimesAreaPermissible(Double numOfTimesAreaPermissible) {
        this.numOfTimesAreaPermissible = numOfTimesAreaPermissible;
    }

    public Double getNumOfTimesAreaPermWitAddnlFee() {
        return numOfTimesAreaPermWitAddnlFee;
    }

    public void setNumOfTimesAreaPermWitAddnlFee(Double numOfTimesAreaPermWitAddnlFee) {
        this.numOfTimesAreaPermWitAddnlFee = numOfTimesAreaPermWitAddnlFee;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }
}