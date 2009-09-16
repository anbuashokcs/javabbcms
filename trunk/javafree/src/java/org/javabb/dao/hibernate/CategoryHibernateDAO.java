package org.javabb.dao.hibernate;

import java.util.List;

import org.javabb.dao.entity.ICategoryDAO;
import org.javabb.vh.Stats;
import org.javabb.vo.Category;
import org.javabb.vo.Post;
import org.javabb.vo.Topic;
import org.javabb.vo.User;

/*
 * Copyright 2004 JavaFree.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * $Id: CategoryHibernateDAO.java,v 1.2 2009/06/22 22:54:07 daltoncamargo Exp $
 * 
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org
 *         </a> <br>
 * @author Ronald Tetsuo Miura
 */
public class CategoryHibernateDAO extends HibernateDAO implements ICategoryDAO {

	static final String[] FIELD_NAMES = new String[] { null, "idCategory",
			"catOrder", "nameCategory" };

	/**
	 * Invalidates a list of categories in the cache
	 */
	public void invalidateCache() {
		categoryCache.invalidateCategoryCache();
	}

	/**
	 * @see org.javabb.dao.entity.ICategoryDAO#load(java.lang.Long)
	 */
	public Category load(Long id, boolean fromCache) {
		Category cat = categoryCache.getLoadCategory(id);
		if (fromCache) {
			cat = null;
		}
		if (cat == null) {
			cat = (Category) getHibernateTemplate().load(Category.class, id);
			categoryCache.setLoadCategory(cat, id);
		}
		return cat;
	}

	/**
	 * @see org.javabb.dao.entity.ICategoryDAO#countAllPosts()
	 */
	public int countAllPosts() {
		Integer count = counterCache.getCountAllPosts();
		if (count == null) {
			count = countRows(Post.class, "idPost");
			counterCache.setCountAllPosts(count);
		}
		return count;
	}

	/**
	 * @see org.javabb.dao.entity.ICategoryDAO#countAllTopics()
	 */
	public int countAllTopics() {
		Integer count = counterCache.getCountAllTopics();
		if (count == null) {
			count = countRows(Topic.class, "idTopic");
			counterCache.setCountAllTopics(count);
		}
		return count;
	}

	/**
	 * @see org.javabb.dao.entity.ICategoryDAO#countAllUsers()
	 */
	public int countAllUsers() {
		Integer count = counterCache.getCountAllUsers();
		if (count == null) {
			count = countRows(User.class, "idUser");
			counterCache.setCountAllUsers(count);
		}
		return count;
	}

	/**
	 * @see org.javabb.dao.hibernate.HibernateDAO#getFieldName(int)
	 */
	protected String getFieldName(int i) {
		return FIELD_NAMES[i];
	}

	/**
	 * @see org.javabb.dao.entity.ICategoryDAO#getStatistics()
	 */
	public Stats getStatistics() {

		Stats sts = counterCache.getStatistics();
		if (sts == null) {
			String[] orderField = { "idUser" };
			String[] orderValue = { "desc" };

			User lastUser = null;
			List lstUsers = findAll(User.class, orderField, orderValue, 0, 1);

			if (!lstUsers.isEmpty()) {
				lastUser = (User) lstUsers.get(0);
			}
			lstUsers = null;
			sts = new Stats();
			sts.setLastRegisteredUserId(lastUser.getIdUser());
			sts.setLastRegisteredUserName(lastUser.getUser());
			counterCache.setStatistics(sts);
		}
		return sts;
	}

	/**
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List findAllCategoriesBySortingPosition() {
		/*
		 * List cats = categoryCache.getFindAllCategoriesBySortingPosition();
		 * cats = null; if(cats == null){ cats = findAll(Category.class, new
		 * int[] { ICategoryDAO.SORTING_POSITION });
		 * //categoryCache.setFindAllCategoriesBySortingPosition(cats); }
		 */

		List cats = null; // categoryCache.getFindAllCategoriesBySortingPosition(
		// );
		// Como esta dando problemas de lazy loading, dispensei o cache..
		if (cats == null) {
			cats = findAll(Category.class,
					new int[] { ICategoryDAO.SORTING_POSITION });
			// categoryCache.setFindAllCategoriesBySortingPosition(cats);
		}

		return cats;
	}

	@SuppressWarnings("unchecked")
	public List findAllCategoriesByName() {
		return findAll(Category.class, new int[] { ICategoryDAO.NAME });
	}

	/**
	 * Insert a category
	 * 
	 * @param cat
	 * @return
	 */
	public Category insertCategory(Category cat) {
		getHibernateTemplate().saveOrUpdate(cat);
		return null;
	}

	public Category getLastCattegoryByOrder() {
		Category cat = null;
		List lstCats = findAll(Category.class, new String[] { "catOrder" },
				new String[] { "desc" }, 0, 1);

		if (!lstCats.isEmpty()) {
			cat = (Category) lstCats.get(0);
		}
		return cat;
	}

	/**
	 * Delete category
	 * 
	 * @param cat
	 */
	public void deleteCategory(Category cat) {
		this.getHibernateTemplate().delete(cat);
	}
}
