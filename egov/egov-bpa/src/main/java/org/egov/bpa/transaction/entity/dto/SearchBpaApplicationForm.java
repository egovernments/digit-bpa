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
package org.egov.bpa.transaction.entity.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import javax.validation.constraints.PositiveOrZero;

import org.egov.bpa.master.entity.PermitRevocation;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.OwnershipTransfer;
import org.egov.bpa.transaction.entity.PermitRenewal;
import org.egov.bpa.transaction.entity.SiteDetail;
import org.egov.bpa.transaction.entity.SlotDetail;
import org.egov.bpa.transaction.entity.oc.OccupancyCertificate;
import org.egov.infra.web.support.search.DataTableSearchRequest;
import org.hibernate.validator.constraints.SafeHtml;

public class SearchBpaApplicationForm extends DataTableSearchRequest {
    private Long id;
    @SafeHtml
    private String applicationNumber;
    private Date applicationDate;
    @SafeHtml
    private String applicantType;
    @SafeHtml
    private String serviceType;
    @SafeHtml
    private String occupancy;
    @PositiveOrZero
    private Long occupancyId;
    @PositiveOrZero
    private Long serviceTypeId;
    @SafeHtml
    private String serviceCode;
    @SafeHtml
    private String status;
    @PositiveOrZero
    private Long statusId;
    @SafeHtml
    private String planPermissionNumber;
    @PositiveOrZero
    private BigDecimal admissionfeeAmount;
    @SafeHtml
    private String applicantName;
    @SafeHtml
    private String stakeHolderName;
    @SafeHtml
    private String currentOwner;
    @SafeHtml
    private String pendingAction;
    @SafeHtml
    private String ward;
    @PositiveOrZero
    private Long wardId;
    @SafeHtml
    private String electionWard;
    @PositiveOrZero
    private Long electionWardId;
    @SafeHtml
    private String zone;
    @PositiveOrZero
    private Long zoneId;
    private boolean isFeeCollected;
    private Date fromDate;
    private Date toDate;
    @SafeHtml
    private String address;
    @SafeHtml
    private String locality;
    @SafeHtml
    private String reSurveyNumber;
    @SafeHtml
    private String serviceTypeEnum;
    private Date appointmentDate;
    @SafeHtml
    private String appointmentTime;
    private Boolean isRescheduledByEmployee;
    private Boolean isOnePermitApplication;
    @SafeHtml
    private String applicationType;
    @SafeHtml
    private String scheduleType;
    @SafeHtml
    private String failureRemarks;
    private Long userId;
    @PositiveOrZero
    private BigDecimal fromPlotArea;
    @PositiveOrZero
    private BigDecimal toPlotArea;
    @PositiveOrZero
    private BigDecimal fromBuiltUpArea;
    @PositiveOrZero
    private BigDecimal toBuiltUpArea;
    @PositiveOrZero
    private Long applicationTypeId;
    @PositiveOrZero
    private Long adminBoundary;
    @PositiveOrZero
    private Long revenueBoundary;
    @PositiveOrZero
    private Long locationBoundary;
    @SafeHtml
    private String revocationNumber;
    private Date planPermissionDate;
    @SafeHtml
    private String occupancyCertificateNumber;
    private Boolean wfEnded;
    private Boolean feeCollector;

    public String getRevocationNumber() {
        return revocationNumber;
    }

    public void setRevocationNumber(String revocationNumber) {
        this.revocationNumber = revocationNumber;
    }

    public SearchBpaApplicationForm() {
        // for form binding
    }

    public SearchBpaApplicationForm(BpaApplication application, String currentOwner, String pendingAction,
            Boolean isFeeCollected) {
        setId(application.getId());
        setApplicationNumber(application.getApplicationNumber());
        setOnePermitApplication(application.getIsOneDayPermitApplication());
        setApplicantName(application.getApplicantName());
        setApplicationDate(application.getApplicationDate());
        if (!application.getSlotApplications().isEmpty()) {
            SlotDetail slotDetail = application.getSlotApplications().get(0).getSlotDetail();
            setAppointmentTime(slotDetail.getAppointmentTime());
            setAppointmentDate(slotDetail.getSlot().getAppointmentDate());
        }
        setAddress(application.getOwner().getAddress());
        setRescheduledByEmployee(application.getIsRescheduledByEmployee());
        setApplicationType(application.getApplicationType() != null ? application.getApplicationType().getName() : "");
        setOccupancy(application.getOccupanciesName());
        setServiceType(application.getServiceType().getDescription());
        setServiceCode(application.getServiceType().getCode());
        setPlanPermissionNumber(application.getPlanPermissionNumber());
        setPlanPermissionDate(application.getPlanPermissionDate());
        setStakeHolderName(application.getStakeHolder().get(0).getStakeHolder().getName());
        if (!application.getSiteDetail().isEmpty()) {
            SiteDetail site = application.getSiteDetail().get(0);
            setReSurveyNumber(site.getReSurveyNumber());
            setZone(site.getAdminBoundary() == null ? "" : site.getAdminBoundary().getParent().getName());
            setWard(site.getAdminBoundary() == null ? "" : site.getAdminBoundary().getName());
            setElectionWard(site.getElectionBoundary() == null ? "" : site.getElectionBoundary().getName());
            setLocality(site.getLocationBoundary() == null ? "" : site.getLocationBoundary().getName());
        }
        setStatus(application.getStatus().getCode());
        setCurrentOwner(currentOwner);
        setPendingAction(pendingAction);
        setFeeCollected(isFeeCollected);
        if (!application.getPermitRevocation().isEmpty()) {
            Optional<PermitRevocation> revoke = application.getPermitRevocation().stream()
                    .reduce((revoke1, revoke2) -> revoke2);
            setRevocationNumber(revoke.isPresent() ? revoke.get().getRevocationNumber() : "");
        }
    }

