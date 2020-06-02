package com.appeti.database.table.order.wrap;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.appeti.database.table.order.Order;
import com.appeti.database.table.order.OrderItem;
import com.appeti.database.table.order.wrap.OrderWrap.SellerMap;
import com.appeti.database.table.user.Address;
import com.appeti.database.table.user.Seller;

public class OrderViewWrap {
	
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
	private Address shippingAddr;
	private Address billingAddr;
	private long totalAmount;
	private long discount;
	private long deliveryCharge;
	private long amountPaid;
	private String notes;
	private String trackingId;
	private Map<Long,SellerMap> sellerMap;
	private String state;
	private Date date;
	
	public static OrderViewWrap getWrap(Order order){
		if(order == null)
			return null;
		OrderViewWrap wrap = new OrderViewWrap();
		wrap.setOrderId(order.getOrderId());
		wrap.setBillingAddr(Address.getById(order.getBillingAddrId()));
		wrap.setShippingAddr(Address.getById(order.getShippingAddrId()));
		wrap.setTotalAmount(order.getTotalAmount());
		wrap.setDiscount(order.getDiscount());
		wrap.setDeliveryCharge(order.getDeliveryCharge());
		wrap.setAmountPaid(order.getAmountPaid());
		wrap.setNotes(StringUtils.defaultIfEmpty(order.getNotes(),""));
		wrap.setTrackingId(String.valueOf(order.getTransId()));
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
		wrap.setState(order.getState());
		wrap.setDate(order.getModified());
		return wrap;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public Address getShippingAddr() {
		return shippingAddr;
	}

	public void setShippingAddr(Address shippingAddr) {
		this.shippingAddr = shippingAddr;
	}

	public Address getBillingAddr() {
		return billingAddr;
	}

	public void setBillingAddr(Address billingAddr) {
		this.billingAddr = billingAddr;
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

	public long getDeliveryCharge() {
		return deliveryCharge;
	}

	public void setDeliveryCharge(long deliveryCharge) {
		this.deliveryCharge = deliveryCharge;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTrackingId() {
		return trackingId;
	}

	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}

	public Map<Long,SellerMap> getSellerMap() {
		return sellerMap;
	}

	public void setSellerMap(Map<Long,SellerMap> sellerMap) {
		this.sellerMap = sellerMap;
	}

	}
