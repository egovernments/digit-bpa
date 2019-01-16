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

import org.egov.bpa.master.entity.ConstructionStages;
import org.egov.bpa.master.entity.LandBuildingTypes;
import org.egov.bpa.master.entity.LayoutMaster;
import org.egov.bpa.master.entity.StormWaterDrain;
import org.egov.bpa.master.entity.SurroundedBldgDtl;
import org.egov.bpa.transaction.entity.enums.LandBldngZoneing;
import org.egov.bpa.transaction.entity.enums.RoadType;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.filestore.entity.FileStoreMapper;
import org.egov.infra.persistence.entity.AbstractAuditable;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "EGBPA_INSPECTION")
@SequenceGenerator(name = Inspection.SEQ_INSPECTION, sequenceName = Inspection.SEQ_INSPECTION, allocationSize = 1)
public class Inspection extends AbstractAuditable {

	public static final String SEQ_INSPECTION = "SEQ_EGBPA_INSPECTION";
	private static final long serialVersionUID = 3078684328383202788L;
	@Id
	@GeneratedValue(generator = SEQ_INSPECTION, strategy = GenerationType.SEQUENCE)
	private Long id;
	@Length(min = 1, max = 64)
	private String inspectionNumber;

	@Temporal(value = TemporalType.DATE)
	private Date inspectionDate;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent")
	private Inspection parent;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "inspectedby")
	private User inspectedBy;
	private Boolean isSiteVacant;
	private Boolean isExistingBuildingAsPerPlan;
	private Boolean isInspected;
	@Length(min = 1, max = 1000)
	private String inspectionRemarks;
	private Boolean isPostponed;
	@Length(min = 1, max = 256)
	private String postponementReason;
	@Temporal(value = TemporalType.DATE)
	private Date postponedDate;

	// Land Details
	@Enumerated(EnumType.ORDINAL)
	private LandBldngZoneing landZoning;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lndlayouttype")
	private LayoutMaster lndLayoutType;
	private BigDecimal lndMinPlotExtent = BigDecimal.ZERO;
	private BigDecimal lndProposedPlotExtent = BigDecimal.ZERO;
	private BigDecimal lndOsrLandExtent = BigDecimal.ZERO;
	private BigDecimal lndGuideLineValue = BigDecimal.ZERO;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "landusage")
	private LandBuildingTypes landUsage;
	private BigDecimal lndRegularizationArea = BigDecimal.ZERO;
	private Integer lndPenaltyPeriod;
	private double lndIsRegularisationCharges;

	// Building Details
	@Enumerated
	private LandBldngZoneing buildingZoning;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "buildingType")
	private LandBuildingTypes buildingType;
	private BigDecimal bldngBuildUpArea = BigDecimal.ZERO;
	private BigDecimal bldngProposedPlotFrontage = BigDecimal.ZERO;
	private BigDecimal bldngRoadWidth = BigDecimal.ZERO;
	private BigDecimal bldngProposedBldngArea = BigDecimal.ZERO;
	private BigDecimal bldngGFloorTiledFloor = BigDecimal.ZERO;
	private BigDecimal bldngGFloorOtherTypes = BigDecimal.ZERO;
	private BigDecimal bldngFrstFloorTotalArea = BigDecimal.ZERO;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bldngstormwaterdrain")
	private StormWaterDrain bldngStormWaterDrain;
	private BigDecimal bldngCompoundWall = BigDecimal.ZERO;
	private BigDecimal bldngWellOhtSumpTankArea = BigDecimal.ZERO;
	private BigDecimal bldngCommercial = BigDecimal.ZERO;
	private BigDecimal bldngResidential = BigDecimal.ZERO;
	private Boolean bldngIsRegularisationCharges;
	private Boolean bldngIsImprovementCharges;
	private BigDecimal bldngAge = BigDecimal.ZERO;
	@Enumerated(EnumType.ORDINAL)
	private RoadType roadType;
	private BigDecimal bldngFsiArea;
	private BigDecimal fsb;
	private BigDecimal rsb;
	private BigDecimal ssb1;
	private BigDecimal ssb2;
	private BigDecimal passageWidth;
	private BigDecimal passageLength;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "surroundedbynorth")
	private SurroundedBldgDtl surroundedByNorth;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "surroundedbysouth")
	private SurroundedBldgDtl surroundedBySouth;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "surroundedbyeast")
	private SurroundedBldgDtl surroundedByEast;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "surroundedbywest")
	private SurroundedBldgDtl surroundedByWest;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "conststages")
	private ConstructionStages constStages;
	@Column(name = "dwellingunitnt")
	private BigDecimal dwellingUnit;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "inspection")
	private List<Docket> docket = new ArrayList<>();
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "application")
	private BpaApplication application;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "egbpa_inspectiondocs", joinColumns = @JoinColumn(name = "inspectionid"), inverseJoinColumns = @JoinColumn(name = "filestoreid"))
	private Set<FileStoreMapper> inspectionSupportDocs = Collections.emptySet();
	private boolean boundaryDrawingSubmitted;
	private boolean rightToMakeConstruction;
	@Length(min = 1, max = 128)
	private String typeofLand;
	@OneToMany(mappedBy = "inspection", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<PlanScrutinyChecklist> planScrutinyChecklist = new ArrayList<>(0);
	@OneToMany(mappedBy = "inspection", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<PlanScrutinyChecklist> planScrutinyChecklistForDrawing = new ArrayList<>(0);

	private transient List<DocketDetail> docketDetailLocList = new ArrayList<>();
	private transient List<DocketDetail> docketDetailAccessList = new ArrayList<>();
	private transient List<DocketDetail> docketDetlSurroundingPlotList = new ArrayList<>();
	private transient List<DocketDetail> docketDetailLandTypeList = new ArrayList<>();
	private transient List<DocketDetail> docketDetailProposedWorkList = new ArrayList<>();
	private transient List<DocketDetail> docketDetailWorkAsPerPlanList = new ArrayList<>();
	private transient List<DocketDetail> docketDetailHgtAbuttRoadList = new ArrayList<>();
	private transient List<DocketDetail> docketDetailMeasumentList = new ArrayList<>();
	private transient List<DocketDetail> docketDetailAreaLoc = new ArrayList<>();
	private transient List<DocketDetail> docketDetailLengthOfCompWall = new ArrayList<>();
	private transient List<DocketDetail> docketDetailNumberOfWell = new ArrayList<>();
	private transient List<DocketDetail> docketDetailErectionTower = new ArrayList<>();
	private transient List<DocketDetail> docketDetailShutter = new ArrayList<>();
	private transient List<DocketDetail> docketDetailRoofConversion = new ArrayList<>();
	private transient List<String> deletedDocketDetailsFilestoreIds;
	private transient MultipartFile[] files;
	private transient Map<Long, String> encodedImages = new HashMap<>();
	private transient List<PlanScrutinyChecklist> planScrutinyChecklistTemp = new ArrayList<>(0);
	private transient List<PlanScrutinyChecklist> planScrutinyChecklistForDrawingTemp = new ArrayList<>(0);

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(final Long id) {
		this.id = id;
	}

	public String getInspectionNumber() {
		return inspectionNumber;
	}

	public void setInspectionNumber(final String inspectionNumber) {
		this.inspectionNumber = inspectionNumber;
	}

	public Date getInspectionDate() {
		return inspectionDate;
	}

	public void setInspectionDate(final Date inspectionDate) {
		this.inspectionDate = inspectionDate;
	}

	public Inspection getParent() {
		return parent;
	}

	public void setParent(final Inspection parent) {
		this.parent = parent;
	}

	public User getInspectedBy() {
		return inspectedBy;
	}

	public void setInspectedBy(final User inspectedBy) {
		this.inspectedBy = inspectedBy;
	}

	public Boolean getIsSiteVacant() {
		return isSiteVacant;
	}

	public void setIsSiteVacant(final Boolean isSiteVacant) {
		this.isSiteVacant = isSiteVacant;
	}

	public Boolean getIsExistingBuildingAsPerPlan() {
		return isExistingBuildingAsPerPlan;
	}

	public void setIsExistingBuildingAsPerPlan(final Boolean isExistingBuildingAsPerPlan) {
		this.isExistingBuildingAsPerPlan = isExistingBuildingAsPerPlan;
	}

	public Boolean getIsInspected() {
		return isInspected;
	}

	public void setIsInspected(final Boolean isInspected) {
		this.isInspected = isInspected;
	}

	public String getInspectionRemarks() {
		return inspectionRemarks;
	}

	public void setInspectionRemarks(final String inspectionRemarks) {
		this.inspectionRemarks = inspectionRemarks;
	}

	public Boolean getIsPostponed() {
		return isPostponed;
	}

	public void setIsPostponed(final Boolean isPostponed) {
		this.isPostponed = isPostponed;
	}

	public String getPostponementReason() {
		return postponementReason;
	}

	public void setPostponementReason(final String postponementReason) {
		this.postponementReason = postponementReason;
	}

	public Date getPostponedDate() {
		return postponedDate;
	}

	public void setPostponedDate(final Date postponedDate) {
		this.postponedDate = postponedDate;
	}

	public LandBldngZoneing getLandZoning() {
		return landZoning;
	}

	public void setLandZoning(final LandBldngZoneing landZoning) {
		this.landZoning = landZoning;
	}

	public LayoutMaster getLndLayoutType() {
		return lndLayoutType;
	}

	public void setLndLayoutType(final LayoutMaster lndLayoutType) {
		this.lndLayoutType = lndLayoutType;
	}

	public BigDecimal getLndMinPlotExtent() {
		return lndMinPlotExtent;
	}

	public void setLndMinPlotExtent(final BigDecimal lndMinPlotExtent) {
		this.lndMinPlotExtent = lndMinPlotExtent;
	}

	public BigDecimal getLndProposedPlotExtent() {
		return lndProposedPlotExtent;
	}

	public void setLndProposedPlotExtent(final BigDecimal lndProposedPlotExtent) {
		this.lndProposedPlotExtent = lndProposedPlotExtent;
	}

	public BigDecimal getLndOsrLandExtent() {
		return lndOsrLandExtent;
	}

	public void setLndOsrLandExtent(final BigDecimal lndOsrLandExtent) {
		this.lndOsrLandExtent = lndOsrLandExtent;
	}

	public BigDecimal getLndGuideLineValue() {
		return lndGuideLineValue;
	}

	public void setLndGuideLineValue(final BigDecimal lndGuideLineValue) {
		this.lndGuideLineValue = lndGuideLineValue;
	}

	public LandBuildingTypes getLandUsage() {
		return landUsage;
	}

	public void setLandUsage(final LandBuildingTypes landUsage) {
		this.landUsage = landUsage;
	}

	public BigDecimal getLndRegularizationArea() {
		return lndRegularizationArea;
	}

	public void setLndRegularizationArea(final BigDecimal lndRegularizationArea) {
		this.lndRegularizationArea = lndRegularizationArea;
	}

	public Integer getLndPenaltyPeriod() {
		return lndPenaltyPeriod;
	}

	public void setLndPenaltyPeriod(final Integer lndPenaltyPeriod) {
		this.lndPenaltyPeriod = lndPenaltyPeriod;
	}

	public double getLndIsRegularisationCharges() {
		return lndIsRegularisationCharges;
	}

	public void setLndIsRegularisationCharges(double lndIsRegularisationCharges) {
		this.lndIsRegularisationCharges = lndIsRegularisationCharges;
	}

	public LandBldngZoneing getBuildingZoning() {
		return buildingZoning;
	}

	public void setBuildingZoning(final LandBldngZoneing buildingZoning) {
		this.buildingZoning = buildingZoning;
	}

	public LandBuildingTypes getBuildingType() {
		return buildingType;
	}

	public void setBuildingType(final LandBuildingTypes buildingType) {
		this.buildingType = buildingType;
	}

	public BigDecimal getBldngBuildUpArea() {
		return bldngBuildUpArea;
	}

	public void setBldngBuildUpArea(final BigDecimal bldngBuildUpArea) {
		this.bldngBuildUpArea = bldngBuildUpArea;
	}

	public BigDecimal getBldngProposedPlotFrontage() {
		return bldngProposedPlotFrontage;
	}

	public void setBldngProposedPlotFrontage(final BigDecimal bldngProposedPlotFrontage) {
		this.bldngProposedPlotFrontage = bldngProposedPlotFrontage;
	}

	public BigDecimal getBldngRoadWidth() {
		return bldngRoadWidth;
	}

	public void setBldngRoadWidth(final BigDecimal bldngRoadWidth) {
		this.bldngRoadWidth = bldngRoadWidth;
	}

	public BigDecimal getBldngProposedBldngArea() {
		return bldngProposedBldngArea;
	}

	public void setBldngProposedBldngArea(final BigDecimal bldngProposedBldngArea) {
		this.bldngProposedBldngArea = bldngProposedBldngArea;
	}

	public StormWaterDrain getBldngStormWaterDrain() {
		return bldngStormWaterDrain;
	}

	public void setBldngStormWaterDrain(final StormWaterDrain bldngStormWaterDrain) {
		this.bldngStormWaterDrain = bldngStormWaterDrain;
	}

	public BigDecimal getBldngCompoundWall() {
		return bldngCompoundWall;
	}

	public void setBldngCompoundWall(final BigDecimal bldngCompoundWall) {
		this.bldngCompoundWall = bldngCompoundWall;
	}

	public BigDecimal getBldngCommercial() {
		return bldngCommercial;
	}

	public void setBldngCommercial(final BigDecimal bldngCommercial) {
		this.bldngCommercial = bldngCommercial;
	}

	public BigDecimal getBldngResidential() {
		return bldngResidential;
	}

	public void setBldngResidential(final BigDecimal bldngResidential) {
		this.bldngResidential = bldngResidential;
	}

	public Boolean getBldngIsRegularisationCharges() {
		return bldngIsRegularisationCharges;
	}

	public void setBldngIsRegularisationCharges(final Boolean bldngIsRegularisationCharges) {
		this.bldngIsRegularisationCharges = bldngIsRegularisationCharges;
	}

	public Boolean getBldngIsImprovementCharges() {
		return bldngIsImprovementCharges;
	}

	public void setBldngIsImprovementCharges(final Boolean bldngIsImprovementCharges) {
		this.bldngIsImprovementCharges = bldngIsImprovementCharges;
	}

	public BigDecimal getBldngAge() {
		return bldngAge;
	}

	public void setBldngAge(final BigDecimal bldngAge) {
		this.bldngAge = bldngAge;
	}

	public RoadType getRoadType() {
		return roadType;
	}

	public void setRoadType(final RoadType roadType) {
		this.roadType = roadType;
	}

	public BigDecimal getBldngFsiArea() {
		return bldngFsiArea;
	}

	public void setBldngFsiArea(final BigDecimal bldngFsiArea) {
		this.bldngFsiArea = bldngFsiArea;
	}

	public BigDecimal getFsb() {
		return fsb;
	}

	public void setFsb(final BigDecimal fsb) {
		this.fsb = fsb;
	}

	public BigDecimal getRsb() {
		return rsb;
	}

	public void setRsb(final BigDecimal rsb) {
		this.rsb = rsb;
	}

	public BigDecimal getSsb1() {
		return ssb1;
	}

	public void setSsb1(final BigDecimal ssb1) {
		this.ssb1 = ssb1;
	}

	public BigDecimal getSsb2() {
		return ssb2;
	}

	public void setSsb2(final BigDecimal ssb2) {
		this.ssb2 = ssb2;
	}

	public BigDecimal getPassageWidth() {
		return passageWidth;
	}

	public void setPassageWidth(final BigDecimal passageWidth) {
		this.passageWidth = passageWidth;
	}

	public BigDecimal getPassageLength() {
		return passageLength;
	}

	public void setPassageLength(final BigDecimal passageLength) {
		this.passageLength = passageLength;
	}

	public SurroundedBldgDtl getSurroundedByNorth() {
		return surroundedByNorth;
	}

	public void setSurroundedByNorth(final SurroundedBldgDtl surroundedByNorth) {
		this.surroundedByNorth = surroundedByNorth;
	}

	public SurroundedBldgDtl getSurroundedBySouth() {
		return surroundedBySouth;
	}

	public void setSurroundedBySouth(final SurroundedBldgDtl surroundedBySouth) {
		this.surroundedBySouth = surroundedBySouth;
	}

	public SurroundedBldgDtl getSurroundedByEast() {
		return surroundedByEast;
	}

	public void setSurroundedByEast(final SurroundedBldgDtl surroundedByEast) {
		this.surroundedByEast = surroundedByEast;
	}

	public SurroundedBldgDtl getSurroundedByWest() {
		return surroundedByWest;
	}

	public void setSurroundedByWest(final SurroundedBldgDtl surroundedByWest) {
		this.surroundedByWest = surroundedByWest;
	}

	public ConstructionStages getConstStages() {
		return constStages;
	}

	public void setConstStages(final ConstructionStages constStages) {
		this.constStages = constStages;
	}

	public BigDecimal getDwellingUnit() {
		return dwellingUnit;
	}

	public void setDwellingUnit(final BigDecimal dwellingUnit) {
		this.dwellingUnit = dwellingUnit;
	}

	public List<Docket> getDocket() {
		return docket;
	}

	public void setDocket(List<Docket> docket) {
		this.docket = docket;
	}

	public BpaApplication getApplication() {
		return application;
	}

	public void setApplication(final BpaApplication application) {
		this.application = application;
	}

	public BigDecimal getBldngGFloorTiledFloor() {
		return bldngGFloorTiledFloor;
	}

	public void setBldngGFloorTiledFloor(final BigDecimal bldngGFloorTiledFloor) {
		this.bldngGFloorTiledFloor = bldngGFloorTiledFloor;
	}

	public BigDecimal getBldngGFloorOtherTypes() {
		return bldngGFloorOtherTypes;
	}

	public void setBldngGFloorOtherTypes(final BigDecimal bldngGFloorOtherTypes) {
		this.bldngGFloorOtherTypes = bldngGFloorOtherTypes;
	}

	public BigDecimal getBldngFrstFloorTotalArea() {
		return bldngFrstFloorTotalArea;
	}

	public void setBldngFrstFloorTotalArea(final BigDecimal bldngFrstFloorTotalArea) {
		this.bldngFrstFloorTotalArea = bldngFrstFloorTotalArea;
	}

	public BigDecimal getBldngWellOhtSumpTankArea() {
		return bldngWellOhtSumpTankArea;
	}

	public void setBldngWellOhtSumpTankArea(final BigDecimal bldngWellOhtSumpTankArea) {
		this.bldngWellOhtSumpTankArea = bldngWellOhtSumpTankArea;
	}

	public List<DocketDetail> getDocketDetailLocList() {
		return docketDetailLocList;
	}

	public void setDocketDetailLocList(List<DocketDetail> docketDetailLocList) {
		this.docketDetailLocList = docketDetailLocList;
	}

	public List<DocketDetail> getDocketDetailAccessList() {
		return docketDetailAccessList;
	}

	public void setDocketDetailAccessList(List<DocketDetail> docketDetailAccessList) {
		this.docketDetailAccessList = docketDetailAccessList;
	}

	public List<DocketDetail> getDocketDetlSurroundingPlotList() {
		return docketDetlSurroundingPlotList;
	}

	public void setDocketDetlSurroundingPlotList(List<DocketDetail> docketDetlSurroundingPlotList) {
		this.docketDetlSurroundingPlotList = docketDetlSurroundingPlotList;
	}

	public List<DocketDetail> getDocketDetailLandTypeList() {
		return docketDetailLandTypeList;
	}

	public void setDocketDetailLandTypeList(List<DocketDetail> docketDetailLandTypeList) {
		this.docketDetailLandTypeList = docketDetailLandTypeList;
	}

	public List<DocketDetail> getDocketDetailProposedWorkList() {
		return docketDetailProposedWorkList;
	}

	public void setDocketDetailProposedWorkList(List<DocketDetail> docketDetailProposedWorkList) {
		this.docketDetailProposedWorkList = docketDetailProposedWorkList;
	}

	public List<DocketDetail> getDocketDetailWorkAsPerPlanList() {
		return docketDetailWorkAsPerPlanList;
	}

	public void setDocketDetailWorkAsPerPlanList(List<DocketDetail> docketDetailWorkAsPerPlanList) {
		this.docketDetailWorkAsPerPlanList = docketDetailWorkAsPerPlanList;
	}

	public List<DocketDetail> getDocketDetailHgtAbuttRoadList() {
		return docketDetailHgtAbuttRoadList;
	}

	public void setDocketDetailHgtAbuttRoadList(List<DocketDetail> docketDetailHgtAbuttRoadList) {
		this.docketDetailHgtAbuttRoadList = docketDetailHgtAbuttRoadList;
	}

	public List<DocketDetail> getDocketDetailMeasumentList() {
		return docketDetailMeasumentList;
	}

	public void setDocketDetailMeasumentList(List<DocketDetail> docketDetailMeasumentList) {
		this.docketDetailMeasumentList = docketDetailMeasumentList;
	}

	public List<DocketDetail> getDocketDetailAreaLoc() {
		return docketDetailAreaLoc;
	}

	public void setDocketDetailAreaLoc(List<DocketDetail> docketDetailAreaLoc) {
		this.docketDetailAreaLoc = docketDetailAreaLoc;
	}

	public List<DocketDetail> getDocketDetailLengthOfCompWall() {
		return docketDetailLengthOfCompWall;
	}

	public void setDocketDetailLengthOfCompWall(List<DocketDetail> docketDetailLengthOfCompWall) {
		this.docketDetailLengthOfCompWall = docketDetailLengthOfCompWall;
	}

	public List<DocketDetail> getDocketDetailNumberOfWell() {
		return docketDetailNumberOfWell;
	}

	public void setDocketDetailNumberOfWell(List<DocketDetail> docketDetailNumberOfWell) {
		this.docketDetailNumberOfWell = docketDetailNumberOfWell;
	}

	public List<DocketDetail> getDocketDetailErectionTower() {
		return docketDetailErectionTower;
	}

	public void setDocketDetailErectionTower(List<DocketDetail> docketDetailErectionTower) {
		this.docketDetailErectionTower = docketDetailErectionTower;
	}

	public List<DocketDetail> getDocketDetailShutter() {
		return docketDetailShutter;
	}

	public void setDocketDetailShutter(List<DocketDetail> docketDetailShutter) {
		this.docketDetailShutter = docketDetailShutter;
	}

	public List<DocketDetail> getDocketDetailRoofConversion() {
		return docketDetailRoofConversion;
	}

	public void setDocketDetailRoofConversion(List<DocketDetail> docketDetailRoofConversion) {
		this.docketDetailRoofConversion = docketDetailRoofConversion;
	}

	public List<String> getDeletedDocketDetailsFilestoreIds() {
		return deletedDocketDetailsFilestoreIds;
	}

	public void setDeletedDocketDetailsFilestoreIds(List<String> deletedDocketDetailsFilestoreIds) {
		this.deletedDocketDetailsFilestoreIds = deletedDocketDetailsFilestoreIds;
	}

	public Set<FileStoreMapper> getInspectionSupportDocs() {
		return inspectionSupportDocs;
	}

	public void setInspectionSupportDocs(Set<FileStoreMapper> inspectionSupportDocs) {
		this.inspectionSupportDocs = inspectionSupportDocs;
	}

	public Map<Long, String> getEncodedImages() {
		return encodedImages;
	}

	public void setEncodedImages(Map<Long, String> encodedImages) {
		this.encodedImages = encodedImages;
	}

	public MultipartFile[] getFiles() {
		return files;
	}

	public void setFiles(MultipartFile[] files) {
		this.files = files;
	}

	public boolean isBoundaryDrawingSubmitted() {
		return boundaryDrawingSubmitted;
	}

	public void setBoundaryDrawingSubmitted(boolean boundaryDrawingSubmitted) {
		this.boundaryDrawingSubmitted = boundaryDrawingSubmitted;
	}

	public boolean isRightToMakeConstruction() {
		return rightToMakeConstruction;
	}

	public void setRightToMakeConstruction(boolean rightToMakeConstruction) {
		this.rightToMakeConstruction = rightToMakeConstruction;
	}

	public String getTypeofLand() {
		return typeofLand;
	}

	public void setTypeofLand(String typeofLand) {
		this.typeofLand = typeofLand;
	}

	public List<PlanScrutinyChecklist> getPlanScrutinyChecklist() {
		return planScrutinyChecklist;
	}

	public void setPlanScrutinyChecklist(List<PlanScrutinyChecklist> planScrutinyChecklist) {
		this.planScrutinyChecklist = planScrutinyChecklist;
	}

	public List<PlanScrutinyChecklist> getPlanScrutinyChecklistForDrawing() {
		return planScrutinyChecklistForDrawing;
	}

	public void setPlanScrutinyChecklistForDrawing(List<PlanScrutinyChecklist> planScrutinyChecklistForDrawing) {
		this.planScrutinyChecklistForDrawing = planScrutinyChecklistForDrawing;
	}

	public List<PlanScrutinyChecklist> getPlanScrutinyChecklistTemp() {
		return planScrutinyChecklistTemp;
	}

	public void setPlanScrutinyChecklistTemp(List<PlanScrutinyChecklist> planScrutinyChecklistTemp) {
		this.planScrutinyChecklistTemp = planScrutinyChecklistTemp;
	}

	public List<PlanScrutinyChecklist> getPlanScrutinyChecklistForDrawingTemp() {
		return planScrutinyChecklistForDrawingTemp;
	}

	public void setPlanScrutinyChecklistForDrawingTemp(List<PlanScrutinyChecklist> planScrutinyChecklistForDrawingTemp) {
		this.planScrutinyChecklistForDrawingTemp = planScrutinyChecklistForDrawingTemp;
	}
}