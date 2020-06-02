package com.appeti.database.table.cart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appeti.database.table.order.Order;
import com.appeti.database.table.product.Product;
import com.appeti.database.table.product.Ptitle;
import com.appeti.database.table.product.Tag;
import com.appeti.database.table.user.Seller;
import com.appeti.database.table.user.User;
import com.appeti.database.utils.DBUtils;
import com.appeti.utils.Constants;
import com.appeti.utils.ExceptionUtils;

public class Cart {
	private long cartId;
	private long userId;
	private long totalAmount;
	private long discountedAmount;
	private long couponId;
	private Date created;
	private Date modified;
	private String state;
	
	private static final String TABNAME = "cart";
	private static final Logger logger = LoggerFactory.getLogger(Cart.class);
	private Cart(ResultSet rs) throws SQLException{
		this.cartId = rs.getLong("cart_id");
		this.userId = rs.getLong("user_id");
		this.totalAmount = rs.getLong("total_amount");
		this.couponId = rs.getLong("coupon_id");
		this.discountedAmount = rs.getLong("discounted_amount");
		this.created = rs.getDate("created");
		this.modified = rs.getDate("modified");
		this.state = rs.getString("state");
	}
	
	private Cart(long userId){
		this.userId = userId;
		this.state = Constants.CartState.ACTIVE;
	}
	
	public static Cart getCartById(long cartId) {
		return getCartById(cartId, true);
	}
	
