/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2019>  eGovernments Foundation
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
package org.egov.bpa.web.controller.ajax;

import java.util.Map;

import org.egov.bpa.transaction.service.BpaDcrService;
import org.egov.bpa.utils.OccupancyCertificateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Controller
public class BpaDcrAjaxController {

    @Autowired
    private OccupancyCertificateUtils occupancyCertificateUtils;
    @Autowired
    private BpaDcrService bpaDcrService;

    @GetMapping(value = "/validate/edcr-usedinbpa", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, String> validateEdcrIsUsedInBpaApplication(@RequestParam final String eDcrNumber) {
        return bpaDcrService.checkIsEdcrUsedInBpaApplication(eDcrNumber);
    }

    @GetMapping(value = "/application/edcr-require", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Boolean checkEDCRIsRequire(@RequestParam final String serviceType, @RequestParam final String occupancy) {
        return bpaDcrService.isEdcrIntegrationRequireByService(serviceType);
    }

    @GetMapping(value = "/validate/occupancy-certificate/edcrno-used", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, String> checkDCRIsUsedWithAnyOccupancyCertificateAppln(@RequestParam final String eDcrNumber) {
        return occupancyCertificateUtils.checkIsEdcrUsedWithAnyOCApplication(eDcrNumber);
    }

    @GetMapping(value = "/validate/edcr-expiry", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, String> validateEdcrExpiry(@RequestParam final String eDcrNumber) {
        return bpaDcrService.checkEdcrExpiry(eDcrNumber,
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
    }

}
