package com.eapproject.DataLayer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import java.util.List;

public interface ApiService {

    @GET("search?")
    Call<List<UniversityModel>> fetchCountries(@Query("country") String country);


    @GET("search?")
    Call<List<UniversityModel>> fetchUniversity(@Query("name") String country);

}
