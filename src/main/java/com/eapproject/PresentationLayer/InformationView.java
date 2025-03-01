package com.eapproject.PresentationLayer;

import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Η κλάση {@code InformationView} αποτελεί ένα JPanel που εμφανίζει πληροφορίες σχετικά με την εφαρμογή.
 * <p>
 * Το panel περιέχει έναν τίτλο "Information", ένα TextArea με περιγραφή της εφαρμογής και διαχωριστικά (separators)
 * για ομαλή διάταξη των στοιχείων.
 * </p>
 */
public class InformationView extends JPanel {

    // Ορισμός του Logger για καταγραφή συμβάντων στο αρχείο logs/InformationView.log
    private static final Logger LOGGER = Logger.getLogger(InformationView.class.getName());

    // Swing components για την εμφάνιση του UI
    private JPanel mainPanel;
    private JSeparator topSeperator;
    private JLabel informationLabel;
    private JScrollPane jScrollPane1;
    private JTextArea TextArea;
    private JSeparator jSeparator1;

    /**
     * Κατασκευαστής της κλάσης InformationView.
     * Αρχικοποιεί τον Logger και τα UI components.
     */
    public InformationView() {
        initializeLogger();
        initComponents();
    }

    /**
     * Αρχικοποιεί τον Logger ώστε να καταγράφει τα συμβάντα στο αρχείο
     * {@code logs/InformationView.log} με τη χρήση του {@code SimpleFormatter}.
     */
    private void initializeLogger() {
        try {
            // Δημιουργία φακέλου logs, εάν δεν υπάρχει.
            Files.createDirectories(Paths.get("logs"));
            // Αφαίρεση τυχόν υπαρχόντων handlers για αποφυγή διπλών καταγραφών.
            for (Handler h : LOGGER.getHandlers()) {
                LOGGER.removeHandler(h);
            }
            // Δημιουργία FileHandler για το αρχείο logs/InformationView.log (με append mode).
            FileHandler fileHandler = new FileHandler("logs/InformationView.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);

            LOGGER.setLevel(Level.ALL);
            LOGGER.setUseParentHandlers(false);

            LOGGER.info("📌 Έναρξη καταγραφής του Logger στο logs/InformationView.log");
        } catch (IOException e) {
            System.err.println("❌ Σφάλμα κατά την αρχικοποίηση του Logger: " + e.getMessage());
        }
    }

    /**
     * Αρχικοποιεί και διαμορφώνει όλα τα Swing components του panel.
     * <p>
     * Το panel περιέχει:
     * <ul>
     *   <li>Έναν τίτλο "Information" με συγκεκριμένο font και χρώμα.</li>
     *   <li>Ένα TextArea που εμφανίζει περιγραφή της εφαρμογής με γραμματοσειρά "Segoe UI", 28pt.</li>
     *   <li>Διαχωριστικά (separators) για ομαλή διάταξη των στοιχείων.</li>
     * </ul>
     * </p>
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {
        // Δημιουργία των components
        mainPanel = new JPanel();
        topSeperator = new JSeparator();
        informationLabel = new JLabel();
        jScrollPane1 = new JScrollPane();
        TextArea = new JTextArea();
        jSeparator1 = new JSeparator();

        // Ρυθμίσεις για το mainPanel
        mainPanel.setBackground(new Color(252, 252, 242));
        this.setPreferredSize(new Dimension(1050, 980));
        mainPanel.setPreferredSize(new Dimension(1050, 960));

        // Ρυθμίσεις για το topSeperator
        topSeperator.setBackground(new Color(223, 109, 35));
        topSeperator.setForeground(new Color(223, 109, 35));
        topSeperator.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        // Ρυθμίσεις για το label "Information"
        informationLabel.setBackground(new Color(223, 109, 35));
        informationLabel.setFont(new Font("Segoe UI", Font.BOLD, 42));
        informationLabel.setForeground(new Color(223, 109, 35));
        informationLabel.setText("Information");

        // Ρυθμίσεις για το TextArea
        TextArea.setBackground(Color.WHITE);
        TextArea.setColumns(20);
        TextArea.setForeground(new Color(96, 59, 6));
        TextArea.setRows(5);
        TextArea.setBorder(new MatteBorder(3, 3, 3, 3, new Color(223, 109, 35)));
        TextArea.setSelectedTextColor(new Color(178, 112, 69));
        TextArea.setSelectionColor(Color.WHITE);
        TextArea.setLineWrap(true);
        TextArea.setFont(new Font("Segoe UI", Font.PLAIN, 28));
        TextArea.setWrapStyleWord(true);
        TextArea.setEditable(false);
        // Ορισμός του κειμένου περιγραφής
        TextArea.setText("This application provides detailed information about universities worldwide. "
                + "Users can explore universities by selecting a country and viewing a list of institutions, "
                + "or they can search for a specific university directly. Each university profile includes essential "
                + "details such as location, ranking, available programs, and admission requirements. "
                + "The intuitive interface ensures easy navigation, making it a valuable resource for students, "
                + "researchers, and anyone interested in higher education. Additionally, users can compare universities, "
                + "save their favorite institutions, and access up-to-date insights on academic offerings and scholarships.");

        // Ορισμός του TextArea ως viewport για το JScrollPane
        jScrollPane1.setViewportView(TextArea);

        // Ρυθμίσεις για το δεύτερο separator
        jSeparator1.setBackground(new Color(223, 109, 35));
        jSeparator1.setForeground(new Color(223, 109, 35));

        // Δημιουργία του layout για το mainPanel χρησιμοποιώντας GroupLayout
        GroupLayout mainPanelLayout = new GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(topSeperator, GroupLayout.Alignment.TRAILING)
                .addGroup(mainPanelLayout.createSequentialGroup()
                    .addGap(78, 78, 78)
                    .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 880, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addComponent(jSeparator1)
                .addGroup(GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                    .addContainerGap(390, Short.MAX_VALUE)
                    .addComponent(informationLabel, GroupLayout.PREFERRED_SIZE, 269, GroupLayout.PREFERRED_SIZE)
                    .addGap(391, 391, 391))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(mainPanelLayout.createSequentialGroup()
                    .addGap(15, 15, 15)
                    .addComponent(informationLabel)
                    .addGap(15, 15, 15)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(topSeperator, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                    .addGap(40, 40, 40)
                    .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 660, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                    .addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                    .addGap(94, 94, 94))
        );

        // Προσθήκη του mainPanel στο InformationView
        this.add(mainPanel);
    }
}
