package com.eapproject.PresentationLayer.UniversityView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class UniversityView extends JPanel {

    private String universityLogo;
    // Χώροι (constants για τα gaps)
    private final int SPACE_Y = 40;
    private final int SPACE_Y_BEFORE_BOTTOM_DIVIDER = 30;
    private final int SPACE_Y_IF_FACULTIES = 40;

    // Ορισμός σταθερών για τις κάρτες
    private static final String CARD_VIEW = "view";
    private static final String CARD_EDIT = "edit";

    // Components
    private JPanel universityViewMainPanel;
    private JLabel universityNameLabel;
    private JSeparator topDivider;
    private JLabel countryLabel;
    private JLabel stateLabel;
    private JLabel domainLabel;
    private JLabel webPageLabel;
    private JLabel alphaTwoCodeLabel;
    private JSeparator bottomDivider;
    private JButton updateButton;
    private JLabel contactLabel;
    private JLabel facultiesLabel;
    private JPanel universityLabelPanel;

    private JPanel countryTodoPanel;
    private JPanel stateTodoPanel;
    private JPanel webPageTodoPanel;
    private JPanel domainTodoPanel;
    private JPanel alphaTwoCodeTodoPanel;
    private JPanel contactTodoPanel;
    private JPanel facultiesTodoPanel;

    public UniversityView(String university) {
        this.universityLogo = university;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(2000, 600));

        // Δημιουργία των panels
        universityViewMainPanel = new JPanel();
        universityLabelPanel = new JPanel();
        universityNameLabel = new JLabel();
        topDivider = new JSeparator();
        bottomDivider = new JSeparator();
        updateButton = new JButton();

        // Δημιουργία των labels
        countryLabel = new JLabel();
        stateLabel = new JLabel();
        domainLabel = new JLabel();
        webPageLabel = new JLabel();
        alphaTwoCodeLabel = new JLabel();
        contactLabel = new JLabel();
        facultiesLabel = new JLabel();

        // Δημιουργία των edit panels χρησιμοποιώντας βοηθητική μέθοδο
        countryTodoPanel = createTodoPanel("TODO country");
        stateTodoPanel = createTodoPanel("TODO state");
        webPageTodoPanel = createTodoPanel("TODO web page");
        domainTodoPanel = createTodoPanel("TODO domain");
        alphaTwoCodeTodoPanel = createTodoPanel("TODO alpha two code");
        contactTodoPanel = createTodoPanel("TODO contact");
        facultiesTodoPanel = createTodoPanel("TODO faculties");

        // Ορισμός χρωμάτων
        Color backgroundColor = new Color(252, 252, 242);
        universityViewMainPanel.setBackground(backgroundColor);
        universityLabelPanel.setBackground(backgroundColor);
        countryTodoPanel.setBackground(backgroundColor);
        stateTodoPanel.setBackground(backgroundColor);
        webPageTodoPanel.setBackground(backgroundColor);
        domainTodoPanel.setBackground(backgroundColor);
        alphaTwoCodeTodoPanel.setBackground(backgroundColor);
        contactTodoPanel.setBackground(backgroundColor);
        facultiesTodoPanel.setBackground(backgroundColor);

        // Ορισμός του University Name Label
        universityNameLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        universityNameLabel.setForeground(new Color(223, 109, 28));
        universityNameLabel.setText(universityLogo);
        universityLabelPanel.add(universityNameLabel);

        // Ορισμός των dividers
        topDivider.setForeground(new Color(223, 109, 35));
        bottomDivider.setForeground(new Color(223, 109, 35));

        // Ρύθμιση των υπόλοιπων labels μέσω βοηθητικής μεθόδου
        configureLabel(countryLabel, "Country:");
        configureLabel(stateLabel, "State:");
        configureLabel(domainLabel, "Domain:");
        configureLabel(webPageLabel, "Web Page:");
        configureLabel(alphaTwoCodeLabel, "Alpha Two Code:");
        configureLabel(contactLabel, "Contact:");
        configureLabel(facultiesLabel, "Faculties:");

        // Ορισμός του Update Button
        updateButton.setBackground(new Color(223, 109, 35));
        updateButton.setForeground(Color.WHITE);
        updateButton.setText("Update University");
        updateButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        updateButton.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        updateButton.addActionListener(this::toggleEditMode);

        // Δημιουργία του layout για το main panel (GroupLayout)
        GroupLayout layout = new GroupLayout(universityViewMainPanel);
        universityViewMainPanel.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(topDivider)
                .addComponent(bottomDivider)
                .addGroup(layout.createSequentialGroup()
                    .addGap(1)
                    .addComponent(universityLabelPanel, GroupLayout.PREFERRED_SIZE, 1043, GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createSequentialGroup()
                    .addGap(50)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(countryLabel)
                                .addComponent(stateLabel)
                                .addComponent(webPageLabel)
                                .addComponent(domainLabel)
                                .addComponent(alphaTwoCodeLabel)
                                .addComponent(contactLabel)
                                .addComponent(facultiesLabel))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(countryTodoPanel, 660, 660, 660)
                                .addComponent(stateTodoPanel, 660, 660, 660)
                                .addComponent(webPageTodoPanel, 660, 660, 660)
                                .addComponent(domainTodoPanel, 660, 660, 660)
                                .addComponent(alphaTwoCodeTodoPanel, 660, 660, 660)
                                .addComponent(contactTodoPanel, 660, 660, 660)
                                .addComponent(facultiesTodoPanel, 660, 660, 660)))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(416)
                            .addComponent(updateButton, GroupLayout.PREFERRED_SIZE, 229, GroupLayout.PREFERRED_SIZE)
                            .addGap(50, 150, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addGap(20)
                .addComponent(universityLabelPanel, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
                .addGap(17)
                .addComponent(topDivider, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                .addGap(30)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(countryLabel)
                    .addComponent(countryTodoPanel))
                .addGap(SPACE_Y)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(stateLabel)
                    .addComponent(stateTodoPanel))
                .addGap(SPACE_Y)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(webPageLabel)
                    .addComponent(webPageTodoPanel))
                .addGap(SPACE_Y)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(domainLabel)
                    .addComponent(domainTodoPanel))
                .addGap(SPACE_Y)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(alphaTwoCodeLabel)
                    .addComponent(alphaTwoCodeTodoPanel))
                .addGap(SPACE_Y_IF_FACULTIES)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(contactLabel)
                    .addComponent(contactTodoPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(40)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(facultiesLabel)
                    .addComponent(facultiesTodoPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(89)
                .addComponent(bottomDivider, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                .addGap(16)
                .addComponent(updateButton, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE)
        );

        // Προσθέτουμε το κύριο panel μέσα σε JScrollPane
        add(new JScrollPane(universityViewMainPanel));
    }

    private JPanel createTodoPanel(String todoLabel) {
        JPanel panel = new JPanel(new CardLayout());
        // Δημιουργία view text area
        JTextArea viewArea = new JTextArea(todoLabel);
        viewArea.setLineWrap(true);
        viewArea.setWrapStyleWord(true);
        viewArea.setEditable(false);
        viewArea.setFont(new Font("Segoe UI", Font.PLAIN, 28));
        viewArea.setForeground(new Color(96, 59, 6));
        viewArea.setBackground(new Color(252, 252, 242));
        viewArea.setBorder(null);

        // Δημιουργία edit text area
        JTextArea editArea = new JTextArea();
        editArea.setLineWrap(true);
        editArea.setWrapStyleWord(true);
        editArea.setFont(new Font("Segoe UI", Font.PLAIN, 36));
        editArea.setForeground(new Color(96, 59, 6));
        editArea.setBackground(new Color(252, 252, 242));
        editArea.setBorder(null);

        panel.add(viewArea, CARD_VIEW);
        panel.add(editArea, CARD_EDIT);
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
        cl.show(panel, edit ? CARD_EDIT : CARD_VIEW);
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
}