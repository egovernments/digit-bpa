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

import static ar.com.fdvs.dj.domain.constants.Stretching.RELATIVE_TO_BAND_HEIGHT;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_MODULE_TYPE;
import static org.egov.infra.security.utils.SecureCodeUtils.generatePDF417Code;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.egov.bpa.model.DocumentDetails;
import org.egov.bpa.model.InspectionImg;
import org.egov.bpa.service.common.JasperReportHelperService;
import org.egov.bpa.transaction.entity.InspectionNotice;
import org.egov.bpa.transaction.entity.PermitInspection;
import org.egov.bpa.transaction.entity.common.DocketDetailCommon;
import org.egov.bpa.transaction.entity.common.InspectionCommon;
import org.egov.bpa.transaction.entity.common.PlanScrutinyChecklistCommon;
import org.egov.bpa.transaction.notice.InspectionReportFormat;
import org.egov.bpa.transaction.service.InspectionNoticeService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.infra.admin.master.service.CityService;
import org.egov.infra.config.core.ApplicationThreadLocals;
import org.egov.infra.filestore.entity.FileStoreMapper;
import org.egov.infra.filestore.service.FileStoreService;
import org.egov.infra.reporting.engine.ReportOutput;
import org.egov.infra.reporting.util.ReportUtil;
import org.egov.infra.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DJDataSource;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.ImageScaleMode;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.entities.Subreport;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/*
 * This is default implementation of all. This can be overridden by state, district, ulb and grade level.
 * When override following name convention to be follow,
 * i) If using same code base for multiple state's then for state level InspectionReportFormatImpl_<State Name>
 * ii)For state, district level can be override by InspectionReportFormatImpl_<District Name>
 * similarly for others can override.
 * If follow above standards, automatically will be pick concern implementation.
 */
@Service
public class InspectionReportFormatImpl implements InspectionReportFormat {

    private static final Logger LOGGER = LoggerFactory.getLogger(InspectionReportFormatImpl.class);

    @Autowired
    private CityService cityService;
    @Autowired
    private JasperReportHelperService jasperReportHelperService;
    @Autowired
    private FileStoreService fileStoreService;
    @Autowired
    private InspectionNoticeService inspectionNoticeService;

