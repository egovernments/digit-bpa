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

@Document(indexName = "bpa", type = "bpa")
public class BpaIndex {

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
	private String applicantName;

	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String applicantMobileNumber;

	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String applicantEmailId;

	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String applicantAddress;

	@Field(type = FieldType.Double)
	private BigDecimal permitFee;

	@Field(type = FieldType.Double)
	private BigDecimal otherFee;

	@Field(type = FieldType.Double)
	private BigDecimal additionalFee;

	@Field(type = FieldType.Double)
	private BigDecimal applicationFee;

	@Field(type = FieldType.Double)
	private BigDecimal wellCharges;

	@Field(type = FieldType.Double)
	private BigDecimal compoundWellCharges;

	@Field(type = FieldType.Double)
	private BigDecimal shutterOrDoorConversionCharges;

	@Field(type = FieldType.Double)
	private BigDecimal roofConversionCharges;

	@Field(type = FieldType.Double)
	private BigDecimal totalDemandAmount;

	@Field(type = FieldType.Double)
	private BigDecimal totalCollectedAmount;

	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String applicantGender;

	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String aadhaarNumber;

	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String serviceType;

	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String applicationNumber;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ES_DATE_FORMAT, timezone = DEFAULT_TIMEZONE)
	@Field(type = FieldType.Date, format = DateFormat.date_optional_time, pattern = ES_DATE_FORMAT)
	private Date applicationDate;

	@Field(type = FieldType.Double)
	private BigDecimal admissionFeeAmount;

	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String stakeHolderName;

	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String stakeHolderType;

	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String remarks;

	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String planPermissionNumber;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ES_DATE_FORMAT, timezone = DEFAULT_TIMEZONE)
	@Field(type = FieldType.Date, format = DateFormat.date_optional_time, pattern = ES_DATE_FORMAT)
	private Date planPermissionDate;

	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String status;

	@Field(type = FieldType.Boolean)
	private Boolean isOneDayPermitApplication = false;

	// SiteDetailData
	@Field(type = FieldType.Double)
	private BigDecimal extentOfLand;

	@Field(type = FieldType.Double)
	private BigDecimal extentinsqmts;

	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String zone; // admin boundary parent

	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String revenueWard; // admin boundary

	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String village; // location boundary

	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String electionWard; // election boundary

	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String reSurveyNumber;

	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String natureofOwnership;

	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String registrarOffice;

	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String nearestbuildingnumber;

	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String doorNumber;

	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String streetAddress;

	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String locality;

	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String cityTown;

	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String pinCode;

	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String taluk;

	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String postOffice;

	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String district;

	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String state;

	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String scheme;

	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String landUsage;

	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String occupancy;

	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String governmentType;

	// plotDetailsData
	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private BigDecimal totalPlintArea;

	@Field(type = FieldType.Integer)
	private Integer floorCount;

	@Field(type = FieldType.Double)
	private BigDecimal noOfPoles;

	@Field(type = FieldType.Double)
	private BigDecimal noOfHutOrSheds;

	@Field(type = FieldType.Double)
	private BigDecimal roofConversion;

	@Field(type = FieldType.Double)
	private BigDecimal noOfShuttersOrDoors;

	@Field(type = FieldType.Double)
	private BigDecimal noOfTowers;

	@Field(type = FieldType.Double)
	private BigDecimal noOfWells;

	@Field(type = FieldType.Double)
	private BigDecimal lengthOfCompoundWall;

	@Field(type = FieldType.Double)
	private BigDecimal heightFromGroundWithStairRoom;

	@Field(type = FieldType.Double)
	private BigDecimal heightFromGroundWithOutStairRoom;

	@Field(type = FieldType.Double)
	private BigDecimal heightFromStreetLevelWithStairRoom;

	@Field(type = FieldType.Double)
	private BigDecimal heightFromStreetLevelWithOutStairRoom;

	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String eDcrNumber;

	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String typeOfLand;

	@Field(type = FieldType.Boolean)
	private Boolean isEconomicallyWeakerSection;

	@Field(type = FieldType.Boolean)
	private Boolean isappForRegularization = false;

	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String constStages;

	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String stateOfConstruction;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ES_DATE_FORMAT, timezone = DEFAULT_TIMEZONE)
	@Field(type = FieldType.Date, format = DateFormat.date_optional_time, pattern = ES_DATE_FORMAT)
	private Date workCommencementDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ES_DATE_FORMAT, timezone = DEFAULT_TIMEZONE)
	@Field(type = FieldType.Date, format = DateFormat.date_optional_time, pattern = ES_DATE_FORMAT)
	private Date workCompletionDate;

	@Field(type = FieldType.Integer)
	private Integer noOfDaysToProcess;
	
	@Field(type = FieldType.String, index = FieldIndex.not_analyzed) 
	private String revocationNumber; 
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ES_DATE_FORMAT, timezone = DEFAULT_TIMEZONE) 
	@Field(type = FieldType.Date, format = DateFormat.date_optional_time, pattern = ES_DATE_FORMAT) 
	private Date revocationDate;

	public String geteDcrNumber() {
		return eDcrNumber;
	}

	public void seteDcrNumber(String eDcrNumber) {
		this.eDcrNumber = eDcrNumber;
	}

	public String getTypeOfLand() {
		return typeOfLand;
	}

	public void setTypeOfLand(String typeOfLand) {
		this.typeOfLand = typeOfLand;
	}

	public Boolean getIsEconomicallyWeakerSection() {
		return isEconomicallyWeakerSection;
	}

	public void setIsEconomicallyWeakerSection(Boolean isEconomicallyWeakerSection) {
		this.isEconomicallyWeakerSection = isEconomicallyWeakerSection;
	}

	public Boolean getIsappForRegularization() {
		return isappForRegularization;
	}

	public void setIsappForRegularization(Boolean isappForRegularization) {
		this.isappForRegularization = isappForRegularization;
	}

	public String getConstStages() {
		return constStages;
	}

	public void setConstStages(String constStages) {
		this.constStages = constStages;
	}

	public String getStateOfConstruction() {
		return stateOfConstruction;
	}

	public void setStateOfConstruction(String stateOfConstruction) {
		this.stateOfConstruction = stateOfConstruction;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public String getApplicantMobileNumber() {
		return applicantMobileNumber;
	}

	public void setApplicantMobileNumber(String applicantMobileNumber) {
		this.applicantMobileNumber = applicantMobileNumber;
	}

	public String getApplicantEmailId() {
		return applicantEmailId;
	}

	public void setApplicantEmailId(String applicantEmailId) {
		this.applicantEmailId = applicantEmailId;
	}

	public String getApplicantAddress() {
		return applicantAddress;
	}

	public void setApplicantAddress(String applicantAddress) {
		this.applicantAddress = applicantAddress;
	}

	public String getApplicantGender() {
		return applicantGender;
	}

	public void setApplicantGender(String applicantGender) {
		this.applicantGender = applicantGender;
	}

	public String getAadhaarNumber() {
		return aadhaarNumber;
	}

	public void setAadhaarNumber(String aadhaarNumber) {
		this.aadhaarNumber = aadhaarNumber;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
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

	public BigDecimal getAdmissionFeeAmount() {
		return admissionFeeAmount;
	}

	public void setAdmissionFeeAmount(BigDecimal admissionfeeAmount) {
		admissionFeeAmount = admissionfeeAmount;
	}

	public String getStakeHolderName() {
		return stakeHolderName;
	}

	public void setStakeHolderName(String stakeHolderName) {
		this.stakeHolderName = stakeHolderName;
	}

	public String getStakeHolderType() {
		return stakeHolderType;
	}

	public void setStakeHolderType(String stakeHolderType) {
		this.stakeHolderType = stakeHolderType;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getPlanPermissionNumber() {
		return planPermissionNumber;
	}

	public void setPlanPermissionNumber(String planPermissionNumber) {
		this.planPermissionNumber = planPermissionNumber;
	}

	public Date getPlanPermissionDate() {
		return planPermissionDate;
	}

	public void setPlanPermissionDate(Date planPermissionDate) {
		this.planPermissionDate = planPermissionDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean getIsOneDayPermitApplication() {
		return isOneDayPermitApplication;
	}

	public void setIsOneDayPermitApplication(Boolean isOneDayPermitApplication) {
		this.isOneDayPermitApplication = isOneDayPermitApplication;
	}

	public BigDecimal getExtentOfLand() {
		return extentOfLand;
	}

	public void setExtentOfLand(BigDecimal extentOfLand) {
		this.extentOfLand = extentOfLand;
	}

	public BigDecimal getExtentinsqmts() {
		return extentinsqmts;
	}

	public void setExtentinsqmts(BigDecimal extentinsqmts) {
		this.extentinsqmts = extentinsqmts;
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

	public String getRevenueWard() {
		return revenueWard;
	}

	public void setRevenueWard(String revenueWard) {
		this.revenueWard = revenueWard;
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	public String getReSurveyNumber() {
		return reSurveyNumber;
	}

	public void setReSurveyNumber(String reSurveyNumber) {
		this.reSurveyNumber = reSurveyNumber;
	}

	public String getNatureofOwnership() {
		return natureofOwnership;
	}

	public void setNatureofOwnership(String natureofOwnership) {
		this.natureofOwnership = natureofOwnership;
	}

	public String getRegistrarOffice() {
		return registrarOffice;
	}

	public void setRegistrarOffice(String registrarOffice) {
		this.registrarOffice = registrarOffice;
	}

	public String getNearestbuildingnumber() {
		return nearestbuildingnumber;
	}

	public void setNearestbuildingnumber(String nearestbuildingnumber) {
		this.nearestbuildingnumber = nearestbuildingnumber;
	}

	public void setDoorNumber(String doorNumber) {
		this.doorNumber = doorNumber;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getCityTown() {
		return cityTown;
	}

	public void setCityTown(String cityTown) {
		this.cityTown = cityTown;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public String getTaluk() {
		return taluk;
	}

	public void setPlotTaluk(String taluk) {
		this.taluk = taluk;
	}

	public String getPostOffice() {
		return postOffice;
	}

	public void setPostOffice(String postOffice) {
		this.postOffice = postOffice;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public String getLandUsage() {
		return landUsage;
	}

	public void setLandUsage(String landUsage) {
		this.landUsage = landUsage;
	}

	public String getOccupancy() {
		return occupancy;
	}

	public void setOccupancy(String occupancy) {
		this.occupancy = occupancy;
	}

	public String getGovernmentType() {
		return governmentType;
	}

	public void setGovernmentType(String governmentType) {
		this.governmentType = governmentType;
	}

	public BigDecimal getTotalPlintArea() {
		return totalPlintArea;
	}

	public void setTotalPlintArea(BigDecimal totalPlintArea) {
		this.totalPlintArea = totalPlintArea;
	}

	public Integer getFloorCount() {
		return floorCount;
	}

	public void setFloorCount(Integer floorCount) {
		this.floorCount = floorCount;
	}

	public BigDecimal getHeightFromGroundWithStairRoom() {
		return heightFromGroundWithStairRoom;
	}

	public void setHeightFromGroundWithStairRoom(BigDecimal heightFromGroundWithStairRoom) {
		this.heightFromGroundWithStairRoom = heightFromGroundWithStairRoom;
	}

	public BigDecimal getHeightFromGroundWithOutStairRoom() {
		return heightFromGroundWithOutStairRoom;
	}

	public void setHeightFromGroundWithOutStairRoom(BigDecimal heightFromGroundWithOutStairRoom) {
		this.heightFromGroundWithOutStairRoom = heightFromGroundWithOutStairRoom;
	}

	public BigDecimal getHeightFromStreetLevelWithStairRoom() {
		return heightFromStreetLevelWithStairRoom;
	}

	public void setHeightFromStreetLevelWithStairRoom(BigDecimal heightFromStreetLevelWithStairRoom) {
		this.heightFromStreetLevelWithStairRoom = heightFromStreetLevelWithStairRoom;
	}

	public BigDecimal getHeightFromStreetLevelWithOutStairRoom() {
		return heightFromStreetLevelWithOutStairRoom;
	}

	public void setHeightFromStreetLevelWithOutStairRoom(BigDecimal heightFromStreetLevelWithOutStairRoom) {
		this.heightFromStreetLevelWithOutStairRoom = heightFromStreetLevelWithOutStairRoom;
	}

	public String getUlbName() {
		return ulbName;
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

	public BigDecimal getRoofConversion() {
		return roofConversion;
	}

	public void setRoofConversion(BigDecimal roofConversion) {
		this.roofConversion = roofConversion;
	}

	public BigDecimal getNoOfShuttersOrDoors() {
		return noOfShuttersOrDoors;
	}

	public void setNoOfShuttersOrDoors(BigDecimal noOfShuttersOrDoors) {
		this.noOfShuttersOrDoors = noOfShuttersOrDoors;
	}

	public BigDecimal getNoOfTowers() {
		return noOfTowers;
	}

	public void setNoOfTowers(BigDecimal noOfTowers) {
		this.noOfTowers = noOfTowers;
	}

	public BigDecimal getNoOfWells() {
		return noOfWells;
	}

	public void setNoOfWells(BigDecimal noOfWells) {
		this.noOfWells = noOfWells;
	}

	public BigDecimal getLengthOfCompoundWall() {
		return lengthOfCompoundWall;
	}

	public void setLengthOfCompoundWall(BigDecimal lengthOfCompoundWall) {
		this.lengthOfCompoundWall = lengthOfCompoundWall;
	}

	public String getDoorNumber() {
		return doorNumber;
	}

	public void setTaluk(String taluk) {
		this.taluk = taluk;
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

	public BigDecimal getPermitFee() {
		return permitFee;
	}

	public void setPermitFee(BigDecimal permitFee) {
		this.permitFee = permitFee;
	}

	public BigDecimal getOtherFee() {
		return otherFee;
	}

	public void setOtherFee(BigDecimal otherFee) {
		this.otherFee = otherFee;
	}

	public BigDecimal getAdditionalFee() {
		return additionalFee;
	}

	public void setAdditionalFee(BigDecimal additionalFee) {
		this.additionalFee = additionalFee;
	}

	public BigDecimal getWellCharges() {
		return wellCharges;
	}

	public void setWellCharges(BigDecimal wellCharges) {
		this.wellCharges = wellCharges;
	}

	public BigDecimal getCompoundWellCharges() {
		return compoundWellCharges;
	}

	public void setCompoundWellCharges(BigDecimal compoundWellCharges) {
		this.compoundWellCharges = compoundWellCharges;
	}

	public BigDecimal getShutterOrDoorConversionCharges() {
		return shutterOrDoorConversionCharges;
	}

	public void setShutterOrDoorConversionCharges(BigDecimal shutterOrDoorConversionCharges) {
		this.shutterOrDoorConversionCharges = shutterOrDoorConversionCharges;
	}

	public BigDecimal getRoofConversionCharges() {
		return roofConversionCharges;
	}

	public void setRoofConversionCharges(BigDecimal roofConversionCharges) {
		this.roofConversionCharges = roofConversionCharges;
	}

	public BigDecimal getTotalDemandAmount() {
		return totalDemandAmount;
	}

	public void setTotalDemandAmount(BigDecimal totalDemandAmount) {
		this.totalDemandAmount = totalDemandAmount;
	}

	public BigDecimal getTotalCollectedAmount() {
		return totalCollectedAmount;
	}

	public void setTotalCollectedAmount(BigDecimal totalCollectedAmount) {
		this.totalCollectedAmount = totalCollectedAmount;
	}

	public BigDecimal getApplicationFee() {
		return applicationFee;
	}

	public void setApplicationFee(BigDecimal applicationFee) {
		this.applicationFee = applicationFee;
	}

	public Integer getNoOfDaysToProcess() {
		return noOfDaysToProcess;
	}

	public void setNoOfDaysToProcess(Integer noOfDaysToProcess) {
		this.noOfDaysToProcess = noOfDaysToProcess;
	}

	public String getRevocationNumber() {
		return revocationNumber;
	}

	public void setRevocationNumber(String revocationNumber) {
		this.revocationNumber = revocationNumber;
	}

	public Date getRevocationDate() {
		return revocationDate;
	}

	public void setRevocationDate(Date revocationDate) {
		this.revocationDate = revocationDate;
	}

}
