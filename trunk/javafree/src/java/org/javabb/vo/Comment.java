package org.javabb.vo;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Comment implements Serializable {

	private Long id;
	private String body;
	private Date datePublished;	
	private User user;
	private Noticias noticias;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Noticias getNoticias() {
		return noticias;
	}
	public void setNoticias(Noticias noticias) {
		this.noticias = noticias;
	}
	public Date getDatePublished() {
		return datePublished;
	}
	public void setDatePublished(Date datePublished) {
		this.datePublished = datePublished;
	}
	
}
