/*
 * Created on 29/08/2007
 */
package org.javabb.infra;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LogWrappper {
	public static Log log = LogFactory.getLog(LogWrappper.class);
	
	
	public static void ERROR(String msg){
		log.error(createMsg(msg));
	}
	
	public static void ERROR(String reqId, String msg){
		log.error(createMsg(reqId, msg));
	}
	
	public static void INFO(String msg){
		log.info(createMsg(msg));
	}
	
	public static void INFO(String reqId, String msg){
		log.info(createMsg(reqId, msg));
	}
	
	public static void TRACE(String msg){
		log.trace(createMsg(msg));
	}
	
	public static void TRACE(String reqId, String msg){
		log.trace(createMsg(reqId, msg));
	}
	
	public static void DEBUG(String msg){
		log.debug(createMsg(msg));
	}
	
	public static void DEBUG(String reqId, String msg){
		log.debug(createMsg(reqId, msg));
	}

	private static String createMsg(String msg){
		return "["+ClassLocator.getClassName()+"] - " + msg;
	}
	
	private static String createMsg(String reqId, String msg){
		return "["+ClassLocator.getClassName()+"] - [ReqId: " + reqId + "] - " + msg;
	}
	
}
