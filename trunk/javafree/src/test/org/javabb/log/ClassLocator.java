package org.javabb.log;


public class ClassLocator extends SecurityManager {
	
	public static Class getCallerClass() {
		ClassLocator cl = new ClassLocator();
		return cl.getClassContext()[2];
	}
}
