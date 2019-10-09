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

package org.egov.edcr.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.egov.common.entity.dcr.helper.EdcrApplicationInfo;
import org.egov.common.entity.dcr.helper.ErrorDetail;
import org.egov.edcr.config.properties.EdcrApplicationSettings;
import org.egov.edcr.contract.EdcrRequest;
import org.egov.edcr.contract.EdcrResponse;
import org.egov.edcr.entity.EdcrApplication;
import org.egov.edcr.entity.EdcrApplicationDetail;
import org.egov.infra.security.utils.SecurityUtils;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import eu.medsea.mimeutil.MimeException;
import eu.medsea.mimeutil.MimeUtil;

@Service
@Transactional(readOnly = true)
public class EdcrRestService {
    private static Logger LOG = Logger.getLogger(EdcrApplicationService.class);
    @Autowired
    protected SecurityUtils securityUtils;

   

    @PersistenceContext
    private EntityManager entityManager;
    
    @Autowired
    private EdcrApplicationSettings edcrApplicationSettings;
    
    @Autowired
    private EdcrApplicationService edcrApplicationService;


    public Session getCurrentSession() {
        return entityManager.unwrap(Session.class);
    }
    
    public EdcrResponse createEdcr(final EdcrRequest edcrRequest, final MultipartFile file) {
    	EdcrResponse edcrResponse = new EdcrResponse();
	    EdcrApplication edcrApplication = new EdcrApplication();
	    List<EdcrApplicationDetail> edcrApplicationDetails = new ArrayList<>();
	    edcrApplication.setApplicantName("Anonymous");
	    edcrApplication.setServiceType("New Construction");
	    edcrApplication.setEdcrApplicationDetails(edcrApplicationDetails);
	    edcrApplication.setDxfFile(file);
	    edcrApplication=edcrApplicationService.create(edcrApplication);
	    edcrResponse.setEdcrNumber(edcrApplication.getEdcrApplicationDetails().get(0).getDcrNumber());
		return edcrResponse;  
   }

    
    public ErrorDetail validateRequestParam(final EdcrRequest edcrRequest, final MultipartFile file) {
        List<String> dcrAllowedExtenstions = new ArrayList<>(
                Arrays.asList(edcrApplicationSettings.getValue("dcr.dxf.allowed.extenstions").split(",")));

        List<String> dcrMimeTypes = new ArrayList<>(
                Arrays.asList(edcrApplicationSettings.getValue("dcr.dxf.allowed.mime.types").split(",")));
        String fileSize = edcrApplicationSettings.getValue("dcr.dxf.max.size");
        return validateParam(dcrAllowedExtenstions, dcrMimeTypes, file, "dxfFile", fileSize, edcrRequest);
    }

    public String getMimeType(final MultipartFile file) {
        MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");

        eu.medsea.mimeutil.MimeType mimeType = null;
        try {
            mimeType = MimeUtil.getMostSpecificMimeType(MimeUtil.getMimeTypes(file.getInputStream()));
        } catch (MimeException | IOException e) {
            LOG.error(e);
        }

        MimeUtil.unregisterMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
        return String.valueOf(mimeType);
    }

    public ErrorDetail validateParam(List<String> allowedExtenstions, List<String> mimeTypes,
            MultipartFile file, String filePath, final String maxAllowSizeInMB, final EdcrRequest edcrRequest) {
        String extension;
        String mimeType;
        ErrorDetail errorDetail = null;
       
        EdcrApplicationInfo applicationInfo = new EdcrApplicationInfo();
        applicationInfo.setErrorDetail(errorDetail);
        if (file != null && !file.isEmpty()) {
            extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1);
            if (extension != null && !extension.isEmpty()) {
                mimeType = getMimeType(file);
                if (!allowedExtenstions.contains(extension.toLowerCase())) {
                	return new ErrorDetail("Invalid File Type","Please upload "+allowedExtenstions+" format file only");
                } else if (allowedExtenstions.contains(extension.toLowerCase())
                        && (!mimeTypes.contains(mimeType)
                                || StringUtils.countMatches(file.getOriginalFilename(), ".") > 1
                                || file.getOriginalFilename().contains("%00"))) {
                	return new ErrorDetail("Malicious File","Malicious file upload");
                } else if (file.getSize() > (Long.valueOf(maxAllowSizeInMB) * 1024 * 1024)) {
                	return new ErrorDetail("File Size","File size should not exceed 30 MB");
                }
            }
        }
        if(!validateTenant(edcrRequest.getTenant()))
        	return new ErrorDetail("tenant","Please enter valid tenant");
        if(!validateAuthToken(edcrRequest.getAuthToken()))
        	return new ErrorDetail("authToken","PLease enter valid authtoken");
        
		return errorDetail;
    }
    
    public Boolean validateTenant(final String tenantId) {
    	return tenantId != null ? true : false;
    }
    
    public Boolean validateAuthToken(final String authToken) {
    	return authToken != null ? true : false;
    }

}