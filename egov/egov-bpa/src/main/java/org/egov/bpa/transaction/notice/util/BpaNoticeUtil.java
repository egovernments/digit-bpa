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
package org.egov.bpa.transaction.notice.util;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_MODULE_TYPE;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_CANCELLED;
import static org.egov.bpa.utils.BpaConstants.BPADEMANDNOTICETITLE;
import static org.egov.bpa.utils.BpaConstants.BPA_ADM_FEE;
import static org.egov.bpa.utils.BpaConstants.ST_CODE_14;
import static org.egov.bpa.utils.BpaConstants.ST_CODE_15;
import static org.egov.bpa.utils.BpaConstants.getEdcrRequiredServices;
import static org.egov.infra.security.utils.SecureCodeUtils.generatePDF417Code;
import static org.egov.infra.utils.DateUtils.currentDateToDefaultDateFormat;
import static org.egov.infra.utils.PdfUtils.appendFiles;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.egov.bpa.master.entity.ServiceType;
import org.egov.bpa.transaction.entity.ApplicationPermitConditions;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.BpaNotice;
import org.egov.bpa.transaction.entity.BuildingSubUsage;
import org.egov.bpa.transaction.entity.BuildingSubUsageDetails;
import org.egov.bpa.transaction.entity.Response;
import org.egov.bpa.transaction.entity.SiteDetail;
import org.egov.bpa.transaction.entity.dto.PermitFeeHelper;
import org.egov.bpa.transaction.entity.enums.PermitConditionType;
import org.egov.bpa.transaction.entity.oc.OccupancyCertificate;
import org.egov.bpa.transaction.repository.BpaNoticeRepository;
import org.egov.bpa.transaction.service.ApplicationBpaService;
import org.egov.bpa.transaction.service.BpaApplicationPermitConditionsService;
import org.egov.bpa.transaction.service.DcrRestService;
import org.egov.bpa.transaction.workflow.BpaWorkFlowService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.bpa.utils.BpaUtils;
import org.egov.common.entity.bpa.SubOccupancy;
import org.egov.common.entity.bpa.Usage;
import org.egov.commons.Installment;
import org.egov.dcb.bean.Receipt;
import org.egov.demand.model.EgDemandDetails;
import org.egov.demand.model.EgdmCollectedReceipt;
import org.egov.eis.entity.Assignment;
import org.egov.infra.admin.master.service.AppConfigValueService;
import org.egov.infra.admin.master.service.CityService;
import org.egov.infra.config.core.ApplicationThreadLocals;
import org.egov.infra.exception.ApplicationRuntimeException;
import org.egov.infra.filestore.entity.FileStoreMapper;
import org.egov.infra.filestore.service.FileStoreService;
import org.egov.infra.reporting.engine.ReportOutput;
import org.egov.infra.reporting.engine.ReportRequest;
import org.egov.infra.reporting.engine.ReportService;
import org.egov.infra.reporting.util.ReportUtil;
import org.egov.infra.utils.DateUtils;
import org.egov.infra.validation.exception.ValidationError;
import org.egov.infra.validation.exception.ValidationException;
import org.egov.infra.workflow.entity.StateHistory;
import org.egov.pims.commons.Position;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BpaNoticeUtil {

    private static final String NOTICE_TYPE_PERMITORDER = "PERMITORDER";
    private static final String PERMIT_ORDER_TITLE = "permitOrderTitle";
    private static final String TOTAL_CARPET_AREA = "totalCarpetArea";
    private static final String TOTAL_FLOOR_AREA = "totalFloorArea";
    private static final String TOTAL_BLT_UP_AREA = "totalBltUpArea";
    private static final String EXST_TOTAL_CARPET_AREA = "exstTotalCarpetArea";
    private static final String EXST_TOTAL_FLOOR_AREA = "exstTotalFloorArea";
    private static final String EXST_TOTAL_BLT_UP_AREA = "exstTotalBltUpArea";
    public static final String TWO_NEW_LINE = "\n\n";
    public static final String N_A = "N/A";
    public static final String ONE_NEW_LINE = "\n";
    private static final String APPLICATION_PDF = "application/pdf";
    private static final String APPLICATION_REJECTION_REASON = "applctn.reject.reason";
    private static final String APPLICATION_AUTO_REJECTION_REASON = "applctn.auto.reject.reason";
    private static final String MERGE_PM_EDCR_ENABLED = "MERGEPERMITEDCR";
    private static final String MODULE_NAME = "BPA";
    public static final String SECTION_CLERK = "SECTION CLERK";

    @Autowired
    @Qualifier("fileStoreService")
    protected FileStoreService fileStoreService;
    @Autowired
    private ReportService reportService;
    @Autowired
    private BpaUtils bpaUtils;
    @Autowired
    @Qualifier("parentMessageSource")
    private MessageSource bpaMessageSource;
    @Autowired
    private BpaNoticeRepository bpaNoticeRepository;
    @Autowired
    private ApplicationBpaService applicationBpaService;
    @Autowired
    private BpaApplicationPermitConditionsService bpaApplicationPermitConditionsService;
    @Autowired
    private BpaWorkFlowService bpaWorkFlowService;
    @Autowired
    private DcrRestService dcrRestService;
    @Autowired
    private AppConfigValueService appConfigValuesService;
    @Autowired
    private CityService cityService;

    public BpaNotice findByApplicationAndNoticeType(final BpaApplication application, final String noticeType) {
        return bpaNoticeRepository.findByApplicationAndNoticeType(application, noticeType);
    }

    private Map<String, Object> buildParametersForDemandDetails(final BpaApplication bpaApplication) {
        List<Response> demandResponseList = new ArrayList<>();
        BigDecimal totalPendingAmt = BigDecimal.ZERO;
        final Map<String, Object> reportParams = new HashMap<>();
        Installment currentInstallemnt = bpaApplication.getDemand().getEgInstallmentMaster();
        for (final EgDemandDetails demandDtl : bpaApplication.getDemand().getEgDemandDetails()) {
            Response response = new Response();
            if (!BPA_ADM_FEE.equalsIgnoreCase(demandDtl.getEgDemandReason().getEgDemandReasonMaster().getReasonMaster())
                    && demandDtl.getBalance().compareTo(BigDecimal.ZERO) > 0) {
                response.setDemandDescription(
                        demandDtl.getEgDemandReason().getEgDemandReasonMaster().getReasonMaster());
                response.setDemandAmount(demandDtl.getBalance().setScale(2, BigDecimal.ROUND_HALF_EVEN));
                totalPendingAmt = totalPendingAmt.add(demandDtl.getBalance());
                demandResponseList.add(response);
            }
        }
        reportParams.put("installmentDesc",
                currentInstallemnt.getDescription() == null ? EMPTY : currentInstallemnt.getDescription());
        reportParams.put("demandResponseList", demandResponseList);
        reportParams.put("totalPendingAmt", totalPendingAmt.setScale(2, BigDecimal.ROUND_HALF_EVEN));
        return reportParams;
    }

    public ReportOutput getReportOutput(BpaApplication bpaApplication, String fileName, BpaNotice bpaNotice,
            String bparejectionfilename, String bpaRejectionNoticeType, HttpServletRequest request) throws IOException {
        ReportOutput reportOutput = new ReportOutput();
        if (bpaNotice == null || bpaNotice.getNoticeFileStore() == null) {
            final Map<String, Object> reportParams = buildParametersForReport(bpaApplication);
            reportParams.putAll(getUlbDetails());
            reportParams.putAll(buildParametersForDemandDetails(bpaApplication));
            ReportRequest reportInput = new ReportRequest(bparejectionfilename, bpaApplication, reportParams);
            reportOutput = reportService.createReport(reportInput);
            saveBpaNotices(bpaApplication, reportOutput, null, fileName, bpaRejectionNoticeType, request);
        } else {
            final FileStoreMapper fmp = bpaNotice.getNoticeFileStore();
            Path path = fileStoreService.fetchAsPath(fmp.getFileStoreId(), APPLICATION_MODULE_TYPE);
            reportOutput.setReportOutputData(Files.readAllBytes(path));
        }
        return reportOutput;
    }

    public BpaNotice saveBpaNotices(final BpaApplication application, ReportOutput reportOutput,
            ReportOutput reportOutputForPermitNote, String fileName,
            String noticeType, HttpServletRequest request) throws IOException {
        BpaNotice bpaNotice = new BpaNotice();
        bpaNotice.setApplication(application);
        Boolean qrCodeEnabled = getEdcrRequiredServices().contains(application.getServiceType().getCode());
        if (Boolean.valueOf(appConfigValuesService.getConfigValuesByModuleAndKey(MODULE_NAME,
                MERGE_PM_EDCR_ENABLED).get(0).getValue()) && qrCodeEnabled
                && noticeType.equalsIgnoreCase(NOTICE_TYPE_PERMITORDER)
                && application.geteDcrNumber() != null) {
            final List<InputStream> pdfs = new ArrayList<>();
            pdfs.add(new ByteArrayInputStream(reportOutput.getReportOutputData()));

            try {
                String reportOutputId = dcrRestService.getDcrReport(application.geteDcrNumber(), request);
                Path path = fileStoreService.fetchAsPath(reportOutputId, "Digit DCR");
                byte[] convertedDigitDcr = Files.readAllBytes(path);
                ByteArrayInputStream dcrReport = new ByteArrayInputStream(convertedDigitDcr);
                if (dcrReport != null)
                    pdfs.add(dcrReport);

                if (reportOutputForPermitNote != null)
                    pdfs.add(new ByteArrayInputStream(reportOutputForPermitNote.getReportOutputData()));

                final byte[] data = appendFiles(pdfs);

                bpaNotice.setNoticeFileStore(fileStoreService.store(new ByteArrayInputStream(data), fileName, APPLICATION_PDF,
                        APPLICATION_MODULE_TYPE));

            } catch (final ApplicationRuntimeException e) {
                throw new ValidationException(Arrays.asList(new ValidationError(
                        "Error occurred, while fetching plan scrutiny report output file!!!!!", e.getMessage())));
            }
            updatePermitOrderReport(bpaNotice.getNoticeFileStore(), application);
        } else if (!Boolean.valueOf(appConfigValuesService.getConfigValuesByModuleAndKey(MODULE_NAME,
                MERGE_PM_EDCR_ENABLED).get(0).getValue()) && qrCodeEnabled
                && noticeType.equalsIgnoreCase(NOTICE_TYPE_PERMITORDER)) {
            final List<InputStream> pdfs = new ArrayList<>();
            pdfs.add(new ByteArrayInputStream(reportOutput.getReportOutputData()));
            if (reportOutputForPermitNote != null)
                pdfs.add(new ByteArrayInputStream(reportOutputForPermitNote.getReportOutputData()));

            final byte[] data = appendFiles(pdfs);
            bpaNotice.setNoticeFileStore(
                    fileStoreService.store(new ByteArrayInputStream(data), fileName,
                            APPLICATION_PDF,
                            APPLICATION_MODULE_TYPE));
        } else {
            bpaNotice.setNoticeFileStore(
                    fileStoreService.store(new ByteArrayInputStream(reportOutput.getReportOutputData()), fileName,
                            APPLICATION_PDF,
                            APPLICATION_MODULE_TYPE));
        }
        bpaNotice.setNoticeGeneratedDate(new Date());
        bpaNotice.setNoticeType(noticeType);
        application.addNotice(bpaNotice);
        applicationBpaService.saveBpaApplication(application);
        return bpaNotice;
    }

    public Map<String, Object> getUlbDetails() {
        final Map<String, Object> ulbDetailsReportParams = new HashMap<>();
        ulbDetailsReportParams.put("cityName", ApplicationThreadLocals.getCityName());
        ulbDetailsReportParams.put("logoPath", cityService.getCityLogoAsStream());
        ulbDetailsReportParams.put("ulbName", ApplicationThreadLocals.getMunicipalityName());
        ulbDetailsReportParams.put("cityNameLocal", ApplicationThreadLocals.getCityNameLocal());
        return ulbDetailsReportParams;
    }

    public Map<String, Object> buildParametersForReport(final BpaApplication bpaApplication) {
        StringBuilder serviceTypeDesc = new StringBuilder();
        final Map<String, Object> reportParams = new HashMap<>();
        reportParams.put("bpademandtitle", WordUtils.capitalize(BPADEMANDNOTICETITLE));
        reportParams.put("currentDate", currentDateToDefaultDateFormat());
        String approverName = getApproverName(bpaApplication);
        String approverDesignation = getApproverDesignation(
                bpaWorkFlowService.getAmountRuleByServiceType(bpaApplication).intValue());

        String lawAct;
        if (!bpaApplication.getSiteDetail().isEmpty() && bpaApplication.getSiteDetail().get(0).getIsappForRegularization()
        		&& bpaApplication.getDemand().getAmtCollected().compareTo(BigDecimal.ZERO)>0) {
            String applicantName = bpaApplication.getOwner().getName();
            String serviceType = bpaApplication.getServiceType().getDescription();
            SiteDetail site = bpaApplication.getSiteDetail().get(0);
            List<Receipt> receipts = new ArrayList();

            for (final EgDemandDetails demandDtl : bpaApplication.getDemand().getEgDemandDetails())
                for (final EgdmCollectedReceipt collRecpt : demandDtl.getEgdmCollectedReceipts())
                    if (!collRecpt.isCancelled()) {
                        Receipt receipt = new Receipt();
                        receipt.setReceiptNumber(collRecpt.getReceiptNumber());
                        receipt.setReceiptDate(collRecpt.getReceiptDate());
                        receipt.setReceiptAmt(collRecpt.getAmount());
                        receipts.add(receipt);
                    }

            receipts.sort((o1, o2) -> o2.getReceiptDate().compareTo(o1.getReceiptDate()));

            String regularizationMsg1 = bpaMessageSource.getMessage("msg.regularization.permit.desc1",
                    new String[] { applicantName, serviceType, site.getReSurveyNumber(), site.getPlotdoornumber(),
                            site.getNearestbuildingnumber(),
                            DateUtils.toDefaultDateFormat(bpaApplication.getPlanPermissionDate()), applicantName,
                            String.valueOf(receipts.get(0).getReceiptAmt()),
                            String.valueOf(receipts.get(0).getReceiptNumber()),
                            String.valueOf(DateUtils.toDefaultDateFormat(receipts.get(0).getReceiptDate())) },
                    null);
            String regularizationMsg2 = bpaMessageSource.getMessage("msg.regularization.permit.desc2",
                    new String[] { approverName, approverDesignation, serviceType, site.getPlotdoornumber(),
                            site.getNearestbuildingnumber(),
                            DateUtils.toDefaultDateFormat(bpaApplication.getPlanPermissionDate()), applicantName,
                            String.valueOf(receipts.get(0).getReceiptAmt()),
                            String.valueOf(receipts.get(0).getReceiptNumber()),
                            String.valueOf(DateUtils.toDefaultDateFormat(receipts.get(0).getReceiptDate())) },
                    null);
            reportParams.put("regularizationMsg1", regularizationMsg1);
            reportParams.put("regularizationMsg2", regularizationMsg2);

            lawAct = "APPENDIX -I, [See rule 146 (3)]";
        } else {
            lawAct = "APPENDIX C, [See Rule 11 (3)]";
        }
        reportParams.put("lawAct", lawAct);
        reportParams.put("applicationNumber", bpaApplication.getApplicationNumber());
        reportParams.put("buildingPermitNumber",
                bpaApplication.getPlanPermissionNumber() == null ? EMPTY : bpaApplication.getPlanPermissionNumber());
        reportParams.put("applicantName", bpaApplication.getOwner().getName());
        reportParams.put("applicantAddress",
                bpaApplication.getOwner() == null ? "Not Mentioned" : bpaApplication.getOwner().getAddress());
        Boolean serviceEnabled = getEdcrRequiredServices().contains(bpaApplication.getServiceType().getCode());
        Boolean mergeEnabled = Boolean.valueOf(appConfigValuesService.getConfigValuesByModuleAndKey(MODULE_NAME,
                MERGE_PM_EDCR_ENABLED).get(0).getValue());
        reportParams.put("qrCodeEnabled", mergeEnabled && serviceEnabled && bpaApplication.geteDcrNumber() != null);
        reportParams.put("applicationDate", DateUtils.getDefaultFormattedDate(bpaApplication.getApplicationDate()));
        if (APPLICATION_STATUS_CANCELLED.equalsIgnoreCase(bpaApplication.getStatus().getCode())) {
            reportParams.put("rejectionReasons", buildRejectionReasons(bpaApplication));
        } else {
            reportParams.put("permitConditions", buildPermitConditions(bpaApplication));
        }
        reportParams.put("additionalNotes", getBuildingCommonPermitNotes());
        String amenities = bpaApplication.getApplicationAmenity().stream().map(ServiceType::getDescription)
                .collect(Collectors.joining(", "));
        if (bpaApplication.getApplicationAmenity().isEmpty()) {
            serviceTypeDesc.append(bpaApplication.getServiceType().getDescription());
        } else {
            serviceTypeDesc.append(bpaApplication.getServiceType().getDescription()).append(", ")
                    .append(amenities);
        }
        reportParams.put("serviceTypeDesc", serviceTypeDesc.toString());
        reportParams.put("serviceTypeForDmd", bpaApplication.getServiceType().getDescription());
        reportParams.put("amenities", StringUtils.isBlank(amenities) ? "N/A" : amenities);
        reportParams.put("occupancy", bpaApplication.getOccupanciesName());
        if (!bpaApplication.getSiteDetail().isEmpty()) {
            reportParams.put("electionWard", bpaApplication.getSiteDetail().get(0).getElectionBoundary() != null
    				? bpaApplication.getSiteDetail().get(0).getElectionBoundary().getName() : "");
            reportParams.put("revenueWard", bpaApplication.getSiteDetail().get(0).getAdminBoundary() != null
    				? bpaApplication.getSiteDetail().get(0).getAdminBoundary().getName() : "");
            reportParams.put("landExtent", bpaApplication.getSiteDetail().get(0).getExtentinsqmts().setScale(2,
                    BigDecimal.ROUND_HALF_UP));
            reportParams.put("buildingNo", bpaApplication.getSiteDetail().get(0).getPlotnumber() == null
                    ? EMPTY
                    : bpaApplication.getSiteDetail().get(0).getPlotnumber());
            reportParams.put("nearestBuildingNo",
                    bpaApplication.getSiteDetail().get(0).getNearestbuildingnumber() == null
                            ? EMPTY
                            : bpaApplication.getSiteDetail().get(0).getNearestbuildingnumber());
            reportParams.put("surveyNo", bpaApplication.getSiteDetail().get(0).getReSurveyNumber() == null
                    ? EMPTY
                    : bpaApplication.getSiteDetail().get(0).getReSurveyNumber());
            reportParams.put("village", bpaApplication.getSiteDetail().get(0).getLocationBoundary() == null
                    ? EMPTY
                    : bpaApplication.getSiteDetail().get(0).getLocationBoundary().getName());
            reportParams.put("taluk",
                    (bpaApplication.getSiteDetail().get(0).getPostalAddress() == null
                            || bpaApplication.getSiteDetail().get(0).getPostalAddress().getTaluk() == null)
                                    ? EMPTY
                                    : bpaApplication.getSiteDetail().get(0).getPostalAddress().getTaluk());
            reportParams.put("district", bpaApplication.getSiteDetail().get(0).getPostalAddress() == null
                    ? cityService.getDistrictName()
                    : bpaApplication.getSiteDetail().get(0).getPostalAddress().getDistrict());
        }
        reportParams.put("certificateValidity",
                getValidityDescription(bpaApplication.getServiceType().getCode(), bpaApplication.getServiceType().getValidity(), 
                		bpaApplication.getPlanPermissionDate()));
        reportParams.put("isBusinessUser", bpaUtils.logedInuseCitizenOrBusinessUser());
        reportParams.put("designation", approverDesignation);
        reportParams.put("approverName", approverName);
        reportParams.put("qrCode", generatePDF417Code(buildQRCodeDetails(bpaApplication)));
        reportParams.put("mobileNo", bpaApplication.getOwner().getUser().getMobileNumber());
        StringBuilder totalBuiltUpArea = new StringBuilder();
        if (bpaApplication.getPermitOccupancies().size() > 1) {
            for (Map.Entry<SubOccupancy, BigDecimal> innerMap : bpaUtils
                    .getBlockWiseOccupancyAndBuiltupArea(bpaApplication.getBuildingDetail()).entrySet()) {
                totalBuiltUpArea = totalBuiltUpArea.append(innerMap.getKey().getDescription()).append(" : ")
                        .append(innerMap.getValue().setScale(2,
                                BigDecimal.ROUND_HALF_UP))
                        .append(" Sq.Mtrs, ");
            }
            reportParams.put("totalBuiltUpArea", totalBuiltUpArea.toString());
        } else {
            reportParams
                    .put("totalBuiltUpArea",
                            !bpaApplication.getBuildingDetail().isEmpty()
                                    ? bpaApplication.getBuildingDetail().get(0).getTotalPlintArea().setScale(2,
                                            BigDecimal.ROUND_HALF_UP).toString() + " Sq.Mtrs, "
                                    : EMPTY);
        }
        if (bpaApplication.getIsOneDayPermitApplication())
            reportParams.put(PERMIT_ORDER_TITLE, "ONE DAY BUILDING PERMIT");
        else if (!bpaApplication.getSiteDetail().isEmpty() && bpaApplication.getSiteDetail().get(0).getIsappForRegularization())
            reportParams.put(PERMIT_ORDER_TITLE, "REGULARISATION GRANTED- ORDERS ISSUED.");
        else
            reportParams.put(PERMIT_ORDER_TITLE, "GENERAL BUILDING PERMIT");
        if (!bpaApplication.getPermitFee().isEmpty())
            reportParams.put("permitFeeDetails", getPermitFeeDetails(bpaApplication));

        reportParams.put("cityName", ApplicationThreadLocals.getCityName());
        String imageURL = ReportUtil.getImageURL(BpaConstants.STATE_LOGO_PATH);
        reportParams.put("stateLogo", imageURL);
        if (bpaApplication.getExistingBuildingDetails() != null && !bpaApplication.getExistingBuildingDetails().isEmpty()) {
            Map<String, BigDecimal> exstArea = BpaUtils.getTotalExstArea(bpaApplication.getExistingBuildingDetails());
            reportParams.put(EXST_TOTAL_BLT_UP_AREA,
                    exstArea.get(EXST_TOTAL_BLT_UP_AREA) != null ? exstArea.get(EXST_TOTAL_BLT_UP_AREA) : BigDecimal.ZERO);
            reportParams.put(EXST_TOTAL_FLOOR_AREA,
                    exstArea.get(EXST_TOTAL_FLOOR_AREA) != null ? exstArea.get(EXST_TOTAL_FLOOR_AREA) : BigDecimal.ZERO);
            reportParams.put(EXST_TOTAL_CARPET_AREA,
                    exstArea.get(EXST_TOTAL_CARPET_AREA) != null ? exstArea.get(EXST_TOTAL_CARPET_AREA) : BigDecimal.ZERO);

        }

        Map<String, BigDecimal> proposedArea = BpaUtils.getTotalProposedArea(bpaApplication.getBuildingDetail());
        reportParams.put(TOTAL_BLT_UP_AREA,
                proposedArea.get(TOTAL_BLT_UP_AREA) != null ? proposedArea.get(TOTAL_BLT_UP_AREA) : BigDecimal.ZERO);
        reportParams.put(TOTAL_FLOOR_AREA,
                proposedArea.get(TOTAL_FLOOR_AREA) != null ? proposedArea.get(TOTAL_FLOOR_AREA) : BigDecimal.ZERO);
        reportParams.put(TOTAL_CARPET_AREA,
                proposedArea.get(TOTAL_CARPET_AREA) != null ? proposedArea.get(TOTAL_CARPET_AREA) : BigDecimal.ZERO);

        List<BuildingSubUsage> buildingSubUsages = bpaApplication.getBuildingSubUsages();

        if (!buildingSubUsages.isEmpty()) {
            StringBuilder subheader = new StringBuilder();

            String mainUsage = null;
            String lastString = " and ";
            for (BuildingSubUsage buildingSubUsage : buildingSubUsages) {
                for (BuildingSubUsageDetails buildingSubUsageDetail : buildingSubUsage.getSubUsageDetails()) {
                    mainUsage = buildingSubUsageDetail.getMainUsage().getDescription();
                    StringBuilder subUsage = new StringBuilder();
                    for (Usage subOccupancy : buildingSubUsageDetail.getSubUsages()) {
                        subUsage = subUsage.append(subOccupancy.getDescription()).append(", ");
                    }
                    subheader = subheader.append("Block ").append(buildingSubUsage.getBlockNumber())
                            .append(" - ").append(subUsage.toString(), 0, subUsage.length() - 2)
                            .append(" under ").append(mainUsage).append(" occupancy ").append(lastString);
                }

            }

            String subHeaderString = subheader.toString();
            subHeaderString = subHeaderString.substring(0, subHeaderString.length() - lastString.length());

            reportParams.put("subHeaderTitle", "Building permit to construct,");
            reportParams.put("subheader", subHeaderString + "confirming to the details and conditions here under.");
        }
        return reportParams;

    }

    private List<PermitFeeHelper> getPermitFeeDetails(final BpaApplication application) {
        List<PermitFeeHelper> permitFeeDetails = new ArrayList<>();
        for (EgDemandDetails demandDetails : application.getDemand().getEgDemandDetails()) {
            if (demandDetails.getAmount().compareTo(BigDecimal.ZERO) > 0) {
                PermitFeeHelper feeHelper = new PermitFeeHelper();
                feeHelper.setFeeDescription(demandDetails.getEgDemandReason().getEgDemandReasonMaster().getReasonMaster());
                feeHelper.setAmount(demandDetails.getAmount());
                permitFeeDetails.add(feeHelper);
            }
        }
        return permitFeeDetails;
    }

    public String buildQRCodeDetails(final BpaApplication bpaApplication) {
        StringBuilder qrCodeValue = new StringBuilder();
        qrCodeValue = isBlank(ApplicationThreadLocals.getMunicipalityName()) ? qrCodeValue.append("")
                : qrCodeValue.append(ApplicationThreadLocals.getMunicipalityName()).append(ONE_NEW_LINE);
        qrCodeValue = bpaApplication.getOwner() == null || isBlank(bpaApplication.getOwner().getName())
                ? qrCodeValue.append("Applicant Name : ").append(N_A).append(ONE_NEW_LINE)
                : qrCodeValue.append("Applicant Name : ").append(bpaApplication.getOwner().getName()).append(ONE_NEW_LINE);
        qrCodeValue = isBlank(bpaApplication.getApplicationNumber())
                ? qrCodeValue.append("Application number : ").append(N_A).append(ONE_NEW_LINE)
                : qrCodeValue.append("Application number : ").append(bpaApplication.getApplicationNumber()).append(ONE_NEW_LINE);
        if (!isBlank(bpaApplication.geteDcrNumber())) {
            qrCodeValue = qrCodeValue.append("Edcr number : ").append(bpaApplication.geteDcrNumber()).append(ONE_NEW_LINE);
        }
        qrCodeValue = isBlank(bpaApplication.getPlanPermissionNumber())
                ? qrCodeValue.append("Permit number : ").append(N_A).append(ONE_NEW_LINE)
                : qrCodeValue.append("Permit number : ").append(bpaApplication.getPlanPermissionNumber()).append(ONE_NEW_LINE);
        qrCodeValue = bpaWorkFlowService.getAmountRuleByServiceType(bpaApplication) == null
                ? qrCodeValue.append("Approved by : ").append(N_A).append(ONE_NEW_LINE)
                : qrCodeValue.append("Approved by : ")
                        .append(getApproverDesignation(bpaWorkFlowService.getAmountRuleByServiceType(bpaApplication).intValue()))
                        .append(ONE_NEW_LINE);
        qrCodeValue = bpaApplication.getPlanPermissionDate() == null
                ? qrCodeValue.append("Date of issue of permit : ").append(N_A).append(ONE_NEW_LINE)
                : qrCodeValue.append("Date of issue of permit : ")
                        .append(DateUtils.getDefaultFormattedDate(bpaApplication.getPlanPermissionDate())).append(ONE_NEW_LINE);
        qrCodeValue = isBlank(getApproverName(bpaApplication))
                ? qrCodeValue.append("Name of approver : ").append(N_A).append(ONE_NEW_LINE)
                : qrCodeValue.append("Name of approver : ").append(getApproverName(bpaApplication)).append(ONE_NEW_LINE);
        return qrCodeValue.toString();
    }

    public String buildQRCodeDetailsForOc(final OccupancyCertificate oc) {
        StringBuilder qrCodeValue = new StringBuilder();
        qrCodeValue = isBlank(ApplicationThreadLocals.getMunicipalityName()) ? qrCodeValue.append("")
                : qrCodeValue.append(ApplicationThreadLocals.getMunicipalityName()).append(ONE_NEW_LINE);

        qrCodeValue = isBlank(oc.getParent().getPlanPermissionNumber())
                ? qrCodeValue.append("Permit number : ").append(N_A).append(ONE_NEW_LINE)
                : qrCodeValue.append("Permit number : ").append(oc.getParent().getPlanPermissionNumber()).append(ONE_NEW_LINE);
        qrCodeValue = oc.getParent().getPlanPermissionDate() == null
                ? qrCodeValue.append("Date of issue of permit : ").append(N_A).append(ONE_NEW_LINE)
                : qrCodeValue.append("Date of issue of permit : ")
                        .append(DateUtils.getDefaultFormattedDate(oc.getParent().getPlanPermissionDate())).append(ONE_NEW_LINE);

        qrCodeValue = isBlank(oc.getParent().getPlanPermissionNumber())
                ? qrCodeValue.append("Occupancy certificate number : ").append(N_A).append(ONE_NEW_LINE)
                : qrCodeValue.append("Occupancy certificate number : ").append(oc.getOccupancyCertificateNumber())
                        .append(ONE_NEW_LINE);
        qrCodeValue = oc.getParent().getPlanPermissionDate() == null
                ? qrCodeValue.append("Date of issue of Occupancy certificate: ").append(N_A).append(ONE_NEW_LINE)
                : qrCodeValue.append("Date of issue of Occupancy certificate: ")
                        .append(DateUtils.getDefaultFormattedDate(oc.getApprovalDate())).append(ONE_NEW_LINE);

        qrCodeValue = bpaWorkFlowService.getAmountRuleByServiceTypeForOc(oc) == null
                ? qrCodeValue.append("Approved by : ").append(N_A).append(ONE_NEW_LINE)
                : qrCodeValue.append("Approved by : ")
                        .append(getApproverDesignation(bpaWorkFlowService.getAmountRuleByServiceTypeForOc(oc).intValue()))
                        .append(ONE_NEW_LINE);

        qrCodeValue = isBlank(getOcApproverName(oc))
                ? qrCodeValue.append("Name of approver : ").append(N_A).append(ONE_NEW_LINE)
                : qrCodeValue.append("Name of approver : ").append(getOcApproverName(oc)).append(ONE_NEW_LINE);
        return qrCodeValue.toString();
    }

    private String getValidityDescription(final String serviceTypeCode, Double validity, final Date planPermissionDate) {
        StringBuilder certificateValidatiy = new StringBuilder();
        String validityExpiryDate;
        validityExpiryDate = calculateCertExpryDate(new DateTime(planPermissionDate),validity);
        certificateValidatiy.append("\n\nValidity : This certificate is valid upto ").append(validityExpiryDate).append(" Only.");
        return certificateValidatiy.toString();
    }

    private String getBuildingCommonPermitNotes() {
        StringBuilder permitNotes = new StringBuilder();
        permitNotes.append(getMessageFromPropertyFile("build.permit.note1"))
                .append(getMessageFromPropertyFile("build.permit.note2")).append(getMessageFromPropertyFile("build.permit.note3"))
                .append(getMessageFromPropertyFile("build.permit.note4"));
        return permitNotes.toString();
    }

    private String buildPermitConditions(final BpaApplication bpaApplication) {

        StringBuilder permitConditions = new StringBuilder();
        List<ApplicationPermitConditions> additionalPermitConditions = bpaApplicationPermitConditionsService
                .findAllByApplicationAndPermitConditionType(bpaApplication, PermitConditionType.ADDITIONAL_PERMITCONDITION);
        if (bpaApplication.getPlanPermissionDate() != null && bpaApplication.getServiceType().getCode().equals(ST_CODE_14)
                || bpaApplication.getServiceType().getCode().equals(ST_CODE_15)) {
            permitConditions.append(getMessageFromPropertyFile("tower.pole.permit.condition1"))
                    .append(getMessageFromPropertyFile("tower.pole.permit.condition2"))
                    .append(getMessageFromPropertyFile("tower.pole.permit.condition3"))
                    .append(getMessageFromPropertyFile("tower.pole.permit.condition4"))
                    .append(getMessageFromPropertyFile("tower.pole.permit.condition5"))
                    .append(getMessageFromPropertyFile("tower.pole.permit.condition6"))
                    .append(getMessageFromPropertyFile("tower.pole.permit.condition7"))
                    .append(getMessageFromPropertyFileWithParameters("tower.pole.permit.condition8",
                            bpaApplication.getOwner().getName()))
                    .append(getMessageFromPropertyFile("tower.pole.permit.condition9"))
                    .append(getMessageFromPropertyFileWithParameters("tower.pole.permit.condition10",
                            DateUtils.getDefaultFormattedDate(bpaApplication.getPlanPermissionDate())))
                    .append(getMessageFromPropertyFile("tower.pole.permit.condition11"));
            int order = 12;
            buildAdditionalPermitConditionsOrRejectionReason(permitConditions, additionalPermitConditions, order);
        } else {
            int order = buildApplicationPermitConditions(bpaApplication, permitConditions);
            buildAdditionalPermitConditionsOrRejectionReason(permitConditions, additionalPermitConditions, order);
        }
        return permitConditions.toString();
    }

    private int buildAdditionalPermitConditionsOrRejectionReason(StringBuilder permitConditions,
            List<ApplicationPermitConditions> additionalPermitConditions, int order) {
        int additionalOrder = order;
        if (!additionalPermitConditions.isEmpty()
                && isNotBlank(additionalPermitConditions.get(0).getAdditionalPermitCondition())) {
            for (ApplicationPermitConditions addnlPermitConditions : additionalPermitConditions) {
                if (isNotBlank(addnlPermitConditions.getAdditionalPermitCondition())) {
                    permitConditions.append(
                            String.valueOf(additionalOrder) + ") " + addnlPermitConditions.getAdditionalPermitCondition()
                                    + TWO_NEW_LINE);
                    additionalOrder++;
                }
            }
        }
        return additionalOrder;
    }

    public String buildRejectionReasons(final BpaApplication bpaApplication) {
        StringBuilder rejectReasons = new StringBuilder();
        if (!bpaApplication.getRejectionReasons().isEmpty()) {
            List<ApplicationPermitConditions> additionalPermitConditions = bpaApplicationPermitConditionsService
                    .findAllByApplicationAndPermitConditionType(bpaApplication, PermitConditionType.ADDITIONAL_PERMITCONDITION);
            int order = buildPredefinedRejectReasons(bpaApplication, rejectReasons);
            int additionalOrder = buildAdditionalPermitConditionsOrRejectionReason(rejectReasons, additionalPermitConditions,
                    order);
            StateHistory<Position> stateHistory = bpaUtils.getRejectionComments(bpaApplication.getStateHistory());
            rejectReasons.append(String.valueOf(additionalOrder) + ") "
                    + (stateHistory != null && isNotBlank(stateHistory.getComments()) ? stateHistory.getComments() : EMPTY)
                    + TWO_NEW_LINE);
        } else {
            rejectReasons.append(bpaApplication.getState().getComments() != null
                    && bpaApplication.getState().getComments().equalsIgnoreCase("Application cancelled by citizen")
                            ? getMessageFromPropertyFile(APPLICATION_REJECTION_REASON)
                            : getMessageFromPropertyFile(APPLICATION_AUTO_REJECTION_REASON));
        }
        return rejectReasons.toString();
    }

    private int buildPredefinedRejectReasons(final BpaApplication bpaApplication,
            StringBuilder permitConditions) {
        int order = 1;
        for (ApplicationPermitConditions rejectReason : bpaApplication.getRejectionReasons()) {
            if (rejectReason.isRequired()
                    && PermitConditionType.REJECTION_REASON.equals(rejectReason.getPermitConditionType())) {
                permitConditions
                        .append(String.valueOf(order) + ") " + rejectReason.getPermitCondition().getDescription() + TWO_NEW_LINE);
                order++;
            }
        }
        return order;
    }

    private int buildApplicationPermitConditions(final BpaApplication bpaApplication,
            StringBuilder permitConditions) {
        int order = 1;
        for (ApplicationPermitConditions applnPermit : bpaApplication.getDynamicPermitConditions()) {
            if (applnPermit.isRequired()
                    && PermitConditionType.DYNAMIC_PERMITCONDITION.equals(applnPermit.getPermitConditionType())) {
                permitConditions
                        .append(String.valueOf(order) + ") " + applnPermit.getPermitCondition().getDescription()
                                + applnPermit.getPermitConditionNumber() + " Dtd "
                                + DateUtils.toDefaultDateFormat(applnPermit.getPermitConditiondDate()) + "." + TWO_NEW_LINE);
                order++;
            }
        }

        for (ApplicationPermitConditions applnPermit : bpaApplication.getStaticPermitConditions()) {
            if (applnPermit.isRequired()
                    && PermitConditionType.STATIC_PERMITCONDITION.equals(applnPermit.getPermitConditionType())) {
                permitConditions
                        .append(String.valueOf(order) + ") " + applnPermit.getPermitCondition().getDescription() + TWO_NEW_LINE);
                order++;
            }
        }
        return order;
    }

    public String getMessageFromPropertyFile(String key) {
        return bpaMessageSource.getMessage(key, null, null);
    }

    private String getMessageFromPropertyFileWithParameters(String key, String value) {
        return bpaMessageSource.getMessage(key, new String[] { value }, null);
    }

    public String calculateCertExpryDate(DateTime permissionDate, Double noOfYears) {
        DateTimeFormatter fmt = DateUtils.defaultDateFormatter();
        return fmt.print(permissionDate.plusYears(noOfYears.intValue()).minusDays(1));
    }

    public String getApproverDesignation(final Integer amountRule) {
        String designation = EMPTY;
        if (amountRule >= 0 && amountRule <= 300) {
            designation = "Assistant engineer";
        } else if (amountRule > 300 && amountRule <= 750) {
            designation = "Assistant executive engineer";
        } else if (amountRule > 750 && amountRule <= 1500) {
            designation = "Executive engineer";
        } else if (amountRule > 1500 && amountRule <= 2500) {
            designation = "Corporation Engineer";
        } else if (amountRule > 2500 && amountRule <= 1000000) {
            designation = "Secretary";
        }
        return designation;
    }

    public String getApproverName(final BpaApplication application) {
        StateHistory<Position> stateHistory = application.getStateHistory().stream()
                .filter(history -> history.getOwnerPosition().getDeptDesig().getDesignation().getName().equalsIgnoreCase(
                        getApproverDesignation(bpaWorkFlowService.getAmountRuleByServiceType(application).intValue())))
                .findAny().orElse(null);
        Assignment assignment = getAssignment(stateHistory);
        return assignment == null ? N_A : assignment.getEmployee().getName();
    }

    public String getOcApproverName(final OccupancyCertificate oc) {
        StateHistory<Position> stateHistory = oc.getStateHistory().stream()
                .filter(history -> history.getOwnerPosition().getDeptDesig().getDesignation().getName().equalsIgnoreCase(
                        getApproverDesignation(bpaWorkFlowService.getAmountRuleByServiceTypeForOc(oc).intValue())))
                .findAny().orElse(null);
        Assignment assignment = getAssignment(stateHistory);
        return assignment == null ? N_A : assignment.getEmployee().getName();
    }

    private Assignment getAssignment(StateHistory<Position> stateHistory) {
        if (stateHistory == null)
            return null;
        else {
            Assignment assignment = bpaWorkFlowService.getApproverAssignmentByDate(stateHistory.getOwnerPosition(),
                    stateHistory.getLastModifiedDate());
            List<Assignment> assignments = Collections.emptyList();
            if (stateHistory.getOwnerUser() != null)
                assignments = bpaWorkFlowService.getAssignmentByPositionAndUserAsOnDate(stateHistory.getOwnerPosition().getId(),
                        stateHistory.getOwnerUser().getId(), stateHistory.getLastModifiedDate());
            else if (assignment == null && assignments.isEmpty())
                assignments = bpaWorkFlowService.getAssignmentsByPositionAndDate(stateHistory.getOwnerPosition().getId(),
                        stateHistory.getLastModifiedDate());
            if (!assignments.isEmpty())
                assignment = assignments.get(0);
            if (assignment == null)
                assignment = bpaWorkFlowService.getAssignmentsForPosition(stateHistory.getOwnerPosition().getId());
            return assignment;
        }
    }

    private void updatePermitOrderReport(FileStoreMapper fileStoreMapper, BpaApplication application) {
        try {
            Path path = bpaUtils.getExistingFilePath(fileStoreMapper, APPLICATION_MODULE_TYPE);

            PDDocument doc = PDDocument.load(new File(path.toString()));
            for (int i = 0; i < doc.getNumberOfPages(); i++) {
                PDPage page = doc.getPage(i);
                PDRectangle mediaBox = page.getMediaBox();
                PDPageContentStream contentStream = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND,
                        true);

                // adding qr code
                String pathToFile = generatePDF417Code(buildQRCodeDetails(application)).getAbsolutePath();
                PDImageXObject pdImage = PDImageXObject.createFromFile(pathToFile, doc);

                // over write the content to be replaced with a white rectangle to cover the footer from dcr report powered by
                contentStream.setNonStrokingColor(Color.white);
                contentStream.addRect((mediaBox.getWidth() / 2f) + 45, 35, mediaBox.getWidth(), 12);
                contentStream.fill();

                // over write the content to be replaced with a white rectangle for dcr report page number
                contentStream.addRect(230, 35, 50, 12);
                contentStream.fill();

                // set the color to black again as we are setting the color to white before
                contentStream.setNonStrokingColor(Color.black);

                // set the coordinates of barcode in pdf page
                float barCodeCoordinate = mediaBox.getWidth() - pdImage.getWidth() - 10;

                contentStream.drawImage(pdImage, barCodeCoordinate, 5);

                contentStream.beginText();

                /*
                 * if bar code width is large and its x coordinate exceeds half page we take bar code x coordinate minus 40
                 */
                float pageNoCoordinate = mediaBox.getWidth() / 2f > barCodeCoordinate ? barCodeCoordinate - 40
                        : mediaBox.getWidth() / 2f - 24;

                contentStream.newLineAtOffset(pageNoCoordinate, 37);

                contentStream.setFont(PDType1Font.TIMES_ROMAN, 10);
                String text = (i + 1) + " of " + doc.getNumberOfPages();
                contentStream.showText(text);
                contentStream.endText();
                contentStream.close();
            }
            doc.save(new File(path.toString()));
            doc.close();
        } catch (IOException e) {
            throw new ValidationException(Arrays.asList(new ValidationError("error", e.getMessage())));
        }
    }

    public List<Map<String, String>> getAllReviewersList(BpaApplication bpaApplication) {
        bpaApplication.getStateHistory().sort(Comparator.comparing(StateHistory::getId));
        List<Map<String, String>> reviewerNameAndDesignation = new LinkedList<>();
        for (StateHistory<Position> stateHistory : bpaApplication.getStateHistory()) {
            Assignment assignment = getAssignment(stateHistory);
            if (assignment != null && !assignment.getDesignation().getName().equals(SECTION_CLERK)
                    && !assignment.getDesignation().getName().equals("Superintendent") &&
                    !assignment.getDesignation().getName().equals(
                            getApproverDesignation(bpaWorkFlowService.getAmountRuleByServiceType(bpaApplication).intValue()))) {
                Map<String, String> reviewerNameAndDesignationMap = new HashMap<>();
                reviewerNameAndDesignationMap.put("name", assignment.getEmployee().getName());
                reviewerNameAndDesignationMap.put("designation", assignment.getDesignation().getName());
                reviewerNameAndDesignation.add(reviewerNameAndDesignationMap);
            }
        }
        return reviewerNameAndDesignation;
    }

    public List<Map<String, String>> getAllOcReviewersList(OccupancyCertificate oc) {
        oc.getStateHistory().sort(Comparator.comparing(StateHistory::getId));
        List<Map<String, String>> reviewerNameAndDesignation = new LinkedList<>();
        for (StateHistory<Position> stateHistory : oc.getStateHistory()) {
            Assignment assignment = getAssignment(stateHistory);
            if (assignment != null && !assignment.getDesignation().getName().equals(SECTION_CLERK)
                    && !assignment.getDesignation().getName().equals("Superintendent") &&
                    !assignment.getDesignation().getName().equals(
                            getApproverDesignation(bpaWorkFlowService.getAmountRuleByServiceTypeForOc(oc).intValue()))) {
                Map<String, String> reviewerNameAndDesignationMap = new HashMap<>();
                reviewerNameAndDesignationMap.put("name", assignment.getEmployee().getName());
                reviewerNameAndDesignationMap.put("designation", assignment.getDesignation().getName());
                reviewerNameAndDesignation.add(reviewerNameAndDesignationMap);
            }
        }
        return reviewerNameAndDesignation;
    }
}
