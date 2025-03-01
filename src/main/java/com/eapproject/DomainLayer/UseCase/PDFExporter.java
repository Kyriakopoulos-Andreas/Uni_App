package com.eapproject.DomainLayer.UseCase;

import com.eapproject.DataLayer.DB.University;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.DoubleBorder;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.*;

/**
 * Η κλάση {@code PDFExporter} παρέχει λειτουργικότητα για την εξαγωγή στατιστικών πανεπιστημίων
 * σε αρχείο PDF, χρησιμοποιώντας τη βιβλιοθήκη iText. Υποστηρίζει καταγραφή μέσω Logger και
 * χειρισμό εξαιρέσεων, ενώ παράγει ένα αρχείο PDF που περιέχει header, τίτλο, πίνακα δεδομένων
 * και footer με αρίθμηση σελίδων.
 */
public class PDFExporter {

    // Σταθερή διαδρομή προς την γραμματοσειρά που υποστηρίζει ελληνικούς χαρακτήρες
    private static final String DEFAULT_FONT_RELATIVE_PATH = "resources/fonts/FreeSans.ttf";

    // Στατικός Logger για την καταγραφή ενεργειών και σφαλμάτων
    private static final Logger LOGGER = Logger.getLogger(PDFExporter.class.getName());

    // Στατική αρχικοποίηση του Logger
    static {
        initializeLogger();
    }

