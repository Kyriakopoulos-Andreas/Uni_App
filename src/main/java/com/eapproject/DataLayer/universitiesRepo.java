/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eapproject.DataLayer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class universitiesRepo implements universitiesInterface{
    private  UniversityDAO dbDAO = UniversityDAO.getInstance();
    
    @Override
    public Object[][] getCountries(){

    Object[][] countries =  {{"1", "Afghanistan"}, {"2", "Albania"}, {"3", "Algeria"}, {"4", "Andorra"}, {"5", "Angola"},
    {"6", "Antigua and Barbuda"}, {"7", "Argentina"}, {"8", "Armenia"}, {"9", "Australia"}, {"10", "Austria"},
    {"11", "Azerbaijan"}, {"12", "Bahamas"}, {"13", "Bahrain"}, {"14", "Bangladesh"}, {"15", "Barbados"},
    {"16", "Belarus"}, {"17", "Belgium"}, {"18", "Belize"}, {"19", "Benin"}, {"20", "Bhutan"},
    {"21", "Bolivia"}, {"22", "Bosnia and Herzegovina"}, {"23", "Botswana"}, {"24", "Brazil"}, {"25", "Brunei"},
    {"26", "Bulgaria"}, {"27", "Burkina Faso"}, {"28", "Burundi"}, {"29", "Cabo Verde"}, {"30", "Cambodia"},
    {"31", "Cameroon"}, {"32", "Canada"}, {"33", "Central African Republic"}, {"34", "Chad"}, {"35", "Chile"},
    {"36", "China"}, {"37", "Colombia"}, {"38", "Comoros"}, {"39", "Congo (Congo-Brazzaville)"}, {"40", "Costa Rica"},
    {"41", "Croatia"}, {"42", "Cuba"}, {"43", "Cyprus"}, {"44", "Czechia (Czech Republic)"}, {"45", "Democratic Republic of the Congo"},
    {"46", "Denmark"}, {"47", "Djibouti"}, {"48", "Dominica"}, {"49", "Dominican Republic"}, {"50", "Ecuador"},
    {"51", "Egypt"}, {"52", "El Salvador"}, {"53", "Equatorial Guinea"}, {"54", "Eritrea"}, {"55", "Estonia"},
    {"56", "Eswatini"}, {"57", "Ethiopia"}, {"58", "Fiji"}, {"59", "Finland"}, {"60", "France"},
    {"61", "Gabon"}, {"62", "Gambia"}, {"63", "Georgia"}, {"64", "Germany"}, {"65", "Ghana"},
    {"66", "Greece"}, {"67", "Grenada"}, {"68", "Guatemala"}, {"69", "Guinea"}, {"70", "Guinea-Bissau"},
    {"71", "Guyana"}, {"72", "Haiti"}, {"73", "Honduras"}, {"74", "Hungary"}, {"75", "Iceland"},
    {"76", "India"}, {"77", "Indonesia"}, {"78", "Iran"}, {"79", "Iraq"}, {"80", "Ireland"},
    {"81", "Israel"}, {"82", "Italy"}, {"83", "Ivory Coast"}, {"84", "Jamaica"}, {"85", "Japan"},
    {"86", "Jordan"}, {"87", "Kazakhstan"}, {"88", "Kenya"}, {"89", "Kiribati"}, {"90", "Korea (North)"},
    {"91", "Korea (South)"}, {"92", "Kuwait"}, {"93", "Kyrgyzstan"}, {"94", "Laos"}, {"95", "Latvia"},
    {"96", "Lebanon"}, {"97", "Lesotho"}, {"98", "Liberia"}, {"99", "Libya"}, {"100", "Liechtenstein"},
    {"101", "Lithuania"}, {"102", "Luxembourg"}, {"103", "Madagascar"}, {"104", "Malawi"}, {"105", "Malaysia"},
    {"106", "Maldives"}, {"107", "Mali"}, {"108", "Malta"}, {"109", "Marshall Islands"}, {"110", "Mauritania"},
    {"111", "Mauritius"}, {"112", "Mexico"}, {"113", "Micronesia"}, {"114", "Moldova"}, {"115", "Monaco"},
    {"116", "Mongolia"}, {"117", "Montenegro"}, {"118", "Morocco"}, {"119", "Mozambique"}, {"120", "Myanmar (formerly Burma)"},
    {"121", "Namibia"}, {"122", "Nauru"}, {"123", "Nepal"}, {"124", "Netherlands"}, {"125", "New Zealand"},
    {"126", "Nicaragua"}, {"127", "Niger"}, {"128", "Nigeria"}, {"129", "North Macedonia"}, {"130", "Norway"},
    {"131", "Oman"}, {"132", "Pakistan"}, {"133", "Palau"}, {"134", "Panama"}, {"135", "Papua New Guinea"},
    {"136", "Paraguay"}, {"137", "Peru"}, {"138", "Philippines"}, {"139", "Poland"}, {"140", "Portugal"},
    {"141", "Qatar"}, {"142", "Romania"}, {"143", "Russia"}, {"144", "Rwanda"}, {"145", "Saint Kitts and Nevis"},
    {"146", "Saint Lucia"}, {"147", "Saint Vincent and the Grenadines"}, {"148", "Samoa"}, {"149", "San Marino"}, {"150", "Sao Tome and Principe"},
    {"151", "Saudi Arabia"}, {"152", "Senegal"}, {"153", "Serbia"}, {"154", "Seychelles"}, {"155", "Sierra Leone"},
    {"156", "Singapore"}, {"157", "Slovakia"}, {"158", "Slovenia"}, {"159", "Solomon Islands"}, {"160", "Somalia"},
    {"161", "South Africa"}, {"162", "South Sudan"}, {"163", "Spain"}, {"164", "Sri Lanka"}, {"165", "Sudan"},
    {"166", "Suriname"}, {"167", "Sweden"}, {"168", "Switzerland"}, {"169", "Syria"}, {"170", "Taiwan"},
    {"171", "Tajikistan"}, {"172", "Tanzania"}, {"173", "Thailand"}, {"174", "Timor-Leste"}, {"175", "Togo"},
    {"176", "Tonga"}, {"177", "Trinidad and Tobago"}, {"178", "Tunisia"}, {"179", "Turkey"}, {"180", "Turkmenistan"},
    {"181", "Tuvalu"}, {"182", "Uganda"}, {"183", "Ukraine"}, {"184", "United Arab Emirates"}, {"185", "United Kingdom"},
    {"186", "United States of America"}, {"187", "Uruguay"}, {"188", "Uzbekistan"}, {"189", "Vanuatu"}, {"190", "Vatican City"},
    {"191", "Venezuela"}, {"192", "Vietnam"}, {"193", "Yemen"}, {"194", "Zambia"}, {"195", "Zimbabwe"}};

    return countries;
    }



    @Override
    public UniversityModel getUniversities(String hint){

            OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();

            Request request = new Request.Builder().url("http://universities.hipolabs.com/search?name="+hint).build();
        
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String responseString = response.body().string();
                    System.out.println(responseString);
                    Gson gson = new Gson();
                    Type UniversityListType = new TypeToken <List<UniversityModel>>() {}.getType();
                    List<UniversityModel> Universities = gson.fromJson(responseString, UniversityListType);
                       for (UniversityModel university : Universities) {
                           dbDAO.upsertUniversity(university);
                       }
                    //return Universities.subList(0, 10);
                    return Universities.getFirst();
                }

            }
            catch (IOException e){
                e.printStackTrace();
            }

        return null;

    }
        

    @Override
    public List<UniversityModel> getUniversities(String hint, String country){
            boolean checkdb = true;
            if (checkdb){
                OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

            Request request = new Request.Builder().url("http://universities.hipolabs.com/search?name="+hint).build();
        
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String responseString = response.body().string();
                    System.out.println(responseString);
                    Gson gson = new Gson();
                    Type UniversityListType = new TypeToken<List<UniversityModel>>() {}.getType();
                    List<UniversityModel> Universities = gson.fromJson(responseString, UniversityListType);
                       for (UniversityModel university : Universities) {
                           System.out.println(university.getCountry());
                       }
                }

            }
            catch (IOException e){
                e.printStackTrace();
            }



        }
            return null;
    }

    @Override
    public void updateUniversity(){
        System.out.println("");
    }
    
}
