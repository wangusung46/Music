package controller;

import javax.swing.JOptionPane;
import model.user.User;
import model.user.UserDao;
import model.user.UserDaoImpl;
import view.FormLogin;
import view.FormMainMenu;
import view.FormRegister;

public class ControllerLogin {

    private final UserDao userDao;

    public ControllerLogin() {
        userDao = new UserDaoImpl();
    }

    public void initController(FormLogin formLogin) {
        formLogin.getjButtonLogin().addActionListener(e -> performLogin(formLogin));
        formLogin.getjButtonRegister().addActionListener(e -> doRegister(formLogin));
    }

    @SuppressWarnings("deprecation")
    private void performLogin(FormLogin formLogin) {
        User user = new User();
        user.setEmail(formLogin.getjTextEmail().getText());
        user.setPassword(formLogin.getjPasswordField().getText());
        if (!user.getEmail().isEmpty()) {
            if (!user.getPassword().isEmpty()) {
                user = userDao.login(user);
                if (!user.getUserName().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Success Login", "Success", JOptionPane.INFORMATION_MESSAGE);
                    doMainMenu(formLogin, user);
                } else {
                    emptyPass(formLogin);
                    JOptionPane.showMessageDialog(null, "UserName or Password Wrong", "Allert", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                emptyPass(formLogin);
                JOptionPane.showMessageDialog(null, "Password Not Empty", "Allert", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            emptyPass(formLogin);
            JOptionPane.showMessageDialog(null, "UserName Not Empty", "Allert", JOptionPane.WARNING_MESSAGE);
        }

    }

    private void doRegister(FormLogin formLogin) {
        formLogin.setVisible(false);
        FormRegister formRegister = new FormRegister();
        formRegister.setVisible(true);
        ControllerRegister controllerRegister = new ControllerRegister();
        controllerRegister.initController(formRegister);
    }

    private void doMainMenu(FormLogin formLogin, User user) {
        formLogin.setVisible(false);
        FormMainMenu formMainMenu = new FormMainMenu();
        formMainMenu.setVisible(true);
        ControllerMainMenu controllerMainMenu = new ControllerMainMenu();
        controllerMainMenu.initController(formMainMenu, user);
    }

    private void emptyPass(FormLogin formLogin) {
        formLogin.getjPasswordField().setText("");
    }
}
