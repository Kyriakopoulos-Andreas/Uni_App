package com.eapproject.DomainLayer.UseCase;

import com.eapproject.DataLayer.DB.University;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDocument;
//import com.itextpdf.kernel.pdf.PdfFormXObject;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.DoubleBorder;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.*;
import java.nio.file.*;
import java.io.IOException;

public class PDFExporter {

    private static final Logger LOGGER = Logger.getLogger(PDFExporter.class.getName());

    static {
        initializeLogger();
    }

    private static void initializeLogger() {
        try {
            Files.createDirectories(Paths.get("logs"));
            for (Handler h : LOGGER.getHandlers()) {
                LOGGER.removeHandler(h);
            }
            FileHandler fileHandler = new FileHandler("logs/PDFExporter.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);
            LOGGER.setLevel(Level.ALL);
            LOGGER.setUseParentHandlers(false);
            LOGGER.info("📌 Έναρξη καταγραφής του Logger στο logs/PDFExporter.log");
        } catch (IOException e) {
            System.err.println("❌ Σφάλμα κατά την αρχικοποίηση του Logger για PDFExporter: " + e.getMessage());
        }
    }

    // Relative path για τη γραμματοσειρά.
    private static final String DEFAULT_FONT_RELATIVE_PATH = "resources/fonts/FreeSans.ttf";

    /**
     * Εξάγει τα στατιστικά των πανεπιστημίων σε αρχείο PDF.
     *
     * @param popularUniversities η λίστα των πανεπιστημίων προς εξαγωγή στο PDF.
     * @return {@code true} αν το PDF δημιουργήθηκε επιτυχώς, {@code false} αν η λίστα είναι null ή κενή.
     * @throws Exception σε περίπτωση σφάλματος κατά την δημιουργία του PDF.
     */
    public static boolean exportStatisticsToPDF(List<University> popularUniversities) throws Exception {
        if (popularUniversities == null || popularUniversities.isEmpty()) {
            LOGGER.log(Level.INFO, "ℹ️ Δεν υπάρχουν διαθέσιμα στατιστικά για εξαγωγή.");
            return false;
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        String timestamp = LocalDateTime.now().format(dtf);
        String dynamicFilename = "Stats_" + timestamp + ".pdf";

        PdfWriter writer = new PdfWriter(dynamicFilename);
        PdfDocument pdf = new PdfDocument(writer);

        // Δημιουργία του placeholder για το συνολικό πλήθος σελίδων
        PdfFormXObject placeholder = new PdfFormXObject(new Rectangle(0, 0, 50, 10));

        // Εγγραφή του FooterHandler με το placeholder για κάθε σελίδα
        pdf.addEventHandler(PdfDocumentEvent.END_PAGE, new FooterHandler(placeholder));

        Document document = new Document(pdf);
        PdfFont font = PdfFontFactory.createFont(DEFAULT_FONT_RELATIVE_PATH, PdfEncodings.IDENTITY_H, true);
        document.setFont(font);

        // Header: Logo και "UniApp"
        float[] headerColWidths = {1, 4};
        Table headerTable = new Table(UnitValue.createPercentArray(headerColWidths)).useAllAvailableWidth();
        ImageData logoData = ImageDataFactory.create("resources/logo/logo.png");
        Image logo = new Image(logoData);
        logo.scaleAbsolute(logo.getImageScaledWidth() * 0.35f, logo.getImageScaledHeight() * 0.35f);
        headerTable.addCell(
                new Cell().add(logo)
                          .setBorder(null)
                          .setVerticalAlignment(com.itextpdf.layout.property.VerticalAlignment.MIDDLE));

        Paragraph headerText = new Paragraph("UniApp")
                .setFont(font)
                .setFontSize(30)
                .setBold();

        headerTable.addCell(
                new Cell().add(headerText)
                          .setBorder(null)
                          .setVerticalAlignment(com.itextpdf.layout.property.VerticalAlignment.MIDDLE)
                          .setTextAlignment(TextAlignment.LEFT)
        );
        document.add(headerTable);
        document.add(new Paragraph("\n"));

        Paragraph title = new Paragraph("Στατιστικά Δημοφιλέστερων Αναζητήσεων")
                .setFontSize(18)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20);
        document.add(title);

        Table table = createTable(popularUniversities);
        table.setBorder(new DoubleBorder(new DeviceRgb(223, 109, 35), 1));
        document.add(table);

        // Ενημέρωση του placeholder με το συνολικό πλήθος σελίδων πριν το κλείσιμο του document
        int totalPages = pdf.getNumberOfPages();
        PdfCanvas canvasPlaceholder = new PdfCanvas(placeholder, pdf);
        Canvas canvasForPlaceholder = new Canvas(canvasPlaceholder, pdf, new Rectangle(0, 0, 50, 10));
        canvasForPlaceholder.showTextAligned(String.valueOf(totalPages), 0, 0, TextAlignment.LEFT);
        canvasForPlaceholder.close();

        // Κλείσιμο του Document
        document.close();

        LOGGER.log(Level.INFO, "✅ Το αρχείο {0} δημιουργήθηκε με επιτυχία.", dynamicFilename);
        return true;
    }

    private static Table createTable(List<University> popularUniversities) {
        float[] columnWidths = {1, 4, 3, 2};
        Table table = new Table(UnitValue.createPercentArray(columnWidths)).useAllAvailableWidth();

        addHeaderCell(table, "Α/Α");
        addHeaderCell(table, "Όνομα Πανεπιστημίου");
        addHeaderCell(table, "Χώρα");
        addHeaderCell(table, "Προβολές");

        int counter = 1;
        for (University uni : popularUniversities) {
            Cell cellIndex = new Cell()
                    .add(new Paragraph(String.valueOf(counter++)))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setPadding(5)
                    .setBorder(new SolidBorder(new DeviceRgb(223, 109, 35), 0.5f));
            table.addCell(cellIndex);

            Cell cellName = new Cell()
                    .add(new Paragraph(uni.getName()))
                    .setTextAlignment(TextAlignment.LEFT)
                    .setPadding(5)
                    .setBorder(new SolidBorder(new DeviceRgb(223, 109, 35), 0.5f));
            table.addCell(cellName);

            Cell cellCountry = new Cell()
                    .add(new Paragraph(uni.getCountry()))
                    .setTextAlignment(TextAlignment.LEFT)
                    .setPadding(5)
                    .setBorder(new SolidBorder(new DeviceRgb(223, 109, 35), 0.5f));
            table.addCell(cellCountry);

            Cell cellViews = new Cell()
                    .add(new Paragraph(String.valueOf(uni.getViewCount())))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setPadding(5)
                    .setBorder(new SolidBorder(new DeviceRgb(223, 109, 35), 0.5f));
            table.addCell(cellViews);
        }
        return table;
    }

    private static void addHeaderCell(Table table, String text) {
        table.addHeaderCell(
                new Cell()
                        .add(new Paragraph(text))
                        .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                        .setBold()
                        .setTextAlignment(TextAlignment.CENTER)
                        .setBorder(new DoubleBorder(new DeviceRgb(223, 109, 35), 1))
        );
    }

    /**
     * FooterHandler: Εμφανίζει "Σελίδα X από Y" στο footer κάθε σελίδας.
     */
    private static class FooterHandler implements IEventHandler {
        private PdfFormXObject placeholder;

        public FooterHandler(PdfFormXObject placeholder) {
            this.placeholder = placeholder;
        }

        @Override
        public void handleEvent(com.itextpdf.kernel.events.Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfDocument pdfDoc = docEvent.getDocument();
            int pageNumber = pdfDoc.getPageNumber(docEvent.getPage());
            Rectangle pageSize = docEvent.getPage().getPageSize();
            float pageWidth = pageSize.getWidth();
            // Ορίζουμε τη γραμματοσειρά και το μέγεθος για το footer
            PdfFont footerFont;
            try {
                footerFont = PdfFontFactory.createFont(DEFAULT_FONT_RELATIVE_PATH, PdfEncodings.IDENTITY_H, true);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            int fontSize = 10;
            // Το κείμενο για την τρέχουσα σελίδα
            String footerText = String.format("Σελίδα %d από ", pageNumber);
            // Υπολογισμός του πλάτους του κειμένου
            float textWidth = footerFont.getWidth(footerText, fontSize);
            // Λήψη του BBox του placeholder ως PdfArray και δημιουργία Rectangle για λήψη του πλάτους
            PdfArray bbox = placeholder.getPdfObject().getAsArray(PdfName.BBox);
            Rectangle bboxRect = new Rectangle(
                    bbox.getAsNumber(0).floatValue(),
                    bbox.getAsNumber(1).floatValue(),
                    bbox.getAsNumber(2).floatValue(),
                    bbox.getAsNumber(3).floatValue()
            );
            float placeholderWidth = bboxRect.getWidth();
            // Ο συνολικός συνδυασμός πλάτους
            float totalWidth = textWidth + placeholderWidth;
            // Υπολογισμός του x ώστε ο συνδυασμός να κεντραριστεί
            float startX = (pageWidth - totalWidth) / 2;
            // Ορίζουμε το y σημείο (προσαρμόστε το όπως απαιτείται)
            float bottomY = pageSize.getBottom() + 15;

            // Δημιουργούμε το Paragraph για το κείμενο
            Paragraph footerParagraph = new Paragraph(footerText)
                    .setFont(footerFont)
                    .setFontSize(fontSize)
                    .setMargin(0)
                    .setMultipliedLeading(1);
            // Δημιουργία ενός PdfCanvas για την τρέχουσα σελίδα
            PdfCanvas pdfCanvas = new PdfCanvas(
                    docEvent.getPage().newContentStreamAfter(),
                    docEvent.getPage().getResources(),
                    pdfDoc
            );
            Canvas canvasModel = new Canvas(pdfCanvas, pdfDoc, pageSize);
            // Εμφανίζουμε το κείμενο στο υπολογισμένο σημείο
            canvasModel.showTextAligned(footerParagraph, startX, bottomY, TextAlignment.LEFT);
            // Τοποθετούμε το placeholder δίπλα στο κείμενο
            canvasModel.add(new Image(placeholder).setFixedPosition(startX + textWidth, bottomY));
            canvasModel.close();
        }
    }
}
