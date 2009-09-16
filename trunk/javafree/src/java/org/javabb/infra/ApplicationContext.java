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

package org.javabb.infra;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javabb.vo.User;

import com.opensymphony.clickstream.Clickstream;
import com.opensymphony.clickstream.ClickstreamListener;
import com.opensymphony.xwork.ActionContext;

/**
 * @author Ronald Tetsuo Miura
 * @since 14/02/2005
 */
public class ApplicationContext {
    private static final Log LOG = LogFactory.getLog(ApplicationContext.class);

    private static final String KEY_APPLICATION_CONTEXT = "javabb.application.context";

    /**
     * @return application context for the current thread
     */
    public synchronized static ApplicationContext getContext() {
        try {
            Map application = ActionContext.getContext().getApplication();
            if (!application.containsKey(KEY_APPLICATION_CONTEXT)) {
                application.put(KEY_APPLICATION_CONTEXT, new ApplicationContext());
            }
            return (ApplicationContext) application.get(KEY_APPLICATION_CONTEXT);
        } catch (RuntimeException e) {
            return null;
        }
    }

    /**
     * @return user list
     */
    public synchronized Collection getOnlineGuests() {
        try {
            Map clickMap = (Map) ActionContext.getContext()
                .getApplication()
                .get(ClickstreamListener.CLICKSTREAMS_ATTRIBUTE_KEY);
            
            Map clickstreams = new TreeMap(clickMap);
            
            UserContext.getContext();

            synchronized (clickstreams) {
                // make copy for thread safety of the map
                // clickstreams = new HashMap(clickstreams);

                Collection users = new ArrayList();
                Iterator it = clickstreams.keySet().iterator();
                while (it.hasNext()) {
                    try {
						String key = (String) it.next();
						Clickstream stream = (Clickstream) clickstreams.get(key);
						HttpSession session = stream.getSession();
						UserContext userContext = (UserContext) session.getAttribute(UserContext.KEY_USER_CONTEXT);
						if(userContext != null){
						    User user = userContext.getUser();
						    if (user == null || StringUtils.isBlank(user.getUser())) {
						        users.add(user);
						    }
						} else{
							users.add(null);
						}
					} catch (IllegalStateException ex) { }
                }
                return users;
            }
        } catch (NullPointerException e) {
            return null;
        }
    }

    /**
     * @return user list
     */
    public synchronized Collection getOnlineRegisteredUsers() {
        Map clickMap = (Map) ActionContext.getContext()
            .getApplication()
            .get(ClickstreamListener.CLICKSTREAMS_ATTRIBUTE_KEY);
        
        Map clickstreams = new TreeMap(clickMap);

        synchronized (clickstreams) {
            // make copy for thread safety of the map
            // clickstreams = new HashMap(clickstreams);

            Collection users = new HashSet();
			try {
				Iterator it = clickstreams.keySet().iterator();
				while (it.hasNext()) {
				    try {
						String key = (String) it.next();
						Clickstream stream = (Clickstream) clickstreams.get(key);
						HttpSession session = stream.getSession();
						UserContext userContext = (UserContext) session.getAttribute(UserContext.KEY_USER_CONTEXT);
						if(userContext != null){
						    User user = userContext.getUser();
						    if (user != null && StringUtils.isNotBlank(user.getUser())) {
						        users.add(user);
						    }
						} else {
							users.add(null);
						}
				    } catch (IllegalStateException ex) { }
				}
			} catch (RuntimeException e) { }
            return users;
        }
    }
    
    /**
     * If some user is Online
     * @param user
     * @return
     */
    public boolean isOnLine(User user){
    	HashSet users = (HashSet) getOnlineRegisteredUsers();
    	Iterator it = users.iterator();
    	if(users != null && !users.isEmpty()){
    		while (it.hasNext()) {
    			User hashUser = (User)it.next();
    			try {
					if(hashUser.getId().intValue() == user.getId().intValue()){
						return true;
					}
				} catch (RuntimeException e) { }
    		}
    	}
    	return false;
    }
    
}
