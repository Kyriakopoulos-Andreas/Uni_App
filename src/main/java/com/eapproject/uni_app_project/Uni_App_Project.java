package com.eapproject.uni_app_project;

import com.eapproject.PresentationLayer.MainView;
import javax.swing.SwingUtilities;
import java.awt.Dimension;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.Level;

/**
 * Η κλάση {@code Uni_App_Project} αποτελεί το entry point της εφαρμογής.
 * Εκκινεί το κύριο παράθυρο (MainView) στο Event Dispatch Thread (EDT) χρησιμοποιώντας τη μέθοδο
 * {@code SwingUtilities.invokeLater}.
 */
public class Uni_App_Project {

    // Ορισμός του Logger για καταγραφή συμβάντων στο αρχείο logs/Uni_App_Project.log
    private static final Logger LOGGER = Logger.getLogger(Uni_App_Project.class.getName());

    /**
     * Η κύρια μέθοδος εκκίνησης της εφαρμογής.
     *
     * @param args Τα επιχειρήματα γραμμής εντολών (δεν χρησιμοποιούνται).
     */
    public static void main(String[] args) {
        // Αρχικοποίηση του Logger για την κλάση main
        initializeLogger();

        // Εκκίνηση του MainView στο Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                MainView main = new MainView();
                main.setVisible(true);
                main.setSize(new Dimension(1500, 1000));
                LOGGER.info("ℹ️ Η εφαρμογή ξεκίνησε επιτυχώς.");
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "❌ Σφάλμα κατά την εκκίνηση της εφαρμογής", e);
            }
        });
    }

    /**
     * Αρχικοποιεί τον Logger ώστε να καταγράφει τα συμβάντα στο αρχείο
     * {@code logs/Uni_App_Project.log} με τη χρήση του {@code SimpleFormatter}.
     */
    private static void initializeLogger() {
        try {
            // Δημιουργία φακέλου logs αν δεν υπάρχει.
            Files.createDirectories(Paths.get("logs"));

            // Αφαίρεση τυχόν υπαρχόντων handlers για αποφυγή διπλών καταγραφών.
            for (var h : LOGGER.getHandlers()) {
                LOGGER.removeHandler(h);
            }

            // Δημιουργία FileHandler για το αρχείο logs/Uni_App_Project.log (με append mode).
            FileHandler fileHandler = new FileHandler("logs/Uni_App_Project.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);

            LOGGER.setLevel(Level.ALL);
            LOGGER.setUseParentHandlers(false);

            LOGGER.info("📌 Έναρξη καταγραφής του Logger στο logs/Uni_App_Project.log");
        } catch (Exception e) {
            System.err.println("❌ Σφάλμα κατά την αρχικοποίηση του Logger: " + e.getMessage());
        }
    }
}

