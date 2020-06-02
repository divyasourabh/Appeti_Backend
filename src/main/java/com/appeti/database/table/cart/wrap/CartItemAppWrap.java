package com.appeti.database.table.cart.wrap;

public class CartItemAppWrap {
	private long cartId;
	private long totalAmount;
	private long cartItemId;
	private long userId;
	private boolean status;
	private String message;
	
	public CartItemAppWrap(){
		this.cartId = -1;
		this.cartItemId = -1;
		this.userId = -1;
		this.status = false;
		this.message = "";
	}
	
	public CartItemAppWrap(long cartId, long amount, long cartItemId, long userId, boolean status, String message){
		this.cartId = cartId;
		this.totalAmount = amount;
		this.cartItemId = cartItemId;
		this.userId = userId;
		this.status = status;
		this.message = message;
	}
	
	public long getCartId() {
		return cartId;
	}
	public void setCartId(long cartId) {
		this.cartId = cartId;
	}
	public long getCartItemId() {
		return cartItemId;
	}
	public void setCartItemId(long cartItemId) {
		this.cartItemId = cartItemId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
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

	public long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(long totalAmount) {
		this.totalAmount = totalAmount;
	}
	
}
