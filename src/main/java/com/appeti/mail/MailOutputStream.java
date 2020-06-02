package com.appeti.mail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.appeti.database.table.utils.AdminString;
import com.appeti.utils.ExceptionUtils;
import com.appeti.utils.Property;

public class MailOutputStream {
	
	private String from;
	private String to;
	private String subject;
	private String content;
	private String contentType = "text/html";
	private static final String HOST = "email-smtp.us-west-2.amazonaws.com";
	private static final int PORT = 465;
	private static Properties properties = null;
	private static final String PROPERTY_FILE_PATH = "/properties/server.properties";
	private static String USER;
	private static String PASSWORD;
	
	static{
		//loadProperties();
		setCredentials();
	}
	
	public MailOutputStream(String from,String to,String subject,String content) {
		this.from = from;
		this.to = to;
		this.subject = subject;
		this.content = content;
	}

	public MailOutputStream(String from,String to,String subject,String content,String contentType) {
		this.from = from;
		this.to = to;
		this.subject = subject;
		this.content = content;
		this.contentType = contentType;
	}

	public void send() throws Exception{
		try{
			Properties props = new Properties();

			props.setProperty("mail.transport.protocol", "smtps");
			props.put("mail.smtp.port", PORT);
			props.setProperty("mail.smtps.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
	    	props.put("mail.smtp.starttls.required", "true");
	    	props.put("mail.smtps.timeout", 10000);
	    	
			Session mailSession = Session.getDefaultInstance(props);
			mailSession.setDebug(true);
			Transport transport = mailSession.getTransport();
			
			MimeMessage message = new MimeMessage(mailSession);
			message.setSubject(subject);
			message.setFrom(new InternetAddress(from));
			message.setContent(content,this.contentType);
			List<Address> addrs = new ArrayList<Address>();
			String[] recepients = to.split(",");
			for(String rec : recepients){
				try{
					addrs.add(new InternetAddress(rec));
				}catch(Exception e){
					ExceptionUtils.logException(e);
				}
			}
			message.addRecipients(Message.RecipientType.TO,addrs.toArray(new Address[0]));
			transport.connect(HOST,PORT,USER,PASSWORD);
			transport.sendMessage(message,message.getRecipients(Message.RecipientType.TO));
			transport.close();
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	
	public static void sendErrorNotification(String subjectPrefix, String stackTrace){
		sendErrorNotification(subjectPrefix, "", stackTrace);
	}
	
	public static void sendErrorNotification(String subjectPrefix, String msg, String stackTrace){
		try{
			String from = "support@appeti.in";
			String to = "nikhil.arora121@gmail.com,error@appeti.in";
			String subject = subjectPrefix + " Error Notification Mail";
			String content = msg + "\n\n" + stackTrace;
			MailOutputStream mail = new MailOutputStream(from, to, subject, content,"text/plain");
			mail.send();
		}catch(Exception ex){
			ex.printStackTrace();
		}
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
	
	private static void setCredentials(){
		//USER = properties.getProperty(Property.SMTP_USERNAME);
		//PASSWORD = properties.getProperty(Property.SMTP_PASSWORD);
		USER = AdminString.get(Property.SMTP_USERNAME);
		PASSWORD = AdminString.get(Property.SMTP_PASSWORD);
	}
}
