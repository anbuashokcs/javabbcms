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

public class Jbb_post_text extends Jbb_Convert {
    
    public void convert() throws ClassNotFoundException, SQLException {
    	
	        sql = "select * from jbb_posts_text ORDER BY id_post";
	        System.out.println("TABLE JBB_POSTS_TEXT.");
	        Connection phpConn = this.getDbOrigin();
	        Statement stm = phpConn.createStatement();
	        System.out.println("\tReading data");
	        ResultSet rs = stm.executeQuery(sql);
	
	        Connection javaConn = this.getDbDest();
	        
	        javaConn.createStatement().executeUpdate("delete from jbb_posts_text");
	        System.out.println("delete from posts_text is OK!");
	        
	        PreparedStatement ps = javaConn.prepareStatement("insert into jbb_posts_text (id_post, post_body) values (?,?)");
	
	        System.out.println("\tInserting data ");
	        int i=0;
	        while (rs.next()) {
	        	int idPost = rs.getInt("id_post");
	            ps.setInt       (1, idPost);
	            ps.setString    (2, rs.getString("post_body"));
	            
	            ps.addBatch();
	            
	            if(i%7000==0){
	                ps.executeBatch();
	            }
	
	            System.out.println("Post = " + idPost);
	            i++;
	        }
	        System.out.println("\n");
	        
	        try {
	            ps.executeBatch();
	        } catch (SQLException e) {
	            System.out.println("\n\n" + e.getNextException().getMessage());
	        }
	        
	        ps.close();
	        stm.close();
	        rs.close();
	        phpConn.close();
	        javaConn.close();
	        System.out.print("Done!\n\n");
    }
}