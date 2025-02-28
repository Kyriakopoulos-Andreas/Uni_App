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
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDocument;
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

/**
 * Η κλάση {@code PDFExporter} παρέχει λειτουργίες για τη δημιουργία PDF αρχείου με στατιστικά πανεπιστημίων.
 * <p>
 * Το PDF που δημιουργείται περιέχει:
 * <ul>
 *   <li>Έναν header με το λογότυπο και το κείμενο "UniApp".</li>
 *   <li>Τίτλο του εγγράφου ("Στατιστικά Δημοφιλέστερων Αναζητήσεων").</li>
 *   <li>Πίνακα με στατιστικά δεδομένα, με 4 στήλες: "Α/Α", "Όνομα Πανεπιστημίου", "Χώρα" και "Προβολές".</li>
 *   <li>Footer που εμφανίζει τη σελιδοποίηση στη μορφή "Σελίδα X από Y", όπου το "Y" ενημερώνεται μέσω ενός placeholder.</li>
 * </ul>
 * Για την σωστή απεικόνιση των ελληνικών χαρακτήρων χρησιμοποιείται η γραμματοσειρά {@code FreeSans.ttf}.
 * Τα περιγράμματα του πίνακα έχουν οριστεί σε μαύρο χρώμα και το footer εμφανίζεται με μέγεθος 10 και γκρι χρώμα.
 * </p>
 */
public class PDFExporter {

    // Logger για την καταγραφή μηνυμάτων και σφαλμάτων.
    private static final Logger LOGGER = Logger.getLogger(PDFExporter.class.getName());

    // Εκκίνηση του Logger κατά τη φόρτωση της κλάσης.
    static {
        initializeLogger();
    }

