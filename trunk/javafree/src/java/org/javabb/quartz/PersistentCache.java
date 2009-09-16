package org.javabb.quartz;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javabb.infra.CacheUtils;
import org.javabb.transaction.BannerTransaction;
import org.javabb.transaction.NoticiasTransaction;
import org.javabb.transaction.TopicTransaction;
import org.javabb.transaction.UserTransaction;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author Dalton Camargo
 */
public class PersistentCache extends QuartzJobBean {

	protected final Log _log = LogFactory.getLog(this.getClass());

	private TopicTransaction topicTransaction;
	private UserTransaction userTransaction;
	private NoticiasTransaction noticiasTransaction;
	private BannerTransaction bannerTransaction;

	public void setTopicTransaction(TopicTransaction topicTransaction) {
		this.topicTransaction = topicTransaction;
	}

	public void setUserTransaction(UserTransaction userTransaction) {
		this.userTransaction = userTransaction;
	}

	public void setNoticiasTransaction(NoticiasTransaction noticiasTransaction) {
		this.noticiasTransaction = noticiasTransaction;
	}

	public void setBannerTransaction(BannerTransaction bannerTransaction) {
		this.bannerTransaction = bannerTransaction;
	}

	@SuppressWarnings("unchecked")
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		_log.info("Initializing Persist Cache Counts.");
		try {

			HashMap cacheIds = CacheUtils._TOPIC_VIEWS_COUNT_CACHE;
			CacheUtils._TOPIC_VIEWS_COUNT_CACHE = null;
			topicTransaction.persistViewCacheInTable(cacheIds);

			HashMap bannerIds = CacheUtils._VIEWS_BANNER;
			CacheUtils._VIEWS_BANNER = null;
			bannerTransaction.persistViewCacheInTable(bannerIds);

			HashMap userLastVisit = CacheUtils._LAST_VISIT_USER;
			CacheUtils._LAST_VISIT_USER = null;
			if (userLastVisit != null) {
				userTransaction.persistLastTimeVisitCache(userLastVisit);
			}

			HashMap noticias = CacheUtils._NOTICIA_VIEWS_COUNT_CACHE;
			CacheUtils._NOTICIA_VIEWS_COUNT_CACHE = null;
			if (noticias != null) {
				noticiasTransaction.persistNoticiasCount(noticias);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		_log.info("Finishing Persist Cache Counts");
	}

}
