package org.javabb.quartz;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javabb.infra.FeedConstantLists;
import org.javabb.transaction.ForumTransaction;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author Dalton Camargo
 */
public class RefreshFeeds extends QuartzJobBean {

	protected final Log _log = LogFactory.getLog(this.getClass());

	private ForumTransaction forumTransaction;

	public void setForumTransaction(ForumTransaction forumTransaction) {
		this.forumTransaction = forumTransaction;
	}

	@SuppressWarnings("unchecked")
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		_log.info("Initializing RefreshFeeds Job.");

		List feedEmpregos = null;
		List feedInfoblogs = null;
		List feedBomDeBlog = null;

		try {
			feedInfoblogs = forumTransaction
					.feedRefresh("http://www.infoblogs.com.br/xml/rss.action?c=1&type=-1&key=infoblogs");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			feedEmpregos = forumTransaction
					.feedRefresh("http://www.empregonaweb.com/integration/rss-vagas.action");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			feedBomDeBlog = forumTransaction
					.feedRefresh("http://www.bomdeblog.com.br/xml/rss.action?c=-1&type=-1");
		} catch (Exception e) {
			e.printStackTrace();
		}

		FeedConstantLists.feedEmpregos = feedEmpregos;
		FeedConstantLists.feedInfoblogs = feedInfoblogs;
		FeedConstantLists.feedBomDeBlog = feedBomDeBlog;

		_log.info("Finishing RefreshFeeds Job.");
	}

}
