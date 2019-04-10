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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Transient;

import org.egov.common.entity.bpa.SubOccupancy;
import org.egov.common.entity.bpa.Usage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*All the details extracted from the plan are referred in this object*/
@JsonIgnoreProperties(ignoreUnknown = true)
public class Plan implements Serializable {

    private static final long serialVersionUID = 7276648029097296311L;

    @JsonIgnore
    private ReportOutput reportOutput = new ReportOutput();
    private Utility utility = new Utility();
    private PlanInformation planInformation;
    private Plot plot;
    private List<Block> blocks = new ArrayList<>();
    private List<AccessoryBlock> accessoryBlocks = new ArrayList<>();
    private VirtualBuilding virtualBuilding;
    private Building building;
    private BigDecimal coverage = BigDecimal.ZERO;
    private BigDecimal coverageArea = BigDecimal.ZERO;
    private BigDecimal far = BigDecimal.ZERO;
    private BigDecimal totalBuiltUpArea;
    private BigDecimal totalFloorArea;
    private Boolean edcrPassed = false;
    private Date applicationDate;
    private transient List<ElectricLine> electricLine = new ArrayList<>();
    private transient List<NonNotifiedRoad> nonNotifiedRoads = new ArrayList<>();
    private transient List<NotifiedRoad> notifiedRoads = new ArrayList<>();
    private List<Occupancy> occupancies = new ArrayList<>();
    private transient List<CulDeSacRoad> culdeSacRoads = new ArrayList<>();
    private transient List<Lane> laneRoads = new ArrayList<>();
    private transient Map<String, String> errors = new LinkedHashMap<>();
    private transient Map<String, String> noObjectionCertificates = new HashMap<>();
    private transient List<BigDecimal> travelDistancesToExit = new ArrayList<>();
    private transient Map<String, String> generalInformation = new HashMap<>();
    private transient Basement basement;
    private transient ParkingDetails parkingDetails = new ParkingDetails();
    private transient List<BigDecimal> canopyDistanceFromPlotBoundary;
    @JsonIgnore
    private transient Map<Integer, org.egov.common.entity.bpa.Occupancy> occupanciesMaster = new HashMap<>();
    @JsonIgnore
    private transient Map<Integer, SubOccupancy> subOccupanciesMaster = new HashMap<>();
    @JsonIgnore
    private transient Map<Integer, Usage> usagesMaster = new HashMap<>();
    @JsonIgnore
    private transient Map<String, Integer> subFeatureColorCodesMaster = new HashMap<>();
    @Transient
    @JsonIgnore
    private Boolean inMeters = true;
    @Transient
    @JsonIgnore
    public StringBuffer additionsToDxf = new StringBuffer();
    @Transient
    private Boolean lengthFactor = true;
    @Transient
    private Double parkingRequired;

    @Transient
    private String dxfFileName;
    @Transient
    @JsonIgnore
    private List<EdcrPdfDetail> edcrPdfDetails;

    private transient List<BigDecimal> distancesFromMonument = new ArrayList<>();
    private transient List<BigDecimal> distancesFromGovtBuilding = new ArrayList<>();

    public List<BigDecimal> getCanopyDistanceFromPlotBoundary() {
        return canopyDistanceFromPlotBoundary;
    }

    public void setCanopyDistanceFromPlotBoundary(List<BigDecimal> canopyDistanceFromPlotBoundary) {
        this.canopyDistanceFromPlotBoundary = canopyDistanceFromPlotBoundary;
    }

    public List<BigDecimal> getTravelDistancesToExit() {
        return travelDistancesToExit;
    }

    public void setTravelDistancesToExit(List<BigDecimal> travelDistancesToExit) {
        this.travelDistancesToExit = travelDistancesToExit;
    }

    private List<BigDecimal> depthCuttings = new ArrayList<>();

    public List<BigDecimal> getDepthCuttings() {
        return depthCuttings;
    }

    public void setDepthCuttings(List<BigDecimal> depthCuttings) {
        this.depthCuttings = depthCuttings;
    }

    public List<AccessoryBlock> getAccessoryBlocks() {
        return accessoryBlocks;
    }

    public void setAccessoryBlocks(List<AccessoryBlock> accessoryBlocks) {
        this.accessoryBlocks = accessoryBlocks;
    }

