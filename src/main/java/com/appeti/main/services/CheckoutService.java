package com.appeti.main.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.appeti.database.table.aggregation.SalesTrack;
import com.appeti.database.table.cart.Cart;
import com.appeti.database.table.cart.CartItem;
import com.appeti.database.table.cart.Coupon;
import com.appeti.database.table.invoice.Invoice;
import com.appeti.database.table.invoice.InvoiceLine;
import com.appeti.database.table.order.Delivery;
import com.appeti.database.table.order.Order;
import com.appeti.database.table.order.OrderItem;
import com.appeti.database.table.order.Tax;
import com.appeti.database.table.order.wrap.OrderWrap;
import com.appeti.database.table.payment.MidTrans;
import com.appeti.database.table.payment.Trans;
import com.appeti.database.table.product.Tag;
import com.appeti.database.table.user.Address;
import com.appeti.database.table.user.User;
import com.appeti.mail.MailUtils;
import com.appeti.payment.CCAvenue;
import com.appeti.payment.Payu;
import com.appeti.sms.SMSUtils;
import com.appeti.utils.BooleanWithReason;
import com.appeti.utils.BooleanWithReasonOrder;
import com.appeti.utils.Constants;
import com.appeti.utils.ExceptionUtils;

public class CheckoutService extends Service {

	public CheckoutService(HttpServletRequest request) {
		super(request);
	}

	public static OrderWrap prepareOrder(Cart cart, boolean setAdresses){
		Order order = Order.getOrderForCartId(cart.getCartId());
		if(order == null){
			order = Order.prepareOrderFromCart(cart);
		}else{
			OrderItem.purgeRemovedCartItems(order);
			order = Order.refreshOrder(order,cart);
		}
		if(order != null){
			List<CartItem> items = CartItem.getItems(cart.getCartId());
			for(CartItem item : items){
				System.out.println("item#"+item.getCartItemId());
				System.out.println(OrderItem.getItem(order.getOrderId(), item));
			}
		}
		//order = calculateTaxes(order);
		order = calculateDeliveryCharges(order);
		order = updateTotalAmount(order);
		return OrderWrap.getWrap(order, setAdresses);
	}
	
	public static Order calculateTaxes(Order order){
		if(order == null)
			return null;
		try{
			List<Tax> taxList = Tax.getAllTaxes();
			double totalTax = 0d;
			double orderAmount = order.getTotalAmount() - order.getDiscount();
			for(Tax tax : taxList){
				totalTax += (double)orderAmount * tax.getPercent()/100; 
			}
			order.setTaxAmount((long)totalTax);
			order.setAmountPaid((long)(orderAmount + totalTax));
			order.dbUpdate();
			return order;
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}
		return null;
	}
	
	public static Order calculateDeliveryCharges(Order order){
		if(order == null)
			return null;
		try{
			double totalDelivery = 0d;
			if(order.getTotalAmount() < Delivery.getFreeDeliveryAmount()){
				List<OrderItem> items = OrderItem.getItems(order.getOrderId());
				for(OrderItem item : items){
					totalDelivery += item.getQuantity() * Delivery.getCharge(Tag.getTagById(item.getTagId()).getCompressedUnitString());
				}
			}
			order.setDeliveryCharge((long)totalDelivery);
			order.dbUpdate();
			return order;
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}
		return null;
	}
	
	public static Order updateTotalAmount(Order order){
		if(order == null)
			return null;
		try{
			order.setAmountPaid(order.getTotalAmount() - order.getDiscount() + order.getDeliveryCharge());
			order.dbUpdate();
			return order;
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}
		return null;
	}
	
	public static Order checkout(long userId, long orderId, Address billAddr, Address shipAddr, String notes){
		Order order = Order.getOrderById(orderId);
		if(order != null && order.getUserId() == userId){
			Address addr1 = Address.insert(billAddr);
			markAddressAsDefault(addr1);
			Address addr2 = Address.insert(shipAddr);
			if(addr2 == null)
				addr2 = addr1;
			try{
				order.setBillingAddrId(addr1.getId());
				order.setShippingAddrId(addr2.getId());
				order.setNotes(notes);
				order.setState(Constants.OrderState.PROCESSING);
				Coupon.markProcessed(order.getCouponId(), order.getCartId());
				order.dbUpdate();
				return order;
			}catch(Exception e){
				ExceptionUtils.logException(e);
			}
			return null;
		}
		return null;
	}
	
	public static BooleanWithReason markAddressAsDefault(long userId, long addrId){
		Address addr = Address.getById(userId, addrId);
		BooleanWithReason result = new BooleanWithReason(false, "");
		if(addr != null){
			List<Address> addresses = Address.getAddressesForUser(addr.getUserId());
			for(Address a : addresses){
				Address.markDefaultAs(a, false);
			}
			result.setStatus(Address.markDefaultAs(addr, true));
		}
		return result;
	}
	
	public static void markAddressAsDefault(Address addr){
		if(addr != null){
			List<Address> addresses = Address.getAddressesForUser(addr.getUserId());
			for(Address a : addresses){
				Address.markDefaultAs(a, false);
			}
			Address.markDefaultAs(addr, true);
		}
	}
	
