/*
 * eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 * accountability and the service delivery of the government  organizations.
 *
 *  Copyright (C) <2019>  eGovernments Foundation
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

package org.egov.common.entity.edcr;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class PlanInformation implements Serializable {

    private static final String NA = "NA";
    public static final String SEQ_EDCR_PLANINFO = "SEQ_EDCR_PLANINFO";
    private static final long serialVersionUID = 4L;

    @Id
    @GeneratedValue(generator = SEQ_EDCR_PLANINFO, strategy = GenerationType.SEQUENCE)
    private Long id;

    private BigDecimal plotArea = BigDecimal.ZERO;

    private String ownerName;

    private String occupancy;

    private String serviceType;

    private String amenities;

    private String architectInformation;

    private Long acchitectId;

    private String applicantName;

    private Boolean crzZoneArea = true;

    private transient String crzZoneDesc = NA;

    private BigDecimal demolitionArea = BigDecimal.ZERO;

    private transient Boolean depthCutting;

    private transient String depthCuttingDesc = NA;

    private transient Boolean governmentOrAidedSchool;

    private transient Boolean securityZone = true;

    private transient String securityZoneDesc = NA;

    private transient BigDecimal accessWidth;

    private transient BigDecimal noOfBeds;

    private transient Boolean nocToAbutSide = false;

    private transient String nocToAbutSideDesc = NA;

    private transient Boolean nocToAbutRear = false;

    private transient String nocToAbutRearDesc = NA;

    private transient Boolean openingOnSide = false;

    private transient Boolean openingOnSideBelow2mts = false;

    private transient String openingOnSideBelow2mtsDesc = NA;

    private transient Boolean openingOnSideAbove2mts = false;

    private transient String openingOnSideAbove2mtsDesc = NA;

    private transient Boolean openingOnRearBelow2mts = false;

    private transient String openingOnRearBelow2mtsDesc = NA;

    private transient Boolean openingOnRearAbove2mts = false;

    private transient String openingOnRearAbove2mtsDesc = NA;

    /*
     * private transient Boolean nocToAbutAdjascentSide = false;
     */
    private transient Boolean openingOnRear = false;

    private transient Boolean parkingToMainBuilding = false;

    private transient Integer noOfSeats = 0;

    private transient Integer noOfMechanicalParking = 0;

    private transient Boolean singleFamilyBuilding;

    private String reSurveyNo;

    private String revenueWard;

    private String desam;

    private String village;

    private transient String zoneWise;

    private transient String landUseZone;
    
    private transient String leaseHoldLand;
            
    private BigDecimal roadWidth = BigDecimal.ZERO;
    
    private String typeOfArea;

    public Boolean getParkingToMainBuilding() {
        return parkingToMainBuilding;
    }

    public void setParkingToMainBuilding(Boolean parkingToMainBuilding) {
        this.parkingToMainBuilding = parkingToMainBuilding;
    }

    public Boolean getGovernmentOrAidedSchool() {
        return governmentOrAidedSchool;
    }

    public void setGovernmentOrAidedSchool(Boolean governmentOrAidedSchool) {
        this.governmentOrAidedSchool = governmentOrAidedSchool;
    }

    public Boolean getCrzZoneArea() {
        return crzZoneArea;
    }

    public void setCrzZoneArea(Boolean crzZoneArea) {
        this.crzZoneArea = crzZoneArea;
    }

    public BigDecimal getPlotArea() {
        return plotArea;
    }

    public void setPlotArea(BigDecimal plotArea) {
        this.plotArea = plotArea;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
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

    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }

    public String getArchitectInformation() {
        return architectInformation;
    }

    public void setArchitectInformation(String architectInformation) {
        this.architectInformation = architectInformation;
    }

    public Long getAcchitectId() {
        return acchitectId;
    }

    public void setAcchitectId(Long acchitectId) {
        this.acchitectId = acchitectId;
    }

    public String getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(String occupancy) {
        this.occupancy = occupancy;
    }

    public Boolean getSecurityZone() {
        return securityZone;
    }

    public void setSecurityZone(Boolean securityZone) {
        this.securityZone = securityZone;
    }

    public BigDecimal getAccessWidth() {
        return accessWidth;
    }

    public Boolean getDepthCutting() {
        return depthCutting;
    }

    public void setDepthCutting(Boolean depthCutting) {
        this.depthCutting = depthCutting;
    }

    public void setAccessWidth(BigDecimal accessWidth) {
        this.accessWidth = accessWidth;
    }

    /*
     * public Boolean getNocToAbutSide() { return nocToAbutSide; } public void setNocToAbutSide(Boolean nocToAbutSide) {
     * this.nocToAbutSide = nocToAbutSide; }
     */
    /*
     * public Boolean getNocToAbutRear() { return nocToAbutRear; } public void setNocToAbutRear(Boolean nocToAbutRear) {
     * this.nocToAbutRear = nocToAbutRear; }
     */

    public Boolean getOpeningOnSide() {
        return openingOnSide;
    }

    public void setOpeningOnSide(Boolean openingOnSide) {
        this.openingOnSide = openingOnSide;
    }

    /*
     * public Boolean getOpeningOnSideBelow2mts() { return openingOnSideBelow2mts; }
     */

    /*
     * public void setOpeningOnSideBelow2mts(Boolean openingOnSideBelow2mts) { this.openingOnSideBelow2mts =
     * openingOnSideBelow2mts; }
     */
    /*
     * public Boolean getOpeningOnSideAbove2mts() { return openingOnSideAbove2mts; }
     */

    /*
     * public void setOpeningOnSideAbove2mts(Boolean openingOnSideAbove2mts) { this.openingOnSideAbove2mts =
     * openingOnSideAbove2mts; }
     */

    /*
     * public Boolean getOpeningOnRearBelow2mts() { return openingOnRearBelow2mts; }
     */

    /*
     * public void setOpeningOnRearBelow2mts(Boolean openingOnRearBelow2mts) { this.openingOnRearBelow2mts =
     * openingOnRearBelow2mts; }
     */
    /*
     * public Boolean getOpeningOnRearAbove2mts() { return openingOnRearAbove2mts; }
     */

    /*
     * public void setOpeningOnRearAbove2mts(Boolean openingOnRearAbove2mts) { this.openingOnRearAbove2mts =
     * openingOnRearAbove2mts; }
     */

    /*
     * public Boolean getNocToAbutAdjascentSide() { return nocToAbutAdjascentSide; } public void setNocToAbutAdjascentSide(Boolean
     * nocToAbutAdjascentSide) { this.nocToAbutAdjascentSide = nocToAbutAdjascentSide; }
     */

    public Boolean getOpeningOnRear() {
        return openingOnRear;
    }

    public void setOpeningOnRear(Boolean openingOnRear) {
        this.openingOnRear = openingOnRear;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public BigDecimal getNoOfBeds() {
        return noOfBeds;
    }

    public void setNoOfBeds(BigDecimal noOfBeds) {
        this.noOfBeds = noOfBeds;
    }

    public Integer getNoOfSeats() {
        return noOfSeats;
    }

    public void setNoOfSeats(Integer noOfSeats) {
        this.noOfSeats = noOfSeats;
    }

    public Integer getNoOfMechanicalParking() {
        return noOfMechanicalParking;
    }

    public void setNoOfMechanicalParking(Integer noOfMechanicalParking) {
        this.noOfMechanicalParking = noOfMechanicalParking;
    }

    public Boolean getSingleFamilyBuilding() {
        return singleFamilyBuilding;
    }

    public void setSingleFamilyBuilding(Boolean singleFamilyBuilding) {
        this.singleFamilyBuilding = singleFamilyBuilding;
    }

    public BigDecimal getDemolitionArea() {
        return demolitionArea;
    }

    public void setDemolitionArea(BigDecimal demolitionArea) {
        this.demolitionArea = demolitionArea;
    }

    public String getReSurveyNo() {
        return reSurveyNo;
    }

    public void setReSurveyNo(String reSurveyNo) {
        this.reSurveyNo = reSurveyNo;
    }

    public String getRevenueWard() {
        return revenueWard;
    }

    public void setRevenueWard(String revenueWard) {
        this.revenueWard = revenueWard;
    }

    public String getDesam() {
        return desam;
    }

    public void setDesam(String desam) {
        this.desam = desam;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getCrzZoneDesc() {
        return crzZoneDesc;
    }

    public void setCrzZoneDesc(String crzZoneDesc) {
        this.crzZoneDesc = crzZoneDesc;
    }

    public String getSecurityZoneDesc() {
        return securityZoneDesc;
    }

    public void setSecurityZoneDesc(String securityZoneDesc) {
        this.securityZoneDesc = securityZoneDesc;
    }

    public String getOpeningOnSideBelow2mtsDesc() {
        return openingOnSideBelow2mtsDesc;
    }

    public void setOpeningOnSideBelow2mtsDesc(String openingOnSideBelow2mtsDesc) {
        this.openingOnSideBelow2mtsDesc = openingOnSideBelow2mtsDesc;
    }

    public String getOpeningOnSideAbove2mtsDesc() {
        return openingOnSideAbove2mtsDesc;
    }

    public void setOpeningOnSideAbove2mtsDesc(String openingOnSideAbove2mtsDesc) {
        this.openingOnSideAbove2mtsDesc = openingOnSideAbove2mtsDesc;
    }

    public String getOpeningOnRearBelow2mtsDesc() {
        return openingOnRearBelow2mtsDesc;
    }

    public void setOpeningOnRearBelow2mtsDesc(String openingOnRearBelow2mtsDesc) {
        this.openingOnRearBelow2mtsDesc = openingOnRearBelow2mtsDesc;
    }

    public String getOpeningOnRearAbove2mtsDesc() {
        return openingOnRearAbove2mtsDesc;
    }

    public void setOpeningOnRearAbove2mtsDesc(String openingOnRearAbove2mtsDesc) {
        this.openingOnRearAbove2mtsDesc = openingOnRearAbove2mtsDesc;
    }

    public String getNocToAbutSideDesc() {
        return nocToAbutSideDesc;
    }

    public void setNocToAbutSideDesc(String nocToAbutSideDesc) {
        this.nocToAbutSideDesc = nocToAbutSideDesc;
    }

    public String getNocToAbutRearDesc() {
        return nocToAbutRearDesc;
    }

    public void setNocToAbutRearDesc(String nocToAbutRearDesc) {
        this.nocToAbutRearDesc = nocToAbutRearDesc;
    }

    public String getDepthCuttingDesc() {
        return depthCuttingDesc;
    }

    public void setDepthCuttingDesc(String depthCuttingDesc) {
        this.depthCuttingDesc = depthCuttingDesc;
    }

	public String getZoneWise() {
		return zoneWise;
	}

	public void setZoneWise(String zoneWise) {
		this.zoneWise = zoneWise;
	}

	public String getLandUseZone() {
		return landUseZone;
	}

	public void setLandUseZone(String landUseZone) {
		this.landUseZone = landUseZone;
	}

	public String getLeaseHoldLand() {
		return leaseHoldLand;
	}

	public void setLeaseHoldLand(String leaseHoldLand) {
		this.leaseHoldLand = leaseHoldLand;
	}

	public BigDecimal getRoadWidth() {
		return roadWidth;
	}

	public void setRoadWidth(BigDecimal roadWidth) {
		this.roadWidth = roadWidth;
	}

	public String getTypeOfArea() {
		return typeOfArea;
	}

	public void setTypeOfArea(String typeOfArea) {
		this.typeOfArea = typeOfArea;
	}

}
