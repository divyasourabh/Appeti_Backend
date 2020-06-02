package com.appeti.sms;

import com.appeti.utils.ExceptionUtils;

public class SMSUtils {
	private static final String PLAYSTORELINK = "https://goo.gl/OPZ8cR";
	private static final String NEW_ORDER = "Hi Costumer, Your ORDER#$orderId$ has been placed with us.ENTER Please download our Android App to track your order " + PLAYSTORELINK;
	//private static final String ORDER_DISPATCHED = "Hi $name$, Your ORDER#$orderId$ has been dispatched and it will be delivered to you within $hours$ hours";
	
	public static void sendNewOrderNotification(long orderId, String phn){
		try{
			MessageOutputStream.sendTransMessage(phn, NEW_ORDER.replace("$orderId$", String.valueOf(orderId)));
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}
	}
}
