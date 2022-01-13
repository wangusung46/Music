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
package model.genre;

import model.user.*;
import connection.Conn;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Bob
 */
public class GenreDaoImpl implements GenreDao {

    private final Connection connection;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
    private String sql;

    public GenreDaoImpl() {
        connection = Conn.getConnection();
    }

    @Override
    public void addGenre(Genre genre) {
        try {
            sql = "INSERT INTO music_genres (genre_name) VALUES (?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, genre.getGenreName());
            System.out.println(preparedStatement.toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void updateGenre(Integer id, Genre genre) {
        try {
            sql = "UPDATE music_genres SET genre_name = ? WHERE id  = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, genre.getGenreName());
            preparedStatement.setInt(2, id);
            System.out.println(preparedStatement.toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void deleteGenre(Integer id) {
        try {
            sql = "DELETE FROM music_genres WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement.toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public Boolean selectGenreExist(String name) {
        boolean usernameCheck;
        try {
            sql = "SELECT genre_name FROM music_genres";
            preparedStatement = connection.prepareStatement(sql);
            System.out.println(preparedStatement.toString());
            resultSet = preparedStatement.executeQuery(sql);
            while (resultSet.next()) {
                if (resultSet.getString(1).equals(name)) {
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
    public List<Genre> selectGenre() {
        List<Genre> genres = new ArrayList<>();
        try {
            sql = "SELECT * FROM music_genres";
            preparedStatement = connection.prepareStatement(sql);
            System.out.println(preparedStatement.toString());
            resultSet = preparedStatement.executeQuery(sql);
            while (resultSet.next()) {
                Genre genre = new Genre();
                genre.setId(resultSet.getInt(1));
                genre.setGenreName(resultSet.getString(2));
                genres.add(genre);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        }
        return genres;
    }

}
