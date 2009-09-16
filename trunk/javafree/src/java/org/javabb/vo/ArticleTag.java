package org.javabb.vo;

import java.io.Serializable;

public class ArticleTag implements Serializable {

	private static final long serialVersionUID = 1L;

	
	private Long id;
	private String name;
	private Topic topic;
	

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Topic getTopic() {
		return topic;
	}
	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	
}
