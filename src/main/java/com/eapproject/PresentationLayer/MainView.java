package com.eapproject.PresentationLayer;

import com.eapproject.DomainLayer.Models.University;
import com.eapproject.DataLayer.DB.UniversityDAO;
import com.eapproject.PresentationLayer.ViewModels.UniversitiesViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static java.sql.DriverManager.println;

/**
 * Η κλάση {@code MainView} είναι το κύριο παράθυρο της εφαρμογής που εμφανίζει τα δεδομένα 
 * πανεπιστημίων και παρέχει λειτουργίες αναζήτησης, προβολής στατιστικών και άλλων πληροφοριών.
 * Υλοποιεί το Observer για να λαμβάνει ενημερώσεις από το {@link UniversitiesViewModel}.
 */
public class MainView extends JFrame implements Observer {

    // Ορισμός του Logger που θα χρησιμοποιηθεί για καταγραφή συμβάντων στο αρχείο logs/DBUtil.log
    private static final Logger LOGGER = Logger.getLogger(MainView.class.getName());

    // ViewModel και λίστα πανεπιστημίων
    private UniversitiesViewModel viewModel;
    private ArrayList<University> universities;  // Λίστα πανεπιστημίων
    private boolean dataLoaded = false;            // Flag που δείχνει εάν έχουν φορτωθεί τα δεδομένα
    private boolean pendingCountryView = false;    // Flag για αυτόματη προβολή της οθόνης "Countries" αν τα δεδομένα δεν έχουν φορτωθεί

    // DAO για πρόσβαση στα δεδομένα
    private final UniversityDAO dao = UniversityDAO.getInstance();

    // Swing components
    private JPanel mainPanel, sideMenuPanel, countriesPanel, statisticsPanel, infotmationsPanel, exitPanel, outLinedTextFieldPanel, rightScreenPanel, jPanel2;
    private JTextField OutLinedTextField;
    private JLabel title, countriesImage, countriesLabel, statisticsImage, statisticsLabel, informationImage, informarionsLabel, exitImage, exitLabel, jLabel1;
    
    // State variables για διαχείριση ενεργού κουμπιού στο side menu
    private JPanel activeButton = null;
    private final Map<JPanel, Color> initialColors = new HashMap<>();

    /**
     * Κατασκευαστής της κλάσης MainView.
     * Αρχικοποιεί τα components, ορίζει το εικονίδιο και τον Logger, προσθέτει listeners,
     * και ξεκινάει το thread για τη φόρτωση των πανεπιστημίων.
     */
    public MainView() {
        // Αρχικοποίηση του Logger για καταγραφή συμβάντων στο αρχείο logs/DBUtil.log
        initializeLogger();
        
        initComponents();
        
        // Ορισμός του εικονιδίου της εφαρμογής
        try {
            ImageIcon icon = new ImageIcon("resources/img/ico.png");
            this.setIconImage(icon.getImage());
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "⚠️ Σφάλμα κατά τη φόρτωση του εικονιδίου", ex);
        }
        
        addMouseListeners();
        setResizable(false);

        universities = new ArrayList<>();
        viewModel = new UniversitiesViewModel();
        viewModel.addObserver(this);

