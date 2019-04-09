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

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.egov.common.entity.edcr.Plan;
import org.egov.common.entity.edcr.Result;
import org.egov.common.entity.edcr.ScrutinyDetail;
import org.egov.infra.utils.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class Distance extends FeatureProcess {

    private static final Logger LOG = Logger.getLogger(Distance.class);
    private static final String RULE_20 = "20";
    private static final String MIN_DISTANCE_FROM_MONUMENT = " >= 100";
    public static final String MIN_DISTANCE_FROM_MONUMENTT_DESC = "Minimum distancce fcrom monument";

    @Override
    public Plan validate(Plan pl) {

        return pl;
    }

    @Override
    public Plan process(Plan pl) {
        HashMap<String, String> errors = new HashMap<>();

        boolean isAccepted = false;
        BigDecimal minDistanceFromMonument = BigDecimal.ZERO;
        ScrutinyDetail scrutinyDetail = getNewScrutinyDetail("Distance_From_Monument");
        List<BigDecimal> distancesFromMonument = pl.getDistancesFromMonument();

        if (StringUtils.isNotBlank(pl.getPlanInformation().getNocNearMonuments())
                && "YES".equalsIgnoreCase(pl.getPlanInformation().getNocNearMonuments()) &&
                StringUtils.isNotBlank(pl.getPlanInformation().getBuildingNearMonuments())
                && "YES".equalsIgnoreCase(pl.getPlanInformation().getBuildingNearMonuments())) {
            if (!distancesFromMonument.isEmpty()) {
                minDistanceFromMonument = distancesFromMonument.stream().reduce(BigDecimal::min).get();
                if (minDistanceFromMonument.compareTo(BigDecimal.valueOf(1)) > 0) {
                    isAccepted = true;
                }
            } else {
                errors.put("Distance_From_Monumnet", "No Construction is allowed with in 100 mts from monument ");
                pl.addErrors(errors);
            }

            if (errors.isEmpty()) {
                Map<String, String> details = new HashMap<>();
                details.put(RULE_NO, RULE_20);
                details.put(DESCRIPTION, MIN_DISTANCE_FROM_MONUMENTT_DESC);
                details.put(REQUIRED, MIN_DISTANCE_FROM_MONUMENT);
                details.put(PROVIDED, String.valueOf(minDistanceFromMonument));
                details.put(STATUS, isAccepted ? Result.Accepted.getResultVal() : Result.Not_Accepted.getResultVal());
                scrutinyDetail.getDetail().add(details);
                pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);
            }
        }
        return pl;
    }

    @Override
    public Map<String, Date> getAmendments() {
        return new LinkedHashMap<>();
    }

    private ScrutinyDetail getNewScrutinyDetail(String key) {
        ScrutinyDetail scrutinyDetail = new ScrutinyDetail();
        scrutinyDetail.addColumnHeading(1, RULE_NO);
        scrutinyDetail.addColumnHeading(2, DESCRIPTION);
        scrutinyDetail.addColumnHeading(3, REQUIRED);
        scrutinyDetail.addColumnHeading(4, PROVIDED);
        scrutinyDetail.addColumnHeading(5, STATUS);
        scrutinyDetail.setKey(key);
        return scrutinyDetail;
    }
}
