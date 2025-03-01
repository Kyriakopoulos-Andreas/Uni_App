package com.eapproject.PresentationLayer;

import com.eapproject.DataLayer.DB.University;
import com.eapproject.DomainLayer.UseCase.PDFExporter;
import com.eapproject.PresentationLayer.ViewModels.UniversitiesViewModel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Η κλάση {@code StatisticsView} αποτελεί ένα JPanel που εμφανίζει τα στατιστικά πανεπιστημίων.
 * <p>
 * Η κλάση δέχεται μια λίστα αντικειμένων {@link University} και ένα ViewModel (UniversitiesViewModel) ώστε να παρουσιάζει τα δεδομένα
 * με μορφή πίνακα, παρέχοντας επιλογές όπως η εξαγωγή σε PDF. Ο πίνακας εμφανίζει τρεις στήλες: "No." (αύξων αριθμός),
 * "University Name" και "Views".
 * </p>
 *
 * <p>
 * Ο σχεδιασμός περιλαμβάνει έναν πίνακα με ομοιόμορφη μορφοποίηση:
 * <ul>
 *   <li>Preferred size του JScrollPane: 849 x 610.</li>
 *   <li>Εξωτερικό περίγραμμα (2px, πορτοκαλί).</li>
 *   <li>Font στα δεδομένα: "Arial", bold, 16pt με καφέ σκούρο χρώμα (Color(139,89,61)).</li>
 *   <li>Στήλες "No." και "Views": κεντρική στοίχιση, "University Name": αριστερή στοίχιση.</li>
 * </ul>
 * 
 *
 * <p>
 * <strong>Παράδειγμα Χρήσης:</strong>
 * <pre>
 *     List&lt;University&gt; stats = UniversityDAO.getInstance().getPopularUniversities();
 *     UniversitiesViewModel viewModel = new UniversitiesViewModel();
 *     StatisticsView statisticsView = new StatisticsView(stats, viewModel);
 *     // Προσθέτουμε το statisticsView στο κύριο παράθυρο της εφαρμογής.
 * </pre>
 * 
 */
public class StatisticsView extends JPanel {

    // Ορισμός του Logger για καταγραφή συμβάντων στο αρχείο logs/StatisticsView.log
    private static final Logger LOGGER = Logger.getLogger(StatisticsView.class.getName());

    // Λίστα πανεπιστημίων και το αντίστοιχο view model
    private List<University> stats;
    private UniversitiesViewModel viewModel;

    // Swing components για το UI
    private JPanel mainPanel;
    private JLabel statisticsLabel;
    private JTable statsTable;
    private JScrollPane jScrollPane1;
    private JButton exportButton;
    private JSeparator topSeperator;
    private JSeparator bottomSeperator;

    /**
     * Δημιουργεί ένα νέο panel για τα στατιστικά.
     *
     * @param statisticsList Η λίστα των πανεπιστημίων που θα εμφανιστούν.
     * @param viewModel      Το view model που σχετίζεται με τα πανεπιστήμια.
     */
    public StatisticsView(List<University> statisticsList, UniversitiesViewModel viewModel) {
        // Αρχικοποίηση του Logger για αυτή την κλάση.
        initializeLogger();
        this.stats = statisticsList;
        this.viewModel = viewModel;
        initComponents();
    }

