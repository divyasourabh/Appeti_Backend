package com.appeti.main.services;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appeti.database.table.cart.Cart;
import com.appeti.database.table.cart.CartItem;
import com.appeti.database.table.cart.Coupon;
import com.appeti.database.table.order.Order;
import com.appeti.database.table.product.Tag;
import com.appeti.utils.ExceptionUtils;
import com.appeti.utils.KeyValue;
import com.appeti.utils.NumberUtils;
import com.appeti.utils.encryption.BasicEncryptionManager;

public class CartService extends Service {
	
	public CartService(HttpServletRequest request){
		super(request);
	}
	
	public static long addItemToCart(Cart cart, long tagId, long quantity){
		try{
			if(cart != null){
				Tag tag = Tag.getTagById(tagId);
				long itemId = CartItem.addItem(cart.getCartId(), tag.getTagId(), tag.getPtitleId(), tag.getSellerId(), quantity, tag.getPricePerUnit());
				Cart.updateCart(cart);
				return itemId;
			}
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}
		return -1;
	}
	
	public Cart getCartFromCookie(HttpServletRequest request, HttpServletResponse response, long userId){
		final BasicEncryptionManager encryptManager = BasicEncryptionManager.getInstance();
		Cart cart = null;
		final Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (final Cookie cookie : cookies) {
				if ("cartId".equals(cookie.getName())) {
					final String cartId = encryptManager.decrypt(cookie.getValue());
					if ((cartId != null) && (cartId.length() > 0)) {
						final Long cid = NumberUtils.stringToScalarLong(cartId, 0);
						if (cid != 0) {
							cart = Cart.getCartById(cid);
						}
						if ((cart != null) && (!cart.isActive())) {
							cart = null;
						}
					}
					break;
				}
			}
		}
		if (cart == null) {
			cart = Cart.createCart(userId);
		
		// Otherwise, map this cart to the current user, if one exists.
		// However, don't map the cart to the user if it's already been 
		// checked out.
		} else {
			cart = Cart.mapUser(cart.getCartId(), userId);
		}
		
		//set cart cookie
		if (cart != null) {
			final String encryptedString =
				encryptManager.encrypt(String.valueOf(cart.getCartId()));
			final Cookie cookie = new Cookie("cartId", encryptedString);
			cookie.setPath("/");
			cookie.setMaxAge(604800); //7 days *24*60*60 = 604800 seconds
			response.addCookie(cookie);
		}
		if(cart != null)System.out.println("CART_ID#"+cart.getCartId());
		return cart;
	}
	
	public static long removeItemFromCart(Cart cart, long itemId){
		try{
			if(cart != null){
				int modified = CartItem.removeItem(itemId);
				Cart.updateCart(cart);
				log.info("Removed " + modified + " items in cart#" + cart.getCartId());
				return cart.getTotalAmount();
			}
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}
		return 0;
	}
	
	public static Cart applyCoupon(long cartId, String code){
		Coupon coupon = Coupon.getCoupon(code);
		return Cart.applyCoupon(cartId, coupon);
	}
	
	public static void removeCoupon(long cartId){
		Cart cart = Cart.getCartById(cartId, false);
		if(cart != null){
			cart.setCouponId(-1);
			Cart.updateCart(cart);
			Order order = Order.getOrderForCartId(cartId);
			if(order != null)
				Order.refreshOrder(order, cart);
		}
	}
	
	public static Cart updateItems(long cartId, List<KeyValue<Long, Long>> list){
		Cart cart = Cart.getActiveCartById(cartId);
		if(cart != null){
			for(KeyValue<Long, Long> item : list){
				CartItem.updateItem(item.getKey(), item.getValue());
			}
			Cart.updateCart(cart);
		}
		return cart;
	}
}
