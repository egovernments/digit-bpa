package org.egov.portal.utils;

import java.util.HashMap;
import java.util.Map;

import org.egov.infra.utils.TenantUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PortalUtils {

    @Autowired
    private TenantUtils tenantUtils;

    @Value("${multitenancy.enabled}")
    private boolean multitenancyEnabled;

    public Map<String, String> tenantsMap() {
        Map<String, String> tenants = new HashMap<>();
        Map<String, String> temp = tenantUtils.tenantsMap();

        for (String key : temp.keySet()) {

            if (!key.equalsIgnoreCase("state")) {
                if (key.equalsIgnoreCase("generic")) {
                    if (!multitenancyEnabled)
                        tenants.put(key, temp.get(key));
                } else {
                    tenants.put(key, temp.get(key));
                }
            }
        }

        return tenants;
    }

}