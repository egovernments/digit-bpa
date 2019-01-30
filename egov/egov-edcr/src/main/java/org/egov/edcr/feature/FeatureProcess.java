package org.egov.edcr.feature;

import org.egov.common.entity.edcr.Plan;
import org.egov.common.entity.edcr.ScrutinyDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public abstract class FeatureProcess {
	
	protected ScrutinyDetail scrutinyDetail = new ScrutinyDetail();
	public static final String STATUS = "Status";

	public static final String PROVIDED = "Provided";
	public static final String LEVEL = "Level";
	public static final String OCCUPANCY = "Occupancy";
	public static final String FIELDVERIFIED = "Field Verified";
	public static final String REQUIRED = "Required";

	public static final String DESCRIPTION = "Description";

	public static final String RULE_NO = "Rule No";

	public abstract Plan validate(Plan pl);

	public abstract  Plan process(Plan pl);
	
	@Autowired
	@Qualifier("parentMessageSource")
	private MessageSource edcrMessageSource;
	public MessageSource getEdcrMessageSource() {
		return edcrMessageSource;
	}
	public void setEdcrMessageSource(MessageSource edcrMessageSource) {
		this.edcrMessageSource = edcrMessageSource;
	}
	
	public String getLocaleMessage(String code, String... args) {
		return edcrMessageSource.getMessage(code, args, LocaleContextHolder.getLocale());

	}
 
}
