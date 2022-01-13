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
package model.historydetail;

import connection.Conn;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.historyheader.HistoryHeader;

/**
 *
 * @author Bob
 */
public class HistoryDetailMusicDaoImpl implements HistoryDetailMusicDao{
    
    private final Connection connection;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
    private String sql;

    public HistoryDetailMusicDaoImpl() {
        connection = Conn.getConnection();
    }

    @Override
    public List<HistoryDetailMusic> selectHistoryMusicDetail(Integer idUser, Integer id) {
        List<HistoryDetailMusic> historyDetailMusics = new ArrayList<>();
        try {
            sql = "SELECT b.history_id, c.music_name, c.music_artist_name, c.music_price "
                    + "FROM history_header a "
                    + "INNER JOIN history_detail b "
                    + "ON a.id = b.history_id "
                    + "INNER JOIN musics c "
                    + "ON b.music_id = c.id "
                    + "WHERE c.id = " + idUser + " AND a.id = " + id;
            preparedStatement = connection.prepareStatement(sql);
            System.out.println(preparedStatement.toString());
            resultSet = preparedStatement.executeQuery(sql);
            while (resultSet.next()) {
                HistoryDetailMusic historyDetailMusic = new HistoryDetailMusic();
                historyDetailMusic.setHistoryId(resultSet.getInt(1));
                historyDetailMusic.setMusicName(resultSet.getString(2));
                historyDetailMusic.setMusicArtist(resultSet.getString(3));
                historyDetailMusic.setMusicPrice(resultSet.getInt(4));
                historyDetailMusics.add(historyDetailMusic);
            }
            resultSet.close();
            preparedStatement.close();
            return historyDetailMusics;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
            throw new Error(e.getMessage());
        }
    }
    
}
