package org.javabb.action;

import java.util.ArrayList;
import java.util.List;

import org.javabb.action.infra.BaseAction;
import org.javabb.component.PostFormatter;
import org.javabb.component.Spy;
import org.javabb.component.UserFormatter;
import org.javabb.infra.UserContext;
import org.javabb.transaction.CategoryTransaction;
import org.javabb.transaction.ForumTransaction;
import org.javabb.transaction.PostTransaction;
import org.javabb.transaction.TopicTransaction;
import org.javabb.transaction.UserTransaction;
import org.javabb.vo.Forum;
import org.javabb.vo.Partner;
import org.javabb.vo.Post;
import org.javabb.vo.PostText;
import org.javabb.vo.Topic;

import com.opensymphony.webwork.ServletActionContext;

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
 * $Id: TopicAction.java,v 1.35.2.5.2.3.2.12 2008/10/07 23:06:10 daltoncamargo
 * Exp $
 * 
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org
 *         </a> <br>
 * @author Ronald Tetsuo Miura <br>
 */
public class TopicAction extends BaseAction {

	// ####################################################################
	// Parameters
	// ####################################################################

	private static final long serialVersionUID = 1L;

	private ForumTransaction _forumTransaction;

	private PostTransaction _postTransaction;

	private CategoryTransaction _categoryTransaction;

	private UserTransaction _userTransaction;

	private Topic _topic = new Topic();

	private Post _post = new Post();

	private Forum _forum = new Forum();

	private List _topics = new ArrayList();

	private Long _moveToForum;

	private List _reversePosts = new ArrayList();

	private List _posts = new ArrayList();

	private List lstCategory = new ArrayList();

	private List _users = new ArrayList();

	private List userRanks = new ArrayList();

	private UserFormatter userFormatter;

	// message used to notify user by mail
	private String message = "";

	private long _foundItemsTotalCount;

	private int favoriteTopic;
	
	private String partnerId = "";

	// ####################################################################
	// Dependencies
	// ####################################################################

	/**
	 * @param categoryTransaction
	 */
	public void setCategoryTransaction(CategoryTransaction categoryTransaction) {
		this._categoryTransaction = categoryTransaction;
	}

	/**
	 * @param forumTransaction
	 *            The forumTransaction to set.
	 */
	public void setForumTransaction(ForumTransaction forumTransaction) {
		this._forumTransaction = forumTransaction;
	}

	/**
	 * @param postTransaction
	 *            the new postTransaction value
	 */
	public void setPostTransaction(PostTransaction postTransaction) {
		this._postTransaction = postTransaction;
	}

	public void setUserFormatter(UserFormatter userFormatter) {
		this.userFormatter = userFormatter;
	}

	// ####################################################################
	// Actions
	// ####################################################################

	public void setUserTransaction(UserTransaction transaction) {
		_userTransaction = transaction;
	}

	/**
	 * For view where list all topics by id of forum
	 * 
	 * @return Action status
	 */
	public String listaTopics() throws Exception {

		if (_forum.getIdForum() != null) {
			_forumId = _forum.getIdForum();
		}
		_topics = topicTransaction.getLastTopicsByLastPosts(_forumId, _page);
		_forum.setIdForum(_forumId);
		_forum = _forumTransaction.loadForum(_forumId);

		setSessionAttribute("categoryChosed", _forum.getCategory().getId());

		// Load all categories to populate the combo of foruns
		lstCategory = _categoryTransaction.listCategory();

		// load user Ranking list
		userRanks = _userTransaction.getUserRanks();

		return SUCCESS;
	}

	/**
	 * @return Action status
	 */
	public String loadTopic() {
		_topic.setIdTopic(_topicId);
		_topic = topicTransaction.loadTopicForVisualization(_topic.getId());
		// _topic = CacheUtils.fillTopicIfCached(_topic);

		return SUCCESS;
	}

