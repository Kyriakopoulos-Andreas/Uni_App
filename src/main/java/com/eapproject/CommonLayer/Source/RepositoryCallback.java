package com.eapproject.CommonLayer.Source;

import com.eapproject.DB.University;
import com.eapproject.DataLayer.UniversityModel;
import com.eapproject.PresentationLayer.UniversityView.UniversityView;

import java.util.List;

public interface RepositoryCallback {
    void onSuccess(List<University> universities);
    void onError(String errorMessage);
}