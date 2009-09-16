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

public class Jbb_forum extends Jbb_Convert {
    
    public void convert() throws ClassNotFoundException, SQLException {
        sql = "SELECT * FROM jbb_forum ORDER BY id_forum;";
        System.out.println("TABLE JBB_FORUM.");
        Connection phpConn = this.getDbOrigin();
        Statement stm = phpConn.createStatement();
        System.out.println("\tReading data");
        ResultSet rs = stm.executeQuery(sql);

        Connection javaConn = this.getDbDest();
        
        //Limpando a tabela
        javaConn.createStatement().executeUpdate("delete from jbb_forum");
        System.out.println("delete from jbb_forum is OK!");
        
        PreparedStatement ps = javaConn.prepareStatement("INSERT INTO jbb_forum (id_forum, nome, descricao, " +
                "forum_status, forum_order, id_category, topic_count, post_count, last_post_id, last_page_post," +
                "last_post_user_name, last_post_user_id, last_post_date, last_topic_id) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?);");

        System.out.println("\tInserting data ");
        while (rs.next()) {
            ps.setInt    ( 1, rs.getInt("id_forum"));
            ps.setString ( 2, rs.getString("nome"));
            ps.setString ( 3, rs.getString("descricao"));
            ps.setInt    ( 4, rs.getInt("forum_status"));
            ps.setInt    ( 5, rs.getInt("forum_order"));
            ps.setInt    ( 6, rs.getInt("id_category"));
            ps.setInt    ( 7, rs.getInt("topic_count"));
            ps.setInt    ( 8, rs.getInt("post_count"));
            ps.setInt    ( 9, rs.getInt("last_post_id"));
            ps.setInt    ( 10, rs.getInt("last_page_post"));
            ps.setString ( 11, rs.getString("last_post_user_name"));
            ps.setInt    ( 12, rs.getInt("last_post_user_id"));
            ps.setTimestamp ( 13, rs.getTimestamp("last_post_date"));
            ps.setInt    ( 14, rs.getInt("last_topic_id"));

            ps.executeUpdate();
            System.out.print(".");
        }
        System.out.println("\n");
        
        ps.close();
        stm.close();
        rs.close();
        phpConn.close();
        javaConn.close();
        System.out.print("Done!\n\n");
    }
}
