package org.javabb.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

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
 * $Id: Post.java,v 1.1 2009/05/11 20:26:50 daltoncamargo Exp $
 * 
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org
 *         </a> <br>
 * @author Ronald Tetsuo Miura
 */
public class Post extends VOObject implements Serializable {

    /** nullable persistent field */
    private Date postDate;

    /** nullable persistent field */
    private String subject;

    /** nullable persistent field */
    private String postBody;

    /** persistent field */
    private User user;

    /** persistent field */
    private Topic topic;

    /** persistent field */
    private Integer sig;

    /** nullable persistent field */
    private String ip;

    /** persistent field */
    private Integer post_state;
    
    private Integer mailReply;

    /** */
    private boolean acceptHTML = false;

    /** */
    private boolean acceptBBCode = true;

    /** */
    private boolean showSignature = true;

    private Set postFiles;

    private Set warnings;

    /**
     * Full constructor.
     * 
     * @param idPost
     * @param postDate
     * @param subject
     * @param postBody
     * @param user
     * @param topic
     */
    public Post(Long idPost, Date postDate, String subject, String postBody,
	    User user, Topic topic) {
	setId(idPost);
	this.postDate = postDate;
	this.subject = subject;
	this.postBody = postBody;
	this.user = user;
	this.topic = topic;
    }

    public Post(Long postId, Long userId, Long topicId, Date postDate,
	    Long forumId, String titleTopic, String subject, String body) {
	setId(postId);
	this.setUser(new User(userId));
	Topic topic = new Topic(topicId);
	topic.setTitleTopic(titleTopic);
	topic.setForum(new Forum(forumId));
	this.setTopic(topic);
	this.postDate = postDate;
	this.setSubject(subject);
	this.setPostBody(body);
    }

    public Post(Long topicId, Integer pageLastPost, Long postId,
	    String titleTopic, Long forumId, String forumName, Date postDate,
	    Long userId, String userName, Integer replies, Integer views) {

	Topic topic = new Topic();
	topic.setId(topicId);
	topic.setPageLastPost(pageLastPost);
	topic.setTitleTopic(titleTopic);
	topic.setRespostas(replies);
	topic.setVisualizacoes(views);

	Forum forum = new Forum();
	forum.setId(forumId);
	forum.setNome(forumName);

	topic.setForum(forum);
	this.setTopic(topic);

	User user = new User();
	user.setIdUser(userId);
	user.setUser(userName);
	this.setUser(user);

	this.setIdPost(postId);
	this.setPostDate(postDate);

    }

    /**
     * default constructor.
     */
    public Post() {
	// do nothing...
    }

    public Post(Long id) {
	setIdPost(id);
    }

    /**
     * Minimal constructor.
     * 
     * @param id
     * @param user
     * @param topic
     */
    public Post(Long id, User user, Topic topic) {
	setId(id);
	this.user = user;
	this.topic = topic;
    }

    /**
     * @return idPost
     */
    public Long getIdPost() {
	return getId();
    }

    /**
     * @param idPost
     */
    public void setIdPost(Long idPost) {
	this.setId(idPost);
    }

    /**
     * @return postDate
     */
    public Date getPostDate() {
	return this.postDate;
    }

    /**
     * @param postDate
     */
    public void setPostDate(Date postDate) {
	this.postDate = postDate;
    }

    /**
     * @return subject
     */
    public String getSubject() {
	return this.subject;
    }

    /**
     * @param subject
     */
    public void setSubject(String subject) {
	this.subject = subject;
    }

    /**
     * @return post body
     */
    public String getPostBody() {
	return this.postBody;
    }

    /**
     * @param postBody
     */
    public void setPostBody(String postBody) {
	this.postBody = postBody;
    }

    /**
     * @return users portal
     */
    public User getUser() {
	return this.user;
    }

    /**
     * @param user
     */
    public void setUser(User user) {
	this.user = user;
    }

    /**
     * @return topic
     */
    public Topic getTopic() {
	return this.topic;
    }

    /**
     * @param topic
     */
    public void setTopic(Topic topic) {
	this.topic = topic;
    }

    /**
     * @return acceptBBCode
     */
    public boolean getAcceptBBCode() {
	return acceptBBCode;
    }

    /**
     * @param acceptBBCode
     *            the new acceptBBCode value
     */
    public void setAcceptBBCode(boolean acceptBBCode) {
	this.acceptBBCode = acceptBBCode;
    }

    /**
     * @return acceptHTML
     */
    public boolean getAcceptHTML() {
	return acceptHTML;
    }

    /**
     * @param acceptHTML
     *            the new acceptHTML value
     */
    public void setHTMLAccepted(boolean acceptHTML) {
	this.acceptHTML = acceptHTML;
    }

    /**
     * @return signatureShown
     */
    public boolean getShowSignature() {
	return showSignature;
    }

    /**
     * @param showSignature
     *            the new showSignature value
     */
    public void setShowSignature(boolean showSignature) {
	this.showSignature = showSignature;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
	return new ToStringBuilder(this).append("idPost", getIdPost())
		.toString();
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object other) {
	if (!(other instanceof Post)) {
	    return false;
	}

	Post castOther = (Post) other;

	return new EqualsBuilder().append(this.getIdPost(),
		castOther.getIdPost()).isEquals();
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
	return new HashCodeBuilder().append(getIdPost()).toHashCode();
    }

    /**
     * @return Returns the sig.
     */
    public Integer getSig() {
	return sig;
    }

    /**
     * @param sig
     *            The sig to set.
     */
    public void setSig(Integer sig) {
	this.sig = sig;
    }

    /**
     * @return Returns the ip.
     */
    public String getIp() {
	return ip;
    }

    /**
     * @param ip
     *            The ip to set.
     */
    public void setIp(String ip) {
	this.ip = ip;
    }

    /**
     * @return Returns the post_state.
     */
    public Integer getPost_state() {
	return post_state;
    }

    /**
     * @param post_state
     *            The post_state to set.
     */
    public void setPost_state(Integer post_state) {
	this.post_state = post_state;
    }

    public Set getPostFiles() {
	return postFiles;
    }

    public void setPostFiles(Set postFiles) {
	this.postFiles = postFiles;
    }

    public Set getWarnings() {
        return warnings;
    }

    public void setWarnings(Set warnings) {
        this.warnings = warnings;
    }

    public Integer getMailReply() {
        return mailReply;
    }

    public void setMailReply(Integer mailReply) {
        this.mailReply = mailReply;
    }


}
