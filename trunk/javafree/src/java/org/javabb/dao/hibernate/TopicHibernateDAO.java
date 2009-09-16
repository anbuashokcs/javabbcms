package org.javabb.dao.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.javabb.dao.entity.ITopicDAO;
import org.javabb.lucene.search.LuceneSearcher;
import org.javabb.vo.AnswerNotify;
import org.javabb.vo.AnswerNotifyPK;
import org.javabb.vo.FavUserTopic;
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
 * $Id: TopicHibernateDAO.java,v 1.21.10.11 2008/11/03 14:55:54 daltoncamargo
 * Exp $
 * 
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org
 *         </a> <br>
 * @author Ronald Tetsuo Miura
 */
public class TopicHibernateDAO extends HibernateDAO implements ITopicDAO {

	private LuceneSearcher searcher;

	/**
	 * @param searcher
	 *            The searcher to set.
	 */
	public void setSearcher(LuceneSearcher seacher) {
		this.searcher = seacher;
	}

	/**
	 * @see org.javabb.dao.entity.ITopicDAO#findLastTopic()
	 */
	public Topic findLastTopic() {
		String sql = " from {vo}Topic as p " + " order by p.idTopic desc";
		Topic t = null;
		List lst = getList(sql, 0, 1);

		if (!lst.isEmpty()) {
			t = (Topic) lst.get(0);
		}

		lst = null;

		return t;
	}

	/**
	 * @see org.javabb.dao.entity.ITopicDAO#findCountOfTopicsByForum(org.javabb.vo.Forum)
	 */
	public Integer findCountOfTopicsByForum(Forum forum) {
		String sql = "Topic as t " + " where t.forum.idForum ="
				+ forum.getIdForum();

		return this.countRowsOfTable(sql, "t.idTopic");
	}

	/**
	 * @see org.javabb.dao.entity.ITopicDAO#load(java.lang.Long)
	 */
	public Topic load(Long id) {
		return (Topic) getHibernateTemplate().load(Topic.class, id);
	}

	/**
	 * @see org.javabb.dao.entity.ITopicDAO#create(org.javabb.vo.Topic)
	 */
	public Long create(Topic topic) {
		invalidateAllCaches();
		return (Long) getHibernateTemplate().save(topic);
	}

	/**
	 * @see org.javabb.dao.entity.ITopicDAO#delete(java.lang.Long)
	 */
	public void delete(Long topicId) {
		invalidateAllCaches();
		getHibernateTemplate().delete(load(topicId));
	}

	/**
	 * @param topicId
	 */
	public void deleteTopicsDependencies(Long topicId) {
		Topic topic = load(topicId);
		this.getHibernateTemplate().deleteAll(topic.getPosts());
		this.getHibernateTemplate().deleteAll(topic.getFavUserTopics());
		this.getHibernateTemplate().deleteAll(topic.getAnswerNotifies());
	}

	/**
	 * @see org.javabb.dao.entity.ITopicDAO#lockTopic(org.javabb.vo.Topic,
	 *      java.lang.Integer)
	 */
	public void lockTopic(Topic topic, Integer lock) {
		topic = load(topic.getId());
		topic.setTopicStatus(lock);
	}

	/**
	 * @see org.javabb.dao.entity.ITopicDAO#moveTopic(org.javabb.vo.Topic,
	 *      java.lang.Long)
	 */
	public void moveTopic(Topic topic, Long idForumDest) {

		String sql = "update jbb_topics set id_forum=" + idForumDest
				+ " where id_forum=" + topic.getForum().getIdForum()
				+ " and id_topic=" + topic.getIdTopic();
		this.executeSQL(sql);

	}

	public synchronized void persistViews(HashMap cacheViews) {
		if (cacheViews != null) {
			Iterator it = cacheViews.keySet().iterator();
			while (it.hasNext()) {
				Long topicId = (Long) it.next();
				Integer view = (Integer) cacheViews.get(topicId);
				
				/*String sql = "update jbb_topics set visualizacoes=" + view
						+ " where id_topic= " + topicId;
				this.executeSQL(sql);*/
				
				Topic t = load(topicId);
				t.setVisualizacoes(view);
			}
		}
	}

	/**
	 * @see org.javabb.dao.entity.ITopicDAO#findAll()
	 */
	public List findAll() {
		return findAll(Topic.class, "o.idTopic", ALL_PAGES, 0);
	}

	/**
	 * @see org.javabb.dao.entity.ITopicDAO#countTopicsByForum(java.lang.Long)
	 */
	public int countTopicsByForum(Long forumId, Integer forumModel) {
		return countRowsWhere(Topic.class, "o.idTopic",
				"o.forum.idForum=? and o.topicModel=?", new Object[] { forumId,
						forumModel });
	}

