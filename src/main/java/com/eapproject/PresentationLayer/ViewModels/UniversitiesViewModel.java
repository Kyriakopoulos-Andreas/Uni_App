package com.eapproject.PresentationLayer.ViewModels;

import com.eapproject.CommonLayer.Source.RepositoryCallback;
import com.eapproject.DomainLayer.Models.University;
import com.eapproject.DataLayer.Repo.Repository;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Observable;
import java.util.stream.Collectors;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Η κλάση {@code UniversitiesViewModel} διαχειρίζεται τη λογική εμφάνισης και ενημέρωσης
 * των δεδομένων πανεπιστημίων, χρησιμοποιώντας το Repository για την πρόσβαση στα δεδομένα.
 * Υλοποιεί το Observer ώστε να ενημερώνει τα views όταν αλλάζουν τα δεδομένα.
 */
public class UniversitiesViewModel extends Observable {

    // Ορισμός του Logger για καταγραφή συμβάντων στο αρχείο logs/UniversitiesViewModel.log
    private static final Logger LOGGER = Logger.getLogger(UniversitiesViewModel.class.getName());

    // Repository για πρόσβαση στα δεδομένα
    private final Repository repo = new Repository();

    // Λίστα πανεπιστημίων και άλλα σχετικά δεδομένα
    private List<University> universities = new ArrayList<>();
    private String errorMessage;
    private List<University> universitiesFromSpecificCountry = new ArrayList<>();
    private University universityFromList;
    private List<University> statisticsList = new ArrayList<>();
    private Boolean availabilityOfCountryUpdate;
    private University updatedUniversity;

    /**
     * Κατασκευαστής της κλάσης UniversitiesViewModel.
     * Αρχικοποιεί τον Logger.
     */
    public UniversitiesViewModel() {
        initializeLogger();
    }

