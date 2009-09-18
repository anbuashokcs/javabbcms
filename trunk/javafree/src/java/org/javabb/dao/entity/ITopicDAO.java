package org.javabb.dao.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.javabb.dao.DAOConstants;
import org.javabb.vo.Forum;
import org.javabb.vo.Topic;

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
 * $Id: ITopicDAO.java,v 1.1 2009/05/11 20:26:57 daltoncamargo Exp $
 * 
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org
 *         </a> <br>
 * @author Ronald Tetsuo Miura
 */
public interface ITopicDAO extends DAOConstants {

	/**
	 * @return topic
	 */
	public Topic findLastTopic();

	/**
	 * @param forum
	 * @return count
	 */
	public Integer findCountOfTopicsByForum(Forum forum);

	/**
	 * @param id
	 * @return topic
	 */
	public Topic load(Long id);

	/**
	 * Method for lock and unlock some topic
	 * 
	 * @param topic
	 *            - Topic with your id
	 * @param lock
	 *            - 1 to lock, 0 to unlock
	 */
	public void lockTopic(Topic topic, Integer lock);

	/**
	 * @param topic
	 * @param idForumDest
	 */
	public void moveTopic(Topic topic, Long idForumDest);

	/**
	 * @return topic list
	 */
	public List findAll();

	/**
	 * @param topic
	 * @return
	 */
	public Long create(Topic topic);

	/**
	 * @param forumId
	 *            Integer forumModel - 0 Normal,1 Fixed, 2 Stick
	 * @return
	 */
	public int countTopicsByForum(Long forumId, Integer forumModel);

	/**
	 * @param forumId
	 * @param forumModel
	 *            - 0 Normal,1 Fixed, 2 Stick
	 * @param pageNumber
	 * @param itemsPerPage
	 * @return
	 */
	public List findByForum(Long forumId, Integer forumModel, int pageNumber,
			int itemsPerPage);

	/**
	 * @param forumId
	 * @param forumModel
	 *            - 0 Normal,1 Fixed, 2 Stick
	 * @return
	 */
	public List findByForum(Long forumId, Integer forumModel);

	/**
	 * @param topicId
	 */
	public void delete(Long topicId);

	/**
	 * @param topicId
	 */
	public void deleteTopicsDependencies(Long topicId);

	/**
	 * @param date
	 * @param pageNumber
	 * @param itemsPerPage
	 * @return
	 */
	public List findPostedAfter(Date date, int pageNumber, int itemsPerPage,
			Integer partnerId);

	/**
	 * @param date
	 * @return
	 */
	public int countPostedAfter(Date date, Integer partnerId);

	/**
	 * @param topic
	 */
	public void update(Topic topic);

	/**
	 * List all watch_topics by userId and topicId
	 * 
	 * @param userId
	 * @param topicId
	 * @return
	 */
	public List wathTopicByTopicUser(Long userId, Long topicId);

	/**
	 * Insert a new User watch topic
	 * 
	 * @param topicId
	 * @param userId
	 */
	public void insertWatchTopicUser(Long topicId, Long userId);

	/**
	 * Delete user watch topic
	 * 
	 * @param topicId
	 * @param userId
	 */
	public void deleteWatchTopicUser(Long topicId, Long userId);

	/**
	 * List all watch_topics by userId
	 * 
	 * @param userId
	 * @return
	 */
	public List wathTopicByUser(Long userId);

	/**
	 * List all watch_topics by topicId
	 * 
	 * @param topicId
	 * @return
	 */
	public List wathTopicByTopic(Long topicId);

	public List findLastTopics(int limit);

	public List favoriteTopicByTopicUser(Long userId, Long topicId);

	public List favoriteTopicByTopic(Long topicId);

	public List favoriteTopicByUser(Long userId);

	public List favoriteTopics();

	public void persistViews(HashMap cacheViews);

	/**
	 * Search a list of related topics
	 * 
	 * @param query
	 * @return
	 */
	public Set searchRelatedTopics(String query, Long topicId);

	public List findByForumArticles(Long forumId) throws Exception;

	public List findLastArticles(Integer nmbArticles) throws Exception;

	public List loadLabelsByTopicId(Long topicId) throws Exception;
}