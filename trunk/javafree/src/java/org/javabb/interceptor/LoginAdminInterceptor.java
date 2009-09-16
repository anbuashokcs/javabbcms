package org.javabb.interceptor;

import org.javabb.infra.UserContext;
import org.javabb.transaction.UserTransaction;
import org.javabb.vo.User;

import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.interceptor.AroundInterceptor;

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

/**
 * $Id: LoginAdminInterceptor.java,v 1.1 2009/05/11 20:27:12 daltoncamargo Exp $
 * 
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org
 *         </a> <br>
 * @author Ronald Tetsuo Miura
 */
public class LoginAdminInterceptor extends AroundInterceptor {

	private UserTransaction userTransaction;

	/**
	 * @param userTransaction
	 *            the new userTransaction value
	 */
	public void setUserTransaction(UserTransaction userTransaction) {
		this.userTransaction = userTransaction;
	}

	/**
	 * @param invocation
	 * @throws Exception
	 * @see com.opensymphony.xwork.interceptor.AroundInterceptor#before(com.opensymphony.xwork.ActionInvocation)
	 */
	protected void before(ActionInvocation invocation) throws Exception {

	}

	/**
	 * @param invocation
	 * @param result
	 * @throws Exception
	 * @see com.opensymphony.xwork.interceptor.AroundInterceptor#after(com.opensymphony.xwork.ActionInvocation,
	 *      java.lang.String)
	 */
	protected void after(ActionInvocation invocation, String result)
			throws Exception {
	}

	/**
	 * @param invocation
	 * @return result
	 * @throws Exception
	 * @see com.opensymphony.xwork.interceptor.Interceptor#intercept(com.opensymphony.xwork.ActionInvocation)
	 */
	public String intercept(ActionInvocation invocation) throws Exception {
		boolean loggedIn = false;
		User user = null;
		ActionContext ctx = ActionContext.getContext();
		user = UserContext.getContext().getUser();
		if ((user != null) && (user.getUser() != null)
				&& (user.getAdmin() != null)
				&& (user.getAdmin().intValue() == 1)) {
			// Check if the user is the same of there in database.
			user = userTransaction.getUser(user.getId());
			if ((user.getAdmin() != null) && (user.getAdmin().intValue() == 1)) {
				loggedIn = true;
			} else {
				loggedIn = false;
			}
		} else {
			loggedIn = false;
		}

		if (loggedIn == false) {
			// Logging
			if (UserContext.getContext().isAuthenticated()) {
				log.debug("Action name: " + ctx.getName());
				log.debug("Admin Denied: "
						+ UserContext.getContext().getUser().getUser());
			}

			// Envia o usu�rio para a tela de login
			return Action.LOGIN;
		}

		// Usu�rio logado, deixa ele acessar o link desejado
		String result = invocation.invoke();
		after(invocation, result);

		return result;
	}
}