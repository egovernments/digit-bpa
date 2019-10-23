/*
 *    eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) 2017  eGovernments Foundation
 *
 *     The updated version of eGov suite of products as by eGovernments Foundation
 *     is available at http://www.egovernments.org
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program. If not, see http://www.gnu.org/licenses/ or
 *     http://www.gnu.org/licenses/gpl.html .
 *
 *     In addition to the terms of the GPL license to be adhered to in using this
 *     program, the following additional terms are to be complied with:
 *
 *         1) All versions of this program, verbatim or modified must carry this
 *            Legal Notice.
 *            Further, all user interfaces, including but not limited to citizen facing interfaces,
 *            Urban Local Bodies interfaces, dashboards, mobile applications, of the program and any
 *            derived works should carry eGovernments Foundation logo on the top right corner.
 *
 *            For the logo, please refer http://egovernments.org/html/logo/egov_logo.png.
 *            For any further queries on attribution, including queries on brand guidelines,
 *            please contact contact@egovernments.org
 *
 *         2) Any misrepresentation of the origin of the material is prohibited. It
 *            is required that all modified versions of this material be marked in
 *            reasonable ways as different from the original version.
 *
 *         3) This license does not grant any rights to any user of the program
 *            with regards to rights under trademark law for use of the trade names
 *            or trademarks of eGovernments Foundation.
 *
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 *
 */

package org.egov.infra.web.filter;

import static org.egov.infra.utils.ApplicationConstant.CITY_CODE_KEY;
import static org.egov.infra.web.utils.WebUtils.extractRequestDomainURL;
import static org.egov.infra.web.utils.WebUtils.extractRequestedDomainName;

import java.io.IOException;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.egov.infra.config.core.ApplicationThreadLocals;
import org.egov.infra.config.core.EnvironmentSettings;
import org.egov.infra.rest.support.MultiReadRequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

public class ApplicationTenantResolverFilter implements Filter {

    @Autowired
    private EnvironmentSettings environmentSettings;

    @Resource(name = "cities")
    private transient List<String> cities;

    public static Map<String, String> tenants = new HashMap<>();

    public static String stateUrl;

    @Autowired
    private ConfigurableEnvironment environment;

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationTenantResolverFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();

        String domainURL = extractRequestDomainURL((HttpServletRequest) request, false);
        String domainName = extractRequestedDomainName(domainURL);
        ApplicationThreadLocals.setTenantID(environmentSettings.schemaName(domainName));
        ApplicationThreadLocals.setDomainName(domainName);
        ApplicationThreadLocals.setDomainURL(domainURL);
        MultiReadRequestWrapper customRequest = prepareRestService(req, session);
        if (customRequest == null)
            chain.doFilter(request, response);
        else
            chain.doFilter(customRequest, response);
    }

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        // Nothing to be initialized
    }

    @Override
    public void destroy() {
        // Nothing to be cleaned up
    }

    private MultiReadRequestWrapper prepareRestService(HttpServletRequest req, HttpSession session) {
        MultiReadRequestWrapper customRequest = null;
        if (tenants == null || tenants.isEmpty()) {
            tenantsMap();
        }
        // restricted only the state URL to access the rest API
        if (req.getRequestURL().toString().contains(tenants.get("state"))
                && (req.getRequestURL().toString().contains("/rest/") || req.getRequestURL().toString().contains("/oauth/"))) {
            String tenantFromBody = StringUtils.EMPTY;
            customRequest = setCustomHeader(req, tenantFromBody);
            String fullTenant = req.getParameter("tenantId");
            if (StringUtils.isBlank(fullTenant)) {
                fullTenant = tenantFromBody;
            }
            if (StringUtils.isBlank(fullTenant)) {
                throw new RuntimeException("RestUrl does not contain tenantId");
            }
            String tenant = fullTenant.substring(fullTenant.lastIndexOf('.') + 1, fullTenant.length());
            LOG.info("tenant=" + tenant);
            LOG.info("City Code" + (String) session.getAttribute(CITY_CODE_KEY));
            boolean found = false;
            if (tenant.equalsIgnoreCase("generic")) {
                ApplicationThreadLocals.setTenantID(tenant);
                found = true;
            } else {
                for (String city : tenants.keySet()) {
                    LOG.info("Key :" + city + " ,Value :" + tenants.get(city) + "request tenant" + tenant);

                    if (tenants.get(city).contains(tenant)) {
                        ApplicationThreadLocals.setTenantID(city);
                        found = true;
                        break;
                    }
                }
            }
            if (!found) {
                throw new RuntimeException("Invalid tenantId");
            }

        }
        return customRequest;
    }

    public Map<String, String> tenantsMap() {
        URL url;
        try {
            url = new URL(ApplicationThreadLocals.getDomainURL());
            environment.getPropertySources().iterator().forEachRemaining(propertySource -> {
                if (propertySource instanceof MapPropertySource)
                    ((MapPropertySource) propertySource).getSource().forEach((key, value) -> {
                        if (key.startsWith("tenant."))
                            tenants.put(value.toString(), url.getProtocol() + "://" + key.replace("tenant.", "")
                                    + (url.getPort() != 80 ? ":" + url.getPort() : "") + "/");
                    });
            });
        } catch (MalformedURLException e) {
            LOG.error("Error occurred, while forming URL", e);
        }
        return tenants;
    }

    private MultiReadRequestWrapper setCustomHeader(HttpServletRequest request, String tenantAtBody) {
        MultiReadRequestWrapper multiReadRequestWrapper = new MultiReadRequestWrapper(request);
        if (request.getRequestURL().toString().contains("/rest/")) {
            try {
                StringWriter writer = new StringWriter();
                IOUtils.copy(multiReadRequestWrapper.getInputStream(), writer, StandardCharsets.UTF_8);
                String reqBody = String.valueOf(writer);
                if (StringUtils.isNoneBlank(reqBody)) {
                    Pattern p = Pattern.compile("\\{.*?\\}");
                    Matcher m = p.matcher(reqBody);
                    while (m.find()) {
                        CharSequence charSequence = m.group().subSequence(1, m.group().length() - 1);
                        String[] reqBodyParams = String.valueOf(charSequence).split(",");
                        for (String param : reqBodyParams) {
                            if (param.contains("tenantId")) {
                                String[] tenant = param.split(":");
                                if (tenant[1].startsWith("\"") && tenant[1].endsWith("\""))
                                    tenantAtBody = tenant[1].substring(1, tenant[1].length() - 1);
                                else
                                    tenantAtBody = tenant[1];
                            } else if (param.contains("authToken")) {
                                String[] authTokenVal = param.split(":");
                                // Next to 'bearer' word space is required to differentiate token type and access token
                                String tokenType = "bearer ";
                                if (authTokenVal[1].startsWith("\"") && authTokenVal[1].endsWith("\"")) {
                                    String authToken = authTokenVal[1].substring(1, authTokenVal[1].length() - 1);
                                    multiReadRequestWrapper.putHeader("Authorization", tokenType + authToken);
                                } else {
                                    multiReadRequestWrapper.putHeader("Authorization", tokenType + authTokenVal[1]);
                                }
                            }
                        }
                    }
                }

            } catch (IOException e) {
                LOG.error("Error occurred, while parsing request body into json", e);
            }

        }
        return multiReadRequestWrapper;
    }

}
