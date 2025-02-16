package com.eapproject.uni_app_project;

import java.util.List;

import javax.swing.SwingUtilities;

import com.eapproject.DataLayer.DBUtil;
import com.eapproject.DataLayer.UniversityDAO;
import com.eapproject.DataLayer.UniversityModel;
import com.eapproject.DataLayer.universitiesRepo;
import com.eapproject.PresentationLayer.MainView.MainView;



/**
 *
 * @author kamoz
 */
public class Uni_App_Project {

    public static void main(String[] args) {
      DBUtil dbinit = DBUtil.getInstance();
      UniversityDAO DB = UniversityDAO.getInstance();
      dbinit.initializeDatabase();
      
      var repository = new  universitiesRepo();
        System.out.println(repository.getCountries().toString());
        List<UniversityModel> list = repository.getUniversities("open");
        for (UniversityModel uni : list) {
            System.out.println(uni.getName());
            DB.insertUniversity(uni);            
        }

        List<UniversityModel> list2 = DB.getAllUniversities();
            for (UniversityModel uni : list2) {
            System.out.println(uni.getName() + "perfect");   
        }

      
      SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainView main = new MainView();
                main.setVisible(true);
                main.setSize(new java.awt.Dimension(1500, 1000));
            }
        });
        dbinit.resetDatabase();

    }

        

}
