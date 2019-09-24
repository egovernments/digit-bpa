package org.egov.bpa.transaction.entity.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import org.hibernate.validator.constraints.SafeHtml;

public class BpaRegisterReportHelper {
    private Long id;
    @SafeHtml
    private String applicationNumber;
    @SafeHtml
    private String applicationType;
    @SafeHtml
    private String permitType;
    private Date dateOfAdmission;
    @SafeHtml
    private String applicantName;
    @SafeHtml
    private String address;
    @SafeHtml
    private String surveyNumber;
    @SafeHtml
    private String village;
    @SafeHtml
    private String revenueWard;
    @SafeHtml
    private String electionWard;
    @SafeHtml
    private String natureOfOccupancy;
    @SafeHtml
    private BigDecimal totalFloorArea = BigDecimal.ZERO;
    private Integer numberOfFloors;
    private BigDecimal far;
    private BigDecimal applicationFee;
    private Date docVerificationDate;
    private Date fieldVerificationDate;
    @SafeHtml
    private String nocDetails;
    @SafeHtml
    private String buildingPermitNo;
    private BigDecimal permitFee;
    private BigDecimal additionalFee;
    private BigDecimal otherFee;
    @SafeHtml
    private String rejectionReason;
    private Date finalApprovalDate;
    @SafeHtml
    private String apprvd_rejected_by;
    @SafeHtml
    private String serviceType;
    private BigDecimal shelterFund;
    private BigDecimal labourcess;
    private BigDecimal developmentPermitFees;

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

    public String getSurveyNumber() {
        return surveyNumber;
    }

    public void setSurveyNumber(String surveyNumber) {
        this.surveyNumber = surveyNumber;
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

    public BigDecimal getTotalFloorArea() {
        return totalFloorArea;
    }

    public void setTotalFloorArea(BigDecimal totalFloorArea) {
        this.totalFloorArea = totalFloorArea;
    }

    public Integer getNumberOfFloors() {
        return numberOfFloors;
    }

    public void setNumberOfFloors(Integer numberOfFloors) {
        this.numberOfFloors = numberOfFloors;
    }

    public BigDecimal getFar() {
        return far;
    }

    public void setFar(BigDecimal far) {
        this.far = far;
    }

    public BigDecimal getApplicationFee() {
        return applicationFee;
    }

    public void setApplicationFee(BigDecimal applicationFee) {
        this.applicationFee = applicationFee;
    }

    public Date getDocVerificationDate() {
        return docVerificationDate;
    }

    public void setDocVerificationDate(Date docVerificationDate) {
        this.docVerificationDate = docVerificationDate;
    }

    public Date getFieldVerificationDate() {
        return fieldVerificationDate;
    }

    public void setFieldVerificationDate(Date fieldVerificationDate) {
        this.fieldVerificationDate = fieldVerificationDate;
    }

    public String getNocDetails() {
        return nocDetails;
    }

    public void setNocDetails(String nocDetails) {
        this.nocDetails = nocDetails;
    }

    public String getBuildingPermitNo() {
        return buildingPermitNo;
    }

    public void setBuildingPermitNo(String buildingPermitNo) {
        this.buildingPermitNo = buildingPermitNo;
    }

    public BigDecimal getPermitFee() {
        return permitFee;
    }

    public void setPermitFee(BigDecimal permitFee) {
        this.permitFee = permitFee;
    }

    public BigDecimal getAdditionalFee() {
        return additionalFee;
    }

    public void setAdditionalFee(BigDecimal additionalFee) {
        this.additionalFee = additionalFee;
    }

    public BigDecimal getOtherFee() {
        return otherFee;
    }

    public void setOtherFee(BigDecimal otherFee) {
        this.otherFee = otherFee;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public Date getFinalApprovalDate() {
        return finalApprovalDate;
    }

    public void setFinalApprovalDate(Date finalApprovalDate) {
        this.finalApprovalDate = finalApprovalDate;
    }

    public String getApprvd_rejected_by() {
        return apprvd_rejected_by;
    }

    public void setApprvd_rejected_by(String apprvd_rejected_by) {
        this.apprvd_rejected_by = apprvd_rejected_by;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public BigDecimal getShelterFund() {
        return shelterFund;
    }

    public void setShelterFund(BigDecimal shelterFund) {
        this.shelterFund = shelterFund;
    }

    public BigDecimal getLabourcess() {
        return labourcess;
    }

    public void setLabourcess(BigDecimal labourcess) {
        this.labourcess = labourcess;
    }

    public BigDecimal getDevelopmentPermitFees() {
        return developmentPermitFees;
    }

    public void setDevelopmentPermitFees(BigDecimal developmentPermitFees) {
        this.developmentPermitFees = developmentPermitFees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof BpaRegisterReportHelper))
            return false;
        BpaRegisterReportHelper that = (BpaRegisterReportHelper) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getApplicationNumber(), that.getApplicationNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getApplicationNumber());
    }
}
