package org.egov.common.entity.edcr;

import java.math.BigDecimal;
import java.util.List;
public class Passage extends Measurement {

	private List<BigDecimal> passageDimensions;
	
	private List<BigDecimal> passageStairDimensions;

	public List<BigDecimal> getPassagePolyLines() {
		return passageDimensions;
	}

	public void setPassagePolyLines(List<BigDecimal> passagePolyLines) {
		this.passageDimensions = passagePolyLines;
	}

	public List<BigDecimal> getPassageStairPolyLines() {
		return passageStairDimensions;
	}

	public void setPassageStairPolyLines(List<BigDecimal> passageStairPolyLines) {
		this.passageStairDimensions = passageStairPolyLines;
	}

}
