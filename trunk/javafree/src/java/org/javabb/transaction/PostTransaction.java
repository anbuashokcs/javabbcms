package org.javabb.transaction;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.javabb.component.VelocityTemplate;
import org.javabb.dao.DAOConstants;
import org.javabb.dao.entity.IPostDAO;
import org.javabb.dao.entity.IPostFileDAO;
import org.javabb.dao.entity.ITopicDAO;
import org.javabb.infra.CacheUtils;
import org.javabb.infra.Configuration;
import org.javabb.infra.ConfigurationFactory;
import org.javabb.infra.Constants;
import org.javabb.infra.Email;
import org.javabb.infra.FileTransfer;
import org.javabb.infra.Paging;
import org.javabb.infra.UserContext;
import org.javabb.infra.VelocityHelper;
import org.javabb.lucene.index.Indexer;
import org.javabb.vo.AnswerNotify;
import org.javabb.vo.BBLog;
import org.javabb.vo.Forum;
import org.javabb.vo.Post;
import org.javabb.vo.PostFile;
import org.javabb.vo.PostText;
import org.javabb.vo.Topic;
import org.javabb.vo.User;

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
 * $Id: PostTransaction.java,v 1.34.2.2.6.13 2008/10/07 23:06:12 daltoncamargo
 * Exp $
 * 
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org
 *         </a> <br>
 * @author Ronald Tetsuo Miura
 */
public class PostTransaction extends Transaction {

	private IPostDAO _postDAO;

	private ITopicDAO _topicDAO;

	private IPostFileDAO postFileDAO;

	private UserTransaction _userTransaction;

	/**
	 * @param userTransaction
	 *            the new userTransaction value
	 */
	public void setUserTransaction(UserTransaction userTransaction) {
		this._userTransaction = userTransaction;
	}

	/**
	 * @param postDAO
	 *            the new postDAO value
	 */
	public void setPostDAO(IPostDAO postDAO) {
		this._postDAO = postDAO;
	}

	/**
	 * @param topicDAO
	 */
	public void setTopicDAO(ITopicDAO topicDAO) {
		this._topicDAO = topicDAO;
	}

	public void setPostFileDAO(IPostFileDAO postFileDAO) {
		this.postFileDAO = postFileDAO;
	}

	private Indexer indexer;

	public void setIndexer(Indexer indexer) {
		this.indexer = indexer;
	}

	// ///////////////////////////////////////////////////////////
	// Business Methods
	// ///////////////////////////////////////////////////////////

	/**
	 * @param id
	 * @return post
	 */
	public PostText loadPost(Long id) {
		return _postDAO.load(id);
	}

	/**
	 * Busca os posts pelo ID de um tópico
	 * 
	 * @param topicId
	 * @param pageNumber
	 * @return - posts de um determinado tópico
	 */
	public List findByTopic(Long topicId, int pageNumber) {
		// PAGING ** Obtendo informações
		int itemsPerPage = ConfigurationFactory.getConf().postsPage.intValue();
		long postCountInTopic = _postDAO.countPostsByTopic(topicId);
		int pageCount = Paging.getNroPages(itemsPerPage, postCountInTopic);
		Paging.setPageList(pageNumber, pageCount);

		return _postDAO.findByTopic(topicId, pageNumber, itemsPerPage);

	}

	/**
	 * @return id
	 */
	public int findIdLastPost() {

		Post p = _postDAO.findLastPost();
		int i = -1;

		if (p != null) {
			i = p.getIdPost().intValue();
		}

		return i;

	}

	/**
	 * Busca o último post de um tópico
	 * 
	 * @param topic
	 * @return post
	 */
	public Post findByTopicDesc(Topic topic) {

		Post p = null;
		List lst = _postDAO.findByTopicDesc(topic);

		if (!lst.isEmpty()) {

			p = (Post) lst.get(0);

		}
		return p;
	}

	public List findPagesByTopic(Topic topic) {
		// PAGING
		int rowsPerPage = ConfigurationFactory.getConf().postsPage.intValue();
		long nroRecords = topic.getRespostas().intValue() + 1;
		int totalPages = Paging.getNroPages(rowsPerPage, nroRecords);

		return Paging.createQuickPaging(totalPages);
	}

