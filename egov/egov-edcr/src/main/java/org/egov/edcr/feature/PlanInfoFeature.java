package org.egov.edcr.feature;

import java.math.BigDecimal;

import org.egov.edcr.entity.Plan;
import org.egov.edcr.entity.blackbox.PlanDetail;
import org.springframework.stereotype.Service;

@Service
public class PlanInfoFeature extends FeatureProcess {
    public static final String MSG_ERROR_MANDATORY = "msg.error.mandatory.object.not.defined";
    private String digitsRegex = "[^\\d.]";
  
    private static final BigDecimal ONEHUDREDTWENTYFIVE = BigDecimal.valueOf(125);

	@Override
	public PlanDetail validate(Plan pl) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlanDetail process(Plan pl) {
		// TODO Auto-generated method stub
		return null;
	}

 

   

   

   
 

}
