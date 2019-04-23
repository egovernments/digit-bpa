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

import static org.egov.edcr.constants.DxfFileConstants.A;
import static org.egov.edcr.constants.DxfFileConstants.A_R;
import static org.egov.edcr.constants.DxfFileConstants.F;
import static org.egov.edcr.utility.DcrConstants.DECIMALDIGITS_MEASUREMENTS;
import static org.egov.edcr.utility.DcrConstants.OBJECTNOTDEFINED;
import static org.egov.edcr.utility.DcrConstants.PLOT_AREA;
import static org.egov.edcr.utility.DcrConstants.ROUNDMODE_MEASUREMENTS;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
import org.egov.common.entity.edcr.OccupancyTypeHelper;
import org.egov.common.entity.edcr.Plan;
import org.egov.common.entity.edcr.Result;
import org.egov.common.entity.edcr.ScrutinyDetail;
import org.egov.edcr.service.ProcessPrintHelper;
import org.egov.infra.utils.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class Far extends FeatureProcess {

    private static final Logger LOG = Logger.getLogger(Far.class);

    private static final String VALIDATION_NEGATIVE_FLOOR_AREA = "msg.error.negative.floorarea.occupancy.floor";
    private static final String VALIDATION_NEGATIVE_EXISTING_FLOOR_AREA = "msg.error.negative.existing.floorarea.occupancy.floor";
    private static final String VALIDATION_NEGATIVE_BUILTUP_AREA = "msg.error.negative.builtuparea.occupancy.floor";
    private static final String VALIDATION_NEGATIVE_EXISTING_BUILTUP_AREA = "msg.error.negative.existing.builtuparea.occupancy.floor";
    public static final String RULE_31_1 = "31(1)";
    public static final String RULE_38 = "38";

    private static final BigDecimal ONE_POINTTWO = BigDecimal.valueOf(1.2);
    private static final BigDecimal ONE_POINTFIVE = BigDecimal.valueOf(1.5);
    private static final BigDecimal ONE_POINTEIGHT = BigDecimal.valueOf(1.8);
    private static final BigDecimal TWO = BigDecimal.valueOf(2);
    private static final BigDecimal TWO_POINTFIVE = BigDecimal.valueOf(2.5);
    private static final BigDecimal THREE = BigDecimal.valueOf(3);
    private static final BigDecimal THREE_POINTTWOFIVE = BigDecimal.valueOf(3.25);
    private static final BigDecimal THREE_POINTFIVE = BigDecimal.valueOf(3.5);

    private static final BigDecimal ROAD_WIDTH_TWO_POINTFOUR = BigDecimal.valueOf(2.4);
    private static final BigDecimal ROAD_WIDTH_TWO_POINTFOURFOUR = BigDecimal.valueOf(2.44);
    private static final BigDecimal ROAD_WIDTH_THREE_POINTSIX = BigDecimal.valueOf(3.6);
    private static final BigDecimal ROAD_WIDTH_FOUR_POINTEIGHT = BigDecimal.valueOf(4.8);
    private static final BigDecimal ROAD_WIDTH_SIX_POINTONE = BigDecimal.valueOf(6.1);
    private static final BigDecimal ROAD_WIDTH_NINE_POINTONE = BigDecimal.valueOf(9.1);
    private static final BigDecimal ROAD_WIDTH_TWELVE_POINTTWO = BigDecimal.valueOf(12.2);

    private static final BigDecimal ROAD_WIDTH_EIGHTEEN_POINTTHREE = BigDecimal.valueOf(18.3);
    private static final BigDecimal ROAD_WIDTH_TWENTYFOUR_POINTFOUR = BigDecimal.valueOf(24.4);
    private static final BigDecimal ROAD_WIDTH_TWENTYSEVEN_POINTFOUR = BigDecimal.valueOf(27.4);
    private static final BigDecimal ROAD_WIDTH_THIRTY_POINTFIVE = BigDecimal.valueOf(30.5);

    public static final String OLD = "OLD";
    public static final String NEW = "NEW";
    public static final String OLD_AREA_ERROR = "road width old area";
    public static final String NEW_AREA_ERROR = "road width new area";
    public static final String OLD_AREA_ERROR_MSG = "No construction shall be permitted if the road width is less than 2.4m for old area.";
    public static final String NEW_AREA_ERROR_MSG = "No construction shall be permitted if the road width is less than 6.1m for new area.";
    private static final String ONLYRESIDENTIAL_ALLOWED = "Only residential buildings will be scrutinized for now.";
    private static final String ONLYRESIDENTIAL_ALLOWED_KEY = "onlyresidential_allowed";

    @Override
    public Plan validate(Plan pl) {
        if (pl.getPlot().getArea() == null || pl.getPlot().getArea().doubleValue() == 0) {
            pl.addError(PLOT_AREA, getLocaleMessage(OBJECTNOTDEFINED, PLOT_AREA));
        }
        return pl;
    }

    @Override
    public Plan process(Plan pl) {
        HashMap<String, String> errorMsgs = new HashMap<>();
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
        Set<OccupancyTypeHelper> distinctOccupancyTypesHelper = new HashSet<>();
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

            // Find Occupancies by block and add
            Set<OccupancyTypeHelper> occupancyByBlock = new HashSet<>();
            for (Floor flr : building.getFloors()) {
                for (Occupancy occupancy : flr.getOccupancies()) {
                    if(occupancy.getTypeHelper() != null)
                        occupancyByBlock.add(occupancy.getTypeHelper());
                }
            }

            List<Map<String, Object>> listOfMapOfAllDtls = new ArrayList<>();
            List<OccupancyTypeHelper> listOfOccupancyTypes = new ArrayList<>();

            for (OccupancyTypeHelper occupancyType : occupancyByBlock) {

                Map<String, Object> allDtlsMap = new HashMap<>();
                BigDecimal blockWiseFloorArea = BigDecimal.ZERO;
                BigDecimal blockWiseBuiltupArea = BigDecimal.ZERO;
                BigDecimal blockWiseExistingFloorArea = BigDecimal.ZERO;
                BigDecimal blockWiseExistingBuiltupArea = BigDecimal.ZERO;
                for (Floor flr : blk.getBuilding().getFloors()) {
                    for (Occupancy occupancy : flr.getOccupancies()) {
                        if (occupancyType.getType() != null
                                && occupancy.getTypeHelper() != null
                                && occupancy.getTypeHelper().getType().equals(occupancyType.getType())) {
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
                occupancy.setTypeHelper(occupancyType);
                building.getTotalArea().add(occupancy);

                allDtlsMap.put("occupancy", occupancyType);
                allDtlsMap.put("totalFloorArea", blockWiseFloorArea);
                allDtlsMap.put("totalBuiltUpArea", blockWiseBuiltupArea);
                allDtlsMap.put("existingFloorArea", blockWiseExistingFloorArea);
                allDtlsMap.put("existingBuiltUpArea", blockWiseExistingBuiltupArea);

                listOfOccupancyTypes.add(occupancyType);

                listOfMapOfAllDtls.add(allDtlsMap);
            }
            Set<OccupancyTypeHelper> setOfOccupancyTypes = new HashSet<>(listOfOccupancyTypes);

            List<Occupancy> listOfOccupanciesOfAParticularblock = new ArrayList<>();
            // for each distinct converted occupancy types
            for (OccupancyTypeHelper occupancyType : setOfOccupancyTypes) {
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
                    occupancy.setTypeHelper(occupancyType);
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
                    if (occupancy.getTypeHelper().getSubtype() != null
                            && A_R.equals(occupancy.getTypeHelper().getSubtype().getCode()))
                        singleFamilyBuildingTypeOccupancyPresent = true;
                    else {
                        otherThanSingleFamilyOccupancyTypePresent = true;
                        break;
                    }
                }
                blk.setSingleFamilyBuilding(
                        !otherThanSingleFamilyOccupancyTypePresent && singleFamilyBuildingTypeOccupancyPresent);
                int allResidentialOccTypes = 0;
                int allResidentialOrCommercialOccTypes = 0;

                for (Occupancy occupancy : listOfOccupanciesOfAParticularblock) {
                    if (occupancy.getTypeHelper() != null) {
                        // setting residentialBuilding
                        int residentialOccupancyType = 0;
                        if (A.equals(occupancy.getTypeHelper().getType().getCode())) {
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
                blk.setResidentialBuilding(allResidentialOccTypes == 1);
                for (Occupancy occupancy : listOfOccupanciesOfAParticularblock) {
                    if (occupancy.getType() != null) {
                        // setting residentialOrCommercial Occupancy Type
                        int residentialOrCommercialOccupancyType = 0;
                        if (A.equals(occupancy.getTypeHelper().getType().getCode())
                                || F.equals(occupancy.getTypeHelper().getType().getCode())) {
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
                blk.setResidentialOrCommercialBuilding(allResidentialOrCommercialOccTypes == 1);
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

        List<OccupancyTypeHelper> plotWiseOccupancyTypes = new ArrayList<>();
        for (Block block : pl.getBlocks()) {
            for (Occupancy occupancy : block.getBuilding().getOccupancies()) {
                if (occupancy.getTypeHelper() != null)
                    plotWiseOccupancyTypes.add(occupancy.getTypeHelper());
            }
        }

        Set<OccupancyTypeHelper> setOfDistinctOccupancyTypes = new HashSet<>(plotWiseOccupancyTypes);

        distinctOccupancyTypesHelper.addAll(setOfDistinctOccupancyTypes);

        List<Occupancy> occupanciesForPlan = new ArrayList<>();

        for (OccupancyTypeHelper occupancyType : setOfDistinctOccupancyTypes) {
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
                        if (occupancyType.equals(buildingOccupancy.getTypeHelper())) {
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
                occupancy.setTypeHelper(occupancyType);
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
        pl.getVirtualBuilding().setOccupancyTypes(distinctOccupancyTypesHelper);
        pl.getVirtualBuilding().setTotalBuitUpArea(totalBuiltUpArea);
        pl.getVirtualBuilding().setMostRestrictiveFarHelper(getMostRestrictiveFar(setOfDistinctOccupancyTypes));

        if (!distinctOccupancyTypesHelper.isEmpty()) {
            int allResidentialOccTypesForPlan = 0;
            for (OccupancyTypeHelper occupancy : distinctOccupancyTypesHelper) {
                LOG.info("occupancy :" + occupancy);
                // setting residentialBuilding
                int residentialOccupancyType = 0;
                if (A.equals(occupancy.getType().getCode())) {
                    residentialOccupancyType = 1;
                }
                if (residentialOccupancyType == 0) {
                    allResidentialOccTypesForPlan = 0;
                    break;
                } else {
                    allResidentialOccTypesForPlan = 1;
                }
            }
            pl.getVirtualBuilding().setResidentialBuilding(allResidentialOccTypesForPlan == 1);
            int allResidentialOrCommercialOccTypesForPlan = 0;
            for (OccupancyTypeHelper occupancyType : distinctOccupancyTypesHelper) {
                int residentialOrCommercialOccupancyTypeForPlan = 0;
                if (A.equals(occupancyType.getType().getCode()) || F.equals(occupancyType.getType().getCode())) {
                    residentialOrCommercialOccupancyTypeForPlan = 1;
                }
                if (residentialOrCommercialOccupancyTypeForPlan == 0) {
                    allResidentialOrCommercialOccTypesForPlan = 0;
                    break;
                } else {
                    allResidentialOrCommercialOccTypesForPlan = 1;
                }
            }
            pl.getVirtualBuilding().setResidentialOrCommercialBuilding(allResidentialOrCommercialOccTypesForPlan == 1);
        }
        if (!pl.getVirtualBuilding().getResidentialBuilding()) {
            pl.getErrors().put(ONLYRESIDENTIAL_ALLOWED_KEY, ONLYRESIDENTIAL_ALLOWED);
            return pl;
        }
        OccupancyTypeHelper mostRestrictiveOccupancyType = pl.getVirtualBuilding().getMostRestrictiveFarHelper();
        BigDecimal far = BigDecimal.ZERO;
        if (pl.getPlot().getArea().doubleValue() > 0)
            far = pl.getVirtualBuilding().getTotalFloorArea().divide(pl.getPlot().getArea(), DECIMALDIGITS_MEASUREMENTS,
                    ROUNDMODE_MEASUREMENTS);

        String typeOfArea = pl.getPlanInformation().getTypeOfArea();
        BigDecimal roadWidth = pl.getPlanInformation().getRoadWidth();

        if (StringUtils.isNotBlank(typeOfArea) && roadWidth != null) {
            processFarResidential(pl, mostRestrictiveOccupancyType, far, typeOfArea, roadWidth, errorMsgs);
        }
        ProcessPrintHelper.print(pl);
        return pl;
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

    protected OccupancyTypeHelper getMostRestrictiveFar(Set<OccupancyTypeHelper> distinctOccupancyTypes) {
        OccupancyTypeHelper occupancyType = null;
        OccupancyTypeHelper a = null;
        OccupancyTypeHelper f = null;
        for (OccupancyTypeHelper typeHelper : distinctOccupancyTypes) {
            if (F.equalsIgnoreCase(typeHelper.getType().getCode()))
                f = typeHelper;
            else if (A.equalsIgnoreCase(typeHelper.getType().getCode()))
                a = typeHelper;

        }
        if (f != null) {
            return f;
        } else if (a != null) {
            return a;
        }
        return occupancyType;
    }

    private void processFarResidential(Plan pl, OccupancyTypeHelper occupancyType, BigDecimal far, String typeOfArea,
            BigDecimal roadWidth, HashMap<String, String> errors) {

        String expectedResult = StringUtils.EMPTY;
        boolean isAccepted = false;
        ScrutinyDetail scrutinyDetail = getFarScrutinyDetail("Common_FAR");

        if (typeOfArea.equalsIgnoreCase(OLD)) {
            if (roadWidth.compareTo(ROAD_WIDTH_TWO_POINTFOUR) < 0) {
                errors.put(OLD_AREA_ERROR, OLD_AREA_ERROR_MSG);
                pl.addErrors(errors);
            } else if (roadWidth.compareTo(ROAD_WIDTH_TWO_POINTFOURFOUR) >= 0
                    && roadWidth.compareTo(ROAD_WIDTH_THREE_POINTSIX) < 0) {
                isAccepted = far.compareTo(ONE_POINTTWO) <= 0;
                expectedResult = "<= 1.2";
            } else if (roadWidth.compareTo(ROAD_WIDTH_THREE_POINTSIX) >= 0
                    && roadWidth.compareTo(ROAD_WIDTH_FOUR_POINTEIGHT) < 0) {
                isAccepted = far.compareTo(ONE_POINTFIVE) <= 0;
                expectedResult = "<= 1.5";
            } else if (roadWidth.compareTo(ROAD_WIDTH_FOUR_POINTEIGHT) >= 0
                    && roadWidth.compareTo(ROAD_WIDTH_SIX_POINTONE) < 0) {
                isAccepted = far.compareTo(ONE_POINTEIGHT) <= 0;
                expectedResult = "<= 1.8";
            } else if (roadWidth.compareTo(ROAD_WIDTH_SIX_POINTONE) >= 0
                    && roadWidth.compareTo(ROAD_WIDTH_NINE_POINTONE) < 0) {
                isAccepted = far.compareTo(TWO) <= 0;
                expectedResult = "<= 2";
            } else if (roadWidth.compareTo(ROAD_WIDTH_NINE_POINTONE) >= 0
                    && roadWidth.compareTo(ROAD_WIDTH_TWELVE_POINTTWO) < 0) {
                isAccepted = far.compareTo(TWO_POINTFIVE) <= 0;
                expectedResult = "<= 2.5";
            } else if (roadWidth.compareTo(ROAD_WIDTH_TWELVE_POINTTWO) >= 0
                    && roadWidth.compareTo(ROAD_WIDTH_EIGHTEEN_POINTTHREE) < 0) {
                isAccepted = far.compareTo(TWO_POINTFIVE) <= 0;
                expectedResult = "<= 2.5";
            } else if (roadWidth.compareTo(ROAD_WIDTH_EIGHTEEN_POINTTHREE) >= 0
                    && roadWidth.compareTo(ROAD_WIDTH_TWENTYFOUR_POINTFOUR) < 0) {
                isAccepted = far.compareTo(TWO_POINTFIVE) <= 0;
                expectedResult = "<= 2.5";
            }

        }

        if (typeOfArea.equalsIgnoreCase(NEW)) {
            if (roadWidth.compareTo(ROAD_WIDTH_SIX_POINTONE) < 0) {
                errors.put(NEW_AREA_ERROR, NEW_AREA_ERROR_MSG);
                pl.addErrors(errors);
            } else if (roadWidth.compareTo(ROAD_WIDTH_SIX_POINTONE) >= 0
                    && roadWidth.compareTo(ROAD_WIDTH_NINE_POINTONE) < 0) {
                isAccepted = far.compareTo(TWO) <= 0;
                expectedResult = "<= 2";
            } else if (roadWidth.compareTo(ROAD_WIDTH_NINE_POINTONE) >= 0
                    && roadWidth.compareTo(ROAD_WIDTH_TWELVE_POINTTWO) < 0) {
                isAccepted = far.compareTo(TWO_POINTFIVE) <= 0;
                expectedResult = "<= 2.5";
            } else if (roadWidth.compareTo(ROAD_WIDTH_TWELVE_POINTTWO) >= 0
                    && roadWidth.compareTo(ROAD_WIDTH_EIGHTEEN_POINTTHREE) < 0) {
                isAccepted = far.compareTo(TWO_POINTFIVE) <= 0;
                expectedResult = "<= 2.5";
            } else if (roadWidth.compareTo(ROAD_WIDTH_EIGHTEEN_POINTTHREE) >= 0
                    && roadWidth.compareTo(ROAD_WIDTH_TWENTYFOUR_POINTFOUR) < 0) {
                isAccepted = far.compareTo(THREE) <= 0;
                expectedResult = "<= 3";
            } else if (roadWidth.compareTo(ROAD_WIDTH_TWENTYFOUR_POINTFOUR) >= 0
                    && roadWidth.compareTo(ROAD_WIDTH_TWENTYSEVEN_POINTFOUR) < 0) {
                isAccepted = far.compareTo(THREE_POINTTWOFIVE) <= 0;
                expectedResult = "<= 3.25";
            } else if (roadWidth.compareTo(ROAD_WIDTH_TWENTYSEVEN_POINTFOUR) >= 0
                    && roadWidth.compareTo(ROAD_WIDTH_THIRTY_POINTFIVE) < 0) {
                isAccepted = far.compareTo(THREE_POINTFIVE) <= 0;
                expectedResult = "<= 3.5";
            }

        }

        if (errors.isEmpty() && StringUtils.isNotBlank(expectedResult)) {
            String actualResult = far.toString();
            String occupancyName;
            if (occupancyType.getSubtype() != null)
                occupancyName = occupancyType.getSubtype().getName();
            else
                occupancyName = occupancyType.getType().getName();
            Map<String, String> details = new HashMap<>();
            details.put(RULE_NO, RULE_38);
            details.put(OCCUPANCY, occupancyName);
            details.put("Area Type", typeOfArea);
            details.put("Road Width", roadWidth.toString());
            details.put(PERMISSIBLE, expectedResult);
            details.put(PROVIDED, actualResult);
            details.put(STATUS, isAccepted ? Result.Accepted.getResultVal() : Result.Not_Accepted.getResultVal());

            scrutinyDetail.getDetail().add(details);
            pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);
        }
    }

    private ScrutinyDetail getFarScrutinyDetail(String key) {
        ScrutinyDetail scrutinyDetail = new ScrutinyDetail();
        scrutinyDetail.addColumnHeading(1, RULE_NO);
        scrutinyDetail.addColumnHeading(2, "Area Type");
        scrutinyDetail.addColumnHeading(3, "Road Width");
        scrutinyDetail.addColumnHeading(4, PERMISSIBLE);
        scrutinyDetail.addColumnHeading(5, PROVIDED);
        scrutinyDetail.addColumnHeading(6, STATUS);
        scrutinyDetail.setKey(key);
        return scrutinyDetail;
    }

    @Override
    public Map<String, Date> getAmendments() {
        return new LinkedHashMap<>();
    }
}
