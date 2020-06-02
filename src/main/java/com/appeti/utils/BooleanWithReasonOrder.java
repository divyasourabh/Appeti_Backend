package com.appeti.utils;

public class BooleanWithReasonOrder extends BooleanWithReason{
	private long orderId;
	
	public BooleanWithReasonOrder(boolean status, String message){
		super(status, message);
	}
	public BooleanWithReasonOrder(boolean status, String message, long orderId) {
		super(status, message);
		this.orderId = orderId;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
}
