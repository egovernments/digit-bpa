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

package org.egov.edcr.feature;

import static org.egov.edcr.constants.DxfFileConstants.BLOCK_NAME_PREFIX;
import static org.egov.edcr.constants.DxfFileConstants.FLOOR_NAME_PREFIX;
import static org.egov.edcr.utility.DcrConstants.DECIMALDIGITS_MEASUREMENTS;
import static org.egov.edcr.utility.DcrConstants.OBJECTNOTDEFINED;
import static org.egov.edcr.utility.DcrConstants.PLOT_AREA;
import static org.egov.edcr.utility.DcrConstants.ROUNDMODE_MEASUREMENTS;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.egov.common.entity.edcr.Block;
import org.egov.common.entity.edcr.Building;
import org.egov.common.entity.edcr.Floor;
import org.egov.common.entity.edcr.Occupancy;
import org.egov.common.entity.edcr.OccupancyType;
import org.egov.common.entity.edcr.Plan;
import org.egov.common.entity.edcr.Result;
import org.egov.common.entity.edcr.ScrutinyDetail;
import org.egov.edcr.constants.DxfFileConstants;
import org.egov.edcr.service.ProcessHelper;
import org.egov.edcr.service.ProcessPrintHelper;
import org.springframework.stereotype.Service;

@Service
public class Far extends FeatureProcess {

    private static final Logger LOG = Logger.getLogger(Far.class);

    private static final String RULE_NAME_KEY = "far.rulename";
    private static final String RULE_DESCRIPTION_KEY = "far.description";
    private static final String RULE_EXPECTED_KEY = "far.expected";
    private static final String RULE_ACTUAL_KEY = "far.actual";
    private static final String RULE_ACTUAL_KEY_NO_FINE = "far.actual.nofine";
    private static final String RULE_DESCRIPTION_KEY_WEIGHTED = "weighted.far.description";
    private static final String RULE_EXPECTED_KEY_WEIGHTED = "weighted.far.expected";
    private static final String RULE_ACTUAL_KEY_WEIGHTED = "weighted.far.actual";
    private static final String VALIDATION_WRONG_COLORCODE_FLOORAREA = "msg.error.wrong.colourcode.floorarea";
    private static final String OCCUPANCY = "Occupancy";

    private static final BigDecimal onePointFive = BigDecimal.valueOf(1.5);
    private static final BigDecimal two = BigDecimal.valueOf(2.0);
    private static final BigDecimal twoPointFive = BigDecimal.valueOf(2.5);
    private static final BigDecimal three = BigDecimal.valueOf(3.0);
    private static final BigDecimal threePointFive = BigDecimal.valueOf(3.5);
    private static final BigDecimal four = BigDecimal.valueOf(4.0);
    private static final String VALIDATION_NEGATIVE_FLOOR_AREA = "msg.error.negative.floorarea.occupancy.floor";
    private static final String VALIDATION_NEGATIVE_EXISTING_FLOOR_AREA = "msg.error.negative.existing.floorarea.occupancy.floor";
    private static final String VALIDATION_NEGATIVE_BUILTUP_AREA = "msg.error.negative.builtuparea.occupancy.floor";
    private static final String VALIDATION_NEGATIVE_EXISTING_BUILTUP_AREA = "msg.error.negative.existing.builtuparea.occupancy.floor";
    public static final String RULE_31_1 = "31(1)";

    String farDeductByFloor = BLOCK_NAME_PREFIX + "%s" + "_" + FLOOR_NAME_PREFIX + "%s" + "_"
            + DxfFileConstants.BUILT_UP_AREA_DEDUCT;

    @Override
    public Plan validate(Plan pl) {
        if (pl.getPlot().getArea() == null || pl.getPlot().getArea().doubleValue() == 0) {
            pl.addError(PLOT_AREA, getLocaleMessage(OBJECTNOTDEFINED, PLOT_AREA));
        }
        // IF SINGLE FAMILY BUILDING DECLARED AND COLOUR CODE 25 NOT USED IN FLOOR LEVEL, THEN THROW ERROR.

        return pl;
    }

