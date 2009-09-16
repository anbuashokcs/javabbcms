package org.javabb.interceptor;

import org.javabb.infra.UserContext;
import org.javabb.transaction.UserTransaction;
import org.javabb.vo.User;

import com.opensymphony.xwork.Action;
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
 * $Id: LoginModeradorInterceptor.java,v 1.1 2009/06/22 22:54:10 daltoncamargo Exp $
 * 
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org
 *         </a> <br>
 * @author Ronald Tetsuo Miura
 */
public class LoginModeradorInterceptor extends AroundInterceptor {

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
		user = UserContext.getContext().getUser();
		if ((user != null) && (user.getUser() != null)) {
		    // Check if the user is the same of there in database.
		    if ((user.getAdmin() != null && user.getAdmin().intValue() == 1) 
			    || (user.getAdmin() != null && user.getAdmin().intValue() == 2)) {
			loggedIn = true;
		    } else {
			loggedIn = false;
		    }
		} else {
		    loggedIn = false;
		}

		if (loggedIn == false) {
		    // Envia o usuário para a tela de login
		    return Action.LOGIN;
		}

		// Usuário logado, deixa ele acessar o link desejado
		String result = invocation.invoke();
		after(invocation, result);

		return result;
	}
}