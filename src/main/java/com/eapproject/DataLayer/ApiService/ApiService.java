package com.eapproject.DataLayer.ApiService;

import com.eapproject.DataLayer.DB.University;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface ApiService {

    @GET("Hipo/university-domains-list/master/world_universities_and_domains.json")
    Call<List<University>> getUniversities();


//    @GET("search?")
//    Call<List<University>> fetchUniversity(@Query("name") String country);

}
