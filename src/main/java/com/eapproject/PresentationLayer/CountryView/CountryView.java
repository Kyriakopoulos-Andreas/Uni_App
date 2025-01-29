package com.eapproject.PresentationLayer.CountryView;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

public class CountryView extends javax.swing.JPanel {

    // Constructor for initializing the CountryView panel
    public CountryView() {
        initComponents();
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
        table.setShowGrid(true);
        table.setGridColor(new java.awt.Color(223, 109, 35)); // Grid lines in orange
        table.setIntercellSpacing(new java.awt.Dimension(0, 0)); // Remove space between cells

        // Table cell customization
        table.setBackground(new java.awt.Color(255, 255, 255)); // White background for table cells
        table.setForeground(new java.awt.Color(0, 0, 0)); // Black text for cells

        // ScrollPane for table with border
        jScrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(223, 109, 35), 2)); // Border color orange

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
                // Ελέγξτε αν το κλικ δεν είναι πάνω στο OutLinedTextField
                if (!outLinedTextField.getBounds().contains(evt.getPoint())) {
                    // Αφαίρεση του focus από το OutLinedTextField
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
        countriesLogo.setFont(new java.awt.Font("Segoe UI", 0, 48)); // Large font size for the title
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

        // Table model with sample data (Country and associated University)
        table.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                        { "1", "Greece", "Athens University" },
                        { "2", "USA", "Harvard University" },
                        { "3", "Germany", "University of Berlin" },
                        { "4", "UK", "Oxford University" },
                        { "5", "France", "Sorbonne University" },
                        { "6", "Italy", "University of Rome" },
                        { "7", "Spain", "University of Madrid" },
                        { "8", "Australia", "University of Sydney" },
                        { "9", "Canada", "University of Toronto" },
                        { "10", "Japan", "University of Tokyo" },
                        { "1", "Greece", "Athens University" },
                        { "2", "USA", "Harvard University" },
                        { "3", "Germany", "University of Berlin" },
                        { "4", "UK", "Oxford University" },
                        { "5", "France", "Sorbonne University" },
                        { "6", "Italy", "University of Rome" },
                        { "7", "Spain", "University of Madrid" },
                        { "8", "Australia", "University of Sydney" },
                        { "9", "Canada", "University of Toronto" },
                        { "10", "Japan", "University of Tokyo" }
                },
                new String[] {
                        "No.", "Country", "Universities"
                }
        ));

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
        addCountryButton.setText("Add Country"); // Set button label
        addCountryButton.setToolTipText(""); // Tooltip text (optional)
        addCountryButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1)); // No border for button

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
                                                .addGap(213, 213, 213))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                                                .addComponent(outLinedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(406, 406, 406))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                                                .addComponent(addCountryButton, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(562, 562, 562))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                                                .addComponent(countriesLogo)
                                                .addGap(542, 542, 542)))
                                )
                        );
        mainPanelLayout.setVerticalGroup(
                mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(countriesLogo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(topDiv, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(outLinedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                                .addComponent(bottomDivier, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(addCountryButton, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40))
        );

        // Add the main panel to the CountryView panel
        this.add(mainPanel);
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
