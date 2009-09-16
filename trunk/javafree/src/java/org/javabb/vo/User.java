package org.javabb.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.javabb.bbcode.ProcessBBCode;

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
 * $Id: User.java,v 1.2 2009/07/08 22:03:00 daltoncamargo Exp $
 * 
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org
 *         </a> <br>
 * @author Ronald Tetsuo Miura
 */
public class User extends VOObject implements Serializable {

    private static final long serialVersionUID = 1L;

    /** nullable persistent field */
    private String user;

    /** nullable persistent field */
    private String passwordHash;

    /** nullable persistent field */
    private String name;

    /** nullable persistent field */
    private String email;

    /** nullable persistent field */
    private Long user_posts;

    private Integer admin;

    private Date dataRegistro;

    /** persistent field */
    private Set posts;

    /** persistent field */
    private Set topics;

    /** nullable persistent field */
    private String localizacao;

    /** nullable persistent field */
    private String website;

    /** nullable persistent field */
    private String occupation;

    /** nullable persistent field */
    private String userSig;

    /** nullable persistent field */
    private String user_avatar;

    private String avatarPath;

    private String avatarExibition;

    /** nullable persistent field */
    private String user_icq;

    /** nullable persistent field */
    private String user_interests;

    /** nullable persistent field */
    private String user_sig;

    /** nullable persistent field */
    private String user_aim;

    /** nullable persistent field */
    private String user_yim;

    /** nullable persistent field */
    private String user_msnm;

    /** nullable persistent field */
    private Date _lastVisitTimestamp;

    /** nullable persistent field */
    private Date user_lastvisit;

    /** nullable persistent field */
    private String user_dateformat;

    /** nullable persistent field */
    private String user_allow_viewonline;

    /** nullable persistent field */
    private String hash_fpwd;

    /** nullable persistent field */
    private Integer show_mail;

    /** nullable persistent field */
    private Integer show_signature;

    /** nullable persistent field */
    private Integer userStatus;

    private Integer receiveNews;
    
    //Indica o parceiro do JavaFree (OpenK, GlobalCode)
    private Integer partnerId;

    /** nullable persistent field */
    private String userCode;

    private Set forumTopUsers;

    // TODO Dependency Injection
    private ProcessBBCode postFormatter = new ProcessBBCode();

    private Set answerNotifies;

    private Set favUserTopics;

    private List noticias;

    private List comments;

    /**
     * @param id
     * @param user
     * @param passwordHash
     * @param name
     * @param email
     * @param user_posts
     * @param posts
     * @param topics
     */
    public User(Long id, String user, String passwordHash, String name,
	    String email, Long user_posts, Set posts, Set topics) {
	setId(id);
	this.user = user;
	this.passwordHash = passwordHash;
	this.name = name;
	this.email = email;
	this.posts = posts;
	this.posts = posts;
	this.topics = topics;
	this.user_posts = user_posts;
    }

    /**
     * Default constructor.
     */
    public User() {
	// do nothing
    }

    /**
     * @param id
     * @param posts
     * @param topics
     */
    public User(Long id, Set posts, Set topics) {
	setId(id);
	this.posts = posts;
	this.topics = topics;
    }

    /**
     * @param id
     */
    public User(Long id) {
	setId(id);
    }

    /**
     * @return the user ID
     */
    public Long getIdUser() {
	return getId();
    }

    /**
     * @param idUser
     */
    public void setIdUser(Long idUser) {
	this.setId(idUser);
    }

    /**
     * @return the username
     */
    public String getUser() {
	return this.user;
    }

    /**
     * @param user
     */
    public void setUser(String user) {
	this.user = user;
    }

    /**
     * @return passwordHash
     */
    public String getPasswordHash() {
	return this.passwordHash;
    }

    /**
     * @param passwordHash
     */
    public void setPasswordHash(String passwordHash) {
	this.passwordHash = passwordHash;
    }

    /**
     * @return the user name
     */
    public String getName() {
	return this.name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
	this.name = name;
    }

    /**
     * @return email
     */
    public String getEmail() {
	return this.email;
    }

    /**
     * @param email
     */
    public void setEmail(String email) {
	this.email = email;
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
     * @return Returns the topics' value
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
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object other) {
	if (!(other instanceof User)) {
	    return false;
	}

	User castOther = (User) other;

	// return new EqualsBuilder().append(this.getIdUser(),
	// castOther.getIdUser()).isEquals();
	if (this.getIdUser().longValue() == castOther.getIdUser().longValue()) {
	    return true;
	} else {
	    return false;
	}
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
	return new HashCodeBuilder().append(getIdUser()).toHashCode();
    }

    /**
     * @return Returns the user_posts.
     */
    public Long getUser_posts() {
	return user_posts;
    }

