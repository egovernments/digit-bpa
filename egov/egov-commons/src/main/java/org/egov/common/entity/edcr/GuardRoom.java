package org.egov.common.entity.edcr;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class GuardRoom {

	private List<Measurement> guardRooms = new ArrayList<>();

	protected List<BigDecimal> cabinHeight = new ArrayList<>();

	public List<Measurement> getGuardRooms() {
		return guardRooms;
	}

	public void setGuardRooms(List<Measurement> guardRooms) {
		this.guardRooms = guardRooms;
	}

	public List<BigDecimal> getCabinHeight() {
		return cabinHeight;
	}

	public void setCabinHeight(List<BigDecimal> cabinHeight) {
		this.cabinHeight = cabinHeight;
	}

}
