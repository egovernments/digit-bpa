/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2019>  eGovernments Foundation
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

package org.egov.bpa.utils;

import static org.egov.bpa.utils.BpaConstants.APPLICATION_MODULE_TYPE;
import static org.egov.bpa.utils.BpaConstants.YES;

import java.math.BigDecimal;
import java.util.List;

import org.egov.infra.admin.master.entity.AppConfigValues;
import org.egov.infra.admin.master.service.AppConfigValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author vinoth
 *
 */
@Service
@Transactional(readOnly = true)
public class BpaAppConfigUtil {

    private static final String PR_APPLY_PRIOR_DAYS_BEFORE_EXPIRY = "PERMIT_RENEWAL_APPLY_PRIOR_DAYS_BEFORE_EXPIRY";
    private static final String PR_ALLOWED_UPTO_AFTER_EXPIRY = "PERMIT_RENEWAL_ALLOWED_UPTO_AFTER_EXPIRY";
    private static final String PERMIT_EXTENSION_FEE = "PERMIT_EXTENSION_FEE_IN_PERCENTAGE";
    private static final String PERMIT_RENEWAL_FEE = "PERMIT_RENEWAL_FEE_IN_PERCENTAGE";
    private static final String SLA_BPA_APPLICATION = "SLA_BPA_APPLICATION";
    private static final String SLA_PERMIT_RENEWAL_APPLICATION = "SLA_PERMIT_RENEWAL_APPLICATION";
    private static final String SLA_OC_APPLICATION = "SLA_OC_APPLICATION";
    private static final String SLA_OWNERSHIP_TRANSFER = "SLA_OWNERSHIP_TRANSFER";
    public static final String AUTOGENERATE_OWNERSHIP_NUMBER = "AUTOGENERATE_OWNERSHIP_NUMBER";
    public static final String OWNERSHIP_APPLN_FEE_REQUIRED = "OWNERSHIPAPPLNFEECOLLECTIONREQUIRED";
    public static final String OWNERSHIP_FEE_REQUIRED = "OWNERSHIPFEECOLLECTIONREQUIRED";
   
    
    @Autowired
    private AppConfigValueService appConfigValueService;

    public String getAppconfigValueByKeyName(String code) {
        List<AppConfigValues> appConfigValueList = appConfigValueService
                .getConfigValuesByModuleAndKey(APPLICATION_MODULE_TYPE, code);
        return appConfigValueList.isEmpty() ? "" : appConfigValueList.get(0).getValue();
    }

    public Integer getDaysPriorPermitRenewalCanApply() {
        return Integer.valueOf(getAppconfigValueByKeyName(PR_APPLY_PRIOR_DAYS_BEFORE_EXPIRY));
    }

    public Integer getDaysMaxAllowedAfterPermitRenewalExpired() {
        return Integer.valueOf(getAppconfigValueByKeyName(PR_ALLOWED_UPTO_AFTER_EXPIRY));
    }
    
    public BigDecimal getPermitExtensionFeeInPercentage() {
        return BigDecimal.valueOf(Double.valueOf(getAppconfigValueByKeyName(PERMIT_EXTENSION_FEE)));
    }
    
    public BigDecimal getPermitRenewalFeeInPercentage() {
        return BigDecimal.valueOf(Double.valueOf(getAppconfigValueByKeyName(PERMIT_RENEWAL_FEE)));
    }
    
    public Integer getSlaBpaApplication(){
        return Integer.valueOf(getAppconfigValueByKeyName(SLA_BPA_APPLICATION));
    }
    public Integer getSlaPermitRenewalApplication(){
        return Integer.valueOf(getAppconfigValueByKeyName(SLA_PERMIT_RENEWAL_APPLICATION));
    }
    public Integer getSlaOcApplication(){
        return Integer.valueOf(getAppconfigValueByKeyName(SLA_OC_APPLICATION));
    }
    public Integer getSlaOwnershipTransfer(){
        return Integer.valueOf(getAppconfigValueByKeyName(SLA_OWNERSHIP_TRANSFER));
    }
    public boolean autogenerateOwnershipNumber() {
        return getAppconfigValueByKeyName(AUTOGENERATE_OWNERSHIP_NUMBER).equalsIgnoreCase(YES);
    }    
    public Boolean ownershipApplicationFeeCollectionRequired() {
        return getAppconfigValueByKeyName(OWNERSHIP_APPLN_FEE_REQUIRED).equalsIgnoreCase(YES);
    }    
    public Boolean ownershipFeeCollectionRequired() {
        return getAppconfigValueByKeyName(OWNERSHIP_FEE_REQUIRED).equalsIgnoreCase(YES);
    }   
}