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
package model.music;

import connection.Conn;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.genre.Genre;

/**
 *
 * @author Rakha
 */
public class MusicDaoImpl implements MusicDao {

    private final Connection connection;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
    private String sql;

    public MusicDaoImpl() {
        connection = Conn.getConnection();
    }

    @Override
    public void addMusic(Music music, Integer genreId) {
        try {
            sql = "INSERT INTO musics (music_name, music_genre_id, music_price, music_artist_name, release_date) VALUES (?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, music.getMusicName());
            preparedStatement.setInt(2, genreId);
            preparedStatement.setInt(3, music.getMusicPrice());
            preparedStatement.setString(4, music.getMusicArtistName());
            preparedStatement.setDate(5, new Date(music.getReleaseDate().getTime()));
            System.out.println(preparedStatement.toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void updateMusic(Integer id, Music music) {
        try {
            sql = "UPDATE musics SET "
                    + "music_name = ?, "
                    + "music_genre_id = ?, "
                    + "music_price = ?, "
                    + "music_artist_name = ?, "
                    + "release_date = ? "
                    + "WHERE id  = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, music.getMusicName());
            preparedStatement.setInt(2, music.getMusicGenreId());
            preparedStatement.setInt(3, music.getMusicPrice());
            preparedStatement.setString(4, music.getMusicArtistName());
            preparedStatement.setDate(5, new Date(music.getReleaseDate().getTime()));
            preparedStatement.setInt(6, id);
            System.out.println(preparedStatement.toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void deleteMusic(Integer id) {
        try {
            sql = "DELETE FROM musics WHERE id = ?";
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
    public Boolean selectMusicExist(String name) {
        boolean usernameCheck;
//        try {
//            sql = "SELECT genre_name FROM music_genres";
//            preparedStatement = connection.prepareStatement(sql);
//            System.out.println(preparedStatement.toString());
//            resultSet = preparedStatement.executeQuery(sql);
//            while (resultSet.next()) {
//                if (resultSet.getString(1).equals(name)) {
//                    usernameCheck = false;
//                    return usernameCheck;
//                }
//            }
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
//        }
        usernameCheck = true;
        return usernameCheck;
    }

    @Override
    public List<Music> selectMusics() {
        List<Music> musics = new ArrayList<>();
        try {
            sql = "SELECT * FROM musics";
            preparedStatement = connection.prepareStatement(sql);
            System.out.println(preparedStatement.toString());
            resultSet = preparedStatement.executeQuery(sql);
            while (resultSet.next()) {
                Music music = new Music();
                music.setId(resultSet.getInt(1));
                music.setMusicName(resultSet.getString(2));
                music.setMusicGenreId(resultSet.getInt(3));
                music.setMusicPrice(resultSet.getInt(4));
                music.setMusicArtistName(resultSet.getString(5));
                music.setReleaseDate(resultSet.getDate(6));
                musics.add(music);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        }
        return musics;
    }

    @Override
    public Genre selectGenre(Integer id) {
        Genre genre = new Genre();
        try {
            sql = "SELECT * FROM music_genres WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement.toString());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                genre.setId(resultSet.getInt(1));
                genre.setGenreName(resultSet.getString(2));
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        }
        return genre;
    }

    @Override
    public List<Genre> selectGenres() {
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

    @Override
    public Music selectMusic(Integer id) {
        Music music = new Music();
        try {
            sql = "SELECT * FROM musics WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement.toString());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                music.setId(resultSet.getInt(1));
                music.setMusicName(resultSet.getString(2));
                music.setMusicGenreId(resultSet.getInt(3));
                music.setMusicPrice(resultSet.getInt(4));
                music.setMusicArtistName(resultSet.getString(5));
                music.setReleaseDate(resultSet.getDate(6));
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        }
        return music;
    }

}
