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


import static org.egov.bpa.utils.BpaConstants.ADDING_OF_EXTENSION;
import static org.egov.bpa.utils.BpaConstants.ALTERATION;
import static org.egov.bpa.utils.BpaConstants.APARTMENT_FLAT;
import static org.egov.bpa.utils.BpaConstants.BPA_ADDITIONAL_FEE;
import static org.egov.bpa.utils.BpaConstants.BPA_COMPOUND_FEE;
import static org.egov.bpa.utils.BpaConstants.BPA_DEMOLITION_FEE;
import static org.egov.bpa.utils.BpaConstants.BPA_LAND_DEVELOPMENT_CHARGES;
import static org.egov.bpa.utils.BpaConstants.BPA_PERMIT_FEE;
import static org.egov.bpa.utils.BpaConstants.BPA_WELL_FEE;
import static org.egov.bpa.utils.BpaConstants.CHANGE_IN_OCCUPANCY;
import static org.egov.bpa.utils.BpaConstants.COMPOUND_WALL;
import static org.egov.bpa.utils.BpaConstants.DEMOLITION;
import static org.egov.bpa.utils.BpaConstants.DIVISION_OF_PLOT;
import static org.egov.bpa.utils.BpaConstants.INDUSTRIAL;
import static org.egov.bpa.utils.BpaConstants.LABOURCESS;
import static org.egov.bpa.utils.BpaConstants.LOWRISK;
import static org.egov.bpa.utils.BpaConstants.MERCANTILE_COMMERCIAL;
import static org.egov.bpa.utils.BpaConstants.NEW_CONSTRUCTION;
import static org.egov.bpa.utils.BpaConstants.PERM_FOR_HUT_OR_SHED;
import static org.egov.bpa.utils.BpaConstants.POLE_CONSTRUCTION_FEE;
import static org.egov.bpa.utils.BpaConstants.POLE_STRUCTURES;
import static org.egov.bpa.utils.BpaConstants.RECONSTRUCTION;
import static org.egov.bpa.utils.BpaConstants.RESIDENTIAL;
import static org.egov.bpa.utils.BpaConstants.ROOF_CNVRSN_FEE;
import static org.egov.bpa.utils.BpaConstants.ROOF_CONVERSION;
import static org.egov.bpa.utils.BpaConstants.SHELTERFUND;
import static org.egov.bpa.utils.BpaConstants.SHTR_DOOR_FEE;
import static org.egov.bpa.utils.BpaConstants.SHUTTER_DOOR_CONVERSION;
import static org.egov.bpa.utils.BpaConstants.THATCHED_TILED_HOUSE;
import static org.egov.bpa.utils.BpaConstants.TOWER_CONSTRUCTION;
import static org.egov.bpa.utils.BpaConstants.TOWER_CONSTRUCTION_FEE;
import static org.egov.bpa.utils.BpaConstants.WELL;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.egov.bpa.master.entity.ApplicationSubType;
import org.egov.bpa.master.entity.BpaFee;
import org.egov.bpa.master.entity.BpaFeeDetail;
import org.egov.bpa.master.entity.BpaFeeMapping;
import org.egov.bpa.master.entity.ServiceType;
import org.egov.bpa.master.entity.enums.FeeSubType;
import org.egov.bpa.master.service.ApplicationSubTypeService;
import org.egov.bpa.master.service.BpaFeeMappingService;
import org.egov.bpa.master.service.ServiceTypeService;
import org.egov.bpa.transaction.entity.ApplicationFee;
import org.egov.bpa.transaction.entity.ApplicationFeeDetail;
import org.egov.bpa.transaction.entity.ApplicationFloorDetail;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.BuildingDetail;
import org.egov.bpa.transaction.entity.ExistingBuildingDetail;
import org.egov.bpa.transaction.entity.ExistingBuildingFloorDetail;
import org.egov.bpa.transaction.entity.PermitFee;
import org.egov.bpa.transaction.service.collection.BpaDemandService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.common.entity.bpa.Occupancy;
import org.egov.common.entity.bpa.SubOccupancy;
import org.egov.commons.Installment;
import org.egov.commons.dao.InstallmentDao;
import org.egov.commons.service.SubOccupancyService;
import org.egov.demand.model.EgDemand;
import org.egov.demand.model.EgDemandDetails;
import org.egov.demand.model.EgDemandReason;
import org.egov.infra.admin.master.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author vinoth
 */
@Service
@Transactional(readOnly = true)
public class PermitFeeCalculationService implements ApplicationBpaFeeCalculation {
	 private static final String RESIDENTIAL_DESC = "Residential";
	    private static final String OTHERS = "Others";
	    private static final BigDecimal ONE_HUNDRED_FIFTY = BigDecimal.valueOf(150);
	    private static final BigDecimal THREE_HUNDRED = BigDecimal.valueOf(300);
	    private static final BigDecimal FIFTY = BigDecimal.valueOf(50);
	    private static final BigDecimal TEN = BigDecimal.valueOf(10);
	    private static final BigDecimal HUNDRED = BigDecimal.valueOf(100);

	    /*
	     * private static final String OCCUPANCY_A1 = "Residential"; private static final String OCCUPANCY_A2 = "Special Residential";
	     * private static final String OCCUPANCY_A5 = "Professional Office"; private static final String OCCUPANCY_B1 = "Educational";
	     * private static final String OCCUPANCY_B2 = "Educational HighSchool"; private static final String OCCUPANCY_B3 =
	     * "Higher Educational Institute"; private static final String OCCUPANCY_C = "Medical/Hospital"; private static final String
	     * OCCUPANCY_C1 = "Medical IP"; private static final String OCCUPANCY_C2 = "Medical OP"; private static final String
	     * OCCUPANCY_C3 = "Medical Admin"; private static final String OCCUPANCY_D = "Assembly"; private static final String
	     * OCCUPANCY_D1 = "Assembly Worship"; private static final String OCCUPANCY_D2 = "Bus Terminal"; private static final String
	     * OCCUPANCY_E = "Office/Business"; private static final String OCCUPANCY_F = "Mercantile / Commercial"; private static final
	     * String OCCUPANCY_H = "Storage"; private static final String OCCUPANCY_A4 = "Apartment/Flat";
	     */

	    @Autowired
	    private PermitFeeService permitFeeService;
	    @Autowired
	    private SubOccupancyService subOccupancyService;
		@Autowired
		private BpaFeeMappingService bpaFeeMappingService;
		@Autowired
		protected ApplicationBpaService applicationBpaService;			 
        @Autowired
		private BpaDemandService bpaDemandService;
        @Autowired
		private InstallmentDao installmentDao;
		@Autowired
		private ModuleService moduleService;	
		@Autowired
		private ApplicationSubTypeService applicationSubTypeService;
		@Autowired
		private ServiceTypeService serviceTypeService;
		@Autowired
		private ApplicationFeeService applicationFeeService;
		
		
	    
