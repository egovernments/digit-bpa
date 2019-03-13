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

import static org.egov.bpa.utils.BpaConstants.BUILDINGHEIGHT_GROUND;
import static org.egov.bpa.utils.BpaConstants.EGMODULE_NAME;
import static org.egov.bpa.utils.BpaConstants.EXTENTINSQMTS;
import static org.egov.bpa.utils.BpaConstants.FLOOR_COUNT;
import static org.egov.bpa.utils.BpaConstants.SCALING_FACTOR;
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
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.egov.bpa.transaction.entity.ApplicationFloorDetail;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.BuildingDetail;
import org.egov.bpa.transaction.entity.oc.OCBuilding;
import org.egov.bpa.transaction.entity.oc.OCFloor;
import org.egov.bpa.transaction.entity.oc.OccupancyCertificate;
import org.egov.bpa.utils.BpaConstants;
import org.egov.bpa.utils.BpaUtils;
import org.egov.common.entity.bpa.SubOccupancy;
import org.egov.infra.admin.master.entity.AppConfigValues;
import org.egov.infra.admin.master.service.AppConfigValueService;
import org.egov.infra.custom.CustomImplProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

/**
 * @author vinoth
 *
 */

@Service
@Transactional(readOnly = true)
public class BpaApplicationValidationService {

    private static final String BUILDINGDETAILSVALIDATIONREQUIRED = "BUILDINGDETAILSVALIDATIONREQUIRED";
    private static final String MSG_VIOLATION_WO_ADDNL_FEE = "msg.violation.wo.addnl.fee";
    private static final String MSG_VIOLATION_WITH_ADDNL_FEE = "msg.violation.with.addnl.fee";
    private static final String MIXED = "Mixed";
    private static final String VIOLATION_MESSAGE = "violationMessage";
    private static final String TRUE = "true";
    private static final String FALSE = "false";
    private static final String VIOLATION_MESSAGE_FOR_COVERAGE = "violationMessageForCoverage";
    private static final String MAXIMUM_ALLOWED_AREA_WITH_ADDNL_FEE = "maximumAllowedAreaWithAddnlFee";
    private static final String MAXIMUM_ALLOWED_AREA_WO_ADDNL_FEE = "maximumAllowedAreaWOAddnlFee";
    private static final String IS_VIOLATING = "isViolating";
    @Autowired
    @Qualifier("parentMessageSource")
    private MessageSource bpaMessageSource;
    @Autowired
    private AppConfigValueService appConfigValuesService;
    @Autowired
    private BpaUtils bpaUtils;
    @Autowired
    private CustomImplProvider customImplProvider;

    /**
     * @param bpaApplication
     * @return
     */
    public boolean checkStakeholderIsValid(final BpaApplication bpaApplication) {
        Integer noOfFloors = 0;
        BigDecimal totalPlinthArea = BigDecimal.ZERO;
        BigDecimal heightOfBuilding = BigDecimal.ZERO;
        if (!bpaApplication.getBuildingDetail().isEmpty()) {
            for (Map.Entry<SubOccupancy, BigDecimal> builtupArea : bpaUtils
                    .getBlockWiseOccupancyAndBuiltupArea(bpaApplication.getBuildingDetail()).entrySet())
                totalPlinthArea = totalPlinthArea.add(builtupArea.getValue());
            BuildingDetail building = bpaUtils.getBuildingHasHighestHeight(bpaApplication.getBuildingDetail());
            noOfFloors = building.getFloorCount();
            heightOfBuilding = building.getHeightFromGroundWithOutStairRoom();
        }
        Map<String, String> cityDetails = customImplProvider.getCityDetails();

        StakeHolderValidation sValidator = (StakeHolderValidation) customImplProvider.find(StakeHolderValidation.class,
                cityDetails);
        if (sValidator == null) {
            throw new RuntimeException("Unable to find Stakeholder validator service");
        } else {
            return sValidator.validateStakeholder(bpaApplication.getServiceType().getCode(),
                    bpaApplication.getStakeHolder().get(0).getStakeHolder().getStakeHolderType().getName(),
                    bpaApplication.getSiteDetail().get(0).getExtentinsqmts(),
                    noOfFloors, heightOfBuilding, totalPlinthArea, bpaApplication.getAuthorizedToSubmitPlan());
        }
    }

