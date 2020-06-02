package com.appeti.main.beans;

import com.appeti.database.table.cart.wrap.CartWrap;
import com.appeti.utils.BooleanWithReason;

public class CartBean {
	private CartWrap cart;
	private BooleanWithReason result;
	
	public CartWrap getCart() {
		return cart;
	}

	public void setCart(CartWrap cart) {
		this.cart = cart;
	}

	public BooleanWithReason getResult() {
		return result;
	}

	public void setResult(BooleanWithReason result) {
		this.result = result;
	}
}