	/**
	 * Busca o último post de um Fórum
	 * 
	 * @param forum
	 * @return post
	 */
	public Post findLastPostByForum(Forum forum) {

		Post p = null;
		List lst = _postDAO.findByForumDesc(forum);
		if (!lst.isEmpty()) {
			p = (Post) lst.get(0);
		}
		lst = null;
		return p;

	}

	/**
	 * TODO METODO NAO SERA MAIS UTILIZADO NA LISTAGEM DA HOME, SO NA INCLUSAO
	 * DE UM NOVO POST (FORUMHIBERNATEDAO) Obtém a página do último post
	 * 
	 * @param topic
	 * @return Numero da página do último post do forum
	 */
	public Integer getPageOfLastPostByTopic(Topic topic) {

		// PAGING ** Obtendo informações
		int rowsPerPage = ConfigurationFactory.getConf().postsPage.intValue();
		long nroRecords = _postDAO.countPostsByTopic(topic.getIdTopic());
		int totalPages = Paging.getNroPages(rowsPerPage, nroRecords);

		return new Integer(totalPages);

	}

	/**
	 * @param forum
	 * @return count
	 */
	public Integer findCountOfPostsByForum(Forum forum) {
		Integer number = _postDAO.findCountOfPostsByForum(forum);
		if (number == null) {
			number = new Integer(0);
		}
		return number;
	}

	/**
	 * Verifica se o usuário tem privilégios para excluir o post
	 * 
	 * @param post
	 * @return result
	 */
	public boolean canDeletePost(Post post) {

		if (!UserContext.getContext().isAuthenticated()) {
			return false;
		}

		User user = UserContext.getContext().getUser();

		if (user.isAdministrator()) {
			return true;
		}
		post = loadPost(post.getId());

		return (post.getUser().getId().equals(user.getId()));
	}

	/**
	 * @param topic
	 */
	@SuppressWarnings("unchecked")
	
