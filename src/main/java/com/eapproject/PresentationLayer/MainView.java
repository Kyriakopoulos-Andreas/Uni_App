package com.eapproject.PresentationLayer;

import com.eapproject.DataLayer.DB.University;
import com.eapproject.DataLayer.DB.UniversityDAO;
import com.eapproject.PresentationLayer.ViewModels.UniversitiesViewModel;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class MainView extends javax.swing.JFrame implements Observer {
    private UniversitiesViewModel viewModel;
    private ArrayList<University> universities;  // Η λίστα των πανεπιστημίων
    private boolean dataLoaded = false;  // Flag για να ελέγχουμε αν τα δεδομένα έχουν κατέβει
    private boolean pendingCountryView = false;
    private final UniversityDAO dao = UniversityDAO.getInstance();

    public MainView() {
        initComponents();
        
        // Ορισμός του εικονιδίου της εφαρμογής
        ImageIcon icon = new ImageIcon("resources/img/ico.png");
        this.setIconImage(icon.getImage());
        
        addMouseListeners();
        setResizable(false);

        universities = new ArrayList<>();
        viewModel = new UniversitiesViewModel();
        viewModel.addObserver(this);

        new Thread(() -> {
            viewModel.fetchUniversities();
        }).start();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof UniversitiesViewModel) {
            UniversitiesViewModel model = (UniversitiesViewModel) o;
            universities = (ArrayList<University>) model.getUniversities();

            if (!universities.isEmpty()) {
                dataLoaded = true;
                System.out.println("Test" + dao.getAllUniversities());
                // Αν ο χρήστης είχε πατήσει το "Countries" πριν φορτωθούν τα δεδομένα, το ανοίγουμε αυτόματα
                if (pendingCountryView) {
                    openCountryView();
                    pendingCountryView = false; // Μηδενίζουμε το flag
                }
            } else {
                System.out.println("Δεν βρέθηκαν πανεπιστήμια!");
                dataLoaded = false;
            }
        }
    }

    private void openCountryView() {
        setActiveButton(countriesPanel);

        jLabel1.setIcon(null);
        jLabel1.setPreferredSize(new java.awt.Dimension(0, 0));
        rightScreenPanel.removeAll();
        rightScreenPanel.setLayout(new CardLayout());
        rightScreenPanel.add(new CountryView(rightScreenPanel, universities, viewModel), "CountryView");
        rightScreenPanel.revalidate();
        rightScreenPanel.repaint();
        ((CardLayout) rightScreenPanel.getLayout()).show(rightScreenPanel, "CountryView");
    }

    // Adds an ActionListener to the OutLinedTextField to handle the "Enter" key press
    private void addOutlinedTextFieldEnterListener() {
        OutLinedTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onEnterPressed();
            }
        });
    }

    // Method executed when Enter is pressed in the OutLinedTextField
    private void onEnterPressed() {
        System.out.println("Right Screen Panel Dimensions:");
        System.out.println("Width: " + rightScreenPanel.getWidth() + ", Height: " + rightScreenPanel.getHeight());
        String searchText = OutLinedTextField.getText();

        viewModel.getUniversityFromList(searchText, universities);
        if (!viewModel.getUniversityFromList().getName().isEmpty()) {
            // Clears the panel and resets its layout
            jLabel1.setIcon(null);
            jLabel1.setPreferredSize(new java.awt.Dimension(0, 0));
            rightScreenPanel.removeAll();
            rightScreenPanel.setLayout(new CardLayout());

            UniversityView panel = new UniversityView(viewModel.getUniversityFromList(), viewModel, universities );

            System.out.println("Adding UniversityView2...");
            System.out.println("Panel size: " + panel.getWidth() + "x" + panel.getHeight());

            rightScreenPanel.add(panel, "universityView");
            rightScreenPanel.revalidate();
            rightScreenPanel.repaint();

            // Activates and displays the new card (UniversityView2)
            CardLayout layout = (CardLayout) rightScreenPanel.getLayout();
            layout.show(rightScreenPanel, "universityView");
            System.out.println("UniversityView2 displayed!");
        }
    }

    // Handles the event when the CountriesPanel is clicked
    private void onCountriesPanelClick(java.awt.event.MouseEvent evt) {
        System.out.println("Countries panel clicked!");
        // Προσθέστε εδώ την επιθυμητή λειτουργία (π.χ. μετάβαση σε άλλη φόρμα)
    }

    // Handles the event when the StatisticsPanel is clicked
    private void onStatisticsPanelClick(java.awt.event.MouseEvent evt) {
        System.out.println("Statistics panel clicked!");
    }

    // Handles the event when the InformationsPanel is clicked
    private void onInformationsPanelClick(java.awt.event.MouseEvent evt) {
        System.out.println("Informations panel clicked!");
        // Προσθέστε εδώ την επιθυμητή λειτουργία
    }

    // Handles the event when the ExitPanel is clicked
    private void onExitPanelClick(java.awt.event.MouseEvent evt) {
        System.out.println("Exit panel clicked!");
        System.exit(0); // Τερματισμός της εφαρμογής
    }

    private void initComponents() {
        this.setTitle("UniApp");

        mainPanel = new javax.swing.JPanel();
        sideMenuPanel = new javax.swing.JPanel();
        title = new javax.swing.JLabel();
        countriesPanel = new javax.swing.JPanel();
        countriesImage = new javax.swing.JLabel();
        countriesLabel = new javax.swing.JLabel();
        exitPanel = new javax.swing.JPanel();
        exitImage = new javax.swing.JLabel();
        exitLabel = new javax.swing.JLabel();
        statisticsPanel = new javax.swing.JPanel();
        statisticsImage = new javax.swing.JLabel();
        statisticsLabel = new javax.swing.JLabel();
        infotmationsPanel = new javax.swing.JPanel();
        informationImage = new javax.swing.JLabel();
        informarionsLabel = new javax.swing.JLabel();
        outLinedTextFieldPanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        OutLinedTextField = new javax.swing.JTextField();
        rightScreenPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        outLinedTextFieldPanel.setFocusable(true);

        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                if (!OutLinedTextField.getBounds().contains(evt.getPoint())) {
                    OutLinedTextField.transferFocus();
                }
            }
        });

        OutLinedTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (OutLinedTextField.getText().isEmpty()) {
                    OutLinedTextField.setText("Search University");
                    OutLinedTextField.setForeground(new java.awt.Color(169, 169, 169));
                }
            }
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (OutLinedTextField.getText().equals("Search University")) {
                    OutLinedTextField.setText("");
                    OutLinedTextField.setForeground(new java.awt.Color(0, 0, 0));
                }
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        sideMenuPanel.setBackground(new java.awt.Color(178, 112, 69));
        sideMenuPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                sideMenuPanelMouseDragged(evt);
            }
        });
        OutLinedTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (OutLinedTextField.getText().equals("Search University")) {
                    OutLinedTextField.setText("");
                    OutLinedTextField.setForeground(new java.awt.Color(0, 0, 0));
                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (OutLinedTextField.getText().isEmpty()) {
                    OutLinedTextField.setText("Search University");
                    OutLinedTextField.setForeground(new java.awt.Color(169, 169, 169));
                }
            }
        });

        // Set the title and logo at the top of the menu, στοιχισμένα στο κέντρο
        title.setBackground(new java.awt.Color(255, 255, 255));
        title.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        title.setForeground(new java.awt.Color(255, 255, 255));
        title.setText("UniApp");

        // Φόρτωση και κλιμάκωση του logo στο 50% των αρχικών διαστάσεων
        ImageIcon logoIcon = new ImageIcon("resources/img/logo_white.png");
        Image logoImage = logoIcon.getImage();
        int newWidth = logoIcon.getIconWidth() / 2;
        int newHeight = logoIcon.getIconHeight() / 2;
        Image scaledLogoImage = logoImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledLogoIcon = new ImageIcon(scaledLogoImage);
        JLabel logoLabel = new JLabel(scaledLogoIcon);

        // Δημιουργία του panel για την ενότητα "Countries"
        countriesPanel.setBackground(new java.awt.Color(187, 124, 82));
        String flagPathImage = "resources/img/flag.png";
        countriesImage.setIcon(new javax.swing.ImageIcon(flagPathImage));
        countriesLabel.setBackground(new java.awt.Color(255, 255, 255));
        countriesLabel.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        countriesLabel.setForeground(new java.awt.Color(255, 255, 255));
        countriesLabel.setText("Countries");

        javax.swing.GroupLayout countriesPanelLayout = new javax.swing.GroupLayout(countriesPanel);
        countriesPanel.setLayout(countriesPanelLayout);
        countriesPanelLayout.setHorizontalGroup(
            countriesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(countriesPanelLayout.createSequentialGroup()
                    .addGap(69, 69, 69)
                    .addComponent(countriesImage)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(countriesLabel)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        countriesPanelLayout.setVerticalGroup(
            countriesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(countriesImage, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(countriesPanelLayout.createSequentialGroup()
                    .addGap(30, 30, 30)
                    .addComponent(countriesLabel)
                    .addContainerGap(19, Short.MAX_VALUE))
        );

        exitPanel.setBackground(new java.awt.Color(187, 124, 82));
        String exitPathImage = "resources/img/exit1.png";
        exitImage.setIcon(new javax.swing.ImageIcon(exitPathImage));
        exitLabel.setBackground(new java.awt.Color(255, 255, 255));
        exitLabel.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        exitLabel.setForeground(new java.awt.Color(255, 255, 255));
        exitLabel.setText("Exit");

        javax.swing.GroupLayout exitPanelLayout = new javax.swing.GroupLayout(exitPanel);
        exitPanel.setLayout(exitPanelLayout);
        exitPanelLayout.setHorizontalGroup(
            exitPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(exitPanelLayout.createSequentialGroup()
                    .addGap(69, 69, 69)
                    .addComponent(exitImage)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(exitLabel)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        exitPanelLayout.setVerticalGroup(
            exitPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(exitImage, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(exitPanelLayout.createSequentialGroup()
                    .addGap(17, 17, 17)
                    .addComponent(exitLabel)
                    .addContainerGap(19, Short.MAX_VALUE))
        );

        statisticsPanel.setBackground(new java.awt.Color(187, 124, 82));
        String statsPathImage = "resources/img/stats.png";
        statisticsImage.setIcon(new javax.swing.ImageIcon(statsPathImage));
        statisticsLabel.setBackground(new java.awt.Color(255, 255, 255));
        statisticsLabel.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        statisticsLabel.setForeground(new java.awt.Color(255, 255, 255));
        statisticsLabel.setText("Statistics");

        javax.swing.GroupLayout statisticsPanelLayout = new javax.swing.GroupLayout(statisticsPanel);
        statisticsPanel.setLayout(statisticsPanelLayout);
        statisticsPanelLayout.setHorizontalGroup(
            statisticsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(statisticsPanelLayout.createSequentialGroup()
                    .addGap(69, 69, 69)
                    .addComponent(statisticsImage)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(statisticsLabel)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        statisticsPanelLayout.setVerticalGroup(
            statisticsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(statisticsImage, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statisticsPanelLayout.createSequentialGroup()
                    .addGap(17, 17, 17)
                    .addComponent(statisticsLabel)
                    .addContainerGap(19, Short.MAX_VALUE))
        );

        infotmationsPanel.setBackground(new java.awt.Color(187, 124, 82));
        String InfoPathImage = "resources/img/info.png";
        informationImage.setIcon(new javax.swing.ImageIcon(InfoPathImage));
        informarionsLabel.setBackground(new java.awt.Color(255, 255, 255));
        informarionsLabel.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        informarionsLabel.setForeground(new java.awt.Color(255, 255, 255));
        informarionsLabel.setText("Information's");

        javax.swing.GroupLayout infotmationsPanelLayout = new javax.swing.GroupLayout(infotmationsPanel);
        infotmationsPanel.setLayout(infotmationsPanelLayout);
        infotmationsPanelLayout.setHorizontalGroup(
            infotmationsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(infotmationsPanelLayout.createSequentialGroup()
                    .addGap(69, 69, 69)
                    .addComponent(informationImage)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(informarionsLabel)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        infotmationsPanelLayout.setVerticalGroup(
            infotmationsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(informationImage, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(infotmationsPanelLayout.createSequentialGroup()
                    .addGap(17, 17, 17)
                    .addComponent(informarionsLabel)
                    .addContainerGap(19, Short.MAX_VALUE))
        );

        outLinedTextFieldPanel.setBackground(new java.awt.Color(187, 124, 82));
        jPanel2.setBackground(new java.awt.Color(187, 124, 82));
        OutLinedTextField.setBackground(new java.awt.Color(255, 255, 255));
        OutLinedTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        OutLinedTextField.setText("Search University");
        OutLinedTextField.setForeground(new java.awt.Color(169, 169, 169));
        OutLinedTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OutLinedTextFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(28, 28, 28)
                    .addComponent(OutLinedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(OutLinedTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                    .addContainerGap())
        );

        javax.swing.GroupLayout outLinedTextFieldPanelLayout = new javax.swing.GroupLayout(outLinedTextFieldPanel);
        outLinedTextFieldPanel.setLayout(outLinedTextFieldPanelLayout);
        outLinedTextFieldPanelLayout.setHorizontalGroup(
            outLinedTextFieldPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(outLinedTextFieldPanelLayout.createSequentialGroup()
                    .addGap(17, 17, 17)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(17, Short.MAX_VALUE))
        );
        outLinedTextFieldPanelLayout.setVerticalGroup(
            outLinedTextFieldPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, outLinedTextFieldPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap())
        );

        // Στοιχειοθέτηση του logo και του τίτλου στο κέντρο του sideMenuPanel
        javax.swing.GroupLayout sideMenuPanelLayout = new javax.swing.GroupLayout(sideMenuPanel);
        sideMenuPanel.setLayout(sideMenuPanelLayout);
        sideMenuPanelLayout.setHorizontalGroup(
            sideMenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                .addGroup(sideMenuPanelLayout.createSequentialGroup()
                    .addComponent(logoLabel)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(title))
                .addComponent(outLinedTextFieldPanel, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(countriesPanel, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(statisticsPanel, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(infotmationsPanel, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(exitPanel, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        sideMenuPanelLayout.setVerticalGroup(
            sideMenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(sideMenuPanelLayout.createSequentialGroup()
                    .addGap(41, 41, 41)
                    .addGroup(sideMenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(logoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, logoLabel.getPreferredSize().height, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(title))
                    .addGap(70, 70, 70)
                    .addComponent(outLinedTextFieldPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(91, 91, 91)
                    .addComponent(countriesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, 0)
                    .addComponent(statisticsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, 0)
                    .addComponent(infotmationsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 187, Short.MAX_VALUE)
                    .addComponent(exitPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(54, 54, 54))
        );

        rightScreenPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                rightScreenPanelMouseDragged(evt);
            }
        });

        String backGroundPathImage = "resources/img/background_image.jpg";
        ImageIcon originalImageIcon = new ImageIcon(backGroundPathImage);
        Image image = originalImageIcon.getImage();
        Image scaledImage = image.getScaledInstance(1400, 960, Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
        jLabel1.setIcon(scaledImageIcon);
        jLabel1.setPreferredSize(new java.awt.Dimension(1400, 800));

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(jLabel1, BorderLayout.CENTER);
        mainPanel.add(sideMenuPanel, BorderLayout.WEST);

        rightScreenPanel.setLayout(new java.awt.CardLayout());
        mainPanel.add(rightScreenPanel, BorderLayout.EAST);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pack();
    }

    private void sideMenuPanelMouseDragged(java.awt.event.MouseEvent evt) {
        // Future functionality placeholder.
    }

    private void OutLinedTextFieldActionPerformed(java.awt.event.ActionEvent evt) {
        // Handle actions when Enter is pressed in the outlined text field.
    }

    private void rightScreenPanelMouseDragged(java.awt.event.MouseEvent evt) {
        // Future functionality placeholder.
    }

    // Swing component declarations
    private javax.swing.JTextField OutLinedTextField;
    private javax.swing.JLabel countriesImage, countriesLabel, exitImage, exitLabel, informarionsLabel, informationImage, statisticsImage, statisticsLabel, title;
    private javax.swing.JPanel countriesPanel, exitPanel, infotmationsPanel, mainPanel, outLinedTextFieldPanel, rightScreenPanel, sideMenuPanel, statisticsPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;

    // State variables for managing the active button and panel colors
    private JPanel activeButton = null;
    private final Map<JPanel, Color> initialColors = new HashMap<>();

    private void setActiveButton(JPanel newActiveButton) {
        if (activeButton != null && activeButton != newActiveButton) {
            activeButton.setBackground(initialColors.get(activeButton));
        }
        activeButton = newActiveButton;
        activeButton.setBackground(new java.awt.Color(139, 89, 61));
    }

    private void addMouseListeners() {
        initialColors.put(countriesPanel, countriesPanel.getBackground());
        initialColors.put(statisticsPanel, statisticsPanel.getBackground());
        initialColors.put(infotmationsPanel, infotmationsPanel.getBackground());
        initialColors.put(exitPanel, exitPanel.getBackground());

        countriesPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                setActiveButton(countriesPanel);
                if (!dataLoaded) {
                    pendingCountryView = true;
                    return;
                }
                openCountryView();
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                onCountriesPanelClick(evt);
            }
        });

        statisticsPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                setActiveButton(statisticsPanel);
                viewModel.fetchStats();
                if(!viewModel.getStatisticsList().isEmpty()) {
                    jLabel1.setIcon(null);
                    jLabel1.setPreferredSize(new java.awt.Dimension(0, 0));
                    rightScreenPanel.removeAll();
                    rightScreenPanel.setLayout(new CardLayout());
                    rightScreenPanel.add(new StatisticsView(viewModel.getStatisticsList(), viewModel), "StatisticsView");
                    rightScreenPanel.revalidate();
                    rightScreenPanel.repaint();
                    ((CardLayout) rightScreenPanel.getLayout()).show(rightScreenPanel, "StatisticsView");
                }
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                onStatisticsPanelClick(evt);
            }
        });

        infotmationsPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                setActiveButton(infotmationsPanel);
                jLabel1.setIcon(null);
                jLabel1.setPreferredSize(new java.awt.Dimension(0, 0));
                rightScreenPanel.removeAll();
                rightScreenPanel.setLayout(new CardLayout());
                rightScreenPanel.add(new InformationView(), "InformationView");
                rightScreenPanel.revalidate();
                rightScreenPanel.repaint();
                ((CardLayout) rightScreenPanel.getLayout()).show(rightScreenPanel, "InformationView");
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                onInformationsPanelClick(evt);
            }
        });

        exitPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                setActiveButton(exitPanel);
                System.exit(0);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                onExitPanelClick(evt);
            }
        });

        addOutlinedTextFieldEnterListener();
    }
}
