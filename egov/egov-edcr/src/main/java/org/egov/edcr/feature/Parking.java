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

import static org.egov.common.entity.edcr.OccupancyType.OCCUPANCY_B2;
import static org.egov.common.entity.edcr.OccupancyType.OCCUPANCY_B3;
import static org.egov.edcr.constants.DxfFileConstants.PARKING_SLOT;
import static org.egov.edcr.utility.DcrConstants.DECIMALDIGITS_MEASUREMENTS;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.egov.common.entity.edcr.Block;
import org.egov.common.entity.edcr.Floor;
import org.egov.common.entity.edcr.FloorUnit;
import org.egov.common.entity.edcr.Measurement;
import org.egov.common.entity.edcr.Occupancy;
import org.egov.common.entity.edcr.OccupancyType;
import org.egov.common.entity.edcr.ParkingDetails;
import org.egov.common.entity.edcr.ParkingHelper;
import org.egov.common.entity.edcr.Plan;
import org.egov.common.entity.edcr.Result;
import org.egov.common.entity.edcr.ScrutinyDetail;
import org.egov.edcr.service.ProcessHelper;
import org.egov.edcr.utility.DcrConstants;
import org.springframework.stereotype.Service;

@Service
public class Parking extends FeatureProcess {

    private static final String LOADING_UNLOADING_DESC = "Minimum required Loading/Unloading area";
    private static final String MINIMUM_AREA_OF_EACH_DA_PARKING = " Minimum Area of Each DA parking";
    private static final String DA_PARKING_SLOT_AREA = "DA Parking Slot Area";
    private static final String NO_VIOLATION_OF_AREA = "No violation of area in ";
    private static final String MIN_AREA_EACH_CAR_PARKING = " Minimum Area of Each Car parking";
    private static final String PARKING_VIOLATED_MINIMUM_AREA = " parking violated minimum area.";
    private static final String PARKING = " parking ";
    private static final String NUMBERS = " Numbers ";
    private static final String MECHANICAL_PARKING = "Mechanical parking";
    private static final String MAX_ALLOWED_MECH_PARK = "Maximum allowed mechanical parking";
    private static final String TWO_WHEELER_PARK_AREA = "Two Wheeler Parking Area";
    private static final String DA_PARKING = "DA parking";
    private static final String OBJECT_NOT_DEFINED = "msg.error.mandatory.object1.not.defined";
    private static final Logger LOGGER = Logger.getLogger(Parking.class);
    private static final String SUB_RULE_34_1 = "34(1)";
    private static final String SUB_RULE_34_1_DESCRIPTION = "Parking Slots Area";
    private static final String SUB_RULE_34_2 = "34(2)";
    private static final String SUB_RULE_40A__5 = "40A(5)";
    private static final String SUB_RULE_34_2_DESCRIPTION = "Car Parking ";
    private static final String PARKING_MIN_AREA = " 2.70 M x 5.50 M";
    private static final double PARKING_SLOT_WIDTH = 2.7;
    private static final double PARKING_SLOT_HEIGHT = 5.5;
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

    @Override
    public Plan validate(Plan pl) {
        return null;
    }

    @Override
    public Plan process(Plan pl) {
        validate(pl);
        /*
         * All blocks is small plot in entire plot and floors above ground less than or equal to three and occupancy type of
         * entire block is either Residential or Commercial then parking process not require.
         */
        List<Boolean> isExempted = checkIsParkingValidationRequired(pl);
        if (isExempted.isEmpty() || isExempted.contains(Boolean.FALSE)) {
            scrutinyDetail = new ScrutinyDetail();
            scrutinyDetail.setKey("Common_Parking");
            scrutinyDetail.addColumnHeading(1, RULE_NO);
            scrutinyDetail.addColumnHeading(2, DESCRIPTION);
            scrutinyDetail.addColumnHeading(3, REQUIRED);
            scrutinyDetail.addColumnHeading(4, PROVIDED);
            scrutinyDetail.addColumnHeading(5, STATUS);
            processParking(pl);
        }
        return pl;
    }

