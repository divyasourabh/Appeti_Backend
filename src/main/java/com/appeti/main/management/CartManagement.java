package com.appeti.main.management;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appeti.database.table.cart.Cart;
import com.appeti.database.table.cart.CartItem;
import com.appeti.database.table.cart.Coupon;
import com.appeti.database.table.cart.wrap.CartItemAppWrap;
import com.appeti.database.table.cart.wrap.CartWrap;
import com.appeti.database.table.product.Tag;
import com.appeti.main.beans.CartBean;
import com.appeti.main.services.CartService;
import com.appeti.utils.BooleanWithReason;
import com.appeti.utils.CartAjaxWrapper;
import com.appeti.utils.Constants;
import com.appeti.utils.ExceptionUtils;
import com.appeti.utils.KeyValue;

public class CartManagement extends Management{
	
	private static final String DEAFAULT_CART_BEAN = "cartBean";
	private static final String KEY_DELMITER = "-";
	
	public CartAjaxWrapper addToCart(HttpServletRequest request, HttpServletResponse response){
		long tagId = request.getParameter("tagId") != null ? Long.valueOf(request.getParameter("tagId")) : -1;
		long quantity = request.getParameter("quantity") != null ? Long.valueOf(request.getParameter("quantity")) : -1;

		// check if request is valid
		if(!Tag.isValidTag(tagId))
			return new CartAjaxWrapper(false, "Invalid product..please try again!!");

		if(quantity <= 0)
			return new CartAjaxWrapper(false, "Please enter a valid quantity..!!");

		CartService service = new CartService(request);
		long userId = getUserId(request);
		Cart cart = service.getCartFromCookie(request, response, userId);
		long itemId = CartService.addItemToCart(cart, tagId, quantity);
		if(itemId != -1){
			return new CartAjaxWrapper(true, " added to your cart");
		}
		return new CartAjaxWrapper(false, "Some problem occured while adding your item. Please try again!!");
	}
	
	public CartAjaxWrapper removeFromCart(HttpServletRequest request, HttpServletResponse response){
		long itemId = request.getParameter("id") != null ? Long.valueOf(request.getParameter("id")) : -1;
		long cartId = request.getParameter("cid") != null ? Long.valueOf(request.getParameter("cid")) : -1;
		
		// check if request is valid
		if(!CartItem.isValidItem(itemId, cartId))
			return new CartAjaxWrapper(false, "Invalid product..please try again!!");
		
		CartService service = new CartService(request);
		long userId = getUserId(request);
		Cart cart = service.getCartFromCookie(request, response, userId);
		long amount = CartService.removeItemFromCart(cart, itemId);
		if(amount != 0){
			return new CartAjaxWrapper(true, "Item removed from cart", cart.getTotalAmount(), cart.getDiscountedAmount());
		}
		else
			return new CartAjaxWrapper(true, "Cart is empty");
	}
	
	public CartWrap viewCart(HttpServletRequest request, HttpServletResponse response){
		prepareHeaderBean(request, response);
		CartBean bean = new CartBean();
		CartService service = new CartService(request);
		long userId = getUserId(request);
		CartWrap cart = CartWrap.getWrap(service.getCartFromCookie(request, response, userId));
		int paymentResponse = -1;
		try{
			paymentResponse = Integer.valueOf(request.getParameter("code"));
		}catch(Exception e){}
		
		if(Constants.PaymentResponse.FAILURE == paymentResponse)
			bean.setResult(new BooleanWithReason(false, "Payment Failed"));
		bean.setCart(cart);
		request.setAttribute(DEAFAULT_CART_BEAN, bean);
		return cart;
	}
	
	public BooleanWithReason applyCoupon(HttpServletRequest request, HttpServletResponse response){
		String code = request.getParameter("coupon_code") != null ? String.valueOf(request.getParameter("coupon_code")) : "";
		long cartId = request.getParameter("cid") != null ? Long.valueOf(request.getParameter("cid")) : -1;
		
		if(!Coupon.isValidCoupon(code))
			return new BooleanWithReason(false, "Coupon is either invalid or expired");
		
		Cart cart = CartService.applyCoupon(cartId, code);
		if(cart != null)
			return new BooleanWithReason(true, "Coupon applied successfully");
		else
			return new BooleanWithReason(false, "Coupon is either invalid or expired");
	}
	
	public BooleanWithReason validateCoupon(HttpServletRequest request, HttpServletResponse response){
		String code = request.getParameter("coupon_code") != null ? String.valueOf(request.getParameter("coupon_code")) : "";
		if(!Coupon.isValidCoupon(code))
			return new BooleanWithReason(false, "Coupon is either invalid or expired");
		
		return new BooleanWithReason(true, "Coupon Applicable");
	}
	