    /**
     * Αρχικοποιεί τον Logger ώστε να καταγράφει μηνύματα σε αρχείο στο φάκελο logs.
     */
    private static void initializeLogger() {
        try {
            // Δημιουργούμε το φάκελο "logs" εάν δεν υπάρχει
            Files.createDirectories(Paths.get("logs"));
            // Αφαιρούμε τυχόν υπάρχοντες handlers για να μην επαναλαμβάνονται μηνύματα
            for (Handler h : LOGGER.getHandlers()) {
                LOGGER.removeHandler(h);
            }
            // Δημιουργούμε ένα FileHandler για το αρχείο καταγραφής
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

    // Ορισμός διαδρομής για τη βασική γραμματοσειρά (χωρίς italic)
    private static final String DEFAULT_FONT_RELATIVE_PATH = "resources/fonts/FreeSans.ttf";

    /**
     * Εξάγει τα στατιστικά των πανεπιστημίων σε αρχείο PDF.
     *
     * @param popularUniversities Η λίστα των πανεπιστημίων προς εξαγωγή.
     * @return {@code true} εάν το PDF δημιουργήθηκε επιτυχώς, {@code false} εάν δεν υπάρχουν διαθέσιμα δεδομένα.
     * @throws Exception Εάν παρουσιαστεί σφάλμα κατά τη δημιουργία του PDF.
     */
    public static boolean exportStatisticsToPDF(List<University> popularUniversities) throws Exception {
        if (popularUniversities == null || popularUniversities.isEmpty()) {
            LOGGER.log(Level.INFO, "ℹ️ Δεν υπάρχουν διαθέσιμα στατιστικά για εξαγωγή.");
            return false;
        }

        // Δημιουργία ονόματος αρχείου βάσει της τρέχουσας ημερομηνίας και ώρας
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        String timestamp = LocalDateTime.now().format(dtf);
        String dynamicFilename = "Stats_" + timestamp + ".pdf";

        // Χρήση try-with-resources για αυτόματη διαχείριση των πόρων
        try (PdfWriter writer = new PdfWriter(dynamicFilename);
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {

            // Δημιουργούμε ένα placeholder για το συνολικό πλήθος σελίδων (θα ενημερωθεί στο footer)
            PdfFormXObject placeholder = new PdfFormXObject(new Rectangle(0, 0, 50, 10));

            // Προσθέτουμε event handler που θα εμφανίζει το footer σε κάθε σελίδα
            pdf.addEventHandler(PdfDocumentEvent.END_PAGE, new FooterHandler(placeholder));

            // Δημιουργούμε το PdfFont από τη βασική γραμματοσειρά
            PdfFont font = PdfFontFactory.createFont(DEFAULT_FONT_RELATIVE_PATH, PdfEncodings.IDENTITY_H, true);
            document.setFont(font);

            // Προσθέτουμε το header του εγγράφου (λογότυπο και κείμενο "UniApp")
            addHeader(document, font);

            // Δημιουργούμε τον τίτλο του εγγράφου και τον προσθέτουμε
            Paragraph title = new Paragraph("Στατιστικά Δημοφιλέστερων Αναζητήσεων")
                    .setFontSize(18)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20);
            document.add(title);

            // Δημιουργούμε τον πίνακα με τα στατιστικά δεδομένα
            Table table = createTable(popularUniversities);
            table.setBorder(new DoubleBorder(ColorConstants.BLACK, 1));
            document.add(table);

            // Ενημερώνουμε το placeholder με το συνολικό πλήθος σελίδων πριν το κλείσιμο του εγγράφου
            updatePlaceholder(pdf, placeholder);
        } // Οι πόροι PdfWriter, PdfDocument και Document κλείνουν αυτόματα εδώ.

        LOGGER.log(Level.INFO, "✅ Το αρχείο {0} δημιουργήθηκε με επιτυχία.", dynamicFilename);
        return true;
    }

    /**
     * Προσθέτει το header στο έγγραφο με το λογότυπο και το κείμενο "UniApp".
     *
     * @param document Το {@code Document} στο οποίο προστίθεται το header.
     * @param font     Η γραμματοσειρά που χρησιμοποιείται για το κείμενο.
     * @throws IOException Εάν παρουσιαστεί σφάλμα κατά τη φόρτωση του λογοτύπου.
     */
    private static void addHeader(Document document, PdfFont font) throws IOException {
        // Ορίζουμε το πλάτος των στηλών του header ως ποσοστό (1:4)
        float[] headerColWidths = {1, 4};
        Table headerTable = new Table(UnitValue.createPercentArray(headerColWidths)).useAllAvailableWidth();

        // Φόρτωση του λογοτύπου από το αρχείο και κλιμάκωση του στο 35% του αρχικού μεγέθους
        ImageData logoData = ImageDataFactory.create("resources/img/logo.png");
        Image logo = new Image(logoData);
        logo.scaleAbsolute(logo.getImageScaledWidth() * 0.35f, logo.getImageScaledHeight() * 0.35f);
        headerTable.addCell(new Cell()
                .add(logo)
                .setBorder(null)
                .setVerticalAlignment(com.itextpdf.layout.property.VerticalAlignment.MIDDLE));

        // Δημιουργία και προσθήκη του κειμένου "UniApp" με τη γραμματοσειρά, μέγεθος 30 και έντονη γραφή
        Paragraph headerText = new Paragraph("UniApp")
                .setFont(font)
                .setFontSize(30)
                .setBold();
        headerTable.addCell(new Cell()
                .add(headerText)
                .setBorder(null)
                .setVerticalAlignment(com.itextpdf.layout.property.VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.LEFT));

        // Προσθήκη του header στο έγγραφο και προσθήκη κενού παραγράφου για διαχωρισμό από το κυρίως περιεχόμενο
        document.add(headerTable);
        document.add(new Paragraph("\n"));
    }

    /**
     * Δημιουργεί και επιστρέφει έναν πίνακα με τις στατιστικές πληροφορίες των πανεπιστημίων.
     *
     * @param popularUniversities Η λίστα των πανεπιστημίων.
     * @return Ο πίνακας που περιέχει τις στατιστικές πληροφορίες.
     */
    private static Table createTable(List<University> popularUniversities) {
        // Ορίζουμε το πλάτος των στηλών ως ποσοστό: 1, 4, 3, 2
        float[] columnWidths = {1, 4, 3, 2};
        Table table = new Table(UnitValue.createPercentArray(columnWidths)).useAllAvailableWidth();

        // Προσθέτουμε τις κεφαλίδες στηλών με μαύρα περιγράμματα
        addHeaderCell(table, "Α/Α");
        addHeaderCell(table, "Όνομα Πανεπιστημίου");
        addHeaderCell(table, "Χώρα");
        addHeaderCell(table, "Προβολές");

        int counter = 1;
        // Για κάθε πανεπιστήμιο, προσθέτουμε μια γραμμή δεδομένων στον πίνακα
        for (University uni : popularUniversities) {
            table.addCell(new Cell()
                    .add(new Paragraph(String.valueOf(counter++)))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setPadding(5)
                    .setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f)));

            table.addCell(new Cell()
                    .add(new Paragraph(uni.getName()))
                    .setTextAlignment(TextAlignment.LEFT)
                    .setPadding(5)
                    .setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f)));

