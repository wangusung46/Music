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
package model.music;

import java.util.Date;

/**
 *
 * @author Bob
 */
public class Music {

    private Integer id;
    private String musicName;
    private Integer musicGenreId;
    private Integer musicPrice;
    private String musicArtistName;
    private Date releaseDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public Integer getMusicGenreId() {
        return musicGenreId;
    }

    public void setMusicGenreId(Integer musicGenreId) {
        this.musicGenreId = musicGenreId;
    }

    public Integer getMusicPrice() {
        return musicPrice;
    }

    public void setMusicPrice(Integer musicPrice) {
        this.musicPrice = musicPrice;
    }

    public String getMusicArtistName() {
        return musicArtistName;
    }

    public void setMusicArtistName(String musicArtistName) {
        this.musicArtistName = musicArtistName;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

}
