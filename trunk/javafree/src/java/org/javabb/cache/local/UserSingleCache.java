package org.javabb.cache.local;

import java.util.HashMap;

import org.javabb.vo.User;


public class UserSingleCache  {
	
	@SuppressWarnings("unchecked")
	private static HashMap cacheValues = new HashMap();
	
	@SuppressWarnings("unchecked")
	public void invalidateCache(){
		cacheValues = new HashMap();
	}

	
	@SuppressWarnings("unchecked")
	public void setLoadByUsercode(String userCode, User user) {
		String key = "loadByUsercode" + userCode;
		cacheValues.put(key, user);
	}

	public User getLoadByUsercode(String userCode) {
		String key = "loadByUsercode" + userCode;
		return (User) cacheValues.get(key);
	}


}
