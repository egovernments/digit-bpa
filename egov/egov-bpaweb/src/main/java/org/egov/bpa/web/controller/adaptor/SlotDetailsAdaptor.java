/*
 * eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 * accountability and the service delivery of the government  organizations.
 *
 *  Copyright (C) <2017>  eGovernments Foundation
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

package org.egov.bpa.web.controller.adaptor;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import org.egov.bpa.transaction.entity.dto.SlotDetailsHelper;
import org.egov.infra.utils.DateUtils;
import org.egov.infra.web.support.json.adapter.DataTableJsonAdapter;
import org.egov.infra.web.support.ui.DataTable;

import java.lang.reflect.Type;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.defaultString;

public class SlotDetailsAdaptor implements DataTableJsonAdapter<SlotDetailsHelper> {
	@Override
	public JsonElement serialize(final DataTable<SlotDetailsHelper> slotDetailsResponse, final Type type,
								 final JsonSerializationContext jsc) {
		List<SlotDetailsHelper> slotDetailsResult = slotDetailsResponse.getData();
		final JsonArray slotDetailsResultData = new JsonArray();
		slotDetailsResult.forEach(baseForm -> {
			final JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("slotId", baseForm.getSlotId());
			jsonObject.addProperty("slotDetailsId", baseForm.getSlotDetailsId());
			jsonObject.addProperty("applicationType", baseForm.getApplicationType());
			jsonObject.addProperty("appointmentDate", DateUtils.toDefaultDateFormat(baseForm.getAppointmentDate()));
			jsonObject.addProperty("appointmentTime", baseForm.getAppointmentTime());
			jsonObject.addProperty("electionWard", defaultString(baseForm.getElectionWard(), "N/A"));
			jsonObject.addProperty("electionWardId", baseForm.getElectionWardId());
			jsonObject.addProperty("zone", defaultString(baseForm.getZone(), "N/A"));
			jsonObject.addProperty("zoneId", baseForm.getZoneId());
			jsonObject.addProperty("maxScheduledSlots", baseForm.getMaxScheduledSlots());
			jsonObject.addProperty("maxRescheduledSlots", baseForm.getMaxRescheduledSlots());
			jsonObject.addProperty("utilizedScheduledSlots", baseForm.getUtilizedScheduledSlots());
			jsonObject.addProperty("utilizedRescheduledSlots", baseForm.getUtilizedRescheduledSlots());
			slotDetailsResultData.add(jsonObject);
		});

		return enhance(slotDetailsResultData, slotDetailsResponse);
	}
}