    private void processUnits(Plan pl) {
        int occupancyA4UnitsCount = 0;
        int occupancyA2UnitsCount = 0;
        int noOfRoomsWithAttchdBthrms = 0;
        int noOfRoomsWithoutAttchdBthrms = 0;
        int noOfDineRooms = 0;
        HashMap<String, String> errors = new HashMap<>();

        for (Block block : pl.getBlocks()) {
            if (block.getBuilding() != null && !block.getBuilding().getFloors().isEmpty()) {
                for (Floor floor : block.getBuilding().getFloors()) {
                    if (floor.getUnits() != null & floor.getUnits().size() > 0) {
                        for (FloorUnit unit : floor.getUnits()) {
                            if (unit.getOccupancy().getType().equals(OccupancyType.OCCUPANCY_A4)) {
                                occupancyA4UnitsCount++;
                            }
                            if (unit.getOccupancy().getType().equals(OccupancyType.OCCUPANCY_A2)) {
                                occupancyA2UnitsCount++;
                                if (unit.getOccupancy().getWithAttachedBath()) {
                                    noOfRoomsWithAttchdBthrms++;
                                }
                                if (unit.getOccupancy().getWithOutAttachedBath()) {
                                    noOfRoomsWithoutAttchdBthrms++;
                                }
                                if (unit.getOccupancy().getWithDinningSpace()) {
                                    noOfDineRooms++;
                                }

                            }
                        }
                    }
                }
            }

        }

        if (pl.getVirtualBuilding().getOccupancies().contains(OccupancyType.OCCUPANCY_A4)) {
            if (occupancyA4UnitsCount == 0) {
                setReportOutputDetails(pl, SUB_RULE_34_2, "UNITFA is not defined", String.valueOf(1),
                        String.valueOf(occupancyA4UnitsCount), Result.Not_Accepted.getResultVal());
            }

            if (occupancyA4UnitsCount > 0) {
                setReportOutputDetails(pl, SUB_RULE_34_2, NO_OF_UNITS, String.valueOf("-"),
                        String.valueOf(occupancyA4UnitsCount), Result.Accepted.getResultVal());
            }

        }

        if (pl.getVirtualBuilding().getOccupancies().contains(OccupancyType.OCCUPANCY_A2)) {

            if (occupancyA2UnitsCount == 0) {
                setReportOutputDetails(pl, SUB_RULE_34_2, "UNITFA is not defined", String.valueOf(1),
                        String.valueOf(occupancyA2UnitsCount), Result.Not_Accepted.getResultVal());
            }

            if (occupancyA2UnitsCount > 0) {
                setReportOutputDetails(pl, SUB_RULE_34_2, "No of rooms without attached bathrooms", String.valueOf("-"),
                        String.valueOf(noOfRoomsWithoutAttchdBthrms), Result.Accepted.getResultVal());
                setReportOutputDetails(pl, SUB_RULE_34_2, "No of rooms with attached bathrooms", String.valueOf("-"),
                        String.valueOf(noOfRoomsWithAttchdBthrms), Result.Accepted.getResultVal());
            }

            if (noOfDineRooms > 0 && (pl.getPlanInformation().getNoOfSeats() == null
                    || pl.getPlanInformation().getNoOfSeats() == 0)) {
                errors.put("SEATS_SP_RESI", "SEATS_SP_RESI not defined in PLAN_INFO");
                pl.addErrors(errors);
            }
        }

    }

    private void validateDimensions(Plan pl) {
        ParkingDetails parkDtls = pl.getParkingDetails();
        if (!parkDtls.getCars().isEmpty()) {
            int count = 0;
            for (Measurement m : parkDtls.getCars())
                if (m.getInvalidReason() != null && m.getInvalidReason().length() > 0)
                    count++;
            if (count > 0)
                pl.addError(PARKING_SLOT, count + " number of Parking slot polygon not having only 4 points.");
        }

        if (!parkDtls.getDisabledPersons().isEmpty()) {
            int count = 0;
            for (Measurement m : parkDtls.getDisabledPersons())
                if (m.getInvalidReason() != null && m.getInvalidReason().length() > 0)
                    count++;
            if (count > 0)
                pl.addError(DA_PARKING, count + " number of DA Parking slot polygon not having only 4 points.");
        }
    }

    public List<Boolean> checkIsParkingValidationRequired(Plan pl) {
        List<Boolean> isSmallPlot = new ArrayList<>();
        for (Block blk : pl.getBlocks()) {
            if (ProcessHelper.checkExemptionConditionForSmallPlotAtBlkLevel(pl.getPlot(), blk))
                isSmallPlot.add(true);
            else if (pl.getPlot().getArea().compareTo(BigDecimal.valueOf(125)) > 0) {
                List<OccupancyType> occupancyTypes = blk.getBuilding().getOccupancies().stream()
                        .map(occupancy -> occupancy.getType()).collect(Collectors.toList());
                for (OccupancyType occupancyType : occupancyTypes) {
                    if ((occupancyType.equals(OccupancyType.OCCUPANCY_A1) || occupancyType.equals(OccupancyType.OCCUPANCY_A5))
                            && blk.getBuilding().getFloorsAboveGround().compareTo(BigDecimal.valueOf(3)) <= 0) {
                        isSmallPlot.add(true);
                    } else {
                        isSmallPlot.add(false);
                    }
                }
            } else
                isSmallPlot.add(false);
        }
        return isSmallPlot;
    }

