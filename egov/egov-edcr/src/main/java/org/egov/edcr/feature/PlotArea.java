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

import static org.egov.edcr.constants.DxfFileConstants.F_CB;
import static org.egov.edcr.constants.DxfFileConstants.F_RT;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.egov.common.entity.dcr.helper.OccupancyHelperDetail;
import org.egov.common.entity.edcr.OccupancyTypeHelper;
import org.egov.common.entity.edcr.Plan;
import org.egov.common.entity.edcr.Result;
import org.egov.common.entity.edcr.ScrutinyDetail;
import org.egov.edcr.constants.DxfFileConstants;
import org.springframework.stereotype.Service;

@Service
public class PlotArea extends FeatureProcess {

    private static final Logger LOG = Logger.getLogger(RoadWidth.class);
    private static final String RULE_34 = "34-1";
    public static final String PLOTAREA_DESCRIPTION = "Minimum Plot Area";
    public static final int THREE_ZERO = 300;
    public static final String ERROR_MSG = "Minimum_plotArea";

    @Override
    public Map<String, Date> getAmendments() {
        return new LinkedHashMap<>();
    }

    @Override
    public Plan validate(Plan pl) {
        return pl;
    }

    @Override
    public Plan process(Plan pl) {

        if (pl.getVirtualBuilding().getMostRestrictiveFarHelper() != null
                && pl.getVirtualBuilding().getMostRestrictiveFarHelper().getType() != null &&
                !DxfFileConstants.A.equalsIgnoreCase(pl.getVirtualBuilding().getMostRestrictiveFarHelper().getType().getCode())) {
            ScrutinyDetail scrutinyDetail = new ScrutinyDetail();
            scrutinyDetail.setKey("Common_Plot Area");
            scrutinyDetail.addColumnHeading(1, RULE_NO);
            scrutinyDetail.addColumnHeading(2, DESCRIPTION);
            scrutinyDetail.addColumnHeading(3, OCCUPANCY);
            scrutinyDetail.addColumnHeading(4, PERMITTED);
            scrutinyDetail.addColumnHeading(5, PROVIDED);
            scrutinyDetail.addColumnHeading(6, STATUS);

            HashMap<String, String> errors = new HashMap<>();

            BigDecimal plotArea = pl.getPlanInformation().getPlotArea();
            if (plotArea != null) {
                Set<OccupancyTypeHelper> occupancyTypes = pl.getVirtualBuilding().getOccupancyTypes();
                for (OccupancyTypeHelper o : occupancyTypes) {
                    Map<String, String> details = new HashMap<>();
                    details.put(RULE_NO, RULE_34);
                    details.put(DESCRIPTION, PLOTAREA_DESCRIPTION);

                    OccupancyHelperDetail occupancyType = o.getSubtype() != null ? o.getSubtype() : o.getType();
                    if (occupancyType != null) {
                        details.put(OCCUPANCY, occupancyType.getName());
                        double occupancyValues = getOccupancyValues(occupancyType.getCode());
                        if (plotArea.compareTo(BigDecimal.valueOf(occupancyValues)) >= 0) {
                            details.put(PERMITTED, String.valueOf(occupancyValues) + "m2");
                            details.put(PROVIDED, plotArea.toString() + "m2");
                            details.put(STATUS, Result.Accepted.getResultVal());
                            scrutinyDetail.getDetail().add(details);
                            pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);

                        } else {
                            details.put(PERMITTED, String.valueOf(occupancyValues) + "m2");
                            details.put(PROVIDED, plotArea.toString() + "m2");
                            details.put(STATUS, Result.Not_Accepted.getResultVal());
                            scrutinyDetail.getDetail().add(details);
                            pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);
                        }

                    }
                }
            }
        }

        return pl;
    }

    public double getOccupancyValues(String occupancyCode) {
        if (F_RT.equals(occupancyCode) || F_CB.equals(occupancyCode)) {
            return THREE_ZERO;
        }
        return 0;
    }
}