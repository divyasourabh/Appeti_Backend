package com.appeti.main.management;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.EmailValidator;
import org.json.JSONObject;

import com.appeti.database.table.cart.Coupon;
import com.appeti.database.table.cart.wrap.CouponWrap;
import com.appeti.database.table.order.Order;
import com.appeti.database.table.testimonial.Testimonial;
import com.appeti.database.table.user.Address;
import com.appeti.database.table.user.User;
import com.appeti.database.table.user.VendorSourcing;
import com.appeti.main.beans.HomeBean;
import com.appeti.main.services.HomeService;
import com.appeti.utils.BooleanWithReason;
import com.appeti.utils.BooleanWithReasonUser;
import com.appeti.utils.Constants;
import com.appeti.utils.Constants.ClickSaleSource;
import com.appeti.utils.CryptWithMD5;

public class HomeManagement extends Management{
	
	private static final String DEAFAULT_HOME_BEAN = "homeBean";
	
	public void setHomePage(HttpServletRequest request, HttpServletResponse response){
		HomeBean bean = new HomeBean();
		bean.setTestimonials(Testimonial.getTestimonials(6));
		request.setAttribute(DEAFAULT_HOME_BEAN, bean);
		prepareHeaderBean(request, response);
	}
	
	public String webPopularProducts(HttpServletRequest request, HttpServletResponse response){
		HomeService service = new HomeService(request);
		return new JSONObject(service.getPopularProducts()).toString();
	}
	
	public BooleanWithReasonUser sociallogin(HttpServletRequest request, HttpServletResponse response, String source){
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String token = request.getParameter("token");
		if(StringUtils.isBlank(email))
		{
			log.info("email invalid " + email);
			return new BooleanWithReasonUser(false, "Invalid email address");
		}
		log.info("email valid " + email);
		User user = HomeService.getUser(firstName,lastName,email,token);
		if(user != null){
			if(ClickSaleSource.WEB.equals(source))
				setUser(request, user);
			return new BooleanWithReasonUser(true, "User logged in successfully",user.getUserId());
		}else{
			return new BooleanWithReasonUser(false, "Some problem occured while logging in");
		}
	}
	
	public BooleanWithReason login(HttpServletRequest request, HttpServletResponse response){
		prepareHeaderBean(request, response);
		String email = request.getParameter("email");
		String password = request.getParameter("pwd");
		User user = HomeService.validateLogin(email,CryptWithMD5.cryptWithMD5(password));
		if(user != null){
			setUser(request, user);
			return new BooleanWithReason(true, "User logged in successfully");
		}else{
			return new BooleanWithReason(false, "Invalid credentials or email not verified yet");
		}
	}
	
	public BooleanWithReasonUser signUp(HttpServletRequest request, HttpServletResponse response){
		prepareHeaderBean(request, response);
		String emailAddress = request.getParameter("email");
		String password = request.getParameter("pwd");
		String confirmPassword = request.getParameter("c_pwd");
		if(emailAddress == null 
				//|| !EmailValidator.getInstance().isValid(emailAddress)
				){
			log.info("email invalid");
			return new BooleanWithReasonUser(false, "Invalid email address");
		}else if(password == null || confirmPassword == null || !password.equals(confirmPassword)){
			log.info("password invalid");
			return new BooleanWithReasonUser(false, "Password and confirm password do not match.");
		}
		else{
			return HomeService.createLogin(emailAddress, CryptWithMD5.cryptWithMD5(password));
		}
	}
	
	public BooleanWithReasonUser loginService(HttpServletRequest request, HttpServletResponse response){
		String email = request.getParameter("email");
		String password = request.getParameter("pwd");
		User user = HomeService.validateLogin(email,password);
		if(user != null){
			setUser(request, user);
			return new BooleanWithReasonUser(true, "User logged in successfully", user.getUserId());
		}else{
			return new BooleanWithReasonUser(false, "Invalid credentials");
		}
	}

	public BooleanWithReasonUser signUpService(HttpServletRequest request, HttpServletResponse response){
		String emailAddress = request.getParameter("email");
		String password = request.getParameter("pwd");
		if(StringUtils.isBlank(emailAddress) 
				|| !EmailValidator.getInstance().isValid(emailAddress)
				){
			log.info("email invalid");
			return new BooleanWithReasonUser(false, "Invalid email address");
		}else{
			return HomeService.createLogin(request, emailAddress, password);
		}
	}

	public BooleanWithReason verifyEmail(HttpServletRequest request, HttpServletResponse response){
		long userId = -1;
		String hash = "";
		try{
			userId = request.getParameter("userid")!=null?Long.valueOf(request.getParameter("userid")):-1;
			hash = request.getParameter("hash");
		}catch(Exception e){}
		return HomeService.verifyEmail(userId,hash);
	}
	
	public BooleanWithReason forgotPassword(HttpServletRequest request, HttpServletResponse response){
		String email = request.getParameter("forgot_email_address");
		return HomeService.forgotPassword(email);
	}
	
	public BooleanWithReason vendorSourcing(HttpServletRequest request, HttpServletResponse response){
		prepareHeaderBean(request, response);
		String brandName = request.getParameter("brand_name");
		String bestProduct = request.getParameter("best_product");
		String bestProductPrice = request.getParameter("best_product_price");
		String secondBestProduct = request.getParameter("second_best_product");
		String secondBestProductPrice = request.getParameter("second_best_product_price");
		String website = request.getParameter("website");
		String other = request.getParameter("other");
		
		VendorSourcing vendor = new VendorSourcing();
		vendor.setBrandName(brandName);
		vendor.setBestProduct(bestProduct);
		vendor.setBestPrice(bestProductPrice);
		vendor.setSecondBestProduct(secondBestProduct);
		vendor.setSecondBestPrice(secondBestProductPrice);
		vendor.setWebsite(website);
		vendor.setOtherInfo(other);
		
		Address addr = processAddress(request, "");
		HomeBean bean = new HomeBean();
		BooleanWithReason result = HomeService.addVendor(vendor, addr);
		bean.setResult(result);
		request.setAttribute(DEAFAULT_HOME_BEAN, bean);
		return result;
	}
	
	public void trackOrder(HttpServletRequest request, HttpServletResponse response){
		prepareHeaderBean(request, response);
		HomeBean bean = new HomeBean();
		BooleanWithReason result;
		long orderId = request.getParameter("orderId")!=null?Long.valueOf(request.getParameter("orderId")):-1;
		String email = request.getParameter("email");
		User user = User.getUserByEmail(email);
		if(user!= null){
			Order order = Order.getOrderById(user.getUserId(), orderId);
			if(order != null){
				result = new BooleanWithReason(true, "Your order has been " + (Constants.OrderState.ORDERED.equals(order.getState()) ? Constants.OrderState.RECEIVED : order.getState()) + ". We will keep posting you for further updates.");
			}else{
				result = new BooleanWithReason(false, "Invalid order id.");
			}
		}else{
			result = new BooleanWithReason(false, "User not found.");
		}
		bean.setResult(result);
		request.setAttribute(DEAFAULT_HOME_BEAN, bean);
	}
	
	public List<CouponWrap> offers(HttpServletRequest request, HttpServletResponse response){
		prepareHeaderBean(request, response);
		HomeBean bean = new HomeBean();
		List<CouponWrap> list = CouponWrap.getWrap(Coupon.getActiveCoupons());
		bean.setActiveCoupons(list);
		request.setAttribute(DEAFAULT_HOME_BEAN, bean);
		return list;
	}
}
