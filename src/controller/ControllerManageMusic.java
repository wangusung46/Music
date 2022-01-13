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

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.genre.Genre;
import model.music.Music;
import model.music.MusicDao;
import model.music.MusicDaoImpl;
import model.user.User;
import view.FormManageMusic;

/**
 *
 * @author Bob
 */
public class ControllerManageMusic {

    private final MusicDao musicDao;
    private Boolean clickTableMusic;

    public ControllerManageMusic() {
        musicDao = new MusicDaoImpl();
        clickTableMusic = false;
    }

    void initController(FormManageMusic formManageMusic, User user) {
        DefaultTableModel defaultTableModel = new DefaultTableModel();
        List<Genre> genres = musicDao.selectGenres();
        for (Genre genre : genres) {
            formManageMusic.getjComboBoxGenre().addItem(genre.toString());
        }

        defaultTableModel.addColumn("ID");
        defaultTableModel.addColumn("Name");
        defaultTableModel.addColumn("Genre");
        defaultTableModel.addColumn("Price");
        defaultTableModel.addColumn("Artist Name");
        defaultTableModel.addColumn("Release Date");

        formManageMusic.getjTableMusic().setModel(defaultTableModel);

        performIdMusic(formManageMusic, defaultTableModel, genres);

        loadTable(defaultTableModel);

        formManageMusic.getjComboBoxGenre().addActionListener(e -> performIdMusic(formManageMusic, defaultTableModel, genres));
        formManageMusic.getjButtonAdd().addActionListener(e -> performAdd(formManageMusic, defaultTableModel, genres));
        formManageMusic.getjButtonUpdate().addActionListener(e -> performUpdate(formManageMusic, defaultTableModel, genres));
        formManageMusic.getjButtonDelete().addActionListener(e -> performDelete(formManageMusic, defaultTableModel));
        formManageMusic.getjTableMusic().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                jTableMusicMouseClicked();
            }

            private void jTableMusicMouseClicked() {
                clickTableMusic = true;
            }
        });
        formManageMusic.getjSpinnerPrice().addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                numberValidation(e);
            }
        });
    }

    private void numberValidation(KeyEvent e) {
        char c = e.getKeyChar();
        if (!((c >= '0') && (c <= '9')
                || (c == KeyEvent.VK_BACK_SPACE)
                || (c == KeyEvent.VK_DELETE))) {
            e.consume();
        }
    }

    private void loadTable(DefaultTableModel defaultTableModel) {
        defaultTableModel.getDataVector().removeAllElements();
        defaultTableModel.fireTableDataChanged();
        List<Music> musics = musicDao.selectMusics();
        Object[] objects = new Object[6];
        for (Music music : musics) {
            objects[0] = music.getId();
            objects[1] = music.getMusicName();
            Genre genre = musicDao.selectGenre(music.getMusicGenreId());
            objects[2] = genre.getGenreName();
            objects[3] = music.getMusicPrice();
            objects[4] = music.getMusicArtistName();
            objects[5] = music.getReleaseDate();
            defaultTableModel.addRow(objects);
        }
        clickTableMusic = false;
    }

    private void performAdd(FormManageMusic formManageMusic, DefaultTableModel defaultTableModel, List<Genre> genres) {
        Music music = new Music();

        Date date = new Date();

        for (Genre genre : genres) {
            if (formManageMusic.getjComboBoxGenre().getSelectedItem().toString().equals(genre.getGenreName())) {
                music.setMusicName(formManageMusic.getjTextFieldMusicName().getText());
                music.setMusicGenreId(genre.getId());
                music.setMusicPrice(Integer.parseInt(formManageMusic.getjSpinnerPrice().getValue().toString()));
                music.setMusicArtistName(formManageMusic.getjTextFieldArtistName().getText());
                music.setReleaseDate(date);
                if (!music.getMusicName().isEmpty()) {
                    if (music.getMusicPrice() > 0) {
                        if (!music.getMusicArtistName().isEmpty()) {
                            musicDao.addMusic(music, genre.getId());
                            loadTable(defaultTableModel);
                        } else {
                            JOptionPane.showMessageDialog(null, "Artist Name Can't be Empty", "Allert", JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Price Can't <= 0", "Allert", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Music Name Can't be Empty", "Allert", JOptionPane.WARNING_MESSAGE);
                }

            }
        }
    }

    private void performUpdate(FormManageMusic formManageMusic, DefaultTableModel defaultTableModel, List<Genre> genres) {
        if (clickTableMusic) {
            for (Genre genre : genres) {
                if (formManageMusic.getjComboBoxGenre().getSelectedItem().toString().equals(genre.getGenreName())) {
                    Music music = new Music();
                    Date date = new Date();
                    music.setMusicName(formManageMusic.getjTextFieldMusicName().getText());
                    music.setMusicGenreId(genre.getId());
                    music.setMusicPrice(Integer.parseInt(formManageMusic.getjSpinnerPrice().getValue().toString()));
                    music.setMusicArtistName(formManageMusic.getjTextFieldArtistName().getText());
                    music.setReleaseDate(date);
                    if (!music.getMusicName().isEmpty()) {
                        if (music.getMusicPrice() <= 0) {
                            if (!music.getMusicArtistName().isEmpty()) {
                                musicDao.updateMusic(Integer.parseInt(defaultTableModel.getValueAt(formManageMusic.getjTableMusic().getSelectedRow(), 0).toString()), music);
                                loadTable(defaultTableModel);
                            } else {
                                JOptionPane.showMessageDialog(null, "Artist Name Can't be Empty", "Allert", JOptionPane.WARNING_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Price Can't <= 0", "Allert", JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Music Name Can't be Empty", "Allert", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please Select Any Row", "Allert", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void performDelete(FormManageMusic formManageMusic, DefaultTableModel defaultTableModel) {
        if (clickTableMusic) {
            musicDao.deleteMusic(Integer.parseInt(defaultTableModel.getValueAt(formManageMusic.getjTableMusic().getSelectedRow(), 0).toString()));
            loadTable(defaultTableModel);
        } else {
            JOptionPane.showMessageDialog(null, "Please Select Any Row", "Allert", JOptionPane.WARNING_MESSAGE);
        }
    }

    private Integer performIdMusic(FormManageMusic formManageMusic, DefaultTableModel defaultTableModel, List<Genre> genres) {
        for (Genre genre : genres) {
            if (formManageMusic.getjComboBoxGenre().getSelectedItem().toString().equals(genre.getGenreName())) {
                return genre.getId();
            }
        }
        return null;
    }

}