	/**
	 * Get all posts by topic
	 * 
	 * @return result
	 */
	public String showTopic() {
		try {
			if (_postId != null) {
				Post p = new Post();
				p.setIdPost(_postId);
				p = _postTransaction.loadPost(_postId);
				_topicId = p.getTopic().getIdTopic();
			}

			_topic = topicTransaction.loadTopicForVisualization(_topicId);
			// _topic = CacheUtils.fillTopicIfCached(_topic);
			_posts = _postTransaction.findByTopic(_topicId, _page);

			_topics = topicTransaction.searchRelatedTopicsByTitle(_topic
					.getTitleTopic(), _topic.getId());

			// Mark the topis as read
			UserContext.getContext().setTopicRead(_topicId);

			// Check if user is watching this topic
			if (UserContext.getContext().isAuthenticated()) {
				watchTopic = topicTransaction.isWatchTopic(_topicId,
						UserContext.getContext().getUser().getIdUser());

				favoriteTopic = topicTransaction.isFavoriteTopic(_topicId,
						UserContext.getContext().getUser().getIdUser());
			}

			String msgLog = null;

			if (UserContext.getContext().isAuthenticated()) {
				String userName = UserContext.getContext().getUser().getUser();
				msgLog = new String("User " + userName + " into of topic "
						+ _topic.getTitleTopic());
			} else {
				msgLog = new String("Anonymous ip["
						+ ServletActionContext.getRequest().getRemoteAddr()
						+ "] user into of topic " + _topic.getTitleTopic());
			}

			log.info(msgLog);

			// Long topicId, String topicTitle
			String frNm = _topic.getForum().getNome();
			Long frId = _topic.getForum().getId();

			Long usrId = null;
			String userName = null;
			String reqURL = ServletActionContext.getRequest().getHeader(
					"referer");

			String spyTitleTopic = _topic.getTitleTopic();
			int admin = 0;
			if (UserContext.getContext().isAuthenticated()) {
				usrId = UserContext.getContext().getUser().getId();
				userName = UserContext.getContext().getUser().getUser();
			} else {
				userName = ServletActionContext.getRequest().getRemoteAddr();
			}

			Spy.addSpyTopic(_topicId, spyTitleTopic, frNm, frId, usrId,
					userName, admin, reqURL, _topic.getDataTopico());

			setSessionAttribute("categoryChosed", _topic.getForum()
					.getCategory().getId());

		} catch (Exception e) {
			e.printStackTrace();
			log.debug("Message not found:" + e.getMessage());
			return "no_messages";
		}

		return SUCCESS;
	}

	/**
	 * @return Action status
	 */
	public String loadTopicNoSumView() {
		if (_post.getIdPost() != null) {
			_postId = _post.getIdPost();
		}
		_post = (PostText) _postTransaction.loadPost(_postId);
		_topic = topicTransaction.loadTopic(_topic.getId());

		return SUCCESS;
	}

	/**
	 * @return Action status
	 */
	public String deleteTopic() {
		_postTransaction.deleteAllPostsByTopic(_topic);
		topicTransaction.deleteTopic(_topic.getId());
		// Atualiza a informaçao dos topicos
		_forumTransaction.refreshForum(_forum.getId());

		setUrl("../viewforum.jbb?f=" + _forum.getIdForum());

		return SUCCESS;
	}

