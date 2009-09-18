package org.javabb.transaction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.javabb.component.VelocityTemplate;
import org.javabb.dao.entity.ITopicDAO;
import org.javabb.infra.CacheUtils;
import org.javabb.infra.Configuration;
import org.javabb.infra.ConfigurationFactory;
import org.javabb.infra.Constants;
import org.javabb.infra.Email;
import org.javabb.infra.Paging;
import org.javabb.infra.UserContext;
import org.javabb.infra.VelocityHelper;
import org.javabb.vo.BBLog;
import org.javabb.vo.Forum;
import org.javabb.vo.Partner;
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
 * $Id: TopicTransaction.java,v 1.4 2009/07/08 22:03:01 daltoncamargo Exp $
 * 
 * @author Dalton Camargo
 * @author Ronald Tetsuo Miura
 */
public class TopicTransaction extends Transaction {
	/**
	 * Sobrepõe o atributo dao definido na superclasse Este atributo está
	 * sobreposto para poder adquirir os métodos específicos desta entidade
	 */
	private ITopicDAO _topicDAO;

	private PostTransaction postTransaction;

	private ForumTransaction forumTransaction;

	/**
	 * @param topicDAO
	 *            the new topicDAO value
	 */
	public void setTopicDAO(ITopicDAO topicDAO) {
		this._topicDAO = topicDAO;
	}

	/**
	 * @param postTransaction
	 *            the new postTransaction value
	 */
	public void setPostTransaction(PostTransaction postTransaction) {
		this.postTransaction = postTransaction;
	}

	/**
	 * @param forumTransaction
	 *            The forumTransaction to set.
	 */
	public void setForumTransaction(ForumTransaction forumTransaction) {
		this.forumTransaction = forumTransaction;
	}

	/**
	 * @return list
	 */
	public List findAll() {
		return _topicDAO.findAll();
	}

	/**
	 * @param forumId
	 * @param pageNumber
	 * @return list
	 */
	public List getLastTopicsByLastPosts(Long forumId, int pageNumber) {

		// PAGING ** Obtendo informações
		int itemsPerPage = ConfigurationFactory.getConf().topicsPage.intValue();
		long topicCount = _topicDAO.countTopicsByForum(forumId, new Integer(0));
		int totalPages = Paging.getNroPages(itemsPerPage, topicCount);
		Paging.setPageList(pageNumber, totalPages);

		List lstTopics = null;
		List arrTopics = new ArrayList();

		// Stick
		lstTopics = _topicDAO.findByForum(forumId, new Integer(2));
		putArrayTopics(lstTopics, arrTopics);
		lstTopics = null;

		// Fixed
		lstTopics = _topicDAO.findByForum(forumId, new Integer(1));
		putArrayTopics(lstTopics, arrTopics);
		lstTopics = null;

		// Normal
		lstTopics = _topicDAO.findByForum(forumId, new Integer(0), pageNumber,
				itemsPerPage);
		putArrayTopics(lstTopics, arrTopics);
		lstTopics = null;

		return arrTopics;
	}

	@SuppressWarnings("unchecked")
	public List searchRelatedTopicsByTitle(String query, Long topicId) {
		// check in the cache
		List topics = CacheUtils.getRelatedTopicsByTopicId(topicId);
		if (topics != null && !topics.isEmpty()) {
			return topics;
		}

		Set relatedTopics = _topicDAO.searchRelatedTopics(query, topicId);
		if (relatedTopics != null) {
			topics = new ArrayList(relatedTopics);
			CacheUtils.putRelatedTopics(topicId, topics);
		}

		return topics;
	}

	public List getAllTopicPerForum(Long forumId) {

		List topicIds = new ArrayList();

		int pageNumber = 1;
		int itemsPerPage = ConfigurationFactory.getConf().topicsPage.intValue();

		List sticks = _topicDAO.findByForum(forumId, new Integer(2));
		topicIds.addAll(setTopicIdsByList(sticks));

		List fixeds = _topicDAO.findByForum(forumId, new Integer(1));
		topicIds.addAll(setTopicIdsByList(fixeds));

		List normals = _topicDAO.findByForum(forumId, new Integer(0),
				pageNumber, itemsPerPage);
		topicIds.addAll(setTopicIdsByList(normals));

		return topicIds;
	}

	public List findByForumId(Long forumId) throws Exception {
		return _topicDAO.findByForumArticles(forumId);
	}

