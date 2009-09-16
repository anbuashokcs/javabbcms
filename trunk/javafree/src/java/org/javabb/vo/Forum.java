package org.javabb.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
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
 * $Id: Forum.java,v 1.1 2009/05/11 20:26:50 daltoncamargo Exp $
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org </a> <br>
 * @author Ronald Tetsuo Miura
 */
public class Forum extends VOObject implements Serializable {

    /** nullable persistent field */
    private String nome;

    /** nullable persistent field */
    private String descricao;

    /** nullable persistent field */
    private Integer forumStatus;

    /** persistent field */
    private Set topics;

    /** Persistent object * */
    private Integer forumOrder;

    private Long topicCount;

    private Long postCount;

    private Long lastPagePost;

    private String lastPostUserName;

    private Long lastPostUserId;

    private Long lastPostId;

    private Date lastPostDate;

    private Long lastTopicId;

    private Category category;
    
    private Set forumTopUsers;

    /** default constructor */
    public Forum() {
        // do nothing
    }

    public Forum(Long id) {
        setIdForum(id);
    }
    
    /**
     * @param name
     * @param description
     * @param sortingPosition
     * @param status
     */
    public Forum(String name, String description, Integer sortingPosition, Integer status) {
        this(null, name, description, sortingPosition, status, new HashSet());
    }

    /**
     * @param id
     * @param name
     * @param description
     * @param sortingPosition
     * @param status
     * @param topics
     */
    public Forum(Long id, String name, String description, Integer sortingPosition, Integer status,
        Set topics) {

        setId(id);
        setNome(name);
        setDescricao(description);
        setForumOrder(sortingPosition);
        setForumStatus(status);
        setTopics(topics);
    }

    /**
     * @return id
     */
    public Long getIdForum() {
        return getId();
    }

    /**
     * @param id
     */
    public void setIdForum(Long id) {
        this.setId(id);
    }

    /**
     * TODO
     * @return name
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * @param nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * TODO
     * @return descricao
     */
    public String getDescricao() {
        return this.descricao;
    }

    /**
     * @param descricao
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * @return topics
     */
    public Set getTopics() {
        return this.topics;
    }

    /**
     * @param topics
     */
    public void setTopics(Set topics) {
        this.topics = topics;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this).append("idForum", getIdForum()).toString();
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object other) {
        if (!(other instanceof Forum)) {
            return false;
        }

        Forum castOther = (Forum) other;

        return new EqualsBuilder().append(this.getIdForum(), castOther.getIdForum()).isEquals();
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder().append(getIdForum()).toHashCode();
    }

    /**
     * @return Returns the forumStatus.
     */
    public Integer getForumStatus() {
        return forumStatus;
    }

    /**
     * @param forumStatus The forumStatus to set.
     */
    public void setForumStatus(Integer forumStatus) {
        this.forumStatus = forumStatus;
    }

    /**
     * @return Returns the forumOrder.
     */
    public Integer getForumOrder() {
        return forumOrder;
    }

    /**
     * @param forumOrder The forumOrder to set.
     */
    public void setForumOrder(Integer forumOrder) {
        this.forumOrder = forumOrder;
    }

    /**
     * @return Returns the category.
     */
    public Category getCategory() {
        return category;
    }

    /**
     * @param category The category to set.
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * @return Returns the topicCount.
     */
    public Long getTopicCount() {
        return topicCount;
    }

    /**
     * @param topicCount The topicCount to set.
     */
    public void setTopicCount(Long topicCount) {
        this.topicCount = topicCount;
    }

    /**
     * @return Returns the postCount.
     */
    public Long getPostCount() {
        return postCount;
    }

    /**
     * @param postCount The postCount to set.
     */
    public void setPostCount(Long postCount) {
        this.postCount = postCount;
    }

    /**
     * @return Returns the lastPagePost.
     */
    public Long getLastPagePost() {
        return lastPagePost;
    }

    /**
     * @param lastPagePost The lastPagePost to set.
     */
    public void setLastPagePost(Long lastPagePost) {
        this.lastPagePost = lastPagePost;
    }

    /**
     * @return Returns the lastPostId.
     */
    public Long getLastPostId() {
        return lastPostId;
    }

    /**
     * @param lastPostId The lastPostId to set.
     */
    public void setLastPostId(Long lastPostId) {
        this.lastPostId = lastPostId;
    }

    /**
     * @return Returns the lastPostUserId.
     */
    public Long getLastPostUserId() {
        return lastPostUserId;
    }

    /**
     * @param lastPostUserId The lastPostUserId to set.
     */
    public void setLastPostUserId(Long lastPostUserId) {
        this.lastPostUserId = lastPostUserId;
    }

    /**
     * @return Returns the lastPostUserName.
     */
    public String getLastPostUserName() {
        return lastPostUserName;
    }

    /**
     * @param lastPostUserName The lastPostUserName to set.
     */
    public void setLastPostUserName(String lastPostUserName) {
        this.lastPostUserName = lastPostUserName;
    }

    /**
     * @return Returns the lastPostDate.
     */
    public Date getLastPostDate() {
        return lastPostDate;
    }

    /**
     * @param lastPostDate The lastPostDate to set.
     */
    public void setLastPostDate(Date lastPostDate) {
        this.lastPostDate = lastPostDate;
    }

    /**
     * @return Returns the lastTopicId.
     */
    public Long getLastTopicId() {
        return lastTopicId;
    }

    /**
     * @param lastTopicId The lastTopicId to set.
     */
    public void setLastTopicId(Long lastTopicId) {
        this.lastTopicId = lastTopicId;
    }

	public Set getForumTopUsers() {
		return forumTopUsers;
	}

	public void setForumTopUsers(Set forumTopUsers) {
		this.forumTopUsers = forumTopUsers;
	}
}
