/*
 *    eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) 2017  eGovernments Foundation
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
 *            Further, all user interfaces, including but not limited to citizen facing interfaces,
 *            Urban Local Bodies interfaces, dashboards, mobile applications, of the program and any
 *            derived works should carry eGovernments Foundation logo on the top right corner.
 *
 *            For the logo, please refer http://egovernments.org/html/logo/egov_logo.png.
 *            For any further queries on attribution, including queries on brand guidelines,
 *            please contact contact@egovernments.org
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
 *
 */
package org.egov.bpa.web.controller.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.egov.bpa.master.entity.ServiceType;
import org.egov.bpa.master.entity.StakeHolder;
import org.egov.bpa.master.service.ServiceTypeService;
import org.egov.bpa.master.service.StakeHolderService;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.enums.StakeHolderType;
import org.egov.bpa.transaction.service.ApplicationBpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class BpaRestController {

    @Autowired
    private StakeHolderService stakeHolderService;

    @Autowired
    private ServiceTypeService serviceTypeService;
    
    @Autowired
    private ApplicationBpaService applicationBpaService;

    @GetMapping(value = "/getstakeholder/{id}", produces = APPLICATION_JSON_VALUE)
    public StakeHolder getStakeHolderById(@PathVariable final String id) {
        return stakeHolderService.findById(Long.valueOf(id));
    }

    @GetMapping(value = "/servicetype/amenities", produces = APPLICATION_JSON_VALUE)
    public List<ServiceType> getAllActiveAmenities() {
        return serviceTypeService.getAllActiveAmenities();
    }

    @GetMapping(value = "/servicetype/main", produces = APPLICATION_JSON_VALUE)
    public List<ServiceType> getAllActiveMainServiceTypes() {
        return serviceTypeService.getAllActiveMainServiceTypes();
    }
    
	@GetMapping(value = "/stakeholder/type", produces = APPLICATION_JSON_VALUE)
	public List<String> getAllStakeHolderTypes() {
		List<String> stkHldrTypes = new ArrayList<>();
		List<StakeHolderType> stkhldrtypes = Arrays.asList(StakeHolderType.values());
		for (StakeHolderType stkHldrType : stkhldrtypes) {
			stkHldrTypes.add(stkHldrType.toString());
		}
		return stkHldrTypes;
	}
    
	@GetMapping(value = "/getbpaApplicationByEdcrNumber/{edcrNumber}", produces = APPLICATION_JSON_VALUE)
	public Map<String, Object> getBpaApplicationByEdcrNumber(@PathVariable final String edcrNumber) {
		Map<String, Object> appNoPrmsnNoMap = new ConcurrentHashMap<>();
		List<BpaApplication> bpaAppsList = applicationBpaService.findApplicationByEDCRNumber(edcrNumber);
		if (bpaAppsList.size() > 0) {
			if (bpaAppsList.get(0).getApplicationNumber() != null) {
				appNoPrmsnNoMap.put("applicationNumber", bpaAppsList.get(0).getApplicationNumber());
				appNoPrmsnNoMap.put("isAppNoPresent", true);
			} else {
				appNoPrmsnNoMap.put("isAppNoPresent", false);
			}
			if (bpaAppsList.get(0).getPlanPermissionNumber() != null) {
				appNoPrmsnNoMap.put("permitNumber", bpaAppsList.get(0).getPlanPermissionNumber());
				appNoPrmsnNoMap.put("isPermitNoPresent", true);
			} else {
				appNoPrmsnNoMap.put("isPermitNoPresent", false);
			}
		} else {
			appNoPrmsnNoMap.put("isAppNoPresent", false);
			appNoPrmsnNoMap.put("isPermitNoPresent", false);
		}
		return appNoPrmsnNoMap;
	}
    
	@GetMapping(value = "/getStakeHolderNameAndIdByType/{type}", produces = APPLICATION_JSON_VALUE)
	public List<Map<String, Object>> getStakeHolderNameAndIdByType(@PathVariable final String type) {
		List<Map<String, Object>> stkHldrDataList = new ArrayList<>();
		StakeHolderType stkHldrType = null;
		for (StakeHolderType stkType : StakeHolderType.values()) {
			if (String.valueOf(stkType.getStakeHolderTypeVal()).equals(type)) {
				stkHldrType = stkType;
				break;
			}
		}
		List<StakeHolder> stakeHolderList = stakeHolderService.getStakeHoldersByType(stkHldrType);
		for (StakeHolder stakeHolder : stakeHolderList) {
			Map<String, Object> mapOfIdAndName = new HashMap<>();
			mapOfIdAndName.put("id", stakeHolder.getId());
			mapOfIdAndName.put("name", stakeHolder.getName());
			stkHldrDataList.add(mapOfIdAndName);
		}
		return stkHldrDataList;
	}
}

