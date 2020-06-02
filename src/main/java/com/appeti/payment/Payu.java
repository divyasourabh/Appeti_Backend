package com.appeti.payment;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.appeti.database.table.order.Order;
import com.appeti.database.table.payment.MidTrans;
import com.appeti.database.table.user.Address;
import com.appeti.utils.Constants;
import com.appeti.utils.ExceptionUtils;
import com.appeti.utils.KeyValue;
import com.appeti.utils.Property;

public class Payu extends Gateway{
	private static String SERVICE_PROVIDER = "payu_paisa";
	public static String outgoingHashSequence = "key|txnid|amount|productinfo|firstname|email|udf1|udf2|udf3|udf4|udf5||||||"; // do not change the sequence
	public static String incomingHashSequence = "|status||||||udf5|udf4|udf3|udf2|udf1|email|firstname|productinfo|amount|txnid|key"; // do not change the sequence
	
	private static final class Params{
		private static final String MERCHANT_ID = "key";
		private static final String SALT = "salt";
		private static final String ORDER_ID = "txnid";
		private static final String SERVICE_PROVIDER = "service_provider";
		private static final String AMOUNT = "amount";
		private static final String DESC = "productinfo";
		private static final String SUCCESS_URL = "surl";
		private static final String FAILURE_URL = "furl";
		private static final String CANCEL_URL = "curl";
		private static final String BILLING_FIRSTNAME = "firstname";
		private static final String BILLING_LASTNAME = "lastname";
		private static final String BILLING_ADDR1 = "address1";
		private static final String BILLING_ADDR2 = "address2";
		private static final String BILLING_CITY = "city";
		private static final String BILLING_STATE = "state";
		private static final String BILLING_ZIP = "zipcode";
		private static final String BILLING_COUNTRY = "country";
		private static final String BILLING_TEL = "phone";
		private static final String BILLING_EMAIL = "email";
		private static final String DELIVERY_FIRSTNAME = "shipping_firstname";
		private static final String DELIVERY_LASTNAME = "shipping_lastname";
		private static final String DELIVERY_ADDR1 = "shipping_address1";
		private static final String DELIVERY_ADDR2 = "shipping_address2";
		private static final String DELIVERY_CITY = "shipping_city";
		private static final String DELIVERY_STATE = "shipping_state";
		private static final String DELIVERY_ZIP = "shipping_zipcode";
		private static final String DELIVERY_COUNTRY = "shipping_country";
		private static final String DELIVERY_TEL = "shipping_phone";
		
		private static final String HASH = "hash";
		private static final String TRACKING_ID = "payuMoneyId";
		private static final String BANK_REF_NO = "bank_ref_num";
		private static final String ORDER_STATUS = "status";
		private static final String FAILURE_MESSAGE = "Error";
		private static final String PAYMENT_MODE = "mode";
	}
	
	public static Map<String,String> prepareRequestMap(Order order){
		if(order == null)
			return null;
		
		long midTransId = getMidTransId(order, Constants.Source.PAY_U);
		if(midTransId == -1)
			return null;
		
		Map<String,String> params = new HashMap<String,String>();
		Address billAddr = Address.getById(order.getBillingAddrId());
		Address shipAddr = Address.getById(order.getShippingAddrId());
		
		params.put(Params.MERCHANT_ID, properties.getProperty(Property.PAYU_KEY));
		params.put(Params.ORDER_ID, String.valueOf(midTransId));
		params.put(Params.SERVICE_PROVIDER, SERVICE_PROVIDER);
		params.put(Params.AMOUNT, String.valueOf(order.getAmountPaid()));
		params.put(Params.DESC, "ORDER#"+String.valueOf(order.getOrderId()));
		params.put(Params.SUCCESS_URL, properties.getProperty(Property.PAYU_SUCCESS_URL,Property.DEFAULT_PAYU_SUCCESS_URL));
		params.put(Params.FAILURE_URL, properties.getProperty(Property.PAYU_FAILURE_URL,Property.DEFAULT_PAYU_FAILURE_URL));
		params.put(Params.CANCEL_URL, properties.getProperty(Property.PAYU_CANCEL_URL,Property.DEFAULT_PAYU_CANCEL_URL));
		
		if(billAddr != null){
			params.put(Params.BILLING_FIRSTNAME, billAddr.getFirstName());
			params.put(Params.BILLING_LASTNAME, billAddr.getLastName());
			params.put(Params.BILLING_ADDR1, billAddr.getAddr1());
			params.put(Params.BILLING_ADDR2, billAddr.getAddr2());
			params.put(Params.BILLING_CITY, billAddr.getCity());
			params.put(Params.BILLING_STATE, billAddr.getState());
			params.put(Params.BILLING_ZIP, billAddr.getZipCode());
			params.put(Params.BILLING_COUNTRY, billAddr.getCountry());
			params.put(Params.BILLING_TEL, billAddr.getPhoneNumber());
			params.put(Params.BILLING_EMAIL, billAddr.getEmailAddr());
		}
		
		if(shipAddr != null){
			params.put(Params.DELIVERY_FIRSTNAME, shipAddr.getFirstName());
			params.put(Params.DELIVERY_LASTNAME, shipAddr.getLastName());
			params.put(Params.DELIVERY_ADDR1, shipAddr.getAddr1());
			params.put(Params.DELIVERY_ADDR2, shipAddr.getAddr2());
			params.put(Params.DELIVERY_CITY, shipAddr.getCity());
			params.put(Params.DELIVERY_STATE, shipAddr.getState());
			params.put(Params.DELIVERY_ZIP, shipAddr.getZipCode());
			params.put(Params.DELIVERY_COUNTRY, shipAddr.getCountry());
			params.put(Params.DELIVERY_TEL, shipAddr.getPhoneNumber());	
		}
		return params;
	}
	
