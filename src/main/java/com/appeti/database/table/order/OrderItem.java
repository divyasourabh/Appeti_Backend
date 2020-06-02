package com.appeti.database.table.order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.appeti.database.table.cart.CartItem;
import com.appeti.database.utils.DBUtils;
import com.appeti.utils.ExceptionUtils;


public class OrderItem {
	private long orderItemId;
	private long orderId;
	private long cartItemId;
	private long tagId;
	private long ptitleId;
	private long sellerId;
	private long quantity;
	private long perUnitPrice;
	private long totalPrice;
	private Date created;
	private Date modified;
	private boolean isRemoved;

	private static final String TABNAME = "order_item";
	
	private OrderItem(){}
	
	private OrderItem(ResultSet rs) throws SQLException{
		this.orderItemId = rs.getLong("order_item_id");
		this.orderId = rs.getLong("order_id");
		this.cartItemId = rs.getLong("cart_item_id");
		this.tagId = rs.getLong("tag_id");
		this.sellerId = rs.getLong("seller_id");
		this.ptitleId = rs.getLong("ptitle_id");
		this.quantity = rs.getLong("quantity");
		this.perUnitPrice = rs.getLong("price_per_unit");
		this.totalPrice = rs.getLong("total_price");
		this.created = rs.getTimestamp("created");
		this.modified = rs.getTimestamp("modified");
		this.isRemoved = rs.getBoolean("is_removed");
	}

	public static OrderItem getById(long orderItemId){
		OrderItem item = null;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery(null, TABNAME, "order_item_id = ?");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, orderItemId);
			rs = stmt.executeQuery();
			if(rs.next())
				item = new OrderItem(rs);
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return item;
	}

	public static OrderItem getByCartItemId(long orderId, long cartItemId){
		OrderItem item = null;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery(null, TABNAME, "order_id = ? and cart_item_id = ?");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, orderId);
			stmt.setLong(2, cartItemId);
			rs = stmt.executeQuery();
			if(rs.next())
				item = new OrderItem(rs);
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return item;
	}

	public static List<OrderItem> getItems(long orderId){
		List<OrderItem> list = new ArrayList<OrderItem>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery(null, TABNAME, "order_id = ? and is_removed = 0");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, orderId);
			rs = stmt.executeQuery();
			while(rs.next())
				list.add(new OrderItem(rs));
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return list;
	}
	
	public static OrderItem createItem(long orderId, CartItem item){
		if(item == null)
			return null;
		try{
			OrderItem order = new OrderItem();
			order.setOrderId(orderId);
			order.setCartItemId(item.getCartItemId());
			order.setTagId(item.getTagId());
			order.setSellerId(item.getSellerId());
			order.setPtitleId(item.getPtitleId());
			order.setQuantity(item.getQuantity());
			order.setPerUnitPrice(item.getPerUnitPrice());
			order.setTotalPrice(item.getQuantity() * item.getPerUnitPrice());
			long id = order.dbInsert();
			return getById(id);
		}catch(SQLException e){
			ExceptionUtils.logException(e);
		}
		return null;
	}
	
	public static OrderItem getItem(long orderId, CartItem item){
		OrderItem orderItem = getByCartItemId(orderId, item.getCartItemId());
		if(orderItem == null){
			orderItem = createItem(orderId, item);
		}else{
			orderItem = refreshItem(orderItem,item);
		}
		return orderItem;
	}
	
	public static OrderItem refreshItem(OrderItem orderItem, CartItem item){
		try{
			orderItem.setPerUnitPrice(item.getPerUnitPrice());
			orderItem.setQuantity(item.getQuantity());
			orderItem.setTotalPrice(item.getPerUnitPrice() * item.getQuantity());
			orderItem.dbUpdate();
			return orderItem;
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}
		return null;
	}
	
	public static void purgeRemovedCartItems(Order order){
		List<OrderItem> items = getItems(order.getOrderId());
		for(OrderItem item : items){
			removeIfRemovedCartItem(item);
		}
	}
	
	public static void removeIfRemovedCartItem(OrderItem item){
		CartItem cartItem = CartItem.getById(item.getCartItemId());
		if(cartItem.isRemoved()){
			item.setRemoved(true);
			try{
				item.dbUpdate();
			}catch(Exception e){
				ExceptionUtils.logException(e);
			}
		}
	}
	private long dbInsert() throws SQLException{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareInsertQuery(TABNAME, "order_id,cart_item_id,tag_id,ptitle_id,seller_id,quantity,price_per_unit,total_price,modified", "?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP");
		try{
			con = DBUtils.getConnection();
			con.setAutoCommit(false);
			stmt = con.prepareStatement(query);
			stmt.setLong(1, this.orderId);
			stmt.setLong(2, this.cartItemId);
			stmt.setLong(3, this.tagId);
			stmt.setLong(4,this.ptitleId);
			stmt.setLong(5, this.sellerId);
			stmt.setLong(6,this.quantity);
			stmt.setDouble(7, this.perUnitPrice);
			stmt.setDouble(8, this.totalPrice);
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
	
	private int dbUpdate() throws SQLException{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareUpdateQuery(TABNAME, "quantity = ?, price_per_unit = ? , total_price = ? , is_removed = ? ,modified = CURRENT_TIMESTAMP", "order_item_id = ?");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, this.quantity);
			stmt.setLong(2, this.perUnitPrice);
			stmt.setLong(3, this.totalPrice);
			stmt.setBoolean(4, this.isRemoved);
			stmt.setLong(5,this.orderItemId);
			return stmt.executeUpdate();
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
	}

	public long getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(long orderItemId) {
		this.orderItemId = orderItemId;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public long getTagId() {
		return tagId;
	}

	public void setTagId(long tagId) {
		this.tagId = tagId;
	}

	public long getPtitleId() {
		return ptitleId;
	}

	public void setPtitleId(long ptitleId) {
		this.ptitleId = ptitleId;
	}

	public long getSellerId() {
		return sellerId;
	}

	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public long getPerUnitPrice() {
		return perUnitPrice;
	}

	public void setPerUnitPrice(long perUnitPrice) {
		this.perUnitPrice = perUnitPrice;
	}

	public long getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(long totalPrice) {
		this.totalPrice = totalPrice;
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

	public boolean isRemoved() {
		return isRemoved;
	}

	public void setRemoved(boolean isRemoved) {
		this.isRemoved = isRemoved;
	}

	public long getCartItemId() {
		return cartItemId;
	}

	public void setCartItemId(long cartItemId) {
		this.cartItemId = cartItemId;
	}

	public static String getTabname() {
		return TABNAME;
	}
	
}
