package com.eapproject.DataLayer;
import com.eapproject.CommonLayer.Source.RepositoryCallback;
import com.eapproject.DB.University;

import java.util.List;
import java.util.ArrayList;
import java.util.Observable;



public class UniversitiesViewModel extends Observable {
    private final Repository repo = new Repository();
    private List<University> universities = new ArrayList<>();
    private String errorMessage;

    public UniversitiesViewModel() {
        fetchUniversities();
    }

    public List<University> getUniversities() {
        return universities;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void fetchUniversities() {
        new Thread(() -> {
            repo.getUniversities(new RepositoryCallback() {
                @Override
                public void onSuccess(List<University> data) {
//                    try {
//                        System.out.println("⏳ Προσομοίωση καθυστέρησης κατά την ανάκτηση των δεδομένων...");
//                        Thread.sleep(10000); // Καθυστέρηση 10 δευτερολέπτων για να δούμε πώς συμπεριφέρεται το GUI
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    universities = data;
                    errorMessage = null;
                    setChanged();
                    notifyObservers(); // 🔥 Ενημέρωση των Observers
                }

                @Override
                public void onError(String error) {
                    errorMessage = error;
                    setChanged();
                    notifyObservers(); // 🔥 Ενημέρωση των Observers
                }
            });
        }).start();
    }
}

