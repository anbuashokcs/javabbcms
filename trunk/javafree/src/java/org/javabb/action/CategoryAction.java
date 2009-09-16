package org.javabb.action;

import java.util.ArrayList;
import java.util.List;

import org.javabb.action.infra.BaseAction;
import org.javabb.infra.UserContext;
import org.javabb.transaction.CategoryTransaction;
import org.javabb.transaction.PostTransaction;
import org.javabb.transaction.UserTransaction;
import org.javabb.vh.Stats;
import org.javabb.vo.Category;

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
 * $Id: CategoryAction.java,v 1.1 2009/05/11 20:26:52 daltoncamargo Exp $
 * 
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org
 *         </a> <br>
 * @author Ronald Tetsuo Miura
 */
@SuppressWarnings("serial")
public class CategoryAction extends BaseAction {

	private CategoryTransaction _categoryTransaction;
	private UserTransaction _userTransaction;
	private PostTransaction _postTransaction;

	private Stats stats = new Stats();
	private Category category = new Category();
	private List lstCategory = new ArrayList();
	private List posts;
	private List noticias = new ArrayList();

	/**
	 * @param categoryTransaction
	 */
	public void setCategoryTransaction(CategoryTransaction categoryTransaction) {
		this._categoryTransaction = categoryTransaction;
	}

	public void setPostTransaction(PostTransaction transaction) {
		_postTransaction = transaction;
	}

	/**
	 * @param userTransaction
	 */
	public void setUserTransaction(UserTransaction userTransaction) {
		_userTransaction = userTransaction;
	}

	// ####################################################################
	// Actions
	// ####################################################################

	public String listCategory() throws Exception {
		stats = _categoryTransaction.lastRegisteredUser();
		stats.setTotalPostCount(new Integer((int) _categoryTransaction
				.countAllPosts()));
		stats.setTotalRegisteredUsers(new Integer((int) _categoryTransaction
				.countAllUsers()));
		lstCategory = _categoryTransaction.listCategory();
		_userTransaction.updateVisitTimestamp();
		return SUCCESS;
	}

	public String loadCategory() throws Exception {

		setStaffHome();

		if (_categoryId != null) {
			setSessionAttribute("categoryChosed", _categoryId);
			category = _categoryTransaction.loadCategory(_categoryId);
		} else {
			if (getSessionAttribute("categoryChosed") != null) {
				_categoryId = (Long) getSessionAttribute("categoryChosed");
				category = _categoryTransaction.loadCategory(_categoryId);
			} else {
				category = (Category) lstCategory.get(0);
				setSessionAttribute("categoryChosed", category.getId());
			}
		}

		noticias = noticiasTransaction.findByNoticias(1, 10);
		posts = _postTransaction.findLastShortPosts(12);

		return SUCCESS;
	}

	private void setStaffHome() throws Exception {
		stats = _categoryTransaction.lastRegisteredUser();
		stats.setTotalPostCount(new Integer((int) _categoryTransaction
				.countAllPosts()));
		stats.setTotalRegisteredUsers(new Integer((int) _categoryTransaction
				.countAllUsers()));
		_userTransaction.updateVisitTimestamp();
		lstCategory = _categoryTransaction.listCategory();
		_categoryTransaction.updateUnreadForuns(lstCategory);
	}

	public String markAllTopicsInCatAsRead() throws Exception {

		_categoryId = (Long) getSessionAttribute("categoryChosed");

		UserContext.getContext().setAllTopicsInCatAsRead(_categoryId);
		log.debug("setting all topics as read");

		setUrl("forum.jbb");

		return SUCCESS;
	}

	public String updateCategory() {
		_categoryTransaction.updateCategory(category.getIdCategory(), category);
		return SUCCESS;
	}

	public String insertCategory() {
		_categoryTransaction.insertCategory(category);
		return SUCCESS;
	}

	public String showEditCategory() {
		category = _categoryTransaction.loadCategory(category.getIdCategory());
		return SUCCESS;
	}

	/**
	 * @return list
	 * @throws Exception
	 */
	public String listAllCategory() throws Exception {
		lstCategory = _categoryTransaction.findAll();
		return SUCCESS;
	}

	public String deleteCategory() throws Exception {
		_categoryTransaction.deleteCategory(category);
		return SUCCESS;
	}

	// ####################################################################
	// View objects accessors
	// ####################################################################

	/**
	 * @return Returns the category.
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * @return Returns the lstCategory.
	 */
	public List getLstCategory() {
		return lstCategory;
	}

	/**
	 * @return Returns the stats.
	 */
	public Stats getStats() {
		return stats;
	}

	public List getPosts() {
		return posts;
	}

	public void setPosts(List posts) {
		this.posts = posts;
	}

	public List getNoticias() {
		return noticias;
	}

	public void setNoticias(List noticias) {
		this.noticias = noticias;
	}
}