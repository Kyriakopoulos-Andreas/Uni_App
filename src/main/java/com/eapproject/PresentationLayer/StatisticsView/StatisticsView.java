package com.eapproject.PresentationLayer.StatisticsView;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class StatisticsView extends JPanel {

    // Ορισμός σταθερών για χρώματα, γραμματοσειρές και διαστάσεις
    private static final Color BACKGROUND_COLOR = new Color(252, 252, 242);
    private static final Color ORANGE_COLOR = new Color(223, 109, 35);
    private static final Color HEADER_FG_COLOR = Color.WHITE;
    private static final Color CELL_FG_COLOR = new Color(139, 89, 61);
    private static final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font TABLE_FONT = new Font("Segoe UI", Font.PLAIN, 24);
    private static final Font LABEL_FONT = new Font("Segoe UI", Font.BOLD, 46);
    private static final Font CELL_RENDERER_FONT = new Font("Arial", Font.BOLD, 28);

    // Variables declaration
    private JSeparator topSeparator;
    private JSeparator bottomSeparator;
    private JLabel statisticsLabel;
    private JScrollPane jScrollPane1;
    private JTable statsTable;
    private JButton exportButton;
    private JPanel mainPanel;
    // End of variables declaration

    public StatisticsView() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        // Αρχικοποίηση των components
        mainPanel = new JPanel();
        topSeparator = new JSeparator();
        bottomSeparator = new JSeparator();
        statisticsLabel = new JLabel();
        jScrollPane1 = new JScrollPane();
        statsTable = new JTable();
        exportButton = new JButton();

        // Ρυθμίσεις για το mainPanel
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setPreferredSize(new Dimension(1050, 960));
        this.setPreferredSize(new Dimension(1050, 960));

        // Διαμορφώνουμε τους διαχωριστές (separators)
        topSeparator.setBackground(ORANGE_COLOR);
        topSeparator.setForeground(ORANGE_COLOR);
        topSeparator.setFont(new Font("Segoe UI", Font.PLAIN, 36));

        bottomSeparator.setBackground(ORANGE_COLOR);
        bottomSeparator.setForeground(ORANGE_COLOR);

        // Ρύθμιση του Label για τα στατιστικά
        statisticsLabel.setBackground(ORANGE_COLOR);
        statisticsLabel.setFont(LABEL_FONT);
        statisticsLabel.setForeground(ORANGE_COLOR);
        statisticsLabel.setText("Statistics");

        // Ρύθμιση του πίνακα (statsTable)
        statsTable.setBackground(Color.WHITE);
        statsTable.setBorder(BorderFactory.createLineBorder(ORANGE_COLOR, 2));
        statsTable.setFont(TABLE_FONT);
        statsTable.setForeground(new Color(96, 59, 6));
        statsTable.getTableHeader().setReorderingAllowed(false);
        statsTable.setShowGrid(false);
        statsTable.setGridColor(ORANGE_COLOR);
        statsTable.setIntercellSpacing(new Dimension(0, 0));
        statsTable.getTableHeader().setBackground(ORANGE_COLOR);
        statsTable.getTableHeader().setForeground(HEADER_FG_COLOR);
        statsTable.getTableHeader().setFont(HEADER_FONT);
        statsTable.setCellSelectionEnabled(true);
        statsTable.setFocusable(false);
        statsTable.setDefaultEditor(Object.class, null);
        statsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        statsTable.setRowSelectionAllowed(true);
        statsTable.setColumnSelectionAllowed(false);
        statsTable.setSelectionBackground(new Color(187, 124, 82, 200));
        statsTable.setSelectionForeground(Color.WHITE);
        statsTable.setRowHeight(40);

        // Ορισμός μοντέλου για τον πίνακα (παράδειγμα δεδομένων)
        statsTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                        {"Harvard University", 1200},
                        {"Stanford University", 950},
                        {"MIT", 1100},
                        {"University of Oxford", 890},
                        {"University of Cambridge", 870},
                        {"ETH Zurich", 740},
                        {"National University of Singapore", 680},
                        {"University of Tokyo", 720}
                },
                new String[]{"University", "Views"}
        ) {
            boolean[] canEdit = new boolean[]{false, false};

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        statsTable.setFillsViewportHeight(true);

        // Εφαρμογή custom renderer για στοίχιση και στυλ στα κελιά του πίνακα
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        centerRenderer.setFont(CELL_RENDERER_FONT);
        centerRenderer.setForeground(CELL_FG_COLOR);
        for (int i = 0; i < statsTable.getColumnCount(); i++) {
            statsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        jScrollPane1.setViewportView(statsTable);

        // Ρύθμιση του κουμπιού εξαγωγής σε PDF
        exportButton.setBackground(ORANGE_COLOR);
        exportButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        exportButton.setForeground(Color.WHITE);
        exportButton.setText("Export PDF");
        exportButton.setBorder(null);

        // Ορισμός του layout με GroupLayout για το mainPanel
        GroupLayout mainPanelLayout = new GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(topSeparator, GroupLayout.Alignment.TRAILING)
                .addComponent(bottomSeparator)
                .addGroup(GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                        .addContainerGap(500, Short.MAX_VALUE)
                        .addGap(100)
                        .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 849, GroupLayout.PREFERRED_SIZE)
                        .addGap(420))
                .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addGroup(mainPanelLayout.createSequentialGroup()
                                        .addGap(420)
                                        .addComponent(statisticsLabel))
                                .addGroup(mainPanelLayout.createSequentialGroup()
                                        .addGap(416)
                                        .addComponent(exportButton, GroupLayout.PREFERRED_SIZE, 229, GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(12)
                        .addComponent(statisticsLabel)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGap(13)
                        .addComponent(topSeparator, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                        .addGap(58)
                        .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 610, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
                        .addComponent(bottomSeparator, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                        .addGap(15)
                        .addComponent(exportButton, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
                        .addGap(40))
        );

        // Προσθήκη του mainPanel στο JPanel (StatisticsView)
        this.add(mainPanel);
    }
}