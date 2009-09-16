package org.javabb.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.javabb.dao.entity.IPostFileDAO;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class JdbcPostFileDAO extends JdbcSuper implements IPostFileDAO{

	public void updateDownloads(Long fileId) {
	   	Statement stmt = null;
    	try {
    		stmt = this.getConnection().createStatement();
    		ResultSet rs = stmt.executeQuery("select downloads from jbb_posts_files where file_id=" + fileId);
    		Long downloads = new Long(1);
    		if(rs.next()){
    			downloads = new Long(rs.getLong(1));
    		}
			stmt.executeUpdate("UPDATE jbb_posts_files SET downloads='"+ (downloads.longValue() + 1) +"' where file_id=" + fileId);
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
	

}
