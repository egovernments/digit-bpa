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

package org.egov.bpa.transaction.entity.common;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.egov.bpa.master.entity.VillageName;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.persistence.entity.AbstractAuditable;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "EGBPA_DOCUMENT_SCRUTINY")
@SequenceGenerator(name = DocumentScrutiny.SEQ, sequenceName = DocumentScrutiny.SEQ, allocationSize = 1)
public class DocumentScrutiny extends AbstractAuditable {

    public static final String SEQ = "SEQ_EGBPA_DOCUMENT_SCRUTINY";
    private static final long serialVersionUID = 28041127141075175L;

    @Id
    @GeneratedValue(generator = SEQ, strategy = GenerationType.SEQUENCE)
    private Long id;

    @Length(min = 1, max = 24)
    private String plotSurveyNumber;

    @Length(min = 1, max = 24)
    private String reSurveyNumber;

    @Length(min = 1, max = 12)
    private String subdivisionNumber;

    private BigDecimal extentInSqmts;

    @Length(min = 1, max = 128)
    private String natureOfOwnership;

    @Length(min = 1, max = 128)
    private String registrarOffice;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "village")
    private VillageName village;

    @Length(min = 1, max = 128)
    private String taluk;

    @Length(min = 1, max = 128)
    private String district;

    @Length(min = 1, max = 64)
    private String deedNumber;

    @Temporal(value = TemporalType.DATE)
    private Date deedDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "verifiedBy")
    private User verifiedBy;

    @OneToMany(mappedBy = "documentScrutiny", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DocumentScrutinyChecklist> documentScrutinyChecklists = new ArrayList<>();

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getPlotSurveyNumber() {
        return plotSurveyNumber;
    }

    public void setPlotSurveyNumber(String plotSurveyNumber) {
        this.plotSurveyNumber = plotSurveyNumber;
    }

    public String getReSurveyNumber() {
        return reSurveyNumber;
    }

    public void setReSurveyNumber(String reSurveyNumber) {
        this.reSurveyNumber = reSurveyNumber;
    }

    public String getSubdivisionNumber() {
        return subdivisionNumber;
    }

    public void setSubdivisionNumber(String subdivisionNumber) {
        this.subdivisionNumber = subdivisionNumber;
    }

    public BigDecimal getExtentInSqmts() {
        return extentInSqmts;
    }

    public void setExtentInSqmts(BigDecimal extentInSqmts) {
        this.extentInSqmts = extentInSqmts;
    }

    public String getNatureOfOwnership() {
        return natureOfOwnership;
    }

    public void setNatureOfOwnership(String natureOfOwnership) {
        this.natureOfOwnership = natureOfOwnership;
    }

    public String getRegistrarOffice() {
        return registrarOffice;
    }

    public void setRegistrarOffice(String registrarOffice) {
        this.registrarOffice = registrarOffice;
    }

    public VillageName getVillage() {
        return village;
    }

    public void setVillage(VillageName village) {
        this.village = village;
    }

    public String getTaluk() {
        return taluk;
    }

    public void setTaluk(String taluk) {
        this.taluk = taluk;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDeedNumber() {
        return deedNumber;
    }

    public void setDeedNumber(String deedNumber) {
        this.deedNumber = deedNumber;
    }

    public Date getDeedDate() {
        return deedDate;
    }

    public void setDeedDate(Date deedDate) {
        this.deedDate = deedDate;
    }

    public User getVerifiedBy() {
        return verifiedBy;
    }

    public void setVerifiedBy(User verifiedBy) {
        this.verifiedBy = verifiedBy;
    }

    public List<DocumentScrutinyChecklist> getDocumentScrutinyChecklists() {
        return documentScrutinyChecklists;
    }

    public void setDocumentScrutinyChecklists(List<DocumentScrutinyChecklist> documentScrutinyChecklists) {
        this.documentScrutinyChecklists = documentScrutinyChecklists;
    }
}
