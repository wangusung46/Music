package controller;

import javax.swing.JOptionPane;
import model.user.User;
import model.user.UserDao;
import model.user.UserDaoImpl;
import view.FormLogin;
import view.FormRegister;

public class ControllerRegister {

    private final UserDao userDao;

    ControllerRegister() {
        userDao = new UserDaoImpl();
    }

    @SuppressWarnings("empty-statement")
    public void initController(FormRegister formRegister) {
        formRegister.getjRadioMale().setActionCommand("Male");
        formRegister.getjRadioFamale().setActionCommand("Famale");

        formRegister.getjButtonRegister().addActionListener(e -> performRegister(formRegister));
        formRegister.getjButtonLogin().addActionListener(e -> doLogin(formRegister));
    }

    @SuppressWarnings("deprecation")
    private void performRegister(FormRegister formRegister) {
        User user = new User();
        if (!formRegister.getjTextUserName().getText().isEmpty()) {
            if (!formRegister.getjTextEmail().getText().isEmpty()) {
                if (!formRegister.getjPasswordField().getText().isEmpty()) {
                    if (!formRegister.getjPasswordConfirm().getText().isEmpty()) {
                        if (userDao.checkEmail(formRegister.getjTextEmail().getText())) {
                            if (formRegister.getjPasswordField().getText().equals(formRegister.getjPasswordConfirm().getText())) {
                                if (userDao.selectUserExist(formRegister.getjTextUserName().getText())) {
                                    user.setUserName(formRegister.getjTextUserName().getText());
                                    user.setEmail(formRegister.getjTextEmail().getText());
                                    user.setPassword(formRegister.getjPasswordField().getText());
                                    user.setRole(2);
                                    user.setGender(formRegister.getButtonGroupGender().getSelection().getActionCommand());
                                    userDao.insertUser(user);
                                    JOptionPane.showMessageDialog(null, "Success Register", "Success", JOptionPane.INFORMATION_MESSAGE);
                                    doLogin(formRegister);
                                } else {
                                    JOptionPane.showMessageDialog(null, "Username Exist", "Allert", JOptionPane.WARNING_MESSAGE);
                                }
                            } else {
                                empty(formRegister);
                                JOptionPane.showMessageDialog(null, "Password Must be Same and Confirmation Password", "Allert", JOptionPane.WARNING_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Email validate", "Allert", JOptionPane.WARNING_MESSAGE);
                        }

                    } else {
                        empty(formRegister);
                        JOptionPane.showMessageDialog(null, "Confirm Password Can't be Empty", "Allert", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    empty(formRegister);
                    JOptionPane.showMessageDialog(null, "Password Can't be Empty", "Allert", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                empty(formRegister);
                JOptionPane.showMessageDialog(null, "Email Can't be Empty", "Allert", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            empty(formRegister);
            JOptionPane.showMessageDialog(null, "Username Can't be Empty", "Allert", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void empty(FormRegister formRegister) {
        formRegister.getjPasswordField().setText("");
        formRegister.getjPasswordConfirm().setText("");
    }

    private void doLogin(FormRegister formRegister) {
        formRegister.setVisible(false);
        FormLogin formLogin = new FormLogin();
        formLogin.setVisible(true);
        ControllerLogin controllerLogin = new ControllerLogin();
        controllerLogin.initController(formLogin);
    }
}
