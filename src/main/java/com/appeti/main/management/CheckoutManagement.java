package com.appeti.main.management;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appeti.database.table.cart.Cart;
import com.appeti.database.table.order.Delivery;
import com.appeti.database.table.order.Order;
import com.appeti.database.table.order.wrap.OrderViewWrap;
import com.appeti.database.table.order.wrap.OrderWrap;
import com.appeti.database.table.user.Address;
import com.appeti.database.table.user.User;
import com.appeti.main.beans.CheckoutBean;
import com.appeti.main.services.CartService;
import com.appeti.main.services.CheckoutService;
import com.appeti.payment.CCAvenue;
import com.appeti.payment.CCAvenuePaymentWrap;
import com.appeti.payment.Payu;
import com.appeti.payment.PayuPaymentWrap;
import com.appeti.utils.BooleanWithReason;
import com.appeti.utils.BooleanWithReasonOrder;
import com.appeti.utils.Constants;
import com.appeti.utils.ExceptionUtils;

public class CheckoutManagement extends Management {
	
	private static final String DEAFAULT_PRODUCT_BEAN = "checkoutBean";
	
	public void prepareCheckoutPage(HttpServletRequest request, HttpServletResponse response){
		prepareHeaderBean(request, response);
		long userId = getUserId(request);
		Cart cart = new CartService(request).getCartFromCookie(request, response, userId);
		OrderWrap order = CheckoutService.prepareOrder(cart, true);
		if(order != null)System.out.println("ORDER_ID#"+order.getOrderId());
		List<Address> list = Address.getAddressesForUser(userId);
		CheckoutBean bean = new CheckoutBean();
		bean.setOrder(order);
		bean.setAddress(list);
		bean.setFreeDeliveryAmount(Delivery.getFreeDeliveryAmount());
		request.setAttribute(DEAFAULT_PRODUCT_BEAN, bean);
	}
	
	public BooleanWithReason checkout(HttpServletRequest request, HttpServletResponse response){
		Order order = finalizeOrderForcheckout(request,response);
		log.info("order finalized " + order);
		if(order != null){
			//int gateway = request.getParameter("p_gate") != null ? Integer.valueOf(request.getParameter("p_gate")) : Constants.PaymentGateway.PAY_U;
			//log.info("gateway " + request.getParameter("p_gate"));
			//if(gateway == Constants.PaymentGateway.CC_AVENUE){
				CCAvenue.setRequestParams(request, order);
		//	}else{
			//	Payu.setRequestParams(request, order);
			//}
			return new BooleanWithReason(true, "forwarding payment request");
		}
		return new BooleanWithReason(false, "Some problem occured while processing your order");
	}
	
	public Order finalizeOrderForcheckout(HttpServletRequest request, HttpServletResponse response){
		long userId = getUserId(request);
		
		long addrId = request.getParameter("address") != null ? Long.valueOf(request.getParameter("address")) : -1;
		Address shippingAddr = Address.getById(addrId);
		long orderId = -1;
		try{
			orderId = request.getParameter("oid") != null ? Long.valueOf(request.getParameter("oid")) : -1;
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}
		String notes = request.getParameter("notes") != null ? request.getParameter("notes") : "";
		return CheckoutService.checkout(userId,orderId,shippingAddr,shippingAddr, notes);
	}
	
	public OrderWrap getOrder(HttpServletRequest request, HttpServletResponse response){
		long userId = request.getParameter("userId") != null ? Long.valueOf(request.getParameter("userId")) : -1;
		if(User.getUserById(userId) != null){
			long cartId = request.getParameter("cartId") != null ? Long.valueOf(request.getParameter("cartId")) : -1;
			Cart cart = Cart.getActiveCart(cartId,userId);
			if(cart != null){
				return CheckoutService.prepareOrder(cart,false);
			}
		}
		return null;
	}
	
	public BooleanWithReason paymentResponse(HttpServletRequest request, HttpServletResponse response, String gateway, String source){
		if(!Constants.ClickSaleSource.APP.equals(source))
				prepareHeaderBean(request, response);
		BooleanWithReasonOrder result = CheckoutService.processPaymentResponse(request, gateway, source);
		if(result.getStatus()){
			OrderViewWrap order = CheckoutService.viewOrder(Order.getOrderById(result.getOrderId()));
			CheckoutBean bean = new CheckoutBean();
			bean.setOrderView(order);
			bean.setResult(result);
			request.setAttribute(DEAFAULT_PRODUCT_BEAN, bean);
		}
		return result;
	}
	
