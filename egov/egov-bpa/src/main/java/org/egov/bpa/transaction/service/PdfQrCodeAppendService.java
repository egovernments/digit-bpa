package org.egov.bpa.transaction.service;

import static org.egov.bpa.utils.BpaConstants.APPLICATION_MODULE_TYPE;
import static org.egov.bpa.utils.BpaConstants.ST_CODE_14;
import static org.egov.bpa.utils.BpaConstants.ST_CODE_15;
import static org.egov.infra.security.utils.SecureCodeUtils.generatePDF417Code;
import static org.egov.infra.utils.ImageUtils.PNG_EXTN;
import static org.slf4j.LoggerFactory.getLogger;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.oc.OccupancyCertificate;
import org.egov.bpa.transaction.notice.util.BpaNoticeUtil;
import org.egov.bpa.transaction.workflow.BpaWorkFlowService;
import org.egov.bpa.utils.BpaUtils;
import org.egov.infra.admin.master.service.CityService;
import org.egov.infra.config.core.ApplicationThreadLocals;
import org.egov.infra.filestore.entity.FileStoreMapper;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImage;
import com.itextpdf.text.pdf.PdfIndirectObject;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

@Service
@Transactional(readOnly = true)
public class PdfQrCodeAppendService {
	private static final Logger LOG = getLogger(BpaUtils.class);

	@Autowired
	private BpaNoticeUtil bpaNoticeUtil;

	@Autowired
	private BpaUtils bpautils;

	@Autowired
	private BpaWorkFlowService bpaWorkFlowService;

	@Autowired
	private CityService cityService;
    @Deprecated
	public void appendQrCodeToPdf(FileStoreMapper fileMapper, BpaApplication application) {
		try {
			Document document = new Document();
			File file = new File(application.getApplicationNumber() + ".pdf");
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
			Rectangle pageSize = new Rectangle(71, 52);
			document.setPageSize(pageSize);
			document.open();
			prepareStamp(application, document, writer, document.getPageSize().getWidth());
			document.close();
			Path pathToStampImage = convertPdfToImage(file);
			Path path = bpautils.getExistingFilePath(fileMapper, APPLICATION_MODULE_TYPE);
			PDDocument document1 = PDDocument.load(new File(path.toString()));
			PDPage page = document1.getPage(document1.getNumberOfPages() - 1);
			PDRectangle mediaBox = page.getMediaBox();
			PDPageContentStream contentStrm = new PDPageContentStream(document1, page,
					PDPageContentStream.AppendMode.APPEND, true, true);
			putImageOnPdf(document1, page, mediaBox, contentStrm, pathToStampImage.toString(), 5, 85);
			String pathOfQrCode = generatePDF417Code(bpaNoticeUtil.buildQRCodeDetails(application))
					.getAbsolutePath();
			putImageOnPdf(document1, page, mediaBox, contentStrm, pathOfQrCode, 30, 23);
			File file2 = new File(application.getApplicationNumber() + ".pdf");
			PdfWriter writer2 = PdfWriter.getInstance(document, new FileOutputStream(file2));
			Rectangle pagesize = new Rectangle(71, 5.5f);
			document.setPageSize(pagesize);
			document.open();
			prepareSeal(writer2);
			document.close();
			Path pathToSealImage = convertPdfToImage(file2);
			putImageOnPdf(document1, page, mediaBox, contentStrm, pathToSealImage.toString(), 5, 0);
			contentStrm.close();
			document1.save(new File(path.toString()));
			document1.close();
		} catch (Exception exception) {
			LOG.info("exception encountered while stamping pdfs " + exception);
		}
	}

	private Path convertPdfToImage(File file) throws IOException {
		PDDocument doc = PDDocument.load(file);
		PDFRenderer pdfRenderer = new PDFRenderer(doc);
		BufferedImage bim = pdfRenderer.renderImageWithDPI(0, 300, ImageType.RGB);
		Path path = Files.createTempFile(RandomStringUtils.randomAlphabetic(5), PNG_EXTN);
		ImageIOUtil.writeImage(bim, path.toString(), 300);
		doc.close();
		return path;
	}

