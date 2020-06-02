package com.appeti.database.table.order.wrap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.appeti.database.table.order.Order;
import com.appeti.database.table.order.OrderItem;

public class OrderMiniWrap {
	private long orderId;
	private Date date;
	private String status;
	private long amount;
	private int numItems;
	
	public static OrderMiniWrap getWrap(Order order){
		if(order == null)
			return null;
		OrderMiniWrap wrap = new OrderMiniWrap();
		wrap.setOrderId(order.getOrderId());
		wrap.setDate(order.getModified());
		wrap.setStatus(order.getState());
		wrap.setAmount(order.getAmountPaid());
		wrap.setNumItems(OrderItem.getItems(order.getOrderId()).size());
		return wrap;
	}
	
	public static List<OrderMiniWrap> getWrapList(List<Order> orders){
		List<OrderMiniWrap> list = new ArrayList<OrderMiniWrap>();
		if(orders != null){
			for(Order order : orders){
				OrderMiniWrap wrap = getWrap(order);
				if(wrap != null)
					list.add(wrap);
			}
		}
		return list;
	}
	
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public int getNumItems() {
		return numItems;
	}
	public void setNumItems(int numItems) {
		this.numItems = numItems;
	}
}
