package org.egov.bpa.web.adaptor;

import java.lang.reflect.Type;

import org.egov.bpa.master.entity.NocConfig;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class NocConfigJsonAdaptor implements JsonSerializer<NocConfig> {
	@Override
	public JsonElement serialize(final NocConfig nocConfig, final Type type, final JsonSerializationContext jsc) {
		final JsonObject jsonObject = new JsonObject();
		if (nocConfig != null) {
			if (nocConfig.getNocDepartment() != null)
				jsonObject.addProperty("nocDepartment", nocConfig.getNocDepartment().getName());
			else
				jsonObject.addProperty("nocDepartment", "");
			if (nocConfig.getApplicationType() != null)
				jsonObject.addProperty("applicationType", nocConfig.getApplicationType().toString());
			else
				jsonObject.addProperty("applicationType", "");
			if (nocConfig.getIntegrationType() != null)
				jsonObject.addProperty("integrationType", nocConfig.getIntegrationType().toString());
			else
				jsonObject.addProperty("integrationType", "");
			if (nocConfig.getIsDeemedApproved() != null)
				jsonObject.addProperty("isDeemedApproved", nocConfig.getIsDeemedApproved());
			else
				jsonObject.addProperty("isDeemedApproved", "");
			if (nocConfig.getDaysForDeemedApproval() != null)
				jsonObject.addProperty("daysForDeemedApproval", nocConfig.getDaysForDeemedApproval());
			else
				jsonObject.addProperty("daysForDeemedApproval", "");
			jsonObject.addProperty("id", nocConfig.getId());
		}
		return jsonObject;
	}
}