	public static BooleanWithReason mapAddress(long orderId, long addrId){
		Order order = Order.getOrderById(orderId);
		String message = "Invalid order";
		if(order != null){
			if(Address.getById(order.getUserId(), addrId) != null){
				try{
					order.setBillingAddrId(addrId);
					order.setShippingAddrId(addrId);
					order.dbUpdate();
					return new BooleanWithReason(true, "Address selected");
				}catch(Exception e){
					ExceptionUtils.logException(e);
					message = "Some problem occured while mapping address";
				}
			}
			message = "Invalid address";
		}
		return new BooleanWithReason(false, message);
	}
	
	public static BooleanWithReason editAddress(long userId, long addrId, Address newAddress){
		String message = "";
		if(User.getUserById(userId) == null){
			message = "Invalid user";
		}else{
			Address oldAddress = Address.getById(userId, addrId);
			if(oldAddress != null){
				Address.markRemoved(oldAddress);
			}
			try{
				Address addr = Address.insert(newAddress);
				if(addr.isDefault())
					CheckoutService.markAddressAsDefault(userId, addrId);
				return new BooleanWithReason(true, "Address edited");
			}catch(Exception e){
				ExceptionUtils.logException(e);
				message = "Some problem occured while mapping address";
			}
		}
		return new BooleanWithReason(false, message);
	}
	
	public static BooleanWithReason removeAddress(long userId, long addrId){
		Address oldAddress = Address.getById(userId, addrId);
		if(oldAddress != null){
			Address.markRemoved(oldAddress);
		}
		return new BooleanWithReason(true, "Address removed");
	}
	
	public static BooleanWithReasonOrder processPaymentResponse(HttpServletRequest request, String gateway, String source){
		MidTrans midTrans = null;
		if(Constants.Source.PAY_U.equals(gateway)){
			midTrans = Payu.processResponse(request);
		}else if(Constants.Source.CC_AVENUE.equals(gateway)){
			midTrans = CCAvenue.processResponse(request);
		}
		
		if(midTrans != null && Constants.PaymentStatus.SUCCESS.equals(midTrans.getTransStatus())){
			Trans trans = Trans.createTrans(midTrans);
			if(trans != null){
				Order order = Order.getOrderById(trans.getOrderId());
				if(order != null){
					order.setTransId(trans.getTransId());
					order.setState(Constants.OrderState.ORDERED);
					try{
						order.dbUpdate();
						Cart.updateCartState(order.getCartId(), Constants.CartState.ORDERED);
						Coupon.markUsed(order.getCouponId(), order.getCartId());
						postProcessOrder(order, source);
						return new BooleanWithReasonOrder(true, midTrans.getTransStatus() + ": " + "Your order has been placed.", order.getOrderId());
					}catch(Exception e){
						ExceptionUtils.logException(e);
					}
				}
			}
		}else if(midTrans != null){
			Order order = Order.getOrderById(midTrans.getOrderId());
			if(order != null){
				order.setState(Constants.OrderState.ABANDONED);
				try{
					Coupon.markActive(order.getCouponId());
					order.dbUpdate();
				}catch(Exception e){
					ExceptionUtils.logException(e);
				}
			}
		}
		return new BooleanWithReasonOrder(false, midTrans.getTransStatus() + ": " + "Payment failed.");
	}
	
	private static Invoice generateAndMailInvoice(Order order){
		Invoice inv = generateInvoice(order);
		if(inv != null){
			Address billingAddr = Address.getById(inv.getBillingAddrId());
			Address shippingAddr = Address.getById(inv.getShippingAddrId());
			boolean result = false;
			if(billingAddr != null){
				result = MailUtils.sendInvoice(inv, billingAddr.getEmailAddr(), billingAddr.getPhoneNumber(), shippingAddr);
				SMSUtils.sendNewOrderNotification(inv.getOrderId(), billingAddr.getPhoneNumber());
			}if(result){
				// change inv state to mailed
			}
		}
		return inv;
	}
	
	private static Invoice generateInvoice(Order order){
		Invoice inv = Invoice.prepareInvoiceFromOrder(order);
		if(inv != null){
			List<OrderItem> items = OrderItem.getItems(order.getOrderId());
			for(OrderItem item : items){
				InvoiceLine.createLine(inv.getInvoiceId(), item);
			}
		}
		return inv;
	}
	
	private static void recordSales(Order order, String source){
		List<OrderItem> items = OrderItem.getItems(order.getOrderId());
		for(OrderItem item : items){
			Tag tag = Tag.getTagById(item.getTagId());
			if(tag != null)
				SalesTrack.record(tag.getProductId(), tag.getPtitleId(), tag.getTagId(), item.getQuantity(), tag.getSellerId(), source);
		}
	}

	
	private static void postProcessOrder(final Order order, final String source){
		Runnable postProcess = new Runnable() {
			@Override
			public void run() {
				generateAndMailInvoice(order);
				recordSales(order, source);
			}
		};
		Thread thread = new Thread(postProcess);
		thread.start();
	}
}
