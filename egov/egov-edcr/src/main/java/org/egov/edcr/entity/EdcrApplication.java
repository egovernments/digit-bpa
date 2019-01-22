package org.egov.edcr.entity;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.egov.infra.persistence.entity.AbstractAuditable;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "EDCR_APPLICATION")
@SequenceGenerator(name = EdcrApplication.SEQ_EDCR_APPLICATION, sequenceName = EdcrApplication.SEQ_EDCR_APPLICATION, allocationSize = 1)
public class EdcrApplication extends AbstractAuditable {
    /*
     * Application number and date.Owner name, contact info,email id, address, Architect name, emailid,contract info.
     */
    public static final String SEQ_EDCR_APPLICATION = "SEQ_EDCR_APPLICATION";
    private static final long serialVersionUID = 61L;

    @Id
    @GeneratedValue(generator = SEQ_EDCR_APPLICATION, strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    @Length(min = 1, max = 128)
    private String applicationNumber;

    @Temporal(value = TemporalType.DATE)
    private Date applicationDate;

    private String status;

    @OneToMany(mappedBy = "application", fetch = LAZY, cascade = ALL)
    @OrderBy("id DESC ")
    private List<EdcrApplicationDetail> edcrApplicationDetails;

    @Transient
    private PlanInformation planInformation;
 
    private transient MultipartFile dxfFile; // File to be process.

    private transient File savedDxfFile;

    private transient EdcrApplicationDetail savedEdcrApplicationDetail;

    private String applicantName;

    private String occupancy;

    private String serviceType;

    private String amenities;

    private String architectInformation;

    private String projectType;

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

    public MultipartFile getDxfFile() {
        return dxfFile;
    }

    public void setDxfFile(MultipartFile dxfFile) {
        this.dxfFile = dxfFile;
    }

    public List<EdcrApplicationDetail> getEdcrApplicationDetails() {
        return edcrApplicationDetails;
    }

    public void setEdcrApplicationDetails(List<EdcrApplicationDetail> edcrApplicationDetails) {
        this.edcrApplicationDetails = edcrApplicationDetails;
    }

    public PlanInformation getPlanInformation() {
        return planInformation;
    }

    public void setPlanInformation(PlanInformation planInformation) {
        this.planInformation = planInformation;
    }

    public File getSavedDxfFile() {
        return savedDxfFile;
    }

    public void setSavedDxfFile(File savedDxfFile) {
        this.savedDxfFile = savedDxfFile;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public EdcrApplicationDetail getSavedEdcrApplicationDetail() {
        return savedEdcrApplicationDetail;
    }

    public void setSavedEdcrApplicationDetail(EdcrApplicationDetail savedEdcrApplicationDetail) {
        this.savedEdcrApplicationDetail = savedEdcrApplicationDetail;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(String occupancy) {
        this.occupancy = occupancy;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getAmenities() {
        return amenities;
    }

    public String getArchitectInformation() {
        return architectInformation;
    }

    public void setArchitectInformation(String architectInformation) {
        this.architectInformation = architectInformation;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }
}
