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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.egov.infra.config.core.ApplicationThreadLocals;
import org.egov.infra.config.core.EnvironmentSettings;
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
		prepareRestService(req, session);
		chain.doFilter(request, response);
	}

	@Override
	public void init(final FilterConfig filterConfig) throws ServletException {
		// Nothing to be initialized
	}

	@Override
	public void destroy() {
		// Nothing to be cleaned up
	}

	private void prepareRestService(HttpServletRequest req, HttpSession session) {

		if (tenants == null || tenants.isEmpty()) {
			tenantsMap();
		}
		if (req.getRequestURL().toString().contains(tenants.get("state"))
				&& (req.getRequestURL().toString().contains("/rest/") || req.getRequestURL().toString().contains("/oauth/")) ) {

			String fullTenant = req.getParameter("tenantId");
			if (fullTenant == null) {
			  
			}
			if(fullTenant==null)
			{
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tenants;
	}

}
