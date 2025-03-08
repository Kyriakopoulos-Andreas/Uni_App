package com.eapproject.PresentationLayer;

import com.eapproject.DomainLayer.Models.University;
import com.eapproject.PresentationLayer.ViewModels.UniversitiesViewModel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

/**
 * Η κλάση {@code CountryUniversities} αντιπροσωπεύει ένα JPanel που εμφανίζει
 * τα πανεπιστήμια μιας συγκεκριμένης χώρας και παρέχει δυνατότητα αναζήτησης.
 * <p>
 * Επιτρέπει επίσης, μέσω διπλού κλικ σε μία εγγραφή του πίνακα, την εμφάνιση
 * αναλυτικών στοιχείων του επιλεγμένου πανεπιστημίου.
 * </p>
 */
public class CountryUniversitiesView extends JPanel {

    // Σταθερό Logger για την καταγραφή συμβάντων, με μηνύματα σε μορφή emoji
    private static final Logger LOGGER = Logger.getLogger(CountryUniversitiesView.class.getName());

    // Μεταβλητές για τη διαχείριση του UI και των δεδομένων
    private String countryLogo;                      // Το όνομα ή λογότυπο της χώρας
    private JPanel rightScreenPanel;                 // Panel στο οποίο εμφανίζονται λεπτομέρειες
    private DefaultTableModel model;                 // Το μοντέλο δεδομένων για τον πίνακα
    private TableRowSorter<DefaultTableModel> sorter;// Χειρισμός φιλτραρίσματος στον πίνακα
    private ArrayList<University> universitiesFromSpecificCountry; // Λίστα πανεπιστημίων για τη συγκεκριμένη χώρα
    private UniversitiesViewModel viewModel;         // Το ViewModel για την επιχειρησιακή λογική
    private List<University> allUniversities;        // Όλοι οι πιθανοί πίνακες πανεπιστημίων

    // Στοιχεία του Swing που δηλώνονται στη φόρμα
    private JPanel todoCountryPanel;
    private JPanel mainPanel;
    private JLabel todoCountry;
    private JSeparator jSeparator1;
    private JSeparator jSeparator2;
    private JTextField outLinedTextField;
    private JScrollPane jScrollPane1;
    private JTable table;
    private JButton addUniversityButton;

