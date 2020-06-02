package com.appeti.database.table.cart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.appeti.database.utils.DBUtils;
import com.appeti.utils.ExceptionUtils;

public class CartItem {
	private long cartItemId;
	private long cartId;
	private long tagId;
	private long ptitleId;
	private long sellerId;
	private long quantity;
	private long perUnitPrice;
	private Date created;
	private Date modified;
	private boolean isRemoved;
	
	private static final String TABNAME = "cart_item";
	
	private CartItem(ResultSet rs) throws SQLException{
		this.cartItemId = rs.getLong("cart_item_id");
		this.cartId = rs.getLong("cart_id");
		this.tagId = rs.getLong("tag_id");
		this.sellerId = rs.getLong("seller_id");
		this.ptitleId = rs.getLong("ptitle_id");
		this.quantity = rs.getLong("quantity");
		this.perUnitPrice = rs.getLong("price_per_unit");
		this.created = rs.getTimestamp("created");
		this.modified = rs.getTimestamp("modified");
		this.isRemoved = rs.getBoolean("is_removed");
	}
	
	private CartItem(long cartId, long tagId, long ptitleId,long sellerId, long quantity, long perUnitPrice){
		this.cartId = cartId;
		this.tagId = tagId;
		this.ptitleId = ptitleId;
		this.sellerId = sellerId;
		this.quantity = quantity;
		this.perUnitPrice = perUnitPrice;
	}
	
	public static long addItem(long cartId, long tagId, long ptitleId,long sellerId, long quantity, long perUnitPrice) throws SQLException{
		CartItem item = getItem(cartId, tagId);
		if(item == null)
			item = createItem(cartId, tagId, ptitleId, sellerId, 0, 0);
		item.setQuantity(item.getQuantity() + quantity);
		item.setPerUnitPrice(perUnitPrice);
		item.dbUpdate();
		return item.getCartItemId();
	}
	
	public static int removeItem(long cartItemId) throws SQLException{
		CartItem item = getById(cartItemId);
		item.setRemoved(true);
		return item.dbUpdate();
	}
	
	public static CartItem getById(long cartItemId){
		CartItem item = null;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery(null, TABNAME, "cart_item_id = ?");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, cartItemId);
			rs = stmt.executeQuery();
			if(rs.next())
				item = new CartItem(rs);
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return item;
	}
	
	public static CartItem getItem(long cartId, long tagId){
		CartItem item = null;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery(null, TABNAME, "cart_id = ? and tag_id = ? and is_removed = 0");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, cartId);
			stmt.setLong(2, tagId);
			rs = stmt.executeQuery();
			if(rs.next())
				item = new CartItem(rs);
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return item;
	}
	
	public static List<CartItem> getItems(long cartId){
		List<CartItem> list = new ArrayList<CartItem>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery(null, TABNAME, "cart_id = ? and is_removed = 0");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, cartId);
			rs = stmt.executeQuery();
			while(rs.next())
				list.add(new CartItem(rs));
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return list;
	}
		
	private static CartItem createItem(long cartId, long tagId, long ptitleId,long sellerId, long quantity, long perUnitPrice) throws SQLException{
		CartItem item = new CartItem(cartId,tagId,ptitleId,sellerId,quantity,perUnitPrice);
		item.dbInsert();
		return getItem(cartId, tagId);
	}
	
	public static CartItem updateItem(long itemId, long quantity){
		try{
			CartItem item = CartItem.getById(itemId);
			if(item != null){
				if(quantity <= 0)
					item.setRemoved(true);
				item.setQuantity(quantity);
			}
			item.dbUpdate();
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}
		return getById(itemId);
	}
	
	private long dbInsert() throws SQLException{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareInsertQuery(TABNAME, "cart_id,tag_id,ptitle_id,seller_id,quantity,price_per_unit,modified", "?,?,?,?,?,?,CURRENT_TIMESTAMP");
		try{
			con = DBUtils.getConnection();
			con.setAutoCommit(false);
			stmt = con.prepareStatement(query);
			stmt.setLong(1, this.cartId);
			stmt.setLong(2, this.tagId);
			stmt.setLong(3,this.ptitleId);
			stmt.setLong(4, this.sellerId);
			stmt.setLong(5,this.quantity);
			stmt.setDouble(6, this.perUnitPrice);
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
		
		String query = DBUtils.prepareUpdateQuery(TABNAME, "quantity = ?, price_per_unit = ? , is_removed = ? ,modified = CURRENT_TIMESTAMP", "cart_item_id = ?");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, this.quantity);
			stmt.setLong(2, this.perUnitPrice);
			stmt.setBoolean(3, this.isRemoved);
			stmt.setLong(4,this.cartItemId);
			return stmt.executeUpdate();
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
	}
	
	public static boolean isValidItem(long itemId, long cartId){
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery(null, TABNAME, "cart_item_id = ? and cart_id = ? and is_removed = 0");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, itemId);
			stmt.setLong(2, cartId);
			rs = stmt.executeQuery();
			if(rs.next())
				return true;
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return false;
	}
	
	public long getCartItemId() {
		return cartItemId;
	}
	public void setCartItemId(long cartItemId) {
		this.cartItemId = cartItemId;
	}
	public long getCartId() {
		return cartId;
	}
	public void setCartId(long cartId) {
		this.cartId = cartId;
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
	
	
}