	public List<Address> getAddressForUser(HttpServletRequest request, HttpServletResponse response){
		long userId = request.getParameter("userId") != null ? Long.valueOf(request.getParameter("userId")) : -1;
		return Address.getAddressesForUser(userId);
	}
	
	// -------------------------------------- APP -------------------------------- //
	
	public BooleanWithReason mapCartToUser(HttpServletRequest request, HttpServletResponse response){
		long cartId = request.getParameter("cartId") != null ? Long.valueOf(request.getParameter("cartId")) : -1;
		long userId = request.getParameter("userId") != null ? Long.valueOf(request.getParameter("userId")) : -1;
		Cart cart = Cart.mapUser(cartId, User.getUserById(userId));
		if(cart != null){
			return new BooleanWithReason(true, "Cart mapped with user");
		}else{
			return new BooleanWithReason(false, "Invalid request");
		}
	}
	
	public BooleanWithReason mapAddress(HttpServletRequest request, HttpServletResponse response){
		long orderId = request.getParameter("orderId") != null ? Long.valueOf(request.getParameter("orderId")) : -1;
		long addrId = request.getParameter("addrId") != null ? Long.valueOf(request.getParameter("addrId")) : -1;
		return CheckoutService.mapAddress(orderId, addrId);
	}
	
	public BooleanWithReason markDefaultAddress(HttpServletRequest request, HttpServletResponse response){
		long userId = request.getParameter("userId") != null ? Long.valueOf(request.getParameter("userId")) : -1;
		long addrId = request.getParameter("addrId") != null ? Long.valueOf(request.getParameter("addrId")) : -1;
		return CheckoutService.markAddressAsDefault(userId, addrId);
	}
	
	public List<Address> editAddress(HttpServletRequest request, HttpServletResponse response){
		long userId = request.getParameter("userId") != null ? Long.valueOf(request.getParameter("userId")) : -1;
		if(userId == -1)
			userId = getUserId(request);
		if(userId == -1)
			return new ArrayList<Address>();
		long addrId = request.getParameter("addrId") != null ? Long.valueOf(request.getParameter("addrId")) : -1;
		Address newAddress = processAddress(request,userId, "");
		CheckoutService.editAddress(userId, addrId,newAddress);
		return Address.getAddressesForUser(userId);
	}
	
	public List<Address> removeAddress(HttpServletRequest request, HttpServletResponse response){
		long userId = request.getParameter("userId") != null ? Long.valueOf(request.getParameter("userId")) : -1;
		if(userId == -1)
			userId = getUserId(request);
		if(userId == -1)
			return new ArrayList<Address>();
		long addrId = request.getParameter("addrId") != null ? Long.valueOf(request.getParameter("addrId")) : -1;
		CheckoutService.removeAddress(userId, addrId);
		return Address.getAddressesForUser(userId);
	}
	
	public CCAvenuePaymentWrap ccAvenueKeys(HttpServletRequest request, HttpServletResponse response){
		long orderId = request.getParameter("orderId") != null ? Long.valueOf(request.getParameter("orderId")) : -1;
		CCAvenuePaymentWrap wrap = new CCAvenuePaymentWrap();
		Order order = Order.getOrderById(orderId);
		if(order != null){
			wrap.setEncodedParams(CCAvenue.prepareAppRequest(order));
		}
		return wrap;
	}
	
	public PayuPaymentWrap payuKeys(HttpServletRequest request, HttpServletResponse response){
		long orderId = request.getParameter("orderId") != null ? Long.valueOf(request.getParameter("orderId")) : -1;
		PayuPaymentWrap wrap = new PayuPaymentWrap();
		Order order = Order.getOrderById(orderId);
		if(order != null){
			wrap.setParams(Payu.prepareRequestMap(order));
			wrap.setHash(Payu.getHash(Payu.outgoingHashSequence, wrap.getParams(), false));
			wrap.setFormUrl(Payu.getFormUrl());
		}
		return wrap;
	}
}
