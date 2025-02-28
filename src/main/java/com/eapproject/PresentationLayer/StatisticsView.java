package com.eapproject.PresentationLayer;

import com.eapproject.DataLayer.DB.University;
import com.eapproject.PresentationLayer.ViewModels.UniversitiesViewModel;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Η κλάση {@code StatisticsView} αποτελεί ένα JPanel που εμφανίζει τα στατιστικά πανεπιστημίων.
 *
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
 *   <li>Font στα δεδομένα: "Arial", bold, 16pt με καφέ σκούρο χρώμα (Color(139, 89, 61)).</li>
 *   <li>Στήλη "No." και "Views": κεντρική στοίχιση, "University Name": αριστερή στοίχιση.</li>
 * </ul>
 * </p>
 *
 * <p>
 * <strong>Παράδειγμα Χρήσης:</strong>
 * <pre>
 *     List&lt;University&gt; stats = UniversityDAO.getInstance().getPopularUniversities();
 *     UniversitiesViewModel viewModel = new UniversitiesViewModel();
 *     StatisticsView statisticsView = new StatisticsView(stats, viewModel);
 *     // Προσθέτουμε το statisticsView στο κύριο παράθυρο της εφαρμογής.
 * </pre>
 * </p>
 */
public class StatisticsView extends JPanel {

    // Λίστα πανεπιστημίων και το αντίστοιχο view model.
    private List<University> stats;
    private UniversitiesViewModel viewModel;

    // Swing components για το UI.
    private JSeparator bottomSeperator;
    private JButton exportButton;
    private JScrollPane jScrollPane1;
    private JPanel mainPanel;
    private JLabel statisticsLabel;
    private JTable statsTable;
    private JSeparator topSeperator;

    /**
     * Δημιουργεί ένα νέο panel για τα στατιστικά.
     *
     * @param statisticsList Η λίστα των πανεπιστημίων που θα εμφανιστούν.
     * @param viewModel      Το view model που σχετίζεται με τα πανεπιστήμια.
     */
    public StatisticsView(List<University> statisticsList, UniversitiesViewModel viewModel) {
        this.stats = statisticsList;
        this.viewModel = viewModel;
        initComponents();
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

        // Δημιουργία των components.
        mainPanel = new JPanel();
        topSeperator = new JSeparator();
        statisticsLabel = new JLabel();
        jScrollPane1 = new JScrollPane();
        statsTable = new JTable();
        bottomSeperator = new JSeparator();
        exportButton = new JButton();

        // Ρυθμίσεις για το mainPanel.
        mainPanel.setBackground(new Color(252, 252, 242));
        mainPanel.setForeground(new Color(252, 252, 242));
        this.setPreferredSize(new Dimension(1050, 960));
        mainPanel.setPreferredSize(new Dimension(1050, 960));

        // Ρυθμίσεις για τον επάνω separator.
        topSeperator.setBackground(new Color(223, 109, 35));
        topSeperator.setForeground(new Color(223, 109, 35));
        topSeperator.setFont(new Font("Segoe UI", Font.PLAIN, 36));

        // Ρυθμίσεις για το label του τίτλου "Statistics".
        statisticsLabel.setBackground(new Color(223, 109, 35));
        statisticsLabel.setFont(new Font("Segoe UI", Font.BOLD, 46));
        statisticsLabel.setForeground(new Color(223, 109, 35));
        statisticsLabel.setText("Statistics");

        // Ρυθμίσεις για τον πίνακα (statsTable).
        statsTable.setBackground(Color.WHITE);
        // Δεν ορίζουμε εσωτερικό περίγραμμα στο JTable, ώστε να εμφανίζεται μόνο το εξωτερικό border του JScrollPane.
        statsTable.setFont(new Font("Arial", Font.BOLD, 16));
        statsTable.setForeground(new Color(139, 89, 61)); // Καφέ σκούρο χρώμα, όπως στην CountryView.
        statsTable.getTableHeader().setReorderingAllowed(false);

        // Ρυθμίσεις εμφάνισης πίνακα:
        statsTable.setShowGrid(false); // Δεν εμφανίζονται εσωτερικά grid lines.
        statsTable.setIntercellSpacing(new Dimension(0, 0));
        // Ρυθμίσεις για τα headers του πίνακα:
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

        // Δημιουργία δεδομένων για τον πίνακα (3 στήλες: "No.", "University Name", "Views").
        Object[][] universitiesData = new Object[stats.size()][3];
        for (int i = 0; i < stats.size(); i++) {
            University uni = stats.get(i);
            universitiesData[i][0] = i + 1;          // Αύξων αριθμός (ξεκινά από 1)
            universitiesData[i][1] = uni.getName();    // Όνομα πανεπιστημίου
            universitiesData[i][2] = uni.getViewCount(); // Προβολές
        }

        // Ονόματα στηλών.
        String[] columnNames = {"No.", "University Name", "Views"};

        // Δημιουργία μοντέλου πίνακα με απαγόρευση επεξεργασίας κελιών.
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

        // Ρύθμιση preferred width για κάθε στήλη.
        statsTable.getColumnModel().getColumn(0).setPreferredWidth(50);  // "No." - μικρό πλάτος.
        statsTable.getColumnModel().getColumn(1).setPreferredWidth(470); // "University Name" - μεγαλύτερο πλάτος.
        statsTable.getColumnModel().getColumn(2).setPreferredWidth(30);  // "Views" - μικρό πλάτος.

        // Custom renderer για τη στήλη "No." (κεντρική στοίχιση, Arial Bold 16pt, καφέ σκούρο).
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

        // Custom renderer για τη στήλη "University Name" (αριστερή στοίχιση, Arial Bold 16pt, καφέ σκούρο).
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

        // Custom renderer για τη στήλη "Views" (κεντρική στοίχιση, Arial Bold 16pt, καφέ σκούρο).
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

        // Ορισμός του πίνακα ως viewport για το JScrollPane.
        jScrollPane1.setViewportView(statsTable);
        // Ορισμός εξωτερικού περιγράμματος και preferred size για το JScrollPane ώστε να ταιριάζει με την CountryView.
        jScrollPane1.setBorder(BorderFactory.createLineBorder(new Color(223, 109, 35), 2));
        jScrollPane1.setPreferredSize(new Dimension(849, 610));

        // Ρυθμίσεις για τον κάτω separator.
        bottomSeperator.setBackground(new Color(223, 109, 35));
        bottomSeperator.setForeground(new Color(223, 109, 35));

        // Ρυθμίσεις για το κουμπί εξαγωγής PDF.
        exportButton.setBackground(new Color(223, 109, 35));
        exportButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        exportButton.setForeground(Color.WHITE);
        exportButton.setText("Export PDF");
        exportButton.setBorder(null);

        // Δημιουργία layout για το mainPanel χρησιμοποιώντας GroupLayout.
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
