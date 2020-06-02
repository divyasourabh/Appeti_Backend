package com.appeti.main.beans;

import java.util.List;

import com.appeti.database.table.order.wrap.OrderMiniWrap;
import com.appeti.database.table.order.wrap.OrderViewWrap;
import com.appeti.utils.BooleanWithReason;

public class AccountBean {
	private OrderViewWrap order;
	private BooleanWithReason result;
	private String email;
	private String fName;
	private String lName;
	
	private List<OrderMiniWrap> orderList;
	
	public OrderViewWrap getOrder() {
		return order;
	}

	public void setOrder(OrderViewWrap order) {
		this.order = order;
	}

	public BooleanWithReason getResult() {
		return result;
	}

	public void setResult(BooleanWithReason result) {
		this.result = result;
	}

	public List<OrderMiniWrap> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<OrderMiniWrap> orderList) {
		this.orderList = orderList;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}
	
}
