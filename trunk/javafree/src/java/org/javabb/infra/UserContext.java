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
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.javabb.vo.Category;
import org.javabb.vo.Forum;
import org.javabb.vo.Topic;
import org.javabb.vo.User;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.ActionContext;

/**
 * This class will hold all transient data for the current user.
 * @author Ronald Tetsuo Miura
 * @author Dalton Camargo
 * @since 14/02/2005
 */
public class UserContext {

    private static final Date DEFAULT_LAST_VISIT_TIMESTAMP = new Date(0);
    /**
     * Key for the user context in the session scope.
     */
    public static final String KEY_USER_CONTEXT = "javabb.user.context";

    private User _user = null;
    private Date _lastVisitTimestamp = null;
    private Set _readTopicIds = new HashSet();
    private Hashtable _timeReadTopic = new Hashtable();
    
    public static Hashtable _topicsInCategory = new Hashtable();
    public static Hashtable _topicsInForum = new Hashtable(); 
    
    

    /**
     * @return user context for the current thread
     */
    public synchronized static UserContext getContext() {
        Map session = ActionContext.getContext().getSession();
        if(session == null){
        	return null;
        }
        
        if (!session.containsKey(KEY_USER_CONTEXT)) {
            session.put(KEY_USER_CONTEXT, new UserContext());
        }
        return (UserContext) session.get(KEY_USER_CONTEXT);
    }

    public boolean isForumRead(Forum forum){
    	try{
    		Long forumId = forum.getId();
    		ArrayList topics = (ArrayList) _topicsInForum.get(forumId);
    		for(int i=0; i < topics.size(); i ++){
    			Topic topic = (Topic)topics.get(i);
    			if(!isTopicRead(topic)){
    				return false;
    			}
    		}
    	}catch(Exception e){
    		return true;
    	}
    	return true;
    }
    
    
    public int getNmbUnreadsInCat(Category cat){
    	int nroReads = 0;
    	try {
			Long catId = cat.getId();
	   		ArrayList topics = (ArrayList) _topicsInCategory.get(catId);
    		for(int i=0; i < topics.size(); i ++){
    			Topic topic = (Topic)topics.get(i);
    			if(!isTopicRead(topic)){
    				nroReads++;
    			}
    		}
		} catch (Exception e) {
			return 0;
		}
		return nroReads;
    }
    
    public void setForumTopics(Long forumId, List topics){
    	_topicsInForum.put(forumId, topics);
    }
    
    public boolean isActiveUnreadForum(){
    	return _topicsInForum.size() == 0;
    }
    
    public void setForumTopic(Long forumId, Topic topic){
    	try{
    		List list = (List) _topicsInForum.get(forumId);
    		list.add(topic);
    	}catch(Exception e){ }
    }
    
    public void setTopicInCat(Long catId, List topics){
    	_topicsInCategory.put(catId, topics);
    }
    
    
    public void setCatTopic(Long catId, Topic topic){
    	try{
    		List list = (List) _topicsInCategory.get(catId);
    		list.add(topic);
    	}catch(Exception e){ }
    }

    /**
     * Mark all topics in some Category as Read
     * @param catId
     */
    public void setAllTopicsInCatAsRead(Long catId){
   		List list = (List) _topicsInCategory.get(catId);
   		if(list != null){
   			for(int i=0; i < list.size(); i++){
   				setTopicRead(((Topic)list.get(i)).getId());
   			}
   		}
    }
    
    /**
     * @return the user logged in this session.
     */
    public User getUser() {
        return _user;
    }

    /**
     * Sets the user logged in this session.
     * @param user
     */
    public void setUser(User user) {
        if (_user == null && user != null) {
            _lastVisitTimestamp = user.getLastVisitTimestamp();
        }
        _user = user;
        ActionContext.getContext().getSession().put("jbbuser", _user);
        ServletActionContext.getRequest().getSession().putValue("jbbuser", _user);
    }

    /**
     * @return true if user is authenticated
     */
    public boolean isAuthenticated() {
        return _user != null;
    }

    public void deauthenticate() {
        _user = null;
        ActionContext.getContext().getSession().remove("jbbuser");
    }

    /**
     * @return timestamp of the last visit
     */
    public Date getLastVisitTimestamp() {
        if (_lastVisitTimestamp == null) {
            return DEFAULT_LAST_VISIT_TIMESTAMP;
        }
        return _lastVisitTimestamp;
    }

	public boolean isTopicRead(Topic topic) {
		try {
			Date dtVisit = (Date) _timeReadTopic.get(topic.getId());

			boolean beforeLastVisit = topic.getLastPostDate().before(getLastVisitTimestamp());
			boolean containsInTopicRead = _readTopicIds.contains(topic.getId());
			boolean lastVisitTopic = (dtVisit != null &&  dtVisit.after(topic.getLastPostDate()));

			return (lastVisitTopic && containsInTopicRead) || beforeLastVisit;
		} catch (RuntimeException e) {
			return true;
		}
	}

    public void setTopicRead(Long topicId) {
        _readTopicIds.add(topicId);
        _timeReadTopic.put(topicId, new Date());
    }

    public void setTopicUnread(Long topicId) {
        _readTopicIds.remove(topicId);
        _timeReadTopic.remove(topicId);
    }
}