	private List setTopicIdsByList(List topics) {
		List topicIds = new ArrayList();
		if (topics != null) {
			for (int i = 0; i < topics.size(); i++) {
				topicIds.add((Topic) topics.get(i));
			}
		}
		return topicIds;
	}

	private void putArrayTopics(List fromArrTopics, List toArrTopics) {
		if (!fromArrTopics.isEmpty()) {
			for (int i = 0; i < fromArrTopics.size(); i++) {
				Topic topic = (Topic) fromArrTopics.get(i);
				topic.setPagesPerTopic(postTransaction.findPagesByTopic(topic));
				toArrTopics.add(topic);
			}
		}
	}

	/**
	 * Adds the visualization counter, and returns the Topic.
	 * 
	 * @param id
	 * @return topic
	 */
	public Topic loadTopicForVisualization(Long id) {

		Topic topic = _topicDAO.load(id);

		Integer viewCount = new Integer(
				(topic.getVisualizacoes().intValue() + 1));
		countViewsOnCache(id, viewCount);

		return topic;
	}

	@SuppressWarnings("unchecked")
	public void countViewsOnCache(Long topicId, Integer viewCount) {
		if (CacheUtils._TOPIC_VIEWS_COUNT_CACHE == null) {
			CacheUtils._TOPIC_VIEWS_COUNT_CACHE = new HashMap();
		}
		Integer view = CacheUtils.getViewTopicById(topicId);
		if (view != null) {
			view += 1;
			CacheUtils._TOPIC_VIEWS_COUNT_CACHE.put(topicId, view);
		} else {
			CacheUtils._TOPIC_VIEWS_COUNT_CACHE.put(topicId, viewCount);
		}
	}

	/**
	 * This method will be called by and quartz to persist all cached view of
	 * topics
	 * 
	 * @param cacheViews
	 */
	@SuppressWarnings("unchecked")
	public void persistViewCacheInTable(HashMap cacheViews) {
		ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
		try {
			lock.writeLock().lock();
			_topicDAO.persistViews(cacheViews);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.writeLock().unlock();
		}

	}

	/**
	 * @return id
	 */
	public int findIdLastTopic() {
		Topic t = _topicDAO.findLastTopic();
		int i = -1;

		if (t != null) {
			i = t.getIdTopic().intValue();
		}

		return i;
	}

	/**
	 * @param topicId
	 */

	public void updateDatePostTopic(Long topicId, Date dt) {
		Topic t = _topicDAO.load(topicId);
		t.setLastPostDate(dt);
	}

	/**
	 * @param topicId
	 */

	public void sumNumberReplysByTopic(Long topicId) {
		Topic t = _topicDAO.load(topicId);

		int resp = t.getRespostas().intValue();
		resp++;
		t.setRespostas(new Integer(resp));
	}

	/**
	 * @param lng
	 */

	public void subNumberReplysByTopic(Long lng) {
		Topic t = _topicDAO.load(lng);

		int resp = t.getRespostas().intValue();
		resp--;
		t.setRespostas(new Integer(resp));
		// topicDAO.update(t);
		// topicDAO.subOrSumMsgTopic(t, new Long(resp));
	}

	/**
	 * @param forum
	 * @return count
	 */
	public Integer findCountOfTopicsByForum(Forum forum) {
		Integer number = _topicDAO.findCountOfTopicsByForum(forum);

		if (number == null) {
			number = new Integer(0);
		}

		return number;
	}

	/**
	 * @param topic
	 */
	public void lockTopic(Topic topic) {
		_topicDAO.lockTopic(topic, new Integer(1));
	}

	/**
	 * @param topic
	 */
	public void unlockTopic(Topic topic) {
		_topicDAO.lockTopic(topic, new Integer(0));
	}

	/**
	 * @param topic
	 * @param idForumDest
	 */

