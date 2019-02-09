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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.egov.bpa.master.entity.BpaFee;
import org.egov.bpa.master.entity.BpaFeeDetail;
import org.egov.bpa.master.entity.ServiceType;
import org.egov.bpa.master.service.BpaFeeService;
import org.egov.bpa.transaction.entity.ApplicationFee;
import org.egov.bpa.transaction.entity.ApplicationFeeDetail;
import org.egov.bpa.transaction.entity.ApplicationFloorDetail;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.BuildingDetail;
import org.egov.bpa.transaction.entity.ExistingBuildingDetail;
import org.egov.bpa.transaction.entity.ExistingBuildingFloorDetail;
import org.egov.bpa.transaction.entity.PermitFee;
import org.egov.bpa.utils.BpaConstants;
import org.egov.common.entity.bpa.Occupancy;
import org.egov.commons.service.OccupancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author vinoth
 */
@Service
@Transactional(readOnly = true)
public class ApplicationBpaFeeCalculationService {
	private static final String OTHERS = "Others";
	private static final BigDecimal ONE_HUNDRED_FIFTY = BigDecimal.valueOf(150);
	private static final BigDecimal THREE_HUNDRED = BigDecimal.valueOf(300);
	private static final BigDecimal FIFTY = BigDecimal.valueOf(50);
	/*
	 * private static final String OCCUPANCY_A1 = "Residential"; private static
	 * final String OCCUPANCY_A2 = "Special Residential"; private static final
	 * String OCCUPANCY_A5 = "Professional Office"; private static final String
	 * OCCUPANCY_B1 = "Educational"; private static final String OCCUPANCY_B2 =
	 * "Educational HighSchool"; private static final String OCCUPANCY_B3 =
	 * "Higher Educational Institute"; private static final String OCCUPANCY_C =
	 * "Medical/Hospital"; private static final String OCCUPANCY_C1 =
	 * "Medical IP"; private static final String OCCUPANCY_C2 = "Medical OP";
	 * private static final String OCCUPANCY_C3 = "Medical Admin"; private
	 * static final String OCCUPANCY_D = "Assembly"; private static final String
	 * OCCUPANCY_D1 = "Assembly Worship"; private static final String
	 * OCCUPANCY_D2 = "Bus Terminal"; private static final String OCCUPANCY_E =
	 * "Office/Business"; private static final String OCCUPANCY_F =
	 * "Mercantile / Commercial"; private static final String OCCUPANCY_H =
	 * "Storage"; private static final String OCCUPANCY_A4 = "Apartment/Flat";
	 */

	@Autowired
	private BpaFeeService bpaFeeService;
	@Autowired
	private ApplicationFeeService applicationFeeService;
	@Autowired
	private OccupancyService occupancyService;
	@Autowired
	private PermitFeeService permitFeeService;

	private PermitFee getbpaFee(final BpaApplication application) {
		PermitFee permitFee = null;
		if (application != null) {
			List<PermitFee> permitFeeList = permitFeeService
					.getPermitFeeListByApplicationId(application.getId());
			if (permitFeeList.isEmpty()) {
				permitFee = new PermitFee();
				permitFee.setApplicationFee(new ApplicationFee());
				permitFee.setApplication(application);
				return permitFee;
			} else {
				return permitFeeList.get(0);
			}
		}
		return permitFee;
	}

	public PermitFee calculateBpaSanctionFees(final BpaApplication application) {
		List<Long> serviceTypeList = new ArrayList<>();
		// getting all service type and amenities to retrieve fee details
		serviceTypeList.add(application.getServiceType().getId());
		if (!application.getApplicationAmenity().isEmpty()) {
			for (ServiceType serviceType : application.getApplicationAmenity()) {
				serviceTypeList.add(serviceType.getId());
			}
		}
		PermitFee permitFee = getbpaFee(application);
		// If record rejected and recalculation required again, then this logic
		// has to be change.
		if (permitFee.getApplicationFee().getApplicationFeeDetail().isEmpty()) {
			calculateFeeByServiceType(application, serviceTypeList, permitFee);
		}

		return permitFee;
	}

	/**
	 * @param application
	 * @param serviceTypeList
	 * @param applicationFee
	 */

