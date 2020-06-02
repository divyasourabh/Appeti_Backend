package com.appeti.main.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.RandomStringUtils;

import com.appeti.database.table.cart.Coupon;
import com.appeti.database.table.node.Node;
import com.appeti.database.table.node.wrap.NodeParentChildWrap;
import com.appeti.database.table.product.Category;
import com.appeti.database.table.product.CategoryWrap;
import com.appeti.database.table.user.Address;
import com.appeti.database.table.user.LoginInfo;
import com.appeti.database.table.user.User;
import com.appeti.database.table.user.VendorSourcing;
import com.appeti.database.utils.DBUtils;
import com.appeti.mail.MailUtils;
import com.appeti.utils.BooleanWithReason;
import com.appeti.utils.BooleanWithReasonUser;
import com.appeti.utils.CryptWithMD5;
import com.appeti.utils.ExceptionUtils;
import com.appeti.utils.Property;

public class HomeService extends Service {
	
	public HomeService(HttpServletRequest request){
		super(request);
	}
	
	private static final int DEFAULT_NUMBER_OF_POPULAR_NODES = 4;
	private static final int DEFAULT_NUMBER_OF_POPULAR_CATEGORIES = 4;
	private static final int DEFAULT_NUMBER_OF_REGIONAL_NODES = 3;
	
	public  List<NodeParentChildWrap> getNodes(String property, String defaultProperty, int number){
		String nodeStr = prop.getProperty(property,defaultProperty);
		log.info("property : " + property + " ---> regional nodes : " + nodeStr);
		List<NodeParentChildWrap> list = new ArrayList<NodeParentChildWrap>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query = DBUtils.prepareSelectQuery(null, Node.TABNAME, "node_id in ( ? ) limit " + number);
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setString(1, nodeStr);
			rs = stmt.executeQuery();
			while(rs.next())
				list.add(new NodeParentChildWrap(new Node(rs)));
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return list;
	}
	
	public  List<NodeParentChildWrap> getNodesInOrder(String property, String defaultProperty, int number){
		String nodeStr = prop.getProperty(property,defaultProperty);
		List<NodeParentChildWrap> list = new LinkedList<NodeParentChildWrap>();
		String[] nodeIds = nodeStr.split(DELIMITER); 
		for(String idStr : nodeIds){
			long id = -1;
			try{
				id = Long.valueOf(idStr);
				Node node = Node.getNodeById(id);
				if(node != null)
					list.add(new NodeParentChildWrap(node));
			}catch(Exception e){
				ExceptionUtils.logException(e, idStr);
			}
		}
		return list;
	}
	
	
	public  List<NodeParentChildWrap> getPopularNodes(){
		return getNodesInOrder(Property.POPULAR_NODES,Property.DEFAULT_POLULAR_NODES,DEFAULT_NUMBER_OF_POPULAR_NODES);
	}
	public  List<NodeParentChildWrap> getWesternNodes(){
		return getNodesInOrder(Property.WESTERN_NODES,Property.DEFAULT_WESTERN_NODES,DEFAULT_NUMBER_OF_REGIONAL_NODES);
	}
	public  List<NodeParentChildWrap> getNorthernNodes(){
		return getNodesInOrder(Property.NORTHERN_NODES,Property.DEFAULT_NORTHERN_NODES,DEFAULT_NUMBER_OF_REGIONAL_NODES);
	}
	public  List<NodeParentChildWrap> getSouthernNodes(){
		return getNodesInOrder(Property.SOUTHERN_NODES,Property.DEFAULT_SOUTHERN_NODES,DEFAULT_NUMBER_OF_REGIONAL_NODES);
	}
	
	public  List<CategoryWrap> getCategoriesInOrder(String property, String defaultProperty, int number){
		String catStr = prop.getProperty(property,defaultProperty);
		log.info("property : " + property + " ---> " + catStr);
		List<CategoryWrap> list = new LinkedList<CategoryWrap>();
		String[] catIds = catStr.split(DELIMITER); 
		for(String idStr : catIds){
			long id = -1;
			try{
				id = Long.valueOf(idStr);
				Category cat = Category.getCategoryById(id);
				if(cat != null)
					list.add(CategoryWrap.getWrap(cat));
			}catch(Exception e){
				ExceptionUtils.logException(e, idStr);
			}
		}
		return list;
	}
	
