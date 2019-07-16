/*
 * eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 * accountability and the service delivery of the government  organizations.
 *
 *  Copyright (C) <2019>  eGovernments Foundation
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

package org.egov.bpa.entity.es;

import static org.egov.infra.utils.ApplicationConstant.DEFAULT_TIMEZONE;
import static org.egov.infra.utils.ApplicationConstant.ES_DATE_FORMAT;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author vinoth
 *
 */
@Document(indexName = "permitrenewal", type = "permitrenewal")
public class PermitRenewalIndex {

    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String id;

    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String ulbName;

    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String districtName;

    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String regionName;

    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String ulbGrade;

    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String ulbCode;

    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String permitApplication;

    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String applicationNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ES_DATE_FORMAT, timezone = DEFAULT_TIMEZONE)
    @Field(type = FieldType.Date, format = DateFormat.date_optional_time, pattern = ES_DATE_FORMAT)
    private Date applicationDate;

    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String renewalNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ES_DATE_FORMAT, timezone = DEFAULT_TIMEZONE)
    @Field(type = FieldType.Date, format = DateFormat.date_optional_time, pattern = ES_DATE_FORMAT)
    private Date renewalApprovalDate;

    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String status;

    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String constructionStatus;

    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String constructionStage;

    @Field(type = FieldType.Double)
    private BigDecimal permitRenewalFee;

    @Field(type = FieldType.Integer)
    private Integer noOfDaysToProcess;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUlbName() {
        return ulbName;
    }

    public void setUlbName(String ulbName) {
        this.ulbName = ulbName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getUlbGrade() {
        return ulbGrade;
    }

    public void setUlbGrade(String ulbGrade) {
        this.ulbGrade = ulbGrade;
    }

    public String getUlbCode() {
        return ulbCode;
    }

    public void setUlbCode(String ulbCode) {
        this.ulbCode = ulbCode;
    }

    public String getPermitApplication() {
        return permitApplication;
    }

    public void setPermitApplication(String permitApplication) {
        this.permitApplication = permitApplication;
    }

    public String getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(String applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getRenewalNumber() {
        return renewalNumber;
    }

    public void setRenewalNumber(String renewalNumber) {
        this.renewalNumber = renewalNumber;
    }

    public Date getRenewalApprovalDate() {
        return renewalApprovalDate;
    }

    public void setRenewalApprovalDate(Date renewalApprovalDate) {
        this.renewalApprovalDate = renewalApprovalDate;
    }

    public String getConstructionStatus() {
        return constructionStatus;
    }

    public void setConstructionStatus(String constructionStatus) {
        this.constructionStatus = constructionStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getConstructionStage() {
        return constructionStage;
    }

    public void setConstructionStage(String constructionStage) {
        this.constructionStage = constructionStage;
    }

    public BigDecimal getPermitRenewalFee() {
        return permitRenewalFee;
    }

    public void setPermitRenewalFee(BigDecimal permitRenewalFee) {
        this.permitRenewalFee = permitRenewalFee;
    }

    public Integer getNoOfDaysToProcess() {
        return noOfDaysToProcess;
    }

    public void setNoOfDaysToProcess(Integer noOfDaysToProcess) {
        this.noOfDaysToProcess = noOfDaysToProcess;
    }

}
