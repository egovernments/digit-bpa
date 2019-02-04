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

import static org.egov.edcr.utility.DcrConstants.IN_LITRE;
import static org.egov.edcr.utility.DcrConstants.OBJECTDEFINED_DESC;
import static org.egov.edcr.utility.DcrConstants.OBJECTNOTDEFINED;
import static org.egov.edcr.utility.DcrConstants.OBJECTNOTDEFINED_DESC;
import static org.egov.edcr.utility.DcrConstants.RAINWATER_HARVESTING;
import static org.egov.edcr.utility.DcrConstants.RAINWATER_HARVES_TANKCAPACITY;
import static org.egov.edcr.utility.DcrConstants.RULE109;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.egov.common.entity.edcr.OccupancyType;
import org.egov.common.entity.edcr.Plan;
import org.egov.common.entity.edcr.Result;
import org.egov.common.entity.edcr.ScrutinyDetail;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class RainWaterHarvesting extends FeatureProcess {
    private static final BigDecimal THREEHUNDREDANDTWENTY = BigDecimal.valueOf(320);
    private static final String SUB_RULE_109_B_DESCRIPTION = "RainWater Storage Arrangement ";

    private static final String SUB_RULE_109_B = "109(B)";
    private static final String RAINWATER_HARVESTING_TANK_CAPACITY = "Minimum capacity of Rain Water Harvesting Tank";
    private static final String OCCUPANCY = "Occupancy";
    private static final BigDecimal ONEHUNDREDFIFTY = BigDecimal.valueOf(150);
    private static final BigDecimal TWENTYFIVE = BigDecimal.valueOf(25);

    @Override
    public Plan validate(Plan pl) {
        HashMap<String, String> errors = new HashMap<>();
        if (pl != null && pl.getUtility() != null) {
            // rain water harvest defined or not
            if (!pl.getVirtualBuilding().getOccupancies().isEmpty()) {
                for (OccupancyType occupancyType : pl.getVirtualBuilding().getOccupancies()) {
                    if (checkOccupancyTypeForRWH(occupancyType)) {
                        if (validateRWH(pl, errors))
                            break;
                    } else if ((occupancyType.equals(OccupancyType.OCCUPANCY_A1)
                            || occupancyType.equals(OccupancyType.OCCUPANCY_A4) ||
                            occupancyType.equals(OccupancyType.OCCUPANCY_A5))
                            && ((pl.getVirtualBuilding().getTotalFloorArea().compareTo(ONEHUNDREDFIFTY) > 0) ||
                                    pl.getPlot().getArea().compareTo(THREEHUNDREDANDTWENTY) > 0)) {
                        if (validateRWH(pl, errors))
                            break;
                    } else if ((occupancyType.equals(OccupancyType.OCCUPANCY_F)
                            || occupancyType.equals(OccupancyType.OCCUPANCY_F1) ||
                            occupancyType.equals(OccupancyType.OCCUPANCY_F2) || occupancyType.equals(OccupancyType.OCCUPANCY_F3)
                            ||
                            occupancyType.equals(OccupancyType.OCCUPANCY_F4))
                            && pl.getVirtualBuilding().getTotalFloorArea() != null &&
                            pl.getVirtualBuilding().getTotalFloorArea().compareTo(BigDecimal.valueOf(100)) > 0 &&
                            pl.getPlot().getArea().compareTo(BigDecimal.valueOf(200)) > 0) {
                        if (validateRWH(pl, errors))
                            break;
                    }
                }
            }
        }
        return pl;
    }

    @Override
    public Plan process(Plan planDetail) {
        validate(planDetail);
        scrutinyDetail = new ScrutinyDetail();
        scrutinyDetail.addColumnHeading(1, RULE_NO);
        scrutinyDetail.addColumnHeading(2, DESCRIPTION);
        scrutinyDetail.addColumnHeading(3, OCCUPANCY);
        scrutinyDetail.addColumnHeading(4, REQUIRED);
        scrutinyDetail.addColumnHeading(5, PROVIDED);
        scrutinyDetail.addColumnHeading(6, STATUS);
        scrutinyDetail.setKey("Common_Rain Water Harvesting");
        String rule = RULE109;
        String subRule = SUB_RULE_109_B;
        String subRuleDesc = SUB_RULE_109_B_DESCRIPTION;
        BigDecimal expectedTankCapacity = BigDecimal.ZERO;
        if (!planDetail.getVirtualBuilding().getOccupancies().isEmpty()) {
            for (OccupancyType occupancyType : planDetail.getVirtualBuilding().getOccupancies()) {
                if (checkOccupancyTypeForRWH(occupancyType)) {
                    if (processRWH(planDetail, rule, subRule, subRuleDesc))
                        break;
                } else if ((occupancyType.equals(OccupancyType.OCCUPANCY_A1) || occupancyType.equals(OccupancyType.OCCUPANCY_A4)
                        ||
                        occupancyType.equals(OccupancyType.OCCUPANCY_A5))
                        && ((planDetail.getVirtualBuilding().getTotalFloorArea().compareTo(ONEHUNDREDFIFTY) > 0) ||
                                planDetail.getPlot().getArea().compareTo(THREEHUNDREDANDTWENTY) > 0)) {
                    if (processRWH(planDetail, rule, subRule, subRuleDesc))
                        break;
                } else if ((occupancyType.equals(OccupancyType.OCCUPANCY_F) || occupancyType.equals(OccupancyType.OCCUPANCY_F1) ||
                        occupancyType.equals(OccupancyType.OCCUPANCY_F2) || occupancyType.equals(OccupancyType.OCCUPANCY_F3) ||
                        occupancyType.equals(OccupancyType.OCCUPANCY_F4))
                        && planDetail.getVirtualBuilding().getTotalFloorArea() != null &&
                        planDetail.getVirtualBuilding().getTotalFloorArea().compareTo(BigDecimal.valueOf(100)) > 0 &&
                        planDetail.getPlot().getArea().compareTo(BigDecimal.valueOf(200)) > 0) {
                    if (processRWH(planDetail, rule, subRule, subRuleDesc))
                        break;
                }
            }
        }
        List<Map<String, Object>> listOfMapOfAllOccupanciesAndTankCapacity = new ArrayList<>();
        if (planDetail.getUtility() != null && !planDetail.getUtility().getRainWaterHarvest().isEmpty() &&
                planDetail.getUtility().getRainWaterHarvestingTankCapacity() != null) {
            if (!planDetail.getVirtualBuilding().getOccupancies().isEmpty()) {
                for (OccupancyType occupancyType : planDetail.getVirtualBuilding().getOccupancies()) {
                    Map<String, Object> mapOfAllOccupancyAndTankCapacity = new HashMap<>();
                    if ((occupancyType.equals(OccupancyType.OCCUPANCY_F) || occupancyType.equals(OccupancyType.OCCUPANCY_F1) ||
                            occupancyType.equals(OccupancyType.OCCUPANCY_F2) || occupancyType.equals(OccupancyType.OCCUPANCY_F3)
                            ||
                            occupancyType.equals(OccupancyType.OCCUPANCY_F4))
                            && planDetail.getVirtualBuilding().getTotalFloorArea() != null &&
                            planDetail.getVirtualBuilding().getTotalFloorArea().compareTo(BigDecimal.valueOf(100)) > 0 &&
                            planDetail.getPlot().getArea().compareTo(BigDecimal.valueOf(200)) > 0
                            && !planDetail.getUtility().getRainWaterHarvest().isEmpty()
                            && planDetail.getUtility().getRainWaterHarvestingTankCapacity() != null
                            && planDetail.getVirtualBuilding().getTotalCoverageArea() != null
                            && planDetail.getVirtualBuilding().getTotalCoverageArea().compareTo(BigDecimal.valueOf(0)) > 0) {
                        expectedTankCapacity = TWENTYFIVE.multiply(planDetail.getVirtualBuilding().getTotalCoverageArea())
                                .setScale(2,
                                        RoundingMode.HALF_UP);
                        mapOfAllOccupancyAndTankCapacity.put("occupancy", occupancyType.getOccupancyTypeVal());
                        mapOfAllOccupancyAndTankCapacity.put("expectedTankCapacity", expectedTankCapacity);
                    } else if ((occupancyType.equals(OccupancyType.OCCUPANCY_A1)
                            || occupancyType.equals(OccupancyType.OCCUPANCY_A4) ||
                            occupancyType.equals(OccupancyType.OCCUPANCY_A5))
                            && ((planDetail.getVirtualBuilding().getTotalFloorArea().compareTo(ONEHUNDREDFIFTY) > 0) ||
                                    planDetail.getPlot().getArea().compareTo(THREEHUNDREDANDTWENTY) > 0)
                            && !planDetail.getUtility().getRainWaterHarvest().isEmpty()
                            && planDetail.getUtility().getRainWaterHarvestingTankCapacity() != null
                            && planDetail.getVirtualBuilding().getTotalCoverageArea() != null
                            && planDetail.getVirtualBuilding().getTotalCoverageArea().compareTo(BigDecimal.valueOf(0)) > 0) {
                        expectedTankCapacity = TWENTYFIVE.multiply(planDetail.getVirtualBuilding().getTotalCoverageArea())
                                .setScale(2,
                                        RoundingMode.HALF_UP);
                        mapOfAllOccupancyAndTankCapacity.put("occupancy", occupancyType.getOccupancyTypeVal());
                        mapOfAllOccupancyAndTankCapacity.put("expectedTankCapacity", expectedTankCapacity);
                    } else if (checkOccupancyTypeForRWH(occupancyType)
                            && !planDetail.getUtility().getRainWaterHarvest().isEmpty()
                            && planDetail.getUtility().getRainWaterHarvestingTankCapacity() != null
                            && planDetail.getVirtualBuilding().getTotalCoverageArea() != null
                            && planDetail.getVirtualBuilding().getTotalCoverageArea().compareTo(BigDecimal.valueOf(0)) > 0) {
                        if (occupancyType.equals(OccupancyType.OCCUPANCY_A2) || occupancyType.equals(OccupancyType.OCCUPANCY_A3)
                                ||
                                occupancyType.equals(OccupancyType.OCCUPANCY_I1)
                                || occupancyType.equals(OccupancyType.OCCUPANCY_I2)) {
                            expectedTankCapacity = TWENTYFIVE.multiply(planDetail.getVirtualBuilding().getTotalCoverageArea())
                                    .setScale(2,
                                            RoundingMode.HALF_UP);
                        } else {
                            expectedTankCapacity = BigDecimal.valueOf(50)
                                    .multiply(planDetail.getVirtualBuilding().getTotalCoverageArea()).setScale(2,
                                            RoundingMode.HALF_UP);
                        }
                        mapOfAllOccupancyAndTankCapacity.put("occupancy", occupancyType.getOccupancyTypeVal());
                        mapOfAllOccupancyAndTankCapacity.put("expectedTankCapacity", expectedTankCapacity);
                    }
                    if (!mapOfAllOccupancyAndTankCapacity.isEmpty()) {
                        listOfMapOfAllOccupanciesAndTankCapacity.add(mapOfAllOccupancyAndTankCapacity);
                    }
                }
                Map<String, Object> mapOfMostRestrictiveOccupancyAndItsTankCapacity = new HashMap<>();
                if (!listOfMapOfAllOccupanciesAndTankCapacity.isEmpty()) {
                    mapOfMostRestrictiveOccupancyAndItsTankCapacity = listOfMapOfAllOccupanciesAndTankCapacity.get(0);
                    for (Map<String, Object> mapOfOccupancyAndTankCapacity : listOfMapOfAllOccupanciesAndTankCapacity) {
                        if (mapOfOccupancyAndTankCapacity.get("expectedTankCapacity")
                                .equals(mapOfMostRestrictiveOccupancyAndItsTankCapacity.get("expectedTankCapacity"))) {
                            if (!(mapOfOccupancyAndTankCapacity.get("occupancy"))
                                    .equals(mapOfMostRestrictiveOccupancyAndItsTankCapacity.get("occupancy"))) {
                                SortedSet<String> uniqueOccupancies = new TreeSet<>();
                                String[] occupancyString = (mapOfOccupancyAndTankCapacity.get("occupancy") + " , "
                                        + mapOfMostRestrictiveOccupancyAndItsTankCapacity.get("occupancy")).split(" , ");
                                for (String str : occupancyString) {
                                    uniqueOccupancies.add(str);
                                }
                                StringBuffer str = new StringBuffer();
                                List<String> unqList = new ArrayList<>(uniqueOccupancies);
                                for (String unique : unqList) {
                                    str.append(unique);
                                    if (!unique.equals(unqList.get(unqList.size() - 1))) {
                                        str.append(" , ");
                                    }
                                }
                                mapOfMostRestrictiveOccupancyAndItsTankCapacity.put("occupancy", str.toString());
                            }
                            continue;
                        } else if (((BigDecimal) mapOfMostRestrictiveOccupancyAndItsTankCapacity.get("expectedTankCapacity"))
                                .compareTo((BigDecimal) mapOfOccupancyAndTankCapacity.get("expectedTankCapacity")) < 0) {
                            mapOfMostRestrictiveOccupancyAndItsTankCapacity.putAll(mapOfOccupancyAndTankCapacity);
                        }
                    }
                }
                Boolean valid = false;
                if (!mapOfMostRestrictiveOccupancyAndItsTankCapacity.isEmpty()
                        && mapOfMostRestrictiveOccupancyAndItsTankCapacity.get("occupancy") != null &&
                        mapOfMostRestrictiveOccupancyAndItsTankCapacity.get("expectedTankCapacity") != null) {
                    if (((BigDecimal) mapOfMostRestrictiveOccupancyAndItsTankCapacity.get("expectedTankCapacity"))
                            .compareTo(planDetail.getUtility().getRainWaterHarvestingTankCapacity()) <= 0) {
                        valid = true;
                    }
                    processRWHTankCapacity(planDetail, rule, subRule, subRuleDesc,
                            (BigDecimal) mapOfMostRestrictiveOccupancyAndItsTankCapacity.get("expectedTankCapacity"),
                            valid, mapOfMostRestrictiveOccupancyAndItsTankCapacity.get("occupancy"));
                }
            }
        }
        return planDetail;
    }

    private void processRWHTankCapacity(Plan planDetail, String rule, String subRule, String subRuleDesc,
            BigDecimal expectedTankCapacity, Boolean valid, Object occupancyType) {
        if (expectedTankCapacity.compareTo(BigDecimal.valueOf(0)) > 0) {
            if (valid) {
                setReportOutputDetails(planDetail, subRule, RAINWATER_HARVESTING_TANK_CAPACITY, occupancyType.toString(),
                        expectedTankCapacity.toString(),
                        planDetail.getUtility().getRainWaterHarvestingTankCapacity().toString(), Result.Accepted.getResultVal());
            } else {
                setReportOutputDetails(planDetail, subRule, RAINWATER_HARVESTING_TANK_CAPACITY, occupancyType.toString(),
                        expectedTankCapacity.toString() + IN_LITRE,
                        planDetail.getUtility().getRainWaterHarvestingTankCapacity().toString() + IN_LITRE,
                        Result.Not_Accepted.getResultVal());
            }
        }
    }

    private boolean processRWH(Plan planDetail, String rule, String subRule, String subRuleDesc) {
        if (!planDetail.getUtility().getRainWaterHarvest().isEmpty()) {
            setReportOutputDetails(planDetail, subRule, subRuleDesc, "", "",
                    OBJECTDEFINED_DESC, Result.Accepted.getResultVal());
            return true;
        } else if (planDetail.getUtility().getRainWaterHarvest().isEmpty()) {
            setReportOutputDetails(planDetail, subRule, subRuleDesc, "", "",
                    OBJECTNOTDEFINED_DESC, Result.Not_Accepted.getResultVal());
            return true;
        }
        return false;
    }

    private boolean checkOccupancyTypeForRWH(OccupancyType occupancyType) {
        return occupancyType.equals(OccupancyType.OCCUPANCY_A2) || occupancyType.equals(OccupancyType.OCCUPANCY_A3)
                || occupancyType.equals(OccupancyType.OCCUPANCY_B1) || occupancyType.equals(OccupancyType.OCCUPANCY_B2) ||
                occupancyType.equals(OccupancyType.OCCUPANCY_B3) || occupancyType.equals(OccupancyType.OCCUPANCY_C) ||
                occupancyType.equals(OccupancyType.OCCUPANCY_C1) || occupancyType.equals(OccupancyType.OCCUPANCY_C2) ||
                occupancyType.equals(OccupancyType.OCCUPANCY_C3) || occupancyType.equals(OccupancyType.OCCUPANCY_D) ||
                occupancyType.equals(OccupancyType.OCCUPANCY_D1) || occupancyType.equals(OccupancyType.OCCUPANCY_D2) ||
                occupancyType.equals(OccupancyType.OCCUPANCY_E) || occupancyType.equals(OccupancyType.OCCUPANCY_G1) ||
                occupancyType.equals(OccupancyType.OCCUPANCY_G2) || occupancyType.equals(OccupancyType.OCCUPANCY_I1) ||
                occupancyType.equals(OccupancyType.OCCUPANCY_I2);
    }

    private void setReportOutputDetails(Plan pl, String ruleNo, String ruleDesc, String occupancy, String expected, String actual,
            String status) {
        Map<String, String> details = new HashMap<>();
        details.put(RULE_NO, ruleNo);
        details.put(DESCRIPTION, ruleDesc);
        details.put(OCCUPANCY, occupancy);
        details.put(REQUIRED, expected);
        details.put(PROVIDED, actual);
        details.put(STATUS, status);
        scrutinyDetail.getDetail().add(details);
        pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);
    }

    private boolean validateRWH(Plan pl, HashMap<String, String> errors) {
        if (pl.getUtility().getRainWaterHarvest().isEmpty()) {
            errors.put(RAINWATER_HARVESTING,
                    edcrMessageSource.getMessage(OBJECTNOTDEFINED, new String[] {
                            RAINWATER_HARVESTING }, LocaleContextHolder.getLocale()));
            pl.addErrors(errors);
            return true;
        } else if (!pl.getUtility().getRainWaterHarvest().isEmpty() &&
                pl.getUtility().getRainWaterHarvestingTankCapacity() == null) {
            errors.put(RAINWATER_HARVES_TANKCAPACITY,
                    edcrMessageSource.getMessage(OBJECTNOTDEFINED, new String[] {
                            RAINWATER_HARVES_TANKCAPACITY }, LocaleContextHolder.getLocale()));
            pl.addErrors(errors);
            return true;
        }
        return false;
    }

    @Override
    public Map<String, Date> getAmendments() {
        return new LinkedHashMap<>();
    }
}
