package org.javabb.cache.local;

import java.util.HashMap;

import org.javabb.vh.Stats;


public class CounterSingleCache  {
	
	@SuppressWarnings("unchecked")
	private static HashMap cacheValues = new HashMap();
	
	@SuppressWarnings("unchecked")
	public void invalidateCache(){
		cacheValues = new HashMap();
	}

	
	@SuppressWarnings("unchecked")
	public void setCountMessagesByUser(Long userId, Integer nmbMessages) {
		String key = "countMessagesByUser" + userId;
		cacheValues.put(key, nmbMessages);
	}

	public Integer getCountMessagesByUser(Long userId) {
		String key = "countMessagesByUser" + userId;
		return (Integer) cacheValues.get(key);
	}

	@SuppressWarnings("unchecked")
	public void setStatistics(Stats input) {
		String key = "getStatistics";
		cacheValues.put(key, input);
	}

	public Stats getStatistics() {
		String key = "getStatistics";
		return (Stats) cacheValues.get(key);
	}
	
	@SuppressWarnings("unchecked")
	public void setCountAllPosts(Integer input) {
		String key = "countAllPosts";
		cacheValues.put(key, input);
	}

	public Integer getCountAllPosts() {
		String key = "countAllPosts";
		return (Integer) cacheValues.get(key);
	}
	
	@SuppressWarnings("unchecked")
	public void setCountAllTopics(Integer input) {
		String key = "countAllTopics";
		cacheValues.put(key, input);
	}

	public Integer getCountAllTopics() {
		String key = "countAllTopics";
		return (Integer) cacheValues.get(key);
	}
	
	@SuppressWarnings("unchecked")
	public void setCountAllUsers(Integer input) {
		String key = "countAllUsers";
		cacheValues.put(key, input);
	}

	public Integer getCountAllUsers() {
		String key = "countAllUsers";
		return (Integer) cacheValues.get(key);
	}
}
