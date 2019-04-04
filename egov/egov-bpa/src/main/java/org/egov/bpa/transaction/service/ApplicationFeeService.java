/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
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
package org.egov.bpa.transaction.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.egov.bpa.autonumber.ApplicationFeeNumberGenerator;
import org.egov.bpa.transaction.entity.ApplicationFee;
import org.egov.bpa.transaction.entity.ApplicationFeeDetail;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.BpaStatus;
import org.egov.bpa.transaction.entity.PermitFee;
import org.egov.bpa.transaction.repository.ApplicationFeeRepository;
import org.egov.bpa.transaction.repository.PermitFeeRepository;
import org.egov.bpa.utils.BpaConstants;
import org.egov.infra.custom.CustomImplProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ApplicationFeeService {

    private static final Logger LOGGER = Logger.getLogger(ApplicationFeeService.class);
    @Autowired
    private ApplicationFeeRepository applicationFeeRepository;

    @Autowired
    private BpaStatusService bpaStatusService;
    @Autowired
    private ApplicationFeeNumberGenerator applicationFeeNumberGenerator;
    @Autowired
    private CustomImplProvider specificNoticeService;
	@Autowired
    private ApplicationFeeService applicationFeeService;
    @Autowired
	private PermitFeeRepository permitFeeRepository;

    @Transactional
    public ApplicationFee saveApplicationFee(ApplicationFee applicationFee) {
        LOGGER.debug("Enter saveApplicationFee");
        if (applicationFee != null && applicationFee.getFeeDate() == null)
            applicationFee.setFeeDate(new Date());

        if (applicationFee != null && applicationFee.getId() == null) {
            applicationFee.setChallanNumber(applicationFeeNumberGenerator.generateApplicationFeeNumber(applicationFee));
            BpaStatus status = bpaStatusService.findByModuleTypeAndCode(BpaConstants.BPASTATUS_APPLICATIONFEE_MODULE,
                    BpaConstants.APPROVED);
            applicationFee.setStatus(status);
        }
        applicationFee.setIsRevised(Boolean.FALSE);
        List<ApplicationFeeDetail> applicationFeeDetailsSet = new ArrayList<>();

        for (ApplicationFeeDetail applicationFeeDtl : applicationFee.getApplicationFeeDetail()) {
            applicationFeeDtl.setAmount(applicationFeeDtl.getAmount() != null ? applicationFeeDtl.getAmount().setScale(0, BigDecimal.ROUND_HALF_UP) : BigDecimal.ZERO);
            applicationFeeDtl.setApplicationFee(applicationFee);
            if (applicationFee != null && applicationFee.getId() == null) {
                applicationFeeDetailsSet.add(applicationFeeDtl);
            }
        }
        if (applicationFee != null && applicationFee.getId() == null) {
            applicationFee.setApplicationFeeDetail(applicationFeeDetailsSet);
        }
        applicationFeeRepository.save(applicationFee);
        LOGGER.debug("Saved saveApplicationFee");
        return applicationFee;
    }
    
    
    public void setPermitFee(BpaApplication application, Map<String, BigDecimal> feeDetails) {
    	ApplicationBpaFeeCalculation feeCalculation = (ApplicationBpaFeeCalculation) specificNoticeService
                .find(PermitFeeCalculationService.class, specificNoticeService.getCityDetails());
        PermitFee permitFee = feeCalculation.calculateBpaSanctionFees(application);
        ApplicationFee applicationFee = applicationFeeService.saveApplicationFee(permitFee.getApplicationFee());
        permitFee.setApplicationFee(applicationFee);
        permitFeeRepository.save(permitFee);
        for(ApplicationFeeDetail appFee : applicationFee.getApplicationFeeDetail()) {
                feeDetails.put(appFee.getBpaFeeMapping().getBpaFeeCommon().getCode(),appFee.getAmount());            
        }
    }

}