	public static Cart getCartById(long cartId, boolean refresh) {
		Cart cart = null;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery(null, TABNAME, "cart_id = ?");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, cartId);
			rs = stmt.executeQuery();
			if(rs.next())
				cart = new Cart(rs);
			if(cart != null && refresh)
				updateCart(cart); // to verify if coupon applied has expired
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return cart;
	}
	
	public static Cart getActiveCartById(long cartId) {
		Cart cart = null;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery(null, TABNAME, "cart_id = ? and state = ?");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, cartId);
			stmt.setString(2, Constants.CartState.ACTIVE);
			rs = stmt.executeQuery();
			if(rs.next())
				cart = new Cart(rs);
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return cart;
	}
	
	public static Cart getActiveCartById(long cartId, long userId) {
		Cart cart = null;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		String query = DBUtils.prepareSelectQuery(null, TABNAME, "cart_id = ? and user_id = ? and state = ?");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, cartId);
			stmt.setLong(2, userId);
			stmt.setString(3, Constants.CartState.ACTIVE);
			rs = stmt.executeQuery();
			if(rs.next())
				cart = new Cart(rs);
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		if(cart == null && userId != -1)
			cart = getLastActiveCartForUser(userId);
		
		return cart;
	}
	
	public static Cart getLastActiveCartForUser(long userId) {
		Cart cart = null;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery(null, TABNAME, "user_id = ? and state = ?", "order by modified desc limit 1");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, userId);
			stmt.setString(2, Constants.CartState.ACTIVE);
			rs = stmt.executeQuery();
			if(rs.next())
				cart = new Cart(rs);
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return cart;
	}
	
	public static Cart getActiveCart(long cartId, long userId) {
		Cart cart = null;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery(null, TABNAME, "cart_id = ? and user_id = ? and state = ?");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, cartId);
			stmt.setLong(2, userId);
			stmt.setString(3, Constants.CartState.ACTIVE);
			rs = stmt.executeQuery();
			if(rs.next())
				cart = new Cart(rs);
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return cart;
	}
	
	public static Cart getActiveCart(long userId){
		Cart cart = null;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery(null, TABNAME, "user_id = ? and state = ?");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, userId);
			stmt.setString(2, Constants.CartState.ACTIVE);
			rs = stmt.executeQuery();
			if(rs.next())
				cart = new Cart(rs);
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return cart;
	}
	
	public static Cart getCart(long userId) throws SQLException{
		Cart cart = getActiveCart(userId);
		if(cart == null)
			cart = createCart(userId);
		return cart;
			
	}

	public static Cart createCart(long userId){
		try{
			Cart cart = new Cart(userId);
			long cartId = cart.dbInsert();
			return Cart.getCartById(cartId);
		}catch(SQLException e){
			ExceptionUtils.logException(e);
			return null;
		}
	}
	
	public static Cart mapUser(long cartId, long userId){
		Cart cart = getCartById(cartId);
		if(cart != null){
			try{
				cart.setUserId(userId);
				cart.dbUpdate();
				return cart;
			}catch(SQLException e){
				ExceptionUtils.logException(e);
			}
		}
		return null;
	}

	public static Cart mapUser(long cartId, User user){
		Cart cart = getCartById(cartId);
		if(cart != null && user != null){
			try{
				cart.setUserId(user.getUserId());
				cart.dbUpdate();
				return cart;
			}catch(SQLException e){
				ExceptionUtils.logException(e);
			}
		}
		return null;
	}

	public static Cart updateCart(Cart cart){
		List<CartItem> items = CartItem.getItems(cart.getCartId());
		long totalAmount = 0;
		for(CartItem item : items){
			totalAmount += (item.getQuantity()*item.getPerUnitPrice());
		}
		cart.setTotalAmount(totalAmount);
		Coupon coupon = Coupon.getActiveCouponById(cart.getCouponId());
		if(coupon != null){
			cart = applyCoupon(cart, coupon);
		}else{
			cart.setCouponId(-1);
			cart.setDiscountedAmount(totalAmount);
		}
		try {
			cart.dbUpdate();
		} catch (SQLException e) {
			ExceptionUtils.logException(e);
		}
		return cart;
	}
	
	public static Cart applyCoupon(long cartId, Coupon coupon){
		try{
			Cart cart = getCartById(cartId);
			if(cart != null){
				cart = applyCoupon(cart, coupon);
				cart.dbUpdate();			
			}
			return cart;
		}catch(Exception e){
			ExceptionUtils.logException(e);
			return null;
		}
	}
	
	public static Cart applyCoupon(Cart cart, Coupon coupon){
		try{
			if(cart != null){
				cart.setCouponId(coupon.getId());

				double discount = 0;
				if(Constants.CouponScope.CART.equals(coupon.getScope())){
					discount = calculateDiscount(cart.getTotalAmount(), coupon.getDiscount()); 
				}else {
					List<CartItem> items = CartItem.getItems(cart.getCartId());
					if(Constants.CouponScope.PRODUCT.equals(coupon.getScope())){
						for(CartItem item: items){
							Tag tag = Tag.getTagById(item.getTagId());
							Product product = Product.getProductById(tag.getProductId());
							if(product != null && coupon.getMappingId() == product.getProductId()){
								discount += calculateDiscount(item.getPerUnitPrice() * item.getQuantity(), coupon.getDiscount());
							}
						}
					}else if(Constants.CouponScope.PTITLE.equals(coupon.getScope())){
						for(CartItem item: items){
							Tag tag = Tag.getTagById(item.getTagId());
							Ptitle ptitle = Ptitle.getPtitleById(tag.getPtitleId());
							if(ptitle != null && coupon.getMappingId() == ptitle.getPtitleId()){
								discount += calculateDiscount(item.getPerUnitPrice() * item.getQuantity(), coupon.getDiscount());
							}
						}
					}else if(Constants.CouponScope.SELLER.equals(coupon.getScope())){
						for(CartItem item: items){
							Tag tag = Tag.getTagById(item.getTagId());
							Seller seller = Seller.getById(tag.getSellerId());
							if(seller != null && coupon.getMappingId() == seller.getSellerId()){
								discount += calculateDiscount(item.getPerUnitPrice() * item.getQuantity(), coupon.getDiscount());
							}
						}
					}
				}
				if(discount < 0 || discount > cart.getTotalAmount())
					throw new IllegalStateException("Discount is negative or greater than total amount");
				cart.setDiscountedAmount(cart.getTotalAmount() - (long)(discount));
				Coupon.markApplied(coupon, cart.cartId);
			}
			return cart;
		}catch(Exception e){
			ExceptionUtils.logException(e);
			return null;
		}
	}
	
	public static void updateCartState(long cartId, String state){
		Cart cart = getCartById(cartId);
		if(cart != null){
			cart.setState(state);
			try{
				cart.dbUpdate();
			}catch(Exception e){
				ExceptionUtils.logException(e);
			}
		}
	}
	
	private long dbInsert() throws SQLException{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareInsertQuery(TABNAME, "user_id, state,modified", "?,?,CURRENT_TIMESTAMP");
		try{
			con = DBUtils.getConnection();
			con.setAutoCommit(false);
			stmt = con.prepareStatement(query);
			stmt.setLong(1, this.getUserId());
			stmt.setString(2, this.getState());
			stmt.executeUpdate();
			rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");
			if(rs.next()){
				con.commit();
				return rs.getLong(1);
			}else{
				throw new SQLException();
			}
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
	}
	
	public int dbUpdate() throws SQLException{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareUpdateQuery(TABNAME, "user_id = ?, total_amount = ?, coupon_id = ? , discounted_amount = ? , state = ?, modified = CURRENT_TIMESTAMP", "cart_id = ?");
		logger.info(query);
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, this.userId);
			stmt.setLong(2, this.totalAmount);
			stmt.setLong(3, this.couponId);
			stmt.setLong(4, this.discountedAmount);
			stmt.setString(5, this.state);
			stmt.setLong(6,this.cartId);
			return stmt.executeUpdate();
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
	}

	public static double calculateDiscount(long amount, long percent){
		return ((double)(amount) * (((double)percent)/100)); 
	}
	
	public boolean isActive() {
		return this.state.equals(Constants.CartState.ACTIVE);
	}
	
	public long getCartId() {
		return cartId;
	}

	public void setCartId(long cartId) {
		this.cartId = cartId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public long getCouponId() {
		return couponId;
	}

	public void setCouponId(long couponId) {
		this.couponId = couponId;
	}

	public long getDiscountedAmount() {
		return discountedAmount;
	}

	public void setDiscountedAmount(long discountedAmount) {
		this.discountedAmount = discountedAmount;
	}
}
