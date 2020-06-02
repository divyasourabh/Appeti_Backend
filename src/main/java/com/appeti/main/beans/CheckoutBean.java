package com.appeti.main.beans;

import java.util.List;

import com.appeti.database.table.order.wrap.OrderViewWrap;
import com.appeti.database.table.order.wrap.OrderWrap;
import com.appeti.database.table.user.Address;
import com.appeti.utils.BooleanWithReason;

public class CheckoutBean {
	private OrderWrap order;
	private List<Address> address;
	private OrderViewWrap orderView;
	private BooleanWithReason result;
	private long freeDeliveryAmount;
	
	public OrderWrap getOrder() {
		return order;
	}

	public void setOrder(OrderWrap order) {
		this.order = order;
	}

	public OrderViewWrap getOrderView() {
		return orderView;
	}

	public void setOrderView(OrderViewWrap orderView) {
		this.orderView = orderView;
	}

	public BooleanWithReason getResult() {
		return result;
	}

	public void setResult(BooleanWithReason result) {
		this.result = result;
	}

	public List<Address> getAddress() {
		return address;
	}

	public void setAddress(List<Address> address) {
		this.address = address;
	}

	public long getFreeDeliveryAmount() {
		return freeDeliveryAmount;
	}

	public void setFreeDeliveryAmount(long freeDeliveryAmount) {
		this.freeDeliveryAmount = freeDeliveryAmount;
	}
}