    /**
     * Κατασκευαστής της κλάσης {@code CountryUniversities}.
     *
     * @param countryLogo           Το λογότυπο ή όνομα της χώρας που παρουσιάζεται.
     * @param rightScreenPanel      To panel όπου θα εμφανιστούν οι λεπτομέρειες (UniversityView).
     * @param universities          Η λίστα των πανεπιστημίων της συγκεκριμένης χώρας.
     * @param viewModel             Το ViewModel που διαχειρίζεται τη λογική για τα πανεπιστήμια.
     * @param allUniversities       Η πλήρης λίστα πανεπιστημίων που χρησιμοποιείται στην εφαρμογή.
     */
    public CountryUniversitiesView(String countryLogo, JPanel rightScreenPanel,
                                   ArrayList<University> universities,
                                   UniversitiesViewModel viewModel,
                                   List<University> allUniversities) {
        // Ενημέρωση των πεδίων της κλάσης
        this.rightScreenPanel = rightScreenPanel;
        this.allUniversities = allUniversities;
        this.countryLogo = countryLogo;
        this.viewModel = viewModel;
        this.universitiesFromSpecificCountry = universities;

        // Κλήση της μεθόδου αρχικοποίησης των components
        try {
            initComponents();
            LOGGER.info("📌 CountryUniversities UI components initialized for country: " + countryLogo);
        } catch (Exception ex) {
            // Εάν παρουσιαστεί σφάλμα κατά την αρχικοποίηση, το καταγράφουμε
            LOGGER.log(Level.SEVERE, "❌ Σφάλμα κατά την αρχικοποίηση του UI στο CountryUniversities.", ex);
        }

        // Ετοιμάζουμε το μοντέλο και τον sorter για τον πίνακα
        try {
            model = (DefaultTableModel) table.getModel();
            sorter = new TableRowSorter<>(model);
            table.setRowSorter(sorter);
            LOGGER.info("ℹ️ Table model & sorter initialized successfully.");
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "⚠️ Σφάλμα κατά την προετοιμασία του πίνακα.", ex);
        }
    }

    /**
     * Αρχικοποιεί και διαμορφώνει όλα τα Swing components του panel.
     * <p>
     * Χρησιμοποιεί σύστημα GroupLayout για την τακτοποίηση των στοιχείων
     * και ορίζει συμπεριφορές για την αναζήτηση καθώς και τον πίνακα.
     * </p>
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {
        // Δημιουργία των κύριων UI components
        todoCountryPanel = new JPanel();
        mainPanel = new JPanel();
        todoCountry = new JLabel();
        jSeparator1 = new JSeparator();
        outLinedTextField = new JTextField();
        jScrollPane1 = new JScrollPane();
        table = new JTable();
        jSeparator2 = new JSeparator();
        addUniversityButton = new JButton();

        // Ρυθμίσεις του mainPanel
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        mainPanel.setBackground(new Color(252, 252, 242));
        mainPanel.setForeground(new Color(255, 255, 255));
        this.setLayout(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(1200, 1000));
        this.setPreferredSize(new Dimension(1200, 1000));
        this.revalidate(); // Διασφάλιση ότι ενημερώνεται το layout

        // Ρυθμίσεις για την ετικέτα που παρουσιάζει τη χώρα (todoCountry)
        todoCountry.setBackground(new Color(223, 109, 35));
        todoCountry.setFont(new Font("Segoe UI", Font.BOLD, 42));
        todoCountry.setForeground(new Color(223, 109, 35));
        todoCountry.setText(countryLogo);
        todoCountry.setPreferredSize(new Dimension(400, 60));
        todoCountry.setHorizontalAlignment(SwingConstants.CENTER);

        // Panel για την ετικέτα της χώρας
        todoCountryPanel.add(todoCountry);
        todoCountryPanel.setBackground(new Color(252, 252, 242));

        // Διαχωριστικό 1 (επάνω)
        jSeparator1.setBackground(new Color(223, 109, 35));
        jSeparator1.setForeground(new Color(223, 109, 35));

        // Ρυθμίσεις για το πεδίο αναζήτησης (outLinedTextField)
        outLinedTextField.setBackground(new Color(255, 255, 255));
        outLinedTextField.setForeground(new Color(153, 153, 153));
        outLinedTextField.setHorizontalAlignment(JTextField.CENTER);
        outLinedTextField.setText("Search University");
        outLinedTextField.setBorder(new LineBorder(new Color(223, 109, 35), 2, true));
        outLinedTextField.addActionListener(evt -> jTextField1ActionPerformed(evt));

        // Focus listener για το outLinedTextField - placeholder
        outLinedTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (outLinedTextField.getText().isEmpty()) {
                    outLinedTextField.setText("Search University");
                    outLinedTextField.setForeground(new Color(169, 169, 169));
                }
            }

            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (outLinedTextField.getText().equals("Search University")) {
                    outLinedTextField.setText("");
                    outLinedTextField.setForeground(new Color(0, 0, 0));
                }
            }
        });

        // Listeners για αλλαγές στο πεδίο αναζήτησης (DocumentListener)
        outLinedTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { updateFilter(); }
            @Override
            public void removeUpdate(DocumentEvent e) { updateFilter(); }
            @Override
            public void changedUpdate(DocumentEvent e) { updateFilter(); }
        });

        // MouseListener για το panel - αν δεν κλικάρουμε στο outLinedTextField, χάνεται το focus
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                if (!outLinedTextField.getBounds().contains(evt.getPoint())) {
                    outLinedTextField.transferFocus();
                }
            }
        });

        // Προετοιμασία των δεδομένων στον πίνακα
        Object[][] universitiesData = new Object[universitiesFromSpecificCountry.size()][2];
        for (int i = 0; i < universitiesFromSpecificCountry.size(); i++) {
            universitiesData[i][0] = String.valueOf(i + 1);  // Αύξων αριθμός
            universitiesData[i][1] = universitiesFromSpecificCountry.get(i).getName(); // Όνομα Πανεπιστημίου
        }

        // Ορισμός των τίτλων των στηλών
        String[] columnNames = {"No.", "University Name"};
        // Δημιουργούμε το model και το δίνουμε στον πίνακα
        DefaultTableModel model = new DefaultTableModel(universitiesData, columnNames);
        table.setModel(model);

        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setBackground(new Color(223, 109, 35));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        // MouseListener για διπλό κλικ στον πίνακα
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                // Έλεγχος αν είναι διπλό κλικ
                if (evt.getClickCount() == 2) {
                    int row = table.getSelectedRow();
                    if (row != -1) {
                        // Λαμβάνουμε το όνομα του πανεπιστημίου από τη στήλη 1
                        String university = table.getValueAt(row, 1).toString();
                        viewModel.getUniversityFromList(university, universitiesFromSpecificCountry);

                        // Εμφάνιση νέου panel με λεπτομέρειες για το πανεπιστήμιο
                        rightScreenPanel.removeAll();
                        rightScreenPanel.add(new UniversityView(viewModel.getUniversityFromList(), viewModel, allUniversities), "CountryUniversities");
                        rightScreenPanel.revalidate();
                        rightScreenPanel.repaint();
                        ((CardLayout) rightScreenPanel.getLayout()).show(rightScreenPanel, "CountryUniversities");
                    }
                }
            }
        });

        // Διαμορφώσεις για τον πίνακα
        table.setShowGrid(false);
        table.setGridColor(new Color(223, 109, 35));
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setBackground(Color.WHITE);
        table.setForeground(Color.BLACK);

        // Γραμμή πλαισίου στο scrollPane
        jScrollPane1.setBorder(BorderFactory.createLineBorder(new Color(223, 109, 35), 2));

        table.setCellSelectionEnabled(true);
        table.setFocusable(false);
        table.setDefaultEditor(Object.class, null);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(false);

        // Ρυθμίσεις γραμματοσειράς και στοίχισης για τα κελιά
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
        table.setPreferredSize(new Dimension(800, table.getPreferredSize().height));

        table.getColumnModel().getColumn(0).setPreferredWidth(5);
        table.getColumnModel().getColumn(1).setPreferredWidth(700);
        table.getTableHeader().getColumnModel().getColumn(0).setPreferredWidth(5);
        table.getTableHeader().getColumnModel().getColumn(1).setPreferredWidth(700);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        table.setFont(new Font("Arial", Font.BOLD, 16));
        jScrollPane1.setViewportView(table);

        jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Διαχωριστικό 2 (κάτω)
        jSeparator2.setBackground(new Color(223, 109, 35));
        jSeparator2.setForeground(new Color(223, 109, 35));

        // Κουμπί για να προσθέσουμε πανεπιστήμιο (Dummy λειτουργία)
        addUniversityButton.setBackground(new Color(223, 109, 35));
        addUniversityButton.setForeground(Color.WHITE);
        addUniversityButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addUniversityButton.setText("Add University");
        addUniversityButton.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));

        // Δημιουργία GroupLayout για το mainPanel
        GroupLayout mainPanelLayout = new GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
                mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jSeparator1)
                        .addComponent(jSeparator2)
                        .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(mainPanelLayout.createSequentialGroup()
                                                .addGap(1, 1, 1)
                                                .addComponent(todoCountryPanel, GroupLayout.PREFERRED_SIZE, 1043, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(mainPanelLayout.createSequentialGroup()
                                                .addGap(287, 287, 287)
                                                .addComponent(outLinedTextField, GroupLayout.PREFERRED_SIZE, 482, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(mainPanelLayout.createSequentialGroup()
                                                .addGap(100, 100, 100)
                                                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 822, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(180, Short.MAX_VALUE))
                        .addGroup(GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(addUniversityButton, GroupLayout.PREFERRED_SIZE, 229, GroupLayout.PREFERRED_SIZE)
                                .addGap(565, 565, 565))
        );

        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(mainPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGap(10, 10, 10)
                    .addComponent(todoCountryPanel, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
                    .addGap(12, 12, 12)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                    .addGap(6, 6, 6)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(outLinedTextField, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 660, GroupLayout.PREFERRED_SIZE)
                    .addGap(44, 44, 44)
                    .addComponent(jSeparator2, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                    .addGap(15, 15, 15)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(addUniversityButton, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(36, Short.MAX_VALUE))
        );

        // Προσθήκη του mainPanel στο τρέχον JPanel
        this.add(mainPanel);
    }

    /**
     * Μέθοδος που καλείται όταν γίνει enter στο πεδίο αναζήτησης (actionPerformed).
     *
     * @param evt Το {@link java.awt.event.ActionEvent} που προκαλείται.
     */
    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {
        // Επιπλέον ενέργειες μπορούν να υλοποιηθούν εδώ, αν απαιτείται
        LOGGER.fine("📝 Action on search field: " + evt.getActionCommand());
    }

    /**
     * Ενημερώνει το φίλτρο αναζήτησης του πίνακα, βασισμένο στο κείμενο που έχει εισαχθεί
     * στο {@code outLinedTextField}. Αν το κείμενο είναι κενό ή ισούται με το "search university",
     * καθαρίζει το φίλτρο, αλλιώς εφαρμόζει ένα RowFilter που φιλτράρει τα δεδομένα.
     */
    private void updateFilter() {
        String text = outLinedTextField.getText().trim().toLowerCase();

        if (text.isEmpty() || text.equals("search university")) {
            sorter.setRowFilter(null);
            LOGGER.fine("ℹ️ Filter removed from table.");
        } else {
            // Εφαρμόζουμε φίλτρο μόνο στις τιμές της στήλης "University Name" (index 1)
            sorter.setRowFilter(new RowFilter<DefaultTableModel, Integer>() {
                @Override
                public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                    String country = entry.getStringValue(1).toLowerCase();
                    return country.startsWith(text);
                }
            });
            LOGGER.fine("🔍 Filter updated to: " + text);
        }
    }
}
