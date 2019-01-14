package org.egov.bpa.transaction.entity.enums;

public enum ServiceTypeEnum {
	ALLOTHERSERVICES("all other services"), ONEDAYPERMIT("one day permit");
	
	private final String serviceTypeVal;

    private ServiceTypeEnum(String serviceTypeVal) {
        this.serviceTypeVal = serviceTypeVal;
    }

    public String getServiceTypeVal() {
        return serviceTypeVal;
    }

    @Override
    public String toString() {
        return serviceTypeVal.replace("_", "");
    }
}
