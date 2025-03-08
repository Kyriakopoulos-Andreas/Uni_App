package com.eapproject.DomainLayer.Models;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.*;

/**
 * Η κλάση {@code UniversityModel} αναπαριστά το μοντέλο δεδομένων για ένα πανεπιστήμιο,
 * με πεδία όπως η χώρα, η επαρχία, οι ιστοσελίδες, τα domains κ.λπ.
 * <p>
 * Παρέχει επίσης βασικούς setters και getters, καθώς και μια μέθοδο {@code toString()}
 * για απλή εμφάνιση των πληροφοριών του πανεπιστημίου.
 * </p>
 */
public class UniversityModel {

    // Στατικός Logger για την καταγραφή ενεργειών και σφαλμάτων
    private static final Logger LOGGER = Logger.getLogger(UniversityModel.class.getName());

    // Στατική αρχικοποίηση του Logger
    static {
        initializeLogger();
    }

    // Πεδία που περιγράφουν τις πληροφορίες του πανεπιστημίου
    private String stateProvince;
    private String alphaTwoCode;
    private String country;
    private List<String> webPages;
    private List<String> domains;
    private String name;
    private String contact;  // Στο αρχικό μοντέλο φαινόταν να υπάρχει πεδίο contact, το προσθέτουμε.

    /**
     * Αρχικοποιεί τον Logger ώστε να καταγράφει μηνύματα σε αρχείο στο φάκελο logs,
     * χρησιμοποιώντας έναν {@link FileHandler} και {@link SimpleFormatter}.
     */
    private static void initializeLogger() {
        try {
            // Δημιουργεί το φάκελο logs, αν δεν υπάρχει
            Files.createDirectories(Paths.get("logs"));

            // Αφαιρεί τυχόν προηγούμενους handlers (αποφυγή διπλο-εκτυπώσεων)
            for (Handler handler : LOGGER.getHandlers()) {
                LOGGER.removeHandler(handler);
            }

            // Δημιουργεί έναν FileHandler για το αρχείο "logs/UniversityModel.log"
            FileHandler fileHandler = new FileHandler("logs/UniversityModel.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);

            LOGGER.setLevel(Level.ALL);
            LOGGER.setUseParentHandlers(false);

            LOGGER.info("📌 Έναρξη καταγραφής του Logger στο logs/UniversityModel.log");
        } catch (IOException e) {
            System.err.println("❌ Σφάλμα κατά την αρχικοποίηση του Logger στο UniversityModel: " + e.getMessage());
        }
    }

    /**
     * Βασικός κατασκευαστής για την κλάση {@code UniversityModel}.
     *
     * @param stateProvince  Η επαρχία/περιφέρεια του πανεπιστημίου.
     * @param alphaTwoCode   Το συντομογραφικό κωδικό (Alpha-2) της χώρας.
     * @param country        Η χώρα στην οποία βρίσκεται το πανεπιστήμιο.
     * @param webPages       Λίστα ιστοσελίδων του πανεπιστημίου.
     * @param domains        Λίστα domains που σχετίζονται με το πανεπιστήμιο.
     * @param name           Το όνομα του πανεπιστημίου.
     */
    public UniversityModel(String stateProvince,
                           String alphaTwoCode,
                           String country,
                           List<String> webPages,
                           List<String> domains,
                           String name) {
        // Χρησιμοποιούμε try-catch μόνο επίδειξης, π.χ. για να καταγράψουμε ένα μήνυμα
        try {
            this.stateProvince = stateProvince;
            this.alphaTwoCode = alphaTwoCode;
            this.country = country;
            this.webPages = webPages;
            this.domains = domains;
            this.name = name;

            LOGGER.info("ℹ️ UniversityModel created for " + name + " in " + country);
        } catch (Exception ex) {
            // Αν κάτι πάει στραβά (π.χ. NullPointerException), το καταγράφουμε
            LOGGER.log(Level.SEVERE, "❌ Σφάλμα κατά τον ορισμό δεδομένων στο UniversityModel constructor.", ex);
        }
    }

    // -------------------- Getters & Setters --------------------

    /**
     * Επιστρέφει την επαρχία/περιφέρεια του πανεπιστημίου.
     *
     * @return Η τιμή του stateProvince
     */
    public String getStateProvince() {
        return stateProvince;
    }

    /**
     * Θέτει την επαρχία/περιφέρεια του πανεπιστημίου.
     *
     * @param stateProvince Η νέα τιμή για το stateProvince
     */
    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
        LOGGER.info("🏛️ Updated stateProvince: " + stateProvince);
    }

