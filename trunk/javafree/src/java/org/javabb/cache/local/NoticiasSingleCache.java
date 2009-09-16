package org.javabb.cache.local;

import java.util.HashMap;
import java.util.List;

public class NoticiasSingleCache {

    @SuppressWarnings("unchecked")
    private static HashMap cacheValues = new HashMap();

    @SuppressWarnings("unchecked")
    public void invalidateCache() {
	cacheValues = new HashMap();
    }

    @SuppressWarnings("unchecked")
    public void setFindByNoticias(List input, int pageNumber, int itemsPerPage) {
	String key = "findByNoticias" + pageNumber + itemsPerPage;
	cacheValues.put(key, input);
    }

    @SuppressWarnings("unchecked")
    public List getFindByNoticias(int pageNumber, int itemsPerPage) {
	String key = "findByNoticias" + pageNumber + itemsPerPage;
	return (List) cacheValues.get(key);
    }

    @SuppressWarnings("unchecked")
    public void setFindByDestaques(List input, int pageNumber, int itemsPerPage) {
	String key = "findByDestaques" + pageNumber + itemsPerPage;
	cacheValues.put(key, input);
    }

    @SuppressWarnings("unchecked")
    public List getFindByDestaques(int pageNumber, int itemsPerPage) {
	String key = "findByDestaques" + pageNumber + itemsPerPage;
	return (List) cacheValues.get(key);
    }

    @SuppressWarnings("unchecked")
    public void setFindAllNoticias(List input, int pageNumber, int itemsPerPage) {
	String key = "findAllNoticias" + pageNumber + itemsPerPage;
	cacheValues.put(key, input);
    }

    @SuppressWarnings("unchecked")
    public List getFindAllNoticias(int pageNumber, int itemsPerPage) {
	String key = "findAllNoticias" + pageNumber + itemsPerPage;
	return (List) cacheValues.get(key);
    }

    @SuppressWarnings("unchecked")
    public void setSearchRelatedNoticias(List input, String query, Long notId) {
	String key = "searchRelatedNoticias" + query + notId;
	cacheValues.put(key, input);
    }

    @SuppressWarnings("unchecked")
    public List getSearchRelatedNoticias(String query, Long notId) {
	String key = "searchRelatedNoticias" + query + notId;
	return (List) cacheValues.get(key);
    }
    
    @SuppressWarnings("unchecked")
    public void setFindLastComments(List input, int items) {
	String key = "findLastComments" + items;
	cacheValues.put(key, input);
    }

    @SuppressWarnings("unchecked")
    public List getFindLastComments(int items) {
	String key = "findLastComments" + items;
	return (List) cacheValues.get(key);
    }
    
    
}
