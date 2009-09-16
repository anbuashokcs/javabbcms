package org.javabb.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javabb.dao.entity.IUserRankDAO;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

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
 * $Id: JdbcUserRankDAO.java,v 1.1 2009/05/11 20:27:13 daltoncamargo Exp $
 * @author Dalton Camargo - dalton@javabb.org
 */
public class JdbcUserRankDAO extends JdbcSuper implements IUserRankDAO {

    private final Log log = LogFactory.getLog(this.getClass());

    public void refreshUserRankByForum(Long forumId) {

        log.debug("Refreshing UserRankByForum...");
        

        ResultSet rs = null;
        PreparedStatement stmtInsert = null;
        
        try {
        	StringBuffer sql = new StringBuffer();
            sql.append(" select u.id_user, count(p.id_user) as cnt from jbb_users as u ");
            sql.append("        inner join jbb_posts as p on p.id_user = u.id_user");
            sql.append("        inner join jbb_topics as t on p.id_topic = t.id_topic");
            sql.append("        inner join jbb_forum as f on t.id_forum = f.id_forum");
            sql.append("        where f.id_forum = " + forumId);
            sql.append(" group by u.id_user, p.id_user");
            sql.append(" order by cnt desc");
            sql.append(" limit 10; ");
            rs = this.getConnection().createStatement().executeQuery(sql.toString());
            
            StringBuffer sqlInsert = new StringBuffer();
        	sqlInsert.append(" INSERT INTO jbb_forum_top_user (ID_USER, POST_COUNT, DATE_ROW, ID_FORUM) ");
        	sqlInsert.append(" VALUES(?,?,?,?) ");
        	stmtInsert = this.getConnection().prepareStatement(sqlInsert.toString());
     
            while(rs.next()){
            	stmtInsert.setLong(1, rs.getLong(1));
            	stmtInsert.setLong(2, rs.getLong(2));
            	stmtInsert.setDate(3, null);
            	stmtInsert.setLong(4, forumId.longValue());
            	stmtInsert.addBatch();
            }

            //Insert all USERS_RANK
            stmtInsert.executeBatch();
            
        } catch (SQLException e) {
            log.error("SQL Exception error:" + e.getMessage());
        } catch (Exception e) {
            log.error("Exception error:" + e.getMessage());
        }finally{
        	try {
				rs.close();
				rs = null;
				stmtInsert.close();
				stmtInsert = null;
			} catch (SQLException e) {}
        }

        log.debug("UserRankByForum: ok!");
    }
   
    
    /**
     * Clean the jbb_forum_top_user table
     */ 
    public void cleanAllUserRank(){
    	Statement stmt = null;
    	try {
    		log.debug("Cleaning UserRank Table...");
    		stmt = this.getConnection().createStatement();
			stmt.executeUpdate("DELETE FROM jbb_forum_top_user");
			log.debug("Table was been cleaned");
		} catch (CannotGetJdbcConnectionException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
	    }finally{
	    	 try {
				stmt.close();
				stmt = null;
			} catch (SQLException e) {}
	    }
    }

    
    public void refreshSession(Object obj) {}
}