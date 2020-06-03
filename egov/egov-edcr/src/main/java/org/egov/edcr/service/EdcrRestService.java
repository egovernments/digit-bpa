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
import org.egov.commons.mdms.config.MdmsConfiguration;
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
import org.joda.time.LocalDate;
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
    private FileStoreService fileStoreService;

    @Autowired
    private TenantUtils tenantUtils;

    @Autowired
    private CityService cityService;

    @Autowired
    private MdmsConfiguration mdmsConfiguration;
    
    @Autowired 
    private OcComparisonService ocComparisonService;
    
    @Autowired
    private EdcrApplicationDetailService applicationDetailService;
    
    public Session getCurrentSession() {
        return entityManager.unwrap(Session.class);
    }

    @Transactional
    public EdcrDetail createEdcr(final EdcrRequest edcrRequest, final MultipartFile file, Map<String, List<Object>> masterData ) {
        EdcrApplication edcrApplication = new EdcrApplication();
        edcrApplication.setMdmsMasterData(masterData);
        EdcrApplicationDetail edcrApplicationDetail = new EdcrApplicationDetail();
        if (ApplicationType.OCCUPANCY_CERTIFICATE.toString()
                .equalsIgnoreCase(edcrRequest.getAppliactionType())) {
            edcrApplicationDetail.setDcrNumber(edcrRequest.getComparisonEdcrNumber());
        }
        List<EdcrApplicationDetail> edcrApplicationDetails = new ArrayList<>();
        edcrApplicationDetails.add(edcrApplicationDetail);
        edcrApplication.setTransactionNumber(edcrRequest.getTransactionNumber());
        if (isNotBlank(edcrRequest.getApplicantName()))
            edcrApplication.setApplicantName(edcrRequest.getApplicantName());
        else
            edcrApplication.setApplicantName(DxfFileConstants.ANONYMOUS_APPLICANT);
        edcrApplication.setArchitectInformation(DxfFileConstants.ANONYMOUS_APPLICANT);
        edcrApplication.setServiceType(edcrRequest.getApplicationSubType());
        if(edcrRequest.getAppliactionType()==null)
        edcrApplication.setApplicationType(ApplicationType.PERMIT);
        else
         edcrApplication.setApplicationType(ApplicationType.valueOf(edcrRequest.getAppliactionType()));
        if(edcrRequest.getPermitNumber()!=null)
        	edcrApplication.setPlanPermitNumber(edcrRequest.getPermitNumber());
        
        
        if(edcrRequest.getPermitDate()!=null)
        {
        	edcrApplication.setApplicationDate(edcrRequest.getPermitDate());
        	edcrApplication.setPermitApplicationDate(edcrRequest.getPermitDate());
        }
       
        edcrApplication.setEdcrApplicationDetails(edcrApplicationDetails);
        edcrApplication.setDxfFile(file);

        if (edcrRequest.getRequestInfo() != null && edcrRequest.getRequestInfo().getUserInfo() != null) {
            edcrApplication.setThirdPartyUserCode(isNotBlank(edcrRequest.getRequestInfo().getUserInfo().getId())
                    ? edcrRequest.getRequestInfo().getUserInfo().getId()
                    : StringUtils.EMPTY);
            edcrApplication
                    .setThirdPartyUserTenant(StringUtils.isNotBlank(edcrRequest.getTenantId())
                            ? edcrRequest.getTenantId()
                            : edcrRequest.getRequestInfo().getUserInfo().getTenantId());
        }

        edcrApplication = edcrApplicationService.createRestEdcr(edcrApplication);
        return setEdcrResponse(edcrApplication.getEdcrApplicationDetails().get(0), edcrRequest);
    }

    @Transactional
    public List<EdcrDetail> edcrDetailsResponse(List<EdcrApplicationDetail> edcrApplications, EdcrRequest edcrRequest) {
        List<EdcrDetail> edcrDetails = new ArrayList<>();
        for (EdcrApplicationDetail edcrApp : edcrApplications)
            edcrDetails.add(setEdcrResponse(edcrApp, edcrRequest));

        return edcrDetails;
    }

    public EdcrDetail setEdcrResponse(EdcrApplicationDetail edcrApplnDtl, EdcrRequest edcrRequest) {
        EdcrDetail edcrDetail = new EdcrDetail();
        List<String> planPdfs = new ArrayList<>();
        edcrDetail.setTransactionNumber(edcrApplnDtl.getApplication().getTransactionNumber());
        LOG.info("edcr number == " + edcrApplnDtl.getDcrNumber());
        edcrDetail.setEdcrNumber(edcrApplnDtl.getDcrNumber());
        edcrDetail.setStatus(edcrApplnDtl.getStatus());
        LOG.info("application number ==" + edcrApplnDtl.getApplication().getApplicationNumber());
        edcrDetail.setApplicationNumber(edcrApplnDtl.getApplication().getApplicationNumber());
        edcrDetail.setApplicationDate(edcrApplnDtl.getApplication().getApplicationDate());

        if (edcrApplnDtl.getApplication().getPlanPermitNumber() != null) {
            edcrDetail.setPermitNumber(edcrApplnDtl.getApplication().getPlanPermitNumber());
        }
        if (edcrApplnDtl.getApplication().getPermitApplicationDate() != null) {
            edcrDetail.setPermitDate(edcrApplnDtl.getApplication().getPermitApplicationDate());
        }
        ApplicationType applicationType = edcrApplnDtl.getApplication().getApplicationType();
        if (applicationType != null) {
            Boolean mdmsEnabled = mdmsConfiguration.getMdmsEnabled();
            if (mdmsEnabled != null && mdmsEnabled) {
                if (ApplicationType.PERMIT.getApplicationTypeVal()
                        .equalsIgnoreCase(edcrApplnDtl.getApplication().getApplicationType().getApplicationTypeVal())) {
                    edcrDetail.setAppliactionType("BUILDING_PLAN_SCRUTINY");
                } else {
                    edcrDetail.setAppliactionType("BUILDING_OC_PLAN_SCRUTINY");
                }
            } else
                edcrDetail.setAppliactionType(applicationType.getApplicationTypeVal());

        }
        if (edcrApplnDtl.getApplication().getServiceType() != null)
            edcrDetail.setApplicationSubType(edcrApplnDtl.getApplication().getServiceType());
        
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
            if (file == null) {
                Plan pl1 = new Plan();
                PlanInformation pi = new PlanInformation();
                pi.setApplicantName(edcrApplnDtl.getApplication().getApplicantName());
                pl1.setPlanInformation(pi);
                edcrDetail.setPlanDetail(pl1);
            } else {
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                Plan pl1 = mapper.readValue(file, Plan.class);
                pl1.getPlanInformation().setApplicantName(edcrApplnDtl.getApplication().getApplicantName());
                if (LOG.isInfoEnabled())
                    LOG.info("**************** Plan detail object **************" + pl1);
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
        edcrDetail.setTenantId(edcrRequest.getTenantId());
        
        if(StringUtils.isNotBlank(edcrRequest.getComparisonEdcrNumber()))
        edcrDetail.setComparisonEdcrNumber(edcrRequest.getComparisonEdcrNumber());
        
        if (!edcrApplnDtl.getStatus().equalsIgnoreCase("Accepted"))
            edcrDetail.setStatus(edcrApplnDtl.getStatus());

        return edcrDetail;
    }

    public EdcrDetail setEdcrResponseForAcrossTenants(Object[] applnDtls, String stateCityCode) {
        EdcrDetail edcrDetail = new EdcrDetail();
        edcrDetail.setTransactionNumber(String.valueOf(applnDtls[1]));
        edcrDetail.setEdcrNumber(String.valueOf(applnDtls[2]));
        edcrDetail.setStatus(String.valueOf(applnDtls[3]));
        edcrDetail.setApplicationDate(new LocalDate(String.valueOf(applnDtls[9])).toDate());
        edcrDetail.setApplicationNumber(String.valueOf(applnDtls[10]));

       
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
                        .append("' as tenantId,appln.transactionNumber,dtl.dcrNumber,dtl.status,appln.applicantName,dxf.fileStoreId as dxfFileId,scrudxf.fileStoreId as scrutinizedDxfFileId,rofile.fileStoreId as reportOutputId,pdfile.fileStoreId as planDetailFileStore,appln.applicationDate,appln.applicationNumber from ")
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
                
                String appliactionType = edcrRequest.getAppliactionType();
                if (isNotBlank(appliactionType)) {
                    ApplicationType applicationType = null;
                    if ("BUILDING_PLAN_SCRUTINY".equalsIgnoreCase(appliactionType)) {
                        applicationType = ApplicationType.PERMIT;
                    } else if ("BUILDING_OC_PLAN_SCRUTINY".equalsIgnoreCase(appliactionType)) {
                        applicationType = ApplicationType.OCCUPANCY_CERTIFICATE;
                    } else if ("Permit".equalsIgnoreCase(appliactionType)) {
                        applicationType = ApplicationType.PERMIT;
                    } else if ("Occupancy certificate".equalsIgnoreCase(appliactionType)) {
                        applicationType = ApplicationType.OCCUPANCY_CERTIFICATE;
                    }
                    queryStr.append("and appln.applicationType=:applicationtype ");
                    params.put("applicationtype", applicationType.toString());
                }

                if (isNotBlank(edcrRequest.getApplicationSubType())) {
                    queryStr.append("and appln.serviceType=:servicetype ");
                    params.put("servicetype", edcrRequest.getApplicationSubType());
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
            if (applns.isEmpty()) {
                EdcrDetail edcrDetail = new EdcrDetail();
                edcrDetail.setErrors("No Record Found");
                return Arrays.asList(edcrDetail);
            } else {
                List<EdcrDetail> edcrDetails2 = new ArrayList<>();
                for (Object[] appln : applns)
                    edcrDetails2.add(setEdcrResponseForAcrossTenants(appln, stateCity.getCode()));
                return edcrDetails2;
            }
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
            
            String appliactionType = edcrRequest.getAppliactionType();
            
            if (edcrRequest != null && isNotBlank(appliactionType)) {
                ApplicationType applicationType = null;
                if ("BUILDING_PLAN_SCRUTINY".equalsIgnoreCase(appliactionType)) {
                    applicationType = ApplicationType.PERMIT;
                } else if ("BUILDING_OC_PLAN_SCRUTINY".equalsIgnoreCase(appliactionType)) {
                    applicationType = ApplicationType.OCCUPANCY_CERTIFICATE;
                }
                if ("Permit".equalsIgnoreCase(appliactionType)) {
                    applicationType = ApplicationType.PERMIT;
                } else if ("Occupancy certificate".equalsIgnoreCase(appliactionType)) {
                    applicationType = ApplicationType.OCCUPANCY_CERTIFICATE;
                }
                criteria.add(Restrictions.eq("application.applicationType", applicationType));
            }

            if (edcrRequest != null && isNotBlank(edcrRequest.getApplicationSubType())) {
                criteria.add(Restrictions.eq("application.serviceType", edcrRequest.getApplicationSubType()));
            }
            
            criteria.addOrder(Order.asc("edcrApplicationDetail.createdDate"));
            criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            edcrApplications = criteria.list();
        }
        
        LOG.info("The number of records = " + edcrApplications.size());
        if (edcrApplications.isEmpty()) {
            EdcrDetail edcrDetail = new EdcrDetail();
            edcrDetail.setErrors("No Record Found");
            return Arrays.asList(edcrDetail);
        } else {
            return edcrDetailsResponse(edcrApplications, edcrRequest);
        }
    }

    public ErrorDetail validatePlanFile(final MultipartFile file) {
        List<String> dcrAllowedExtenstions = new ArrayList<>(
                Arrays.asList(edcrApplicationSettings.getValue("dcr.dxf.allowed.extenstions").split(",")));

        
        String fileSize = edcrApplicationSettings.getValue("dcr.dxf.max.size");
		final String maxAllowSizeInMB = fileSize;
        String extension;
		if (file != null && !file.isEmpty()) {
		    extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1);
		    if (extension != null && !extension.isEmpty()) {
		       
		        if (!dcrAllowedExtenstions.contains(extension.toLowerCase())) {
		            return new ErrorDetail("BPA-02", "Please upload " + dcrAllowedExtenstions + " format file only");
		        } else if (file.getSize() > (Long.valueOf(maxAllowSizeInMB) * 1024 * 1024)) {
		            return new ErrorDetail("BPA-04", "File size should not exceed 30 MB");
		        } /*
		           * else if (allowedExtenstions.contains(extension.toLowerCase()) && (!mimeTypes.contains(mimeType) ||
		           * StringUtils.countMatches(file.getOriginalFilename(), ".") > 1 || file.getOriginalFilename().contains("%00")))
		           * { return new ErrorDetail("BPA-03", "Malicious file upload"); }
		           */
		    }
		} else {
		    return new ErrorDetail(BPA_05, "Please upload plan file, It is mandatory");
		}
		
		return null;
    }

    public ErrorDetail validateEdcrRequest(final EdcrRequest edcrRequest, final MultipartFile planFile) {
    	if (edcrRequest.getRequestInfo() == null)
            return new ErrorDetail("BPA-07", "Required request body is missing");
    	else if (edcrRequest.getRequestInfo().getUserInfo() == null || (edcrRequest.getRequestInfo().getUserInfo() != null && isBlank(edcrRequest.getRequestInfo().getUserInfo().getId())))
            return new ErrorDetail("BPA-07", "User id is mandatory");
    	
        if (isBlank(edcrRequest.getTransactionNumber()))
            return new ErrorDetail("BPA-07", "Please enter transaction number");
        if (isNotBlank(edcrRequest.getTransactionNumber())
                && edcrApplicationService.findByTransactionNumber(edcrRequest.getTransactionNumber()) != null) {
            return new ErrorDetail("BPA-01", "Transaction Number should be unique");
        }
        
        return validatePlanFile(planFile);
    }
    
    public ErrorDetail validateEdcrOcRequest(final EdcrRequest edcrRequest, final MultipartFile planFile) {
    	if (edcrRequest.getRequestInfo() == null)
            return new ErrorDetail("BPA-07", "Required request body is missing");
    	else if (edcrRequest.getRequestInfo().getUserInfo() == null || (edcrRequest.getRequestInfo().getUserInfo() != null && isBlank(edcrRequest.getRequestInfo().getUserInfo().getId())))
            return new ErrorDetail("BPA-07", "User id is mandatory");
    	
        if (isBlank(edcrRequest.getTransactionNumber()))
            return new ErrorDetail("BPA-07", "Transaction number is mandatory");
        
        if (null==edcrRequest.getPermitDate())
            return new ErrorDetail("BPA-08", "Permit Date is mandatory");
        if (isNotBlank(edcrRequest.getTransactionNumber())
                && edcrApplicationService.findByTransactionNumber(edcrRequest.getTransactionNumber()) != null) {
            return new ErrorDetail("BPA-01", "Transaction Number should be unique");
            
        }
        
        return validatePlanFile(planFile);
    }
    
    public List<ErrorDetail> validateScrutinizeOcRequest(final EdcrRequest edcrRequest, final MultipartFile planFile) {
        List<ErrorDetail> errorDetails = new ArrayList<>();

        if (edcrRequest.getRequestInfo() == null)
            errorDetails.add(new ErrorDetail("BPA-07", "Required request body is missing"));
        else if (edcrRequest.getRequestInfo().getUserInfo() == null || (edcrRequest.getRequestInfo().getUserInfo() != null
                && isBlank(edcrRequest.getRequestInfo().getUserInfo().getId())))
            errorDetails.add(new ErrorDetail("BPA-08", "User id is mandatory"));

        if (isBlank(edcrRequest.getTransactionNumber()))
            errorDetails.add(new ErrorDetail("BPA-09", "Transaction number is mandatory"));

        if (null == edcrRequest.getPermitDate())
            errorDetails.add(new ErrorDetail("BPA-10", "Permit Date is mandatory"));
        if (isNotBlank(edcrRequest.getTransactionNumber())
                && edcrApplicationService.findByTransactionNumber(edcrRequest.getTransactionNumber()) != null) {
            errorDetails.add(new ErrorDetail("BPA-11", "Transaction Number should be unique"));

        }

        String dcrNo = edcrRequest.getComparisonEdcrNumber();
        if (StringUtils.isBlank(dcrNo)) {
            errorDetails.add(new ErrorDetail("BPA-29", "Comparison eDcr number is mandatory"));
        } else {
            EdcrApplicationDetail permitDcr = applicationDetailService.findByDcrNumberAndTPUserTenant(dcrNo,
                    edcrRequest.getTenantId());

            if (permitDcr != null && permitDcr.getApplication() != null
                    && StringUtils.isBlank(permitDcr.getApplication().getServiceType())) {
                errorDetails.add(new ErrorDetail("BPA-26", "No service type found for dcr number " + dcrNo));
            }

            if (permitDcr == null) {
                errorDetails.add(new ErrorDetail("BPA-24", "No record found with dcr number " + dcrNo));
            }

            if (permitDcr != null && permitDcr.getApplication() != null
                    && edcrRequest.getAppliactionType()
                            .equalsIgnoreCase(permitDcr.getApplication().getApplicationType().toString())) {
                errorDetails.add(new ErrorDetail("BPA-27", "Application types are same"));
            }

            if (permitDcr != null && permitDcr.getApplication() != null
                    && !edcrRequest.getApplicationSubType().equalsIgnoreCase(permitDcr.getApplication().getServiceType())) {
                errorDetails.add(new ErrorDetail("BPA-28", "Service types are not mathing"));
            }
        }

        ErrorDetail validatePlanFile = validatePlanFile(planFile);
        if (validatePlanFile != null)
            errorDetails.add(validatePlanFile);

        return errorDetails;
    }
        
    public List<ErrorDetail> validateEdcrMandatoryFields(final EdcrRequest edcrRequest) {
        List<ErrorDetail> errors = new ArrayList<>();
        if (StringUtils.isBlank(edcrRequest.getAppliactionType())) {
            errors.add(new ErrorDetail("BPA-10", "Application type is missing"));
        }

        if (StringUtils.isBlank(edcrRequest.getApplicationSubType())) {
            errors.add(new ErrorDetail("BPA-11", "Service type is missing"));
        }
         
        return errors;
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