    /**
     * @param user_posts
     *            The user_posts to set.
     */
    public void setUser_posts(Long user_posts) {
	this.user_posts = user_posts;
    }

    /**
     * @return Returns the admin.
     */
    public Integer getAdmin() {
	return admin;
    }

    /**
     * @param admin
     *            The admin to set.
     */
    public void setAdmin(Integer admin) {
	this.admin = admin;
    }

    /**
     * @return Returns the admin.
     */
    public boolean isAdministrator() {
	return getAdmin().intValue() == 1;
    }

    /**
     * @return Returns the dataRegistro.
     */
    public Date getDataRegistro() {
	return dataRegistro;
    }

    /**
     * @param dataRegistro
     *            The dataRegistro to set.
     */
    public void setDataRegistro(Date dataRegistro) {
	this.dataRegistro = dataRegistro;
    }

    /**
     * @return Returns the localizacao.
     */
    public String getLocalizacao() {
	return localizacao;
    }

    /**
     * @param localizacao
     *            The localizacao to set.
     */
    public void setLocalizacao(String localizacao) {
	this.localizacao = localizacao;
    }

    /**
     * @return Returns the website.
     */
    public String getWebsite() {
	return website;
    }

    /**
     * @param website
     *            The website to set.
     */
    public void setWebsite(String website) {
	this.website = website;
    }

    /**
     * @return Returns the occupation.
     */
    public String getOccupation() {
	return occupation;
    }

    /**
     * @param occupation
     *            The occupation to set.
     */
    public void setOccupation(String occupation) {
	this.occupation = occupation;
    }

    /**
     * @return Returns the userSig.
     */
    public String getUserSig() {
	return userSig;
    }

    /**
     * TODO Deveria estar em outro lugar. Mesmo na view mas não aqui.
     * 
     * @return Returns the userSig.
     */
    public String getUserSig_formated() {
	String ret;
	try {
	    postFormatter.setAcceptHTML(false);
	    postFormatter.setAcceptBBCode(true);
	    ret = postFormatter.preparePostText(getUserSig());
	} catch (Exception e) {
	    ret = userSig;
	}
	return ret;
    }

    /**
     * @param userSig
     *            The userSig to set.
     */
    public void setUserSig(String userSig) {
	this.userSig = userSig;
    }

    /**
     * @return Returns the user_aim.
     */
    public String getUser_aim() {
	return user_aim;
    }

    /**
     * @param user_aim
     *            The user_aim to set.
     */
    public void setUser_aim(String user_aim) {
	this.user_aim = user_aim;
    }

    /**
     * @return Returns the hash_fpwd.
     */
    public String getHash_fpwd() {
	return hash_fpwd;
    }

    /**
     * @param user_aim
     *            The hash_fpwd to set.
     */
    public void setHash_fpwd(String hash_fpwd) {
	this.hash_fpwd = hash_fpwd;
    }

    /**
     * @return Returns the user_avatar.
     */
    public String getUser_avatar() {
	return user_avatar;
    }

    /**
     * @param user_avatar
     *            The user_avatar to set.
     */
    public void setUser_avatar(String user_avatar) {
	this.user_avatar = user_avatar;
    }

    /**
     * @return Returns the user_dateformat.
     */
    public String getUser_dateformat() {
	return user_dateformat;
    }

    /**
     * @param user_dateformat
     *            The user_dateformat to set.
     */
    public void setUser_dateformat(String user_dateformat) {
	this.user_dateformat = user_dateformat;
    }

    /**
     * @return Returns the user_icq.
     */
    public String getUser_icq() {
	return user_icq;
    }

    /**
     * @param user_icq
     *            The user_icq to set.
     */
    public void setUser_icq(String user_icq) {
	this.user_icq = user_icq;
    }

    /**
     * @return Returns the user_interests.
     */
    public String getUser_interests() {
	return user_interests;
    }

    /**
     * @param user_interests
     *            The user_interests to set.
     */
    public void setUser_interests(String user_interests) {
	this.user_interests = user_interests;
    }

    /**
     * @return Returns the user_msnm.
     */
    public String getUser_msnm() {
	return user_msnm;
    }

    /**
     * @param user_msnm
     *            The user_msnm to set.
     */
    public void setUser_msnm(String user_msnm) {
	this.user_msnm = user_msnm;
    }

    /**
     * @return Returns the user_sig.
     */
    public String getUser_sig() {
	return user_sig;
    }

    /**
     * @param user_sig
     *            The user_sig to set.
     */
    public void setUser_sig(String user_sig) {
	this.user_sig = user_sig;
    }

    /**
     * @return Returns the user_yim.
     */
    public String getUser_yim() {
	return user_yim;
    }

    /**
     * @param user_yim
     *            The user_yim to set.
     */
    public void setUser_yim(String user_yim) {
	this.user_yim = user_yim;
    }

