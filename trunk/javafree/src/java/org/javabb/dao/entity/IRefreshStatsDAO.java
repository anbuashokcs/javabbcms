/*
 * Created on 02/07/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.javabb.dao.entity;

import org.javabb.dao.DAOConstants;

/**
 * @author Dalton
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface IRefreshStatsDAO extends DAOConstants {
    public void refreshForum(Long forumId);
    public void refreshTopic(Long topicId);
    public void refreshPost(Long postId);
}
