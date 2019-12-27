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

import static java.lang.String.format;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.egov.common.entity.dcr.helper.ErrorDetail;
import org.egov.common.entity.edcr.Plan;
import org.egov.common.entity.edcr.PlanInformation;
import org.egov.edcr.config.properties.EdcrApplicationSettings;
import org.egov.edcr.constants.DxfFileConstants;
import org.egov.edcr.contract.EdcrDetail;
import org.egov.edcr.contract.EdcrRequest;
import org.egov.edcr.entity.ApplicationType;
import org.egov.edcr.entity.EdcrApplication;
import org.egov.edcr.entity.EdcrApplicationDetail;
import org.egov.edcr.utility.DcrConstants;
import org.egov.infra.config.core.ApplicationThreadLocals;
import org.egov.infra.filestore.service.FileStoreService;
import org.egov.infra.microservice.contract.ResponseInfo;
import org.egov.infra.microservice.models.RequestInfo;
import org.egov.infra.microservice.models.UserInfo;
import org.egov.infra.security.utils.SecurityUtils;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Transactional(readOnly = true)
public class EdcrRestService {
    private static final String BPA_05 = "BPA-05";

    private static Logger LOG = Logger.getLogger(EdcrApplicationService.class);

    public static final String FILE_DOWNLOAD_URL = "%s/edcr/rest/dcr/downloadfile/";

    @Autowired
    protected SecurityUtils securityUtils;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private EdcrApplicationSettings edcrApplicationSettings;

    @Autowired
    private EdcrApplicationService edcrApplicationService;

    @Autowired
    private EdcrApplicationDetailService edcrApplicationDetailService;

    @Autowired
    private FileStoreService fileStoreService;

    public Session getCurrentSession() {
        return entityManager.unwrap(Session.class);
    }

    @Transactional
    public EdcrDetail createEdcr(final EdcrRequest edcrRequest, final MultipartFile file) {
        EdcrApplication edcrApplication = new EdcrApplication();
        EdcrApplicationDetail edcrApplicationDetail = new EdcrApplicationDetail();
        List<EdcrApplicationDetail> edcrApplicationDetails = new ArrayList<>();
        edcrApplicationDetails.add(edcrApplicationDetail);
        edcrApplication.setTransactionNumber(edcrRequest.getTransactionNumber());
        if (StringUtils.isNotBlank(edcrRequest.getApplicantName()))
            edcrApplication.setApplicantName(edcrRequest.getApplicantName());
        else
            edcrApplication.setApplicantName(DxfFileConstants.ANONYMOUS_APPLICANT);
        edcrApplication.setArchitectInformation(DxfFileConstants.ANONYMOUS_APPLICANT);
        edcrApplication.setServiceType(DxfFileConstants.NEWCONSTRUCTION_SERVICE);
        edcrApplication.setApplicationType(ApplicationType.PERMIT);
        edcrApplication.setEdcrApplicationDetails(edcrApplicationDetails);
        edcrApplication.setDxfFile(file);
        
        if(edcrRequest.getRequestInfo() != null && edcrRequest.getRequestInfo().getUserInfo() != null)
    		edcrApplication.setThirdPartyUserCode(edcrRequest.getRequestInfo().getUserInfo().getId() != null ? edcrRequest.getRequestInfo().getUserInfo().getId().toString() : StringUtils.EMPTY);
            edcrApplication.setThirdPartyUserTenant(edcrRequest.getRequestInfo().getUserInfo().getTenantId());
            
        edcrApplication = edcrApplicationService.createRestEdcr(edcrApplication);
        return setEdcrResponse(edcrApplication, edcrRequest.getTenantId());
    }
    
    @Transactional
    public List<EdcrDetail> edcrDetailsResponse(List<EdcrApplication> edcrApplications, String tenantId) {
        List<EdcrDetail> edcrDetails = new ArrayList<>();
        for (EdcrApplication edcrApp : edcrApplications)
            edcrDetails.add(setEdcrResponse(edcrApp, tenantId));

        return edcrDetails;
    }

