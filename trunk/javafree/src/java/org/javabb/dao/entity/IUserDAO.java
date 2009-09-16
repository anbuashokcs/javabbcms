package org.javabb.dao.entity;

import java.util.List;

import org.javabb.dao.DAOConstants;
import org.javabb.vo.User;
import org.javabb.vo.UserRank;

/*
 * Copyright 2004 JavaFree.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * $Id: IUserDAO.java,v 1.1 2009/05/11 20:26:57 daltoncamargo Exp $
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org </a> <br>
 * @author Ronald Tetsuo Miura
 */
public interface IUserDAO extends DAOConstants {

    /**
     * @param userId
     * @return user
     */
    public User loadUser(Long userId);

    /**
     * @param email
     * @return list of users
     */
    public List findByEmail(final String email);
    
    /**
     * @param email
     * @return list of users
     */
    public List findByEmail(final String email, final Long userId);

    /**
     * @param name
     * @return user
     */
    public User loadByUsername(String name);

    /**
     * @return user
     */
    public User lastUserRegistered();

    /**
     * @param name
     * @param passwd
     * @return user
     */
    public User verificaLogin(String name, String passwd);

    /**
     * Delete an user and move your messages to user Guest - ID=0
     * @param user
     */
    public void deleteUser(User user);

    /**
     * @param usercode
     * @return user
     */
    public User loadByUsercode(String usercode);

    /**
     * @return user count
     */
    public int countAllUsers();

    /**
     * @param userName
     * @param page
     * @param itemsPerPage
     * @return user list
     */
    public List findAllUsernameLike(String userName, int page, int itemsPerPage);

    /**
     * @param pageNumber
     * @param itemsPerPage
     * @return
     */
    public List findAllUserAdmin(int pageNumber, int itemsPerPage);
    
    /**
     * @param pageNumber
     * @param itemsPerPage
     * @param orderBy
     * @param ascDesc
     * @return user list
     */
    public List findAllSortedBy(int pageNumber, int itemsPerPage, String orderBy, String ascDesc);

    /**
     * @param user
     */
    public void update(User user);

    /**
     * @param pageNumber
     * @param itemsPerPage
     * @return user list
     */
    public List findAll(int pageNumber, int itemsPerPage);

    /**
     * @param user
     */
    public Long create(User user) throws Exception;

	/**
	 * @param username
	 * @return
	 */
	public List loadListByUsername(String username);
	
	/**
	 * Load a list of userRanks
	 * @return
	 */
	public List loadUserRanks();
	
	/**
	 * Get UserRank VO using the postCount specified
	 * @param postCount
	 * @return
	 */
	public UserRank getUserRank(Long postCount);
}
