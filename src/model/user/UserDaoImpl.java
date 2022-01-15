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
package model.user;

import connection.Conn;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.swing.JOptionPane;

/**
 *
 * @author Rakha
 */
public class UserDaoImpl implements UserDao {

    private final Connection connection;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
    private String sql;

    public UserDaoImpl() {
        connection = Conn.getConnection();
    }

    @Override
    public void insertUser(User user) {
        try {
            sql = "INSERT INTO users (username, email, password, role, gender) VALUES (?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setInt(4, user.getRole());
            preparedStatement.setString(5, user.getGender());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    @Override
    public Boolean selectUserExist(String userName) {
        boolean usernameCheck;
        try {
            sql = "SELECT email FROM users";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery(sql);
            while (resultSet.next()) {
                if (resultSet.getString(1).equals(userName)) {
                    usernameCheck = false;
                    return usernameCheck;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        }
        usernameCheck = true;
        return usernameCheck;
    }

    @Override
    public User login(User user) {
        try {
            sql = "SELECT * FROM users WHERE email = ? AND password = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
                user.setUserName(resultSet.getString(2));
                user.setEmail(resultSet.getString(3));
                user.setPassword(resultSet.getString(4));
                user.setRole(resultSet.getInt(5));
                user.setGender(resultSet.getString(6));
                return user;
            } else {
                user.setId(null);
                user.setUserName("");
                user.setEmail("");
                user.setPassword("");
                user.setRole(null);
                user.setGender("");
                return user;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
            throw new Error(e.getMessage());
        }
    }
    
    @Override
    public Boolean checkEmail(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

}
