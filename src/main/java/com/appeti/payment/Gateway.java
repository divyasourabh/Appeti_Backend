package com.appeti.payment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.appeti.database.table.order.Order;
import com.appeti.database.table.payment.MidTrans;
import com.appeti.utils.ExceptionUtils;

public abstract class Gateway {
	
	protected static Properties properties = null;
	private static final String PROPERTY_FILE_PATH = "/properties/gateway.properties";
	
	protected static long getMidTransId(Order order, String source){
		return MidTrans.createTrans(order.getOrderId(), order.getUserId(), source, order.getAmountPaid());
	}
	
	static{
		loadProperties();
	}
	
	private static void loadProperties(){
		if(properties == null){
			try {
				properties = new Properties();
				properties.load(new FileInputStream(PROPERTY_FILE_PATH));
			} catch (FileNotFoundException e) {
				ExceptionUtils.logException(e);
			} catch (IOException e) {
				ExceptionUtils.logException(e);
			}
		}
	}
}
