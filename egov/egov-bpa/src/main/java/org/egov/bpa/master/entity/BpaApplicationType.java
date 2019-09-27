package org.egov.bpa.master.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum BpaApplicationType {

	PLAN_PERMIT("Plan Permit"), 
	
	OCCUPANCY_PERMIT("Occupancy certificate"), 
	
	COMPLETION_CERTIFICATE("Completion Certificate");

	@JsonValue
	private final String applicationTypeVal;

	BpaApplicationType(String aTypeVal) {
		this.applicationTypeVal = aTypeVal;
	}

	public String getBpaApplicationType() {
		return applicationTypeVal;
	}

	public String getBpaApplicationTypeVal() {
		return applicationTypeVal;
	}

}