package org.javabb.vo;

import java.io.Serializable;
import java.util.Date;

public class ForumTopUser implements Serializable {

	public ForumTopUserPK comp_id;
	public Forum forum;
	public User user;
	private Long postCount;
	private Date dateRow;


	public ForumTopUserPK getComp_id() {
		return comp_id;
	}
	public void setComp_id(ForumTopUserPK comp_id) {
		this.comp_id = comp_id;
	}
	public Forum getForum() {
		return forum;
	}
	public void setForum(Forum forum) {
		this.forum = forum;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Date getDateRow() {
		return dateRow;
	}
	public void setDateRow(Date dateRow) {
		this.dateRow = dateRow;
	}
	public Long getPostCount() {
		return postCount;
	}
	public void setPostCount(Long postCount) {
		this.postCount = postCount;
	}
	
}
