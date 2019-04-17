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
package org.egov.bpa.transaction.entity.oc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.BpaStatus;
import org.egov.bpa.transaction.entity.WorkflowBean;
import org.egov.commons.entity.Source;
import org.egov.dcb.bean.Receipt;
import org.egov.demand.model.EgDemand;
import org.egov.infra.utils.DateUtils;
import org.egov.infra.workflow.entity.StateAware;
import org.egov.pims.commons.Position;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "EGBPA_OCCUPANCY_CERTIFICATE")
@SequenceGenerator(name = OccupancyCertificate.SEQ_EGBPA_OCCUPANCY_CERTIFICATE, sequenceName = OccupancyCertificate.SEQ_EGBPA_OCCUPANCY_CERTIFICATE, allocationSize = 1)
public class OccupancyCertificate extends StateAware<Position> {

    public static final String SEQ_EGBPA_OCCUPANCY_CERTIFICATE = "SEQ_EGBPA_OCCUPANCY_CERTIFICATE";
    public static final String ORDER_BY_ID_ASC = "id ASC";
    private static final long serialVersionUID = 2655013364406241434L;

    @Id
    @GeneratedValue(generator = SEQ_EGBPA_OCCUPANCY_CERTIFICATE, strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "parent")
    private BpaApplication parent;

    @Length(min = 1, max = 128)
    private String applicationNumber;

    @Length(min = 1, max = 128)
    private String occupancyCertificateNumber;

    @Length(min = 1, max = 20)
    private String eDcrNumber;

    @Temporal(value = TemporalType.DATE)
    private Date applicationDate;

    @Temporal(value = TemporalType.DATE)
    private Date approvalDate;

    @Enumerated(EnumType.STRING)
    private Source source;

