/*
 * eGov suite of products aim to improve the internal efficiency,transparency
,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2015>  eGovernments Foundation
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
package org.egov.bpa.transaction.service.impl;

import static org.egov.bpa.utils.BpaConstants.BUILDINGHEIGHT_GROUND

;
import static org.egov.bpa.utils.BpaConstants.EXTENTINSQMTS;
import static org.egov.bpa.utils.BpaConstants.FLOOR_COUNT;
import static org.egov.bpa.utils.BpaConstants.TOTAL_PLINT_AREA;
import static org.egov.bpa.utils.BpaConstants.getStakeholderType1Restrictions;
import static org.egov.bpa.utils.BpaConstants.getStakeholderType2Restrictions;
import static org.egov.bpa.utils.BpaConstants.getStakeholderType3Restrictions;
import static org.egov.bpa.utils.BpaConstants.getStakeholderType4Restrictions;
import static org.egov.bpa.utils.BpaConstants.getStakeholderType5Restrictions;
import static org.egov.bpa.utils.BpaConstants.getStakeholderType6Restrictions;
import static org.egov.bpa.utils.BpaConstants.getStakeholderType7Restrictions;
import static org.egov.bpa.utils.BpaConstants.getStakeholderType8Restrictions;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.egov.bpa.utils.BpaConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
@Service
public class StakeholderValidation {
	
	@Autowired
	@Qualifier("parentMessageSource")
	private MessageSource bpaMessageSource;

	
	public Map<Boolean, String> validateStakeholder(final String serviceTypeCode, final String serviceTypeName, final String type, final BigDecimal extentInArea,
			final Integer floorCount, final BigDecimal buildingHeight, final BigDecimal totalPlinthArea,
			final Boolean isAuthorizedToSubmitPlan) {
		
		Map<Boolean,String> stakeholder = new HashMap<Boolean ,String>();
		
		if (BpaConstants.ST_CODE_08.equalsIgnoreCase(serviceTypeCode)
				|| BpaConstants.ST_CODE_09.equalsIgnoreCase(serviceTypeCode)) {
			// For service type of Amenities and Permission for Temporary hut or
			// shed any registered business user can apply and no validations.
			 stakeholder.put(true,getValidationMessageForBusinessResgistration(type, serviceTypeCode, serviceTypeName));
		
		} else if (getStakeholderType1Restrictions().containsKey(type.toLowerCase())
				&& BpaConstants.getServicesForDevelopPermit().contains(serviceTypeCode) && !isAuthorizedToSubmitPlan) {
			 stakeholder.put(extentInArea
					.compareTo(getStakeholderType1Restrictions().get(type.toLowerCase()).get(EXTENTINSQMTS)) <= 0,
					getValidationMessageForBusinessResgistration(type, serviceTypeCode, serviceTypeName));
		
		} else if ((getStakeholderType7Restrictions().containsKey(type.toLowerCase())
				|| getStakeholderType8Restrictions().containsKey(type.toLowerCase()))
				&& BpaConstants.getServicesForBuildPermit().contains(serviceTypeCode)) {
			 stakeholder.put(false,getValidationMessageForBusinessResgistration(type, serviceTypeCode, serviceTypeName));
		
		} else if ((getStakeholderType1Restrictions().containsKey(type.toLowerCase())
				|| getStakeholderType6Restrictions().containsKey(type.toLowerCase()))
				&& BpaConstants.getServicesForBuildPermit().contains(serviceTypeCode)) {
			 stakeholder.put(true,getValidationMessageForBusinessResgistration(type, serviceTypeCode, serviceTypeName));
		
		} else if (getStakeholderType2Restrictions().containsKey(type.toLowerCase())
				&& BpaConstants.getServicesForDevelopPermit().contains(serviceTypeCode) && !isAuthorizedToSubmitPlan) {
			 stakeholder.put(extentInArea
					.compareTo(getStakeholderType2Restrictions().get(type.toLowerCase()).get(EXTENTINSQMTS)) <= 0,
					getValidationMessageForBusinessResgistration(type, serviceTypeCode, serviceTypeName));
	
		} else if (getStakeholderType2Restrictions().containsKey(type.toLowerCase())
				&& BpaConstants.getServicesForBuildPermit().contains(serviceTypeCode)) {
			Map<String, BigDecimal> stakeHolderType2Restriction = getStakeholderType2Restrictions()
					.get(type.toLowerCase());
			BigDecimal plinthAreaInput = stakeHolderType2Restriction.get(TOTAL_PLINT_AREA);
			BigDecimal floorCountInput = stakeHolderType2Restriction.get(FLOOR_COUNT);
			BigDecimal buildingHeightInput = stakeHolderType2Restriction.get(BUILDINGHEIGHT_GROUND);
			stakeholder.put(totalPlinthArea.compareTo(plinthAreaInput) <= 0 && buildingHeight.compareTo(buildingHeightInput) <= 0
					&& BigDecimal.valueOf(floorCount).compareTo(floorCountInput) <= 0,
					getValidationMessageForBusinessResgistration(type, serviceTypeCode, serviceTypeName));
		
		} else if (getStakeholderType3Restrictions().containsKey(type.toLowerCase())
				&& BpaConstants.getServicesForDevelopPermit().contains(serviceTypeCode) && !isAuthorizedToSubmitPlan) {
			stakeholder.put(extentInArea
					.compareTo(getStakeholderType3Restrictions().get(type.toLowerCase()).get(EXTENTINSQMTS)) <= 0,
							getValidationMessageForBusinessResgistration(type, serviceTypeCode, serviceTypeName));
	
		} else if (getStakeholderType3Restrictions().containsKey(type.toLowerCase())
				&& BpaConstants.getServicesForBuildPermit().contains(serviceTypeCode)) {
			Map<String, BigDecimal> stakeHolderType3Restriction = getStakeholderType3Restrictions()
					.get(type.toLowerCase());
			BigDecimal plinthAreaInput = stakeHolderType3Restriction.get(TOTAL_PLINT_AREA);
			BigDecimal floorCountInput = stakeHolderType3Restriction.get(FLOOR_COUNT);
			BigDecimal buildingHeightInput = stakeHolderType3Restriction.get(BUILDINGHEIGHT_GROUND);
			stakeholder.put(totalPlinthArea.compareTo(plinthAreaInput) <= 0 && buildingHeight.compareTo(buildingHeightInput) <= 0
					&& BigDecimal.valueOf(floorCount).compareTo(floorCountInput) <= 0,
					getValidationMessageForBusinessResgistration(type, serviceTypeCode, serviceTypeName));
		
		} else if (getStakeholderType4Restrictions().containsKey(type.toLowerCase())
				&& BpaConstants.getServicesForDevelopPermit().contains(serviceTypeCode) && !isAuthorizedToSubmitPlan) {
			stakeholder.put(extentInArea
					.compareTo(getStakeholderType4Restrictions().get(type.toLowerCase()).get(EXTENTINSQMTS)) <= 0,
					getValidationMessageForBusinessResgistration(type, serviceTypeCode, serviceTypeName));
	
		} else if (getStakeholderType4Restrictions().containsKey(type.toLowerCase())
				&& BpaConstants.getServicesForBuildPermit().contains(serviceTypeCode)) {
			Map<String, BigDecimal> stakeHolderType4Restriction = getStakeholderType4Restrictions()
					.get(type.toLowerCase());
			BigDecimal plinthAreaInput = stakeHolderType4Restriction.get(TOTAL_PLINT_AREA);
			BigDecimal floorCountInput = stakeHolderType4Restriction.get(FLOOR_COUNT);
			BigDecimal buildingHeightInput = stakeHolderType4Restriction.get(BUILDINGHEIGHT_GROUND);
			stakeholder.put(totalPlinthArea.compareTo(plinthAreaInput) <= 0 && buildingHeight.compareTo(buildingHeightInput) <= 0
					&& BigDecimal.valueOf(floorCount).compareTo(floorCountInput) <= 0,
					getValidationMessageForBusinessResgistration(type, serviceTypeCode, serviceTypeName));
		
		} else if (getStakeholderType5Restrictions().containsKey(type.toLowerCase())
				&& BpaConstants.getServicesForDevelopPermit().contains(serviceTypeCode) && !isAuthorizedToSubmitPlan) {
			stakeholder.put(extentInArea
					.compareTo(getStakeholderType5Restrictions().get(type.toLowerCase()).get(EXTENTINSQMTS)) <= 0,
					getValidationMessageForBusinessResgistration(type, serviceTypeCode, serviceTypeName));
		
		} else if (getStakeholderType5Restrictions().containsKey(type.toLowerCase())
				&& BpaConstants.getServicesForBuildPermit().contains(serviceTypeCode)) {
			Map<String, BigDecimal> stakeHolderType5Restriction = getStakeholderType5Restrictions()
					.get(type.toLowerCase());
			BigDecimal plinthAreaInput = stakeHolderType5Restriction.get(TOTAL_PLINT_AREA);
			BigDecimal floorCountInput = stakeHolderType5Restriction.get(FLOOR_COUNT);
			stakeholder.put(totalPlinthArea.compareTo(plinthAreaInput) <= 0
					&& BigDecimal.valueOf(floorCount).compareTo(floorCountInput) <= 0,
					getValidationMessageForBusinessResgistration(type, serviceTypeCode, serviceTypeName));
		
		} else if (getStakeholderType6Restrictions().containsKey(type.toLowerCase())
				&& BpaConstants.getServicesForDevelopPermit().contains(serviceTypeCode) && !isAuthorizedToSubmitPlan) {
			stakeholder.put(extentInArea
					.compareTo(getStakeholderType6Restrictions().get(type.toLowerCase()).get(EXTENTINSQMTS)) <= 0,
					getValidationMessageForBusinessResgistration(type, serviceTypeCode, serviceTypeName));
		
		} else if (getStakeholderType7Restrictions().containsKey(type.toLowerCase())
				&& BpaConstants.getServicesForDevelopPermit().contains(serviceTypeCode)) {
			stakeholder.put(true,getValidationMessageForBusinessResgistration(type, serviceTypeCode, serviceTypeName));
		
		} else if (getStakeholderType8Restrictions().containsKey(type.toLowerCase())
				&& BpaConstants.getServicesForDevelopPermit().contains(serviceTypeCode) && !isAuthorizedToSubmitPlan) {
			stakeholder.put(extentInArea
					.compareTo(getStakeholderType8Restrictions().get(type.toLowerCase()).get(EXTENTINSQMTS)) <= 0,
					getValidationMessageForBusinessResgistration(type, serviceTypeCode, serviceTypeName));
		}
		return stakeholder;
	}

	
	
	public String getValidationMessageForBusinessResgistration(final String stakeHolderType, final String serviceTypeCode, final String serviceTypeName) {
        BigDecimal extentInSqmtsInput = BigDecimal.ZERO;
        BigDecimal plinthAreaInput = BigDecimal.ZERO;
        BigDecimal floorCountInput = BigDecimal.ZERO;
        BigDecimal buildingHeightInput = BigDecimal.ZERO;
        String message;

        if ((getStakeholderType1Restrictions().containsKey(stakeHolderType.toLowerCase())
                || getStakeholderType3Restrictions().containsKey(stakeHolderType.toLowerCase())
                || getStakeholderType4Restrictions().containsKey(stakeHolderType.toLowerCase())
                || getStakeholderType5Restrictions().containsKey(stakeHolderType.toLowerCase())
                || getStakeholderType8Restrictions().containsKey(stakeHolderType.toLowerCase()))
                && BpaConstants.getServicesForDevelopPermit().contains(serviceTypeCode)) {
            Map<String, BigDecimal> stakeHolderType1Restriction = getStakeholderType1Restrictions()
                    .get(stakeHolderType.toLowerCase());
            extentInSqmtsInput = stakeHolderType1Restriction.get(EXTENTINSQMTS);
        
        } else if ((getStakeholderType2Restrictions().containsKey(stakeHolderType.toLowerCase())
                || getStakeholderType6Restrictions().containsKey(stakeHolderType.toLowerCase()))
                && BpaConstants.getServicesForDevelopPermit().contains(serviceTypeCode)) {
            Map<String, BigDecimal> stakeHolderType2Restriction = getStakeholderType2Restrictions()
                    .get(stakeHolderType.toLowerCase());
            extentInSqmtsInput = stakeHolderType2Restriction.get(EXTENTINSQMTS);
      
        } else if (getStakeholderType2Restrictions().containsKey(stakeHolderType.toLowerCase())
                && BpaConstants.getServicesForBuildPermit().contains(serviceTypeCode)) {
            Map<String, BigDecimal> stakeHolderType2Restriction = getStakeholderType2Restrictions()
                    .get(stakeHolderType.toLowerCase());
            plinthAreaInput = stakeHolderType2Restriction.get(TOTAL_PLINT_AREA);
            floorCountInput = stakeHolderType2Restriction.get(FLOOR_COUNT);
            buildingHeightInput = stakeHolderType2Restriction.get(BUILDINGHEIGHT_GROUND);
        
        } else if (getStakeholderType3Restrictions().containsKey(stakeHolderType.toLowerCase())
                && BpaConstants.getServicesForBuildPermit().contains(serviceTypeCode)) {
            Map<String, BigDecimal> stakeHolderType3Restriction = getStakeholderType3Restrictions()
                    .get(stakeHolderType.toLowerCase());
            plinthAreaInput = stakeHolderType3Restriction.get(TOTAL_PLINT_AREA);
            floorCountInput = stakeHolderType3Restriction.get(FLOOR_COUNT);
            buildingHeightInput = stakeHolderType3Restriction.get(BUILDINGHEIGHT_GROUND);
        
        } else if (getStakeholderType4Restrictions().containsKey(stakeHolderType.toLowerCase())
                && BpaConstants.getServicesForBuildPermit().contains(serviceTypeCode)) {
            Map<String, BigDecimal> stakeHolderType4Restriction = getStakeholderType4Restrictions()
                    .get(stakeHolderType.toLowerCase());
            plinthAreaInput = stakeHolderType4Restriction.get(TOTAL_PLINT_AREA);
            floorCountInput = stakeHolderType4Restriction.get(FLOOR_COUNT);
            buildingHeightInput = stakeHolderType4Restriction.get(BUILDINGHEIGHT_GROUND);
        
        } else if (getStakeholderType5Restrictions().containsKey(stakeHolderType.toLowerCase())
                && BpaConstants.getServicesForBuildPermit().contains(serviceTypeCode)) {
            Map<String, BigDecimal> stakeHolderType5Restriction = getStakeholderType5Restrictions()
                    .get(stakeHolderType.toLowerCase());
            plinthAreaInput = stakeHolderType5Restriction.get(TOTAL_PLINT_AREA);
            floorCountInput = stakeHolderType5Restriction.get(FLOOR_COUNT);
        }
        if (getStakeholderType7Restrictions().containsKey(stakeHolderType.toLowerCase())
                || getStakeholderType8Restrictions().containsKey(stakeHolderType.toLowerCase())
                        && BpaConstants.getServicesForBuildPermit().contains(serviceTypeCode)) {
            message = bpaMessageSource.getMessage("msg.invalid.stakeholder3", new String[] { stakeHolderType },
                    LocaleContextHolder.getLocale());
        } else if (!extentInSqmtsInput.equals(BigDecimal.ZERO) && plinthAreaInput.equals(BigDecimal.ZERO)) {
            message = bpaMessageSource.getMessage("msg.invalid.stakeholder2",
                    new String[] { stakeHolderType, extentInSqmtsInput.toString(),
                    		serviceTypeName },
                    LocaleContextHolder.getLocale());
        } else if (BpaConstants.getStakeholderType5Restrictions().containsKey(stakeHolderType.toLowerCase())) {
            message = bpaMessageSource.getMessage("msg.invalid.stakeholder4",
                    new String[] { stakeHolderType, extentInSqmtsInput.toString(), plinthAreaInput.toString(),
                            floorCountInput.toString(), serviceTypeName },
                    LocaleContextHolder.getLocale());
        } else {
            message = bpaMessageSource.getMessage("msg.invalid.stakeholder1",
                    new String[] { stakeHolderType, plinthAreaInput.toString(),
                            floorCountInput.toString(), buildingHeightInput.toString(),
                            serviceTypeName },
                    LocaleContextHolder.getLocale());
        }
        return message;
    }	
}