	public void calculateFeeByServiceType(final BpaApplication application, final List<Long> serviceTypeList,
			final PermitFee permitFee) {
		if (application != null) {
			for (Long serviceTypdId : serviceTypeList) {

				BigDecimal beyondPermissibleArea = BigDecimal.ZERO;

				// RESTRICT TO FEW SERVICES
				if (!application.getBuildingDetail().isEmpty() && application.getOccupancy() != null
						&& !application.getSiteDetail().isEmpty()
						&& application.getSiteDetail().get(0).getExtentinsqmts() != null) {
					beyondPermissibleArea = calculateAreaForAdditionalFeeCalculation(application).setScale(2,
							BigDecimal.ROUND_HALF_UP);
				}
				for (BpaFee bpaFee : bpaFeeService.getActiveSanctionFeeForListOfServices(serviceTypdId)) {
					if (bpaFee != null) {
						BigDecimal amount = BigDecimal.ZERO;
						if (!application.getIsEconomicallyWeakerSection()) {// In
																			// case
																			// of
																			// economically
																			// weaker
																			// section,
																			// amount
																			// will
							// be zero.
							String occupancy;
							BigDecimal inputArea = getInputUnitForEachServiceType(application,
									bpaFee.getServiceType().getCode());

							if (BpaConstants.getBpaFeeCateory2().contains(bpaFee.getServiceType().getCode())
									&& (BpaConstants.INDUSTRIAL.equalsIgnoreCase(application.getOccupancy().getCode())
											|| BpaConstants.MERCANTILE_COMMERCIAL
													.equalsIgnoreCase(application.getOccupancy().getCode())))
								occupancy = application.getOccupancy().getDescription();
							else if (BpaConstants.RESIDENTIAL.equalsIgnoreCase(application.getOccupancy().getCode())
									|| BpaConstants.APARTMENT_FLAT
											.equalsIgnoreCase(application.getOccupancy().getDescription()))
								occupancy = BpaConstants.RESIDENTIAL;
							else if (BpaConstants.THATCHED_TILED_HOUSE
									.equalsIgnoreCase(application.getOccupancy().getCode()))
								occupancy = application.getOccupancy().getCode();
							else
								occupancy = OTHERS;

							BigDecimal feeAmount = getBpaFeeObjByOccupancyType(bpaFee.getCode(), occupancy, bpaFee);

							if (("101").equals(bpaFee.getCode()) || ("301").equals(bpaFee.getCode())
									|| ("401").equals(bpaFee.getCode()) || ("601").equals(bpaFee.getCode())
									|| ("701").equals(bpaFee.getCode())) {
								BigDecimal existBldgInputArea = getExistBldgTotalFloorArea(application);
								if (BpaConstants.MIXED_OCCUPANCY
										.equalsIgnoreCase(application.getOccupancy().getCode())) {
									List<Occupancy> occupancies = occupancyService.findAllOrderByOrderNumber();
									Map<String, Map<Occupancy, BigDecimal>> convertedOccupancies = new ConcurrentHashMap<>();
									for (Map.Entry<String, Map<Occupancy, BigDecimal>> block : groupBlockOccupancyFloorArea(
											application.getBuildingDetail()).entrySet()) {
										for (Map.Entry<Occupancy, BigDecimal> blockOccupancy : block.getValue()
												.entrySet()) {
											Map<Occupancy, BigDecimal> convertInner;
											String convertedOccu = getOccupancyAsPerFloorArea(blockOccupancy.getKey(),
													blockOccupancy.getValue());
											Optional<Occupancy> occp = occupancies.stream()
													.filter(o -> o.getCode().equals(convertedOccu)).findFirst();
											if (convertedOccupancies.containsKey(block.getKey())) {
												Map<Occupancy, BigDecimal> existOccupancyMap = convertedOccupancies
														.get(block.getKey());
												if (existOccupancyMap.containsKey(occp.get())) {
													existOccupancyMap.put(occp.get(), existOccupancyMap.get(occp.get())
															.add(blockOccupancy.getValue()));
													convertedOccupancies.put(block.getKey(), existOccupancyMap);
												} else {
													convertInner = new ConcurrentHashMap<>();
													convertInner.put(occp.get(), blockOccupancy.getValue());
													convertedOccupancies.get(block.getKey()).putAll(convertInner);
												}
											} else {
												convertInner = new ConcurrentHashMap<>();
												convertInner.put(occp.get(), blockOccupancy.getValue());
												convertedOccupancies.put(block.getKey(), convertInner);
											}
										}
									}
									for (Map.Entry<String, Map<Occupancy, BigDecimal>> blockOccupancyFloorAreas : convertedOccupancies
											.entrySet()) {
										for (Map.Entry<Occupancy, BigDecimal> occupancyWiseArea : blockOccupancyFloorAreas
												.getValue().entrySet()) {
											// occupancy =
											// getOccupancyToGetFeeAmt(occupancyWiseArea);
											if (blockOccupancyFloorAreas.getValue().size() == 1
													&& (BpaConstants.RESIDENTIAL
															.equalsIgnoreCase(occupancyWiseArea.getKey().getCode())
															|| BpaConstants.APARTMENT_FLAT.equalsIgnoreCase(
																	occupancyWiseArea.getKey().getCode())))
												occupancy = BpaConstants.RESIDENTIAL;
											else
												occupancy = OTHERS;
											// set occupancy type and get fee
											// and calculate amount.
											feeAmount = getBpaFeeObjByOccupancyType(bpaFee.getCode(), occupancy,
													bpaFee);
											amount = amount
													.add(calculatePermitFee(occupancyWiseArea.getValue(), feeAmount));
										}
									}
								} else {
									amount = calculatePermitFee(inputArea, feeAmount);
								}
								// CHECK WHETHER THIS APPLICABLE TO ONLY 701
								// OCCUPANCY TYPE.. ALSO HERE WORK
								// STARTED,INPROGRSS,COMPLETED TO BE CONSIDER.
								if (checkIsWorkAlreadyStarted(application) && BpaConstants.getServicesForValidation()
										.contains(bpaFee.getServiceType().getCode())) {
									amount = amount.multiply(BigDecimal.valueOf(3));
								} else if (checkIsEligibleForDiscountOnPermitFee(inputArea, existBldgInputArea,
										bpaFee.getServiceType().getCode(), application.getOccupancy().getCode())) {
									amount = calculateAndGetDiscountedPermitFee(inputArea.multiply(feeAmount)); // 50%
																												// off
																												// if
																												// area
									// less than 150
									// mts
								}

							} else if (("102").equals(bpaFee.getCode()) || ("302").equals(bpaFee.getCode())
									|| ("402").equals(bpaFee.getCode()) || ("602").equals(bpaFee.getCode())
									|| ("702").equals(bpaFee.getCode())) {
								feeAmount = getBpaFeeObjByOccupancyType(bpaFee.getCode(), OTHERS, bpaFee);
								// calculate beyond permissable area tax for
								// other
								if (beyondPermissibleArea.compareTo(BigDecimal.ZERO) > 0) {
									amount = calculateAdditionalFee(beyondPermissibleArea, feeAmount);
								}

							} else if (("201").equals(bpaFee.getCode()))
								amount = calculateDemolitionFee(inputArea, feeAmount);
							else if (("501").equals(bpaFee.getCode()))
								amount = calculateLandDevelopmentCharges(inputArea, feeAmount);
							else if (("901").equals(bpaFee.getCode())) {
								amount = calculatePermitFeeForHut(inputArea, feeAmount);
							} else if (("1001").equals(bpaFee.getCode()))
								amount = calculateChargesForWell(inputArea, feeAmount);
							else if (("1002").equals(bpaFee.getCode()))
								amount = calculateChargesForCompoundWall(inputArea, feeAmount);
							else if (("1003").equals(bpaFee.getCode()))
								amount = calculateShutterDoorConversionCharges(inputArea, feeAmount);
							else if (("1004").equals(bpaFee.getCode()))
								amount = calculateRoofConversionCharges(inputArea, feeAmount);
							else if (("1005").equals(bpaFee.getCode()))
								amount = calculateTowerConstructionCharges(inputArea, feeAmount);
							else if (("1006").equals(bpaFee.getCode()))
								amount = calculatePoleStructureConstructionCharges(inputArea, feeAmount);
						}
						permitFee.getApplicationFee()
								.addApplicationFeeDetail(buildApplicationFeeDetail(bpaFee, permitFee.getApplicationFee(), amount));
					}
				}
			}
		}
	}

