package org.javabb.dao.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.javabb.dao.DAOConstants;
import org.javabb.vo.Comment;
import org.javabb.vo.Noticias;

public interface INoticiasDAO extends DAOConstants {
	
    	public void invalidateNoticiasCache() throws Exception;
	public List<Noticias> findByDestaques(int pageNumber, int itemsPerPage) throws Exception;
	public List<Noticias> findByPendentes(int pageNumber, int itemsPerPage) throws Exception;
	public int countNoticias(Integer active) throws Exception;
	public int countAllNoticias() throws Exception;
	public Noticias load(Long id) throws Exception;
	public void excluirNoticia(Long id) throws Exception;
	public List<Noticias> findByNoticias(int pageNumber, int itemsPerPage) throws Exception;
	public List<Noticias> findAllNoticias(int pageNumber, int itemsPerPage) throws Exception;
	public void updateNoticia(Long id, Noticias noticia) throws Exception;
	public void insertNoticia(Noticias noticia) throws Exception;
	public Long insertComment(Comment comment) throws Exception;
	public Set searchRelatedNoticias(String query, Long notId) throws Exception;
	public List<Comment> findCommentByNotId(Long notId) throws Exception;
	public List<Comment> findLastComments(int items) throws Exception;
	public void indexNoticiasInLucene() throws Exception;
	public void persistNewsCache(HashMap countNews) throws Exception;
}
