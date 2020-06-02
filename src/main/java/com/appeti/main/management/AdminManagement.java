package com.appeti.main.management;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.appeti.database.table.user.User;
import com.appeti.mail.MailUtils;
import com.appeti.main.beans.AdminBean;
import com.appeti.utils.BooleanWithReason;

public class AdminManagement extends Management{
	
	private static final String DEAFAULT_ADMIN_BEAN = "adminBean";
	
	public boolean isLoggedIn(HttpServletRequest request){
		if(request.getSession(false) != null){
			User user = (User)request.getSession(false).getAttribute("user");
			if(user != null && user.getRoleId() == 1)
				return true;
		}
		return false;
	}
	
	public void sendPromoMail(HttpServletRequest request, HttpServletResponse response){
		prepareHeaderBean(request, response);
		String subject = request.getParameter("subject");
		String heading = request.getParameter("heading");
		String content = request.getParameter("content");
		String emailIds = request.getParameter("emailIds");
		
		BooleanWithReason result;
		if(StringUtils.isNotBlank(subject) 
				&& StringUtils.isNotBlank(heading) 
				&& StringUtils.isNotBlank(content) 
				&& StringUtils.isNotBlank(emailIds)){
			boolean flag = MailUtils.sendPromotionalMail(subject, heading, content, emailIds);
			if(flag)
				result = new BooleanWithReason(flag, "Mail Sent");
			else
				result = new BooleanWithReason(false, "Some intermediate problem occured while sending mail..Contact dev");
		}else{
			result = new BooleanWithReason(false, "None of the fields can be blank");
		}
		AdminBean bean = new AdminBean();
		bean.setResult(result);
		request.setAttribute(DEAFAULT_ADMIN_BEAN, bean);
	}
}
