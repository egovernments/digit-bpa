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

    public List<BigDecimal> getPassageStairDimensions() {
        return passageStairDimensions;
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

    public void setPassageStairDimensions(List<BigDecimal> passageStairDimensions) {
        this.passageStairDimensions = passageStairDimensions;
    }

    public List<BigDecimal> getPassageDimensions() {
        return passageDimensions;
    }

    public void setPassageDimensions(List<BigDecimal> passageDimensions) {
        this.passageDimensions = passageDimensions;
    }

}