	/**
	 * @see org.javabb.dao.entity.ITopicDAO#findByForum(java.lang.Long, Long,
	 *      int, int)
	 */
	public List findByForum(Long forumId, Integer forumModel, int pageNumber,
			int itemsPerPage) {
		/*
		 * 
		 * List topics = find(Topic.class, "o.forum.idForum=? and
		 * o.topicModel=?", new Object[] { forumId, forumModel },
		 * "o.lastPostDate desc, o.idTopic desc", pageNumber, itemsPerPage);
		 */

		String sql = " SELECT o FROM Topic as o "
				+ " inner join fetch o.user as user "
				+ " inner join fetch o.forum as forum "
				+ " inner join fetch forum.category as cat "
				+ " WHERE o.forum.idForum=? and o.topicModel=? "
				+ " ORDER BY o.lastPostDate desc, o.idTopic desc " + "  ";

		List topics = find(sql, new Object[] { forumId, forumModel },
				pageNumber, itemsPerPage);

		return topics;
	}

	public List findByForumArticles(Long forumId) throws Exception {
		/*
		 * 
		 * List topics = find(Topic.class, "o.forum.idForum=? and
		 * o.topicModel=?", new Object[] { forumId, forumModel },
		 * "o.lastPostDate desc, o.idTopic desc", pageNumber, itemsPerPage);
		 */

		String sql = " SELECT o FROM Topic as o "
				+ " inner join fetch o.user as user "
				+ " inner join fetch o.forum as forum "
				+ " inner join fetch forum.category as cat "
				+ " WHERE o.forum.idForum=? " + " ORDER BY o.titleTopic asc "
				+ "  ";

		List topics = find(sql, new Object[] { forumId }, -1, -1);

		return topics;

	}

	/**
	 * @see org.javabb.dao.entity.ITopicDAO#findByForum(java.lang.Long, Long)
	 */
	public List findByForum(Long forumId, Integer forumModel) {

		return find(Topic.class, "o.forum.idForum=? and o.topicModel=?",
				new Object[] { forumId, forumModel },
				"o.lastPostDate desc, o.idTopic desc", ALL_PAGES, 0);
	}

	/**
	 * Returns the number of articles in the JavaFree.org
	 * 
	 * @param nmbArticles
	 * @return
	 */
	public List findLastArticles(Integer nmbArticles) throws Exception {

		List articles = topicCache.getFindLastArticles(nmbArticles);
		if (articles == null) {
			articles = find(Topic.class, "o.forum.category.idCategory=?",
					new Object[] { 4L }, "o.id desc", 1, nmbArticles);
			topicCache.setFindLastArticles(articles, nmbArticles);
		}
		return articles;

	}

	/**
	 * @see org.javabb.dao.entity.ITopicDAO#findPostedAfter(java.util.Date, int,
	 *      int)
	 */
	public List findPostedAfter(Date date, int pageNumber, int itemsPerPage, Integer partnerId) {
		if(partnerId == null){
			return find(Topic.class, "o.lastPostDate >= ?", new Object[] { date },
					"o.lastPostDate DESC", pageNumber, itemsPerPage);
		}else {
			return find(Topic.class, "o.lastPostDate >= ? and o.partnerId = ?", new Object[] { date, partnerId },
					"o.lastPostDate DESC", pageNumber, itemsPerPage);
		}
	}

	/**
	 * @see org.javabb.dao.entity.ITopicDAO#countPostedAfter(java.util.Date)
	 */
	public int countPostedAfter(Date date, Integer partnerId) {
		if(partnerId == null){
			return countRowsWhere(Topic.class, "o.idTopic", "o.lastPostDate >= ?",
					new Object[] { date });
		} else {
			return countRowsWhere(Topic.class, "o.idTopic", "o.lastPostDate >= ? and o.partnerId = ?",
					new Object[] { date, partnerId });
		}
		
	}

	/**
	 * @see org.javabb.dao.entity.ITopicDAO#update(org.javabb.vo.Topic)
	 */
	public void update(Topic topic) {
		getHibernateTemplate().update(topic);
	}

	/**
	 * @see org.javabb.dao.entity.ITopicDAO#public List
	 *      wathTopicByTopicUser(Long, Long)
	 */
	public List wathTopicByTopicUser(Long userId, Long topicId) {
		return find(AnswerNotify.class,
				"o.user.idUser = ? AND o.topic.idTopic = ?", new Object[] {
						userId, topicId }, "o.user.idUser asc", ALL_PAGES, 0);
	}

