package com.appeti.utils;

public class CartAjaxWrapper {
	private boolean status = false;
	private String message = "";
	private String cartAmount = "0";
	private String discount = "0";
	private String totalAmount = "0";
	
	public CartAjaxWrapper(boolean status,String message){
		this.status = status;
		this.message = message;
	}
	
	public CartAjaxWrapper(boolean status,String message, long amount){
		this.status = status;
		this.message = message;
		this.cartAmount = String.valueOf(amount);
	}
	
	public CartAjaxWrapper(boolean status,String message, long amount, long totalAmount){
		this.status = status;
		this.message = message;
		this.cartAmount = String.valueOf(amount);
		this.discount = String.valueOf(amount-totalAmount);
		this.totalAmount = String.valueOf(totalAmount);
	}

	public CartAjaxWrapper(boolean status,String message, long amount, long discount, long totalAmount){
		this.status = status;
		this.message = message;
		this.cartAmount = String.valueOf(amount);
		this.discount = String.valueOf(discount);
		this.totalAmount = String.valueOf(totalAmount);
	}
	
	public boolean isStatus() {
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
	public String getCartAmount() {
		return cartAmount;
	}
	public void setCartAmount(String cartAmount) {
		this.cartAmount = cartAmount;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	
}
