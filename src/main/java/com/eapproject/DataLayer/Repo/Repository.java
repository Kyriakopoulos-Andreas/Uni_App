package com.eapproject.DataLayer.Repo;

import com.eapproject.CommonLayer.Source.RepositoryCallback;
import com.eapproject.DataLayer.DB.University;
import com.eapproject.DataLayer.DB.UniversityDAO;
import com.eapproject.DataLayer.ApiService.ApiService;
import com.eapproject.DataLayer.ApiService.RetrofitClient;

import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.*;
import java.util.logging.*;

/**
 * Η κλάση {@code Repository} διαχειρίζεται τη ροή δεδομένων μεταξύ του API (Retrofit),
 * της βάσης δεδομένων (μέσω {@link UniversityDAO}) και της εφαρμογής.
 * <p>
 * Περιλαμβάνει λειτουργίες όπως:
 * <ul>
 *   <li>Λήψη λίστας πανεπιστημίων από το API.</li>
 *   <li>Ενημέρωση/Εισαγωγή πανεπιστημίου στη βάση.</li>
 *   <li>Ενημέρωση επεκταμένων δεδομένων (π.χ. χώρα, stateProvince).</li>
 *   <li>Λήψη στατιστικών δεδομένων (π.χ. πιο δημοφιλή πανεπιστήμια).</li>
 * </ul>
 * Χρησιμοποιεί Logger με μηνύματα σε μορφή emoji για καταγραφή ενεργειών και σφαλμάτων.
 */
public class Repository {

    // Στατικός Logger για την καταγραφή ενεργειών και σφαλμάτων
    private static final Logger LOGGER = Logger.getLogger(Repository.class.getName());

    // Αρχικοποίηση του Logger σε στατικό block ή μέθοδο
    static {
        initializeLogger();
    }

    // Διαχειριστές του API και της βάσης δεδομένων
    private final ApiService apiService = RetrofitClient.getApiService();
    private final UniversityDAO dao = UniversityDAO.getInstance();

    // Χρησιμοποιούμε έναν ExecutorService για διαχείριση νημάτων στις βάσεις δεδομένων
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    /**
     * Προεπιλεγμένος κατασκευαστής της κλάσης {@code Repository}.
     */
    public Repository() {
        LOGGER.info("📌 Repository initialized.");
    }