    public SearchBpaApplicationForm(OccupancyCertificate occupancyCertificate, String currentOwner,
            String pendingAction, Boolean isFeeCollected) {
        setId(occupancyCertificate.getId());
        setApplicationNumber(occupancyCertificate.getApplicationNumber());
        setApplicantName(occupancyCertificate.getParent().getOwner().getName());
        setApplicationDate(occupancyCertificate.getApplicationDate());
        if (!occupancyCertificate.getOcSlots().isEmpty()) {
            SlotDetail slotDetail = occupancyCertificate.getOcSlots().get(0).getSlotDetail();
            setAppointmentTime(slotDetail.getAppointmentTime());
            setAppointmentDate(slotDetail.getSlot().getAppointmentDate());
        }
        setAddress(occupancyCertificate.getParent().getOwner().getAddress());
        setRescheduledByEmployee(occupancyCertificate.getRescheduledByEmployee());
        setApplicationType(occupancyCertificate.getParent().getApplicantType());
        setOccupancy(occupancyCertificate.getParent().getOccupanciesName());
        setServiceType(occupancyCertificate.getParent().getServiceType().getDescription());
        setServiceCode(occupancyCertificate.getParent().getServiceType().getCode());
        setPlanPermissionNumber(occupancyCertificate.getParent().getPlanPermissionNumber());
        setStakeHolderName(occupancyCertificate.getParent().getStakeHolder().get(0).getStakeHolder().getName());
        setOccupancyCertificateNumber(occupancyCertificate.getOccupancyCertificateNumber());
        if (!occupancyCertificate.getParent().getSiteDetail().isEmpty()) {
            SiteDetail site = occupancyCertificate.getParent().getSiteDetail().get(0);
            setReSurveyNumber(site.getReSurveyNumber());
            setZone(site.getAdminBoundary() == null ? "" : site.getAdminBoundary().getParent().getName());
            setWard(site.getAdminBoundary() == null ? "" : site.getAdminBoundary().getName());
            setElectionWard(site.getElectionBoundary() == null ? "" : site.getElectionBoundary().getName());
            setLocality(site.getLocationBoundary() == null ? "" : site.getLocationBoundary().getName());
        }
        setStatus(occupancyCertificate.getStatus().getCode());
        setCurrentOwner(currentOwner);
        setPendingAction(pendingAction);
        setFeeCollected(isFeeCollected);
    }

    public SearchBpaApplicationForm(BpaApplication application, OccupancyCertificate occupancyCertificate) {
        setApplicationNumber(application.getApplicationNumber());
        setApplicantName(application.getOwner().getName());
        setPlanPermissionNumber(application.getPlanPermissionNumber());
        setPlanPermissionDate(application.getPlanPermissionDate());
        setOccupancy(application.getOccupanciesName());
        setStatus(application.getStatus().getCode());
        setServiceType(application.getServiceType().getDescription());
        setWfEnded(application.getState().isEnded());
        if (occupancyCertificate != null)
            setOccupancyCertificateNumber(occupancyCertificate.getApplicationNumber());
    }