	private String getOccupancyToGetFeeAmt(Entry<Occupancy, BigDecimal> occupancyWiseArea) {
		String occupancy;
		if (BpaConstants.RESIDENTIAL.equalsIgnoreCase(occupancyWiseArea.getKey().getCode())
				|| BpaConstants.APARTMENT_FLAT.equalsIgnoreCase(occupancyWiseArea.getKey().getCode()))
			occupancy = BpaConstants.RESIDENTIAL;
		else if (BpaConstants.THATCHED_TILED_HOUSE.equalsIgnoreCase(occupancyWiseArea.getKey().getCode()))
			occupancy = occupancyWiseArea.getKey().getCode();
		else
			occupancy = OTHERS;

		return occupancy;
	}

	private ApplicationFeeDetail buildApplicationFeeDetail(final BpaFee bpaFee, final ApplicationFee applicationFee,
			BigDecimal amount) {
		ApplicationFeeDetail feeDetail = new ApplicationFeeDetail();
		feeDetail.setAmount(amount.setScale(0, BigDecimal.ROUND_HALF_UP));
		feeDetail.setBpaFee(bpaFee);
		feeDetail.setApplicationFee(applicationFee);
		return feeDetail;
	}

	private BigDecimal calculatePermitFeeForHut(BigDecimal inputArea, BigDecimal feeAmount) {
		return inputArea.multiply(feeAmount);
	}

