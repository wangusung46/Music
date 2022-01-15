/*
 * Copyright (c) 2022 Rakha.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Rakha - initial API and implementation and/or initial documentation
 */
package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import model.historydetail.HistoryDetailMusic;
import model.historydetail.HistoryDetailMusicDao;
import model.historydetail.HistoryDetailMusicDaoImpl;
import model.historyheader.HistoryHeader;
import model.historyheader.HistoryHeaderDao;
import model.historyheader.HistoryHeaderDaoImpl;
import model.music.MusicDao;
import model.music.MusicDaoImpl;
import model.user.User;
import view.FormHistory;

/**
 *
 * @author Rakha
 */
public class ControllerHistory {

    private final HistoryDetailMusicDao historyDetailMusicDao;
    private final HistoryHeaderDao historyHeaderDao;

    public ControllerHistory() {
        historyDetailMusicDao = new HistoryDetailMusicDaoImpl();
        historyHeaderDao = new HistoryHeaderDaoImpl();
    }

    void initController(FormHistory formHistory, User user) {
        DefaultTableModel defaultTableModel1 = new DefaultTableModel();
        DefaultTableModel defaultTableModel2 = new DefaultTableModel();

        defaultTableModel1.addColumn("ID");
        defaultTableModel1.addColumn("Total Purchase");
        defaultTableModel1.addColumn("Date Purchase");

        defaultTableModel2.addColumn("History Id");
        defaultTableModel2.addColumn("Music Name");
        defaultTableModel2.addColumn("Music Artist");
        defaultTableModel2.addColumn("Music Price (IDR)");

        formHistory.getjTableHeader().setModel(defaultTableModel1);
        formHistory.getjTableDetail().setModel(defaultTableModel2);

        Integer idUser = loadTable1(defaultTableModel1);

        formHistory.getjTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                jTableHeaderMouseClicked();
            }

            private void jTableHeaderMouseClicked() {
                defaultTableModel2.getDataVector().removeAllElements();
                defaultTableModel2.fireTableDataChanged();
                List<HistoryDetailMusic> historyDetailMusics = historyDetailMusicDao.selectHistoryMusicDetail(idUser, Integer.parseInt(defaultTableModel1.getValueAt(formHistory.getjTableHeader().getSelectedRow(), 0).toString()));
                Object[] objects = new Object[6];
                for (HistoryDetailMusic historyDetailMusic : historyDetailMusics) {
                    objects[0] = historyDetailMusic.getHistoryId();
                    objects[1] = historyDetailMusic.getMusicName();
                    objects[2] = historyDetailMusic.getMusicArtist();
                    objects[3] = historyDetailMusic.getMusicPrice();
                    defaultTableModel2.addRow(objects);
                }
            }
        });
    }

    private Integer loadTable1(DefaultTableModel defaultTableModel1) {
        Integer idUser = new Integer(0);
        defaultTableModel1.getDataVector().removeAllElements();
        defaultTableModel1.fireTableDataChanged();
        List<HistoryHeader> historyHeaders = historyHeaderDao.selectHistoryHeaders();
        Object[] objects = new Object[3];
        for (HistoryHeader historyHeader : historyHeaders) {
            objects[0] = historyHeader.getId();
            objects[1] = historyHeader.getTotalPurchase();
            objects[2] = historyHeader.getDatePurchase();
            idUser = historyHeader.getUserId();
            defaultTableModel1.addRow(objects);
        }
        return idUser;
    }
}
