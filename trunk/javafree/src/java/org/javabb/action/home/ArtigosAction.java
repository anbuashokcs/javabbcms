package org.javabb.action.home;

import java.util.List;

import org.javabb.action.infra.BaseAction;
import org.javabb.transaction.CategoryTransaction;
import org.javabb.transaction.ForumTransaction;
import org.javabb.transaction.TopicTransaction;
import org.javabb.vo.Forum;

@SuppressWarnings("serial")
public class ArtigosAction extends BaseAction {

    private List list;
    private Forum forum;
    private List articles;

    private CategoryTransaction categoryTransaction;

    public void setCategoryTransaction(CategoryTransaction categoryTransaction) {
	this.categoryTransaction = categoryTransaction;
    }

    private ForumTransaction forumTransaction;

    public void setForumTransaction(ForumTransaction forumTransaction) {
	this.forumTransaction = forumTransaction;
    }

    private TopicTransaction topicTransaction;

    public void setTopicTransaction(TopicTransaction topicTransaction) {
	this.topicTransaction = topicTransaction;
    }

    public String loadArticles() throws Exception {
	list = forumTransaction.findForumByCatId(4L);
	articles = topicTransaction.findLastArticles(20);
	return SUCCESS;
    }

    public String loadSingleArticle() throws Exception {
	forum = forumTransaction.loadForum(getForumId());
	list = topicTransaction.findByForumId(getForumId());
	articles = forumTransaction.findForumByCatId(4L);
	return SUCCESS;
    }

  
    
    public List getList() {
	return list;
    }

    public void setList(List list) {
	this.list = list;
    }

    public Forum getForum() {
	return forum;
    }

    public void setForum(Forum forum) {
	this.forum = forum;
    }

    public List getArticles() {
	return articles;
    }

    public void setArticles(List articles) {
	this.articles = articles;
    }

}
