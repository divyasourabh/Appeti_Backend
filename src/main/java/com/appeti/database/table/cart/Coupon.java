package com.appeti.database.table.cart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;

import com.appeti.database.utils.DBUtils;
import com.appeti.main.services.CartService;
import com.appeti.utils.Constants;
import com.appeti.utils.ExceptionUtils;

public class Coupon {
	private long id;
	private String code;
	private int discount;
	private String scope;
	private long mappingId;
	private boolean isOneTime;
	private String state;
	private Date to;
	private Date created;
	private Date modified;
	
	private static final String TABNAME = "coupon";
	private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	private Coupon(ResultSet rs) throws SQLException{
		this.id = rs.getLong("id");
		this.code = rs.getString("code");
		this.discount = rs.getInt("discount");
		this.scope = rs.getString("scope");
		this.mappingId = rs.getLong("mapping_id");
		this.isOneTime = rs.getBoolean("is_onetime");
		this.state = rs.getString("state");
		this.to = rs.getTimestamp("to_");
		this.created = rs.getTimestamp("created");
		this.modified = rs.getTimestamp("modified");
	}

	public static Coupon getCoupon(String code){
		Coupon coupon = null;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String curDate = "'" + df.format(new Date()) + "'";
		
		String query = DBUtils.prepareSelectQuery(null, TABNAME , "lower(code) = ? and (is_onetime = 0 or state = ? or state = ? or (state = ? and modified <= NOW() - INTERVAL 5 MINUTE)) and to_ > " + curDate);
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setString(1, code.toLowerCase());
			stmt.setString(2, Constants.CouponState.ACTIVE);
			stmt.setString(3, Constants.CouponState.APPLIED);
			stmt.setString(4, Constants.CouponState.PROCESSED);
			rs = stmt.executeQuery();
			if(rs.next())
				coupon = new Coupon(rs);
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return coupon;
	}
	
	public static Coupon getActiveCouponById(long couponId) {
		Coupon coupon = null;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String curDate = "'" + df.format(new Date()) + "'";
		
		String query = DBUtils.prepareSelectQuery(null, TABNAME, "id = ? and (is_onetime = 0 or state = ? or state = ? or (state = ? and modified <= ?)) and to_ > " + curDate);
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, couponId);
			stmt.setString(2, Constants.CouponState.ACTIVE);
			stmt.setString(3, Constants.CouponState.APPLIED);
			stmt.setString(4, Constants.CouponState.PROCESSED);
			stmt.setString(5, df.format(DateUtils.addMinutes(new Date(), -5).getTime()));
			rs = stmt.executeQuery();
			if(rs.next())
				coupon = new Coupon(rs);
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return coupon;
	}
	
	public static Coupon getCouponById(long couponId) {
		Coupon coupon = null;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery(null, TABNAME, "id = ?");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, couponId);
			rs = stmt.executeQuery();
			if(rs.next())
				coupon = new Coupon(rs);
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return coupon;
	}

	public static List<Coupon> getActiveCoupons(){
		List<Coupon> list = new ArrayList<Coupon>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String curDate = "'" + df.format(new Date()) + "'";
		
		String query = DBUtils.prepareSelectQuery(null, TABNAME, "is_onetime = 0 and to_ > " + curDate, "order by created desc");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();
			while(rs.next())
				list.add(new Coupon(rs));
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return list;
	}
	
	public static void markApplied(Coupon coupon, long cartId){
		if(coupon.isOneTime){
			coupon.setState(Constants.CouponState.APPLIED);
			CartService.removeCoupon(coupon.getMappingId()); // remove from old cart and order
			coupon.setMappingId(cartId);
			try{
				coupon.dbUpdate();
			}catch(Exception e){
				ExceptionUtils.logException(e);
			}
		}
	}
	
	public static void markProcessed(long couponId, long cartId){
		Coupon coupon = getCouponById(couponId);
		if(coupon != null && coupon.isOneTime){
			coupon.setState(Constants.CouponState.PROCESSED);
			CartService.removeCoupon(coupon.getMappingId()); // remove from old cart and order
			coupon.setMappingId(cartId);
			try{
				coupon.dbUpdate();
			}catch(Exception e){
				ExceptionUtils.logException(e);
			}
		}
	}
	
	public static void markUsed(long couponId, long cartId){
		Coupon coupon = getCouponById(couponId);
		if(coupon != null && coupon.isOneTime){
			coupon.setState(Constants.CouponState.USED);
			CartService.removeCoupon(coupon.getMappingId()); // remove from old cart and order
			coupon.setMappingId(cartId);
			try{
				coupon.dbUpdate();
			}catch(Exception e){
				ExceptionUtils.logException(e);
			}
		}
	}
	
	public static void markActive(long couponId){
		Coupon coupon = getCouponById(couponId);
		if(coupon != null && coupon.isOneTime){
			coupon.setState(Constants.CouponState.ACTIVE);
			CartService.removeCoupon(coupon.getMappingId()); // remove from old cart and order
			coupon.setMappingId(-1);
			try{
				coupon.dbUpdate();
			}catch(Exception e){
				ExceptionUtils.logException(e);
			}
		}
	}
	
	public long dbUpdate() throws SQLException{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareUpdateQuery(TABNAME, "state = ?, mapping_id = ?, modified = CURRENT_TIMESTAMP", "id = ?");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setString(1, this.state);
			stmt.setLong(2, this.mappingId);
			stmt.setLong(3, this.id);
			return stmt.executeUpdate();
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
	}
	
	public static boolean isValidCoupon(String code){
		if(getCoupon(code) != null)
			return true;
		else
			return false;
	}
	
	public static int createOneTimeCoupon(String code){
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareInsertQuery(TABNAME, "code, discount, scope, is_onetime, state, to_, modified", "?,?,?,?,?,?,current_timestamp");
		System.out.println(query);
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setString(1, code);
			stmt.setInt(2, 10);
			stmt.setString(3, Constants.CouponScope.CART);
			stmt.setBoolean(4, true);
			stmt.setString(5, Constants.CouponState.ACTIVE);
			stmt.setDate(6, new java.sql.Date(DateUtils.addMonths(new Date(), 1).getTime()));
			return stmt.executeUpdate();
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return -1;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public long getMappingId() {
		return mappingId;
	}

	public void setMappingId(long mappingId) {
		this.mappingId = mappingId;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

}