    /**
     * Αρχικοποιεί τον Logger, ώστε να καταγράφει μηνύματα σε αρχείο στο φάκελο logs,
     * με χρήση του {@code SimpleFormatter}. Επίσης, αφαιρεί τυχόν προηγούμενους handlers
     * για την αποφυγή διπλο-καταγραφών.
     */
    private static void initializeLogger() {
        try {
            // Δημιουργούμε το φάκελο "logs" αν δεν υπάρχει
            Files.createDirectories(Paths.get("logs"));

            // Αφαιρούμε τυχόν υπάρχοντες handlers
            for (Handler h : LOGGER.getHandlers()) {
                LOGGER.removeHandler(h);
            }
            // Προσθήκη FileHandler για το αρχείο "logs/PDFExporter.log"
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

    /**
     * Εξάγει τα στατιστικά των πανεπιστημίων σε αρχείο PDF.
     * <p>
     * Το όνομα του αρχείου δημιουργείται αυτόματα βάσει της τρέχουσας ημερομηνίας και ώρας.
     * Περιλαμβάνει header (λογότυπο + κείμενο "UniApp"), έναν τίτλο κεντραρισμένο,
     * έναν πίνακα δεδομένων (Α/Α, Όνομα Πανεπιστημίου, Χώρα, Προβολές)
     * και footer με σελιδοποίηση "Σελίδα X από Y".
     * </p>
     *
     * @param popularUniversities η λίστα πανεπιστημίων με τις προβολές που θα εμφανιστούν στο PDF
     * @return {@code true} αν το PDF δημιουργήθηκε επιτυχώς, διαφορετικά {@code false}
     * @throws Exception σε περίπτωση σφάλματος κατά τη δημιουργία του PDF.
     */
    public static boolean exportStatisticsToPDF(List<University> popularUniversities) throws Exception {
        // Έλεγχος για κενή ή null λίστα
        if (popularUniversities == null || popularUniversities.isEmpty()) {
            LOGGER.log(Level.INFO, "ℹ️ Δεν υπάρχουν διαθέσιμα στατιστικά για εξαγωγή.");
            return false;
        }

        // Δημιουργία ονόματος αρχείου βάσει τρέχουσας ημερομηνίας/ώρας
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        String timestamp = LocalDateTime.now().format(dtf);
        String dynamicFilename = "Stats_" + timestamp + ".pdf";

        // try-with-resources για αυτόματο κλείσιμο των πόρων (PdfWriter, PdfDocument, Document)
        try (PdfWriter writer = new PdfWriter(dynamicFilename);
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {

            // Placeholder για το συνολικό πλήθος σελίδων - θα χρησιμοποιηθεί στο footer
            PdfFormXObject placeholder = new PdfFormXObject(new Rectangle(0, 0, 50, 10));

            // Σύνδεση του FooterHandler με το έγγραφο
            pdf.addEventHandler(PdfDocumentEvent.END_PAGE, new FooterHandler(placeholder));

            // Φόρτωση γραμματοσειράς που υποστηρίζει ελληνικά
            PdfFont font = PdfFontFactory.createFont(DEFAULT_FONT_RELATIVE_PATH, PdfEncodings.IDENTITY_H, true);
            document.setFont(font);

            // Δημιουργία header (λογότυπο + "UniApp")
            addHeader(document, font);

            // Τίτλος εγγράφου
            Paragraph title = new Paragraph("Στατιστικά Δημοφιλέστερων Αναζητήσεων")
                    .setFontSize(18)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20);
            document.add(title);

            // Δημιουργία πίνακα δεδομένων
            Table table = createTable(popularUniversities);
            table.setBorder(new DoubleBorder(ColorConstants.BLACK, 1));
            document.add(table);

            // Ενημέρωση του placeholder με το σύνολο των σελίδων
            updatePlaceholder(pdf, placeholder);

        } // Αυτόματα κλείνουν οι πόροι (PdfWriter, PdfDocument, Document)

        LOGGER.log(Level.INFO, "✅ Το αρχείο {0} δημιουργήθηκε με επιτυχία.", dynamicFilename);
        return true;
    }

    /**
     * Προσθέτει header με λογότυπο και κείμενο "UniApp" στο έγγραφο.
     *
     * @param document Το {@link Document} στο οποίο προσθέτουμε το header
     * @param font     Η γραμματοσειρά που θα χρησιμοποιήσουμε
     * @throws IOException Αν παρουσιαστεί σφάλμα φόρτωσης εικόνας λογοτύπου
     */
    private static void addHeader(Document document, PdfFont font) throws IOException {
        // Καθορισμός πλάτους στηλών για το header: 1, 4
        float[] headerColWidths = {1, 4};
        Table headerTable = new Table(UnitValue.createPercentArray(headerColWidths)).useAllAvailableWidth();

        // Φόρτωση εικόνας λογοτύπου
        ImageData logoData = ImageDataFactory.create("resources/img/logo.png");
        Image logo = new Image(logoData);
        // Κλιμάκωση του λογοτύπου στο ~35% του αρχικού μεγέθους
        logo.scaleAbsolute(logo.getImageScaledWidth() * 0.35f, logo.getImageScaledHeight() * 0.35f);

        // Πρώτη στήλη: το λογότυπο
        headerTable.addCell(new Cell()
                .add(logo)
                .setBorder(null)
                .setVerticalAlignment(VerticalAlignment.MIDDLE));

        // Δεύτερη στήλη: κείμενο "UniApp"
        Paragraph headerText = new Paragraph("UniApp")
                .setFont(font)
                .setFontSize(30)
                .setBold();

        headerTable.addCell(new Cell()
                .add(headerText)
                .setBorder(null)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.LEFT));

        // Προσθήκη του headerTable στο document
        document.add(headerTable);
        // Κενή παράγραφος για απόσταση
        document.add(new Paragraph("\n"));
    }

    /**
     * Δημιουργεί έναν πίνακα που περιλαμβάνει τα πανεπιστήμια και τις προβολές τους.
     *
     * @param popularUniversities Η λίστα των πανεπιστημίων προς εμφάνιση
     * @return Ένα {@link Table} με στήλες "Α/Α", "Όνομα Πανεπιστημίου", "Χώρα", "Προβολές"
     */
    private static Table createTable(List<University> popularUniversities) {
        float[] columnWidths = {1, 4, 3, 2};
        Table table = new Table(UnitValue.createPercentArray(columnWidths)).useAllAvailableWidth();

        // Προσθήκη κεφαλίδων
        addHeaderCell(table, "Α/Α");
        addHeaderCell(table, "Όνομα Πανεπιστημίου");
        addHeaderCell(table, "Χώρα");
        addHeaderCell(table, "Προβολές");

        int counter = 1;
        for (University uni : popularUniversities) {
            // Στήλη Α/Α
            table.addCell(new Cell()
                    .add(new Paragraph(String.valueOf(counter++)))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setPadding(5)
                    .setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f)));

            // Στήλη Όνομα Πανεπιστημίου
            table.addCell(new Cell()
                    .add(new Paragraph(uni.getName()))
                    .setTextAlignment(TextAlignment.LEFT)
                    .setPadding(5)
                    .setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f)));

            // Στήλη Χώρα
            table.addCell(new Cell()
                    .add(new Paragraph(uni.getCountry()))
                    .setTextAlignment(TextAlignment.LEFT)
                    .setPadding(5)
                    .setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f)));

            // Στήλη Προβολές
            table.addCell(new Cell()
                    .add(new Paragraph(String.valueOf(uni.getViewCount())))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setPadding(5)
                    .setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f)));
        }
        return table;
    }

    /**
     * Προσθέτει μία κεφαλίδα (header) σε έναν πίνακα με διπλό (Double) περίγραμμα.
     *
     * @param table Το {@link Table} στο οποίο θα προστεθεί η κεφαλίδα
     * @param text  Το κείμενο της κεφαλίδας
     */
    private static void addHeaderCell(Table table, String text) {
        table.addHeaderCell(new Cell()
                .add(new Paragraph(text))
                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
                .setBorder(new DoubleBorder(ColorConstants.BLACK, 1)));
    }

    /**
     * Ενημερώνει το placeholder που εμφανίζει το σύνολο των σελίδων στο footer.
     *
     * @param pdf         Το {@link PdfDocument} του PDF.
     * @param placeholder Το placeholder σε μορφή {@link PdfFormXObject}.
     * @throws IOException Αν προκύψει σφάλμα κατά τη φόρτωση της γραμματοσειράς.
     */
    private static void updatePlaceholder(PdfDocument pdf, PdfFormXObject placeholder) throws IOException {
        int totalPages = pdf.getNumberOfPages();
        // Φόρτωση της γραμματοσειράς για το footer
        PdfFont footerFont = PdfFontFactory.createFont(DEFAULT_FONT_RELATIVE_PATH, PdfEncodings.IDENTITY_H, true);

        // Δημιουργούμε Canvas για να γράψουμε στο placeholder
        try (Canvas canvasForPlaceholder = new Canvas(new PdfCanvas(placeholder, pdf),
                pdf, new Rectangle(0, 0, 50, 10))) {
            Paragraph totalPagesParagraph = new Paragraph(String.valueOf(totalPages))
                    .setFont(footerFont)
                    .setFontSize(10)
                    .setFontColor(ColorConstants.GRAY);

            canvasForPlaceholder.showTextAligned(totalPagesParagraph, 0, 0, TextAlignment.LEFT);
        }
    }

    /**
     * Η εσωτερική κλάση {@code FooterHandler} δημιουργεί το footer σε κάθε σελίδα,
     * εμφανίζοντας το "Σελίδα X από Y".
     */
    private static class FooterHandler implements IEventHandler {

        private final PdfFormXObject placeholder;

        /**
         * Κατασκευαστής που λαμβάνει το placeholder για την ένδειξη "Y" στο "Σελίδα X από Y".
         *
         * @param placeholder Το {@link PdfFormXObject} που θα αντικατασταθεί με τον αριθμό
         *                    των συνολικών σελίδων.
         */
        public FooterHandler(PdfFormXObject placeholder) {
            this.placeholder = placeholder;
        }

        @Override
        public void handleEvent(com.itextpdf.kernel.events.Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfDocument pdfDoc = docEvent.getDocument();
            int pageNumber = pdfDoc.getPageNumber(docEvent.getPage());
            Rectangle pageSize = docEvent.getPage().getPageSize();

            PdfFont footerFont;
            try {
                footerFont = PdfFontFactory.createFont(DEFAULT_FONT_RELATIVE_PATH, PdfEncodings.IDENTITY_H, true);
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "❌ Σφάλμα κατά τη φόρτωση της γραμματοσειράς στο footer", e);
                return;
            }

            int fontSize = 10;
            String footerText = String.format("Σελίδα %d από ", pageNumber);

            float textWidth = footerFont.getWidth(footerText, fontSize);
            PdfArray bbox = placeholder.getPdfObject().getAsArray(PdfName.BBox);
            Rectangle bboxRect = new Rectangle(
                    bbox.getAsNumber(0).floatValue(),
                    bbox.getAsNumber(1).floatValue(),
                    bbox.getAsNumber(2).floatValue(),
                    bbox.getAsNumber(3).floatValue()
            );
            float placeholderWidth = bboxRect.getWidth();
            float totalWidth = textWidth + placeholderWidth;

            float startX = (pageSize.getWidth() - totalWidth) / 2;
            float bottomY = pageSize.getBottom() + 15;

            // Δημιουργία παραγράφου για την ένδειξη "Σελίδα X από "
            Paragraph footerParagraph = new Paragraph(footerText)
                    .setFont(footerFont)
                    .setFontSize(fontSize)
                    .setFontColor(ColorConstants.GRAY)
                    .setMargin(0);

            // Δημιουργούμε PdfCanvas (εκτός try-with-resources)
            PdfCanvas pdfCanvas = new PdfCanvas(
                    docEvent.getPage().newContentStreamAfter(),
                    docEvent.getPage().getResources(),
                    pdfDoc
            );

            // Χρήση try-with-resources μόνο για το Canvas (υλοποιεί AutoCloseable)
            try (Canvas canvasModel = new Canvas(pdfCanvas, pdfDoc, pageSize)) {
                // Τοποθετούμε το κείμενο "Σελίδα X από "
                canvasModel.showTextAligned(footerParagraph, startX, bottomY, TextAlignment.LEFT);

                // Τοποθετούμε το placeholder (με το σύνολο των σελίδων)
                Image placeholderImage = new Image(placeholder).setFixedPosition(startX + textWidth, bottomY);
                canvasModel.add(placeholderImage);

            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "⚠️ Σφάλμα κατά το render του footer στο PDF.", e);
            }
        }
    }
}