    /**
     * Αρχικοποιεί τον Logger ώστε να καταγράφει τα συμβάντα στο αρχείο
     * {@code logs/StatisticsView.log} με τη χρήση του {@code SimpleFormatter}.
     */
    private void initializeLogger() {
        try {
            // Δημιουργία φακέλου logs αν δεν υπάρχει.
            Files.createDirectories(Paths.get("logs"));
            // Αφαίρεση τυχόν υπαρχόντων handlers για αποφυγή διπλών καταγραφών.
            for (Handler h : LOGGER.getHandlers()) {
                LOGGER.removeHandler(h);
            }
            // Δημιουργία FileHandler για το αρχείο logs/StatisticsView.log (με append mode).
            FileHandler fileHandler = new FileHandler("logs/StatisticsView.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);

            LOGGER.setLevel(Level.ALL);
            LOGGER.setUseParentHandlers(false);

            LOGGER.info("📌 Έναρξη καταγραφής του Logger στο logs/StatisticsView.log");
        } catch (IOException e) {
            System.err.println("❌ Σφάλμα κατά την αρχικοποίηση του Logger: " + e.getMessage());
        }
    }

    /**
     * Αρχικοποιεί και διαμορφώνει όλα τα Swing components του panel.
     *
     * <p>
     * Ο πίνακας δημιουργείται με τρεις στήλες: "No.", "University Name" και "Views".
     * Οι στήλες διαμορφώνονται ως εξής:
     * <ul>
     *   <li>"No.": Preferred width 50, κεντρική στοίχιση, font "Arial" Bold 16pt, χρώμα (139,89,61).</li>
     *   <li>"University Name": Preferred width 470, αριστερή στοίχιση, font "Arial" Bold 16pt, χρώμα (139,89,61).</li>
     *   <li>"Views": Preferred width 30, κεντρική στοίχιση, font "Arial" Bold 16pt, χρώμα (139,89,61).</li>
     * </ul>
     * Επιπλέον, το JScrollPane που περιέχει τον πίνακα έχει preferred size 849×610 και εξωτερικό περίγραμμα πορτοκαλί 2px.
     * </p>
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {
        // Δημιουργία των κύριων components
        mainPanel = new JPanel();
        topSeperator = new JSeparator();
        statisticsLabel = new JLabel();
        jScrollPane1 = new JScrollPane();
        statsTable = new JTable();
        bottomSeperator = new JSeparator();
        exportButton = new JButton();

        // Ρυθμίσεις για το mainPanel
        mainPanel.setBackground(new Color(252, 252, 242));
        mainPanel.setPreferredSize(new Dimension(1050, 960));

        // Ρυθμίσεις για τον επάνω separator
        topSeperator.setBackground(new Color(223, 109, 35));
        topSeperator.setForeground(new Color(223, 109, 35));
        topSeperator.setFont(new Font("Segoe UI", Font.PLAIN, 36));

        // Ρυθμίσεις για το label "Statistics"
        statisticsLabel.setBackground(new Color(223, 109, 35));
        statisticsLabel.setFont(new Font("Segoe UI", Font.BOLD, 46));
        statisticsLabel.setForeground(new Color(223, 109, 35));
        statisticsLabel.setText("Statistics");

        // Ρυθμίσεις για τον πίνακα statsTable
        statsTable.setBackground(Color.WHITE);
        statsTable.setFont(new Font("Arial", Font.BOLD, 16));
        statsTable.setForeground(new Color(139, 89, 61));
        statsTable.getTableHeader().setReorderingAllowed(false);
        statsTable.setShowGrid(false);
        statsTable.setIntercellSpacing(new Dimension(0, 0));
        statsTable.getTableHeader().setBackground(new Color(223, 109, 35));
        statsTable.getTableHeader().setForeground(Color.WHITE);
        statsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        statsTable.setCellSelectionEnabled(true);
        statsTable.setFocusable(false);
        statsTable.setDefaultEditor(Object.class, null);
        statsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        statsTable.setRowSelectionAllowed(true);
        statsTable.setColumnSelectionAllowed(false);
        statsTable.setSelectionBackground(new Color(187, 124, 82, 200));
        statsTable.setSelectionForeground(Color.WHITE);
        statsTable.setRowHeight(40);

        // Δημιουργία των δεδομένων για τον πίνακα από τη λίστα των πανεπιστημίων
        Object[][] universitiesData = new Object[stats.size()][3];
        for (int i = 0; i < stats.size(); i++) {
            University uni = stats.get(i);
            universitiesData[i][0] = i + 1;                // Αύξων αριθμός (ξεκινά από 1)
            universitiesData[i][1] = uni.getName();          // Όνομα πανεπιστημίου
            universitiesData[i][2] = uni.getViewCount();     // Αριθμός προβολών
        }
        // Ονόματα στηλών
        String[] columnNames = {"No.", "University Name", "Views"};

        // Δημιουργία του μοντέλου του πίνακα με απαγόρευση επεξεργασίας κελιών
        DefaultTableModel model = new DefaultTableModel(universitiesData, columnNames) {
            final boolean[] canEdit = new boolean[]{false, false, false};

            @Override
            public boolean isCellEditable(int row, int column) {
                return canEdit[column];
            }
        };
        statsTable.setModel(model);
        statsTable.setFillsViewportHeight(true);
        statsTable.setAutoCreateRowSorter(true);
        statsTable.getTableHeader().setResizingAllowed(false);
        statsTable.getTableHeader().setReorderingAllowed(false);

        // Ορισμός preferred width για κάθε στήλη
        statsTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        statsTable.getColumnModel().getColumn(1).setPreferredWidth(470);
        statsTable.getColumnModel().getColumn(2).setPreferredWidth(30);

        // Custom renderer για τη στήλη "No." (κεντρική στοίχιση)
        DefaultTableCellRenderer indexRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);
                label.setFont(new Font("Arial", Font.BOLD, 16));
                label.setForeground(new Color(139, 89, 61));
                label.setHorizontalAlignment(SwingConstants.CENTER);
                return label;
            }
        };
        statsTable.getColumnModel().getColumn(0).setCellRenderer(indexRenderer);

        // Custom renderer για τη στήλη "University Name" (αριστερή στοίχιση)
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);
                label.setFont(new Font("Arial", Font.BOLD, 16));
                label.setForeground(new Color(139, 89, 61));
                label.setHorizontalAlignment(SwingConstants.LEFT);
                return label;
            }
        };
        statsTable.getColumnModel().getColumn(1).setCellRenderer(leftRenderer);

        // Custom renderer για τη στήλη "Views" (κεντρική στοίχιση)
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);
                label.setFont(new Font("Arial", Font.BOLD, 16));
                label.setForeground(new Color(139, 89, 61));
                label.setHorizontalAlignment(SwingConstants.CENTER);
                return label;
            }
        };
        statsTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

        // Ορισμός του πίνακα ως viewport για το JScrollPane και ρύθμιση εξωτερικού περιγράμματος
        jScrollPane1.setViewportView(statsTable);
        jScrollPane1.setBorder(new LineBorder(new Color(223, 109, 35), 2));
        jScrollPane1.setPreferredSize(new Dimension(849, 610));

        // Ρυθμίσεις για τον κάτω separator
        bottomSeperator.setBackground(new Color(223, 109, 35));
        bottomSeperator.setForeground(new Color(223, 109, 35));

        // Ρυθμίσεις για το κουμπί εξαγωγής PDF
        exportButton.setBackground(new Color(223, 109, 35));
        exportButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        exportButton.setForeground(Color.WHITE);
        exportButton.setText("Export PDF");
        exportButton.setBorder(null);

        // Προσθήκη ActionListener στο κουμπί εξαγωγής PDF με διαχείριση εξαιρέσεων
        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    boolean success = PDFExporter.exportStatisticsToPDF(stats);
                    if (success) {
                        JOptionPane.showMessageDialog(null, "Το PDF εξήχθη επιτυχώς!");
                        LOGGER.info("ℹ️ Το PDF εξήχθη επιτυχώς.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Δεν υπάρχουν διαθέσιμα στατιστικά για εξαγωγή.");
                        LOGGER.warning("⚠️ Δεν υπάρχουν διαθέσιμα στατιστικά για εξαγωγή.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Σφάλμα εξαγωγής PDF: " + ex.getMessage(),
                            "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                    LOGGER.log(Level.SEVERE, "❌ Σφάλμα εξαγωγής PDF", ex);
                }
            }
        });

        // Δημιουργία layout για το mainPanel χρησιμοποιώντας GroupLayout
        GroupLayout mainPanelLayout = new GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(topSeperator, GroupLayout.Alignment.TRAILING)
                .addComponent(bottomSeperator)
                .addGroup(GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                    .addContainerGap(500, Short.MAX_VALUE)
                    .addGap(100)
                    .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 849, GroupLayout.PREFERRED_SIZE)
                    .addGap(420, 420, 420))
                .addGroup(mainPanelLayout.createSequentialGroup()
                    .addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(mainPanelLayout.createSequentialGroup()
                            .addGap(420, 420, 420)
                            .addComponent(statisticsLabel))
                        .addGroup(mainPanelLayout.createSequentialGroup()
                            .addGap(416, 416, 416)
                            .addComponent(exportButton, GroupLayout.PREFERRED_SIZE, 229, GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(mainPanelLayout.createSequentialGroup()
                    .addGap(12, 12, 12)
                    .addComponent(statisticsLabel)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGap(13)
                    .addComponent(topSeperator, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                    .addGap(58)
                    .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 610, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
                    .addComponent(bottomSeperator, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                    .addGap(15, 15, 15)
                    .addComponent(exportButton, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
                    .addGap(40))
        );

        // Προσθήκη του mainPanel στο StatisticsView.
        this.add(mainPanel);
    }
}
