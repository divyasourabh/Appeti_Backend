package com.appeti.utils;

import org.apache.log4j.Logger;

import com.appeti.mail.MailOutputStream;

public class ExceptionUtils {
	
	public static void logException(Exception e){
		logException(e, "Exception", null);
	}
	public static void logException(Exception e, Logger logger){
		logException(e, "Exception", logger);
	}
	public static void logException(Exception e, String message){
		logException(e, message, null);
	}
	public static void logException(final String stackTrace){
		Runnable postProcess = new Runnable() {
			@Override
			public void run() {
				//MailOutputStream.sendErrorNotification("APPLICATION -",stackTrace);
			}
		};
		Thread thread = new Thread(postProcess);
		thread.start();
		
	}
	public static void logException(final Exception e, final String message, Logger logger){
		Runnable postProcess = new Runnable() {
			@Override
			public void run() {
				e.printStackTrace();
				//MailOutputStream.sendErrorNotification("SERVER -", message, org.apache.commons.lang.exception.ExceptionUtils.getStackTrace(e));
			}
		};
		Thread thread = new Thread(postProcess);
		thread.start();
	}
}
