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

import static org.egov.common.entity.edcr.OccupancyType.OCCUPANCY_A1;
import static org.egov.common.entity.edcr.OccupancyType.OCCUPANCY_A2;
import static org.egov.common.entity.edcr.OccupancyType.OCCUPANCY_A3;
import static org.egov.common.entity.edcr.OccupancyType.OCCUPANCY_A4;
import static org.egov.common.entity.edcr.OccupancyType.OCCUPANCY_A5;
import static org.egov.common.entity.edcr.OccupancyType.OCCUPANCY_B1;
import static org.egov.common.entity.edcr.OccupancyType.OCCUPANCY_B2;
import static org.egov.common.entity.edcr.OccupancyType.OCCUPANCY_B3;
import static org.egov.common.entity.edcr.OccupancyType.OCCUPANCY_C;
import static org.egov.common.entity.edcr.OccupancyType.OCCUPANCY_C1;
import static org.egov.common.entity.edcr.OccupancyType.OCCUPANCY_C2;
import static org.egov.common.entity.edcr.OccupancyType.OCCUPANCY_C3;
import static org.egov.common.entity.edcr.OccupancyType.OCCUPANCY_D;
import static org.egov.common.entity.edcr.OccupancyType.OCCUPANCY_D1;
import static org.egov.common.entity.edcr.OccupancyType.OCCUPANCY_D2;
import static org.egov.common.entity.edcr.OccupancyType.OCCUPANCY_E;
import static org.egov.common.entity.edcr.OccupancyType.OCCUPANCY_F;
import static org.egov.common.entity.edcr.OccupancyType.OCCUPANCY_F1;
import static org.egov.common.entity.edcr.OccupancyType.OCCUPANCY_F2;
import static org.egov.common.entity.edcr.OccupancyType.OCCUPANCY_F3;
import static org.egov.common.entity.edcr.OccupancyType.OCCUPANCY_F4;
import static org.egov.common.entity.edcr.OccupancyType.OCCUPANCY_G1;
import static org.egov.common.entity.edcr.OccupancyType.OCCUPANCY_G2;
import static org.egov.common.entity.edcr.OccupancyType.OCCUPANCY_H;
import static org.egov.common.entity.edcr.OccupancyType.OCCUPANCY_I1;
import static org.egov.common.entity.edcr.OccupancyType.OCCUPANCY_I2;
import static org.egov.edcr.utility.DcrConstants.DECIMALDIGITS_MEASUREMENTS;
import static org.egov.edcr.utility.DcrConstants.NA;
import static org.egov.edcr.utility.DcrConstants.NO;
import static org.egov.edcr.utility.DcrConstants.OBJECTNOTDEFINED;
import static org.egov.edcr.utility.DcrConstants.ROUNDMODE_MEASUREMENTS;
import static org.egov.edcr.utility.DcrConstants.SIDE_YARD1_DESC;
import static org.egov.edcr.utility.DcrConstants.SIDE_YARD2_DESC;
import static org.egov.edcr.utility.DcrConstants.SIDE_YARD_DESC;
import static org.egov.edcr.utility.DcrConstants.YES;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.egov.common.entity.edcr.Block;
import org.egov.common.entity.edcr.Building;
import org.egov.common.entity.edcr.Occupancy;
import org.egov.common.entity.edcr.OccupancyType;
import org.egov.common.entity.edcr.Plan;
import org.egov.common.entity.edcr.Plot;
import org.egov.common.entity.edcr.Result;
import org.egov.common.entity.edcr.ScrutinyDetail;
import org.egov.common.entity.edcr.SetBack;
import org.egov.common.entity.edcr.Yard;
import org.egov.edcr.utility.DcrConstants;
import org.springframework.stereotype.Service;

@Service
public class SideYardService extends GeneralRule {
    private static final double VALUE_0_5 = 0.5;
    private static final String RULE_54_3 = "54(3)";

    private static final String RULE_54_3_II = "54-(3-ii)";
    private static final String RULE_56_3_D = "56-(3d)";
    private static final String RULE_55_2_1 = "55-2-(1)";
    private static final String RULE_55_2_2 = "55-2-(2)";
    private static final String RULE_55_2_PROV = "55-2(Prov)";
    private static final String RULE_55_2_3 = "55-2-(3)";
    private static final String RULE_57_4 = "57(4)";
    private static final String RULE_59_3 = "59(3)";
    private static final String RULE_62_1_A = "62-(1-a)";
    private static final String SUB_RULE_24_3 = "24(3)";
    private static final String SUB_RULE_24_5 = "24(5)";
    private static final String RULE_59_11 = "59(11)";
    private static final String SUB_RULE_24_5_PROVISION1 = "24(5) Provision1";
    private static final String SUB_RULE_24_5_PROVISION2 = "24(5) Provision2";
    private static final String SUB_RULE_24_12 = "24(12)";
    private static final BigDecimal FIVE = BigDecimal.valueOf(5);
    private static final BigDecimal SIDEVALUE_ZERO = BigDecimal.valueOf(0);
    private static final BigDecimal SIDEVALUE_SIXTY_CM = BigDecimal.valueOf(0.60);
    private static final BigDecimal SIDEVALUE_SIXTYONE_CM = BigDecimal.valueOf(0.601);

    private static final BigDecimal SIDEVALUE_SEVENTYFIVE_CM = BigDecimal.valueOf(0.75);
    private static final BigDecimal SIDEVALUE_NINTY_CM = BigDecimal.valueOf(0.90);

    private static final BigDecimal SIDEVALUE_ONE = BigDecimal.valueOf(1);
    private static final BigDecimal SIDEVALUE_ONE_TWO = BigDecimal.valueOf(1.2);

    private static final BigDecimal SIDEVALUE_ONEPOINTFIVE = BigDecimal.valueOf(1.5);
    private static final BigDecimal SIDEVALUE_TWO = BigDecimal.valueOf(2);
    private static final BigDecimal SIDEVALUE_THREE = BigDecimal.valueOf(3);
    private static final BigDecimal SIDEVALUE_FOUR = BigDecimal.valueOf(4);
    private static final BigDecimal SIDEVALUE_FIVE = BigDecimal.valueOf(5);
    private static final BigDecimal SIDEVALUE_SEVEN_FIVE = BigDecimal.valueOf(7.5);

    private static final int SITEAREA_125 = 125;
    private static final int BUILDUPAREA_300 = 300;
    private static final int BUILDUPAREA_150 = 150;

    private static final int FLOORAREA_800 = 800;
    private static final int FLOORAREA_500 = 500;
    private static final int FLOORAREA_300 = 300;
    private static final String SIDENUMBER = "Side Number";
    private static final String MEANMINIMUMLABEL = "(Minimum distance,Mean distance) ";

    private class SideYardResult {
        String rule;
        String subRule;
        String blockName;
        Integer level;
        BigDecimal actualMeanDistance = BigDecimal.ZERO;
        BigDecimal actualDistance = BigDecimal.ZERO;
        String occupancy;
        BigDecimal expectedDistance = BigDecimal.ZERO;
        BigDecimal expectedmeanDistance = BigDecimal.ZERO;
        boolean status = false;
    }

