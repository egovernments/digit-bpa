/*
 * eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 * accountability and the service delivery of the government  organizations.
 *
 *  Copyright (C) <2017>  eGovernments Foundation
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

package org.egov.edcr.web.controller.rest;

import java.io.IOException;

import javax.validation.Valid;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.egov.common.entity.dcr.helper.ErrorDetail;
import org.egov.edcr.contract.EdcrRequest;
import org.egov.edcr.contract.EdcrResponse;
import org.egov.edcr.service.EdcrRestService;
import org.egov.infra.utils.FileStoreUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value = "/rest")
public class RestEdcrApplicationController {

    private static final Logger LOGGER = Logger.getLogger(RestEdcrApplicationController.class);
    private static final String DIGIT_DCR = "Digit DCR";

    @Autowired
    private EdcrRestService edcrRestService;

    @Autowired
    protected FileStoreUtils fileStoreUtils;
    
    @PostMapping(value="/dcr/uploadplan", produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> uploadPlan(@Valid @RequestParam("planFile") MultipartFile planFile, @Valid @RequestParam("edcrRequest") String edcrRequest ) {
		EdcrResponse edcrResponse = new EdcrResponse();
    	try{
    		EdcrRequest edcr  = new ObjectMapper().readValue(edcrRequest, EdcrRequest.class);
    		ErrorDetail errorResponses = edcrRestService.validateRequestParam(edcr, planFile);
    		if(errorResponses!=null)
    			return new ResponseEntity<ErrorDetail>(errorResponses, HttpStatus.BAD_REQUEST);    
    		else {
    			edcrResponse = edcrRestService.createEdcr(edcr, planFile);
    		}
    		
    	}catch (IOException e) {
    		LOGGER.log(Level.ERROR, e);
        }        
		return new ResponseEntity<EdcrResponse>(edcrResponse, HttpStatus.OK);    
    }
    
    @GetMapping(value="/dcr/getdetails", produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> getDetails(@ModelAttribute EdcrRequest edcrRequest) {
		ErrorDetail errorResponses = edcrRestService.validateSearchRequest(edcrRequest.getEdcrNumber(), edcrRequest.getTransactionNumber());
		EdcrResponse edcrResponse = new EdcrResponse();
		if(errorResponses!=null)
			return new ResponseEntity<ErrorDetail>(errorResponses, HttpStatus.BAD_REQUEST);    
		else
	    	edcrResponse = edcrRestService.fetchEdcr(edcrRequest.getEdcrNumber(), edcrRequest.getTransactionNumber());
		return new ResponseEntity<EdcrResponse>(edcrResponse, HttpStatus.OK);    
    }
    
    
    @GetMapping("/dcr/downloadfile/{fileStoreId}")
    public ResponseEntity<InputStreamResource> download(@PathVariable final String fileStoreId) {
        return fileStoreUtils.fileAsResponseEntity(fileStoreId, DIGIT_DCR, true);
    }
}