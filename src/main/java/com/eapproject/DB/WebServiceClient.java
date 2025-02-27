package com.eapproject.DB;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 🔹 Κλάση για τη λήψη δεδομένων Πανεπιστημίων από το Web Service.
 */
public class WebServiceClient {
    private static final String API_URL = "http://universities.hipolabs.com/search";
    private static final OkHttpClient client = new OkHttpClient();
    private static final Gson gson = new Gson();

    /**
     * 🔹 Μέθοδος που φέρνει όλα τα πανεπιστήμια από το Web Service.
     * @return Λίστα με τα πανεπιστήμια.
     */
    public static List<University> fetchUniversities() {
        List<University> universities = new ArrayList<>();

        try {
            Request request = new Request.Builder()
                    .url(API_URL)
                    .build();

            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                System.out.println("❌ Σφάλμα κατά την ανάκτηση δεδομένων: " + response.code());
                return universities;
            }

            // Μετατροπή JSON σε λίστα αντικειμένων `UniversityJSON`
            String jsonData = response.body().string();
            Type listType = new TypeToken<List<UniversityJSON>>() {}.getType();
            List<UniversityJSON> jsonList = gson.fromJson(jsonData, listType);

            // Μετατροπή `UniversityJSON` σε `University`
            for (UniversityJSON jsonUni : jsonList) {
                universities.add(convertToUniversity(jsonUni));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return universities;
    }

    /**
     * 🔹 Μετατροπή από `UniversityJSON` σε `University`
     */
    private static University convertToUniversity(UniversityJSON jsonUni) {
        return new University(
                0, // ID (θα δημιουργηθεί από τη ΒΔ)
                jsonUni.getName(),
                jsonUni.getCountry(),
                jsonUni.getAlphaTwoCode(),
                jsonUni.getStateProvince(),
                (jsonUni.getDomains() != null) ? String.join(",", jsonUni.getDomains()) : "",
                Collections.singletonList((jsonUni.getWebPages() != null) ? String.join(",", jsonUni.getWebPages()) : ""),
                "", // Σχολή (τοπικό πεδίο)
                "", // Τμήμα (τοπικό πεδίο)
                "", // Περιγραφή (τοπικό πεδίο)
                "", // Επικοινωνία (τοπικό πεδίο)
                "", // Σχόλια (τοπικό πεδίο)
                false // Δεν έχει τροποποιηθεί τοπικά
        );
    }

    /**
     * 🔹 Εσωτερική κλάση για την αντιστοίχιση JSON
     */
    private static class UniversityJSON {
        private String name;
        private String country;
        private String alpha_two_code;
        private String state_province;
        private List<String> domains;
        private List<String> web_pages;

        public String getName() { return name; }
        public String getCountry() { return country; }
        public String getAlphaTwoCode() { return alpha_two_code; }
        public String getStateProvince() { return state_province; }
        public List<String> getDomains() { return domains; }
        public List<String> getWebPages() { return web_pages; }
    }
}