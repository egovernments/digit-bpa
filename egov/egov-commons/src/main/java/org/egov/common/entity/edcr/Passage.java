package org.egov.common.entity.edcr;

import java.math.BigDecimal;
import java.util.List;

public class Passage extends Measurement {

    /**
    * 
    */
    private static final long serialVersionUID = -1029392214753164176L;

    private List<BigDecimal> passageDimensions;

    private List<BigDecimal> passageStairDimensions;

    public List<BigDecimal> getPassagePolyLines() {
        return passageDimensions;
    }

    public void setPassagePolyLines(List<BigDecimal> passagePolyLines) {
        passageDimensions = passagePolyLines;
    }

    public List<BigDecimal> getPassageStairPolyLines() {
        return passageStairDimensions;
    }

    public void setPassageStairPolyLines(List<BigDecimal> passageStairPolyLines) {
        passageStairDimensions = passageStairPolyLines;
    }

}
