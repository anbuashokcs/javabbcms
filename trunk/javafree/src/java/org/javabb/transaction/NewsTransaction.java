package org.javabb.transaction;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javabb.component.NewsletterStatus;
import org.javabb.dao.entity.INewsDAO;
import org.javabb.thread.NewsThread;
import org.javabb.vo.Newsletter;
import org.springframework.beans.BeanUtils;

public class NewsTransaction {
	
	protected final Log _log = LogFactory.getLog(this.getClass());
	
	private INewsDAO newsDAO;
	public void setNewsDAO(INewsDAO newsDAO) {
		this.newsDAO = newsDAO;
	}

	
	public List getNews() throws Exception{
		return newsDAO.getNews();
	}

	public Newsletter loadNews(Long id) throws Exception{
		return newsDAO.loadNews(id);
	}
	
	
	public void insertNews(Newsletter news) throws Exception{
		newsDAO.insert(news);
	}
	
	
	public void deleteNews(Newsletter news) throws Exception{
		newsDAO.delete(news);
	}
	
	
	public void updateNews(Newsletter news) throws Exception{
		Newsletter newsletter = newsDAO.loadNews(news.getId());
		BeanUtils.copyProperties(news,newsletter);
	}
	
	public void sendNewsletter(Long id) throws Exception{
		boolean status = NewsletterStatus.getStatusNews(id);
		_log.debug("Sending newsletter");
		if(!status){
			_log.debug("Getting users");
			List users = newsDAO.getUsersNewsletter();
			if(users != null){
				_log.debug("Initializing newsletter Thread to " + users.size() +" users");
				Newsletter newsletter = loadNews(id);
				Thread tr = new Thread(new NewsThread(users, newsletter));
				tr.start();
				_log.debug("Newsletter thread was initialized.." + tr);
			} else {
				_log.debug("There is no users to send this newsletter.");
			}
		} else {
			_log.debug("Thread has already been started.");
		}
		
	}
	
}