    public void processSideYard(final Plan pl) {

        Plot plot = pl.getPlot();
        if (plot == null)
            return;

        validateSideYardRule(pl);

        // Side yard 1 and side yard 2 both may not mandatory in same levels. Get previous level side yards in this case.
        // In case of side yard 1 defined and other side not required, then consider other side as zero distance ( in case of noc
        // provided cases).

        Boolean valid = false;
        if (plot != null && !pl.getBlocks().isEmpty()) {
            for (Block block : pl.getBlocks()) {  // for each block
                scrutinyDetail = new ScrutinyDetail();
                scrutinyDetail.addColumnHeading(1, RULE_NO);
                scrutinyDetail.addColumnHeading(2, LEVEL);
                scrutinyDetail.addColumnHeading(3, OCCUPANCY);
                scrutinyDetail.addColumnHeading(4, SIDENUMBER);
                scrutinyDetail.addColumnHeading(5, FIELDVERIFIED);
                scrutinyDetail.addColumnHeading(6, REQUIRED);
                scrutinyDetail.addColumnHeading(7, PROVIDED);
                scrutinyDetail.addColumnHeading(8, STATUS);
                SideYardResult sideYard1Result = new SideYardResult();
                SideYardResult sideYard2Result = new SideYardResult();

                for (SetBack setback : block.getSetBacks()) {
                    Yard sideYard1 = null;
                    Yard sideYard2 = null;

                    if (setback.getSideYard1() != null && setback.getSideYard1().getMean().compareTo(BigDecimal.ZERO) > 0) {
                        sideYard1 = setback.getSideYard1();
                    }
                    if (setback.getSideYard2() != null && setback.getSideYard2().getMean().compareTo(BigDecimal.ZERO) > 0) {
                        sideYard2 = setback.getSideYard2();
                    }

                    if (sideYard1 != null || sideYard2 != null) {
                        if (sideYard1 == null)// mean side yard2 present and defined only in that level. Get sideyard1 from
                                              // previous level.
                        {
                            SetBack sideYardSetback = block.getLowerLevelSetBack(setback.getLevel(), SIDE_YARD1_DESC);
                            if (sideYardSetback != null && sideYardSetback.getSideYard1() != null &&
                                    sideYardSetback.getSideYard1().getMean().compareTo(BigDecimal.ZERO) > 0) {
                                sideYard1 = sideYardSetback.getSideYard1();
                            }
                        }
                        if (sideYard2 == null)// mean side yard1 present and defined only in that level. Get sideyard2 from
                                              // previous level.
                        {
                            SetBack sideYardSetback = block.getLowerLevelSetBack(setback.getLevel(), SIDE_YARD2_DESC);
                            if (sideYardSetback != null && sideYardSetback.getSideYard2() != null &&
                                    sideYardSetback.getSideYard2().getMean().compareTo(BigDecimal.ZERO) > 0) {
                                sideYard2 = sideYardSetback.getSideYard2();
                            } else {
                                sideYard2 = new Yard();
                                sideYard2.setHeight(BigDecimal.ZERO);
                                sideYard2.setMinimumDistance(BigDecimal.ZERO);
                                sideYard2.setMean(BigDecimal.ZERO);
                            }// If side yard 2 not defined in case of NOC to abut side, then using empty yard.
                        }
                    }

                    BigDecimal buildingHeight;
                    if (sideYard1 != null && sideYard2 != null) {
                        // If there is changes in height of building, then consider the maximum height among both side
                        if (sideYard1.getHeight() != null && sideYard1.getHeight().compareTo(BigDecimal.ZERO) > 0
                                && sideYard2.getHeight() != null && sideYard2.getHeight().compareTo(BigDecimal.ZERO) > 0) {
                            buildingHeight = sideYard1.getHeight().compareTo(sideYard2.getHeight()) >= 0 ? sideYard1.getHeight()
                                    : sideYard2.getHeight();
                        } else {
                            buildingHeight = sideYard1.getHeight() != null && sideYard1.getHeight().compareTo(BigDecimal.ZERO) > 0
                                    ? sideYard1.getHeight()
                                    : sideYard2.getHeight() != null && sideYard2.getHeight().compareTo(BigDecimal.ZERO) > 0
                                            ? sideYard2.getHeight()
                                            : block.getBuilding().getBuildingHeight();
                        }

                        double minlength = 0;
                        double max = 0;
                        double minMeanlength = 0;
                        double maxMeanLength = 0;
                        if (sideYard2 != null && sideYard1 != null) {
                            /*
                             * minlength = sideYard2.getMinimumDistance().doubleValue(); max =
                             * sideYard1.getMinimumDistance().doubleValue(); minMeanlength= sideYard2.getMean().doubleValue();
                             * maxMeanLength=sideYard1.getMean().doubleValue(); }
                             */
                            if (sideYard2.getMinimumDistance().doubleValue() > sideYard1.getMinimumDistance().doubleValue()) {
                                minlength = sideYard1.getMinimumDistance().doubleValue();
                                max = sideYard2.getMinimumDistance().doubleValue();
                                minMeanlength = sideYard1.getMean().doubleValue();
                                maxMeanLength = sideYard2.getMean().doubleValue();

                            } else {
                                minlength = sideYard2.getMinimumDistance().doubleValue();
                                max = sideYard1.getMinimumDistance().doubleValue();
                                minMeanlength = sideYard2.getMean().doubleValue();
                                maxMeanLength = sideYard1.getMean().doubleValue();
                            }
                        }

                        if (buildingHeight != null && (minlength > 0 || max > 0)) {
                            for (final Occupancy occupancy : block.getBuilding().getTotalArea()) {
                                scrutinyDetail.setKey("Block_" + block.getName() + "_" + "Side Yard");
                                if (-1 == setback.getLevel()) {
                                    scrutinyDetail.setKey("Block_" + block.getName() + "_" + "Basement Side Yard");
                                    checkSideYardLessThanTenOrEqualToMts(pl, block.getBuilding(), BigDecimal.valueOf(10),
                                            block.getName(), setback.getLevel(), plot,
                                            minlength, max, minMeanlength, maxMeanLength, occupancy.getType(), sideYard1Result,
                                            sideYard2Result);

                                } else if (buildingHeight.compareTo(BigDecimal.valueOf(10)) <= 0)
                                    checkSideYardLessThanTenOrEqualToMts(pl, block.getBuilding(), buildingHeight, block.getName(),
                                            setback.getLevel(), plot,
                                            minlength, max, minMeanlength, maxMeanLength, occupancy.getType(), sideYard1Result,
                                            sideYard2Result);
                                else if (buildingHeight.compareTo(BigDecimal.valueOf(10)) > 0
                                        && buildingHeight.compareTo(BigDecimal.valueOf(16)) <= 0)
                                    checkSideYardBetweenTenToSixteenMts(pl, block.getBuilding(), buildingHeight, block.getName(),
                                            setback.getLevel(), plot, minlength, max, minMeanlength, maxMeanLength,
                                            occupancy.getType(), sideYard1Result, sideYard2Result);
                                else if (buildingHeight.compareTo(BigDecimal.valueOf(16)) > 0)
                                    checkSideYardMoreThanSixteenMts(pl, block.getBuilding(), buildingHeight, block.getName(),
                                            setback.getLevel(), plot, minlength, max, minMeanlength, maxMeanLength,
                                            occupancy.getType(), sideYard1Result, sideYard2Result);
                            }

                            if (sideYard1Result != null) {
                                Map<String, String> details = new HashMap<>();
                                details.put(RULE_NO, sideYard1Result.subRule);
                                details.put(LEVEL, sideYard1Result.level != null ? sideYard1Result.level.toString() : "");
                                details.put(OCCUPANCY, sideYard1Result.occupancy);
                                /*
                                 * if (sideYard1Result.expectedmeanDistance != null &&
                                 * sideYard1Result.expectedmeanDistance.compareTo(BigDecimal.valueOf(0)) == 0) {
                                 * details.put(FIELDVERIFIED, MINIMUMLABEL); details.put(REQUIRED,
                                 * sideYard1Result.expectedDistance.toString()); details.put(PROVIDED,
                                 * sideYard1Result.actualDistance.toString()); } else {
                                 */
                                details.put(FIELDVERIFIED, MEANMINIMUMLABEL);
                                details.put(REQUIRED, "(" + sideYard1Result.expectedDistance + ","
                                        + sideYard1Result.expectedmeanDistance + ")");
                                details.put(PROVIDED,
                                        "(" + sideYard1Result.actualDistance + "," + sideYard1Result.actualMeanDistance + ")");
                                // }
                                details.put(SIDENUMBER, SIDE_YARD1_DESC);
                                /*
                                 * details.put(REQUIRED, sideYard1Result.expectedDistance.toString()); details.put(PROVIDED,
                                 * sideYard1Result.actualDistance.toString());
                                 */
                                if (sideYard1Result.status) {
                                    details.put(STATUS, Result.Accepted.getResultVal());
                                } else {
                                    details.put(STATUS, Result.Not_Accepted.getResultVal());
                                }

                                scrutinyDetail.getDetail().add(details);
                                pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);
                            }
                            if (sideYard2Result != null) {
                                Map<String, String> detailsSideYard2 = new HashMap<>();
                                detailsSideYard2.put(RULE_NO, sideYard2Result.subRule);
                                detailsSideYard2.put(LEVEL,
                                        sideYard2Result.level != null ? sideYard2Result.level.toString() : "");
                                detailsSideYard2.put(OCCUPANCY, sideYard2Result.occupancy);
                                detailsSideYard2.put(SIDENUMBER, SIDE_YARD2_DESC);
                                /*
                                 * detailsSideYard2.put(REQUIRED, sideYard2Result.expectedDistance.toString());
                                 * detailsSideYard2.put(PROVIDED, sideYard2Result.actualDistance.toString());
                                 */
                                /*
                                 * if (sideYard2Result.expectedmeanDistance != null &&
                                 * sideYard2Result.expectedmeanDistance.compareTo(BigDecimal.valueOf(0)) == 0) {
                                 * detailsSideYard2.put(FIELDVERIFIED, MINIMUMLABEL); detailsSideYard2.put(REQUIRED,
                                 * sideYard2Result.expectedDistance.toString()); detailsSideYard2.put(PROVIDED,
                                 * sideYard2Result.actualDistance.toString()); } else {
                                 */
                                detailsSideYard2.put(FIELDVERIFIED, MEANMINIMUMLABEL);
                                detailsSideYard2.put(REQUIRED, "(" + sideYard2Result.expectedDistance + ","
                                        + sideYard2Result.expectedmeanDistance + ")");
                                detailsSideYard2.put(PROVIDED,
                                        "(" + sideYard2Result.actualDistance + "," + sideYard2Result.actualMeanDistance + ")");
                                // }
                                if (sideYard2Result.status) {
                                    detailsSideYard2.put(STATUS, Result.Accepted.getResultVal());
                                } else {
                                    detailsSideYard2.put(STATUS, Result.Not_Accepted.getResultVal());
                                }

                                scrutinyDetail.getDetail().add(detailsSideYard2);
                                pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);
                            }
                        }
                    }
                }
            }
        }

    }

    private void checkSideYardMoreThanSixteenMts(final Plan pl, Building building, BigDecimal buildingHeight, String blockName,
            Integer level, final Plot plot, final double min, final double max, double minMeanlength, double maxMeanLength,
            final OccupancyType mostRestrictiveOccupancy, SideYardResult sideYard1Result, SideYardResult sideYard2Result) {
        String subRule = SUB_RULE_24_3;
        String rule = SIDE_YARD_DESC;
        Boolean valid2 = false;
        Boolean valid1 = false;
        BigDecimal side2val = SIDEVALUE_ONE;
        BigDecimal side1val = SIDEVALUE_ONE_TWO;
        BigDecimal side2Meanval = SIDEVALUE_ZERO;
        BigDecimal side1Meanval = SIDEVALUE_ZERO;

        if (mostRestrictiveOccupancy.equals(OCCUPANCY_A1) ||
                mostRestrictiveOccupancy.equals(OCCUPANCY_A2) ||
                mostRestrictiveOccupancy.equals(OCCUPANCY_A3) ||
                mostRestrictiveOccupancy.equals(OCCUPANCY_A4) ||
                mostRestrictiveOccupancy.equals(OCCUPANCY_A5) ||
                mostRestrictiveOccupancy.equals(OCCUPANCY_F) || mostRestrictiveOccupancy.equals(OCCUPANCY_F3)) {

            if (plot.getArea().compareTo(BigDecimal.valueOf(SITEAREA_125)) <= 0) {
                // rule = RULE_62;
                subRule = RULE_62_1_A;
                side2val = SIDEVALUE_SIXTY_CM;
                side1val = SIDEVALUE_NINTY_CM;
                side2Meanval = side2val;
                side1Meanval = side1val;
            } else {
                subRule = SUB_RULE_24_3;
                // rule = RULE24;
                side2val = SIDEVALUE_ONE;
                side1val = SIDEVALUE_ONE_TWO;
                side2Meanval = side2val;
                side1Meanval = side1val;
            }
            if (buildingHeight != null && buildingHeight.compareTo(BigDecimal.TEN) >= 0) {
                // rule = RULE_24;
                subRule = SUB_RULE_24_3;
                final BigDecimal distanceIncrementBasedOnHeight = BigDecimal.valueOf(VALUE_0_5)
                        .multiply(BigDecimal.valueOf(Math.ceil(buildingHeight.subtract(BigDecimal.TEN)
                                .divide(BigDecimal.valueOf(3), DECIMALDIGITS_MEASUREMENTS, ROUNDMODE_MEASUREMENTS)
                                .doubleValue())));

                side2val = side2val.add(distanceIncrementBasedOnHeight);
                side1val = side1val.add(distanceIncrementBasedOnHeight);

                side2Meanval = side2Meanval.add(distanceIncrementBasedOnHeight);
                side1Meanval = side1Meanval.add(distanceIncrementBasedOnHeight);

                // if building is high rise building, then from level 0, minimum length should be 5 mts in one side.
                if (level >= 0 && building.getBuildingHeight().compareTo(BigDecimal.valueOf(16)) > 0) {
                    if (side1val.compareTo(FIVE) <= 0) {
                        side1val = FIVE;
                        side1Meanval = FIVE;
                    }
                }

                if (max >= side1val.doubleValue() && maxMeanLength >= side1Meanval.doubleValue())
                    valid1 = true;
                if (min >= side2val.doubleValue() && minMeanlength >= side2Meanval.doubleValue())
                    valid2 = true;

                compareSideYard2Result(blockName, side2val, BigDecimal.valueOf(min), side2Meanval,
                        BigDecimal.valueOf(minMeanlength), mostRestrictiveOccupancy, sideYard2Result, valid2, subRule, rule,
                        level);
                compareSideYard1Result(blockName, side1val, BigDecimal.valueOf(max), side1Meanval,
                        BigDecimal.valueOf(maxMeanLength), mostRestrictiveOccupancy, sideYard1Result, valid1, subRule, rule,
                        level);
            }
        } else {
            final BigDecimal distanceIncrementBasedOnHeight = BigDecimal.valueOf(VALUE_0_5)
                    .multiply(BigDecimal
                            .valueOf(Math.ceil(buildingHeight.subtract(BigDecimal.TEN)
                                    .divide(BigDecimal.valueOf(3), DECIMALDIGITS_MEASUREMENTS, ROUNDMODE_MEASUREMENTS)
                                    .doubleValue())));

            processSideYardForOccupanciesOtherThanA1A2F(pl, building, plot, blockName, level, min, max, minMeanlength,
                    maxMeanLength, mostRestrictiveOccupancy, buildingHeight, side2val, side1val,
                    distanceIncrementBasedOnHeight, true, sideYard1Result, sideYard2Result);

        }

    }

    private void checkSideYardBetweenTenToSixteenMts(final Plan pl, Building building, BigDecimal buildingHeight,
            String blockName, Integer level, final Plot plot, final double min,
            final double max, double minMeanlength, double maxMeanLength,
            final OccupancyType mostRestrictiveOccupancy, SideYardResult sideYard1Result, SideYardResult sideYard2Result) {
        String subRule = SUB_RULE_24_3;
        String rule = SIDE_YARD_DESC;

        Boolean valid2 = false;
        Boolean valid1 = false;
        BigDecimal side2val = SIDEVALUE_ONE;
        BigDecimal side1val = SIDEVALUE_ONE_TWO;
        BigDecimal side2Meanval = SIDEVALUE_ZERO;
        BigDecimal side1Meanval = SIDEVALUE_ZERO;

        if (mostRestrictiveOccupancy.equals(OCCUPANCY_A1) ||
                mostRestrictiveOccupancy.equals(OCCUPANCY_A2) ||
                mostRestrictiveOccupancy.equals(OCCUPANCY_A3) ||
                mostRestrictiveOccupancy.equals(OCCUPANCY_A4) ||
                mostRestrictiveOccupancy.equals(OCCUPANCY_A5) ||
                mostRestrictiveOccupancy.equals(OCCUPANCY_F) || mostRestrictiveOccupancy.equals(OCCUPANCY_F3)) {

            if (plot.getArea().compareTo(BigDecimal.valueOf(SITEAREA_125)) <= 0) {
                // rule = RULE_62;
                subRule = RULE_62_1_A;
                side2val = SIDEVALUE_SIXTY_CM;
                side1val = SIDEVALUE_NINTY_CM;
                side2Meanval = side2val;
                side1Meanval = side1val;
            } else {
                subRule = SUB_RULE_24_3;
                // rule = RULE24;
                side2val = SIDEVALUE_ONE;
                side1val = SIDEVALUE_ONE_TWO;
                side2Meanval = side2val;
                side1Meanval = side1val;
            }
            if (buildingHeight != null && buildingHeight.compareTo(BigDecimal.TEN) >= 0) {
                // rule = RULE_24;
                subRule = SUB_RULE_24_3;
                final BigDecimal distanceIncrementBasedOnHeight = BigDecimal.valueOf(VALUE_0_5)
                        .multiply(BigDecimal.valueOf(Math.ceil(buildingHeight.subtract(BigDecimal.TEN)
                                .divide(BigDecimal.valueOf(3), DECIMALDIGITS_MEASUREMENTS, ROUNDMODE_MEASUREMENTS)
                                .doubleValue())));
                side1val = side1val.add(distanceIncrementBasedOnHeight);
                side2val = side2val.add(distanceIncrementBasedOnHeight);

                side2Meanval = side2Meanval.add(distanceIncrementBasedOnHeight);
                side1Meanval = side1Meanval.add(distanceIncrementBasedOnHeight);

                // if building is high rise building, then from level 1, minimum length should be 5 mts in one side.
                if (level >= 0 && building.getBuildingHeight().compareTo(BigDecimal.valueOf(16)) > 0) {
                    if (side1val.compareTo(FIVE) <= 0) {
                        side1val = FIVE;
                        side1Meanval = FIVE;
                    }
                }

                if (max >= side1val.doubleValue() && maxMeanLength >= side1Meanval.doubleValue())
                    valid1 = true;
                if (min >= side2val.doubleValue() && minMeanlength >= side2Meanval.doubleValue())
                    valid2 = true;

                compareSideYard2Result(blockName, side2val, BigDecimal.valueOf(min), side2Meanval,
                        BigDecimal.valueOf(minMeanlength), mostRestrictiveOccupancy, sideYard2Result, valid2, subRule, rule,
                        level);
                compareSideYard1Result(blockName, side1val, BigDecimal.valueOf(max), side1Meanval,
                        BigDecimal.valueOf(maxMeanLength), mostRestrictiveOccupancy, sideYard1Result, valid1, subRule, rule,
                        level);
            }
        } else {
            final BigDecimal distanceIncrementBasedOnHeight = BigDecimal.valueOf(VALUE_0_5)
                    .multiply(BigDecimal
                            .valueOf(Math.ceil(buildingHeight.subtract(BigDecimal.TEN)
                                    .divide(BigDecimal.valueOf(3), DECIMALDIGITS_MEASUREMENTS, ROUNDMODE_MEASUREMENTS)
                                    .doubleValue())));

            processSideYardForOccupanciesOtherThanA1A2F(pl, building, plot, blockName, level, min, max, minMeanlength,
                    maxMeanLength, mostRestrictiveOccupancy, buildingHeight, side2val, side1val,
                    distanceIncrementBasedOnHeight, false, sideYard1Result, sideYard2Result);

        }
    }

    private void processSideYardForOccupanciesOtherThanA1A2F(final Plan pl, Building building, final Plot plot, String blockName,
            Integer level, final double min,
            final double max, double minMeanlength, double maxMeanLength,
            final OccupancyType mostRestrictiveOccupancy,
            final BigDecimal buildingHeight,
            BigDecimal side2val, BigDecimal side1val, final BigDecimal distanceIncrementBasedOnHeight,
            final Boolean checkMinimum5mtsCondition, SideYardResult sideYard1Result, SideYardResult sideYard2Result) {

        Boolean valid2 = false;
        Boolean valid1 = false;

        String subRule = SUB_RULE_24_5;
        String rule = SIDE_YARD_DESC;

        BigDecimal side2Meanval = SIDEVALUE_ZERO;
        BigDecimal side1Meanval = SIDEVALUE_ZERO;

        if (mostRestrictiveOccupancy.equals(OCCUPANCY_B1) ||
                mostRestrictiveOccupancy.equals(OCCUPANCY_B2) ||
                mostRestrictiveOccupancy.equals(OCCUPANCY_B3) ||
                mostRestrictiveOccupancy.equals(OCCUPANCY_C) ||
                mostRestrictiveOccupancy.equals(OCCUPANCY_C1) ||
                mostRestrictiveOccupancy.equals(OCCUPANCY_C2) ||
                mostRestrictiveOccupancy.equals(OCCUPANCY_C3)) {

            if (buildingHeight.compareTo(BigDecimal.valueOf(7)) <= 0) {
                subRule = SUB_RULE_24_5;
                // rule = RULE24;
                if (building.getTotalBuitUpArea().compareTo(BigDecimal.valueOf(BUILDUPAREA_150)) < 0) {// check this
                    if (pl.getPlanInformation().getOpeningOnSideBelow2mtsDesc().equalsIgnoreCase(DcrConstants.YES)) {
                        side2val = distanceIncrementBasedOnHeight.add(SIDEVALUE_ONE);
                        side1val = distanceIncrementBasedOnHeight.add(SIDEVALUE_ONE_TWO);
                        side2Meanval = side2val;
                        side1Meanval = side1val;
                    } else {
                        if (pl.getPlanInformation().getOpeningOnSideAbove2mtsDesc().equalsIgnoreCase(DcrConstants.YES)) {

                            side2val = distanceIncrementBasedOnHeight.add(SIDEVALUE_SEVENTYFIVE_CM);
                            side1val = distanceIncrementBasedOnHeight.add(SIDEVALUE_ONE_TWO);
                            side2Meanval = side2val;
                            side1Meanval = side1val;
                        } else {
                            if (pl.getPlanInformation().getNocToAbutSideDesc().equalsIgnoreCase(DcrConstants.YES)) {
                                side2val = distanceIncrementBasedOnHeight.add(SIDEVALUE_ZERO);
                                side1val = distanceIncrementBasedOnHeight.add(SIDEVALUE_ONE_TWO);
                                side2Meanval = side2val;
                                side1Meanval = side1val;
                            } else {
                                side2val = distanceIncrementBasedOnHeight.add(SIDEVALUE_SEVENTYFIVE_CM);
                                side1val = distanceIncrementBasedOnHeight.add(SIDEVALUE_ONE_TWO);
                                side2Meanval = side2val;
                                side1Meanval = side1val;
                            }
                        }
                    }

                } else {
                    if (building.getTotalBuitUpArea().compareTo(BigDecimal.valueOf(BUILDUPAREA_150)) >= 0
                            &&
                            building.getTotalBuitUpArea()
                                    .compareTo(BigDecimal.valueOf(BUILDUPAREA_300)) <= 0) {
                        side2val = distanceIncrementBasedOnHeight.add(SIDEVALUE_ONE);
                        side1val = distanceIncrementBasedOnHeight.add(SIDEVALUE_ONE_TWO);
                        side2Meanval = side2val;
                        side1Meanval = side1val;
                    } else {
                        // rule = RULE_54;
                        subRule = RULE_54_3_II;
                        side2val = distanceIncrementBasedOnHeight.add(SIDEVALUE_ONEPOINTFIVE);
                        side1val = distanceIncrementBasedOnHeight.add(SIDEVALUE_ONEPOINTFIVE);
                        side2Meanval = distanceIncrementBasedOnHeight.add(SIDEVALUE_TWO);
                        side1Meanval = distanceIncrementBasedOnHeight.add(SIDEVALUE_TWO);
                    }
                }
            } else {
                // rule = RULE_54;
                subRule = RULE_54_3_II;
                if (building.getTotalBuitUpArea().compareTo(BigDecimal.valueOf(BUILDUPAREA_150)) < 0) {

                    side2val = distanceIncrementBasedOnHeight.add(SIDEVALUE_ONE);
                    side1val = distanceIncrementBasedOnHeight.add(SIDEVALUE_ONE_TWO);
                    side2Meanval = side2val;
                    side1Meanval = side1val;
                } else {
                    if (building.getTotalBuitUpArea().compareTo(BigDecimal.valueOf(BUILDUPAREA_150)) >= 0
                            &&
                            building.getTotalBuitUpArea()
                                    .compareTo(BigDecimal.valueOf(BUILDUPAREA_300)) <= 0) {
                        side2val = distanceIncrementBasedOnHeight.add(SIDEVALUE_ONE);
                        side1val = distanceIncrementBasedOnHeight.add(SIDEVALUE_ONE_TWO);
                        side2Meanval = side2val;
                        side1Meanval = side1val;
                    } else {
                        side2val = distanceIncrementBasedOnHeight.add(SIDEVALUE_ONEPOINTFIVE);
                        side1val = distanceIncrementBasedOnHeight.add(SIDEVALUE_ONEPOINTFIVE);
                        side2Meanval = distanceIncrementBasedOnHeight.add(SIDEVALUE_TWO);
                        side1Meanval = distanceIncrementBasedOnHeight.add(SIDEVALUE_TWO);
                    }
                }

            }
            /*
             * if (max >= side2val.doubleValue()) valid2 = true; if (min >= side1val.doubleValue()) valid1 = true;
             */

        } else if (mostRestrictiveOccupancy.equals(OCCUPANCY_E) ||
                mostRestrictiveOccupancy.equals(OCCUPANCY_H)) {
            subRule = SUB_RULE_24_5;
            // rule = DcrConstants.RULE24;
            if (building.getTotalBuitUpArea().compareTo(BigDecimal.valueOf(BUILDUPAREA_300)) < 0) {
                if (buildingHeight.compareTo(BigDecimal.valueOf(7)) <= 0) {
                    if (pl.getPlanInformation().getOpeningOnSideBelow2mtsDesc().equalsIgnoreCase(DcrConstants.YES)) {
                        side2val = distanceIncrementBasedOnHeight.add(SIDEVALUE_ONE);
                        side1val = distanceIncrementBasedOnHeight.add(SIDEVALUE_ONE_TWO);
                        side2Meanval = side2val;
                        side1Meanval = side1val;
                    } else {
                        if (pl.getPlanInformation().getOpeningOnSideAbove2mtsDesc().equalsIgnoreCase(DcrConstants.YES)) {

                            side2val = distanceIncrementBasedOnHeight.add(SIDEVALUE_SEVENTYFIVE_CM);
                            side1val = distanceIncrementBasedOnHeight.add(SIDEVALUE_ONE_TWO);
                            side2Meanval = side2val;
                            side1Meanval = side1val;

                        } else {
                            if (pl.getPlanInformation().getNocToAbutSideDesc().equalsIgnoreCase(DcrConstants.YES)) {
                                side2val = distanceIncrementBasedOnHeight.add(SIDEVALUE_ZERO);
                                side1val = distanceIncrementBasedOnHeight.add(SIDEVALUE_ONE_TWO);
                                side2Meanval = side2val;
                                side1Meanval = side1val;
                            } else {
                                side2val = distanceIncrementBasedOnHeight.add(SIDEVALUE_SEVENTYFIVE_CM);
                                side1val = distanceIncrementBasedOnHeight.add(SIDEVALUE_ONE_TWO);
                                side2Meanval = side2val;
                                side1Meanval = side1val;
                            }
                        }
                    }
                } else {
                    side2val = distanceIncrementBasedOnHeight.add(SIDEVALUE_ONE);
                    side1val = distanceIncrementBasedOnHeight.add(SIDEVALUE_ONE_TWO);
                    side2Meanval = side2val;
                    side1Meanval = side1val;
                }
            } else {
                // rule = RULE_54;
                subRule = RULE_54_3;
                side2val = distanceIncrementBasedOnHeight.add(SIDEVALUE_ONEPOINTFIVE);
                side1val = distanceIncrementBasedOnHeight.add(SIDEVALUE_ONEPOINTFIVE);
                side2Meanval = distanceIncrementBasedOnHeight.add(SIDEVALUE_TWO);
                side1Meanval = distanceIncrementBasedOnHeight.add(SIDEVALUE_TWO);
            }
            /*
             * if (max >= side2val.doubleValue()) valid2 = true; if (min >= side1val.doubleValue()) valid1 = true;
             */

        } else if (mostRestrictiveOccupancy.equals(OCCUPANCY_D) ||
                mostRestrictiveOccupancy.equals(OCCUPANCY_D1) ||
                mostRestrictiveOccupancy.equals(OCCUPANCY_D2)) {

            if (building.getTotalFloorArea().compareTo(BigDecimal.valueOf(FLOORAREA_800)) > 0) {
                // rule = RULE_55;
                subRule = RULE_55_2_3;
                side2val = distanceIncrementBasedOnHeight.add(SIDEVALUE_ONEPOINTFIVE);
                side1val = distanceIncrementBasedOnHeight.add(SIDEVALUE_ONEPOINTFIVE);
                side2Meanval = distanceIncrementBasedOnHeight.add(SIDEVALUE_FIVE);
                side1Meanval = distanceIncrementBasedOnHeight.add(SIDEVALUE_FIVE);

            } else {
                if (building.getTotalFloorArea().compareTo(BigDecimal.valueOf(FLOORAREA_500)) > 0 &&
                        building.getTotalFloorArea().compareTo(BigDecimal.valueOf(FLOORAREA_800)) <= 0) {
                    // rule = RULE_55;
                    subRule = RULE_55_2_2;
                    side2val = distanceIncrementBasedOnHeight.add(SIDEVALUE_ONEPOINTFIVE);
                    side1val = distanceIncrementBasedOnHeight.add(SIDEVALUE_ONEPOINTFIVE);
                    side2Meanval = distanceIncrementBasedOnHeight.add(SIDEVALUE_FOUR);
                    side1Meanval = distanceIncrementBasedOnHeight.add(SIDEVALUE_FOUR);

                } else if (building.getTotalFloorArea().compareTo(BigDecimal.valueOf(FLOORAREA_300)) > 0 &&
                        building.getTotalFloorArea().compareTo(BigDecimal.valueOf(FLOORAREA_500)) <= 0) {
                    // rule = RULE_55;
                    subRule = RULE_55_2_1;
                    side2val = distanceIncrementBasedOnHeight.add(SIDEVALUE_ONEPOINTFIVE);
                    side1val = distanceIncrementBasedOnHeight.add(SIDEVALUE_ONEPOINTFIVE);
                    side2Meanval = distanceIncrementBasedOnHeight.add(SIDEVALUE_TWO);
                    side1Meanval = distanceIncrementBasedOnHeight.add(SIDEVALUE_TWO);

                } else {
                    // rule = RULE_55;
                    subRule = RULE_55_2_PROV;
                    if (mostRestrictiveOccupancy.equals(OCCUPANCY_D1)) {
                        side2val = distanceIncrementBasedOnHeight.add(SIDEVALUE_ONEPOINTFIVE);
                        side1val = distanceIncrementBasedOnHeight.add(SIDEVALUE_ONEPOINTFIVE);
                    } else {
                        side2val = distanceIncrementBasedOnHeight.add(SIDEVALUE_ONE);
                        side1val = distanceIncrementBasedOnHeight.add(SIDEVALUE_ONE_TWO);
                    }

                }
            }
            /*
             * if (max >= side2val.doubleValue()) valid2 = true; if (min >= side1val.doubleValue()) valid1 = true;
             */
        } else if (mostRestrictiveOccupancy.equals(OCCUPANCY_F1)) {
            // rule = RULE_56;
            subRule = RULE_56_3_D;
            side2val = distanceIncrementBasedOnHeight.add(SIDEVALUE_FIVE);
            side1val = distanceIncrementBasedOnHeight.add(SIDEVALUE_FIVE);
        } else if (mostRestrictiveOccupancy.equals(OCCUPANCY_F2)) {
            if (plot.getArea().compareTo(BigDecimal.valueOf(SITEAREA_125)) <= 0) {
                side2val = distanceIncrementBasedOnHeight.add(SIDEVALUE_ONE);
                side1val = distanceIncrementBasedOnHeight.add(SIDEVALUE_ONE);
                side2Meanval = side2val;
                side1Meanval = side1val;
            } else {
                side2val = distanceIncrementBasedOnHeight.add(SIDEVALUE_ONE);
                side1val = distanceIncrementBasedOnHeight.add(SIDEVALUE_ONE_TWO);
                side2Meanval = side2val;
                side1Meanval = side1val;
            }
        } else if (mostRestrictiveOccupancy.equals(OCCUPANCY_G1) ||
                mostRestrictiveOccupancy.equals(OCCUPANCY_G2)) {
            // rule = RULE_57;
            subRule = RULE_57_4;
            side2val = distanceIncrementBasedOnHeight.add(SIDEVALUE_THREE);
            side1val = distanceIncrementBasedOnHeight.add(SIDEVALUE_THREE);
            side2Meanval = side2val;
            side1Meanval = side1val;
        } else if (mostRestrictiveOccupancy.equals(OCCUPANCY_I1)) {
            // rule = RULE_59;
            subRule = RULE_59_3;
            side2val = distanceIncrementBasedOnHeight.add(SIDEVALUE_THREE);
            side1val = distanceIncrementBasedOnHeight.add(SIDEVALUE_THREE);
            side2Meanval = side2val;
            side1Meanval = side1val;
        } else if (mostRestrictiveOccupancy.equals(OCCUPANCY_I2)) {
            // rule = RULE_59;
            subRule = RULE_59_3;
            side2val = distanceIncrementBasedOnHeight.add(SIDEVALUE_SEVEN_FIVE);
            side1val = distanceIncrementBasedOnHeight.add(SIDEVALUE_SEVEN_FIVE);
            side2Meanval = side2val;
            side1Meanval = side1val;
        } else if (mostRestrictiveOccupancy.equals(OCCUPANCY_F4)) {
            subRule = RULE_59_11;
            side2val = distanceIncrementBasedOnHeight.add(SIDEVALUE_ONE);
            side1val = distanceIncrementBasedOnHeight.add(SIDEVALUE_ONE);
            side2Meanval = side2val;
            side1Meanval = side1val;
        }

        if (level >= 0 && building.getBuildingHeight().compareTo(BigDecimal.valueOf(16)) > 0) {
            if (side1val.compareTo(FIVE) <= 0) {
                side1val = FIVE;
                side1Meanval = FIVE;
            }
        }

        if (checkMinimum5mtsCondition) {
            // Checking maximum value of both sides and comparing whether that value less than or equal to 5. If yes, then
            // expecting 5mt minimum.
            if (side1val.compareTo(side2val) > 0 && side1val.compareTo(FIVE) <= 0) {
                side1val = FIVE;
                side1Meanval = FIVE;

            } /*
               * else { if( side2val.compareTo(FIVE) <= 0) { side2val =FIVE; } }
               */
            /*
             * side1val = side1val.compareTo(FIVE) <= 0 ? FIVE : side1val; side2val = side2val.compareTo(FIVE) <= 0 ? FIVE :
             * side2val;
             */
        }

        if (max >= side1val.doubleValue() && maxMeanLength >= side1Meanval.doubleValue())
            valid1 = true;
        if (min >= side2val.doubleValue() && minMeanlength >= side2Meanval.doubleValue())
            valid2 = true;

        if (-1 == level)
            subRule = SUB_RULE_24_12;

        compareSideYard2Result(blockName, side2val, BigDecimal.valueOf(min), side2Meanval, BigDecimal.valueOf(minMeanlength),
                mostRestrictiveOccupancy, sideYard2Result, valid2, subRule, rule, level);
        compareSideYard1Result(blockName, side1val, BigDecimal.valueOf(max), side1Meanval, BigDecimal.valueOf(maxMeanLength),
                mostRestrictiveOccupancy, sideYard1Result, valid1, subRule, rule, level);
    }

    private void checkSideYardLessThanTenOrEqualToMts(final Plan pl, Building building, BigDecimal buildingHeight,
            String blockName, Integer level, final Plot plot,
            final double min, final double max, double minMeanlength, double maxMeanLength,
            final OccupancyType mostRestrictiveOccupancy, SideYardResult sideYard1Result, SideYardResult sideYard2Result) {
        String subRule = SUB_RULE_24_3;
        String rule = SIDE_YARD_DESC;
        Boolean valid2 = false;
        Boolean valid1 = false;
        BigDecimal side2val = SIDEVALUE_ONE;
        BigDecimal side1val = SIDEVALUE_ONE_TWO;
        BigDecimal side2Meanval = SIDEVALUE_ZERO;
        BigDecimal side1Meanval = SIDEVALUE_ZERO;

        Boolean valueGreaterThan = false;

        if (mostRestrictiveOccupancy.equals(OCCUPANCY_A1) ||
                mostRestrictiveOccupancy.equals(OCCUPANCY_A2) ||
                mostRestrictiveOccupancy.equals(OCCUPANCY_A3) ||
                mostRestrictiveOccupancy.equals(OCCUPANCY_A4) ||
                mostRestrictiveOccupancy.equals(OCCUPANCY_A5) ||
                mostRestrictiveOccupancy.equals(OCCUPANCY_F) || mostRestrictiveOccupancy.equals(OCCUPANCY_F3)) {
            // Plot area less than or equal to 125
            if (plot.getArea().compareTo(BigDecimal.valueOf(SITEAREA_125)) <= 0) {

                if (building.getFloorsAboveGround().compareTo(BigDecimal.valueOf(3)) <= 0) {
                    // yes
                    subRule = RULE_62_1_A;
                    if (pl.getPlanInformation().getOpeningOnSideBelow2mtsDesc().equalsIgnoreCase(YES) ||
                            pl.getPlanInformation().getOpeningOnSideBelow2mtsDesc().equalsIgnoreCase(NA)) { // CHECK THIS.
                        side2val = SIDEVALUE_SIXTYONE_CM;
                        side1val = SIDEVALUE_NINTY_CM;
                        side2Meanval = side2val;
                        side1Meanval = side1val;
                        // valueGreaterThan=true;
                    } else {

                        if (pl.getPlanInformation().getOpeningOnSideAbove2mtsDesc().equalsIgnoreCase(DcrConstants.YES) ||
                                pl.getPlanInformation().getOpeningOnSideAbove2mtsDesc().equalsIgnoreCase(DcrConstants.NA)) {
                            side2val = SIDEVALUE_SIXTY_CM;
                            side1val = SIDEVALUE_NINTY_CM;
                            side2Meanval = side2val;
                            side1Meanval = side1val;
                        } else {

                            if (pl.getPlanInformation().getNocToAbutSideDesc().equalsIgnoreCase(YES)) {
                                side2val = SIDEVALUE_ZERO;
                                side1val = SIDEVALUE_NINTY_CM;
                                side2Meanval = side2val;
                                side1Meanval = side1val;
                            } /*
                               * else if (planDetail.getPlanInformation().getNocToAbutSideDesc().equalsIgnoreCase(NA)) { side2val
                               * = SIDEVALUE_SIXTY_CM; side1val = SIDEVALUE_NINTY_CM; side2Meanval = side2val; side1Meanval =
                               * side1val; }
                               */
                            else {
                                side2val = SIDEVALUE_SIXTY_CM;
                                side1val = SIDEVALUE_NINTY_CM;
                                side2Meanval = side2val;
                                side1Meanval = side1val;
                            }
                        }
                    }
                } else { // no

                    if (pl.getPlanInformation().getOpeningOnSideBelow2mtsDesc().equalsIgnoreCase(YES)) {
                        side2val = SIDEVALUE_ONE;
                        side1val = SIDEVALUE_ONE_TWO;
                        subRule = SUB_RULE_24_5;
                        side2Meanval = side2val;
                        side1Meanval = side1val;
                    } else if (pl.getPlanInformation().getOpeningOnSideBelow2mtsDesc().equalsIgnoreCase(NA)) {
                        side2val = SIDEVALUE_ONE;
                        side1val = SIDEVALUE_ONE_TWO;
                        subRule = SUB_RULE_24_5_PROVISION2;
                        side2Meanval = side2val;
                        side1Meanval = side1val;
                    } else {
                        subRule = SUB_RULE_24_5_PROVISION1;
                        if (pl.getPlanInformation().getOpeningOnSideAbove2mtsDesc().equalsIgnoreCase(YES)) {
                            side2val = SIDEVALUE_SEVENTYFIVE_CM;
                            side1val = SIDEVALUE_ONE_TWO;
                            side2Meanval = side2val;
                            side1Meanval = side1val;
                        } else if (pl.getPlanInformation().getOpeningOnSideAbove2mtsDesc().equalsIgnoreCase(NA)) {
                            side2val = SIDEVALUE_ONE;
                            side1val = SIDEVALUE_ONE_TWO;
                            subRule = SUB_RULE_24_5_PROVISION2;
                            side2Meanval = side2val;
                            side1Meanval = side1val;
                        } else {
                            if (pl.getPlanInformation().getNocToAbutSideDesc().equalsIgnoreCase(YES)) {
                                side2val = SIDEVALUE_ZERO;
                                side1val = SIDEVALUE_ONE_TWO;
                                subRule = SUB_RULE_24_5_PROVISION2;
                                side2Meanval = side2val;
                                side1Meanval = side1val;
                            } else if (pl.getPlanInformation().getNocToAbutSideDesc().equalsIgnoreCase(NO)) {
                                side2val = SIDEVALUE_SEVENTYFIVE_CM;
                                side1val = SIDEVALUE_ONE_TWO;
                                side2Meanval = side2val;
                                side1Meanval = side1val;
                            } else {
                                side2val = SIDEVALUE_ONE;
                                side1val = SIDEVALUE_ONE_TWO;
                                subRule = SUB_RULE_24_5_PROVISION2;
                                side2Meanval = side2val;
                                side1Meanval = side1val;
                            }
                        }
                    }
                }
            } else {
                subRule = SUB_RULE_24_3;
                // rule = DcrConstants.RULE24;
                // Plot area greater than 125 mts

                if (buildingHeight.compareTo(BigDecimal.valueOf(7)) <= 0) {

                    if (pl.getPlanInformation().getOpeningOnSideBelow2mtsDesc().equalsIgnoreCase(YES) ||
                            pl.getPlanInformation().getOpeningOnSideBelow2mtsDesc().equalsIgnoreCase(NA)) {
                        side2val = SIDEVALUE_ONE;
                        side1val = SIDEVALUE_ONE_TWO;
                        side2Meanval = side2val;
                        side1Meanval = side1val;
                    } else {
                        if (pl.getPlanInformation().getOpeningOnSideAbove2mtsDesc().equalsIgnoreCase(DcrConstants.YES) ||
                                pl.getPlanInformation().getOpeningOnSideAbove2mtsDesc().equalsIgnoreCase(DcrConstants.NA)) {
                            side2val = SIDEVALUE_SEVENTYFIVE_CM;
                            side1val = SIDEVALUE_ONE_TWO;
                            side2Meanval = side2val;
                            side1Meanval = side1val;

                        } else {
                            if (pl.getPlanInformation().getNocToAbutSideDesc().equalsIgnoreCase(DcrConstants.YES)) {
                                side2val = SIDEVALUE_ZERO;
                                side1val = SIDEVALUE_ONE_TWO;
                                side2Meanval = side2val;
                                side1Meanval = side1val;
                            } else {
                                side2val = SIDEVALUE_SEVENTYFIVE_CM;
                                side1val = SIDEVALUE_ONE_TWO;
                                side2Meanval = side2val;
                                side1Meanval = side1val;
                            }
                        }
                    }

                } else {
                    side2val = SIDEVALUE_ONE;
                    side1val = SIDEVALUE_ONE_TWO;
                    side2Meanval = side2val;
                    side1Meanval = side1val;
                }
            }
            /*
             * if (valueGreaterThan) { if (max > side1val.doubleValue() && maxMeanLength > side1Meanval.doubleValue()) valid1 =
             * true; if (min > side2val.doubleValue() && minMeanlength > side2Meanval.doubleValue()) valid2 = true; } else {
             */
            if (max >= side1val.doubleValue() && maxMeanLength >= side1Meanval.doubleValue())
                valid1 = true;
            if (min >= side2val.doubleValue() && minMeanlength >= side2Meanval.doubleValue())
                valid2 = true;
            // }
            /*
             * if (max >= side2val.doubleValue()) valid2 = true; if (min >= side1val.doubleValue()) valid1 = true;
             */
            /*
             * if (max >= side2val.doubleValue()) valid2 = true; if (min >= side1val.doubleValue()) valid1 = true;
             */

            if (-1 == level)
                subRule = SUB_RULE_24_12;

            compareSideYard2Result(blockName, side2val, BigDecimal.valueOf(min), side2Meanval, BigDecimal.valueOf(minMeanlength),
                    mostRestrictiveOccupancy, sideYard2Result, valid2, subRule, rule, level);
            compareSideYard1Result(blockName, side1val, BigDecimal.valueOf(max), side1Meanval, BigDecimal.valueOf(maxMeanLength),
                    mostRestrictiveOccupancy, sideYard1Result, valid1, subRule, rule, level);
        } else
            processSideYardForOccupanciesOtherThanA1A2F(pl, building, plot, blockName, level, min, max, minMeanlength,
                    maxMeanLength, mostRestrictiveOccupancy, buildingHeight, side2val, side1val,
                    BigDecimal.ZERO, false, sideYard1Result, sideYard2Result);
    }

    private void compareSideYard1Result(String blockName, BigDecimal exptDistance, BigDecimal actualDistance,
            BigDecimal expectedMeanDistance, BigDecimal actualMeanDistance, OccupancyType mostRestrictiveOccupancy,
            SideYardResult sideYard1Result, Boolean valid, String subRule, String rule, Integer level) {
        if (exptDistance.compareTo(sideYard1Result.expectedDistance) >= 0) {
            if (exptDistance.compareTo(sideYard1Result.expectedDistance) == 0) {
                sideYard1Result.rule = sideYard1Result.rule != null ? sideYard1Result.rule + "," + rule : rule;
                sideYard1Result.occupancy = sideYard1Result.occupancy != null
                        ? sideYard1Result.occupancy + "," + mostRestrictiveOccupancy.getOccupancyTypeVal()
                        : mostRestrictiveOccupancy.getOccupancyTypeVal();
                if (expectedMeanDistance.compareTo(sideYard1Result.expectedmeanDistance) >= 0) {
                    sideYard1Result.expectedmeanDistance = expectedMeanDistance;
                    sideYard1Result.actualMeanDistance = actualMeanDistance;
                }
            } else {
                sideYard1Result.rule = rule;
                sideYard1Result.occupancy = mostRestrictiveOccupancy.getOccupancyTypeVal();
                sideYard1Result.expectedmeanDistance = expectedMeanDistance;
                sideYard1Result.actualMeanDistance = actualMeanDistance;
            }

            sideYard1Result.subRule = subRule;
            sideYard1Result.blockName = blockName;
            sideYard1Result.level = level;
            sideYard1Result.actualDistance = actualDistance;
            sideYard1Result.expectedDistance = exptDistance;
            sideYard1Result.status = valid;
        }
    }

    private void compareSideYard2Result(String blockName, BigDecimal exptDistance, BigDecimal actualDistance,
            BigDecimal expectedMeanDistance, BigDecimal actualMeanDistance, OccupancyType mostRestrictiveOccupancy,
            SideYardResult sideYard2Result, Boolean valid, String subRule, String rule, Integer level) {
        if (exptDistance.compareTo(sideYard2Result.expectedDistance) >= 0) {
            if (exptDistance.compareTo(sideYard2Result.expectedDistance) == 0) {
                sideYard2Result.rule = sideYard2Result.rule != null ? sideYard2Result.rule + "," + rule : rule;
                sideYard2Result.occupancy = sideYard2Result.occupancy != null
                        ? sideYard2Result.occupancy + "," + mostRestrictiveOccupancy.getOccupancyTypeVal()
                        : mostRestrictiveOccupancy.getOccupancyTypeVal();
                if (expectedMeanDistance.compareTo(sideYard2Result.expectedmeanDistance) >= 0) {
                    sideYard2Result.expectedmeanDistance = expectedMeanDistance;
                    sideYard2Result.actualMeanDistance = actualMeanDistance;
                }
            } else {
                sideYard2Result.rule = rule;
                sideYard2Result.occupancy = mostRestrictiveOccupancy.getOccupancyTypeVal();
                sideYard2Result.expectedmeanDistance = expectedMeanDistance;
                sideYard2Result.actualMeanDistance = actualMeanDistance;
            }

            sideYard2Result.subRule = subRule;
            sideYard2Result.blockName = blockName;
            sideYard2Result.level = level;
            sideYard2Result.actualDistance = actualDistance;
            sideYard2Result.expectedDistance = exptDistance;
            sideYard2Result.status = valid;
        }
    }

    private void validateSideYardRule(final Plan pl) {

        for (Block block : pl.getBlocks()) {
            if (!block.getCompletelyExisting()) {
                Boolean sideYardDefined = false;
                for (SetBack setback : block.getSetBacks()) {
                    if (setback.getSideYard1() != null
                            && setback.getSideYard1().getMean().compareTo(BigDecimal.valueOf(0)) > 0) {
                        sideYardDefined = true;
                    } else if (setback.getSideYard2() != null
                            && setback.getSideYard2().getMean().compareTo(BigDecimal.valueOf(0)) > 0) {
                        sideYardDefined = true;
                    }
                }
                if (!sideYardDefined) {
                    HashMap<String, String> errors = new HashMap<>();
                    errors.put(SIDE_YARD_DESC,
                            prepareMessage(OBJECTNOTDEFINED, SIDE_YARD_DESC + " for Block " + block.getName()));
                    pl.addErrors(errors);
                }
            }

        }

    }

}
