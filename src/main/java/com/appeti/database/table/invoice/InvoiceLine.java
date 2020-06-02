package com.appeti.database.table.invoice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.appeti.database.table.order.OrderItem;
import com.appeti.database.utils.DBUtils;
import com.appeti.utils.ExceptionUtils;

public class InvoiceLine {
	private long invoiceLineId;
	private long invoiceId;
	private long tagId;
	private long quantity;
	private long perUnitPrice;
	private long totalPrice;
	private Date created;
	
	private static final String TABNAME = "invoice_line";
	
	private InvoiceLine(){}
	
	private InvoiceLine(ResultSet rs) throws SQLException{
		this.invoiceId = rs.getLong("invoice_id");
		this.tagId = rs.getLong("tag_id");
		this.quantity = rs.getLong("quantity");
		this.perUnitPrice = rs.getLong("price_per_unit");
		this.totalPrice = rs.getLong("total_price");
		this.created = rs.getTimestamp("created");
	}

	public static InvoiceLine getById(long lineId){
		InvoiceLine line = null;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery(null, TABNAME, "invoice_line_id = ?");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, lineId);
			rs = stmt.executeQuery();
			if(rs.next())
				line = new InvoiceLine(rs);
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return line;
	}

	public static List<InvoiceLine> getInvoiceLines(long invoiceId){
		List<InvoiceLine> list = new ArrayList<InvoiceLine>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery(null, TABNAME, "invoice_id = ?");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, invoiceId);
			rs = stmt.executeQuery();
			while(rs.next())
				list.add(new InvoiceLine(rs));
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return list;
	}
	
	public static InvoiceLine createLine(long invoiceId, OrderItem item){
		if(item == null)
			return null;
		try{
			InvoiceLine line = new InvoiceLine();
			line.setInvoiceId(invoiceId);
			line.setTagId(item.getTagId());
			line.setQuantity(item.getQuantity());
			line.setPerUnitPrice(item.getPerUnitPrice());
			line.setTotalPrice(item.getTotalPrice());
			long id = line.dbInsert();
			return getById(id);
		}catch(SQLException e){
			ExceptionUtils.logException(e);
		}
		return null;
	}
	
	private long dbInsert() throws SQLException{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareInsertQuery(TABNAME, "invoice_id,tag_id,quantity,price_per_unit,total_price", "?,?,?,?,?");
		try{
			con = DBUtils.getConnection();
			con.setAutoCommit(false);
			stmt = con.prepareStatement(query);
			int ordinal = 1;
			stmt.setLong(ordinal++, this.invoiceId);
			stmt.setLong(ordinal++, this.tagId);
			stmt.setLong(ordinal++,this.quantity);
			stmt.setDouble(ordinal++, this.perUnitPrice);
			stmt.setDouble(ordinal++, this.totalPrice);
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
	
	public long getInvoiceLineId() {
		return invoiceLineId;
	}

	public void setInvoiceLineId(long invoiceLineId) {
		this.invoiceLineId = invoiceLineId;
	}

	public long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public long getTagId() {
		return tagId;
	}

	public void setTagId(long tagId) {
		this.tagId = tagId;
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
}