	private BigDecimal calculatePoleStructureConstructionCharges(BigDecimal inputArea, BigDecimal feeAmount) {
		return inputArea.multiply(feeAmount);
	}

	private BigDecimal calculateTowerConstructionCharges(BigDecimal inputArea, BigDecimal feeAmount) {
		return inputArea.multiply(feeAmount);
	}

	private BigDecimal calculateRoofConversionCharges(BigDecimal inputArea, BigDecimal feeAmount) {
		return inputArea.multiply(feeAmount);
	}

	private BigDecimal calculateShutterDoorConversionCharges(BigDecimal inputArea, BigDecimal feeAmount) {
		return inputArea.multiply(feeAmount);
	}

	private BigDecimal calculateChargesForCompoundWall(BigDecimal inputArea, BigDecimal feeAmount) {
		return inputArea.multiply(feeAmount);
	}

	private BigDecimal calculateChargesForWell(BigDecimal inputArea, BigDecimal feeAmount) {
		return inputArea.multiply(feeAmount);
	}

	private BigDecimal calculateLandDevelopmentCharges(BigDecimal inputArea, BigDecimal feeAmount) {
		return inputArea.multiply(feeAmount);
	}

	private BigDecimal calculateDemolitionFee(BigDecimal inputArea, BigDecimal feeAmount) {
		return inputArea.multiply(feeAmount);
	}

	private BigDecimal calculateAdditionalFee(BigDecimal inputArea, BigDecimal feeAmount) {
		return inputArea.multiply(feeAmount);
	}

	private BigDecimal calculatePermitFee(BigDecimal inputArea, BigDecimal feeAmount) {
		return inputArea.multiply(feeAmount);

	}

	/**
	 * @param application
	 * @return is work already started or not ?
	 */
	private Boolean checkIsWorkAlreadyStarted(final BpaApplication application) {
		return application.getSiteDetail().get(0).getIsappForRegularization();
	}

	/**
	 * @param inputUnit
	 * @param serviceTypeCode
	 * @return is eligible for permit fee 50% waive off ?
	 */
	private Boolean checkIsEligibleForDiscountOnPermitFee(final BigDecimal inputUnit,
			final BigDecimal existBldgInputArea, final String serviceTypeCode, final String occupancyType) {
		return BpaConstants.RESIDENTIAL.equalsIgnoreCase(occupancyType)
				&& BpaConstants.getServicesForValidation().contains(serviceTypeCode)
				&& inputUnit.add(existBldgInputArea).compareTo(BigDecimal.valueOf(150)) <= 0 ? true : false;
	}

	/**
	 * @param occupancyType
	 * @param occupancyType
	 * @param bpaFee
	 * @return master rate value for each service type based on occupancy type
	 */
	private BigDecimal getBpaFeeObjByOccupancyType(final String feeCode, String occupancyType, final BpaFee bpaFee) {
		BigDecimal rate = BigDecimal.ZERO;
		for (BpaFeeDetail feeDetail : bpaFee.getFeeDetail()) {
			if (feeCode != null && feeCode.equalsIgnoreCase(bpaFee.getCode())) {
				if (feeDetail.getAdditionalType() != null
						&& occupancyType.equalsIgnoreCase(feeDetail.getAdditionalType())) {
					rate = BigDecimal.valueOf(feeDetail.getAmount());
					break;
				} else {
					rate = BigDecimal.valueOf(feeDetail.getAmount());
				}
			}
		}
		return rate;
	}

	/**
	 * @param amount
	 * @return after discounted amount (eligible if constructing only up to 150
	 *         sqmtrs)
	 */
	private BigDecimal calculateAndGetDiscountedPermitFee(final BigDecimal amount) {
		return amount.multiply(BigDecimal.valueOf(50)).divide(BigDecimal.valueOf(100));
	}

