package org.egov.bpa.transaction.entity;

import org.egov.bpa.master.entity.VillageName;
import org.egov.bpa.transaction.entity.enums.ChecklistValues;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.persistence.entity.AbstractAuditable;
import org.hibernate.validator.constraints.Length;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "EGBPA_DOCUMENTSCRUTINY")
@SequenceGenerator(name = BpaDocumentScrutiny.SEQ_EGBPA_DOCUMENTSCRUTINY, sequenceName = BpaDocumentScrutiny.SEQ_EGBPA_DOCUMENTSCRUTINY, allocationSize = 1)
public class BpaDocumentScrutiny extends AbstractAuditable {
    private static final long serialVersionUID = 1L;
    public static final String SEQ_EGBPA_DOCUMENTSCRUTINY = "SEQ_EGBPA_DOCUMENTSCRUTINY";
    @Id
    @GeneratedValue(generator = SEQ_EGBPA_DOCUMENTSCRUTINY, strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @NotNull
    @JoinColumn(name = "application", nullable = false)
    private BpaApplication application;
    @Length(min = 1, max = 24)
    private String plotsurveynumber;
    @Length(min = 1, max = 24)
    private String reSurveynumber;
    @Length(min = 1, max = 12)
    private String subdivisionNumber;
    private BigDecimal extentinsqmts;
    @Length(min = 1, max = 128)
    private String natureofOwnership;
    @Length(min = 1, max = 128)
    private String registrarOffice;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "village")
    private VillageName village;
    @Length(min = 1, max = 128)
    private String taluk;
    @Length(min = 1, max = 128)
    private String district;
    @Enumerated(EnumType.STRING)
    private ChecklistValues neighoutOwnerDtlSubmitted;
    @Length(min = 1, max = 64)
    private String deedNumber;
    @Temporal(value = TemporalType.DATE)
    private Date deedDate;
    @Enumerated(EnumType.STRING)
    private ChecklistValues whetherAllDocAttached;
    @Enumerated(EnumType.STRING)
    private ChecklistValues whetherAllPageOfDocAttached;
    @Enumerated(EnumType.STRING)
    private ChecklistValues whetherDocumentMatch;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "verifiedBy")
    private User verifiedBy;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(final Long id) {
        this.id = id;
    }

    public BpaApplication getApplication() {
        return application;
    }

    public void setApplication(final BpaApplication application) {
        this.application = application;
    }

    public String getPlotsurveynumber() {
        return plotsurveynumber;
    }

    public void setPlotsurveynumber(final String plotsurveynumber) {
        this.plotsurveynumber = plotsurveynumber;
    }

    public String getSubdivisionNumber() {
        return subdivisionNumber;
    }

    public void setSubdivisionNumber(final String subdivisionNumber) {
        this.subdivisionNumber = subdivisionNumber;
    }

    public BigDecimal getExtentinsqmts() {
        return extentinsqmts;
    }

    public void setExtentinsqmts(final BigDecimal extentinsqmts) {
        this.extentinsqmts = extentinsqmts;
    }

    public String getNatureofOwnership() {
        return natureofOwnership;
    }

    public void setNatureofOwnership(final String natureofOwnership) {
        this.natureofOwnership = natureofOwnership;
    }

    public String getRegistrarOffice() {
        return registrarOffice;
    }

    public void setRegistrarOffice(final String registrarOffice) {
        this.registrarOffice = registrarOffice;
    }

    public VillageName getVillage() {
        return village;
    }

    public void setVillage(final VillageName village) {
        this.village = village;
    }

    public String getTaluk() {
        return taluk;
    }

    public void setTaluk(final String taluk) {
        this.taluk = taluk;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(final String district) {
        this.district = district;
    }

    public ChecklistValues getNeighoutOwnerDtlSubmitted() {
        return neighoutOwnerDtlSubmitted;
    }

    public void setNeighoutOwnerDtlSubmitted(ChecklistValues neighoutOwnerDtlSubmitted) {
        this.neighoutOwnerDtlSubmitted = neighoutOwnerDtlSubmitted;
    }

    public ChecklistValues getWhetherAllDocAttached() {
        return whetherAllDocAttached;
    }

    public void setWhetherAllDocAttached(ChecklistValues whetherAllDocAttached) {
        this.whetherAllDocAttached = whetherAllDocAttached;
    }

    public ChecklistValues getWhetherAllPageOfDocAttached() {
        return whetherAllPageOfDocAttached;
    }

    public void setWhetherAllPageOfDocAttached(ChecklistValues whetherAllPageOfDocAttached) {
        this.whetherAllPageOfDocAttached = whetherAllPageOfDocAttached;
    }

    public ChecklistValues getWhetherDocumentMatch() {
        return whetherDocumentMatch;
    }

    public void setWhetherDocumentMatch(ChecklistValues whetherDocumentMatch) {
        this.whetherDocumentMatch = whetherDocumentMatch;
    }

    public String getDeedNumber() {
        return deedNumber;
    }

    public void setDeedNumber(final String deedNumber) {
        this.deedNumber = deedNumber;
    }

    public Date getDeedDate() {
        return deedDate;
    }

    public void setDeedDate(final Date deedDate) {
        this.deedDate = deedDate;
    }

    public User getVerifiedBy() {
        return verifiedBy;
    }

    public void setVerifiedBy(final User verifiedBy) {
        this.verifiedBy = verifiedBy;
    }

    public String getReSurveynumber() {
        return reSurveynumber;
    }

    public void setReSurveynumber(String reSurveynumber) {
        this.reSurveynumber = reSurveynumber;
    }

}
