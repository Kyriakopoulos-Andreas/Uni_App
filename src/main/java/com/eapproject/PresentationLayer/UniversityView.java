package com.eapproject.PresentationLayer;

import com.eapproject.DataLayer.DB.University;
import com.eapproject.PresentationLayer.ViewModels.UniversitiesViewModel;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Η κλάση {@code UniversityView} αντιπροσωπεύει ένα JPanel που εμφανίζει τις λεπτομέρειες ενός πανεπιστημίου και παρέχει δυνατότητα επεξεργασίας.
 * Περιέχει λειτουργίες για μετάβαση μεταξύ λειτουργίας προβολής και επεξεργασίας, καθώς και μηχανισμό επικύρωσης και ενημέρωσης των δεδομένων,
 * μέσω της χρήσης SwingWorker για την εκτέλεση εργασιών στο παρασκήνιο.
 */
public class UniversityView extends JPanel {

    // Logger για καταγραφή σφαλμάτων και πληροφοριών κατά την εκτέλεση του προγράμματος.
    private static final Logger LOGGER = Logger.getLogger(UniversityView.class.getName());

    // Σταθερές για τις αποστάσεις και το layout
    private static final int SPACE_Y = 40;
    private static final int SPACE_Y_BEFORE_BOTTOM_DIVIDER = 30;
    private static final int SPACE_Y_IF_FACULTIES = 40;

