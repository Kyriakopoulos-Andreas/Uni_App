package com.eapproject.PresentationLayer;

import com.eapproject.DomainLayer.Models.University;
import com.eapproject.PresentationLayer.ViewModels.UniversitiesViewModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Η κλάση {@code CountryView} αποτελεί ένα JPanel που εμφανίζει τα στατιστικά για τις χώρες,
 * παρουσιάζοντας έναν πίνακα που περιέχει τον αριθμό των πανεπιστημίων ανά χώρα.
 * Επιπλέον, παρέχει λειτουργίες αναζήτησης μέσω ενός πεδίου εισόδου και δυνατότητα
 * διπλού κλικ για εμφάνιση λεπτομερειών για την επιλεγμένη χώρα.
 */
public class CountryView extends JPanel {

    // Ορισμός του Logger για καταγραφή συμβάντων στο αρχείο logs/CountryView.log
    private static final Logger LOGGER = Logger.getLogger(CountryView.class.getName());

    // Λίστες και δεδομένα
    private ArrayList<University> universitiesList;
    private UniversitiesViewModel viewModel;
    private ArrayList<University> universitiesFromSpecificCountry;

    // Αναφορά στο δεξί panel για αλλαγή περιεχομένου
    private final JPanel rightScreenPanel;
    // Μοντέλο και sorter για τον πίνακα
    private DefaultTableModel model;
    private TableRowSorter<DefaultTableModel> sorter;

    // Swing components για το UI
    private JPanel jPanel2;
    private JTextField jTextField3;
    private JPanel mainPanel;
    private JLabel countriesLogo;
    private JSeparator topDiv;
    private JTextField outLinedTextField;
    private JScrollPane jScrollPane3;
    private JTable table;
    private JSeparator bottomDivier;
    private JButton addCountryButton;

    /**
     * Κατασκευαστής της κλάσης CountryView.
     *
     * @param rightScreenJpanel Το panel στο οποίο θα εμφανίζονται τα αποτελέσματα.
     * @param universities      Η λίστα όλων των πανεπιστημίων.
     * @param viewModel         Το ViewModel που διαχειρίζεται τη λογική των πανεπιστημίων.
     */
    public CountryView(JPanel rightScreenJpanel, ArrayList<University> universities, UniversitiesViewModel viewModel) {
        this.rightScreenPanel = rightScreenJpanel;
        this.viewModel = viewModel;
        this.universitiesList = universities;
        initializeLogger();
        initComponents();

        // Αρχικοποίηση του μοντέλου και του sorter για τον πίνακα
        model = (DefaultTableModel) table.getModel();
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        // Ορισμός του preferred size του panel
        this.setPreferredSize(new Dimension(1050, 500));
        LOGGER.info("CountryView initialized with " + universities.size() + " universities.");
    }

