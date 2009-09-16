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

public class Jbb_category extends Jbb_Convert {
    
    public void convert() throws ClassNotFoundException, SQLException {
        sql = "SELECT * FROM "+phpbb_prefix+"categories ORDER BY cat_id;";
        System.out.println("TABLE JBB_CATEGORY.");
        Connection phpConn = this.getPhpBBConnection();
        Statement stm = phpConn.createStatement();
        System.out.println("\tReading data");
        ResultSet rs = stm.executeQuery(sql);

        Connection javaConn = this.getJavaBBConnection();
        
        //Limpando a tabela
        javaConn.createStatement().executeUpdate("delete from jbb_category");
        System.out.println("delete from jbb_category is OK!");
        
        PreparedStatement ps = javaConn.prepareStatement("INSERT INTO jbb_category (id_category, nm_category, cat_order) VALUES (?,?,?);");

        System.out.println("\tInserting data ");
        while (rs.next()) {
            ps.setInt(1, rs.getInt("cat_id"));
            ps.setString(2, rs.getString("cat_title"));
            ps.setInt(3, new Integer(rs.getString("cat_order")).intValue());
            
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
