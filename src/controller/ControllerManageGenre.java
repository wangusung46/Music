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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.genre.Genre;
import model.genre.GenreDao;
import model.genre.GenreDaoImpl;
import model.user.User;
import view.FormManageGenre;

/**
 *
 * @author Bob
 */
public class ControllerManageGenre {

    private final GenreDao genreDao;
    private Boolean clickTableGenre;

    public ControllerManageGenre() {
        genreDao = new GenreDaoImpl();
        clickTableGenre = false;
    }

    void initController(FormManageGenre formManageGenre, User user) {
        DefaultTableModel defaultTableModel = new DefaultTableModel();

        defaultTableModel.addColumn("ID");
        defaultTableModel.addColumn("Genre Name");
        formManageGenre.getjTableGenre().setModel(defaultTableModel);

        loadTable(defaultTableModel);
        formManageGenre.getjButtonAdd().addActionListener(e -> performAdd(formManageGenre, defaultTableModel));
        formManageGenre.getjButtonUpdate().addActionListener(e -> performUpdate(formManageGenre, defaultTableModel));
        formManageGenre.getjButtonDelete().addActionListener(e -> performDelete(formManageGenre, defaultTableModel));
        formManageGenre.getjTableGenre().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                jTableGenreMouseClicked();
            }

            private void jTableGenreMouseClicked() {
                clickTableGenre = true;
            }
        });
    }

    private void performAdd(FormManageGenre formManageGenre, DefaultTableModel defaultTableModel) {
        Genre genre = new Genre();
        genre.setGenreName(formManageGenre.getjTextFieldGenreName().getText());
        if (!genre.getGenreName().isEmpty()) {
            genreDao.addGenre(genre);
            loadTable(defaultTableModel);
        } else {
            JOptionPane.showMessageDialog(null, "Genre Name Cant' be Empty", "Allert", JOptionPane.WARNING_MESSAGE);
        }

    }

    private void performUpdate(FormManageGenre formManageGenre, DefaultTableModel defaultTableModel) {
        Genre genre = new Genre();
        genre.setGenreName(formManageGenre.getjTextFieldGenreName().getText());
        if (clickTableGenre) {
            if (!genre.getGenreName().isEmpty()) {
                genreDao.updateGenre(Integer.parseInt(defaultTableModel.getValueAt(formManageGenre.getjTableGenre().getSelectedRow(), 0).toString()), genre);
                loadTable(defaultTableModel);
            } else {
                JOptionPane.showMessageDialog(null, "Genre Name Cant' be Empty", "Allert", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Pleasa Select Any Row", "Allert", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void performDelete(FormManageGenre formManageGenre, DefaultTableModel defaultTableModel) {
        if (clickTableGenre) {
            genreDao.deleteGenre(Integer.parseInt(defaultTableModel.getValueAt(formManageGenre.getjTableGenre().getSelectedRow(), 0).toString()));
            loadTable(defaultTableModel);
        } else {
            JOptionPane.showMessageDialog(null, "Pleasa Select Any Row", "Allert", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void loadTable(DefaultTableModel defaultTableModel) {
        defaultTableModel.getDataVector().removeAllElements();
        defaultTableModel.fireTableDataChanged();
        List<Genre> genres = genreDao.selectGenre();
        Object[] objects = new Object[2];
        for (Genre genre : genres) {
            objects[0] = genre.getId();
            objects[1] = genre.getGenreName();
            defaultTableModel.addRow(objects);
        }
        clickTableGenre = false;
    }

}
