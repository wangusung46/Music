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
package model.historydetail;

import connection.Conn;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Rakha
 */
public class HistoryDetailDaoImpl implements HistoryDetailDao {

    private final Connection connection;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
    private String sql;

    public HistoryDetailDaoImpl() {
        connection = Conn.getConnection();
    }

    @Override
    public void addHistoryDetail(HistoryDetail historyDetail) {
        try {
            sql = "INSERT INTO history_detail (history_id, music_id) VALUES (?, ?)";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, historyDetail.getHistoryId());
                preparedStatement.setInt(2, historyDetail.getMusicId());
                System.out.println(preparedStatement.toString());
                preparedStatement.executeUpdate();
                preparedStatement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
