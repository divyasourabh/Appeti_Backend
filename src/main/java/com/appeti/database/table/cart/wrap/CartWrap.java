package com.appeti.database.table.cart.wrap;

import java.util.ArrayList;
import java.util.List;

import com.appeti.database.table.cart.Cart;
import com.appeti.database.table.cart.CartItem;
import com.appeti.database.table.cart.Coupon;

public class CartWrap {
	private long cartId;
	private long totalAmount;
	private String couponCode;
	private long discount;
	private long discountedAmount;
	private List<CartItemWrap> items;
	
	public CartWrap(){
		this.cartId = -1;
		this.totalAmount = 0;
		items = new ArrayList<CartItemWrap>();
	}
	
	public static CartWrap getWrap(Cart cart){
		if(cart == null)
			return null;
		CartWrap wrap = new CartWrap();
		wrap.setCartId(cart.getCartId());
		wrap.setTotalAmount(cart.getTotalAmount());
		wrap.setDiscountedAmount(cart.getDiscountedAmount());
		Coupon coupon = Coupon.getCouponById(cart.getCouponId());
		if(coupon != null)
			wrap.setCouponCode(coupon.getCode().toUpperCase());
		wrap.setDiscount(cart.getTotalAmount()-cart.getDiscountedAmount());
		List<CartItemWrap> list = new ArrayList<CartItemWrap>();
		for(CartItem item : CartItem.getItems(cart.getCartId())){
			list.add(CartItemWrap.getWrap(item));
		}
		wrap.setItems(list);
		return wrap;
	}
	public long getCartId() {
		return cartId;
	}
	public void setCartId(long cartId) {
		this.cartId = cartId;
	}
	public long getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(long totalAmount) {
		this.totalAmount = totalAmount;
	}
	public long getDiscount() {
		return discount;
	}

	public void setDiscount(long discount) {
		this.discount = discount;
	}

	public long getDiscountedAmount() {
		return discountedAmount;
	}

	public void setDiscountedAmount(long discountedAmount) {
		this.discountedAmount = discountedAmount;
	}

	public List<CartItemWrap> getItems() {
		return items;
	}
	public void setItems(List<CartItemWrap> items) {
		this.items = items;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}
	
	
}
