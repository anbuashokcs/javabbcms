package org.javabb.dao.jdbc;

import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javabb.dao.entity.IUserSecurityDAO;
import org.springframework.jdbc.CannotGetJdbcConnectionException;

public class JdbcUserSecurityDAO extends JdbcSuper implements
		IUserSecurityDAO {

	private final Log log = LogFactory.getLog(JdbcUserSecurityDAO.class);
	
	/**
	 * @see IUserSecurityDAO#createHashCode(Long, String)
	 */
	public void createHashCode(Long userId, String hashCode) throws Exception {
    	Statement stmt = null;
    	try {
    		log.debug("Creating new userHashCode to userId " + userId);
    		stmt = this.getConnection().createStatement();
			stmt.executeUpdate("UPDATE jbb_users SET user_code='"+ hashCode +"' where id_user=" + userId);
			log.debug("HashCode created");
		} catch (CannotGetJdbcConnectionException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
	    }finally{
	    	 try {
	    		getConnection().commit();
				stmt.close();
				stmt = null;
			} catch (SQLException e) {}
	    }
		
	}
	
	public void refreshSession(Object obj) {}

}
