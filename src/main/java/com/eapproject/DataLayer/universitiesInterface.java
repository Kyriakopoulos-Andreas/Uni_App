/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.eapproject.DataLayer;

import java.util.List;


/**
 *
 * @author Administrator
 */
public interface universitiesInterface {
    
    public Object[][] getCountries();
    public List<UniversityModel> getUniversities(String hint);
    public List<UniversityModel> getUniversities(String hint,String country);
    public void updateUniversity();
    
    
}