    public void processParking(Plan Plan) {
        Map<String, Integer> unitsCountMap = new ConcurrentHashMap<>();

        Map<String, BigDecimal> unitsAreaMap = new ConcurrentHashMap<>();
        ParkingHelper helper = new ParkingHelper();
        for (Occupancy occupancy : Plan.getOccupancies()) {
            switch (occupancy.getType()) {
            case OCCUPANCY_A1:
            case OCCUPANCY_A4:
                unitsCountMap.put(ZERO_TO_60, 0);
                unitsCountMap.put(SIXTY_TO_150, 0);
                unitsCountMap.put(HUNDRED_FIFTY_TO_250, 0);
                unitsCountMap.put(GREATER_THAN_TWO_HUNDRED_FIFTY, 0);
                for (Block b : Plan.getBlocks()) {
                    for (Floor f : b.getBuilding().getFloors()) {
                        for (FloorUnit unit : f.getUnits()) {
                            /*
                             * Even though if building type is single family (OCCUPANCY_A1) and no of floors more than 3 excluding
                             * stair room then parking validation is required.
                             **/
                            if (OccupancyType.OCCUPANCY_A4.equals(unit.getOccupancy().getType())
                                    || OccupancyType.OCCUPANCY_A1.equals(unit.getOccupancy().getType())) {
                                if (unit.getArea().doubleValue() < 60d) {
                                    unitsCountMap.put(ZERO_TO_60, unitsCountMap.get(ZERO_TO_60) + 1);
                                } else if (unit.getArea().doubleValue() < 150) {
                                    unitsCountMap.put(SIXTY_TO_150, unitsCountMap.get(SIXTY_TO_150) + 1);
                                } else if (unit.getArea().doubleValue() < 250d) {
                                    unitsCountMap.put(HUNDRED_FIFTY_TO_250, unitsCountMap.get(HUNDRED_FIFTY_TO_250) + 1);
                                } else {
                                    unitsCountMap.put(GREATER_THAN_TWO_HUNDRED_FIFTY,
                                            unitsCountMap.get(GREATER_THAN_TWO_HUNDRED_FIFTY) + 1);
                                }
                            }
                        }
                    }
                }

                helper.a1TotalParking = Math.floor(unitsCountMap.get(ZERO_TO_60) / 3)
                        + Math.floor(unitsCountMap.get(SIXTY_TO_150) * 1)
                        + Math.floor(unitsCountMap.get(HUNDRED_FIFTY_TO_250) * 1.5)
                        + Math.floor(unitsCountMap.get(GREATER_THAN_TWO_HUNDRED_FIFTY) * 2);

                helper.carParkingForDACal += helper.a1TotalParking;
                helper.visitorParking = Math.floor(helper.a1TotalParking * 15 / 100);
                helper.totalRequiredCarParking += helper.a1TotalParking + helper.visitorParking;

                break;
            case OCCUPANCY_A2:
                unitsCountMap.put(ZERO_TO_5, 0);
                unitsCountMap.put(FIVE_TO_12, 0);
                unitsCountMap.put(GREATER_THAN_TWELVE, 0);
                unitsCountMap.put(ZERO_TO_12, 0);
                unitsCountMap.put(TWELVE_TO_20, 0);
                unitsCountMap.put(GREATER_THAN_TWENTY, 0);
                unitsAreaMap.put(ZERO_TO_N, BigDecimal.ZERO);

                for (Block b : Plan.getBlocks()) {
                    for (Floor f : b.getBuilding().getFloors()) {
                        for (FloorUnit unit : f.getUnits()) {
                            if (OccupancyType.OCCUPANCY_A2.equals(unit.getOccupancy().getType())
                                    && unit.getOccupancy().getWithOutAttachedBath()) {
                                if (unit.getArea().compareTo(BigDecimal.valueOf(5)) < 0) {
                                    unitsCountMap.put(ZERO_TO_5, unitsCountMap.get(ZERO_TO_5) + 1);
                                } else if (unit.getArea().compareTo(BigDecimal.valueOf(12)) <= 0) {
                                    unitsCountMap.put(FIVE_TO_12, unitsCountMap.get(FIVE_TO_12) + 1);
                                } else if (unit.getArea().compareTo(BigDecimal.valueOf(12)) > 0) {
                                    unitsCountMap.put(GREATER_THAN_TWELVE, unitsCountMap.get(GREATER_THAN_TWELVE) + 1);
                                }
                            } else if (OccupancyType.OCCUPANCY_A2.equals(unit.getOccupancy().getType())
                                    && unit.getOccupancy().getWithAttachedBath()) {
                                if (unit.getArea().compareTo(BigDecimal.valueOf(12)) < 0) {
                                    unitsCountMap.put(ZERO_TO_12, unitsCountMap.get(ZERO_TO_12) + 1);
                                } else if (unit.getArea().compareTo(BigDecimal.valueOf(20)) <= 0) {
                                    unitsCountMap.put(TWELVE_TO_20, unitsCountMap.get(TWELVE_TO_20) + 1);
                                } else if (unit.getArea().compareTo(BigDecimal.valueOf(20)) > 0) {
                                    unitsCountMap.put(GREATER_THAN_TWENTY, unitsCountMap.get(GREATER_THAN_TWENTY) + 1);
                                }
                            } else if (OccupancyType.OCCUPANCY_A2.equals(unit.getOccupancy().getType())
                                    && unit.getOccupancy().getWithDinningSpace())
                                unitsAreaMap.put(ZERO_TO_N, unitsAreaMap.get(ZERO_TO_N).add(unit.getArea()));
                        }
                    }
                }
                // For A2 with out attached bathroom
                double a2WOAttach = unitsCountMap.get(ZERO_TO_5) / 9
                        + unitsCountMap.get(FIVE_TO_12) / 6
                        + unitsCountMap.get(GREATER_THAN_TWELVE) / 3;
                helper.totalRequiredCarParking += a2WOAttach;
                helper.carParkingForDACal += a2WOAttach;

                // For A2 with attached bathroom
                double a2WithAttach = unitsCountMap.get(ZERO_TO_12) / 4
                        + unitsCountMap.get(TWELVE_TO_20) / 2.5
                        + unitsCountMap.get(GREATER_THAN_TWENTY) / 1.5;
                helper.totalRequiredCarParking += a2WithAttach;
                helper.carParkingForDACal += a2WithAttach;

                // For A2 with dinning area
                double noOfSeatsPlInfo = Plan.getPlanInformation().getNoOfSeats() == null ? 0
                        : Plan.getPlanInformation().getNoOfSeats() / 10;
                BigDecimal noOfSeats = unitsAreaMap.get(ZERO_TO_N).divide(BigDecimal.valueOf(20), DECIMALDIGITS_MEASUREMENTS,
                        RoundingMode.HALF_UP);
                if (noOfSeatsPlInfo > 0 && BigDecimal.valueOf(noOfSeatsPlInfo).compareTo(noOfSeats) > 0) {
                    helper.totalRequiredCarParking += noOfSeatsPlInfo;
                    helper.carParkingForDACal += noOfSeatsPlInfo;
                } else {
                    helper.totalRequiredCarParking += noOfSeats.doubleValue();
                    helper.carParkingForDACal += noOfSeats.doubleValue();
                }
                break;
            case OCCUPANCY_B1:
            case OCCUPANCY_B2:
            case OCCUPANCY_B3:
                BigDecimal carpetAreaForB2 = getTotalCarpetAreaByOccupancy(Plan, OCCUPANCY_B2);
                if (carpetAreaForB2 != null) {
                    double b2 = carpetAreaForB2.divide(BigDecimal.valueOf(250), DECIMALDIGITS_MEASUREMENTS, RoundingMode.HALF_UP)
                            .doubleValue();
                    helper.totalRequiredCarParking += b2;
                    helper.carParkingForDACal += b2;
                }
                BigDecimal carpetAreaForB3 = getTotalCarpetAreaByOccupancy(Plan, OCCUPANCY_B3);
                if (carpetAreaForB3 != null) {
                    double b3 = carpetAreaForB3.divide(BigDecimal.valueOf(100), DECIMALDIGITS_MEASUREMENTS, RoundingMode.HALF_UP)
                            .doubleValue();
                    helper.totalRequiredCarParking += b3;
                    helper.carParkingForDACal += b3;
                }
                break;
            case OCCUPANCY_D:
                BigDecimal hallArea = BigDecimal.ZERO;
                BigDecimal balconyArea = BigDecimal.ZERO;
                BigDecimal diningSpace = BigDecimal.ZERO;
                for (Block b : Plan.getBlocks()) {
                    for (Measurement measurement : b.getHallAreas())
                        hallArea = hallArea.add(measurement.getArea());
                    for (Measurement measurement : b.getBalconyAreas())
                        balconyArea = balconyArea.add(measurement.getArea());
                    for (Measurement measurement : b.getDiningSpaces())
                        diningSpace = diningSpace.add(measurement.getArea());
                }

                BigDecimal totalArea = hallArea.add(balconyArea);
                if (totalArea.doubleValue() > 0)
                    if (totalArea.compareTo(diningSpace) > 0) {
                        BigDecimal hall = totalArea.divide((BigDecimal.valueOf(1.5).multiply(BigDecimal.valueOf(15))),
                                DECIMALDIGITS_MEASUREMENTS,
                                RoundingMode.HALF_UP);
                        helper.totalRequiredCarParking += hall.doubleValue();
                        helper.carParkingForDACal += hall.doubleValue();
                    } else {
                        BigDecimal dining = diningSpace
                                .divide((BigDecimal.valueOf(1.5).multiply(BigDecimal.valueOf(15))), DECIMALDIGITS_MEASUREMENTS,
                                        RoundingMode.HALF_UP);
                        helper.totalRequiredCarParking += dining.doubleValue();
                        helper.carParkingForDACal += dining.doubleValue();
                    }
                break;
            case OCCUPANCY_F:
            case OCCUPANCY_F4:
                BigDecimal noOfCarParking = BigDecimal.ZERO;
                if (occupancy.getCarpetArea().compareTo(BigDecimal.valueOf(75)) > 0) {
                    if (occupancy != null && occupancy.getCarpetArea() != null
                            && occupancy.getCarpetArea().compareTo(BigDecimal.valueOf(1000)) <= 0)
                        noOfCarParking = occupancy.getCarpetArea().divide(BigDecimal.valueOf(75), DECIMALDIGITS_MEASUREMENTS,
                                RoundingMode.HALF_UP);
                    else if (occupancy != null && occupancy.getCarpetArea() != null
                            && occupancy.getCarpetArea().compareTo(BigDecimal.valueOf(1000)) > 0)
                        noOfCarParking = occupancy.getCarpetArea()
                                .divide(BigDecimal.valueOf(75), DECIMALDIGITS_MEASUREMENTS, RoundingMode.HALF_UP)
                                .add(occupancy.getCarpetArea().subtract(BigDecimal.valueOf(1000)).divide(BigDecimal.valueOf(50),
                                        0, RoundingMode.HALF_UP));
                }
                double f = noOfCarParking == null ? 0 : noOfCarParking.doubleValue();
                helper.totalRequiredCarParking += f;
                helper.carParkingForDACal += f;
                break;
            case OCCUPANCY_E:
                BigDecimal noOfCarParking1 = BigDecimal.ZERO;
                if (occupancy != null && occupancy.getCarpetArea() != null
                        && occupancy.getCarpetArea().compareTo(BigDecimal.valueOf(1000)) <= 0)
                    noOfCarParking1 = occupancy.getCarpetArea().divide(BigDecimal.valueOf(75), DECIMALDIGITS_MEASUREMENTS,
                            RoundingMode.HALF_UP);
                else if (occupancy != null && occupancy.getCarpetArea() != null
                        && occupancy.getCarpetArea().compareTo(BigDecimal.valueOf(1000)) > 0)
                    noOfCarParking1 = occupancy.getCarpetArea()
                            .divide(BigDecimal.valueOf(75), DECIMALDIGITS_MEASUREMENTS, RoundingMode.HALF_UP)
                            .add(occupancy.getCarpetArea().subtract(BigDecimal.valueOf(1000)).divide(BigDecimal.valueOf(50),
                                    DECIMALDIGITS_MEASUREMENTS, RoundingMode.HALF_UP));
                double e = noOfCarParking1 == null ? 0 : noOfCarParking1.doubleValue();
                helper.totalRequiredCarParking += e;
                helper.carParkingForDACal += e;
                break;
            }
        }

        double medical = processParkingForMedical(Plan);
        helper.totalRequiredCarParking += medical;
        helper.carParkingForDACal += medical;
        helper.totalRequiredCarParking += processParkingForIndustrialAndStorage(Plan);
        helper.totalRequiredCarParking = Math.ceil(helper.totalRequiredCarParking);
        helper.carParkingForDACal = Math.ceil(helper.carParkingForDACal);
        Plan.setParkingRequired(helper.totalRequiredCarParking);
        if (helper.totalRequiredCarParking > 0) {
            validateDAParking(Plan, helper);
            checkDimensionForCarParking(Plan, helper);
            Integer providedMechParking = 0;
            if (!Plan.getParkingDetails().getMechParking().isEmpty())
                providedMechParking = Plan.getPlanInformation().getNoOfMechanicalParking();
            Integer totalProvidedParking = Plan.getParkingDetails().getValidCarParkingSlots()
                    + Plan.getParkingDetails().getValidDAParkingSlots()
                    + providedMechParking;
            if (helper.totalRequiredCarParking > totalProvidedParking) {
                setReportOutputDetails(Plan, SUB_RULE_34_2, SUB_RULE_34_2_DESCRIPTION,
                        helper.totalRequiredCarParking.intValue() + NUMBERS, String.valueOf(totalProvidedParking) + NUMBERS,
                        Result.Not_Accepted.getResultVal());
            } else {
                setReportOutputDetails(Plan, SUB_RULE_34_2, SUB_RULE_34_2_DESCRIPTION,
                        helper.totalRequiredCarParking.intValue() + NUMBERS, String.valueOf(totalProvidedParking) + NUMBERS,
                        Result.Accepted.getResultVal());
            }
            /*
             * if (visitorParking > 0) { setReportOutputDetails(Plan, SUB_RULE_34_2, SUB_RULE_34_2_DESCRIPTION,
             * visitorParking.intValue() + NUMBERS, BigDecimal.valueOf(visitorParking).setScale(2, RoundingMode.UP).intValue() +
             * NUMBERS, Result.Accepted.getResultVal()); }
             */
            processTwoWheelerParking(Plan, helper);
            processMechanicalParking(Plan, helper);
        }

        processUnits(Plan);

        LOGGER.info("******************Require no of Car Parking***************" + helper.totalRequiredCarParking);
    }

