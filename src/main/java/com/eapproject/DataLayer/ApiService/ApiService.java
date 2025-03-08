package com.eapproject.DataLayer.ApiService;

import com.eapproject.DomainLayer.Models.University;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

/**
 * Το {@code ApiService} αποτελεί ένα interface για την επικοινωνία με το API
 * που επιστρέφει δεδομένα πανεπιστημίων.
 * <p>
 * Περιέχει τη βασική μέθοδο {@link #getUniversities()}, η οποία εκτελεί
 * αίτημα τύπου GET και επιστρέφει μια λίστα αντικειμένων {@link University}.
 * </p>
 */
public interface ApiService {

    /**
     * Κάνει αίτημα GET στη διαδρομή
     * {@code "Hipo/university-domains-list/master/world_universities_and_domains.json"}
     * προκειμένου να ανακτηθεί η λίστα των πανεπιστημίων.
     * <p>
     * Χρησιμοποιεί τον μηχανισμό της Retrofit μέσω του {@code Call<List<University>>}
     * ώστε η κλήση να εκτελείται ασύγχρονα και να επιστρέφει δεδομένα τύπου {@link University}.
     * </p>
     *
     * @return ένα {@link Call} που περιέχει μια λίστα {@link University} μόλις ολοκληρωθεί το αίτημα
     */
    @GET("Hipo/university-domains-list/master/world_universities_and_domains.json")
    Call<List<University>> getUniversities(); // Μέθοδος που καλεί το API και επιστρέφει την λίστα πανεπιστημίων
}

