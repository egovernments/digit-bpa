/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2017>  eGovernments Foundation
 *
 *     The updated version of eGov suite of products as by eGovernments Foundation
 *     is available at http://www.egovernments.org
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program. If not, see http://www.gnu.org/licenses/ or
 *     http://www.gnu.org/licenses/gpl.html .
 *
 *     In addition to the terms of the GPL license to be adhered to in using this
 *     program, the following additional terms are to be complied with:
 *
 *         1) All versions of this program, verbatim or modified must carry this
 *            Legal Notice.
 *
 *         2) Any misrepresentation of the origin of the material is prohibited. It
 *            is required that all modified versions of this material be marked in
 *            reasonable ways as different from the original version.
 *
 *         3) This license does not grant any rights to any user of the program
 *            with regards to rights under trademark law for use of the trade names
 *            or trademarks of eGovernments Foundation.
 *
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */
package org.egov.bpa.web.controller.adaptor;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.apache.commons.lang.StringUtils;
import org.egov.bpa.master.entity.StakeHolder;
import org.egov.infra.utils.DateUtils;

import java.lang.reflect.Type;

import static org.apache.commons.lang3.StringUtils.defaultString;

public class StakeHolderJsonAdaptor implements JsonSerializer<StakeHolder> {
	@Override
	public JsonElement serialize(final StakeHolder stakeHolder, final Type type,
								 final JsonSerializationContext jsc) {
		final JsonObject jsonObject = new JsonObject();
		if (stakeHolder != null) {
			jsonObject.addProperty("name", defaultString(stakeHolder.getName()));
			jsonObject.addProperty("stakeHolderType", defaultString(stakeHolder.getStakeHolderType().getName()));
			jsonObject.addProperty("code", defaultString(stakeHolder.getCode()));
			jsonObject.addProperty("licenceNumber", defaultString(stakeHolder.getLicenceNumber()));
			jsonObject.addProperty("aadharNumber", defaultString(stakeHolder.getAadhaarNumber()));
			jsonObject.addProperty("status", StringUtils.defaultString(stakeHolder.getStatus().getStakeHolderStatusVal()));
			jsonObject.addProperty("isActive", stakeHolder.getIsActive());
			jsonObject.addProperty("createdDate", DateUtils.toDefaultDateFormat(stakeHolder.getCreatedDate()));
			jsonObject.addProperty("id", stakeHolder.getId());
		}
		return jsonObject;
	}
}