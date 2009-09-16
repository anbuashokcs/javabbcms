package org.javabb.cache.local;

import java.util.HashMap;
import java.util.List;

import org.javabb.vo.Category;

public class CategorySingleCache {

	@SuppressWarnings("unchecked")
	private static HashMap catValues = new HashMap();
	
	@SuppressWarnings("unchecked")
	public void invalidateCategoryCache(){
		catValues = new HashMap();
	}

	
	@SuppressWarnings("unchecked")
	public void setFindAllCategoriesBySortingPosition(List input) {
		String key = "findAllCategoriesBySortingPosition";
		catValues.put(key, input);
	}

	@SuppressWarnings("unchecked")
	public List getFindAllCategoriesBySortingPosition() {
		String key = "findAllCategoriesBySortingPosition";
		return (List) catValues.get(key);
	}
	
	@SuppressWarnings("unchecked")
	public void setLoadCategory(Category input, Long catId) {
		String key = "loadCategory"+catId;
		catValues.put(key, input);
	}

	public Category getLoadCategory(Long catId) {
		String key = "loadCategory"+catId;
		return (Category) catValues.get(key);
	}

	
}