	public  List<CategoryWrap> getPopularCategories(){
		return getCategoriesInOrder(Property.POPULAR_CATEGORIES,Property.DEFAULT_POLULAR_CATEGORIES,DEFAULT_NUMBER_OF_POPULAR_CATEGORIES);
	}
	
	public static User getUser(String firstName, String lastName, String email,String token){
		User user = User.getUserByEmail(email);
		if(user == null){
			user = User.createUser(firstName,lastName,email,token);
			if(user != null){
				final User user_ = user;
				Runnable postProcess = new Runnable() {
					@Override
					public void run() {
						String randomCouponCode = RandomStringUtils.randomAlphanumeric(8).toUpperCase();
						Coupon.createOneTimeCoupon(randomCouponCode);
						MailUtils.sendWelcomeUserMail(user_, randomCouponCode);
					}
				};
				Thread thread = new Thread(postProcess);
				thread.start();
			}
		}
		return user;
	}
	
	public static BooleanWithReasonUser createLogin(HttpServletRequest request, String email,String password){
		BooleanWithReasonUser result = createLogin(email, password);
		User user = User.getUserById(result.getUserId());
		setUser(request, user);
		return result;
	}
	
	public static BooleanWithReasonUser createLogin(String email,String password){
		User user = User.getUserByEmail(email);
		if(user == null){
			user = User.createUser(email);
		}
		if(user != null){
			LoginInfo login = LoginInfo.getInfo(user.getUserId());
			if(login != null){
				return new BooleanWithReasonUser(false, "Email id already registered. Please use forget password link if you have forgotten the password");
			}else{
				login = LoginInfo.createLogin(user.getUserId(),email, password);
				if(login != null){
					String randomCouponCode = RandomStringUtils.randomAlphanumeric(8).toUpperCase();
					Coupon.createOneTimeCoupon(randomCouponCode);
					MailUtils.sendWelcomeUserMail(user, randomCouponCode);
					return new BooleanWithReasonUser(true, "Sign up successfull. Promo coupon code sent to your email id.", user.getUserId());
				}else{
					return new BooleanWithReasonUser(false, "Some problem occured while signing you up");
				}
			}
		}
		return new BooleanWithReasonUser(false, "Some problem occured while signing you up");
	}
	
	public static User validateLogin(String email, String password){
		long userId = LoginInfo.validateUser(email, password);
		if(userId != -1){
			return User.getUserById(userId);
		}
		return null;
	}
	
	public static BooleanWithReason verifyEmail(long userId, String hash){
		String message = "Invalid verification url";
		LoginInfo login = LoginInfo.getByUserId(userId);
		if(login != null){
			if(login.getHashCode().equals(hash)){
				try{
					login.setActive(true);
					login.dbUpdate();
					message = "Email verified";
					return new BooleanWithReason(true, message);
				}catch(Exception e){
					ExceptionUtils.logException(e);
				}
			}
		}
		return new BooleanWithReason(false, message);
	}
	
	public static BooleanWithReason forgotPassword(String email){
		String message = "No user with this email address";
		LoginInfo login = LoginInfo.getInfo(email);
		if(login != null){
			String newPassword = RandomStringUtils.random(20,true,true);
			try{
				login.setPassword(CryptWithMD5.cryptWithMD5(newPassword));
				login.dbUpdate();
				MailUtils.sendForgetPasswordMail(login, newPassword);
				return new BooleanWithReason(true, "Password reset mail has been successfully sent to your email address.");
			}catch(Exception e){
				ExceptionUtils.logException(e);
			}
		}
		User user = User.getUserByEmail(email);
		if(user != null){
			String newPassword = RandomStringUtils.random(20,true,true);
			login = LoginInfo.createLogin(user.getUserId(),email, newPassword);
			if(login != null){
				MailUtils.sendForgetPasswordMail(login, newPassword);
				return new BooleanWithReason(true, "Password reset mail has been successfully sent to your email address.");
			}
		}
		return new BooleanWithReason(false, message);
	}
	
	public static BooleanWithReason addVendor(VendorSourcing vendor, Address addr){
		if(addr != null){
			try{
				long id = addr.dbInsert();
				if(id != -1){
					vendor.setAddrId(id);
					vendor.dbInsert();
					// add mail code in thread
					return new BooleanWithReason(true, "Thank you for your interest. Our team will contact you shortly.");
				}
			}catch(Exception e){
				ExceptionUtils.logException(e);
			}
		}
		return new BooleanWithReason(false, "Some problem occured while submitting your request. Please try again later.");

	}
	
}