    @Override
    public Plan process(Plan pl) {

        int errors = pl.getErrors().size();
        validate(pl);
        int validatedErrors = pl.getErrors().size();
        if (validatedErrors > errors) {
            return pl;
        }
        BigDecimal totalExistingBuiltUpArea = BigDecimal.ZERO;
        BigDecimal totalExistingFloorArea = BigDecimal.ZERO;
        BigDecimal totalBuiltUpArea = BigDecimal.ZERO;
        BigDecimal totalFloorArea = BigDecimal.ZERO;
        EnumSet<OccupancyType> distinctOccupancyTypes = EnumSet.noneOf(OccupancyType.class);
        for (Block blk : pl.getBlocks()) {
            BigDecimal flrArea = BigDecimal.ZERO;
            BigDecimal bltUpArea = BigDecimal.ZERO;
            BigDecimal existingFlrArea = BigDecimal.ZERO;
            BigDecimal existingBltUpArea = BigDecimal.ZERO;
            Building building = blk.getBuilding();
            for (Floor flr : building.getFloors()) {
                for (Occupancy occupancy : flr.getOccupancies()) {
                    validate2(pl, blk, flr, occupancy);

                    occupancy.setCarpetArea(occupancy.getFloorArea().multiply(BigDecimal.valueOf(0.80)));

                    occupancy.setExistingCarpetArea(occupancy.getExistingFloorArea().multiply(BigDecimal.valueOf(0.80)));

                    bltUpArea = bltUpArea
                            .add(occupancy.getBuiltUpArea() == null ? BigDecimal.valueOf(0) : occupancy.getBuiltUpArea());

                    flrArea = flrArea.add(occupancy.getFloorArea());

                    existingFlrArea = existingFlrArea.add(occupancy.getExistingFloorArea());

                    existingBltUpArea = existingBltUpArea.add(occupancy.getExistingBuiltUpArea() == null ? BigDecimal.valueOf(0)
                            : occupancy.getExistingBuiltUpArea());

                }
            }
            building.setTotalFloorArea(flrArea);

            building.setTotalBuitUpArea(bltUpArea);

            building.setTotalExistingBuiltUpArea(existingBltUpArea);

            building.setTotalExistingFloorArea(existingFlrArea);

            // check block is completely existing building or not.
            if (existingBltUpArea.compareTo(bltUpArea) == 0)
                blk.setCompletelyExisting(Boolean.TRUE);

            totalFloorArea = totalFloorArea.add(flrArea);
            totalBuiltUpArea = totalBuiltUpArea.add(bltUpArea);
            totalExistingBuiltUpArea = totalExistingBuiltUpArea.add(existingBltUpArea);
            totalExistingFloorArea = totalExistingFloorArea.add(existingFlrArea);

            /*
             * BigDecimal far = flrArea.divide(pl.getPlot().getArea(), DcrConstants.DECIMALDIGITS_MEASUREMENTS,
             * DcrConstants.ROUNDMODE_MEASUREMENTS); blk.getBuilding().setFar(far);
             */
            // occupancy conversion logic

            // Find Occupancies by block and add
            Set<OccupancyType> occupancyByBlock = new HashSet<>();
            for (Floor flr : building.getFloors()) {
                for (Occupancy occupancy : flr.getOccupancies()) {
                    occupancyByBlock.add(occupancy.getType());
                }
            }

            List<Map<String, Object>> listOfMapOfAllDtls = new ArrayList<>();
            List<OccupancyType> listOfOccupancyTypes = new ArrayList<>();

            for (OccupancyType occupancyType : occupancyByBlock) {

                Map<String, Object> allDtlsMap = new HashMap<>();
                BigDecimal blockWiseFloorArea = BigDecimal.ZERO;
                BigDecimal blockWiseBuiltupArea = BigDecimal.ZERO;
                BigDecimal blockWiseExistingFloorArea = BigDecimal.ZERO;
                BigDecimal blockWiseExistingBuiltupArea = BigDecimal.ZERO;
                for (Floor flr : blk.getBuilding().getFloors()) {
                    for (Occupancy occupancy : flr.getOccupancies()) {
                        if (occupancy.getType().equals(occupancyType)) {
                            blockWiseFloorArea = blockWiseFloorArea
                                    .add(occupancy.getFloorArea());

                            blockWiseBuiltupArea = blockWiseBuiltupArea
                                    .add(occupancy.getBuiltUpArea() == null ? BigDecimal.valueOf(0) : occupancy.getBuiltUpArea());
                            blockWiseExistingFloorArea = blockWiseExistingFloorArea

                                    .add(occupancy.getExistingFloorArea());

                            blockWiseExistingBuiltupArea = blockWiseExistingBuiltupArea
                                    .add(occupancy.getExistingBuiltUpArea() == null ? BigDecimal.valueOf(0)
                                            : occupancy.getExistingBuiltUpArea());

                        }
                    }
                }
                Occupancy occupancy = new Occupancy();
                occupancy.setBuiltUpArea(blockWiseBuiltupArea);
                occupancy.setFloorArea(blockWiseFloorArea);
                occupancy.setExistingFloorArea(blockWiseExistingFloorArea);
                occupancy.setExistingBuiltUpArea(blockWiseExistingBuiltupArea);
                occupancy.setCarpetArea(blockWiseFloorArea.multiply(BigDecimal.valueOf(.80)));
                occupancy.setType(occupancyType);
                building.getTotalArea().add(occupancy);

                // this util access not valid move this inside edcr
                OccupancyType occupancyTypeAsPerFloorArea = ProcessHelper.getOccupancyAsPerFloorArea(occupancyType,
                        blockWiseFloorArea);

                allDtlsMap.put("occupancy", occupancyTypeAsPerFloorArea);
                allDtlsMap.put("totalFloorArea", blockWiseFloorArea);
                allDtlsMap.put("totalBuiltUpArea", blockWiseBuiltupArea);
                allDtlsMap.put("existingFloorArea", blockWiseExistingFloorArea);
                allDtlsMap.put("existingBuiltUpArea", blockWiseExistingBuiltupArea);

                listOfOccupancyTypes.add(occupancyTypeAsPerFloorArea);

                listOfMapOfAllDtls.add(allDtlsMap);
            }
            Set<OccupancyType> setOfOccupancyTypes = new HashSet<>(listOfOccupancyTypes);

            List<Occupancy> listOfOccupanciesOfAParticularblock = new ArrayList<>();
            // fpr each distinct converted occupancy types
            for (OccupancyType occupancyType : setOfOccupancyTypes) {
                if (occupancyType != null) {
                    Occupancy occupancy = new Occupancy();
                    BigDecimal totalFlrArea = BigDecimal.ZERO;
                    BigDecimal totalBltUpArea = BigDecimal.ZERO;
                    BigDecimal totalExistingFlrArea = BigDecimal.ZERO;
                    BigDecimal totalExistingBltUpArea = BigDecimal.ZERO;

                    for (Map<String, Object> dtlsMap : listOfMapOfAllDtls) {
                        if (occupancyType.equals(dtlsMap.get("occupancy"))) {
                            totalFlrArea = totalFlrArea.add((BigDecimal) dtlsMap.get("totalFloorArea"));
                            totalBltUpArea = totalBltUpArea.add((BigDecimal) dtlsMap.get("totalBuiltUpArea"));

                            totalExistingBltUpArea = totalExistingBltUpArea.add((BigDecimal) dtlsMap.get("existingBuiltUpArea"));
                            totalExistingFlrArea = totalExistingFlrArea.add((BigDecimal) dtlsMap.get("existingFloorArea"));

                        }
                    }
                    occupancy.setType(occupancyType);
                    occupancy.setBuiltUpArea(totalBltUpArea);
                    occupancy.setFloorArea(totalFlrArea);
                    occupancy.setExistingBuiltUpArea(totalExistingBltUpArea);
                    occupancy.setExistingFloorArea(totalExistingFlrArea);
                    occupancy.setExistingCarpetArea(totalExistingFlrArea.multiply(BigDecimal.valueOf(0.80)));
                    occupancy.setCarpetArea(totalFlrArea.multiply(BigDecimal.valueOf(0.80)));

                    listOfOccupanciesOfAParticularblock.add(occupancy);
                }
            }
            blk.getBuilding().setOccupancies(listOfOccupanciesOfAParticularblock);

            if (!listOfOccupanciesOfAParticularblock.isEmpty()) {
                // listOfOccupanciesOfAParticularblock already converted. In case of professional building type, converted into A1
                // type
                boolean singleFamilyBuildingTypeOccupancyPresent = false;
                boolean otherThanSingleFamilyOccupancyTypePresent = false;

                for (Occupancy occupancy : listOfOccupanciesOfAParticularblock) {

                    {
                        if (occupancy.getType().equals(OccupancyType.OCCUPANCY_A1))
                            singleFamilyBuildingTypeOccupancyPresent = true;
                        else {
                            otherThanSingleFamilyOccupancyTypePresent = true;
                            break;
                        }
                    }
                }
                if (!otherThanSingleFamilyOccupancyTypePresent && singleFamilyBuildingTypeOccupancyPresent)
                    blk.setSingleFamilyBuilding(true);
                else
                    blk.setSingleFamilyBuilding(false);

                int allResidentialOccTypes = 0;
                int allResidentialOrCommercialOccTypes = 0;

                for (Occupancy occupancy : listOfOccupanciesOfAParticularblock) {
                    // System.out.println("occupancy :" + occupancy.getType());
                    if (occupancy.getType() != null) {
                        // setting residentialBuilding
                        int residentialOccupancyType = 0;
                        if (occupancy.getType().equals(OccupancyType.OCCUPANCY_A1)
                                || occupancy.getType().equals(OccupancyType.OCCUPANCY_A4)) {
                            residentialOccupancyType = 1;
                        }
                        if (residentialOccupancyType == 0) {
                            allResidentialOccTypes = 0;
                            break;
                        } else {
                            allResidentialOccTypes = 1;
                        }
                    }
                }
                if (allResidentialOccTypes == 1) {
                    blk.setResidentialBuilding(true);
                } else {
                    blk.setResidentialBuilding(false);
                }

                for (Occupancy occupancy : listOfOccupanciesOfAParticularblock) {
                    if (occupancy.getType() != null) {
                        // setting residentialOrCommercial Occupancy Type
                        int residentialOrCommercialOccupancyType = 0;
                        if (occupancy.getType().equals(OccupancyType.OCCUPANCY_A1)
                                || occupancy.getType().equals(OccupancyType.OCCUPANCY_A4) ||
                                occupancy.getType().equals(OccupancyType.OCCUPANCY_F)
                                || occupancy.getType().equals(OccupancyType.OCCUPANCY_F1) ||
                                occupancy.getType().equals(OccupancyType.OCCUPANCY_F2)
                                || occupancy.getType().equals(OccupancyType.OCCUPANCY_F3)
                                || occupancy.getType().equals(OccupancyType.OCCUPANCY_F4)) {
                            residentialOrCommercialOccupancyType = 1;
                        }
                        if (residentialOrCommercialOccupancyType == 0) {
                            allResidentialOrCommercialOccTypes = 0;
                            break;
                        } else {
                            allResidentialOrCommercialOccTypes = 1;
                        }
                    }
                }
                if (allResidentialOrCommercialOccTypes == 1) {
                    blk.setResidentialOrCommercialBuilding(true);
                } else {
                    blk.setResidentialOrCommercialBuilding(false);
                }
            }

            if (blk.getBuilding().getFloors() != null && !blk.getBuilding().getFloors().isEmpty()) {
                BigDecimal noOfFloorsAboveGround = BigDecimal.ZERO;
                for (Floor floor : blk.getBuilding().getFloors()) {
                    if (floor.getNumber() != null && floor.getNumber() >= 0) {
                        noOfFloorsAboveGround = noOfFloorsAboveGround.add(BigDecimal.valueOf(1));
                    }
                }

                boolean hasTerrace = blk.getBuilding().getFloors().stream()
                        .anyMatch(floor -> floor.getTerrace().equals(Boolean.TRUE));

                noOfFloorsAboveGround = hasTerrace ? noOfFloorsAboveGround.subtract(BigDecimal.ONE) : noOfFloorsAboveGround;

                blk.getBuilding().setMaxFloor(noOfFloorsAboveGround);
                blk.getBuilding().setFloorsAboveGround(noOfFloorsAboveGround);
                blk.getBuilding().setTotalFloors(BigDecimal.valueOf(blk.getBuilding().getFloors().size()));
            }

        }

        List<OccupancyType> plotWiseOccupancy = new ArrayList<>();
        for (Block block : pl.getBlocks()) {
            for (Occupancy occupancy : block.getBuilding().getOccupancies()) {
                if (occupancy.getType() != null)
                    plotWiseOccupancy.add(occupancy.getType());
            }
        }

        Set<OccupancyType> setOfDistinctOccupancies = new HashSet<>(plotWiseOccupancy);

        distinctOccupancyTypes.addAll(setOfDistinctOccupancies);

        List<Occupancy> occupanciesForPlan = new ArrayList<>();

        for (OccupancyType occupancyType : setOfDistinctOccupancies) {
            if (occupancyType != null) {
                BigDecimal totalFloorAreaForAllBlks = BigDecimal.ZERO;
                BigDecimal totalBuiltUpAreaForAllBlks = BigDecimal.ZERO;
                BigDecimal totalCarpetAreaForAllBlks = BigDecimal.ZERO;
                BigDecimal totalExistBuiltUpAreaForAllBlks = BigDecimal.ZERO;
                BigDecimal totalExistFloorAreaForAllBlks = BigDecimal.ZERO;
                BigDecimal totalExistCarpetAreaForAllBlks = BigDecimal.ZERO;
                Occupancy occupancy = new Occupancy();
                for (Block block : pl.getBlocks()) {
                    for (Occupancy buildingOccupancy : block.getBuilding().getOccupancies()) {
                        if (occupancyType.equals(buildingOccupancy.getType())) {
                            totalFloorAreaForAllBlks = totalFloorAreaForAllBlks.add(buildingOccupancy.getFloorArea());
                            totalBuiltUpAreaForAllBlks = totalBuiltUpAreaForAllBlks.add(buildingOccupancy.getBuiltUpArea());
                            totalCarpetAreaForAllBlks = totalCarpetAreaForAllBlks.add(buildingOccupancy.getCarpetArea());
                            totalExistBuiltUpAreaForAllBlks = totalExistBuiltUpAreaForAllBlks
                                    .add(buildingOccupancy.getExistingBuiltUpArea());
                            totalExistFloorAreaForAllBlks = totalExistFloorAreaForAllBlks
                                    .add(buildingOccupancy.getExistingFloorArea());
                            totalExistCarpetAreaForAllBlks = totalExistCarpetAreaForAllBlks
                                    .add(buildingOccupancy.getExistingCarpetArea());
                        }
                    }
                }
                occupancy.setType(occupancyType);
                occupancy.setBuiltUpArea(totalBuiltUpAreaForAllBlks);
                occupancy.setCarpetArea(totalCarpetAreaForAllBlks);
                occupancy.setFloorArea(totalFloorAreaForAllBlks);
                occupancy.setExistingBuiltUpArea(totalExistBuiltUpAreaForAllBlks);
                occupancy.setExistingFloorArea(totalExistFloorAreaForAllBlks);
                occupancy.setExistingCarpetArea(totalExistCarpetAreaForAllBlks);
                occupanciesForPlan.add(occupancy);
            }
        }
        pl.setOccupancies(occupanciesForPlan);
        pl.getVirtualBuilding().setTotalFloorArea(totalFloorArea);
        pl.getVirtualBuilding().setTotalCarpetArea(totalFloorArea.multiply(BigDecimal.valueOf(0.80)));
        pl.getVirtualBuilding().setTotalExistingBuiltUpArea(totalExistingBuiltUpArea);
        pl.getVirtualBuilding().setTotalExistingFloorArea(totalExistingFloorArea);
        pl.getVirtualBuilding().setTotalExistingCarpetArea(totalExistingFloorArea.multiply(BigDecimal.valueOf(0.80)));
        pl.getVirtualBuilding().setOccupancies(distinctOccupancyTypes);
        pl.getVirtualBuilding().setTotalBuitUpArea(totalBuiltUpArea);
        pl.getVirtualBuilding().setMostRestrictiveFar(getMostRestrictiveFar(distinctOccupancyTypes));

        if (!distinctOccupancyTypes.isEmpty()) {
            int allResidentialOccTypesForPlan = 0;
            for (OccupancyType occupancy : distinctOccupancyTypes) {
                LOG.info("occupancy :" + occupancy);
                // setting residentialBuilding
                int residentialOccupancyType = 0;
                if (occupancy.equals(OccupancyType.OCCUPANCY_A1) || occupancy.equals(OccupancyType.OCCUPANCY_A4)) {
                    residentialOccupancyType = 1;
                }
                if (residentialOccupancyType == 0) {
                    allResidentialOccTypesForPlan = 0;
                    break;
                } else {
                    allResidentialOccTypesForPlan = 1;
                }
            }
            if (allResidentialOccTypesForPlan == 1) {
                pl.getVirtualBuilding().setResidentialBuilding(true);
            } else {
                pl.getVirtualBuilding().setResidentialBuilding(false);
            }
            int allResidentialOrCommercialOccTypesForPlan = 0;
            for (OccupancyType occupancyType : distinctOccupancyTypes) {
                int residentialOrCommercialOccupancyTypeForPlan = 0;
                if (occupancyType.equals(OccupancyType.OCCUPANCY_A1) || occupancyType.equals(OccupancyType.OCCUPANCY_A4) ||
                        occupancyType.equals(OccupancyType.OCCUPANCY_F) || occupancyType.equals(OccupancyType.OCCUPANCY_F1) ||
                        occupancyType.equals(OccupancyType.OCCUPANCY_F2) || occupancyType.equals(OccupancyType.OCCUPANCY_F3) ||
                        occupancyType.equals(OccupancyType.OCCUPANCY_F4)) {
                    residentialOrCommercialOccupancyTypeForPlan = 1;
                }
                if (residentialOrCommercialOccupancyTypeForPlan == 0) {
                    allResidentialOrCommercialOccTypesForPlan = 0;
                    break;
                } else {
                    allResidentialOrCommercialOccTypesForPlan = 1;
                }
            }
            if (allResidentialOrCommercialOccTypesForPlan == 1) {
                pl.getVirtualBuilding().setResidentialOrCommercialBuilding(true);
            } else {
                pl.getVirtualBuilding().setResidentialOrCommercialBuilding(false);
            }
        }
        OccupancyType mostRestrictiveOccupancy = pl.getVirtualBuilding().getMostRestrictiveFar();
        BigDecimal far = BigDecimal.ZERO;
        if (pl.getPlot().getArea().doubleValue() > 0)
            far = pl.getVirtualBuilding().getTotalFloorArea().divide(pl.getPlot().getArea(), DECIMALDIGITS_MEASUREMENTS,
                    ROUNDMODE_MEASUREMENTS);

        pl.setFar(far);
        if (!ProcessHelper.isSmallPlot(pl)) {
            calculateFar(pl, mostRestrictiveOccupancy, far);
        }
        ProcessPrintHelper.print(pl);
        return pl;
    }