    public List<Occupancy> getOccupancies() {
        return occupancies;
    }

    public void setOccupancies(List<Occupancy> occupancies) {
        this.occupancies = occupancies;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<Block> blocks) {
        this.blocks = blocks;
    }

    public Block getBlockByName(String blockName) {
        for (Block block : getBlocks()) {
            if (block.getName().equalsIgnoreCase(blockName))
                return block;
        }
        return null;
    }

    public Map<String, String> getNoObjectionCertificates() {
        return noObjectionCertificates;
    }

    public void setNoObjectionCertificates(Map<String, String> noObjectionCertificates) {
        this.noObjectionCertificates = noObjectionCertificates;
    }

    public List<CulDeSacRoad> getCuldeSacRoads() {
        return culdeSacRoads;
    }

    public void setCuldeSacRoads(List<CulDeSacRoad> culdeSacRoads) {
        this.culdeSacRoads = culdeSacRoads;
    }

    public List<Lane> getLaneRoads() {
        return laneRoads;
    }

    public void setLaneRoads(List<Lane> laneRoads) {
        this.laneRoads = laneRoads;
    }

    public List<ElectricLine> getElectricLine() {
        return electricLine;
    }

    public void setElectricLine(List<ElectricLine> electricLine) {
        this.electricLine = electricLine;
    }

    public Boolean getEdcrPassed() {
        return edcrPassed;
    }

