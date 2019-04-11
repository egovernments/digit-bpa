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
public class RiverDistance extends FeatureProcess {

	private static final Logger LOG = Logger.getLogger(RiverDistance.class);
	private static final String RULE_22 = "22";
	public static final String RIVER_GANGA_DESCRIPTION = "Distancce from ganga river";
	public static final String RIVER_NON_GANGA_DESCRIPTION = "Distancce from non ganga river";
	public static final String PROTECTION_WALL_GANGA_DESCRIPTION = "Distancce from protection wall ganga";
	public static final String EMBANKMENT_GANGA_DESCRIPTION = "Distancce from embankment ganga";

	@Override
	public Plan validate(Plan pl) {

		return pl;
	}

	@Override
	public Plan process(Plan pl) {

		ScrutinyDetail scrutinyDetail = new ScrutinyDetail();
		scrutinyDetail.setKey("Common_River Distance");
		scrutinyDetail.addColumnHeading(1, RULE_NO);
		scrutinyDetail.addColumnHeading(2, DESCRIPTION);
		scrutinyDetail.addColumnHeading(3, PERMITTED);
		scrutinyDetail.addColumnHeading(4, PROVIDED);
		scrutinyDetail.addColumnHeading(5, STATUS);

		HashMap<String, String> errors = new HashMap<>();
		Map<String, String> details = new HashMap<>();
		details.put(RULE_NO, RULE_22);

		BigDecimal minDistanceFromProtectionWallGanga = BigDecimal.ZERO;
		BigDecimal minDistanceFromEmbankmentGanga = BigDecimal.ZERO;
		BigDecimal minDistanceFromRiverGangaEdge = BigDecimal.ZERO;
		BigDecimal minDistanceFromRiverNonGanga = BigDecimal.ZERO;
		List<BigDecimal> distancesFromProtectionWallGanga = pl.getDistancesFromProtectionWallGanga();
		List<BigDecimal> distancesFromEmbankmentGanga = pl.getDistancesFromEmbankmentGanga();
		List<BigDecimal> distancesFromRiverGangaEdge = pl.getDistancesFromRiverGangaEdge();
		List<BigDecimal> distancesFromRiverNonGanga = pl.getDistancesFromRiverNonGanga();

		if (StringUtils.isNotBlank(pl.getPlanInformation().getBuildingNearToRiver())
				&& "YES".equalsIgnoreCase(pl.getPlanInformation().getBuildingNearToRiver())) {
			if (!distancesFromProtectionWallGanga.isEmpty()) {

				minDistanceFromProtectionWallGanga = distancesFromProtectionWallGanga.stream().reduce(BigDecimal::min)
						.get();

				if (minDistanceFromProtectionWallGanga.compareTo(BigDecimal.valueOf(30)) > 0) {
					details.put(DESCRIPTION, PROTECTION_WALL_GANGA_DESCRIPTION);
					details.put(PERMITTED, ">30");
					details.put(PROVIDED, minDistanceFromProtectionWallGanga.toString());
					details.put(STATUS, Result.Accepted.getResultVal());
					scrutinyDetail.getDetail().add(details);
					pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);
				} else {
					details.put(DESCRIPTION, PROTECTION_WALL_GANGA_DESCRIPTION);
					details.put(PERMITTED, "<=30");
					details.put(PROVIDED, minDistanceFromProtectionWallGanga.toString());
					details.put(STATUS, Result.Not_Accepted.getResultVal());
					scrutinyDetail.getDetail().add(details);
					pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);

				}
			} else if (!distancesFromEmbankmentGanga.isEmpty()) {
				details = new HashMap<>();
				details.put(RULE_NO, RULE_22);

				minDistanceFromEmbankmentGanga = distancesFromEmbankmentGanga.stream().reduce(BigDecimal::min).get();

				if (minDistanceFromEmbankmentGanga.compareTo(BigDecimal.valueOf(50)) > 0) {
					details.put(DESCRIPTION, EMBANKMENT_GANGA_DESCRIPTION);
					details.put(PERMITTED, ">50");
					details.put(PROVIDED, minDistanceFromEmbankmentGanga.toString());
					details.put(STATUS, Result.Accepted.getResultVal());
					scrutinyDetail.getDetail().add(details);
					pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);
				} else {
					details.put(DESCRIPTION, EMBANKMENT_GANGA_DESCRIPTION);
					details.put(PERMITTED, "<=50");
					details.put(PROVIDED, minDistanceFromEmbankmentGanga.toString());
					details.put(STATUS, Result.Not_Accepted.getResultVal());
					scrutinyDetail.getDetail().add(details);
					pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);

				}
			} else if (!distancesFromRiverGangaEdge.isEmpty()) {
				details = new HashMap<>();
				details.put(RULE_NO, RULE_22);

				minDistanceFromRiverGangaEdge = distancesFromRiverGangaEdge.stream().reduce(BigDecimal::min).get();

				if (minDistanceFromRiverGangaEdge.compareTo(BigDecimal.valueOf(200)) > 0) {
					details.put(DESCRIPTION, RIVER_GANGA_DESCRIPTION);
					details.put(PERMITTED, ">200");
					details.put(PROVIDED, minDistanceFromRiverGangaEdge.toString());
					details.put(STATUS, Result.Accepted.getResultVal());
					scrutinyDetail.getDetail().add(details);
					pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);
				} else {
					details.put(DESCRIPTION, RIVER_GANGA_DESCRIPTION);
					details.put(PERMITTED, "<=200");
					details.put(PROVIDED, minDistanceFromRiverGangaEdge.toString());
					details.put(STATUS, Result.Not_Accepted.getResultVal());
					scrutinyDetail.getDetail().add(details);
					pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);

				}
			}

			if (!distancesFromRiverNonGanga.isEmpty()) {

				minDistanceFromRiverNonGanga = distancesFromRiverNonGanga.stream().reduce(BigDecimal::min).get();
				details = new HashMap<>();
				details.put(RULE_NO, RULE_22);

				if (minDistanceFromRiverNonGanga.compareTo(BigDecimal.valueOf(100)) > 0) {
					details.put(DESCRIPTION, RIVER_NON_GANGA_DESCRIPTION);
					details.put(PERMITTED, ">100");
					details.put(PROVIDED, minDistanceFromRiverNonGanga.toString());
					details.put(STATUS, Result.Accepted.getResultVal());
					scrutinyDetail.getDetail().add(details);
					pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);
				} else {
					details.put(DESCRIPTION, RIVER_NON_GANGA_DESCRIPTION);
					details.put(PERMITTED, "<=100");
					details.put(PROVIDED, minDistanceFromRiverNonGanga.toString());
					details.put(STATUS, Result.Not_Accepted.getResultVal());
					scrutinyDetail.getDetail().add(details);
					pl.getReportOutput().getScrutinyDetails().add(scrutinyDetail);

				}
			}

			if (distancesFromProtectionWallGanga.isEmpty() && distancesFromEmbankmentGanga.isEmpty()
					&& distancesFromRiverGangaEdge.isEmpty() && distancesFromRiverNonGanga.isEmpty()) {
				errors.put("Distance_From_River",
						"No distance is provided from protection wall ganga/embankment/river ganga edge or non ganga river");
				pl.addErrors(errors);
			}
		}
		return pl;
	}

	@Override
	public Map<String, Date> getAmendments() {
		return new LinkedHashMap<>();
	}

}