    public EdcrDetail setEdcrResponse(EdcrApplication edcrApplication, String tenantId) {
        EdcrDetail edcrDetail = new EdcrDetail();
        List<String> planPdfs = new ArrayList<>();
        edcrDetail.setTransactionNumber(edcrApplication.getTransactionNumber());
        edcrDetail.setEdcrNumber(edcrApplication.getEdcrApplicationDetails().get(0).getDcrNumber());
        edcrDetail.setStatus(edcrApplication.getStatus());

        if (edcrApplication.getEdcrApplicationDetails().get(0).getDxfFileId() != null)
            edcrDetail.setDxfFile(
                    format(getFileDownloadUrl(
                            edcrApplication.getEdcrApplicationDetails().get(0).getDxfFileId().getFileStoreId())));

        if (edcrApplication.getEdcrApplicationDetails().get(0).getScrutinizedDxfFileId() != null)
            edcrDetail.setUpdatedDxfFile(
                    format(getFileDownloadUrl(
                            edcrApplication.getEdcrApplicationDetails().get(0).getScrutinizedDxfFileId().getFileStoreId())));

        if (edcrApplication.getEdcrApplicationDetails().get(0).getReportOutputId() != null)
            edcrDetail.setPlanReport(format(
                    getFileDownloadUrl(edcrApplication.getEdcrApplicationDetails().get(0).getReportOutputId().getFileStoreId())));

        File file = edcrApplication.getEdcrApplicationDetails().get(0).getPlanDetailFileStore() != null ? fileStoreService.fetch(
                edcrApplication.getEdcrApplicationDetails().get(0).getPlanDetailFileStore().getFileStoreId(),
                DcrConstants.APPLICATION_MODULE_TYPE) : null;

        if (LOG.isInfoEnabled())
            LOG.info("**************** End - Reading Plan detail file **************" + file);
        try {
            if (file != null) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                Plan pl1 = mapper.readValue(file, Plan.class);
                pl1.getPlanInformation().setApplicantName(edcrApplication.getApplicantName());
                if (LOG.isInfoEnabled())
                    LOG.info("**************** Plan detail object **************" + pl1);
                edcrDetail.setPlanDetail(pl1);
            } else {
                Plan pl1 = new Plan();
                PlanInformation pi = new PlanInformation();
                pi.setApplicantName(edcrApplication.getApplicantName());
                pl1.setPlanInformation(pi);
                edcrDetail.setPlanDetail(pl1);
            }
        } catch (IOException e) {
            LOG.log(Level.ERROR, e);
        }

        if (edcrApplication.getEdcrApplicationDetails().get(0).getDxfFileId() != null)
            planPdfs.add(
                    format(getFileDownloadUrl(
                            edcrApplication.getEdcrApplicationDetails().get(0).getDxfFileId().getFileStoreId())));

        if (edcrApplication.getEdcrApplicationDetails().get(0).getReportOutputId() != null)
            planPdfs.add(format(
                    getFileDownloadUrl(edcrApplication.getEdcrApplicationDetails().get(0).getReportOutputId().getFileStoreId())));

        edcrDetail.setPlanPdfs(planPdfs);
        edcrDetail.setTenantId(tenantId);

        if (!edcrApplication.getStatus().equalsIgnoreCase("Accepted"))
            edcrDetail.setStatus(edcrApplication.getStatus());

