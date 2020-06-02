package com.appeti.utils;

import java.security.MessageDigest;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.appeti.mail.MailOutputStream;

public class CryptWithMD5 {
	private static MessageDigest md;

	public static String cryptWithMD5(String pass){
		try {
			md = MessageDigest.getInstance("MD5");
			byte[] passBytes = pass.getBytes();
			md.reset();
			byte[] digested = md.digest(passBytes);
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<digested.length;i++){
				sb.append(Integer.toHexString(0xff & digested[i]));
			}
			return sb.toString();
		} catch (Exception e) {
			ExceptionUtils.logException(e);
		}
		return null;


	}
}