    private double processParkingForIndustrialAndStorage(Plan Plan) {
        List<Occupancy> occupancyList = new ArrayList<>();
        Occupancy f = Plan.getOccupancies().stream()
                .filter(occupancy -> OccupancyType.OCCUPANCY_F4.equals(occupancy.getType())
                        || OccupancyType.OCCUPANCY_F.equals(occupancy.getType()))
                .findAny().orElse(null);
        Occupancy g1 = Plan.getOccupancies().stream()
                .filter(occupancy -> OccupancyType.OCCUPANCY_G1.equals(occupancy.getType())).findAny().orElse(null);
        Occupancy g2 = Plan.getOccupancies().stream()
                .filter(occupancy -> OccupancyType.OCCUPANCY_G2.equals(occupancy.getType())).findAny().orElse(null);
        Occupancy h = Plan.getOccupancies().stream()
                .filter(occupancy -> OccupancyType.OCCUPANCY_H.equals(occupancy.getType())).findAny().orElse(null);

        if (f != null)
            occupancyList.add(f);

        BigDecimal noOfCarParking = BigDecimal.ZERO;
        if (g1 != null && g1.getCarpetArea() != null) {
            occupancyList.add(g1);
            noOfCarParking = g1.getCarpetArea().divide(BigDecimal.valueOf(200), 0, RoundingMode.UP);
        }
        if (g2 != null && g2.getCarpetArea() != null) {
            noOfCarParking = g2.getCarpetArea().divide(BigDecimal.valueOf(200), 0, RoundingMode.UP);
            occupancyList.add(g2);
        }
        if (h != null && h.getCarpetArea() != null) {
            occupancyList.add(h);
            noOfCarParking = h.getCarpetArea().divide(BigDecimal.valueOf(200), 0, RoundingMode.UP);
        }
        processLoadingAndUnloading(Plan, occupancyList);
        return noOfCarParking == null ? 0 : noOfCarParking.doubleValue();
    }

