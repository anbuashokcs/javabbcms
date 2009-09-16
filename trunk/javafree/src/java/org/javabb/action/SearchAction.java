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
package org.javabb.action;

import java.util.ArrayList;
import java.util.List;

import org.javabb.action.infra.BaseAction;
import org.javabb.component.PostFormatter;
import org.javabb.infra.CharFilter;
import org.javabb.infra.Utils;
import org.javabb.transaction.CategoryTransaction;
import org.javabb.transaction.PostTransaction;
import org.javabb.vo.Post;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;

/**
 * 
 * @author <a href="mailto:jackganzha@dev.java.net">Marcos Silva Pereira</a>
 * 
 * @since 22/04/2005
 * 
 * @version $Id: SearchAction.java,v 1.2 2009/06/22 22:54:05 daltoncamargo Exp $
 */
public class SearchAction extends BaseAction {

    private static final long serialVersionUID = 1L;
    public static final String EMPTY = "empty";

    private String query;

    private int page;
    private int totalRowsFound;
    private boolean empty = false;
    private Integer typeSearch = new Integer(1);

    private List posts;
    private PostTransaction postTransaction;
    private CategoryTransaction _categoryTransaction;

    public List getLstCategory() throws Exception {
	return _categoryTransaction.listCategory();
    }

    public String searchInPosts() {
	posts = postTransaction.findInPosts(query, page);
	return SUCCESS;
    }

    public String formatPost(Post post) {
	String basePath = ServletActionContext.getRequest().getContextPath();
	return postFormatter.formatPost(basePath, post);
    }

    public void setCategoryTransaction(CategoryTransaction categoryTransaction) {
	this._categoryTransaction = categoryTransaction;
    }

    public String execute() {
	return Action.SUCCESS;
    }

    /**
     * actually do search
     * 
     * @return action status
     */
    public String search() throws Exception {
	Long forumId = getForumId();
	
	log.debug("Search: [query]:" + query);
	if (query != null) {
	    maintainSearchList(query, forumId);
	    query = CharFilter.replaceSpecial(query);
	    if (query.indexOf(" ") > 0 && query.indexOf(" AND ") == -1) {
		query = query.replaceAll(" ", " AND ");
	    }
	}
	posts = postTransaction.findByQuery(query, forumId, page);
	if (posts.isEmpty()) {
	    empty = true;
	    query = query.replaceAll(" AND ", " ");
	    log.debug("Search: [No rows found]");
	    return EMPTY;
	}
	totalRowsFound = postTransaction.getTotalRowsOfLucene(query, forumId);

	query = query.replaceAll(" AND ", " ");

	log.debug("Search: [total rows]:" + totalRowsFound);

	return SUCCESS;
    }

    /**
     * This method is responsable for check in a Session List if the user
     * already has searched for some string, it he didn't we stored this string
     * in a Statitics table.
     * 
     * @param query
     */
    private void maintainSearchList(String query, Long forumId)
	    throws Exception {
	if (page == 1 && query.length() > 2) {
	    if (getSessionAttribute("querySearchList") != null) {
		List words = (List) getSessionAttribute("querySearchList");
		if (!Utils.compositeWord(query)) {
		    String[] parsedWords = query.split(" ");
		    for (int i = 0; i < parsedWords.length; i++) {
			if (!words.contains(parsedWords[i])) {
			    words.add(parsedWords[i]);
			    postTransaction.insertSearchLockUp(parsedWords[i],
				    forumId);
			}
		    }
		    setSessionAttribute("querySearchList", words);
		} else {
		    // This is a composite word, Sample:
		    // "Data Base or in portuguese"
		    // "Banco de Dados"
		    if (words != null && !words.contains(query)) {
			words.add(query);
			postTransaction.insertSearchLockUp(query, forumId);
			setSessionAttribute("querySearchList", words);
		    }
		}

	    } else {
		List words = new ArrayList();
		if (!Utils.compositeWord(query)) {
		    String[] parsedWords = query.split(" ");
		    for (int i = 0; i < parsedWords.length; i++) {
			if (!words.contains(parsedWords[i])) {
			    words.add(parsedWords[i]);
			    postTransaction.insertSearchLockUp(parsedWords[i],
				    forumId);
			}
		    }
		} else {
		    words.add(query);
		    postTransaction.insertSearchLockUp(query, forumId);
		}
		setSessionAttribute("querySearchList", words);
	    }
	}
    }

    /**
     * @param post
     * @return formated post
     */
    public String formatPostWithoutBBCode(Post post) {
	String basePath = ServletActionContext.getRequest().getContextPath();
	return postFormatter.formatWithoutBBCode(basePath, post);
    }

    public boolean isEmpty() {
	return empty;
    }

    public void setEmpty(boolean empty) {
	this.empty = empty;
    }

    public List getPosts() {
	return posts;
    }

    public void setPosts(List posts) {

	this.posts = posts;
    }

    public PostTransaction getPostTransaction() {

	return postTransaction;
    }

    public void setPostTransaction(PostTransaction postTransaction) {

	this.postTransaction = postTransaction;
    }

    public String getQuery() {

	return query;
    }

    public void setQuery(String query) {

	this.query = query;
    }

    public int getPage() {

	return page;
    }

    public void setPage(int page) {

	this.page = page;
    }

    public int getTotalRowsFound() {
	return totalRowsFound;
    }

    public void setTotalRowsFound(int totalRowsFound) {
	this.totalRowsFound = totalRowsFound;
    }

    public Integer getTypeSearch() {
	return typeSearch;
    }

    public void setTypeSearch(Integer typeSearch) {
	this.typeSearch = typeSearch;
    }

}
