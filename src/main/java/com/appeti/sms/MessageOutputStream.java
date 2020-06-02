package com.appeti.sms;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import com.appeti.utils.Property;

public class MessageOutputStream {
	private static final String URL = "http://cloud.smsindiahub.in/vendorsms/pushsms.aspx?";
	private static final String USER_AGENT = "Mozilla/5.0";
	private static String USER;
	private static String PASSWORD;
	private static final String PROMO_SENDER_ID = "WEBSMS";
	private static final String TRANS_SENDER_ID = "";
	private static int FLASH = 0;
	private static int TRANS_GWID = 2;
	private static final String EQUALS = "=";
	private static final String SEPARATOR = "&";
	private static Properties properties = null;
	private static final String PROPERTY_FILE_PATH = "/properties/server.properties";
	
	private static class PARAMS{
		private static final String USER = "user";
		private static final String PASSWORD = "password";
		private static final String NUMBER = "msisdn";
		private static final String SENDERID= "sid";
		private static final String CONTENT = "msg";
		private static final String FLASH = "fl";
		private static final String GWID = "gwid";
	}
	
	static{
		loadProperties();
	}
	
	private static void loadProperties(){
		if(properties == null){
			try {
				properties = new Properties();
				properties.load(new FileInputStream(PROPERTY_FILE_PATH));
				setCredentials();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void sendTransMessage(String number, String content) throws Exception{
		String params = PARAMS.USER + EQUALS + USER + SEPARATOR + 
				PARAMS.PASSWORD + EQUALS + PASSWORD + SEPARATOR + 
				PARAMS.SENDERID + EQUALS + TRANS_SENDER_ID + SEPARATOR + 
				PARAMS.FLASH + EQUALS + FLASH + SEPARATOR + 
				PARAMS.GWID + EQUALS + TRANS_GWID + SEPARATOR + 
				PARAMS.NUMBER + EQUALS + number + SEPARATOR + 
				PARAMS.CONTENT + EQUALS + content;
		sendMessage(params);
	}
	
	public static void sendPromoMessage(String number, String content) throws Exception{
		String params = PARAMS.USER + EQUALS + USER + SEPARATOR + 
				PARAMS.PASSWORD + EQUALS + PASSWORD + SEPARATOR + 
				PARAMS.SENDERID + EQUALS + PROMO_SENDER_ID + SEPARATOR + 
				PARAMS.FLASH + EQUALS + FLASH + SEPARATOR + 
				PARAMS.NUMBER + EQUALS + number + SEPARATOR + 
				PARAMS.CONTENT + EQUALS + content;
		sendMessage(params);
	}
	
	private static void sendMessage(String params) throws Exception{
		URL obj = new URL(URL + params);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
		int response = con.getResponseCode();
		System.out.println(response);
	}
	
	private static void setCredentials(){
		USER = properties.getProperty(Property.SMS_USERNAME);
		PASSWORD = properties.getProperty(Property.SMS_PASSWORD);
	}
}
