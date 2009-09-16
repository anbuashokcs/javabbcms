package org.javabb.quartz;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javabb.transaction.UserTransaction;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author Dalton Camargo
 */
public class UserSecurityCodeRenew extends QuartzJobBean {

	protected final Log _log = LogFactory.getLog(this.getClass());

	
	private UserTransaction userTransaction;
	public void setUserTransaction(UserTransaction userTransaction) {
		this.userTransaction = userTransaction;
	}


	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		_log.info("Initializing Security Code renew Job.");
		try {
			userTransaction.renewUserSecurityCode();
		} catch (Exception e) {
			e.printStackTrace();
		}
		_log.debug("Security Code renew is finished!");
	}

	
}
