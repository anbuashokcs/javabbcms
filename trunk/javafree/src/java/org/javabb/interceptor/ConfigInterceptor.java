package org.javabb.interceptor;

import org.javabb.infra.Configuration;

import com.opensymphony.webwork.ServletActionContext;
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
 * $Id: ConfigInterceptor.java,v 1.1 2009/05/11 20:27:12 daltoncamargo Exp $
 * @author Dalton Camargo - <a href="mailto:dalton@ag2.com.br">dalton@ag2.com.br </a> <br>
 */
public class ConfigInterceptor extends AroundInterceptor {

    /**
     * @param invocation
     * @throws Exception
     * @see com.opensymphony.xwork.interceptor.AroundInterceptor#before(com.opensymphony.xwork.ActionInvocation)
     */
    protected void before(ActionInvocation invocation) throws Exception {
        Configuration.realPath = ServletActionContext.getServletContext().getRealPath("/");
    }

    /**
     * @param invocation
     * @param result
     * @throws Exception
     * @see com.opensymphony.xwork.interceptor.AroundInterceptor#after(com.opensymphony.xwork.ActionInvocation,
     * java.lang.String)
     */
    protected void after(ActionInvocation invocation, String result) throws Exception {
        //
    }

    /**
     * @see com.opensymphony.xwork.interceptor.Interceptor#intercept(com.opensymphony.xwork.ActionInvocation)
     */
    public String intercept(ActionInvocation invocation) throws Exception {
        before(invocation);
        return invocation.invoke();
    }
}
