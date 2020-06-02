package com.appeti.database.table.order.wrap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.appeti.database.table.cart.Coupon;
import com.appeti.database.table.order.Order;
import com.appeti.database.table.order.OrderItem;
import com.appeti.database.table.user.Address;
import com.appeti.database.table.user.Seller;

public class OrderWrap {
	
	public class SellerMap{
		String name;
		List<OrderItemWrap> items;
		
		public SellerMap(long sellerId){
			Seller seller = Seller.getById(sellerId);
			if(seller != null){
				this.name = seller.getBrandName();
			}
			this.items = new ArrayList<OrderItemWrap>();
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public List<OrderItemWrap> getItems() {
			return items;
		}
		public void setItems(List<OrderItemWrap> items) {
			this.items = items;
		}
	}
	private long orderId;
	private long cartId;
	private long shippingAddrId;
	private long billingAddrId;
	private List<Address> addresses;
	private long totalAmount;
	private String couponCode;
	private long discount;
	private long deliveryCharge;
	private List<TaxWrap> taxList;
	private long taxAmount;
	private long amountPaid;
	private String notes;
	private Map<Long,SellerMap> sellerMap;
	
	public static OrderWrap getWrap(Order order){
		return getWrap(order, true);
	}
	
	public static OrderWrap getWrap(Order order, boolean setAddress){
		if(order == null)
			return null;
		OrderWrap wrap = new OrderWrap();
		wrap.setOrderId(order.getOrderId());
		wrap.setCartId(order.getCartId());
		wrap.setShippingAddrId(order.getShippingAddrId());
		wrap.setBillingAddrId(order.getBillingAddrId());
		//if(setAddress)
			//wrap.setAddresses(Address.getAddressesForUser(order.getUserId()));
		wrap.setTotalAmount(order.getTotalAmount());
		Coupon coupon = Coupon.getCouponById(order.getCouponId());
		if(coupon != null)
			wrap.setCouponCode(coupon.getCode().toUpperCase());
		wrap.setDiscount(order.getDiscount());
		wrap.setDeliveryCharge(order.getDeliveryCharge());
		//wrap.setTaxList(TaxWrap.getWrapList((double)(order.getTotalAmount() - order.getDiscount())));
		wrap.setTaxAmount(order.getTaxAmount());
		wrap.setAmountPaid(order.getAmountPaid());
		wrap.setNotes(StringUtils.defaultIfEmpty(order.getNotes(),""));
		List<OrderItemWrap> items = OrderItemWrap.getWrapList(OrderItem.getItems(order.getOrderId()));
		Map<Long,SellerMap> sellerMap = new HashMap<Long,SellerMap>();
		for(OrderItemWrap item : items){
			long sellerId = item.getSellerId();
			SellerMap obj = sellerMap.get(sellerId);
			if(obj == null){
				obj = wrap.new SellerMap(sellerId);
			}
			obj.items.add(item);
			sellerMap.put(sellerId, obj);
		}
		wrap.setSellerMap(sellerMap);
		return wrap;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public long getCartId() {
		return cartId;
	}

	public void setCartId(long cartId) {
		this.cartId = cartId;
	}

	public long getShippingAddrId() {
		return shippingAddrId;
	}

	public void setShippingAddrId(long shippingAddrId) {
		this.shippingAddrId = shippingAddrId;
	}

	public long getBillingAddrId() {
		return billingAddrId;
	}

	public void setBillingAddrId(long billingAddrId) {
		this.billingAddrId = billingAddrId;
	}

	public long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public long getDiscount() {
		return discount;
	}

	public void setDiscount(long discount) {
		this.discount = discount;
	}

	public long getDeliveryCharge() {
		return deliveryCharge;
	}

	public void setDeliveryCharge(long deliveryCharge) {
		this.deliveryCharge = deliveryCharge;
	}

	public List<TaxWrap> getTaxList() {
		return taxList;
	}

	public void setTaxList(List<TaxWrap> taxList) {
		this.taxList = taxList;
	}

	public long getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(long taxAmount) {
		this.taxAmount = taxAmount;
	}

	public long getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(long amountPaid) {
		this.amountPaid = amountPaid;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public Map<Long, SellerMap> getSellerMap() {
		return sellerMap;
	}

	public void setSellerMap(Map<Long, SellerMap> sellerMap) {
		this.sellerMap = sellerMap;
	}
}
