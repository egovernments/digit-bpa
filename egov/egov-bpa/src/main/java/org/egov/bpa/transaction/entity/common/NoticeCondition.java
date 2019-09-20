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

package org.egov.bpa.transaction.entity.common;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.egov.bpa.master.entity.ChecklistServiceTypeMapping;
import org.egov.bpa.transaction.entity.enums.ConditionType;
import org.egov.infra.persistence.entity.AbstractAuditable;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Table(name = "egbpa_notice_conditions")
@SequenceGenerator(name = NoticeCondition.SEQ_BPA_NOTICE_CONDITION, sequenceName = NoticeCondition.SEQ_BPA_NOTICE_CONDITION, allocationSize = 1)
public class NoticeCondition extends AbstractAuditable {

	private static final long serialVersionUID = 1L;
	public static final String SEQ_BPA_NOTICE_CONDITION = "SEQ_EGBPA_NOTICE_CONDITION";

	@Id
	@GeneratedValue(generator = SEQ_BPA_NOTICE_CONDITION, strategy = GenerationType.SEQUENCE)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "checklistservicetype", nullable = false)
	private ChecklistServiceTypeMapping checklistServicetype;
	
	private Date conditiondDate;
	
	@SafeHtml
	@Length(min = 1, max = 30)
	private String conditionNumber;
	
	@PositiveOrZero
	private Integer orderNumber;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "type")
	private ConditionType type;
	
	private boolean isRequired;
	
	@SafeHtml
	@Length(min = 1, max = 600)
	private String additionalCondition;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	protected void setId(Long id) {
		this.id = id;
	}

	public ChecklistServiceTypeMapping getChecklistServicetype() {
		return checklistServicetype;
	}

	public void setChecklistServicetype(ChecklistServiceTypeMapping checklistServicetype) {
		this.checklistServicetype = checklistServicetype;
	}

	public Date getConditiondDate() {
		return conditiondDate;
	}

	public void setConditiondDate(Date conditiondDate) {
		this.conditiondDate = conditiondDate;
	}

	public String getConditionNumber() {
		return conditionNumber;
	}

	public void setConditionNumber(String conditionNumber) {
		this.conditionNumber = conditionNumber;
	}

	public Integer getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

	public ConditionType getType() {
		return type;
	}

	public void setType(ConditionType type) {
		this.type = type;
	}

	public boolean isRequired() {
		return isRequired;
	}

	public void setRequired(boolean isRequired) {
		this.isRequired = isRequired;
	}

	public String getAdditionalCondition() {
		return additionalCondition;
	}

	public void setAdditionalCondition(String additionalCondition) {
		this.additionalCondition = additionalCondition;
	}
}