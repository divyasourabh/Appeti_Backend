package com.appeti.main.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.appeti.database.table.order.Order;
import com.appeti.database.table.order.wrap.OrderMiniWrap;
import com.appeti.database.table.user.LoginInfo;
import com.appeti.database.table.user.User;
import com.appeti.utils.BooleanWithReason;
import com.appeti.utils.ExceptionUtils;

public class AccountService extends Service{

	public AccountService(HttpServletRequest request) {
		super(request);
	}

	public static List<OrderMiniWrap> getOrderList(long userId){
		List<Order> orders = Order.getItems(userId);
		return OrderMiniWrap.getWrapList(orders);
	}
	
	public static List<OrderMiniWrap> getPreviousOrderList(long userId){
		List<Order> orders = Order.getPreviousOrders(userId);
		return OrderMiniWrap.getWrapList(orders);
	}
	
	public static BooleanWithReason editAccount(long userId, String currentPassword, String password, String fName, String lName){
		User user = User.getUserById(userId);
		if(user != null){
			if(StringUtils.isNotBlank(password)){
				LoginInfo info = LoginInfo.getByUserId(userId);
				if(info != null && LoginInfo.validatePassword(userId, currentPassword)){
					try{
						info.setPassword(password);
						info.dbUpdate();
					}catch(Exception e){
						ExceptionUtils.logException(e);
					}
				}else if(info == null){
					LoginInfo.createLogin(userId, user.getEmailAddress(), password);
				}
				else{
					return new BooleanWithReason(false, "Incorrect Password");
				}
			}
			if(StringUtils.isNotBlank(fName))
				user.setFirstName(fName);
			if(StringUtils.isNotBlank(lName))
				user.setLastName(lName);
			user.updateDB();
			return new BooleanWithReason(true, "Account Updated");
		}
		return new BooleanWithReason(false, "Some problem occured while saving your details. Please try again later.");
	}
}