	/**
	 * @return result
	 */
	public String listUnreadTopics() {
		_topics = topicTransaction.listUnreadTopics(_page);
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String listRecentTopics() throws Exception{
		Integer pId = null;
		if(partnerId != null && !"".equals(partnerId)){
			pId = new Integer(partnerId);
		}
		_topics = topicTransaction.listLastsTopics(_page, pId);
		partners = topicTransaction.loadAll(new Partner());
		
		return SUCCESS;
	}

	/**
	 * @return result
	 */
	public String lockTopic() {
		topicTransaction.lockTopic(_topic);
		setUrl("../viewtopic.jbb?t=" + _topic.getIdTopic());
		return SUCCESS;
	}

	/**
	 * @return result
	 */
	public String unlockTopic() {
		topicTransaction.unlockTopic(_topic);
		setUrl("../viewtopic.jbb?t=" + _topic.getIdTopic());
		return SUCCESS;
	}

	/**
	 * @return Action status
	 */
	public String moveTopic() throws Exception {
		Topic tmp = this.getTopic(); // topicTransaction.loadVO(topic);
		topicTransaction.moveTopic(tmp, getMoveToForum(), message,
				getText("forum_topic_moved_from"),
				getText("forum_topic_moved_to"), getText("topic"));

		setUrl("../viewtopic.jbb?t=" + _topic.getIdTopic());

		return SUCCESS;
	}

	/**
	 * @return result
	 */
	public String reviewTopic() {
		_reversePosts = _postTransaction.listPostsByTopicRev(_topic);
		return SUCCESS;
	}

	public String watchTopicByUser() throws Exception {
		_users = topicTransaction.watchTopicsByUserId(_userId);
		return SUCCESS;
	}

	public String watchTopicsByTopic() throws Exception {
		_topics = topicTransaction.watchTopicsByTopic(_topicId);
		return SUCCESS;
	}

	public String favoriteTopicByUser() throws Exception {
		_users = topicTransaction.favoriteTopicsByUserId(_userId);
		return SUCCESS;
	}

	public String favoriteTopicsByTopic() throws Exception {
		_topics = topicTransaction.favoriteTopicsByTopic(_topicId);
		return SUCCESS;
	}

	public String findAllLastTopics() throws Exception {
		_topics = topicTransaction.findLastTopics();
		return SUCCESS;
	}

	public String findFavoriteTopics() throws Exception {
		_topics = topicTransaction.favoriteTopics();
		return SUCCESS;
	}

	// ####################################################################
	// View objects accessors
	// ####################################################################

	/**
	 * @return Returns the topic.
	 */
	public Topic getTopic() {
		return _topic;
	}

	/**
	 * @return Returns the topics.
	 */
	public List getTopics() {
		return _topics;
	}

	/**
	 * @return Returns the moveToForum.
	 */
	public Long getMoveToForum() {
		return _moveToForum;
	}

	/**
	 * @param moveToForum
	 *            The moveToForum to set.
	 */
	public void setMoveToForum(Long moveToForum) {
		this._moveToForum = moveToForum;
	}

	/**
	 * @return Returns the reverseTopics.
	 */
	public List getReversePosts() {
		return _reversePosts;
	}

	/**
	 * @param text
	 * @return formated post
	 */
	public String formatEscaped(String text) {
		return postFormatter.formatEscaped(text);
	}

	/**
	 * @return Returns the posts.
	 */
	public List getPosts() {
		return _posts;
	}

	/**
	 * @return Returns the forum.
	 */
	public Forum getForum() {
		return _forum;
	}

	public long getFoundItemsTotalCount() {
		return _foundItemsTotalCount;
	}

	/**
	 * @return Returns the lstCategory.
	 */
	public List getLstCategory() {
		return lstCategory;
	}

	/**
	 * @return Returns the message.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            The message to set.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return Returns the _users.
	 */
	public List getUsers() {
		return _users;
	}

	/**
	 * @param _users
	 *            The _users to set.
	 */
	public void setUsers(List _users) {
		this._users = _users;
	}

	/**
	 * @return Returns the _post.
	 */
	public Post getPost() {
		return _post;
	}

	/**
	 * @param _post
	 *            The _post to set.
	 */
	public void setPost(Post _post) {
		this._post = _post;
	}

	public UserFormatter getUserFormatter() {
		return userFormatter;
	}

	public List getUserRanks() {
		return userRanks;
	}

	public void setUserRanks(List userRanks) {
		this.userRanks = userRanks;
	}

	public int getFavoriteTopic() {
		return favoriteTopic;
	}

	public void setFavoriteTopic(int favoriteTopic) {
		this.favoriteTopic = favoriteTopic;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

}