    private double processParkingForMedical(Plan Plan) {
        Occupancy c = Plan.getOccupancies().stream()
                .filter(occupancy -> OccupancyType.OCCUPANCY_C.equals(occupancy.getType())).findAny().orElse(null);
        Occupancy c1 = Plan.getOccupancies().stream()
                .filter(occupancy -> OccupancyType.OCCUPANCY_C1.equals(occupancy.getType())).findAny().orElse(null);
        Occupancy c2 = Plan.getOccupancies().stream()
                .filter(occupancy -> OccupancyType.OCCUPANCY_C2.equals(occupancy.getType())).findAny().orElse(null);
        Occupancy c3 = Plan.getOccupancies().stream()
                .filter(occupancy -> OccupancyType.OCCUPANCY_C3.equals(occupancy.getType())).findAny().orElse(null);
        BigDecimal totalCarpetArea = BigDecimal.ZERO;
        if (c != null && c.getCarpetArea() != null)
            totalCarpetArea = totalCarpetArea.add(c.getCarpetArea());
        if (c1 != null && c1.getCarpetArea() != null)
            totalCarpetArea = totalCarpetArea.add(c1.getCarpetArea());
        if (c2 != null && c2.getCarpetArea() != null)
            totalCarpetArea = totalCarpetArea.add(c2.getCarpetArea());
        if (c3 != null && c3.getCarpetArea() != null)
            totalCarpetArea = totalCarpetArea.add(c3.getCarpetArea());
        return totalCarpetArea == null ? 0
                : totalCarpetArea.divide(BigDecimal.valueOf(75), DECIMALDIGITS_MEASUREMENTS, RoundingMode.HALF_UP).doubleValue();
    }