	    @Override
	    public BigDecimal calculateAdmissionFeeAmount(List<Long> serviceTypeIds, Long applicationTypeId) {
	    	BigDecimal amount = BigDecimal.ZERO;
	    	List<ApplicationSubType> riskBasedAppTypes = applicationSubTypeService.getRiskBasedApplicationTypes();
	    	List<Long> ids = riskBasedAppTypes.stream().map(rs -> rs.getId()).collect(Collectors.toList());
	    	if(applicationTypeId!=null && ids.contains(applicationTypeId)) {
	    	List<ServiceType> mainServiceTypes = serviceTypeService.getAllActiveMainServiceTypes();
	    	ServiceType serviceType =			mainServiceTypes.stream()
	    		            .filter(st -> serviceTypeIds.contains(st.getId())) .collect(Collectors.toList()).get(0);
	    	List<BpaFeeMapping> appSubTypeFee = bpaFeeMappingService.getAppSubTypeFee(serviceType.getId(), FeeSubType.APPLICATION_FEE, applicationTypeId);
	    	for (final BpaFeeMapping feeMap : appSubTypeFee)
	    		amount = amount.add(BigDecimal.valueOf(feeMap.getAmount()));
	    	}
	    	else {
	    		amount = applicationBpaService.getTotalFeeAmountByPassingServiceTypeAndAmenities(serviceTypeIds);
	    	}
	    	return amount;
	    }
	    
	    @Override
	    public BigDecimal setAdmissionFeeAmount(final BpaApplication application, List<ServiceType> amenityList) {
		    List<Long> amenityIds = amenityList.stream().map(al -> al.getId()).collect(Collectors.toList());
		    amenityIds.add(application.getServiceType().getId());
		    return calculateAdmissionFeeAmount(amenityIds,application.getApplicationType().getId());
	    }
	    
	    
	    protected PermitFee getbpaFee(final BpaApplication application) {
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

	    @Override
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
	        	calculateFeeByCityGrade(application, serviceTypeList, permitFee);
	        }