	/**
	 * @param application
	 * @param serviceTypeCode
	 * @return input unit value for fee calculation
	 */
	private BigDecimal getInputUnitForEachServiceType(final BpaApplication application, final String serviceTypeCode) {
		BigDecimal inputUnit = BigDecimal.ZERO;

		if (BpaConstants.getBpaFeeCateory1().contains(serviceTypeCode)) {
			inputUnit = getTotalFloorArea(application);
		} else if (BpaConstants.getBpaFeeCateory2().contains(serviceTypeCode)) { // Sub-Division
																					// of
																					// plot/Land
																					// Development
			inputUnit = application.getSiteDetail().get(0).getExtentinsqmts();
		} else if ("10".equals(serviceTypeCode)) { // well
			inputUnit = application.getSiteDetail().get(0).getDwellingunitnt();
		} else if ("11".equals(serviceTypeCode)) { // Compound Wall
			inputUnit = application.getSiteDetail().get(0).getLengthOfCompoundWall();
		} else if ("14".equals(serviceTypeCode)) { // Tower Construction
			inputUnit = application.getSiteDetail().get(0).getErectionoftower();
		} else if ("12".equals(serviceTypeCode)) { // Shutter or Door
													// Conversion/Erection
			inputUnit = application.getSiteDetail().get(0).getShutter();
		} else if ("13".equals(serviceTypeCode)) { // Roof Conversion
			inputUnit = application.getSiteDetail().get(0).getRoofConversion();
		} else if ("15".equals(serviceTypeCode)) { // Pole Structures
			inputUnit = application.getSiteDetail().get(0).getNoOfPoles();
		} else if ("09".equals(serviceTypeCode)) { // hut or shed
			inputUnit = application.getSiteDetail().get(0).getNoOfHutOrSheds();
		}
		return inputUnit;
	}

	/***
	 * Calculate Area for additional fee calculation.
	 * 
	 * @param application
	 * @return
	 */

	public BigDecimal calculateAreaForAdditionalFeeCalculation(final BpaApplication application) {
		BigDecimal extentOfLand = application.getSiteDetail().get(0).getExtentinsqmts();
		BigDecimal minimumFARWithOutAdditionalFee;
		BigDecimal minimumFARWithAdditionalFee;
		BigDecimal weightageAvgFAR;
		BigDecimal maximumPermittedFloorAreaWithAddnFee = BigDecimal.ZERO;
		BigDecimal maximumPermittedFARWithAdditionalFee;
		BigDecimal maximumPermittedFloorArea = BigDecimal.ZERO;
		BigDecimal maximumPermittedFAR;
		BigDecimal additionalFeeCalculationArea = BigDecimal.ZERO;

		Map<Occupancy, BigDecimal> occFloorArea = getOccupancyWiseFloorArea(application.getBuildingDetail());
		Map<Occupancy, BigDecimal> existBldgOccupancyWiseFloorArea = getExistBldgOccupancyWiseFloorArea(
				application.getExistingBuildingDetails());
		BigDecimal proposedBldgFloorArea = getTotalFloorArea(application);
		BigDecimal existBldgFloorArea = getExistBldgTotalFloorArea(application);
		BigDecimal totalFloorArea = proposedBldgFloorArea.add(existBldgFloorArea);
		if (totalFloorArea.compareTo(BigDecimal.ZERO) > 0) {
			if (extentOfLand.compareTo(new BigDecimal(5000)) <= 0) {

				minimumFARWithOutAdditionalFee = minimumFARWithoutAdditionalFee(application);
				minimumFARWithAdditionalFee = minimumFARWithAdditionalFee(application);
				maximumPermittedFAR = minimumFARWithOutAdditionalFee.multiply(extentOfLand);

				// Mean additional fee has to collect BUT CITIZEN NOT READY TO
				// PAY ADDITIONAL TAX
				// AdditionalFeePaymentAccepted condition need to be check if on
				// submission suppose data captured
				if (totalFloorArea.compareTo(maximumPermittedFAR) > 0) {
					maximumPermittedFARWithAdditionalFee = minimumFARWithAdditionalFee.multiply(extentOfLand);
					// Calclulate additional Fee.
					if (totalFloorArea.compareTo(maximumPermittedFARWithAdditionalFee) <= 0) {
						additionalFeeCalculationArea = proposedBldgFloorArea.subtract(maximumPermittedFAR);
					}
				}
			} else // above area greater than 5000sq.mt.
			{
				weightageAvgFAR = weightageAverageFarWithoutAdditionalFee(occFloorArea,
						existBldgOccupancyWiseFloorArea);
				if (weightageAvgFAR != null)
					maximumPermittedFloorArea = weightageAvgFAR.multiply(extentOfLand);
				// Mean Aggregate violation of area
				// AdditionalFeePaymentAccepted check need to be added if
				// require(i.e:application.getBuildingDetail().get(0).getAdditionalFeePaymentAccepted())
				if (totalFloorArea.compareTo(maximumPermittedFloorArea) > 0) {
					weightageAvgFAR = weightageAverageFarWithAdditionalFee(occFloorArea,
							existBldgOccupancyWiseFloorArea);
					if (weightageAvgFAR != null)
						maximumPermittedFloorAreaWithAddnFee = weightageAvgFAR.multiply(extentOfLand);
					// Mean Aggregate violation of area
					if (totalFloorArea.compareTo(maximumPermittedFloorAreaWithAddnFee) <= 0) {
						// Calculate additional Fee.
						additionalFeeCalculationArea = proposedBldgFloorArea.subtract(maximumPermittedFloorArea);
					}
				}
			}
		}
		return additionalFeeCalculationArea.setScale(2, RoundingMode.HALF_UP);
	}

