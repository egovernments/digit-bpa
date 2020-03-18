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

        for (Map.Entry<String, String> map : temp.entrySet()) {
            if (!map.getKey().equalsIgnoreCase("state")) {
               /* if (map.getKey().equalsIgnoreCase("generic")) {
                    if (!multitenancyEnabled)
                        tenants.put(map.getKey(), temp.get(map.getKey()));
                } else {*/
            	if(!temp.get(map.getKey()).endsWith("/"))
                    tenants.put(map.getKey(), temp.get(map.getKey())+"/");
            	else
            		 tenants.put(map.getKey(), temp.get(map.getKey()));
            }
        }
        return tenants;
    }

}