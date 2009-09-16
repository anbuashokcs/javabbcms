package org.javabb.infra;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.javabb.vo.Noticias;
import org.javabb.vo.Topic;

public class CacheUtils {

	@SuppressWarnings("unchecked")
	public static HashMap _TOPIC_VIEWS_COUNT_CACHE = new HashMap();

	@SuppressWarnings("unchecked")
	public static HashMap _TOPIC_LIST_RELATED_TOPICS = new HashMap();

	@SuppressWarnings("unchecked")
	public static HashMap _NOTICIA_VIEWS_COUNT_CACHE = new HashMap();

	@SuppressWarnings("unchecked")
	public static HashMap _LAST_VISIT_USER = new HashMap();

	@SuppressWarnings("unchecked")
	public static HashMap _VIEWS_BANNER = new HashMap();

	@SuppressWarnings("unchecked")
	public static Integer getViewBannerId(Long bannerId) {
		if (CacheUtils._VIEWS_BANNER == null) {
			CacheUtils._VIEWS_BANNER = new HashMap();
		}

		if (bannerId == null) {
			return null;
		}
		return (Integer) CacheUtils._VIEWS_BANNER.get(bannerId);
	}

	@SuppressWarnings("unchecked")
	public static Integer getViewTopicById(Long topicId) {
		if (CacheUtils._TOPIC_VIEWS_COUNT_CACHE == null) {
			CacheUtils._TOPIC_VIEWS_COUNT_CACHE = new HashMap();
		}

		if (topicId == null) {
			return null;
		}
		return (Integer) CacheUtils._TOPIC_VIEWS_COUNT_CACHE.get(topicId);
	}

	@SuppressWarnings("unchecked")
	public static Topic fillTopicIfCached(Topic t) {
		if (CacheUtils._TOPIC_VIEWS_COUNT_CACHE == null) {
			CacheUtils._TOPIC_VIEWS_COUNT_CACHE = new HashMap();
		}

		if (t != null) {
			Integer view = getViewTopicById(t.getId());
			if (view != null) {
				t.setVisualizacoes(view);
			}
		}
		return t;
	}

	@SuppressWarnings("unchecked")
	public static Integer getViewNoticiaById(Long id) {
		if (CacheUtils._NOTICIA_VIEWS_COUNT_CACHE == null) {
			CacheUtils._NOTICIA_VIEWS_COUNT_CACHE = new HashMap();
		}

		if (id == null) {
			return null;
		}
		return (Integer) CacheUtils._NOTICIA_VIEWS_COUNT_CACHE.get(id);
	}

	@SuppressWarnings("unchecked")
	public static Noticias fillNoticiaIfCached(Noticias noticia) {
		if (CacheUtils._NOTICIA_VIEWS_COUNT_CACHE == null) {
			CacheUtils._NOTICIA_VIEWS_COUNT_CACHE = new HashMap();
		}

		if (noticia != null) {
			Integer view = getViewNoticiaById(noticia.getNotId());
			if (view != null) {
				noticia.setViews(view);
			}
		}
		return noticia;
	}

	/**
	 * Cache of related topics
	 * 
	 * @param origTopicId
	 * @param relatedTopics
	 */
	@SuppressWarnings("unchecked")
	public static void putRelatedTopics(Long origTopicId, List relatedTopics) {
		if (CacheUtils._TOPIC_LIST_RELATED_TOPICS == null) {
			CacheUtils._TOPIC_LIST_RELATED_TOPICS = new HashMap();
		}
		CacheUtils._TOPIC_LIST_RELATED_TOPICS.put(origTopicId, relatedTopics);
	}

	/**
	 * get an related topic according the topic id in the parameter
	 * 
	 * @param topicId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List getRelatedTopicsByTopicId(Long topicId) {
		if (CacheUtils._TOPIC_LIST_RELATED_TOPICS == null) {
			CacheUtils._TOPIC_LIST_RELATED_TOPICS = new HashMap();
		}
		return (List) CacheUtils._TOPIC_LIST_RELATED_TOPICS.get(topicId);
	}

	/**
	 * Invalidate a cache using topicId
	 * 
	 * @param topicId
	 */
	public static void invalidateRelatedCacheTopicById(Long topicId) {
		if (CacheUtils._TOPIC_LIST_RELATED_TOPICS != null) {
			CacheUtils._TOPIC_LIST_RELATED_TOPICS.remove(topicId);
		}
	}

	@SuppressWarnings("unchecked")
	public static void setLastVisitUser(Long userId, Date visit) {
		if (CacheUtils._LAST_VISIT_USER == null) {
			CacheUtils._LAST_VISIT_USER = new HashMap();
		}
		if (CacheUtils._LAST_VISIT_USER.get(userId) == null) {
			CacheUtils._LAST_VISIT_USER.put(userId, visit);
		}
	}

}
