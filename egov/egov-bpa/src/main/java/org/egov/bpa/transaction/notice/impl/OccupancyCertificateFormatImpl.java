/*
 * eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 * accountability and the service delivery of the government  organizations.
 *
 *  Copyright (C) <2019>  eGovernments Foundation
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
package org.egov.bpa.transaction.notice.impl;

import static org.egov.bpa.utils.BpaConstants.APPLICATION_MODULE_TYPE;
import static org.egov.bpa.utils.BpaConstants.OCCUPANCY_CERTIFICATE_NOTICE_TYPE;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.egov.bpa.transaction.entity.oc.OCNotice;
import org.egov.bpa.transaction.entity.oc.OccupancyCertificate;
import org.egov.bpa.transaction.notice.OccupancyCertificateNoticesFormat;
import org.egov.bpa.transaction.notice.util.OCNoticeUtil;
import org.egov.infra.exception.ApplicationRuntimeException;
import org.egov.infra.filestore.entity.FileStoreMapper;
import org.egov.infra.filestore.service.FileStoreService;
import org.egov.infra.reporting.engine.ReportFormat;
import org.egov.infra.reporting.engine.ReportOutput;
import org.egov.infra.reporting.engine.ReportRequest;
import org.egov.infra.reporting.engine.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
 * This is default implementation of all. This can be overridden by state, district, ulb and grade level.
 * When override following name convention to be follow,
 * i) If using same code base for multiple state's then for state level OCPermitOrderFormatImpl_<State Name>
 * ii)For state, district level can be override by OCPermitOrderFormatImpl_<District Name>
 * similarly for others can override.
 * If follow above standards, automatically will be pick concern implementation.
 */
@Service
@Transactional(readOnly = true)
public class OccupancyCertificateFormatImpl implements OccupancyCertificateNoticesFormat {

    private static final String REPORT_FILE_NAME = "occupancycertificate";

    @Autowired
    @Qualifier("fileStoreService")
    protected FileStoreService fileStoreService;
    @Autowired
    protected ReportService reportService;
    @Autowired
    protected OCNoticeUtil ocNoticeService;

    @Override
    public ReportOutput generateNotice(final OccupancyCertificate oc) {
        ReportOutput reportOutput = new ReportOutput();
        ReportRequest reportInput = null;
        OCNotice ocNotice = ocNoticeService.findByOcAndNoticeType(oc, OCCUPANCY_CERTIFICATE_NOTICE_TYPE);
        if (ocNotice == null || ocNotice.getNoticeCommon().getNoticeFileStore() == null) {
            Map<String, Object> reportParams = ocNoticeService.buildParametersForOc(oc);
            reportInput = new ReportRequest(REPORT_FILE_NAME, oc == null
                    ? new OccupancyCertificate()
                    : oc, reportParams);
            reportOutput = reportService.createReport(reportInput);
            ocNoticeService.saveOcNotice(oc, oc.getApplicationNumber(), reportOutput, OCCUPANCY_CERTIFICATE_NOTICE_TYPE);
            List<OCNotice> occCertificate = oc.getOcNotices().stream()
                    .filter(ocNotice1 -> ocNotice1.getNoticeCommon().getNoticeType()
                            .equalsIgnoreCase(OCCUPANCY_CERTIFICATE_NOTICE_TYPE))
                    .collect(Collectors.toList());
            if (!occCertificate.isEmpty()) {
                final FileStoreMapper fmp = occCertificate.get(0).getNoticeCommon().getNoticeFileStore();
                Path path = fileStoreService.fetchAsPath(fmp.getFileStoreId(), APPLICATION_MODULE_TYPE);
                try {
                    reportOutput.setReportOutputData(Files.readAllBytes(path));
                } catch (IOException e) {
                    throw new ApplicationRuntimeException("Error occurred, when reading file!!!!", e);
                }
            }

        } else {
            final FileStoreMapper fmp = ocNotice.getNoticeCommon().getNoticeFileStore();
            Path path = fileStoreService.fetchAsPath(fmp.getFileStoreId(), APPLICATION_MODULE_TYPE);
            try {
                reportOutput.setReportOutputData(Files.readAllBytes(path));
            } catch (IOException e) {
                throw new ApplicationRuntimeException("Error occurred, when reading file!!!!", e);
            }
        }
        reportOutput.setReportFormat(ReportFormat.PDF);
        return reportOutput;
    }

}
