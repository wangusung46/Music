/*
 * Copyright (c) 2022 Bob.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Bob - initial API and implementation and/or initial documentation
 */
package controller;

import model.user.User;
import view.FormHistory;
import view.FormLogin;
import view.FormMainMenu;
import view.FormManageGenre;
import view.FormManageMusic;
import view.FormMusic;

/**
 *
 * @author Bob
 */
class ControllerMainMenu {

    @SuppressWarnings("empty-statement")
    public void initController(FormMainMenu formMainMenu, User user) {
        if(user.getRole() == 1){
            formMainMenu.getjMenuStore().setVisible(false);
        } else {
            formMainMenu.getjMenuManage().setVisible(false);
        }
        formMainMenu.getjMenuItemLogOff().addActionListener(e -> doItemLogout(formMainMenu));
        formMainMenu.getjMenuItemBuyMusic().addActionListener(e -> doMenuBuyMusic(formMainMenu, user));
        formMainMenu.getjMenuItemHistory().addActionListener(e -> doMenuHistory(formMainMenu, user));
        formMainMenu.getjMenuIManageMusic().addActionListener(e -> doMenuManageMusic(formMainMenu, user));
        formMainMenu.getjMenuIManageMusicGenre().addActionListener(e -> doMenuManageGenre(formMainMenu, user));
    }

    private void doItemLogout(FormMainMenu formMainMenu) {
        formMainMenu.setVisible(false);
        FormLogin formLogin = new FormLogin();
        formLogin.setVisible(true);
        ControllerLogin controllerLogin = new ControllerLogin();
        controllerLogin.initController(formLogin);
    }

    private void doMenuBuyMusic(FormMainMenu formMainMenu, User user) {
        formMainMenu.getjDesktopPane1().removeAll();
        formMainMenu.getjDesktopPane1().updateUI();
        FormMusic formMusic = new FormMusic();
        formMainMenu.getjDesktopPane1().add(formMusic);
        formMusic.setVisible(true);
        ControllerBuyMusic controllerBuyMusic = new ControllerBuyMusic();
        controllerBuyMusic.initController(formMusic, user);
    }

    private void doMenuHistory(FormMainMenu formMainMenu, User user) {
        formMainMenu.getjDesktopPane1().removeAll();
        formMainMenu.getjDesktopPane1().updateUI();
        FormHistory formHistory = new FormHistory();
        formMainMenu.getjDesktopPane1().add(formHistory);
        formHistory.setVisible(true);
        ControllerHistory controllerHistory = new ControllerHistory();
        controllerHistory.initController(formHistory, user);
    }

    private void doMenuManageMusic(FormMainMenu formMainMenu, User user) {
        formMainMenu.getjDesktopPane1().removeAll();
        formMainMenu.getjDesktopPane1().updateUI();
        FormManageMusic formManageMusic = new FormManageMusic();
        formMainMenu.getjDesktopPane1().add(formManageMusic);
        formManageMusic.setVisible(true);
        ControllerManageMusic controllerManageMusic = new ControllerManageMusic();
        controllerManageMusic.initController(formManageMusic, user);
    }

    private void doMenuManageGenre(FormMainMenu formMainMenu, User user) {
        formMainMenu.getjDesktopPane1().removeAll();
        formMainMenu.getjDesktopPane1().updateUI();
        FormManageGenre formManageGenre = new FormManageGenre();
        formMainMenu.getjDesktopPane1().add(formManageGenre);
        formManageGenre.setVisible(true);
        ControllerManageGenre controllerManageGenre = new ControllerManageGenre();
        controllerManageGenre.initController(formManageGenre, user);
    }

}
