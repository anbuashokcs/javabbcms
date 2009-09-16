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

package org.javabb.transaction;

import java.util.ArrayList;
import java.util.List;

import org.javabb.dao.entity.ICategoryDAO;
import org.javabb.infra.UserContext;
import org.javabb.vh.Stats;
import org.javabb.vo.Category;
import org.javabb.vo.Forum;

/**
 * $Id: CategoryTransaction.java,v 1.1 2009/05/11 20:27:03 daltoncamargo Exp $
 * 
 * @author Dalton Camargo
 * @author Ronald Tetsuo Miura
 */
public class CategoryTransaction extends Transaction {

	private ICategoryDAO categoryDAO;

	private ForumTransaction forumTransaction;

	private TopicTransaction topicTransaction;

	/**
	 * @param categoryDAO
	 *            the new categoryDAO value
	 */
	public void setCategoryDAO(ICategoryDAO categoryDAO) {
		this.categoryDAO = categoryDAO;
	}

	/**
	 * @param forumTransaction
	 *            the new forumTransaction value
	 */
	public void setForumTransaction(ForumTransaction forumTransaction) {
		this.forumTransaction = forumTransaction;
	}

	public void setTopicTransaction(TopicTransaction topicTransaction) {
		this.topicTransaction = topicTransaction;
	}

	public List listCategory() throws Exception {
		List lst = categoryDAO.findAllCategoriesBySortingPosition();
		return lst;
	}

	/**
	 * @return result
	 */
	public List findAll() {
		return categoryDAO.findAllCategoriesByName();
	}

	
	public void deleteCategory(Category cat) {
		categoryDAO.deleteCategory(cat);
	}

	/**
	 * Carrega uma determinada categoria
	 * 
	 * @param idCat
	 * @return
	 */
	public Category loadCategory(Long idCat) {
		return categoryDAO.load(idCat, false);
	}

	/**
	 * Carrega uma determinada categoria
	 * 
	 * @param category
	 * @return Categoria e seus foruns devidamente configurados
	 * @throws Exception
	 */
	public Category obtainCategory(Category category) throws Exception {
		category = categoryDAO.load(category.getId(), false);
		List forumByCats = forumTransaction.findAll(category);
		category.setForuns(forumByCats);
		return category;
	}

	/**
	 * @return post count
	 */
	public long countAllPosts() {
		return categoryDAO.countAllPosts();
	}

	/**
	 * @return user count
	 */
	public long countAllUsers() {
		return categoryDAO.countAllUsers();
	}

	/**
	 * @return topic count
	 */
	public long countAllTopics() {
		return categoryDAO.countAllTopics();
	}

	/**
	 * @return
	 */
	public Stats lastRegisteredUser() {
		return categoryDAO.getStatistics();
	}

	
	public void updateCategory(Long catId, Category cat) {
		Category category = categoryDAO.load(catId, false);
		category.setNameCategory(cat.getNameCategory());
		categoryDAO.invalidateCache();
	}

	
	public void insertCategory(Category cat) {
		Category lastCategory = categoryDAO.getLastCattegoryByOrder();
		cat.setCatOrder(lastCategory == null ? new Integer(1) : lastCategory
				.getCatOrder());
		categoryDAO.insertCategory(cat);
		categoryDAO.invalidateCache();
	}

	
	public void updateUnreadForuns(List lstCategory) throws Exception {
		if (UserContext.getContext().isActiveUnreadForum()) {
			for (int y = 0; y < lstCategory.size(); y++) {
				List topicsPerCat = new ArrayList();
				Category cat = (Category) lstCategory.get(y);
				List foruns = cat.getForuns();
				if (foruns != null) {
					for (int i = 0; i < foruns.size(); i++) {
						Long forumId = ((Forum) foruns.get(i)).getId();
						List topics = topicTransaction
								.getAllTopicPerForum(forumId);
						UserContext.getContext()
								.setForumTopics(forumId, topics);
						topicsPerCat.addAll(topics);
					}
				}
				UserContext.getContext().setTopicInCat(cat.getId(),
						topicsPerCat);
			}
		}
	}

}