    public SearchBpaApplicationForm(PermitRenewal renewal, String currentOwner, String pendingAction,
            Boolean feeCollector, Boolean isFeeCollected) {
        setId(renewal.getId());
        setApplicationNumber(renewal.getApplicationNumber());
        setApplicantName(renewal.getParent().getApplicantName());
        setApplicationDate(renewal.getApplicationDate());
        setAddress(renewal.getParent().getOwner().getAddress());
        setApplicationType(
                renewal.getParent().getApplicationType() != null ? renewal.getParent().getApplicationType().getName()
                        : "");
        setOccupancy(renewal.getParent().getOccupanciesName());
        setServiceType(renewal.getParent().getServiceType().getDescription());
        setServiceCode(renewal.getParent().getServiceType().getCode());
        setPlanPermissionNumber(renewal.getParent().getPlanPermissionNumber());
        setPlanPermissionDate(renewal.getParent().getPlanPermissionDate());
        setStakeHolderName(renewal.getParent().getStakeHolder().get(0).getStakeHolder().getName());
        if (!renewal.getParent().getSiteDetail().isEmpty()) {
            SiteDetail site = renewal.getParent().getSiteDetail().get(0);
            setReSurveyNumber(site.getReSurveyNumber());
            setZone(site.getAdminBoundary() == null ? "" : site.getAdminBoundary().getParent().getName());
            setWard(site.getAdminBoundary() == null ? "" : site.getAdminBoundary().getName());
            setElectionWard(site.getElectionBoundary() == null ? "" : site.getElectionBoundary().getName());
            setLocality(site.getLocationBoundary() == null ? "" : site.getLocationBoundary().getName());
        }
        setStatus(renewal.getStatus().getCode());
        setCurrentOwner(currentOwner);
        setPendingAction(pendingAction);
        setFeeCollected(isFeeCollected);
        setFeeCollector(feeCollector);
    }
    
    public SearchBpaApplicationForm(OwnershipTransfer ownershipTransfer, String currentOwner, String pendingAction, Boolean feeCollector,
            Boolean isFeeCollected) {
        setId(ownershipTransfer.getId());
        setApplicationNumber(ownershipTransfer.getApplicationNumber());
        setApplicantName(ownershipTransfer.getApplicantName());
        setApplicationDate(ownershipTransfer.getApplicationDate());
        setAddress(ownershipTransfer.getOwner().getAddress());
        setApplicationType(ownershipTransfer.getApplication().getApplicationType() != null ? ownershipTransfer.getApplication().getApplicationType().getName() : "");
        setOccupancy(ownershipTransfer.getApplication().getOccupanciesName());
        setServiceType(ownershipTransfer.getApplication().getServiceType().getDescription());
        setServiceCode(ownershipTransfer.getApplication().getServiceType().getCode());
        setPlanPermissionNumber(ownershipTransfer.getApplication().getPlanPermissionNumber());
        setPlanPermissionDate(ownershipTransfer.getApplication().getPlanPermissionDate());
        setStakeHolderName(ownershipTransfer.getApplication().getStakeHolder().get(0).getStakeHolder().getName());
        if (!ownershipTransfer.getApplication().getSiteDetail().isEmpty()) {
            SiteDetail site = ownershipTransfer.getApplication().getSiteDetail().get(0);
            setReSurveyNumber(site.getReSurveyNumber());
            setZone(site.getAdminBoundary() == null ? "" : site.getAdminBoundary().getParent().getName());
            setWard(site.getAdminBoundary() == null ? "" : site.getAdminBoundary().getName());
            setElectionWard(site.getElectionBoundary() == null ? "" : site.getElectionBoundary().getName());
            setLocality(site.getLocationBoundary() == null ? "" : site.getLocationBoundary().getName());
        }
        setStatus(ownershipTransfer.getStatus().getCode());
        setCurrentOwner(currentOwner);
        setPendingAction(pendingAction);
        setFeeCollected(isFeeCollected);
        setFeeCollector(feeCollector);
    }

    public Boolean getIsRescheduledByEmployee() {
        return isRescheduledByEmployee;
    }

    public void setIsRescheduledByEmployee(Boolean isRescheduledByEmployee) {
        this.isRescheduledByEmployee = isRescheduledByEmployee;
    }

    public Boolean getIsOnePermitApplication() {
        return isOnePermitApplication;
    }

    public void setIsOnePermitApplication(Boolean isOnePermitApplication) {
        this.isOnePermitApplication = isOnePermitApplication;
    }

    public String getFailureRemarks() {
        return failureRemarks;
    }

    public void setFailureRemarks(String failureRemarks) {
        this.failureRemarks = failureRemarks;
    }

    public String getServiceTypeEnum() {
        return serviceTypeEnum;
    }

