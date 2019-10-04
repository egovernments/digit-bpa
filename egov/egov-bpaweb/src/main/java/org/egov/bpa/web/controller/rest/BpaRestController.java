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

import static org.egov.bpa.utils.BpaConstants.APPLICATION_MODULE_TYPE;
import static org.egov.infra.persistence.entity.enums.UserType.BUSINESS;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.egov.bpa.master.entity.ServiceType;
import org.egov.bpa.master.entity.StakeHolder;
import org.egov.bpa.master.entity.StakeHolderType;
import org.egov.bpa.master.service.ServiceTypeService;
import org.egov.bpa.master.service.StakeHolderService;
import org.egov.bpa.master.service.StakeholderTypeService;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.service.ApplicationBpaService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.common.entity.dcr.helper.ErrorDetail;
import org.egov.infra.admin.master.entity.AppConfigValues;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.admin.master.service.AppConfigValueService;
import org.egov.infra.admin.master.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class BpaRestController {

    private static final String IS_PERMIT_NO_PRESENT = "isPermitNoPresent";

    private static final String IS_APP_NO_PRESENT = "isAppNoPresent";

    @Autowired
    private StakeHolderService stakeHolderService;

    @Autowired
    private ServiceTypeService serviceTypeService;

    @Autowired
    private ApplicationBpaService applicationBpaService;

    @Autowired
    private AppConfigValueService appConfigValueService;

    @Autowired
    private UserService userService;

    @Autowired
    private StakeholderTypeService stakeholderTypeService;

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

    @GetMapping(value = "/fetch/servicetype/edcr-required", produces = APPLICATION_JSON_VALUE)
    public List<String> getEdcrRequiredServiceTypes() {
        return serviceTypeService.getEDcrRequiredServiceTypes().stream().map(ServiceType::getDescription)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/stakeholder/type", produces = APPLICATION_JSON_VALUE)
    public List<String> getAllStakeHolderTypes() {
        List<String> stkHldrTypes = new ArrayList<>();
        List<StakeHolderType> stkhldrtypes = stakeholderTypeService.findAllIsActive();
        for (StakeHolderType stkHldrType : stkhldrtypes) {
            stkHldrTypes.add(stkHldrType.getName());
        }
        return stkHldrTypes;
    }

    @GetMapping(value = "/getbpaApplicationByEdcrNumber/{edcrNumber}", produces = APPLICATION_JSON_VALUE)
    public Map<String, Object> getBpaApplicationByEdcrNumber(@PathVariable final String edcrNumber) {
        Map<String, Object> appNoPrmsnNoMap = new ConcurrentHashMap<>();
        List<BpaApplication> bpaAppsList = applicationBpaService.findApplicationByEDCRNumber(edcrNumber);
        if (!bpaAppsList.isEmpty()) {
            if (bpaAppsList.get(0).getApplicationNumber() != null) {
                appNoPrmsnNoMap.put("applicationNumber", bpaAppsList.get(0).getApplicationNumber());
                appNoPrmsnNoMap.put(IS_APP_NO_PRESENT, true);
            } else {
                appNoPrmsnNoMap.put(IS_APP_NO_PRESENT, false);
            }
            if (bpaAppsList.get(0).getPlanPermissionNumber() != null) {
                appNoPrmsnNoMap.put("permitNumber", bpaAppsList.get(0).getPlanPermissionNumber());
                appNoPrmsnNoMap.put(IS_PERMIT_NO_PRESENT, true);
            } else {
                appNoPrmsnNoMap.put(IS_PERMIT_NO_PRESENT, false);
            }
        } else {
            appNoPrmsnNoMap.put(IS_APP_NO_PRESENT, false);
            appNoPrmsnNoMap.put(IS_PERMIT_NO_PRESENT, false);
        }
        return appNoPrmsnNoMap;
    }

    @GetMapping(value = "/getStakeHolderNameAndIdByType/{type}", produces = APPLICATION_JSON_VALUE)
    public List<Map<String, Object>> getStakeHolderNameAndIdByType(@PathVariable final String type) {
        List<Map<String, Object>> stkHldrDataList = new ArrayList<>();
        StakeHolderType stkHldrType = null;
        List<StakeHolderType> stakeholderTypeList = stakeholderTypeService.findAllIsActive();
        for (StakeHolderType stkType : stakeholderTypeList) {
            if ((stkType.getName()).equals(type)) {
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

    @GetMapping(value = "/stakeholder/check/demand-pending/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Boolean> isDemandPending(@PathVariable final Long userId, HttpServletResponse response) {
        User user = userService.getUserById(userId);
        Map<String, Boolean> isDemandPending = new HashMap<>();
        isDemandPending.put("pending", false);
        StakeHolder stkHldr = stakeHolderService.findById(userId);

        if (user.getType().equals(BUSINESS) && stkHldr.getDemand() != null) {
            List<AppConfigValues> appConfigValueList = appConfigValueService
                    .getConfigValuesByModuleAndKey(APPLICATION_MODULE_TYPE, "BUILDING_LICENSEE_REG_FEE_REQUIRED");
            if (!appConfigValueList.isEmpty() && appConfigValueList.get(0).getValue().equalsIgnoreCase("YES")
                    && BpaConstants.APPLICATION_STATUS_PENDNING.equalsIgnoreCase(stkHldr.getStatus().name())) {
                isDemandPending.put("pending", true);
                return isDemandPending;
            }
        }
        return isDemandPending;
    }

    @GetMapping(value = "/validate/stakeholder/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ErrorDetail validateStakeholder(@PathVariable final Long userId, HttpServletResponse response) {
        StakeHolder stakeHolder = stakeHolderService.findById(userId);
        return stakeHolderService.validateStakeholder(stakeHolder);
    }

}
