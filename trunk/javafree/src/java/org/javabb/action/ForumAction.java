package org.javabb.action;

import java.util.ArrayList;
import java.util.List;

import org.javabb.action.infra.BaseAction;
import org.javabb.transaction.ForumTransaction;
import org.javabb.vh.ForumConfigView;
import org.javabb.vo.Forum;

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
 * $Id: ForumAction.java,v 1.1 2009/05/11 20:26:52 daltoncamargo Exp $
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org </a> <br>
 * @author Ronald Tetsuo Miura
 * @author Lucas Teixeira - <a href="mailto:lucas@javabb.org">lucas@javabb.org </a> <br>
 */
public class ForumAction extends BaseAction {

	private ForumConfigView forumConfigView = new ForumConfigView();
	
    protected Forum forum = new Forum();

    protected ForumTransaction forumTransaction;

    protected List forums = new ArrayList();

    private int removeForum = 0;

    // Temas (pastas)
    private List themes = new ArrayList();
    private List langs;
    // Buttons Language
    private List btnLang = new ArrayList();

    // ####################################################################
    // Parameters
    // ####################################################################

    //Parameters to sort foruns (Admin Painel)
    private Integer destOrder;
    private Integer position;
    
    /**
     * @param removeForum The removeForum to set.
     */
    public void setRemoveForum(int removeForum) {
        this.removeForum = removeForum;
    }

    // ####################################################################
    // Dependencies
    // ####################################################################


    /**
     * @param forumTransaction the new forumTransaction value
     */
    public void setForumTransaction(ForumTransaction forumTransaction) {
        this.forumTransaction = forumTransaction;
    }

    // ####################################################################
    // View objects accessors
    // ####################################################################

    /**
     * @return result
     * @throws Exception
     */
    public String forumConfig() throws Exception {
    	btnLang = forumTransaction.listButtons();
        //langs = forumTransaction.listLangs();
        return SUCCESS;
    }    
    
    /**
     * @return action status
     * @throws Exception
     */
    public String listaForum() throws Exception {
        forums = forumTransaction.findAll();
        return SUCCESS;
    }

    /**
     * @return action status
     * @throws Exception
     */
    public String loadForum() throws Exception {
        forum = forumTransaction.loadForum(forum.getId());
        return SUCCESS;
    }

    /**
     * @return action status
     * @throws Exception
     */
    public String updateForum() throws Exception {
        forumTransaction.update(forum.getIdForum(), forum);
        return SUCCESS;
    }

    /**
     * @return action status
     * @throws Exception
     */
    public String insertForum() throws Exception {
        forumTransaction.insertForum(forum);
        return SUCCESS;
    }

    /**
     * @return action status
     * @throws Exception
     */
    public String deleteForum() throws Exception {
        if (removeForum != -1) {
            forumTransaction.transferForum(forum, removeForum);
        }
        forumTransaction.deleteForum(forum);
        return SUCCESS;
    }

    public String sortForum() throws Exception {
    	forumTransaction.sortForuns(_categoryId, destOrder, position);
        return SUCCESS;
    }
    
    
    /**
     * @return result
     * @throws Exception
     */
    public String saveForumConfig() throws Exception {
    	forumTransaction.saveConfigForum(forumConfigView);
        return SUCCESS;
    }
    
    public String sortUserRankByForum() throws Exception {
    	forumTransaction.refreshForumUserRank();
    	return SUCCESS;
    }

    
    // ####################################################################
    // View objects accessors
    // ####################################################################

    /**
     * @return Returns the forum.
     */
    public Forum getForum() {
        return forum;
    }

    /**
     * @return Returns the forums.
     */
    public List getForums() {
        return forums;
    }

    /**
     * @return Returns the removeForum.
     */
    public int getRemoveForum() {
        return removeForum;
    }

    /**
     * @return Returns the themes.
     */
    public List getThemes() {
        return themes;
    }

    /**
     * @return Returns the langs.
     */
    public List getLangs() {
        return langs;
    }
	/**
	 * @return Returns the forumConfigView.
	 */
	public ForumConfigView getForumConfigView() {
		return forumConfigView;
	}
	/**
	 * @return Returns the destOrder.
	 */
	public Integer getDestOrder() {
		return destOrder;
	}
	/**
	 * @param destOrder The destOrder to set.
	 */
	public void setDestOrder(Integer destOrder) {
		this.destOrder = destOrder;
	}
	/**
	 * @return Returns the position.
	 */
	public Integer getPosition() {
		return position;
	}
	/**
	 * @param position The position to set.
	 */
	public void setPosition(Integer position) {
		this.position = position;
	}
	/**
	 * @return Returns the btnLang.
	 */
	public List getBtnLang() {
		return btnLang;
	}
}