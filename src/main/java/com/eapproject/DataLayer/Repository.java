package com.eapproject.DataLayer;

import java.util.List;

import com.eapproject.CommonLayer.Source.RepositoryCallback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    ApiService apiService;
    public Repository() {
        ApiService apiService = RetrofitClient.getApiService();
    }

    public void getUniversities(final RepositoryCallback callback) {
        Call<List<UniversityModel>> call = apiService.fetchCountries("*");


        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<UniversityModel>> call, Response<List<UniversityModel>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    callback.onSuccess(response.body());
                } else {

                    callback.onError("Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<UniversityModel>> call, Throwable t) {
                callback.onError("Failure: " + t.getMessage());
            }
        });
    }
}
