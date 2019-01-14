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
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.joda.time.DateTime;

import static org.egov.bpa.utils.BpaConstants.ST_CODE_14;
import static org.egov.bpa.utils.BpaConstants.ST_CODE_15;
import static org.egov.infra.utils.ImageUtils.PNG_EXTN;

public class AddImageAbsolutePosition {

    public static void main(String[] args) throws Exception {
        Document document = new Document();
        File file = new File("sample.pdf");
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
        com.itextpdf.text.Rectangle pageSize = new com.itextpdf.text.Rectangle(71, 52);
        document.setPageSize(pageSize);
        document.open();
        prepareStamp(document, writer, document.getPageSize().getWidth());
    /*    Image img = Image.getInstance("/home/mansi/Downloads/Corporation_Emblem(1).jpg");
        img.scaleAbsolute(14, 9);
        img.setAbsolutePosition(29f, 42.5f);
        document.add(img);
        String corporationName = "KOZHIKODE MUNICIPAL CORPORATION";
        Font font = new Font(BaseFont.createFont("/fonts/ROCC____.TTF", BaseFont.IDENTITY_H,
                BaseFont.EMBEDDED), 4.5f, Font.BOLD, BaseColor.BLACK);
        PdfContentByte canvas = writer.getDirectContent();
        Phrase phrase = new Phrase(corporationName, font);
        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase, 9, 38.6f, 0);
        font.setStyle(Font.NORMAL);
        font.setSize(3.5f);
        String permitDocument = "BUILDING PERMIT DOCUMENT";
        Phrase phrase2 = new Phrase(permitDocument, font);
        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase2, 17, 35.4f, 0);
        font.setSize(2.5f);

        String permitNumber = "PERMIT NUMBER : " + "BPA20180000272";
        Phrase phrase3 = new Phrase(permitNumber, font);
        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase3, 18, 32.5f, 0);

        String permitValidThrough = "PERMIT VALID THROUGH : " + "04-10-2018" + " TO " + "03-10-2021";
        Phrase phrase4 = new Phrase(permitValidThrough, font);
        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase4, 10, 30, 0);

        String applicationNumber = "APPLICATION NUMBER : " + "00128-2018-AB";
        Phrase phrase5 = new Phrase(applicationNumber, font);
        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase5, 17, 27.5f, 0);

        Font font2 = new Font(Font.FontFamily.COURIER, 2f, Font.BOLD, BaseColor.BLACK);


        String approvedBy = "APPROVED BY :";
        Phrase phrase6 = new Phrase(approvedBy, font2);
        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase6, 24, 24.5f, 0);

        String approverName = "Mr." + " Joseph Alex Maliyekkal Thazhedath Meethal ".toUpperCase() + "(" + "Secretary".toUpperCase() + ")";
        Phrase phrase7 = new Phrase(approverName, font);
        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase7, ((pageWidth / 2f) - ((approverName.length() / 2f) * 1.25f)), 21.5f, 0);

        String reviewedBy = "REVIEWED BY :";
        Phrase phrase8 = new Phrase(reviewedBy, font2);
        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase8, 24, 18.5f, 0);

        Set<String> reviewersList = new HashSet<>();
        reviewersList.add("Mr. " + "Sibi Anthikkadu Malayil ".toUpperCase() + "(" + "Superintendent Engineer".toUpperCase() + ")");
        reviewersList.add("Mr. " + "Muhammed Ameer Malayam ".toUpperCase() + "(" + "Executive Engineer".toUpperCase() + ")");
        reviewersList.add("Mrs. " + "Asa Latha Ezhikkal ".toUpperCase() + "(" + "Asst. Executive Engineer".toUpperCase() + ")");
        reviewersList.add("Mr. " + "Sreedharan Pillai A.S ".toUpperCase() + "(" + "Asst. Engineer".toUpperCase() + ")");
        reviewersList.add("Mr. " + "Volga Maddasseri Vayal ".toUpperCase() + "(" + "Overseer".toUpperCase() + ")");



    Float y = 18f;
    y = y - 2.5f;
        for(String reviewer : reviewersList) {
            Phrase phrase9 = new Phrase(reviewer, font);
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase9, ((pageWidth / 2f) - ((reviewer.length() / 2f) * 1.25f)), y, 0);
            y = y - 2.5f;
        }*/
        document.close();
        Path pathToStampImage = convertPdfToImage(file);
        String path = "/home/mansi/Downloads/APARTMENT_HIGHT 9.75-Model.pdf A2(2).pdf";
        PDDocument document1 = PDDocument.load(new File(path.toString()));
        PDPage page = document1.getPage(document1.getNumberOfPages() - 1);
        PDRectangle mediaBox = page.getMediaBox();
        PDPageContentStream contentStrm = new PDPageContentStream(document1, page, PDPageContentStream.AppendMode.APPEND,
                true, true);
        try {
            putImageOnPdf(document1, page, mediaBox, contentStrm, pathToStampImage.toString(), 5, 85);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String pathOfQrCode = "/home/mansi/bpaedcr/bpa/xkbnO8192883102616721608.png";
        try {
            putImageOnPdf(document1, page, mediaBox, contentStrm, pathOfQrCode, 30, 23);
        } catch (Exception e) {
            e.printStackTrace();
        }
/*
        Image img2 = Image.getInstance("/home/mansi/bpaedcr/bpa/xkbnO8192883102616721608.png");
*/
        File file2 = new File("sample2.pdf");
        PdfWriter writer2 = PdfWriter.getInstance(document, new FileOutputStream(file2));
        com.itextpdf.text.Rectangle pagesize = new Rectangle(71, 5.5f);
        document.setPageSize(pagesize);
        document.open();
        String authentication = "This is a computer generated authentication".toUpperCase();
        Font fontName = new Font(BaseFont.createFont("/fonts/ROCC____.TTF", BaseFont.IDENTITY_H,
                BaseFont.EMBEDDED), 3f, Font.NORMAL, BaseColor.BLACK);
        PdfContentByte canvas2 = writer2.getDirectContent();
        Phrase elements = new Phrase(authentication, fontName);
        ColumnText.showTextAligned(canvas2, Element.ALIGN_LEFT, elements, 9, 3, 0);
        String seal = "and does not require any seal or signature in original".toUpperCase();
        Phrase phrase1 = new Phrase(seal, fontName);
        ColumnText.showTextAligned(canvas2, Element.ALIGN_LEFT, phrase1, 3, 0.5f, 0);
        document.close();
        Path pathToSealImage = convertPdfToImage(file2);
        putImageOnPdf(document1, page, mediaBox, contentStrm, pathToSealImage.toString(), 5, 0);
        contentStrm.close();
        document1.save(new File(path.toString()));
        document1.close();

        }


    private static void prepareStamp(Document document, PdfWriter writer, Float pageWidth) throws IOException, DocumentException {
        Image img = Image.getInstance("/home/mansi/Downloads/Corporation_Emblem(1).jpg");
        img.scaleAbsolute(14, 9);
        img.setAbsolutePosition(29f, 42.5f);
        document.add(img);
        String corporationName = "KOZHIKODE MUNICIPAL CORPORATION";
        Font font = new Font(BaseFont.createFont("/fonts/ROCC____.TTF", BaseFont.IDENTITY_H,
                BaseFont.EMBEDDED), 4.5f, Font.BOLD, BaseColor.BLACK);
        PdfContentByte canvas = writer.getDirectContent();
        Phrase phrase = new Phrase(corporationName, font);
        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase, 9, 38.6f, 0);
        font.setStyle(Font.NORMAL);
        font.setSize(4f);
        String permitDocument = "BUILDING PERMIT DOCUMENT";
        Phrase phrase2 = new Phrase(permitDocument, font);
        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase2, 17, 35.4f, 0);
        font.setSize(3f);
        String permitNumber = "PERMIT NUMBER : " + "BPA20180000272";
        Phrase phrase3 = new Phrase(permitNumber, font);
        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase3, 18, 32.5f, 0);
        String permitValidThrough = "PERMIT VALID THROUGH : " + "04-10-2018" + " TO " + "03-10-2021";
        Phrase phrase4 = new Phrase(permitValidThrough, font);
        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase4, 10, 30, 0);

        String applicationNumber = "APPLICATION NUMBER : " + "00128-2018-AB";
        Phrase phrase5 = new Phrase(applicationNumber, font);
        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase5, 17, 27.5f, 0);

        Font font2 = new Font(Font.FontFamily.COURIER, 3f, Font.BOLD, BaseColor.BLACK);

        String approvedBy = "APPROVED BY :";
        Phrase phrase6 = new Phrase(approvedBy, font2);
        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase6, 24, 24.5f, 0);

        String approverName = "Mr." + " Joseph Alex Maliyekkal Thazhedath Meethal ".toUpperCase() + "(" + "Secretary".toUpperCase() + ")";

        Phrase phrase7 = new Phrase(approverName, font);
        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase7, ((pageWidth / 2f) - ((approverName.length() / 2f) * 1.25f)), 21.5f, 0);
        String reviewedBy = "REVIEWED BY :";
        Phrase phrase8 = new Phrase(reviewedBy, font2);
        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase8, 24, 18.5f, 0);
        Set<String> reviewersList = new HashSet<>();
        reviewersList.add("Mr. " + "Sibi  Malayil ".toUpperCase() + "(" + "Superintendent Engineer".toUpperCase() + ")");
        reviewersList.add("Mr. " + "Muhammed  Malayam ".toUpperCase() + "(" + "Executive Engineer".toUpperCase() + ")");
        reviewersList.add("Mrs. " + "Asa  Ezhikkal ".toUpperCase() + "(" + "Asst. Executive Engineer".toUpperCase() + ")");
        reviewersList.add("Mr. " + "Sreedharan  A.S ".toUpperCase() + "(" + "Asst. Engineer".toUpperCase() + ")");
        reviewersList.add("Mr. " + "Volga  Vayal ".toUpperCase() + "(" + "Overseer".toUpperCase() + ")");
        reviewersList.add("Mr. " + "Hari ".toUpperCase() + "(" + "Town Planning overseer".toUpperCase() + ")");
        Float y = 18f;
        y = y - 2.5f;
        for (String reviewer : reviewersList) {
            Phrase phrase9 = new Phrase(reviewer, font);
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase9, ((pageWidth / 2f) - ((reviewer.length() / 2f) * 1.25f)), y, 0);
            y = y - 2.5f;
        }
    }

    private static void putImageOnPdf(PDDocument document1, PDPage page, PDRectangle mediaBox, PDPageContentStream contentStrm, String path, int x, int y) throws Exception {
        PDImageXObject pdImage;
        if (page.getRotation() == 0) {
            pdImage = PDImageXObject.createFromFile(path, document1);
            contentStrm.drawImage(pdImage, mediaBox.getWidth() - (pdImage.getWidth() + x), y, pdImage.getWidth(),
                    pdImage.getHeight());
        }
        if (page.getRotation() == 90) {
            pdImage = rotateImage(path, document1, 3 * Math.PI / 2);
            contentStrm.drawImage(pdImage, mediaBox.getWidth() - (pdImage.getWidth() + x),
                    mediaBox.getHeight() - (pdImage.getHeight() + y), pdImage.getWidth(), pdImage.getHeight());
        }
        if (page.getRotation() == 180) {
            pdImage = rotateImage(path, document1, Math.PI / 2);
            contentStrm.drawImage(pdImage, mediaBox.getWidth() - (pdImage.getWidth() + x), y, pdImage.getWidth(),
                    pdImage.getHeight());
        }
        if (page.getRotation() == 270) {
            pdImage = rotateImage(path, document1, Math.PI / 2);
            contentStrm.drawImage(pdImage, x, y, pdImage.getWidth(), pdImage.getHeight());
        }
    }

    public  static Path convertPdfToImage(File file) throws IOException {
        PDDocument doc = PDDocument.load(file);
        PDFRenderer pdfRenderer = new PDFRenderer(doc);
        BufferedImage bim = pdfRenderer.renderImageWithDPI(0, 300, ImageType.RGB);
        Path path = Files.createTempFile(RandomStringUtils.randomAlphabetic(5), PNG_EXTN);
        ImageIOUtil.writeImage(bim, path.toString(), 300);
        doc.close();
        return path;
    }


   /* private static BufferedImage resize(BufferedImage img, int height, int width) {
        java.awt.Image tmp = img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }



    public static void resize(String inputImagePath,
                              String outputImagePath, int scaledWidth, int scaledHeight)
            throws IOException {
        // reads input image
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);

        // creates output image
        BufferedImage outputImage = new BufferedImage(scaledWidth,
                scaledHeight, inputImage.getType());

        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();

        // extracts extension of output file
        String formatName = outputImagePath.substring(outputImagePath
                .lastIndexOf(".") + 1);

        // writes to output file
        ImageIO.write(outputImage, formatName, new File(outputImagePath));
    }*/

    private static PDImageXObject rotateImage(String pathToFile, PDDocument doc, double angle) throws Exception {
        BufferedImage source = ImageIO.read(new File(pathToFile));
        BufferedImage output = new BufferedImage(source.getHeight(), source.getWidth(), source.getType());
        AffineTransformOp op = new AffineTransformOp(rotateClockwise(source,angle), AffineTransformOp.TYPE_BILINEAR);
        op.filter(source, output);
        ImageIO.write(output, "png", new File(pathToFile));
        return PDImageXObject.createFromFile(pathToFile, doc);
    }

    private static  AffineTransform rotateClockwise(BufferedImage source, Double angle) {
        AffineTransform transform = new AffineTransform();
        transform.rotate(angle, source.getWidth() / 2, source.getHeight() / 2);
        double offset = (source.getWidth() - source.getHeight()) / 2;
        transform.translate(offset, offset);
        return transform;
    }

}
