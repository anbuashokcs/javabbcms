package org.javabb.action;

import java.util.List;

import org.javabb.action.infra.BaseAction;
import org.javabb.component.NewsletterStatus;
import org.javabb.transaction.NewsTransaction;
import org.javabb.vo.Newsletter;

public class NewsAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	private NewsTransaction newsTransaction;
	public void setNewsTransaction(NewsTransaction newsTransaction) {
		this.newsTransaction = newsTransaction;
	}

	private List newsList;
	private Newsletter newsletter;
	private NewsletterStatus newsStatus = new NewsletterStatus();
	
	
	public String getNews() throws Exception{
		newsList = newsTransaction.getNews();
		return SUCCESS;
	}

	public String loadNews() throws Exception{
		newsletter = newsTransaction.loadNews(newsletter.getId());
		return SUCCESS;
	}
	
	public String insert() throws Exception{
		newsTransaction.insertNews(newsletter);
		return SUCCESS;
	}
	
	public String delete() throws Exception{
		newsTransaction.deleteNews(newsletter);
		return SUCCESS;
	}
	
	public String edit() throws Exception{
		newsTransaction.updateNews(newsletter);
		return SUCCESS;
	}	
	
	public String activeNews() throws Exception{
		newsTransaction.sendNewsletter(newsletter.getId());
		return SUCCESS;
	}
	
	
	public Newsletter getNewsletter() {
		return newsletter;
	}

	public void setNewsletter(Newsletter newsletter) {
		this.newsletter = newsletter;
	}

	public List getNewsList() {
		return newsList;
	}

	public void setNewsList(List newsList) {
		this.newsList = newsList;
	}

	public NewsletterStatus getNewsStatus() {
		return newsStatus;
	}

	public void setNewsStatus(NewsletterStatus newsStatus) {
		this.newsStatus = newsStatus;
	}

	
	

}