    /**
     * Επιστρέφει τον κωδικό Alpha-2 της χώρας.
     *
     * @return Η τιμή του alphaTwoCode
     */
    public String getAlphaTwoCode() {
        return alphaTwoCode;
    }

    /**
     * Θέτει τον κωδικό Alpha-2 της χώρας.
     *
     * @param alphaTwoCode Νέα τιμή για το alphaTwoCode
     */
    public void setAlphaTwoCode(String alphaTwoCode) {
        this.alphaTwoCode = alphaTwoCode;
        LOGGER.info("🏛️ Updated alphaTwoCode: " + alphaTwoCode);
    }

    /**
     * Επιστρέφει τη χώρα στην οποία βρίσκεται το πανεπιστήμιο.
     *
     * @return Η τιμή του country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Θέτει τη χώρα στην οποία βρίσκεται το πανεπιστήμιο.
     *
     * @param country Η νέα τιμή για το country
     */
    public void setCountry(String country) {
        this.country = country;
        LOGGER.info("🏛️ Updated country: " + country);
    }

    /**
     * Επιστρέφει τη λίστα ιστοσελίδων του πανεπιστημίου.
     *
     * @return Μια λίστα με τα web pages
     */
    public List<String> getWebPages() {
        return webPages;
    }

    /**
     * Θέτει τη λίστα ιστοσελίδων του πανεπιστημίου.
     *
     * @param webPages Η νέα λίστα web pages
     */
    public void setWebPages(List<String> webPages) {
        this.webPages = webPages;
        LOGGER.info("🏛️ Updated webPages: " + webPages);
    }

    /**
     * Επιστρέφει τη λίστα domains που σχετίζονται με το πανεπιστήμιο.
     *
     * @return Μια λίστα domains
     */
    public List<String> getDomains() {
        return domains;
    }

    /**
     * Θέτει τη λίστα domains που σχετίζονται με το πανεπιστήμιο.
     *
     * @param domains Η νέα λίστα domains
     */
    public void setDomains(List<String> domains) {
        this.domains = domains;
        LOGGER.info("🏛️ Updated domains: " + domains);
    }

    /**
     * Επιστρέφει το όνομα του πανεπιστημίου.
     *
     * @return Η τιμή του name
     */
    public String getName() {
        return name;
    }

    /**
     * Θέτει το όνομα του πανεπιστημίου.
     *
     * @param name Νέα τιμή για το name
     */
    public void setName(String name) {
        this.name = name;
        LOGGER.info("🏛️ Updated name: " + name);
    }

    /**
     * Επιστρέφει τα στοιχεία επικοινωνίας του πανεπιστημίου (εφόσον υπάρχει).
     *
     * @return Η τιμή του contact
     */
    public String getContact() {
        return contact;
    }

    /**
     * Θέτει τα στοιχεία επικοινωνίας του πανεπιστημίου.
     *
     * @param contact Νέα τιμή για το contact
     */
    public void setContact(String contact) {
        this.contact = contact;
        LOGGER.info("🏛️ Updated contact: " + contact);
    }

    // -------------------- Άλλες Μέθοδοι --------------------

    /**
     * Επιστρέφει μια αναπαράσταση κειμένου του {@code UniversityModel}.
     *
     * @return Μια συμβολοσειρά που εμφανίζει τις τιμές των πεδίων.
     */
    @Override
    public String toString() {
        return "UniversityModel{" +
                "stateProvince='" + stateProvince + '\'' +
                ", alphaTwoCode='" + alphaTwoCode + '\'' +
                ", country='" + country + '\'' +
                ", webPages=" + webPages +
                ", domains=" + domains +
                ", name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}