    public void setEdcrPassed(Boolean edcrPassed) {
        this.edcrPassed = edcrPassed;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public List<NonNotifiedRoad> getNonNotifiedRoads() {
        return nonNotifiedRoads;
    }

    public void setNonNotifiedRoads(List<NonNotifiedRoad> nonNotifiedRoads) {
        this.nonNotifiedRoads = nonNotifiedRoads;
    }

    public List<NotifiedRoad> getNotifiedRoads() {
        return notifiedRoads;
    }

    public void setNotifiedRoads(List<NotifiedRoad> notifiedRoads) {
        this.notifiedRoads = notifiedRoads;
    }

    public Map<String, String> getGeneralInformation() {
        return generalInformation;
    }

    public void setGeneralInformation(Map<String, String> generalInformation) {
        this.generalInformation = generalInformation;
    }

    public void addGeneralInformation(Map<String, String> generalInformation) {
        if (generalInformation != null)
            getGeneralInformation().entrySet().add((Entry<String, String>) generalInformation);
    }

    public void addErrors(Map<String, String> errors) {
        if (errors != null)
            getErrors().putAll(errors);
    }

    public void addNocs(Map<String, String> nocs) {
        if (noObjectionCertificates != null)
            getNoObjectionCertificates().putAll(nocs);
    }

    public void addNoc(String key, String value) {

        if (noObjectionCertificates != null)
            getNoObjectionCertificates().put(key, value);
    }

    public void addError(String key, String value) {

        if (errors != null)
            getErrors().put(key, value);
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public PlanInformation getPlanInformation() {
        return planInformation;
    }

    public void setPlanInformation(PlanInformation planInformation) {
        this.planInformation = planInformation;
    }

    public Plot getPlot() {
        return plot;
    }

    public void setPlot(Plot plot) {
        this.plot = plot;
    }

    public VirtualBuilding getVirtualBuilding() {
        return virtualBuilding;
    }

    public void setVirtualBuilding(VirtualBuilding virtualBuilding) {
        this.virtualBuilding = virtualBuilding;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public Utility getUtility() {
        return utility;
    }

    public void setUtility(Utility utility) {
        this.utility = utility;
    }

    public Basement getBasement() {
        return basement;
    }

    public void setBasement(Basement basement) {
        this.basement = basement;
    }

    public BigDecimal getCoverage() {
        return coverage;
    }

    public void setCoverage(BigDecimal coverage) {
        this.coverage = coverage;
    }

    public BigDecimal getFar() {
        return far;
    }

    public void setFar(BigDecimal far) {
        this.far = far;
    }

    public BigDecimal getTotalBuiltUpArea() {
        return totalBuiltUpArea;
    }

    public void setTotalBuiltUpArea(BigDecimal totalBuiltUpArea) {
        this.totalBuiltUpArea = totalBuiltUpArea;
    }

    public BigDecimal getTotalFloorArea() {
        return totalFloorArea;
    }

    public void setTotalFloorArea(BigDecimal totalFloorArea) {
        this.totalFloorArea = totalFloorArea;
    }

    public void sortBlockByName() {
        if (!blocks.isEmpty())
            Collections.sort(blocks, Comparator.comparing(Block::getNumber));
    }

    public void sortSetBacksByLevel() {
        for (Block block : blocks)
            Collections.sort(block.getSetBacks(), Comparator.comparing(SetBack::getLevel));
    }

    public ParkingDetails getParkingDetails() {
        return parkingDetails;
    }

    public void setParkingDetails(ParkingDetails parkingDetails) {
        this.parkingDetails = parkingDetails;
    }

    public Map<Integer, org.egov.common.entity.bpa.Occupancy> getOccupanciesMaster() {
        return occupanciesMaster;
    }

    public void setOccupanciesMaster(Map<Integer, org.egov.common.entity.bpa.Occupancy> occupanciesMaster) {
        this.occupanciesMaster = occupanciesMaster;
    }

    public Map<Integer, SubOccupancy> getSubOccupanciesMaster() {
        return subOccupanciesMaster;
    }

    public void setSubOccupanciesMaster(Map<Integer, SubOccupancy> subOccupanciesMaster) {
        this.subOccupanciesMaster = subOccupanciesMaster;
    }

    public Map<Integer, Usage> getUsagesMaster() {
        return usagesMaster;
    }

    public void setUsagesMaster(Map<Integer, Usage> usagesMaster) {
        this.usagesMaster = usagesMaster;
    }

    public Map<String, Integer> getSubFeatureColorCodesMaster() {
        return subFeatureColorCodesMaster;
    }

    public void setSubFeatureColorCodesMaster(Map<String, Integer> subFeatureColorCodesMaster) {
        this.subFeatureColorCodesMaster = subFeatureColorCodesMaster;
    }

    public Boolean getInMeters() {
        return inMeters;
    }

    public void setInMeters(Boolean inMeters) {
        this.inMeters = inMeters;
    }

    public Boolean getLengthFactor() {
        return lengthFactor;
    }

    public void setLengthFactor(Boolean lengthFactor) {
        this.lengthFactor = lengthFactor;
    }

    public StringBuffer getAdditionsToDxf() {
        return additionsToDxf;
    }

    public void addToAdditionsToDxf(String s) {
        additionsToDxf.append(s);
    }

    public void setAdditionsToDxf(StringBuffer additionsToDxf) {
        this.additionsToDxf = additionsToDxf;
    }

    public BigDecimal getCoverageArea() {
        return coverageArea;
    }

    public void setCoverageArea(BigDecimal coverageArea) {
        this.coverageArea = coverageArea;
    }

    public Double getParkingRequired() {
        return parkingRequired;
    }

    public void setParkingRequired(Double parkingRequired) {
        this.parkingRequired = parkingRequired;
    }

    public String getDxfFileName() {
        return dxfFileName;
    }

    public void setDxfFileName(String dxfFileName) {
        this.dxfFileName = dxfFileName;
    }

    public List<EdcrPdfDetail> getEdcrPdfDetails() {
        return edcrPdfDetails;
    }

    public void setEdcrPdfDetails(List<EdcrPdfDetail> edcrPdfDetails) {
        this.edcrPdfDetails = edcrPdfDetails;
    }

    public ReportOutput getReportOutput() {
        return reportOutput;
    }

    public void setReportOutput(ReportOutput reportOutput) {
        this.reportOutput = reportOutput;
    }

    public List<BigDecimal> getDistancesFromMonument() {
        return distancesFromMonument;
    }

    public void setDistancesFromMonument(List<BigDecimal> distancesFromMonument) {
        this.distancesFromMonument = distancesFromMonument;
    }

	public List<BigDecimal> getDistancesFromGovtBuilding() {
		return distancesFromGovtBuilding;
	}

	public void setDistancesFromGovtBuilding(List<BigDecimal> distancesFromGovtBuilding) {
		this.distancesFromGovtBuilding = distancesFromGovtBuilding;
	}

}