	private void putImageOnPdf(PDDocument document1, PDPage page, PDRectangle mediaBox, PDPageContentStream contentStrm,
			String path, int x, int y) throws Exception {
		PDImageXObject pdImage;
		if (page.getRotation() == 0) {
			pdImage = PDImageXObject.createFromFile(path, document1);
			contentStrm.drawImage(pdImage, mediaBox.getWidth() - (pdImage.getWidth() + x), y, pdImage.getWidth(),
					pdImage.getHeight());
		}
		if (page.getRotation() == 90) {
			pdImage = rotateImage(path, document1, 3 * Math.PI / 2);
			contentStrm.drawImage(pdImage, mediaBox.getWidth() - (pdImage.getWidth() + y),
					mediaBox.getHeight() - (pdImage.getHeight() + x), pdImage.getWidth(), pdImage.getHeight());
		}
		if (page.getRotation() == 180) {
			pdImage = rotateImage(path, document1, Math.PI / 2);
			contentStrm.drawImage(pdImage, mediaBox.getWidth() - (pdImage.getWidth() + x), y, pdImage.getWidth(),
					pdImage.getHeight());
		}
		if (page.getRotation() == 270) {
			pdImage = rotateImage(path, document1, Math.PI / 2);
			contentStrm.drawImage(pdImage, y, x, pdImage.getWidth(), pdImage.getHeight());
		}
	}