    /**
     * Αρχικοποιεί τον Logger, ώστε να καταγράφει μηνύματα σε αρχείο {@code logs/Repository.log}.
     */
    private static void initializeLogger() {
        try {
            // Δημιουργία φακέλου logs, αν δεν υπάρχει
            Files.createDirectories(Paths.get("logs"));

            // Αφαίρεση τυχόν προηγούμενων handlers για αποφυγή διπλο-καταγραφών
            for (Handler h : LOGGER.getHandlers()) {
                LOGGER.removeHandler(h);
            }

            // FileHandler για καταγραφή στο logs/Repository.log
            FileHandler fileHandler = new FileHandler("logs/Repository.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);

            LOGGER.setLevel(Level.ALL);
            LOGGER.setUseParentHandlers(false);

            LOGGER.info("📌 Έναρξη καταγραφής του Logger στο logs/Repository.log");
        } catch (IOException e) {
            System.err.println("❌ Σφάλμα κατά την αρχικοποίηση του Logger στο Repository: " + e.getMessage());
        }
    }

    /**
     * Μέθοδος που ζητά από το API τη λίστα πανεπιστημίων, και ενημερώνει το RepositoryCallback
     * για την επιτυχία ή την αποτυχία της λειτουργίας.
     *
     * @param callback Παρέχει onSuccess και onError για αναφορά αποτελεσμάτων.
     */
    public void getUniversities(final RepositoryCallback callback) {
        LOGGER.info("🔎 Fetching universities from API...");
        // Λαμβάνουμε ένα Retrofit2 Call για τη λίστα πανεπιστημίων
        Call<List<University>> call = apiService.getUniversities();

        // Κάνουμε enqueue την κλήση για ασύγχρονη λήψη των δεδομένων
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<University>> call, Response<List<University>> response) {
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        // Παίρνουμε τη λίστα από το body της απόκρισης
                        List<University> universitiesFromApi = response.body();

                        if (universitiesFromApi.isEmpty()) {
                            // Επιστρέφουμε σφάλμα αν η λίστα είναι κενή
                            String errorMsg = "Received empty data from API";
                            callback.onError(errorMsg);
                            LOGGER.warning("⚠️ " + errorMsg);
                            return;
                        }

                        // Ελέγχουμε ήδη αποθηκευμένα πανεπιστήμια στην DB
                        List<University> universitiesFromDb = getAllUniversitiesFromDb();
                        if (universitiesFromDb != null && !universitiesFromDb.isEmpty()) {
                            // Για κάθε πανεπιστήμιο από το API ελέγχουμε αν υπάρχει στη DB
                            for (int i = 0; i < universitiesFromApi.size(); i++) {
                                University apiUniversity = universitiesFromApi.get(i);

                                for (University dbUniversity : universitiesFromDb) {
                                    if (apiUniversity.getName().equalsIgnoreCase(dbUniversity.getName())) {
                                        // Αν υπάρχει ήδη, αντικαθιστούμε το αντικείμενο API μ' εκείνο της DB
                                        universitiesFromApi.set(i, dbUniversity);
                                        break;
                                    }
                                }
                            }
                        }

                        // Επιστρέφουμε επιτυχώς τη λίστα πανεπιστημίων
                        LOGGER.info("✅ Universities fetched successfully from API. Size: " + universitiesFromApi.size());
                        callback.onSuccess(universitiesFromApi);

                    } else {
                        // Αν η απόκριση του server δεν είναι επιτυχής
                        String errorMsg = "Error: " + response.code();
                        LOGGER.warning("⚠️ " + errorMsg);
                        callback.onError(errorMsg);
                    }
                } catch (Exception e) {
                    // Σε περίπτωση εξαίρεσης εντός του onResponse
                    String errorInResponse = "Exception in onResponse: " + e.getMessage();
                    LOGGER.log(Level.SEVERE, "❌ " + errorInResponse, e);
                    callback.onError(errorInResponse);
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<University>> call, @NotNull Throwable t) {
                // Αν η κλήση απέτυχε πριν φτάσει στο onResponse
                String failureMsg = "Failure: " + t.getMessage();
                LOGGER.log(Level.SEVERE, "❌ " + failureMsg, t);
                callback.onError(failureMsg);
            }
        });
    }

    /**
     * Αποθηκεύει ή ενημερώνει τα στοιχεία ενός {@link University} στη βάση δεδομένων.
     * <p>Επίσης αυξάνει το view count του συγκεκριμένου πανεπιστημίου αν υπάρχει ήδη.</p>
     *
     * @param university το πανεπιστήμιο προς αποθήκευση ή ενημέρωση.
     */
    public void saveOrUpdateToDB(University university) {
        LOGGER.info("💾 Saving or updating university in DB: " + university.getName());
        new Thread(() -> {
            try {
                if (!university.getName().isEmpty()) {
                    // Upsert στη βάση μέσω DAO
                    dao.upsertUniversity(university);
                    // Μικρή αναμονή για προσομοίωση φόρτου
                    Thread.sleep(500);

                    // Λήψη του πανεπιστημίου από τη βάση για αύξηση του view count
                    University uni = dao.getUniversity(university.getName());
                    if (uni != null) {
                        LOGGER.info("🔎 Found university in DB for view increment: " + uni.getName());
                        dao.increaseViewCount(uni.getId());
                    }
                    LOGGER.info("✅ University saved: " + university.getName());
                } else {
                    LOGGER.warning("⚠️ Attempted to save an empty-named university to DB.");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                LOGGER.log(Level.SEVERE, "❌ Thread interrupted while saving/updating university.", e);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "❌ Error in saveOrUpdateToDB for " + university.getName(), e);
            }
        }).start();
    }

    /**
     * Ενημερώνει ένα πανεπιστήμιο με εκτεταμένα δεδομένα (π.χ. χώρα, stateProvince)
     * και επιστρέφει το ενημερωμένο αντικείμενο.
     *
     * @param university Το πανεπιστήμιο προς ενημέρωση
     * @return Το ίδιο αντικείμενο πανεπιστημίου, μετά την αναβάθμιση των δεδομένων του στη DB.
     */
    public University updateExtendUniversity(University university) {
        LOGGER.info("🔧 Updating extended data for university: " + university.getName());
        try {
            // Κλήση του DAO για την ενημέρωση
            dao.updateUniversityUser(university);
            LOGGER.info("✅ Extended data updated for: " + university.getName());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "❌ Failed to update extended data for " + university.getName(), e);
        }
        return university;
    }

    /**
     * Επιστρέφει τη λίστα όλων των πανεπιστημίων από τη βάση δεδομένων, χρησιμοποιώντας έναν ExecutorService.
     *
     * @return Λίστα {@link University} ή {@code null} σε περίπτωση σφάλματος.
     */
    public List<University> getAllUniversitiesFromDb() {
        LOGGER.info("🔎 Fetching all universities from DB...");
        Future<List<University>> future = executor.submit(() -> {
            try {
                return dao.getAllUniversities();
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "❌ Error fetching universities from DB: " + e.getMessage(), e);
                return null; // Επιστρέφουμε null σε περίπτωση σφάλματος
            }
        });

        try {
            List<University> result = future.get();
            if (result == null) {
                LOGGER.warning("⚠️ Received null result when fetching universities from DB.");
            } else {
                LOGGER.info("✅ Successfully fetched " + result.size() + " universities from DB.");
            }
            return result;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "❌ Error executing thread to fetch universities: " + e.getMessage(), e);
            return null;
        }
    }

    /**
     * Ασύγχρονη μέθοδος που επιστρέφει στατιστικά δεδομένα (τα πιο δημοφιλή πανεπιστήμια) από τη βάση,
     * διαμέσου του callback.
     *
     * @param callback το {@link RepositoryCallback} για να ειδοποιήσουμε για επιτυχία ή σφάλμα
     */
    public void fetchStatsAsync(RepositoryCallback callback) {
        LOGGER.info("🔎 Fetching stats (popular universities) from DB asynchronously...");
        new Thread(() -> {
            try {
                List<University> popularUniversities = dao.getPopularUniversities();
                if (popularUniversities == null || popularUniversities.isEmpty()) {
                    String error = "No Stats Available";
                    LOGGER.warning("⚠️ " + error);
                    callback.onError(error);
                } else {
                    LOGGER.info("✅ Fetched " + popularUniversities.size() + " popular universities from DB.");
                    callback.onSuccess(popularUniversities);
                }
            } catch (Exception e) {
                String errorMsg = "❌ Exception while fetching stats: " + e.getMessage();
                LOGGER.log(Level.SEVERE, errorMsg, e);
                callback.onError(e.getMessage());
            }
        }).start();
    }
}
