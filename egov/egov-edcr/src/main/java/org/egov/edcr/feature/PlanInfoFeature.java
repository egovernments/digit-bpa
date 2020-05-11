package org.egov.edcr.feature;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.egov.common.entity.edcr.Plan;
import org.egov.commons.mdms.config.MdmsConfiguration;
import org.egov.infra.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlanInfoFeature extends FeatureProcess {

    @Autowired
    private MdmsConfiguration mdmsConfiguration;

    @Override
    public Plan validate(Plan pl) {
        // TODO Auto-generated method stub
        return pl;
    }

    @Override
    public Plan process(Plan pl) {
        Boolean mdmsEnabled = mdmsConfiguration.getMdmsEnabled();
        if (mdmsEnabled != null && mdmsEnabled) {
            String serviceType = pl.getPlanInformation().getServiceType();

            if (StringUtils.isBlank(serviceType)) {
                pl.addError("SERVICE_TYPE_NOT_DEFINED", "Service type is not declared in PLAN_INFO");
            } else {
                Map<String, List<String>> mdmsMasterData = pl.getMdmsMasterData();
                List<String> serviceTypeList = mdmsMasterData.get("ServiceType");

                if (serviceTypeList != null && !serviceTypeList.isEmpty()
                        && !serviceTypeList.stream().anyMatch(serviceType::equalsIgnoreCase))
                    pl.addError("SERVICE_TYPE_NOT_VALID",
                            "Service type " + serviceType + " declared in PLAN_INFO is not correct.");
            }
        }
        return pl;
    }

    @Override
    public Map<String, Date> getAmendments() {
        return new LinkedHashMap<>();
    }

}
