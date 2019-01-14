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
import org.egov.bpa.transaction.entity.dto.PersonalRegisterHelper;
import org.joda.time.DateTime;

import java.lang.reflect.Type;
import java.math.BigDecimal;

import static org.apache.commons.lang3.StringUtils.defaultString;
import static org.egov.infra.config.core.LocalizationSettings.jodaTimeZone;
import static org.egov.infra.utils.DateUtils.formatter;
import static org.egov.infra.utils.DateUtils.toDefaultDateFormat;

public class SearchPersonalRegisterAdaptor implements JsonSerializer<PersonalRegisterHelper> {

    protected static final String N_A = "N/A";

    @Override
    public JsonElement serialize(final PersonalRegisterHelper searchFormObj, final Type type,
                                 final JsonSerializationContext jsc) {
        final JsonObject jsonObject = new JsonObject();

        if (searchFormObj != null) {
            jsonObject.addProperty("applicationNumber", defaultString(searchFormObj.getApplicationNumber()));
            jsonObject.addProperty("applicationType", defaultString(searchFormObj.getApplicationType()));
            jsonObject.addProperty("permitType", defaultString(searchFormObj.getPermitType()));
            jsonObject.addProperty("dateOfAdmission", toDefaultDateFormat(searchFormObj.getDateOfAdmission()));
            jsonObject.addProperty("applicantName", defaultString(searchFormObj.getApplicantName()));
            jsonObject.addProperty("address", defaultString(searchFormObj.getAddress()));
            jsonObject.addProperty("surveyNo", defaultString(searchFormObj.getSurveyNo()));
            jsonObject.addProperty("village", defaultString(searchFormObj.getVillage(), N_A));
            jsonObject.addProperty("revenueWard", searchFormObj.getRevenueWard());
            jsonObject.addProperty("electionWard", searchFormObj.getElectionWard());
            jsonObject.addProperty("natureOfOccupancy", searchFormObj.getNatureOfOccupancy());
            jsonObject.addProperty("totalFloorArea", searchFormObj.getTotalFloarArea() == null ? N_A : String.valueOf(searchFormObj.getTotalFloarArea()));
            jsonObject.addProperty("noOfFloars", searchFormObj.getNoOfFloors() == null ? N_A : String.valueOf(searchFormObj.getNoOfFloors()));
            jsonObject.addProperty("far", searchFormObj.getFar() != null && searchFormObj.getFar().compareTo(BigDecimal.ZERO) == 0 ? N_A : String.valueOf(searchFormObj.getFar()));
            jsonObject.addProperty("fromWhom", defaultString(searchFormObj.getFromWhom(), N_A));
            jsonObject.addProperty("previousStatus", defaultString(searchFormObj.getPreviousStatus(), N_A));
            jsonObject.addProperty("previousDateAndTime", searchFormObj.getPreviousDateAndTime() != null ? formatter("dd/MM/yyyy hh:mm a").print(new DateTime(searchFormObj.getPreviousDateAndTime(), jodaTimeZone())) : N_A);
            jsonObject.addProperty("toWhom", defaultString(searchFormObj.getToWhom(), N_A));
            jsonObject.addProperty("currentStatus", defaultString(searchFormObj.getCurrentStatus(), N_A));
            jsonObject.addProperty("nextDateAndTime", searchFormObj.getNextDateAndTime() != null ? formatter("dd/MM/yyyy hh:mm a").print(new DateTime(searchFormObj.getNextDateAndTime(), jodaTimeZone())) : N_A);
            jsonObject.addProperty("userId", searchFormObj.getUserId());
            jsonObject.addProperty("serviceTypeEnum", searchFormObj.getServiceTypeEnum());
        }
        return jsonObject;
    }
}