    /**
     * @return Returns the user_lastvisit.
     */
    public Date getUser_lastvisit() {
	return user_lastvisit;
    }

    /**
     * @param user_lastvisit
     *            The user_lastvisit to set.
     */
    public void setUser_lastvisit(Date user_lastvisit) {
	this.user_lastvisit = user_lastvisit;
    }

    /**
     * @return Returns the user_allow_viewonline.
     */
    public String getUser_allow_viewonline() {
	return user_allow_viewonline;
    }

    /**
     * @param user_allow_viewonline
     *            The user_allow_viewonline to set.
     */
    public void setUser_allow_viewonline(String user_allow_viewonline) {
	this.user_allow_viewonline = user_allow_viewonline;
    }

    /**
     * @return Returns the show_mail.
     */
    public Integer getShow_mail() {
	return show_mail;
    }

    /**
     * @param show_mail
     *            The show_mail to set.
     */
    public void setShow_mail(Integer show_mail) {
	this.show_mail = show_mail;
    }

    /**
     * @return Returns the show_signature.
     */
    public Integer getShow_signature() {
	return show_signature;
    }

    /**
     * @param show_signature
     *            The show_signature to set.
     */
    public void setShow_signature(Integer show_signature) {
	this.show_signature = show_signature;
    }

    /**
     * @return Returns the userStatus.
     */
    public Integer getUserStatus() {
	return userStatus;
    }

    /**
     * @param userStatus
     *            The userStatus to set.
     */
    public void setUserStatus(Integer userStatus) {
	this.userStatus = userStatus;
    }

    /**
     * @return Returns the userCode.
     */
    public String getUserCode() {
	return userCode;
    }

    /**
     * @param userCode
     *            The userCode to set.
     */
    public void setUserCode(String userCode) {
	this.userCode = userCode;
    }

    /**
     * @return Returns the timestamp of the last visit.
     */
    public Date getLastVisitTimestamp() {
	return _lastVisitTimestamp;
    }

    /**
     * @param lastVisitTimestamp
     */
    public void setLastVisitTimestamp(Date lastVisitTimestamp) {
	this._lastVisitTimestamp = lastVisitTimestamp;
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

    public Set getForumTopUsers() {
	return forumTopUsers;
    }

    public void setForumTopUsers(Set forumTopUsers) {
	this.forumTopUsers = forumTopUsers;
    }

    public Set getFavUserTopics() {
	return favUserTopics;
    }

    public void setFavUserTopics(Set favUserTopics) {
	this.favUserTopics = favUserTopics;
    }

    public Integer getReceiveNews() {
	return receiveNews;
    }

    public void setReceiveNews(Integer receiveNews) {
	this.receiveNews = receiveNews;
    }

    public List getNoticias() {
	return noticias;
    }

    public void setNoticias(List noticias) {
	this.noticias = noticias;
    }

    public List getComments() {
	return comments;
    }

    public void setComments(List comments) {
	this.comments = comments;
    }

    public String getAvatarPath() {
	return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
	this.avatarPath = avatarPath;
    }

    public String getAvatarExibition() {
	return avatarExibition;
    }

    public void setAvatarExibition(String avatarExibition) {
	this.avatarExibition = avatarExibition;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this).append("show_signature",
        	this.show_signature).append("passwordHash", this.passwordHash)
        	.append("user_dateformat", this.user_dateformat).append(
        		"userStatus", this.userStatus).append("user_msnm",
        		this.user_msnm).append("avatarExibition",
        		this.avatarExibition).append("lastVisitTimestamp",
        		this.getLastVisitTimestamp()).append("idUser",
        		this.getIdUser()).append("user_sig", this.user_sig)
        	.append("avatarPath", this.avatarPath).append("dataRegistro",
        		this.dataRegistro)
        	.append("user_posts", this.user_posts).append("email",
        		this.email).append("website", this.website).append(
        		"show_mail", this.show_mail).append("user_interests",
        		this.user_interests).append("user_avatar",
        		this.user_avatar).append("userCode", this.userCode)
        	.append("receiveNews", this.receiveNews).append("id",
        		this.getId()).append("userSig", this.userSig).append(
        		"user", this.user).append("localizacao",
        		this.localizacao).append("user_yim", this.user_yim).append("hash_fpwd",
        		this.hash_fpwd).append("user_icq", this.user_icq)
        	.append("user_allow_viewonline", this.user_allow_viewonline)
        	.append("userSig_formated",this.getUserSig_formated()).append("occupation",
        		this.occupation).append("user_lastvisit",
        		this.user_lastvisit).append("admin", this.admin).append("name",
        		this.name).append("administrator", this.isAdministrator()).append(
        		"user_aim", this.user_aim).toString();
    }

	public Integer getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
	}
}
