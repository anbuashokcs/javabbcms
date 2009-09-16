package org.javabb.dao.hibernate;

import java.util.List;

import org.javabb.dao.entity.INewsDAO;
import org.javabb.vo.Newsletter;

public class NewsHibernateDAO extends HibernateDAO implements INewsDAO {

    public List getNews() throws Exception {
	return loadAll(new Newsletter());
    }

    public Newsletter loadNews(Long id) throws Exception {
	return (Newsletter) getHibernateTemplate().load(Newsletter.class, id);
    }

    public void insert(Newsletter news) throws Exception {
	getHibernateTemplate().save(news);
    }

    public void delete(Newsletter news) throws Exception {
	
	getHibernateTemplate().delete(news);
    }

    public List getUsersNewsletter() throws Exception {
	return getHibernateTemplate().find(
		"from User u where u.receiveNews = 1");
    }

}
