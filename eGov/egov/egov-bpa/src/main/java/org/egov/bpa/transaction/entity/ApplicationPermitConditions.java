/*
 * eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 * accountability and the service delivery of the government  organizations.
 *
 *  Copyright (C) <2017>  eGovernments Foundation
 *
 *  The updated version of eGov suite of products as by eGovernments Foundation
 *  is available at http://www.egovernments.org
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see http://www.gnu.org/licenses/ or
 *  http://www.gnu.org/licenses/gpl.html .
 *
 *  In addition to the terms of the GPL license to be adhered to in using this
 *  program, the following additional terms are to be complied with:
 *
 *      1) All versions of this program, verbatim or modified must carry this
 *         Legal Notice.
 *      Further, all user interfaces, including but not limited to citizen facing interfaces,
 *         Urban Local Bodies interfaces, dashboards, mobile applications, of the program and any
 *         derived works should carry eGovernments Foundation logo on the top right corner.
 *
 *      For the logo, please refer http://egovernments.org/html/logo/egov_logo.png.
 *      For any further queries on attribution, including queries on brand guidelines,
 *         please contact contact@egovernments.org
 *
 *      2) Any misrepresentation of the origin of the material is prohibited. It
 *         is required that all modified versions of this material be marked in
 *         reasonable ways as different from the original version.
 *
 *      3) This license does not grant any rights to any user of the program
 *         with regards to rights under trademark law for use of the trade names
 *         or trademarks of eGovernments Foundation.
 *
 *  In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */
package org.egov.bpa.transaction.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.egov.bpa.master.entity.PermitConditions;
import org.egov.bpa.transaction.entity.enums.PermitConditionType;

@Entity
@Table(name = "egbpa_application_permit_conditions")
@SequenceGenerator(name = ApplicationPermitConditions.SEQ_APPLICATION_PERMIT_CONDITIONS, sequenceName = ApplicationPermitConditions.SEQ_APPLICATION_PERMIT_CONDITIONS, allocationSize = 1)
public class ApplicationPermitConditions implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String SEQ_APPLICATION_PERMIT_CONDITIONS = "seq_egbpa_application_permit_conditions";

    @Id
    @GeneratedValue(generator = SEQ_APPLICATION_PERMIT_CONDITIONS, strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "application", nullable = false)
    private BpaApplication application;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "permitcondition", nullable = false)
    private PermitConditions permitCondition;
    private Date permitConditiondDate;
    private String permitConditionNumber;
    private Integer orderNumber;
    @Enumerated(EnumType.STRING)
    private PermitConditionType permitConditionType;
    private boolean isRequired;
    private String additionalPermitCondition;

    public BpaApplication getApplication() {
        return application;
    }

    public void setApplication(BpaApplication application) {
        this.application = application;
    }

    public PermitConditions getPermitCondition() {
        return permitCondition;
    }

    public void setPermitCondition(PermitConditions permitCondition) {
        this.permitCondition = permitCondition;
    }

    public Date getPermitConditiondDate() {
        return permitConditiondDate;
    }

    public void setPermitConditiondDate(Date permitConditiondDate) {
        this.permitConditiondDate = permitConditiondDate;
    }

    public String getPermitConditionNumber() {
        return permitConditionNumber;
    }

    public void setPermitConditionNumber(String permitConditionNumber) {
        this.permitConditionNumber = permitConditionNumber;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public PermitConditionType getPermitConditionType() {
        return permitConditionType;
    }

    public void setPermitConditionType(PermitConditionType permitConditionType) {
        this.permitConditionType = permitConditionType;
    }

    public boolean isRequired() {
        return isRequired;
    }

    public void setRequired(boolean isRequired) {
        this.isRequired = isRequired;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdditionalPermitCondition() {
        return additionalPermitCondition;
    }

    public void setAdditionalPermitCondition(String additionalPermitCondition) {
        this.additionalPermitCondition = additionalPermitCondition;
    }

}