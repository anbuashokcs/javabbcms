package org.javabb.dao.hibernate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.javabb.cache.local.WikiSingleCache;
import org.javabb.dao.entity.IWikiDAO;
import org.javabb.lucene.index.Indexer;
import org.javabb.lucene.search.LuceneSearcher;
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

/* $Id: WikiHibernateDAO.java,v 1.1 2009/05/11 20:26:58 daltoncamargo Exp $ */
/**
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org
 *         </a> <br>
 */
public class WikiHibernateDAO extends HibernateDAO implements IWikiDAO {

	private WikiSingleCache wikiCache;

	public void setWikiCache(WikiSingleCache wikiCache) {
		this.wikiCache = wikiCache;
	}

	private LuceneSearcher searcher;
	private Indexer indexer;

	public void setSearcher(LuceneSearcher searcher) {
		this.searcher = searcher;
	}

	public void setIndexer(Indexer indexer) {
		this.indexer = indexer;
	}

	public void update(Wiki wiki) throws Exception {
		Wiki upd = (Wiki) load(new Wiki(), wiki.getId());
		upd.setBody(wiki.getBody());
		upd.setWord(wiki.getWord());
		wikiCache.invalidateCache();
	}

	@SuppressWarnings("unchecked")
	public Wiki loadWikiWord(String word) throws Exception {
		List lst = wikiCache.getLoadWikiWord(word);
		if (lst == null) {
			lst = find(Wiki.class, "o.word = ?", new Object[] { word },
					"o.word DESC", -1, -1);
			wikiCache.setLoadWikiWord(word, lst);
		}
		if (lst != null && lst.size() > 0) {
			return (Wiki) lst.get(0);
		} else {
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	public Set searchRelatedWiki(String query) throws Exception {
		List wikies = wikiCache.getRelatedWikiWord(query);
		if (wikies == null) {
			List ids = searcher.searchSimilarWords(query, new String[] {
					"wikiWord", "wikiBody" }, "wikiId");
			wikies = searchWikiByIds(ids, 0, 30);
			wikiCache.setRelatedWikiWord(query, wikies);
		}
		return new HashSet(wikies);
	}

	@SuppressWarnings("unchecked")
	private List searchWikiByIds(final List ids, final int start,
			final int limit) {

		List result = new ArrayList();
		if (!ids.isEmpty()) {
			int _start = Math.max(0, start);
			int _limit = Math.min(limit, ids.size());
			List subListIds = ids.subList(_start, _limit);
			String hql = "FROM Wiki as wiki where wiki.id in";
			hql += "(";
			if (!subListIds.isEmpty()) {
				for (int i = 0; i < subListIds.size(); i++) {
					hql += subListIds.get(i) + ",";
				}
				hql = hql.substring(0, hql.length() - 1);
				hql += ") ORDER BY wiki.word DESC";
				result = getHibernateTemplate().find(hql);
			}
		}

		return result;

	}

	/**
	 * Reload no Wiki
	 */
	@SuppressWarnings("unchecked")
	public void reloadLuceneWiki() throws Exception {
		List<Wiki> wikis = getHibernateTemplate().find("from Wiki");
		for (Wiki wiki : wikis) {
			if (wiki != null) {
				indexer.deleteWiki(wiki.getId());
				indexer.indexWiki(wiki);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List loadAllWikies() throws Exception {
		List wikies = wikiCache.getLoadAllWikies();
		if (wikies == null) {
			wikies = getHibernateTemplate().find("from Wiki order by word ASC");
			wikiCache.setLoadAllWikies(wikies);
		}
		return wikies;
	}

}