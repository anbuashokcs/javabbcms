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

public class Jbb_posts extends Jbb_Convert {
    
    public void convert() throws ClassNotFoundException, SQLException {
        sql = "select p.post_id, p.topic_id, p.poster_id, p.post_time, p.poster_ip, p.enable_sig, pt.post_subject, " +
                "pt.post_text from "+phpbb_prefix+"posts as p, "+phpbb_prefix+"posts_text as pt where p.post_id = " +
                "pt.post_id;";
        System.out.println("TABLE JBB_POSTS.");
        Connection phpConn = this.getPhpBBConnection();
        Statement stm = phpConn.createStatement();
        System.out.println("\tReading data");
        ResultSet rs = stm.executeQuery(sql);
        long i = 0;

        Connection javaConn = this.getJavaBBConnection();
        
        javaConn.createStatement().executeUpdate("delete from jbb_posts");
        System.out.println("delete from jbb_posts is OK!");
        
        PreparedStatement ps = javaConn.prepareStatement("insert into jbb_posts (id_post, id_user, id_topic, " +
                "data_post, assunto, sig, ip, post_state) values (?,?,?,?,?,?,?,?)");

        System.out.println("\tInserting data ");
        while (rs.next()) {
            ps.setInt       (1, rs.getInt("post_id"));
            ps.setInt       (2, rs.getInt("poster_id") == -1 ? 0 : rs.getInt("poster_id"));
            ps.setInt       (3, rs.getInt("topic_id"));
            ps.setTimestamp (4, new Timestamp(rs.getLong("post_time")*1000));
            ps.setString    (5, toJbbCode(rs.getString("post_subject")));
            ps.setInt       (6, rs.getInt("enable_sig"));
            ps.setString    (7, convertIP(rs.getString("poster_ip")));
            ps.setInt       (8, 0); //TODO não usado
            
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