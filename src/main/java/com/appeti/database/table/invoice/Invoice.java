package com.appeti.database.table.invoice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.appeti.database.table.order.Order;
import com.appeti.database.utils.DBUtils;
import com.appeti.utils.Constants;
import com.appeti.utils.ExceptionUtils;

public class Invoice {
	private long invoiceId;
	private long userId;
	private long orderId;
	private long billingAddrId;
	private long shippingAddrId;
	private long totalAmount;
	private long discount;
	private long deliveryCharge;
	private long taxAmount;
	private long invoiceAmount;
	private long transId;
	private String notes;
	private Date created;
	private String state;
	
	private static final String TABNAME = "invoice";
	
	private Invoice(){}
	
	private Invoice(ResultSet rs) throws SQLException{
		this.invoiceId = rs.getLong("invoice_id");
		this.userId = rs.getLong("user_id");
		this.orderId = rs.getLong("order_id");
		this.billingAddrId = rs.getLong("billing_address_id");
		this.shippingAddrId = rs.getLong("shipping_address_id");
		this.totalAmount = rs.getLong("total_amount");
		this.discount = rs.getLong("discount");
		this.deliveryCharge = rs.getLong("delivery_charge");
		this.taxAmount = rs.getLong("tax_amount");
		this.invoiceAmount = rs.getLong("invoice_amount");
		this.transId = rs.getLong("trans_id");
		this.notes = rs.getString("notes");
		this.created = rs.getTimestamp("created");
		this.state = rs.getString("state");
	}

	public static Invoice getInvoiceById(long orderId){
		Invoice inv = null;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery(null, TABNAME , "invoice_id = ?");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, orderId);
			rs = stmt.executeQuery();
			if(rs.next())
				inv = new Invoice(rs);
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return inv;
	}

	public static Invoice getInvoiceForOrderId(long orderId){
		Invoice inv = null;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery(null, TABNAME , "order_id = ? ");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, orderId);
			rs = stmt.executeQuery();
			if(rs.next())
				inv = new Invoice(rs);
			}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return inv;
	}
	
	public static Invoice prepareInvoiceFromOrder(Order order){
		if(order == null)
			return null;
		try{
			Invoice inv = new Invoice();
			inv.setOrderId(order.getOrderId());
			inv.setUserId(order.getUserId());
			inv.setBillingAddrId(order.getBillingAddrId());
			inv.setShippingAddrId(order.getShippingAddrId());
			inv.setTotalAmount(order.getTotalAmount());
			inv.setDiscount(order.getDiscount());
			inv.setTaxAmount(order.getTaxAmount());
			inv.setDeliveryCharge(order.getDeliveryCharge());
			inv.setInvoiceAmount(order.getAmountPaid());
			inv.setTransId(order.getTransId());
			long invId = inv.dbInsert();
			inv = getInvoiceById(invId);
			return inv;
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}
		return null;
	}
	
	private long dbInsert() throws SQLException{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareInsertQuery(TABNAME, "user_id, order_id , billing_address_id, shipping_address_id, total_amount, discount, "
				+ "tax_amount, delivery_charge, invoice_amount, trans_id, state", "?,?,?,?,?,?,?,?,?,?,?");
		try{
			con = DBUtils.getConnection();
			con.setAutoCommit(false);
			stmt = con.prepareStatement(query);
			int ordinal = 1;
			stmt.setLong(ordinal++, this.getUserId());
			stmt.setLong(ordinal++, this.getOrderId());
			stmt.setLong(ordinal++, this.getBillingAddrId());
			stmt.setLong(ordinal++, this.getShippingAddrId());
			stmt.setLong(ordinal++, this.getTotalAmount());
			stmt.setLong(ordinal++, this.getDiscount());
			stmt.setLong(ordinal++, this.getTaxAmount());
			stmt.setLong(ordinal++, this.getDeliveryCharge());
			stmt.setLong(ordinal++, this.getInvoiceAmount());
			stmt.setLong(ordinal++, this.getTransId());
			stmt.setString(ordinal++, Constants.InvoiceState.NEW);
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
	
	public long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public long getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(long invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
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

	public long getShippingAddrId() {
		return shippingAddrId;
	}

	public void setShippingAddrId(long shippingAddrId) {
		this.shippingAddrId = shippingAddrId;
	}
	
	
}
