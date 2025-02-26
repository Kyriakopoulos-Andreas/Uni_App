package com.eapproject.uni_app_project;

import com.eapproject.CommonLayer.Source.RepositoryCallback;
import com.eapproject.DataLayer.Repository;
import com.eapproject.DataLayer.UniversityModel;
import com.eapproject.PresentationLayer.MainView.MainView;
import retrofit2.Call;

import javax.swing.SwingUtilities;
import java.util.List;

/**
 *
 * @author kamoz
 */
public class Uni_App_Project {

    public static void main(String[] args) {
      SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainView main = new MainView();
                main.setVisible(true);
                main.setSize(new java.awt.Dimension(1500, 1000));
//                Repository test = new Repository();
//                RepositoryCallback list = new RepositoryCallback() {
//                    @Override
//                    public void onSuccess(List<UniversityModel> universities) {
//                        universities.forEach(a ->
//                              System.out.println(a.getCountry())
//                        );
//                    }
//
//                    @Override
//                    public void onError(String errorMessage) {
//                        System.out.println(errorMessage);
//
//                    }
//                };
//                test.getUniversities(list);




            }
        });

    }
}
