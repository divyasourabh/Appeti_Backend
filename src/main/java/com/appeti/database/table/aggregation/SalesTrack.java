package com.appeti.database.table.aggregation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.appeti.database.utils.DBUtils;
import com.appeti.utils.ExceptionUtils;

public class SalesTrack {
	private long salestrackId;
	private Date saleTime;
	private long tagId;
	private long ptitleId;
	private long productId;
	private long quantity;
	private long sellerId;
	private String source;
	
	public static final String TABNAME = "sales_track";
	
	public static void record(long productId, long ptitleId, long tagId, long quantity, long sellerId, String source){
		SalesTrack sale = new SalesTrack();
		sale.setProductId(productId);
		sale.setPtitleId(ptitleId);
		sale.setTagId(tagId);
		sale.setQuantity(quantity);
		sale.setSellerId(sellerId);
		sale.setSource(source);
		try{
			sale.dbInsert();
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}
	}
	
	private void dbInsert() throws SQLException{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareInsertQuery(TABNAME, "product_id, ptitle_id, tag_id, quantity, seller_id, source", "?,?,?,?,?,?");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, this.productId);
			stmt.setLong(2, this.ptitleId);
			stmt.setLong(3, this.tagId);
			stmt.setLong(4, this.quantity);
			stmt.setLong(5, this.sellerId);
			stmt.setString(6, this.source);
			stmt.executeUpdate();
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
	}
	
	public static Map<String,Long> getSales(Date startDate, Date endDate){
		Map<String,Long> map = new HashMap<String, Long>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery("product_id, ptitle_id, tag_id, sum(quantity) as sales", TABNAME, "sale_time >= ? and sale_time < ?"," group by product_id, ptitle_id, tag_id");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setDate(1, new java.sql.Date(startDate.getTime()));
			stmt.setDate(2, new java.sql.Date(endDate.getTime()));
			rs = stmt.executeQuery();
			while(rs.next()){
				String key = rs.getLong("product_id") + "#" + rs.getLong("ptitle_id") + "#" + rs.getLong("tag_id");
				Long value = rs.getLong("sales");
				map.put(key, value);
			}
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return map;
	}
	
	public long getSalestrackId() {
		return salestrackId;
	}

	public void setSalestrackId(long salestrackId) {
		this.salestrackId = salestrackId;
	}

	public Date getSaleTime() {
		return saleTime;
	}

	public void setSaleTime(Date saleTime) {
		this.saleTime = saleTime;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public long getSellerId() {
		return sellerId;
	}

	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
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
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
}
