package org.javabb.transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.javabb.dao.entity.INoticiasDAO;
import org.javabb.dao.entity.IWikiDAO;
import org.javabb.infra.CacheUtils;
import org.javabb.infra.ConfigurationFactory;
import org.javabb.infra.Paging;
import org.javabb.infra.replacer.WordStringReplacer;
import org.javabb.vo.Comment;
import org.javabb.vo.Noticias;

public class NoticiasTransaction extends Transaction {

	private INoticiasDAO noticiasDAO;
	private IWikiDAO wikiDAO;

	private WordStringReplacer replacer = null;

	public void setNoticiasDAO(INoticiasDAO noticiasDAO) {
		this.noticiasDAO = noticiasDAO;
	}

	public void setWikiDAO(IWikiDAO wikiDAO) {
		this.wikiDAO = wikiDAO;
	}

	public List<Noticias> findDestaquesHome(int page, int itens)
			throws Exception {
		return noticiasDAO.findByDestaques(page, itens);
	}

	public NoticiasTransaction() {
		replacer = new WordStringReplacer();
	}

	/**
	 * Busca uma lista de noticias pendentes
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<Noticias> findPendentes(Integer page) throws Exception {
		int itemsPerPage = 20;
		long postCountInTopic = noticiasDAO.countNoticias(0);
		int pageCount = Paging.getNroPages(itemsPerPage, postCountInTopic);
		Paging.setPageList(page, pageCount);

		return noticiasDAO.findByPendentes(page, 20);
	}

	/**
	 * Carrega uma determinada noticia
	 * 
	 * @param id
	 * @param countRead
	 *            - true para marcar a leitura
	 * @return
	 * @throws Exception
	 */
	public Noticias loadNoticia(Long id, boolean countRead) throws Exception {
		Noticias noticia = noticiasDAO.load(id);
		if (noticia != null && countRead) {
			Integer notView = noticia.getViews();
			Integer views = (notView != null) ? notView + 1 : 1;
			countViewsOnCache(noticia.getNotId(), views);
		}

		return noticia;
	}

	/**
	 * Persite as visualizacoes de uma determinada noticia. Um agendador ira
	 * persistir essas visualizacoes de tempos em tempos
	 * 
	 * @param id
	 * @param viewCount
	 */
	@SuppressWarnings("unchecked")
	public void countViewsOnCache(Long id, Integer viewCount) {
		if (CacheUtils._NOTICIA_VIEWS_COUNT_CACHE == null) {
			CacheUtils._NOTICIA_VIEWS_COUNT_CACHE = new HashMap();
		}

		Integer view = CacheUtils.getViewNoticiaById(id);
		if (view != null) {
			view += 1;
			CacheUtils._NOTICIA_VIEWS_COUNT_CACHE.put(id, view);
		} else {
			CacheUtils._NOTICIA_VIEWS_COUNT_CACHE.put(id, viewCount);
		}
	}

	/**
	 * Persist all user cache
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void persistNoticiasCount(HashMap noticias) throws Exception {
		ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
		try {
			lock.writeLock().lock();
			noticiasDAO.persistNewsCache(noticias);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.writeLock().unlock();
		}
	}

	public void addNoticia(Noticias noticia) throws Exception {
		noticiasDAO.insertNoticia(noticia);
	}

	/**
	 * Lista as ultimas noticias
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Noticias> findByNoticias(int init, int limit) throws Exception {
		return noticiasDAO.findAllNoticias(init, limit);
	}

	public List<Noticias> findAllNoticias(Integer page) throws Exception {
		// PAGING ** Obtendo informações
		int itemsPerPage = ConfigurationFactory.getConf().postsPage.intValue();
		long postCountInTopic = noticiasDAO.countAllNoticias();
		int pageCount = Paging.getNroPages(itemsPerPage, postCountInTopic);
		Paging.setPageList(page, pageCount);

		return noticiasDAO.findAllNoticias(page, itemsPerPage);
	}

	public void excluirNoticia(Long id) throws Exception {
		noticiasDAO.excluirNoticia(id);
	}

	public void updateNoticia(Long id, Noticias noticia) throws Exception {
		noticiasDAO.updateNoticia(id, noticia);
	}

	public void approveNoticia(Long id) throws Exception {
		Noticias noticia = loadNoticia(id, false);
		noticia.setActive(1);
		noticia.setPublishDate(new Date());
		noticiasDAO.invalidateNoticiasCache();
	}

	public void reproveNoticia(Long id) throws Exception {
		Noticias noticia = loadNoticia(id, false);
		noticia.setActive(0);
	}

	@SuppressWarnings("unchecked")
	public List<Noticias> searchRelatedNoticias(String query, Long notId)
			throws Exception {
		List<Noticias> noticias = null;
		Set related = noticiasDAO.searchRelatedNoticias(query, notId);
		if (related != null) {
			noticias = new ArrayList(related);
		}

		return noticias;
	}

	public Long addComment(Comment comment) throws Exception {
		return (Long) noticiasDAO.insertComment(comment);
	}

	public List<Comment> findCommentByNotId(Long notId) throws Exception {
		return noticiasDAO.findCommentByNotId(notId);
	}

	public void deleteComment(Comment comment) throws Exception {
		delete(comment);
		noticiasDAO.invalidateNoticiasCache();
	}

	public void luceneIndexNoticias() throws Exception {
		noticiasDAO.indexNoticiasInLucene();
	}

	public List<Comment> findLastComments(int limit) throws Exception {
		return noticiasDAO.findLastComments(limit);
	}

}
