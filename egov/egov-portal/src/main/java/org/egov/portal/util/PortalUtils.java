package org.egov.portal.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.egov.infra.config.core.ApplicationThreadLocals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PortalUtils {

    @Autowired
    private ConfigurableEnvironment environment;

    public Map<String, String> tenantsMap() {
        URL url;
        Map<String, String> tenants = new HashMap<>();
        try {
            url = new URL(ApplicationThreadLocals.getDomainURL());
            environment.getPropertySources().iterator().forEachRemaining(propertySource -> {
                if (propertySource instanceof MapPropertySource)
                    ((MapPropertySource) propertySource).getSource().forEach((key, value) -> {
                        if (key.startsWith("tenant."))
                            tenants.put(value.toString(), url.getProtocol() + "://" + key.replace("tenant.", "")
                                    + (url.getPort() >= 8080 ? ":" + url.getPort() : "") + "/");
                    });
            });
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return tenants;
    }

}