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
import static org.egov.edcr.constants.DxfFileConstants.A_AF;
import static org.egov.edcr.constants.DxfFileConstants.F;
import static org.egov.edcr.constants.DxfFileConstants.PARKING_SLOT;
import static org.egov.edcr.utility.DcrConstants.SQMTRS;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.egov.common.entity.edcr.Block;
import org.egov.common.entity.edcr.Floor;
import org.egov.common.entity.edcr.Measurement;
import org.egov.common.entity.edcr.Occupancy;
import org.egov.common.entity.edcr.OccupancyType;
import org.egov.common.entity.edcr.OccupancyTypeHelper;
import org.egov.common.entity.edcr.ParkingDetails;
import org.egov.common.entity.edcr.ParkingHelper;
import org.egov.common.entity.edcr.Plan;
import org.egov.common.entity.edcr.Result;
import org.egov.common.entity.edcr.ScrutinyDetail;
import org.egov.edcr.utility.DcrConstants;
import org.springframework.stereotype.Service;

@Service
public class Parking extends FeatureProcess {

    private static final String OUT_OF = "Out of ";
    private static final String PARKING_SLOT_POLYGON_NOT_HAVING_ONLY_4_POINTS = " number of polygon not having only 4 points.";
    private static final String LOADING_UNLOADING_DESC = "Minimum required Loading/Unloading area";
    private static final String MINIMUM_AREA_OF_EACH_DA_PARKING = " Minimum Area of Each DA parking";
    private static final String DA_PARKING_SLOT_AREA = "DA Parking Slot Area";
    private static final String NO_VIOLATION_OF_AREA = "No violation of area in ";
    private static final String MIN_AREA_EACH_CAR_PARKING = " Minimum Area of Each ECS parking";
    private static final String PARKING_VIOLATED_MINIMUM_AREA = " parking violated minimum area.";
    private static final String PARKING = " parking ";
    private static final String NUMBERS = " Numbers ";
    private static final String MECHANICAL_PARKING = "Mechanical parking";
    private static final String MAX_ALLOWED_MECH_PARK = "Maximum allowed mechanical parking";
    private static final String TWO_WHEELER_PARK_AREA = "Two Wheeler Parking Area";
    private static final String DA_PARKING = "DA parking";
    private static final Logger LOGGER = Logger.getLogger(Parking.class);
    private static final String SUB_RULE_34_1 = "34(1)";
    private static final String SUB_RULE_34_1_DESCRIPTION = "Parking Slots Area";
    private static final String SUB_RULE_34_2 = "34(2)";
    private static final String SUB_RULE_40A__5 = "40A(5)";
    private static final String SUB_RULE_34_2_DESCRIPTION = "Car Parking ";
    private static final String PARKING_MIN_AREA = "5 M x 2 M";
    private static final double PARKING_SLOT_WIDTH = 2;
    private static final double PARKING_SLOT_HEIGHT = 5;
    private static final double DA_PARKING_SLOT_WIDTH = 3.6;
    private static final double DA_PARKING_SLOT_HEIGHT = 5.5;
    private static final String DA_PARKING_MIN_AREA = " 3.60 M x 5.50 M";
    private static final String ZERO_TO_60 = "0-60";
    private static final String SIXTY_TO_150 = "60-150";
    private static final String HUNDRED_FIFTY_TO_250 = "150-250";
    private static final String GREATER_THAN_TWO_HUNDRED_FIFTY = ">250";
    private static final String ZERO_TO_5 = "0-5";
    private static final String FIVE_TO_12 = "5-12";
    private static final String GREATER_THAN_TWELVE = ">12";
    private static final String ZERO_TO_12 = "0-12";
    private static final String TWELVE_TO_20 = "12-20";
    private static final String GREATER_THAN_TWENTY = ">20";
    private static final String ZERO_TO_N = ">0";
    public static final String NO_OF_UNITS = "No of apartment units";

