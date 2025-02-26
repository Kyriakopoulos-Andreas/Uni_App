package com.eapproject.DataLayer;

import com.eapproject.CommonLayer.Source.RepositoryCallback;
import com.eapproject.DB.University;
import com.eapproject.DB.UniversityDAO;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class Repository {
    private final ApiService apiService = RetrofitClient.getApiService();
   private final UniversityDAO dao = UniversityDAO.getInstance();

    public Repository() {

    }

;

// Εδω μόλις τελειώσεις με τα test και είναι να βγάλουμε μπροστά στο επόμενο επίπεδο τα data βαλέ άλλη μεταβλητή String ως όρισμα στο function για να περναμε απο εξω το country
public void getUniversities(final RepositoryCallback callback) {
    Call<List<University>> call = apiService.getUniversities(); // Μπορείς να προσθέσεις το country εδώ, αν θες

    call.enqueue(new Callback<List<University>>() {
        @Override
        public void onResponse(Call<List<University>> call, Response<List<University>> response) {
            try {
                if (response.isSuccessful() && response.body() != null) {
//                    System.out.println("Response Code: " + response.code());  // Εκτυπώνει τον κωδικό απόκρισης (π.χ. 200)
//                    System.out.println("Response Body: " + response.body());  // Εκτυπώνει τα δεδομένα από το API

                    List<University> universities = response.body();

                    if (universities == null || universities.isEmpty()) {
                        callback.onError("Received empty data from API");
                        return;
                    }

                    saveToDb(universities);  // Αποθήκευση δεδομένων στη βάση
                    callback.onSuccess(universities);  // Επιτυχής λήψη δεδομένων

                } else {
                    callback.onError("Error: " + response.code());  // Κωδικός σφάλματος αν δεν είναι επιτυχές
//                    System.out.println("Error: " + response.code());
                }
            } catch (Exception e) {
                callback.onError("Exception in onResponse: " + e.getMessage());
//                System.out.println("Exception in onResponse: " + e.getMessage());  // Εκτύπωση του σφάλματος
            }
        }

        @Override
        public void onFailure(@NotNull Call<List<University>> call, @NotNull Throwable t) {
            callback.onError("Failure: " + t.getMessage());
            System.out.println("Failure: " + t.getMessage());  // Εκτύπωση του σφάλματος δικτύου
        }
    });
}


    private void saveToDb(List<University> universities) {
        new Thread(() -> {
            //database.universityDao().insertAll(universities); // Εδω παίξε μπάλα για την αποθήκευση στην βάση.
            int insertedCount = 0;
            if(!universities.isEmpty()){
                universities.forEach(dao::upsertUniversity);
            }else{
                System.out.println("Save to Db Issue");
            }
            System.out.println("Data saved to database!");
        }).start();
    }

//    public List<University> fetchUniversitiesFromDb(String country){
//        new Thread(() ->{
//            try{
//
//
//
//            }catch (Exception e){
//
//            }
//
//        }
//
//
//        ).start();
//    }
}
