/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eapproject.PresentationLayer.UniversityView;

/**
 *
 * @author kamoz
 */
import java.awt.*;
public class UniversityView extends javax.swing.JPanel {

    /**
     * Creates the initial view of the panel for the university display
     */
    public UniversityView() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        this.setLayout(new BorderLayout()); // Basic layout for the panel
        // Code to add components to this panel
        this.setPreferredSize(new java.awt.Dimension(2000, 600)); // Set dimensions for the panel

        // Creating the components of the panel
        universityViewMainPanel = new javax.swing.JPanel();
        UniversityLabelTODO = new javax.swing.JLabel();
        topDivider = new javax.swing.JSeparator();
        countryLabel = new javax.swing.JLabel();
        stateLabel = new javax.swing.JLabel();
        domainLabel = new javax.swing.JLabel();
        webPageLabel = new javax.swing.JLabel();
        alphaTwoCodeLabel = new javax.swing.JLabel();
        bottomDivider = new javax.swing.JSeparator();
        updateButton = new javax.swing.JButton();
        TODOcountryLabel = new javax.swing.JLabel();
        TODOwebPageLabel = new javax.swing.JLabel();
        TODOstateLabel = new javax.swing.JLabel();
        TODOalphaTwoCodeLabel = new javax.swing.JLabel();
        TODOdomainLabel = new javax.swing.JLabel();
        contactLabel = new javax.swing.JLabel();
        facultiesLabel = new javax.swing.JLabel();
        TODOcontactLabel = new javax.swing.JLabel();
        TODOfacultiesLabel = new javax.swing.JLabel();

        // Set properties for the contact label (TODO)
        TODOcontactLabel.setFont(new java.awt.Font("Segoe UI", 0, 28)); // Font size
        TODOcontactLabel.setForeground(new java.awt.Color(96, 59, 6)); // Font color
        TODOcontactLabel.setText("TODO");

        // Set properties for the faculties label (TODO)
        TODOfacultiesLabel.setFont(new java.awt.Font("Segoe UI", 0, 28)); // Font size
        TODOfacultiesLabel.setForeground(new java.awt.Color(96, 59, 6)); // Font color
        TODOfacultiesLabel.setText("TODO");

        // Set properties for the "Contact" label
        contactLabel.setFont(new java.awt.Font("Segoe UI", 0, 42)); // Font size
        contactLabel.setForeground(new java.awt.Color(223, 109, 35)); // Font color
        contactLabel.setText("Contact:");

        // Set properties for the "Faculties" label
        facultiesLabel.setFont(new java.awt.Font("Segoe UI", 0, 42)); // Font size
        facultiesLabel.setForeground(new java.awt.Color(223, 109, 35)); // Font color
        facultiesLabel.setText("Faculties:");

        // Set background color for the main panel
        universityViewMainPanel.setBackground(new java.awt.Color(252, 252, 242));

        // Set properties for the main university label
        UniversityLabelTODO.setBackground(new java.awt.Color(223, 109, 35));
        UniversityLabelTODO.setFont(new java.awt.Font("Segoe UI", 0, 48)); // Font size
        UniversityLabelTODO.setForeground(new java.awt.Color(223, 109, 35)); // Font color
        UniversityLabelTODO.setText("University");

        // Set properties for the dividers
        topDivider.setBackground(new java.awt.Color(223, 109, 35));
        topDivider.setForeground(new java.awt.Color(223, 109, 35));

        // Set properties for the "Country" label
        countryLabel.setFont(new java.awt.Font("Segoe UI", 0, 42)); // Font size
        countryLabel.setForeground(new java.awt.Color(223, 109, 35)); // Font color
        countryLabel.setText("Country:");

        // Set properties for the "State" label
        stateLabel.setFont(new java.awt.Font("Segoe UI", 0, 42)); // Font size
        stateLabel.setForeground(new java.awt.Color(223, 109, 35)); // Font color
        stateLabel.setText("State:");

        // Set properties for the "Domain" label
        domainLabel.setFont(new java.awt.Font("Segoe UI", 0, 42)); // Font size
        domainLabel.setForeground(new java.awt.Color(223, 109, 35)); // Font color
        domainLabel.setText("Domain:");

        // Set properties for the "Web Page" label
        webPageLabel.setFont(new java.awt.Font("Segoe UI", 0, 42)); // Font size
        webPageLabel.setForeground(new java.awt.Color(223, 109, 35)); // Font color
        webPageLabel.setText("Web Page:");

        // Set properties for the "Alpha Two Code" label
        alphaTwoCodeLabel.setFont(new java.awt.Font("Segoe UI", 0, 42)); // Font size
        alphaTwoCodeLabel.setForeground(new java.awt.Color(223, 109, 35)); // Font color
        alphaTwoCodeLabel.setText("Alpha Two Code:");

        // Set properties for the bottom divider
        bottomDivider.setBackground(new java.awt.Color(223, 109, 35));
        bottomDivider.setForeground(new java.awt.Color(223, 109, 35));

        // Set properties for the "Update Information" button
        updateButton.setBackground(new java.awt.Color(223, 109, 35)); // Button background color
        updateButton.setForeground(new java.awt.Color(255, 255, 255)); // Button text color
        updateButton.setText("Update Information");
        updateButton.setBorder(javax.swing.BorderFactory.createMatteBorder(6, 6, 6, 6, new java.awt.Color(223, 109, 35)));

