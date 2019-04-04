/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2017>  eGovernments Foundation
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
package org.egov.bpa.transaction.entity;

import java.math.BigDecimal;
import java.util.Date;

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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.egov.bpa.master.entity.BpaScheme;
import org.egov.bpa.master.entity.BpaSchemeLandUsage;
import org.egov.bpa.master.entity.ConstructionStages;
import org.egov.bpa.master.entity.PostalAddress;
import org.egov.bpa.master.entity.RegistrarOfficeVillage;
import org.egov.bpa.master.entity.VillageName;
import org.egov.bpa.transaction.entity.enums.BpaUom;
import org.egov.infra.admin.master.entity.Boundary;
import org.egov.infra.persistence.entity.AbstractAuditable;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "EGBPA_SITEDETAIL")
@SequenceGenerator(name = SiteDetail.SEQ_EGBPA_SITEDETAIL, sequenceName = SiteDetail.SEQ_EGBPA_SITEDETAIL, allocationSize = 1)
public class SiteDetail extends AbstractAuditable {

    private static final long serialVersionUID = 1L;
    public static final String SEQ_EGBPA_SITEDETAIL = "SEQ_EGBPA_SITEDETAIL";
    @Id
    @GeneratedValue(generator = SEQ_EGBPA_SITEDETAIL, strategy = GenerationType.SEQUENCE)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @NotNull
    @JoinColumn(name = "application", nullable = false)
    private BpaApplication application;
    @Length(min = 1, max = 12)
    private String plotdoornumber;
    @Length(min = 1, max = 128)
    private String plotlandmark;
    @Length(min = 1, max = 24)
    private String plotnumber;
    @Length(min = 1, max = 24)
    private String plotsurveynumber;
    @Length(min = 1, max = 24)
    private String surveynumberType;
    @Length(min = 1, max = 24)
    private String oldSurveyNumber;
    @Length(min = 1, max = 128)
    private String reSurveyNumber;
    @Length(min = 1, max = 24)
    private String townSurveyNumber;
    @Length(min = 1, max = 128)
    private String plotNumber;
    @Length(min = 1, max = 128)
    private String khataNumber;
    @Length(min = 1, max = 128)
    private String holdingNumber;
    @Length(min = 1, max = 128)
    private String streetaddress1;
    @Length(min = 1, max = 128)
    private String streetaddress2;
    @Length(min = 1, max = 128)
    private String area;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "adminboundary")
    private Boundary adminBoundary;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "locationBoundary")
    private Boundary locationBoundary;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "electionBoundary")
    private Boundary electionBoundary;
    @Length(min = 1, max = 128)
    private String citytown;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "street")
    private Boundary street;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "village")
    private VillageName village;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postalAddress")
    private PostalAddress postalAddress;
    @Length(min = 1, max = 128)
    private String natureofOwnership;
    private BigDecimal extentOfLand;
    private BigDecimal extentinsqmts;
    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private BpaUom unitOfMeasurement;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "registrarOffice")
    private RegistrarOfficeVillage registrarOffice;
    @Length(min = 1, max = 12)
    private String nearestbuildingnumber;
    @Length(min = 1, max = 12)
    private String subdivisionNumber;
    @Length(min = 1, max = 128)
    private Boolean encroachmentIssuesPresent;
    @Length(min = 1, max = 128)
    private String encroachmentRemarks;
    /// this need to be check as we can refer master --statusofsite -- construction in progress,vacant ,completed
    private Boolean siteinApprovedLayout;
    @Length(min = 1, max = 128)
    private String approvedLayoutDetail;
    private BigDecimal setBackFront;
    private BigDecimal setBackRear;
    private BigDecimal setBackSide1;
    private BigDecimal setBackSide2;
    private BigDecimal lengthOfCompoundWall;
    private BigDecimal dwellingunitnt;
    private String locationOfPlot;
    private BigDecimal roofConversion;
    private BigDecimal shutter;
    private BigDecimal erectionoftower;
    private BigDecimal noOfPoles;
    private BigDecimal noOfHutOrSheds;
    private BigDecimal demolitionArea;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "conststages")
    private ConstructionStages constStages;
    @Length(min = 1, max = 128)
    private String stateOfConstruction;
    private Boolean isappForRegularization = false;
    private Boolean charitableTrustBuilding;
    private Boolean affordableHousingScheme;
    @Temporal(value = TemporalType.DATE)
    private Date workCommencementDate;
    @Temporal(value = TemporalType.DATE)
    private Date workCompletionDate;
    private transient Long postalId;
    private transient Long landUsageId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "scheme")
    private BpaScheme scheme;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "landUsage")
    private BpaSchemeLandUsage landUsage;
    private transient Long registrarVillageId;
    @Length(min = 1,max = 4000)
    private String previousownerdetails;
    @Length(min = 1,max = 4000)
    private String landregistrationdetails;

    @Override
    public Long getId() {
        return id;
    }

    public BpaApplication getApplication() {
        return application;
    }

    public void setApplication(final BpaApplication application) {
        this.application = application;
    }

    public String getPlotdoornumber() {
        return plotdoornumber;
    }

    public void setPlotdoornumber(final String plotdoornumber) {
        this.plotdoornumber = plotdoornumber;
    }

    public String getPlotlandmark() {
        return plotlandmark;
    }

    public void setPlotlandmark(final String plotlandmark) {
        this.plotlandmark = plotlandmark;
    }

    public String getPlotnumber() {
        return plotnumber;
    }

    public void setPlotnumber(final String plotnumber) {
        this.plotnumber = plotnumber;
    }

    public String getPlotsurveynumber() {
        return plotsurveynumber;
    }

    public void setPlotsurveynumber(final String plotsurveynumber) {
        this.plotsurveynumber = plotsurveynumber;
    }

    public String getReSurveyNumber() {
        return reSurveyNumber;
    }

    public void setReSurveyNumber(String reSurveyNumber) {
        this.reSurveyNumber = reSurveyNumber;
    }

    public String getTownSurveyNumber() {
        return townSurveyNumber;
    }

    public void setTownSurveyNumber(String townSurveyNumber) {
        this.townSurveyNumber = townSurveyNumber;
    }
    
    public String getPlotNumber() {
        return plotNumber;
    }

    public void setPlotNumber(String plotNumber) {
        this.plotNumber = plotNumber;
    }

    public String getKhataNumber() {
        return khataNumber;
    }

    public void setKhataNumber(String khataNumber) {
        this.khataNumber = khataNumber;
    }

    public String getHoldingNumber() {
        return holdingNumber;
    }

    public void setHoldingNumber(String holdingNumber) {
        this.holdingNumber = holdingNumber;
    }

    public String getSurveynumberType() {
        return surveynumberType;
    }

    public void setSurveynumberType(final String surveynumberType) {
        this.surveynumberType = surveynumberType;
    }

    public String getOldSurveyNumber() {
        return oldSurveyNumber;
    }

    public void setOldSurveyNumber(final String oldSurveyNumber) {
        this.oldSurveyNumber = oldSurveyNumber;
    }

    public String getStreetaddress1() {
        return streetaddress1;
    }

    public void setStreetaddress1(final String streetaddress1) {
        this.streetaddress1 = streetaddress1;
    }

    public String getStreetaddress2() {
        return streetaddress2;
    }

    public void setStreetaddress2(final String streetaddress2) {
        this.streetaddress2 = streetaddress2;
    }

    public String getArea() {
        return area;
    }

    public void setArea(final String area) {
        this.area = area;
    }

    public String getCitytown() {
        return citytown;
    }

    public void setCitytown(final String citytown) {
        this.citytown = citytown;
    }

    public Boundary getStreet() {
        return street;
    }

    public void setStreet(final Boundary street) {
        this.street = street;
    }

    public VillageName getVillage() {
        return village;
    }

    public void setVillage(final VillageName village) {
        this.village = village;
    }

    public PostalAddress getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(PostalAddress postalAddress) {
        this.postalAddress = postalAddress;
    }

    public String getNatureofOwnership() {
        return natureofOwnership;
    }

    public void setNatureofOwnership(final String natureofOwnership) {
        this.natureofOwnership = natureofOwnership;
    }

    public BigDecimal getExtentinsqmts() {
        return extentinsqmts;
    }

    public void setExtentinsqmts(final BigDecimal extentinsqmts) {
        this.extentinsqmts = extentinsqmts;
    }

    public RegistrarOfficeVillage getRegistrarOffice() {
        return registrarOffice;
    }

    public void setRegistrarOffice(RegistrarOfficeVillage registrarOffice) {
        this.registrarOffice = registrarOffice;
    }

    public String getNearestbuildingnumber() {
        return nearestbuildingnumber;
    }

    public void setNearestbuildingnumber(final String nearestbuildingnumber) {
        this.nearestbuildingnumber = nearestbuildingnumber;
    }

    public String getSubdivisionNumber() {
        return subdivisionNumber;
    }

    public void setSubdivisionNumber(final String subdivisionNumber) {
        this.subdivisionNumber = subdivisionNumber;
    }

    public Boolean getEncroachmentIssuesPresent() {
        return encroachmentIssuesPresent;
    }

    public void setEncroachmentIssuesPresent(final Boolean encroachmentIssuesPresent) {
        this.encroachmentIssuesPresent = encroachmentIssuesPresent;
    }

    public String getEncroachmentRemarks() {
        return encroachmentRemarks;
    }

    public void setEncroachmentRemarks(final String encroachmentRemarks) {
        this.encroachmentRemarks = encroachmentRemarks;
    }

    public Boolean getSiteinApprovedLayout() {
        return siteinApprovedLayout;
    }

    public void setSiteinApprovedLayout(final Boolean siteinApprovedLayout) {
        this.siteinApprovedLayout = siteinApprovedLayout;
    }

    public String getApprovedLayoutDetail() {
        return approvedLayoutDetail;
    }

    public void setApprovedLayoutDetail(final String approvedLayoutDetail) {
        this.approvedLayoutDetail = approvedLayoutDetail;
    }

    public BigDecimal getSetBackFront() {
        return setBackFront;
    }

    public void setSetBackFront(final BigDecimal setBackFront) {
        this.setBackFront = setBackFront;
    }

    public BigDecimal getSetBackRear() {
        return setBackRear;
    }

    public void setSetBackRear(final BigDecimal setBackRear) {
        this.setBackRear = setBackRear;
    }

    public BigDecimal getSetBackSide1() {
        return setBackSide1;
    }

    public void setSetBackSide1(final BigDecimal setBackSide1) {
        this.setBackSide1 = setBackSide1;
    }

    public BigDecimal getSetBackSide2() {
        return setBackSide2;
    }

    public void setSetBackSide2(final BigDecimal setBackSide2) {
        this.setBackSide2 = setBackSide2;
    }

    @Override
    public void setId(final Long id) {
        this.id = id;
    }

    public Boundary getAdminBoundary() {
        return adminBoundary;
    }

    public void setAdminBoundary(final Boundary adminBoundary) {
        this.adminBoundary = adminBoundary;
    }

    public Boundary getLocationBoundary() {
        return locationBoundary;
    }

    public void setLocationBoundary(final Boundary locationBoundary) {
        this.locationBoundary = locationBoundary;
    }

    public Boundary getElectionBoundary() {
        return electionBoundary;
    }

    public void setElectionBoundary(final Boundary electionBoundary) {
        this.electionBoundary = electionBoundary;
    }

    public BigDecimal getLengthOfCompoundWall() {
        return lengthOfCompoundWall;
    }

    public void setLengthOfCompoundWall(BigDecimal lengthOfCompoundWall) {
        this.lengthOfCompoundWall = lengthOfCompoundWall;
    }

    public BigDecimal getDwellingunitnt() {
        return dwellingunitnt;
    }

    public void setDwellingunitnt(BigDecimal dwellingunitnt) {
        this.dwellingunitnt = dwellingunitnt;
    }

    public String getLocationOfPlot() {
        return locationOfPlot;
    }

    public void setLocationOfPlot(String locationOfPlot) {
        this.locationOfPlot = locationOfPlot;
    }

    public BigDecimal getRoofConversion() {
        return roofConversion;
    }

    public void setRoofConversion(BigDecimal roofConversion) {
        this.roofConversion = roofConversion;
    }

    public BigDecimal getShutter() {
        return shutter;
    }

    public void setShutter(BigDecimal shutter) {
        this.shutter = shutter;
    }

    public BigDecimal getErectionoftower() {
        return erectionoftower;
    }

    public void setErectionoftower(BigDecimal erectionoftower) {
        this.erectionoftower = erectionoftower;
    }

    public BigDecimal getNoOfPoles() {
        return noOfPoles;
    }

    public void setNoOfPoles(BigDecimal noOfPoles) {
        this.noOfPoles = noOfPoles;
    }

    public BigDecimal getNoOfHutOrSheds() {
        return noOfHutOrSheds;
    }

    public void setNoOfHutOrSheds(BigDecimal noOfHutOrSheds) {
        this.noOfHutOrSheds = noOfHutOrSheds;
    }

    public BigDecimal getDemolitionArea() {
        return demolitionArea;
    }

    public void setDemolitionArea(BigDecimal demolitionArea) {
        this.demolitionArea = demolitionArea;
    }

    public BpaUom getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public void setUnitOfMeasurement(BpaUom unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public ConstructionStages getConstStages() {
        return constStages;
    }

    public void setConstStages(ConstructionStages constStages) {
        this.constStages = constStages;
    }

    public String getStateOfConstruction() {
        return stateOfConstruction;
    }

    public void setStateOfConstruction(String stateOfConstruction) {
        this.stateOfConstruction = stateOfConstruction;
    }

    public Boolean getIsappForRegularization() {
        return isappForRegularization;
    }

    public void setIsappForRegularization(Boolean isappForRegularization) {
        this.isappForRegularization = isappForRegularization;
    }

    public BigDecimal getExtentOfLand() {
        return extentOfLand;
    }

    public void setExtentOfLand(BigDecimal extentOfLand) {
        this.extentOfLand = extentOfLand;
    }

    public Long getPostalId() {
        return postalId;
    }

    public void setPostalId(Long postalId) {
        this.postalId = postalId;
    }

    public BpaScheme getScheme() {
        return scheme;
    }

    public void setScheme(BpaScheme scheme) {
        this.scheme = scheme;
    }

    public BpaSchemeLandUsage getLandUsage() {
        return landUsage;
    }

    public void setLandUsage(BpaSchemeLandUsage landUsage) {
        this.landUsage = landUsage;
    }

    public Long getLandUsageId() {
        return landUsageId;
    }

    public void setLandUsageId(Long landUsageId) {
        this.landUsageId = landUsageId;
    }

    public Date getWorkCommencementDate() {
        return workCommencementDate;
    }

    public void setWorkCommencementDate(Date workCommencementDate) {
        this.workCommencementDate = workCommencementDate;
    }

    public Date getWorkCompletionDate() {
        return workCompletionDate;
    }

    public void setWorkCompletionDate(Date workCompletionDate) {
        this.workCompletionDate = workCompletionDate;
    }

    public Long getRegistrarVillageId() {
        return registrarVillageId;
    }

    public void setRegistrarVillageId(Long registrarVillageId) {
        this.registrarVillageId = registrarVillageId;
    }

	public Boolean getCharitableTrustBuilding() {
		return charitableTrustBuilding;
	}

	public void setCharitableTrustBuilding(Boolean charitableTrustBuilding) {
		this.charitableTrustBuilding = charitableTrustBuilding;
	}

	public Boolean getAffordableHousingScheme() {
		return affordableHousingScheme;
	}

	public void setAffordableHousingScheme(Boolean affordableHousingScheme) {
		this.affordableHousingScheme = affordableHousingScheme;
	}
	
	public String getPreviousownerdetails() {
		return previousownerdetails;
	}

	public void setPreviousownerdetails(String previousownerdetails) {
		this.previousownerdetails = previousownerdetails;
	}

	public String getLandregistrationdetails() {
		return landregistrationdetails;
	}

	public void setLandregistrationdetails(String landregistrationdetails) {
		this.landregistrationdetails = landregistrationdetails;
	}
}