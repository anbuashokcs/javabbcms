/**
 * Script de migração phpBB -> JavaBB
 * @author Lucas Teixeira - lucas@javabb.org
 */

package org.javabb.migration.phpbb;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Jbb_users extends Jbb_Convert {
    
    public void convert() throws ClassNotFoundException, SQLException {
        sql = "select * from phpbb_users order by user_id;";
        System.out.println("TABLE JBB_USERS.");
        Connection phpConn = this.getPhpBBConnection();
        Statement stm = phpConn.createStatement();
        System.out.println("\tReading data");
        ResultSet rs = stm.executeQuery(sql);
        long i = 0;
       
        Connection javaConn = this.getJavaBBConnection();
        
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
            ps.setInt       ( 1, rs.getInt("user_id")); 
            ps.setString    ( 2, rs.getString("username"));
            ps.setString    ( 3, rs.getString("user_password"));
            ps.setString    ( 4, ""); //TODO Porque todos os usuários tem que se chamar 'JavaBB User!' ? (;
            ps.setString    ( 5, rs.getString("user_email"));
            ps.setInt       ( 6, rs.getInt("user_posts"));
            ps.setInt       ( 7, 0); //TODO ninguem é admin a princípio
            
            String data_registro = ""; 
            String last_visit = "";
            try{
                data_registro = "1063221218";// long2timestamp(rs.getLong("user_regdate") * 1000);
            } catch(Exception e){
                data_registro = "1063221218";
            }
            
            try{
            	last_visit = "1063221218";// long2timestamp(rs.getLong("user_lastvisit") * 1000);
            } catch(Exception e){
            	last_visit = "1063221218";
            }
        
            ps.setDate	( 8, new Date(new Long(data_registro).longValue()));
            ps.setString    ( 9, rs.getString("user_from"));
            ps.setString    (10, rs.getString("user_website"));
            ps.setString    (11, rs.getString("user_occ"));
            ps.setString    (12, "[url=http://www.javafree.org]JavaFree.org[/url]");
            ps.setString    (13, rs.getString("user_msnm"));
            ps.setDate  	(14, new Date(new Long(last_visit).longValue()));
            ps.setString    (15, rs.getString("user_dateformat"));
            ps.setString    (16, rs.getString("user_allow_viewonline"));
            
            String user_avatar = rs.getString("user_avatar");
            if(user_avatar != null && !"".equals(user_avatar))
                if(user_avatar.charAt(0) != 'h')
                    user_avatar = "http://www.javafree.com.br/forum/images/avatars/" + user_avatar;
            
            ps.setString    (17, user_avatar);
            ps.setString    (18, rs.getString("user_icq"));
            ps.setString    (19, rs.getString("user_interests"));
            ps.setString    (20, rs.getString("user_aim"));
            ps.setString    (21, rs.getString("user_yim"));
            ps.setInt       (22, rs.getInt("user_viewemail"));
            ps.setInt       (23, rs.getInt("user_attachsig"));
            ps.setString    (24, rs.getString("user_id").concat(""+System.currentTimeMillis()));
            
            ps.addBatch();
            System.out.print(".");

            try{
	            if(i%1000==0){
	                ps.executeBatch();
	            }
            }catch(SQLException ex1){
            	System.out.println("Cannot insert the userId:" + rs.getInt("user_id"));
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
