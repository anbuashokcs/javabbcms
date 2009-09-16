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
package org.javabb.component;

import java.util.Date;

import org.javabb.vh.SpyVH;

/**
 * $Id: Spy.java,v 1.2 2009/05/12 22:19:19 daltoncamargo Exp $
 * 
 * @author Dalton Camargo
 */
public class Spy {

    public static SpyVH _SPYVH = new SpyVH();

    // public static LinkedHashSet topicViews = new LinkedHashSet();
    public static String topicViews = "";

    public static void addSpyTopic(Long topicId, String topicTitle,
	    String forumName, Long forumId, Long userId, String userName,
	    int admin, String url, Date topicDate) {
	SpyVH spy = new SpyVH();
	spy.setTopicId(topicId);
	spy.setTopicTitle(topicTitle);
	spy.setForumName(forumName);
	spy.setForumId(forumId);
	spy.setUrl(url);
	spy.setTopicDate(topicDate);
	spy.setUserId(userId);
	spy.setUserName(userName);

	_SPYVH = spy;
    }

}