	public void moveTopic(Topic topic, Long idForumDest, String message,
			String fFrom_i18n, String fTo_i18n, String topic_i18n)
			throws Exception {

		topic = _topicDAO.load(topic.getId());

		String fromForumName = topic.getForum().getNome();
		Long fromForumId = topic.getForum().getIdForum();

		_topicDAO.moveTopic(topic, idForumDest);

		Forum forumDest = forumTransaction.loadForum(idForumDest);

		// Send mail to user
		if (message != null && !"".equals(message)) {
			Configuration conf = new Configuration();
			String mailUser = topic.getUser().getEmail();

			message = message.replaceAll("\n", "<br>");

			Map mailMap = new HashMap();
			mailMap.put("conf", conf);
			mailMap.put("messageBody", message);
			mailMap.put("topic", topic_i18n);
			mailMap.put("topicName", topic.getTitleTopic());
			mailMap.put("topicId", topic.getIdTopic());
			mailMap.put("forumFrom", fFrom_i18n);
			mailMap.put("forumFromId", fromForumId);
			mailMap.put("forumFromName", fromForumName);
			mailMap.put("forumTo", fTo_i18n);
			mailMap.put("forumToId", forumDest.getIdForum());
			mailMap.put("forumToName", forumDest.getNome());

			message = VelocityTemplate.makeTemplate(mailMap,
					Constants.moveTopicMailTemplate);

			Email.sendMail(conf.adminMail, mailUser, conf.forumName, message,
					true);
		}

		forumTransaction.refreshForum(idForumDest);
		forumTransaction.refreshForum(topic.getForum().getId());
	}

	/**
	 * @param id
	 * @return topic
	 */
	public Topic loadTopic(Long id) {
		return _topicDAO.load(id);
	}

	/**
	 * @param topic
	 * @return topic id
	 */