	public List favoriteTopicByTopicUser(Long userId, Long topicId) {
		return find(FavUserTopic.class,
				"o.user.idUser = ? AND o.topic.idTopic = ?", new Object[] {
						userId, topicId }, "o.user.idUser asc", ALL_PAGES, 0);
	}

	public List favoriteTopicByTopic(Long topicId) {
		return find(FavUserTopic.class, "o.topic.idTopic = ?",
				new Object[] { topicId }, "o.topic.idTopic asc", ALL_PAGES, 0);
	}

	/**
	 * @see org.javabb.dao.entity.ITopicDAO#public void
	 *      insertWatchTopicUser(Long, Long)
	 */
	public void insertWatchTopicUser(Long topicId, Long userId) {
		AnswerNotify answerNotify = new AnswerNotify(new AnswerNotifyPK(
				topicId, userId));
		this.getHibernateTemplate().save(answerNotify);
	}

	/**
	 * @see org.javabb.dao.entity.ITopicDAO#public void
	 *      deleteWatchTopicUser(Long, Long)
	 */
	public void deleteWatchTopicUser(Long topicId, Long userId) {
		AnswerNotify answerNotify = new AnswerNotify(new AnswerNotifyPK(
				topicId, userId));
		this.getHibernateTemplate().delete(answerNotify);
	}

	/**
	 * @see org.javabb.dao.entity.ITopicDAO#public List wathTopicByUser(Long)
	 */
	public List wathTopicByUser(Long userId) {
		return find(AnswerNotify.class, "o.user.idUser = ?",
				new Object[] { userId }, "o.user.idUser asc", ALL_PAGES, 0);
	}

	public List favoriteTopicByUser(Long userId) {
		return find(FavUserTopic.class, "o.user.idUser = ?",
				new Object[] { userId }, "o.user.idUser asc", ALL_PAGES, 0);
	}

	/**
	 * @see org.javabb.dao.entity.ITopicDAO#public List wathTopicByTopic(Long)
	 */
	public List wathTopicByTopic(Long topicId) {
		return find(AnswerNotify.class, "o.topic.idTopic = ?",
				new Object[] { topicId }, "o.topic.idTopic asc", ALL_PAGES, 0);
	}

	/**
	 * @see org.javabb.dao.entity.ITopicDAO#findLastTopics(int)
	 */
	public List findLastTopics(int limit) {
		String sql = " SELECT o FROM Topic as o "
				+ " inner join fetch o.user as user "
				+ " inner join fetch o.forum as forum "
				+ " ORDER BY o.lastPostId desc ";
		return find(sql, new Object[] {}, 1, limit);
	}

	/**
	 * Favorite Topics listed by Rank
	 */
	public List favoriteTopics() {
		String hql = "SELECT distinct f.topic.idTopic from FavUserTopic as f order by f.topic.idTopic desc";
		List favs = find(hql, new Object[] {}, ALL_PAGES, 50);

		if (!favs.isEmpty()) {
			hql = "SELECT t from Topic as t where t.idTopic in (";
			for (int i = 0; i < favs.size(); i++) {
				Long fav = (Long) favs.get(i);
				hql += fav + ",";
			}
			hql = hql.substring(0, hql.length() - 1);
			hql += ")";

			favs = getHibernateTemplate().find(hql);
		}

		return favs;
	}

	/**
	 * This method returns a set of Related posts with the String in the
	 * parameter
	 * 
	 * @param query
	 *            - Query to be searched into lucene
	 * @param topicId
	 *            - Topic in question, this topic doesnt must appear in the
	 *            results
	 */
	@SuppressWarnings("unchecked")
	public Set searchRelatedTopics(String query, Long topicId) {
		List ids = searcher.searchSimilarWords(query, new String[] { "text",
				"subject" }, "postId");
		List topics = searchTopicByPostsId(topicId, ids, 0, 30);
		return new HashSet(topics);
	}

	@SuppressWarnings("unchecked")
	private List searchTopicByPostsId(final Long topicId, final List ids,
			final int start, final int limit) {

		List result = new ArrayList();
		if (!ids.isEmpty()) {
			int _start = Math.max(0, start);
			int _limit = Math.min(limit, ids.size());
			List subListIds = ids.subList(_start, _limit);
			String hql = "SELECT new Topic(" + "post.topic.id, "
					+ "post.topic.titleTopic) "
					+ " FROM Post as post where post.topic.id !=" + topicId
					+ " AND post.idPost in";
			hql += "(";
			if (!subListIds.isEmpty()) {
				for (int i = 0; i < subListIds.size(); i++) {
					hql += subListIds.get(i) + ",";
				}
				hql = hql.substring(0, hql.length() - 1);
				hql += ") ORDER BY post.idPost DESC";
				result = getHibernateTemplate().find(hql);
			}
		}

		return result;

	}

}
