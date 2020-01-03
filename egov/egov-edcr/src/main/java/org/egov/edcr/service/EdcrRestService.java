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
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
import org.egov.infra.admin.master.entity.City;
import org.egov.infra.admin.master.service.CityService;
import org.egov.infra.config.core.ApplicationThreadLocals;
import org.egov.infra.filestore.service.FileStoreService;
import org.egov.infra.microservice.contract.RequestInfoWrapper;
import org.egov.infra.microservice.contract.ResponseInfo;
import org.egov.infra.microservice.models.RequestInfo;
import org.egov.infra.microservice.models.UserInfo;
import org.egov.infra.security.utils.SecurityUtils;
import org.egov.infra.utils.TenantUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
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

    @Autowired
    private TenantUtils tenantUtils;

    @Autowired
    private CityService cityService;

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
        if (isNotBlank(edcrRequest.getApplicantName()))
            edcrApplication.setApplicantName(edcrRequest.getApplicantName());
        else
            edcrApplication.setApplicantName(DxfFileConstants.ANONYMOUS_APPLICANT);
        edcrApplication.setArchitectInformation(DxfFileConstants.ANONYMOUS_APPLICANT);
        edcrApplication.setServiceType(DxfFileConstants.NEWCONSTRUCTION_SERVICE);
        edcrApplication.setApplicationType(ApplicationType.PERMIT);
        edcrApplication.setEdcrApplicationDetails(edcrApplicationDetails);
        edcrApplication.setDxfFile(file);

        if (edcrRequest.getRequestInfo() != null && edcrRequest.getRequestInfo().getUserInfo() != null) {
            edcrApplication.setThirdPartyUserCode(isNotBlank(edcrRequest.getRequestInfo().getUserInfo().getId())
                    ? edcrRequest.getRequestInfo().getUserInfo().getId()
                    : StringUtils.EMPTY);
            edcrApplication
                    .setThirdPartyUserTenant(StringUtils.isNotBlank(edcrRequest.getRequestInfo().getUserInfo().getTenantId())
                            ? edcrRequest.getRequestInfo().getUserInfo().getTenantId()
                            : edcrRequest.getTenantId());
        }

        edcrApplication = edcrApplicationService.createRestEdcr(edcrApplication);
        return setEdcrResponse(edcrApplication.getEdcrApplicationDetails().get(0), edcrRequest.getTenantId());
    }

    @Transactional
    public List<EdcrDetail> edcrDetailsResponse(List<EdcrApplicationDetail> edcrApplications, String tenantId) {
        List<EdcrDetail> edcrDetails = new ArrayList<>();
        for (EdcrApplicationDetail edcrApp : edcrApplications)
            edcrDetails.add(setEdcrResponse(edcrApp, tenantId));

        return edcrDetails;
    }

    public EdcrDetail setEdcrResponse(EdcrApplicationDetail edcrApplnDtl, String tenantId) {
        EdcrDetail edcrDetail = new EdcrDetail();
        List<String> planPdfs = new ArrayList<>();
        edcrDetail.setTransactionNumber(edcrApplnDtl.getApplication().getTransactionNumber());
        edcrDetail.setEdcrNumber(edcrApplnDtl.getDcrNumber());
        edcrDetail.setStatus(edcrApplnDtl.getStatus());

        if (edcrApplnDtl.getDxfFileId() != null)
            edcrDetail.setDxfFile(
                    format(getFileDownloadUrl(
                            edcrApplnDtl.getDxfFileId().getFileStoreId(),
                            ApplicationThreadLocals.getTenantID())));

        if (edcrApplnDtl.getScrutinizedDxfFileId() != null)
            edcrDetail.setUpdatedDxfFile(
                    format(getFileDownloadUrl(
                            edcrApplnDtl.getScrutinizedDxfFileId().getFileStoreId(),
                            ApplicationThreadLocals.getTenantID())));

        if (edcrApplnDtl.getReportOutputId() != null)
            edcrDetail.setPlanReport(format(
                    getFileDownloadUrl(edcrApplnDtl.getReportOutputId().getFileStoreId(),
                            ApplicationThreadLocals.getTenantID())));

        File file = edcrApplnDtl.getPlanDetailFileStore() != null ? fileStoreService.fetch(
                edcrApplnDtl.getPlanDetailFileStore().getFileStoreId(),
                DcrConstants.APPLICATION_MODULE_TYPE) : null;

        if (LOG.isInfoEnabled())
            LOG.info("**************** End - Reading Plan detail file **************" + file);
        try {
            if (file != null) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                Plan pl1 = mapper.readValue(file, Plan.class);
                pl1.getPlanInformation().setApplicantName(edcrApplnDtl.getApplication().getApplicantName());
                if (LOG.isInfoEnabled())
                    LOG.info("**************** Plan detail object **************" + pl1);
                edcrDetail.setPlanDetail(pl1);
            } else {
                Plan pl1 = new Plan();
                PlanInformation pi = new PlanInformation();
                pi.setApplicantName(edcrApplnDtl.getApplication().getApplicantName());
                pl1.setPlanInformation(pi);
                edcrDetail.setPlanDetail(pl1);
            }
        } catch (IOException e) {
            LOG.log(Level.ERROR, e);
        }

        if (edcrApplnDtl.getDxfFileId() != null)
            planPdfs.add(
                    format(getFileDownloadUrl(
                            edcrApplnDtl.getDxfFileId().getFileStoreId(),
                            ApplicationThreadLocals.getTenantID())));

        if (edcrApplnDtl.getReportOutputId() != null)
            planPdfs.add(format(
                    getFileDownloadUrl(edcrApplnDtl.getReportOutputId().getFileStoreId(),
                            ApplicationThreadLocals.getTenantID())));

        edcrDetail.setPlanPdfs(planPdfs);
        edcrDetail.setTenantId(tenantId);

        if (!edcrApplnDtl.getStatus().equalsIgnoreCase("Accepted"))
            edcrDetail.setStatus(edcrApplnDtl.getStatus());

        return edcrDetail;
    }

    public EdcrDetail setEdcrResponseForAcrossTenants(Object[] applnDtls, String stateCityCode) {
        EdcrDetail edcrDetail = new EdcrDetail();
        edcrDetail.setTransactionNumber(String.valueOf(applnDtls[1]));
        edcrDetail.setEdcrNumber(String.valueOf(applnDtls[2]));
        edcrDetail.setStatus(String.valueOf(applnDtls[3]));

        if (String.valueOf(applnDtls[5]) != null)
            edcrDetail.setDxfFile(
                    format(getFileDownloadUrl(String.valueOf(applnDtls[5]), String.valueOf(applnDtls[0]))));

        if (String.valueOf(applnDtls[6]) != null)
            edcrDetail.setUpdatedDxfFile(
                    format(getFileDownloadUrl(String.valueOf(applnDtls[6]), String.valueOf(applnDtls[0]))));

        if (String.valueOf(applnDtls[7]) != null)
            edcrDetail.setPlanReport(format(
                    getFileDownloadUrl(String.valueOf(applnDtls[7]), String.valueOf(applnDtls[0]))));
        Plan pl1 = new Plan();
        PlanInformation pi = new PlanInformation();
        pi.setApplicantName(String.valueOf(applnDtls[4]));
        pl1.setPlanInformation(pi);
        edcrDetail.setPlanDetail(pl1);

        edcrDetail.setTenantId(stateCityCode.concat(".").concat(String.valueOf(applnDtls[0])));

        if (!String.valueOf(applnDtls[3]).equalsIgnoreCase("Accepted"))
            edcrDetail.setStatus(String.valueOf(applnDtls[3]));

        return edcrDetail;
    }

    public List<EdcrDetail> fetchEdcr(final EdcrRequest edcrRequest, final RequestInfoWrapper reqInfoWrapper) {
        List<EdcrApplicationDetail> edcrApplications = new ArrayList<>();
        UserInfo userInfo = reqInfoWrapper.getRequestInfo() == null ? null : reqInfoWrapper.getRequestInfo().getUserInfo();
        City stateCity = cityService.fetchStateCityDetails();
        if (edcrRequest != null && edcrRequest.getTenantId().equalsIgnoreCase(stateCity.getCode())) {
            final Map<String, String> params = new ConcurrentHashMap<>();
            Map<String, String> tenants = tenantUtils.tenantsMap();
            StringBuilder queryStr = new StringBuilder();
            Iterator<Map.Entry<String, String>> tenantItr = tenants.entrySet().iterator();
            while (tenantItr.hasNext()) {
                Map.Entry<String, String> value = tenantItr.next();
                queryStr.append("(select '")
                        .append(value.getKey())
                        .append("' as tenantId,appln.transactionNumber,dtl.dcrNumber,dtl.status,appln.applicantName,dxf.fileStoreId as dxfFileId,scrudxf.fileStoreId as scrutinizedDxfFileId,rofile.fileStoreId as reportOutputId,pdfile.fileStoreId as planDetailFileStore from ")
                        .append(value.getKey())
                        .append(".edcr_application appln, ")
                        .append(value.getKey())
                        .append(".edcr_application_detail dtl, ")
                        .append(value.getKey())
                        .append(".eg_filestoremap dxf, ")
                        .append(value.getKey())
                        .append(".eg_filestoremap scrudxf, ")
                        .append(value.getKey())
                        .append(".eg_filestoremap rofile, ")
                        .append(value.getKey())
                        .append(".eg_filestoremap pdfile ")
                        .append("where appln.id = dtl.application and dtl.dxfFileId=dxf.id and dtl.scrutinizedDxfFileId=scrudxf.id and dtl.reportOutputId=rofile.id and dtl.planDetailFileStore=pdfile.id ");

                if (isNotBlank(edcrRequest.getEdcrNumber())) {
                    queryStr.append("and dtl.dcrNumber=:dcrNumber ");
                    params.put("dcrNumber", edcrRequest.getEdcrNumber());
                }

                if (isNotBlank(edcrRequest.getTransactionNumber())) {
                    queryStr.append("and appln.transactionNumber=:transactionNumber ");
                    params.put("transactionNumber", edcrRequest.getTransactionNumber());
                }

                if (userInfo != null && isNotBlank(userInfo.getId())) {
                    queryStr.append("and appln.thirdPartyUserCode=:thirdPartyUserCode ");
                    params.put("thirdPartyUserCode", userInfo.getId());
                }
                queryStr.append(" order by appln.createddate desc)");
                if (tenantItr.hasNext()) {
                    queryStr.append(" union ");
                }
            }

            final Query query = getCurrentSession().createSQLQuery(queryStr.toString());
            for (final Map.Entry<String, String> param : params.entrySet())
                query.setParameter(param.getKey(), param.getValue());
            List<Object[]> applns = query.list();
            List<EdcrDetail> edcrDetails2 = new ArrayList<>();
            for (Object[] appln : applns)
                edcrDetails2.add(setEdcrResponseForAcrossTenants(appln, stateCity.getCode()));
            return edcrDetails2;
        } else {
            final Criteria criteria = getCurrentSession().createCriteria(EdcrApplicationDetail.class, "edcrApplicationDetail");
            criteria.createAlias("edcrApplicationDetail.application", "application");
            if (edcrRequest != null && isNotBlank(edcrRequest.getEdcrNumber())) {
                criteria.add(Restrictions.eq("edcrApplicationDetail.dcrNumber", edcrRequest.getEdcrNumber()));
            }
            if (edcrRequest != null && isNotBlank(edcrRequest.getTransactionNumber())) {
                criteria.add(Restrictions.eq("application.transactionNumber", edcrRequest.getTransactionNumber()));
            }
            if (userInfo != null && isNotBlank(userInfo.getId())) {
                criteria.add(Restrictions.eq("application.thirdPartyUserCode", userInfo.getId()));
            }
            criteria.addOrder(Order.asc("edcrApplicationDetail.createdDate"));
            criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            edcrApplications = criteria.list();
        }

        if (!edcrApplications.isEmpty())
            return edcrDetailsResponse(edcrApplications, edcrRequest.getTenantId());
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
        if (isBlank(edcrRequest.getTransactionNumber()))
            return new ErrorDetail("BPA-07", "Please enter transaction number");
        if (isNotBlank(edcrRequest.getTransactionNumber())
                && edcrApplicationService.findByTransactionNumber(edcrRequest.getTransactionNumber()) != null) {
            return new ErrorDetail("BPA-01", "Transaction Number should be unique");
        }
        return validatePlanFile(planFile);
    }

    public ErrorDetail validateSearchRequest(final String edcrNumber, final String transactionNumber) {
        ErrorDetail errorDetail = null;
        if (isBlank(edcrNumber) && isBlank(transactionNumber))
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

    public String getFileDownloadUrl(final String fileStoreId, final String tenantId) {
        return String.format(FILE_DOWNLOAD_URL, ApplicationThreadLocals.getDomainURL()) + fileStoreId + "?tenantId="
                + tenantId;
    }

}