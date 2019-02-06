package org.egov.edcr.feature;

import static org.egov.edcr.utility.DcrConstants.DECIMALDIGITS_MEASUREMENTS;
import static org.egov.edcr.utility.DcrConstants.MEZZANINE_HALL;
import static org.egov.edcr.utility.DcrConstants.ROUNDMODE_MEASUREMENTS;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.egov.common.entity.edcr.Balcony;
import org.egov.common.entity.edcr.Block;
import org.egov.common.entity.edcr.Floor;
import org.egov.common.entity.edcr.Hall;
import org.egov.common.entity.edcr.MezzanineFloor;
import org.egov.common.entity.edcr.Plan;
import org.egov.common.entity.edcr.Result;
import org.egov.common.entity.edcr.ScrutinyDetail;
import org.egov.edcr.service.ProcessHelper;
import org.egov.edcr.utility.DcrConstants;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class MezzanineFloorService extends FeatureProcess {
    private static final String SUBRULE_35_1 = "35(1)";
    private static final String SUBRULE_35_1_DESC = "Maximum area of mezzanine floor";
    public static final String SUB_RULE_55_7_DESC = "Maximum allowed area of balcony";
    public static final String SUB_RULE_55_7 = "55(7)";
    private static final String FLOOR = "Floor";
    public static final String HALL_NUMBER = "Hall Number";

    @Override
    public Plan validate(Plan pl) {
        HashMap<String, String> errors = new HashMap<>();
        if (pl != null && !pl.getBlocks().isEmpty()) {
            blk: for (Block block : pl.getBlocks()) {
                if (block.getBuilding() != null && !block.getBuilding().getFloors().isEmpty()) {
                    if (ProcessHelper.checkExemptionConditionForBuildingParts(block)) {
                        continue blk;
                    }
                    for (Floor floor : block.getBuilding().getFloors()) {
                        if (!floor.getMezzanineFloor().isEmpty()) {
                            for (MezzanineFloor mezzanineFloor : floor.getMezzanineFloor()) {
                                if (mezzanineFloor != null && mezzanineFloor.getNumber() != null) {
                                    boolean mezzanineHallFound = false;
                                    for (Hall hall : floor.getHalls()) {
                                        if (hall.getNumber().equals(mezzanineFloor.getNumber())) {
                                            mezzanineHallFound = true;
                                            break;
                                        }
                                    }
                                    if (!mezzanineHallFound) {
                                        errors.put(
                                                String.format(MEZZANINE_HALL, mezzanineFloor.getNumber(), floor.getNumber(),
                                                        block.getNumber()),
                                                edcrMessageSource.getMessage(DcrConstants.OBJECTNOTDEFINED,
                                                        new String[] { String.format(MEZZANINE_HALL, mezzanineFloor.getNumber(),
                                                                floor.getNumber(), block.getNumber()) },
                                                        LocaleContextHolder.getLocale()));
                                        pl.addErrors(errors);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return pl;
    }

    @Override
    public Plan process(Plan pl) {
        validate(pl);
        String subRule = SUBRULE_35_1;
        if (pl != null && !pl.getBlocks().isEmpty()) {
            blk: for (Block block : pl.getBlocks()) {
                scrutinyDetail = new ScrutinyDetail();
                scrutinyDetail.addColumnHeading(1, RULE_NO);
                scrutinyDetail.addColumnHeading(2, DESCRIPTION);
                scrutinyDetail.addColumnHeading(3, FLOOR);
                scrutinyDetail.addColumnHeading(4, REQUIRED);
                scrutinyDetail.addColumnHeading(5, PROVIDED);
                scrutinyDetail.addColumnHeading(6, STATUS);
                scrutinyDetail.setKey("Block_" + block.getNumber() + "_" + "Mezzanine Floor");
                if (block.getBuilding() != null && !block.getBuilding().getFloors().isEmpty()) {
                    if (ProcessHelper.checkExemptionConditionForBuildingParts(block)) {
                        continue blk;
                    }
                    for (Floor floor : block.getBuilding().getFloors()) {
                        for (MezzanineFloor mezzanineFloor : floor.getMezzanineFloor()) {
                            for (Hall hall : floor.getHalls()) {
                                if (mezzanineFloor.getNumber().equals(hall.getNumber())) {
                                    Boolean valid = false;
                                    boolean isTypicalRepititiveFloor = false;
                                    BigDecimal mezzanineFloorArea = mezzanineFloor.getBuiltUpArea() == null ? BigDecimal.ZERO
                                            : mezzanineFloor.getBuiltUpArea()
                                                    .subtract(mezzanineFloor.getDeductions() == null ? BigDecimal.ZERO
                                                            : mezzanineFloor.getDeductions());
                                    BigDecimal hallFloorArea = hall.getBuiltUpArea() == null ? BigDecimal.ZERO
                                            : hall.getBuiltUpArea().subtract(
                                                    hall.getDeductions() == null ? BigDecimal.ZERO : hall.getDeductions());
                                    BigDecimal oneThirdHallFloorArea = hallFloorArea.divide(BigDecimal.valueOf(3),
                                            DECIMALDIGITS_MEASUREMENTS,
                                            ROUNDMODE_MEASUREMENTS);
                                    Map<String, Object> typicalFloorValues = ProcessHelper.getTypicalFloorValues(block, floor,
                                            isTypicalRepititiveFloor);
                                    if (!(Boolean) typicalFloorValues.get("isTypicalRepititiveFloor")) {
                                        if (mezzanineFloorArea.compareTo(oneThirdHallFloorArea) <= 0) {
                                            valid = true;
                                        }
                                        String value = typicalFloorValues.get("typicalFloors") != null
                                                ? (String) typicalFloorValues.get("typicalFloors")
                                                : " floor " + floor.getNumber();
                                        if (valid) {
                                            setReportOutputDetails(pl, subRule,
                                                    SUBRULE_35_1_DESC + " " + mezzanineFloor.getNumber(), value,
                                                    oneThirdHallFloorArea + DcrConstants.IN_METER_SQR, mezzanineFloorArea +
                                                            DcrConstants.IN_METER_SQR,
                                                    Result.Accepted.getResultVal());
                                        } else {
                                            setReportOutputDetails(pl, subRule,
                                                    SUBRULE_35_1_DESC + " " + mezzanineFloor.getNumber(), value,
                                                    oneThirdHallFloorArea + DcrConstants.IN_METER_SQR, mezzanineFloorArea +
                                                            DcrConstants.IN_METER_SQR,
                                                    Result.Not_Accepted.getResultVal());

                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        processAssembly(pl);
        return pl;
    }

    public void processAssembly(Plan pl) {
        for (Block b : pl.getBlocks()) {
            if (!b.getHallAreas().isEmpty() && !b.getBalconyAreas().isEmpty()) {
                scrutinyDetail = new ScrutinyDetail();
                scrutinyDetail.addColumnHeading(1, RULE_NO);
                scrutinyDetail.addColumnHeading(2, DESCRIPTION);
                scrutinyDetail.addColumnHeading(3, HALL_NUMBER);
                scrutinyDetail.addColumnHeading(4, REQUIRED);
                scrutinyDetail.addColumnHeading(5, PROVIDED);
                scrutinyDetail.addColumnHeading(6, STATUS);
                scrutinyDetail.setKey("Block_" + b.getNumber() + "_" + "Maximum area of balcony");

                for (Hall hall : b.getHallAreas()) {
                    BigDecimal balconyArea = BigDecimal.ZERO;
                    BigDecimal hallArea = hall.getArea();
                    String hallNo = hall.getNumber();
                    for (Balcony balcony : b.getBalconyAreas()) {
                        String balconyNo = balcony.getNumber();
                        if (hallNo.equalsIgnoreCase(balconyNo))
                            balconyArea = balconyArea.add(balcony.getArea());
                    }
                    double maxAllowedArea = (hallArea.doubleValue() * 25) / 100;
                    if (balconyArea.doubleValue() > 0) {
                        Map<String, String> details = new HashMap<>();
                        details.put(RULE_NO, SUB_RULE_55_7);
                        details.put(DESCRIPTION, SUB_RULE_55_7_DESC);
                        details.put(HALL_NUMBER, hallNo);
                        details.put(REQUIRED, "<= " + maxAllowedArea);
                        details.put(PROVIDED, String.valueOf(balconyArea));
                        details.put(STATUS, Result.Not_Accepted.getResultVal());

                        if (balconyArea.doubleValue() > maxAllowedArea)
                            details.put(STATUS, Result.Not_Accepted.getResultVal());
                        else
                            details.put(STATUS, Result.Accepted.getResultVal());
                        scrutinyDetail.getDetail().add(details);
                        pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);
                    }
                }
            }
        }
    }

    private void setReportOutputDetails(Plan pl, String ruleNo, String ruleDesc, String floor, String expected, String actual,
            String status) {
        Map<String, String> details = new HashMap<>();
        details.put(RULE_NO, ruleNo);
        details.put(DESCRIPTION, ruleDesc);
        details.put(FLOOR, floor);
        details.put(REQUIRED, expected);
        details.put(PROVIDED, actual);
        details.put(STATUS, status);
        scrutinyDetail.getDetail().add(details);
        pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);
    }

    @Override
    public Map<String, Date> getAmendments() {
        return new LinkedHashMap<>();
    }
}