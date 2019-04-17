package org.egov.edcr.feature;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.egov.common.entity.edcr.Block;
import org.egov.common.entity.edcr.Floor;
import org.egov.common.entity.edcr.Measurement;
import org.egov.common.entity.edcr.OccupancyTypeHelper;
import org.egov.common.entity.edcr.Plan;
import org.egov.common.entity.edcr.Result;
import org.egov.common.entity.edcr.ScrutinyDetail;
import org.egov.edcr.constants.DxfFileConstants;
import org.egov.edcr.utility.DcrConstants;
import org.egov.edcr.utility.Util;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class GeneralStair extends FeatureProcess {
    private static final Logger LOG = Logger.getLogger(GeneralStair.class);
    private static final String FLOOR = "Floor";
    private static final String RULE42_5_II = "42(5)(ii)";
    // private static final String RULE42 = "42";
    // private static final String EXPECTED_WIDTH = "0.75";
    // private static final String EXPECTED_LINE = "0.75";
    // private static final String EXPECTED_TREAD = "0.15";
    private static final String EXPECTED_NO_OF_RISE = "12";
    // private static final String EXPECTED_TREAD_HIGHRISE = "0.2";
    private static final String NO_OF_RISE_DESCRIPTION = "Maximum no of rises required per flight for general stair %s";
    private static final String WIDTH_DESCRIPTION = "Minimum width for general stair %s";
    private static final String TREAD_DESCRIPTION = "Minimum tread for general stair %s";
    // private static final String LINE_DESCRIPTION = "Minimum length of line for
    // fire stair %s flight layer";
    // private static final String HEIGHT_FLOOR_DESCRIPTION = "Height of floor in
    // layer ";
    private static final String NO_OF_RISES = "Number of rises ";
    private static final String FLIGHT_POLYLINE_NOT_DEFINED_DESCRIPTION = "Flight polyline is not defined in layer ";
    private static final String FLIGHT_LENGTH_DEFINED_DESCRIPTION = "Flight polyline length is not defined in layer ";
    private static final String FLIGHT_WIDTH_DEFINED_DESCRIPTION = "Flight polyline width is not defined in layer ";

    @Override
    public Plan validate(Plan planDetail) {
        return planDetail;
    }

    @Override
    public Plan process(Plan planDetail) {
        // validate(planDetail);
        HashMap<String, String> errors = new HashMap<>();
        blk: for (Block block : planDetail.getBlocks()) {
            int generalStairCount = 0;

            if (block.getBuilding() != null) {
                /*
                 * if (Util.checkExemptionConditionForBuildingParts(block) ||
                 * Util.checkExemptionConditionForSmallPlotAtBlkLevel(planDetail.getPlot(), block)) { continue blk; }
                 */
                ScrutinyDetail scrutinyDetail2 = new ScrutinyDetail();
                scrutinyDetail2.addColumnHeading(1, RULE_NO);
                scrutinyDetail2.addColumnHeading(2, FLOOR);
                scrutinyDetail2.addColumnHeading(3, DESCRIPTION);
                scrutinyDetail2.addColumnHeading(4, PERMISSIBLE);
                scrutinyDetail2.addColumnHeading(5, PROVIDED);
                scrutinyDetail2.addColumnHeading(6, STATUS);
                scrutinyDetail2.setKey("Block_" + block.getNumber() + "_" + "General Stair - Width");

                ScrutinyDetail scrutinyDetail3 = new ScrutinyDetail();
                scrutinyDetail3.addColumnHeading(1, RULE_NO);
                scrutinyDetail3.addColumnHeading(2, FLOOR);
                scrutinyDetail3.addColumnHeading(3, DESCRIPTION);
                scrutinyDetail3.addColumnHeading(4, PERMISSIBLE);
                scrutinyDetail3.addColumnHeading(5, PROVIDED);
                scrutinyDetail3.addColumnHeading(6, STATUS);
                scrutinyDetail3.setKey("Block_" + block.getNumber() + "_" + "General Stair - Tread");

                ScrutinyDetail scrutinyDetailRise = new ScrutinyDetail();
                scrutinyDetailRise.addColumnHeading(1, RULE_NO);
                scrutinyDetailRise.addColumnHeading(2, FLOOR);
                scrutinyDetailRise.addColumnHeading(3, DESCRIPTION);
                scrutinyDetailRise.addColumnHeading(4, PERMISSIBLE);
                scrutinyDetailRise.addColumnHeading(5, PROVIDED);
                scrutinyDetailRise.addColumnHeading(6, STATUS);
                scrutinyDetailRise.setKey("Block_" + block.getNumber() + "_" + "General Stair - Number of rises");

                OccupancyTypeHelper mostRestrictiveOccupancyType = planDetail.getVirtualBuilding()
                        .getMostRestrictiveFarHelper();

                /*
                 * String occupancyType = mostRestrictiveOccupancy != null ? mostRestrictiveOccupancy.getOccupancyType() : null;
                 */

                List<Floor> floors = block.getBuilding().getFloors();
                // BigDecimal floorSize = block.getBuilding().getFloorsAboveGround();
                for (Floor floor : floors) {

                    boolean isTypicalRepititiveFloor = false;
                    Map<String, Object> typicalFloorValues = Util.getTypicalFloorValues(block, floor,
                            isTypicalRepititiveFloor);

                    List<org.egov.common.entity.edcr.GeneralStair> generalStairs = floor.getGeneralStairs();

                    int size = generalStairs.size();
                    generalStairCount = generalStairCount + size;

                    if (size != 0) {

                        for (org.egov.common.entity.edcr.GeneralStair generalStair : generalStairs) {
                            List<Measurement> flightPolyLines = generalStair.getFlightPolyLines();
                            List<BigDecimal> flightLengths = generalStair.getLengthOfFlights();
                            List<BigDecimal> flightWidths = generalStair.getWidthOfFlights();
                            BigDecimal noOfRises = generalStair.getNoOfRises();
                            Boolean flightPolyLineClosed = generalStair.getFlightPolyLineClosed();

                            BigDecimal minTread = BigDecimal.ZERO;
                            BigDecimal minFlightWidth = BigDecimal.ZERO;
                            String flightLayerName = String.format(DxfFileConstants.LAYER_STAIR_FLIGHT_FLOOR,
                                    block.getNumber(), floor.getNumber(), generalStair.getNumber());

                            if (!floor.getTerrace()) {
                                if (flightPolyLines != null && flightPolyLines.size() > 0) {
                                    if (flightPolyLineClosed) {
                                        if (flightWidths != null && flightWidths.size() > 0) {
                                            minFlightWidth = validateWidth(planDetail, scrutinyDetail2, floor, block,
                                                    typicalFloorValues, generalStair, flightWidths, minFlightWidth,
                                                    mostRestrictiveOccupancyType);

                                        } else {
                                            errors.put("Flight PolyLine width" + flightLayerName,
                                                    FLIGHT_WIDTH_DEFINED_DESCRIPTION + flightLayerName);
                                            planDetail.addErrors(errors);
                                        }

                                        /*
                                         * (Total length of polygons in layer BLK_n_FLR_i_STAIR_k_FLIGHT) / (Number of rises -
                                         * number of polygons in layer BLK_n_FLR_i_STAIR_k_FLIGHT - number of lines in layer
                                         * BLK_n_FLR_i_STAIR_k_FLIGHT)
                                         */

                                        if (flightLengths != null && flightLengths.size() > 0) {
                                            try {
                                                minTread = validateTread(planDetail, errors, block, scrutinyDetail3,
                                                        floor, typicalFloorValues, generalStair, flightLengths, minTread,
                                                        mostRestrictiveOccupancyType);
                                            } catch (ArithmeticException e) {
                                                LOG.info("Denominator is zero");
                                            }
                                        } else {
                                            errors.put("Flight PolyLine length" + flightLayerName,
                                                    FLIGHT_LENGTH_DEFINED_DESCRIPTION + flightLayerName);
                                            planDetail.addErrors(errors);

                                        }

                                        if (noOfRises.compareTo(BigDecimal.ZERO) > 0) {
                                            try {
                                                validateNoOfRises(planDetail, errors, block, scrutinyDetailRise, floor,
                                                        typicalFloorValues, generalStair, noOfRises);
                                            } catch (ArithmeticException e) {
                                                LOG.info("Denominator is zero");
                                            }
                                        } else {
                                            String layerName = String.format(
                                                    DxfFileConstants.LAYER_STAIR_FLIGHT_FLOOR, block.getNumber(),
                                                    floor.getNumber(), generalStair.getNumber());
                                            errors.put("noofRise" + layerName,
                                                    edcrMessageSource.getMessage(DcrConstants.OBJECTNOTDEFINED,
                                                            new String[] { NO_OF_RISES + layerName },
                                                            LocaleContextHolder.getLocale()));
                                            planDetail.addErrors(errors);
                                        }

                                    }
                                } else {
                                    errors.put("Flight PolyLine " + flightLayerName,
                                            FLIGHT_POLYLINE_NOT_DEFINED_DESCRIPTION + flightLayerName);
                                    planDetail.addErrors(errors);
                                }
                            }

                            /*
                             * List<Line> lines = fireStair.getLinesInFlightLayer(); if (lines != null && lines.size() > 0) { Line
                             * line = lines.stream().min(Comparator.comparing(Line::getLength)).get(); boolean valid = false; if
                             * (line != null) { BigDecimal lineLength = Util.roundOffTwoDecimal(line.getLength()); if (!(Boolean)
                             * typicalFloorValues.get("isTypicalRepititiveFloor")) { BigDecimal minLineLength =
                             * Util.roundOffTwoDecimal(BigDecimal.valueOf(0.75)); if (lineLength.compareTo(minLineLength) >= 0) {
                             * valid = true; } String value = typicalFloorValues.get("typicalFloors") != null ? (String)
                             * typicalFloorValues.get("typicalFloors") : " floor " + floor.getNumber(); if (valid)
                             * setReportOutputDetailsFloorStairWise(planDetail, RULE114, value, String.format(LINE_DESCRIPTION,
                             * fireStair.getNumber()), EXPECTED_LINE, String.valueOf(lineLength), Result.Accepted.getResultVal(),
                             * scrutinyDetail6); else setReportOutputDetailsFloorStairWise(planDetail, RULE114, value,
                             * String.format(LINE_DESCRIPTION, fireStair.getNumber()), EXPECTED_LINE, String.valueOf(lineLength),
                             * Result.Not_Accepted.getResultVal(), scrutinyDetail6); } } }
                             */

                            /*
                             * if (minFlightWidth.compareTo(BigDecimal.valueOf(1.2)) >= 0 &&
                             * minTread.compareTo(BigDecimal.valueOf(0.3)) >= 0 && !floor.getTerrace()) {
                             * fireStair.setGeneralStair(true); }
                             */

                        }
                    }
                }
 
                    if (block.getBuilding().getFloorsAboveGround().compareTo(BigDecimal.ONE) > 0
                            && generalStairCount == 0) {
                        errors.put("General Stair not defined in blk " + block.getNumber(),
                                "General Stair not defined in block " + block.getNumber() + ", it is mandatory for building with floors more than one.");
                        planDetail.addErrors(errors);
                    }
               
                /*
                 * boolean isAbuting = abutingList.stream().anyMatch(aBoolean -> aBoolean == true); if (occupancyType != null) {
                 * if (occupancyType.equalsIgnoreCase("RESIDENTIAL") && floorSize.compareTo(BigDecimal.valueOf(3)) > 0) { if
                 * (fireStairCount > 0) { setReportOutputDetails(planDetail, RULE42, String.format(DcrConstants.RULE114,
                 * block.getNumber()), "", DcrConstants.OBJECTDEFINED_DESC, Result.Accepted.getResultVal(), scrutinyDetail4); }
                 * else { if (spiralStairCount == 0) setReportOutputDetails(planDetail, RULE42,
                 * String.format(DcrConstants.RULE114, block.getNumber()), "Minimum 1 fire stair is required",
                 * DcrConstants.OBJECTNOTDEFINED_DESC, Result.Not_Accepted.getResultVal(), scrutinyDetail4); } } else { if
                 * (floorSize.compareTo(BigDecimal.valueOf(2)) > 0) { if (fireStairCount > 0) { setReportOutputDetails(planDetail,
                 * RULE42, String.format(DcrConstants.RULE114, block.getNumber()), "", DcrConstants.OBJECTDEFINED_DESC,
                 * Result.Accepted.getResultVal(), scrutinyDetail4); } else { if (spiralStairCount == 0)
                 * setReportOutputDetails(planDetail, RULE42, String.format(DcrConstants.RULE114, block.getNumber()), "",
                 * DcrConstants.OBJECTNOTDEFINED_DESC, Result.Not_Accepted.getResultVal(), scrutinyDetail4); } } } }
                 */

                /*
                 * if (fireStairCount > 0) { if (isAbuting) { setReportOutputDetails(planDetail, RULE114,
                 * String.format(DcrConstants.RULE114, block.getNumber()), "should abut built up area",
                 * "is abutting built up area", Result.Accepted.getResultVal(), scrutinyDetail7); } else {
                 * setReportOutputDetails(planDetail, RULE114, String.format(DcrConstants.RULE114, block.getNumber()),
                 * "should abut built up area", "is not abutting built up area", Result.Not_Accepted.getResultVal(),
                 * scrutinyDetail7); } }
                 */
            } 

        }

        return planDetail;
    }

    private BigDecimal validateWidth(Plan planDetail, ScrutinyDetail scrutinyDetail2, Floor floor, Block block,
            Map<String, Object> typicalFloorValues, org.egov.common.entity.edcr.GeneralStair generalStair,
            List<BigDecimal> flightWidths, BigDecimal minFlightWidth,
            OccupancyTypeHelper mostRestrictiveOccupancyType) {
        BigDecimal flightPolyLine = flightWidths.stream().reduce(BigDecimal::min).get();

        boolean valid = false;

        if (!(Boolean) typicalFloorValues.get("isTypicalRepititiveFloor")) {
            minFlightWidth = Util.roundOffTwoDecimal(flightPolyLine);
            BigDecimal minimumWidth = getRequiredWidth(block, mostRestrictiveOccupancyType);

            if (minFlightWidth.compareTo(minimumWidth) >= 0) {
                valid = true;
            }
            String value = typicalFloorValues.get("typicalFloors") != null
                    ? (String) typicalFloorValues.get("typicalFloors")
                    : " floor " + floor.getNumber();

            if (valid) {
                setReportOutputDetailsFloorStairWise(planDetail, RULE42_5_II, value,
                        String.format(WIDTH_DESCRIPTION, generalStair.getNumber()), minimumWidth.toString(),
                        String.valueOf(minFlightWidth), Result.Accepted.getResultVal(), scrutinyDetail2);
            } else {
                setReportOutputDetailsFloorStairWise(planDetail, RULE42_5_II, value,
                        String.format(WIDTH_DESCRIPTION, generalStair.getNumber()), minimumWidth.toString(),
                        String.valueOf(minFlightWidth), Result.Not_Accepted.getResultVal(), scrutinyDetail2);
            }
        }
        return minFlightWidth;
    }

    private BigDecimal getRequiredWidth(Block block, OccupancyTypeHelper mostRestrictiveOccupancyType) {
        if (mostRestrictiveOccupancyType.getType() != null
                && DxfFileConstants.A.equalsIgnoreCase(mostRestrictiveOccupancyType.getType().getCode())
                && block.getBuilding().getBuildingHeight().compareTo(BigDecimal.valueOf(10)) <= 0
                && block.getBuilding().getFloorsAboveGround().compareTo(BigDecimal.valueOf(3)) <= 0) {
            return BigDecimal.ONE;
        } else if (mostRestrictiveOccupancyType.getType() != null
                && DxfFileConstants.A_AF_GH.equalsIgnoreCase(mostRestrictiveOccupancyType.getType().getCode())) {
            return BigDecimal.valueOf(0.75);
        } else if (mostRestrictiveOccupancyType.getType() != null
                && DxfFileConstants.A.equalsIgnoreCase(mostRestrictiveOccupancyType.getType().getCode())) {
            return BigDecimal.valueOf(1.25);
        } else if (mostRestrictiveOccupancyType.getType() != null
                && DxfFileConstants.B.equalsIgnoreCase(mostRestrictiveOccupancyType.getType().getCode())) {
            return BigDecimal.valueOf(1.5);
        } else if (mostRestrictiveOccupancyType.getType() != null
                && DxfFileConstants.D.equalsIgnoreCase(mostRestrictiveOccupancyType.getType().getCode())) {
            return BigDecimal.valueOf(2);
        } else {
            return BigDecimal.valueOf(1.5);
        }
    }

    private BigDecimal validateTread(Plan planDetail, HashMap<String, String> errors, Block block,
            ScrutinyDetail scrutinyDetail3, Floor floor, Map<String, Object> typicalFloorValues,
            org.egov.common.entity.edcr.GeneralStair generalStair, List<BigDecimal> flightLengths, BigDecimal minTread,
            OccupancyTypeHelper mostRestrictiveOccupancyType) {
        BigDecimal totalLength = flightLengths.stream().reduce(BigDecimal.ZERO, BigDecimal::add);

        totalLength = Util.roundOffTwoDecimal(totalLength);

        BigDecimal requiredTread = getRequiredTread(mostRestrictiveOccupancyType);

        if (generalStair.getNoOfRises() != null) {
            /*
             * BigDecimal denominator = fireStair.getNoOfRises().subtract(BigDecimal.valueOf(flightLengths.size()))
             * .subtract(BigDecimal.valueOf(fireStair.getLinesInFlightLayer().size()));
             */
            BigDecimal noOfFlights = BigDecimal.valueOf(flightLengths.size());

            if (generalStair.getNoOfRises().compareTo(noOfFlights) > 0) {
                BigDecimal denominator = generalStair.getNoOfRises().subtract(noOfFlights);

                minTread = totalLength.divide(denominator, DcrConstants.DECIMALDIGITS_MEASUREMENTS,
                        DcrConstants.ROUNDMODE_MEASUREMENTS);

                boolean valid = false;

                if (!(Boolean) typicalFloorValues.get("isTypicalRepititiveFloor")) {

                    if (Util.roundOffTwoDecimal(minTread).compareTo(Util.roundOffTwoDecimal(requiredTread)) >= 0) {
                        valid = true;
                    }

                    String value = typicalFloorValues.get("typicalFloors") != null
                            ? (String) typicalFloorValues.get("typicalFloors")
                            : " floor " + floor.getNumber();
                    if (valid) {
                        setReportOutputDetailsFloorStairWise(planDetail, RULE42_5_II, value,
                                String.format(TREAD_DESCRIPTION, generalStair.getNumber()), requiredTread.toString(),
                                String.valueOf(minTread), Result.Accepted.getResultVal(), scrutinyDetail3);
                    } else {
                        setReportOutputDetailsFloorStairWise(planDetail, RULE42_5_II, value,
                                String.format(TREAD_DESCRIPTION, generalStair.getNumber()), requiredTread.toString(),
                                String.valueOf(minTread), Result.Not_Accepted.getResultVal(), scrutinyDetail3);
                    }
                }
            } else {
                if (generalStair.getNoOfRises().compareTo(BigDecimal.ZERO) > 0) {
                    String flightLayerName = String.format(DxfFileConstants.LAYER_STAIR_FLIGHT_FLOOR, block.getNumber(),
                            floor.getNumber(), generalStair.getNumber());
                    errors.put("NoOfRisesCount" + flightLayerName,
                            "Number of rises count should be greater than the count of length of flight dimensions defined in layer "
                                    + flightLayerName);
                    planDetail.addErrors(errors);
                }
            }
        }
        return minTread;
    }

    private BigDecimal getRequiredTread(OccupancyTypeHelper mostRestrictiveOccupancyType) {
        if (mostRestrictiveOccupancyType.getSubtype() != null
                && DxfFileConstants.A_AF.equalsIgnoreCase(mostRestrictiveOccupancyType.getSubtype().getCode())) {
            return BigDecimal.valueOf(0.25);
        } else {
            return BigDecimal.valueOf(0.3);
        }
    }

    private void validateNoOfRises(Plan planDetail, HashMap<String, String> errors, Block block,
            ScrutinyDetail scrutinyDetail3, Floor floor, Map<String, Object> typicalFloorValues,
            org.egov.common.entity.edcr.GeneralStair generalStair, BigDecimal noOfRises) {
        boolean valid = false;

        if (!(Boolean) typicalFloorValues.get("isTypicalRepititiveFloor")) {
            if (Util.roundOffTwoDecimal(noOfRises).compareTo(Util.roundOffTwoDecimal(BigDecimal.valueOf(12))) <= 0) {
                valid = true;
            }

            String value = typicalFloorValues.get("typicalFloors") != null
                    ? (String) typicalFloorValues.get("typicalFloors")
                    : " floor " + floor.getNumber();
            if (valid) {
                setReportOutputDetailsFloorStairWise(planDetail, RULE42_5_II, value,
                        String.format(NO_OF_RISE_DESCRIPTION, generalStair.getNumber()), EXPECTED_NO_OF_RISE,
                        String.valueOf(noOfRises), Result.Accepted.getResultVal(), scrutinyDetail3);
            } else {
                setReportOutputDetailsFloorStairWise(planDetail, RULE42_5_II, value,
                        String.format(NO_OF_RISE_DESCRIPTION, generalStair.getNumber()), EXPECTED_NO_OF_RISE,
                        String.valueOf(noOfRises), Result.Not_Accepted.getResultVal(), scrutinyDetail3);
            }
        }
    }

    /*
     * private void setReportOutputDetails(Plan pl, String ruleNo, String ruleDesc, String expected, String actual, String status,
     * ScrutinyDetail scrutinyDetail) { Map<String, String> details = new HashMap<>(); details.put(RULE_NO, ruleNo);
     * details.put(DESCRIPTION, ruleDesc); details.put(REQUIRED, expected); details.put(PROVIDED, actual); details.put(STATUS,
     * status); scrutinyDetail.getDetail().add(details); pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail); }
     */

    private void setReportOutputDetailsFloorStairWise(Plan pl, String ruleNo, String floor, String description,
            String expected, String actual, String status, ScrutinyDetail scrutinyDetail) {
        Map<String, String> details = new HashMap<>();
        details.put(RULE_NO, ruleNo);
        details.put(FLOOR, floor);
        details.put(DESCRIPTION, description);
        details.put(PERMISSIBLE, expected);
        details.put(PROVIDED, actual);
        details.put(STATUS, status);
        scrutinyDetail.getDetail().add(details);
        pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);
    }

    /*
     * private void validateDimensions(Plan planDetail, String blockNo, int floorNo, String stairNo, List<Measurement>
     * flightPolyLines) { int count = 0; for (Measurement m : flightPolyLines) { if (m.getInvalidReason() != null &&
     * m.getInvalidReason().length() > 0) { count++; } } if (count > 0) { planDetail.addError(String.format(DxfFileConstants.
     * LAYER_FIRESTAIR_FLIGHT_FLOOR, blockNo, floorNo, stairNo), count +
     * " number of flight polyline not having only 4 points in layer " +
     * String.format(DxfFileConstants.LAYER_FIRESTAIR_FLIGHT_FLOOR, blockNo, floorNo, stairNo)); } }
     */

    @Override
    public Map<String, Date> getAmendments() {
        return new LinkedHashMap<>();
    }

}
