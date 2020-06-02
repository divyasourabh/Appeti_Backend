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

public class Click {
	private long clickId;
	private Date clickTime;
	private long tagId;
	private long ptitleId;
	private long productId;
	private String source;
	
	public static final String TABNAME = "click";
	
	public static void record(long productId, long ptitleId, long tagId, String source){
		Click click = new Click();
		click.setProductId(productId);
		click.setPtitleId(ptitleId);
		click.setTagId(tagId);
		click.setSource(source);
		try{
			click.dbInsert();
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}
	}
	
	private void dbInsert() throws SQLException{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareInsertQuery(TABNAME, "product_id, ptitle_id, tag_id, source", "?,?,?,?");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, this.productId);
			stmt.setLong(2, this.ptitleId);
			stmt.setLong(3, this.tagId);
			stmt.setString(4, this.source);
			stmt.executeUpdate();
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
	}
	
	public static Map<String,Long> getClicks(Date startDate, Date endDate){
		Map<String,Long> map = new HashMap<String, Long>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery("product_id, ptitle_id, tag_id, count(1) as clicks", TABNAME, "click_time >= ? and click_time < ?"," group by product_id, ptitle_id, tag_id");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setDate(1, new java.sql.Date(startDate.getTime()));
			stmt.setDate(2, new java.sql.Date(endDate.getTime()));
			rs = stmt.executeQuery();
			while(rs.next()){
				String key = rs.getLong("product_id") + "#" + rs.getLong("ptitle_id") + "#" + rs.getLong("tag_id");
				Long value = rs.getLong("clicks");
				map.put(key, value);
			}
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return map;
	}
	
	public long getClickId() {
		return clickId;
	}
	public void setClickId(long clickId) {
		this.clickId = clickId;
	}
	public Date getClickTime() {
		return clickTime;
	}
	public void setClickTime(Date clickTime) {
		this.clickTime = clickTime;
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
