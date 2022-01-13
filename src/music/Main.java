/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package music;

import controller.ControllerLogin;
import view.FormLogin;

/**
 *
 * @author Bob
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FormLogin formLogin = new FormLogin();
        formLogin.setVisible(true);
        ControllerLogin buttonController = new ControllerLogin();
        buttonController.initController(formLogin);
    }

}
