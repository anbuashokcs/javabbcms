/**
 * Script de migração phpBB -> JavaBB
 * @author Lucas Teixeira - lucas@javabb.org
 */

package org.javabb.migration.betweendbs;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.util.HtmlUtils;

public abstract class Jbb_Convert {
    
    //TODO Get all this references from an external resource 
    private static String phpbb_url = "jdbc:mysql://localhost:3306/javabb3?unicode=true";
    private static String phpbb_user = "root";
    private static String phpbb_pass = "root";
    
    /* POSTGRESQL */
    private static String javabb_url = "jdbc:postgresql://localhost/javabb";
    private static String javabb_user = "postgres";
    private static String javabb_pass = "postgres";
    private static String javabb_driver = "org.postgresql.Driver";
    
    protected String sql = "";
        
    private static DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        

    protected Connection getDbOrigin() throws ClassNotFoundException, SQLException {
        Class.forName(javabb_driver);
        System.out.println("Retrieving javaBB connection");
        return DriverManager.getConnection(javabb_url, javabb_user, javabb_pass);
    }


    protected Connection getDbDest() throws ClassNotFoundException, SQLException {
    	Class.forName("com.mysql.jdbc.Driver");
        System.out.println("Retrieving phpBB connection");
        return DriverManager.getConnection(phpbb_url, phpbb_user, phpbb_pass);
    }    
    
	/**
     * Convert the table data
	 * @return
	 */
	public abstract void convert() throws Exception;
    
    /**
     * Convert the hashed bbcode from phpbb to an clean bbcode to javaBB
     * @param phpBBCode the dirty bbcode
     * @return the bbcode w/out hashes
     * @author Ronald Tetsuo Miura - ronald.tetsuo@gmail.com
     */
    public String toJbbCode(String phpBBCode) {
        phpBBCode = phpBBCode == null ? "": phpBBCode;
        phpBBCode = HtmlUtils.htmlUnescape(phpBBCode);
    	String ret = phpBBCode.replaceAll(
                        "\\[(/)?(b|u|i|list|quote|code|img|url)(?::\\w+)?(=\"?[^\":]+\"?)?(:\\w)?(?::\\w+)?\\]",
                        "[$1$2$3]"
                     );
        return ret;
    }
    
    /**
     * Convert the hashed ip from phpbb to an clean ip
     * @param hexCode the phpbb ip representation
     * @return the ip well formatted
     * @author Flávio Bianchi - flavio@javafree.com.br
     */
    public String convertIP(String hexCode) {
        
        if (hexCode.length() != 8)
            return "127.0.0.1";
        
        StringBuffer ip = new StringBuffer();
        
        ip.append(Integer.parseInt(hexCode.substring(0,2), 16));
        ip.append(".");
        ip.append(Integer.parseInt(hexCode.substring(2,4), 16));
        ip.append(".");
        ip.append(Integer.parseInt(hexCode.substring(4,6), 16));
        ip.append(".");
        ip.append(Integer.parseInt(hexCode.substring(6,8), 16));
        
        return ip.toString();
    }
    
    private static final String JBB_TIMESTAMP_FORMAT = "yyyyMMddHHmmss";
    public String long2timestamp(long pre) {
        if (df == null)
            df = new SimpleDateFormat(JBB_TIMESTAMP_FORMAT);
    	
        return df.format(new Date(pre));
    }    
    
    public static void convertJbb_badwords() throws ClassNotFoundException, SQLException {
        new Jbb_badwords().convert();
    }
    
    public static void convertJbb_category() throws ClassNotFoundException, SQLException {
        new Jbb_category().convert();
    }
    
    public static void convertJbb_emoticon() throws ClassNotFoundException, SQLException {
        new Jbb_emoticon().convert();
    }
    
    public static void convertJbb_forum() throws ClassNotFoundException, SQLException {
        new Jbb_forum().convert();
    }
    
    public static void convertJbb_posts() throws ClassNotFoundException, SQLException {
        new Jbb_posts().convert();
    }
    
    public static void convertJbb_topics() throws ClassNotFoundException, SQLException {
        new Jbb_topics().convert();
    }
    
    public static void convertJbb_users() throws ClassNotFoundException, SQLException {
        new Jbb_users().convert();
    }
    
    public static void convertJbb_post_text() throws ClassNotFoundException, SQLException {
        new Jbb_post_text().convert();
    }
}