	public static void setRequestParams(HttpServletRequest request,Order order){
		Map<String,String> params = prepareRequestMap(order);
		request.setAttribute(Params.HASH, getHash(outgoingHashSequence, params, false));
		request.setAttribute("params", params);
		request.setAttribute("formUrl", properties.getProperty(Property.PAYU_FORM_URL));
	}
	
	public static String getSalt(){
		return properties.getProperty(Property.PAYU_SALT);
	}
	
	public static String getAccessCode(){
		return properties.getProperty(Property.CC_AVENUE_ACCESS_CODE);
	}
	
	public static HashMap<String,String> parseResponse(HttpServletRequest request){
		HashMap<String,String> hs=new HashMap<String,String>();
		
		Enumeration keys = request.getParameterNames();
		List<KeyValue<Long,Long>> list = new ArrayList<KeyValue<Long, Long>>();
		while(keys.hasMoreElements()){
			String key = (String)keys.nextElement();
			String value = request.getParameter(key);
			hs.put(key, value);
		}
		return hs;
	}
	
	public static MidTrans processResponse(HttpServletRequest request){
		HashMap<String,String> table = parseResponse(request);
		long midTransId = -1;
		try{
			midTransId = Long.valueOf(table.get(Params.ORDER_ID));
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}
		if(midTransId != -1 && verifyHash(table)){
			MidTrans trans = MidTrans.getById(midTransId);
			if(trans != null){
				trans.setTrackingId(table.get(Params.TRACKING_ID));
				trans.setBankRefNo(table.get(Params.BANK_REF_NO));
				trans.setTransStatus(table.get(Params.ORDER_STATUS));
				trans.setFailureMessage(table.get(Params.FAILURE_MESSAGE));
				trans.setMode(table.get(Params.PAYMENT_MODE));
				try{
					trans.dbUpdate();
				}catch(Exception e){
					ExceptionUtils.logException(e);
					return null;
				}
			}
		}
		return null;
	}
	
	private static String hashCal(String type,String str){
		byte[] hashseq=str.getBytes();
		StringBuffer hexString = new StringBuffer();
		try{
		MessageDigest algorithm = MessageDigest.getInstance(type);
		algorithm.reset();
		algorithm.update(hashseq);
		byte messageDigest[] = algorithm.digest();
		for (int i=0;i<messageDigest.length;i++) {
			String hex=Integer.toHexString(0xFF & messageDigest[i]);
			if(hex.length()==1) hexString.append("0");
			hexString.append(hex);
		}	
		}catch(NoSuchAlgorithmException e){
			ExceptionUtils.logException(e);
		}
		return hexString.toString();
	}
	
	private static boolean verifyHash(Map<String,String> params){
		String generatedHash = getHash(incomingHashSequence, params, true);
		if(params.get(Params.HASH) == null || generatedHash == null)
			return false;
		return params.get(Params.HASH).equals(generatedHash);
	}
	
	public static String getHash(String hashSequence, Map<String,String> params, boolean reverse){
		String[] hashVarSeq=hashSequence.split("\\|");
		String hashString="";
		for(String part : hashVarSeq)
		{
			hashString= params.get(part) != null ? hashString.concat(params.get(part)) : hashString.concat("");
			hashString=hashString.concat("|");
		}
		if(!reverse)
			hashString = hashString + getSalt();
		else
			hashString = getSalt() + hashString;
		
		return hashCal("SHA-512",hashString);
	}
	
	public static String getFormUrl(){
		return properties.getProperty(Property.PAYU_FORM_URL);
	}
}