	        return permitFee;
	    }

	    
	    //calculate fee based on city grade , height and built up area
	    public void calculateFeeByCityGrade(final BpaApplication application, final List<Long> serviceTypeList,
	            final PermitFee permitFee) {	    	
	    	
	        if (application != null) {
	            for (Long serviceTypeId : serviceTypeList) {

	                BigDecimal beyondPermissibleArea = BigDecimal.ZERO;

	                // RESTRICT TO FEW SERVICES
	                if (!application.getBuildingDetail().isEmpty() && !application.getPermitOccupancies().isEmpty()
	                        && !application.getSiteDetail().isEmpty()
	                        && application.getSiteDetail().get(0).getExtentinsqmts() != null) {
	                    beyondPermissibleArea = calculateBuiltUpAreaForAdditionalFeeCalculation(application).setScale(2,
	                            BigDecimal.ROUND_HALF_UP);
	                }                
	                
	                
	                for (BpaFeeMapping bpaFee : bpaFeeMappingService.getPermitFeesByAppType(application, serviceTypeId)) {
	                    if (bpaFee != null) {
	                        BigDecimal amount = BigDecimal.ZERO;
	                        if (!application.getIsEconomicallyWeakerSection()) {
	                            BigDecimal inputArea = getBuiltUpInputUnitForEachServiceType(application,
	                                    bpaFee.getServiceType().getCode());
	                            List<Occupancy> selectdOccupancies = application.getPermitOccupancies();
	                           

	                            if (BpaConstants.BPA_PERMIT_FEE.equals(bpaFee.getBpaFeeCommon().getName())
										&& ((bpaFee.getServiceType().getDescription().equalsIgnoreCase(NEW_CONSTRUCTION))
												|| (bpaFee.getServiceType().getDescription()
														.equalsIgnoreCase(RECONSTRUCTION))
												|| (bpaFee.getServiceType().getDescription().equalsIgnoreCase(ALTERATION))
												|| (bpaFee.getServiceType().getDescription()
														.equalsIgnoreCase(ADDING_OF_EXTENSION))
												|| (bpaFee.getServiceType().getDescription()
														.equalsIgnoreCase(CHANGE_IN_OCCUPANCY)))) {
	                                if (selectdOccupancies.size() > 1 ) {
	                                    List<SubOccupancy> occupancies = subOccupancyService.findAllOrderByOrderNumber();
	                                    Map<String, Map<Occupancy, BigDecimal>> convertedOccupancies = new ConcurrentHashMap<>();
	                                    for (Map.Entry<String, Map<SubOccupancy, BigDecimal>> block : groupBlockOccupancyBuiltUpArea(
	                                            application.getBuildingDetail()).entrySet()) {
	                                        for (Map.Entry<SubOccupancy, BigDecimal> blockOccupancy : block.getValue()
	                                                .entrySet()) {
	                                            Map<Occupancy, BigDecimal> convertInner;
	                                            String convertedOccu = getOccupancyAsPerFloorArea(blockOccupancy.getKey(),
	                                                    blockOccupancy.getValue());
	                                            Optional<SubOccupancy> subOccp = occupancies.stream()
	                                                    .filter(o -> o.getCode().equals(convertedOccu)).findFirst();
	                                            Occupancy occp = null;
	                                            if(subOccp.isPresent())
	                                                occp = subOccp.get().getOccupancy();
	                                            if(occp != null) {
	                                                if (convertedOccupancies.containsKey(block.getKey())) {
	                                                    Map<Occupancy, BigDecimal> existOccupancyMap = convertedOccupancies
	                                                            .get(block.getKey());
	                                                    if (existOccupancyMap.containsKey(occp)) {
	                                                        existOccupancyMap.put(occp, existOccupancyMap.get(occp)
	                                                                .add(blockOccupancy.getValue()));
	                                                        convertedOccupancies.put(block.getKey(), existOccupancyMap);
	                                                    } else {
	                                                        convertInner = new ConcurrentHashMap<>();
	                                                        convertInner.put(occp, blockOccupancy.getValue());
	                                                        convertedOccupancies.get(block.getKey()).putAll(convertInner);
	                                                    }
	                                                } else {
	                                                    convertInner = new ConcurrentHashMap<>();
	                                                    convertInner.put(occp, blockOccupancy.getValue());
	                                                    convertedOccupancies.put(block.getKey(), convertInner);
	                                                }
	                                            }
	                                        }
	                                    }
	                                    for (Map.Entry<String, Map<Occupancy, BigDecimal>> blockOccupancyFloorAreas : convertedOccupancies
	                                            .entrySet()) {
	                                        for (Map.Entry<Occupancy, BigDecimal> occupancyWiseArea : blockOccupancyFloorAreas
	                                                .getValue().entrySet()) {                                        
	                                            
	                                            
	                                            amount = amount
	                                                    .add(calculatePermitFee(occupancyWiseArea.getValue(), BigDecimal.valueOf(bpaFee.getAmount()), false));
	                                        }
	                                    }
	                                } else {
	                                    amount = calculatePermitFee(inputArea, BigDecimal.valueOf(bpaFee.getAmount()), application.getSiteDetail().get(0).getCharitableTrustBuilding());
	                                }
	                                }
	                                
	                            else if (BPA_ADDITIONAL_FEE.equals(bpaFee.getBpaFeeCommon().getName())
										&& ((bpaFee.getServiceType().getDescription().equalsIgnoreCase(NEW_CONSTRUCTION))
												|| (bpaFee.getServiceType().getDescription()
														.equalsIgnoreCase(RECONSTRUCTION))
												|| (bpaFee.getServiceType().getDescription().equalsIgnoreCase(ALTERATION))
												|| (bpaFee.getServiceType().getDescription()
														.equalsIgnoreCase(ADDING_OF_EXTENSION))
												|| (bpaFee.getServiceType().getDescription()
														.equalsIgnoreCase(CHANGE_IN_OCCUPANCY)))) {
	                               
	                                if (beyondPermissibleArea.compareTo(BigDecimal.ZERO) > 0) {
	                                    amount = calculateAdditionalFee(beyondPermissibleArea, BigDecimal.valueOf(bpaFee.getAmount()));
	                                }

	                            }  else if (BPA_DEMOLITION_FEE.equals(bpaFee.getBpaFeeCommon().getName())
										&& (bpaFee.getServiceType().getDescription().equalsIgnoreCase(DEMOLITION)))
									amount = calculateDemolitionFee(inputArea, BigDecimal.valueOf(bpaFee.getAmount()));
								else if (BPA_LAND_DEVELOPMENT_CHARGES.equals(bpaFee.getBpaFeeCommon().getName())
										&& (bpaFee.getServiceType().getDescription().equalsIgnoreCase(DIVISION_OF_PLOT)))
									amount = calculateLandDevelopmentCharges(inputArea, BigDecimal.valueOf(bpaFee.getAmount()));
								else if (BPA_PERMIT_FEE.equals(bpaFee.getBpaFeeCommon().getName()) && (bpaFee
										.getServiceType().getDescription().equalsIgnoreCase(PERM_FOR_HUT_OR_SHED))) {
									amount = calculatePermitFeeForHut(inputArea, BigDecimal.valueOf(bpaFee.getAmount()));
								} else if (BPA_WELL_FEE.equals(bpaFee.getBpaFeeCommon().getName())
										&& (bpaFee.getServiceType().getDescription().equalsIgnoreCase(WELL)))
									amount = calculateChargesForWell(inputArea, BigDecimal.valueOf(bpaFee.getAmount()));
								else if (BPA_COMPOUND_FEE.equals(bpaFee.getBpaFeeCommon().getName())
										&& (bpaFee.getServiceType().getDescription().equalsIgnoreCase(COMPOUND_WALL)))
									amount = calculateChargesForCompoundWall(inputArea, BigDecimal.valueOf(bpaFee.getAmount()));
								else if (SHTR_DOOR_FEE.equals(bpaFee.getBpaFeeCommon().getName()) && (bpaFee
										.getServiceType().getDescription().equalsIgnoreCase(SHUTTER_DOOR_CONVERSION)))
									amount = calculateShutterDoorConversionCharges(inputArea, BigDecimal.valueOf(bpaFee.getAmount()));
								else if (ROOF_CNVRSN_FEE.equals(bpaFee.getBpaFeeCommon().getName())
										&& (bpaFee.getServiceType().getDescription().equalsIgnoreCase(ROOF_CONVERSION)))
									amount = calculateRoofConversionCharges(inputArea, BigDecimal.valueOf(bpaFee.getAmount()));
								else if (TOWER_CONSTRUCTION_FEE.equals(bpaFee.getBpaFeeCommon().getName())
										&& (bpaFee.getServiceType().getDescription().equalsIgnoreCase(TOWER_CONSTRUCTION)))
									amount = calculateTowerConstructionCharges(inputArea, BigDecimal.valueOf(bpaFee.getAmount()));
								else if (POLE_CONSTRUCTION_FEE.equals(bpaFee.getBpaFeeCommon().getName())
										&& (bpaFee.getServiceType().getDescription().equalsIgnoreCase(POLE_STRUCTURES)))
									amount = calculatePoleStructureConstructionCharges(inputArea, BigDecimal.valueOf(bpaFee.getAmount()));
							
	                        if (SHELTERFUND.equals(bpaFee.getBpaFeeCommon().getName())
									&& inputArea.compareTo(BigDecimal.valueOf(500)) > 0
									&& inputArea.compareTo(BigDecimal.valueOf(4000)) < 0
									&& !application.getSiteDetail().get(0).getAffordableHousingScheme()) {
	                        	amount = calculateShelterFundAmount(inputArea, BigDecimal.valueOf(bpaFee.getAmount()));
	                        }
	                        if(LABOURCESS.equals(bpaFee.getBpaFeeCommon().getName()) && application.getConstructionCost() != null) {
	                        	amount = calculateLabourCessAmount(application.getConstructionCost());
	                        }
	                        
	                        if(BpaConstants.DEV_PERMIT_FEE.equals(bpaFee.getBpaFeeCommon().getCode()) && application.getBuildingDetail().size() > 1) {
	                        	amount = BigDecimal.valueOf(bpaFee.getAmount());
	                        }
	                        }
	                        
	                        if (checkIsWorkAlreadyStarted(application)) {
	                            amount = amount.multiply(BigDecimal.valueOf(3));
	                        }
	                        permitFee.getApplicationFee()
	                                .addApplicationFeeDetail(
	                                        buildApplicationFeeDetail(bpaFee, permitFee.getApplicationFee(), amount));
	                    }
	                }
	            }
	        }
	    }
	    
	    /**
	     * @param application
	     * @param serviceTypeList
	     * @param applicationFee
	     */

	    public void calculateFeeByServiceType(final BpaApplication application, final List<Long> serviceTypeList,
	            final PermitFee permitFee) {
	        if (application != null) {
	            for (Long serviceTypeId : serviceTypeList) {

	                BigDecimal beyondPermissibleArea = BigDecimal.ZERO;

	                // RESTRICT TO FEW SERVICES
	                if (!application.getBuildingDetail().isEmpty() && !application.getPermitOccupancies().isEmpty()
	                        && !application.getSiteDetail().isEmpty()
	                        && application.getSiteDetail().get(0).getExtentinsqmts() != null) {
	                    beyondPermissibleArea = calculateAreaForAdditionalFeeCalculation(application).setScale(2,
	                            BigDecimal.ROUND_HALF_UP);
	                }
	                for (BpaFeeMapping bpaFee : bpaFeeMappingService.getSanctionFeeForListOfServices(serviceTypeId)) {
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
	                            List<Occupancy> selectdOccupancies = application.getPermitOccupancies();
	                            if (BpaConstants.getBpaFeeCateory2().contains(bpaFee.getServiceType().getCode())
	                                    && (applicationBpaService.isOccupancyContains(selectdOccupancies, INDUSTRIAL)
	                                            || applicationBpaService.isOccupancyContains(selectdOccupancies, MERCANTILE_COMMERCIAL)))
	                                occupancy = selectdOccupancies.get(0).getName();
	                            else if (selectdOccupancies.size() == 1
	                                    && (RESIDENTIAL.equalsIgnoreCase(selectdOccupancies.get(0).getCode())
	                                            || APARTMENT_FLAT
	                                                    .equalsIgnoreCase(selectdOccupancies.get(0).getCode())))
	                                occupancy = RESIDENTIAL_DESC;
	                            else if (selectdOccupancies.size() == 1
	                                    && THATCHED_TILED_HOUSE
	                                                    .equalsIgnoreCase(selectdOccupancies.get(0).getCode()))
	                                occupancy = selectdOccupancies.get(0).getName();
	                            else
	                                occupancy = OTHERS;

	                           // BigDecimal feeAmount = getBpaFeeObjByOccupancyType(bpaFee.getBpaFeeCommon().getCode(), occupancy, bpaFee);

								/*
								 * if (("101").equals(bpaFee.getCode()) || ("301").equals(bpaFee.getCode()) ||
								 * ("401").equals(bpaFee.getCode()) || ("601").equals(bpaFee.getCode()) ||
								 * ("701").equals(bpaFee.getCode())) {
								 */
	                            if (BPA_PERMIT_FEE.equals(bpaFee.getBpaFeeCommon().getName())
										&& ((bpaFee.getServiceType().getDescription().equalsIgnoreCase(NEW_CONSTRUCTION))
												|| (bpaFee.getServiceType().getDescription()
														.equalsIgnoreCase(RECONSTRUCTION))
												|| (bpaFee.getServiceType().getDescription().equalsIgnoreCase(ALTERATION))
												|| (bpaFee.getServiceType().getDescription()
														.equalsIgnoreCase(ADDING_OF_EXTENSION))
												|| (bpaFee.getServiceType().getDescription()
														.equalsIgnoreCase(CHANGE_IN_OCCUPANCY)))) {
	                                BigDecimal existBldgInputArea = applicationBpaService.getExistBldgTotalFloorArea(application);
	                                if (selectdOccupancies.size() > 1) {
	                                    List<SubOccupancy> occupancies = subOccupancyService.findAllOrderByOrderNumber();
	                                    Map<String, Map<Occupancy, BigDecimal>> convertedOccupancies = new ConcurrentHashMap<>();
	                                    for (Map.Entry<String, Map<SubOccupancy, BigDecimal>> block : groupBlockOccupancyFloorArea(
	                                            application.getBuildingDetail()).entrySet()) {
	                                        for (Map.Entry<SubOccupancy, BigDecimal> blockOccupancy : block.getValue()
	                                                .entrySet()) {
	                                            Map<Occupancy, BigDecimal> convertInner;
	                                            String convertedOccu = getOccupancyAsPerFloorArea(blockOccupancy.getKey(),
	                                                    blockOccupancy.getValue());
	                                            Optional<SubOccupancy> subOccp = occupancies.stream()
	                                                    .filter(o -> o.getCode().equals(convertedOccu)).findFirst();
	                                            Occupancy occp = null;
	                                            if(subOccp.isPresent())
	                                                occp = subOccp.get().getOccupancy();
	                                            if(occp != null) {
	                                                if (convertedOccupancies.containsKey(block.getKey())) {
	                                                    Map<Occupancy, BigDecimal> existOccupancyMap = convertedOccupancies
	                                                            .get(block.getKey());
	                                                    if (existOccupancyMap.containsKey(occp)) {
	                                                        existOccupancyMap.put(occp, existOccupancyMap.get(occp)
	                                                                .add(blockOccupancy.getValue()));
	                                                        convertedOccupancies.put(block.getKey(), existOccupancyMap);
	                                                    } else {
	                                                        convertInner = new ConcurrentHashMap<>();
	                                                        convertInner.put(occp, blockOccupancy.getValue());
	                                                        convertedOccupancies.get(block.getKey()).putAll(convertInner);
	                                                    }
	                                                } else {
	                                                    convertInner = new ConcurrentHashMap<>();
	                                                    convertInner.put(occp, blockOccupancy.getValue());
	                                                    convertedOccupancies.put(block.getKey(), convertInner);
	                                                }
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
	                                                    && (RESIDENTIAL
	                                                            .equalsIgnoreCase(occupancyWiseArea.getKey().getCode())
	                                                            || APARTMENT_FLAT.equalsIgnoreCase(
	                                                                    occupancyWiseArea.getKey().getCode())))
	                                                occupancy = RESIDENTIAL_DESC;
	                                            else
	                                                occupancy = OTHERS;
	                                            // set occupancy type and get fee
	                                            // and calculate amount.
												/*
												 * feeAmount = getBpaFeeObjByOccupancyType(bpaFee.getCode(), occupancy,
												 * bpaFee);
												 */
	                                            amount = amount
	                                                    .add(calculatePermitFee(occupancyWiseArea.getValue(), BigDecimal.valueOf(bpaFee.getAmount()), false));
	                                        }
	                                    }
	                                } else {
	                                    amount = calculatePermitFee(inputArea, BigDecimal.valueOf(bpaFee.getAmount()),application.getSiteDetail().get(0).getCharitableTrustBuilding());
	                                }
	                                // CHECK WHETHER THIS APPLICABLE TO ONLY 701
	                                // OCCUPANCY TYPE.. ALSO HERE WORK
	                                // STARTED,INPROGRSS,COMPLETED TO BE CONSIDER.
	                                if (checkIsWorkAlreadyStarted(application) && BpaConstants.getServicesForValidation()
	                                        .contains(bpaFee.getServiceType().getCode())) {
	                                    amount = amount.multiply(BigDecimal.valueOf(3));
	                                } else if (checkIsEligibleForDiscountOnPermitFee(inputArea, existBldgInputArea,
	                                        bpaFee.getServiceType().getCode(), application.getPermitOccupancies())) {
	                                    amount = calculateAndGetDiscountedPermitFee(inputArea.multiply(BigDecimal.valueOf(bpaFee.getAmount()))); // 50%
	                                                                                                                // off
	                                                                                                                // if
	                                                                                                                // area
	                                    // less than 150
	                                    // mts
	                                }

								} /*
									 * else if (("102").equals(bpaFee.getCode()) || ("302").equals(bpaFee.getCode())
									 * || ("402").equals(bpaFee.getCode()) || ("602").equals(bpaFee.getCode()) ||
									 * ("702").equals(bpaFee.getCode())) {
									 */
	                            else if (BPA_ADDITIONAL_FEE.equals(bpaFee.getBpaFeeCommon().getName())
										&& ((bpaFee.getServiceType().getDescription().equalsIgnoreCase(NEW_CONSTRUCTION))
												|| (bpaFee.getServiceType().getDescription()
														.equalsIgnoreCase(RECONSTRUCTION))
												|| (bpaFee.getServiceType().getDescription().equalsIgnoreCase(ALTERATION))
												|| (bpaFee.getServiceType().getDescription()
														.equalsIgnoreCase(ADDING_OF_EXTENSION))
												|| (bpaFee.getServiceType().getDescription()
														.equalsIgnoreCase(CHANGE_IN_OCCUPANCY)))) {
	                               // feeAmount = getBpaFeeObjByOccupancyType(bpaFee.getBpaFeeCommon().getCode(), OTHERS, bpaFee);
	                                // calculate beyond permissable area tax for
	                                // other
	                                if (beyondPermissibleArea.compareTo(BigDecimal.ZERO) > 0) {
	                                    amount = calculateAdditionalFee(beyondPermissibleArea, BigDecimal.valueOf(bpaFee.getAmount()));
	                                }

	                            }  else if (BPA_DEMOLITION_FEE.equals(bpaFee.getBpaFeeCommon().getName())
										&& (bpaFee.getServiceType().getDescription().equalsIgnoreCase(DEMOLITION)))
									amount = calculateDemolitionFee(inputArea, BigDecimal.valueOf(bpaFee.getAmount()));
								else if (BPA_LAND_DEVELOPMENT_CHARGES.equals(bpaFee.getBpaFeeCommon().getName())
										&& bpaFee.getServiceType().getDescription().equalsIgnoreCase(DIVISION_OF_PLOT) 
										&& application.getBuildingDetail().size() > 1)
									amount = calculateLandDevelopmentCharges(inputArea, BigDecimal.valueOf(bpaFee.getAmount()));
								else if (BPA_PERMIT_FEE.equals(bpaFee.getBpaFeeCommon().getName()) && (bpaFee
										.getServiceType().getDescription().equalsIgnoreCase(PERM_FOR_HUT_OR_SHED))) {
									amount = calculatePermitFeeForHut(inputArea, BigDecimal.valueOf(bpaFee.getAmount()));
								} else if (BPA_WELL_FEE.equals(bpaFee.getBpaFeeCommon().getName())
										&& (bpaFee.getServiceType().getDescription().equalsIgnoreCase(WELL)))
									amount = calculateChargesForWell(inputArea, BigDecimal.valueOf(bpaFee.getAmount()));
								else if (BPA_COMPOUND_FEE.equals(bpaFee.getBpaFeeCommon().getName())
										&& (bpaFee.getServiceType().getDescription().equalsIgnoreCase(COMPOUND_WALL)))
									amount = calculateChargesForCompoundWall(inputArea, BigDecimal.valueOf(bpaFee.getAmount()));
								else if (SHTR_DOOR_FEE.equals(bpaFee.getBpaFeeCommon().getName()) && (bpaFee
										.getServiceType().getDescription().equalsIgnoreCase(SHUTTER_DOOR_CONVERSION)))
									amount = calculateShutterDoorConversionCharges(inputArea, BigDecimal.valueOf(bpaFee.getAmount()));
								else if (ROOF_CNVRSN_FEE.equals(bpaFee.getBpaFeeCommon().getName())
										&& (bpaFee.getServiceType().getDescription().equalsIgnoreCase(ROOF_CONVERSION)))
									amount = calculateRoofConversionCharges(inputArea, BigDecimal.valueOf(bpaFee.getAmount()));
								else if (TOWER_CONSTRUCTION_FEE.equals(bpaFee.getBpaFeeCommon().getName())
										&& (bpaFee.getServiceType().getDescription().equalsIgnoreCase(TOWER_CONSTRUCTION)))
									amount = calculateTowerConstructionCharges(inputArea, BigDecimal.valueOf(bpaFee.getAmount()));
								else if (POLE_CONSTRUCTION_FEE.equals(bpaFee.getBpaFeeCommon().getName())
										&& (bpaFee.getServiceType().getDescription().equalsIgnoreCase(POLE_STRUCTURES)))
									amount = calculatePoleStructureConstructionCharges(inputArea, BigDecimal.valueOf(bpaFee.getAmount()));
							}
	                        if (checkIsWorkAlreadyStarted(application)) {
	                            amount = amount.multiply(BigDecimal.valueOf(3));
	                        }
	                        permitFee.getApplicationFee()
	                                .addApplicationFeeDetail(
	                                        buildApplicationFeeDetail(bpaFee, permitFee.getApplicationFee(), amount));
	                    }
	                }
	            }
	        }
	    }

	   

	    private String getOccupancyToGetFeeAmt(Entry<Occupancy, BigDecimal> occupancyWiseArea) {
	        String occupancy;
	        if (RESIDENTIAL.equalsIgnoreCase(occupancyWiseArea.getKey().getCode())
	                || APARTMENT_FLAT.equalsIgnoreCase(occupancyWiseArea.getKey().getCode()))
	            occupancy = RESIDENTIAL;
	        else if (THATCHED_TILED_HOUSE.equalsIgnoreCase(occupancyWiseArea.getKey().getCode()))
	            occupancy = occupancyWiseArea.getKey().getCode();
	        else
	            occupancy = OTHERS;

	        return occupancy;
	    }

	    protected ApplicationFeeDetail buildApplicationFeeDetail(final BpaFeeMapping bpaFee, final ApplicationFee applicationFee,
				BigDecimal amount) {
			ApplicationFeeDetail feeDetail = new ApplicationFeeDetail();
			feeDetail.setAmount(amount.setScale(0, BigDecimal.ROUND_HALF_UP));
			feeDetail.setBpaFeeMapping(bpaFee);
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
	    
		protected BigDecimal calculatePermitFee(BigDecimal inputArea, BigDecimal feeAmount, Boolean isCharitable) {
			if(isCharitable)
			    return inputArea.multiply(feeAmount).multiply(BigDecimal.valueOf(50)).divide(BigDecimal.valueOf(100));
			else
				return inputArea.multiply(feeAmount);
		}

	    protected BigDecimal calculateShelterFundAmount(BigDecimal area, BigDecimal amount) {
	        return amount.multiply(area.multiply(TEN).divide(HUNDRED));
	    }
	    
	    protected BigDecimal calculateLabourCessAmount(BigDecimal amount) {
	        return amount.multiply(BigDecimal.valueOf(1)).divide(HUNDRED);
	    }

	    /**
	     * @param application
	     * @return is work already started or not ?
	     */
	    protected Boolean checkIsWorkAlreadyStarted(final BpaApplication application) {
	        return application.getSiteDetail().get(0).getIsappForRegularization();
	    }

	    /**
	     * @param inputUnit
	     * @param serviceTypeCode
	     * @return is eligible for permit fee 50% waive off ?
	     */
	    private Boolean checkIsEligibleForDiscountOnPermitFee(final BigDecimal inputUnit,
	            final BigDecimal existBldgInputArea, final String serviceTypeCode, final List<Occupancy> occupancies) {
	        return applicationBpaService.isOccupancyContains(occupancies, BpaConstants.RESIDENTIAL)
	                && BpaConstants.getServicesForValidation().contains(serviceTypeCode)
	                && inputUnit.add(existBldgInputArea).compareTo(BigDecimal.valueOf(150)) <= 0;
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
	     * @return after discounted amount (eligible if constructing only up to 150 sqmtrs)
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
	            inputUnit = applicationBpaService.getTotalFloorArea(application);
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
	    
	    
	    
	    protected BigDecimal getBuiltUpInputUnitForEachServiceType(final BpaApplication application, final String serviceTypeCode) {
	        BigDecimal inputUnit = BigDecimal.ZERO;

	        if (BpaConstants.getBpaFeeCateory1().contains(serviceTypeCode)) {
	            inputUnit = getTotalBuiltUpArea(application);
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

	        Map<SubOccupancy, BigDecimal> occFloorArea = getOccupancyWiseFloorArea(application.getBuildingDetail());
	        Map<SubOccupancy, BigDecimal> existBldgOccupancyWiseFloorArea = getExistBldgOccupancyWiseFloorArea(
	                application.getExistingBuildingDetails());
	        BigDecimal proposedBldgFloorArea = applicationBpaService.getTotalFloorArea(application);
	        BigDecimal existBldgFloorArea = applicationBpaService.getExistBldgTotalFloorArea(application);
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
	    
	    
	    public BigDecimal calculateBuiltUpAreaForAdditionalFeeCalculation(final BpaApplication application) {
	        BigDecimal extentOfLand = application.getSiteDetail().get(0).getExtentinsqmts();
	        BigDecimal minimumFARWithOutAdditionalFee;
	        BigDecimal minimumFARWithAdditionalFee;
	        BigDecimal weightageAvgFAR;
	        BigDecimal maximumPermittedBuiltUpAreaWithAddnFee = BigDecimal.ZERO;
	        BigDecimal maximumPermittedFARWithAdditionalFee;
	        BigDecimal maximumPermittedBuiltUpArea = BigDecimal.ZERO;
	        BigDecimal maximumPermittedFAR;
	        BigDecimal additionalFeeCalculationArea = BigDecimal.ZERO;

	        Map<SubOccupancy, BigDecimal> occBuiltUpArea = getOccupancyWiseBuiltUpArea(application.getBuildingDetail());
	        Map<SubOccupancy, BigDecimal> existBldgOccupancyWiseBuiltUpArea = getExistBldgOccupancyWiseBuiltUpArea(
	                application.getExistingBuildingDetails());
	        BigDecimal proposedBldgBuiltUpArea = getTotalBuiltUpArea(application);
	        BigDecimal existBldgBuiltUpArea = getExistBldgTotalBuiltUpArea(application);
	        BigDecimal totalBuiltUpArea = proposedBldgBuiltUpArea.add(existBldgBuiltUpArea);
	        if (totalBuiltUpArea.compareTo(BigDecimal.ZERO) > 0) {
	            if (extentOfLand.compareTo(new BigDecimal(5000)) <= 0) {

	                minimumFARWithOutAdditionalFee = minimumFARWithoutAdditionalFee(application);
	                minimumFARWithAdditionalFee = minimumFARWithAdditionalFee(application);
	                maximumPermittedFAR = minimumFARWithOutAdditionalFee.multiply(extentOfLand);

	                // Mean additional fee has to collect BUT CITIZEN NOT READY TO
	                // PAY ADDITIONAL TAX
	                // AdditionalFeePaymentAccepted condition need to be check if on
	                // submission suppose data captured
	                if (totalBuiltUpArea.compareTo(maximumPermittedFAR) > 0) {
	                    maximumPermittedFARWithAdditionalFee = minimumFARWithAdditionalFee.multiply(extentOfLand);
	                    // Calclulate additional Fee.
	                    if (totalBuiltUpArea.compareTo(maximumPermittedFARWithAdditionalFee) <= 0) {
	                        additionalFeeCalculationArea = proposedBldgBuiltUpArea.subtract(maximumPermittedFAR);
	                    }
	                }
	            } else // above area greater than 5000sq.mt.
	            {
	                weightageAvgFAR = weightageAverageFarWithoutAdditionalFee(occBuiltUpArea,
	                		existBldgOccupancyWiseBuiltUpArea);
	                if (weightageAvgFAR != null)
	                    maximumPermittedBuiltUpArea = weightageAvgFAR.multiply(extentOfLand);
	                // Mean Aggregate violation of area
	                // AdditionalFeePaymentAccepted check need to be added if
	                // require(i.e:application.getBuildingDetail().get(0).getAdditionalFeePaymentAccepted())
	                if (totalBuiltUpArea.compareTo(maximumPermittedBuiltUpArea) > 0) {
	                    weightageAvgFAR = weightageAverageFarWithAdditionalFee(occBuiltUpArea,
	                    		existBldgOccupancyWiseBuiltUpArea);
	                    if (weightageAvgFAR != null)
	                        maximumPermittedBuiltUpAreaWithAddnFee = weightageAvgFAR.multiply(extentOfLand);
	                    // Mean Aggregate violation of area
	                    if (totalBuiltUpArea.compareTo(maximumPermittedBuiltUpAreaWithAddnFee) <= 0) {
	                        // Calculate additional Fee.
	                        additionalFeeCalculationArea = proposedBldgBuiltUpArea.subtract(maximumPermittedBuiltUpArea);
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
	    public Map<SubOccupancy, BigDecimal> getOccupancyWiseFloorArea(List<BuildingDetail> buildingDetails) {
	        Map<SubOccupancy, BigDecimal> occupancyWiseFloorArea = new ConcurrentHashMap<>();
	        for (BuildingDetail building : buildingDetails) {
	            for (ApplicationFloorDetail floor : building.getApplicationFloorDetails()) {
	                if (occupancyWiseFloorArea.containsKey(floor.getSubOccupancy())) {
	                    occupancyWiseFloorArea.put(floor.getSubOccupancy(),
	                            occupancyWiseFloorArea.get(floor.getSubOccupancy()).add(floor.getFloorArea()));
	                } else {
	                    occupancyWiseFloorArea.put(floor.getSubOccupancy(), floor.getFloorArea());
	                }
	            }
	        }
	        return occupancyWiseFloorArea;
	    }
	    
	    
	    public Map<SubOccupancy, BigDecimal> getOccupancyWiseBuiltUpArea(List<BuildingDetail> buildingDetails) {
	        Map<SubOccupancy, BigDecimal> occupancyWiseBuiltUpArea = new ConcurrentHashMap<>();
	        for (BuildingDetail building : buildingDetails) {
	            for (ApplicationFloorDetail floor : building.getApplicationFloorDetails()) {
	                if (occupancyWiseBuiltUpArea.containsKey(floor.getSubOccupancy())) {
	                	occupancyWiseBuiltUpArea.put(floor.getSubOccupancy(),
	                			occupancyWiseBuiltUpArea.get(floor.getSubOccupancy()).add(floor.getPlinthArea()));
	                } else {
	                	occupancyWiseBuiltUpArea.put(floor.getSubOccupancy(), floor.getPlinthArea());
	                }
	            }
	        }
	        return occupancyWiseBuiltUpArea;
	    }
	    
	    public Map<SubOccupancy, BigDecimal> getExistBldgOccupancyWiseBuiltUpArea(List<ExistingBuildingDetail> existBldgDtls) {
	        Map<SubOccupancy, BigDecimal> occupancyWiseFloorArea = new ConcurrentHashMap<>();
	        for (ExistingBuildingDetail building : existBldgDtls) {
	            for (ExistingBuildingFloorDetail floor : building.getExistingBuildingFloorDetails()) {
	                if (occupancyWiseFloorArea.containsKey(floor.getSubOccupancy())) {
	                    occupancyWiseFloorArea.put(floor.getSubOccupancy(),
	                            occupancyWiseFloorArea.get(floor.getSubOccupancy()).add(floor.getPlinthArea()));
	                } else {
	                    occupancyWiseFloorArea.put(floor.getSubOccupancy(), floor.getPlinthArea());
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
	    public Map<SubOccupancy, BigDecimal> getExistBldgOccupancyWiseFloorArea(List<ExistingBuildingDetail> existBldgDtls) {
	        Map<SubOccupancy, BigDecimal> occupancyWiseFloorArea = new ConcurrentHashMap<>();
	        for (ExistingBuildingDetail building : existBldgDtls) {
	            for (ExistingBuildingFloorDetail floor : building.getExistingBuildingFloorDetails()) {
	                if (occupancyWiseFloorArea.containsKey(floor.getSubOccupancy())) {
	                    occupancyWiseFloorArea.put(floor.getSubOccupancy(),
	                            occupancyWiseFloorArea.get(floor.getSubOccupancy()).add(floor.getFloorArea()));
	                } else {
	                    occupancyWiseFloorArea.put(floor.getSubOccupancy(), floor.getFloorArea());
	                }
	            }
	        }
	        return occupancyWiseFloorArea;
	    }

	    public Map<String, Map<SubOccupancy, BigDecimal>> groupBlockOccupancyFloorArea(List<BuildingDetail> buildingDetails) {
	        Map<String, Map<SubOccupancy, BigDecimal>> groupByBlkOccupancyFloorArea = new ConcurrentHashMap<>();
	        for (BuildingDetail building : buildingDetails) {
	            Map<SubOccupancy, BigDecimal> subMap = new ConcurrentHashMap<>();
	            for (ApplicationFloorDetail floor : building.getApplicationFloorDetails()) {
	                if (subMap.containsKey(floor.getSubOccupancy())) {
	                    subMap.put(floor.getSubOccupancy(), subMap.get(floor.getSubOccupancy()).add(floor.getFloorArea()));
	                } else {
	                    subMap.put(floor.getSubOccupancy(), floor.getFloorArea());
	                }
	            }
	            groupByBlkOccupancyFloorArea.put(building.getName(), subMap);
	        }
	        return groupByBlkOccupancyFloorArea;
	    }
	    
	    public Map<String, Map<SubOccupancy, BigDecimal>> groupBlockOccupancyBuiltUpArea(List<BuildingDetail> buildingDetails) {
	        Map<String, Map<SubOccupancy, BigDecimal>> groupByBlkOccupancyFloorArea = new ConcurrentHashMap<>();
	        for (BuildingDetail building : buildingDetails) {
	            Map<SubOccupancy, BigDecimal> subMap = new ConcurrentHashMap<>();
	            for (ApplicationFloorDetail floor : building.getApplicationFloorDetails()) {
	                if (subMap.containsKey(floor.getSubOccupancy())) {
	                    subMap.put(floor.getSubOccupancy(), subMap.get(floor.getSubOccupancy()).add(floor.getPlinthArea()));
	                } else {
	                    subMap.put(floor.getSubOccupancy(), floor.getPlinthArea());
	                }
	            }
	            groupByBlkOccupancyFloorArea.put(building.getName(), subMap);
	        }
	        return groupByBlkOccupancyFloorArea;
	    }
	    
	    public BigDecimal getTotalBuiltUpArea(final BpaApplication application) {
	        BigDecimal totalBuiltUpArea = BigDecimal.ZERO;
	        for (BuildingDetail buildingDetail : application.getBuildingDetail())
	            for (ApplicationFloorDetail floorDetails : buildingDetail.getApplicationFloorDetails())
	            	totalBuiltUpArea = totalBuiltUpArea.add(floorDetails.getPlinthArea());
	        return totalBuiltUpArea;
	    }
	    
	    public BigDecimal getExistBldgTotalBuiltUpArea(final BpaApplication application) {
	        BigDecimal totalBuiltUpArea = BigDecimal.ZERO;
	        if (!application.getExistingBuildingDetails().isEmpty()
	                && application.getExistingBuildingDetails().get(0).getTotalPlintArea() != null)
	            for (ExistingBuildingFloorDetail floor : application.getExistingBuildingDetails().get(0)
	                    .getExistingBuildingFloorDetails()) {
	            	totalBuiltUpArea = totalBuiltUpArea.add(floor.getPlinthArea());
	            }
	        return totalBuiltUpArea;
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
	                minimumFARs.add(floor.getSubOccupancy().getMinFar());
	            }
	        }
	        if (!application.getExistingBuildingDetails().isEmpty()
	                && application.getExistingBuildingDetails().get(0).getTotalPlintArea() != null)
	            for (ExistingBuildingDetail existBldg : application.getExistingBuildingDetails())
	                for (ExistingBuildingFloorDetail floorDetails : existBldg.getExistingBuildingFloorDetails())
	                    minimumFARs.add(floorDetails.getSubOccupancy().getMaxFar());
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
	                maximumFARs.add(floor.getSubOccupancy().getMaxFar());
	            }
	        }
	        if (!application.getExistingBuildingDetails().isEmpty()
	                && application.getExistingBuildingDetails().get(0).getTotalPlintArea() != null)
	            for (ExistingBuildingDetail existBldg : application.getExistingBuildingDetails())
	                for (ExistingBuildingFloorDetail floorDetails : existBldg.getExistingBuildingFloorDetails())
	                    maximumFARs.add(floorDetails.getSubOccupancy().getMaxFar());
	        return Collections.min(maximumFARs);
	    }

	    /***
	     * Weightage Average FAR Without Additional Fee
	     * 
	     * @param occupancyWiseFloorArea
	     * @return
	     */
	    public BigDecimal weightageAverageFarWithoutAdditionalFee(Map<SubOccupancy, BigDecimal> occupancyWiseFloorArea,
	            Map<SubOccupancy, BigDecimal> existBldgOccupancyWiseFloorArea) {
	        BigDecimal maxPermittedFloorArea = BigDecimal.ZERO;
	        BigDecimal sumOfFloorArea = BigDecimal.ZERO;
	        for (Entry<SubOccupancy, BigDecimal> setOfOccupancy : occupancyWiseFloorArea.entrySet()) {
	            maxPermittedFloorArea = maxPermittedFloorArea
	                    .add(setOfOccupancy.getKey().getMinFar().multiply(setOfOccupancy.getValue()));
	            sumOfFloorArea = sumOfFloorArea.add(setOfOccupancy.getValue());
	        }
	        if (!existBldgOccupancyWiseFloorArea.isEmpty()) {
	            for (Entry<SubOccupancy, BigDecimal> occupancy : existBldgOccupancyWiseFloorArea.entrySet()) {
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
	    public BigDecimal weightageAverageFarWithAdditionalFee(Map<SubOccupancy, BigDecimal> occupancyWiseFloorArea,
	            Map<SubOccupancy, BigDecimal> existBldgOccupancyWiseFloorArea) {

	        BigDecimal maxPermittedFloorArea = BigDecimal.ZERO;
	        BigDecimal sumOfFloorArea = BigDecimal.ZERO;
	        for (Entry<SubOccupancy, BigDecimal> setOfOccupancy : occupancyWiseFloorArea.entrySet()) {
	            maxPermittedFloorArea = maxPermittedFloorArea
	                    .add(setOfOccupancy.getKey().getMaxFar().multiply(setOfOccupancy.getValue()));
	            sumOfFloorArea = sumOfFloorArea.add(setOfOccupancy.getValue());
	        }
	        if (!existBldgOccupancyWiseFloorArea.isEmpty()) {
	            for (Entry<SubOccupancy, BigDecimal> occupancy : existBldgOccupancyWiseFloorArea.entrySet()) {
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

	    public static String getOccupancyAsPerFloorArea(SubOccupancy occupancy, BigDecimal floorArea) {
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

	/*
	 * @Override public EgDemand createDemand(BpaApplication application) { return
	 * applicationBpaBillService.createDemand(application); }
	 * 
	 * @Override public EgDemand
	 * createDemandWhenFeeCollectionNotRequire(BpaApplication application) { return
	 * applicationBpaBillService.createDemandWhenFeeCollectionNotRequire(application
	 * ); }
	 */
	    
	    @Override
	    public EgDemand createDemand(final BpaApplication application) {
	        final Map<String, BigDecimal> feeDetails = new HashMap<>();
	        if (application.getApplicationType().getName().equals(LOWRISK)) {
	            applicationFeeService.setPermitFee(application, feeDetails);
	        }

	        EgDemand egDemand = null;
	        final Installment installment = installmentDao.getInsatllmentByModuleForGivenDateAndInstallmentType(
	                moduleService.getModuleByName(BpaConstants.EGMODULE_NAME), new Date(), BpaConstants.YEARLY);
	        
	        List<BpaFeeMapping> bpaAdmissionFees = bpaFeeMappingService
	                .getFeeForListOfServices(application.getServiceType().getId(), BpaConstants.BPA_APP_FEE);
   		   if(application.getApplicationType().getId() != null) 
   			  application.setAdmissionfeeAmount(setAdmissionFeeAmount(application, new ArrayList<>()));
         
	        
	        feeDetails.put(bpaAdmissionFees.get(0).getBpaFeeCommon().getCode(), application.getAdmissionfeeAmount());
	        BigDecimal baseDemandAmount = feeDetails.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);  
	        if (installment != null) {
	            final Set<EgDemandDetails> dmdDetailSet = new HashSet<>();
	            for (final Entry<String, BigDecimal> demandReason : feeDetails.entrySet())
	                dmdDetailSet.add(createDemandDetails(feeDetails.get(demandReason.getKey()), demandReason.getKey(), installment));
	            egDemand = new EgDemand();
	            egDemand.setEgInstallmentMaster(installment);
	            egDemand.getEgDemandDetails().addAll(dmdDetailSet);
	            egDemand.setIsHistory("N");
	            egDemand.setBaseDemand(baseDemandAmount);
	            egDemand.setCreateDate(new Date());
	            egDemand.setModifiedDate(new Date());
	        }

	        return egDemand;
	    }
	    
	    public EgDemand createDemandWhenFeeCollectionNotRequire(BpaApplication application) {
	        
	        
	        EgDemand egDemand = new EgDemand();
	         final Set<EgDemandDetails> dmdDetailSet = new HashSet<>();

	        final Installment installment = installmentDao.getInsatllmentByModuleForGivenDateAndInstallmentType(
	                moduleService.getModuleByName(BpaConstants.EGMODULE_NAME), new Date(), BpaConstants.YEARLY);
	        Map<String, BigDecimal> feeDetails = new  HashMap<>();
	        if (application.getApplicationType().getName().equals(LOWRISK) && installment!=null) {
	        	applicationFeeService.setPermitFee(application, feeDetails);
	         for (final Entry<String, BigDecimal> demandReason : feeDetails.entrySet())
	             dmdDetailSet.add(createDemandDetails(feeDetails.get(demandReason.getKey()), demandReason.getKey(), installment));
	        }
	        egDemand.setEgInstallmentMaster(installment);
	        egDemand.setIsHistory("N");
	        egDemand.setCreateDate(new Date());
	        if (application.getApplicationType().getName().equals(LOWRISK)) {
	            egDemand.getEgDemandDetails().addAll(dmdDetailSet);
	            egDemand.setBaseDemand(feeDetails.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add));
	        }
	        else {
	         egDemand.setEgDemandDetails(new HashSet<>());
	         egDemand.setBaseDemand(BigDecimal.ZERO);
	        }

	        egDemand.setModifiedDate(new Date());
	        return egDemand;
	    }
	    
	    private EgDemandDetails createDemandDetails(final BigDecimal amount, final String demandReason,
	            final Installment installment) {
	        final EgDemandReason demandReasonObj = bpaDemandService.getDemandReasonByCodeAndInstallment(demandReason,
	                installment);
	        final EgDemandDetails demandDetail = new EgDemandDetails();
	        demandDetail.setAmount(amount);
	        demandDetail.setAmtCollected(BigDecimal.ZERO);
	        demandDetail.setAmtRebate(BigDecimal.ZERO);
	        demandDetail.setEgDemandReason(demandReasonObj);
	        demandDetail.setCreateDate(new Date());
	        demandDetail.setModifiedDate(new Date());
	        return demandDetail;
	    }
}