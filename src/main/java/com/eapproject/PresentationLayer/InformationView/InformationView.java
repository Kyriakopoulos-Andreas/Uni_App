package com.eapproject.PresentationLayer.InformationView;
public class InformationView extends javax.swing.JPanel {

    public InformationView() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        topSeperator = new javax.swing.JSeparator();
        informationLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TextArea = new javax.swing.JTextArea();
        jSeparator1 = new javax.swing.JSeparator();

        mainPanel.setBackground(new java.awt.Color(252, 252, 242));
        this.setPreferredSize(new java.awt.Dimension(1050, 980));
        mainPanel.setPreferredSize(new java.awt.Dimension(1050, 960));

        topSeperator.setBackground(new java.awt.Color(223, 109, 35));
        topSeperator.setForeground(new java.awt.Color(223, 109, 35));
        topSeperator.setFont(new java.awt.Font("Segoe UI", 0, 18));

        informationLabel.setBackground(new java.awt.Color(223, 109, 35));
        informationLabel.setFont(new java.awt.Font("Segoe UI", 1, 42));
        informationLabel.setForeground(new java.awt.Color(223, 109, 35));
        informationLabel.setText("Information");

        TextArea.setBackground(new java.awt.Color(255, 255, 255));
        TextArea.setColumns(20);
        TextArea.setForeground(new java.awt.Color(96, 59, 6));
        TextArea.setRows(5);
        TextArea.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(223, 109, 35)));
        TextArea.setSelectedTextColor(new java.awt.Color(178, 112, 69));
        TextArea.setSelectionColor(new java.awt.Color(255, 255, 255));
        TextArea.setLineWrap(true);
        TextArea.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 28));
        TextArea.setWrapStyleWord(true);
        TextArea.setEditable(false);
        TextArea.setText("This application provides detailed information about universities worldwide. "
                + "Users can explore universities by selecting a country and viewing a list of institutions, "
                + "or they can search for a specific university directly. Each university profile includes essential "
                + "details such as location, ranking, available programs, and admission requirements. "
                + "The intuitive interface ensures easy navigation, making it a valuable resource for students, "
                + "researchers, and anyone interested in higher education. Additionally, users can compare universities, "
                + "save their favorite institutions, and access up-to-date insights on academic offerings and scholarships.");

        jScrollPane1.setViewportView(TextArea);

        jSeparator1.setBackground(new java.awt.Color(223, 109, 35));
        jSeparator1.setForeground(new java.awt.Color(223, 109, 35));

        // Αλλαγή 1: Αφαίρεση όλων των αναφορών στο jPanel2
        // Αλλαγή 2: Ενημέρωση του GroupLayout για μείωση κενών
        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
                mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(topSeperator, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGap(78, 78, 78)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 880, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(jSeparator1)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                                .addContainerGap(390, Short.MAX_VALUE)
                                .addComponent(informationLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(391, 391, 391))
        );
        mainPanelLayout.setVerticalGroup(
                mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(informationLabel)
                                .addGap(15, 15, 15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(topSeperator, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40) // Μείωση κενού από 57 σε 30
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 660, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE) // Μείωση κενού από 63 σε 50
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(94, 94, 94)) // Μείωση κενού από 83 σε 50
        );

        this.add(mainPanel);
    }

    // Variables declaration - do not modify
    private javax.swing.JTextArea TextArea;
    private javax.swing.JLabel informationLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JSeparator topSeperator;
    // End of variables declaration
}