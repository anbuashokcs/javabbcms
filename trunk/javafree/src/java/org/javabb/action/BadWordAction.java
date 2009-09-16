package org.javabb.action;

import java.util.ArrayList;
import java.util.List;

import org.javabb.action.infra.BaseAction;
import org.javabb.transaction.BadWordTransaction;
import org.javabb.vo.BadWord;

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

/* $Id: BadWordAction.java,v 1.1 2009/05/11 20:26:52 daltoncamargo Exp $ */
/**
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org
 *         </a> <br>
 * @author Ronald Tetsuo Miura
 */
public class BadWordAction extends BaseAction {
	private BadWord badWord;

	private BadWordTransaction badWordTransaction;

	private List badWords = new ArrayList();

	private Long id;

	private String word;

	private String replacement;

	// ####################################################################
	// Parameters
	// ####################################################################

	/**
	 * @param id
	 *            the new id value
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param replacement
	 *            the new replacement value
	 */
	public void setReplacement(String replacement) {
		this.replacement = replacement;
	}

	/**
	 * @param word
	 *            the new word value
	 */
	public void setWord(String word) {
		this.word = word;
	}

	// ####################################################################
	// Dependencies
	// ####################################################################

	/**
	 * @param badWordTransaction
	 *            the new badWordTransaction value
	 */
	public void setBadWordTransaction(BadWordTransaction badWordTransaction) {
		this.badWordTransaction = badWordTransaction;
	}

	// ####################################################################
	// Actions
	// ####################################################################

	/**
	 * @return result
	 * @throws Exception
	 */
	public String listBadWords() throws Exception {
		badWords = badWordTransaction.listAll();
		return SUCCESS;
	}

	/**
	 * @return result
	 * @throws Exception
	 */
	public String loadBadWord() throws Exception {
		badWord = badWordTransaction.getBadWord(id);
		badWords = badWordTransaction.listAll();
		return SUCCESS;
	}

	/**
	 * @return result
	 * @throws Exception
	 */
	public String delBadWord() throws Exception {
		badWordTransaction.delete(new BadWord(id));
		return SUCCESS;
	}

	/**
	 * @return result
	 * @throws Exception
	 */
	public String editBadWord() throws Exception {
		badWordTransaction.update(new BadWord(id, word, replacement));
		return SUCCESS;
	}

	/**
	 * @return result
	 * @throws Exception
	 */
	public String addBadWord() throws Exception {
		badWordTransaction.save(new BadWord(word, replacement));
		return SUCCESS;
	}

	// ####################################################################
	// View objects accessors
	// ####################################################################

	/**
	 * @return Returns the badWord.
	 */
	public BadWord getBadWord() {
		return badWord;
	}

	/**
	 * @return Returns the badWords.
	 */
	public List getBadWords() {
		return badWords;
	}

}