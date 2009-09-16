package org.javabb.interceptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javabb.action.infra.ActionSuper;

import com.opensymphony.xwork.Action;
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
 * $Id: ExceptionInterceptor.java
 * @author Lucas Teixeira - <a href="mailto:lucas@javabb.org">lucas@javabb.org </a> <br>
 */
public class ExceptionInterceptor implements Interceptor {

    protected final Log log = LogFactory.getLog(this.getClass());
    
    public void destroy() {}
    public void init() {}

    public String intercept(ActionInvocation actionInvocation) throws Exception {
        //log.info("Interceptor de exception ativado");
        ActionSuper action = (ActionSuper) actionInvocation.getAction();
        try {
            return actionInvocation.invoke();
        } catch (Exception e) {
            action.addActionError("Error: " + e.getMessage());
            log.error(e.getMessage(), e);
            return Action.ERROR;
        }
    }
}
