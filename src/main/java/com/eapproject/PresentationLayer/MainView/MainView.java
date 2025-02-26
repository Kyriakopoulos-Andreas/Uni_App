
package com.eapproject.PresentationLayer.MainView;

import com.eapproject.CommonLayer.Source.RepositoryCallback;
import com.eapproject.DB.University;
import com.eapproject.DB.UniversityDAO;
import com.eapproject.DataLayer.UniversitiesViewModel;
import com.eapproject.PresentationLayer.CountryView.CountryView;
import com.eapproject.PresentationLayer.InformationView.InformationView;
import com.eapproject.PresentationLayer.StatisticsView.StatisticsView;
import com.eapproject.PresentationLayer.UniversityView.UniversityView;

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
        addMouseListeners();
        setResizable(false);

        universities = new ArrayList<>();
        viewModel = new UniversitiesViewModel();
        viewModel.addObserver(this);  // Εγγραφή ως παρατηρητής
        // Δημιουργούμε νέο νήμα για καθυστέρηση πριν καλέσουμε την fetchUniversities
        new Thread(() -> {

            // Αφού περάσει η καθυστέρηση, καλούμε την fetchUniversities
            System.out.println("🔄 Ξεκινά η λήψη δεδομένων...");
            viewModel.fetchUniversities(); // Ξεκινά την ανάκτηση των δεδομένων
        }).start();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof UniversitiesViewModel) {
            UniversitiesViewModel model = (UniversitiesViewModel) o;

            universities = (ArrayList<University>) model.getUniversities();


            if (!universities.isEmpty()) {
//                System.out.println("✅ Πανεπιστήμια που φορτώθηκαν:");
//                for (University uni : universities) {
//                    System.out.println(uni.getName() + " - " + uni.getCountry());
//                }
                dataLoaded = true;
                System.out.println("Test" + dao.getAllUniversities());
                // Αν ο χρήστης είχε πατήσει το "Countries" πριν φορτωθούν τα δεδομένα, το ανοίγουμε αυτόματα
                if (pendingCountryView) {
                    openCountryView();
                    System.out.println("Test" + dao.getAllUniversities());
                    pendingCountryView = false; // Μηδενίζουμε το flag
                }

            } else {
                System.out.println("❌ Δεν βρέθηκαν πανεπιστήμια!");
                dataLoaded = false;  // Αν κάτι πάει λάθος ορίζουμε το flag σε true μόλις τα δεδομένα είναι έτοιμα
            }
        }
    }

    private void openCountryView() {
        setActiveButton(countriesPanel);

        jLabel1.setIcon(null);
        jLabel1.setPreferredSize(new java.awt.Dimension(0, 0));
        rightScreenPanel.removeAll();
        rightScreenPanel.setLayout(new CardLayout());
        rightScreenPanel.add(new CountryView(rightScreenPanel, universities), "CountryView");
        rightScreenPanel.revalidate();
        rightScreenPanel.repaint();
        ((CardLayout) rightScreenPanel.getLayout()).show(rightScreenPanel, "CountryView");
    }

    // Adds an ActionListener to the OutLinedTextField to handle the "Enter" key press
    private void addOutlinedTextFieldEnterListener() {
        OutLinedTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // Call the method to handle actions when the Enter key is pressed
                onEnterPressed();
            }
        });
    }

    // Method executed when Enter is pressed in the OutLinedTextField
    private void onEnterPressed() {
        System.out.println("Right Screen Panel Dimensions:");
        System.out.println("Width: " + rightScreenPanel.getWidth() + ", Height: " + rightScreenPanel.getHeight());
        String searchText = OutLinedTextField.getText();

        // Clears the panel and resets its layout
        jLabel1.setIcon(null); // Removes any icon from jLabel1
        jLabel1.setPreferredSize(new java.awt.Dimension(0, 0)); // Resets preferred size of jLabel1
        rightScreenPanel.removeAll(); // Removes all components from the panel
        rightScreenPanel.setLayout(new CardLayout()); // Sets CardLayout for switching views

        // Creates and adds a new panel (UniversityView2) to the rightScreenPanel
        UniversityView panel = new UniversityView(searchText);

        System.out.println("Adding UniversityView2...");
        System.out.println("Panel size: " + panel.getWidth() + "x" + panel.getHeight());

        rightScreenPanel.add(panel, "universityView"); // Adds the new panel with a unique key
        rightScreenPanel.revalidate(); // Refreshes the layout
        rightScreenPanel.repaint(); // Repaints the panel

        // Activates and displays the new card (UniversityView2)
        CardLayout layout = (CardLayout) rightScreenPanel.getLayout();
        layout.show(rightScreenPanel, "universityView");
        System.out.println("UniversityView2 displayed!");
    }

    // Handles the event when the CountriesPanel is clicked
    private void onCountriesPanelClick(java.awt.event.MouseEvent evt) {
        System.out.println("Countries panel clicked!");
        // Add your functionality here (e.g., navigate to another form)
    }

    // Handles the event when the StatisticsPanel is clicked
    private void onStatisticsPanelClick(java.awt.event.MouseEvent evt) {
        System.out.println("Statistics panel clicked!");

    }

    // Handles the event when the InformationsPanel is clicked
    private void onInformationsPanelClick(java.awt.event.MouseEvent evt) {
        System.out.println("Informations panel clicked!");
        // Add your functionality here
    }

    // Handles the event when the ExitPanel is clicked
    private void onExitPanelClick(java.awt.event.MouseEvent evt) {
        System.out.println("Exit panel clicked!");
        System.exit(0); // Terminates the application
    }


    private void initComponents() {
        // Initialize the main panels and UI components
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
                    // Αφαίρεση του focus από το OutLinedTextField
                    OutLinedTextField.transferFocus();
                }
            }
        });

        // Add a mouse listener to OutLinedTextField to transfer focus when clicked outside
        // Add a mouse listener to OutLinedTextField to transfer focus when clicked outside
        OutLinedTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (OutLinedTextField.getText().isEmpty()) {
                    // Αν το πεδίο είναι άδειο, επαναφέρουμε την υπόδειξη κειμένου
                    OutLinedTextField.setText("Search University");
                    OutLinedTextField.setForeground(new java.awt.Color(169, 169, 169));
                }
            }

            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                // Όταν ο χρήστης πατήσει πάνω στο πεδίο, καθαρίζει το placeholder
                if (OutLinedTextField.getText().equals("Search University")) {
                    OutLinedTextField.setText(""); // Καθαρίζει το placeholder
                    OutLinedTextField.setForeground(new java.awt.Color(0, 0, 0)); // Ρυθμίζει το χρώμα του κειμένου σε μαύρο
                }
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        sideMenuPanel.setBackground(new java.awt.Color(178, 112, 69));
        // Add a drag event listener to the side menu
        sideMenuPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                sideMenuPanelMouseDragged(evt);
            }
        });
        // Handle focus events for OutLinedTextField
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


        // Set the title at the top of the menu
        title.setBackground(new java.awt.Color(255, 255, 255));
        title.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        title.setForeground(new java.awt.Color(255, 255, 255));
        title.setText("UniApp");

        // Create the panel for the "Countries" section
        countriesPanel.setBackground(new java.awt.Color(187, 124, 82));
        String flagPathImage = "src/main/java/images/flag.png";
        countriesImage.setIcon(new javax.swing.ImageIcon(flagPathImage)); // NOI18N*/
        countriesLabel.setBackground(new java.awt.Color(255, 255, 255));
        countriesLabel.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        countriesLabel.setForeground(new java.awt.Color(255, 255, 255));
        countriesLabel.setText("Countries");

        // Layout configuration for the "Countries" panel
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
        String exitPathImage = "src/main/java/images/exit1.png";

        // Create the panel for the "Exit" section
        exitImage.setIcon(new javax.swing.ImageIcon(exitPathImage)); // NOI18N*/
        exitLabel.setBackground(new java.awt.Color(255, 255, 255));
        exitLabel.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        exitLabel.setForeground(new java.awt.Color(255, 255, 255));
        exitLabel.setText("Exit");

        // Layout configuration for the "Exit" panel
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

        // Create the panel for the "Statistics" section
        statisticsPanel.setBackground(new java.awt.Color(187, 124, 82));
        String statsPathImage = "src/main/java/images/stats.png";
        statisticsImage.setIcon(new javax.swing.ImageIcon(statsPathImage)); // NOI18N
        statisticsLabel.setBackground(new java.awt.Color(255, 255, 255));
        statisticsLabel.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        statisticsLabel.setForeground(new java.awt.Color(255, 255, 255));
        statisticsLabel.setText("Statistics");

        // Layout configuration for the "Statistics" panel
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


        // Create the panel for the "Information" section
        infotmationsPanel.setBackground(new java.awt.Color(187, 124, 82));
        String InfoPathImage = "src/main/java/images/info.png";
        informationImage.setIcon(new javax.swing.ImageIcon(InfoPathImage)); // NOI18N
        informarionsLabel.setBackground(new java.awt.Color(255, 255, 255));
        informarionsLabel.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        informarionsLabel.setForeground(new java.awt.Color(255, 255, 255));
        informarionsLabel.setText("Information's");

        // Layout configuration for the "Information" panel
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

        // Configure the "Search University" text field
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

        // Layout configuration for jPanel2, which contains the search text field
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

        // Add the search field panel to the side menu panel layout
        javax.swing.GroupLayout sideMenuPanelLayout = new javax.swing.GroupLayout(sideMenuPanel);
        sideMenuPanel.setLayout(sideMenuPanelLayout);
        sideMenuPanelLayout.setHorizontalGroup(
                sideMenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(countriesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(infotmationsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(statisticsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(sideMenuPanelLayout.createSequentialGroup()
                                .addGap(125, 125, 125)
                                .addComponent(title)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(outLinedTextFieldPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(exitPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        sideMenuPanelLayout.setVerticalGroup(
                sideMenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(sideMenuPanelLayout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addComponent(title)
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


        // Add a mouse motion listener for the right screen panel
        rightScreenPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                rightScreenPanelMouseDragged(evt);
            }
        });

        String backGroundPathImage = "src/main/java/images/background_image.jpg";
        ImageIcon originalImageIcon = new ImageIcon(backGroundPathImage);
        Image image = originalImageIcon.getImage();
        Image scaledImage = image.getScaledInstance(1400, 960, Image.SCALE_SMOOTH); // Κλιμάκωση εικόνας
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
        jLabel1.setIcon(scaledImageIcon); // Εφαρμογή εικόνας στο jLabel
        jLabel1.setPreferredSize(new java.awt.Dimension(1400, 800));

        // Set layout for the mainPanel
        mainPanel.setLayout(new BorderLayout());


        // Set the background image label

        mainPanel.add(jLabel1, BorderLayout.CENTER); // Τοποθέτηση εικόνας στο κέντρο

        // Set sideMenuPanel on the left side
        mainPanel.add(sideMenuPanel, BorderLayout.WEST);

        // Set the rightScreenPanel on the right side (it's the CardLayout panel)
        rightScreenPanel.setLayout(new java.awt.CardLayout());
        mainPanel.add(rightScreenPanel, BorderLayout.EAST);

        // Layout for the outer frame
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
    }// </editor-fold>

    private void sideMenuPanelMouseDragged(java.awt.event.MouseEvent evt) {
        // Handles mouse drag events for the side menu panel (future functionality placeholder).
    }

    private void OutLinedTextFieldActionPerformed(java.awt.event.ActionEvent evt) {
        // Handles actions when Enter is pressed in the outlined text field.
    }

    private void rightScreenPanelMouseDragged(java.awt.event.MouseEvent evt) {
        // Handles mouse drag events for the right screen panel (future functionality placeholder).
    }

    /**
     * Main entry point for the application.
     *
     * @param args the command line arguments
     */

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
        // Αν υπάρχει προηγούμενο ενεργό κουμπί, επαναφέρουμε το αρχικό του χρώμα
        if (activeButton != null && activeButton != newActiveButton) {
            activeButton.setBackground(initialColors.get(activeButton));
        }

        // Ορίζουμε το νέο ενεργό κουμπί και αλλάζουμε το χρώμα του
        activeButton = newActiveButton;
        activeButton.setBackground(new java.awt.Color(139, 89, 61));
    }

    private void addMouseListeners() {
        // Save initial colors of panels for restoring their state
        initialColors.put(countriesPanel, countriesPanel.getBackground());
        initialColors.put(statisticsPanel, statisticsPanel.getBackground());
        initialColors.put(infotmationsPanel, infotmationsPanel.getBackground());
        initialColors.put(exitPanel, exitPanel.getBackground());

        // Mouse listener for "Countries" panel
        countriesPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                setActiveButton(countriesPanel);

                if (!dataLoaded) {
                    pendingCountryView = true; // Αν τα δεδομένα δεν έχουν φορτωθεί, περιμένουμε να το ανοίξουμε αυτόματα
                    return;
                }

                openCountryView();
            }

            public void mouseClicked(java.awt.event.MouseEvent evt) {
                onCountriesPanelClick(evt);
            }
        });

        // Mouse listener for "Statistics" panel
        statisticsPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                setActiveButton(statisticsPanel);

                jLabel1.setIcon(null);
                jLabel1.setPreferredSize(new java.awt.Dimension(0, 0));
                rightScreenPanel.removeAll();
                rightScreenPanel.setLayout(new CardLayout());
                rightScreenPanel.add(new StatisticsView(), "StatisticsView");
                rightScreenPanel.revalidate();
                rightScreenPanel.repaint();
                ((CardLayout) rightScreenPanel.getLayout()).show(rightScreenPanel, "StatisticsView");
            }

            public void mouseClicked(java.awt.event.MouseEvent evt) {
                onStatisticsPanelClick(evt);
            }
        });

        // Mouse listener for "Informations" panel
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

        // Mouse listener for "Exit" panel
        exitPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                setActiveButton(exitPanel);
                System.exit(0);
            }

            public void mouseClicked(java.awt.event.MouseEvent evt) {
                onExitPanelClick(evt);
            }
        });

        // Add enter key listener to the outlined text field
        addOutlinedTextFieldEnterListener();
    }
}


// End of variables declaration