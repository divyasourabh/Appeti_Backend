package com.appeti.main.management;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.appeti.database.table.testimonial.ProductSuggestion;
import com.appeti.database.table.testimonial.Subscriber;
import com.appeti.database.table.testimonial.Testimonial;
import com.appeti.main.beans.MarketingBean;
import com.appeti.utils.BooleanWithReason;

public class MarketingManagement extends Management {

	private static final String MARKETING_BEAN = "mktBean";
	
	public List<Testimonial> prepareTestimonials(HttpServletRequest request, HttpServletResponse response){
		List<Testimonial> list = Testimonial.getTestimonials();
		MarketingBean bean = new MarketingBean();
		bean.setTestimonials(list);
		request.setAttribute(MARKETING_BEAN, bean);
		return list;
	}
	
	public BooleanWithReason addTestimonial(HttpServletRequest request, HttpServletResponse response){
		long userId = getUserId(request);
		String userName = request.getParameter("user_name");
		String title = request.getParameter("title");
		String desc = request.getParameter("desc");
		BooleanWithReason result = new BooleanWithReason(false, "");
		if(userId == -1){
			result.setMessage("Please login first");
		}else if(StringUtils.isBlank(desc)){
			result.setMessage("Description cannot be blank");
		}else{
			Testimonial obj = Testimonial.insertTestimonial(userId, userName, title, desc);
			if(obj != null){
				result.setStatus(true);
				result.setMessage("Thankyou for posting the testimonial. It will shortly reflect on our website");
			}else
				result.setMessage("Some problem occured while adding your testimonial. Please try again later!!");
		}
		return result;
	}
	
	public BooleanWithReason addSubscriber(HttpServletRequest request, HttpServletResponse response){
		String email = request.getParameter("email");
		boolean rs = Subscriber.addSubsciber(email);
		return new BooleanWithReason(true, "Subscription successful");
	}
	
	public BooleanWithReason addSuggestion(HttpServletRequest request, HttpServletResponse response){
		String name = request.getParameter("sug_name");
		String email = request.getParameter("sug_email");
		String phone = request.getParameter("sug_phone");
		String product_name = request.getParameter("sug_product_name");
		String brand = request.getParameter("sug_brand_name");
		String city = request.getParameter("sug_city");
		
		ProductSuggestion obj = new ProductSuggestion();
		obj.setName(name);
		obj.setEmail(email);
		obj.setPhone(phone);
		obj.setProduct(product_name);
		obj.setBrand(brand);
		obj.setCity(city);
		obj.add();
		boolean rs = obj.add();
		return new BooleanWithReason(true, "Thank you for your suggestion. We will start working on it shortly.");
	}
}
