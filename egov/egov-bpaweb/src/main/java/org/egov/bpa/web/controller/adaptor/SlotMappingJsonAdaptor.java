package org.egov.bpa.web.controller.adaptor;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.egov.bpa.master.entity.SlotMapping;
import org.egov.bpa.master.entity.enums.WorkingDays;

import java.lang.reflect.Type;

import static org.apache.commons.lang3.StringUtils.defaultString;

public class SlotMappingJsonAdaptor implements JsonSerializer<SlotMapping> {
    @Override
    public JsonElement serialize(final SlotMapping slotmapping, final Type type,
            final JsonSerializationContext jsc) {
        final JsonObject jsonObject = new JsonObject();
        if (slotmapping != null) {
            jsonObject.addProperty("maxSlotsAllowed", slotmapping.getMaxSlotsAllowed());
            jsonObject.addProperty("maxRescheduledSlotsAllowed", slotmapping.getMaxRescheduledSlotsAllowed() != null ? slotmapping.getMaxRescheduledSlotsAllowed() : 0 );
			jsonObject.addProperty("revenueWard", slotmapping.getRevenueWard() != null
					? defaultString(slotmapping.getRevenueWard().getName(), "N/A") : "");
			jsonObject.addProperty("electionWard",
                    slotmapping.getElectionWard() != null ? defaultString(slotmapping.getElectionWard().getName(), "N/A") : "");
			jsonObject.addProperty("zone", defaultString(slotmapping.getZone().getName()));
			jsonObject.addProperty("applType", defaultString(slotmapping.getApplType().getApplicationTypeVal()));
			jsonObject.addProperty("day", slotmapping.getDay() != null
					? WorkingDays.getEnumNameForValue(slotmapping.getDay()).toString() : "");
			jsonObject.addProperty("id", slotmapping.getId());
        }
        return jsonObject;
    }
}