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
import static org.egov.bpa.utils.BpaConstants.MESSAGE;
import static org.egov.bpa.utils.BpaConstants.RECENT_DCRRULE_AMENDMENTDAYS;
import static org.egov.bpa.utils.BpaConstants.YES;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.egov.bpa.master.entity.ServiceType;
import org.egov.bpa.master.service.ServiceTypeService;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.oc.OccupancyCertificate;
import org.egov.bpa.transaction.service.oc.OccupancyCertificateService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.bpa.utils.OccupancyCertificateUtils;
import org.egov.infra.admin.master.entity.AppConfigValues;
import org.egov.infra.admin.master.service.AppConfigValueService;
import org.egov.infra.security.utils.SecurityUtils;
import org.egov.infra.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class BpaDcrService {

    private static final String NOT_EXPIRED = "Not expired";
    private static final String IS_EXISTS = "isExists";
    private static final String TRUE = "true";
    private static final String FALSE = "false";
    private static final String IS_EXPIRED = "isExpired";
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
    private OccupancyCertificateUtils occupancyCertificateUtils;
    @Autowired
    private ServiceTypeService serviceTypeService;
    @Autowired
    private DcrRestService dcrRestService;
    @Autowired
    private OccupancyCertificateService occupancyCertificateService;

    public Map<String, String> checkIsEdcrUsedInBpaApplication(final String eDcrNumber) {
        Map<String, String> eDcrApplicationDetails = new HashMap<>();
        List<BpaApplication> bpaApplications = applicationBpaService.findApplicationByEDCRNumber(eDcrNumber);
        if (bpaApplications.isEmpty()) {
            eDcrApplicationDetails.put(IS_EXISTS, FALSE);
            eDcrApplicationDetails.put(BpaConstants.MESSAGE, "Not used");
		} else {
			if (bpaApplications.get(0)!=null && bpaApplications.get(0).getStatus()!=null && 
					BpaConstants.APPLICATION_STATUS_CANCELLED
					.equals(bpaApplications.get(0).getStatus().getCode())) {
				eDcrApplicationDetails.put(IS_EXISTS, FALSE);
				eDcrApplicationDetails.put(BpaConstants.MESSAGE, "Not used");
			} else {
				String message = bpaMessageSource.getMessage(
						"msg.dcr.exist.with.appln", new String[] { securityUtils.getCurrentUser().getName(),
								bpaApplications.get(0).geteDcrNumber(), bpaApplications.get(0).getApplicationNumber() },
						null);
				eDcrApplicationDetails.put(IS_EXISTS, TRUE);
				eDcrApplicationDetails.put("applnNoUsedEdcr", bpaApplications.get(0).getApplicationNumber());
				eDcrApplicationDetails.put(BpaConstants.MESSAGE, message);
			}
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
    
    /***
     * Validate the dcr number within the specified date range.Configuration value used to decide the validity. Based on number of
     * days configured, DCR plan will be compared.
     * @param eDcrNumber
     * @param request
     * @return
     */
    public Map<String, String> checkEdcrExpiry(final String eDcrNumber, HttpServletRequest request) {
        Map<String, String> eDcrExpiryDetails = new HashMap<>();
        List<AppConfigValues> appConfigValueList = appConfigValueService
                .getConfigValuesByModuleAndKey(EGMODULE_NAME, RECENT_DCRRULE_AMENDMENTDAYS);
        int expirydays = Integer.parseInt(appConfigValueList.get(0).getValue());
        Date dcrCreatedDate = dcrRestService.getDcrCreatedDate(eDcrNumber, request);

        eDcrExpiryDetails.put(IS_EXPIRED, FALSE);
        eDcrExpiryDetails.put(MESSAGE, NOT_EXPIRED);

        if (dcrCreatedDate != null) {
            int diffInDays = DateUtils.daysBetween(dcrCreatedDate, new Date());
            if (diffInDays <= expirydays) {
                eDcrExpiryDetails.put(IS_EXPIRED, FALSE);
                eDcrExpiryDetails.put(MESSAGE, NOT_EXPIRED);
            } else {
                String message = bpaMessageSource.getMessage("msg.dcr.expiry", new String[] {
                        securityUtils.getCurrentUser().getName(), eDcrNumber, Integer.toString(expirydays) }, null);
                eDcrExpiryDetails.put(IS_EXPIRED, TRUE);
                eDcrExpiryDetails.put(MESSAGE, message);
            }
        }
        return eDcrExpiryDetails;
    }
    
    public Map<String, String> checkIsEdcrUsedWithAnyOCApplication(final String eDcrNumber,HttpServletRequest request) {
        Map<String, String> eDcrApplicationDetails = new HashMap<>();
        List<OccupancyCertificate> occupancyCertificates = occupancyCertificateService.findByEdcrNumber(eDcrNumber);
        if(occupancyCertificates.isEmpty()){
        	 String planpermissionno=dcrRestService.getEdcrPlanPermissionNo(eDcrNumber,request);
        	 if(StringUtils.isNotBlank(planpermissionno)){
        		 eDcrApplicationDetails=occupancyCertificateUtils.checkIsPermitNumberUsedWithAnyOCApplication(planpermissionno);
        	 String message = bpaMessageSource.getMessage("msg.dcr.exist.with.appln",
                     new String[] { securityUtils.getCurrentUser().getName(), eDcrNumber,
                    		 eDcrApplicationDetails.get("applnNoUsedEdcr") },
                     null);
        	 eDcrApplicationDetails.put(BpaConstants.MESSAGE, message);
        	 return eDcrApplicationDetails;
        	 }
        	 eDcrApplicationDetails.put("isExists", "false");
             eDcrApplicationDetails.put(BpaConstants.MESSAGE, "Not used");
        }else if (!occupancyCertificates.isEmpty() && !isOCInProgress(occupancyCertificates.get(0))
                || BpaConstants.APPLICATION_STATUS_CANCELLED.equals(occupancyCertificates.get(0).getStatus().getCode())) {
            eDcrApplicationDetails.put("isExists", "false");
            eDcrApplicationDetails.put(BpaConstants.MESSAGE, "Not used");
        } else {
            String message = bpaMessageSource.getMessage("msg.dcr.exist.with.appln",
                    new String[] { securityUtils.getCurrentUser().getName(), occupancyCertificates.get(0).geteDcrNumber(),
                            occupancyCertificates.get(0).getApplicationNumber() },
                    null);
            eDcrApplicationDetails.put("isExists", "true");
            eDcrApplicationDetails.put("applnNoUsedEdcr", occupancyCertificates.get(0).getApplicationNumber());
            eDcrApplicationDetails.put(BpaConstants.MESSAGE, message);
        }
        return eDcrApplicationDetails;
    }
    
    public boolean isOCInProgress(OccupancyCertificate occupancyCertificate){
    	boolean inProgress = false;
    	if(occupancyCertificate.getState() == null 
    			&& (BpaConstants.APPLICATION_STATUS_SUBMITTED.equals(occupancyCertificate.getStatus().getCode())
    				|| BpaConstants.APPLICATION_STATUS_CREATED.equals(occupancyCertificate.getStatus().getCode())))
    		inProgress = true;
    	else if(occupancyCertificate.getState() != null && occupancyCertificate.getState().isInprogress())
    		inProgress = true;
    	return inProgress;
    }
    
}
