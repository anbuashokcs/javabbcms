package org.javabb.interceptor;

import java.util.Iterator;
import java.util.Map;

import org.javabb.infra.UserContext;
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
 * $Id: LoginInterceptor.java,v 1.1 2009/05/11 20:27:12 daltoncamargo Exp $
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org </a> <br>
 */
public class LoginInterceptor extends AroundInterceptor {

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
    protected void after(ActionInvocation invocation, String result) throws Exception {
        //
    }

    /**
     * @see com.opensymphony.xwork.interceptor.Interceptor#intercept(com.opensymphony.xwork.ActionInvocation)
     */
    public String intercept(ActionInvocation invocation) throws Exception {
        
        boolean loggedIn = false;
        User user = null;
        ActionContext ctx = ActionContext.getContext();
        Map session = ctx.getSession();
        user = UserContext.getContext().getUser();

        String guest = (String) session.get("jbbguest");

        if ((guest != null) && (user != null)
            && (user.getUser() != null)
            && !"".equals(user.getUser())) {
            loggedIn = true;
            session.remove("jbbUrlBeforeLogin");
        } else {
            loggedIn = false;
            if(!"login".equals(ctx.getName())){
	            Map parameters = ctx.getParameters();
	            String lastURL = "";
	
	            for (Iterator i = parameters.keySet().iterator(); i.hasNext();) {
	                String param = (String) i.next();
	                String[] value = (String[]) parameters.get(param);
	                lastURL += (param + "=" + value[0] + "&");
	            }
	
	            int lastLength = Math.max(0, lastURL.length() - 1);
				lastURL = lastURL.substring(0, lastLength);
	
	            lastURL = ctx.getName() + ".jbb?" + lastURL;
	            //Insert the url on the session
	           	session.put("jbbUrlBeforeLogin", lastURL);
            }
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
