package org.javabb.cache.local;

import java.util.HashMap;
import java.util.List;

public class PostSingleCache {

    @SuppressWarnings("unchecked")
    private static HashMap cacheValues = new HashMap();

    @SuppressWarnings("unchecked")
    public void invalidateCache() {
	cacheValues = new HashMap();
    }

    @SuppressWarnings("unchecked")
    public void setFindLastPostUsingSQL(List input, int limit) {
	String key = "findLastPostUsingSQL-" + limit;
	cacheValues.put(key, input);
    }

    @SuppressWarnings("unchecked")
    public List getFindLastPostUsingSQL(int limit) {
	String key = "findLastPostUsingSQL-" + limit;
	return (List) cacheValues.get(key);
    }

}