    private static final double OPEN_ECS = 23;
    private static final double COVER_ECS = 28;
    private static final double BSMNT_ECS = 32;
    private static final double PARK_A = 0.25;
    private static final double PARK_F = 0.30;
    private static final double PARK_VISITOR = 0.15;
    private static final String SUB_RULE_40 = "40";
    private static final String SUB_RULE_40_2 = "40(2)";
    private static final String SUB_RULE_40_2_DESCRIPTION = "Parking space";
    private static final String SUB_RULE_40_10 = "40(10)";
    private static final String SUB_RULE_40_10_DESCRIPTION = "Visitor’s Parking";
    public static final String OPEN_PARKING_DIM_DESC = "Open parking ECS dimension ";
    public static final String COVER_PARKING_DIM_DESC = "Cover parking ECS dimension ";
    public static final String BSMNT_PARKING_DIM_DESC = "Basement parking ECS dimension ";
    public static final String VISITOR_PARKING = "Visitor parking";
    public static final String SPECIAL_PARKING_DIM_DESC = "Special parking ECS dimension ";

    @Override
    public Plan validate(Plan pl) {
        // validateDimensions(pl);
        return pl;
    }

    @Override
    public Plan process(Plan pl) {
        validate(pl);
        scrutinyDetail = new ScrutinyDetail();
        scrutinyDetail.setKey("Common_Parking");
        scrutinyDetail.addColumnHeading(1, RULE_NO);
        scrutinyDetail.addColumnHeading(2, DESCRIPTION);
        scrutinyDetail.addColumnHeading(3, REQUIRED);
        scrutinyDetail.addColumnHeading(4, PROVIDED);
        scrutinyDetail.addColumnHeading(5, STATUS);
        processParking(pl);
        return pl;
    }

    private void validateDimensions(Plan pl) {
        ParkingDetails parkDtls = pl.getParkingDetails();
        if (!parkDtls.getCars().isEmpty()) {
            int count = 0;
            for (Measurement m : parkDtls.getCars())
                if (m.getInvalidReason() != null && m.getInvalidReason().length() > 0)
                    count++;
            if (count > 0)
                pl.addError(PARKING_SLOT, PARKING_SLOT + count + PARKING_SLOT_POLYGON_NOT_HAVING_ONLY_4_POINTS);
        }

        if (!parkDtls.getOpenCars().isEmpty()) {
            int count = 0;
            for (Measurement m : parkDtls.getOpenCars())
                if (m.getInvalidReason() != null && m.getInvalidReason().length() > 0)
                    count++;
            if (count > 0)
                pl.addError(OPEN_PARKING_DIM_DESC, OPEN_PARKING_DIM_DESC + count + PARKING_SLOT_POLYGON_NOT_HAVING_ONLY_4_POINTS);
        }

        if (!parkDtls.getCoverCars().isEmpty()) {
            int count = 0;
            for (Measurement m : parkDtls.getCoverCars())
                if (m.getInvalidReason() != null && m.getInvalidReason().length() > 0)
                    count++;
            if (count > 0)
                pl.addError(COVER_PARKING_DIM_DESC,
                        COVER_PARKING_DIM_DESC + count + PARKING_SLOT_POLYGON_NOT_HAVING_ONLY_4_POINTS);
        }

        if (!parkDtls.getCoverCars().isEmpty()) {
            int count = 0;
            for (Measurement m : parkDtls.getBasementCars())
                if (m.getInvalidReason() != null && m.getInvalidReason().length() > 0)
                    count++;
            if (count > 0)
                pl.addError(BSMNT_PARKING_DIM_DESC,
                        BSMNT_PARKING_DIM_DESC + count + PARKING_SLOT_POLYGON_NOT_HAVING_ONLY_4_POINTS);
        }

        if (!parkDtls.getSpecial().isEmpty()) {
            int count = 0;
            for (Measurement m : parkDtls.getDisabledPersons())
                if (m.getInvalidReason() != null && m.getInvalidReason().length() > 0)
                    count++;
            if (count > 0)
                pl.addError(SPECIAL_PARKING_DIM_DESC,
                        SPECIAL_PARKING_DIM_DESC + count + " number of DA Parking slot polygon not having only 4 points.");
        }
    }

