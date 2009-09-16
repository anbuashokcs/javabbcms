package org.javabb.interceptor;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javabb.infra.ConfigurationFactory;

import com.opensymphony.webwork.ServletActionContext;
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
 * $Id: DelayInterceptor.java,v 1.1 2009/05/11 20:27:12 daltoncamargo Exp $
 * 
 * @author Dalton Camargo - 
 */
public class DelayInterceptor extends AroundInterceptor {

    protected final Log _log = LogFactory.getLog(this.getClass());
    private boolean _isFlood = false;


    /**
     * @param invocation
     * @throws Exception
     * @see com.opensymphony.xwork.interceptor.AroundInterceptor#before(com.opensymphony.xwork.ActionInvocation)
     */
    protected void before(ActionInvocation invocation) throws Exception {
        Map session = ActionContext.getContext().getSession();

        boolean createDelay = false;
        Long userLastVisit = (Long) session.get("user_last_visit");
        if (session.get("user_last_visit") == null) {
            userLastVisit = new Long(System.currentTimeMillis());
            session.put("user_last_visit", userLastVisit);
        } else {
            createDelay = true;
        }

        String userIP = (String) session.get("user_ip");
        if (userIP == null) {
            userIP = ServletActionContext.getRequest().getRemoteAddr();
            session.put("user_ip", userIP);
        }

        if (userIP.equals(ServletActionContext.getRequest().getRemoteAddr())) {
            long currTime = System.currentTimeMillis();
            long timeDiff = currTime - userLastVisit.longValue();
            if (createDelay && (timeDiff < 1500)) {
            	Integer nmbFloods = (Integer) session.get("user_number_floods");
            	if(nmbFloods != null && nmbFloods.intValue() > 4){
            		_log.debug("LAST FLOOD:  |" + userLastVisit.longValue());
                    _log.debug("IP FLOODING: |" + ServletActionContext.getRequest().getRemoteAddr());
                    _isFlood = true;
            	} else {
            		if(nmbFloods == null){
            			session.put("user_number_floods", new Integer(1));
            		} else {
            			session.put("user_number_floods", new Integer(nmbFloods.intValue() + 1));
            		}
            	}
            } else {
            	//User doesn't flooded
            	session.put("user_number_floods", null);
            }
        }
        //updating the session
        session.put("user_last_visit", new Long(System.currentTimeMillis()));
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
        //
    }

    /**
     * @see com.opensymphony.xwork.interceptor.Interceptor#intercept(com.opensymphony.xwork.ActionInvocation)
     */
    public String intercept(ActionInvocation invocation) throws Exception {
        before(invocation);
        if (_isFlood && !"0".equals(ConfigurationFactory.getConf().floodControl)) {
            _isFlood = false;
            return "flood_control";
        }
        return invocation.invoke();
    }
}