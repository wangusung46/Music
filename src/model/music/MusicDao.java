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

import model.genre.*;
import java.util.List;

/**
 *
 * @author Bob
 */
public interface MusicDao {
    
    public abstract List<Genre> selectGenres();
    
    public abstract List<Music> selectMusics();

    public abstract void addMusic(Music music, Integer genreId);

    public abstract void updateMusic(Integer id, Music music);

    public abstract void deleteMusic(Integer id);

    public abstract Boolean selectMusicExist(String name);
    
    public abstract Genre selectGenre(Integer id);
    
    public abstract Music selectMusic(Integer id);
}
