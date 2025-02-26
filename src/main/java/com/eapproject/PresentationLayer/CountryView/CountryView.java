package com.eapproject.PresentationLayer.CountryView;

import java.awt.CardLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import com.eapproject.DataLayer.universitiesRepo;
import com.eapproject.PresentationLayer.CountryUniversitiesView.CountryUniversities;

public class CountryView extends javax.swing.JPanel {

    private final JPanel rightScreenPanel;
    private DefaultTableModel model;
    private TableRowSorter<DefaultTableModel> sorter;
    private final universitiesRepo repo = new universitiesRepo();

    // Constructor for initializing the CountryView panel
    public CountryView(JPanel rightScreenJpanel) {
        this.rightScreenPanel = rightScreenJpanel;
        initComponents();
        model = (DefaultTableModel) table.getModel();
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        this.setPreferredSize(new java.awt.Dimension(1050, 500));
    }



    // Initializes all the components and sets up layout and styles
    @SuppressWarnings("unchecked")
    private void initComponents() {

        // Panel for the search section
        jPanel2 = new javax.swing.JPanel();
        jTextField3 = new javax.swing.JTextField();
        mainPanel = new javax.swing.JPanel();
        countriesLogo = new javax.swing.JLabel();
        topDiv = new javax.swing.JSeparator();
        outLinedTextField = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        bottomDivier = new javax.swing.JSeparator();
        addCountryButton = new javax.swing.JButton();
        this.setPreferredSize(new java.awt.Dimension(2000, 980)); // Large preferred size for the panel

        mainPanel.setPreferredSize(new java.awt.Dimension(1300, 960)); // Set main panel size (width and height)
        this.revalidate(); // Ensure layout updates with new preferred size

        // Table Header customization: set color, font, etc.
        table.getTableHeader().setBackground(new java.awt.Color(223, 109, 35)); // Orange header color
        table.getTableHeader().setForeground(new java.awt.Color(255, 255, 255)); // White text for header
        table.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14)); // Bold font for headers

        // Table Grid customization
        table.setShowGrid(false);
        table.setGridColor(new java.awt.Color(223, 109, 35)); // Grid lines in orange
        table.setIntercellSpacing(new java.awt.Dimension(0, 0)); // Remove space between cells

        // Table cell customization
        table.setBackground(new java.awt.Color(255, 255, 255)); // White background for table cells
        table.setForeground(new java.awt.Color(0, 0, 0)); // Black text for cells

        // ScrollPane for table with border
        jScrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(223, 109, 35), 2)); // Border color orange
        jScrollPane3.getViewport().setBackground(new java.awt.Color(255, 255, 255)); // Αλλαγή του background χρώματος
        // Disable table editing and selection behavior settings
        table.setCellSelectionEnabled(true);
        table.setFocusable(false); // Disable focus (no direct editing)
        table.setDefaultEditor(Object.class, null); // Disable editor for all cells
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION); // Allow only single row selection
        table.setRowSelectionAllowed(true); // Enable row selection, but not column selection
        table.setColumnSelectionAllowed(false); // Disable column selection

        // Search section setup
        jPanel2.setBackground(new java.awt.Color(252, 252, 242)); // Light background color for search panel

        jTextField3.setBackground(new java.awt.Color(252, 252, 242)); // Match background color
        jTextField3.setHorizontalAlignment(javax.swing.JTextField.CENTER); // Centered text
        jTextField3.setText("Search Country"); // Placeholder text
        jTextField3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(223, 109, 35), 2, true)); // Border with orange color

        // Layout adjustments for the search panel
        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(447, 447, 447)
                                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 524, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                )
        );

        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {

                if (!outLinedTextField.getBounds().contains(evt.getPoint())) {

                    outLinedTextField.transferFocus();
                }
            }
        });

        // Add a mouse listener to OutLinedTextField to transfer focus when clicked outside
        outLinedTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (outLinedTextField.getText().isEmpty()) {
                    // Αν το πεδίο είναι άδειο, επαναφέρουμε την υπόδειξη κειμένου
                    outLinedTextField.setText("Search Country");
                    outLinedTextField.setForeground(new java.awt.Color(169, 169, 169));
                }
            }

            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                // Όταν ο χρήστης πατήσει πάνω στο πεδίο, καθαρίζει το placeholder
                if (outLinedTextField.getText().equals("Search Country")) {
                    outLinedTextField.setText(""); // Καθαρίζει το placeholder
                    outLinedTextField.setForeground(new java.awt.Color(0, 0, 0)); // Ρυθμίζει το χρώμα του κειμένου σε μαύρο
                }
            }
        });




        // Main panel setup
        mainPanel.setBackground(new java.awt.Color(252, 252, 242)); // Light background for main panel
        mainPanel.setForeground(new java.awt.Color(252, 252, 242)); // Text color for the panel

        // Logo text in main panel
        countriesLogo.setBackground(new java.awt.Color(223, 109, 35)); // Orange background for logo
        countriesLogo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 48)); // Bold font
        countriesLogo.setForeground(new java.awt.Color(223, 109, 35)); // Orange color for logo text
        countriesLogo.setText("Countries"); // Setting logo name

        // Top divider line
        topDiv.setBackground(new java.awt.Color(223, 109, 35)); // Divider in orange
        topDiv.setForeground(new java.awt.Color(223, 109, 35));

        // Outlined text field for search input
        outLinedTextField.setBackground(new java.awt.Color(255, 255, 255)); // White background for the text field
        outLinedTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER); // Centered text
        outLinedTextField.setText("Search Country");
        outLinedTextField.setForeground(new java.awt.Color(169, 169, 169));
        outLinedTextField.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(223, 109, 35), 2, true)); // Orange border

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



        // Table model with sample data (Country and associated University)
        table.setBackground(new java.awt.Color(255, 255, 255)); // White background for table cells
        jScrollPane3.setBackground(new java.awt.Color(255, 255, 255)); // White background for scroll pane
        table.setModel(new javax.swing.table.DefaultTableModel(
            repo.getCountries()
                // new Object[][] {
                //         repo.getCountries(),
                //         { "1", "Greece" },
                //         { "2", "USA", 5000 },
                //         { "3", "Germany", 400 },
                //         { "4", "UK", 150 },
                //         { "5", "France", 100 },
                //         { "6", "Italy", 80 },
                //         { "7", "Spain", 70 },
                //         { "8", "Australia", 40 },
                //         { "9", "Canada", 100 },
                //         { "10", "Japan", 100 },
                //         { "11", "China", 3000 },
                //         { "12", "India", 1000 },
                //         { "13", "Russia", 600 },
                //         { "14", "Brazil", 250 },
                //         { "15", "Mexico", 100 },
                //         { "16", "South Korea", 400 },
                //         { "17", "Turkey", 200 },
                //         { "18", "Netherlands", 50 },
                //         { "19", "Sweden", 30 },
                //         { "20", "Norway", 10 },
                //         { "21", "Finland", 20 },
                //         { "22", "Belgium", 30 },
                //         { "23", "Switzerland", 20 },
                //         { "24", "South Africa", 50 },
                //         { "25", "Argentina", 40 },
                //         { "26", "Chile", 30 },
                //         { "27", "Egypt", 40 },
                //         { "28", "Saudi Arabia", 30 },
                //         { "29", "UAE", 20 },
                //         { "30", "New Zealand", 8 }
                // },
                ,
        new String[] {
                        "No.", "Country", "Universities"
                }
        ));

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) { // Έλεγχος αν έγινε διπλό κλικ
                    int row = table.getSelectedRow();
                    if (row != -1) { // Έλεγχος ότι επιλέχθηκε γραμμή
                        String country = table.getValueAt(row, 1).toString(); // Παίρνουμε το όνομα της χώρας
                        rightScreenPanel.removeAll();
                        rightScreenPanel.add(new CountryUniversities(country, rightScreenPanel), "CountryUniversities");
                        rightScreenPanel.revalidate();
                        rightScreenPanel.repaint();
                        ((CardLayout) rightScreenPanel.getLayout()).show(rightScreenPanel, "CountryUniversities");
                    }
                }
            }
        });
        jScrollPane3.setBackground(new java.awt.Color(255, 255, 255));




        // Custom font and renderer for table cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER); // Center text in cells
        centerRenderer.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 28)); // Bold font for cells
        centerRenderer.setForeground(new java.awt.Color(139, 89, 61)); // Brown color for cell text

        // Apply the custom renderer to all columns
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Selection customization
        table.setSelectionBackground(new java.awt.Color(187, 124, 82, 200)); // Background color for selected row
        table.setSelectionForeground(java.awt.Color.WHITE); // White text color for selected row

        // Row height adjustment for better visibility
        table.setRowHeight(40); // Increased row height to 40px for better display

        // Set font for table data cells
        table.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 16)); // Apply bold font for all data cells

        jScrollPane3.setViewportView(table); // Add the table to the scroll pane

        // Bottom divider line
        bottomDivier.setBackground(new java.awt.Color(223, 109, 35));
        bottomDivier.setForeground(new java.awt.Color(223, 109, 35));

        // Button to add a country
        addCountryButton.setBackground(new java.awt.Color(223, 109, 35)); // Button background color
        addCountryButton.setForeground(new java.awt.Color(255, 255, 255)); // Button text color
        addCountryButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        addCountryButton.setText("Add Country"); // Set button label
        addCountryButton.setToolTipText(""); // Tooltip text (optional)
        addCountryButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1)); // No border for button
        table.getColumnModel().getColumn(0).setPreferredWidth(20); // Στήλη 0 (No.)
        table.getColumnModel().getColumn(1).setPreferredWidth(300); // Στήλη 1 (Universities)