    private void calculateFar(Plan pl, OccupancyType mostRestrictiveOccupancy, BigDecimal far) {
        ScrutinyDetail scrutinyDetail = new ScrutinyDetail();
        scrutinyDetail.setKey("Common_FAR");
        scrutinyDetail.addColumnHeading(1, RULE_NO);
        scrutinyDetail.addColumnHeading(2, DESCRIPTION);
        scrutinyDetail.addColumnHeading(3, OCCUPANCY);
        scrutinyDetail.addColumnHeading(4, REQUIRED);
        scrutinyDetail.addColumnHeading(5, PROVIDED);
        scrutinyDetail.addColumnHeading(6, STATUS);

        BigDecimal upperWeightedFar = BigDecimal.ZERO;
        BigDecimal loweWeightedFar = BigDecimal.ZERO;
        BigDecimal weightedAreaWOAddnlFee = BigDecimal.ZERO;
        BigDecimal weightedAreaWithAddnlFee = BigDecimal.ZERO;
        upperWeightedFar.setScale(DECIMALDIGITS_MEASUREMENTS, ROUNDMODE_MEASUREMENTS);
        loweWeightedFar.setScale(DECIMALDIGITS_MEASUREMENTS, ROUNDMODE_MEASUREMENTS);
        weightedAreaWOAddnlFee.setScale(DECIMALDIGITS_MEASUREMENTS, ROUNDMODE_MEASUREMENTS);

        if (pl.getPlot().getArea().doubleValue() >= 5000) {

            for (Occupancy occ : pl.getOccupancies()) {
                weightedAreaWOAddnlFee = weightedAreaWOAddnlFee
                        .add(occ.getFloorArea().multiply(getPermissibleFar(occ.getType())));
                weightedAreaWithAddnlFee = weightedAreaWithAddnlFee
                        .add(occ.getFloorArea().multiply(getMaxPermissibleFar(occ.getType())));
            }
            if (pl.getVirtualBuilding().getTotalFloorArea() != null
                    && pl.getVirtualBuilding().getTotalFloorArea().doubleValue() > 0) {
                loweWeightedFar = weightedAreaWOAddnlFee.divide(pl.getVirtualBuilding().getTotalFloorArea(), 2,
                        ROUNDMODE_MEASUREMENTS);
                upperWeightedFar = weightedAreaWithAddnlFee.divide(pl.getVirtualBuilding().getTotalFloorArea(), 2,
                        ROUNDMODE_MEASUREMENTS);
            }

            processFar(pl, "-", pl.getFar(), upperWeightedFar, loweWeightedFar, scrutinyDetail, RULE_DESCRIPTION_KEY_WEIGHTED);

        } else {
            if (mostRestrictiveOccupancy != null) {
                switch (mostRestrictiveOccupancy) {
                case OCCUPANCY_A1:
                case OCCUPANCY_A4:
                    processFar(pl, mostRestrictiveOccupancy.getOccupancyTypeVal(), pl.getFar(), four, three, scrutinyDetail,
                            null);
                    break;
                case OCCUPANCY_A2:
                    processFar(pl, mostRestrictiveOccupancy.getOccupancyTypeVal(), pl.getFar(), four, twoPointFive,
                            scrutinyDetail, null);
                    break;
                // case OCCUPANCY_B:
                case OCCUPANCY_B1:
                case OCCUPANCY_B2:
                case OCCUPANCY_B3:
                    processFar(pl, mostRestrictiveOccupancy.getOccupancyTypeVal(), pl.getFar(), three, twoPointFive,
                            scrutinyDetail, null);
                    break;
                case OCCUPANCY_C:
                case OCCUPANCY_C1:
                case OCCUPANCY_C2:
                case OCCUPANCY_C3:
                    processFar(pl, mostRestrictiveOccupancy.getOccupancyTypeVal(), pl.getFar(), threePointFive, twoPointFive,
                            scrutinyDetail, null);
                    break;
                case OCCUPANCY_D:
                case OCCUPANCY_D1:
                case OCCUPANCY_D2:
                    processFar(pl, mostRestrictiveOccupancy.getOccupancyTypeVal(), pl.getFar(), twoPointFive, onePointFive,
                            scrutinyDetail, null);
                    break;
                case OCCUPANCY_E:
                    processFar(pl, mostRestrictiveOccupancy.getOccupancyTypeVal(), pl.getFar(), four, three, scrutinyDetail,
                            null);
                    break;
                case OCCUPANCY_F:
                case OCCUPANCY_F4:
                    processFar(pl, mostRestrictiveOccupancy.getOccupancyTypeVal(), pl.getFar(), four, three, scrutinyDetail,
                            null);
                    break;
                case OCCUPANCY_G1:
                    processFar(pl, mostRestrictiveOccupancy.getOccupancyTypeVal(), pl.getFar(), twoPointFive, twoPointFive,
                            scrutinyDetail, null);
                    break;
                case OCCUPANCY_G2:
                    processFar(pl, mostRestrictiveOccupancy.getOccupancyTypeVal(), pl.getFar(), four, threePointFive,
                            scrutinyDetail, null);
                    break;
                case OCCUPANCY_H:
                    processFar(pl, mostRestrictiveOccupancy.getOccupancyTypeVal(), pl.getFar(), four, three, scrutinyDetail,
                            null);
                    break;
                case OCCUPANCY_I1:
                    processFar(pl, mostRestrictiveOccupancy.getOccupancyTypeVal(), pl.getFar(), two, two, scrutinyDetail, null);
                    break;
                case OCCUPANCY_I2:
                    processFar(pl, mostRestrictiveOccupancy.getOccupancyTypeVal(), pl.getFar(), onePointFive, onePointFive,
                            scrutinyDetail, null);
                    break;
                default:
                    break;

                }
            }
        }
    }

