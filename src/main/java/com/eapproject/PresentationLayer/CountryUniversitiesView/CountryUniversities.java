
package com.eapproject.PresentationLayer.CountryUniversitiesView;


import com.eapproject.PresentationLayer.UniversityView.UniversityView;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CountryUniversities extends javax.swing.JPanel {

    private String countryLogo;
    private JPanel rightScreenPanel;
    private DefaultTableModel model;
    private TableRowSorter<DefaultTableModel> sorter;

    public CountryUniversities(String countryLogo, JPanel rightScreenPanel) {
        this.rightScreenPanel = rightScreenPanel;
        this.countryLogo = countryLogo;
        initComponents();
        model = (DefaultTableModel) table.getModel();
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {//GEN-BEGIN:initComponents
        todoCountryPanel = new javax.swing.JPanel();
        mainPanel = new javax.swing.JPanel();
        todoCountry = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        outLinedTextField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jSeparator2 = new javax.swing.JSeparator();
        addUniversityButton = new javax.swing.JButton();

        // Set FlowLayout to center the todoCountry label
        mainPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER));

        mainPanel.setBackground(new java.awt.Color(252, 252, 242));
        mainPanel.setForeground(new java.awt.Color(255, 255, 255));

        this.setPreferredSize(new java.awt.Dimension(1200, 1000)); // Large preferred size for the panel
        this.revalidate();
        mainPanel.setPreferredSize(new java.awt.Dimension(1200, 1000)); // Set main panel size (width and height)

        todoCountry.setBackground(new java.awt.Color(223, 109, 35));
        todoCountry.setFont(new java.awt.Font("Segoe UI", 1, 42)); // NOI18N
        todoCountry.setForeground(new java.awt.Color(223, 109, 35));
        todoCountry.setText(countryLogo);

        todoCountryPanel.add(todoCountry);
        todoCountryPanel.setBackground(new java.awt.Color(252, 252, 242));

        // Set a fixed width for todoCountry to prevent shifting
        todoCountry.setPreferredSize(new java.awt.Dimension(400, 60)); // Adjust width and height as needed
        todoCountry.setHorizontalAlignment(SwingConstants.CENTER);

        jSeparator1.setBackground(new java.awt.Color(223, 109, 35));
        jSeparator1.setForeground(new java.awt.Color(223, 109, 35));

        outLinedTextField.setBackground(new java.awt.Color(255, 255, 255));
        outLinedTextField.setForeground(new java.awt.Color(153, 153, 153));
        outLinedTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        outLinedTextField.setText("Search University");
        outLinedTextField.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(223, 109, 35), 2, true));
        outLinedTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

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
                    outLinedTextField.setText("Search University");
                    outLinedTextField.setForeground(new java.awt.Color(169, 169, 169));
                }
            }

            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                // Όταν ο χρήστης πατήσει πάνω στο πεδίο, καθαρίζει το placeholder
                if (outLinedTextField.getText().equals("Search University")) {
                    outLinedTextField.setText(""); // Καθαρίζει το placeholder
                    outLinedTextField.setForeground(new java.awt.Color(0, 0, 0)); // Ρυθμίζει το χρώμα του κειμένου σε μαύρο
                }
            }
        });


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

        table.setBackground(new java.awt.Color(252, 252, 242));
        table.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(223, 109, 35), 2, true));
        table.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                        { "1", "National and Kapodistrian University of Athens" },
                        { "2", "Aristotle University of Thessaloniki" },
                        { "3", "University of Crete" },
                        { "4", "University of Patras" },
                        { "5", "Athens University of Economics and Business" },
                        { "6", "University of Ioannina" },
                        { "7", "University of Thessaly" },
                        { "8", "University of Macedonia" },
                        { "9", "National Technical University of Athens" },
                        { "10", "Democritus University of Thrace" },
                        { "11", "University of Piraeus" },
                        { "12", "University of Aegean" },
                        { "13", "University of the Aegean" },
                        { "14", "University of West Macedonia" },
                        { "15", "University of the Peloponnese" },
                        { "16", "Harokopio University of Athens" },
                        { "17", "Hellenic Open University" },
                        { "18", "Panteion University of Social and Political Sciences" },
                        { "19", "University of Thessaloniki" },
                        { "20", "School of Pedagogical and Technological Education (ASPETE)" }
                },
        new String [] {
                        "No.", "Universites"
                }
        ) {
            Class[] types = new Class [] {
                    java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        table.getTableHeader().setBackground(new java.awt.Color(223, 109, 35)); // Orange header color
        table.getTableHeader().setForeground(new java.awt.Color(255, 255, 255)); // White text for header
        table.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14)); // Bold font for headers

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) { // Έλεγχος αν έγινε διπλό κλικ
                    int row = table.getSelectedRow();
                    if (row != -1) { // Έλεγχος ότι επιλέχθηκε γραμμή
                        String university = table.getValueAt(row, 1).toString(); // Παίρνουμε το όνομα της χώρας
                        rightScreenPanel.removeAll();
                        rightScreenPanel.add(new UniversityView(university), "CountryUniversities");
                        rightScreenPanel.revalidate();
                        rightScreenPanel.repaint();
                        ((CardLayout) rightScreenPanel.getLayout()).show(rightScreenPanel, "CountryUniversities");
                    }
                }
            }
        });

        // Table Grid customization
        table.setShowGrid(false);
        table.setGridColor(new java.awt.Color(223, 109, 35)); // Grid lines in orange
        table.setIntercellSpacing(new java.awt.Dimension(0, 0)); // Remove space between cells

        // Table cell customization
        table.setBackground(new java.awt.Color(255, 255, 255)); // White background for table cells
        table.setForeground(new java.awt.Color(0, 0, 0)); // Black text for cells

        // ScrollPane for table with border
        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(223, 109, 35), 2)); // Border color orange

        // Disable table editing and selection behavior settings
        table.setCellSelectionEnabled(true);
        table.setFocusable(false); // Disable focus (no direct editing)
        table.setDefaultEditor(Object.class, null); // Disable editor for all cells
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION); // Allow only single row selection
        table.setRowSelectionAllowed(true); // Enable row selection, but not column selection
        table.setColumnSelectionAllowed(false); // Disable column selection
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
        table.setPreferredSize(new Dimension(800, table.getPreferredSize().height));
        // Αλλαγή πλάτους για τις στήλες (δεδομένα και κεφαλίδα)
        table.getColumnModel().getColumn(0).setPreferredWidth(5); // Στήλη 0 (No.)
        table.getColumnModel().getColumn(1).setPreferredWidth(700); // Στήλη 1 (Universities)

