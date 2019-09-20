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
package org.egov.bpa.master.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Table(name = "egbpa_master_nocconfiguration")
@SequenceGenerator(name = NocConfiguration.SEQ_NOCCONFIG, sequenceName = NocConfiguration.SEQ_NOCCONFIG, allocationSize = 1)
public class NocConfiguration {

	public static final String SEQ_NOCCONFIG = "SEQ_EGBPA_MSTR_NOCCONFIG";
	@Id
	@GeneratedValue(generator = SEQ_NOCCONFIG, strategy = GenerationType.SEQUENCE)
	private Long id;

	@SafeHtml
	@NotNull
	@Length(min = 1, max = 20)
	@Column(name = "department")
	private String department;

	@SafeHtml
	@NotNull
	@Length(min = 1, max = 25)
	@Column(name = "applicationType")
	private String applicationType;

	@NotNull
	@Length(min = 1, max = 20)
	@Column(name = "integrationtype")
	private String integrationType;

	@SafeHtml
	@NotNull
	@Length(min = 1, max = 20)
	@Column(name = "integrationinitiation")
	private String integrationInitiation;

	@PositiveOrZero
	@NotNull
	@Column(name = "sla")
	private Long sla;

	@NotNull
	@Column(name = "isdeemedapproval")
	private Boolean isDeemedApproval;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIntegrationType() {
		return integrationType;
	}

	public void setIntegrationType(String integrationType) {
		this.integrationType = integrationType;
	}

	public String getIntegrationInitiation() {
		return integrationInitiation;
	}

	public void setIntegrationInitiation(String integrationInitiation) {
		this.integrationInitiation = integrationInitiation;
	}

	public Long getSla() {
		return sla;
	}

	public void setSla(Long sla) {
		this.sla = sla;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}

	public Boolean getIsDeemedApproval() {
		return isDeemedApproval;
	}

	public void setIsDeemedApproval(Boolean isDeemedApproval) {
		this.isDeemedApproval = isDeemedApproval;
	}

}
