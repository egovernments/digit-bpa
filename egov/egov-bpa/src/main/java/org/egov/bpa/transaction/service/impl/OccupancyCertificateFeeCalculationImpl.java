/*
 * eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 * accountability and the service delivery of the government  organizations.
 *
 *  Copyright (C) <2019>  eGovernments Foundation
 *
 *  The updated version of eGov suite of products as by eGovernments Foundation
 *  is available at http://www.egovernments.org
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see http://www.gnu.org/licenses/ or
 *  http://www.gnu.org/licenses/gpl.html .
 *
 *  In addition to the terms of the GPL license to be adhered to in using this
 *  program, the following additional terms are to be complied with:
 *
 *      1) All versions of this program, verbatim or modified must carry this
 *         Legal Notice.
 *      Further, all user interfaces, including but not limited to citizen facing interfaces,
 *         Urban Local Bodies interfaces, dashboards, mobile applications, of the program and any
 *         derived works should carry eGovernments Foundation logo on the top right corner.
 *
 *      For the logo, please refer http://egovernments.org/html/logo/egov_logo.png.
 *      For any further queries on attribution, including queries on brand guidelines,
 *         please contact contact@egovernments.org
 *
 *      2) Any misrepresentation of the origin of the material is prohibited. It
 *         is required that all modified versions of this material be marked in
 *         reasonable ways as different from the original version.
 *
 *      3) This license does not grant any rights to any user of the program
 *         with regards to rights under trademark law for use of the trade names
 *         or trademarks of eGovernments Foundation.
 *
 *  In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */
package org.egov.bpa.transaction.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.egov.bpa.master.entity.BpaFee;
import org.egov.bpa.master.entity.BpaFeeDetail;
import org.egov.bpa.master.service.BpaFeeService;
import org.egov.bpa.transaction.entity.ApplicationFee;
import org.egov.bpa.transaction.entity.ApplicationFeeDetail;
import org.egov.bpa.transaction.entity.oc.OccupancyFee;
import org.egov.bpa.transaction.entity.oc.OccupancyCertificate;
import org.egov.bpa.transaction.service.OccupancyCertificateFeeCalculation;
import org.egov.bpa.transaction.service.oc.OccupancyFeeService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.bpa.utils.BpaUtils;
import org.egov.commons.service.OccupancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class OccupancyCertificateFeeCalculationImpl implements OccupancyCertificateFeeCalculation {
    private static final String TOTAL_FLOOR_AREA = "totalFloorArea";
    private static final String OTHERS = "Others";

    @Autowired
    protected BpaUtils bpaUtils;

    @Autowired
    protected OccupancyService occupancyService;

    @Autowired
    protected OccupancyFeeService ocFeeService;

    @Autowired
    protected BpaFeeService bpaFeeService;

    // .setScale(0, BigDecimal.ROUND_HALF_UP)

    private OccupancyFee getOCFee(final OccupancyCertificate oc) {
        OccupancyFee ocFee = null;
        if (oc != null) {
            List<OccupancyFee> ocFeeList = ocFeeService
                    .getOCFeeListByApplicationId(oc.getId());
            if (ocFeeList.isEmpty()) {
                ocFee = new OccupancyFee();
                ocFee.setApplicationFee(new ApplicationFee());
                ocFee.setOc(oc);
                return ocFee;
            } else {
                return ocFeeList.get(0);
            }
        }
        return ocFee;
    }

    @Override
    public OccupancyFee calculateOCSanctionFees(final OccupancyCertificate oc) {

        OccupancyFee ocFee = getOCFee(oc);
        // If record rejected and recalculation required again, then this logic
        // has to be change.
        if (ocFee.getApplicationFee().getApplicationFeeDetail().isEmpty()) {
            calculateOCFees(oc, ocFee);
        }

        return ocFee;
    }

    public void calculateOCFees(OccupancyCertificate oc, OccupancyFee ocFee) {
        Map<String, BigDecimal> ocProposedArea = bpaUtils.getProposedBuildingAreasOfOC(oc.getBuildings());
        Map<String, BigDecimal> totalProposedArea = bpaUtils.getTotalProposedArea(oc.getParent().getBuildingDetail());
        BigDecimal ocFloorArea = ocProposedArea.get(TOTAL_FLOOR_AREA) != null ? ocProposedArea.get(TOTAL_FLOOR_AREA)
                : BigDecimal.ZERO;
        BigDecimal proposedFloorArea = totalProposedArea.get(TOTAL_FLOOR_AREA) != null ? totalProposedArea.get(TOTAL_FLOOR_AREA)
                : BigDecimal.ZERO;
        BigDecimal maxPermittedFloorArea = proposedFloorArea.multiply(BigDecimal.valueOf(5)).divide(BigDecimal.valueOf(100))
                .add(proposedFloorArea);
        if (ocFloorArea.compareTo(proposedFloorArea) > 0 && ocFloorArea.compareTo(maxPermittedFloorArea) < 0) {

            BigDecimal deviatedArea = ocFloorArea.subtract(proposedFloorArea);
            calculateFeeByServiceType(oc, deviatedArea, ocFee);
        }
    }

    public void calculateFeeByServiceType(OccupancyCertificate oc, BigDecimal deviatedArea, OccupancyFee ocFee) {
        BigDecimal feeAmount;
        for (BpaFee bpaFee : bpaFeeService.getActiveOCSanctionFeeForListOfServices(oc.getParent().getServiceType().getId())) {
            String occupancy;
            if ((BpaConstants.RESIDENTIAL
                    .equalsIgnoreCase(oc.getParent().getOccupancy().getCode())
                    || BpaConstants.APARTMENT_FLAT.equalsIgnoreCase(
                            oc.getParent().getOccupancy().getCode())))
                occupancy = BpaConstants.RESIDENTIAL;
            else
                occupancy = OTHERS;
            // set occupancy type and get fee
            // and calculate amount.
            feeAmount = getBpaFeeObjByOccupancyType(bpaFee.getCode(), occupancy,
                    bpaFee);
            BigDecimal amount = deviatedArea.multiply(feeAmount).multiply(BigDecimal.valueOf(3));
            ocFee.getApplicationFee()
                    .addApplicationFeeDetail(buildApplicationFeeDetail(bpaFee, ocFee.getApplicationFee(), amount));
        }
    }

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

    private ApplicationFeeDetail buildApplicationFeeDetail(final BpaFee bpaFee, final ApplicationFee applicationFee,
            BigDecimal amount) {
        ApplicationFeeDetail feeDetail = new ApplicationFeeDetail();
        feeDetail.setAmount(amount.setScale(0, BigDecimal.ROUND_HALF_UP));
        feeDetail.setBpaFee(bpaFee);
        feeDetail.setApplicationFee(applicationFee);
        return feeDetail;
    }
}