// Ρύθμιση πλάτους για την κεφαλίδα των στηλών
        table.getTableHeader().getColumnModel().getColumn(0).setPreferredWidth(5); // Κεφαλίδα της πρώτης στήλης
        table.getTableHeader().getColumnModel().getColumn(1).setPreferredWidth(700); //

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Set font for table data cells
        table.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 16)); // Apply bold font for all data cells



        jScrollPane1.setViewportView(table);
        jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        jSeparator2.setBackground(new java.awt.Color(223, 109, 35));
        jSeparator2.setForeground(new java.awt.Color(223, 109, 35));

        // Button to add a country
        addUniversityButton.setBackground(new java.awt.Color(223, 109, 35)); // Button background color
        addUniversityButton.setForeground(new java.awt.Color(255, 255, 255)); // Button text color
        addUniversityButton.setText("Add University"); // Set button label
        addUniversityButton.setToolTipText(""); // Tooltip text (optional)
        addUniversityButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1)); // No border for button

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
                mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jSeparator1)
                        .addComponent(jSeparator2)
                        .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(mainPanelLayout.createSequentialGroup()
                                                .addGap(1, 1, 1)
                                                .addComponent(todoCountryPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 1043, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(mainPanelLayout.createSequentialGroup()
                                                .addGap(287, 287, 287)
                                                .addComponent(outLinedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(mainPanelLayout.createSequentialGroup()
                                                .addGap(120, 120, 120)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 822, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(209, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(addUniversityButton, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(555, 555, 555))
        );
        mainPanelLayout.setVerticalGroup(
                mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(mainPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGap(10, 10, 10)
                                .addComponent(todoCountryPanel,javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(outLinedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 660, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(43, 43, 43)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(addUniversityButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(36, Short.MAX_VALUE))
        );

        this.add(mainPanel);
    }//GEN-END:initComponents


    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void updateFilter() {
        String text = outLinedTextField.getText().trim().toLowerCase();

        if (text.isEmpty() || text.equals("search university")) {
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addUniversityButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextField outLinedTextField;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JTable table;
    private javax.swing.JLabel todoCountry;
    private javax.swing.JPanel todoCountryPanel;
    // End of variables declaration//GEN-END:variables


}
