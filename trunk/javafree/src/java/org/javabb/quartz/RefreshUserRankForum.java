package org.javabb.quartz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javabb.transaction.ForumTransaction;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author Dalton Camargo
 */
public class RefreshUserRankForum extends QuartzJobBean {

	protected final Log _log = LogFactory.getLog(this.getClass());
	
	private ForumTransaction forumTransaction;
	public void setForumTransaction(ForumTransaction forumTransaction) {
		this.forumTransaction = forumTransaction;
	}

	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		_log.info("Initializing RefreshUserRankForum Job.");
		try {
			forumTransaction.refreshForumUserRank();
		} catch (Exception e) {
			e.printStackTrace();
		}
		_log.info("Finishing RefreshUserRankForum Job.");
	}

	
}
