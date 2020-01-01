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
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.egov.common.entity.dcr.helper.ErrorDetail;
import org.egov.common.entity.edcr.Plan;
import org.egov.edcr.contract.EdcrDetail;
import org.egov.edcr.contract.EdcrRequest;
import org.egov.edcr.contract.EdcrResponse;
import org.egov.edcr.contract.PlanResponse;
import org.egov.edcr.service.EdcrRestService;
import org.egov.edcr.service.PlanService;
import org.egov.infra.microservice.contract.RequestInfoWrapper;
import org.egov.infra.microservice.contract.ResponseInfo;
import org.egov.infra.microservice.models.RequestInfo;
import org.egov.infra.utils.FileStoreUtils;
import org.egov.infra.web.rest.error.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@RestController
@RequestMapping(value = "/rest/dcr")
public class RestEdcrApplicationController {

    private static final Logger LOGGER = Logger.getLogger(RestEdcrApplicationController.class);
    private static final String DIGIT_DCR = "Digit DCR";

    @Autowired
    private EdcrRestService edcrRestService;
    
    @Autowired
    private PlanService planService;

    @Autowired
    protected FileStoreUtils fileStoreUtils;

    @PostMapping(value = "/scrutinizeplan", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> scrutinizePlan(@RequestBody MultipartFile planFile,
            @RequestParam String edcrRequest) {
        EdcrDetail edcrDetail = new EdcrDetail();
        EdcrRequest edcr = new EdcrRequest();
        try {
            edcr = new ObjectMapper().readValue(edcrRequest, EdcrRequest.class);
            ErrorDetail errorResponses = (edcrRestService.validateEdcrRequest(edcr, planFile));
            if (errorResponses != null)
                return new ResponseEntity<>(errorResponses, HttpStatus.BAD_REQUEST);
            else {
                edcrDetail = edcrRestService.createEdcr(edcr, planFile);
            }

        } catch (IOException e) {
            ErrorResponse error = new ErrorResponse("INCORRECT_REQUEST", e.getLocalizedMessage(),
                    HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return getSuccessResponse(Arrays.asList(edcrDetail), edcr.getRequestInfo());
    }

    @PostMapping(value = "/scrutinydetails", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> scrutinyDetails(@ModelAttribute EdcrRequest edcrRequest,
            @RequestBody @Valid RequestInfoWrapper requestInfoWrapper) {
        List<EdcrDetail> edcrDetail = edcrRestService.fetchEdcr(edcrRequest,requestInfoWrapper);

        if (!edcrDetail.isEmpty() && edcrDetail.get(0).getErrors() != null)
            return new ResponseEntity<>(edcrDetail.get(0).getErrors(), HttpStatus.NOT_FOUND);
        else {
            return getSuccessResponse(edcrDetail, requestInfoWrapper.getRequestInfo());
        }
    }    
	
    @PostMapping(value = "/extractplan", produces =  MediaType.APPLICATION_JSON_VALUE)	
	@ResponseBody 
	public ResponseEntity<?> planDetails(@RequestBody MultipartFile planFile,
            @RequestParam String edcrRequest) {          
    	Plan plan = new Plan();
        EdcrRequest edcr = new EdcrRequest();
        try {
            edcr = new ObjectMapper().readValue(edcrRequest, EdcrRequest.class);
            ErrorDetail errorResponses = edcrRestService.validatePlanFile(planFile);
            if (errorResponses != null)
                return new ResponseEntity<>(errorResponses, HttpStatus.BAD_REQUEST);
            else {
                plan = planService.extractPlan(edcr, planFile);   
          }
        } catch (IOException e) {
            ErrorResponse error = new ErrorResponse("INCORRECT_REQUEST", e.getLocalizedMessage(),
                    HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        String jsonRes = "";
        try {
        	jsonRes = mapper.writeValueAsString(plan);
		} catch (JsonProcessingException e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
		}
        return getPlanSuccessResponse(jsonRes, edcr.getRequestInfo());	 	  
	}	 

    @GetMapping("/downloadfile/{fileStoreId}")
    public ResponseEntity<InputStreamResource> download(@PathVariable final String fileStoreId) {
        return fileStoreUtils.fileAsResponseEntity(fileStoreId, DIGIT_DCR, true);
    }

    private ResponseEntity<?> getSuccessResponse(List<EdcrDetail> edcrDetails, RequestInfo requestInfo) {
        EdcrResponse edcrRes = new EdcrResponse();
        edcrRes.setEdcrDetail(edcrDetails);
        ResponseInfo responseInfo = edcrRestService.createResponseInfoFromRequestInfo(requestInfo, true);
        edcrRes.setResponseInfo(responseInfo);
        return new ResponseEntity<>(edcrRes, HttpStatus.OK);

    }
    
    private ResponseEntity<?> getPlanSuccessResponse(String jsonRes, RequestInfo requestInfo) {
        PlanResponse planRes = new PlanResponse();
        Plan plan;
		try {
			plan = new ObjectMapper().readValue(jsonRes, Plan.class);
		} catch (IOException e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
        planRes.setPlan(plan);
        ResponseInfo responseInfo = edcrRestService.createResponseInfoFromRequestInfo(requestInfo, true);
        planRes.setResponseInfo(responseInfo);
        return new ResponseEntity<>(planRes, HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        String errorDesc;
        if (ex.getLocalizedMessage() == null)
            errorDesc = String.valueOf(ex).length() <= 200 ? String.valueOf(ex).substring(0, String.valueOf(ex).length())
                    : String.valueOf(ex).substring(1, 200);
        else
            errorDesc = ex.getMessage();
        ErrorResponse error = new ErrorResponse("Internal Server Error", errorDesc,
                HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}