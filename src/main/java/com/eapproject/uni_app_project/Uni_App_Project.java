package com.eapproject.uni_app_project;


import com.eapproject.PresentationLayer.MainView;
import javax.swing.SwingUtilities;


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
            }
        });

    }
}
