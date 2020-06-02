package com.appeti.payment;

import java.util.Map;

public class CCAvenuePaymentWrap {
	private Map<String,String> encodedParams;
	
	public CCAvenuePaymentWrap(){}
	
	public CCAvenuePaymentWrap( Map<String,String> params){
		this.encodedParams = params;
	}

	public Map<String,String> getEncodedParams() {
		return encodedParams;
	}

	public void setEncodedParams(Map<String,String> encodedParams) {
		this.encodedParams = encodedParams;
	}
}
