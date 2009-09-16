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

public class Jbb_topics extends Jbb_Convert {
    
    public void convert() throws ClassNotFoundException, SQLException {
        sql = "select t.topic_id, t.forum_id, t.topic_title, t.topic_poster, t.topic_time, t.topic_views, " +
              "t.topic_replies, t.topic_status, p.post_time from "+phpbb_prefix+"topics as t, "+phpbb_prefix+"posts as p " +
              "where t.topic_last_post_id = p.post_id";
        
        System.out.println("TABLE JBB_TOPICS.");
        Connection phpConn = this.getPhpBBConnection();
        Statement stm = phpConn.createStatement();
        System.out.println("\tReading data");
        ResultSet rs = stm.executeQuery(sql);
        String sqlInsert = "";
        long i = 0;

        Connection javaConn = this.getJavaBBConnection();
        
        javaConn.createStatement().executeUpdate("delete from jbb_topics");
        System.out.println("delete from jbb_topics is OK!");
        
        PreparedStatement ps = javaConn.prepareStatement("insert into jbb_topics (id_topic, id_user, " +
                "id_forum, title_topic, data_topico, visualizacoes, respostas, topic_status) " +
                "values (?,?,?,?,?,?,?,?);");

        System.out.println("\tInserting data ");
        while (rs.next()) {
            ps.setInt       (1, rs.getInt("topic_id"));
            ps.setInt       (2, (rs.getInt("topic_poster") == -1 ? 0 : rs.getInt("topic_poster")));
            ps.setInt       (3, rs.getInt("forum_id"));
            ps.setString    (4, rs.getString("topic_title"));
            ps.setTimestamp (5, new Timestamp(rs.getLong("topic_time")*1000));
            ps.setInt       (6, rs.getInt("topic_views"));
            ps.setInt       (7, rs.getInt("topic_replies"));
            ps.setInt       (8, rs.getInt("topic_status"));
            //ps.setTimestamp (9, new Timestamp(rs.getLong("post_time")*1000));
            
            ps.addBatch();
            
            if(i%7000==0)
                ps.executeBatch();
            
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