    private void validate2(Plan pl, Block blk, Floor flr, Occupancy occupancy) {
        if (occupancy.getBuiltUpArea() != null && occupancy.getBuiltUpArea().compareTo(BigDecimal.valueOf(0)) < 0) {
            pl.addError(VALIDATION_NEGATIVE_BUILTUP_AREA,
                    getLocaleMessage(VALIDATION_NEGATIVE_BUILTUP_AREA, blk.getNumber(), flr.getNumber().toString(),
                            occupancy.getType().getOccupancyTypeVal()));
        }
        if (occupancy.getExistingBuiltUpArea() != null
                && occupancy.getExistingBuiltUpArea().compareTo(BigDecimal.valueOf(0)) < 0) {
            pl.addError(VALIDATION_NEGATIVE_EXISTING_BUILTUP_AREA,
                    getLocaleMessage(VALIDATION_NEGATIVE_EXISTING_BUILTUP_AREA, blk.getNumber(), flr.getNumber().toString(),
                            occupancy.getType().getOccupancyTypeVal()));
        }
        occupancy.setFloorArea(
                (occupancy.getBuiltUpArea() == null ? BigDecimal.ZERO : occupancy.getBuiltUpArea())
                        .subtract(occupancy.getDeduction() == null ? BigDecimal.ZERO
                                : occupancy.getDeduction()));
        if (occupancy.getFloorArea() != null && occupancy.getFloorArea().compareTo(BigDecimal.valueOf(0)) < 0) {
            pl.addError(VALIDATION_NEGATIVE_FLOOR_AREA,
                    getLocaleMessage(VALIDATION_NEGATIVE_FLOOR_AREA, blk.getNumber(), flr.getNumber().toString(),
                            occupancy.getType().getOccupancyTypeVal()));
        }
        occupancy.setExistingFloorArea(
                (occupancy.getExistingBuiltUpArea() == null ? BigDecimal.ZERO : occupancy.getExistingBuiltUpArea()).subtract(
                        occupancy.getExistingDeduction() == null ? BigDecimal.ZERO : occupancy.getExistingDeduction()));
        if (occupancy.getExistingFloorArea() != null && occupancy.getExistingFloorArea().compareTo(BigDecimal.valueOf(0)) < 0) {
            pl.addError(VALIDATION_NEGATIVE_EXISTING_FLOOR_AREA,
                    getLocaleMessage(VALIDATION_NEGATIVE_EXISTING_FLOOR_AREA, blk.getNumber(), flr.getNumber().toString(),
                            occupancy.getType().getOccupancyTypeVal()));
        }
    }