	private void prepareSeal(PdfWriter writer2) throws IOException, DocumentException {
		String authentication = "This is a computer generated authentication".toUpperCase();
		Font font = new Font(BaseFont.createFont("/fonts/ROCC____.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 3f,
				Font.NORMAL, BaseColor.BLACK);
		PdfContentByte canvas = writer2.getDirectContent();
		Phrase phrase = new Phrase(authentication, font);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase, 9, 3, 0);
		String seal = "and does not require any seal or signature in original".toUpperCase();
		Phrase phrase1 = new Phrase(seal, font);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase1, 3, 0.5f, 0);
	}

	private void prepareStamp(BpaApplication application, Document document, PdfWriter writer, Float pageWidth)
			throws IOException, DocumentException {
		Image img = Image.getInstance(cityService.getCityLogoURL());
		img.scaleAbsolute(14, 9);
		img.setAbsolutePosition(29f, 42.5f);
		document.add(img);
		String corporationName = "KOZHIKODE MUNICIPAL CORPORATION";
		Font font = new Font(BaseFont.createFont("/fonts/ROCC____.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 4.5f,
				Font.BOLD, BaseColor.BLACK);
		PdfContentByte canvas = writer.getDirectContent();
		Phrase phrase = new Phrase(corporationName, font);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase, 9, 38.6f, 0);
		font.setStyle(Font.NORMAL);
		font.setSize(4.0f);
		String permitDocument = "BUILDING PERMIT DOCUMENT";
		Phrase phrase2 = new Phrase(permitDocument, font);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase2, 17, 35.4f, 0);
		font.setSize(3f);
		String permitNumber = "PERMIT NUMBER : " + application.getPlanPermissionNumber();
		Phrase phrase3 = new Phrase(permitNumber, font);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase3, 18, 32.5f, 0);
		String validityExpiryDate = application.getServiceType().getCode().equals(ST_CODE_14)
				|| application.getServiceType().getCode().equals(ST_CODE_15)
						? bpaNoticeUtil.calculateCertExpryDate(new DateTime(application.getPlanPermissionDate()),
								bpaNoticeUtil.getMessageFromPropertyFile("tower.pole.certificate.expiry"))
						: bpaNoticeUtil.calculateCertExpryDate(new DateTime(application.getPlanPermissionDate()),
								bpaNoticeUtil.getMessageFromPropertyFile("common.services.certificate.expiry"));
		String pattern = "dd-MM-yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(application.getPlanPermissionDate());
		String permitValidThrough = "PERMIT VALID THROUGH : " + date + " TO " + validityExpiryDate.replace("/", "-");
		Phrase phrase4 = new Phrase(permitValidThrough, font);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase4, 10, 30, 0);

		String applicationNumber = "APPLICATION NUMBER : " + application.getApplicationNumber();
		Phrase phrase5 = new Phrase(applicationNumber, font);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase5, 17, 27.5f, 0);

		Font font2 = new Font(Font.FontFamily.TIMES_ROMAN, 3f, Font.NORMAL, BaseColor.BLACK);

		String approvedBy = "APPROVED BY :";
		Phrase phrase6 = new Phrase(approvedBy, font2);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase6, 24, 24.5f, 0);

		String approverName = bpaNoticeUtil.getApproverName(application).toUpperCase() + "("
							  + bpaNoticeUtil
						.getApproverDesignation(bpaWorkFlowService.getAmountRuleByServiceType(application).intValue())
						.toUpperCase()
							  + ")";
		Phrase phrase7 = new Phrase(approverName, font);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase7,
				((pageWidth / 2f) - ((approverName.length() / 2f) * 1.25f)), 21.5f, 0);
		String reviewedBy = "REVIEWED BY :";
		Phrase phrase8 = new Phrase(reviewedBy, font2);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase8, 24, 18.5f, 0);
		List<Map<String, String>> reviewersNameAndDesignationMapList = bpaNoticeUtil
				.getAllReviewersList(application);
		LinkedHashSet<String> reviewersList = new LinkedHashSet<>();
		for (Map<String, String> nameAndDesignation : reviewersNameAndDesignationMapList) {
			reviewersList.add(nameAndDesignation.get("name").toUpperCase() + " " + "("
					+ nameAndDesignation.get("designation").toUpperCase() + ")");
		}

		Float y = 18f;
		y = y - 2.5f;
		for (String reviewer : reviewersList) {
			Phrase phrase9 = new Phrase(reviewer, font);
			ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase9,
					((pageWidth / 2f) - ((reviewer.length() / 2f) * 1.25f)), y, 0);
			y = y - 2.5f;
		}
	}

	private static PDImageXObject rotateImage(String pathToFile, PDDocument doc, double angle) throws Exception {
		BufferedImage source = ImageIO.read(new File(pathToFile));
		BufferedImage output = new BufferedImage(source.getHeight(), source.getWidth(), source.getType());
		AffineTransformOp op = new AffineTransformOp(rotateClockwise(source, angle), AffineTransformOp.TYPE_BILINEAR);
		op.filter(source, output);
		ImageIO.write(output, "png", new File(pathToFile));
		return PDImageXObject.createFromFile(pathToFile, doc);
	}

	private static AffineTransform rotateClockwise(BufferedImage source, Double angle) {
		AffineTransform transform = new AffineTransform();
		transform.rotate(angle, source.getWidth() / 2, source.getHeight() / 2);
		double offset = (source.getWidth() - source.getHeight()) / 2;
		transform.translate(offset, offset);
		return transform;
	}

	public void addStamp(FileStoreMapper fileMapper, BpaApplication application) {
		try {
			Path path = bpautils.getExistingFilePath(fileMapper, APPLICATION_MODULE_TYPE);
			
			if(path.toString().contains("e028ea4e-9bfc-4681-9684-3fb8e2b6eec7"))
			{
				LOG.info("Test");
				
			}
			PdfReader pdfReader = new PdfReader(path.toString());
			String newpath=path.toString()+"Modified";
			PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(newpath));

			int pageNum = pdfReader.getNumberOfPages();
			//PdfDictionary pageN = pdfReader.getPageN(pageNum);
			Rectangle pageSize = pdfReader.getPageSize(pageNum);
			int pageRotation = pdfReader.getPageRotation(pageNum);
			float height = pageSize.getHeight();
			float width = pageSize.getWidth();
			String location = getLocation(pageRotation, width, height, pageSize, 280, 280);
			String[] split = location.split("--");
		
			float height1 = height / PageSize.A4.getHeight();
			float width1 = width / PageSize.A4.getWidth();
		
		
			float x = 0;
			float y = 0;
			float x1 = 0;
			float y1 = 0;
			if (split != null && split.length > 1) {
				x = Float.valueOf(split[0]);
				y = Float.valueOf(split[1]);
			}
			/*  This is not used as of now 
			 * if (height > width) {
				x1 = x * width;
				y1 = y * height;
			} else {
				x1 = x * height;
				y1 = y * width;
			}*/
			
			
			PdfContentByte canvas = pdfStamper.getOverContent(pageNum);
		//	City city = cityService.getCityByCode(ApplicationThreadLocals.getCityCode());
			String corporationName = ApplicationThreadLocals.getMunicipalityName(); 

			Font font = new Font(BaseFont.createFont("/fonts/ROCC____.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 14,
					Font.BOLD, BaseColor.BLACK);
			
			Font font1 = new Font(Font.FontFamily.TIMES_ROMAN, 14f, Font.BOLD, BaseColor.BLACK);
			
			Font font2 = new Font(Font.FontFamily.COURIER, 9f, Font.NORMAL, BaseColor.BLACK);

			Phrase phrase = new Phrase(corporationName, font1);
			x1=x + (140 - (corporationName.length()/2) * 0.8f );
			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, phrase, x1, y, 0);
			font1.setStyle(Font.BOLD);
			font1.setSize(12);
			String permitDocument = "Building Permit Document";
			x1=x+(140 -(permitDocument.length()/2));
			Phrase phrase2 = new Phrase(permitDocument, font1);
			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, phrase2, x1, y - 12, 0);
			
			font1.setSize(10);
			font1.setStyle(Font.NORMAL);
			String permitNumber = "Permit Number : "+application.getPlanPermissionNumber();
			x1=x+(140 -(permitNumber.length()/2));
			Phrase phrase3 = new Phrase(permitNumber, font1);
			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, phrase3, x1, y - 30, 0);

			String validityExpiryDate = application.getServiceType().getCode().equals(ST_CODE_14)
					|| application.getServiceType().getCode().equals(ST_CODE_15)
							? bpaNoticeUtil.calculateCertExpryDate(new DateTime(application.getPlanPermissionDate()),
									bpaNoticeUtil.getMessageFromPropertyFile("tower.pole.certificate.expiry"))
							: bpaNoticeUtil.calculateCertExpryDate(new DateTime(application.getPlanPermissionDate()),
									bpaNoticeUtil.getMessageFromPropertyFile("common.services.certificate.expiry"));
			String pattern = "dd-MM-yyyy";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			String date = simpleDateFormat.format(application.getPlanPermissionDate());
			String permitValidThrough = "Permit Valid Through : " + date + " TO " + validityExpiryDate.replace("/", "-");
			Phrase phrase4 = new Phrase(permitValidThrough, font1);
			x1=x+(140 -(permitValidThrough.length()/2));
			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, phrase4, x1, y-45, 0);

			String applicationNumber = "Application Number : " + application.getApplicationNumber();
			Phrase phrase5 = new Phrase(applicationNumber, font1);
			x1=x+(140 -(applicationNumber.length()/2));
			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, phrase5, x1, y-60, 0);
			
			String approvedBy = "Approved By :";
			Phrase phrase6 = new Phrase(approvedBy, font2);
			x1=x+(140 -(approvedBy.length()/2));
			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, phrase6, x1, y - 75, 0);

			String approverName = bpaNoticeUtil.getApproverName(application) + "("
								  + bpaNoticeUtil
							.getApproverDesignation(bpaWorkFlowService.getAmountRuleByServiceType(application).intValue())

								  + ")";
			Phrase phrase7 = new Phrase(approverName, font1);
			x1=x+(140 -(approverName.length()/2));
			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, phrase7, x1, y - 90, 0);
			String reviewedBy = "Reviewed By :";
			Phrase phrase8 = new Phrase(reviewedBy, font2);
			x1=x+(140 -(reviewedBy.length()/2));
			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, phrase8, x1, y - 105, 0);
			
			
			
			LinkedHashSet<String> reviewersList = new LinkedHashSet<>();
			List<Map<String, String>> reviewersNameAndDesignationMapList = bpaNoticeUtil
					.getAllReviewersList(application);

			
			for (Map<String, String> nameAndDesignation : reviewersNameAndDesignationMapList) {
				reviewersList.add(nameAndDesignation.get("name") + " " + "("
						+ nameAndDesignation.get("designation") + ")");
			}

            List<String> listOfAuthorities = new ArrayList<>(reviewersList);
            Collections.reverse(listOfAuthorities);
			 
			Float yy = y - 120;

			for (String reviewer : listOfAuthorities) {
				Phrase phrase9 = new Phrase(reviewer, font1);
				x1=x+(140 -(reviewer.length()/2));
				ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, phrase9, x1, yy, 0);
				yy = yy - 13f;
			}
		
			
			LOG.info("Path = "+path.toString());
			LOG.info("Height ="+height +"width ="+width);
			LOG.info("x ="+x +"y ="+y);
			LOG.info("Rotation =" +pageRotation);
			LOG.info("RATIO HEIGT =" +height1);
			LOG.info("RATIO width =" +width);
			if (height > width || pageRotation==0 || pageRotation==90) {
				LOG.info("Portrait");
			}else
			{
				LOG.info("LandScape");
			}
			
			String pathOfQrCode = generatePDF417Code(bpaNoticeUtil.buildQRCodeDetails(application),140, 50)
					.getAbsolutePath();

			//File f = SecureCodeUtils.generatePDF417Code("manikanta PT", 140, 50).getAbsoluteFile();

			Image image = Image.getInstance(pathOfQrCode);
			PdfImage stream = new PdfImage(image, "", null);

			yy=yy - 55;
		
			PdfIndirectObject ref = pdfStamper.getWriter().addToBody(stream);
			image.setDirectReference(ref.getIndirectReference());
			x1=x+(140-image.getWidth()/2);
					
			image.setAbsolutePosition(x1, yy);
			canvas.addImage(image);
			yy=yy - 15;
			String authentication = "This is a computer generated authentication";
			Phrase phrase9 = new Phrase(authentication, font1);
			x1=x+(140 -(authentication.length()/2));
			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, phrase9, x1, yy , 0);
			yy=yy - 10;
			 authentication = "And Does not require any Seal or Signature in original";
			Phrase phrase10 = new Phrase(authentication, font1);
			x1=x+(140 -(authentication.length()/2));
			//yy=yy-15;
			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, phrase10, x1, yy , 0);
			LOG.info("Last y position "+yy);
			
			pdfStamper.close();
			pdfReader.close();
			
			File modified=new File(newpath.toString());
			File original=new File(path.toString());
			boolean delete = original.delete();
			LOG.info("Deleted " +path.toString() +" status " +delete);
			modified.renameTo(original);
			
			

		} catch (Exception e) {
			e.printStackTrace();
			
		}

	}

	public void addStampForOc(FileStoreMapper fileMapper, OccupancyCertificate oc) {
		try {
			Path path = bpautils.getExistingFilePath(fileMapper, APPLICATION_MODULE_TYPE);
			
			if(path.toString().contains("e028ea4e-9bfc-4681-9684-3fb8e2b6eec7"))
			{
				LOG.info("Test");
				
			}
			PdfReader pdfReader = new PdfReader(path.toString());
			String newpath=path.toString()+"Modified";
			PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(newpath));

			int pageNum = pdfReader.getNumberOfPages();
			//PdfDictionary pageN = pdfReader.getPageN(pageNum);
			Rectangle pageSize = pdfReader.getPageSize(pageNum);
			int pageRotation = pdfReader.getPageRotation(pageNum);
			float height = pageSize.getHeight();
			float width = pageSize.getWidth();
			String location = getLocation(pageRotation, width, height, pageSize, 280, 280);
			String[] split = location.split("--");
		
			float height1 = height / PageSize.A4.getHeight();
			float width1 = width / PageSize.A4.getWidth();
		
		
			float x = 0;
			float y = 0;
			float x1 = 0;
			float y1 = 0;
			if (split != null && split.length > 1) {
				x = Float.valueOf(split[0]);
				y = Float.valueOf(split[1]);
			}
			/*  This is not used as of now 
			 * if (height > width) {
				x1 = x * width;
				y1 = y * height;
			} else {
				x1 = x * height;
				y1 = y * width;
			}*/
			
			
			PdfContentByte canvas = pdfStamper.getOverContent(pageNum);
		//	City city = cityService.getCityByCode(ApplicationThreadLocals.getCityCode());
			String corporationName = ApplicationThreadLocals.getMunicipalityName(); 

			Font font = new Font(BaseFont.createFont("/fonts/ROCC____.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 14,
					Font.BOLD, BaseColor.BLACK);
			
			Font font1 = new Font(Font.FontFamily.TIMES_ROMAN, 14f, Font.BOLD, BaseColor.BLACK);
			
			Font font2 = new Font(Font.FontFamily.COURIER, 9f, Font.NORMAL, BaseColor.BLACK);

			Phrase phrase = new Phrase(corporationName, font1);
			x1=x + (140 - (corporationName.length()/2) * 0.8f );
			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, phrase, x1, y, 0);
			font1.setStyle(Font.BOLD);
			font1.setSize(12);
			String permitDocument = "OCCUPANCY CERTIFICATE";
			x1=x+(140 -(permitDocument.length()/2));
			Phrase phrase2 = new Phrase(permitDocument, font1);
			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, phrase2, x1, y - 12, 0);
			
			font1.setSize(10);
			font1.setStyle(Font.NORMAL);
			String pattern = "dd-MM-yyyy";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			String planPermissionDate = simpleDateFormat.format(oc.getParent().getPlanPermissionDate());
			String permitNumber = "PERMIT NUMBER : "+oc.getParent().getPlanPermissionNumber() + ", Dt :" + planPermissionDate.replace("/", "-");
			x1=x+(140 -(permitNumber.length()/2));
			Phrase phrase3 = new Phrase(permitNumber, font1);
			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, phrase3, x1, y - 30, 0);

			
			String ocApprovalDate = simpleDateFormat.format(oc.getApprovalDate());
			String ocNumber = "OCCUPANCY CERTIFICATE NUMBER : " + oc.getOccupancyCertificateNumber() + ", Dt : " + ocApprovalDate.replace("/", "-");
			Phrase phrase4 = new Phrase(ocNumber, font1);
			x1=x+(140 -(ocNumber.length()/2));
			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, phrase4, x1, y-45, 0);

			/*
			 * String applicationNumber = "Application Number : " +
			 * oc.getApplicationNumber(); Phrase phrase5 = new Phrase(applicationNumber,
			 * font1); x1=x+(140 -(applicationNumber.length()/2));
			 * ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, phrase5, x1, y-60,
			 * 0);
			 */
			
			String approvedBy = "Approved By :";
			Phrase phrase6 = new Phrase(approvedBy, font2);
			x1=x+(140 -(approvedBy.length()/2));
			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, phrase6, x1, y - 60, 0);

			String approverName = bpaNoticeUtil.getOcApproverName(oc) + "("
								  + bpaNoticeUtil
							.getApproverDesignation(bpaWorkFlowService.getAmountRuleByServiceTypeForOc(oc).intValue())

								  + ")";
			Phrase phrase7 = new Phrase(approverName, font1);
			x1=x+(140 -(approverName.length()/2));
			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, phrase7, x1, y - 75, 0);
			/*
			 * String reviewedBy = "Reviewed By :"; Phrase phrase8 = new Phrase(reviewedBy,
			 * font2); x1=x+(140 -(reviewedBy.length()/2));
			 * ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, phrase8, x1, y - 90,
			 * 0);
			 */
			
			
			
			/*
			 * LinkedHashSet<String> reviewersList = new LinkedHashSet<>(); List<Map<String,
			 * String>> reviewersNameAndDesignationMapList = bpaNoticeUtil
			 * .getAllOcReviewersList(oc);
			 * 
			 * 
			 * for (Map<String, String> nameAndDesignation :
			 * reviewersNameAndDesignationMapList) {
			 * reviewersList.add(nameAndDesignation.get("name") + " " + "(" +
			 * nameAndDesignation.get("designation") + ")"); }
			 * 
			 * List<String> listOfAuthorities = new ArrayList<>(reviewersList);
			 * Collections.reverse(listOfAuthorities);
			 */
			 
			Float yy = y - 120;

			/*
			 * for (String reviewer : listOfAuthorities) { Phrase phrase9 = new
			 * Phrase(reviewer, font1); x1=x+(140 -(reviewer.length()/2));
			 * ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, phrase9, x1, yy, 0);
			 * yy = yy - 13f; }
			 */
		
			
			LOG.info("Path = "+path.toString());
			LOG.info("Height ="+height +"width ="+width);
			LOG.info("x ="+x +"y ="+y);
			LOG.info("Rotation =" +pageRotation);
			LOG.info("RATIO HEIGT =" +height1);
			LOG.info("RATIO width =" +width);
			if (height > width || pageRotation==0 || pageRotation==90) {
				LOG.info("Portrait");
			}else
			{
				LOG.info("LandScape");
			}
			
			String pathOfQrCode = generatePDF417Code(bpaNoticeUtil.buildQRCodeDetailsForOc(oc),140, 50)
					.getAbsolutePath();

			//File f = SecureCodeUtils.generatePDF417Code("manikanta PT", 140, 50).getAbsoluteFile();

			Image image = Image.getInstance(pathOfQrCode);
			PdfImage stream = new PdfImage(image, "", null);

			yy=yy - 35;
		
			PdfIndirectObject ref = pdfStamper.getWriter().addToBody(stream);
			image.setDirectReference(ref.getIndirectReference());
			x1=x+(140-image.getWidth()/2);
					
			image.setAbsolutePosition(x1, yy);
			canvas.addImage(image);
			yy=yy - 15;
			String authentication = "This is a computer generated authentication";
			Phrase phrase9 = new Phrase(authentication, font1);
			x1=x+(140 -(authentication.length()/2));
			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, phrase9, x1, yy , 0);
			yy=yy - 10;
			 authentication = "And Does not require any Seal or Signature in original";
			Phrase phrase10 = new Phrase(authentication, font1);
			x1=x+(140 -(authentication.length()/2));
			//yy=yy-15;
			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, phrase10, x1, yy , 0);
			LOG.info("Last y position "+yy);
			
			pdfStamper.close();
			pdfReader.close();
			
			File modified=new File(newpath.toString());
			File original=new File(path.toString());
			boolean delete = original.delete();
			LOG.info("Deleted " +path.toString() +" status " +delete);
			modified.renameTo(original);
			
			

		} catch (Exception e) {
			e.printStackTrace();
			
		}

	}
	public static String getLocation(int rotation, float width, float height, Rectangle mediaBox, int x, int y)
			throws Exception {
		PDImageXObject pdImage;
		float x1 = 0;
		float y1 = 0;
		
		
		System.out.print("Rotation:" + rotation);
		if (rotation == 0) {

			 
		}
		if (rotation == 90 ) {

		x1=width;
		width=height;
		height=x1;
		}
		if (rotation == 180) {


			 
		}
		if (rotation == 270) {

			x1=width;
			width=height;
			height=x1;
		 
		}
		
		x1 = width - (0 + x);
		y1 = height- (height - y);
	
		return x1 + "--" + y1;
	}

}