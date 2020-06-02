package com.appeti.main.management;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.appeti.database.table.node.wrap.NodeTree;
import com.appeti.database.table.product.CategoryTree;
import com.appeti.database.table.user.Address;
import com.appeti.database.table.user.User;
import com.appeti.main.beans.HeaderBean;
import com.appeti.main.services.Service;

public class Management {
	protected static Logger log = Logger.getLogger(Management.class);
	protected static String DEFAULT_HEADER_BEAN = "headerBean";
	
	public void prepareHeaderBean(HttpServletRequest request, HttpServletResponse response){
		HeaderBean bean = new HeaderBean();
		Service service = new Service(request);
		bean.setNodeTree(service.getNodeTree()); // can be optimized if set depth
		bean.setCategoryTree(service.getCategoryTree());
		bean.setPhoneNumber(service.getContactNumber());
		bean.setEmail(service.getContactEmail());
		request.setAttribute(DEFAULT_HEADER_BEAN, bean);
	}
	
	public List<NodeTree> getNLevelNodes(HttpServletRequest request, HttpServletResponse response, int n){
		Service service = new Service(request);
		NodeTree tree = service.getNodeTree(n);
		return tree.getChildNodes();
	}
	
	public List<CategoryTree> getNLevelCategories(HttpServletRequest request, HttpServletResponse response, int n, boolean sendProducts){
		Service service = new Service(request);
		CategoryTree tree = service.getCategoryTree(n, sendProducts);
		return tree.getChildCategories();
	}
	
	public Address processAddress(HttpServletRequest request,String prefix){
		long userId = getUserId(request);
		return processAddress(request, userId, prefix);
	}
	
	public Address processAddress(HttpServletRequest request,long userId, String prefix){
		Address addr = new Address();
		addr.setUserId(userId);
		addr.setFirstName(request.getParameter(prefix + "first_name"));
		addr.setLastName(request.getParameter(prefix + "last_name"));
		addr.setName1(request.getParameter(prefix + "company"));
		addr.setAddr1(request.getParameter(prefix + "address_1"));
		addr.setAddr2(request.getParameter(prefix + "address_2"));
		addr.setCity(request.getParameter(prefix + "city"));
		addr.setState(request.getParameter(prefix + "state"));
		addr.setZipCode(request.getParameter(prefix + "postcode"));
		addr.setEmailAddr(request.getParameter(prefix + "email"));
		addr.setPhoneNumber(request.getParameter(prefix + "phone"));
		addr.setAltPhoneNumber(request.getParameter(prefix + "alt_phone"));
		boolean isDefault = false;
		try{
			System.out.println(request.getParameter("mark_address_as_default"));
			isDefault = request.getParameter("mark_address_as_default") != null ? Integer.parseInt(request.getParameter("mark_address_as_default")) == 1 : false;
		}catch(Exception e){}
		addr.setDefault(isDefault);
		return addr;
	}
	
	public boolean isLoggedIn(HttpServletRequest request){
		if(request.getSession(false) != null && request.getSession(false).getAttribute("user") != null){
			return true;
		}else{
			return false;
		}
	}
	
	public long getUserId(HttpServletRequest request){
		if(!isLoggedIn(request))
			return -1;
		return request.getSession(false) == null ? -1 : (Long)(request.getSession(false).getAttribute("userId"));
	}
	
	public User getUser(HttpServletRequest request){
		if(!isLoggedIn(request))
			return null;
		return request.getSession(false) == null ? null : (User)(request.getSession(false).getAttribute("user"));
	}
	
	public void setUser(HttpServletRequest request, User user){
		request.getSession(true).setAttribute("user",user);
		request.getSession(true).setAttribute("userId",user.getUserId());
	}
}
