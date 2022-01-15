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

/**
 *
 * @author Rakha
 */
public interface UserDao {

    public abstract void insertUser(User user);

    public abstract Boolean selectUserExist(String userName);
    
    public abstract User login(User user);
    
    public abstract Boolean checkEmail(String email);
}