    /**
     * Αρχικοποιεί τον Logger ώστε να καταγράφει τα συμβάντα στο αρχείο
     * {@code logs/CountryView.log} με τη χρήση του {@code SimpleFormatter}.
     */
    private void initializeLogger() {
        try {
            // Χρήση try-with-resources δεν είναι απαραίτητη εδώ, όμως γίνεται προσπάθεια δημιουργίας φακέλου logs.
            java.nio.file.Files.createDirectories(java.nio.file.Paths.get("logs"));
            // Αφαίρεση υπαρχόντων handlers για αποφυγή διπλών καταγραφών.
            for (Handler h : LOGGER.getHandlers()) {
                LOGGER.removeHandler(h);
            }
            // Δημιουργία FileHandler για το αρχείο logs/CountryView.log (με append mode)
            FileHandler fileHandler = new FileHandler("logs/CountryView.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);

            LOGGER.setLevel(Level.ALL);
            LOGGER.setUseParentHandlers(false);

            LOGGER.info("📌 Έναρξη καταγραφής του Logger στο logs/CountryView.log");
        } catch (Exception e) {
            System.err.println("❌ Σφάλμα κατά την αρχικοποίηση του Logger: " + e.getMessage());
        }
    }

    /**
     * Αρχικοποιεί και διαμορφώνει όλα τα Swing components του panel.
     * Περιλαμβάνει ρύθμιση πίνακα, πεδίων αναζήτησης και διάταξη με GroupLayout.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {
        // Δημιουργία components για το search panel
        jPanel2 = new JPanel();
        jTextField3 = new JTextField();

        // Δημιουργία των κυρίων components
        mainPanel = new JPanel();
        countriesLogo = new JLabel();
        topDiv = new JSeparator();
        outLinedTextField = new JTextField();
        jScrollPane3 = new JScrollPane();
        table = new JTable();
        bottomDivier = new JSeparator();
        addCountryButton = new JButton();

        // Ορισμός preferred sizes
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(2000, 980));
        mainPanel.setPreferredSize(new Dimension(1300, 960));
        this.revalidate();

        // Ρυθμίσεις για το table header
        table.getTableHeader().setBackground(new Color(223, 109, 35));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Ρυθμίσεις για το table
        table.setShowGrid(false);
        table.setGridColor(new Color(223, 109, 35));
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setBackground(Color.WHITE);
        table.setForeground(Color.BLACK);
        table.setCellSelectionEnabled(true);
        table.setFocusable(false);
        table.setDefaultEditor(Object.class, null);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(false);

        // Ρυθμίσεις για το JScrollPane που περιέχει τον πίνακα
        jScrollPane3.setBorder(BorderFactory.createLineBorder(new Color(223, 109, 35), 2));
        jScrollPane3.getViewport().setBackground(Color.WHITE);

        // Ρυθμίσεις για το panel αναζήτησης (jPanel2)
        jPanel2.setBackground(new Color(252, 252, 242));
        jTextField3.setBackground(new Color(252, 252, 242));
        jTextField3.setHorizontalAlignment(JTextField.CENTER);
        jTextField3.setText("Search Country");
        jTextField3.setBorder(BorderFactory.createLineBorder(new Color(223, 109, 35), 2, true));

        // Ορισμός layout για το jPanel2 με GroupLayout
        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(447, 447, 447)
                                .addComponent(jTextField3, GroupLayout.PREFERRED_SIZE, 524, GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jTextField3, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
        );

        // Προσθήκη MouseListener στο CountryView για μεταβίβαση focus στο outLinedTextField αν δεν έχει γίνει κλικ πάνω του
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                if (!outLinedTextField.getBounds().contains(evt.getPoint())) {
                    outLinedTextField.transferFocus();
                }
            }
        });

        // Προσθήκη FocusListener στο outLinedTextField για διαχείριση placeholder
        outLinedTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (outLinedTextField.getText().isEmpty()) {
                    outLinedTextField.setText("Search Country");
                    outLinedTextField.setForeground(new Color(169, 169, 169));
                }
            }

            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (outLinedTextField.getText().equals("Search Country")) {
                    outLinedTextField.setText("");
                    outLinedTextField.setForeground(new Color(0, 0, 0));
                }
            }
        });

        // Ρυθμίσεις για το mainPanel
        mainPanel.setBackground(new Color(252, 252, 242));
        mainPanel.setForeground(new Color(252, 252, 242));

        // Ρυθμίσεις για το logo (countriesLogo)
        countriesLogo.setBackground(new Color(223, 109, 35));
        countriesLogo.setFont(new Font("Segoe UI", Font.BOLD, 48));
        countriesLogo.setForeground(new Color(223, 109, 35));
        countriesLogo.setText("Countries");

        // Ρυθμίσεις για το πάνω διαχωριστικό (topDiv)
        topDiv.setBackground(new Color(223, 109, 35));
        topDiv.setForeground(new Color(223, 109, 35));

        // Ρυθμίσεις για το outLinedTextField (για αναζήτηση χώρας)
        outLinedTextField.setBackground(Color.WHITE);
        outLinedTextField.setHorizontalAlignment(JTextField.CENTER);
        outLinedTextField.setText("Search Country");
        outLinedTextField.setForeground(new Color(169, 169, 169));
        outLinedTextField.setBorder(BorderFactory.createLineBorder(new Color(223, 109, 35), 2, true));

        // Προσθήκη DocumentListener για δυναμική ενημέρωση φίλτρου στον πίνακα
        outLinedTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateFilter();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateFilter();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateFilter();
            }
        });

        // Δημιουργία χάρτη για μέτρηση πανεπιστημίων ανά χώρα
        Map<String, Integer> countryUniversityCount = new HashMap<>();
        for (University uni : universitiesList) {
            String country = uni.getCountry();
            countryUniversityCount.put(country, countryUniversityCount.getOrDefault(country, 0) + 1);
        }

        // Δημιουργία Object[][] για τον πίνακα με 3 στήλες: "No.", "Country", "Universities"
        Object[][] data = new Object[countryUniversityCount.size()][3];
        int rowIndex = 0;
        for (Map.Entry<String, Integer> entry : countryUniversityCount.entrySet()) {
            data[rowIndex][0] = rowIndex + 1; // Αύξων αριθμός
            data[rowIndex][1] = entry.getKey(); // Χώρα
            data[rowIndex][2] = entry.getValue(); // Αριθμός πανεπιστημίων
            rowIndex++;
        }

        // Δημιουργία μοντέλου πίνακα με απαγόρευση επεξεργασίας κελιών
        DefaultTableModel tableModel = new DefaultTableModel(data, new String[]{"No.", "Country", "Universities"}) {
            final boolean[] canEdit = new boolean[]{false, false, false};

            @Override
            public boolean isCellEditable(int row, int column) {
                return canEdit[column];
            }
        };
        table.setModel(tableModel);
        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(true);

        // Ρυθμίσεις για την κεφαλίδα του πίνακα
        table.getTableHeader().getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getTableHeader().getColumnModel().getColumn(1).setPreferredWidth(400);
        table.getTableHeader().getColumnModel().getColumn(2).setPreferredWidth(50);
        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setReorderingAllowed(false);

        // Εφαρμογή custom renderer για κεντρική στοίχιση και ρύθμιση γραμματοσειράς για όλα τα κελιά
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        centerRenderer.setFont(new Font("Arial", Font.BOLD, 28));
        centerRenderer.setForeground(new Color(139, 89, 61));
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        table.setSelectionBackground(new Color(187, 124, 82, 200));
        table.setSelectionForeground(Color.WHITE);
        table.setRowHeight(40);
        table.setFont(new Font("Arial", Font.BOLD, 16));

        // Ορισμός του πίνακα ως viewport για το JScrollPane
        jScrollPane3.setViewportView(table);
        jScrollPane3.setBackground(Color.WHITE);

        // Ρυθμίσεις για το κάτω διαχωριστικό (bottomDivier)
        bottomDivier.setBackground(new Color(223, 109, 35));
        bottomDivier.setForeground(new Color(223, 109, 35));

        // Ρυθμίσεις για το κουμπί "Add Country"
        addCountryButton.setBackground(new Color(223, 109, 35));
        addCountryButton.setForeground(Color.WHITE);
        addCountryButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addCountryButton.setText("Add Country");
        addCountryButton.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));

        // Προσθήκη MouseListener στον πίνακα για διπλό κλικ, ώστε να φορτώνονται τα πανεπιστήμια για την επιλεγμένη χώρα
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int row = table.getSelectedRow();
                    if (row != -1) {
                        String country = table.getValueAt(row, 1).toString();
                        // Κλήση του ViewModel για φόρτωση πανεπιστημίων για τη συγκεκριμένη χώρα
                        viewModel.fetchUniversitiesFromSpecificCountry(country, universitiesList);
                        universitiesFromSpecificCountry = (ArrayList<University>) viewModel.getUniversitiesFromSpecificCountry();
                        LOGGER.info("Διπλό κλικ στη χώρα: " + country + " - Πανεπιστήμια: " + universitiesFromSpecificCountry);
                        // Ενημέρωση του δεξιού panel για εμφάνιση των πανεπιστημίων της χώρας
                        rightScreenPanel.removeAll();
                        rightScreenPanel.add(new CountryUniversities(country, rightScreenPanel, universitiesFromSpecificCountry, viewModel, universitiesList), "CountryUniversities");
                        rightScreenPanel.revalidate();
                        rightScreenPanel.repaint();
                        ((CardLayout) rightScreenPanel.getLayout()).show(rightScreenPanel, "CountryUniversities");
                    }
                }
            }
        });
        jScrollPane3.setBackground(Color.WHITE);

        // Δημιουργία layout για το mainPanel με χρήση GroupLayout
        GroupLayout mainPanelLayout = new GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
                mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(topDiv)
                        .addComponent(bottomDivier)
                        .addGroup(GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                                .addContainerGap(208, Short.MAX_VALUE)
                                .addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                                                .addComponent(jScrollPane3, GroupLayout.PREFERRED_SIZE, 888, GroupLayout.PREFERRED_SIZE)
                                                .addGap(85, 85, 85))
                                        .addGroup(GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                                                .addComponent(outLinedTextField, GroupLayout.PREFERRED_SIZE, 482, GroupLayout.PREFERRED_SIZE)
                                                .addGap(291, 291, 291))
                                        .addGroup(GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                                                .addComponent(addCountryButton, GroupLayout.PREFERRED_SIZE, 229, GroupLayout.PREFERRED_SIZE)
                                                .addGap(415, 415, 415))
                                        .addGroup(GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                                                .addComponent(countriesLogo)
                                                .addGap(427, 427, 427)))
                        )
                    );
        mainPanelLayout.setVerticalGroup(
                mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(countriesLogo)
                                .addGap(11, 11, 11)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(topDiv, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(outLinedTextField, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane3, GroupLayout.PREFERRED_SIZE, 650, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                                .addComponent(bottomDivier, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(addCountryButton, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40))
        );

        // Προσθήκη του mainPanel στο CountryView
        this.add(mainPanel);
    }

    /**
     * Ενημερώνει το φίλτρο του πίνακα βάσει του κειμένου στο outLinedTextField.
     */
    private void updateFilter() {
        String text = outLinedTextField.getText().trim().toLowerCase();
        if (text.isEmpty() || text.equals("search country")) {
            sorter.setRowFilter(null);
            LOGGER.info("Αφαιρέθηκε το φίλτρο στον πίνακα.");
        } else {
            sorter.setRowFilter(new RowFilter<DefaultTableModel, Integer>() {
                @Override
                public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                    String country = entry.getStringValue(1).toLowerCase();
                    return country.startsWith(text);
                }
            });
            LOGGER.info("Εφαρμόστηκε φίλτρο στον πίνακα: " + text);
        }
    }
}
