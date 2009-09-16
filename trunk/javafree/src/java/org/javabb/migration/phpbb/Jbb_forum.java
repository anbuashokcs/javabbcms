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

public class Jbb_forum extends Jbb_Convert {
    
    public void convert() throws ClassNotFoundException, SQLException {
        sql = "SELECT * FROM "+phpbb_prefix+"forums ORDER BY forum_id;";
        System.out.println("TABLE JBB_FORUM.");
        Connection phpConn = this.getPhpBBConnection();
        Statement stm = phpConn.createStatement();
        System.out.println("\tReading data");
        ResultSet rs = stm.executeQuery(sql);

        Connection javaConn = this.getJavaBBConnection();
        
        //Limpando a tabela
        javaConn.createStatement().executeUpdate("delete from jbb_forum");
        System.out.println("delete from jbb_forum is OK!");
        
        PreparedStatement ps = javaConn.prepareStatement("INSERT INTO jbb_forum (id_forum, nome, descricao, " +
                "forum_status, forum_order, id_category, topic_count, post_count, last_post_id, last_page_post) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?);");

        System.out.println("\tInserting data ");
        while (rs.next()) {
            ps.setInt    ( 1, rs.getInt("forum_id"));
            ps.setString ( 2, rs.getString("forum_name"));
            ps.setString ( 3, rs.getString("forum_desc"));
            ps.setInt    ( 4, rs.getInt("forum_status"));
            ps.setInt    ( 5, rs.getInt("forum_order"));
            ps.setInt    ( 6, rs.getInt("cat_id"));
            ps.setInt    ( 7, rs.getInt("forum_topics"));
            ps.setInt    ( 8, rs.getInt("forum_posts"));
            ps.setInt    ( 9, rs.getInt("forum_last_post_id"));
            ps.setInt    (10, 1); //TODO recuperar qual página
                       
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
