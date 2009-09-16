package org.javabb.interceptor;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.interceptor.Interceptor;

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
 * $Id: ParameterInterceptor.java,v 1.1 2009/05/11 20:27:12 daltoncamargo Exp $
 * @author Ronald Tetsuo Miura
 */
public class ParameterInterceptor implements Interceptor {

    String allowedBeans = "";

    /**
     * @param allowedBeans
     */
    public void setAllowedBeans(String[] allowedBeans) {
        this.allowedBeans = StringUtils.join(allowedBeans, '|');
    }

    /**
     * @see com.opensymphony.xwork.interceptor.Interceptor#intercept(com.opensymphony.xwork.ActionInvocation)
     */
    public String intercept(ActionInvocation invocation) throws Exception {
        String regex = "^(?:" + allowedBeans + ")?\\.?[^.]*$";

        Map parameters = ActionContext.getContext().getParameters();
        Iterator it = parameters.keySet().iterator();
        while (it.hasNext()) {
            String param = (String) it.next();
            if (!param.matches(regex)) {
                it.remove();
            }
        }
        return invocation.invoke();
    }

    /**
     * @see com.opensymphony.xwork.interceptor.Interceptor#destroy()
     */
    public void destroy() {
        // TODO Auto-generated method stub
    }

    /**
     * @see com.opensymphony.xwork.interceptor.Interceptor#init()
     */
    public void init() {
        // TODO Auto-generated method stub
    }
}
