/**
 * Script de migração phpBB -> JavaBB
 * @author Lucas Teixeira - lucas@javabb.org
 */

package org.javabb.migration.betweendbs;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Jbb_topics extends Jbb_Convert {
    
    public void convert() throws ClassNotFoundException, SQLException {
        sql = "select * from jbb_topics";
        
        System.out.println("TABLE JBB_TOPICS.");
        Connection phpConn = this.getDbOrigin();
        Statement stm = phpConn.createStatement();
        System.out.println("\tReading data");
        ResultSet rs = stm.executeQuery(sql);
        long i = 0;

        Connection javaConn = this.getDbDest();
        
        javaConn.createStatement().executeUpdate("delete from jbb_topics");
        System.out.println("delete from jbb_topics is OK!");
        
        PreparedStatement ps = javaConn.prepareStatement("insert into jbb_topics (id_topic, id_user, " +
                "id_forum, title_topic, data_topico, visualizacoes, respostas, topic_status," +
                "topic_model, last_post_date, last_post_id, last_post_page, last_post_user_id, last_post_user_name, notify_me) " +
                "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");

        System.out.println("\tInserting data ");
        
//        Statement stmt = javaConn.createStatement();
        while (rs.next()) {
        	
        	
//        	String sqlI = "insert into jbb_topics (id_topic, id_user, " +
//                    "id_forum, title_topic, data_topico, visualizacoes, respostas, topic_status" +
//                    "topic_model, last_post_date, last_post_id, last_post_page, last_post_user_id, last_post_user_name, notify_me) " +
//                    "values ("+ rs.getInt("id_topic") +","+ rs.getInt("id_user") +","+ rs.getInt("id_forum") +",'"+ rs.getString("title_topic") +"'," +
//                    		"'"+ rs.getTimestamp("data_topico") +"',"+ rs.getInt("visualizacoes") +"," +
//                    		""+ rs.getInt("respostas") +","+ rs.getInt("topic_status") +"," +
//                    		""+ rs.getInt("topic_model") +",'"+ rs.getTimestamp("last_post_date") +"'," +
//                    		""+ rs.getInt("last_post_id") +","+ rs.getInt("last_post_page") +"," +
//                    		""+ rs.getInt("last_post_user_id") +",'"+ rs.getString("last_post_user_name") +"',"+ rs.getInt("notify_me") +");";
//        	System.out.println(sqlI);
//        	stmt.executeUpdate(sqlI);
        	
        	ps.setInt       (1, rs.getInt("id_topic"));
          	ps.setInt(2, rs.getInt("id_user"));
			ps.setInt(3, rs.getInt("id_forum"));
			ps.setString(4, rs.getString("title_topic"));
			ps.setTimestamp(5, rs.getTimestamp("data_topico"));
			ps.setInt(6, rs.getInt("visualizacoes"));
			ps.setInt(7, rs.getInt("respostas"));
			ps.setInt(8, rs.getInt("topic_status"));
			ps.setInt(9, rs.getInt("topic_model"));
			ps.setTimestamp(10, rs.getTimestamp("last_post_date"));
			ps.setInt(11, rs.getInt("last_post_id"));
			ps.setInt(12, rs.getInt("last_post_page"));
			ps.setInt(13, rs.getInt("last_post_user_id"));
			ps.setString(14, rs.getString("last_post_user_name"));
			ps.setInt(15, rs.getInt("notify_me"));
            ps.addBatch();
            
            if(i%7000==0){
                ps.executeBatch();
            }
            
            System.out.print(".");
        }
        System.out.println("\n");
        
        ps.executeBatch();
        ps.close();
        stm.close();
        rs.close();
        phpConn.close();
        javaConn.close();
        System.out.print("Done!\n\n");
    }
}
