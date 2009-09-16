package org.javabb.migration.phpbb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*$Id: JbbMerge.java,v 1.1 2009/05/11 20:27:07 daltoncamargo Exp $*/
public class JbbMerge extends Jbb_Convert {
    
    public void convert() throws ClassNotFoundException, SQLException {
        sql = "SELECT * FROM jbb_posts ORDER BY id_post;";

        Connection phpConn = this.getPhpBBConnection();
        Statement stm = phpConn.createStatement();

        ResultSet rs = stm.executeQuery(sql);

        Connection javaConn = this.getJavaBBConnection();
        PreparedStatement ps = javaConn.prepareStatement("INSERT INTO jbb_category (id_category, nm_category, cat_order) VALUES (?,?,?);");

        System.out.print("\tInserting data ");
        while (rs.next()) {
            ps.setInt(1, rs.getInt("cat_id"));
            ps.setString(2, rs.getString("cat_title"));
            ps.setString(3, rs.getString("cat_order"));
            
            ps.executeUpdate();
            System.out.print(".");
        }
        System.out.print("\n");
        
        ps.close();
        stm.close();
        rs.close();
        phpConn.close();
        javaConn.close();
        System.out.print("Done!\n\n");
    }
}
