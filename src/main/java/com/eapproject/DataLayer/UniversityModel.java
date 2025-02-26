package com.eapproject.DataLayer;

import java.util.List;

public class UniversityModel {
    private String stateProvince;
    private String alphaTwoCode;
    private String country;
    private List<String> webPages;
    private List<String> domains;
    private String name;
    private String contact;

    // Getters και Setters
    public String getStateProvince() {
        return stateProvince;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public String getAlphaTwoCode() {
        return alphaTwoCode;
    }

    public void setAlphaTwoCode(String alphaTwoCode) {
        this.alphaTwoCode = alphaTwoCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<String> getWebPages() {
        return webPages;
    }

    public void setWebPages(List<String> webPages) {
        this.webPages = webPages;
    }

    public List<String> getDomains() {
        return domains;
    }

    public void setDomains(List<String> domains) {
        this.domains = domains;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Constructor
    public UniversityModel(String stateProvince, String alphaTwoCode, String country, List<String> webPages, List<String> domains, String name) {
        this.stateProvince = stateProvince;
        this.alphaTwoCode = alphaTwoCode;
        this.country = country;
        this.webPages = webPages;
        this.domains = domains;
        this.name = name;
    }

    //For Testing
    @Override
    public String toString() {
        return "University{" +
                "stateProvince='" + stateProvince + '\'' +
                ", alphaTwoCode='" + alphaTwoCode + '\'' +
                ", country='" + country + '\'' +
                ", webPages=" + webPages +
                ", domains=" + domains +
                ", name='" + name + '\'' +
                '}';
    }
}