    public void processParking(Plan pl) {
        ParkingHelper helper = new ParkingHelper();
        // checkDimensionForCarParking(pl, helper);
        OccupancyTypeHelper mostRestrictiveOccupancy = pl.getVirtualBuilding().getMostRestrictiveFarHelper();
        BigDecimal totalBuiltupArea = pl.getOccupancies().stream().map(Occupancy::getBuiltUpArea).reduce(BigDecimal.ZERO,
                BigDecimal::add);
        BigDecimal coverParkingArea = BigDecimal.ZERO;
        BigDecimal basementParkingArea = BigDecimal.ZERO;
        for (Block block : pl.getBlocks()) {
            for (Floor floor : block.getBuilding().getFloors()) {
                coverParkingArea = floor.getParking().getCoverCars().stream().map(Measurement::getArea)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                basementParkingArea = floor.getParking().getBasementCars().stream().map(Measurement::getArea)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
            }
        }
        BigDecimal openParkingArea = pl.getParkingDetails().getOpenCars().stream().map(Measurement::getArea)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalProvidedCarParkArea = openParkingArea.add(coverParkingArea).add(basementParkingArea);
        helper.totalRequiredCarParking += openParkingArea.doubleValue() / OPEN_ECS;
        helper.totalRequiredCarParking += coverParkingArea.doubleValue() / COVER_ECS;
        helper.totalRequiredCarParking += basementParkingArea.doubleValue() / BSMNT_ECS;
        Double requiredCarParkArea = 0d;
        Double requiredVisitorParkArea = 0d;
        BigDecimal providedVisitorParkArea = BigDecimal.ZERO;
        if (pl.getPlot().getArea() != null && pl.getPlot().getArea().doubleValue() <= 300) {
            if (A.equals(mostRestrictiveOccupancy.getType().getCode())) {
                if (openParkingArea.doubleValue() > 0 && coverParkingArea.doubleValue() > 0)
                    requiredCarParkArea += COVER_ECS * 2;
                else if (coverParkingArea.doubleValue() > 0)
                    requiredCarParkArea += COVER_ECS * 2;
                else if (basementParkingArea.doubleValue() > 0)
                    requiredCarParkArea += BSMNT_ECS * 2;
                else if (openParkingArea.doubleValue() > 0)
                    requiredCarParkArea += OPEN_ECS * 2;
            } else {
                BigDecimal builtupArea = totalBuiltupArea.subtract(totalBuiltupArea.multiply(BigDecimal.valueOf(0.15)));
                double requiredEcs = builtupArea.divide(BigDecimal.valueOf(100)).multiply(BigDecimal.valueOf(2))
                        .setScale(0, RoundingMode.UP).doubleValue();
                if (openParkingArea.doubleValue() > 0 && coverParkingArea.doubleValue() > 0)
                    requiredCarParkArea += COVER_ECS * requiredEcs;
                else if (openParkingArea.doubleValue() > 0 && basementParkingArea.doubleValue() > 0)
                    requiredCarParkArea += BSMNT_ECS * requiredEcs;
                else if (coverParkingArea.doubleValue() > 0 && basementParkingArea.doubleValue() > 0)
                    requiredCarParkArea += BSMNT_ECS * requiredEcs;
                else if (coverParkingArea.doubleValue() > 0)
                    requiredCarParkArea += COVER_ECS * requiredEcs;
                else if (basementParkingArea.doubleValue() > 0)
                    requiredCarParkArea += BSMNT_ECS * requiredEcs;
                else if (openParkingArea.doubleValue() > 0)
                    requiredCarParkArea += OPEN_ECS * requiredEcs;
            }
        } else {
            providedVisitorParkArea = pl.getParkingDetails().getVisitors().stream().map(Measurement::getArea)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            if (A.equals(mostRestrictiveOccupancy.getType().getCode())) {
                requiredCarParkArea = totalBuiltupArea.doubleValue() * PARK_A;
                if (mostRestrictiveOccupancy.getSubtype() != null && A_AF.equals(mostRestrictiveOccupancy.getSubtype().getCode()))
                    requiredVisitorParkArea = requiredCarParkArea * PARK_VISITOR;
            } else if (F.equals(mostRestrictiveOccupancy.getType().getCode())) {
                requiredCarParkArea = totalBuiltupArea.doubleValue() * PARK_F;
            }
        }

        if (requiredCarParkArea > 0 && totalProvidedCarParkArea.doubleValue() < requiredCarParkArea) {
            setReportOutputDetails(pl, SUB_RULE_40_2, SUB_RULE_40_2_DESCRIPTION,
                    requiredCarParkArea + SQMTRS, totalProvidedCarParkArea + SQMTRS,
                    Result.Not_Accepted.getResultVal());
        } else {
            setReportOutputDetails(pl, SUB_RULE_40_2, SUB_RULE_40_2_DESCRIPTION,
                    requiredCarParkArea + SQMTRS, totalProvidedCarParkArea + SQMTRS,
                    Result.Accepted.getResultVal());
        }
        if (requiredVisitorParkArea > 0 && providedVisitorParkArea.doubleValue() < requiredVisitorParkArea) {
            setReportOutputDetails(pl, SUB_RULE_40_10, SUB_RULE_40_10_DESCRIPTION,
                    requiredVisitorParkArea + SQMTRS, providedVisitorParkArea + SQMTRS,
                    Result.Not_Accepted.getResultVal());
        } else if (requiredVisitorParkArea > 0) {
            setReportOutputDetails(pl, SUB_RULE_40_10, SUB_RULE_40_10_DESCRIPTION,
                    requiredVisitorParkArea + SQMTRS, providedVisitorParkArea + SQMTRS,
                    Result.Accepted.getResultVal());
        }

        LOGGER.info("******************Require no of Car Parking***************" + helper.totalRequiredCarParking);
    }

