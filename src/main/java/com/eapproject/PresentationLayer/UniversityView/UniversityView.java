/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eapproject.PresentationLayer.UniversityView;

/**
 *
 * @author kamoz
 */
import javax.swing.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;

public class UniversityView extends javax.swing.JPanel {
    private String universityLogo;
    private int spaceY = 40;
    private int spaceYBeforeBottomDivider = 30;
    private int spaceYifFaculties = 40;


    public UniversityView(String university) {
        this.universityLogo = university;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new java.awt.Dimension(2000, 600));

        // Initialize components
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
        contactLabel = new javax.swing.JLabel();
        facultiesLabel = new javax.swing.JLabel();
        universityLabelPanel = new javax.swing.JPanel();



        // Initialize edit panels
        countryTodoPanel = createTodoPanel("TODO country");
        stateTodoPanel = createTodoPanel("TODO State");
        webPageTodoPanel = createTodoPanel("TODO WebPage");
        domainTodoPanel = createTodoPanel("TODO Domain");
        alphaTwoCodeTodoPanel = createTodoPanel("TODO Alpha_Two_Code");
        contactTodoPanel = createTodoPanel("ξηγξγηξηγ");
        facultiesTodoPanel = createTodoPanel("γηξη");

        // Set properties
        universityViewMainPanel.setBackground(new java.awt.Color(252, 252, 242));
        universityLabelPanel.setBackground(new java.awt.Color(252, 252, 242));

        // University Label
        UniversityLabelTODO.setFont(new java.awt.Font("Segoe UI", 1, 36));
        UniversityLabelTODO.setForeground(new java.awt.Color(223, 109, 28));
        UniversityLabelTODO.setText(universityLogo);
        universityLabelPanel.add(UniversityLabelTODO);

        // Dividers
        topDivider.setForeground(new java.awt.Color(223, 109, 35));
        bottomDivider.setForeground(new java.awt.Color(223, 109, 35));

        // Labels configuration
        configureLabel(countryLabel, "Country:");
        configureLabel(stateLabel, "State:");
        configureLabel(domainLabel, "Domain:");
        configureLabel(webPageLabel, "Web Page:");
        configureLabel(alphaTwoCodeLabel, "Alpha Two Code:");
        configureLabel(contactLabel, "Contact:");
        configureLabel(facultiesLabel, "Faculties:");





        countryTodoPanel.setBackground(new java.awt.Color(252, 252, 242));
        stateTodoPanel.setBackground(new java.awt.Color(252, 252, 242));
        webPageTodoPanel.setBackground(new java.awt.Color(252, 252, 242));
        domainTodoPanel.setBackground(new java.awt.Color(252, 252, 242));
        alphaTwoCodeTodoPanel.setBackground(new java.awt.Color(252, 252, 242));
        contactTodoPanel.setBackground(new java.awt.Color(252, 252, 242));
        facultiesTodoPanel.setBackground(new java.awt.Color(252, 252, 242));

        // Update Button
        updateButton.setBackground(new java.awt.Color(223, 109, 35));
        updateButton.setForeground(Color.WHITE);
        updateButton.setText("Update University");
        updateButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        updateButton.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        updateButton.addActionListener(this::toggleEditMode);

        // Layout
        javax.swing.GroupLayout universityViewMainPanelLayout = new javax.swing.GroupLayout(universityViewMainPanel);
        universityViewMainPanel.setLayout(universityViewMainPanelLayout);


        universityViewMainPanelLayout.setHorizontalGroup(
                universityViewMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(topDivider)
                        .addComponent(bottomDivider)
                        .addGroup(universityViewMainPanelLayout.createSequentialGroup()
                                .addGap(1)
                                .addComponent(universityLabelPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 1043, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(universityViewMainPanelLayout.createSequentialGroup()
                                .addGap(50)
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
                                                .addGroup(universityViewMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(countryTodoPanel, 660, 660, 660)
                                                        .addComponent(stateTodoPanel, 660, 660, 660)
                                                        .addComponent(webPageTodoPanel, 660, 660, 660)
                                                        .addComponent(domainTodoPanel, 660, 660, 660)
                                                        .addComponent(alphaTwoCodeTodoPanel,660, 660, 660)
                                                        .addComponent(contactTodoPanel, 660, 660, 660)
                                                        .addComponent(facultiesTodoPanel, 660, 660, 660))))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(universityViewMainPanelLayout.createSequentialGroup()
                                .addGap(416)
                                .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 150, Short.MAX_VALUE))
        );

