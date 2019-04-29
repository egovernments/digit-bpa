package org.egov.bpa.web.controller.adaptor;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import org.egov.bpa.transaction.entity.dto.BpaRegisterReportHelper;
import org.egov.infra.web.support.json.adapter.DataTableJsonAdapter;
import org.egov.infra.web.support.ui.DataTable;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.defaultString;
import static org.egov.infra.utils.DateUtils.toDefaultDateFormat;

public class BpaRegisterReportAdaptor implements DataTableJsonAdapter<BpaRegisterReportHelper> {
	protected static final String N_A = "N/A";

	@Override
	public JsonElement serialize(final DataTable<BpaRegisterReportHelper> bpaRegisterResponse, final Type type,
								 final JsonSerializationContext jsc) {
		List<BpaRegisterReportHelper> bpaRegisterResult = bpaRegisterResponse.getData();
		final JsonArray bpaRegisterResultData = new JsonArray();
		bpaRegisterResult.forEach(baseForm -> {
			final JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("applicationNumber", defaultString(baseForm.getApplicationNumber()));
			jsonObject.addProperty("applicationType", defaultString(baseForm.getApplicationType()));
			jsonObject.addProperty("serviceType", defaultString(baseForm.getServiceType()));
			jsonObject.addProperty("permitType", defaultString(baseForm.getPermitType()));
			jsonObject.addProperty("dateOfAdmission", toDefaultDateFormat(baseForm.getDateOfAdmission()));
			jsonObject.addProperty("applicantName", defaultString(baseForm.getApplicantName()));
			jsonObject.addProperty("address", defaultString(baseForm.getAddress()));
			jsonObject.addProperty("surveyNumber", defaultString(baseForm.getSurveyNumber()));
			jsonObject.addProperty("village", baseForm.getVillage());
			jsonObject.addProperty("revenueWard", baseForm.getRevenueWard());
			jsonObject.addProperty("electionWard", baseForm.getElectionWard());
			jsonObject.addProperty("natureOfOccupancy", defaultString(baseForm.getNatureOfOccupancy()));
			jsonObject.addProperty("totalFloorArea", baseForm.getTotalFloorArea().compareTo(BigDecimal.ZERO) == 0 ? N_A : String.valueOf(baseForm.getTotalFloorArea()));
			jsonObject.addProperty("numberOfFloors", baseForm.getNumberOfFloors() == null ? N_A : String.valueOf(baseForm.getNumberOfFloors()));
			jsonObject.addProperty("far", baseForm.getFar().compareTo(BigDecimal.ZERO) == 0 ? N_A : String.valueOf(baseForm.getFar()));
			jsonObject.addProperty("applicationFee", baseForm.getApplicationFee());
			jsonObject.addProperty("docVerificationDate", toDefaultDateFormat(baseForm.getDocVerificationDate()));
			jsonObject.addProperty("fieldVerificationDate", toDefaultDateFormat(baseForm.getFieldVerificationDate()));
			jsonObject.addProperty("nocDetails", defaultString(baseForm.getNocDetails()));
			jsonObject.addProperty("buildingPermitNo", defaultString(baseForm.getBuildingPermitNo()));
			jsonObject.addProperty("permitFee", baseForm.getPermitFee());
			jsonObject.addProperty("additionalFee", baseForm.getAdditionalFee());
			jsonObject.addProperty("otherFee", baseForm.getOtherFee());
			jsonObject.addProperty("shelterFund", baseForm.getShelterFund());
			jsonObject.addProperty("labourcess", baseForm.getLabourcess());
			jsonObject.addProperty("developmentPermitFees", baseForm.getDevelopmentPermitFees());
			jsonObject.addProperty("rejectionReason", defaultString(baseForm.getRejectionReason(), N_A));
			jsonObject.addProperty("finalApprovalDate", baseForm.getFinalApprovalDate() != null
														? toDefaultDateFormat(baseForm.getFinalApprovalDate()) : N_A);
			jsonObject.addProperty("apprvd_rejected_by", defaultString(baseForm.getApprvd_rejected_by()));
			bpaRegisterResultData.add(jsonObject);
		});

		return enhance(bpaRegisterResultData, bpaRegisterResponse);
	}
}
