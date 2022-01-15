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
package model.historyheader;

import connection.Conn;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.music.Music;

/**
 *
 * @author Rakha
 */
public class HistoryHeaderDaoImpl implements HistoryHeaderDao {

    private final Connection connection;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
    private String sql;

    public HistoryHeaderDaoImpl() {
        connection = Conn.getConnection();
    }

    @Override
    public void addHistoryHeader(HistoryHeader historyHeaders) {
        try {
            sql = "INSERT INTO history_header (total_purchase, date_purchase, user_id) VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, historyHeaders.getTotalPurchase());
            preparedStatement.setDate(2, new Date(historyHeaders.getDatePurchase().getTime()));
            preparedStatement.setInt(3, historyHeaders.getUserId());
            System.out.println(preparedStatement.toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public Integer lastInsertId() {
        Integer id = new Integer(0);
        try {
            sql = "SELECT LAST_INSERT_ID();";
            preparedStatement = connection.prepareStatement(sql);
            System.out.println(preparedStatement.toString());
            resultSet = preparedStatement.executeQuery(sql);
            while (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            resultSet.close();
            preparedStatement.close();
            return id;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
            throw new UnsupportedOperationException(e.getMessage());
        }
        
    }

    @Override
    public List<HistoryHeader> selectHistoryHeaders() {
        List<HistoryHeader> historyHeaders = new ArrayList<>();
        try {
            sql = "SELECT * FROM history_header";
            preparedStatement = connection.prepareStatement(sql);
            System.out.println(preparedStatement.toString());
            resultSet = preparedStatement.executeQuery(sql);
            while (resultSet.next()) {
                HistoryHeader historyHeader = new HistoryHeader();
                historyHeader.setId(resultSet.getInt(1));
                historyHeader.setTotalPurchase(resultSet.getInt(2));
                historyHeader.setDatePurchase(resultSet.getDate(3));
                historyHeader.setUserId(resultSet.getInt(4));
                historyHeaders.add(historyHeader);
            }
            resultSet.close();
            preparedStatement.close();
            return historyHeaders;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
            throw new Error(e.getMessage());
        }
        
    }

}
