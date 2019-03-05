/*
 * eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 * accountability and the service delivery of the government  organizations.
 *
 *  Copyright (C) <2018>  eGovernments Foundation
 *
 *  The updated version of eGov suite of products as by eGovernments Foundation
 *  is available at http://www.egovernments.org
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see http://www.gnu.org/licenses/ or
 *  http://www.gnu.org/licenses/gpl.html .
 *
 *  In addition to the terms of the GPL license to be adhered to in using this
 *  program, the following additional terms are to be complied with:
 *
 *      1) All versions of this program, verbatim or modified must carry this
 *         Legal Notice.
 *      Further, all user interfaces, including but not limited to citizen facing interfaces,
 *         Urban Local Bodies interfaces, dashboards, mobile applications, of the program and any
 *         derived works should carry eGovernments Foundation logo on the top right corner.
 *
 *      For the logo, please refer http://egovernments.org/html/logo/egov_logo.png.
 *      For any further queries on attribution, including queries on brand guidelines,
 *         please contact contact@egovernments.org
 *
 *      2) Any misrepresentation of the origin of the material is prohibited. It
 *         is required that all modified versions of this material be marked in
 *         reasonable ways as different from the original version.
 *
 *      3) This license does not grant any rights to any user of the program
 *         with regards to rights under trademark law for use of the trade names
 *         or trademarks of eGovernments Foundation.
 *
 *  In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */

package org.egov.bpa.utils;

import static org.egov.bpa.utils.BpaConstants.APPLICATION_MODULE_TYPE;
import static org.egov.bpa.utils.BpaConstants.YES;
import static org.egov.bpa.utils.OcConstants.OC_APPLN_FEE_COLLECTION_REQUIRED;
import static org.egov.bpa.utils.OcConstants.OC_DOC_SCRUTINY_INTEGRATION_REQUIRED;
import static org.egov.bpa.utils.OcConstants.OC_INSPECTION_SCHEDULE_INTEGRATION_REQUIRED;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.egov.bpa.transaction.entity.oc.OccupancyCertificate;
import org.egov.bpa.transaction.service.oc.OccupancyCertificateService;
import org.egov.infra.admin.master.entity.AppConfigValues;
import org.egov.infra.admin.master.service.AppConfigValueService;
import org.egov.infra.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class OccupancyCertificateUtils {

    @Autowired
    private AppConfigValueService appConfigValueService;
    @Autowired
    private OccupancyCertificateService occupancyCertificateService;
    @Autowired
    @Qualifier("parentMessageSource")
    private MessageSource bpaMessageSource;
    @Autowired
    private SecurityUtils securityUtils;

    public String getAppConfigValueByKeyName(String code) {
        List<AppConfigValues> appConfigValueList = appConfigValueService
                .getConfigValuesByModuleAndKey(APPLICATION_MODULE_TYPE, code);
        return appConfigValueList.isEmpty() ? "" : appConfigValueList.get(0).getValue();
    }

    public Boolean isApplicationFeeCollectionRequired() {
        return getAppConfigValueByKeyName(OC_APPLN_FEE_COLLECTION_REQUIRED).equals(YES);
    }

    public boolean isDocScrutinyIntegrationRequiredForOc() {
        return getAppConfigValueByKeyName(OC_DOC_SCRUTINY_INTEGRATION_REQUIRED).equals(YES);
    }

    public boolean isOCInspectionSchedulingIntegrationRequired() {
        return getAppConfigValueByKeyName(OC_INSPECTION_SCHEDULE_INTEGRATION_REQUIRED).equalsIgnoreCase(YES);
    }
    
    public Map<String, String> checkIsPermitNumberUsedWithAnyOCApplication(final String permitNumber) {
        Map<String, String> ocApplicationDetails = new HashMap<>();
        List<OccupancyCertificate> occupancyCertificates = occupancyCertificateService.findByPermitNumber(permitNumber);
        OccupancyCertificate occupancyCertificate = occupancyCertificates.isEmpty() ? null:occupancyCertificates.get(0);
        if (occupancyCertificate==null || 
        		(occupancyCertificate != null && occupancyCertificate.getStatus().getCode().equalsIgnoreCase("Cancelled"))) {
        	ocApplicationDetails.put("isExists", "false");
        	ocApplicationDetails.put(BpaConstants.MESSAGE, "Not used");
        } else {
            String message = bpaMessageSource.getMessage("msg.oc.exist.with.appln",
                    new String[] { securityUtils.getCurrentUser().getName(), permitNumber,
                            occupancyCertificate.getApplicationNumber() },
                    null);
            ocApplicationDetails.put("isExists", "true");
            ocApplicationDetails.put("applnNoUsedEdcr", occupancyCertificate.getApplicationNumber());
            ocApplicationDetails.put(BpaConstants.MESSAGE, message);
        }
        return ocApplicationDetails;
    }
}