// Ρύθμιση πλάτους για την κεφαλίδα των στηλών
        table.getTableHeader().getColumnModel().getColumn(0).setPreferredWidth(50); // Κεφαλίδα της πρώτης στήλης
        table.getTableHeader().getColumnModel().getColumn(1).setPreferredWidth(400); //
        table.getTableHeader().getColumnModel().getColumn(2).setPreferredWidth(50); //


        // Layout for main panel components
        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
                mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(topDiv)
                        .addComponent(bottomDivier)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                                .addContainerGap(208, Short.MAX_VALUE)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 888, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(200, 200, 200))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                                                .addComponent(outLinedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(406, 406, 406))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                                                .addComponent(addCountryButton, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(530, 530, 530))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                                                .addComponent(countriesLogo)
                                                .addGap(542, 542, 542)))
                                )
                        );
        mainPanelLayout.setVerticalGroup(
                mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(countriesLogo)
                                .addGap(11, 11, 11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(topDiv, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(outLinedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                                .addComponent(bottomDivier, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(addCountryButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40))
        );

        // Add the main panel to the CountryView panel
        this.add(mainPanel);
    }

    private void updateFilter() {
        String text = outLinedTextField.getText().trim().toLowerCase();

        if (text.isEmpty() || text.equals("search country")) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(new RowFilter<>() {
                @Override
                public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                    String country = entry.getStringValue(1).toLowerCase();
                    return country.startsWith(text);
                }
            });
        }
    }

    // Declare components as private variables
    private javax.swing.JButton addCountryButton;
    private javax.swing.JSeparator bottomDivier;
    private javax.swing.JLabel countriesLogo;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JTextField outLinedTextField;
    private javax.swing.JTable table;
    private javax.swing.JSeparator topDiv;




}
