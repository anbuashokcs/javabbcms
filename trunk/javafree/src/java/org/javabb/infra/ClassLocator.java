package org.javabb.infra;

public class ClassLocator extends SecurityManager {

	public static String getClassName(){
		ClassLocator cl = new ClassLocator();
		Class cls = cl.getClassContext()[3];
		
		if(cls != null){
			return cls.getName();
		} else {
			return ClassLocator.class.getName();
		}
	}
}
