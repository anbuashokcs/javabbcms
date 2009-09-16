package org.javabb.transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.javabb.dao.entity.IWikiDAO;
import org.javabb.vo.Wiki;

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

/* $Id: WikiTransaction.java,v 1.1 2009/05/11 20:27:03 daltoncamargo Exp $ */
/**
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org
 *         </a> <br>
 */
public class WikiTransaction extends Transaction {

	private IWikiDAO wikiDAO = null;

	public void setWikiDAO(IWikiDAO wikiDAO) {
		this.wikiDAO = wikiDAO;
	}

	public Wiki loadWiki(String word) throws Exception {
		return wikiDAO.loadWikiWord(word);
	}

	public void reloadLuceneWiki() throws Exception {
		wikiDAO.reloadLuceneWiki();
	}

	@SuppressWarnings("unchecked")	
	public List relatedWikiWords(String word) throws Exception {
		List<Wiki> wikies = null;
		Set related = wikiDAO.searchRelatedWiki(word);
		if (related != null) {
			wikies = new ArrayList(related);
		}
		return wikies;
	}

	public List loadAllWikies() throws Exception {
		return wikiDAO.loadAllWikies();
	}

	public Wiki load(Wiki wiki) throws Exception {
		return (Wiki) load(new Wiki(), wiki.getId());
	}

	
	public void update(Wiki wiki) throws Exception {
		wikiDAO.update(wiki);
	}

}