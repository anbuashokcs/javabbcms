package org.javabb.component;

import java.util.HashMap;

/**
 * Used to maintain the newsletter events
 * @author dalton
 *
 */
public class NewsletterStatus {

	private static HashMap hashNews = new HashMap();
	
	public static boolean getStatusNews(Long idNews){
		if(hashNews.get(idNews) == null){
			return false;
		} else {
			Boolean bol = (Boolean) hashNews.get(idNews);
			return bol.booleanValue();
		}
	}

	/**
	 * @param idNews
	 * @param status
	 */
	public static void setStatusNews(Long idNews, Boolean status){
		hashNews.put(idNews, status);
	}
	

}
