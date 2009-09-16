package org.javabb.vo;

import java.util.Date;


public class PostText extends Post {
	
	private static final long serialVersionUID = 1L;
	public String postBody;

	/*
	 * Used on search
	 */
    public PostText(Long topicId, Integer pageLastPost, Long postId, String titleTopic,
    		Long forumId, String forumName, Date postDate, Long userId, String userName,
    		Integer replies, Integer views, String postBody){
    	
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
    	this.setPostBody(postBody);
    	
    }
    
    
    public PostText(){
    	
    }
    public PostText(Long _pId){
    	setId(_pId);
    }
	
	/**
	 * @return Returns the postBody.
	 */
	public String getPostBody() {
		return postBody;
	}
	/**
	 * @param postBody The postBody to set.
	 */
	public void setPostBody(String postBody) {
		this.postBody = postBody;
	}
}
