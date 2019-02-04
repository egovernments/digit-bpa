package org.egov.edcr.feature;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.egov.common.entity.edcr.Plan;
import org.springframework.stereotype.Service;

@Service
public class PlanInfoFeature extends FeatureProcess {
    public static final String MSG_ERROR_MANDATORY = "msg.error.mandatory.object.not.defined";
    private String digitsRegex = "[^\\d.]";

    private static final BigDecimal ONEHUDREDTWENTYFIVE = BigDecimal.valueOf(125);

    @Override
    public Plan validate(Plan pl) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Plan process(Plan pl) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Date> getAmendments() {
        return new LinkedHashMap<>();
    }

}