        // Εκτέλεση φόρτωσης πανεπιστημίων σε ξεχωριστό thread με προστασία από εξαιρέσεις.
        new Thread(() -> {
            try {
                viewModel.fetchUniversities();
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, "❌ Σφάλμα κατά την φόρτωση των πανεπιστημίων", ex);
            }
        }).start();
    }

    /**
     * Αρχικοποιεί τον Logger ώστε να καταγράφει τα συμβάντα στο αρχείο
     * {@code logs/DBUtil.log} με τη χρήση του {@code SimpleFormatter}.
     */
    private void initializeLogger() {
        try {
            // Δημιουργία φακέλου logs αν δεν υπάρχει
            Files.createDirectories(Paths.get("logs"));

            // Αφαίρεση τυχόν υπαρχόντων handlers για αποφυγή διπλών καταγραφών.
            for (Handler h : LOGGER.getHandlers()) {
                LOGGER.removeHandler(h);
            }

            // Δημιουργία FileHandler για το αρχείο logs/DBUtil.log (με append mode)
            FileHandler fileHandler = new FileHandler("logs/MainView.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);

            LOGGER.setLevel(Level.ALL);
            LOGGER.setUseParentHandlers(false);

            LOGGER.info("📌 Έναρξη καταγραφής του Logger στο logs/MainView.log");
        } catch (IOException e) {
            System.err.println("❌ Σφάλμα κατά την αρχικοποίηση του Logger: " + e.getMessage());
        }
    }

    /**
     * Ενημερώνει το view όταν το ViewModel αλλάζει.
     *
     * @param o   Το Observable αντικείμενο (αναμένεται να είναι {@link UniversitiesViewModel}).
     * @param arg Επιπλέον δεδομένα (δεν χρησιμοποιούνται εδώ).
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof UniversitiesViewModel) {
            try {
                UniversitiesViewModel model = (UniversitiesViewModel) o;
                // Ενημέρωση της λίστας πανεπιστημίων από το ViewModel.
                universities = (ArrayList<University>) model.getUniversities();

                if (!universities.isEmpty()) {
                    dataLoaded = true;
                    LOGGER.info("ℹ️ Loaded universities: " + dao.getAllUniversities());
                    // Αν ο χρήστης είχε πατήσει "Countries" πριν φορτωθούν τα δεδομένα, ανοίγουμε αυτόματα το CountryView.
                    if (pendingCountryView) {
                        openCountryView();
                        pendingCountryView = false;
                    }
                } else {
                    LOGGER.warning("⚠️ Δεν βρέθηκαν πανεπιστήμια!");
                    dataLoaded = false;
                }
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, "❌ Σφάλμα στην ενημέρωση του MainView", ex);
            }
        }
    }

    /**
     * Ανοίγει την οθόνη "CountryView" και ενημερώνει το rightScreenPanel.
     */
    private void openCountryView() {
        setActiveButton(countriesPanel);
        jLabel1.setIcon(null);
        jLabel1.setPreferredSize(new Dimension(0, 0));
        rightScreenPanel.removeAll();
        rightScreenPanel.setLayout(new CardLayout());
        // Προσθήκη του CountryView στο rightScreenPanel
        rightScreenPanel.add(new CountryView(rightScreenPanel, universities, viewModel), "CountryView");
        rightScreenPanel.revalidate();
        rightScreenPanel.repaint();
        ((CardLayout) rightScreenPanel.getLayout()).show(rightScreenPanel, "CountryView");
    }

    /**
     * Προσθέτει ActionListener στο OutLinedTextField ώστε να χειρίζεται το πάτημα του πλήκτρου Enter.
     */
    private void addOutlinedTextFieldEnterListener() {
        OutLinedTextField.addActionListener((ActionEvent evt) -> {
            onEnterPressed();
        });
    }

    /**
     * Μέθοδος που εκτελείται όταν ο χρήστης πατάει Enter στο OutLinedTextField.
     * Εμφανίζει διαστάσεις του rightScreenPanel και αναζητά το πανεπιστήμιο.
     */
    private void onEnterPressed() {
        LOGGER.info("ℹ️ Right Screen Panel Dimensions: Width = " + rightScreenPanel.getWidth() +
                    ", Height = " + rightScreenPanel.getHeight());
        String searchText = OutLinedTextField.getText();

        // Αναζήτηση πανεπιστημίου στη λίστα βάσει του κειμένου αναζήτησης
        viewModel.getUniversityFromList(searchText, universities);
        University uni = viewModel.getUniversityFromList();
        
        // ΔΙΟΡΘΩΣΗ: Έλεγχος αν η αναζήτηση δεν επέστρεψε πανεπιστήμιο (uni == null).
        if (uni == null) {
            return; // Τερματίζουμε εδώ για να αποφύγουμε το NullPointerException
        }

        if (!uni.getName().isEmpty()) {
            // Καθαρίζουμε το panel και επαναφέρουμε το layout για να εμφανίσουμε το UniversityView.
            jLabel1.setIcon(null);
            jLabel1.setPreferredSize(new Dimension(0, 0));
            rightScreenPanel.removeAll();
            rightScreenPanel.setLayout(new CardLayout());

            UniversityView panel = new UniversityView(uni, viewModel, universities);

            LOGGER.info("ℹ️ Adding UniversityView... Panel size: " + panel.getWidth() + "x" + panel.getHeight());

            rightScreenPanel.add(panel, "universityView");
            rightScreenPanel.revalidate();
            rightScreenPanel.repaint();

            // Εμφάνιση της νέας κάρτας (UniversityView)
            CardLayout layout = (CardLayout) rightScreenPanel.getLayout();
            layout.show(rightScreenPanel, "universityView");
            LOGGER.info("ℹ️ UniversityView displayed!");
        }
    }

    /**
     * Χειρίζεται το πάτημα του mouse στο CountriesPanel.
     *
     * @param evt Το MouseEvent που προκάλεσε την ενέργεια.
     */
    private void onCountriesPanelClick(MouseEvent evt) {
        LOGGER.info("ℹ️ Countries panel clicked!");
        // Μπορείτε να προσθέσετε επιπλέον λειτουργικότητα εδώ.
    }



    /**
     * Χειρίζεται το πάτημα του mouse στο InformationsPanel.
     *
     * @param evt Το MouseEvent που προκάλεσε την ενέργεια.
     */
    private void onInformationsPanelClick(MouseEvent evt) {
        LOGGER.info("ℹ️ Informations panel clicked!");
        // Προσθέστε επιπλέον λειτουργικότητα εδώ αν απαιτείται.
    }

    /**
     * Χειρίζεται το πάτημα του mouse στο ExitPanel.
     *
     * @param evt Το MouseEvent που προκάλεσε την ενέργεια.
     */
    private void onExitPanelClick(MouseEvent evt) {
        LOGGER.info("ℹ️ Exit panel clicked!");
        System.exit(0);
    }

    /**
     * Αρχικοποιεί όλα τα components της διεπαφής (UI) και ορίζει τη διάταξη.
     * Περιλαμβάνει φόρτωση εικόνων, ρύθμιση χρωμάτων, listeners και διάταξη με GroupLayout.
     */
    private void initComponents() {
        // Ορισμός τίτλου παραθύρου
        this.setTitle("UniApp");

        // Δημιουργία των κύριων panels
        mainPanel = new JPanel();
        sideMenuPanel = new JPanel();
        rightScreenPanel = new JPanel();

        // Δημιουργία components για το side menu
        title = new JLabel("UniApp");
        countriesPanel = new JPanel();
        countriesImage = new JLabel();
        countriesLabel = new JLabel("Countries");
        statisticsPanel = new JPanel();
        statisticsImage = new JLabel();
        statisticsLabel = new JLabel("Statistics");
        infotmationsPanel = new JPanel();
        informationImage = new JLabel();
        informarionsLabel = new JLabel("Information's");
        exitPanel = new JPanel();
        exitImage = new JLabel();
        exitLabel = new JLabel("Exit");

        // Δημιουργία panel για το OutLinedTextField
        outLinedTextFieldPanel = new JPanel();
        jPanel2 = new JPanel();
        OutLinedTextField = new JTextField();
        jLabel1 = new JLabel();

        // Ορισμός focus στο outLinedTextFieldPanel
        outLinedTextFieldPanel.setFocusable(true);

        // Προσθήκη MouseListener στο κύριο παράθυρο για μεταβίβαση focus αν δεν είναι στο OutLinedTextField
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                if (!OutLinedTextField.getBounds().contains(evt.getPoint())) {
                    OutLinedTextField.transferFocus();
                }
            }
        });

        // Προσθήκη FocusListener στο OutLinedTextField για αλλαγή εμφάνισης κειμένου
        OutLinedTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent evt) {
                if (OutLinedTextField.getText().isEmpty()) {
                    OutLinedTextField.setText("Search University");
                    OutLinedTextField.setForeground(new Color(169, 169, 169));
                }
            }
            @Override
            public void focusGained(FocusEvent evt) {
                if (OutLinedTextField.getText().equals("Search University")) {
                    OutLinedTextField.setText("");
                    OutLinedTextField.setForeground(new Color(0, 0, 0));
                }
            }
        });

        // Ορισμός default close operation
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Διαμόρφωση του sideMenuPanel
        sideMenuPanel.setBackground(new Color(178, 112, 69));
        sideMenuPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(MouseEvent evt) {
                sideMenuPanelMouseDragged(evt);
            }
        });

        // Επαναπροσθήκη FocusListener για το OutLinedTextField
        OutLinedTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent evt) {
                if (OutLinedTextField.getText().equals("Search University")) {
                    OutLinedTextField.setText("");
                    OutLinedTextField.setForeground(new Color(0, 0, 0));
                }
            }
            @Override
            public void focusLost(FocusEvent evt) {
                if (OutLinedTextField.getText().isEmpty()) {
                    OutLinedTextField.setText("Search University");
                    OutLinedTextField.setForeground(new Color(169, 169, 169));
                }
            }
        });

        // Διαμόρφωση του τίτλου στο sideMenuPanel
        title.setBackground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));
        title.setForeground(Color.WHITE);

        // Φόρτωση και κλιμάκωση του λογότυπου
        ImageIcon logoIcon = new ImageIcon("resources/img/logo_white.png");
        Image logoImage = logoIcon.getImage();
        int newWidth = logoIcon.getIconWidth() / 2;
        int newHeight = logoIcon.getIconHeight() / 2;
        Image scaledLogoImage = logoImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledLogoIcon = new ImageIcon(scaledLogoImage);
        JLabel logoLabel = new JLabel(scaledLogoIcon);

        // Διαμόρφωση του countriesPanel
        countriesPanel.setBackground(new Color(187, 124, 82));
        String flagPathImage = "resources/img/flag.png";
        countriesImage.setIcon(new ImageIcon(flagPathImage));
        countriesLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        countriesLabel.setForeground(Color.WHITE);

        GroupLayout countriesPanelLayout = new GroupLayout(countriesPanel);
        countriesPanel.setLayout(countriesPanelLayout);
        countriesPanelLayout.setHorizontalGroup(
            countriesPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(countriesPanelLayout.createSequentialGroup()
                    .addGap(69, 69, 69)
                    .addComponent(countriesImage)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(countriesLabel)
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        countriesPanelLayout.setVerticalGroup(
            countriesPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(countriesImage, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(countriesPanelLayout.createSequentialGroup()
                    .addGap(30, 30, 30)
                    .addComponent(countriesLabel)
                    .addContainerGap(19, Short.MAX_VALUE))
        );

        // Διαμόρφωση του exitPanel
        exitPanel.setBackground(new Color(187, 124, 82));
        String exitPathImage = "resources/img/exit1.png";
        exitImage.setIcon(new ImageIcon(exitPathImage));
        exitLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        exitLabel.setForeground(Color.WHITE);

        GroupLayout exitPanelLayout = new GroupLayout(exitPanel);
        exitPanel.setLayout(exitPanelLayout);
        exitPanelLayout.setHorizontalGroup(
            exitPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(exitPanelLayout.createSequentialGroup()
                    .addGap(69, 69, 69)
                    .addComponent(exitImage)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(exitLabel)
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        exitPanelLayout.setVerticalGroup(
            exitPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(exitImage, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(exitPanelLayout.createSequentialGroup()
                    .addGap(17, 17, 17)
                    .addComponent(exitLabel)
                    .addContainerGap(19, Short.MAX_VALUE))
        );

        // Διαμόρφωση του statisticsPanel
        statisticsPanel.setBackground(new Color(187, 124, 82));
        String statsPathImage = "resources/img/stats.png";
        statisticsImage.setIcon(new ImageIcon(statsPathImage));
        statisticsLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        statisticsLabel.setForeground(Color.WHITE);

        GroupLayout statisticsPanelLayout = new GroupLayout(statisticsPanel);
        statisticsPanel.setLayout(statisticsPanelLayout);
        statisticsPanelLayout.setHorizontalGroup(
            statisticsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(statisticsPanelLayout.createSequentialGroup()
                    .addGap(69, 69, 69)
                    .addComponent(statisticsImage)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(statisticsLabel)
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        statisticsPanelLayout.setVerticalGroup(
            statisticsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(statisticsImage, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statisticsPanelLayout.createSequentialGroup()
                    .addGap(17, 17, 17)
                    .addComponent(statisticsLabel)
                    .addContainerGap(19, Short.MAX_VALUE))
        );

        // Διαμόρφωση του infotmationsPanel
        infotmationsPanel.setBackground(new Color(187, 124, 82));
        String infoPathImage = "resources/img/info.png";
        informationImage.setIcon(new ImageIcon(infoPathImage));
        informarionsLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        informarionsLabel.setForeground(Color.WHITE);

        GroupLayout infotmationsPanelLayout = new GroupLayout(infotmationsPanel);
        infotmationsPanel.setLayout(infotmationsPanelLayout);
        infotmationsPanelLayout.setHorizontalGroup(
            infotmationsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(infotmationsPanelLayout.createSequentialGroup()
                    .addGap(69, 69, 69)
                    .addComponent(informationImage)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(informarionsLabel)
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        infotmationsPanelLayout.setVerticalGroup(
            infotmationsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(informationImage, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(infotmationsPanelLayout.createSequentialGroup()
                    .addGap(17, 17, 17)
                    .addComponent(informarionsLabel)
                    .addContainerGap(19, Short.MAX_VALUE))
        );

        // Διαμόρφωση του OutLinedTextField και του panel που το περιέχει
        outLinedTextFieldPanel.setBackground(new Color(187, 124, 82));
        jPanel2.setBackground(new Color(187, 124, 82));
        OutLinedTextField.setBackground(Color.WHITE);
        OutLinedTextField.setHorizontalAlignment(JTextField.CENTER);
        OutLinedTextField.setText("Search University");
        OutLinedTextField.setForeground(new Color(169, 169, 169));
        OutLinedTextField.addActionListener((ActionEvent evt) -> {
            OutLinedTextFieldActionPerformed(evt);
        });

        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(28, 28, 28)
                    .addComponent(OutLinedTextField, GroupLayout.PREFERRED_SIZE, 337, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(OutLinedTextField, GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                    .addContainerGap())
        );

        GroupLayout outLinedTextFieldPanelLayout = new GroupLayout(outLinedTextFieldPanel);
        outLinedTextFieldPanel.setLayout(outLinedTextFieldPanelLayout);
        outLinedTextFieldPanelLayout.setHorizontalGroup(
            outLinedTextFieldPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(outLinedTextFieldPanelLayout.createSequentialGroup()
                    .addGap(17, 17, 17)
                    .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(17, Short.MAX_VALUE))
        );
        outLinedTextFieldPanelLayout.setVerticalGroup(
            outLinedTextFieldPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(GroupLayout.Alignment.TRAILING, outLinedTextFieldPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap())
        );

        // Διαμόρφωση του sideMenuPanel με το logo, τίτλο και τα μενού
        GroupLayout sideMenuPanelLayout = new GroupLayout(sideMenuPanel);
        sideMenuPanel.setLayout(sideMenuPanelLayout);
        sideMenuPanelLayout.setHorizontalGroup(
            sideMenuPanelLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addGroup(sideMenuPanelLayout.createSequentialGroup()
                    .addComponent(logoLabel)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(title))
                .addComponent(outLinedTextFieldPanel, GroupLayout.Alignment.CENTER, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(countriesPanel, GroupLayout.Alignment.CENTER, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(statisticsPanel, GroupLayout.Alignment.CENTER, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(infotmationsPanel, GroupLayout.Alignment.CENTER, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(exitPanel, GroupLayout.Alignment.CENTER, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        sideMenuPanelLayout.setVerticalGroup(
            sideMenuPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(sideMenuPanelLayout.createSequentialGroup()
                    .addGap(41, 41, 41)
                    .addGroup(sideMenuPanelLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(logoLabel, GroupLayout.PREFERRED_SIZE, logoLabel.getPreferredSize().height, GroupLayout.PREFERRED_SIZE)
                        .addComponent(title))
                    .addGap(70, 70, 70)
                    .addComponent(outLinedTextFieldPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(91, 91, 91)
                    .addComponent(countriesPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, 0)
                    .addComponent(statisticsPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, 0)
                    .addComponent(infotmationsPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 187, Short.MAX_VALUE)
                    .addComponent(exitPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(54, 54, 54))
        );

        // Προσθήκη MouseMotionListener στο rightScreenPanel
        rightScreenPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(MouseEvent evt) {
                rightScreenPanelMouseDragged(evt);
            }
        });

        // Φόρτωση εικόνας background, κλιμάκωση και ορισμός στο jLabel1
        String backGroundPathImage = "resources/img/background_image.jpg";
        try {
            // Δεν απαιτείται try-with-resources εδώ, όμως χρησιμοποιούμε try-catch για αποφυγή εξαιρέσεων.
            ImageIcon originalImageIcon = new ImageIcon(backGroundPathImage);
            Image image = originalImageIcon.getImage();
            Image scaledImage = image.getScaledInstance(1400, 960, Image.SCALE_SMOOTH);
            ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
            jLabel1.setIcon(scaledImageIcon);
            jLabel1.setPreferredSize(new Dimension(1400, 800));
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "⚠️ Σφάλμα κατά τη φόρτωση της εικόνας background", ex);
        }

        // Διαμόρφωση του mainPanel με BorderLayout και τοποθέτηση των sideMenuPanel και rightScreenPanel
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(jLabel1, BorderLayout.CENTER);
        mainPanel.add(sideMenuPanel, BorderLayout.WEST);
        rightScreenPanel.setLayout(new CardLayout());
        mainPanel.add(rightScreenPanel, BorderLayout.EAST);

        // Τοποθέτηση του mainPanel στο content pane του παραθύρου
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(mainPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(mainPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pack();
    }

    /**
     * Χειρίζεται το event του mouse drag στο sideMenuPanel.
     *
     * @param evt Το MouseEvent που προκάλεσε την ενέργεια.
     */
    private void sideMenuPanelMouseDragged(MouseEvent evt) {
        // Placeholder για μελλοντική λειτουργικότητα.
    }

    /**
     * Χειρίζεται το action του OutLinedTextField όταν πατιέται το Enter.
     *
     * @param evt Το ActionEvent που προκάλεσε την ενέργεια.
     */
    private void OutLinedTextFieldActionPerformed(ActionEvent evt) {
        // Μπορείτε να προσθέσετε επιπλέον λειτουργικότητα εδώ.
    }

    /**
     * Χειρίζεται το event του mouse drag στο rightScreenPanel.
     *
     * @param evt Το MouseEvent που προκάλεσε την ενέργεια.
     */
    private void rightScreenPanelMouseDragged(MouseEvent evt) {
        // Placeholder για μελλοντική λειτουργικότητα.
    }

    /**
     * Ορίζει το ενεργό panel στο side menu αλλάζοντας το background του.
     *
     * @param newActiveButton Το νέο ενεργό panel.
     */
    private void setActiveButton(JPanel newActiveButton) {
        if (activeButton != null && activeButton != newActiveButton) {
            activeButton.setBackground(initialColors.get(activeButton));
        }
        activeButton = newActiveButton;
        activeButton.setBackground(new Color(139, 89, 61));
    }

    /**
     * Προσθέτει MouseListeners στα panels του side menu για να διαχειρίζονται τα click events.
     */
    private void addMouseListeners() {
        // Αποθήκευση αρχικών χρωμάτων για κάθε panel του side menu
        initialColors.put(countriesPanel, countriesPanel.getBackground());
        initialColors.put(statisticsPanel, statisticsPanel.getBackground());
        initialColors.put(infotmationsPanel, infotmationsPanel.getBackground());
        initialColors.put(exitPanel, exitPanel.getBackground());

        // Listener για το countriesPanel
        countriesPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                setActiveButton(countriesPanel);
                // Αν τα δεδομένα δεν έχουν φορτωθεί, θέτουμε το flag και επιστρέφουμε.
                if (!dataLoaded) {
                    pendingCountryView = true;
                    return;
                }
                openCountryView();
            }
            public void mouseClicked(MouseEvent evt) {
                onCountriesPanelClick(evt);
            }
        });

        // Listener για το statisticsPanel
        statisticsPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                System.out.println("clicked"); // Για debugging
                setActiveButton(statisticsPanel);

                // Δημιουργούμε έναν νέο SwingWorker για να φορτώσουμε τα στατιστικά ασύγχρονα
                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        System.out.println("fetching stats..."); // Για debugging
                        viewModel.fetchStats(); // Φορτώνουμε τα δεδομένα στον background thread
                        return null;
                    }

                    @Override
                    protected void done() {
                        // Όταν η φόρτωση ολοκληρωθεί, ενημερώνουμε το UI στον Event Dispatch Thread
                        try {
                            if (!viewModel.getStatisticsList().isEmpty()) {
                                System.out.println("stats loaded, displaying..."); // Για debugging
                                // Εμφανίζουμε τα στατιστικά
                                displayStatistics();
                            } else {
                                System.out.println("Δεν βρέθηκαν στατιστικά.");
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, "❌ Σφάλμα κατά την φόρτωση στατιστικών", ex);
                        }
                    }
                };

                // Εκκινήστε τον SwingWorker
                worker.execute();
            }
            private void displayStatistics() {
                // Εμφανίζουμε τα στατιστικά στην οθόνη
                jLabel1.setIcon(null);
                jLabel1.setPreferredSize(new Dimension(0, 0));
                rightScreenPanel.removeAll();
                rightScreenPanel.setLayout(new CardLayout());
                rightScreenPanel.add(new StatisticsView(viewModel.getStatisticsList(), viewModel), "StatisticsView");
                rightScreenPanel.revalidate();
                rightScreenPanel.repaint();
                ((CardLayout) rightScreenPanel.getLayout()).show(rightScreenPanel, "StatisticsView");
            }
        });



        // Listener για το infotmationsPanel
        infotmationsPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                setActiveButton(infotmationsPanel);
                jLabel1.setIcon(null);
                jLabel1.setPreferredSize(new Dimension(0, 0));
                rightScreenPanel.removeAll();
                rightScreenPanel.setLayout(new CardLayout());
                rightScreenPanel.add(new InformationView(), "InformationView");
                rightScreenPanel.revalidate();
                rightScreenPanel.repaint();
                ((CardLayout) rightScreenPanel.getLayout()).show(rightScreenPanel, "InformationView");
            }
            public void mouseClicked(MouseEvent evt) {
                onInformationsPanelClick(evt);
            }
        });

        // Listener για το exitPanel
        exitPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                setActiveButton(exitPanel);
                System.exit(0);
            }
            public void mouseClicked(MouseEvent evt) {
                onExitPanelClick(evt);
            }
        });

        // Προσθήκη listener για το OutLinedTextField ώστε να χειρίζεται το Enter
        addOutlinedTextFieldEnterListener();
    }
}
