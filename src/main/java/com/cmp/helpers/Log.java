package com.cmp.helpers;
import org.apache.log4j.Logger;

public class Log {

	private static Logger Log = Logger.getLogger(Log.class.getName()); 
	
	public static void startTestCase(String testCaseName){
		Log.info(testCaseName);
	}


	public static void endTestCase(String testCaseName){
		Log.info(testCaseName);
	}

	public static void info(String message)
	{
		Log.info(message);
	}
	
	public static void error(String message, Object object)
	{
		Log.error(message + "::" + object);
	}
	
	public static void error(String message)
	{
		Log.error(message);
	}
	
	
	public static boolean condition(boolean value) {
		return value;
	}
}