package org.javabb.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class Noticias implements Serializable {

	private Long notId;
	private Date publishDate;
	private String title;
	private Integer views;
	private String body;
	private String link;
	private String linkImg;
	private Integer active;
	private Integer home;
	private User user;
	private List comments;

	public Noticias(){}
	
	public Noticias(Long id){
		notId = id;
	}
	
	public Long getNotId() {
		return notId;
	}
	public void setNotId(Long notId) {
		this.notId = notId;
	}
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getViews() {
		return views;
	}
	public void setViews(Integer views) {
		this.views = views;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public Integer getActive() {
		return active;
	}
	public void setActive(Integer active) {
		this.active = active;
	}
	public Integer getHome() {
		return home;
	}
	public void setHome(Integer home) {
		this.home = home;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public List getComments() {
		return comments;
	}
	public void setComments(List comments) {
		this.comments = comments;
	}
	public String getLinkImg() {
		return linkImg;
	}
	public void setLinkImg(String linkImg) {
		this.linkImg = linkImg;
	}
	
	
}
