package org.javabb.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
 * $Id: Topic.java,v 1.1 2009/05/11 20:26:50 daltoncamargo Exp $
 * 
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org
 *         </a> <br>
 */
public class Topic extends VOObject implements Serializable {

    private static final long serialVersionUID = 1L;

    /** nullable persistent field */
    private String titleTopic;

    private Date dataTopico;

    /** persistent field */
    private User user;

    /** persistent field */
    private Forum forum;

    /** persistent field */
    private Set posts;

    /** nullable persistent field */
    private Integer visualizacoes;

    /** nullable persistent field */
    private Integer respostas;

    /** nullable persistent field */
    private Integer notifyMe;

    /** nullable persistent field */
    private Integer topicStatus;

    /** nullable persistent field */
    private Integer topicModel;

    /** No persistent object * */
    private Post lastPost;

    /** nullable persistent field */
    private Date lastPostDate;

    /** Persistent object * */
    private Integer pageLastPost;

    /** No persistent object * */
    private List pagesPerTopic;

    /** nullable persistent field */
    private Long lastPostId;

    /** nullable persistent field */
    private String lastPostUserName;

    /** nullable persistent field */
    private Long lastPostUserId;

    private Set answerNotifies;

    private Set favUserTopics;
    
    private Integer partnerId;

    /**
     * full constructor
     * 
     * @param id
     * @param titleTopic
     * @param usersPortal
     * @param forum
     * @param posts
     */
    public Topic(Long id, String titleTopic, User user, Forum forum, Set posts) {
	setId(id);
	this.titleTopic = titleTopic;
	this.user = user;
	this.forum = forum;
	this.posts = posts;
    }

    /** default constructor */
    public Topic() {
	// do nothing...
    }

    public Topic(Long id) {
	setIdTopic(id);
    }

    public Topic(Long id, String topicTitle) {
	setIdTopic(id);
	setTitleTopic(topicTitle);
    }

    /**
     * minimal constructor.
     * 
     * @param id
     * @param usersPortal
     * @param forum
     * @param posts
     */
    public Topic(Long id, User user, Forum forum, Set posts) {
	setId(id);
	this.user = user;
	this.forum = forum;
	this.posts = posts;
    }

    /**
     * @return id
     */
    public Long getIdTopic() {
	return getId();
    }

    /**
     * @param id
     */
    public void setIdTopic(Long id) {
	this.setId(id);
    }

    /**
     * @return title
     */
    public String getTitleTopic() {
	return this.titleTopic;
    }

    /**
     * @param titleTopic
     */
    public void setTitleTopic(String titleTopic) {
	this.titleTopic = titleTopic;
    }

    /**
     * @return user
     */
    public User getUser() {
	return this.user;
    }

    /**
     * @param usersPortal
     */
    public void setUser(User user) {
	this.user = user;
    }

    /**
     * @return forum
     */
    public Forum getForum() {
	return this.forum;
    }

    /**
     * @param forum
     */
    public void setForum(Forum forum) {
	this.forum = forum;
    }

    /**
     * @return posts
     */
    public Set getPosts() {
	return this.posts;
    }

    /**
     * @param posts
     */
    public void setPosts(Set posts) {
	this.posts = posts;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
	return new ToStringBuilder(this).append("idTopic", getIdTopic())
		.toString();
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object other) {
	if (!(other instanceof Topic)) {
	    return false;
	}

	Topic castOther = (Topic) other;

	return new EqualsBuilder().append(this.getIdTopic(),
		castOther.getIdTopic()).isEquals();
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
	return new HashCodeBuilder().append(getIdTopic()).toHashCode();
    }

    /**
     * @return Returns the dataTopico.
     */
    public Date getDataTopico() {
	return dataTopico;
    }

    /**
     * @param dataTopico
     *            The dataTopico to set.
     */
    public void setDataTopico(Date dataTopico) {
	this.dataTopico = dataTopico;
    }

    /**
     * @return Returns the visualizacoes.
     */
    public Integer getVisualizacoes() {
	return visualizacoes;
    }

