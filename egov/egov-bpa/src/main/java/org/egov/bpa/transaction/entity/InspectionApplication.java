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

package org.egov.bpa.transaction.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;

import org.egov.bpa.master.entity.BuildingConstructionStage;
import org.egov.infra.filestore.entity.FileStoreMapper;
import org.egov.infra.utils.DateUtils;
import org.egov.infra.workflow.entity.StateAware;
import org.egov.pims.commons.Position;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "EGBPA_INSPECTION_APPLICATION")
@SequenceGenerator(name = InspectionApplication.SEQ_INSPECTIONAPPLICATION, sequenceName = InspectionApplication.SEQ_INSPECTIONAPPLICATION, allocationSize = 1)
public class InspectionApplication extends StateAware<Position> {

    public static final String SEQ_INSPECTIONAPPLICATION = "SEQ_EGBPA_INSPECTION_APPLICATION";
    private static final long serialVersionUID = -6537197288191260269L;
    public static final String ORDER_BY_ID_ASC = "id ASC";

    @Id
    @GeneratedValue(generator = SEQ_INSPECTIONAPPLICATION, strategy = GenerationType.SEQUENCE)
    private Long id;

    @SafeHtml
    @Length(min = 1, max = 64)
    private String applicationNumber;

    @Temporal(value = TemporalType.DATE)
    private Date applicationDate;

    @Temporal(value = TemporalType.DATE)
    private Date approvalDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "status")
    private BpaStatus status;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "buildingConstructionStage")
    private BuildingConstructionStage buildingConstructionStage;

    @SafeHtml
    @Length(min = 1, max = 1000)
    private String remarks;

    private Boolean isSentToPreviousOwner = false;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "egbpa_ts_inspnappln_documents", joinColumns = @JoinColumn(name = "inspectionapplication"), inverseJoinColumns = @JoinColumn(name = "fileStoreId"))
    private Set<FileStoreMapper> tsInspnSupportDocs = Collections.emptySet();

    @OneToMany(mappedBy = "inspectionApplication", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy(ORDER_BY_ID_ASC)
    private List<InspectionAppointmentSchedule> appointmentSchedules = new ArrayList<>();

    @Valid
    @OneToMany(mappedBy = "inspectionApplication", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("id DESC ")
    private List<InConstructionInspection> inspections = new ArrayList<>();

    @OneToMany(mappedBy = "inspectionApplication", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<InspectionLetterToParty> inspectionLetterToParties = new ArrayList<>();

    @SafeHtml
    @Length(min = 1, max = 5000)
    private String townSurveyorRemarks;

    private Boolean isTownSurveyorInspectionRequire = false;

    private Boolean isLPRequestInitiated;

    private transient Long approvalDepartment;

    private transient Long approvalDesignation;

    @SafeHtml
    @Length(max = 1024)
    private transient String approvalComent;

    private transient MultipartFile[] files;

    @Override
    public String getStateDetails() {
        return String.format(" Acknowledgement Number %s Dated %s ", applicationNumber,
                applicationDate == null ? DateUtils.toDefaultDateFormat(new Date())
                        : DateUtils.toDefaultDateFormat(applicationDate));
    }

    @Override
    public String myLinkId() {
        return applicationNumber;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public BpaStatus getStatus() {
        return status;
    }

    public void setStatus(BpaStatus status) {
        this.status = status;
    }

    public Long getApprovalDepartment() {
        return approvalDepartment;
    }

    public void setApprovalDepartment(Long approvalDepartment) {
        this.approvalDepartment = approvalDepartment;
    }

    public String getApprovalComent() {
        return approvalComent;
    }

    public void setApprovalComent(String approvalComent) {
        this.approvalComent = approvalComent;
    }

    public List<InspectionLetterToParty> getInspectionLetterToParties() {
        return inspectionLetterToParties;
    }

    public void setInspectionLetterToParties(List<InspectionLetterToParty> inspectionLetterToParties) {
        this.inspectionLetterToParties = inspectionLetterToParties;
    }

    public List<InspectionAppointmentSchedule> getAppointmentSchedules() {
        return appointmentSchedules;
    }

    public void setAppointmentSchedules(List<InspectionAppointmentSchedule> appointmentSchedules) {
        this.appointmentSchedules = appointmentSchedules;
    }

    public List<InConstructionInspection> getInspections() {
        return inspections;
    }

    public void setInspections(List<InConstructionInspection> inspections) {
        this.inspections = inspections;
    }

    public Set<FileStoreMapper> getTsInspnSupportDocs() {
        return tsInspnSupportDocs;
    }

    public void setTsInspnSupportDocs(Set<FileStoreMapper> tsInspnSupportDocs) {
        this.tsInspnSupportDocs = tsInspnSupportDocs;
    }

    public Boolean getLPRequestInitiated() {
        return isLPRequestInitiated;
    }

    public void setLPRequestInitiated(Boolean LPRequestInitiated) {
        isLPRequestInitiated = LPRequestInitiated;
    }

    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }

    public Date getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }

    public Boolean getSentToPreviousOwner() {
        return isSentToPreviousOwner;
    }

    public void setSentToPreviousOwner(Boolean sentToPreviousOwner) {
        isSentToPreviousOwner = sentToPreviousOwner;
    }

    public Boolean getTownSurveyorInspectionRequire() {
        return isTownSurveyorInspectionRequire;
    }

    public void setTownSurveyorInspectionRequire(Boolean townSurveyorInspectionRequire) {
        isTownSurveyorInspectionRequire = townSurveyorInspectionRequire;
    }

    public String getTownSurveyorRemarks() {
        return townSurveyorRemarks;
    }

    public void setTownSurveyorRemarks(String townSurveyorRemarks) {
        this.townSurveyorRemarks = townSurveyorRemarks;
    }

    public Long getApprovalDesignation() {
        return approvalDesignation;
    }

    public void setApprovalDesignation(Long approvalDesignation) {
        this.approvalDesignation = approvalDesignation;
    }

    public BuildingConstructionStage getBuildingConstructionStage() {
        return buildingConstructionStage;
    }

    public void setBuildingConstructionStage(BuildingConstructionStage buildingConstructionStage) {
        this.buildingConstructionStage = buildingConstructionStage;
    }
}