    /**
     * Validation building licensee eligible criteria to submit application.
     * @param serviceType
     * @param type
     * @param extentInArea
     * @param floorCount
     * @param buildingHeight
     * @param totalPlinthArea
     * @return isEligible ? true :false;
     */
    private boolean validateStakeholder(final String serviceType, final String type, final BigDecimal extentInArea,
            final Integer floorCount,
            final BigDecimal buildingHeight, final BigDecimal totalPlinthArea, final Boolean isAuthorizedToSubmitPlan) {
        if (BpaConstants.ST_CODE_08.equalsIgnoreCase(serviceType)
                || BpaConstants.ST_CODE_09.equalsIgnoreCase(serviceType)) {
            // For service type of Amenities and Permission for Temporary hut or
            // shed any registered business user can apply and no validations.
            return true;
        } else if (getStakeholderType1Restrictions().containsKey(type.toLowerCase())
                && BpaConstants.getServicesForDevelopPermit().contains(serviceType) && !isAuthorizedToSubmitPlan) {
            return extentInArea.compareTo(getStakeholderType1Restrictions().get(type.toLowerCase()).get(EXTENTINSQMTS)) <= 0;
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
            return extentInArea.compareTo(getStakeholderType2Restrictions().get(type.toLowerCase()).get(EXTENTINSQMTS)) <= 0;
        } else if (getStakeholderType2Restrictions().containsKey(type.toLowerCase())
                && BpaConstants.getServicesForBuildPermit().contains(serviceType)) {
            Map<String, BigDecimal> stakeHolderType2Restriction = getStakeholderType2Restrictions()
                    .get(type.toLowerCase());
            BigDecimal plinthAreaInput = stakeHolderType2Restriction.get(TOTAL_PLINT_AREA);
            BigDecimal floorCountInput = stakeHolderType2Restriction.get(FLOOR_COUNT);
            BigDecimal buildingHeightInput = stakeHolderType2Restriction.get(BUILDINGHEIGHT_GROUND);
            return totalPlinthArea.compareTo(plinthAreaInput) <= 0
                    && buildingHeight.compareTo(buildingHeightInput) <= 0
                    && BigDecimal.valueOf(floorCount).compareTo(floorCountInput) <= 0;
        } else if (getStakeholderType3Restrictions().containsKey(type.toLowerCase())
                && BpaConstants.getServicesForDevelopPermit().contains(serviceType) && !isAuthorizedToSubmitPlan) {
            return extentInArea.compareTo(getStakeholderType3Restrictions().get(type.toLowerCase()).get(EXTENTINSQMTS)) <= 0;
        } else if (getStakeholderType3Restrictions().containsKey(type.toLowerCase())
                && BpaConstants.getServicesForBuildPermit().contains(serviceType)) {
            Map<String, BigDecimal> stakeHolderType3Restriction = getStakeholderType3Restrictions()
                    .get(type.toLowerCase());
            BigDecimal plinthAreaInput = stakeHolderType3Restriction.get(TOTAL_PLINT_AREA);
            BigDecimal floorCountInput = stakeHolderType3Restriction.get(FLOOR_COUNT);
            BigDecimal buildingHeightInput = stakeHolderType3Restriction.get(BUILDINGHEIGHT_GROUND);
            return totalPlinthArea.compareTo(plinthAreaInput) <= 0
                    && buildingHeight.compareTo(buildingHeightInput) <= 0
                    && BigDecimal.valueOf(floorCount).compareTo(floorCountInput) <= 0;
        } else if (getStakeholderType4Restrictions().containsKey(type.toLowerCase())
                && BpaConstants.getServicesForDevelopPermit().contains(serviceType) && !isAuthorizedToSubmitPlan) {
            return extentInArea.compareTo(getStakeholderType4Restrictions().get(type.toLowerCase()).get(EXTENTINSQMTS)) <= 0;
        } else if (getStakeholderType4Restrictions().containsKey(type.toLowerCase())
                && BpaConstants.getServicesForBuildPermit().contains(serviceType)) {
            Map<String, BigDecimal> stakeHolderType4Restriction = getStakeholderType4Restrictions()
                    .get(type.toLowerCase());
            BigDecimal plinthAreaInput = stakeHolderType4Restriction.get(TOTAL_PLINT_AREA);
            BigDecimal floorCountInput = stakeHolderType4Restriction.get(FLOOR_COUNT);
            BigDecimal buildingHeightInput = stakeHolderType4Restriction.get(BUILDINGHEIGHT_GROUND);
            return totalPlinthArea.compareTo(plinthAreaInput) <= 0
                    && buildingHeight.compareTo(buildingHeightInput) <= 0
                    && BigDecimal.valueOf(floorCount).compareTo(floorCountInput) <= 0;
        } else if (getStakeholderType5Restrictions().containsKey(type.toLowerCase())
                && BpaConstants.getServicesForDevelopPermit().contains(serviceType) && !isAuthorizedToSubmitPlan) {
            return extentInArea.compareTo(getStakeholderType5Restrictions().get(type.toLowerCase()).get(EXTENTINSQMTS)) <= 0;
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
            return extentInArea.compareTo(getStakeholderType6Restrictions().get(type.toLowerCase()).get(EXTENTINSQMTS)) <= 0;
        } else if (getStakeholderType7Restrictions().containsKey(type.toLowerCase())
                && BpaConstants.getServicesForDevelopPermit().contains(serviceType)) {
            return true;
        } else if (getStakeholderType8Restrictions().containsKey(type.toLowerCase())
                && BpaConstants.getServicesForDevelopPermit().contains(serviceType) && !isAuthorizedToSubmitPlan) {
            return extentInArea.compareTo(getStakeholderType8Restrictions().get(type.toLowerCase()).get(EXTENTINSQMTS)) <= 0;
        }
        return true;
    }