    /**
     * Αρχικοποιεί τον Logger ώστε να καταγράφει τα συμβάντα στο αρχείο
     * {@code logs/UniversitiesViewModel.log} με τη χρήση του {@code SimpleFormatter}.
     */
    private void initializeLogger() {
        try {
            // Δημιουργία φακέλου logs αν δεν υπάρχει.
            java.nio.file.Files.createDirectories(java.nio.file.Paths.get("logs"));
            // Αφαίρεση τυχόν υπαρχόντων handlers για αποφυγή διπλών καταγραφών.
            for (Handler h : LOGGER.getHandlers()) {
                LOGGER.removeHandler(h);
            }
            // Δημιουργία FileHandler για το αρχείο logs/UniversitiesViewModel.log (με append mode).
            FileHandler fileHandler = new FileHandler("logs/UniversitiesViewModel.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);

            LOGGER.setLevel(Level.ALL);
            LOGGER.setUseParentHandlers(false);

            LOGGER.info("📌 Έναρξη καταγραφής του Logger στο logs/UniversitiesViewModel.log");
        } catch (Exception e) {
            System.err.println("❌ Σφάλμα κατά την αρχικοποίηση του Logger: " + e.getMessage());
        }
    }

    // Getters και setters

    public Boolean getAvailabilityOfCountryUpdate() {
        return availabilityOfCountryUpdate;
    }

    public void setAvailabilityOfCountryUpdate(Boolean availabilityOfCountryUpdate) {
        this.availabilityOfCountryUpdate = availabilityOfCountryUpdate;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public University getUpdatedUniversity() {
        return updatedUniversity;
    }

    public void setUpdatedUniversity(University updatedUniversity) {
        this.updatedUniversity = updatedUniversity;
    }

    public University getUniversityFromList() {
        return universityFromList;
    }

    public List<University> getUniversities() {
        return universities;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Ορίζει το πανεπιστήμιο που βρέθηκε στη λίστα και ενημερώνει τους observers.
     *
     * @param university Το πανεπιστήμιο που βρέθηκε.
     */
    public void setUniversityFromList(University university) {
        this.universityFromList = university;
        setChanged();
        notifyObservers();
    }

    /**
     * Ανακτά τα πανεπιστήμια από το Repository.
     * Η μέθοδος εκτελείται σε ξεχωριστό thread και ενημερώνει τους observers.
     */
    public void fetchUniversities() {
        new Thread(() -> {
            repo.getUniversities(new RepositoryCallback() {
                @Override
                public void onSuccess(List<University> data) {
                    universities = data;
                    errorMessage = null;
                    setChanged();
                    notifyObservers();
                    LOGGER.info("ℹ️ Επανακτήθηκαν " + data.size() + " πανεπιστήμια.");
                }
                @Override
                public void onError(String error) {
                    errorMessage = error;
                    setChanged();
                    notifyObservers();
                    LOGGER.warning("⚠️ Σφάλμα στην ανάκτηση των πανεπιστημίων: " + error);
                }
            });
        }).start();
    }

    /**
     * Φιλτράρει τα πανεπιστήμια βάσει της χώρας.
     *
     * @param country       Η χώρα για την οποία γίνεται ο φιλτράρισμα.
     * @param universities  Η λίστα πανεπιστημίων από την οποία γίνεται ο φιλτράρισμα.
     */
    public void fetchUniversitiesFromSpecificCountry(String country, ArrayList<University> universities) {
        // Χρήση stream για φιλτράρισμα των πανεπιστημίων
        List<University> filteredUniversities = universities.stream()
                .filter(uni -> uni.getCountry().equalsIgnoreCase(country))
                .collect(Collectors.toList());

        if (!filteredUniversities.isEmpty()) {
            this.universitiesFromSpecificCountry = filteredUniversities;
            filteredUniversities.forEach(e -> LOGGER.info("Uni: " + e.getName()));
            errorMessage = null;
        } else {
            errorMessage = "No universities found for country: " + country;
            LOGGER.warning("⚠️ " + errorMessage);
        }

        setChanged();
        notifyObservers();
    }

    /**
     * Ελέγχει αν υπάρχει πανεπιστήμιο με τη δοσμένη χώρα στη λίστα.
     *
     * @param country       Η χώρα για έλεγχο.
     * @param universities  Η λίστα πανεπιστημίων στην οποία γίνεται ο έλεγχος.
     */
    public void checkIfCountryUpdateIsAvailable(String country, List<University> universities) {
        LOGGER.info("🔎 Έλεγχος διαθεσιμότητας για χώρα: " + country);
        // Χρήση του country μετά την επεξεργασία του και δημιουργία τελικής μεταβλητής για χρήση στο lambda.
        country = country.trim();
        final String finalCountry = country;
        // Η χρήση του finalCountry στο lambda είναι επιτρεπτή καθώς είναι final.
        boolean found = universities.stream()
                .anyMatch(universityCheck -> universityCheck.getCountry().trim().equalsIgnoreCase(finalCountry));
        availabilityOfCountryUpdate = found;
        LOGGER.info("ℹ️ Διαθεσιμότητα ενημέρωσης χώρας: " + availabilityOfCountryUpdate);
    }

    /**
     * Ενημερώνει το αντικείμενο πανεπιστημίου μέσω του Repository.
     *
     * @param university Το αντικείμενο πανεπιστημίου προς ενημέρωση.
     */
    public void updateExtendUniversity(University university) {
        LOGGER.info("UpdateCountry: " + university.getCountry());
        LOGGER.info("Before Saving - State: " + university.getStateProvince());
        LOGGER.info("Before Saving - Alpha: " + university.getAlphaTwoCode());
        LOGGER.info("Before Saving - Faculties: " + university.getSchool());
        LOGGER.info("Before Saving - Contact: " + university.getContact());
        LOGGER.info("Before Saving - WebPages: " + university.getWebPages());

        University updatedUniversity = repo.updateExtendUniversity(university);

        if (!updatedUniversity.getName().isEmpty()) {
            this.updatedUniversity = updatedUniversity;
            errorMessage = null;
            LOGGER.info("ℹ️ Ενημέρωση επιτυχής για " + university.getName());
        } else {
            errorMessage = "Update failed for " + university.getName();
            LOGGER.warning("⚠️ " + errorMessage);
        }
        setChanged();
        notifyObservers();
    }

    /**
     * Αναζητά ένα πανεπιστήμιο στη λίστα βάσει του ονόματός του.
     *
     * @param universityName Το όνομα του πανεπιστημίου που αναζητείται.
     * @param universities   Η λίστα πανεπιστημίων.
     */
    public void getUniversityFromList(String universityName, ArrayList<University> universities) {
        try {
            if (universities == null || universities.isEmpty()) {
                throw new IllegalArgumentException("Η λίστα των πανεπιστημίων είναι κενή ή μη αρχικοποιημένη.");
            }
            // Αναζήτηση του πανεπιστημίου χωρίς ευαισθησία σε πεζά/κεφαλαία
            for (University uni : universities) {
                if (uni.getName().equalsIgnoreCase(universityName)) {
                    setUniversityFromList(uni);
                    // Αποθήκευση ή ενημέρωση στη βάση δεδομένων
                    repo.saveOrUpdateToDB(uni);
                    LOGGER.info("ℹ️ Βρέθηκε το πανεπιστήμιο: " + uni.getName());
                    return;
                }
            }
            // Εάν δεν βρεθεί το πανεπιστήμιο, εμφάνιση προειδοποίησης στον χρήστη
            JOptionPane.showMessageDialog(null,
                    "⚠️ Το πανεπιστήμιο '" + universityName + "' δεν βρέθηκε στη λίστα.",
                    "Προσοχή!",
                    JOptionPane.WARNING_MESSAGE);
            setUniversityFromList(null);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "❌ Σφάλμα κατά την αναζήτηση του πανεπιστημίου: " + e.getMessage(),
                    "Σφάλμα",
                    JOptionPane.ERROR_MESSAGE);
            setErrorMessage(e.getMessage());
            LOGGER.log(Level.SEVERE, "❌ Σφάλμα στην αναζήτηση του πανεπιστημίου", e);
        }
    }

    /**
     * Ανακτά τα στατιστικά από το Repository.
     * Η μέθοδος εκτελείται σε ξεχωριστό thread και ενημερώνει τους observers.
     */
    public void fetchStats() {
        new Thread(() -> {
            repo.fetchStatsAsync(new RepositoryCallback() {
                @Override
                public void onSuccess(List<University> data) {
                    statisticsList = data;
                    errorMessage = null;
                    setChanged();
                    notifyObservers();
                    LOGGER.info("ℹ️ Επανακτήθηκαν στατιστικά για " + data.size() + " πανεπιστήμια.");
                }
                @Override
                public void onError(String error) {
                    errorMessage = error;
                    setChanged();
                    notifyObservers();
                    if (Objects.equals(error, "No Stats Available")) {
                        JOptionPane.showMessageDialog(null,
                                "Δεν υπάρχουν ακόμη διαθέσιμα στατιστικά.",
                                "Προειδοποίηση",
                                JOptionPane.WARNING_MESSAGE);
                        LOGGER.warning("⚠️ Τα στατιστικά δεν είναι διαθέσιμα.");
                        return;
                    }else{
                        if (error !=  null || error.isEmpty()) {
                            JOptionPane.showMessageDialog(null,
                                    "Τα στατιστικά δεν είναι διαθέσιμα!",
                                    "Προειδοποίηση",
                                    JOptionPane.WARNING_MESSAGE);
                            LOGGER.warning("⚠️ Τα στατιστικά δεν είναι διαθέσιμα.");
                            return;
                        }
                    }
                    LOGGER.warning("⚠️ Σφάλμα στην ανάκτηση στατιστικών: " + error);
                    LOGGER.warning("⚠️ Σφάλμα στην ανάκτηση στατιστικών: " + error);
                }
            });
        }).start();
    }

    // Getters & setters για τα επιμέρους δεδομένα

    public List<University> getUniversitiesFromSpecificCountry() {
        return universitiesFromSpecificCountry;
    }

    private void setUniversitiesFromSpecificCountry(List<University> universitiesFromSpecificCountry) {
        this.universitiesFromSpecificCountry = universitiesFromSpecificCountry;
    }

    public List<University> getStatisticsList() {
        return statisticsList;
    }

    public void setStatisticsList(List<University> statisticsList) {
        this.statisticsList = statisticsList;
    }
}