    private void processLoadingAndUnloading(Plan pl, List<Occupancy> occupancies) {
        double providedArea = 0;
        for (Measurement measurement : pl.getParkingDetails().getLoadUnload()) {
            providedArea = providedArea + measurement.getArea().doubleValue();
        }
        BigDecimal totalCarpetArea = BigDecimal.ZERO;
        for (Occupancy occupancy : occupancies) {
            totalCarpetArea = totalCarpetArea.add(occupancy.getArea() == null ? BigDecimal.ZERO : occupancy.getArea());
        }
        if (totalCarpetArea.doubleValue() > 700) {
            double requiredArea = Math.ceil(((totalCarpetArea.doubleValue() - 700) / 1000) * 30);
            HashMap<String, String> errors = new HashMap<>();
            if (pl.getParkingDetails().getLoadUnload().isEmpty()) {
                errors.put(DcrConstants.RULE34,
                        getLocaleMessage(DcrConstants.OBJECTNOTDEFINED, DcrConstants.LOAD_UNLOAD_AREA));
                pl.addErrors(errors);
            } else if (providedArea < requiredArea) {
                setReportOutputDetails(pl, SUB_RULE_34_2, LOADING_UNLOADING_DESC, String.valueOf(requiredArea),
                        String.valueOf(providedArea), Result.Not_Accepted.getResultVal());
            } else {
                setReportOutputDetails(pl, SUB_RULE_34_2, LOADING_UNLOADING_DESC, String.valueOf(requiredArea),
                        String.valueOf(providedArea), Result.Accepted.getResultVal());
            }
        }
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
        } else if (pl.getParkingDetails().getValidDAParkingSlots() < helper.daParking) {
            setReportOutputDetails(pl, SUB_RULE_40A__5, DA_PARKING, helper.daParking.intValue() + NUMBERS,
                    pl.getParkingDetails().getValidDAParkingSlots() + NUMBERS, Result.Not_Accepted.getResultVal());
        } else {
            setReportOutputDetails(pl, SUB_RULE_40A__5, DA_PARKING, helper.daParking.intValue() + NUMBERS,
                    pl.getParkingDetails().getValidDAParkingSlots() + NUMBERS, Result.Accepted.getResultVal());
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

    private double processMechanicalParking(Plan Plan, ParkingHelper helper) {
        Integer noOfMechParkingFromPlInfo = Plan.getPlanInformation().getNoOfMechanicalParking();
        Integer providedSlots = Plan.getParkingDetails().getMechParking().size();
        double maxAllowedMechPark = BigDecimal.valueOf(helper.totalRequiredCarParking / 2).setScale(0, RoundingMode.UP)
                .intValue();
        if (noOfMechParkingFromPlInfo > 0) {
            if (noOfMechParkingFromPlInfo > 0 && providedSlots == 0) {
                setReportOutputDetails(Plan, SUB_RULE_34_2, MECHANICAL_PARKING, 1 + NUMBERS, providedSlots + NUMBERS,
                        Result.Not_Accepted.getResultVal());
            } else if (noOfMechParkingFromPlInfo > 0 && providedSlots > 0 && noOfMechParkingFromPlInfo > maxAllowedMechPark) {
                setReportOutputDetails(Plan, SUB_RULE_34_2, MAX_ALLOWED_MECH_PARK, maxAllowedMechPark + NUMBERS,
                        noOfMechParkingFromPlInfo + NUMBERS, Result.Not_Accepted.getResultVal());
            } else if (noOfMechParkingFromPlInfo > 0 && providedSlots > 0) {
                setReportOutputDetails(Plan, SUB_RULE_34_2, MECHANICAL_PARKING, "", noOfMechParkingFromPlInfo + NUMBERS,
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

    private void checkDimensionForCarParking(Plan Plan, ParkingHelper helper) {

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

        int parkingCount = Plan.getParkingDetails().getCars().size();
        int failedCount = 0;
        int success = 0;

        for (Measurement slot : Plan.getParkingDetails().getCars()) {
            if (slot.getHeight().setScale(2, RoundingMode.UP).doubleValue() >= PARKING_SLOT_HEIGHT
                    && slot.getWidth().setScale(2, RoundingMode.UP).doubleValue() >= PARKING_SLOT_WIDTH)
                success++;
            else
                failedCount++;
        }
        Plan.getParkingDetails().setValidCarParkingSlots(parkingCount - failedCount);
        if (parkingCount > 0)
            if (failedCount > 0) {
                if (helper.totalRequiredCarParking.intValue() == Plan.getParkingDetails().getValidCarParkingSlots()) {
                    setReportOutputDetails(Plan, SUB_RULE_34_1, SUB_RULE_34_1_DESCRIPTION,
                            PARKING_MIN_AREA + MIN_AREA_EACH_CAR_PARKING,
                            "Out of " + parkingCount + PARKING + failedCount + PARKING_VIOLATED_MINIMUM_AREA,
                            Result.Accepted.getResultVal());
                } else {
                    setReportOutputDetails(Plan, SUB_RULE_34_1, SUB_RULE_34_1_DESCRIPTION,
                            PARKING_MIN_AREA + MIN_AREA_EACH_CAR_PARKING,
                            "Out of " + parkingCount + PARKING + failedCount + PARKING_VIOLATED_MINIMUM_AREA,
                            Result.Not_Accepted.getResultVal());
                }
            } else {
                setReportOutputDetails(Plan, SUB_RULE_34_1, SUB_RULE_34_1_DESCRIPTION,
                        PARKING_MIN_AREA + MIN_AREA_EACH_CAR_PARKING, NO_VIOLATION_OF_AREA + parkingCount + PARKING,
                        Result.Accepted.getResultVal());
            }

    }

    private void checkDimensionForDAParking(Plan Plan, ParkingHelper helper) {

        int success = 0;
        int daFailedCount = 0;
        int daParkingCount = Plan.getParkingDetails().getDisabledPersons().size();
        for (Measurement daParkingSlot : Plan.getParkingDetails().getDisabledPersons()) {
            if (daParkingSlot.getWidth().setScale(2, RoundingMode.UP).doubleValue() >= DA_PARKING_SLOT_WIDTH
                    && daParkingSlot.getHeight().setScale(2, RoundingMode.UP).doubleValue() >= DA_PARKING_SLOT_HEIGHT)
                success++;
            else
                daFailedCount++;
        }
        Plan.getParkingDetails().setValidDAParkingSlots(daParkingCount - daFailedCount);
        if (daParkingCount > 0)
            if (daFailedCount > 0) {
                if (helper.daParking.intValue() == Plan.getParkingDetails().getValidDAParkingSlots()) {
                    setReportOutputDetails(Plan, SUB_RULE_40A__5, DA_PARKING_SLOT_AREA,
                            DA_PARKING_MIN_AREA + MINIMUM_AREA_OF_EACH_DA_PARKING,
                            NO_VIOLATION_OF_AREA + Plan.getParkingDetails().getValidDAParkingSlots() + PARKING,
                            Result.Accepted.getResultVal());
                } else {
                    setReportOutputDetails(Plan, SUB_RULE_40A__5, DA_PARKING_SLOT_AREA,
                            DA_PARKING_MIN_AREA + MINIMUM_AREA_OF_EACH_DA_PARKING,
                            "Out of " + daParkingCount + PARKING + daFailedCount + PARKING_VIOLATED_MINIMUM_AREA,
                            Result.Not_Accepted.getResultVal());
                }
            } else {
                setReportOutputDetails(Plan, SUB_RULE_40A__5, DA_PARKING_SLOT_AREA,
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

}
