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

import java.util.List;

/**
 *
 * @author Rakha
 */
public interface HistoryDetailMusicDao {
    
    public abstract List<HistoryDetailMusic> selectHistoryMusicDetail(Integer idUser, Integer id);
    
}
