/*
 * eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 * accountability and the service delivery of the government  organizations.
 *
 *  Copyright (C) <2018>  eGovernments Foundation
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
package org.egov.bpa.transaction.entity.common;

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

import org.egov.bpa.master.entity.ChecklistServiceTypeMapping;
import org.egov.bpa.transaction.entity.enums.ChecklistValues;
import org.egov.bpa.transaction.entity.enums.ScrutinyChecklistType;
import org.egov.infra.persistence.entity.AbstractAuditable;

@Entity
@Table(name = "egbpa_plan_scrutiny_checklist_common")
@SequenceGenerator(name = PlanScrutinyChecklistCommon.SEQ_PLAN_SCRUTINY, sequenceName = PlanScrutinyChecklistCommon.SEQ_PLAN_SCRUTINY, allocationSize = 1)
public class PlanScrutinyChecklistCommon extends AbstractAuditable {

    protected static final String SEQ_PLAN_SCRUTINY = "seq_egbpa_plan_scrutiny_checklist_common";
    private static final long serialVersionUID = -4606373944456639905L;

    @Id
    @GeneratedValue(generator = SEQ_PLAN_SCRUTINY, strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "inspection", nullable = false)
    private InspectionCommon inspection;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "serviceChecklist", nullable = false)
    private ChecklistServiceTypeMapping serviceChecklist;

    @Enumerated(EnumType.STRING)
    private ChecklistValues scrutinyValue;
    
    @Enumerated(EnumType.STRING)
    private ScrutinyChecklistType scrutinyChecklistType;

    private String remarks;

    private Integer orderNumber;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public InspectionCommon getInspection() {
        return inspection;
    }

    public void setInspection(InspectionCommon inspection) {
        this.inspection = inspection;
    }

    public ChecklistServiceTypeMapping getServiceChecklist() {
        return serviceChecklist;
    }

    public void setServiceChecklist(ChecklistServiceTypeMapping serviceChecklist) {
        this.serviceChecklist = serviceChecklist;
    }

    public ChecklistValues getScrutinyValue() {
        return scrutinyValue;
    }

    public void setScrutinyValue(ChecklistValues scrutinyValue) {
        this.scrutinyValue = scrutinyValue;
    }

    public ScrutinyChecklistType getScrutinyChecklistType() {
        return scrutinyChecklistType;
    }

    public void setScrutinyChecklistType(ScrutinyChecklistType scrutinyChecklistType) {
        this.scrutinyChecklistType = scrutinyChecklistType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

}