    public void setServiceTypeEnum(String serviceTypeEnum) {
        this.serviceTypeEnum = serviceTypeEnum;
    }

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

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(String occupancy) {
        this.occupancy = occupancy;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getApplicantType() {
        return applicantType;
    }

    public void setApplicantType(String applicantType) {
        this.applicantType = applicantType;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPlanPermissionNumber() {
        return planPermissionNumber;
    }

    public void setPlanPermissionNumber(String planPermissionNumber) {
        this.planPermissionNumber = planPermissionNumber;
    }

    public BigDecimal getAdmissionfeeAmount() {
        return admissionfeeAmount;
    }

    public void setAdmissionfeeAmount(BigDecimal admissionfeeAmount) {
        this.admissionfeeAmount = admissionfeeAmount;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getStakeHolderName() {
        return stakeHolderName;
    }

    public void setStakeHolderName(String stakeHolderName) {
        this.stakeHolderName = stakeHolderName;
    }

    public String getCurrentOwner() {
        return currentOwner;
    }

    public void setCurrentOwner(String currentOwner) {
        this.currentOwner = currentOwner;
    }

    public String getPendingAction() {
        return pendingAction;
    }

    public void setPendingAction(String pendingAction) {
        this.pendingAction = pendingAction;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getElectionWard() {
        return electionWard;
    }

    public void setElectionWard(String electionWard) {
        this.electionWard = electionWard;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public Long getWardId() {
        return wardId;
    }

    public void setWardId(Long wardId) {
        this.wardId = wardId;
    }

    public Long getElectionWardId() {
        return electionWardId;
    }

    public void setElectionWardId(Long electionWardId) {
        this.electionWardId = electionWardId;
    }

    public Long getZoneId() {
        return zoneId;
    }

    public void setZoneId(Long zoneId) {
        this.zoneId = zoneId;
    }

    public boolean isFeeCollected() {
        return isFeeCollected;
    }

    public void setFeeCollected(boolean isFeeCollected) {
        this.isFeeCollected = isFeeCollected;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public Long getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(Long serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getReSurveyNumber() {
        return reSurveyNumber;
    }

    public void setReSurveyNumber(String reSurveyNumber) {
        this.reSurveyNumber = reSurveyNumber;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public Boolean getRescheduledByEmployee() {
        return isRescheduledByEmployee;
    }

    public void setRescheduledByEmployee(Boolean rescheduledByEmployee) {
        isRescheduledByEmployee = rescheduledByEmployee;
    }

    public Boolean getOnePermitApplication() {
        return isOnePermitApplication;
    }

    public void setOnePermitApplication(Boolean onePermitApplication) {
        isOnePermitApplication = onePermitApplication;
    }

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    public String getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }

    public Long getOccupancyId() {
        return occupancyId;
    }

    public void setOccupancyId(Long occupancyId) {
        this.occupancyId = occupancyId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getFromPlotArea() {
        return fromPlotArea;
    }

    public void setFromPlotArea(BigDecimal fromPlotArea) {
        this.fromPlotArea = fromPlotArea;
    }

    public BigDecimal getToPlotArea() {
        return toPlotArea;
    }

    public void setToPlotArea(BigDecimal toPlotArea) {
        this.toPlotArea = toPlotArea;
    }

    public BigDecimal getFromBuiltUpArea() {
        return fromBuiltUpArea;
    }

    public void setFromBuiltUpArea(BigDecimal fromBuiltUpArea) {
        this.fromBuiltUpArea = fromBuiltUpArea;
    }

    public BigDecimal getToBuiltUpArea() {
        return toBuiltUpArea;
    }

    public void setToBuiltUpArea(BigDecimal toBuiltUpArea) {
        this.toBuiltUpArea = toBuiltUpArea;
    }

    public Long getApplicationTypeId() {
        return applicationTypeId;
    }

    public void setApplicationTypeId(Long applicationTypeId) {
        this.applicationTypeId = applicationTypeId;
    }

    public Long getAdminBoundary() {
        return adminBoundary;
    }

    public void setAdminBoundary(Long adminBoundary) {
        this.adminBoundary = adminBoundary;
    }

    public Long getRevenueBoundary() {
        return revenueBoundary;
    }

    public void setRevenueBoundary(Long revenueBoundary) {
        this.revenueBoundary = revenueBoundary;
    }

    public Long getLocationBoundary() {
        return locationBoundary;
    }

    public void setLocationBoundary(Long locationBoundary) {
        this.locationBoundary = locationBoundary;
    }

    public Date getPlanPermissionDate() {
        return planPermissionDate;
    }

    public void setPlanPermissionDate(Date planPermissionDate) {
        this.planPermissionDate = planPermissionDate;
    }

    public String getOccupancyCertificateNumber() {
        return occupancyCertificateNumber;
    }

    public void setOccupancyCertificateNumber(String occupancyCertificateNumber) {
        this.occupancyCertificateNumber = occupancyCertificateNumber;
    }

    public Boolean getWfEnded() {
        return wfEnded;
    }

    public void setWfEnded(Boolean wfEnded) {
        this.wfEnded = wfEnded;
    }

    public Boolean getFeeCollector() {
        return feeCollector;
    }

    public void setFeeCollector(Boolean feeCollector) {
        this.feeCollector = feeCollector;
    }

}