    @Override
    public ReportOutput generateNotice(PermitInspection permitInspn) {

        ReportOutput reportOutput = new ReportOutput();
        InspectionNotice notice;
        if (permitInspn != null) {
            InspectionCommon inspection = permitInspn.getInspection();

            notice = inspectionNoticeService.findByRefNumberAndInspectionNumber(
                    permitInspn.getApplication().getApplicationNumber(), inspection.getInspectionNumber());
            if (notice != null) {
                final FileStoreMapper fmp = notice.getNoticeFileStore();
                Path path = fileStoreService.fetchAsPath(fmp.getFileStoreId(),
                        APPLICATION_MODULE_TYPE);
                try {
                    reportOutput.setReportOutputData(Files.readAllBytes(path));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {

                InputStream reportOutputStream = buildReportParameters(permitInspn);

                final String fileName = permitInspn.getApplication().getApplicationNumber() + "_"
                        + inspection.getInspectionNumber() + ".pdf";

                final FileStoreMapper fileStoreMapper = fileStoreService.store(reportOutputStream, fileName,
                        "application/pdf", BpaConstants.FILESTORE_MODULECODE);

                notice = new InspectionNotice();
                notice.setApplicationType(BpaConstants.PERMIT_APPLICATION_NOTICE_TYPE);
                notice.setInspectionNumber(inspection.getInspectionNumber());
                notice.setRefNumber(permitInspn.getApplication().getApplicationNumber());
                notice.setNoticeFileStore(fileStoreMapper);
                notice.setNoticeGeneratedDate(new Date());
                inspectionNoticeService.create(notice);
                Path path = fileStoreService.fetchAsPath(fileStoreMapper.getFileStoreId(), APPLICATION_MODULE_TYPE);
                try {
                    reportOutput.setReportOutputData(Files.readAllBytes(path));
                } catch (IOException ioe) {
                    LOGGER.error("Error occurred while generate notice", ioe);
                }
            }
        }
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(MediaType.APPLICATION_PDF_VALUE));
        headers.add("content-disposition", "inline;filename=inspectionreport.pdf");
        return reportOutput;
    }

    public InputStream buildReportParameters(final PermitInspection inspection) {
        final JRDataSource ds = new JRBeanCollectionDataSource(Collections.singletonList(inspection));
        final Map<String, Object> reportParams = new HashMap<>();
        FastReportBuilder drb = new FastReportBuilder();
        List<DocumentDetails> ddList = new ArrayList<>();
        Map<String, List<InspectionImg>> imageMap = new HashMap<>();
        reportParams.put("stateLogo", ReportUtil.getImageURL(BpaConstants.STATE_LOGO_PATH));
        reportParams.put("logoPath", cityService.getCityLogoAsStream());
        reportParams.put("cityName", ApplicationThreadLocals.getCityName());
        reportParams.put("ulbName", ApplicationThreadLocals.getMunicipalityName());
        reportParams.put("applicantName", inspection.getApplication().getApplicantName());
        reportParams.put("address", inspection.getApplication().getOwner().getAddress());
        reportParams.put("plotDetails",
                inspection.getApplication().getSiteDetail().get(0).getExtentinsqmts().setScale(2, BigDecimal.ROUND_HALF_EVEN));
        reportParams.put("inspectedBy", inspection.getInspection().getInspectedBy().getName());
        reportParams.put("inspectedDate", DateUtils.getDefaultFormattedDate(inspection.getInspection().getInspectionDate()));
        drb.addConcatenatedReport(getSubreport("inspectedRemarks"));
        reportParams.put("occupancyType", inspection.getApplication().getOccupanciesName());
        reportParams.put("scrutinyNumber",
                inspection.getApplication().geteDcrNumber() != null
                        && !inspection.getApplication().geteDcrNumber().isEmpty()
                                ? inspection.getApplication().geteDcrNumber()
                                : "N/A");
        reportParams.put("applicationType", inspection.getApplication().getIsOneDayPermitApplication()
                ? BpaConstants.APPLICATION_TYPE_ONEDAYPERMIT
                : BpaConstants.APPLICATION_TYPE_REGULAR);
        reportParams.put("amenityType",
                inspection.getApplication().getAmenityName() != null
                        && !inspection.getApplication().getAmenityName().isEmpty()
                                ? inspection.getApplication().getAmenityName()
                                : "N/A");
        reportParams.put("licenseeType", inspection.getApplication().getStakeHolder().get(0).getStakeHolder()
                .getStakeHolderType().getName());
        reportParams.put("licenseeName",
                inspection.getApplication().getStakeHolder().get(0).getStakeHolder().getName());
        reportParams.put("applicationNo", inspection.getApplication().getApplicationNumber());
        reportParams.put("applicationDate",
                DateUtils.getDefaultFormattedDate(inspection.getApplication().getApplicationDate()));
        reportParams.put("inspectionNumber", inspection.getInspection().getInspectionNumber());
        reportParams.put("rptHeader", "Inspection Report");
        drb.setPageSizeAndOrientation(new Page(842, 595, true));
        
        String remarks=inspection.getInspection().getInspectionRemarks() == null
                || inspection.getInspection().getInspectionRemarks().isEmpty() ? "NA"
                        : inspection.getInspection().getInspectionRemarks();
        inspection.getInspection().setInspectionRemarks(remarks);
        List <InspectionCommon> listRemarks=new ArrayList<>();
        listRemarks.add(inspection.getInspection());
        drb.addConcatenatedReport(getSubreportForRemarks("inspectionRemarks"));
        reportParams.put("inspectionRemarks",listRemarks);

        ddList = getDocumentDetails(inspection, BpaConstants.INSPECTIONLOCATION);
        drb.addConcatenatedReport(getSubreport("Location of the Plot"));
        reportParams.put("Location of the Plot", ddList);

        ddList = getDocumentDetails(inspection, BpaConstants.INSPECTIONMEASUREMENT);
        drb.addConcatenatedReport(getSubreport("Measurement of the Plot"));
        reportParams.put("Measurement of the Plot", ddList);

        ddList = getDocumentDetails(inspection, BpaConstants.INSPECTIONACCESS);
        drb.addConcatenatedReport(getSubreport("Access To Plot"));
        reportParams.put("Access To Plot", ddList);

        ddList = getDocumentDetails(inspection, BpaConstants.INSPECTIONSURROUNDING);
        drb.addConcatenatedReport(getSubreport("Required details surrounding the plot"));
        reportParams.put("Required details surrounding the plot", ddList);

        ddList = getDocumentDetails(inspection, BpaConstants.INSPECTIONTYPEOFLAND);
        drb.addConcatenatedReport(getSubreport("Type of land"));
        reportParams.put("Type of land", ddList);

        ddList = getDocumentDetails(inspection, BpaConstants.INSPECTIONPROPOSEDSTAGEWORK);
        drb.addConcatenatedReport(getSubreport("Stage of proposed work"));
        reportParams.put("Stage of proposed work", ddList);

        ddList = getDocumentDetails(inspection, BpaConstants.INSPECTIONWORKCOMPLETEDPERPLAN);
        drb.addConcatenatedReport(getSubreport("If work Started/completed"));
        reportParams.put("If work Started/completed", ddList);

        ddList = getDocumentDetails(inspection, BpaConstants.INSPECTIONHGTBUILDABUTROAD);
        drb.addConcatenatedReport(getSubreport("General Provisions regarding Site & Building Requirements"));
        reportParams.put("General Provisions regarding Site & Building Requirements", ddList);

        ddList = getDocumentDetails(inspection, BpaConstants.INSPECTIONAREALOC);
        drb.addConcatenatedReport(getSubreport("Extent of Plot"));
        reportParams.put("Extent of Plot", ddList);

        ddList = getDocumentDetails(inspection, BpaConstants.INSPECTIONLENGTHOFCOMPOUNDWALL);
        drb.addConcatenatedReport(getSubreport("Length of the Compound Wall"));
        reportParams.put("Length of the Compound Wall", ddList);

        ddList = getDocumentDetails(inspection, BpaConstants.INSPECTIONNUMBEROFWELLS);
        drb.addConcatenatedReport(getSubreport("Number of Wells"));
        reportParams.put("Number of Wells", ddList);

        ddList = getDocumentDetails(inspection, BpaConstants.INSPECTIONSHUTTER);
        drb.addConcatenatedReport(getSubreport("Shutter"));
        reportParams.put("Shutter", ddList);

        ddList = getDocumentDetails(inspection, BpaConstants.INSPECTIONROOFCONVERSION);
        drb.addConcatenatedReport(getSubreport("Roof Conversion"));
        reportParams.put("Roof Conversion", ddList);

        ddList = getDocumentDetails(inspection, "Rule Validations");
        drb.addConcatenatedReport(getSubreport("Building Plan Scrutiny Details For Rule Validations"));
        reportParams.put("Building Plan Scrutiny Details For Rule Validations", ddList);

        ddList = getDocumentDetails(inspection, "Drawing Details");
        drb.addConcatenatedReport(getSubreport("Building Plan Scrutiny Details For Drawing Details"));
        reportParams.put("Building Plan Scrutiny Details For Drawing Details", ddList);

        imageMap = getImages(inspection);
        for(String m:imageMap.keySet()){  
           drb.addConcatenatedReport(getSubreportForImgs(m,imageMap.get(m).size()));
           reportParams.put(m, imageMap.get(m));
        } 
        reportParams.put("qrCode", generatePDF417Code(buildQRCodeDetails(inspection)));

        drb.setTemplateFile("/reports/templates/inspectionreport.jrxml");
        // drb.setMargins(5, 0, 33, 20);
        final DynamicReport dr = drb.build();
        InputStream exportPdf = null;
        try {
            JasperPrint generateJasperPrint = DynamicJasperHelper.generateJasperPrint(dr, new ClassicLayoutManager(),
                    ds, reportParams);
            exportPdf = jasperReportHelperService.exportPdf(generateJasperPrint);
        } catch (JRException jre) {
            LOGGER.error("Error occurred while generating inspection report", jre);
        }
        return exportPdf;
    }
    
    private Subreport getSubreportForRemarks(String title) {
		try {

			FastReportBuilder frb = new FastReportBuilder();

			AbstractColumn inspectionRm = ColumnBuilder.getNew()
					.setColumnProperty("inspectionRemarks", String.class.getName())
					.setWidth(542).setStyle(jasperReportHelperService.getBldgDetlsHeaderStyle()).build();

			frb.addColumn(inspectionRm);
			frb.setMargins(0, 0, 0, 0);
			frb.setUseFullPageWidth(false);
			frb.setTopMargin(5);
			frb.setAllowDetailSplit(false);
			frb.setPageSizeAndOrientation(Page.Page_A4_Portrait());
			DynamicReport build = frb.build();
			Subreport sub = new Subreport();
			sub.setDynamicReport(build);
			Style style = new Style();
			style.setStretchWithOverflow(true);
			style.setStreching(RELATIVE_TO_BAND_HEIGHT);
			sub.setStyle(style);
			sub.setDatasource(new DJDataSource(title, DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, 0));
			sub.setLayoutManager(new ClassicLayoutManager());
			return sub;
		} catch (ColumnBuilderException cbe) {
			LOGGER.error("Error occurred while getting subreport", cbe);
		}
		return null;
	}

    private List<DocumentDetails> getDocumentDetails(PermitInspection permitInspn, String type) {

        List<DocumentDetails> docList = new ArrayList<>();
        InspectionCommon inspection = permitInspn.getInspection();
        if ("Rule Validations".equals(type)) {
            for (PlanScrutinyChecklistCommon cl : inspection.getPlanScrutinyChecklistForRule()) {
                docList.add(getDocumentDetail(cl));
            }
        } else if ("Drawing Details".equals(type)) {
            for (PlanScrutinyChecklistCommon cl : inspection.getPlanScrutinyChecklistForDrawing()) {
                docList.add(getDocumentDetail(cl));
            }
        } else {
            for (final DocketDetailCommon docketDet : inspection.getDocket().get(0).getDocketDetail()) {
                if (type.equalsIgnoreCase(docketDet.getServiceChecklist().getChecklist().getChecklistType().getCode()))
                    docList.add(getDocumentDetail(docketDet));
            }
        }

        return docList;
    }

    private DocumentDetails getDocumentDetail(DocketDetailCommon docketDet) {
        DocumentDetails dd = new DocumentDetails();
        dd.setDocumentType(docketDet.getServiceChecklist().getChecklist().getDescription());
        dd.setProvided(docketDet.getValue().getCheckListVal());
        dd.setRemarks(
                docketDet.getRemarks() == null || docketDet.getRemarks().isEmpty() ? "NA" : docketDet.getRemarks());
        dd.setCheckListType(docketDet.getServiceChecklist().getChecklist().getChecklistType().getDescription());
        return dd;
    }

    private DocumentDetails getDocumentDetail(PlanScrutinyChecklistCommon cl) {
        DocumentDetails dd = new DocumentDetails();
        dd.setDocumentType(cl.getServiceChecklist().getChecklist().getDescription());
        dd.setProvided(cl.getScrutinyValue().getCheckListVal());
        dd.setRemarks(cl.getRemarks() == null || cl.getRemarks().isEmpty() ? "NA" : cl.getRemarks());
        dd.setCheckListType(cl.getServiceChecklist().getChecklist().getChecklistType().getDescription());
        return dd;
    }

    private Map<String, List<InspectionImg>> getImages(PermitInspection permitInspn) {
		List<InspectionImg> imgsList = new ArrayList<>();
		Map<String, List<InspectionImg>> imageMap = new HashMap<>();
		if (!permitInspn.getInspection().getInspectionSupportDocs().isEmpty())
			permitInspn.getInspection().getInspectionSupportDocs().forEach(docketFile -> {
				if (docketFile != null) {
					docketFile.getImages().forEach(imageFilestore -> {
						final File file = fileStoreService.fetch(imageFilestore.getFileStoreId(),
								BpaConstants.FILESTORE_MODULECODE);
						if (file != null) {
							imgsList.add(getInspectionImg(docketFile.getImages(),
									docketFile.getServiceChecklist().getChecklist().getDescription()));
						}
					});
				}

			});
		for (InspectionImg inspImage : imgsList) {
			String desc = inspImage.getDescription();
			if (imageMap.containsKey(desc)) {
				imageMap.get(desc).add(inspImage);
			} else {
				List<InspectionImg> inspImageList = new ArrayList();
				inspImageList.add(inspImage);
				imageMap.put(desc, inspImageList);
			}
		}
		return imageMap;
	}

    private InspectionImg getInspectionImg(Set<FileStoreMapper> images, String checklistDesc) {
        InspectionImg img = new InspectionImg();
        try {
            for (FileStoreMapper fileMapper : images) {
                // FIXME FileInputSteam needs to be closed after report is generated to avoid resource leak
                img.setImg(
                        new FileInputStream(fileStoreService.fetch(fileMapper.getFileStoreId(), APPLICATION_MODULE_TYPE)));
            }
        } catch (IOException ioe) {
            LOGGER.error("Error occurred while getting inspection Image", ioe);
        }
        img.setDescription(checklistDesc);

        return img;
    }

    private Subreport getSubreport(String title) {
        try {

            FastReportBuilder frb = new FastReportBuilder();

            AbstractColumn builtUpArea = ColumnBuilder.getNew()
                    .setColumnProperty("documentType", String.class.getName()).setTitle("Document Type").setWidth(250)
                    .setStyle(jasperReportHelperService.getBldgDetlsHeaderStyle()).build();

            AbstractColumn floorArea = ColumnBuilder.getNew().setColumnProperty("provided", String.class.getName())
                    .setTitle("Provided").setWidth(70).setStyle(jasperReportHelperService.getBldgDetlsHeaderStyle())
                    .build();

            AbstractColumn carpetArea = ColumnBuilder.getNew().setColumnProperty("remarks", String.class.getName())
                    .setTitle("Remarks").setWidth(222).setStyle(jasperReportHelperService.getBldgDetlsHeaderStyle())
                    .build();

            frb.addColumn(builtUpArea);
            frb.addColumn(floorArea);
            frb.addColumn(carpetArea);
            frb.setMargins(0, 0, 0, 0);
            frb.setUseFullPageWidth(false);
            frb.setTitle(title);
            frb.setTitleStyle(jasperReportHelperService.getTitleStyle());
            frb.setHeaderHeight(5);
            frb.setTopMargin(5);
            frb.setDefaultStyles(jasperReportHelperService.getTitleStyle(),
                    jasperReportHelperService.getSubTitleStyle(), jasperReportHelperService.getColumnHeaderStyle(),
                    jasperReportHelperService.getDetailStyle());
            frb.setAllowDetailSplit(false);
            frb.setPageSizeAndOrientation(Page.Page_A4_Portrait());
            DynamicReport build = frb.build();
            Subreport sub = new Subreport();
            sub.setDynamicReport(build);
            Style style = new Style();
            style.setStretchWithOverflow(true);
            style.setStreching(RELATIVE_TO_BAND_HEIGHT);
            sub.setStyle(style);
            sub.setDatasource(new DJDataSource(title, DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, 0));
            sub.setLayoutManager(new ClassicLayoutManager());
            return sub;
        } catch (ColumnBuilderException cbe) {
            LOGGER.error("Error occurred while getting subreport", cbe);
        }
        return null;
    }

    private Subreport getSubreportForImgs(String title,int i) {
    	 try {
    	        FastReportBuilder frb = new FastReportBuilder();
    	           try {
    	            	
    	                frb.addColumn("Description ", "description", String.class.getName(),60,true);
    	                frb.addImageColumn("Photograph", "img", 482, false, ImageScaleMode.FILL_PROPORTIONALLY);
    	                
    	            } catch (ClassNotFoundException cnfe) {
                LOGGER.error("Error occurred while getting subreport image", cnfe);
            }
            frb.setDetailHeight(250);
            frb.setMargins(0, 0, 0, 0);
            frb.setUseFullPageWidth(false);
            frb.setTitle("Inspection Site Images");
            frb.setTitleStyle(jasperReportHelperService.getTitleStyle());
            frb.setHeaderHeight(5);
            frb.setTopMargin(5);
            frb.setDefaultStyles(jasperReportHelperService.getTitleStyle(),
                    jasperReportHelperService.getSubTitleStyle(), jasperReportHelperService.getColumnHeaderStyle(),
                    jasperReportHelperService.getDetailStyle());
            frb.setAllowDetailSplit(false);
            frb.setPageSizeAndOrientation(Page.Page_A4_Portrait());
            DynamicReport build = frb.build();
            Subreport sub = new Subreport();
            sub.setDynamicReport(build);
            Style style = new Style();
            style.setStretchWithOverflow(true);
            style.setStreching(RELATIVE_TO_BAND_HEIGHT);
            sub.setStyle(style);
            sub.setDatasource(new DJDataSource(title, DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, 0));
            sub.setLayoutManager(new ClassicLayoutManager());
            return sub;
        } catch (ColumnBuilderException cbe) {
            LOGGER.error("Error occurred while getting subreport image", cbe);
        }
        return null;
    }

    private String buildQRCodeDetails(final PermitInspection permitInspn) {
        StringBuilder qrCodeValue = new StringBuilder();
        InspectionCommon inspection = permitInspn.getInspection();
        qrCodeValue = !org.apache.commons.lang.StringUtils.isEmpty(permitInspn.getApplication().getApplicationNumber())
                ? qrCodeValue.append("Application Number : ").append(permitInspn.getApplication().getApplicationNumber())
                        .append("\n")
                : qrCodeValue.append("Application Number : ").append("N/A").append("\n");
        qrCodeValue = permitInspn.getApplication().getApplicationDate() != null
                ? qrCodeValue.append("Application Date : ")
                        .append(DateUtils.getDefaultFormattedDate(permitInspn.getApplication().getApplicationDate()))
                        .append("\n")
                : qrCodeValue.append("Application Date : ").append("N/A").append("\n");
        qrCodeValue = permitInspn.getApplication().getOwner() != null ? qrCodeValue.append("Applicant Name : ")
                .append(permitInspn.getApplication().getOwner().getName()).append("\n")
                : qrCodeValue.append("Applicant Name : ").append("N/A").append("\n");
        qrCodeValue = !org.apache.commons.lang.StringUtils.isEmpty(inspection.getInspectionNumber())
                ? qrCodeValue.append("Inspection Number : ").append(inspection.getInspectionNumber()).append("\n")
                : qrCodeValue.append("Inspection Number : ").append("N/A").append("\n");
        qrCodeValue = inspection.getInspectionDate() != null
                ? qrCodeValue.append("Inspection Date : ")
                        .append(DateUtils.getDefaultFormattedDate(inspection.getInspectionDate())).append("\n")
                : qrCodeValue.append("Inspection Date : ").append("N/A").append("\n");
        qrCodeValue = inspection.getInspectedBy() != null
                ? qrCodeValue.append("Inspected By : ").append(inspection.getInspectedBy().getName()).append("\n")
                : qrCodeValue.append("Inspected By : ").append("N/A").append("\n");

        return qrCodeValue.toString();
    }
}
