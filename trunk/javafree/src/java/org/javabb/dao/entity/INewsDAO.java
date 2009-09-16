package org.javabb.dao.entity;

import java.util.List;

import org.javabb.dao.DAOConstants;
import org.javabb.vo.Newsletter;

public interface INewsDAO extends DAOConstants {

	public List getNews() throws Exception;
	public Newsletter loadNews(Long id) throws Exception;
	public void insert(Newsletter news)throws Exception;
	public void delete(Newsletter news) throws Exception;
	public List getUsersNewsletter() throws Exception;	
}
