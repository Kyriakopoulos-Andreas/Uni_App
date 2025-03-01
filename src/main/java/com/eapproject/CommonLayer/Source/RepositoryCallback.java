package com.eapproject.CommonLayer.Source;

import com.eapproject.DataLayer.DB.University;
import java.util.List;

/**
 * Το {@code RepositoryCallback} ορίζει τις δύο μεθόδους callback που καλούνται
 * όταν ολοκληρωθεί μια ασύγχρονη λειτουργία στο {@code Repository}.
 * <p>
 * Η μέθοδος {@link #onSuccess(List)} καλείται σε περίπτωση επιτυχούς λήψης δεδομένων,
 * ενώ η μέθοδος {@link #onError(String)} καλείται σε περίπτωση σφάλματος.
 * </p>
 */
public interface RepositoryCallback {

    /**
     * Καλείται όταν η ασύγχρονη λειτουργία ολοκληρωθεί με επιτυχία.
     * <p>
     * Επιστρέφεται μια λίστα {@link University} που αντιπροσωπεύει τα αποτελέσματα
     * της επιτυχημένης λειτουργίας (π.χ. ανάκτηση δεδομένων από DB ή API).
     * </p>
     *
     * @param universities η λίστα των πανεπιστημίων που ελήφθησαν επιτυχώς
     */
    void onSuccess(List<University> universities);

    /**
     * Καλείται όταν παρουσιαστεί κάποιο σφάλμα κατά την εκτέλεση της ασύγχρονης λειτουργίας.
     * <p>
     * Ενημερώνει τον καλούντα για τη φύση του προβλήματος μέσω ενός μηνύματος σφάλματος.
     * </p>
     *
     * @param errorMessage το μήνυμα που περιγράφει το σφάλμα που προέκυψε
     */
    void onError(String errorMessage);
}
