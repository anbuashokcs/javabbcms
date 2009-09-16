package org.javabb.migration.betweendbs;



public class JavaBB_DBScript {
   
    public static void main(String[] args) {

		try {
//            Jbb_Convert.convertJbb_forum();
//            Jbb_Convert.convertJbb_topics();
            Jbb_Convert.convertJbb_users();
            
//            Jbb_Convert.convertJbb_badwords();
//            Jbb_Convert.convertJbb_category();
//            Jbb_Convert.convertJbb_emoticon();
//            Jbb_Convert.convertJbb_posts();
//            Jbb_Convert.convertJbb_post_text();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}