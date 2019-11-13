/*
 *    eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) 2017  eGovernments Foundation
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
package org.egov.bpa.contract;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "ApplicationData")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ApplicationData")
public class ApplicationData {
	@XmlElement(name = "AUTHORITY")
    protected String authority;
    @XmlElement(name = "UNIQUEID")
    protected String uniqueId;
    @XmlElement(name = "APPLICATIONDATE")
    protected String applicationDate;
    @XmlElement(name = "APPLICANTNAME")
    protected String applicantName;
    @XmlElement(name = "APPLICANTADDRESS")
    protected String applicantAddress;
    @XmlElement(name = "APPLICANTNO")
    protected String applicantNo;
    @XmlElement(name = "APPLICANTEMAIL")
    protected String applicantEmail;
    @XmlElement(name = "APPLICATIONNO")
    protected String applicationNumber;
    @XmlElement(name = "OWNERNAME")
    protected String ownerName;
    @XmlElement(name = "OWNERADDRESS")
    protected String ownerAddress;
    @XmlElement(name = "STRUCTURETYPE")
    protected String structureType;
    @XmlElement(name = "STRUCTUREPURPOSE")
    protected String structurePurpose;
    @XmlElement(name = "SITEADDRESS")
    protected String siteAddress;
    @XmlElement(name = "SITECITY")
    protected String siteCity;
    @XmlElement(name = "SITESTATE")
    protected String siteState;
    @XmlElement(name = "PLOTSIZE")
    protected String plotSize;
    @XmlElement(name = "ISINAIRPORTPREMISES")
    protected String inAirportPremises;
    @XmlElement(name = "PERMISSIONTAKEN")
    protected String permissionTaken;
    
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	public String getApplicationDate() {
		return applicationDate;
	}
	public void setApplicationDate(String applicationDate) {
		this.applicationDate = applicationDate;
	}
	public String getApplicantName() {
		return applicantName;
	}
	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}
	public String getApplicantAddress() {
		return applicantAddress;
	}
	public void setApplicantAddress(String applicantAddress) {
		this.applicantAddress = applicantAddress;
	}
	public String getApplicantNo() {
		return applicantNo;
	}
	public void setApplicantNo(String applicantNo) {
		this.applicantNo = applicantNo;
	}
	public String getApplicantEmail() {
		return applicantEmail;
	}
	public void setApplicantEmail(String applicantEmail) {
		this.applicantEmail = applicantEmail;
	}
	public String getApplicationNumber() {
		return applicationNumber;
	}
	public void setApplicationNumber(String applicationNumber) {
		this.applicationNumber = applicationNumber;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getOwnerAddress() {
		return ownerAddress;
	}
	public void setOwnerAddress(String ownerAddress) {
		this.ownerAddress = ownerAddress;
	}
	public String getStructureType() {
		return structureType;
	}
	public void setStructureType(String structureType) {
		this.structureType = structureType;
	}
	public String getStructurePurpose() {
		return structurePurpose;
	}
	public void setStructurePurpose(String structurePurpose) {
		this.structurePurpose = structurePurpose;
	}
	public String getSiteAddress() {
		return siteAddress;
	}
	public void setSiteAddress(String siteAddress) {
		this.siteAddress = siteAddress;
	}
	public String getSiteCity() {
		return siteCity;
	}
	public void setSiteCity(String siteCity) {
		this.siteCity = siteCity;
	}
	public String getSiteState() {
		return siteState;
	}
	public void setSiteState(String siteState) {
		this.siteState = siteState;
	}
	public String getPlotSize() {
		return plotSize;
	}
	public void setPlotSize(String plotSize) {
		this.plotSize = plotSize;
	}
	public String getInAirportPremises() {
		return inAirportPremises;
	}
	public void setInAirportPremises(String inAirportPremises) {
		this.inAirportPremises = inAirportPremises;
	}
	public String getPermissionTaken() {
		return permissionTaken;
	}
	public void setPermissionTaken(String permissionTaken) {
		this.permissionTaken = permissionTaken;
	}
}