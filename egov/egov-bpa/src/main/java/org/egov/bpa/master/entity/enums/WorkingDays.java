package org.egov.bpa.master.entity.enums;

public enum WorkingDays {

	MON("1"), TUE("2"), WED("3"), THURS("4"), FRI("5");

	private final String wDaysVal;

	private WorkingDays(String wDaysVal) {
		this.wDaysVal = wDaysVal;
	}

	public String getHolidayTypeVal() {
		return wDaysVal;
	}

	public static String getEnumNameForValue(String value) {
		WorkingDays[] values = WorkingDays.values();
		for (WorkingDays eachValue : values) {
			if (eachValue.getHolidayTypeVal().equals(value)) {
				return eachValue.name();
			}
		}
		return null;
	}

}
