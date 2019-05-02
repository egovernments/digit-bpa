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

package org.egov.bpa.transaction.service.oc;

import static org.egov.bpa.utils.BpaConstants.SCALING_FACTOR;

import java.math.BigDecimal;
import java.util.List;

import org.egov.bpa.transaction.entity.ApplicationFloorDetail;
import org.egov.bpa.transaction.entity.BuildingDetail;
import org.egov.bpa.transaction.entity.oc.OCBuilding;
import org.egov.bpa.transaction.entity.oc.OCFloor;
import org.egov.bpa.transaction.entity.oc.OccupancyCertificate;
import org.egov.bpa.utils.BpaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class OccupancyCertificateValidationService {
	
    private static final String OC_COMPARISON_VALIDATION = "OcComparisonValidation";
	
	@Autowired
	@Qualifier("parentMessageSource")
	private MessageSource bpaMessageSource;
	@Autowired
    private BpaUtils bpaUtils;
	
	public Boolean validateOcApplnWithPermittedBpaAppln(final Model model, final OccupancyCertificate occupancyCertificate) {
		
		if(!validateOcApplnWithPermittedAppln(model, occupancyCertificate) ||
				!validateBuildingHeight(occupancyCertificate.getParent().getBuildingDetail(), occupancyCertificate.getBuildings(), model))
			return false;
		
      return true;
	}
	
	public Boolean validateOcApplnWithPermittedAppln(final Model model, final OccupancyCertificate occupancyCertificate) {

        Boolean isValid = Boolean.FALSE;
        List<OCBuilding> ocBuildings = occupancyCertificate.getBuildings();
        List<BuildingDetail> permitBuildings = occupancyCertificate.getParent().getBuildingDetail();

        // 1.Validate plot area
        double ocPlotArea = occupancyCertificate.getExtentInSqmts().setScale(SCALING_FACTOR, BigDecimal.ROUND_HALF_UP)
                .doubleValue();
        double permitPlotArea = occupancyCertificate.getParent().getSiteDetail().get(0).getExtentinsqmts()
                .setScale(SCALING_FACTOR, BigDecimal.ROUND_HALF_UP).doubleValue();
        if (ocPlotArea > permitPlotArea) {
            model.addAttribute(OC_COMPARISON_VALIDATION,
                    bpaMessageSource.getMessage("msg.oc.comp.plot.area.more",
                            new String[] { String.valueOf(ocPlotArea), String.valueOf(permitPlotArea) },
                            LocaleContextHolder.getLocale()));
            return isValid;
        }

        // check number of blocks same or not
        int ocExistingBldgSize = occupancyCertificate.getExistingBuildings().size();
        int permitExistingBldgSize = occupancyCertificate.getParent().getExistingBuildingDetails().size();
        int totalOcBldgs = ocBuildings.size() + ocExistingBldgSize;
        int totalPermitBldgs = permitBuildings.size() + permitExistingBldgSize;
        if (ocBuildings.size() > permitBuildings.size()) {
            model.addAttribute(OC_COMPARISON_VALIDATION,
                    bpaMessageSource.getMessage("msg.oc.comp.blks.count.not.match",
                            new String[] { String.valueOf(totalOcBldgs), String.valueOf(totalPermitBldgs) },
                            LocaleContextHolder.getLocale()));
            return isValid;
        }

        // Validate block's number is same
        for (OCBuilding oc : ocBuildings) {
            boolean buildingExist = false;
            Integer permitBldgNo = 0;
            for (BuildingDetail bpa : permitBuildings) {
                permitBldgNo = bpa.getNumber();
                if (oc.getBuildingNumber().equals(bpa.getNumber()))
                    buildingExist = true;
            }
            if (!buildingExist) {
                model.addAttribute(OC_COMPARISON_VALIDATION,
                        bpaMessageSource.getMessage("msg.oc.comp.blk.no.not.match",
                                new String[] { String.valueOf(oc.getBuildingNumber()), String.valueOf(permitBldgNo) },
                                LocaleContextHolder.getLocale()));
                return isValid;
            }
        }

        // check the floor count for particular building same or not
        for (OCBuilding oc : ocBuildings) {
            int ocFloorsCount = oc.getFloorDetailsForUpdate().size();
            for (BuildingDetail bpa : permitBuildings) {
                int permitFloorsCount = bpa.getApplicationFloorDetails().size();
                if (oc.getBuildingNumber().equals(bpa.getNumber()) && ocFloorsCount > permitFloorsCount) {
                    model.addAttribute(OC_COMPARISON_VALIDATION,
                            bpaMessageSource.getMessage("msg.oc.comp.blk.flr.count.not.match",
                                    new String[] { String.valueOf(oc.getBuildingNumber()), String.valueOf(ocFloorsCount),
                                            String.valueOf(bpa.getNumber()), String.valueOf(permitFloorsCount) },
                                    LocaleContextHolder.getLocale()));
                    return isValid;
                }
            }
        }

        BigDecimal hundred = new BigDecimal(100);
        BigDecimal percent = new BigDecimal(bpaUtils.getAppConfigForOcAllowDeviation());
        // check floor wise occupancy same are not
        for (OCBuilding oc : ocBuildings) {
            for (BuildingDetail bpa : permitBuildings) {
                if (oc.getBuildingNumber().equals(bpa.getNumber())) {
                    for (OCFloor ocFloor : oc.getFloorDetailsForUpdate()) {
                        int floorNumber = ocFloor.getFloorNumber();
                        for (ApplicationFloorDetail bpaFloor : bpa.getApplicationFloorDetails()) {
                            if (floorNumber == bpaFloor.getFloorNumber()
                                    && !ocFloor.getSubOccupancy().getCode().equals(bpaFloor.getSubOccupancy().getCode())) {
                                model.addAttribute(OC_COMPARISON_VALIDATION,
                                        bpaMessageSource.getMessage("msg.oc.comp.blk.flr.occupancy.not.match",
                                                new String[] { String.valueOf(oc.getBuildingNumber()),
                                                        String.valueOf(floorNumber), ocFloor.getSubOccupancy().getDescription(),
                                                        String.valueOf(bpa.getNumber()),
                                                        String.valueOf(bpaFloor.getFloorNumber()),
                                                        bpaFloor.getSubOccupancy().getDescription() },
                                                LocaleContextHolder.getLocale()));
                                return isValid;
                            }
                        }
                    }
                }
            }
        }
        // check block wise floor area same are not
        BigDecimal limitSqurMtrs = new BigDecimal(40);
        for (OCBuilding oc : ocBuildings) {
            for (BuildingDetail bpa : permitBuildings) {
                if (oc.getBuildingNumber().equals(bpa.getNumber())) {
                    BigDecimal totalOcFloor = getOcTotalFloorArea(oc.getFloorDetailsForUpdate());
                    BigDecimal totalBpaFloor = getBpaTotalFloorArea(bpa.getApplicationFloorDetails());
                    BigDecimal allowDeviation = totalBpaFloor.multiply(percent).divide(hundred);
                    BigDecimal totalBpaWithAllowDeviation = totalBpaFloor.add(allowDeviation);
                    if (totalBpaWithAllowDeviation.compareTo(totalOcFloor) < 0) {
                        model.addAttribute(OC_COMPARISON_VALIDATION,
                                bpaMessageSource.getMessage("msg.oc.comp.blk.area.not.match1",
                                        new String[] { String.valueOf(oc.getBuildingNumber()), String.valueOf(totalOcFloor),
                                                String.valueOf(bpa.getNumber()), String.valueOf(totalBpaFloor) },
                                        LocaleContextHolder.getLocale()));
                        return isValid;
                    }
                    if (totalOcFloor.subtract(totalBpaFloor).compareTo(limitSqurMtrs) > 0) {
                        model.addAttribute(OC_COMPARISON_VALIDATION,
                                bpaMessageSource.getMessage("msg.oc.comp.blk.area.not.match2",
                                        new String[] { String.valueOf(oc.getBuildingNumber()), String.valueOf(totalOcFloor),
                                                String.valueOf(bpa.getNumber()), String.valueOf(totalBpaFloor) },
                                        LocaleContextHolder.getLocale()));
                        return isValid;
                    }
                }
            }
        }
        return true;
    }
	
	private Boolean validateBuildingHeight(List<BuildingDetail> permitBuildings, List<OCBuilding> ocBuildings, final Model model) {
      for (OCBuilding oc : ocBuildings) {
        boolean isHeightSame = false;
        BigDecimal permitBldgHgt = BigDecimal.ZERO;
        for (BuildingDetail bpa : permitBuildings) {
            permitBldgHgt = bpa.getHeightFromGroundWithOutStairRoom().setScale(SCALING_FACTOR, BigDecimal.ROUND_HALF_UP);
            if (oc.getHeightFromGroundWithOutStairRoom().setScale(SCALING_FACTOR, BigDecimal.ROUND_HALF_UP)
                    .doubleValue() <= permitBldgHgt.doubleValue())
                isHeightSame = true;
        }
        if (!isHeightSame) {
            model.addAttribute(OC_COMPARISON_VALIDATION,
                    bpaMessageSource.getMessage("msg.oc.comp.blk.hgt.not.match",
                            new String[] {
                                    String.valueOf(oc.getHeightFromGroundWithOutStairRoom().setScale(SCALING_FACTOR,
                                            BigDecimal.ROUND_HALF_UP)),
                                    String.valueOf(permitBldgHgt) },
                            LocaleContextHolder.getLocale()));
            return false;
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