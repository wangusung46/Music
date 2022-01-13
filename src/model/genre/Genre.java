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

/**
 *
 * @author Bob
 */
public class Genre {
    
    private Integer id;
    private String genreName;

    @Override
    public String toString() {
        return genreName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }
    
}