    /**
     * @param bpaApplication
     * @return error message if building licensee is not meeting eligible criteria.
     */
    public String getValidationMessageForBusinessResgistration(final BpaApplication bpaApplication) {
        String stakeHolderType = bpaApplication.getStakeHolder().get(0).getStakeHolder().getStakeHolderType()
                .getName();
        String serviceType = bpaApplication.getServiceType().getCode();
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
                && BpaConstants.getServicesForDevelopPermit().contains(serviceType)) {
            Map<String, BigDecimal> stakeHolderType1Restriction = getStakeholderType1Restrictions()
                    .get(stakeHolderType.toLowerCase());
            extentInSqmtsInput = stakeHolderType1Restriction.get(EXTENTINSQMTS);
        } else if ((getStakeholderType2Restrictions().containsKey(stakeHolderType.toLowerCase())
                || getStakeholderType6Restrictions().containsKey(stakeHolderType.toLowerCase()))
                && BpaConstants.getServicesForDevelopPermit().contains(serviceType)) {
            Map<String, BigDecimal> stakeHolderType2Restriction = getStakeholderType2Restrictions()
                    .get(stakeHolderType.toLowerCase());
            extentInSqmtsInput = stakeHolderType2Restriction.get(EXTENTINSQMTS);
        } else if (getStakeholderType2Restrictions().containsKey(stakeHolderType.toLowerCase())
                && BpaConstants.getServicesForBuildPermit().contains(serviceType)) {
            Map<String, BigDecimal> stakeHolderType2Restriction = getStakeholderType2Restrictions()
                    .get(stakeHolderType.toLowerCase());
            plinthAreaInput = stakeHolderType2Restriction.get(TOTAL_PLINT_AREA);
            floorCountInput = stakeHolderType2Restriction.get(FLOOR_COUNT);
            buildingHeightInput = stakeHolderType2Restriction.get(BUILDINGHEIGHT_GROUND);
        } else if (getStakeholderType3Restrictions().containsKey(stakeHolderType.toLowerCase())
                && BpaConstants.getServicesForBuildPermit().contains(serviceType)) {
            Map<String, BigDecimal> stakeHolderType3Restriction = getStakeholderType3Restrictions()
                    .get(stakeHolderType.toLowerCase());
            plinthAreaInput = stakeHolderType3Restriction.get(TOTAL_PLINT_AREA);
            floorCountInput = stakeHolderType3Restriction.get(FLOOR_COUNT);
            buildingHeightInput = stakeHolderType3Restriction.get(BUILDINGHEIGHT_GROUND);
        } else if (getStakeholderType4Restrictions().containsKey(stakeHolderType.toLowerCase())
                && BpaConstants.getServicesForBuildPermit().contains(serviceType)) {
            Map<String, BigDecimal> stakeHolderType4Restriction = getStakeholderType4Restrictions()
                    .get(stakeHolderType.toLowerCase());
            plinthAreaInput = stakeHolderType4Restriction.get(TOTAL_PLINT_AREA);
            floorCountInput = stakeHolderType4Restriction.get(FLOOR_COUNT);
            buildingHeightInput = stakeHolderType4Restriction.get(BUILDINGHEIGHT_GROUND);
        } else if (getStakeholderType5Restrictions().containsKey(stakeHolderType.toLowerCase())
                && BpaConstants.getServicesForBuildPermit().contains(serviceType)) {
            Map<String, BigDecimal> stakeHolderType5Restriction = getStakeholderType5Restrictions()
                    .get(stakeHolderType.toLowerCase());
            plinthAreaInput = stakeHolderType5Restriction.get(TOTAL_PLINT_AREA);
            floorCountInput = stakeHolderType5Restriction.get(FLOOR_COUNT);
        }
        if (getStakeholderType7Restrictions().containsKey(stakeHolderType.toLowerCase())
                || getStakeholderType8Restrictions().containsKey(stakeHolderType.toLowerCase())
                        && BpaConstants.getServicesForBuildPermit().contains(serviceType)) {
            message = bpaMessageSource.getMessage("msg.invalid.stakeholder3", new String[] { stakeHolderType },
                    LocaleContextHolder.getLocale());
        } else if (!extentInSqmtsInput.equals(BigDecimal.ZERO) && plinthAreaInput.equals(BigDecimal.ZERO)) {
            message = bpaMessageSource.getMessage("msg.invalid.stakeholder2",
                    new String[] { stakeHolderType, extentInSqmtsInput.toString(),
                            bpaApplication.getServiceType().getDescription() },
                    LocaleContextHolder.getLocale());
        } else if (BpaConstants.getStakeholderType5Restrictions().containsKey(stakeHolderType.toLowerCase())) {
            message = bpaMessageSource.getMessage("msg.invalid.stakeholder4",
                    new String[] { stakeHolderType, extentInSqmtsInput.toString(), plinthAreaInput.toString(),
                            floorCountInput.toString(), bpaApplication.getServiceType().getDescription() },
                    LocaleContextHolder.getLocale());
        } else {
            message = bpaMessageSource.getMessage("msg.invalid.stakeholder1",
                    new String[] { stakeHolderType, plinthAreaInput.toString(),
                            floorCountInput.toString(), buildingHeightInput.toString(),
                            bpaApplication.getServiceType().getDescription() },
                    LocaleContextHolder.getLocale());
        }
        return message;
    }

    /**
     * checking each floor wise coverage area is violating for all occupancy where ever building details capturing to those
     * services this validation is applicable
     * @param application
     * @return
     */
    public Map<String, String> checkIsViolatingCoverageArea(final BpaApplication application) {
        Map<String, BigDecimal[]> floorMap = new HashMap<>();
        for (ApplicationFloorDetail floorDetail : application.getBuildingDetail().get(0).getApplicationFloorDetails()) {
            String floorDesc = floorDetail.getFloorDescription().concat("-").concat(floorDetail.getFloorNumber().toString());
            if (floorMap
                    .containsKey(floorDesc)) {
                BigDecimal permissableFloorCoveredArea = floorMap.get(floorDesc)[0]
                        .add(floorDetail.getSubOccupancy().getMaxCoverage()
                                .multiply(floorDetail.getPlinthArea()));
                BigDecimal floorWiseArea = floorMap.get(floorDesc)[1].add(floorDetail.getPlinthArea());
                floorMap.put(floorDesc, new BigDecimal[] { permissableFloorCoveredArea, floorWiseArea });
            } else {
                BigDecimal permissableFloorCoveredArea = floorDetail.getSubOccupancy().getMaxCoverage()
                        .multiply(floorDetail.getPlinthArea());
                floorMap.put(floorDesc, new BigDecimal[] { permissableFloorCoveredArea, floorDetail.getPlinthArea() });
            }
        }
        Map<String, String> violationCoverage = new HashMap<>();
        StringBuilder floorDescBuilder = new StringBuilder();
        StringBuilder floorAreaBuilder = new StringBuilder();
        for (Entry<String, BigDecimal[]> floorDescSet : floorMap.entrySet()) {
            String floorDesc = floorDescSet.getKey();
            BigDecimal permissableCoveredArea = floorDescSet.getValue()[0];
            BigDecimal plinthArea = floorDescSet.getValue()[1];
            BigDecimal extentInSqmts = application.getSiteDetail().get(0).getExtentinsqmts();
            BigDecimal weightedCoverage = permissableCoveredArea.divide(plinthArea, BigDecimal.ROUND_HALF_UP);
            BigDecimal coverageProvided = plinthArea.divide(extentInSqmts, BigDecimal.ROUND_HALF_UP)
                    .multiply(new BigDecimal(100));
            BigDecimal permitedFloorArea = (weightedCoverage.multiply(extentInSqmts)
                    .divide(new BigDecimal(100))).setScale(SCALING_FACTOR, BigDecimal.ROUND_HALF_UP);

            if (plinthArea.setScale(SCALING_FACTOR, BigDecimal.ROUND_HALF_UP).compareTo(permitedFloorArea) > 0) {
                floorDescBuilder.append(floorDesc).append(", ");
                floorAreaBuilder.append(permitedFloorArea).append(", ");
            }
        }
        if (floorDescBuilder.length() > 0) {
            String message = bpaMessageSource.getMessage("msg.coverage.violate",
                    new String[] { floorDescBuilder.substring(0, floorDescBuilder.length() - 1),
                            floorAreaBuilder.substring(0, floorAreaBuilder.length() - 1) },
                    null);
            violationCoverage.put(VIOLATION_MESSAGE_FOR_COVERAGE, message);
            violationCoverage.put(IS_VIOLATING, TRUE);
        } else {
            violationCoverage.put(IS_VIOLATING, FALSE);
        }
        return violationCoverage;
    }

    /**
     * 
     * @param application
     * @param model
     * @return
     */
    public Boolean validateBuildingDetails(final BpaApplication application, final Model model) {
        /*
         * if (isBuildingFloorDetailsValidationRequired() &&
         * getServicesForValidation().contains(application.getServiceType().getCode())) { List<ApplicationFloorDetail>
         * deletedFloorDetails = new ArrayList<>(); for (ApplicationFloorDetail applicationFloorDetails :
         * application.getBuildingDetail().get(0) .getApplicationFloorDetails()) { if
         * (application.getBuildingDetail().get(0).getDeletedFloorIds() != null &&
         * application.getBuildingDetail().get(0).getDeletedFloorIds().length > 0 &&
         * Arrays.asList(application.getBuildingDetail().get(0).getDeletedFloorIds()) .contains(applicationFloorDetails.getId()))
         * { deletedFloorDetails.add(applicationFloorDetails); } }
         * application.getBuildingDetail().get(0).delete(deletedFloorDetails);
         * buildingFloorDetailsService.buildNewlyAddedFloorDetails(application); Map<String, String> violationCoverage =
         * checkIsViolatingCoverageArea(application); if (TRUE.equalsIgnoreCase(violationCoverage.get(IS_VIOLATING))) {
         * model.addAttribute(VIOLATION_MESSAGE, violationCoverage.get(VIOLATION_MESSAGE_FOR_COVERAGE)); return true; }
         * Map<String, String> violationWOAddnlFee = checkIsViolatingMaximumPermissableWOAddnlFee(application); Map<String,
         * String> violationWithAddnlFee = checkIsViolatingMaximumPermissableWithAddnlFee(application); if(new
         * BigDecimal(violationWithAddnlFee.get(MAXIMUM_ALLOWED_AREA_WITH_ADDNL_FEE)).compareTo(BigDecimal.ZERO) == 0) {
         * BigDecimal sumOfFloorArea = applicationBpaFeeCalculationService
         * .getOccupancyWiseFloorArea(application.getBuildingDetail()).entrySet().stream()
         * .map(Entry::getValue).reduce(BigDecimal.ZERO, BigDecimal::add); if(sumOfFloorArea.compareTo(new
         * BigDecimal(violationWOAddnlFee.get(MAXIMUM_ALLOWED_AREA_WO_ADDNL_FEE))) > 0) { String message =
         * bpaMessageSource.getMessage("msg.violation.max.with.wo.addnl.fee", new String[] {
         * application.getOccupancy().getDescription(), violationWOAddnlFee.get(MAXIMUM_ALLOWED_AREA_WO_ADDNL_FEE) }, null);
         * model.addAttribute(VIOLATION_MESSAGE, message); return true; } return false; } else if
         * (TRUE.equalsIgnoreCase(violationWOAddnlFee.get(IS_VIOLATING)) &&
         * !application.getBuildingDetail().get(0).getAdditionalFeePaymentAccepted()) { String message =
         * bpaMessageSource.getMessage(MSG_VIOLATION_WO_ADDNL_FEE, new String[] { application.getOccupancy().getDescription(),
         * violationWOAddnlFee.get(MAXIMUM_ALLOWED_AREA_WO_ADDNL_FEE) }, null); model.addAttribute(VIOLATION_MESSAGE, message);
         * return true; } else if (TRUE.equalsIgnoreCase(violationWithAddnlFee.get(IS_VIOLATING)) &&
         * application.getBuildingDetail().get(0).getAdditionalFeePaymentAccepted()) { String message =
         * bpaMessageSource.getMessage(MSG_VIOLATION_WITH_ADDNL_FEE, new String[] { application.getOccupancy().getDescription(),
         * violationWithAddnlFee.get(MAXIMUM_ALLOWED_AREA_WITH_ADDNL_FEE) }, null); model.addAttribute(VIOLATION_MESSAGE,
         * message); return true; } }
         */
        return false;
    }

    /**
     * checking total floor area is violating against maximum permissable area with out additional fee and where ever building
     * details capturing to those services this validation is applicable
     * @param application
     * @return isViolated and maximum allowed area with out additional fee
     */
    public Map<String, String> checkIsViolatingMaximumPermissableWOAddnlFee(final BpaApplication application) {
        Map<String, String> violation = new HashMap<>();
        /*
         * BigDecimal sumOfFloorArea = applicationBpaFeeCalculationService
         * .getOccupancyWiseFloorArea(application.getBuildingDetail().get(0)).entrySet().stream()
         * .map(Entry::getValue).reduce(BigDecimal.ZERO, BigDecimal::add); BigDecimal maximumAllowedAreaWOAddnlFee =
         * BigDecimal.ZERO; if (MIXED.equalsIgnoreCase(application.getOccupancy().getDescription())) { if
         * (application.getSiteDetail().get(0).getExtentinsqmts().compareTo(new BigDecimal(5000)) <= 0) {
         * maximumAllowedAreaWOAddnlFee = application.getSiteDetail().get(0).getExtentinsqmts()
         * .multiply(applicationBpaFeeCalculationService.minimumFARWithoutAdditionalFee(application)); } else if
         * (application.getSiteDetail().get(0).getExtentinsqmts().compareTo(new BigDecimal(5000)) > 0) {
         * maximumAllowedAreaWOAddnlFee = application.getSiteDetail().get(0).getExtentinsqmts()
         * .multiply(applicationBpaFeeCalculationService.weightageAverageFarWithoutAdditionalFee(
         * applicationBpaFeeCalculationService .getOccupancyWiseFloorArea(application.getBuildingDetail().get(0)))); } } else {
         * maximumAllowedAreaWOAddnlFee = application.getSiteDetail().get(0).getExtentinsqmts() .multiply(new
         * BigDecimal(application.getOccupancy().getNumOfTimesAreaPermissible())); } violation.put(IS_VIOLATING,
         * sumOfFloorArea.compareTo(maximumAllowedAreaWOAddnlFee) > 0 ? TRUE : FALSE);
         * violation.put(MAXIMUM_ALLOWED_AREA_WO_ADDNL_FEE, maximumAllowedAreaWOAddnlFee.setScale(SCALING_FACTOR,
         * BigDecimal.ROUND_HALF_UP).toString());
         */
        return violation;
    }

    /**
     * checking total floor area is violating against maximum permissable area with additional fee and is additional fee
     * applicable or not.
     * @param application
     * @return isViolated and maximum allowed area with additional fee
     */
    public Map<String, String> checkIsViolatingMaximumPermissableWithAddnlFee(final BpaApplication application) {
        Map<String, String> violation = new HashMap<>();
        /*
         * for(BuildingDetail building : application.getBuildingDetail()) { violation = new HashMap<>(); Map<Occupancy,
         * BigDecimal> floorArea = applicationBpaFeeCalculationService
         * .getOccupancyWiseFloorArea(application.getBuildingDetail()).get(building.getName()); BigDecimal sumOfFloorArea =
         * floorArea.entrySet().stream() .map(Entry::getValue).reduce(BigDecimal.ZERO, BigDecimal::add); BigDecimal
         * maximumAllowedAreaWithAddnlFee = BigDecimal.ZERO; if
         * (MIXED.equalsIgnoreCase(application.getOccupancy().getDescription())) { if
         * (application.getSiteDetail().get(0).getExtentinsqmts().compareTo(new BigDecimal(5000)) <= 0) {
         * maximumAllowedAreaWithAddnlFee = application.getSiteDetail().get(0).getExtentinsqmts()
         * .multiply(applicationBpaFeeCalculationService.minimumFARWithAdditionalFee(application)); } else if
         * (application.getSiteDetail().get(0).getExtentinsqmts().compareTo(new BigDecimal(5000)) > 0) {
         * maximumAllowedAreaWithAddnlFee = application.getSiteDetail().get(0).getExtentinsqmts()
         * .multiply(applicationBpaFeeCalculationService.weightageAverageFarWithAdditionalFee(floorArea)); } } else {
         * maximumAllowedAreaWithAddnlFee = application.getSiteDetail().get(0).getExtentinsqmts() .multiply(new
         * BigDecimal(application.getOccupancy().getNumOfTimesAreaPermWitAddnlFee())); } violation.put(IS_VIOLATING,
         * sumOfFloorArea.compareTo(maximumAllowedAreaWithAddnlFee) > 0 ? TRUE : FALSE);
         * violation.put(MAXIMUM_ALLOWED_AREA_WITH_ADDNL_FEE, maximumAllowedAreaWithAddnlFee.setScale(SCALING_FACTOR,
         * BigDecimal.ROUND_HALF_UP).toString()); }
         */

        return violation;
    }

    public Boolean isBuildingFloorDetailsValidationRequired() {
        return getAppConfigValueByPassingModuleAndType(EGMODULE_NAME, BUILDINGDETAILSVALIDATIONREQUIRED);
    }

    public Boolean getAppConfigValueByPassingModuleAndType(String moduleName, String keyName) {
        final List<AppConfigValues> appConfigValue = appConfigValuesService.getConfigValuesByModuleAndKey(moduleName,
                keyName);
        return "YES".equalsIgnoreCase(
                appConfigValue != null && !appConfigValue.isEmpty() ? appConfigValue.get(0).getValue() : "NO");
    }

    public Boolean validateOcApplnWithPermittedBpaAppln(final Model model, final OccupancyCertificate occupancyCertificate) {
        Boolean isValid = Boolean.FALSE;
        List<OCBuilding> ocBuildings = occupancyCertificate.getBuildings();
        List<BuildingDetail> bpaBuildingDetail = occupancyCertificate.getParent().getBuildingDetail();
        // check number of blocks same are not
        if (ocBuildings.size() > bpaBuildingDetail.size()) {
            model.addAttribute("OcComparisonValidation",
                    bpaMessageSource.getMessage("msg.oc.appln.comparison.validation1", null, null));
            return isValid;
        }

        // check building number exist in permitted building details.
        for (OCBuilding oc : ocBuildings) {
            boolean buildingExist = false;
            for (BuildingDetail bpa : bpaBuildingDetail) {
                if (oc.getBuildingNumber() == bpa.getNumber())
                    buildingExist = true;
            }
            if (!buildingExist) {
                model.addAttribute("OcComparisonValidation", bpaMessageSource.getMessage("msg.oc.appln.comparison.validation2",
                        new String[] { oc.getBuildingNumber().toString() }, null));
                return isValid;
            }
        }
        // check the floor count for particular building same or not
        for (OCBuilding oc : ocBuildings) {
            Integer size = oc.getFloorDetailsForUpdate().size();
            for (BuildingDetail bpa : bpaBuildingDetail) {
                if (oc.getBuildingNumber() == bpa.getNumber()) {
                    if (size > bpa.getApplicationFloorDetails().size()) {
                        model.addAttribute("OcComparisonValidation",
                                bpaMessageSource.getMessage("msg.oc.appln.comparison.validation3",
                                        new String[] { oc.getBuildingNumber().toString(), size.toString(),
                                                bpa.getNumber().toString(),
                                                String.valueOf(bpa.getApplicationFloorDetails().size()) },
                                        null));
                        return isValid;
                    }
                }
            }
        }
        // check floor wise floor area same or not
        BigDecimal hundred = new BigDecimal(100);
        BigDecimal percent = new BigDecimal(bpaUtils.getAppConfigForOcAllowDeviation());
        for (OCBuilding oc : ocBuildings) {
            for (BuildingDetail bpa : bpaBuildingDetail) {
                if (oc.getBuildingNumber() == bpa.getNumber()) {
                    for (OCFloor ocFloor : oc.getFloorDetailsForUpdate()) {
                        int floorNumber = ocFloor.getFloorNumber();
                        for (ApplicationFloorDetail bpaFloor : bpa.getApplicationFloorDetails()) {
                            if (floorNumber == bpaFloor.getFloorNumber()) {
                                BigDecimal allowDeviation = bpaFloor.getFloorArea().multiply(percent).divide(hundred);
                                BigDecimal total = bpaFloor.getFloorArea().add(allowDeviation);
                                if (ocFloor.getFloorArea().compareTo(total) > 0) {
                                    model.addAttribute("OcComparisonValidation", bpaMessageSource.getMessage(
                                            "msg.oc.appln.comparison.validation4",
                                            new String[] { oc.getBuildingNumber().toString(), String.valueOf(floorNumber),
                                                    String.valueOf(bpa.getNumber()), bpaFloor.getFloorNumber().toString() },
                                            null));
                                    return isValid;
                                }
                            }
                        }
                    }
                }
            }
        }
        // check floor wise occupancy same are not
        for (OCBuilding oc : ocBuildings) {
            for (BuildingDetail bpa : bpaBuildingDetail) {
                if (oc.getBuildingNumber() == bpa.getNumber()) {
                    for (OCFloor ocFloor : oc.getFloorDetailsForUpdate()) {
                        int floorNumber = ocFloor.getFloorNumber();
                        for (ApplicationFloorDetail bpaFloor : bpa.getApplicationFloorDetails()) {
                            if (floorNumber == bpaFloor.getFloorNumber()) {
                                if (!ocFloor.getSubOccupancy().getCode().equals(bpaFloor.getSubOccupancy().getCode())) {
                                    model.addAttribute("OcComparisonValidation", bpaMessageSource.getMessage(
                                            "msg.oc.appln.comparison.validation5",
                                            new String[] { oc.getBuildingNumber().toString(), String.valueOf(floorNumber),
                                                    String.valueOf(bpa.getNumber()), bpaFloor.getFloorNumber().toString() },
                                            null));
                                    return isValid;
                                }
                            }
                        }
                    }
                }
            }
        }
        // check block wise floor area same are not
        BigDecimal limitSqurMtrs = new BigDecimal(40);
        for (OCBuilding oc : ocBuildings) {
            for (BuildingDetail bpa : bpaBuildingDetail) {
                if (oc.getBuildingNumber() == bpa.getNumber()) {
                    BigDecimal totalOcFloor = getOcTotalFloorArea(oc.getFloorDetailsForUpdate());
                    BigDecimal totalBpaFloor = getBpaTotalFloorArea(bpa.getApplicationFloorDetails());
                    BigDecimal allowDeviation = totalBpaFloor.multiply(percent).divide(hundred);
                    BigDecimal totalBpaWithAllowDeviation = totalBpaFloor.add(allowDeviation);
                    if (totalBpaWithAllowDeviation.compareTo(totalOcFloor) < 0) {
                        model.addAttribute("OcComparisonValidation",
                                bpaMessageSource.getMessage("msg.oc.appln.comparison.validation6",
                                        new String[] { oc.getBuildingNumber().toString() }, null));
                        return isValid;
                    }
                    if (totalOcFloor.subtract(totalBpaFloor).compareTo(limitSqurMtrs) > 0) {
                        model.addAttribute("OcComparisonValidation",
                                bpaMessageSource.getMessage("msg.oc.appln.comparison.validation7",
                                        new String[] { oc.getBuildingNumber().toString() }, null));
                        return isValid;
                    }
                }
            }
        }
        return true;
    }

    private BigDecimal getOcTotalFloorArea(List<OCFloor> floorList) {
        BigDecimal totalFloorArea = BigDecimal.ZERO;
        for (OCFloor floorDetail : floorList)
            totalFloorArea = totalFloorArea.add(floorDetail.getFloorArea());
        return totalFloorArea;
    }

    private BigDecimal getBpaTotalFloorArea(List<ApplicationFloorDetail> floorList) {
        BigDecimal totalFloorArea = BigDecimal.ZERO;
        for (ApplicationFloorDetail floorDetail : floorList)
            totalFloorArea = totalFloorArea.add(floorDetail.getFloorArea());
        return totalFloorArea;
    }
}
