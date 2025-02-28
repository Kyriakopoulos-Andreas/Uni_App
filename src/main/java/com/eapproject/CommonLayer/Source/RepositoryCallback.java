package com.eapproject.CommonLayer.Source;

import com.eapproject.DataLayer.DB.University;

import java.util.List;

public interface RepositoryCallback {
    void onSuccess(List<University> universities);
    void onError(String errorMessage);
}