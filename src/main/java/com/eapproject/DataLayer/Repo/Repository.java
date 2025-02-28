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

import java.util.List;
import java.util.concurrent.*;

public class Repository {
    private final ApiService apiService = RetrofitClient.getApiService();
    private final UniversityDAO dao = UniversityDAO.getInstance();

    public Repository() {

    }



    public void getUniversities(final RepositoryCallback callback) {

        Call<List<University>> call = apiService.getUniversities();

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<University>> call, Response<List<University>> response) {
                try {
                    if (response.isSuccessful() && response.body() != null) {

                        List<University> universitiesFromApi = response.body();

                        if (universitiesFromApi.isEmpty()) {
                            callback.onError("Received empty data from API");
                            return;
                        }
                        List<University> universitiesFromDb = getAllUniversitiesFromDb();
                        if (universitiesFromDb != null && !universitiesFromDb.isEmpty()) {
                            for (int i = 0; i < universitiesFromApi.size(); i++) {
                                University apiUniversity = universitiesFromApi.get(i);

                                for (University dbUniversity : universitiesFromDb) {
                                    if (apiUniversity.getName().equalsIgnoreCase(dbUniversity.getName())) {

                                        universitiesFromApi.set(i, dbUniversity);
                                        break;
                                    }
                                }
                            }
                        }

                        callback.onSuccess(universitiesFromApi);

                    } else {
                        callback.onError("Error: " + response.code());
                    }
                } catch (Exception e) {
                    callback.onError("Exception in onResponse: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<University>> call, @NotNull Throwable t) {
                callback.onError("Failure: " + t.getMessage());
            }
        });
    }





    public void saveOrUpdateToDB(University university) {
        new Thread(() -> {
            if(!university.getName().isEmpty()){
                dao.upsertUniversity(university);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                University uni = dao.getUniversity(university.getName());
                if (uni != null) {
                    System.out.println("για ++ του view " + uni.getName());
                    dao.increaseViewCount(uni.getId());
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
        System.out.println("UpdateCountry2" + university.getCountry());
        System.out.println("Before Saving On Repo State " + university.getStateProvince());
        System.out.println("Before Saving On Repo Alpha " + university.getAlphaTwoCode());
        System.out.println("Before Saving On Repo Faculties"  + university.getSchool());
        System.out.println("Before Saving On Repo  contact2 " + university.getContact());
        System.out.println("Before Saving On Repo webPages2 " + university.getWebPages());
        dao.updateUniversityUser(university);
        return university;
    }

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    public List<University> getAllUniversitiesFromDb() {

        Future<List<University>> future = executor.submit(() -> {
            try {
                return dao.getAllUniversities();
            } catch (Exception e) {
                System.out.println("Error fetching universities: " + e.getMessage());
                return null; // Επιστρέφουμε null σε περίπτωση σφάλματος
            }
        });

        try {
            return future.get();
        } catch (Exception e) {
            System.out.println("Error executing thread: " + e.getMessage());
            return null;
        }
    }

    public void fetchStatsAsync(RepositoryCallback callback) {
        new Thread(() -> {
            try {
                List<University> popularUniversities = dao.getPopularUniversities();
                if (popularUniversities == null || popularUniversities.isEmpty()) {
                    callback.onError("No Stats Available");
                } else {
                    callback.onSuccess(popularUniversities);
                }
            } catch (Exception e) {
                callback.onError(e.getMessage());
            }
        }).start();
    }

}



