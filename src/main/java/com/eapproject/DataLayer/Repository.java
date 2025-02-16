package com.eapproject.DataLayer;

import com.eapproject.CommonLayer.Source.RepositoryCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class Repository {
    ApiService apiService;
    List<UniversityModel> data;

    public Repository() {
        apiService = RetrofitClient.getApiService();
        //database = UniversityDatabase.getInstance(); // Εδω βαλε το instance της βαση του στελιου

    }

;

// Εδω μόλις τελειώσεις με τα test και είναι να βγάλουμε μπροστά στο επόμενο επίπεδο τα data βαλέ άλλη μεταβλητή String ως όρισμα στο function για να περναμε απο εξω το country
    public void getUniversities(final RepositoryCallback callback) {
        Call<List<UniversityModel>> call = apiService.fetchCountries("Greece"); // Περνα το country εδω στην Θεση του greece




        call.enqueue(new Callback<List<UniversityModel>>() {
            @Override
            public void onResponse(Call<List<UniversityModel>> call, Response<List<UniversityModel>> response) {
                try {
                    if (response.isSuccessful() && response.body() != null) {

                        System.out.println("Response Code: " + response.code());
                        System.out.println("Response Body: " + response.body());

                        List<UniversityModel> universities = response.body();

                        if (universities == null || universities.isEmpty()) {
                            callback.onError("Received empty data from API"); //Σε περίπτωση empty δεδομένων
                            return;
                        }

                        saveToDb(universities);  // Γιαννη εδω παίρνουμε τα data απο το call και τα αποθηκεύουμε στην βάση.
                        // Εδώ μπορείς να καλέσεις όποια function θέλει τα data του API!
                        callback.onSuccess(universities);

                    } else {
                        callback.onError("Error: " + response.code());
                    }
                } catch (Exception e) {
                    callback.onError("Exception in onResponse: " + e.getMessage()); // Πιάνει τυχόν exceptions
                }
            }

            @Override
            public void onFailure(Call<List<UniversityModel>> call, Throwable t) {
                callback.onError("Failure: " + t.getMessage()); // Πιάνει σφάλματα δικτύου
            }
        });

    }

    private void saveToDb(List<UniversityModel> universities) {
        new Thread(() -> {
            //database.universityDao().insertAll(universities); // Εδω παίξε μπάλα για την αποθήκευση στην βάση.
            System.out.println("Data saved to database!");
        }).start();
    }
}