	/***
	 * Group block wise occupancy and floor area
	 * 
	 * @param buildingDetails
	 * @return
	 */
	// Floor Area considered here.
	public Map<Occupancy, BigDecimal> getOccupancyWiseFloorArea(List<BuildingDetail> buildingDetails) {
		Map<Occupancy, BigDecimal> occupancyWiseFloorArea = new ConcurrentHashMap<>();
		for (BuildingDetail building : buildingDetails) {
			for (ApplicationFloorDetail floor : building.getApplicationFloorDetails()) {
				if (occupancyWiseFloorArea.containsKey(floor.getOccupancy())) {
					occupancyWiseFloorArea.put(floor.getOccupancy(),
							occupancyWiseFloorArea.get(floor.getOccupancy()).add(floor.getFloorArea()));
				} else {
					occupancyWiseFloorArea.put(floor.getOccupancy(), floor.getFloorArea());
				}
			}
		}
		return occupancyWiseFloorArea;
	}

	/***
	 * Group block wise occupancy and floor area
	 * 
	 * @param existBldgDtls
	 * @return
	 */
	// Floor Area considered here.
	public Map<Occupancy, BigDecimal> getExistBldgOccupancyWiseFloorArea(List<ExistingBuildingDetail> existBldgDtls) {
		Map<Occupancy, BigDecimal> occupancyWiseFloorArea = new ConcurrentHashMap<>();
		for (ExistingBuildingDetail building : existBldgDtls) {
			for (ExistingBuildingFloorDetail floor : building.getExistingBuildingFloorDetails()) {
				if (occupancyWiseFloorArea.containsKey(floor.getOccupancy())) {
					occupancyWiseFloorArea.put(floor.getOccupancy(),
							occupancyWiseFloorArea.get(floor.getOccupancy()).add(floor.getFloorArea()));
				} else {
					occupancyWiseFloorArea.put(floor.getOccupancy(), floor.getFloorArea());
				}
			}
		}
		return occupancyWiseFloorArea;
	}

	public Map<String, Map<Occupancy, BigDecimal>> groupBlockOccupancyFloorArea(List<BuildingDetail> buildingDetails) {
		Map<String, Map<Occupancy, BigDecimal>> groupByBlkOccupancyFloorArea = new ConcurrentHashMap<>();
		for (BuildingDetail building : buildingDetails) {
			Map<Occupancy, BigDecimal> subMap = new ConcurrentHashMap<>();
			for (ApplicationFloorDetail floor : building.getApplicationFloorDetails()) {
				if (subMap.containsKey(floor.getOccupancy())) {
					subMap.put(floor.getOccupancy(), subMap.get(floor.getOccupancy()).add(floor.getFloorArea()));
				} else {
					subMap.put(floor.getOccupancy(), floor.getFloorArea());
				}
			}
			groupByBlkOccupancyFloorArea.put(building.getName(), subMap);
		}
		return groupByBlkOccupancyFloorArea;
	}

