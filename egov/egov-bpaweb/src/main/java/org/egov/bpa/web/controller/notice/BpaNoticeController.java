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

package org.egov.bpa.web.controller.notice;

import static org.egov.bpa.utils.BpaConstants.BPAREJECTIONFILENAME;
import static org.egov.bpa.utils.BpaConstants.BUILDINGPERMITFILENAME;
import static org.egov.bpa.utils.BpaConstants.DEMANDNOCFILENAME;
import static org.egov.bpa.utils.BpaConstants.OCDEMANDFILENAME;
import static org.egov.bpa.utils.BpaConstants.OCREJECTIONFILENAME;
import static org.egov.infra.utils.StringUtils.append;
import static org.springframework.http.MediaType.APPLICATION_PDF_VALUE;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.egov.bpa.transaction.notice.OccupancyCertificateNoticesFormat;
import org.egov.bpa.transaction.notice.PermitApplicationNoticesFormat;
import org.egov.bpa.transaction.notice.impl.DemandDetailsFormatImpl;
import org.egov.bpa.transaction.notice.impl.OccupancyCertificateDemandFormatImpl;
import org.egov.bpa.transaction.notice.impl.OccupancyCertificateFormatImpl;
import org.egov.bpa.transaction.notice.impl.OccupancyRejectionFormatImpl;
import org.egov.bpa.transaction.notice.impl.OwnershipTransferNoticeService;
import org.egov.bpa.transaction.notice.impl.PermitOrderFormatImpl;
import org.egov.bpa.transaction.notice.impl.PermitRejectionFormatImpl;
import org.egov.bpa.transaction.notice.impl.PermitRenewalRejectionNoticeService;
import org.egov.bpa.transaction.service.ApplicationBpaService;
import org.egov.bpa.transaction.service.OwnershipTransferService;
import org.egov.bpa.transaction.service.PermitRenewalService;
import org.egov.bpa.transaction.service.oc.OccupancyCertificateService;
import org.egov.infra.custom.CustomImplProvider;
import org.egov.infra.reporting.engine.ReportOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BpaNoticeController {

    private static final String PDFEXTN = ".pdf";
    private static final String INLINE_FILENAME = "inline;filename=";
    private static final String CONTENT_DISPOSITION = "content-disposition";
    private static final String REPORT_FILE_NAME = "occupancycertificate";

    @Autowired
    private ApplicationBpaService applicationBpaService;
    @Autowired
    private OccupancyCertificateService occupancyCertificateService;
    @Autowired
    private CustomImplProvider specificNoticeService;
    @Autowired
    private PermitRenewalService permitRenewalService;
    @Autowired
    private OwnershipTransferService ownershipTransferService;

    @GetMapping(value = "/application/demandnotice/{applicationNumber}", produces = APPLICATION_PDF_VALUE)
    @ResponseBody
    public ResponseEntity<InputStreamResource> viewDemandNoticeReport(@PathVariable final String applicationNumber)
            throws IOException {
        PermitApplicationNoticesFormat bpaNoticeFeature = (PermitApplicationNoticesFormat) specificNoticeService
                .find(DemandDetailsFormatImpl.class, specificNoticeService.getCityDetails());
        ReportOutput reportOutput = bpaNoticeFeature
                .generateNotice(applicationBpaService.findByApplicationNumber(applicationNumber));
        return getFileAsResponseEntity(applicationNumber, reportOutput, DEMANDNOCFILENAME);
    }

    @GetMapping(value = "/application/generatepermitorder/{applicationNumber}", produces = APPLICATION_PDF_VALUE)
    @ResponseBody
    public ResponseEntity<InputStreamResource> generateBuildingPermitOrder(@PathVariable final String applicationNumber,
            HttpServletRequest request) throws IOException {
        PermitApplicationNoticesFormat bpaNoticeFeature = (PermitApplicationNoticesFormat) specificNoticeService
                .find(PermitOrderFormatImpl.class, specificNoticeService.getCityDetails());
        ReportOutput reportOutput = bpaNoticeFeature
                .generateNotice(applicationBpaService.findByApplicationNumber(applicationNumber));
        return getFileAsResponseEntity(applicationNumber, reportOutput, BUILDINGPERMITFILENAME);
    }

    private ResponseEntity<InputStreamResource> getFileAsResponseEntity(String applicationNumber, ReportOutput reportOutput,
            String prefixFileName) {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_PDF)
                .cacheControl(CacheControl.noCache())
                .contentLength(reportOutput.getReportOutputData().length)
                .header(CONTENT_DISPOSITION, String.format(INLINE_FILENAME,
                        append(prefixFileName, applicationNumber) + PDFEXTN))
                .body(new InputStreamResource(reportOutput.asInputStream()));
    }

    @GetMapping(value = "/application/occupancy-certificate/generate-occupancy-certificate/{applicationNumber}", produces = APPLICATION_PDF_VALUE)
    @ResponseBody
    public ResponseEntity<InputStreamResource> generateOccupancyCertificate(@PathVariable final String applicationNumber) {
        OccupancyCertificateNoticesFormat ocNoticeFeature = (OccupancyCertificateNoticesFormat) specificNoticeService
                .find(OccupancyCertificateFormatImpl.class, specificNoticeService.getCityDetails());
        ReportOutput reportOutput = ocNoticeFeature
                .generateNotice(occupancyCertificateService.findByApplicationNumber(applicationNumber));
        return getFileAsResponseEntity(applicationNumber, reportOutput, REPORT_FILE_NAME);
    }

    @GetMapping(value = "/application/rejectionnotice/{applicationNumber}", produces = APPLICATION_PDF_VALUE)
    @ResponseBody
    public ResponseEntity<InputStreamResource> generateRejectionNotice(@PathVariable final String applicationNumber,
            HttpServletRequest request) throws IOException {
        PermitApplicationNoticesFormat bpaNoticeFeature = (PermitApplicationNoticesFormat) specificNoticeService
                .find(PermitRejectionFormatImpl.class, specificNoticeService.getCityDetails());
        ReportOutput reportOutput = bpaNoticeFeature
                .generateNotice(applicationBpaService.findByApplicationNumber(applicationNumber));
        return getFileAsResponseEntity(applicationNumber, reportOutput, BPAREJECTIONFILENAME);
    }
    
    @GetMapping(value = "/application/occupancy-certificate/rejectionnotice/{applicationNumber}", produces = APPLICATION_PDF_VALUE)
    @ResponseBody
    public ResponseEntity<InputStreamResource> generateOccupancyCertificateRejectionNotice(@PathVariable final String applicationNumber) throws IOException {
        OccupancyCertificateNoticesFormat ocNoticeFeature = (OccupancyCertificateNoticesFormat) specificNoticeService
                .find(OccupancyRejectionFormatImpl.class, specificNoticeService.getCityDetails());
        ReportOutput reportOutput = ocNoticeFeature
                .generateNotice(occupancyCertificateService.findByApplicationNumber(applicationNumber));
        return getFileAsResponseEntity(applicationNumber, reportOutput, OCREJECTIONFILENAME);
    }
    
    @GetMapping(value = "/application/occupancy-certificate/demandnotice/{applicationNumber}", produces = APPLICATION_PDF_VALUE)
    @ResponseBody
    public ResponseEntity<InputStreamResource> generateOccupancyCertificateDemandNotice(@PathVariable final String applicationNumber)
            throws IOException {
    	OccupancyCertificateNoticesFormat ocNoticeFeature  = (OccupancyCertificateNoticesFormat) specificNoticeService
                .find(OccupancyCertificateDemandFormatImpl.class, specificNoticeService.getCityDetails());
        ReportOutput reportOutput = ocNoticeFeature
                .generateNotice(occupancyCertificateService.findByApplicationNumber(applicationNumber));
        return getFileAsResponseEntity(applicationNumber, reportOutput, OCDEMANDFILENAME);
    }
    
    @GetMapping(value = "/application/permitrenewal/rejectionnotice/{applicationNumber}", produces = APPLICATION_PDF_VALUE)
    @ResponseBody
    public ResponseEntity<InputStreamResource> generatePermitRenewalRejectionNotice(@PathVariable final String applicationNumber,
            HttpServletRequest request) throws IOException {
    	PermitRenewalRejectionNoticeService renewalNoticeFeature = (PermitRenewalRejectionNoticeService) specificNoticeService
                .find(PermitRenewalRejectionNoticeService.class, specificNoticeService.getCityDetails());
        ReportOutput reportOutput = renewalNoticeFeature
                .generateNotice(permitRenewalService.findByApplicationNumber(applicationNumber));
        return getFileAsResponseEntity(applicationNumber, reportOutput, "permitrenewalrejectionnotice");
    }
    
    @GetMapping(value = "/application/renewal/generaterenewalorder/{applicationNumber}", produces = APPLICATION_PDF_VALUE)
    @ResponseBody
    public ResponseEntity<InputStreamResource> generatePermitRenewalOrder(@PathVariable final String applicationNumber,
            HttpServletRequest request) throws IOException {
    	PermitRenewalRejectionNoticeService renewalNoticeFeature = (PermitRenewalRejectionNoticeService) specificNoticeService
                .find(PermitRenewalRejectionNoticeService.class, specificNoticeService.getCityDetails());
        ReportOutput reportOutput = renewalNoticeFeature
                .generateRenewalOrder(permitRenewalService.findByApplicationNumber(applicationNumber));
        return getFileAsResponseEntity(applicationNumber, reportOutput, "permitrenewalorder");
    }
    
    @GetMapping(value = "/application/ownership/transfer/rejectionnotice/{applicationNumber}", produces = APPLICATION_PDF_VALUE)
    @ResponseBody
    public ResponseEntity<InputStreamResource> generateOwnershipTransferRejectionNotice(@PathVariable final String applicationNumber,
            HttpServletRequest request) throws IOException {
    	OwnershipTransferNoticeService ownershipNoticeFeature = (OwnershipTransferNoticeService) specificNoticeService
                .find(OwnershipTransferNoticeService.class, specificNoticeService.getCityDetails());
        ReportOutput reportOutput = ownershipNoticeFeature
                .generateNotice(ownershipTransferService.findByApplicationNumber(applicationNumber));
        return getFileAsResponseEntity(applicationNumber, reportOutput, "ownershiptransferrejectionnotice");
    }
    
    @GetMapping(value = "/application/ownership/transfer/generateorder/{applicationNumber}", produces = APPLICATION_PDF_VALUE)
    @ResponseBody
    public ResponseEntity<InputStreamResource> generateOwnershipTransferOrder(@PathVariable final String applicationNumber,
            HttpServletRequest request) throws IOException {
    	OwnershipTransferNoticeService ownershipNoticeFeature = (OwnershipTransferNoticeService) specificNoticeService
                .find(OwnershipTransferNoticeService.class, specificNoticeService.getCityDetails());
        ReportOutput reportOutput = ownershipNoticeFeature
                .generateOwnershipOrder(ownershipTransferService.findByApplicationNumber(applicationNumber));
        return getFileAsResponseEntity(applicationNumber, reportOutput, "ownershiptransferorder");
    }
}