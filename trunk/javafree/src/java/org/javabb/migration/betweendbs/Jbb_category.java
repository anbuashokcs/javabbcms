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

public class Jbb_category extends Jbb_Convert {
    
    public void convert() throws ClassNotFoundException, SQLException {
        sql = "SELECT * FROM jbb_category;";
        System.out.println("TABLE JBB_CATEGORY.");
        Connection phpConn = this.getDbOrigin();
        Statement stm = phpConn.createStatement();
        System.out.println("\tReading data");
        ResultSet rs = stm.executeQuery(sql);

        Connection javaConn = this.getDbDest();
        
        //Limpando a tabela
        javaConn.createStatement().executeUpdate("delete from jbb_category");
        System.out.println("delete from jbb_category is OK!");
        
        PreparedStatement ps = javaConn.prepareStatement("INSERT INTO jbb_category (id_category, nm_category, cat_order) VALUES (?,?,?);");

        System.out.println("\tInserting data ");
        while (rs.next()) {
            ps.setInt(1, rs.getInt("id_category"));
            ps.setString(2, rs.getString("nm_category"));
            ps.setInt(3, rs.getInt("cat_order"));
            
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
