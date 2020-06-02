package com.appeti.database.table.payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.appeti.database.utils.DBUtils;
import com.appeti.utils.ExceptionUtils;

public class Trans {
	private long transId;
	private Date date;
	private long orderId;
	private long userId;
	private long midTransId;
	private double amountCharged;
	private String currency;
	private String source;
	private String trackingId;
	private String notes;
	
	private static final String TABNAME = "trans";
	
	private Trans(ResultSet rs) throws SQLException{
		this.transId = rs.getLong("trans_id");
		this.date = rs.getTimestamp("date_");
		this.orderId = rs.getLong("order_id");
		this.userId = rs.getLong("user_id");
		this.midTransId = rs.getLong("mid_trans_id");
		this.amountCharged = rs.getDouble("amount_charged");
		this.currency = rs.getString("currency");
		this.source = rs.getString("source");
		this.trackingId = rs.getString("tracking_id");
		this.notes = rs.getString("notes");
	}
	
	public Trans(long userId, long orderId, long midTransId){
		this.userId = userId;
		this.orderId = orderId;
		this.midTransId = midTransId;
	}
	
	public static Trans createTrans(MidTrans midTrans){
		Trans trans = null;
		if(midTrans != null){
			trans = new Trans(midTrans.getUserId(), midTrans.getOrderId(),midTrans.getTransId());
			trans.setTrackingId(midTrans.getTrackingId());
			trans.setAmountCharged(midTrans.getAmount());
			trans.setCurrency(midTrans.getCurrency());
			trans.setSource(midTrans.getSource());
			try{
				long id = trans.dbInsert();
				return Trans.getById(id);
			}catch(Exception e){
				ExceptionUtils.logException(e);
				return null;
			}
		}
		return trans;
	}
	
	public static Trans getById(long transId) {
		Trans trans = null;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery(null, TABNAME, "trans_id = ?");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, transId);
			rs = stmt.executeQuery();
			if(rs.next())
				trans = new Trans(rs);
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return trans;
	}
	
	public long dbInsert() throws SQLException{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareInsertQuery(TABNAME, "order_id, user_id, mid_trans_id, amount_charged, currency, source, tracking_id, notes", "?,?,?,?,?,?,?,?");
		try{
			con = DBUtils.getConnection();
			con.setAutoCommit(false);
			stmt = con.prepareStatement(query);
			int ordinal = 1;
			stmt.setLong(ordinal++, this.orderId);
			stmt.setLong(ordinal++, this.userId);
			stmt.setLong(ordinal++, this.midTransId);
			stmt.setDouble(ordinal++, this.amountCharged);
			stmt.setString(ordinal++, this.currency);
			stmt.setString(ordinal++, this.source);
			stmt.setString(ordinal++, this.trackingId);
			stmt.setString(ordinal++, this.notes);
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
	
	public long getTransId() {
		return transId;
	}
	public void setTransId(long transId) {
		this.transId = transId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public double getAmountCharged() {
		return amountCharged;
	}
	public void setAmountCharged(double amountCharged) {
		this.amountCharged = amountCharged;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getTrackingId() {
		return trackingId;
	}
	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}

	public long getMidTransId() {
		return midTransId;
	}

	public void setMidTransId(long midTransId) {
		this.midTransId = midTransId;
	}
}