        return edcrDetail;
    }

    public List<EdcrDetail> fetchEdcr(final String edcrNumber, final String transactionNumber, String tenantId) {
        List<EdcrApplication> edcrDetails = new ArrayList<>();
        EdcrApplication edcrApplication = new EdcrApplication();

        if (StringUtils.isNotBlank(edcrNumber) && StringUtils.isNotBlank(transactionNumber)) {
            EdcrApplicationDetail dcrDetails = edcrApplicationDetailService.findByDcrAndTransactionNumber(edcrNumber,
                    transactionNumber);
            if (dcrDetails != null) {
                edcrApplication = dcrDetails.getApplication();
                edcrDetails.add(edcrApplication);
            }
        } else if (StringUtils.isNotBlank(edcrNumber)) {
            EdcrApplicationDetail dcrDetails = edcrApplicationDetailService.findByDcrNumber(edcrNumber);
            if (dcrDetails != null) {
                edcrApplication = dcrDetails.getApplication();
                edcrDetails.add(edcrApplication);
            }
        } else if (StringUtils.isNotBlank(transactionNumber)) {
            edcrApplication = edcrApplicationService.findByTransactionNumber(transactionNumber);
            if (edcrApplication != null)
                edcrDetails.add(edcrApplication);
        } else
            edcrDetails = edcrApplicationService.getEdcrApplications();

        if (!edcrDetails.isEmpty())
            return edcrDetailsResponse(edcrDetails, tenantId);
        else {
            EdcrDetail edcrDetail = new EdcrDetail();
            edcrDetail.setErrors("No Record Found");
            return Arrays.asList(edcrDetail);
        }
    }

    public ErrorDetail validatePlanFile(final MultipartFile file) {
        List<String> dcrAllowedExtenstions = new ArrayList<>(
                Arrays.asList(edcrApplicationSettings.getValue("dcr.dxf.allowed.extenstions").split(",")));

        List<String> dcrMimeTypes = new ArrayList<>(
                Arrays.asList(edcrApplicationSettings.getValue("dcr.dxf.allowed.mime.types").split(",")));
        String fileSize = edcrApplicationSettings.getValue("dcr.dxf.max.size");
        return validateParam(dcrAllowedExtenstions, dcrMimeTypes, file, fileSize);
    }

    public ErrorDetail validateEdcrRequest(final EdcrRequest edcrRequest, final MultipartFile planFile) {
        if (StringUtils.isBlank(edcrRequest.getTransactionNumber()))
            return new ErrorDetail("BPA-07", "Please enter transaction number");
        if (StringUtils.isNotBlank(edcrRequest.getTransactionNumber())
                && edcrApplicationService.findByTransactionNumber(edcrRequest.getTransactionNumber()) != null) {
            return new ErrorDetail("BPA-01", "Transaction Number should be unique");
        }
        return validatePlanFile(planFile);
    }

    public ErrorDetail validateSearchRequest(final String edcrNumber, final String transactionNumber) {
        ErrorDetail errorDetail = null;
        if (StringUtils.isBlank(edcrNumber) && StringUtils.isBlank(transactionNumber))
            return new ErrorDetail("BPA-07", "Please enter valid edcr number or transaction number");
        return errorDetail;
    }

    /*
     * public String getMimeType(final MultipartFile file) {
     * MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector"); eu.medsea.mimeutil.MimeType mimeType =
     * null; try { mimeType = MimeUtil.getMostSpecificMimeType(MimeUtil.getMimeTypes(file.getInputStream())); } catch
     * (MimeException | IOException e) { LOG.error(e); }
     * MimeUtil.unregisterMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector"); return String.valueOf(mimeType); }
     */

    public ErrorDetail validateParam(List<String> allowedExtenstions, List<String> mimeTypes,
            MultipartFile file, final String maxAllowSizeInMB) {
        String extension;
        String mimeType;
        if (file != null && !file.isEmpty()) {
            extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1);
            if (extension != null && !extension.isEmpty()) {
                // mimeType = getMimeType(file);
                if (!allowedExtenstions.contains(extension.toLowerCase())) {
                    return new ErrorDetail("BPA-02", "Please upload " + allowedExtenstions + " format file only");
                } else if (file.getSize() > (Long.valueOf(maxAllowSizeInMB) * 1024 * 1024)) {
                    return new ErrorDetail("BPA-04", "File size should not exceed 30 MB");
                } /*
                   * else if (allowedExtenstions.contains(extension.toLowerCase()) && (!mimeTypes.contains(mimeType) ||
                   * StringUtils.countMatches(file.getOriginalFilename(), ".") > 1 || file.getOriginalFilename().contains("%00")))
                   * { return new ErrorDetail("BPA-03", "Malicious file upload"); }
                   */
            }
        } else {
            return new ErrorDetail(BPA_05, "Please, upload plan file is mandatory");
        }

        return null;
    }

    public ResponseInfo createResponseInfoFromRequestInfo(RequestInfo requestInfo, Boolean success) {
        String apiId = null;
        String ver = null;
        String ts = null;
        String resMsgId = "";
        String msgId = null;
        if (requestInfo != null) {
            apiId = requestInfo.getApiId();
            ver = requestInfo.getVer();
            if (requestInfo.getTs() != null)
                ts = requestInfo.getTs().toString();
            msgId = requestInfo.getMsgId();
        }
        String responseStatus = success ? "successful" : "failed";

        return new ResponseInfo(apiId, ver, ts, resMsgId, msgId, responseStatus);
    }

    public String getFileDownloadUrl(final String fileStoreId) {
        return String.format(FILE_DOWNLOAD_URL, ApplicationThreadLocals.getDomainURL()) + fileStoreId + "?tenantId="
                + ApplicationThreadLocals.getTenantID();
    }

}