package com.appeti.database.table.payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.appeti.database.table.cart.Cart;
import com.appeti.database.utils.DBUtils;
import com.appeti.utils.ExceptionUtils;

public class MidTrans {
	private long transId;
	private Date date;
	private long orderId;
	private long userId;
	private String source;
	private String trackingId;
	private String bankRefNo;
	private String transStatus;
	private String failureMessage;
	private String mode;
	private String cardName;
	private String statusCode;
	private String statusMsg;
	private String currency;
	private double amount;
	
	private static final String TABNAME = "mid_trans";
	
	private MidTrans(ResultSet rs) throws SQLException{
		this.transId = rs.getLong("trans_id");
		this.date = rs.getTimestamp("date_");
		this.orderId = rs.getLong("order_id");
		this.userId = rs.getLong("user_id");
		this.source = rs.getString("source");
		this.trackingId = rs.getString("tracking_id");
		this.bankRefNo = rs.getString("bank_ref_no");
		this.transStatus = rs.getString("trans_status");
		this.failureMessage = rs.getString("failure_msg");
		this.mode = rs.getString("mode");
		this.cardName = rs.getString("card_name");
		this.statusCode = rs.getString("status_code");
		this.statusMsg = rs.getString("status_msg");
		this.currency = rs.getString("currency");
		this.amount = rs.getDouble("amount");
	}
	
	public MidTrans(long orderId, long userId, String source, double amount, String currency){
		this.orderId = orderId;
		this.userId = userId;
		this.source = source;
		this.amount = amount;
		this.currency = currency;
	}
	
	public static long createTrans(long orderId, long userId, String source, double amount, String currency){
		try{
			MidTrans trans = new MidTrans(orderId, userId, source, amount, currency);
			return trans.dbInsert();
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}
		return -1;
	}
	
	public static long createTrans(long orderId, long userId, String source, double amount){
		return createTrans(orderId, userId, source, amount, "INR");
	}
	
	public static MidTrans getById(long transId) {
		MidTrans trans = null;
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
				trans = new MidTrans(rs);
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return trans;
	}
	private long dbInsert() throws SQLException{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareInsertQuery(TABNAME, "order_id, user_id, source, amount, currency", "?,?,?,?,?");
		try{
			con = DBUtils.getConnection();
			con.setAutoCommit(false);
			stmt = con.prepareStatement(query);
			int ordinal = 1;
			stmt.setLong(ordinal++, this.orderId);
			stmt.setLong(ordinal++, this.userId);
			stmt.setString(ordinal++, this.source);
			stmt.setDouble(ordinal++, this.amount);
			stmt.setString(ordinal++, this.currency);
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
		
		String query = DBUtils.prepareUpdateQuery(TABNAME, "tracking_id = ?, bank_ref_no = ?, trans_status = ? , failure_msg = ? , mode = ? , card_name = ? , status_code = ? ,status_msg= ?", "trans_id = ?");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			int ordinal = 1;
			stmt.setString(ordinal++, this.trackingId);
			stmt.setString(ordinal++, this.bankRefNo);
			stmt.setString(ordinal++, this.transStatus);
			stmt.setString(ordinal++, this.failureMessage);
			stmt.setString(ordinal++, this.mode);
			stmt.setString(ordinal++, this.cardName);
			stmt.setString(ordinal++, this.statusCode);
			stmt.setString(ordinal++, this.statusMsg);
			stmt.setLong(ordinal++, this.transId);
			return stmt.executeUpdate();
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
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
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
	public String getBankRefNo() {
		return bankRefNo;
	}
	public void setBankRefNo(String bankRefNo) {
		this.bankRefNo = bankRefNo;
	}
	public String getTransStatus() {
		return transStatus;
	}
	public void setTransStatus(String transStatus) {
		this.transStatus = transStatus;
	}
	public String getFailureMessage() {
		return failureMessage;
	}
	public void setFailureMessage(String failureMessage) {
		this.failureMessage = failureMessage;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusMsg() {
		return statusMsg;
	}
	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
