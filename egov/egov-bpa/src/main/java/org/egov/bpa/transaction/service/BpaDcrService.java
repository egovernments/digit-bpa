/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2017>  eGovernments Foundation
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
 */

package org.egov.bpa.transaction.service;

import static org.egov.bpa.utils.BpaConstants.DCR_BPA_INTEGRATION_REQUIRE;
import static org.egov.bpa.utils.BpaConstants.EGMODULE_NAME;
import static org.egov.bpa.utils.BpaConstants.YES;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.egov.bpa.master.entity.ServiceType;
import org.egov.bpa.master.service.ServiceTypeService;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.utils.BpaConstants;
import org.egov.infra.admin.master.entity.AppConfigValues;
import org.egov.infra.admin.master.service.AppConfigValueService;
import org.egov.infra.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class BpaDcrService {

    @Autowired
    private AppConfigValueService appConfigValueService;
    @Autowired
    private ApplicationBpaService applicationBpaService;
    @Autowired
    @Qualifier("parentMessageSource")
    private MessageSource bpaMessageSource;
    @Autowired
    private SecurityUtils securityUtils;
    @Autowired
    private ServiceTypeService serviceTypeService;

    public Map<String, String> checkIsEdcrUsedInBpaApplication(final String eDcrNumber) {
        Map<String, String> eDcrApplicationDetails = new HashMap<>();
        List<BpaApplication> bpaApplications = applicationBpaService.findApplicationByEDCRNumber(eDcrNumber);
        if (bpaApplications.isEmpty() || !bpaApplications.isEmpty() && null == bpaApplications.get(0).getState()
                && BpaConstants.APPLICATION_STATUS_CANCELLED.equals(bpaApplications.get(0).getStatus().getCode())) {
            eDcrApplicationDetails.put("isExists", "false");
            eDcrApplicationDetails.put(BpaConstants.MESSAGE, "Not used");
        } else {
            String message = bpaMessageSource.getMessage("msg.dcr.exist.with.appln",
                    new String[] { securityUtils.getCurrentUser().getName(), bpaApplications.get(0).geteDcrNumber(),
                            bpaApplications.get(0).getApplicationNumber() },
                    null);
            eDcrApplicationDetails.put("isExists", "true");
            eDcrApplicationDetails.put("applnNoUsedEdcr", bpaApplications.get(0).getApplicationNumber());
            eDcrApplicationDetails.put(BpaConstants.MESSAGE, message);
        }
        return eDcrApplicationDetails;
    }

    public boolean isDcrIntegrationRequiredWithApplication() {
        List<AppConfigValues> appConfigValueList = appConfigValueService
                .getConfigValuesByModuleAndKey(EGMODULE_NAME, DCR_BPA_INTEGRATION_REQUIRE);
        return appConfigValueList.get(0).getValue().equalsIgnoreCase(YES);
    }

    public boolean isEdcrIntegrationRequireByService(final String serviceType) {
        boolean isRequire = false;
        if (isDcrIntegrationRequiredWithApplication()) {
            ServiceType type = serviceTypeService.getServiceTypeByCode(serviceType);
            if (type.getIsEdcrMandatory())
                isRequire = true;
        }
        return isRequire;
    }
}
