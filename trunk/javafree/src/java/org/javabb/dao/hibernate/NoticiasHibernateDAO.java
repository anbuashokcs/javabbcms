package org.javabb.dao.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.javabb.dao.entity.INoticiasDAO;
import org.javabb.infra.UserContext;
import org.javabb.lucene.index.Indexer;
import org.javabb.lucene.search.LuceneSearcher;
import org.javabb.vo.Comment;
import org.javabb.vo.Noticias;
import org.springframework.beans.BeanUtils;

public class NoticiasHibernateDAO extends HibernateDAO implements INoticiasDAO {

	private LuceneSearcher searcher;
	private Indexer indexer;

	public void setSearcher(LuceneSearcher searcher) {
		this.searcher = searcher;
	}

	public void setIndexer(Indexer indexer) {
		this.indexer = indexer;
	}

	public void invalidateNoticiasCache() throws Exception {
		noticiasCache.invalidateCache();
	}

	@SuppressWarnings("unchecked")
	public List<Noticias> findByDestaques(int pageNumber, int itemsPerPage)
			throws Exception {
		List find = noticiasCache.getFindByDestaques(pageNumber, itemsPerPage);
		if (find == null) {
			find = find(Noticias.class, "o.active=? and o.home = ?",
					new Object[] { 1, 1 }, "o.publishDate DESC", pageNumber,
					itemsPerPage);
			noticiasCache.setFindByDestaques(find, pageNumber, itemsPerPage);
		}
		return find;
	}

	@SuppressWarnings("unchecked")
	public List<Noticias> findByPendentes(int pageNumber, int itemsPerPage)
			throws Exception {
		List find = find(Noticias.class, "o.active=? ", new Object[] { 0 },
				"o.publishDate DESC", pageNumber, itemsPerPage);
		return find;
	}

	public int countNoticias(Integer active) throws Exception {
		return countRowsWhere(Noticias.class, "o.notId", "o.active=?",
				new Object[] { active });
	}

	public int countAllNoticias() throws Exception {
		return countRows(Noticias.class, "notId");
	}

	public Noticias load(Long id) throws Exception {
		return (Noticias) load(new Noticias(), id);
	}

	public void insertNoticia(Noticias noticia) throws Exception {
		getHibernateTemplate().save(noticia);
		indexer.indexNoticia(noticia);
		noticiasCache.invalidateCache();
	}

	public Long insertComment(Comment comment) throws Exception {
		noticiasCache.invalidateCache();
		return (Long) getHibernateTemplate().save(comment);
	}

	public void updateNoticia(Long id, Noticias noticia) throws Exception {
		Noticias toUpdate = load(id);
		Long userId = toUpdate.getUser().getId();

		if (UserContext.getContext().getUser().getAdmin() == 2
				&& UserContext.getContext().getUser().getId().intValue() != userId
						.intValue()) {
			throw new Exception(
					"Voce nao pode editar uma noticia de outro usuario!");
		}

		Date published = toUpdate.getPublishDate();
		BeanUtils.copyProperties(noticia, toUpdate);
		toUpdate.setPublishDate(published);
		getHibernateTemplate().saveOrUpdate(toUpdate);

		indexer.updateNoticia(noticia);

		noticiasCache.invalidateCache();
	}

	public void excluirNoticia(Long id) throws Exception {
		Noticias not = (Noticias) load(Noticias.class, id);
		getHibernateTemplate().deleteAll(not.getComments());
		getHibernateTemplate().delete(not);

		indexer.deleteNoticia(id);

		noticiasCache.invalidateCache();
	}

	@SuppressWarnings("unchecked")
	public List<Noticias> findByNoticias(int pageNumber, int itemsPerPage)
			throws Exception {
		List find = noticiasCache.getFindByNoticias(pageNumber, itemsPerPage);
		if (find == null) {
			find = find(Noticias.class,
					"o.active=? and (o.home != ? or o.home is null)",
					new Object[] { 1, 1 }, "o.publishDate DESC", pageNumber,
					itemsPerPage);
			noticiasCache.setFindByNoticias(find, pageNumber, itemsPerPage);
		}
		return find;
	}

	@SuppressWarnings("unchecked")
	public List<Noticias> findAllNoticias(int pageNumber, int itemsPerPage)
			throws Exception {
		List find = noticiasCache.getFindAllNoticias(pageNumber, itemsPerPage);
		if (find == null) {
			find = find(Noticias.class, "o.active=? ", new Object[] { 1 },
					"o.publishDate DESC", pageNumber, itemsPerPage);
			noticiasCache.setFindAllNoticias(find, pageNumber, itemsPerPage);
		}
		return find;
	}

	/**
	 * This method returns a set of Related News with the String in the
	 * parameter
	 * 
	 * @param query
	 *            - Query to be searched into lucene
	 */
	@SuppressWarnings("unchecked")
	public Set searchRelatedNoticias(String query, Long notId) throws Exception {
		List topics = noticiasCache.getSearchRelatedNoticias(query, notId);
		if (topics == null) {
			List ids = searcher.searchSimilarWords(query, new String[] {
					"notTitle", "notBody" }, "notId");
			topics = searchNoticiasByIds(ids, notId, 0, 10);
			noticiasCache.setSearchRelatedNoticias(topics, query, notId);
		}
		return new HashSet(topics);
	}

	@SuppressWarnings("unchecked")
	private List searchNoticiasByIds(final List ids, final Long notId,
			final int start, final int limit) {
		List result = new ArrayList();
		if (!ids.isEmpty()) {
			int _start = Math.max(0, start);
			int _limit = Math.min(limit, ids.size());
			List subListIds = ids.subList(_start, _limit);
			String hql = "FROM Noticias as noticia where noticia.notId !="
					+ notId + "   AND noticia.active = 1"
					+ " AND noticia.notId in";
			hql += "(";
			if (!subListIds.isEmpty()) {
				for (int i = 0; i < subListIds.size(); i++) {
					hql += subListIds.get(i) + ",";
				}
				hql = hql.substring(0, hql.length() - 1);
				hql += ") ORDER BY noticia.notId DESC";
				// result = session.find(hql);
				result = getHibernateTemplate().find(hql);
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<Comment> findCommentByNotId(Long notId) throws Exception {
		List find = find(Comment.class, "o.noticias.notId = ? ",
				new Object[] { notId }, " o.id ASC", -1, -1);
		return find;
	}

	@SuppressWarnings("unchecked")
	public List<Comment> findLastComments(int items) throws Exception {
		List comments = noticiasCache.getFindLastComments(items);
		if (comments == null) {
			comments = find(Comment.class, "o.noticias.notId != ? ",
					new Object[] { 0L }, " o.id DESC", 1, items);
			noticiasCache.setFindLastComments(comments, items);
		}
		return comments;
	}

	/**
	 * Index noticias on Lucene
	 * 
	 * @param notId
	 * @throws Exception
	 */
	public void indexNoticiasInLucene() throws Exception {
		List<Noticias> nots = findAllNoticias(-1, -1);
		for (Noticias noticia : nots) {
			if (noticia != null) {
				indexer.deleteNoticia(noticia.getNotId());
				indexer.indexNoticia(noticia);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void persistNewsCache(HashMap noticias) throws Exception {
		Iterator it = noticias.keySet().iterator();
		while (it.hasNext()) {
			Long notId = (Long) it.next();
			Integer view = (Integer) noticias.get(notId);
			Noticias not = load(notId);
			not.setViews(view);
		}
	}
}
