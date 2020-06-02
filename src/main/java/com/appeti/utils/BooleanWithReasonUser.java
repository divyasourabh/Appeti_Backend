package com.appeti.utils;

public class BooleanWithReasonUser extends BooleanWithReason{
	private long userId;
	
	public BooleanWithReasonUser(boolean status, String message, long userId){
		super(status,message);
		this.userId = userId;
	}
	
	public BooleanWithReasonUser(boolean status, String message){
		super(status,message);
		this.userId = -1;
	}
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
}
