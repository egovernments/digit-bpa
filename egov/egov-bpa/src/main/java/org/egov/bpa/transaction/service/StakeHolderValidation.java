package org.egov.bpa.transaction.service;

import static org.egov.bpa.utils.BpaConstants.BUILDINGHEIGHT_GROUND;
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
import java.util.Map;

import org.egov.bpa.utils.BpaConstants;
import org.springframework.stereotype.Service;
@Service
public class StakeHolderValidation {

	public boolean validateStakeholder(final String serviceType, final String type, final BigDecimal extentInArea,
			final Integer floorCount, final BigDecimal buildingHeight, final BigDecimal totalPlinthArea,
			final Boolean isAuthorizedToSubmitPlan) {
		if (BpaConstants.ST_CODE_08.equalsIgnoreCase(serviceType)
				|| BpaConstants.ST_CODE_09.equalsIgnoreCase(serviceType)) {
			// For service type of Amenities and Permission for Temporary hut or
			// shed any registered business user can apply and no validations.
			return true;
		} else if (getStakeholderType1Restrictions().containsKey(type.toLowerCase())
				&& BpaConstants.getServicesForDevelopPermit().contains(serviceType) && !isAuthorizedToSubmitPlan) {
			return extentInArea
					.compareTo(getStakeholderType1Restrictions().get(type.toLowerCase()).get(EXTENTINSQMTS)) <= 0;
		} else if ((getStakeholderType7Restrictions().containsKey(type.toLowerCase())
				|| getStakeholderType8Restrictions().containsKey(type.toLowerCase()))
				&& BpaConstants.getServicesForBuildPermit().contains(serviceType)) {
			return false;
		} else if ((getStakeholderType1Restrictions().containsKey(type.toLowerCase())
				|| getStakeholderType6Restrictions().containsKey(type.toLowerCase()))
				&& BpaConstants.getServicesForBuildPermit().contains(serviceType)) {
			return true;
		} else if (getStakeholderType2Restrictions().containsKey(type.toLowerCase())
				&& BpaConstants.getServicesForDevelopPermit().contains(serviceType) && !isAuthorizedToSubmitPlan) {
			return extentInArea
					.compareTo(getStakeholderType2Restrictions().get(type.toLowerCase()).get(EXTENTINSQMTS)) <= 0;
		} else if (getStakeholderType2Restrictions().containsKey(type.toLowerCase())
				&& BpaConstants.getServicesForBuildPermit().contains(serviceType)) {
			Map<String, BigDecimal> stakeHolderType2Restriction = getStakeholderType2Restrictions()
					.get(type.toLowerCase());
			BigDecimal plinthAreaInput = stakeHolderType2Restriction.get(TOTAL_PLINT_AREA);
			BigDecimal floorCountInput = stakeHolderType2Restriction.get(FLOOR_COUNT);
			BigDecimal buildingHeightInput = stakeHolderType2Restriction.get(BUILDINGHEIGHT_GROUND);
			return totalPlinthArea.compareTo(plinthAreaInput) <= 0 && buildingHeight.compareTo(buildingHeightInput) <= 0
					&& BigDecimal.valueOf(floorCount).compareTo(floorCountInput) <= 0;
		} else if (getStakeholderType3Restrictions().containsKey(type.toLowerCase())
				&& BpaConstants.getServicesForDevelopPermit().contains(serviceType) && !isAuthorizedToSubmitPlan) {
			return extentInArea
					.compareTo(getStakeholderType3Restrictions().get(type.toLowerCase()).get(EXTENTINSQMTS)) <= 0;
		} else if (getStakeholderType3Restrictions().containsKey(type.toLowerCase())
				&& BpaConstants.getServicesForBuildPermit().contains(serviceType)) {
			Map<String, BigDecimal> stakeHolderType3Restriction = getStakeholderType3Restrictions()
					.get(type.toLowerCase());
			BigDecimal plinthAreaInput = stakeHolderType3Restriction.get(TOTAL_PLINT_AREA);
			BigDecimal floorCountInput = stakeHolderType3Restriction.get(FLOOR_COUNT);
			BigDecimal buildingHeightInput = stakeHolderType3Restriction.get(BUILDINGHEIGHT_GROUND);
			return totalPlinthArea.compareTo(plinthAreaInput) <= 0 && buildingHeight.compareTo(buildingHeightInput) <= 0
					&& BigDecimal.valueOf(floorCount).compareTo(floorCountInput) <= 0;
		} else if (getStakeholderType4Restrictions().containsKey(type.toLowerCase())
				&& BpaConstants.getServicesForDevelopPermit().contains(serviceType) && !isAuthorizedToSubmitPlan) {
			return extentInArea
					.compareTo(getStakeholderType4Restrictions().get(type.toLowerCase()).get(EXTENTINSQMTS)) <= 0;
		} else if (getStakeholderType4Restrictions().containsKey(type.toLowerCase())
				&& BpaConstants.getServicesForBuildPermit().contains(serviceType)) {
			Map<String, BigDecimal> stakeHolderType4Restriction = getStakeholderType4Restrictions()
					.get(type.toLowerCase());
			BigDecimal plinthAreaInput = stakeHolderType4Restriction.get(TOTAL_PLINT_AREA);
			BigDecimal floorCountInput = stakeHolderType4Restriction.get(FLOOR_COUNT);
			BigDecimal buildingHeightInput = stakeHolderType4Restriction.get(BUILDINGHEIGHT_GROUND);
			return totalPlinthArea.compareTo(plinthAreaInput) <= 0 && buildingHeight.compareTo(buildingHeightInput) <= 0
					&& BigDecimal.valueOf(floorCount).compareTo(floorCountInput) <= 0;
		} else if (getStakeholderType5Restrictions().containsKey(type.toLowerCase())
				&& BpaConstants.getServicesForDevelopPermit().contains(serviceType) && !isAuthorizedToSubmitPlan) {
			return extentInArea
					.compareTo(getStakeholderType5Restrictions().get(type.toLowerCase()).get(EXTENTINSQMTS)) <= 0;
		} else if (getStakeholderType5Restrictions().containsKey(type.toLowerCase())
				&& BpaConstants.getServicesForBuildPermit().contains(serviceType)) {
			Map<String, BigDecimal> stakeHolderType5Restriction = getStakeholderType5Restrictions()
					.get(type.toLowerCase());
			BigDecimal plinthAreaInput = stakeHolderType5Restriction.get(TOTAL_PLINT_AREA);
			BigDecimal floorCountInput = stakeHolderType5Restriction.get(FLOOR_COUNT);
			return totalPlinthArea.compareTo(plinthAreaInput) <= 0
					&& BigDecimal.valueOf(floorCount).compareTo(floorCountInput) <= 0;
		} else if (getStakeholderType6Restrictions().containsKey(type.toLowerCase())
				&& BpaConstants.getServicesForDevelopPermit().contains(serviceType) && !isAuthorizedToSubmitPlan) {
			return extentInArea
					.compareTo(getStakeholderType6Restrictions().get(type.toLowerCase()).get(EXTENTINSQMTS)) <= 0;
		} else if (getStakeholderType7Restrictions().containsKey(type.toLowerCase())
				&& BpaConstants.getServicesForDevelopPermit().contains(serviceType)) {
			return true;
		} else if (getStakeholderType8Restrictions().containsKey(type.toLowerCase())
				&& BpaConstants.getServicesForDevelopPermit().contains(serviceType) && !isAuthorizedToSubmitPlan) {
			return extentInArea
					.compareTo(getStakeholderType8Restrictions().get(type.toLowerCase()).get(EXTENTINSQMTS)) <= 0;
		}
		return true;
	}

}
