package org.javabb.component.feed;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Feeds {
	private static Feeds singleton;
	
	private Map feeds;
	
	private Feeds() {
		feeds = new HashMap();
	}
	
	public static Feeds getInstance() {
		if (singleton == null)
			singleton = new Feeds();
		return singleton;
	}
	
	public static Map getFeeds() {
		return getInstance().feeds;
	}
	
	public static List getFeed(String name) {
		return (List) getInstance().feeds.get(name);
	}
	
	public void setFeeds(Map feeds) {
		this.feeds = feeds;
	}
}
