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
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.egov.bpa.master.entity.BpaFeeMapping;
import org.egov.bpa.master.entity.enums.FeeApplicationType;
import org.egov.bpa.master.service.BpaFeeMappingService;
import org.egov.bpa.transaction.entity.ApplicationFee;
import org.egov.bpa.transaction.entity.ApplicationFeeDetail;
import org.egov.bpa.transaction.entity.OwnershipFee;
import org.egov.bpa.transaction.entity.OwnershipTransfer;
import org.egov.bpa.transaction.service.collection.BpaDemandService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.commons.Installment;
import org.egov.commons.dao.InstallmentDao;
import org.egov.demand.model.EgDemand;
import org.egov.demand.model.EgDemandDetails;
import org.egov.demand.model.EgDemandReason;
import org.egov.infra.admin.master.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class OwnershipFeeCalculationService {

	    @Autowired
	    private OwnershipFeeService ownershipFeeService;
		@Autowired
		private BpaFeeMappingService bpaFeeMappingService;
		@Autowired
		protected ApplicationBpaService applicationBpaService;		
		@Autowired
		private InstallmentDao installmentDao;
		@Autowired
		private ModuleService moduleService;	
		@Autowired
	    private BpaDemandService bpaDemandService;
		
	    public BigDecimal calculateAdmissionFeeAmount(Long serviceTypeId) {
	    	BigDecimal amount = BigDecimal.ZERO;
	    	
	    	List<BpaFeeMapping> appSubTypeFee = bpaFeeMappingService.getApplicationFeeByServiceAppType(serviceTypeId, BpaConstants.BPA_APP_FEE, FeeApplicationType.OWNERSHIP_TRANSFER);
	    	for (final BpaFeeMapping feeMap : appSubTypeFee) {
	    		amount = amount.add(BigDecimal.valueOf(feeMap.getAmount()));
	    	}	    	
	    	return amount;
	    }
        	    
	    
	    protected OwnershipFee getbpaFee(final OwnershipTransfer ownershipTransfer) {
	    	OwnershipFee ownershipFee = null;
	        if (ownershipTransfer != null) {
	            List<OwnershipFee> ownershipFeeList = ownershipFeeService
	                    .getOwnershipFeeListByApplicationId(ownershipTransfer.getId());
	            if (ownershipFeeList.isEmpty()) {
	                ownershipFee = new OwnershipFee();
	                ownershipFee.setApplicationFee(new ApplicationFee());
	                ownershipFee.setOwnershipTransfer(ownershipTransfer);
	                return ownershipFee;
	            } else {
	                return ownershipFeeList.get(0);
	            }
	        }
	        return ownershipFee;
	    }

	    public OwnershipFee calculateOwnershipSanctionFees(final OwnershipTransfer ownershipTransfer) {	       	       
	        OwnershipFee ownershipFee = getbpaFee(ownershipTransfer);
	       
	        if (ownershipFee.getApplicationFee().getApplicationFeeDetail().isEmpty() && ownershipTransfer != null) {
	        	for (BpaFeeMapping bpaFee : bpaFeeMappingService.getSanctionFeesByServiceAppType(ownershipTransfer.getParent().getServiceType().getId(), BpaConstants.OWNERSHIP_FEE, FeeApplicationType.OWNERSHIP_TRANSFER)) {
                    if (bpaFee != null) {                            
                        ownershipFee.getApplicationFee()
                                .addApplicationFeeDetail(
                                        buildApplicationFeeDetail(bpaFee, ownershipFee.getApplicationFee(), BigDecimal.valueOf(bpaFee.getAmount())));
                    }
                }	        
	        }
	        return ownershipFee;
	    }	    
	   

	    protected ApplicationFeeDetail buildApplicationFeeDetail(final BpaFeeMapping bpaFee, final ApplicationFee applicationFee,
				BigDecimal amount) {
			ApplicationFeeDetail feeDetail = new ApplicationFeeDetail();
			feeDetail.setAmount(amount.setScale(0, BigDecimal.ROUND_HALF_UP));
			feeDetail.setBpaFeeMapping(bpaFee);
			feeDetail.setApplicationFee(applicationFee);
			return feeDetail;
		}
	    
	    public EgDemand createDemand(final OwnershipTransfer ownershipTransfer) {
	        final Map<String, BigDecimal> feeDetails = new HashMap<>();

	        EgDemand egDemand = null;
	        final Installment installment = installmentDao.getInsatllmentByModuleForGivenDateAndInstallmentType(
	                moduleService.getModuleByName(BpaConstants.EGMODULE_NAME), new Date(), BpaConstants.YEARLY);
	        
	        List<BpaFeeMapping> bpaAdmissionFees = bpaFeeMappingService
	                .getApplicationFeeByServiceAppType(ownershipTransfer.getParent().getServiceType().getId(), BpaConstants.BPA_APP_FEE, FeeApplicationType.OWNERSHIP_TRANSFER);
	        ownershipTransfer.setAdmissionfeeAmount(calculateAdmissionFeeAmount(ownershipTransfer.getParent().getServiceType().getId()));
         
	        
	        feeDetails.put(bpaAdmissionFees.get(0).getBpaFeeCommon().getCode(), ownershipTransfer.getAdmissionfeeAmount());
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
	    
	    public EgDemand createDemandWhenFeeCollectionNotRequire(OwnershipTransfer ownershipTransfer) {
	        
	        
	        EgDemand egDemand = new EgDemand();
	         final Set<EgDemandDetails> dmdDetailSet = new HashSet<>();

	        final Installment installment = installmentDao.getInsatllmentByModuleForGivenDateAndInstallmentType(
	                moduleService.getModuleByName(BpaConstants.EGMODULE_NAME), new Date(), BpaConstants.YEARLY);
	        Map<String, BigDecimal> feeDetails = new  HashMap<>();

	         for (final Entry<String, BigDecimal> demandReason : feeDetails.entrySet())
	             dmdDetailSet.add(createDemandDetails(feeDetails.get(demandReason.getKey()), demandReason.getKey(), installment));
	         
	        egDemand.setEgInstallmentMaster(installment);
	        egDemand.setIsHistory("N");
	        egDemand.setCreateDate(new Date());	        
	        egDemand.setEgDemandDetails(new HashSet<>());
	        egDemand.setBaseDemand(BigDecimal.ZERO);
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