    private void setReportOutputDetails(Plan pl, String ruleNo, String ruleDesc, String expected, String actual,
            String status) {
        Map<String, String> details = new HashMap<>();
        details.put(RULE_NO, ruleNo);
        details.put(DESCRIPTION, ruleDesc);
        details.put(REQUIRED, expected);
        details.put(PROVIDED, actual);
        details.put(STATUS, status);
        scrutinyDetail.getDetail().add(details);
        pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);
    }

    private void validateDAParking(Plan pl, ParkingHelper helper) {
        helper.daParking = Math.ceil(helper.carParkingForDACal * 3 / 100);
        checkDimensionForDAParking(pl, helper);
        HashMap<String, String> errors = new HashMap<>();
        Boolean isValid = true;
        if (pl.getParkingDetails().getDisabledPersons().isEmpty()) {
            isValid = false;
            errors.put(SUB_RULE_40A__5, getLocaleMessage(DcrConstants.OBJECTNOTDEFINED, DcrConstants.DAPARKING_UNIT));
            pl.addErrors(errors);
        } else if (pl.getParkingDetails().getDistFromDAToMainEntrance() == null
                || pl.getParkingDetails().getDistFromDAToMainEntrance().compareTo(BigDecimal.ZERO) == 0) {
            isValid = false;
            errors.put(SUB_RULE_40A__5,
                    getLocaleMessage(DcrConstants.OBJECTNOTDEFINED, DcrConstants.DIST_FROM_DA_TO_ENTRANCE));
            pl.addErrors(errors);
        } else if (pl.getParkingDetails().getDistFromDAToMainEntrance().compareTo(BigDecimal.valueOf(30)) > 0) {
            isValid = false;
            setReportOutputDetails(pl, SUB_RULE_40A__5, DcrConstants.DIST_FROM_DA_TO_ENTRANCE,
                    "Should be less than 30" + DcrConstants.IN_METER,
                    pl.getParkingDetails().getDistFromDAToMainEntrance() + DcrConstants.IN_METER,
                    Result.Not_Accepted.getResultVal());
        } else if (pl.getParkingDetails().getValidSpecialSlots() < helper.daParking) {
            setReportOutputDetails(pl, SUB_RULE_40A__5, DA_PARKING, helper.daParking.intValue() + NUMBERS,
                    pl.getParkingDetails().getValidSpecialSlots() + NUMBERS, Result.Not_Accepted.getResultVal());
        } else {
            setReportOutputDetails(pl, SUB_RULE_40A__5, DA_PARKING, helper.daParking.intValue() + NUMBERS,
                    pl.getParkingDetails().getValidSpecialSlots() + NUMBERS, Result.Accepted.getResultVal());
        }
        if (isValid) {
            setReportOutputDetails(pl, SUB_RULE_40A__5, DcrConstants.DIST_FROM_DA_TO_ENTRANCE,
                    "Less than 30" + DcrConstants.IN_METER,
                    pl.getParkingDetails().getDistFromDAToMainEntrance() + DcrConstants.IN_METER, Result.Accepted.getResultVal());
        }
    }

    private void processTwoWheelerParking(Plan pl, ParkingHelper helper) {
        helper.twoWheelerParking = BigDecimal.valueOf(0.25 * helper.totalRequiredCarParking * 2.70 * 5.50)
                .setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
        double providedArea = 0;
        for (Measurement measurement : pl.getParkingDetails().getTwoWheelers()) {
            providedArea = providedArea + measurement.getArea().doubleValue();
        }
        if (providedArea < helper.twoWheelerParking) {
            setReportOutputDetails(pl, SUB_RULE_34_2, TWO_WHEELER_PARK_AREA, helper.twoWheelerParking + " " + DcrConstants.SQMTRS,
                    BigDecimal.valueOf(providedArea).setScale(2, BigDecimal.ROUND_HALF_UP) + " " + DcrConstants.SQMTRS,
                    Result.Not_Accepted.getResultVal());
        } else {
            setReportOutputDetails(pl, SUB_RULE_34_2, TWO_WHEELER_PARK_AREA, helper.twoWheelerParking + " " + DcrConstants.SQMTRS,
                    BigDecimal.valueOf(providedArea).setScale(2, BigDecimal.ROUND_HALF_UP) + " " + DcrConstants.SQMTRS,
                    Result.Accepted.getResultVal());
        }
    }

    private double processMechanicalParking(Plan pl, ParkingHelper helper) {
        Integer noOfMechParkingFromPlInfo = pl.getPlanInformation().getNoOfMechanicalParking();
        Integer providedSlots = pl.getParkingDetails().getMechParking().size();
        double maxAllowedMechPark = BigDecimal.valueOf(helper.totalRequiredCarParking / 2).setScale(0, RoundingMode.UP)
                .intValue();
        if (noOfMechParkingFromPlInfo > 0) {
            if (noOfMechParkingFromPlInfo > 0 && providedSlots == 0) {
                setReportOutputDetails(pl, SUB_RULE_34_2, MECHANICAL_PARKING, 1 + NUMBERS, providedSlots + NUMBERS,
                        Result.Not_Accepted.getResultVal());
            } else if (noOfMechParkingFromPlInfo > 0 && providedSlots > 0 && noOfMechParkingFromPlInfo > maxAllowedMechPark) {
                setReportOutputDetails(pl, SUB_RULE_34_2, MAX_ALLOWED_MECH_PARK, maxAllowedMechPark + NUMBERS,
                        noOfMechParkingFromPlInfo + NUMBERS, Result.Not_Accepted.getResultVal());
            } else if (noOfMechParkingFromPlInfo > 0 && providedSlots > 0) {
                setReportOutputDetails(pl, SUB_RULE_34_2, MECHANICAL_PARKING, "", noOfMechParkingFromPlInfo + NUMBERS,
                        Result.Accepted.getResultVal());
            }
        }
        return 0;
    }

    /*
     * private void buildResultForYardValidation(Plan Plan, BigDecimal parkSlotAreaInFrontYard, BigDecimal maxAllowedArea, String
     * type) { Plan.reportOutput .add(buildRuleOutputWithSubRule(DcrConstants.RULE34, SUB_RULE_34_1,
     * "Parking space should not exceed 50% of the area of mandatory " + type,
     * "Parking space should not exceed 50% of the area of mandatory " + type, "Maximum allowed area for parking in " + type +" "
     * + maxAllowedArea + DcrConstants.SQMTRS, "Parking provided in more than the allowed area " + parkSlotAreaInFrontYard +
     * DcrConstants.SQMTRS, Result.Not_Accepted, null)); } private BigDecimal validateParkingSlotsAreWithInYard(Plan Plan, Polygon
     * yardPolygon) { BigDecimal area = BigDecimal.ZERO; for (Measurement parkingSlot : Plan.getParkingDetails().getCars()) {
     * Iterator parkSlotIterator = parkingSlot.getPolyLine().getVertexIterator(); while (parkSlotIterator.hasNext()) { DXFVertex
     * dxfVertex = (DXFVertex) parkSlotIterator.next(); Point point = dxfVertex.getPoint(); if (rayCasting.contains(point,
     * yardPolygon)) { area = area.add(parkingSlot.getArea()); } } } return area; }
     */

    private void checkDimensionForCarParking(Plan pl, ParkingHelper helper) {

        /*
         * for (Block block : Plan.getBlocks()) { for (SetBack setBack : block.getSetBacks()) { if (setBack.getFrontYard() != null
         * && setBack.getFrontYard().getPresentInDxf()) { Polygon frontYardPolygon =
         * ProcessHelper.getPolygon(setBack.getFrontYard().getPolyLine()); BigDecimal parkSlotAreaInFrontYard =
         * validateParkingSlotsAreWithInYard(Plan, frontYardPolygon); BigDecimal maxAllowedArea =
         * setBack.getFrontYard().getArea().divide(BigDecimal.valueOf(2), DcrConstants.DECIMALDIGITS_MEASUREMENTS,
         * RoundingMode.HALF_UP); if (parkSlotAreaInFrontYard.compareTo(maxAllowedArea) > 0) { buildResultForYardValidation(Plan,
         * parkSlotAreaInFrontYard, maxAllowedArea, "front yard space"); } } if (setBack.getRearYard() != null &&
         * setBack.getRearYard().getPresentInDxf()) { Polygon rearYardPolygon =
         * ProcessHelper.getPolygon(setBack.getRearYard().getPolyLine()); BigDecimal parkSlotAreaInRearYard =
         * validateParkingSlotsAreWithInYard(Plan, rearYardPolygon); BigDecimal maxAllowedArea =
         * setBack.getRearYard().getArea().divide(BigDecimal.valueOf(2), DcrConstants.DECIMALDIGITS_MEASUREMENTS,
         * RoundingMode.HALF_UP); if (parkSlotAreaInRearYard.compareTo(maxAllowedArea) > 0) { buildResultForYardValidation(Plan,
         * parkSlotAreaInRearYard, maxAllowedArea, "rear yard space"); } } if (setBack.getSideYard1() != null &&
         * setBack.getSideYard1().getPresentInDxf()) { Polygon sideYard1Polygon =
         * ProcessHelper.getPolygon(setBack.getSideYard1().getPolyLine()); BigDecimal parkSlotAreaInSideYard1 =
         * validateParkingSlotsAreWithInYard(Plan, sideYard1Polygon); BigDecimal maxAllowedArea =
         * setBack.getSideYard1().getArea().divide(BigDecimal.valueOf(2), DcrConstants.DECIMALDIGITS_MEASUREMENTS,
         * RoundingMode.HALF_UP); if (parkSlotAreaInSideYard1.compareTo(maxAllowedArea) > 0) { buildResultForYardValidation(Plan,
         * parkSlotAreaInSideYard1, maxAllowedArea, "side yard1 space"); } } if (setBack.getSideYard2() != null &&
         * setBack.getSideYard2().getPresentInDxf()) { Polygon sideYard2Polygon =
         * ProcessHelper.getPolygon(setBack.getSideYard2().getPolyLine()); BigDecimal parkSlotAreaInFrontYard =
         * validateParkingSlotsAreWithInYard(Plan, sideYard2Polygon); BigDecimal maxAllowedArea =
         * setBack.getSideYard2().getArea().divide(BigDecimal.valueOf(2), DcrConstants.DECIMALDIGITS_MEASUREMENTS,
         * RoundingMode.HALF_UP); if (parkSlotAreaInFrontYard.compareTo(maxAllowedArea) > 0) { buildResultForYardValidation(Plan,
         * parkSlotAreaInFrontYard, maxAllowedArea, "side yard2 space"); } } } }
         */

        int parkingCount = pl.getParkingDetails().getCars().size();
        int failedCount = 0;
        int success = 0;
        for (Measurement slot : pl.getParkingDetails().getCars()) {
            if (slot.getHeight().setScale(2, RoundingMode.UP).doubleValue() >= PARKING_SLOT_HEIGHT
                    && slot.getWidth().setScale(2, RoundingMode.UP).doubleValue() >= PARKING_SLOT_WIDTH)
                success++;
            else
                failedCount++;
        }
        pl.getParkingDetails().setValidCarParkingSlots(parkingCount - failedCount);
        if (parkingCount > 0)
            if (failedCount > 0) {
                if (helper.totalRequiredCarParking.intValue() == pl.getParkingDetails().getValidCarParkingSlots()) {
                    setReportOutputDetails(pl, SUB_RULE_40, SUB_RULE_34_1_DESCRIPTION,
                            PARKING_MIN_AREA + MIN_AREA_EACH_CAR_PARKING,
                            OUT_OF + parkingCount + PARKING + failedCount + PARKING_VIOLATED_MINIMUM_AREA,
                            Result.Accepted.getResultVal());
                } else {
                    setReportOutputDetails(pl, SUB_RULE_40, SUB_RULE_34_1_DESCRIPTION,
                            PARKING_MIN_AREA + MIN_AREA_EACH_CAR_PARKING,
                            OUT_OF + parkingCount + PARKING + failedCount + PARKING_VIOLATED_MINIMUM_AREA,
                            Result.Not_Accepted.getResultVal());
                }
            } else {
                setReportOutputDetails(pl, SUB_RULE_40, SUB_RULE_34_1_DESCRIPTION,
                        PARKING_MIN_AREA + MIN_AREA_EACH_CAR_PARKING, NO_VIOLATION_OF_AREA + parkingCount + PARKING,
                        Result.Accepted.getResultVal());
            }
        int openParkCount = pl.getParkingDetails().getOpenCars().size();
        int openFailedCount = 0;
        int openSuccess = 0;
        for (Measurement slot : pl.getParkingDetails().getOpenCars()) {
            if (slot.getHeight().setScale(2, RoundingMode.UP).doubleValue() >= PARKING_SLOT_HEIGHT
                    && slot.getWidth().setScale(2, RoundingMode.UP).doubleValue() >= PARKING_SLOT_WIDTH)
                openSuccess++;
            else
                openFailedCount++;
        }
        pl.getParkingDetails().setValidOpenCarSlots(openParkCount - openFailedCount);
        if (openParkCount > 0)
            if (openFailedCount > 0) {
                if (helper.totalRequiredCarParking.intValue() == pl.getParkingDetails().getValidOpenCarSlots()) {
                    setReportOutputDetails(pl, SUB_RULE_40, OPEN_PARKING_DIM_DESC,
                            PARKING_MIN_AREA + MIN_AREA_EACH_CAR_PARKING,
                            OUT_OF + openParkCount + PARKING + openFailedCount + PARKING_VIOLATED_MINIMUM_AREA,
                            Result.Accepted.getResultVal());
                } else {
                    setReportOutputDetails(pl, SUB_RULE_40, OPEN_PARKING_DIM_DESC,
                            PARKING_MIN_AREA + MIN_AREA_EACH_CAR_PARKING,
                            OUT_OF + openParkCount + PARKING + openFailedCount + PARKING_VIOLATED_MINIMUM_AREA,
                            Result.Not_Accepted.getResultVal());
                }
            } else {
                setReportOutputDetails(pl, SUB_RULE_40, OPEN_PARKING_DIM_DESC,
                        PARKING_MIN_AREA + MIN_AREA_EACH_CAR_PARKING, NO_VIOLATION_OF_AREA + openParkCount + PARKING,
                        Result.Accepted.getResultVal());
            }

        int coverParkCount = pl.getParkingDetails().getCoverCars().size();
        int coverFailedCount = 0;
        int coverSuccess = 0;
        for (Measurement slot : pl.getParkingDetails().getCoverCars()) {
            if (slot.getHeight().setScale(2, RoundingMode.UP).doubleValue() >= PARKING_SLOT_HEIGHT
                    && slot.getWidth().setScale(2, RoundingMode.UP).doubleValue() >= PARKING_SLOT_WIDTH)
                coverSuccess++;
            else
                coverFailedCount++;
        }
        pl.getParkingDetails().setValidCoverCarSlots(coverParkCount - coverFailedCount);
        if (coverParkCount > 0)
            if (coverFailedCount > 0) {
                if (helper.totalRequiredCarParking.intValue() == pl.getParkingDetails().getValidCoverCarSlots()) {
                    setReportOutputDetails(pl, SUB_RULE_40, COVER_PARKING_DIM_DESC,
                            PARKING_MIN_AREA + MIN_AREA_EACH_CAR_PARKING,
                            OUT_OF + coverParkCount + PARKING + coverFailedCount + PARKING_VIOLATED_MINIMUM_AREA,
                            Result.Accepted.getResultVal());
                } else {
                    setReportOutputDetails(pl, SUB_RULE_40, COVER_PARKING_DIM_DESC,
                            PARKING_MIN_AREA + MIN_AREA_EACH_CAR_PARKING,
                            OUT_OF + coverParkCount + PARKING + coverFailedCount + PARKING_VIOLATED_MINIMUM_AREA,
                            Result.Not_Accepted.getResultVal());
                }
            } else {
                setReportOutputDetails(pl, SUB_RULE_40, COVER_PARKING_DIM_DESC,
                        PARKING_MIN_AREA + MIN_AREA_EACH_CAR_PARKING, NO_VIOLATION_OF_AREA + coverParkCount + PARKING,
                        Result.Accepted.getResultVal());
            }

        // Validate dimension of basement
        int bsmntParkCount = pl.getParkingDetails().getBasementCars().size();
        int bsmntFailedCount = 0;
        int bsmntSuccess = 0;
        for (Measurement slot : pl.getParkingDetails().getBasementCars()) {
            if (slot.getHeight().setScale(2, RoundingMode.UP).doubleValue() >= PARKING_SLOT_HEIGHT
                    && slot.getWidth().setScale(2, RoundingMode.UP).doubleValue() >= PARKING_SLOT_WIDTH)
                bsmntSuccess++;
            else
                bsmntFailedCount++;
        }
        pl.getParkingDetails().setValidBasementCarSlots(bsmntParkCount - bsmntFailedCount);
        if (bsmntParkCount > 0)
            if (bsmntFailedCount > 0) {
                if (helper.totalRequiredCarParking.intValue() == pl.getParkingDetails().getValidBasementCarSlots()) {
                    setReportOutputDetails(pl, SUB_RULE_40, BSMNT_PARKING_DIM_DESC,
                            PARKING_MIN_AREA + MIN_AREA_EACH_CAR_PARKING,
                            OUT_OF + bsmntParkCount + PARKING + bsmntFailedCount + PARKING_VIOLATED_MINIMUM_AREA,
                            Result.Accepted.getResultVal());
                } else {
                    setReportOutputDetails(pl, SUB_RULE_40, BSMNT_PARKING_DIM_DESC,
                            PARKING_MIN_AREA + MIN_AREA_EACH_CAR_PARKING,
                            OUT_OF + bsmntParkCount + PARKING + bsmntFailedCount + PARKING_VIOLATED_MINIMUM_AREA,
                            Result.Not_Accepted.getResultVal());
                }
            } else {
                setReportOutputDetails(pl, SUB_RULE_40, BSMNT_PARKING_DIM_DESC,
                        PARKING_MIN_AREA + MIN_AREA_EACH_CAR_PARKING, NO_VIOLATION_OF_AREA + bsmntParkCount + PARKING,
                        Result.Accepted.getResultVal());
            }

    }

    private void checkDimensionForDAParking(Plan pl, ParkingHelper helper) {

        int success = 0;
        int daFailedCount = 0;
        int daParkingCount = pl.getParkingDetails().getDisabledPersons().size();
        for (Measurement daParkingSlot : pl.getParkingDetails().getDisabledPersons()) {
            if (daParkingSlot.getWidth().setScale(2, RoundingMode.UP).doubleValue() >= DA_PARKING_SLOT_WIDTH
                    && daParkingSlot.getHeight().setScale(2, RoundingMode.UP).doubleValue() >= DA_PARKING_SLOT_HEIGHT)
                success++;
            else
                daFailedCount++;
        }
        pl.getParkingDetails().setValidSpecialSlots(daParkingCount - daFailedCount);
        if (daParkingCount > 0)
            if (daFailedCount > 0) {
                if (helper.daParking.intValue() == pl.getParkingDetails().getValidSpecialSlots()) {
                    setReportOutputDetails(pl, SUB_RULE_40A__5, DA_PARKING_SLOT_AREA,
                            DA_PARKING_MIN_AREA + MINIMUM_AREA_OF_EACH_DA_PARKING,
                            NO_VIOLATION_OF_AREA + pl.getParkingDetails().getValidSpecialSlots() + PARKING,
                            Result.Accepted.getResultVal());
                } else {
                    setReportOutputDetails(pl, SUB_RULE_40A__5, DA_PARKING_SLOT_AREA,
                            DA_PARKING_MIN_AREA + MINIMUM_AREA_OF_EACH_DA_PARKING,
                            OUT_OF + daParkingCount + PARKING + daFailedCount + PARKING_VIOLATED_MINIMUM_AREA,
                            Result.Not_Accepted.getResultVal());
                }
            } else {
                setReportOutputDetails(pl, SUB_RULE_40A__5, DA_PARKING_SLOT_AREA,
                        DA_PARKING_MIN_AREA + MINIMUM_AREA_OF_EACH_DA_PARKING, NO_VIOLATION_OF_AREA + daParkingCount + PARKING,
                        Result.Accepted.getResultVal());
            }
    }

    private BigDecimal getTotalCarpetAreaByOccupancy(Plan pl, OccupancyType type) {
        BigDecimal totalArea = BigDecimal.ZERO;
        for (Block b : pl.getBlocks())
            for (Occupancy occupancy : b.getBuilding().getTotalArea())
                if (occupancy.getType().equals(type))
                    totalArea = totalArea.add(occupancy.getCarpetArea());
        return totalArea;
    }

    @Override
    public Map<String, Date> getAmendments() {
        return new LinkedHashMap<>();
    }
}
