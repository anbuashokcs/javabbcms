package org.javabb.action.infra;

import java.util.ArrayList;
import java.util.List;

import org.javabb.component.PostFormatter;
import org.javabb.exception.FieldException;
import org.javabb.infra.FeedConstantLists;
import org.javabb.transaction.BannerTransaction;
import org.javabb.transaction.NoticiasTransaction;
import org.javabb.transaction.TopicTransaction;
import org.javabb.vo.Banners;
import org.javabb.vo.Partner;
import org.javabb.vo.Post;

import com.opensymphony.webwork.ServletActionContext;

/*
 * Copyright 2004 JavaFree.org
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

/**
 * $Id: BaseAction.java,v 1.3 2009/07/06 03:18:39 daltoncamargo Exp $
 * 
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org
 *         </a> <br>
 * @author Ronald Tetsuo Miura
 * @author Lucas Teixeira - <a href="mailto:lucas@javabb.org">lucas@javabb.org
 *         </a> <br>
 */
public class BaseAction extends AuthAction {

	private static final long serialVersionUID = 1L;

	/** */
	protected Long _categoryId;

	/** */
	protected Long _forumId;

	/** */
	protected Long _topicId;

	/** */
	protected Long _postId;

	/** */
	protected Long _userId;

	/** */
	protected Long _smileId;

	/** */
	protected Long _badWordId;

	private String subject;
	private String message;
	protected int watchTopic;
	private Integer checkSign;
	protected List msgErrors;
	protected List<Partner> partners = new ArrayList<Partner>();

	protected PostFormatter postFormatter;

	public void setPostFormatter(PostFormatter postFormatter) {
		this.postFormatter = postFormatter;
	}

	protected NoticiasTransaction noticiasTransaction;

	public void setNoticiasTransaction(NoticiasTransaction noticiasTransaction) {
		this.noticiasTransaction = noticiasTransaction;
	}

	protected TopicTransaction topicTransaction;

	public void setTopicTransaction(TopicTransaction topicTransaction) {
		this.topicTransaction = topicTransaction;
	}

	protected BannerTransaction bannerTransaction;

	public void setBannerTransaction(BannerTransaction bannerTransaction) {
		this.bannerTransaction = bannerTransaction;
	}

	/**
	 * @param post
	 * @return formated post
	 */
	public String formatPost(Post post) {
		String basePath = ServletActionContext.getRequest().getContextPath();
		return postFormatter.formatPost(basePath, post);
	}

	/**
	 * @param text
	 * @return formated post
	 */
	public String formatEscaped(String text) {
		return postFormatter.formatEscaped(text);
	}

	public String formatOnlyBBCodeHTML(String text) {
		return postFormatter.formatOnlyBBCodeHTML(text);
	}

	public String formatTextToBBCode(String text) {
		String basePath = ServletActionContext.getRequest().getContextPath();
		return (text != null) ? postFormatter
				.formatTextToBBCode(basePath, text) : "";
	}

	public String formatTextToBBCode(String text, String contextPath) {
		return (text != null) ? postFormatter.formatTextToBBCode(contextPath,
				text) : "";
	}

	protected void checkMessage() throws Exception {
		if (message != null) {
			if (message.replaceAll(" ", "").length() < 2) {
				throw new FieldException(getText("topic.message.required"));
			}
		} else {
			throw new FieldException(getText("topic.message.required"));
		}
	}

	protected void checkSubject() throws Exception {
		if (subject != null) {
			if (subject.replaceAll(" ", "").length() < 2) {
				throw new FieldException(getText("topic.subject.required"));
			}
		} else {
			throw new FieldException(getText("topic.subject.required"));
		}
	}

	protected String checkFlood() throws Exception {
		// Proteção contra flood... 7 segundos
		long currTime = System.currentTimeMillis();
		Long lastPost = (Long) getSessionAttribute("my_last_post");

		if ((lastPost != null) && ((currTime - lastPost.longValue()) < 7000)) {
			// 15 milisegundos
			this.addActionError(this.getText("message_so_quickly"));
			return ERROR;

		}
		setSessionAttribute("my_last_post", new Long(currTime));
		return "";
	}

	/**
	 * @param id
	 *            the new badWordId value
	 */
	public final void setB(Long id) {
		this._badWordId = id;
	}

	/**
	 * @param id
	 *            the new categoryId value
	 */
	public final void setC(Long id) {
		this._categoryId = id;
	}

	/**
	 * @param id
	 *            the new smileId value
	 */
	public final void setS(Long id) {
		this._smileId = id;
	}

	/**
	 * @param id
	 *            the new forumId value
	 */
	public final void setF(Long id) {
		this._forumId = id;
	}

	/**
	 * @param id
	 *            the new postId value
	 */
	public final void setP(Long id) {
		this._postId = id;
	}

	/**
	 * @param id
	 *            the new topicId value
	 */
	public final void setT(Long id) {
		this._topicId = id;
	}

	/**
	 * @param id
	 *            the new userId value
	 */
	public final void setU(Long id) {
		this._userId = id;
	}

	/**
	 * @return id
	 */
	public Long getBadWordId() {
		return _badWordId;
	}

	/**
	 * @return id
	 */
	public Long getCategoryId() {
		return _categoryId;
	}

	/**
	 * @return id
	 */
	public Long getSmileId() {
		return _smileId;
	}

	/**
	 * @return id
	 */
	public Long getForumId() {
		return _forumId;
	}

	/**
	 * @return id
	 */
	public Long getPostId() {
		return _postId;
	}

	/**
	 * @return id
	 */
	public Long getTopicId() {
		return _topicId;
	}

	/**
	 * @return id
	 */
	public Long getUserId() {
		return _userId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Integer getCheckSign() {
		return checkSign;
	}

	public void setCheckSign(Integer checkSign) {
		this.checkSign = checkSign;
	}

	public List getMsgErrors() {
		return msgErrors;
	}

	public void setMsgErrors(List msgErrors) {
		this.msgErrors = msgErrors;
	}

	public int getWatchTopic() {
		return watchTopic;
	}

	public void setWatchTopic(int watchTopic) {
		this.watchTopic = watchTopic;
	}

	public List getFeedEmpregos() {
		return FeedConstantLists.feedEmpregos;
	}

	public List getFeedInfoBlogs() {
		return FeedConstantLists.feedInfoblogs;
	}

	public List getFeedBomdeBlog() {
		return FeedConstantLists.feedBomDeBlog;
	}

	// utils direct access methods

	public String replaceTut(String txt) {
		if (txt == null) {
			return "";
		}
		return txt.replaceAll("\\[Tutoriais\\] - ", "");
	}

	public List getLastArticles() {
		try {
			return topicTransaction.findLastArticles(14);
		} catch (Exception e) {
			log.error("Get find last articles error:" + e);
			return null;
		}
	}

	public Banners loadLocationBanner(Integer location) throws Exception {
		return bannerTransaction.displayBanner(location);
	}
	
	
	
	

	public List<Partner> getPartners() {
		return partners;
	}

	public void setPartners(List<Partner> partners) {
		this.partners = partners;
	}
	


}