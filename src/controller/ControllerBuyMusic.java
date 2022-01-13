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
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.genre.Genre;
import model.historydetail.HistoryDetail;
import model.historydetail.HistoryDetailDao;
import model.historydetail.HistoryDetailDaoImpl;
import model.historyheader.HistoryHeader;
import model.historyheader.HistoryHeaderDao;
import model.historyheader.HistoryHeaderDaoImpl;
import model.music.Music;
import model.music.MusicDao;
import model.music.MusicDaoImpl;
import model.user.User;
import view.FormMusic;

/**
 *
 * @author Bob
 */
public class ControllerBuyMusic {

    private final MusicDao musicDao;
    private final HistoryDetailDao historyDetailDao;
    private final HistoryHeaderDao historyHeaderDao;
    private Boolean clickTableMusic;
    private Boolean clickTableCart;

    public ControllerBuyMusic() {
        musicDao = new MusicDaoImpl();
        historyDetailDao = new HistoryDetailDaoImpl();
        historyHeaderDao = new HistoryHeaderDaoImpl();
        clickTableMusic = false;
        clickTableCart = false;
    }

    void initController(FormMusic formMusic, User user) {
        DefaultTableModel defaultTableModel1 = new DefaultTableModel();
        DefaultTableModel defaultTableModel2 = new DefaultTableModel();

        defaultTableModel1.addColumn("ID");
        defaultTableModel1.addColumn("Name");
        defaultTableModel1.addColumn("Genre");
        defaultTableModel1.addColumn("Price");
        defaultTableModel1.addColumn("Artist Name");
        defaultTableModel1.addColumn("Release Date");

        defaultTableModel2.addColumn("ID");
        defaultTableModel2.addColumn("Name");
        defaultTableModel2.addColumn("Genre");
        defaultTableModel2.addColumn("Price");
        defaultTableModel2.addColumn("Artist Name");
        defaultTableModel2.addColumn("Release Date");

        formMusic.getjTableMusic().setModel(defaultTableModel1);
        formMusic.getjTableCart().setModel(defaultTableModel2);

        loadTable1(defaultTableModel1);

        formMusic.getjButtonAdd().addActionListener(e -> performAdd(formMusic, defaultTableModel1, defaultTableModel2));
        formMusic.getjButtonRemove().addActionListener(e -> performRemove(formMusic, defaultTableModel2));
        formMusic.getjButtonBuy().addActionListener(e -> performBuy(formMusic, defaultTableModel2, user));
        formMusic.getjTableMusic().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                jTableMusicMouseClicked();
            }

            private void jTableMusicMouseClicked() {
                clickTableMusic = true;
            }
        });
        formMusic.getjTableCart().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                jTableCartMouseClicked();
            }

            private void jTableCartMouseClicked() {
                clickTableCart = true;
            }
        });
    }

    private void loadTable1(DefaultTableModel defaultTableModel1) {
        defaultTableModel1.getDataVector().removeAllElements();
        defaultTableModel1.fireTableDataChanged();
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
            defaultTableModel1.addRow(objects);
        }
        clickTableMusic = false;
    }

    private void performAdd(FormMusic formMusic, DefaultTableModel defaultTableModel1, DefaultTableModel defaultTableModel2) {
        if (clickTableMusic) {
            Music music = musicDao.selectMusic(Integer.parseInt(defaultTableModel1.getValueAt(formMusic.getjTableMusic().getSelectedRow(), 0).toString()));
            Object[] objects = new Object[6];
            objects[0] = music.getId();
            objects[1] = music.getMusicName();
            Genre genre = musicDao.selectGenre(music.getMusicGenreId());
            objects[2] = genre.getGenreName();
            objects[3] = music.getMusicPrice();
            objects[4] = music.getMusicArtistName();
            objects[5] = music.getReleaseDate();
            defaultTableModel2.addRow(objects);
        } else {
            JOptionPane.showMessageDialog(null, "Please Select Any Music From Table Music", "Allert", JOptionPane.WARNING_MESSAGE);
        }

    }

    private void performRemove(FormMusic formMusic, DefaultTableModel defaultTableModel2) {
        if (clickTableCart) {
            defaultTableModel2.removeRow(formMusic.getjTableCart().getSelectedRow());
            clickTableCart = false;
        } else {
            JOptionPane.showMessageDialog(null, "Please Select Any Music From Table Cart", "Allert", JOptionPane.WARNING_MESSAGE);
        }

    }

    private void performBuy(FormMusic formMusic, DefaultTableModel defaultTableModel2, User user) {
        HistoryHeader historyHeader = new HistoryHeader();
        HistoryDetail historyDetail = new HistoryDetail();

        Date date = new Date();

        for (int count = 0; count < defaultTableModel2.getRowCount(); count++) {
            historyHeader.setTotalPurchase(historyHeader.getTotalPurchase() + Integer.parseInt(defaultTableModel2.getValueAt(count, 3).toString()));
        }
        historyHeader.setDatePurchase(date);
        historyHeader.setUserId(user.getId());
        if (defaultTableModel2.getRowCount() > 0) {
            historyHeaderDao.addHistoryHeader(historyHeader);
            Integer lastId = historyHeaderDao.lastInsertId();
            for (int count = 0; count < defaultTableModel2.getRowCount(); count++) {
                historyDetail.setHistoryId(lastId);
                historyDetail.setMusicId(Integer.parseInt(defaultTableModel2.getValueAt(count, 0).toString()));
                historyDetailDao.addHistoryDetail(historyDetail);
            }
            defaultTableModel2.getDataVector().removeAllElements();
            defaultTableModel2.fireTableDataChanged();
        } else {
            JOptionPane.showMessageDialog(null, "Please Select Any Music To Cart", "Allert", JOptionPane.WARNING_MESSAGE);
        }
    }
}
