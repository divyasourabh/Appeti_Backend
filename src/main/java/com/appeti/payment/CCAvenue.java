package com.appeti.payment;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.appeti.database.table.order.Order;
import com.appeti.database.table.payment.MidTrans;
import com.appeti.database.table.user.Address;
import com.appeti.utils.Constants;
import com.appeti.utils.ExceptionUtils;
import com.appeti.utils.Property;
import com.appeti.utils.encryption.AesCryptUtil;

public class CCAvenue extends Gateway{
	
	private static final String PARAMETER_SEP = "&";
	private static final String PARAMETER_EQUALS = "=";
	private static final String TRANS_URL = "https://secure.ccavenue.com/transaction/initTrans";
	
	private static final class Params{
		private static final String ACCESS_CODE = "access_code";
		private static final String RSA_KEY = "rsa_key";
		private static final String MERCHANT_ID = "merchant_id";
		private static final String ORDER_ID = "order_id";
		private static final String CURRENCY = "currency";
		private static final String AMOUNT = "amount";
		private static final String REDIRECT_URL = "redirect_url";
		private static final String CANCEL_URL = "cancel_url";
		private static final String LANGUAGE = "language";
		private static final String BILLING_NAME = "billing_name";
		private static final String BILLING_ADDR = "billing_address";
		private static final String BILLING_CITY = "billing_city";
		private static final String BILLING_STATE = "billing_state";
		private static final String BILLING_ZIP = "billing_zip";
		private static final String BILLING_COUNTRY = "billing_country";
		private static final String BILLING_TEL = "billing_tel";
		private static final String BILLING_EMAIL = "billing_email";
		private static final String DELIVERY_NAME = "delivery_name";
		private static final String DELIVERY_ADDR = "delivery_address";
		private static final String DELIVERY_CITY = "delivery_city";
		private static final String DELIVERY_STATE = "delivery_state";
		private static final String DELIVERY_ZIP = "delivery_zip";
		private static final String DELIVERY_COUNTRY = "delivery_country";
		private static final String DELIVERY_TEL = "delivery_tel";
		
		private static final String TRACKING_ID = "tracking_id";
		private static final String BANK_REF_NO = "bank_ref_no";
		private static final String ORDER_STATUS = "order_status";
		private static final String FAILURE_MESSAGE = "failure_message";
		private static final String PAYMENT_MODE = "payment_mode";
		private static final String CARD_NAME = "card_name";
		private static final String STATUS_CODE = "status_code";
		private static final String STATUS_MESSGAE = "status_message";
	}
	
	public static Map<String,String> prepareRequestMap(Order order){
		if(order == null)
			return null;
		
		long midTransId = getMidTransId(order, Constants.Source.CC_AVENUE);
		if(midTransId == -1)
			return null;
		
		Map<String,String> params = new HashMap<String,String>();
		Address billAddr = Address.getById(order.getBillingAddrId());
		Address shipAddr = Address.getById(order.getShippingAddrId());
		
		params.put(Params.MERCHANT_ID, properties.getProperty(Property.CC_AVENUE_MERCHANTID));
		params.put(Params.ORDER_ID, String.valueOf(midTransId));
		params.put(Params.AMOUNT, String.valueOf(order.getAmountPaid()));
		params.put(Params.CURRENCY, "INR");
		params.put(Params.REDIRECT_URL, properties.getProperty(Property.CC_AVENUE_REDIRECT_URL,Property.DEFAULT_CC_AVENUE_REDIRECT_URL));
		params.put(Params.CANCEL_URL, properties.getProperty(Property.CC_AVENUE_CANCEL_URL,Property.DEFAULT_CC_AVENUE_CANCEL_URL));
		params.put(Params.LANGUAGE, "en");
		
		if(billAddr != null){
			params.put(Params.BILLING_NAME, billAddr.getFirstName() + " " + billAddr.getLastName());
			params.put(Params.BILLING_ADDR, billAddr.getAddr1() + (StringUtils.isNotBlank(billAddr.getAddr2()) ? "," + billAddr.getAddr2() : ""));
			params.put(Params.BILLING_CITY, billAddr.getCity());
			params.put(Params.BILLING_STATE, billAddr.getState());
			params.put(Params.BILLING_ZIP, billAddr.getZipCode());
			params.put(Params.BILLING_COUNTRY, billAddr.getCountry());
			params.put(Params.BILLING_TEL, billAddr.getPhoneNumber());
			params.put(Params.BILLING_EMAIL, billAddr.getEmailAddr());
		}
		
		if(shipAddr != null){
			params.put(Params.DELIVERY_NAME, shipAddr.getFirstName() + " " + shipAddr.getLastName());
			params.put(Params.DELIVERY_ADDR, billAddr.getAddr1() + (StringUtils.isNotBlank(billAddr.getAddr2()) ? "," + billAddr.getAddr2() : ""));
			params.put(Params.DELIVERY_CITY, shipAddr.getCity());
			params.put(Params.DELIVERY_STATE, shipAddr.getState());
			params.put(Params.DELIVERY_ZIP, shipAddr.getZipCode());
			params.put(Params.DELIVERY_COUNTRY, shipAddr.getCountry());
			params.put(Params.DELIVERY_TEL, shipAddr.getPhoneNumber());	
		}
		return params;
	}
	
