package org.javabb.dao.entity;

import java.util.Date;

import org.javabb.dao.DAOConstants;

public interface IUserRankDAO extends DAOConstants {
	
	public void refreshUserRankByForum(Long forumId);
	public void cleanAllUserRank();

}
