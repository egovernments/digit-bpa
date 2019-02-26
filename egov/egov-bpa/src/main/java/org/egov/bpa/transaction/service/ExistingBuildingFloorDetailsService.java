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
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.ExistingBuildingDetail;
import org.egov.bpa.transaction.entity.ExistingBuildingFloorDetail;
import org.egov.bpa.transaction.repository.ExistingBuildingFloorDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ExistingBuildingFloorDetailsService {
    @Autowired
    private ExistingBuildingFloorDetailsRepository existingBuildingFloorDetailsRepository;

    @Transactional
    public void delete(final List<ExistingBuildingFloorDetail> existingBuildingFloorDetails) {
        existingBuildingFloorDetailsRepository.deleteInBatch(existingBuildingFloorDetails);
    }

    @Transactional
    public ExistingBuildingFloorDetail findById(final Long id) {
        return existingBuildingFloorDetailsRepository.findOne(id);
    }

    public void buildExistingBuildingFloorDetails(final BpaApplication application) {
        if (!application.getExistingBuildingDetails().isEmpty()) {
            for (ExistingBuildingDetail existBldg : application.getExistingBuildingDetails()) {
                buildAndDeleteExistingBuildingFloorDetails(existBldg);
                buildNewlyAddedFloorDetails(existBldg);
                List<ExistingBuildingFloorDetail> floorDetailsList = new ArrayList<>();
                existBldg.setApplication(application);
                for (ExistingBuildingFloorDetail existingBuildFloorDetails : existBldg.getExistingBuildingFloorDetails()) {
                    if (null != existingBuildFloorDetails && null == existingBuildFloorDetails.getId()
                        && existingBuildFloorDetails.getFloorDescription() != null) {
                        ExistingBuildingFloorDetail floorDetails = new ExistingBuildingFloorDetail();
                        floorDetails.setExistingBuildingDetail(existBldg);
                        floorDetails.setSubOccupancy(existingBuildFloorDetails.getSubOccupancy());
                        floorDetails.setUsage(existingBuildFloorDetails.getUsage());
                        floorDetails.setOrderOfFloor(existingBuildFloorDetails.getOrderOfFloor());
                        floorDetails.setFloorNumber(existingBuildFloorDetails.getFloorNumber());
                        floorDetails.setFloorDescription(existingBuildFloorDetails.getFloorDescription());
                        floorDetails.setPlinthArea(existingBuildFloorDetails.getPlinthArea());
                        floorDetails.setCarpetArea(existingBuildFloorDetails.getCarpetArea());
                        floorDetails.setFloorArea(existingBuildFloorDetails.getFloorArea());
                        floorDetailsList.add(floorDetails);
                    } else if (null != existingBuildFloorDetails && null != existingBuildFloorDetails.getId()
                               && existingBuildFloorDetails.getFloorDescription() != null) {
                        floorDetailsList.add(existingBuildFloorDetails);
                    }
                }
                existBldg.getExistingBuildingFloorDetails().clear();
                existBldg.setExistingBuildingFloorDetails(floorDetailsList);
            }
        }
        validateAndBuildBuildingDetails(application);
    }

    private void validateAndBuildBuildingDetails(final BpaApplication application) {
        List<ExistingBuildingDetail> newBuildingDetailsList = new ArrayList<>();
        for (ExistingBuildingDetail existingBuildingDetail : application.getExistingBuildingDetails()) {
            if (existingBuildingDetail != null && null != existingBuildingDetail.getApplication()
                    && null != existingBuildingDetail.getTotalPlintArea()) {
                newBuildingDetailsList.add(existingBuildingDetail);
            }
        }
        application.getExistingBuildingDetails().clear();
        if (!newBuildingDetailsList.isEmpty())
            application.setExistingBuildingDetails(newBuildingDetailsList);
    }

    public void buildNewlyAddedFloorDetails(final ExistingBuildingDetail existingBldg) {
        if (!existingBldg.getExistingBuildingFloorDetailsUpdate().isEmpty()) {
            List<ExistingBuildingFloorDetail> newFloorDetails = new ArrayList<>();
            for (ExistingBuildingFloorDetail applicationFloorDetail : existingBldg.getExistingBuildingFloorDetailsUpdate()) {
                if (applicationFloorDetail != null && StringUtils.isNotBlank(applicationFloorDetail.getFloorDescription()))
                    newFloorDetails.add(applicationFloorDetail);
            }
            existingBldg.getExistingBuildingFloorDetails().addAll(newFloorDetails);
        }
    }

    private void buildAndDeleteExistingBuildingFloorDetails(final ExistingBuildingDetail existingBldg) {
        List<ExistingBuildingFloorDetail> floorDetails = new ArrayList<>();
        if (existingBldg.getDeletedFloorIds() != null && existingBldg.getDeletedFloorIds().length > 0) {
            for (Long id : existingBldg.getDeletedFloorIds()) {
                floorDetails.add(findById(id));
            }
            existingBldg.delete(floorDetails);
            delete(floorDetails);
        }
    }

    public void removeDuplicateExistingBuildFloorDetails(final BpaApplication application) {
        if (!application.getExistingBuildingDetails().isEmpty() && application.getExistingBuildingDetails().get(0) != null
                && !application.getExistingBuildingDetails().get(0).getExistingBuildingFloorDetails().isEmpty()
                && application.getExistingBuildingDetails().get(0).getTotalPlintArea() != null) {
            application.getExistingBuildingDetails().get(0).setExistingBuildingFloorDetails(
                    new ArrayList<>(
                            new HashSet<>(application.getExistingBuildingDetails().get(0).getExistingBuildingFloorDetails())));
        }
    }
}