	public static Hashtable<String,String> parseResponse(HttpServletRequest request){
		String encResp = request.getParameter("encResp");
		AesCryptUtil aesUtil=new AesCryptUtil(CCAvenue.getWorkingKey());
		String decResp = aesUtil.decrypt(encResp);
		StringTokenizer tokenizer = new StringTokenizer(decResp, "&");
		Hashtable<String,String> hs=new Hashtable<String,String>();
		String pair=null, pname=null, pvalue=null;
		while (tokenizer.hasMoreTokens()) {
			pair = (String)tokenizer.nextToken();
			if(pair!=null) {
				StringTokenizer strTok=new StringTokenizer(pair, "=");
				pname=""; pvalue="";
				if(strTok.hasMoreTokens()) {
					pname = strTok.nextToken();
					if(strTok.hasMoreTokens())
						pvalue = strTok.nextToken();
					hs.put(pname, URLDecoder.decode(pvalue));
				}
			}
		}
		return hs;
	}
	
	public static MidTrans processResponse(HttpServletRequest request){
		Hashtable<String,String> table = parseResponse(request);
		long midTransId = -1;
		try{
			midTransId = Long.valueOf(table.get(Params.ORDER_ID));
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}
		if(midTransId != -1){
			MidTrans trans = MidTrans.getById(midTransId);
			if(trans != null){
				trans.setTrackingId(table.get(Params.TRACKING_ID));
				trans.setBankRefNo(table.get(Params.BANK_REF_NO));
				trans.setTransStatus(table.get(Params.ORDER_STATUS));
				trans.setFailureMessage(table.get(Params.FAILURE_MESSAGE));
				trans.setMode(table.get(Params.PAYMENT_MODE));
				trans.setCardName(table.get(Params.CARD_NAME));
				trans.setStatusCode(table.get(Params.STATUS_CODE));
				trans.setStatusMsg(table.get(Params.STATUS_MESSGAE));
				try{
					trans.dbUpdate();
					return trans;
				}catch(Exception e){
					ExceptionUtils.logException(e);
					return null;
				}
			}
		}
		return null;
	}
	
	public static void setRequestParams(HttpServletRequest request,Order order){
		String encRequest = getEncrRequest(order);
		request.setAttribute("encRequest", encRequest);
		request.setAttribute("access_code", CCAvenue.getAccessCode());
		request.setAttribute("formUrl", getFormUrl());
	}
	
	public static String getEncrRequest(Order order){
		Map<String,String> params = prepareRequestMap(order);
		Set<Entry<String,String>> entrySet = params.entrySet();
		Iterator<Entry<String,String>> iterator = entrySet.iterator();
		String paramStr = "";
		while(iterator.hasNext()){
			Entry<String,String> entry = iterator.next();
			paramStr += entry.getKey() + "=" + entry.getValue() + "&";
		}
		AesCryptUtil aesUtil=new AesCryptUtil(CCAvenue.getWorkingKey());
		String encRequest = aesUtil.encrypt(paramStr);
		return encRequest;
	}
	