    // Σταθερές για χρώματα και γραμματοσειρές που χρησιμοποιούνται στο UI
    private static final Color BACKGROUND_COLOR = new Color(252, 252, 242);
    private static final Color LABEL_COLOR = new Color(223, 109, 35);
    private static final Color TEXT_FOREGROUND = new Color(96, 59, 6);
    private static final Font LABEL_FONT = new Font("Segoe UI", Font.PLAIN, 42);
    private static final Font VIEW_FONT = new Font("Segoe UI", Font.PLAIN, 28);
    private static final Font EDIT_FONT = new Font("Segoe UI", Font.PLAIN, 36);
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 36);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 14);

    // Αντικείμενα δεδομένων (domain objects) και ViewModel
    private University university;
    private UniversitiesViewModel viewModel;
    private List<University> universities;

    // Κύρια στοιχεία του UI
    private JPanel universityViewMainPanel;
    private JLabel universityLabel;
    private JPanel universityLabelPanel;
    private JSeparator topDivider;
    private JSeparator bottomDivider;
    private JLabel countryLabel;
    private JLabel stateLabel;
    private JLabel domainLabel;
    private JLabel webPageLabel;
    private JLabel alphaTwoCodeLabel;
    private JLabel contactLabel;
    private JLabel facultiesLabel;
    private JButton updateButton;

    // Panels για τα πεδία (country, state, κ.λπ.) που θα εμφανίζουν τόσο το περιεχόμενο όσο και το editable πεδίο
    private JPanel countryTodoPanel;
    private JPanel stateTodoPanel;
    private JPanel webPageTodoPanel;
    private JPanel domainTodoPanel;
    private JPanel alphaTwoCodeTodoPanel;
    private JPanel contactTodoPanel;
    private JPanel facultiesTodoPanel;

    /**
     * Κατασκευαστής της κλάσης UniversityView.
     *
     * @param university   Το αντικείμενο {@code University} που θα εμφανιστεί.
     * @param viewModel    Το ViewModel που διαχειρίζεται τη λογική της εφαρμογής.
     * @param universities Λίστα πανεπιστημίων που χρησιμοποιείται για τον έλεγχο εγκυρότητας των δεδομένων.
     */
    public UniversityView(University university, UniversitiesViewModel viewModel, List<University> universities) {
        this.university = university;
        this.viewModel = viewModel;
        this.universities = universities;
        initComponents(); // Αρχικοποιούμε όλα τα components του UI.
    }

    /**
     * Αρχικοποιεί τα UI components και ορίζει τη διάταξη του panel.
     */
    private void initComponents() {
        // Ορίζουμε το layout του κύριου panel και το προεπιλεγμένο μέγεθος.
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(2000, 600));

        // Κλήση μεθόδων για αρχικοποίηση και διάταξη των components.
        initUIComponents();
        layoutComponents();

        // Τοποθέτηση του κύριου panel μέσα σε JScrollPane για δυνατότητα κύλισης.
        this.add(new JScrollPane(universityViewMainPanel));
    }

    /**
     * Αρχικοποιεί και διαμορφώνει τα επιμέρους components του UI.
     */
    private void initUIComponents() {
        // Δημιουργία του κύριου panel και ορισμός φόντου.
        universityViewMainPanel = new JPanel();
        universityViewMainPanel.setBackground(BACKGROUND_COLOR);

        // Δημιουργία panel για την εμφάνιση του ονόματος του πανεπιστημίου.
        universityLabelPanel = new JPanel();
        universityLabelPanel.setBackground(BACKGROUND_COLOR);

        // Δημιουργία και διαμόρφωση της ετικέτας με το όνομα του πανεπιστημίου.
        universityLabel = new JLabel(university.getName());
        universityLabel.setFont(TITLE_FONT);
        universityLabel.setForeground(LABEL_COLOR);
        universityLabelPanel.add(universityLabel);

        // Δημιουργία διαχωριστικών γραμμών για καλύτερη οπτική διάκριση.
        topDivider = new JSeparator();
        topDivider.setForeground(LABEL_COLOR);
        bottomDivider = new JSeparator();
        bottomDivider.setForeground(LABEL_COLOR);

        // Δημιουργία και διαμόρφωση των ετικετών για τα διάφορα πεδία πληροφοριών.
        countryLabel = createConfiguredLabel("Country:");
        stateLabel = createConfiguredLabel("State:");
        domainLabel = createConfiguredLabel("Domain:");
        webPageLabel = createConfiguredLabel("Web Page:");
        alphaTwoCodeLabel = createConfiguredLabel("Alpha Two Code:");
        contactLabel = createConfiguredLabel("Contact:");
        facultiesLabel = createConfiguredLabel("Faculties:");

        // Δημιουργία των panels για τα πεδία με τις λειτουργίες προβολής και επεξεργασίας.
        countryTodoPanel = createTodoPanel(safeValue(university.getCountry()));
        stateTodoPanel = createTodoPanel(safeValue(university.getStateProvince()));
        webPageTodoPanel = createTodoPanel(safeValue(String.join(", ", university.getWebPages())));
        domainTodoPanel = createTodoPanel(safeValue(university.getDomains()));
        alphaTwoCodeTodoPanel = createTodoPanel(safeValue(university.getAlphaTwoCode()));
        contactTodoPanel = createTodoPanel(safeValue(university.getContact()));
        facultiesTodoPanel = createTodoPanel(safeValue(university.getDepartment()));

        // Ορισμός του ίδιου φόντου για όλα τα panels ώστε να υπάρχει ομοιομορφία στο UI.
        countryTodoPanel.setBackground(BACKGROUND_COLOR);
        stateTodoPanel.setBackground(BACKGROUND_COLOR);
        webPageTodoPanel.setBackground(BACKGROUND_COLOR);
        domainTodoPanel.setBackground(BACKGROUND_COLOR);
        alphaTwoCodeTodoPanel.setBackground(BACKGROUND_COLOR);
        contactTodoPanel.setBackground(BACKGROUND_COLOR);
        facultiesTodoPanel.setBackground(BACKGROUND_COLOR);

        // Δημιουργία και διαμόρφωση του κουμπιού ενημέρωσης.
        updateButton = new JButton("Update University");
        updateButton.setBackground(LABEL_COLOR);
        updateButton.setForeground(Color.WHITE);
        updateButton.setFont(BUTTON_FONT);
        updateButton.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        // Προσθήκη listener για την εναλλαγή λειτουργίας επεξεργασίας/αποθήκευσης.
        updateButton.addActionListener(this::toggleEditMode);
    }

    /**
     * Δημιουργεί και διαμορφώνει μια ετικέτα (JLabel) με το δοσμένο κείμενο.
     *
     * @param text Το κείμενο που θα εμφανίζεται.
     * @return Η διαμορφωμένη ετικέτα.
     */
    private JLabel createConfiguredLabel(String text) {
        JLabel label = new JLabel();
        label.setFont(LABEL_FONT);
        label.setForeground(LABEL_COLOR);
        label.setText(text);
        return label;
    }

    /**
     * Δημιουργεί ένα panel με CardLayout που περιέχει δύο JTextArea:
     * μία για προβολή (view) και μία για επεξεργασία (edit).
     *
     * @param text Το αρχικό κείμενο που θα εμφανίζεται στην κατάσταση προβολής.
     * @return Το panel με τις δύο καταστάσεις ("view" και "edit").
     */
    private JPanel createTodoPanel(String text) {
        JPanel panel = new JPanel(new CardLayout());

        // Δημιουργία του JTextArea για προβολή (μη επεξεργάσιμο).
        JTextArea viewArea = new JTextArea(text);
        viewArea.setLineWrap(true);
        viewArea.setWrapStyleWord(true);
        viewArea.setEditable(false); // Απαγορεύουμε την επεξεργασία όταν βρίσκεται σε κατάσταση προβολής.
        viewArea.setFont(VIEW_FONT);
        viewArea.setForeground(TEXT_FOREGROUND);
        viewArea.setBackground(BACKGROUND_COLOR);
        viewArea.setBorder(null);

        // Δημιουργία του JTextArea για επεξεργασία.
        JTextArea editArea = new JTextArea();
        editArea.setLineWrap(true);
        editArea.setWrapStyleWord(true);
        editArea.setFont(EDIT_FONT);
        editArea.setForeground(TEXT_FOREGROUND);
        editArea.setBackground(BACKGROUND_COLOR);
        editArea.setBorder(null);

        // Προσθέτουμε τα δύο text areas στο panel με τα ονόματα "view" και "edit".
        panel.add(viewArea, "view");
        panel.add(editArea, "edit");

        return panel;
    }

    /**
     * Ορίζει τη διάταξη των components στο κύριο panel χρησιμοποιώντας το GroupLayout.
     */
    private void layoutComponents() {
        GroupLayout layout = new GroupLayout(universityViewMainPanel);
        universityViewMainPanel.setLayout(layout);

        // Ενεργοποίηση αυτόματων κενών μεταξύ των components.
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // Ορισμός οριζόντιας διάταξης (horizontal grouping)
        layout.setHorizontalGroup(
            layout.createParallelGroup(Alignment.LEADING)
                .addComponent(topDivider)
                .addComponent(bottomDivider)
                .addGroup(layout.createSequentialGroup()
                    .addGap(1)
                    .addComponent(universityLabelPanel, GroupLayout.PREFERRED_SIZE, 1043, GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createSequentialGroup()
                    .addGap(50)
                    .addGroup(layout.createParallelGroup(Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(Alignment.LEADING)
                                .addComponent(countryLabel)
                                .addComponent(stateLabel)
                                .addComponent(webPageLabel)
                                .addComponent(domainLabel)
                                .addComponent(alphaTwoCodeLabel)
                                .addComponent(contactLabel)
                                .addComponent(facultiesLabel))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(Alignment.LEADING)
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
                            .addGap(50, 150, Short.MAX_VALUE)))
            )            
        );

        // Ορισμός κάθετης διάταξης (vertical grouping)
        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addGap(20)
                .addComponent(universityLabelPanel, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
                .addGap(17)
                .addComponent(topDivider, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                .addGap(30)
                .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                    .addComponent(countryLabel)
                    .addComponent(countryTodoPanel))
                .addGap(SPACE_Y)
                .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                    .addComponent(stateLabel)
                    .addComponent(stateTodoPanel))
                .addGap(SPACE_Y)
                .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                    .addComponent(webPageLabel)
                    .addComponent(webPageTodoPanel))
                .addGap(SPACE_Y)
                .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                    .addComponent(domainLabel)
                    .addComponent(domainTodoPanel))
                .addGap(SPACE_Y)
                .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                    .addComponent(alphaTwoCodeLabel)
                    .addComponent(alphaTwoCodeTodoPanel))
                .addGap(SPACE_Y_IF_FACULTIES)
                .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                    .addComponent(contactLabel)
                    .addComponent(contactTodoPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(40)
                .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                    .addComponent(facultiesLabel)
                    .addComponent(facultiesTodoPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(89)
                .addComponent(bottomDivider, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                .addGap(16)
                .addComponent(updateButton, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE)
        );
    }

    /**
     * Επιστρέφει το δοσμένο κείμενο αν δεν είναι null ή κενό. Διαφορετικά, επιστρέφει "Unknown".
     *
     * @param value Το κείμενο που πρέπει να ελεγχθεί.
     * @return Το αρχικό κείμενο ή "Unknown" αν το κείμενο είναι null ή κενό.
     */
    private String safeValue(String value) {
        return (value == null || value.trim().isEmpty()) ? "Unknown" : value;
    }

    /**
     * Διαχειρίζεται το πάτημα του κουμπιού ενημέρωσης, εναλλάσσοντας μεταξύ λειτουργίας επεξεργασίας και αποθήκευσης.
     * Αν βρίσκεται σε λειτουργία επεξεργασίας, επιχειρεί την αποθήκευση των αλλαγών, αλλιώς φορτώνει τα δεδομένα για επεξεργασία.
     *
     * @param evt Το ActionEvent που προκαλεί την κλήση αυτής της μεθόδου.
     */
    private void toggleEditMode(ActionEvent evt) {
        // Ελέγχουμε αν το κείμενο του κουμπιού είναι "Save Changes" για να καθορίσουμε τη λειτουργία.
        boolean isEditMode = updateButton.getText().equals("Save Changes");

        if (isEditMode) {
            // Εάν είμαστε σε λειτουργία επεξεργασίας, επιχειρούμε την αποθήκευση των αλλαγών.
            saveTodoData();
        } else {
            // Διαφορετικά, φορτώνουμε τα υπάρχοντα δεδομένα στον πίνακα επεξεργασίας.
            loadTodoData();
            updateButton.setText("Save Changes");
            setEditMode(true);
        }
    }

    /**
     * Αποθηκεύει τα δεδομένα που έχουν τροποποιηθεί, χρησιμοποιώντας SwingWorker για εκτέλεση στο παρασκήνιο.
     * Εκτελεί επικύρωση της χώρας και, σε περίπτωση επιτυχίας, ενημερώνει το αντικείμενο {@code University}.
     */
    private void saveTodoData() {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                try {
                    // Επικύρωση αν η νέα τιμή της χώρας είναι έγκυρη μέσω του viewModel.
                    viewModel.checkIfCountryUpdateIsAvailable(getTextFromPanel(countryTodoPanel), universities);
                } catch (Exception ex) {
                    // Καταγραφή σφάλματος και εμφάνιση μηνύματος σε περίπτωση εξαίρεσης.
                    LOGGER.log(Level.SEVERE, "Σφάλμα κατά την επικύρωση της χώρας", ex);
                    JOptionPane.showMessageDialog(null, "Παρουσιάστηκε σφάλμα κατά την επικύρωση.",
                            "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                }
                return null;
            }

            @Override
            protected void done() {
                // Ελέγχουμε αν η επικύρωση της χώρας ήταν επιτυχής.
                if (viewModel.getAvailabilityOfCountryUpdate()) {
                    try {
                        // Ενημέρωση του αντικειμένου πανεπιστημίου με τις νέες τιμές από τα panels επεξεργασίας.
                        university.setCountry(getTextFromPanel(countryTodoPanel));
                        university.setStateProvince(getTextFromPanel(stateTodoPanel));
                        university.setWebPages(List.of(getTextFromPanel(webPageTodoPanel).split(",")));
                        university.setDomains(Arrays.toString(getTextFromPanel(domainTodoPanel).split(",")));
                        university.setAlphaTwoCode(getTextFromPanel(alphaTwoCodeTodoPanel));
                        university.setContact(getTextFromPanel(contactTodoPanel));
                        university.setDepartment(getTextFromPanel(facultiesTodoPanel));

                        // Κλήση του viewModel για την ενημέρωση του πανεπιστημίου στη βάση δεδομένων.
                        viewModel.updateExtendUniversity(university);
                    } catch (Exception ex) {
                        // Σε περίπτωση σφάλματος κατά την ενημέρωση, καταγράφουμε το σφάλμα και ενημερώνουμε τον χρήστη.
                        LOGGER.log(Level.SEVERE, "Σφάλμα κατά την ενημέρωση των δεδομένων του πανεπιστημίου", ex);
                        JOptionPane.showMessageDialog(null, "Παρουσιάστηκε σφάλμα κατά την ενημέρωση του πανεπιστημίου.",
                                "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    // Αντιγραφή των νέων δεδομένων από το editable πεδίο στο πεδίο προβολής.
                    copyBetweenTextAreas(true);
                    updateButton.setText("Update University");
                    setEditMode(false); // Επιστροφή στη λειτουργία προβολής.
                } else {
                    // Εάν η επικύρωση απέτυχε, εμφανίζεται προειδοποιητικό μήνυμα.
                    updateButton.setText("Save Changes");
                    JOptionPane.showMessageDialog(null, "Η χώρα που εισήχθη δεν υπάρχει. Παρακαλώ εισάγετε έγκυρη τιμή.",
                            "ΠΡΟΕΙΔΟΠΟΙΗΣΗ", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        // Εκτέλεση του SwingWorker για την ασύγχρονη επεξεργασία.
        worker.execute();
    }

    /**
     * Επιστρέφει το κείμενο από το editable JTextArea του panel, αφού το κόψει (trim).
     *
     * @param panel Το panel από το οποίο αντλείται το κείμενο.
     * @return Το κείμενο που έχει εισαχθεί στο editable πεδίο.
     */
    private String getTextFromPanel(JPanel panel) {
        JTextArea editTextArea = getEditTextArea(panel);
        return (editTextArea != null) ? editTextArea.getText().trim() : "";
    }

    /**
     * Αντιγράφει το κείμενο μεταξύ των JTextAreas των panels, είτε από edit σε view είτε αντίστροφα.
     *
     * @param fromEditToView Αν true, αντιγράφει το κείμενο από το editable στο view πεδίο.
     */
    private void copyBetweenTextAreas(boolean fromEditToView) {
        JPanel[] panels = {
            countryTodoPanel, stateTodoPanel, webPageTodoPanel, domainTodoPanel,
            alphaTwoCodeTodoPanel, contactTodoPanel, facultiesTodoPanel
        };

        for (JPanel panel : panels) {
            JTextArea source = fromEditToView ? getEditTextArea(panel) : getViewTextArea(panel);
            JTextArea target = fromEditToView ? getViewTextArea(panel) : getEditTextArea(panel);
            if (source != null && target != null) {
                target.setText(source.getText());
            }
        }
    }

    /**
     * Φορτώνει τα δεδομένα από τα view JTextAreas στα αντίστοιχα editable JTextAreas.
     */
    private void loadTodoData() {
        copyBetweenTextAreas(false);
    }

    /**
     * Εναλλάσσει τη λειτουργία εμφάνισης των panels μεταξύ επεξεργασίας και προβολής.
     *
     * @param edit Αν true, εμφανίζει τα components επεξεργασίας. Αν false, εμφανίζει τα components προβολής.
     */
    private void setEditMode(boolean edit) {
        togglePanel(edit, countryTodoPanel);
        togglePanel(edit, stateTodoPanel);
        togglePanel(edit, webPageTodoPanel);
        togglePanel(edit, domainTodoPanel);
        togglePanel(edit, alphaTwoCodeTodoPanel);
        togglePanel(edit, contactTodoPanel);
        togglePanel(edit, facultiesTodoPanel);
        revalidate();  // Ανανέωση του layout
        repaint();     // Ζωγράφιση των αλλαγών
    }

    /**
     * Εναλλάσσει το card που εμφανίζεται στο panel (είτε "edit" είτε "view") με βάση τη λειτουργία.
     *
     * @param edit  Αν true, εμφανίζει το card "edit". Αν false, το "view".
     * @param panel Το panel που διαχειρίζεται το CardLayout.
     */
    private void togglePanel(boolean edit, JPanel panel) {
        CardLayout cl = (CardLayout) panel.getLayout();
        cl.show(panel, edit ? "edit" : "view");
    }

    /**
     * Επιστρέφει το view (μη επεξεργάσιμο) JTextArea από το panel.
     *
     * @param panel Το panel που περιέχει τα JTextAreas.
     * @return Το JTextArea που χρησιμοποιείται για προβολή ή {@code null} αν δεν βρεθεί.
     */
    private JTextArea getViewTextArea(JPanel panel) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JTextArea) {
                JTextArea area = (JTextArea) comp;
                if (!area.isEditable()) {
                    return area;
                }
            }
        }
        return null;
    }

    /**
     * Επιστρέφει το edit (επεξεργάσιμο) JTextArea από το panel.
     *
     * @param panel Το panel που περιέχει τα JTextAreas.
     * @return Το JTextArea που χρησιμοποιείται για επεξεργασία ή {@code null} αν δεν βρεθεί.
     */
    private JTextArea getEditTextArea(JPanel panel) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JTextArea) {
                JTextArea area = (JTextArea) comp;
                if (area.isEditable()) {
                    return area;
                }
            }
        }
        return null;
    }
}
