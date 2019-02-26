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

import org.apache.commons.lang.StringUtils;
import org.egov.bpa.transaction.entity.ApplicationFloorDetail;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.BuildingDetail;
import org.egov.bpa.transaction.repository.BuildingFloorDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BuildingFloorDetailsService {
	@Autowired
	private BuildingFloorDetailsRepository buildingFloorDetailsRepository;

	@Transactional
	public void delete(final List<ApplicationFloorDetail> applicationFloorDetails) {
		buildingFloorDetailsRepository.deleteInBatch(applicationFloorDetails);
	}

	@Transactional
	public ApplicationFloorDetail findById(final Long id) {
		return buildingFloorDetailsRepository.findOne(id);
	}

	public void buildProposedBuildingFloorDetails(final BpaApplication application) {
	        buildNewlyAddedFloorDetails(application);
		if (!application.getBuildingDetail().isEmpty()) {
			for (BuildingDetail bldg : application.getBuildingDetail()) {
				if (!bldg.getApplicationFloorDetails().isEmpty()) {
					buildAndDeleteFloorDetails(bldg);
					List<ApplicationFloorDetail> floorDetailsList = new ArrayList<>();
					bldg.setApplication(application);
					for (ApplicationFloorDetail floor : bldg.getApplicationFloorDetails()) {
						if (null != floor && null == floor.getId()
							&& floor.getFloorDescription() != null) {
							ApplicationFloorDetail floorDetails = new ApplicationFloorDetail();
							floorDetails.setBuildingDetail(bldg);
							floorDetails.setSubOccupancy(floor.getSubOccupancy());
							floorDetails.setUsage(floor.getUsage());
							floorDetails.setOrderOfFloor(floor.getOrderOfFloor());
							floorDetails.setFloorNumber(floor.getFloorNumber());
							floorDetails.setFloorDescription(floor.getFloorDescription());
							floorDetails.setPlinthArea(floor.getPlinthArea());
							floorDetails.setCarpetArea(floor.getCarpetArea());
							floorDetails.setFloorArea(floor.getFloorArea());
							floorDetailsList.add(floorDetails);
						} else if (null != floor && null != floor.getId()
								   && floor.getFloorDescription() != null) {
							floorDetailsList.add(floor);
						}
					}
					bldg.getApplicationFloorDetails().clear();
					bldg.setApplicationFloorDetails(floorDetailsList);
				}
			}

			validateAndBuildBuildingDetails(application);
		}
	}

	private void validateAndBuildBuildingDetails(final BpaApplication application) {
		List<BuildingDetail> newBuildingDetailsList = new ArrayList<>();
		for (BuildingDetail buildingDetail : application.getBuildingDetail()) {
			if (buildingDetail != null && null != buildingDetail.getApplication() && null != buildingDetail.getTotalPlintArea()) {
				newBuildingDetailsList.add(buildingDetail);
			}
		}
		application.getBuildingDetail().clear();
		if (!newBuildingDetailsList.isEmpty())
			application.setBuildingDetail(newBuildingDetailsList);
	}

	public void buildNewlyAddedFloorDetails(final BpaApplication application) {
		if (!application.getBuildingDetail().isEmpty()) {
			for (BuildingDetail bldg : application.getBuildingDetail()) {
				List<ApplicationFloorDetail> newFloorDetails = new ArrayList<>();
				for (ApplicationFloorDetail applicationFloorDetail : bldg.getApplicationFloorDetailsForUpdate()) {
					if (applicationFloorDetail != null && StringUtils.isNotBlank(applicationFloorDetail.getFloorDescription()))
						newFloorDetails.add(applicationFloorDetail);
				}
				bldg.getApplicationFloorDetails().addAll(newFloorDetails);
			}
		}
	}

	private void buildAndDeleteFloorDetails(final BuildingDetail building) {
		List<ApplicationFloorDetail> existingFloorDetails = new ArrayList<>();
		if (building.getDeletedFloorIds() != null
			&& building.getDeletedFloorIds().length > 0) {
			for (Long id : building.getDeletedFloorIds()) {
				existingFloorDetails.add(findById(id));
			}
			building.delete(existingFloorDetails);
			delete(existingFloorDetails);
		}
	}

	public void removeDuplicateProposedBuildFloorDetails(final BuildingDetail building) {
		if (building != null && building.getTotalPlintArea() != null) {
			building.setApplicationFloorDetails(
					new ArrayList<>(new HashSet<>(building.getApplicationFloorDetails())));
		}
	}
}
