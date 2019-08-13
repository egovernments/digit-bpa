/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2018>  eGovernments Foundation
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
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.egov.bpa.transaction.entity.ApplicationFeeDetail;
import org.egov.bpa.transaction.entity.PermitFee;
import org.egov.bpa.transaction.entity.PermitRenewal;
import org.egov.bpa.transaction.notice.util.BpaNoticeUtil;
import org.egov.bpa.utils.BpaAppConfigUtil;
import org.egov.infra.utils.DateUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author vinoth
 *
 */
@Service
@Transactional(readOnly = true)
public class PermitRenewalFeeCalculationService {

    @Autowired
    private PermitRenewalService permitRenewalService;
    @Autowired
    private BpaAppConfigUtil appConfigUtil;
    @Autowired
    private BpaNoticeUtil bpaNoticeUtil;

    public Map<String, BigDecimal> calculateRenewalFee(final PermitRenewal permitRenewal) {
        Map<String, BigDecimal> demandReasonAndAmt = new ConcurrentHashMap<>();
        BigDecimal totalPermitRenewalFee = BigDecimal.ZERO;
        BigDecimal totalPermitFee = getPermitFee(permitRenewal);
        String demandReasonCode = "PEF";
        Date permitExpiryDate = DateUtils.toDateUsingDefaultPattern(bpaNoticeUtil.calculateCertExpryDate(
                new DateTime(permitRenewal.getParent().getPlanPermissionDate()),
                permitRenewal.getParent().getServiceType().getValidity()));
        Date minAllowedRenewalDate = DateUtils.toDateUsingDefaultPattern(
                permitRenewalService.getMinAllowedDateForRenewalPriorExpiry(permitRenewal.getParent()));
        Date maxAllowedRenewalDate = DateUtils.toDateUsingDefaultPattern(
                permitRenewalService.getMaxAllowedDateForRenewalAfterExpiry(permitRenewal.getParent()));
        if (permitRenewal.getApplicationDate().after(minAllowedRenewalDate)
                && permitRenewal.getApplicationDate().before(permitExpiryDate)) {
            BigDecimal permitExtensionFee = appConfigUtil.getPermitExtensionFeeInPercentage();
            totalPermitRenewalFee = totalPermitFee.multiply(permitExtensionFee)
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            demandReasonCode = "PEF";
        } else if (permitRenewal.getApplicationDate().after(minAllowedRenewalDate)
                && permitRenewal.getApplicationDate().before(maxAllowedRenewalDate)) {
            BigDecimal permitRenewalFeeInPercent = appConfigUtil.getPermitRenewalFeeInPercentage();
            totalPermitRenewalFee = totalPermitFee.multiply(permitRenewalFeeInPercent)
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            demandReasonCode = "PRF";
        }
        demandReasonAndAmt.put(demandReasonCode, totalPermitRenewalFee.setScale(0, BigDecimal.ROUND_HALF_UP));
        return demandReasonAndAmt;
    }

    private BigDecimal getPermitFee(final PermitRenewal permitRenewal) {
        BigDecimal totalPermitFee = BigDecimal.ZERO;
        if (!permitRenewal.getParent().getPermitFee().isEmpty()) {
            for (PermitFee permitFee : permitRenewal.getParent().getPermitFee()) {
                for (ApplicationFeeDetail feeDetail : permitFee.getApplicationFee().getApplicationFeeDetail()) {
                    totalPermitFee = totalPermitFee.add(feeDetail.getAmount());
                }
            }
        }
        return totalPermitFee;
    }
}
