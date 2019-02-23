package org.egov.bpa.web.controller.adaptor;


import java.lang.reflect.Type;

import org.egov.bpa.master.entity.ChecklistServiceTypeMapping;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ChecklistServicetypeMappingAdaptor implements JsonSerializer<ChecklistServiceTypeMapping> {
    @Override
    public JsonElement serialize(final ChecklistServiceTypeMapping checklistmapping, final Type type,
            final JsonSerializationContext jsc) {
        final JsonObject jsonObject = new JsonObject();
        if (checklistmapping != null) {
            jsonObject.addProperty("checklist", checklistmapping.getChecklist().getDescription());
            jsonObject.addProperty("serviceType", checklistmapping.getServiceType().getDescription());
			jsonObject.addProperty("required",checklistmapping.isRequired());
			jsonObject.addProperty("mandatory",checklistmapping.isMandatory());
			jsonObject.addProperty("id", checklistmapping.getId());
        }
        return jsonObject;
    }
}