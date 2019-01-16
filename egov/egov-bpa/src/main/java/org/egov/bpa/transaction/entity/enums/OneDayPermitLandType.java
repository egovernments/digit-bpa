package org.egov.bpa.transaction.entity.enums;

public enum OneDayPermitLandType {
	   GARDENLAND("Garden Land"), WETLAND("Wet Land");
	    
	    private final String OneDayPermitLandTypeVal;

	    private OneDayPermitLandType(String landTypeVal) {
	        this.OneDayPermitLandTypeVal = landTypeVal;
	    }

	    public String OneDayPermitLandTypeVal() {
			return OneDayPermitLandTypeVal;
		}

		@Override
	    public String toString() {
			return OneDayPermitLandTypeVal.replace("_", "");
	    }
}
