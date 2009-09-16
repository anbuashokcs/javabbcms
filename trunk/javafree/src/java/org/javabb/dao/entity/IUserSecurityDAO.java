package org.javabb.dao.entity;

import org.javabb.dao.DAOConstants;

public interface IUserSecurityDAO extends DAOConstants{
	/**
	 * Just for security, this userCode is generated only by JDBC
	 * @param userId
	 * @param hashCode
	 * @throws Exception
	 */
	public void createHashCode(Long userId, String hashCode) throws Exception;

}