	public BigDecimal getTotalFloorArea(final BpaApplication application) {
		BigDecimal totalFloorArea = BigDecimal.ZERO;
		for (BuildingDetail buildingDetail : application.getBuildingDetail())
			for (ApplicationFloorDetail floorDetails : buildingDetail.getApplicationFloorDetails())
				totalFloorArea = totalFloorArea.add(floorDetails.getFloorArea());
		return totalFloorArea;
	}

	public BigDecimal getExistBldgTotalFloorArea(final BpaApplication application) {
		BigDecimal totalFloorArea = BigDecimal.ZERO;
		if (!application.getExistingBuildingDetails().isEmpty()
				&& application.getExistingBuildingDetails().get(0).getTotalPlintArea() != null)
			for (ExistingBuildingFloorDetail floor : application.getExistingBuildingDetails().get(0)
					.getExistingBuildingFloorDetails()) {
				totalFloorArea = totalFloorArea.add(floor.getFloorArea());
			}
		return totalFloorArea;
	}

	/***
	 * Minimum FAR Without Additional Fee
	 * 
	 * @param application
	 * @return
	 */
	public BigDecimal minimumFARWithoutAdditionalFee(final BpaApplication application) {
		List<BigDecimal> minimumFARs = new ArrayList<>();
		for (BuildingDetail bldg : application.getBuildingDetail()) {
			for (ApplicationFloorDetail floor : bldg.getApplicationFloorDetails()) {
				minimumFARs.add(floor.getOccupancy().getMinFar());
			}
		}
		if (!application.getExistingBuildingDetails().isEmpty()
				&& application.getExistingBuildingDetails().get(0).getTotalPlintArea() != null)
			for (ExistingBuildingDetail existBldg : application.getExistingBuildingDetails())
				for (ExistingBuildingFloorDetail floorDetails : existBldg.getExistingBuildingFloorDetails())
					minimumFARs.add(floorDetails.getOccupancy().getMaxFar());
		return Collections.min(minimumFARs);
	}

	/***
	 * Minimum FAR with Additional Fee
	 * 
	 * @param application
	 * @return
	 */
	public BigDecimal minimumFARWithAdditionalFee(final BpaApplication application) {
		List<BigDecimal> maximumFARs = new ArrayList<>();
		for (BuildingDetail bldg : application.getBuildingDetail()) {
			for (ApplicationFloorDetail floor : bldg.getApplicationFloorDetails()) {
				maximumFARs.add(floor.getOccupancy().getMaxFar());
			}
		}
		if (!application.getExistingBuildingDetails().isEmpty()
				&& application.getExistingBuildingDetails().get(0).getTotalPlintArea() != null)
			for (ExistingBuildingDetail existBldg : application.getExistingBuildingDetails())
				for (ExistingBuildingFloorDetail floorDetails : existBldg.getExistingBuildingFloorDetails())
					maximumFARs.add(floorDetails.getOccupancy().getMaxFar());
		return Collections.min(maximumFARs);
	}

	/***
	 * Weightage Average FAR Without Additional Fee
	 * 
	 * @param occupancyWiseFloorArea
	 * @return
	 */
	public BigDecimal weightageAverageFarWithoutAdditionalFee(Map<Occupancy, BigDecimal> occupancyWiseFloorArea,
			Map<Occupancy, BigDecimal> existBldgOccupancyWiseFloorArea) {
		BigDecimal maxPermittedFloorArea = BigDecimal.ZERO;
		BigDecimal sumOfFloorArea = BigDecimal.ZERO;
		for (Entry<Occupancy, BigDecimal> setOfOccupancy : occupancyWiseFloorArea.entrySet()) {
			maxPermittedFloorArea = maxPermittedFloorArea
					.add(setOfOccupancy.getKey().getMinFar().multiply(setOfOccupancy.getValue()));
			sumOfFloorArea = sumOfFloorArea.add(setOfOccupancy.getValue());
		}
		if (!existBldgOccupancyWiseFloorArea.isEmpty()) {
			for (Entry<Occupancy, BigDecimal> occupancy : existBldgOccupancyWiseFloorArea.entrySet()) {
				maxPermittedFloorArea = maxPermittedFloorArea
						.add(occupancy.getKey().getMinFar().multiply(occupancy.getValue()));
				sumOfFloorArea = sumOfFloorArea.add(occupancy.getValue());
			}
		}
		if (sumOfFloorArea.compareTo(BigDecimal.ZERO) > 0)
			return maxPermittedFloorArea.divide(sumOfFloorArea, 6, RoundingMode.HALF_UP).setScale(6,
					RoundingMode.HALF_UP);

		return null;
	}

