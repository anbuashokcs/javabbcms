package org.javabb.cache.local;

import java.util.HashMap;
import java.util.List;

public class TopicSingleCache {

    @SuppressWarnings("unchecked")
    private static HashMap cacheValues = new HashMap();

    @SuppressWarnings("unchecked")
    public void invalidateCache() {
	cacheValues = new HashMap();
    }

    @SuppressWarnings("unchecked")
    public void setFindLastArticles(List input, Integer nmbArticles) {
	String key = "findLastArticles-" + nmbArticles;
	cacheValues.put(key, input);
    }

    @SuppressWarnings("unchecked")
    public List getFindLastArticles(Integer nmbArticles) {
	String key = "findLastArticles-" + nmbArticles;
	return (List) cacheValues.get(key);
    }

}
