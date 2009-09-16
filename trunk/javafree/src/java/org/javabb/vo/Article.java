package org.javabb.vo;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Article implements Serializable {

	private Long artId;
	private String name;
	private String about;
	private Date datePub;
	private Integer views;
	private String body;
	private Integer active;
	private CatArticle catArticle;
	
	
	
	public Long getArtId() {
		return artId;
	}
	public void setArtId(Long artId) {
		this.artId = artId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public Date getDatePub() {
		return datePub;
	}
	public void setDatePub(Date datePub) {
		this.datePub = datePub;
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
	public CatArticle getCatArticle() {
		return catArticle;
	}
	public void setCatArticle(CatArticle catArticle) {
		this.catArticle = catArticle;
	}
	public Integer getActive() {
		return active;
	}
	public void setActive(Integer active) {
		this.active = active;
	}
	
	
	
}
