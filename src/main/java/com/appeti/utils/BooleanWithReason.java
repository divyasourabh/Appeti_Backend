package com.appeti.utils;

public class BooleanWithReason {
	private boolean status = false;
	private String message = "";
	public BooleanWithReason(boolean status,String message){
		this.status = status;
		this.message = message;
	}

	public boolean getStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
