package com.eapproject.DataLayer;

import com.eapproject.DB.University;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import java.util.List;

public interface ApiService {

    @GET("Hipo/university-domains-list/master/world_universities_and_domains.json")
    Call<List<University>> getUniversities();


    @GET("search?")
    Call<List<University>> fetchUniversity(@Query("name") String country);

}
