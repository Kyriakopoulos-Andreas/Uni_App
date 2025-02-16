package com.eapproject.DataLayer;

import java.util.List;

public class UniversityModel {
    private int id;
    private String stateProvince;
    private String alphaTwoCode;
    private String country;
    private List<String> webPages;
    private List<String> domains;
    private String name;
    private String school;
    private String department;
    private String description;
    private String contact;
    private String comments;
    private boolean isModified;
    private int viewCount;

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

        /**
     * Επιστρέφει το ID του πανεπιστημίου.
     *
     * @return το ID
     */
    public int getId() { 
        return id; 
    }

    /**
     * Θέτει το ID του πανεπιστημίου.
     *
     * @param id το νέο ID
     */
    public void setId(int id) { 
        this.id = id; 
    }

    public String getSchool() { 
        return safeString(school); 
    }

    /**
     * Θέτει το όνομα της σχολής.
     *
     * @param school το νέο όνομα της σχολής
     */
    public void setSchool(String school) { 
        this.school = school; 
    }

    /**
     * Επιστρέφει το όνομα του τμήματος.
     *
     * @return το όνομα του τμήματος
     */
    public String getDepartment() { 
        return safeString(department); 
    }

    /**
     * Θέτει το όνομα του τμήματος.
     *
     * @param department το νέο όνομα του τμήματος
     */
    public void setDepartment(String department) { 
        this.department = department; 
    }

    /**
     * Επιστρέφει την περιγραφή του πανεπιστημίου.
     *
     * @return την περιγραφή
     */
    public String getDescription() { 
        return safeString(description); 
    }

    /**
     * Θέτει την περιγραφή του πανεπιστημίου.
     *
     * @param description η νέα περιγραφή
     */
    public void setDescription(String description) { 
        this.description = description; 
    }

    /**
     * Επιστρέφει τα στοιχεία επικοινωνίας του πανεπιστημίου.
     *
     * @return τα στοιχεία επικοινωνίας
     */
    public String getContact() { 
        return safeString(contact); 
    }

    /**
     * Θέτει τα στοιχεία επικοινωνίας του πανεπιστημίου.
     *
     * @param contact τα νέα στοιχεία επικοινωνίας
     */
    public void setContact(String contact) { 
        this.contact = contact; 
    }

    /**
     * Επιστρέφει τα σχόλια σχετικά με το πανεπιστήμιο.
     *
     * @return τα σχόλια
     */
    public String getComments() { 
        return safeString(comments); 
    }

    /**
     * Θέτει τα σχόλια σχετικά με το πανεπιστήμιο.
     *
     * @param comments τα νέα σχόλια
     */
    public void setComments(String comments) { 
        this.comments = comments; 
    }


        /**
     * Επιστρέφει την ένδειξη αν το πανεπιστήμιο έχει τροποποιηθεί τοπικά.
     *
     * @return true αν έχει τροποποιηθεί, αλλιώς false
     */
    public boolean isModified() { 
        return isModified; 
    }

    /**
     * Θέτει την ένδειξη τροποποίησης του πανεπιστημίου.
     *
     * @param isModified true αν έχει τροποποιηθεί, αλλιώς false
     */
    public void setModified(boolean isModified) { 
        this.isModified = isModified; 
    }

    /**
     * Επιστρέφει τον μετρητή προβολών του πανεπιστημίου.
     *
     * @return ο μετρητής προβολών
     */
    public int getViewCount() { 
        return viewCount; 
    }

    /**
     * Θέτει τον μετρητή προβολών του πανεπιστημίου.
     *
     * @param viewCount ο νέος μετρητής προβολών
     */
    public void setViewCount(int viewCount) { 
        this.viewCount = viewCount; 
    }

        /**
     * Επιστρέφει την είσοδο ως ασφαλές string για να αποφεύγονται null τιμές.
     *
     * @param value το string που θέλουμε να ελέγξουμε
     * @return το ίδιο string ή κενό string αν το value είναι null
     */
    private String safeString(String value) {
        return (value == null) ? "" : value;
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

    public UniversityModel(int id, String name, String country, String alphaTwoCode, String stateProvince,
                      List<String> domains, List<String> webPages, String school, String department,
                      String description, String contact, String comments, boolean isModified) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.alphaTwoCode = alphaTwoCode;
        this.stateProvince = stateProvince;
        this.domains = domains;
        this.webPages = webPages;
        this.school = school;
        this.department = department;
        this.description = description;
        this.contact = contact;
        this.comments = comments;
        this.isModified = isModified;
    }

    public UniversityModel(){
        
    }

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