	public Long createTopic(Topic topic, Date lastPostDate) throws Exception{

		
		Integer partnerId = UserContext.getContext().getUser().getPartnerId();
		topic.getUser().setId(UserContext.getContext().getUser().getId());
		topic.setVisualizacoes(new Integer(0));
		topic.setRespostas(new Integer(0));
		topic.setPartnerId(partnerId);

		topic.setTitleTopic(topic.getTitleTopic());
		topic.setLastPostDate(lastPostDate);

		if (topic.getNotifyMe() == null) {
			topic.setNotifyMe(new Integer(0));
		}

		if (UserContext.getContext().getUser().getAdmin().intValue() != 1) {
			topic.setTopicModel(new Integer(0));
		}

		
		topic.setDataTopico(new Date());
		Long id =  _topicDAO.create(topic);
		
		//Send an email to owner of this partnership
		Partner partner = null;
		if(partnerId != null && partnerId.intValue() > 0){
			try {
				partner = (Partner) genericDAO.load(new Partner(), new Long(partnerId.intValue()));
				sendMailToPartner(partner, id, topic.getTitleTopic());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return id;
	}

	/**
	 * Send an email advising to a partner that some
	 * new topic were created
	 * @param partner
	 * @param topic
	 */
	@SuppressWarnings("unchecked")
	private void sendMailToPartner(Partner partner, Long topicId, String titleTopic){
		VelocityHelper vh = new VelocityHelper();
		String parsedTitle = vh.parseStringTitle(titleTopic);
		String url = "topic-" + topicId + "-" + parsedTitle;
		
		Configuration conf = new Configuration();

		Map mailMap = new HashMap();
		mailMap.put("url", url);
		mailMap.put("conf", conf);
		mailMap.put("partner", partner);
		mailMap.put("titleTopic", titleTopic);

		String message = VelocityTemplate.makeTemplate(mailMap, "partner_new_email.vm");
		
		String[] emails = partner.getEmail().split(",");
		for (int i = 0; i < emails.length; i++) {
			String mail = emails[i];
			Email.sendMail(conf.adminMail, mail, "Novo Topico", message, true);
		}
		
	}
	
	/**
	 * @param topicId
	 */

	public void deleteTopic(Long topicId) {
		CacheUtils.invalidateRelatedCacheTopicById(topicId);
		_topicDAO.delete(topicId);

		try {
			BBLog bbLog = new BBLog();
			bbLog.setActionDate(new Date());
			bbLog.setObs("User State = "
					+ UserContext.getContext().getUser().toString());
			bbLog.setTopicId(topicId);
			bbLog.setUserId(UserContext.getContext().getUser().getId());
			bbLog.setUserName(UserContext.getContext().getUser().getUser());
			bbLog.setSubject("Delete Topic");
			add(bbLog);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void updateTopic(Topic topic) {
		if (topic.getNotifyMe() == null) {
			topic.setNotifyMe(new Integer(0));
		}
		_topicDAO.update(topic);
		CacheUtils.invalidateRelatedCacheTopicById(topic.getId());
	}

	/**
	 * @param pageNumber
	 * @return unread posts list
	 */
	public List listUnreadTopics(int pageNumber) {

		Date lastVisitTimestamp = UserContext.getContext()
				.getLastVisitTimestamp();

		// PAGING
		int itemsPerPage = ConfigurationFactory.getConf().topicsPage.intValue();
		long topicCount = _topicDAO.countPostedAfter(lastVisitTimestamp, null);
		int totalPages = Paging.getNroPages(itemsPerPage, topicCount);
		Paging.setPageList(pageNumber, totalPages);

		List items = _topicDAO.findPostedAfter(lastVisitTimestamp, pageNumber,
				itemsPerPage, null);

		List topics = new ArrayList();
		for (int i = 0; i < items.size(); i++) {
			Topic topic = (Topic) items.get(i);
			topic.setPagesPerTopic(postTransaction.findPagesByTopic(topic));
			topics.add(topic);
		}
		items = null;

		return topics;
	}

	/**
	 * List all recent topics
	 * 
	 * @param pageNumber
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List listLastsTopics(int pageNumber, Integer partnerId) {

		Calendar cal = GregorianCalendar.getInstance();
		if(partnerId == null) {
			cal.add(Calendar.YEAR, -2);
		}else {
			cal.add(Calendar.YEAR, -7);
		}

		// PAGING
		int itemsPerPage = ConfigurationFactory.getConf().topicsPage.intValue();
		long topicCount = _topicDAO.countPostedAfter(cal.getTime(), partnerId);
		int totalPages = Paging.getNroPages(itemsPerPage, topicCount);
		Paging.setPageList(pageNumber, totalPages);

		List items = _topicDAO.findPostedAfter(cal.getTime(), pageNumber, itemsPerPage, partnerId);

		List topics = new ArrayList();
		for (int i = 0; i < items.size(); i++) {
			Topic topic = (Topic) items.get(i);
			topic.setPagesPerTopic(postTransaction.findPagesByTopic(topic));
			topics.add(topic);
		}
		items = null;

		return topics;
	}

	/**
	 * Verify if the user has watch topic
	 * 
	 * @param topicId
	 * @param userId
	 * @return 0 to false and 1 to true
	 */
	public int isWatchTopic(Long topicId, Long userId) {
		List watchTopics = _topicDAO.wathTopicByTopicUser(userId, topicId);
		if (!watchTopics.isEmpty()) {
			watchTopics = null;
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * Insert a new User watch topic
	 * 
	 * @param topicId
	 * @param userId
	 */

	public void insertWatchTopicUser(Long topicId, Long userId) {
		if (isWatchTopic(topicId, userId) == 0) {
			_topicDAO.insertWatchTopicUser(topicId, userId);
		}
	}

	/**
	 * Delete user watch topic
	 * 
	 * @param topicId
	 * @param userId
	 */

	public void deleteWatchTopicUser(Long topicId, Long userId) {
		_topicDAO.deleteWatchTopicUser(topicId, userId);
	}

	/**
	 * List all watch topics by UserId
	 * 
	 * @param userId
	 * @return
	 */
	public List watchTopicsByUserId(Long userId) {
		return _topicDAO.wathTopicByUser(userId);
	}

	public List favoriteTopicsByUserId(Long userId) {
		return _topicDAO.favoriteTopicByUser(userId);
	}

	/**
	 * List all watch topics by TopicId
	 * 
	 * @param topicId
	 * @return
	 */
	public List watchTopicsByTopic(Long topicId) {
		return _topicDAO.wathTopicByTopic(topicId);
	}

	public List favoriteTopicsByTopic(Long topicId) {
		return _topicDAO.favoriteTopicByTopic(topicId);
	}

	public List favoriteTopics() {
		return _topicDAO.favoriteTopics();
	}

	public List findLastTopics() {
		return _topicDAO.findLastTopics(new Configuration().getTopicsPage()
				.intValue());
	}

	public int isFavoriteTopic(Long topicId, Long userId) {
		List favoriteTopics = _topicDAO.favoriteTopicByTopicUser(userId,
				topicId);
		if (!favoriteTopics.isEmpty()) {
			favoriteTopics = null;
			return 1;
		} else {
			return 0;
		}
	}

	public List findLastArticles(int nmb) throws Exception {
		return _topicDAO.findLastArticles(nmb);
	}
	
	public List loadTopicsByLabel(Long topicId) throws Exception {
		return _topicDAO.loadLabelsByTopicId(topicId);
	}
	
	
}