        universityViewMainPanelLayout.setVerticalGroup(
                universityViewMainPanelLayout.createSequentialGroup()
                        .addGap(20)
                        .addComponent(universityLabelPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17)
                        .addComponent(topDivider, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30)
                        .addGroup(universityViewMainPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(countryLabel)
                                .addComponent(countryTodoPanel))
                        .addGap(spaceY)
                        .addGroup(universityViewMainPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(stateLabel)
                                .addComponent(stateTodoPanel))
                        .addGap(spaceY)
                        .addGroup(universityViewMainPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(webPageLabel)
                                .addComponent(webPageTodoPanel))
                        .addGap(spaceY)
                        .addGroup(universityViewMainPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(domainLabel)
                                .addComponent(domainTodoPanel))
                        .addGap(spaceY)
                        .addGroup(universityViewMainPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(alphaTwoCodeLabel)
                                .addComponent(alphaTwoCodeTodoPanel))
                        .addGap(spaceYifFaculties)
                        .addGroup(universityViewMainPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(contactLabel)
                                .addComponent(contactTodoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40)
                        .addGroup(universityViewMainPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(facultiesLabel)
                                .addComponent(facultiesTodoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(89)
                        .addComponent(bottomDivider, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16)
                        .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(50, Short.MAX_VALUE)
        );



        this.add(new JScrollPane(universityViewMainPanel));

    }



//    private void updateVisibility() {
//        boolean isContactEmpty = TODOcontactLabel.getText().trim().isEmpty();
//        boolean isFacultiesEmpty = TODOfacultiesLabel.getText().trim().isEmpty();
//
//        // Ενημέρωση ορατότητας
//        contactLabel.setVisible(!isContactEmpty || updateOn);
//        contactTodoPanel.setVisible(!isContactEmpty || updateOn);
//        facultiesLabel.setVisible(!isFacultiesEmpty || updateOn);
//        facultiesTodoPanel.setVisible(!isFacultiesEmpty || updateOn);
//
//        // Ρύθμιση gaps
//        if (updateOn) {
//            spaceY = 10; // Επιθυμητό μικρότερο gap
//            spaceYifFaculties = 10;
//            spaceYBeforeBottomDivider = 10;
//        }
//        else if (!isContactEmpty && !isFacultiesEmpty) {
//            spaceY = 40;
//            spaceYifFaculties = 40;
//            spaceYBeforeBottomDivider = 30;
//        }
//        else if (!isContactEmpty || !isFacultiesEmpty) {
//            spaceY = 60;
//            spaceYBeforeBottomDivider = isFacultiesEmpty ? 45 : 86;
//            spaceYifFaculties = isFacultiesEmpty ? 40 : 20;
//        }
//        else {
//            spaceY = 100;
//            spaceYBeforeBottomDivider = 19;
//        }
//
//        revalidate();
//        repaint();
//    }


    private JPanel createTodoPanel(String todoLabel) {
        JPanel panel = new JPanel(new CardLayout());


        JTextArea viewArea = new JTextArea(todoLabel);
        viewArea.setLineWrap(true);
        viewArea.setWrapStyleWord(true);
        viewArea.setEditable(false);
        viewArea.setFont(new Font("Segoe UI", Font.PLAIN, 28));
        viewArea.setForeground(new Color(96, 59, 6));
        viewArea.setBackground(new Color(252, 252, 242));
        viewArea.setBorder(null);



        JTextArea editArea = new JTextArea();
        editArea.setLineWrap(true);
        editArea.setWrapStyleWord(true);
        editArea.setFont(new Font("Segoe UI", Font.PLAIN, 36));
        editArea.setForeground(new Color(96, 59, 6));
        editArea.setBackground(new Color(252, 252, 242));
        editArea.setBorder(null);

        panel.add(viewArea, "view");
        panel.add(editArea, "edit");

        return panel;
    }

    private void toggleEditMode(ActionEvent evt) {
        boolean isEditMode = updateButton.getText().equals("Save Changes");

        if (isEditMode) {
            saveTodoData();
            updateButton.setText("Update University");
        } else {
            loadTodoData();
            updateButton.setText("Save Changes");
        }

        setEditMode(!isEditMode);
    }

    private void setEditMode(boolean edit) {
        togglePanel(edit, countryTodoPanel);
        togglePanel(edit, stateTodoPanel);
        togglePanel(edit, webPageTodoPanel);
        togglePanel(edit, domainTodoPanel);
        togglePanel(edit, alphaTwoCodeTodoPanel);
        togglePanel(edit, contactTodoPanel);
        togglePanel(edit, facultiesTodoPanel);

        revalidate();
        repaint();
    }

    private void togglePanel(boolean edit, JPanel panel) {
        CardLayout cl = (CardLayout) panel.getLayout();
        cl.show(panel, edit ? "edit" : "view");
    }

    private void loadTodoData() {
        copyBetweenTextAreas(false); // Από view -> edit
    }

    private void saveTodoData() {
        copyBetweenTextAreas(true); // Από edit -> view
    }

    private void copyBetweenTextAreas(boolean fromEditToView) {
        JPanel[] panels = {countryTodoPanel, stateTodoPanel, webPageTodoPanel, domainTodoPanel,
                alphaTwoCodeTodoPanel, contactTodoPanel, facultiesTodoPanel};

        for (JPanel panel : panels) {
            JTextArea source = fromEditToView ? getEditTextArea(panel) : getViewTextArea(panel);
            JTextArea target = fromEditToView ? getViewTextArea(panel) : getEditTextArea(panel);

            if (source != null && target != null) {
                target.setText(source.getText());
            }
        }
    }

    private JTextArea getViewTextArea(JPanel panel) {
        for (Component c : panel.getComponents()) {
            if (c instanceof JTextArea && !((JTextArea) c).isEditable()) {
                return (JTextArea) c;
            }
        }
        return null;
    }

    private JTextArea getEditTextArea(JPanel panel) {
        for (Component c : panel.getComponents()) {
            if (c instanceof JTextArea && ((JTextArea) c).isEditable()) {
                return (JTextArea) c;
            }
        }
        return null;
    }


    private void configureLabel(JLabel label, String text) {
        label.setFont(new Font("Segoe UI", Font.PLAIN, 42));
        label.setForeground(new Color(223, 109, 35));
        label.setText(text);
    }

    private void configureTODO(JLabel label, String text) {
        label.setFont(new Font("Segoe UI", Font.PLAIN, 28));
        label.setForeground(new Color(96, 59, 6));
        label.setText(text);
    }



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
    private javax.swing.JPanel universityLabelPanel;

    private javax.swing.JPanel countryTodoPanel;
    private javax.swing.JPanel stateTodoPanel;
    private javax.swing.JPanel webPageTodoPanel;
    private javax.swing.JPanel domainTodoPanel;
    private javax.swing.JPanel alphaTwoCodeTodoPanel;
    private javax.swing.JPanel contactTodoPanel;
    private javax.swing.JPanel facultiesTodoPanel;
}