	/***
	 * Weightage Average FAR With Additional Fee
	 * 
	 * @param occupancyWiseFloorArea
	 * @return
	 */
	public BigDecimal weightageAverageFarWithAdditionalFee(Map<Occupancy, BigDecimal> occupancyWiseFloorArea,
			Map<Occupancy, BigDecimal> existBldgOccupancyWiseFloorArea) {

		BigDecimal maxPermittedFloorArea = BigDecimal.ZERO;
		BigDecimal sumOfFloorArea = BigDecimal.ZERO;
		for (Entry<Occupancy, BigDecimal> setOfOccupancy : occupancyWiseFloorArea.entrySet()) {
			maxPermittedFloorArea = maxPermittedFloorArea
					.add(setOfOccupancy.getKey().getMaxFar().multiply(setOfOccupancy.getValue()));
			sumOfFloorArea = sumOfFloorArea.add(setOfOccupancy.getValue());
		}
		if (!existBldgOccupancyWiseFloorArea.isEmpty()) {
			for (Entry<Occupancy, BigDecimal> occupancy : existBldgOccupancyWiseFloorArea.entrySet()) {
				maxPermittedFloorArea = maxPermittedFloorArea
						.add(occupancy.getKey().getMaxFar().multiply(occupancy.getValue()));
				sumOfFloorArea = sumOfFloorArea.add(occupancy.getValue());
			}
		}
		if (sumOfFloorArea.compareTo(BigDecimal.ZERO) > 0)
			return maxPermittedFloorArea.divide(sumOfFloorArea, 6, RoundingMode.HALF_UP).setScale(6,
					RoundingMode.HALF_UP);
		return null;
	}

	public static String getOccupancyAsPerFloorArea(Occupancy occupancy, BigDecimal floorArea) {
		String type = occupancy.getCode();
		String convertedOccupancy = type;
		if ((BpaConstants.EDUCATIONAL.equals(type) || BpaConstants.EDUCATIONAL_HIGHSCHOOL.equals(type)
				|| BpaConstants.HIGHER_EDUCATIONAL_INSTITUTE.equals(occupancy.getCode()))) {
			if (floorArea != null && floorArea.compareTo(ONE_HUNDRED_FIFTY) <= 0)
				convertedOccupancy = BpaConstants.SPECIAL_RESIDENTIAL;
			else
				convertedOccupancy = BpaConstants.EDUCATIONAL;
		} else if ((BpaConstants.MEDIACL_HOSPITAL.equals(type) || BpaConstants.MEDIACL_IP.equals(type)
				|| BpaConstants.MEDIACL_OP.equals(type) || BpaConstants.MEDICAL_ADMIN.equals(type))) {
			if (floorArea != null && floorArea.compareTo(ONE_HUNDRED_FIFTY) <= 0)
				convertedOccupancy = BpaConstants.MERCANTILE_COMMERCIAL;
			else
				convertedOccupancy = BpaConstants.MEDIACL_HOSPITAL;
		} else if (floorArea != null && floorArea.compareTo(ONE_HUNDRED_FIFTY) <= 0
				&& (BpaConstants.ASSEMBLY.equals(type)))
			convertedOccupancy = BpaConstants.MERCANTILE_COMMERCIAL;
		else if ((BpaConstants.ASSEMBLY_WORSHIP.equals(type) || BpaConstants.BUS_TERMINAL.equals(type)))
			convertedOccupancy = BpaConstants.ASSEMBLY;
		else if ((BpaConstants.OFFICE_BUSINESS.equals(type))) {
			if (floorArea != null && floorArea.compareTo(THREE_HUNDRED) <= 0)
				convertedOccupancy = BpaConstants.MERCANTILE_COMMERCIAL;
			else
				convertedOccupancy = BpaConstants.OFFICE_BUSINESS;
		} else if ((BpaConstants.STORAGE.equals(type))) {
			if (floorArea != null && floorArea.compareTo(THREE_HUNDRED) <= 0)
				convertedOccupancy = BpaConstants.MERCANTILE_COMMERCIAL;
			else
				convertedOccupancy = BpaConstants.STORAGE;
		} else if (BpaConstants.PROFESSIONAL_OFFICE.equals(type)) {
			if (floorArea != null && floorArea.compareTo(FIFTY) <= 0)
				convertedOccupancy = BpaConstants.RESIDENTIAL;
			else
				convertedOccupancy = BpaConstants.MERCANTILE_COMMERCIAL;
		}
		return convertedOccupancy;
	}

}