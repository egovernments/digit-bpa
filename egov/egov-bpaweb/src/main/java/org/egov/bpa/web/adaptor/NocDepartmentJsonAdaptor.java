package org.egov.bpa.web.adaptor;

import java.lang.reflect.Type;

import org.egov.bpa.master.entity.NocDepartment;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class NocDepartmentJsonAdaptor implements JsonSerializer<NocDepartment> {
	@Override
	public JsonElement serialize(final NocDepartment nocDepartment, final Type type,
			final JsonSerializationContext jsc) {
		final JsonObject jsonObject = new JsonObject();
		if (nocDepartment != null) {
			if (nocDepartment.getName() != null)
				jsonObject.addProperty("name", nocDepartment.getName());
			else
				jsonObject.addProperty("name", "");
			if (nocDepartment.getIsActive() != null)
				jsonObject.addProperty("isActive", nocDepartment.getIsActive());
			else
				jsonObject.addProperty("isActive", "");
			jsonObject.addProperty("id", nocDepartment.getId());
		}
		return jsonObject;
	}
}