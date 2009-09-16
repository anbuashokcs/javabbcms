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

public class Jbb_badwords extends Jbb_Convert {

    public void convert() throws ClassNotFoundException, SQLException {
        sql = "SELECT * FROM jbb_badwords;";
        System.out.println("TABLE JBB_BADWORDS.");
        Connection phpConn = this.getDbOrigin();
        Statement stm = phpConn.createStatement();
        System.out.println("\tReading data");
        ResultSet rs = stm.executeQuery(sql);

        Connection javaConn = this.getDbDest();
        //Limpando a tabela
        javaConn.createStatement().executeUpdate("delete from jbb_badwords");
        System.out.println("delete from jbb_badwords is OK!");
        
        
        PreparedStatement ps = javaConn.prepareStatement("INSERT INTO jbb_badwords (id_badword, word, replacement) VALUES (?,?,?);");

        System.out.println("\tInserting data ");
        while (rs.next()) {
            ps.setInt(1, rs.getInt("id_badword"));
            ps.setString(2, rs.getString("word"));
            ps.setString(3, rs.getString("replacement"));
            
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