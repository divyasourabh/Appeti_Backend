package com.appeti.database.table.order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.appeti.database.table.cart.Cart;
import com.appeti.database.table.user.Address;
import com.appeti.database.utils.DBUtils;
import com.appeti.utils.Constants;
import com.appeti.utils.ExceptionUtils;

public class Order {
	private long orderId;
	private long userId;
	private long cartId;
	private long shippingAddrId;
	private long billingAddrId;
	private long totalAmount;
	private long couponId;
	private long discount;
	private long deliveryCharge;
	private long taxAmount;
	private long amountPaid;
	private long transId;
	private String notes;
	private Date created;
	private Date modified;
	private String state;
	
	private static final String TABNAME = "order_";
	
	private Order(){}
	
	private Order(ResultSet rs) throws SQLException{
		this.orderId = rs.getLong("order_id");
		this.userId = rs.getLong("user_id");
		this.cartId = rs.getLong("cart_id");
		this.shippingAddrId = rs.getLong("shipping_address_id");
		this.billingAddrId = rs.getLong("billing_address_id");
		this.totalAmount = rs.getLong("total_amount");
		this.couponId = rs.getLong("coupon_id");
		this.discount = rs.getLong("discount");
		this.deliveryCharge = rs.getLong("delivery_charge");
		this.taxAmount = rs.getLong("tax_amount");
		this.amountPaid = rs.getLong("amount_paid");
		this.transId = rs.getLong("trans_id");
		this.notes = rs.getString("notes");
		this.created = rs.getTimestamp("created");
		this.modified = rs.getTimestamp("modified");
		this.state = rs.getString("state");
	}

	public static Order getOrderById(long orderId){
		Order order = null;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery(null, TABNAME , "order_id = ?");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, orderId);
			rs = stmt.executeQuery();
			if(rs.next())
				order = new Order(rs);
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return order;
	}
	
	public static Order getOrderById(long userId, long orderId){
		Order order = null;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery(null, TABNAME , "user_id = ? and order_id = ?");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, userId);
			stmt.setLong(2, orderId);
			rs = stmt.executeQuery();
			if(rs.next())
				order = new Order(rs);
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return order;
	}

	public static List<Order> getItems(long userId){
		List<Order> list = new ArrayList<Order>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery(null, TABNAME, "user_id = ? and state != ?", "order by modified desc");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, userId);
			stmt.setString(2, Constants.OrderState.ACTIVE);
			rs = stmt.executeQuery();
			while(rs.next())
				list.add(new Order(rs));
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return list;
	}
	
	public static List<Order> getPreviousOrders(long userId){
		List<Order> list = new ArrayList<Order>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery(null, TABNAME, "user_id = ? and state != ? and state != ? and state != ? ", "order by modified desc");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, userId);
			stmt.setString(2, Constants.OrderState.ACTIVE);
			stmt.setString(3, Constants.OrderState.PROCESSING);
			stmt.setString(4, Constants.OrderState.ABANDONED);
			
			rs = stmt.executeQuery();
			while(rs.next())
				list.add(new Order(rs));
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return list;
	}
	
	public static Order getOrderForCartId(long cartId){
		Order order = null;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery(null, TABNAME , "cart_id = ? and state = ?");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, cartId);
			stmt.setString(2, Constants.OrderState.ACTIVE);
			rs = stmt.executeQuery();
			if(rs.next())
				order = new Order(rs);
			}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return order;
	}
	
	public static Order prepareOrderFromCart(Cart cart){
		if(cart == null)
			return null;
		try{
			Order order = new Order();
			order.setCartId(cart.getCartId());
			order.setUserId(cart.getUserId());
			if(cart.getUserId() != -1){
				Address addr = Address.getDefaultForUser(cart.getUserId());
				if(addr != null){
					order.setShippingAddrId(addr.getId());
					order.setBillingAddrId(addr.getId());
				}
			}
			order.setTotalAmount(cart.getTotalAmount());
			order.setCouponId(cart.getCouponId());
			order.setDiscount(cart.getTotalAmount()-cart.getDiscountedAmount());
			order.setState(Constants.OrderState.ACTIVE);
			long orderId = order.dbInsert();
			order = getOrderById(orderId);
			return order;
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}
		return null;
	}
	
	public static Order refreshOrder(Order order, Cart cart){
		try{
			order.setUserId(cart.getUserId());

			order.setTotalAmount(cart.getTotalAmount());
			order.setCouponId(cart.getCouponId());
			order.setDiscount(cart.getTotalAmount()-cart.getDiscountedAmount());
			order.dbUpdate();
			return order;
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}
		return null;
	}
	
	private long dbInsert() throws SQLException{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareInsertQuery(TABNAME, "user_id, cart_id , shipping_address_id , billing_address_id, total_amount, coupon_id, discount, state, modified", "?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP");
		try{
			con = DBUtils.getConnection();
			con.setAutoCommit(false);
			stmt = con.prepareStatement(query);
			stmt.setLong(1, this.getUserId());
			stmt.setLong(2, this.getCartId());
			stmt.setLong(3, this.getShippingAddrId());
			stmt.setLong(4, this.getBillingAddrId());
			stmt.setLong(5, this.getTotalAmount());
			stmt.setLong(6, this.getCouponId());
			stmt.setLong(7, this.getDiscount());
			stmt.setString(8, this.getState());
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
		
		String query = DBUtils.prepareUpdateQuery(TABNAME, "user_id = ?, total_amount = ?, coupon_id = ? , discount = ? , "
				+ "tax_amount = ? , delivery_charge = ? , amount_paid = ? , trans_id = ?, shipping_address_id = ?, billing_address_id = ?, notes = ?, state = ?, modified = CURRENT_TIMESTAMP", "order_id = ?");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, this.userId);
			stmt.setLong(2, this.totalAmount);
			stmt.setLong(3, this.couponId);
			stmt.setLong(4, this.discount);
			stmt.setLong(5, this.taxAmount);
			stmt.setLong(6, this.deliveryCharge);
			stmt.setLong(7, this.amountPaid);
			stmt.setLong(8, this.transId);
			stmt.setLong(9, this.shippingAddrId);
			stmt.setLong(10, this.billingAddrId);
			stmt.setString(11, this.notes);
			stmt.setString(12, this.state);
			stmt.setLong(13,this.orderId);
			return stmt.executeUpdate();
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
	}
	
	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getCartId() {
		return cartId;
	}

	public void setCartId(long cartId) {
		this.cartId = cartId;
	}

	public long getShippingAddrId() {
		return shippingAddrId;
	}

	public void setShippingAddrId(long shippingAddrId) {
		this.shippingAddrId = shippingAddrId;
	}

	public long getBillingAddrId() {
		return billingAddrId;
	}

	public void setBillingAddrId(long billingAddrId) {
		this.billingAddrId = billingAddrId;
	}

	public long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public long getCouponId() {
		return couponId;
	}

	public void setCouponId(long couponId) {
		this.couponId = couponId;
	}

	public long getDiscount() {
		return discount;
	}

	public void setDiscount(long discount) {
		this.discount = discount;
	}

	public long getDeliveryCharge() {
		return deliveryCharge;
	}

	public void setDeliveryCharge(long deliveryCharge) {
		this.deliveryCharge = deliveryCharge;
	}

	public long getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(long taxAmount) {
		this.taxAmount = taxAmount;
	}

	public long getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(long amountPaid) {
		this.amountPaid = amountPaid;
	}

	public long getTransId() {
		return transId;
	}

	public void setTransId(long transId) {
		this.transId = transId;
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

	public static String getTabname() {
		return TABNAME;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	
}
