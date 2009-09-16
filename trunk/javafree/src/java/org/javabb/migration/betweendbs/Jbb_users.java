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

public class Jbb_users extends Jbb_Convert {
    
    public void convert() throws ClassNotFoundException, SQLException {
        sql = "select * from jbb_users order by id_user;";
        System.out.println("TABLE JBB_USERS.");
        Connection phpConn = this.getDbOrigin();
        Statement stm = phpConn.createStatement();
        System.out.println("\tReading data");
        ResultSet rs = stm.executeQuery(sql);
        long i = 0;
       
        Connection javaConn = this.getDbDest();
        
        //javaConn.createStatement().executeUpdate("ALTER TABLE jbb_users MODIFY COLUMN id_user BIGINT(20) NOT NULL DEFAULT '0'");
        //System.out.println("ALTER TABLE jbb_users MODIFY COLUMN id_user BIGINT(20) NOT NULL DEFAULT '0' is OK!");
        //"Operação  : ALTER TABLE jbb_users MODIFY COLUMN id_user BIGINT(20) NOT NULL DEFAULT '0' AUTO_INCREMENT"
        
        javaConn.createStatement().executeUpdate("delete from jbb_users");
        System.out.println("delete from jbb_users is OK!");
        
        PreparedStatement ps = javaConn.prepareStatement("insert into jbb_users (id_user, user_name, pws, name, email, posts, admin, " +
                "data_registro, localizacao, website, occupation, user_sig, user_msnm, user_lastvisit, user_dateformat, " +
                "user_allow_viewonline, user_avatar, user_icq, user_interests, user_aim, user_yim, show_mail, show_signature, " +
                "user_code) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

        System.out.println("\tInserting data ");
        while (rs.next()) {
            ps.setInt       ( 1, rs.getInt("id_user")); 
            ps.setString    ( 2, rs.getString("user_name"));
            ps.setString    ( 3, rs.getString("pws"));
            ps.setString    ( 4, rs.getString("name"));
            ps.setString    ( 5, rs.getString("email"));
            ps.setInt       ( 6, rs.getInt("posts"));
            ps.setInt       ( 7, rs.getInt("admin"));
            ps.setTimestamp	( 8, rs.getTimestamp("data_registro"));
            ps.setString    ( 9, rs.getString("localizacao"));
            ps.setString    (10, rs.getString("website"));
            ps.setString    (11, rs.getString("occupation"));
            ps.setString    (12, rs.getString("user_sig"));
            ps.setString    (13, rs.getString("user_msnm"));
            ps.setTimestamp (14, rs.getTimestamp("user_lastvisit"));
            ps.setString    (15, rs.getString("user_dateformat"));
            ps.setString    (16, rs.getString("user_allow_viewonline"));
            ps.setString    (17, rs.getString("user_avatar"));
            ps.setString    (18, rs.getString("user_icq"));
            ps.setString    (19, rs.getString("user_interests"));
            ps.setString    (20, rs.getString("user_aim"));
            ps.setString    (21, rs.getString("user_yim"));
            ps.setInt       (22, rs.getInt("show_mail"));
            ps.setInt       (23, rs.getInt("show_signature"));
            ps.setString    (24, rs.getString("user_code"));
            
            ps.addBatch();
            System.out.print(".");

            try{
	            if(i%2000==0){
	                ps.executeBatch();
	            }
            }catch(SQLException ex1){
            	System.out.println("Cannot insert the userId:" + rs.getInt("id_user"));
            }
            i++;

        }
        System.out.println("\n");
        ps.executeBatch(); //Executa o restante.
        
        //Corrige o bug do Usuário Anonymous
        ps = javaConn.prepareStatement("UPDATE jbb_users SET id_user = ? WHERE user_name = ?;");
        ps.setInt   (1, 0); //id 0
        ps.setString(2, "Anonymous");
        ps.executeUpdate();
        
        
        //javaConn.createStatement().executeUpdate("ALTER TABLE jbb_users MODIFY COLUMN id_user BIGINT(20) NOT NULL DEFAULT '0' AUTO_INCREMENT");
        //System.out.println("ALTER TABLE jbb_users MODIFY COLUMN id_user BIGINT(20) NOT NULL DEFAULT '0' AUTO_INCREMENT is OK!");
        
        ps.close();
        stm.close();
        rs.close();
        phpConn.close();
        javaConn.close();
        System.out.print("Done!\n\n");
    }
}