    @Length(min = 1, max = 128)
    private String applicationType;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "status")
    private BpaStatus status;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "demand")
    private EgDemand demand;

    @Temporal(value = TemporalType.DATE)
    private Date commencedDate;

    @Temporal(value = TemporalType.DATE)
    private Date completionDate;

    @Temporal(value = TemporalType.DATE)
    private Date workCompletionDueDate;

    private Boolean citizenAccepted = false;

    private Boolean architectAccepted = false;

    private Boolean isSentToPreviousOwner = false;

    private Boolean isRescheduledByCitizen = false;

    private Boolean isRescheduledByEmployee = false;

    private Boolean authorizedToSubmitPlan = false;

    private Boolean failureInScheduler = false;

    private String schedulerFailedRemarks;

    private BigDecimal admissionfeeAmount;

    @Length(min = 1, max = 5000)
    private String townSurveyorRemarks;

    private Boolean isTownSurveyorInspectionRequire = false;

    private Boolean isLPRequestInitiated;

    @NotNull
    private BigDecimal extentInSqmts = BigDecimal.ZERO;

    @OneToMany(mappedBy = "oc", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("buildingNumber ASC")
    private List<OCBuilding> buildings = new ArrayList<>();

    @OneToMany(mappedBy = "oc", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OccupancyFee> occupancyFee = new ArrayList<>();

    @OneToMany(mappedBy = "oc", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("buildingNumber ASC")
    private List<OCExistingBuilding> existingBuildings = new ArrayList<>();

    @OneToMany(mappedBy = "oc", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OCNotice> ocNotices = new ArrayList<>(0);

    @OneToMany(mappedBy = "oc", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy(ORDER_BY_ID_ASC)
    private List<OCDocuments> documents = new ArrayList<>();

    @OneToMany(mappedBy = "oc", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy(ORDER_BY_ID_ASC)
    private List<OCDcrDocuments> dcrDocuments = new ArrayList<>();

    @OneToMany(mappedBy = "oc", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy(ORDER_BY_ID_ASC)
    private List<OCNocDocuments> nocDocuments = new ArrayList<>();

    @OneToMany(mappedBy = "oc", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("id DESC ")
    private List<OCInspection> inspections = new ArrayList<>();

    @OneToMany(mappedBy = "oc", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy(ORDER_BY_ID_ASC)
    private List<OCDocumentScrutiny> documentScrutinies = new ArrayList<>();

    @OneToMany(mappedBy = "oc", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy(ORDER_BY_ID_ASC)
    private List<OCSlot> ocSlots = new ArrayList<>();

    @OneToMany(mappedBy = "oc", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy(ORDER_BY_ID_ASC)
    private List<OCAppointmentSchedule> appointmentSchedules = new ArrayList<>();

    @OneToMany(mappedBy = "oc", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OCNoticeConditions> rejectionReasons = new ArrayList<>(0);

    @OrderBy(ORDER_BY_ID_ASC)
    @OneToMany(mappedBy = "oc", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OCNoticeConditions> additionalNoticeConditions = new ArrayList<>(0);

    private transient WorkflowBean workflowBean;
    private transient Set<Receipt> receipts = new HashSet<>();
    private transient MultipartFile[] files;
    private transient Long approvalDepartment;
    private transient Long zoneId;
    private transient Long wardId;
    private transient String approvalComent;
    private transient List<OCBuilding> buildingDetailFromEdcr = new ArrayList<>();
    private transient List<OCExistingBuilding> existingBldgDetailFromEdcr = new ArrayList<>();
    private transient List<OCNoticeConditions> rejectionReasonsTemp = new ArrayList<>(0);
    private transient List<OCNoticeConditions> additionalRejectReasonsTemp = new ArrayList<>(0);

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String myLinkId() {
        return applicationNumber == null ? occupancyCertificateNumber : applicationNumber;
    }

    public BpaApplication getParent() {
        return parent;
    }

    public void setParent(BpaApplication parent) {
        this.parent = parent;
    }

    public String getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(String applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    public String getOccupancyCertificateNumber() {
        return occupancyCertificateNumber;
    }

    public void setOccupancyCertificateNumber(String occupancyCertificateNumber) {
        this.occupancyCertificateNumber = occupancyCertificateNumber;
    }

    public String geteDcrNumber() {
        return eDcrNumber;
    }

    public void seteDcrNumber(String eDcrNumber) {
        this.eDcrNumber = eDcrNumber;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    public BpaStatus getStatus() {
        return status;
    }

    public void setStatus(BpaStatus status) {
        this.status = status;
    }

    public EgDemand getDemand() {
        return demand;
    }

    public void setDemand(EgDemand demand) {
        this.demand = demand;
    }

    public List<OccupancyFee> getOccupancyFee() {
        return occupancyFee;
    }

    public void setOccupancyFee(List<OccupancyFee> occupancyFee) {
        this.occupancyFee = occupancyFee;
    }

    public Date getCommencedDate() {
        return commencedDate;
    }

    public void setCommencedDate(Date commencedDate) {
        this.commencedDate = commencedDate;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    public Date getWorkCompletionDueDate() {
        return workCompletionDueDate;
    }

    public void setWorkCompletionDueDate(Date workCompletionDueDate) {
        this.workCompletionDueDate = workCompletionDueDate;
    }

    public Boolean isCitizenAccepted() {
        return citizenAccepted;
    }

    public void setCitizenAccepted(Boolean citizenAccepted) {
        this.citizenAccepted = citizenAccepted;
    }

    public Boolean isArchitectAccepted() {
        return architectAccepted;
    }

    public void setArchitectAccepted(Boolean architectAccepted) {
        this.architectAccepted = architectAccepted;
    }

    public Boolean getSentToPreviousOwner() {
        return isSentToPreviousOwner;
    }

    public void setSentToPreviousOwner(Boolean sentToPreviousOwner) {
        isSentToPreviousOwner = sentToPreviousOwner;
    }

    public Boolean getRescheduledByCitizen() {
        return isRescheduledByCitizen;
    }

    public void setRescheduledByCitizen(Boolean rescheduledByCitizen) {
        isRescheduledByCitizen = rescheduledByCitizen;
    }

    public Boolean getRescheduledByEmployee() {
        return isRescheduledByEmployee;
    }

    public void setRescheduledByEmployee(Boolean rescheduledByEmployee) {
        isRescheduledByEmployee = rescheduledByEmployee;
    }

    public Boolean getAuthorizedToSubmitPlan() {
        return authorizedToSubmitPlan;
    }

    public void setAuthorizedToSubmitPlan(Boolean authorizedToSubmitPlan) {
        this.authorizedToSubmitPlan = authorizedToSubmitPlan;
    }

    public Boolean getFailureInScheduler() {
        return failureInScheduler;
    }

    public void setFailureInScheduler(Boolean failureInScheduler) {
        this.failureInScheduler = failureInScheduler;
    }

    public String getSchedulerFailedRemarks() {
        return schedulerFailedRemarks;
    }

    public void setSchedulerFailedRemarks(String schedulerFailedRemarks) {
        this.schedulerFailedRemarks = schedulerFailedRemarks;
    }

    public BigDecimal getAdmissionfeeAmount() {
        return admissionfeeAmount;
    }

    public void setAdmissionfeeAmount(final BigDecimal admissionfeeAmount) {
        this.admissionfeeAmount = admissionfeeAmount;
    }

    public String getTownSurveyorRemarks() {
        return townSurveyorRemarks;
    }

    public void setTownSurveyorRemarks(String townSurveyorRemarks) {
        this.townSurveyorRemarks = townSurveyorRemarks;
    }

    public Boolean getTownSurveyorInspectionRequire() {
        return isTownSurveyorInspectionRequire;
    }

    public void setTownSurveyorInspectionRequire(Boolean townSurveyorInspectionRequire) {
        isTownSurveyorInspectionRequire = townSurveyorInspectionRequire;
    }

    public Boolean getLPRequestInitiated() {
        return isLPRequestInitiated;
    }

    public void setLPRequestInitiated(Boolean LPRequestInitiated) {
        isLPRequestInitiated = LPRequestInitiated;
    }

    public BigDecimal getExtentInSqmts() {
        return extentInSqmts;
    }

    public void setExtentInSqmts(BigDecimal extentInSqmts) {
        this.extentInSqmts = extentInSqmts;
    }

    public List<OCBuilding> getBuildings() {
        return buildings;
    }

    public void setBuildings(List<OCBuilding> buildings) {
        this.buildings = buildings;
    }

    public List<OCExistingBuilding> getExistingBuildings() {
        return existingBuildings;
    }

    public void setExistingBuildings(List<OCExistingBuilding> existingBuildings) {
        this.existingBuildings = existingBuildings;
    }

    public List<OCDocuments> getDocuments() {
        return documents;
    }

    public void setDocuments(List<OCDocuments> documents) {
        this.documents = documents;
    }

    public List<OCDcrDocuments> getDcrDocuments() {
        return dcrDocuments;
    }

    public void setDcrDocuments(List<OCDcrDocuments> dcrDocuments) {
        this.dcrDocuments = dcrDocuments;
    }

    public List<OCNocDocuments> getNocDocuments() {
        return nocDocuments;
    }

    public void setNocDocuments(List<OCNocDocuments> nocDocuments) {
        this.nocDocuments = nocDocuments;
    }

    public List<OCInspection> getInspections() {
        return inspections;
    }

    public void setInspections(List<OCInspection> inspections) {
        this.inspections = inspections;
    }

    public List<OCDocumentScrutiny> getDocumentScrutinies() {
        return documentScrutinies;
    }

    public void setDocumentScrutinies(List<OCDocumentScrutiny> documentScrutinies) {
        this.documentScrutinies = documentScrutinies;
    }

    public List<OCSlot> getOcSlots() {
        return ocSlots;
    }

    public void setOcSlots(List<OCSlot> ocSlots) {
        this.ocSlots = ocSlots;
    }

    public List<OCAppointmentSchedule> getAppointmentSchedules() {
        return appointmentSchedules;
    }

    public void setAppointmentSchedules(List<OCAppointmentSchedule> appointmentSchedules) {
        this.appointmentSchedules = appointmentSchedules;
    }

    @Override
    public String getStateDetails() {
        return String.format("Applicant Name: %s Application Number %s Dated %s For the service type - %s.",
                parent.getOwner() == null ? "Not Specified" : parent.getOwner().getName(),
                applicationNumber == null ? occupancyCertificateNumber : applicationNumber,
                applicationDate == null ? DateUtils.toDefaultDateFormat(new Date())
                        : DateUtils.toDefaultDateFormat(applicationDate),
                parent.getServiceType().getDescription() == null ? "" : parent.getServiceType().getDescription());
    }

    public WorkflowBean getWorkflowBean() {
        return workflowBean;
    }

    public void setWorkflowBean(WorkflowBean workflowBean) {
        this.workflowBean = workflowBean;
    }

    public Set<Receipt> getReceipts() {
        return receipts;
    }

    public void setReceipts(Set<Receipt> receipts) {
        this.receipts = receipts;
    }

    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }

    public Long getApprovalDepartment() {
        return approvalDepartment;
    }

    public void setApprovalDepartment(Long approvalDepartment) {
        this.approvalDepartment = approvalDepartment;
    }

    public Long getZoneId() {
        return zoneId;
    }

    public void setZoneId(Long zoneId) {
        this.zoneId = zoneId;
    }

    public Long getWardId() {
        return wardId;
    }

    public void setWardId(Long wardId) {
        this.wardId = wardId;
    }

    public String getApprovalComent() {
        return approvalComent;
    }

    public void setApprovalComent(String approvalComent) {
        this.approvalComent = approvalComent;
    }

    public Date getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }

    public void addNotice(OCNotice ocNotice) {
        this.ocNotices.add(ocNotice);
    }

    public List<OCNotice> getOcNotices() {
        return ocNotices;
    }

    public void setOcNotices(List<OCNotice> ocNotices) {
        this.ocNotices = ocNotices;
    }

    public List<OCBuilding> getBuildingDetailFromEdcr() {
        return buildingDetailFromEdcr;
    }

    public void setBuildingDetailFromEdcr(List<OCBuilding> buildingDetailFromEdcr) {
        this.buildingDetailFromEdcr = buildingDetailFromEdcr;
    }

    public List<OCExistingBuilding> getExistingBldgDetailFromEdcr() {
        return existingBldgDetailFromEdcr;
    }

    public void setExistingBldgDetailFromEdcr(List<OCExistingBuilding> existingBldgDetailFromEdcr) {
        this.existingBldgDetailFromEdcr = existingBldgDetailFromEdcr;
    }

    public List<OCNoticeConditions> getRejectionReasons() {
        return rejectionReasons;
    }

    public void setRejectionReasons(List<OCNoticeConditions> rejectionReasons) {
        this.rejectionReasons = rejectionReasons;
    }

    public List<OCNoticeConditions> getAdditionalNoticeConditions() {
        return additionalNoticeConditions;
    }

    public void setAdditionalNoticeConditions(List<OCNoticeConditions> additionalNoticeConditions) {
        this.additionalNoticeConditions = additionalNoticeConditions;
    }

    public List<OCNoticeConditions> getRejectionReasonsTemp() {
        return rejectionReasonsTemp;
    }

    public void setRejectionReasonsTemp(List<OCNoticeConditions> rejectionReasonsTemp) {
        this.rejectionReasonsTemp = rejectionReasonsTemp;
    }

    public List<OCNoticeConditions> getAdditionalRejectReasonsTemp() {
        return additionalRejectReasonsTemp;
    }

    public void setAdditionalRejectReasonsTemp(List<OCNoticeConditions> additionalRejectReasonsTemp) {
        this.additionalRejectReasonsTemp = additionalRejectReasonsTemp;
    }

}