package org.javabb.dao.lucene;

import junit.framework.TestCase;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LucenePostsTest extends TestCase{
	
	private static ClassPathXmlApplicationContext context;
	
	public ClassPathXmlApplicationContext getContext () {
		if ( context == null ) {
			context = new ClassPathXmlApplicationContext ("file:WEB-INF/applicationContext.xml" );
		}
		return context;
	}
	
	public static void main(String[] args){
		LucenePostsTest lp = new LucenePostsTest();
		context = lp.getContext();
	}

}
