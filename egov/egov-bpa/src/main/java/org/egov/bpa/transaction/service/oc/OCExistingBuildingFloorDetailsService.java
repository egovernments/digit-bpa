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
import org.egov.bpa.transaction.entity.oc.OCExistingBuilding;
import org.egov.bpa.transaction.entity.oc.OCExistingBuildingFloor;
import org.egov.bpa.transaction.entity.oc.OccupancyCertificate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class OCExistingBuildingFloorDetailsService {

	public void buildExistingBuildingFloorDetails(final OccupancyCertificate oc) {
		if (!oc.getExistingBuildings().isEmpty()) {
			for (OCExistingBuilding existBldg : oc.getExistingBuildings()) {
				buildNewlyAddedFloorDetails(existBldg);
				List<OCExistingBuildingFloor> floorDetailsList = new ArrayList<>();
				existBldg.setOc(oc);
				for (OCExistingBuildingFloor existFloor : existBldg.getExistingBuildingFloorDetails()) {
					if (null != existFloor && null == existFloor.getId() && existFloor.getFloorDescription() != null) {
						OCExistingBuildingFloor floorDetails = new OCExistingBuildingFloor();
						floorDetails.setExistingBuildingDetail(existBldg);
						floorDetails.setOccupancy(existFloor.getOccupancy());
						floorDetails.setOrderOfFloor(existFloor.getOrderOfFloor());
						floorDetails.setFloorNumber(existFloor.getFloorNumber());
						floorDetails.setFloorDescription(existFloor.getFloorDescription());
						floorDetails.setPlinthArea(existFloor.getPlinthArea());
						floorDetails.setCarpetArea(existFloor.getCarpetArea());
						floorDetails.setFloorArea(existFloor.getFloorArea());
						floorDetailsList.add(floorDetails);
					} else if (null != existFloor && null != existFloor.getId()
							&& existFloor.getFloorDescription() != null) {
						floorDetailsList.add(existFloor);
					}
				}
				existBldg.getExistingBuildingFloorDetails().clear();
				existBldg.setExistingBuildingFloorDetails(floorDetailsList);
			}
		}
		validateAndBuildBuildingDetails(oc);
	}

	private void validateAndBuildBuildingDetails(final OccupancyCertificate oc) {
		List<OCExistingBuilding> newBuildingDetailsList = new ArrayList<>();
		for (OCExistingBuilding existBuilding : oc.getExistingBuildings()) {
			if (existBuilding != null && null != existBuilding.getOc() && null != existBuilding.getTotalPlinthArea()) {
				newBuildingDetailsList.add(existBuilding);
			}
		}
		oc.getExistingBuildings().clear();
		if (!newBuildingDetailsList.isEmpty())
			oc.setExistingBuildings(newBuildingDetailsList);
	}

	public void buildNewlyAddedFloorDetails(final OCExistingBuilding existingBldg) {
		if (!existingBldg.getExistingBuildingFloorDetailsUpdate().isEmpty()) {
			List<OCExistingBuildingFloor> newFloorDetails = new ArrayList<>();
			for (OCExistingBuildingFloor floor : existingBldg.getExistingBuildingFloorDetailsUpdate()) {
				if (floor != null && StringUtils.isNotBlank(floor.getFloorDescription()))
					newFloorDetails.add(floor);
			}
			existingBldg.getExistingBuildingFloorDetails().addAll(newFloorDetails);
		}
	}

}
