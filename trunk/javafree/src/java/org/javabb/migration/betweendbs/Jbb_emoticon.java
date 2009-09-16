/**
 * Script de migra��o phpBB -> JavaBB
 * @author Lucas Teixeira - lucas@javabb.org
 */

package org.javabb.migration.betweendbs;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Jbb_emoticon extends Jbb_Convert {
    
    public void convert() throws ClassNotFoundException, SQLException {
        sql = "SELECT * FROM jbb_smiles ORDER BY id;";
        System.out.println("TABLE JBB_EMOTICONS.");
        Connection phpConn = this.getDbOrigin();
        Statement stm = phpConn.createStatement();
        System.out.println("\tReading data");
        ResultSet rs = stm.executeQuery(sql);

        Connection javaConn = this.getDbDest();
        
        //Limpando a tabela
        javaConn.createStatement().executeUpdate("delete from jbb_smiles");
        System.out.println("delete from jbb_smiles is OK!");
        
        PreparedStatement ps = javaConn.prepareStatement("INSERT INTO jbb_smiles (id, symbol, filename, emoticon) VALUES (?,?,?,?);");

        System.out.println("\tInserting data ");
        while (rs.next()) {
            ps.setInt   (1, rs.getInt("id"));
            ps.setString(2, rs.getString("symbol"));
            ps.setString(3, rs.getString("filename"));
            ps.setString(4, rs.getString("emoticon"));
            
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