	public BooleanWithReason updateCart(HttpServletRequest request, HttpServletResponse response){
		long cartId = request.getParameter("cartId") != null ? Long.valueOf(request.getParameter("cartId")) : -1;
		Enumeration keys = request.getParameterNames();
		List<KeyValue<Long,Long>> list = new ArrayList<KeyValue<Long, Long>>();
		while(keys.hasMoreElements()){
			String key = (String)keys.nextElement();
			String[] str = key.split(KEY_DELMITER);
			if(str.length >= 4){
				Long itemId = -1l;
				Long quantity = 0l;
				try{
					itemId = Long.valueOf(str[3]);
					quantity = Long.valueOf(request.getParameter(key));
					if(quantity < 0)
						quantity = 0l;
				}catch(Exception e){
					ExceptionUtils.logException(e);
				}
				if(itemId != -1){
					list.add(new KeyValue<Long, Long>(itemId, quantity));
				}
			}
		}
		Cart cart = CartService.updateItems(cartId,list);
		if(cart != null)
			return new BooleanWithReason(true, "Cart Updated");
		else
			return new BooleanWithReason(false, "Cart Expired");
 	}
	// ---------------------------------- APP CALLS -------------------------------- //
	
	public CartItemAppWrap addToAppCart(HttpServletRequest request, HttpServletResponse response){
		CartItemAppWrap wrap = new CartItemAppWrap();
		long cartId = request.getParameter("cartId") != null ? Long.valueOf(request.getParameter("cartId")) : -1;
		long userId = request.getParameter("userId") != null ? Long.valueOf(request.getParameter("userId")) : -1;
		long tagId = request.getParameter("tagId") != null ? Long.valueOf(request.getParameter("tagId")) : -1;
		long quantity = request.getParameter("quantity") != null ? Long.valueOf(request.getParameter("quantity")) : -1;
		
		wrap.setUserId(userId);
		// check if request is valid
		if(!Tag.isValidTag(tagId)){
			wrap.setStatus(false);
			wrap.setMessage("Invalid product..please try again!!");
			return wrap;
		}
		if(quantity <= 0){
			wrap.setStatus(false);
			wrap.setMessage("Please enter a valid quantity..!!");
			return wrap;
		}
			
		Cart cart = Cart.getActiveCartById(cartId);
		if(cart == null)
			cart = Cart.createCart(userId);
		if(cart != null){
			long itemId = CartService.addItemToCart(cart, tagId, quantity);
			wrap.setCartId(cart.getCartId());
			wrap.setCartItemId(itemId);
			wrap.setStatus(true);
			wrap.setTotalAmount(cart.getTotalAmount());
			wrap.setMessage("Item added to your cart");
		}
		return wrap;
	}
	
	public CartItemAppWrap removeFromAppCart(HttpServletRequest request, HttpServletResponse response){
		CartItemAppWrap wrap = new CartItemAppWrap();
		long cartId = request.getParameter("cartId") != null ? Long.valueOf(request.getParameter("cartId")) : -1;
		long itemId = request.getParameter("cartItemId") != null ? Long.valueOf(request.getParameter("cartItemId")) : -1;
		long userId = request.getParameter("userId") != null ? Long.valueOf(request.getParameter("userId")) : -1;
		
		wrap.setUserId(userId);
		
		if(!CartItem.isValidItem(itemId, cartId)){
			wrap.setStatus(false);
			wrap.setMessage("Invalid product..please try again!!");
			return wrap;
		}
		
		Cart cart = Cart.getActiveCartById(cartId);
		if(cart != null){
			long amount = CartService.removeItemFromCart(cart, itemId);
			wrap.setCartId(cart.getCartId());
			wrap.setTotalAmount(amount);
			wrap.setStatus(true);
			wrap.setMessage("Item removed from cart");
		}
		return wrap;
	}
	
	public CartWrap viewAppCart(HttpServletRequest request, HttpServletResponse response){
		long cartId = request.getParameter("cartId") != null ? Long.valueOf(request.getParameter("cartId")) : -1;
		long userId = request.getParameter("userId") != null ? Long.valueOf(request.getParameter("userId")) : -1;
		
		CartWrap cart = CartWrap.getWrap(Cart.getActiveCartById(cartId, userId));
		if(cart == null){
			cart = new CartWrap();
		}
		return cart;
	}
	
	public CartWrap updateAppCart(HttpServletRequest request, HttpServletResponse response){
		long cartId = request.getParameter("cartId") != null ? Long.valueOf(request.getParameter("cartId")) : -1;
		long cartItemId = request.getParameter("cartItemId") != null ? Long.valueOf(request.getParameter("cartItemId")) : -1;
		long quantity = request.getParameter("quantity") != null ? Long.valueOf(request.getParameter("quantity")) : -1;
		
		Enumeration keys = request.getParameterNames();
		List<KeyValue<Long,Long>> list = new ArrayList<KeyValue<Long, Long>>();
		list.add(new KeyValue<Long, Long>(cartItemId, quantity));
		Cart cart = CartService.updateItems(cartId,list);
		if(cart != null)
			return CartWrap.getWrap(cart);
		else
			return new CartWrap();
 	}
}
