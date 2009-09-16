package org.javabb.dao.entity;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.javabb.dao.DAOConstants;
import org.javabb.vo.Forum;

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
 * $Id: IForumDAO.java,v 1.1 2009/05/11 20:26:57 daltoncamargo Exp $
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org </a> <br>
 * @author Ronald Tetsuo Miura
 */
public interface IForumDAO extends DAOConstants {

    /**
     * @param id
     * @return forum
     */
    public Forum load(Long id);

    /**
     * @param forum
     * @param forumTo
     * @throws Exception
     */
    public void transferForum(Forum forum, int forumTo) throws Exception;

    /**
     * @param forum
     * @throws Exception
     */
    public void deleteForum(Forum forum) throws Exception;

    /**
     * Refresh the information of Forum
     * @param forumId
     */
    public void refreshForum(Long forumId);

    /**
     * Refresh the information of Topic count at forum
     * @param topicId
     */
    public void refreshTopic(Long topicId);

    /**
     * Refresh the information of Post count at forum
     * @param postId
     */
    public void refreshPost(Long postId);

    /**
     * @return forum list
     */
    public List findAll();
    
    public List findByCategoryOrderAsc(Long id);    

    /**
     * @param id
     * @return forum list
     */
    public List findByCategory(Long id);

    /**
     * @param forum
     */
    public void update(Forum forum);

    /**
     * @return forum count
     */
    public int countAllForums();

    /**
     * Insert a forum
     * @param forum
     * @return
     */
    public Forum insertForum(Forum forum);
    
	/**
	 * Get all id foruns with unread topics
	 * @param readTopics - Topics reads
	 * @param lastUserVisit - Last user visit of user
	 * @param userId - id of user
	 */
    public List obtainUnreadForuns(Set readTopics, Date lastUserVisit, Long userId) throws Exception;

}