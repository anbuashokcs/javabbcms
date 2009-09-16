package org.javabb.cache.local;

import java.util.HashMap;
import java.util.List;

public class WikiSingleCache {

    @SuppressWarnings("unchecked")
    private static HashMap cacheValues = new HashMap();

    @SuppressWarnings("unchecked")
    public void invalidateCache() {
	cacheValues = new HashMap();
    }

    @SuppressWarnings("unchecked")
    public void setLoadWikiWord(String word, List wiki) {
	String key = "loadWikiWord" + word;
	cacheValues.put(key, wiki);
    }

    @SuppressWarnings("unchecked")
    public List getLoadWikiWord(String word) {
	String key = "loadWikiWord" + word;
	return (List) cacheValues.get(key);
    }

    @SuppressWarnings("unchecked")
    public List getRelatedWikiWord(String word){
	String key = "relatedWikiWord" + word;
	return (List) cacheValues.get(key);
    }
    @SuppressWarnings("unchecked")
    public void setRelatedWikiWord(String word, List wiki) {
	String key = "relatedWikiWord" + word;
	cacheValues.put(key, wiki);
    }
    
    @SuppressWarnings("unchecked")
    public List getLoadAllWikies() {
	String key = "loadAllWikies";
	return (List) cacheValues.get(key);
    }
    
    @SuppressWarnings("unchecked")
    public void setLoadAllWikies(List wikies){
	String key = "loadAllWikies";
	cacheValues.put(key, wikies);
    }
    
    
}
