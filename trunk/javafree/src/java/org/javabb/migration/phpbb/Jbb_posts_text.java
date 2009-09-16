/**
 * Script de migração phpBB -> JavaBB
 * @author Lucas Teixeira - lucas@javabb.org
 */

package org.javabb.migration.phpbb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class Jbb_posts_text extends Jbb_Convert {
    
    public void convert() throws ClassNotFoundException, SQLException {
        sql = "select post_id, post_text " +
                " from "+phpbb_prefix+"posts_text;";
        System.out.println("TABLE JBB_POSTS_TEXT.");
        Connection phpConn = this.getPhpBBConnection();
        Statement stm = phpConn.createStatement();
        System.out.println("\tReading data");
        ResultSet rs = stm.executeQuery(sql);
        long i = 0;

        Connection javaConn = this.getJavaBBConnection();
        
        javaConn.createStatement().executeUpdate("delete from jbb_posts_text");
        System.out.println("delete from jbb_posts is OK!");
        
        PreparedStatement ps = javaConn.prepareStatement("insert into jbb_posts_text (id_post, post_body) values (?,?)");

        System.out.println("\tInserting data ");
        while (rs.next()) {
            ps.setInt       (1, rs.getInt("post_id"));
            ps.setString    (2, toJbbCode(rs.getString("post_text")));
            
            ps.addBatch();
            
            if (i%10000 == 0){
                try {
                    ps.executeBatch();
                } catch (SQLException e) {
                    System.out.println("\n\n" + e);
                };
            }

            System.out.print(".");
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