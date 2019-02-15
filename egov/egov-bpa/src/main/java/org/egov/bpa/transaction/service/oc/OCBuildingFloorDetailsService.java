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
package org.egov.bpa.transaction.service.oc;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.egov.bpa.transaction.entity.oc.OCBuilding;
import org.egov.bpa.transaction.entity.oc.OCFloor;
import org.egov.bpa.transaction.entity.oc.OccupancyCertificate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class OCBuildingFloorDetailsService {

	public void buildProposedBuildingFloorDetails(final OccupancyCertificate oc) {
		if (!oc.getBuildings().isEmpty()) {
			for (OCBuilding bldg : oc.getBuildings()) {
				buildNewlyAddedFloorDetails(bldg);
				if (!bldg.getFloorDetails().isEmpty()) {
					List<OCFloor> floorDetailsList = new ArrayList<>();
					bldg.setOc(oc);
					for (OCFloor floor : bldg.getFloorDetails()) {
						if (null != floor && null == floor.getId() && floor.getFloorDescription() != null) {
							OCFloor floorDetails = new OCFloor();
							floorDetails.setBuildingDetails(bldg);
							floorDetails.setSubOccupancy(floor.getSubOccupancy());
							floorDetails.setOrderOfFloor(floor.getOrderOfFloor());
							floorDetails.setFloorNumber(floor.getFloorNumber());
							floorDetails.setFloorDescription(floor.getFloorDescription());
							floorDetails.setPlinthArea(floor.getPlinthArea());
							floorDetails.setCarpetArea(floor.getCarpetArea());
							floorDetails.setFloorArea(floor.getFloorArea());
							floorDetailsList.add(floorDetails);
						} else if (null != floor && null != floor.getId() && floor.getFloorDescription() != null) {
							floorDetailsList.add(floor);
						}
					}
					bldg.getFloorDetails().clear();
					bldg.setFloorDetails(floorDetailsList);
				}
			}

			validateAndBuildBuildingDetails(oc);
		}
	}

	private void validateAndBuildBuildingDetails(final OccupancyCertificate oc) {
		List<OCBuilding> newBuildingDetailsList = new ArrayList<>();
		for (OCBuilding buildingDetail : oc.getBuildings())
			if (buildingDetail != null && null != buildingDetail.getOc() && null != buildingDetail.getTotalPlinthArea())
				newBuildingDetailsList.add(buildingDetail);
		oc.getBuildings().clear();
		if (!newBuildingDetailsList.isEmpty())
			oc.setBuildings(newBuildingDetailsList);
	}

	public void buildNewlyAddedFloorDetails(final OCBuilding bldg) {
		List<OCFloor> newFloorDetails = new ArrayList<>();
		for (OCFloor floorDetail : bldg.getFloorDetailsForUpdate())
			if (floorDetail != null && StringUtils.isNotBlank(floorDetail.getFloorDescription()))
				newFloorDetails.add(floorDetail);
		bldg.getFloorDetails().addAll(newFloorDetails);
	}

}
