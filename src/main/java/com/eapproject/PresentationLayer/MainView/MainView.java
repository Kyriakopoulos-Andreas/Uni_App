package com.eapproject.PresentationLayer.MainView;

import com.eapproject.DB.University;
import com.eapproject.DB.UniversityDAO;
import com.eapproject.DataLayer.UniversitiesViewModel;
import com.eapproject.PresentationLayer.CountryView.CountryView;
import com.eapproject.PresentationLayer.InformationView.InformationView;
import com.eapproject.PresentationLayer.StatisticsView.StatisticsView;
import com.eapproject.PresentationLayer.UniversityView.UniversityView;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class MainView extends JFrame implements Observer {

    // Data structures
    private UniversitiesViewModel viewModel;
    private ArrayList<University> universities = new ArrayList<>();
    private boolean dataLoaded = false;
    private boolean pendingCountryView = false;
    private final UniversityDAO dao = UniversityDAO.getInstance();

    // Swing components – ονομασίες σύμφωνα με camelCase
    private JPanel mainPanel, sideMenuPanel, rightScreenPanel, outlinedTextFieldPanel, jPanel2;
    private JLabel title, backgroundLabel, countriesLabel, exitLabel;
    private JTextField outlinedTextField;
    private JPanel countriesPanel, statisticsPanel, informationPanel, exitPanel;
    private JLabel countriesImage, statisticsImage, informationImage, exitImage;
    
    // Map για αποθήκευση αρχικών χρωμάτων των side menu panels
    private final Map<JPanel, Color> initialColors = new HashMap<>();
    private JPanel activeButton = null;

    public MainView() {
        initComponents();
        addMouseListeners();
        setResizable(false);

        viewModel = new UniversitiesViewModel();
        viewModel.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof UniversitiesViewModel) {
            UniversitiesViewModel model = (UniversitiesViewModel) o;
            // Ενημέρωση του UI στον Event Dispatch Thread
            SwingUtilities.invokeLater(() -> {
                universities = new ArrayList<>(model.getUniversities());
                if (!universities.isEmpty()) {
                    dataLoaded = true;
                    System.out.println("Test: " + dao.getAllUniversities());
                    if (pendingCountryView) {
                        openCountryView();
                        System.out.println("Test: " + dao.getAllUniversities());
                        pendingCountryView = false;
                    }
                } else {
                    System.out.println("❌ Δεν βρέθηκαν πανεπιστήμια!");
                    dataLoaded = false;
                }
            });
        }
    }

    private void openCountryView() {
        setActiveButton(countriesPanel);
        backgroundLabel.setIcon(null);
        backgroundLabel.setPreferredSize(new Dimension(0, 0));
        rightScreenPanel.removeAll();
        rightScreenPanel.setLayout(new CardLayout());
        rightScreenPanel.add(new CountryView(rightScreenPanel, universities), "CountryView");
        rightScreenPanel.revalidate();
        rightScreenPanel.repaint();
        ((CardLayout) rightScreenPanel.getLayout()).show(rightScreenPanel, "CountryView");
    }

    private void addOutlinedTextFieldEnterListener() {
        outlinedTextField.addActionListener(evt -> onEnterPressed());
    }

    private void onEnterPressed() {
        System.out.println("Right Screen Panel Dimensions:");
        System.out.println("Width: " + rightScreenPanel.getWidth() + ", Height: " + rightScreenPanel.getHeight());
        String searchText = outlinedTextField.getText();
        backgroundLabel.setIcon(null);
        backgroundLabel.setPreferredSize(new Dimension(0, 0));
        rightScreenPanel.removeAll();
        rightScreenPanel.setLayout(new CardLayout());
        UniversityView panel = new UniversityView(searchText);
        System.out.println("Adding UniversityView...");
        rightScreenPanel.add(panel, "universityView");
        rightScreenPanel.revalidate();
        rightScreenPanel.repaint();
        ((CardLayout) rightScreenPanel.getLayout()).show(rightScreenPanel, "universityView");
        System.out.println("UniversityView displayed!");
    }

    private void setActiveButton(JPanel newActiveButton) {
        if (activeButton != null && activeButton != newActiveButton) {
            activeButton.setBackground(initialColors.get(activeButton));
        }
        activeButton = newActiveButton;
        activeButton.setBackground(new Color(139, 89, 61));
    }

    private void addMouseListeners() {
        // Αποθήκευση αρχικών χρωμάτων των panels για επαναφορά
        initialColors.put(countriesPanel, countriesPanel.getBackground());
        initialColors.put(statisticsPanel, statisticsPanel.getBackground());
        initialColors.put(informationPanel, informationPanel.getBackground());
        initialColors.put(exitPanel, exitPanel.getBackground());

        countriesPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                setActiveButton(countriesPanel);
                if (!dataLoaded) {
                    pendingCountryView = true;
                    return;
                }
                openCountryView();
            }
            @Override
            public void mouseClicked(MouseEvent evt) {
                onCountriesPanelClick(evt);
            }
        });

        statisticsPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                setActiveButton(statisticsPanel);
                backgroundLabel.setIcon(null);
                backgroundLabel.setPreferredSize(new Dimension(0, 0));
                rightScreenPanel.removeAll();
                rightScreenPanel.setLayout(new CardLayout());
                rightScreenPanel.add(new StatisticsView(), "StatisticsView");
                rightScreenPanel.revalidate();
                rightScreenPanel.repaint();
                ((CardLayout) rightScreenPanel.getLayout()).show(rightScreenPanel, "StatisticsView");
            }
            @Override
            public void mouseClicked(MouseEvent evt) {
                onStatisticsPanelClick(evt);
            }
        });

        informationPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                setActiveButton(informationPanel);
                backgroundLabel.setIcon(null);
                backgroundLabel.setPreferredSize(new Dimension(0, 0));
                rightScreenPanel.removeAll();
                rightScreenPanel.setLayout(new CardLayout());
                rightScreenPanel.add(new InformationView(), "InformationView");
                rightScreenPanel.revalidate();
                rightScreenPanel.repaint();
                ((CardLayout) rightScreenPanel.getLayout()).show(rightScreenPanel, "InformationView");
            }
            @Override
            public void mouseClicked(MouseEvent evt) {
                onInformationsPanelClick(evt);
            }
        });

        exitPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                setActiveButton(exitPanel);
                System.exit(0);
            }
            @Override
            public void mouseClicked(MouseEvent evt) {
                onExitPanelClick(evt);
            }
        });

        addOutlinedTextFieldEnterListener();
    }

    // Event handlers
    private void onCountriesPanelClick(MouseEvent evt) {
        System.out.println("Countries panel clicked!");
    }

    private void onStatisticsPanelClick(MouseEvent evt) {
        System.out.println("Statistics panel clicked!");
    }

    private void onInformationsPanelClick(MouseEvent evt) {
        System.out.println("Informations panel clicked!");
    }

    private void onExitPanelClick(MouseEvent evt) {
        System.out.println("Exit panel clicked!");
    }

    // initComponents δημιουργήθηκε κυρίως από το GUI Builder με βελτιώσεις στις ονομασίες και το layout
    private void initComponents() {
        setTitle("UniApp");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Δημιουργία βασικών panels
        mainPanel = new JPanel(new BorderLayout());
        // Ορίστε το background του mainPanel σύμφωνα με το αρχικό σχέδιο:
        mainPanel.setBackground(new Color(252, 252, 242));
        
        sideMenuPanel = new JPanel();
        // Ορίστε το background του sideMenuPanel σύμφωνα με το αρχικό (178,112,69)
        sideMenuPanel.setBackground(new Color(178, 112, 69));
        
        rightScreenPanel = new JPanel(new CardLayout());
        outlinedTextFieldPanel = new JPanel();
        jPanel2 = new JPanel();

        // Δημιουργία label τίτλου
        title = new JLabel("UniApp");
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));
        title.setForeground(Color.WHITE);

        // Δημιουργία side menu panels
        countriesPanel = new JPanel();
        statisticsPanel = new JPanel();
        informationPanel = new JPanel();
        exitPanel = new JPanel();

        // Ορισμός background για τα side menu panels (187,124,82)
        Color sidePanelBg = new Color(187, 124, 82);
        countriesPanel.setBackground(sidePanelBg);
        statisticsPanel.setBackground(sidePanelBg);
        informationPanel.setBackground(sidePanelBg);
        exitPanel.setBackground(sidePanelBg);

        // Δημιουργία εικόνων και labels για το side menu
        countriesImage = new JLabel(new ImageIcon("src/main/java/images/flag.png"));
        countriesLabel = new JLabel("Countries");
        countriesLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        countriesLabel.setForeground(Color.WHITE);

        statisticsImage = new JLabel(new ImageIcon("src/main/java/images/stats.png"));
        JLabel statisticsSideLabel = new JLabel("Statistics");
        statisticsSideLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        statisticsSideLabel.setForeground(Color.WHITE);

        informationImage = new JLabel(new ImageIcon("src/main/java/images/info.png"));
        JLabel informationSideLabel = new JLabel("Information's");
        informationSideLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        informationSideLabel.setForeground(Color.WHITE);

        exitImage = new JLabel(new ImageIcon("src/main/java/images/exit1.png"));
        exitLabel = new JLabel("Exit");
        exitLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        exitLabel.setForeground(Color.WHITE);

        // Layout για το "Countries" panel
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
                .addComponent(countriesImage, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(countriesPanelLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(countriesLabel)
                        .addContainerGap(19, Short.MAX_VALUE))
        );

        // Layout για το "Statistics" panel
        GroupLayout statisticsPanelLayout = new GroupLayout(statisticsPanel);
        statisticsPanel.setLayout(statisticsPanelLayout);
        statisticsPanelLayout.setHorizontalGroup(
            statisticsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(statisticsPanelLayout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(statisticsImage)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(statisticsSideLabel)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        statisticsPanelLayout.setVerticalGroup(
            statisticsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(statisticsImage, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statisticsPanelLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(statisticsSideLabel)
                        .addContainerGap(19, Short.MAX_VALUE))
        );

        // Layout για το "Information" panel
        GroupLayout informationPanelLayout = new GroupLayout(informationPanel);
        informationPanel.setLayout(informationPanelLayout);
        informationPanelLayout.setHorizontalGroup(
            informationPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(informationPanelLayout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(informationImage)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(informationSideLabel)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        informationPanelLayout.setVerticalGroup(
            informationPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(informationImage, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(informationPanelLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(informationSideLabel)
                        .addContainerGap(19, Short.MAX_VALUE))
        );

        // Layout για το "Exit" panel
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
                .addComponent(exitImage, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(exitPanelLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(exitLabel)
                        .addContainerGap(19, Short.MAX_VALUE))
        );

        // Δημιουργία του panel για το πεδίο αναζήτησης
        outlinedTextFieldPanel = new JPanel();
        outlinedTextFieldPanel.setBackground(new Color(187, 124, 82));
        jPanel2 = new JPanel();
        jPanel2.setBackground(new Color(187, 124, 82));
        outlinedTextField = new JTextField("Search University", 20);
        outlinedTextField.setBackground(Color.WHITE);
        outlinedTextField.setHorizontalAlignment(JTextField.CENTER);
        outlinedTextField.setForeground(new Color(169, 169, 169));
        outlinedTextField.addActionListener(e -> OutLinedTextFieldActionPerformed(e));

        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(28)
                        .addComponent(outlinedTextField, GroupLayout.PREFERRED_SIZE, 337, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(outlinedTextField, GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                        .addContainerGap())
        );

        GroupLayout outlinedTextFieldPanelLayout = new GroupLayout(outlinedTextFieldPanel);
        outlinedTextFieldPanel.setLayout(outlinedTextFieldPanelLayout);
        outlinedTextFieldPanelLayout.setHorizontalGroup(
            outlinedTextFieldPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(outlinedTextFieldPanelLayout.createSequentialGroup()
                        .addGap(17)
                        .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(17, Short.MAX_VALUE))
        );
        outlinedTextFieldPanelLayout.setVerticalGroup(
            outlinedTextFieldPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(GroupLayout.Alignment.TRAILING, outlinedTextFieldPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
        );

        // Ορισμός layout για το sideMenuPanel και προσθήκη των components
        sideMenuPanel.setLayout(new GroupLayout(sideMenuPanel));
        GroupLayout sideMenuPanelLayout = (GroupLayout) sideMenuPanel.getLayout();
        sideMenuPanelLayout.setHorizontalGroup(
            sideMenuPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(countriesPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(statisticsPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(informationPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(sideMenuPanelLayout.createSequentialGroup()
                        .addGap(125)
                        .addComponent(title)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addComponent(outlinedTextFieldPanel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(exitPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        sideMenuPanelLayout.setVerticalGroup(
            sideMenuPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(sideMenuPanelLayout.createSequentialGroup()
                        .addGap(41)
                        .addComponent(title)
                        .addGap(70)
                        .addComponent(outlinedTextFieldPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(91)
                        .addComponent(countriesPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(statisticsPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(informationPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 187, Short.MAX_VALUE)
                        .addComponent(exitPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(54))
        );

        // Προσθήκη listener για mouse drag στο rightScreenPanel
        rightScreenPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent evt) {
                rightScreenPanelMouseDragged(evt);
            }
        });

        // Φόρτωση εικόνας φόντου
        backgroundLabel = new JLabel();
        String backGroundPathImage = "src/main/java/images/background_image.jpg";
        ImageIcon originalImageIcon = new ImageIcon(backGroundPathImage);
        Image image = originalImageIcon.getImage();
        Image scaledImage = image.getScaledInstance(1400, 960, Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
        backgroundLabel.setIcon(scaledImageIcon);
        backgroundLabel.setPreferredSize(new Dimension(1400, 800));

        // Προσθήκη των panels στο mainPanel
        mainPanel.add(backgroundLabel, BorderLayout.CENTER);
        mainPanel.add(sideMenuPanel, BorderLayout.WEST);
        mainPanel.add(rightScreenPanel, BorderLayout.EAST);

        // Ορισμός layout για το JFrame
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

    private void OutLinedTextFieldActionPerformed(ActionEvent evt) {
        // επιπλέον λειτουργικότητα όταν πατιέται το enter στο πεδίο αναζήτησης.
    }

    private void rightScreenPanelMouseDragged(MouseEvent evt) {
        // Placeholder για μελλοντική υλοποίηση του mouse drag στο rightScreenPanel.
    }
}
