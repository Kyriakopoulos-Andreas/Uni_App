package com.eapproject.DataLayer.ApiService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.*;

/**
 * Η κλάση {@code RetrofitClient} παρέχει τη ρύθμιση και τη διαχείριση
 * ενός Retrofit instance που χρησιμοποιείται για κλήσεις σε REST APIs.
 * <p>
 * Χρησιμοποιεί το μοτίβο Singleton για τη δημιουργία ενός μοναδικού
 * {@link Retrofit} αντικειμένου, καθώς και έναν {@link Logger} για
 * την καταγραφή σφαλμάτων ή σημαντικών συμβάντων.
 */
public class RetrofitClient {

    /**
     * Το βασικό URL του API. Μπορεί να προσαρμοστεί ανάλογα με τις ανάγκες.
     */
    private static final String BASE_URL = "https://raw.githubusercontent.com/";

    /**
     * Το μοναδικό {@link Retrofit} instance που θα χρησιμοποιηθεί στην εφαρμογή.
     */
    private static Retrofit retrofit = null;

    /**
     * Στατικός {@code Logger} για καταγραφή συμβάντων που αφορούν το RetrofitClient.
     */
    private static final Logger LOGGER = Logger.getLogger(RetrofitClient.class.getName());

    // Στατική αρχικοποίηση του Logger
    static {
        initializeLogger();
    }

    /**
     * Αρχικοποιεί τον Logger ώστε να καταγράφει μηνύματα σε αρχείο
     * {@code logs/RetrofitClient.log}, χρησιμοποιώντας έναν {@link FileHandler}.
     * <p>
     * Επίσης, αφαιρεί τυχόν προηγούμενους handlers για να αποφευχθεί η πολλαπλή
     * καταγραφή των ίδιων μηνυμάτων.
     */
    private static void initializeLogger() {
        try {
            // Δημιουργία φακέλου logs αν δεν υπάρχει
            Files.createDirectories(Paths.get("logs"));

            // Αφαίρεση τυχόν προϋπάρχοντων handlers
            for (Handler handler : LOGGER.getHandlers()) {
                LOGGER.removeHandler(handler);
            }

            FileHandler fileHandler = new FileHandler("logs/RetrofitClient.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);

            LOGGER.setLevel(Level.ALL);
            LOGGER.setUseParentHandlers(false);

            LOGGER.info("📌 Έναρξη καταγραφής του Logger στο logs/RetrofitClient.log");
        } catch (IOException e) {
            System.err.println("❌ Σφάλμα κατά την αρχικοποίηση του Logger στο RetrofitClient: " + e.getMessage());
        }
    }

    /**
     * Επιστρέφει ένα instance του {@link ApiService}, το οποίο αποτελεί
     * το interface για τις κλήσεις στο API. Εάν το Retrofit instance δεν έχει
     * αρχικοποιηθεί, το δημιουργεί (Singleton μοτίβο).
     *
     * @return ένα {@link ApiService} για χρήση στις κλήσεις προς το API
     */
    public static ApiService getApiService() {
        if (retrofit == null) {
            try {
                LOGGER.info("🔧 Δημιουργία νέου Retrofit instance με BASE_URL: " + BASE_URL);
                
                // Ρυθμίζουμε το Retrofit με GSON converter και το βασικό URL
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                LOGGER.info("✅ Δημιουργήθηκε επιτυχώς το Retrofit instance.");
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, "❌ Σφάλμα κατά τη δημιουργία του Retrofit instance.", ex);
                throw ex; // (Προαιρετικά) επαν-ρίχνουμε την εξαίρεση, αν χρειάζεται να ενημερωθεί αλλού.
            }
        }
        // Εδώ υποθέτουμε ότι υπάρχει ξεχωριστό interface `ApiService.java`
        return retrofit.create(ApiService.class);
    }
}