    /**
     * @param visualizacoes
     *            The visualizacoes to set.
     */
    public void setVisualizacoes(Integer visualizacoes) {
	this.visualizacoes = visualizacoes;
    }

    /**
     * @return Returns the respostas.
     */
    public Integer getRespostas() {
	return respostas;
    }

    /**
     * @param respostas
     *            The respostas to set.
     */
    public void setRespostas(Integer respostas) {
	this.respostas = respostas;
    }

    /**
     * @return Returns the lastPost.
     */
    public Post getLastPost() {
	return lastPost;
    }

    /**
     * @param lastPost
     *            The lastPost to set.
     */
    public void setLastPost(Post lastPost) {
	this.lastPost = lastPost;
    }

    /**
     * @return Returns the topicStatus.
     */
    public Integer getTopicStatus() {
	return topicStatus;
    }

    /**
     * @param topicStatus
     *            The topicStatus to set.
     */
    public void setTopicStatus(Integer topicStatus) {
	this.topicStatus = topicStatus;
    }

    /**
     * @return Returns the pageLastPost.
     */
    public Integer getPageLastPost() {
	return pageLastPost;
    }

    /**
     * @param pageLastPost
     *            The pageLastPost to set.
     */
    public void setPageLastPost(Integer pageLastPost) {
	this.pageLastPost = pageLastPost;
    }

    /**
     * @return Returns the pagesPerTopic.
     */
    public List getPagesPerTopic() {
	return pagesPerTopic;
    }

    /**
     * @param pagesPerTopic
     *            The pagesPerTopic to set.
     */
    public void setPagesPerTopic(List pagesPerTopic) {
	this.pagesPerTopic = pagesPerTopic;
    }

    /**
     * @return Returns the lastPostDate.
     */
    public Date getLastPostDate() {
	return lastPostDate;
    }

    /**
     * @param lastPostDate
     *            The lastPostDate to set.
     */
    public void setLastPostDate(Date lastPostDate) {
	this.lastPostDate = lastPostDate;
    }

    /**
     * @return Returns the lastPostId.
     */
    public Long getLastPostId() {
	return lastPostId;
    }

    /**
     * @param lastPostId
     *            The lastPostId to set.
     */
    public void setLastPostId(Long lastPostId) {
	this.lastPostId = lastPostId;
    }

    /**
     * @return Returns the topicModel.
     */
    public Integer getTopicModel() {
	return topicModel;
    }

    /**
     * @param topicModel
     *            The topicModel to set.
     */
    public void setTopicModel(Integer topicModel) {
	this.topicModel = topicModel;
    }

    /**
     * @return Returns the notifyMe.
     */
    public Integer getNotifyMe() {
	return notifyMe;
    }

    /**
     * @param notifyMe
     *            The notifyMe to set.
     */
    public void setNotifyMe(Integer notifyMe) {
	this.notifyMe = notifyMe;
    }

    /**
     * @return Returns the lastPostUserName.
     */
    public String getLastPostUserName() {
	return lastPostUserName;
    }

    /**
     * @param lastPostUserName
     *            The lastPostUserName to set.
     */
    public void setLastPostUserName(String lastPostUserName) {
	this.lastPostUserName = lastPostUserName;
    }

    /**
     * @return Returns the lastPostUserId.
     */
    public Long getLastPostUserId() {
	return lastPostUserId;
    }

    /**
     * @param lastPostUserId
     *            The lastPostUserId to set.
     */
    public void setLastPostUserId(Long lastPostUserId) {
	this.lastPostUserId = lastPostUserId;
    }

    /**
     * @return Returns the answerNotifies.
     */
    public Set getAnswerNotifies() {
	return answerNotifies;
    }

    /**
     * @param answerNotifies
     *            The answerNotifies to set.
     */
    public void setAnswerNotifies(Set answerNotifies) {
	this.answerNotifies = answerNotifies;
    }

    public Set getFavUserTopics() {
	return favUserTopics;
    }

    public void setFavUserTopics(Set favUserTopics) {
	this.favUserTopics = favUserTopics;
    }

	public Integer getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
	}
}