        updateButton.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 18)); // Font size
        updateButton.setPreferredSize(new java.awt.Dimension(300, 100)); // Button size
        updateButton.setMargin(new java.awt.Insets(20, 40, 20, 40));

        // Set properties for the TODO labels
        TODOcountryLabel.setFont(new java.awt.Font("Segoe UI", 0, 28)); // Font size
        TODOcountryLabel.setForeground(new java.awt.Color(96, 59, 6)); // Font color
        TODOcountryLabel.setText("TODO country");

        TODOwebPageLabel.setFont(new java.awt.Font("Segoe UI", 0, 28)); // Font size
        TODOwebPageLabel.setForeground(new java.awt.Color(96, 59, 6)); // Font color
        TODOwebPageLabel.setText("TODO WebPage");

        TODOstateLabel.setFont(new java.awt.Font("Segoe UI", 0, 28)); // Font size
        TODOstateLabel.setForeground(new java.awt.Color(96, 59, 6)); // Font color
        TODOstateLabel.setText("TODO State");

        TODOalphaTwoCodeLabel.setFont(new java.awt.Font("Segoe UI", 0, 28)); // Font size
        TODOalphaTwoCodeLabel.setForeground(new java.awt.Color(96, 59, 6)); // Font color
        TODOalphaTwoCodeLabel.setText("TODO Alpha_Two_Code");

        TODOdomainLabel.setFont(new java.awt.Font("Segoe UI", 0, 28)); // Font size
        TODOdomainLabel.setForeground(new java.awt.Color(96, 59, 6)); // Font color
        TODOdomainLabel.setText("TODO Domain");

        // Layout configuration for the main panel
        javax.swing.GroupLayout universityViewMainPanelLayout = new javax.swing.GroupLayout(universityViewMainPanel);
        universityViewMainPanel.setLayout(universityViewMainPanelLayout);

        // Horizontal group configuration
        universityViewMainPanelLayout.setHorizontalGroup(
                universityViewMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(topDivider)
                        .addComponent(bottomDivider)
                        .addGroup(universityViewMainPanelLayout.createSequentialGroup()
                                .addGap(400, 400, 400) // The `UniversityLabelTODO` stays in place
                                .addComponent(UniversityLabelTODO))
                        .addGroup(universityViewMainPanelLayout.createSequentialGroup()
                                .addGap(120, 120, 120) // The remaining labels move to the left
                                .addGroup(universityViewMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(universityViewMainPanelLayout.createSequentialGroup()
                                                .addGroup(universityViewMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(countryLabel)
                                                        .addComponent(stateLabel)
                                                        .addComponent(webPageLabel)
                                                        .addComponent(domainLabel)
                                                        .addComponent(alphaTwoCodeLabel)
                                                        .addComponent(contactLabel)
                                                        .addComponent(facultiesLabel))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(universityViewMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false) // The `false` ensures the labels are equally spaced
                                                        .addComponent(TODOcountryLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(TODOstateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(TODOwebPageLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(TODOdomainLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(TODOalphaTwoCodeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(TODOcontactLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(TODOfacultiesLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(universityViewMainPanelLayout.createSequentialGroup()
                                .addGap(410, 410, 410) // Moves the button to the left
                                .addComponent(updateButton)
                                .addGap(50, 150, Short.MAX_VALUE))
        );

        // Vertical group configuration
        universityViewMainPanelLayout.setVerticalGroup(
                universityViewMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(universityViewMainPanelLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(UniversityLabelTODO)
                                .addGap(30, 30, 30)
                                .addComponent(topDivider, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addGroup(universityViewMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(countryLabel)
                                        .addComponent(TODOcountryLabel))
                                .addGap(40, 40, 40)
                                .addGroup(universityViewMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(stateLabel)
                                        .addComponent(TODOstateLabel))
                                .addGap(40, 40, 40)
                                .addGroup(universityViewMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(webPageLabel)
                                        .addComponent(TODOwebPageLabel))
                                .addGap(40, 40, 40)
                                .addGroup(universityViewMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(domainLabel)
                                        .addComponent(TODOdomainLabel))
                                .addGap(40, 40, 40)
                                .addGroup(universityViewMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(alphaTwoCodeLabel)
                                        .addComponent(TODOalphaTwoCodeLabel))
                                .addGap(40, 40, 40)
                                .addGroup(universityViewMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(contactLabel)
                                        .addComponent(TODOcontactLabel))
                                .addGap(40, 40, 40)
                                .addGroup(universityViewMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(facultiesLabel)
                                        .addComponent(TODOfacultiesLabel))
                                .addGap(40, 40, 40)
                                .addComponent(bottomDivider, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addComponent(updateButton)
                                .addContainerGap(50, Short.MAX_VALUE))
        );

        // Add the main panel to the frame or parent container
        this.add(universityViewMainPanel);
    }

    // Variables declaration - do not modify
    private javax.swing.JLabel TODOalphaTwoCodeLabel;
    private javax.swing.JLabel TODOcountryLabel;
    private javax.swing.JLabel TODOdomainLabel;
    private javax.swing.JLabel TODOstateLabel;
    private javax.swing.JLabel TODOwebPageLabel;
    private javax.swing.JLabel UniversityLabelTODO;
    private javax.swing.JLabel alphaTwoCodeLabel;
    private javax.swing.JSeparator bottomDivider;
    private javax.swing.JLabel countryLabel;
    private javax.swing.JLabel domainLabel;
    private javax.swing.JLabel stateLabel;
    private javax.swing.JSeparator topDivider;
    private javax.swing.JPanel universityViewMainPanel;
    private javax.swing.JButton updateButton;
    private javax.swing.JLabel webPageLabel;
    private javax.swing.JLabel contactLabel;
    private javax.swing.JLabel facultiesLabel;
    private javax.swing.JLabel TODOcontactLabel;
    private javax.swing.JLabel TODOfacultiesLabel;
    // End of variables declaration
}
