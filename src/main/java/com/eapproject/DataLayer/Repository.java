package com.eapproject.DataLayer;

import com.eapproject.CommonLayer.Source.RepositoryCallback;
import com.eapproject.DB.University;
import com.eapproject.DB.UniversityDAO;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    private final ApiService apiService = RetrofitClient.getApiService();
    private final UniversityDAO dao = UniversityDAO.getInstance();
    private List<University> universitiesList = new ArrayList<>();

    public Repository() {

    }



// Εδω μόλις τελειώσεις με τα test και είναι να βγάλουμε μπροστά στο επόμενο επίπεδο τα data βαλέ άλλη μεταβλητή String ως όρισμα στο function για να περναμε απο εξω το country
    public void getUniversities(final RepositoryCallback callback) {
        Call<List<University>> call = apiService.getUniversities(); // Μπορείς να προσθέσεις το country εδώ, αν θες

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<University>> call, Response<List<University>> response) {
                try {
                    if (response.isSuccessful() && response.body() != null) {
    //                    System.out.println("Response Code: " + response.code());  // Εκτυπώνει τον κωδικό απόκρισης (π.χ. 200)
//                  System.out.println("Response Body: " + response.body());

                        List<University> universities = response.body();

                        if (universities == null || universities.isEmpty()) {
                            callback.onError("Received empty data from API");
                            return;
                        }


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


    public void fetchUniversitiesFromSpecificCountry(String country, RepositoryCallback callback) {
        new Thread(() -> {
            try {
                List<University> universities = dao.getUniversitiesFromSpecificCountry(country);
                universities.forEach(e -> {
                    System.out.println("un" + e.getName());
                });
                callback.onSuccess(universities);
            } catch (Exception e) {
                callback.onError(e.getMessage());
            }
        }).start();
    }



    public void saveOrUpdateToDB(University university) {
        new Thread(() -> {
            if(!university.getName().isEmpty()){
                dao.upsertUniversity(university);
                try {
                    // Δίνουμε λίγο χρόνο στην βάση να ολοκληρώσει την εγγραφή
                    Thread.sleep(500); // 500ms καθυστέρηση
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                University uni = dao.getUniversity(university.getName());
                if (uni != null) {
                    System.out.println("για ++ του view " + uni.getName());
                    //dao.increaseViewCount(uni.getId());
                }
                System.out.println(university);
                System.out.println("Get All" + dao.getAllUniversities());
            } else {
                System.out.println("Save to Db Issue");
            }
            System.out.println("Data saved to database!");
        }).start();
    }

    public University updateExtendUniversity(University university) {
        dao.updateUniversityUser(university);
        return university; // Επιστρέφουμε το ενημερωμένο αντικείμενο
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
