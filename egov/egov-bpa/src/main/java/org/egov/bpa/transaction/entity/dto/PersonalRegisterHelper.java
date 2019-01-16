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

package org.egov.bpa.transaction.entity.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class PersonalRegisterHelper {
    private Long id;
    private String applicationNumber;
    private String applicationType;
    private String permitType;
    private Date dateOfAdmission;
    private String applicantName;
    private String address;
    private String surveyNo;
    private String village;
    private String revenueWard;
    private String electionWard;
    private String natureOfOccupancy;
    private BigDecimal totalFloorArea;
    private Integer noOfFloors;
    private BigDecimal far;
    private String fromWhom;
    private String toWhom;
    private String previousStatus;
    private String currentStatus;
    private Date previousDateAndTime;
    private Date NextDateAndTime;
    private Long userId;
    private String serviceTypeEnum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(String applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    public String getPermitType() {
        return permitType;
    }

    public void setPermitType(String permitType) {
        this.permitType = permitType;
    }

    public Date getDateOfAdmission() {
        return dateOfAdmission;
    }

    public void setDateOfAdmission(Date dateOfAdmission) {
        this.dateOfAdmission = dateOfAdmission;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSurveyNo() {
        return surveyNo;
    }

    public void setSurveyNo(String surveyNo) {
        this.surveyNo = surveyNo;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getRevenueWard() {
        return revenueWard;
    }

    public void setRevenueWard(String revenueWard) {
        this.revenueWard = revenueWard;
    }

    public String getElectionWard() {
        return electionWard;
    }

    public void setElectionWard(String electionWard) {
        this.electionWard = electionWard;
    }

    public String getNatureOfOccupancy() {
        return natureOfOccupancy;
    }

    public void setNatureOfOccupancy(String natureOfOccupancy) {
        this.natureOfOccupancy = natureOfOccupancy;
    }

    public BigDecimal getTotalFloarArea() {
        return totalFloorArea;
    }

    public void setTotalFloarArea(BigDecimal totalFloarArea) {
        this.totalFloorArea = totalFloarArea;
    }

    public BigDecimal getFar() {
        return far;
    }

    public void setFar(BigDecimal far) {
        this.far = far;
    }

    public String getFromWhom() {
        return fromWhom;
    }

    public void setFromWhom(String fromWhom) {
        this.fromWhom = fromWhom;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public Date getPreviousDateAndTime() {
        return previousDateAndTime;
    }

    public void setPreviousDateAndTime(Date previousDateAndTime) {
        this.previousDateAndTime = previousDateAndTime;
    }

    public Integer getNoOfFloors() {
        return noOfFloors;
    }

    public void setNoOfFloors(Integer noOfFloors) {
        this.noOfFloors = noOfFloors;
    }

    public String getToWhom() {
        return toWhom;
    }

    public void setToWhom(String toWhom) {
        this.toWhom = toWhom;
    }

    public String getPreviousStatus() {
        return previousStatus;
    }

    public void setPreviousStatus(String previousStatus) {
        this.previousStatus = previousStatus;
    }

    public Date getNextDateAndTime() {
        return NextDateAndTime;
    }

    public void setNextDateAndTime(Date nextDateAndTime) {
        NextDateAndTime = nextDateAndTime;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getServiceTypeEnum() {
        return serviceTypeEnum;
    }

    public void setServiceTypeEnum(String serviceTypeEnum) {
        this.serviceTypeEnum = serviceTypeEnum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonalRegisterHelper)) return false;
        PersonalRegisterHelper that = (PersonalRegisterHelper) o;
        return Objects.equals(getId(), that.getId()) &&
               Objects.equals(getApplicationNumber(), that.getApplicationNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getApplicationNumber());
    }
}
