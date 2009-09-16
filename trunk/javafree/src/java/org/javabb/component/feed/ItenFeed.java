package org.javabb.component.feed;

import java.util.Date;

public class ItenFeed implements Comparable{
	private String link;
	private String desc;
	private String title;
	private Date date;
	
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
   
	public int compareTo(Object o1) {
        if (o1 == null) return -1;
        
        ItenFeed news = (ItenFeed)o1;
        
        int ret = this.date.compareTo(news.date) * -1;
        
        if (ret != 0) return ret; 
        return 0;
	}
}