            table.addCell(new Cell()
                    .add(new Paragraph(uni.getCountry()))
                    .setTextAlignment(TextAlignment.LEFT)
                    .setPadding(5)
                    .setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f)));

            table.addCell(new Cell()
                    .add(new Paragraph(String.valueOf(uni.getViewCount())))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setPadding(5)
                    .setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f)));
        }
        return table;
    }

    /**
     * Προσθέτει μια κεφαλίδα στηλών σε έναν πίνακα με μαύρο περίγραμμα.
     *
     * @param table Το {@code Table} στο οποίο θα προστεθεί η κεφαλίδα.
     * @param text  Το κείμενο της κεφαλίδας.
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
     * Ενημερώνει το placeholder του footer με το συνολικό πλήθος σελίδων.
     * Αυτό επιτρέπει στο footer να εμφανίζει σωστά το "Y" στο "Σελίδα X από Y".
     *
     * @param pdf         Το {@code PdfDocument} που περιέχει το έγγραφο.
     * @param placeholder Το {@code PdfFormXObject} που χρησιμοποιείται ως placeholder.
     * @throws IOException Εάν παρουσιαστεί σφάλμα κατά τη φόρτωση της γραμματοσειράς.
     */
    private static void updatePlaceholder(PdfDocument pdf, PdfFormXObject placeholder) throws IOException {
        int totalPages = pdf.getNumberOfPages();
        PdfFont footerFont = PdfFontFactory.createFont(DEFAULT_FONT_RELATIVE_PATH, PdfEncodings.IDENTITY_H, true);
        // Χρήση try-with-resources για τη δημιουργία του Canvas που θα αποδώσει το placeholder
        try (Canvas canvasForPlaceholder = new Canvas(new PdfCanvas(placeholder, pdf), pdf, new Rectangle(0, 0, 50, 10))) {
            Paragraph totalPagesParagraph = new Paragraph(String.valueOf(totalPages))
                    .setFont(footerFont)
                    .setFontSize(10)
                    .setFontColor(ColorConstants.GRAY);
            canvasForPlaceholder.showTextAligned(totalPagesParagraph, 0, 0, TextAlignment.LEFT);
        }
    }

    /**
     * Ο event handler που αποδίδει το footer σε κάθε σελίδα.
     * Εμφανίζει το μήνυμα "Σελίδα X από Y" με μέγεθος 10 και γκρι χρώμα, χρησιμοποιώντας τη βασική γραμματοσειρά.
     */
    private static class FooterHandler implements IEventHandler {
        private final PdfFormXObject placeholder;

        /**
         * Κατασκευαστής που δέχεται το placeholder για το συνολικό πλήθος σελίδων.
         *
         * @param placeholder Το {@code PdfFormXObject} που θα χρησιμοποιηθεί στο footer.
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
            float pageWidth = pageSize.getWidth();

            PdfFont footerFont;
            try {
                // Φόρτωση της βασικής γραμματοσειράς χωρίς italic
                footerFont = PdfFontFactory.createFont(DEFAULT_FONT_RELATIVE_PATH, PdfEncodings.IDENTITY_H, true);
            } catch (IOException e) {
                // Καταγραφή εξαιρέσεων μέσω του Logger
                LOGGER.log(Level.SEVERE, "❌ Σφάλμα κατά τη φόρτωση της γραμματοσειράς στο footer", e);
                return;
            }
            int fontSize = 10;
            // Δημιουργούμε το κείμενο για την τρέχουσα σελίδα
            String footerText = String.format("Σελίδα %d από ", pageNumber);
            // Υπολογισμός του πλάτους του κειμένου βάσει της γραμματοσειράς και του μεγέθους
            float textWidth = footerFont.getWidth(footerText, fontSize);

            // Λήψη του BBox του placeholder ως PdfArray και μετατροπή του σε Rectangle για να ληφθεί το πλάτος
            PdfArray bbox = placeholder.getPdfObject().getAsArray(PdfName.BBox);
            Rectangle bboxRect = new Rectangle(
                    bbox.getAsNumber(0).floatValue(),
                    bbox.getAsNumber(1).floatValue(),
                    bbox.getAsNumber(2).floatValue(),
                    bbox.getAsNumber(3).floatValue()
            );
            float placeholderWidth = bboxRect.getWidth();

            float totalWidth = textWidth + placeholderWidth;
            // Υπολογισμός της αρχικής θέσης x ώστε το συνολικό footer να είναι κεντραρισμένο
            float startX = (pageWidth - totalWidth) / 2;
            // Ορισμός της θέσης y για το footer (προσαρμόστε το όπως απαιτείται)
            float bottomY = pageSize.getBottom() + 15;

            // Δημιουργία παραγράφου για το footer με τις επιθυμητές ρυθμίσεις
            Paragraph footerParagraph = new Paragraph(footerText)
                    .setFont(footerFont)
                    .setFontSize(fontSize)
                    .setFontColor(ColorConstants.GRAY)
                    .setMargin(0);
            PdfCanvas pdfCanvas = new PdfCanvas(
                    docEvent.getPage().newContentStreamAfter(),
                    docEvent.getPage().getResources(),
                    pdfDoc
            );
            // Χρήση try-with-resources για τη δημιουργία του Canvas του footer
            try (Canvas canvasModel = new Canvas(pdfCanvas, pdfDoc, pageSize)) {
                // Απόδοση του κειμένου του footer
                canvasModel.showTextAligned(footerParagraph, startX, bottomY, TextAlignment.LEFT);
                // Απόδοση του placeholder δίπλα στο κείμενο, ώστε να εμφανίζεται το συνολικό πλήθος σελίδων
                canvasModel.add(new Image(placeholder).setFixedPosition(startX + textWidth, bottomY));
            }
        }
    }
}