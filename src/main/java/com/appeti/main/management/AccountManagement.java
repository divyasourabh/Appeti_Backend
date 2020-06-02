package com.appeti.main.management;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.appeti.database.table.order.wrap.OrderMiniWrap;
import com.appeti.database.table.order.wrap.OrderViewWrap;
import com.appeti.database.table.user.User;
import com.appeti.main.beans.AccountBean;
import com.appeti.main.services.AccountService;
import com.appeti.utils.BooleanWithReason;
import com.appeti.utils.BooleanWithReasonUser;
import com.appeti.utils.Constants;
import com.appeti.utils.CryptWithMD5;
import com.appeti.utils.ExceptionUtils;

public class AccountManagement extends Management{
	
	private static final String DEFAULT_ACCOUNT_BEAN = "accBean";
	
	public void setAccountPage(HttpServletRequest request, HttpServletResponse response){
		prepareHeaderBean(request, response);
		AccountBean bean = new AccountBean();
		long userId = getUserId(request);
		if(userId == -1){
			bean.setResult(new BooleanWithReason(false, "Please log in first"));
		}else{
			User user = getUser(request);
			List<OrderMiniWrap> orderList = AccountService.getOrderList(userId);
			bean.setOrderList(orderList);
			if(user != null)
				bean.setEmail(user.getEmailAddress().split("@")[0]);
		}
		request.setAttribute(DEFAULT_ACCOUNT_BEAN, bean);
	}
	
	public void setEditAccountPage(HttpServletRequest request, HttpServletResponse response){
		prepareHeaderBean(request, response);
		AccountBean bean = new AccountBean();
		long userId = getUserId(request);
		if(userId == -1){
			bean.setResult(new BooleanWithReason(false, "Please log in first"));
		}else{
			User user = getUser(request);
			if(user != null){
				bean.setEmail(user.getEmailAddress());
				bean.setfName(user.getFirstName());
				bean.setlName(user.getLastName());
			}
		}
		request.setAttribute(DEFAULT_ACCOUNT_BEAN, bean);
	}
	
	public void editAccountWeb(HttpServletRequest request, HttpServletResponse response){
		prepareHeaderBean(request, response);
		AccountBean bean = new AccountBean();
		long userId = getUserId(request);
		BooleanWithReason result = editAccount(request, response, userId);
		bean.setResult(result);
		if(result.getStatus()){
			setUser(request, User.getUserById(userId));
		}
		User user = getUser(request);
		if(user != null){
			bean.setEmail(user.getEmailAddress());
			bean.setfName(user.getFirstName());
			bean.setlName(user.getLastName());
		}
		request.setAttribute(DEFAULT_ACCOUNT_BEAN, bean);
	}
	
	public BooleanWithReason editAccount(HttpServletRequest request, HttpServletResponse response, long userId){
		if(userId == -1){
			return new BooleanWithReason(false, "Please log in first");
		}else{
			User user = getUser(request);
			if(user != null){
				String fName = request.getParameter("first_name") ;
				String lName = request.getParameter("last_name");
				String currentPassword = request.getParameter("cur_pwd");
				String password = request.getParameter("pwd");
				String confirmPassword = request.getParameter("c_pwd");
				if(StringUtils.isNotBlank(password) && StringUtils.isNotBlank(confirmPassword) && !password.equals(confirmPassword)){
					log.info("password invalid");
					return new BooleanWithReasonUser(false, "Password and confirm password do not match.");
				}else{
					return AccountService.editAccount(userId, StringUtils.isBlank(currentPassword) ? "" : CryptWithMD5.cryptWithMD5(currentPassword),StringUtils.isBlank(password) ? "" :CryptWithMD5.cryptWithMD5(password), fName, lName);
				}
			}
		}
		return new BooleanWithReasonUser(false, "Some problem occured while saving your details. Please try again later.");
	}
	
	public void viewOrder(HttpServletRequest request, HttpServletResponse response, String orderIdStr){
		prepareHeaderBean(request, response);
		AccountBean bean = new AccountBean();
		OrderViewWrap order = viewOrderServ(request, response,getUserId(request), orderIdStr);
		if(order == null){
			bean.setResult(new BooleanWithReason(false, "Invalid order"));
		}else{
			bean.setOrder(order);
		}
		request.setAttribute(DEFAULT_ACCOUNT_BEAN, bean);
	}
	
	public List<OrderMiniWrap> getPreviousOrders(HttpServletRequest request, HttpServletResponse response){
		long userId = request.getParameter("userId") != null ? Long.valueOf(request.getParameter("userId")) : -1;
		return AccountService.getPreviousOrderList(userId);
	}
	
	public OrderViewWrap viewOrderServ(HttpServletRequest request, HttpServletResponse response){
		return viewOrderServ(request, response,request.getParameter("userId") != null ? Long.valueOf(request.getParameter("userId")) : -1, request.getParameter("orderId"));
	}
	
	public OrderViewWrap viewOrderServ(HttpServletRequest request, HttpServletResponse response, long userId, String orderIdStr){
		long orderId = -1;
		try{
			orderId = Long.valueOf(orderIdStr);
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}
		if(userId == -1){
			return null;
		}else{
			OrderViewWrap order = AccountService.viewOrder(userId,orderId);
			if(order == null){
				return null;
			}else{
				return order;
			}
		}
	}
	
	public List<OrderMiniWrap> getOrders(HttpServletRequest request, HttpServletResponse response){
		long userId = request.getParameter("userId") != null ? Long.valueOf(request.getParameter("userId")) : -1;
		if(userId == -1){
			return null;
		}else{
			return AccountService.getOrderList(userId);
		}
	}
}