    private void processFar(Plan pl, String occupancyType, BigDecimal far, BigDecimal upperLimit, BigDecimal additionFeeLimit,
            ScrutinyDetail scrutinyDetail, String desc) {

        if (far.doubleValue() <= upperLimit.doubleValue()) {

            if (far.doubleValue() > additionFeeLimit.doubleValue()) {
                BigDecimal additonalFee = pl.getPlot().getArea().multiply(new BigDecimal(5000))
                        .multiply(far.subtract(additionFeeLimit));

                String actualResult = getLocaleMessage(RULE_ACTUAL_KEY, far.toString(), additonalFee.toString());
                String expectedResult = getLocaleMessage(RULE_EXPECTED_KEY, upperLimit.toString(), far.toString(),
                        additionFeeLimit.toString(), pl.getPlot().getArea().toString());
                if (desc == null)
                    desc = getLocaleMessage(RULE_DESCRIPTION_KEY, upperLimit.toString(),
                            additionFeeLimit.toString());
                else
                    desc = getLocaleMessage(desc, upperLimit.toString(),
                            additionFeeLimit.toString());

                Map<String, String> details = new HashMap<>();
                details.put(RULE_NO, RULE_31_1);
                details.put(DESCRIPTION, desc);
                details.put(OCCUPANCY, occupancyType);
                details.put(REQUIRED, expectedResult);
                details.put(PROVIDED, actualResult);
                details.put(STATUS, Result.Verify.getResultVal());
                scrutinyDetail.getDetail().add(details);
                pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);
                // pl.reportOutput.add(buildResult(desc, actualResult, expectedResult, Result.Verify));
            } else {
                if (desc == null)
                    desc = getLocaleMessage(RULE_DESCRIPTION_KEY, upperLimit.toString(),
                            additionFeeLimit.toString());
                else
                    desc = getLocaleMessage(desc, upperLimit.toString(),
                            additionFeeLimit.toString());
                String actualResult = far.toString();
                String expectedResult = getLocaleMessage(RULE_EXPECTED_KEY, upperLimit.toString(), far.toString(),
                        additionFeeLimit.toString(), pl.getPlot().getArea().toString());

                Map<String, String> details = new HashMap<>();
                details.put(RULE_NO, RULE_31_1);
                details.put(DESCRIPTION, desc);
                details.put(OCCUPANCY, occupancyType);
                details.put(REQUIRED, expectedResult);
                details.put(PROVIDED, actualResult);
                details.put(STATUS, Result.Accepted.getResultVal());
                scrutinyDetail.getDetail().add(details);
                pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);
            }
        } else {
            if (desc == null)
                desc = getLocaleMessage(RULE_DESCRIPTION_KEY, upperLimit.toString(), additionFeeLimit.toString());
            else
                desc = getLocaleMessage(desc, upperLimit.toString(), additionFeeLimit.toString());
            String actualResult = far.toString();
            String expectedResult = getLocaleMessage(RULE_EXPECTED_KEY, far.toString(), BigDecimal.ZERO.toString());

            Map<String, String> details = new HashMap<>();
            details.put(RULE_NO, RULE_31_1);
            details.put(DESCRIPTION, desc);
            details.put(OCCUPANCY, occupancyType);
            details.put(REQUIRED, expectedResult);
            details.put(PROVIDED, actualResult);
            details.put(STATUS, Result.Not_Accepted.getResultVal());
            scrutinyDetail.getDetail().add(details);
            pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);

        }

    }

    protected OccupancyType getMostRestrictiveFar(EnumSet<OccupancyType> distinctOccupancyTypes) {
        if (distinctOccupancyTypes.contains(OccupancyType.OCCUPANCY_I2))
            return OccupancyType.OCCUPANCY_I2;
        if (distinctOccupancyTypes.contains(OccupancyType.OCCUPANCY_I1))
            return OccupancyType.OCCUPANCY_I1;
        if (distinctOccupancyTypes.contains(OccupancyType.OCCUPANCY_G1))
            return OccupancyType.OCCUPANCY_G1;
        if (distinctOccupancyTypes.contains(OccupancyType.OCCUPANCY_D))
            return OccupancyType.OCCUPANCY_D;
        if (distinctOccupancyTypes.contains(OccupancyType.OCCUPANCY_B1))
            return OccupancyType.OCCUPANCY_B1;
        if (distinctOccupancyTypes.contains(OccupancyType.OCCUPANCY_B2))
            return OccupancyType.OCCUPANCY_B2;
        if (distinctOccupancyTypes.contains(OccupancyType.OCCUPANCY_B3))
            return OccupancyType.OCCUPANCY_B3;
        if (distinctOccupancyTypes.contains(OccupancyType.OCCUPANCY_C))
            return OccupancyType.OCCUPANCY_C;
        if (distinctOccupancyTypes.contains(OccupancyType.OCCUPANCY_D1))
            return OccupancyType.OCCUPANCY_D1;
        if (distinctOccupancyTypes.contains(OccupancyType.OCCUPANCY_E))
            return OccupancyType.OCCUPANCY_E;
        if (distinctOccupancyTypes.contains(OccupancyType.OCCUPANCY_F))
            return OccupancyType.OCCUPANCY_F;
        if (distinctOccupancyTypes.contains(OccupancyType.OCCUPANCY_F4))
            return OccupancyType.OCCUPANCY_F4;
        if (distinctOccupancyTypes.contains(OccupancyType.OCCUPANCY_A1))
            return OccupancyType.OCCUPANCY_A1;
        if (distinctOccupancyTypes.contains(OccupancyType.OCCUPANCY_A4))
            return OccupancyType.OCCUPANCY_A4;
        if (distinctOccupancyTypes.contains(OccupancyType.OCCUPANCY_A2))
            return OccupancyType.OCCUPANCY_A2;
        if (distinctOccupancyTypes.contains(OccupancyType.OCCUPANCY_G2))
            return OccupancyType.OCCUPANCY_G2;
        else
            return null;
    }

    private BigDecimal getPermissibleFar(OccupancyType occupancyType) {
        BigDecimal permissibleFar = BigDecimal.ZERO;
        switch (occupancyType) {
        case OCCUPANCY_A1:
        case OCCUPANCY_A4:
            permissibleFar = three;
            break;
        case OCCUPANCY_A2:
            permissibleFar = twoPointFive;
            break;
        case OCCUPANCY_B1:
        case OCCUPANCY_B2:
        case OCCUPANCY_B3:
            permissibleFar = twoPointFive;
            break;
        case OCCUPANCY_C:
            permissibleFar = twoPointFive;
            break;
        case OCCUPANCY_D:
        case OCCUPANCY_D1:
            permissibleFar = onePointFive;
            break;
        case OCCUPANCY_E:
            permissibleFar = three;
            break;
        case OCCUPANCY_F:
        case OCCUPANCY_F4:
            permissibleFar = three;
            break;
        case OCCUPANCY_G1:
            permissibleFar = twoPointFive;
            break;
        case OCCUPANCY_G2:
            permissibleFar = threePointFive;
            break;
        case OCCUPANCY_H:
            permissibleFar = three;
            break;
        case OCCUPANCY_I1:
            permissibleFar = two;
            break;
        case OCCUPANCY_I2:
            permissibleFar = onePointFive;
            break;
        default:
            break;

        }
        return permissibleFar;
    }

    private BigDecimal getMaxPermissibleFar(OccupancyType occupancyType) {
        BigDecimal permissibleFar = BigDecimal.ZERO;
        switch (occupancyType) {
        case OCCUPANCY_A1:
        case OCCUPANCY_A4:
            permissibleFar = four;
            break;
        case OCCUPANCY_A2:
            permissibleFar = four;
            break;
        case OCCUPANCY_B1:
        case OCCUPANCY_B2:
        case OCCUPANCY_B3:
            permissibleFar = three;
            break;
        case OCCUPANCY_C:
        case OCCUPANCY_C1:
        case OCCUPANCY_C2:
        case OCCUPANCY_C3:

            permissibleFar = threePointFive;
            break;
        case OCCUPANCY_D:
        case OCCUPANCY_D1:
        case OCCUPANCY_D2:
            permissibleFar = twoPointFive;
            break;
        case OCCUPANCY_E:
            permissibleFar = four;
            break;
        case OCCUPANCY_F:
        case OCCUPANCY_F4:
            permissibleFar = four;
            break;
        case OCCUPANCY_G1:
            permissibleFar = twoPointFive;
            break;
        case OCCUPANCY_G2:
            permissibleFar = four;
            break;
        case OCCUPANCY_H:
            permissibleFar = four;
            break;
        case OCCUPANCY_I1:
            permissibleFar = two;
            break;
        case OCCUPANCY_I2:
            permissibleFar = onePointFive;
            break;
        default:
            break;

        }
        return permissibleFar;
    }

    @Override
    public Map<String, Date> getAmendments() {
        return new LinkedHashMap<>();
    }
}
