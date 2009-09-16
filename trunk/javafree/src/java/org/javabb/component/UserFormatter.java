package org.javabb.component;

import org.javabb.infra.ApplicationContext;
import org.javabb.transaction.UserTransaction;
import org.javabb.vo.User;
import org.javabb.vo.UserRank;

public class UserFormatter {
	private UserTransaction userTransaction;

	public void setUserTransaction(UserTransaction userTransaction) {
		this.userTransaction = userTransaction;
	}

	public UserRank getUserRank(Long postCount) {
		return userTransaction.getUserRank(postCount);
	}

	public int getUserStatus(User user) {
		try{
			return (ApplicationContext.getContext().isOnLine(user)) ? 1 : 0;
		}catch(Exception e){
			return 0;
		}
	}

}
