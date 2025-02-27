package com.eapproject.DataLayer;
import com.eapproject.CommonLayer.Source.RepositoryCallback;
import com.eapproject.DB.University;

import javax.swing.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Observable;
import java.util.stream.Collectors;


public class UniversitiesViewModel extends Observable {
    private final Repository repo = new Repository();
    private List<University> universities = new ArrayList<>();
    private String errorMessage;
    private List<University> universitiesFromSpecificCountry = new ArrayList<>();
    private University universityFromList;

    public University getUpdatedUniversity() {
        return updatedUniversity;
    }

    public void setUpdatedUniversity(University updatedUniversity) {
        this.updatedUniversity = updatedUniversity;
    }

    private University updatedUniversity;


    public UniversitiesViewModel() {
    }

    public University getUniversityFromList() {
        return universityFromList;
    }

    public List<University> getUniversities() {
        return universities;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setUniversityFromList(University university) {
        this.universityFromList = university;
        setChanged();
        notifyObservers();
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
                    notifyObservers();
                }

                @Override
                public void onError(String error) {
                    errorMessage = error;
                    setChanged();
                    notifyObservers();
                }
            });
        }).start();
    }

    public void fetchUniversitiesFromSpecificCountry(String country, ArrayList<University> universities) {

        List<University> filteredUniversities = universities.stream()
                .filter(uni -> uni.getCountry().equalsIgnoreCase(country))
                .collect(Collectors.toList());


        if (!filteredUniversities.isEmpty()) {
            this.universitiesFromSpecificCountry = filteredUniversities;
            filteredUniversities.forEach(e -> System.out.println("Uni: " + e.getName()));
            errorMessage = null;
        } else {
            errorMessage = "No universities found for country: " + country;
        }

        setChanged();
        notifyObservers();
    }

    public void updateExtendUniversity(University university) {
        System.out.println("UpdateCountry" + university.getCountry());
        System.out.println("Before Saving On Repo: state" + university.getStateProvince());
        System.out.println("Before Saving On Repo: aLPHA" + university.getAlphaTwoCode());
        System.out.println("Before Saving On Repo: Faculties" + university.getSchool());
        System.out.println("Before Saving On Repo: contact" + university.getContact());
        System.out.println("Before Saving On Repo: webPages" + university.getWebPages());
        new Thread(() -> {
            University updatedUniversity = repo.updateExtendUniversity(university);

            if (!updatedUniversity.getName().isEmpty()) {
                this.updatedUniversity = updatedUniversity;
                errorMessage = null;
            } else {
                errorMessage = "Update failed for " + university.getName();
            }

            // Ενημέρωση των παρατηρητών
            setChanged();
            notifyObservers();
        }).start();
    }





    public void getUniversityFromList(String universityName, ArrayList<University> universities) {
        try {
            if (universities == null || universities.isEmpty()) {
                throw new IllegalArgumentException("Η λίστα των πανεπιστημίων είναι κενή ή μη αρχικοποιημένη.");
            }

            for (University uni : universities) {
                if (uni.getName().equalsIgnoreCase(universityName)) {
                    setUniversityFromList(uni);
                    repo.saveOrUpdateToDB(uni);
                    return;
                }
            }

            // Αν δεν βρεθεί το πανεπιστήμιο, εμφανίζουμε προειδοποίηση
            JOptionPane.showMessageDialog(null,
                    "⚠️ Το πανεπιστήμιο '" + universityName + "' δεν βρέθηκε στη λίστα.",
                    "Προσοχή",
                    JOptionPane.WARNING_MESSAGE
            );

            setUniversityFromList(null);

        } catch (Exception e) {
            // Αν προκύψει οποιοδήποτε σφάλμα, το καταγράφουμε και εμφανίζουμε μήνυμα
            JOptionPane.showMessageDialog(null,
                    "❌ Σφάλμα κατά την αναζήτηση του πανεπιστημίου: " + e.getMessage(),
                    "Σφάλμα",
                    JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }
    }




    public List<University> getUniversitiesFromSpecificCountry() {
        return universitiesFromSpecificCountry;
    }

    private void setUniversitiesFromSpecificCountry(List<University> universitiesFromSpecificCountry) {
        this.universitiesFromSpecificCountry = universitiesFromSpecificCountry;
    }
}