	public void deleteAllPostsByTopic(Topic topic) {
		String logPersist = "Excluding AllPosts";

		List posts = this.findByTopic(topic.getIdTopic(), 1);
		Iterator it = posts.iterator();
		while (it.hasNext()) {
			Post post = (Post) it.next();
			logPersist += " PostId: " + post.getIdPost() + " - ";
			_userTransaction.subNumberMsgUser(post.getUser().getIdUser());
		}
		_topicDAO.deleteTopicsDependencies(topic.getId());

		try {
			BBLog bbLog = new BBLog();
			bbLog.setActionDate(new Date());
			bbLog.setObs(logPersist + " - User State = "
					+ UserContext.getContext().getUser().toString());
			bbLog.setTopicId(topic.getId());
			bbLog.setUserId(UserContext.getContext().getUser().getId());
			bbLog.setUserName(UserContext.getContext().getUser().getUser());
			bbLog.setSubject("Deleting all Posts in Posts In Topic");
			add(bbLog);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param topic
	 * @return list
	 */
	public List listPostsByTopicRev(Topic topic) {
		List posts = _postDAO.findByTopic(topic.getId(),
				DAOConstants.ALL_PAGES, 0);
		Collections.reverse(posts);
		return posts;
	}

	/**
	 * @param userId
	 * @return list
	 */
	public List listPostsByUser(Long userId, int pageNumber) {

		// PAGING ** Obtendo informações
		int itemsPerPage = ConfigurationFactory.getConf().postsPage.intValue();
		long postsCount = _postDAO.countPostsByUser(userId);
		int totalPages = Paging.getNroPages(itemsPerPage, postsCount);
		Paging.setPageList(pageNumber, totalPages);

		List posts = _postDAO.findByUser(userId, pageNumber, itemsPerPage);
		return posts;
	}

	/**
	 * @return list
	 */
	public List listUnAnswaredPosts(int pageNumber) {
		// PAGING ** Obtendo informações
		int itemsPerPage = ConfigurationFactory.getConf().postsPage.intValue();
		return _postDAO.findUnanswered(pageNumber, itemsPerPage);
	}

	/**
	 * @param query
	 * @return post list
	 */
	public List findByQuery(String query, Long forumId, int pageNumber) {

		if ((query == null) && query.trim().equals("")) {
			throw new IllegalArgumentException(
					"You should fill the query field");
		}
		int itemsPerPage = ConfigurationFactory.getConf().getTopicsPage()
				.intValue();

		return _postDAO.search(query, forumId, pageNumber, itemsPerPage);
	}

	/**
	 * Number of rows found into Lucene index
	 * 
	 * @param query
	 *            - keywords to search rows
	 * @return number of rows
	 */
	public int getTotalRowsOfLucene(String query, Long forumId) {
		return _postDAO.getTotalRowsOfLucene(query, forumId);
	}

	/**
	 * @param post
	 */
	
	public void updatePost(Post post) {
		_postDAO.update(post);
	}

	/**
	 * Search for all last posts
	 * 
	 * @return
	 */
	public List findLasPosts() {
		return _postDAO.findLastPosts(new Configuration().getTopicsPage()
				.intValue());
	}

	public List findLastShortPosts(int limit) throws Exception {
		return _postDAO.findLastPostUsingSQL(limit);
	}

	/**
	 * Search for all last posts
	 * 
	 * @param limit
	 * @return
	 */
	public List findAllByTopicDesc(Topic topic) {
		return _postDAO.findByTopicDesc(topic);
	}

	public List findInPosts(final String query, final int page) {
		// PAGING ** Getting informations
		/*
		 * int itemsPerPage =
		 * ConfigurationFactory.getConf().postsPage.intValue(); long postsCount
		 * = _postDAO.countUnanswered(); int totalPages =
		 * Paging.getNroPages(itemsPerPage, postsCount);
		 * Paging.setPageList(page, totalPages);
		 */

		return _postDAO.search(query, page);
	}

	/**
	 * Send an email notification to User if the notify flag is set
	 * 
	 * @param topic
	 * @throws Exception
	 */
	public void notifyUserTopicByMail(Topic topic) throws Exception {
		long userId = UserContext.getContext().getUser().getIdUser()
				.longValue();

		if (topic != null && topic.getNotifyMe() != null) {
			if (topic.getNotifyMe().intValue() == 1
					&& topic.getUser().getIdUser().longValue() != userId) {
				sendMailNotify(topic, topic.getUser().getEmail());
			}
		}
	}

	/**
	 * Send an mail to user saying to him that his topic was answered
	 * 
	 * @param topic
	 * @param email
	 * @throws Exception
	 */
	public void sendMailNotify(Topic topic, String email) throws Exception {
		Configuration conf = new Configuration();

		String bodyMail = conf.emailNofityTopic.replaceAll("\n", "<br>")
				+ "<br><br>" + "\"<b>" + topic.getTitleTopic() + "</b>\""
				+ "<br><Br> " + "<b>Link:</b> <a href=\"" + conf.domain
				+ "viewtopic.jbb?t=" + topic.getIdTopic() + "\">" + conf.domain
				+ "viewtopic.jbb?t=" + topic.getIdTopic();

		Email.sendMail(conf.adminMail, email, conf.forumName, bodyMail, true);
	}

	/**
	 * Used to send email to all users that is in that topic wich was answered
	 * 
	 * @param topic
	 * @param user
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void sendAllNotifyEmailsReply(Topic topic, User user)
			throws Exception {
		List emailLists = _postDAO.findEmailRepliesWhenReplyPost(topic, user);
		Set mails = new HashSet(emailLists);
		if (mails != null) {
			Iterator it = mails.iterator();
			while (it.hasNext()) {
				String mail = (String) it.next();
				sendMailNotify(topic, mail);
			}
		}
	}

	/**
	 * Send mail to all watch users of this topic
	 */
	@SuppressWarnings("unchecked")
	public void nofityWatchUsers(Topic topic, String url, String message1_i18n,
			String message2_i18n, String topic_i18n, String watch_i18n) {

		// Getting the userId of user logged
		long userId = UserContext.getContext().getUser().getIdUser()
				.longValue();

		// Loading topic informations
		topic = _topicDAO.load(topic.getIdTopic());

		// Verify if this topic has users watching it
		if (topic.getAnswerNotifies() != null) {
			Configuration conf = new Configuration();

			Map mailMap = new HashMap();
			mailMap.put("conf", conf);
			mailMap.put("message1", message1_i18n);
			mailMap.put("message2", message2_i18n);
			mailMap.put("topicId", topic.getIdTopic());
			mailMap.put("topicName", topic.getTitleTopic());
			mailMap.put("url", url);
			mailMap.put("topic", topic_i18n);

			String message = VelocityTemplate.makeTemplate(mailMap,
					Constants.watchTopicTemplate);

			Iterator it = topic.getAnswerNotifies().iterator();
			Set users = new HashSet();
			while (it.hasNext()) {
				AnswerNotify answer = (AnswerNotify) it.next();
				User user = answer.getUser();
				// Dont send mail to current user post
				if (user.getIdUser().longValue() != userId) {
					users.add(user.getEmail());
				}
			}

			String subject = conf.forumName + " - " + watch_i18n + " - "
					+ topic.getTitleTopic();

			it = users.iterator();
			while (it.hasNext()) {
				String mail = (String) it.next();
				Email.sendMail(conf.adminMail, mail, subject, message, true);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void notifyWarnMessage(Long postId) {
		if (postId != null) {
			Post post = loadPost(postId);

			Configuration conf = new Configuration();
			VelocityHelper vh = new VelocityHelper();

			String title = post.getTopic().getTitleTopic();

			// http://www.javafree.org/topic-872686-GUI.html?page=1#167291
			String url = conf.getDomain() + "topic-" + post.getTopic().getId()
					+ "-" + vh.parseStringTitle(title) + "?page="
					+ post.getTopic().getPageLastPost() + "#" + postId;

			Map mailMap = new HashMap();
			mailMap.put("topicTitle", title);
			mailMap.put("topicURL", url);
			mailMap.put("conf", conf);

			String message = VelocityTemplate.makeTemplate(mailMap,
					Constants.warnNotify);
			Email.sendMail(conf.adminMail, post.getUser().getEmail(),
					"JavaFree - Warn", message, true);

		}

	}

	public void indexPost(Post post) {
		indexer.indexPost(post);
	}

	/**
	 * @param post
	 * @return
	 */
	
	public Long createPost(Post post) {
		CacheUtils.invalidateRelatedCacheTopicById(post.getTopic().getId());
		return _postDAO.create(post);
	}

	/**
	 * @param postId
	 */
	
	public void deletePost(Long postId) {
		Post post = loadPost(postId);
		CacheUtils.invalidateRelatedCacheTopicById(post.getTopic().getId());
		FileTransfer.deleteFileByList(post.getPostFiles());
		_postDAO.delete(postId);

		try {
			BBLog bbLog = new BBLog();
			bbLog.setActionDate(new Date());
			bbLog.setObs("User State = "
					+ UserContext.getContext().getUser().toString());
			bbLog.setTopicId(postId);
			bbLog.setUserId(UserContext.getContext().getUser().getId());
			bbLog.setUserName(UserContext.getContext().getUser().getUser());
			bbLog.setSubject("Excluding OnlyPost Id");
			add(bbLog);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public PostFile loadPostFile(Long fileId) throws Exception {
		PostFile pFile = _postDAO.loadPostFile(fileId);
		postFileDAO.updateDownloads(fileId);
		return pFile;
	}

	
	public void deletePostFile(Long fileId) throws Exception {
		_postDAO.deletePostFile(fileId);
	}

	public void insertSearchLockUp(String query, Long forumId) throws Exception {
		/*
		 * SearchLockUp lockUp = new SearchLockUp(); lockUp.setKeySearch(query);
		 * lockUp.setSearchDate(new Date()); lockUp.setForumId(forumId);
		 * _postDAO.add(lockUp);
		 */
	}

}