	public static String getEncrAppRequest(Order order){
		Map<String,String> params = prepareAppRequest(order);
		StringBuffer paramsStr = new StringBuffer();
		Set<String> keySet = params.keySet();
		Iterator<String> iterator = keySet.iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			String value = params.get(key);
			paramsStr.append(addToPostParams(key, value));
		}
		return paramsStr.substring(0,paramsStr.length()-1);
	}
	
	public static Map<String,String> prepareAppRequest(Order order){
		if(order == null)
			return null;
		
		long midTransId = getMidTransId(order, Constants.Source.CC_AVENUE);
		if(midTransId == -1)
			return null;
		
		Map<String,String> params = new HashMap<String,String>();
		Address billAddr = Address.getById(order.getBillingAddrId());
		Address shipAddr = Address.getById(order.getShippingAddrId());
		
		params.put(Params.ACCESS_CODE, getAccessCode());
		params.put(Params.MERCHANT_ID, properties.getProperty(Property.CC_AVENUE_MERCHANTID));
		params.put(Params.ORDER_ID, String.valueOf(midTransId));
		params.put(Params.RSA_KEY, getRSA(midTransId));
		params.put(Params.REDIRECT_URL, properties.getProperty(Property.CC_AVENUE_APP_REDIRECT_URL,Property.DEFAULT_CC_AVENUE_REDIRECT_URL));
		params.put(Params.CANCEL_URL, properties.getProperty(Property.CC_AVENUE_APP_REDIRECT_URL,Property.DEFAULT_CC_AVENUE_REDIRECT_URL));
		
		params.put(Params.AMOUNT, String.valueOf(order.getAmountPaid()));
		params.put(Params.CURRENCY, "INR");
		
		params.put(Params.LANGUAGE, "en");
		
		if(billAddr != null){
			params.put(Params.BILLING_NAME, billAddr.getFirstName() + " " + billAddr.getLastName());
			params.put(Params.BILLING_ADDR, billAddr.getAddr1() + (StringUtils.isNotBlank(billAddr.getAddr2()) ? "," + billAddr.getAddr2() : ""));
			params.put(Params.BILLING_CITY, billAddr.getCity());
			params.put(Params.BILLING_STATE, billAddr.getState());
			params.put(Params.BILLING_ZIP, billAddr.getZipCode());
			params.put(Params.BILLING_COUNTRY, billAddr.getCountry());
			params.put(Params.BILLING_TEL, billAddr.getPhoneNumber());
			params.put(Params.BILLING_EMAIL, billAddr.getEmailAddr());
		}
		
		if(shipAddr != null){
			params.put(Params.DELIVERY_NAME, shipAddr.getFirstName() + " " + shipAddr.getLastName());
			params.put(Params.DELIVERY_CITY, shipAddr.getCity());
			params.put(Params.DELIVERY_STATE, shipAddr.getState());
			params.put(Params.DELIVERY_ZIP, shipAddr.getZipCode());
			params.put(Params.DELIVERY_COUNTRY, shipAddr.getCountry());
			params.put(Params.DELIVERY_TEL, shipAddr.getPhoneNumber());	
		}
		
		/*Set<String> keyset = params.keySet();
		Iterator<String> iterator = keyset.iterator();
		System.out.println("App payment params");
		while(iterator.hasNext()){
			String key = iterator.next();
			System.out.println("key: " + key + " value: " + params.get(key));
		}
		*/
		return params;
	}
	
	public static String getRSA(long midTransId){
		StringBuffer vRequest = new StringBuffer("");
		vRequest.append(Params.ACCESS_CODE+"="+getAccessCode()+"&");
		vRequest.append(Params.ORDER_ID+"="+midTransId);
		
		
		URL	vUrl = null;
		URLConnection vHttpUrlConnection = null;
		DataOutputStream vPrintout = null;
		DataInputStream	vInput = null;
		StringBuffer vStringBuffer = null;
		try{
			System.setProperty("https.protocols", "TLSv1");
			vUrl = new URL("https://secure.ccavenue.com/transaction/getRSAKey");
			if(vUrl.openConnection() instanceof HttpsURLConnection){
				vHttpUrlConnection = (HttpsURLConnection)vUrl.openConnection();
			}else if(vUrl.openConnection() instanceof com.sun.net.ssl.HttpsURLConnection){
				vHttpUrlConnection = (com.sun.net.ssl.HttpsURLConnection)vUrl.openConnection();
			}else{
				vHttpUrlConnection = (URLConnection)vUrl.openConnection();
			}
			vHttpUrlConnection.setDoInput(true);
			vHttpUrlConnection.setDoOutput(true);
			vHttpUrlConnection.setUseCaches(false);
			vHttpUrlConnection.connect();
			vPrintout = new DataOutputStream (vHttpUrlConnection.getOutputStream());
			vPrintout.writeBytes(vRequest.toString());
			vPrintout.flush();
			vPrintout.close();
			BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(vHttpUrlConnection.getInputStream()));
			vStringBuffer = new StringBuffer();
			String vRespData;
			while((vRespData = bufferedreader.readLine()) != null)
				if(vRespData.length() != 0)
					vStringBuffer.append(vRespData.trim()+"\n");
			bufferedreader.close();
			bufferedreader = null;
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}finally {  
			if (vInput != null)
				try{
					vInput.close();  
				}catch(Exception e){}
			if (vHttpUrlConnection != null)  
				vHttpUrlConnection = null;
		}
		return vStringBuffer.substring(0,vStringBuffer.length()-1);
	}
	
	public static String addToPostParams(String paramKey, String paramValue){
		if(paramValue!=null)
			return paramKey.concat(PARAMETER_EQUALS).concat(paramValue)
					.concat(PARAMETER_SEP);
		return "";
	}
	
	public static String getWorkingKey(){
		return properties.getProperty(Property.CC_AVENUE_WORKING_KEY);
	}
	
	public static String getAccessCode(){
		return properties.getProperty(Property.CC_AVENUE_ACCESS_CODE);
	}
	
	public static String getFormUrl(){
		return properties.getProperty(Property.CC_AVENUE_FORM_URL);
	}
	
	public static String getAppTransUrl(){
		return properties.getProperty(Property.CC_AVENUE_APP_TRANS_URL, TRANS_URL);
	}
}
