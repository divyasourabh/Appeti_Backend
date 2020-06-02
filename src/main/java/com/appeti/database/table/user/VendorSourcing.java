package com.appeti.database.table.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.appeti.database.utils.DBUtils;

public class VendorSourcing {
	private String brandName;
	private String bestProduct;
	private String bestPrice;
	private String secondBestProduct;
	private String secondBestPrice;
	private long addrId;
	private String website;
	private String otherInfo;
	
	private static final String TABNAME = "vendor_sourcing";
	
	public long dbInsert() throws SQLException{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareInsertQuery(TABNAME, "brand_name, best_product, best_price , second_best_product, second_best_price, addrId, website, other_info", "?,?,?,?,?,?,?,?");
		try{
			con = DBUtils.getConnection();
			con.setAutoCommit(false);
			stmt = con.prepareStatement(query);
			int ordinal = 1;
			stmt.setString(ordinal++, this.brandName);
			stmt.setString(ordinal++, this.bestProduct);
			stmt.setString(ordinal++, this.bestPrice);
			stmt.setString(ordinal++, this.secondBestProduct);
			stmt.setString(ordinal++, this.secondBestPrice);
			stmt.setLong(ordinal++, this.addrId);
			stmt.setString(ordinal++, this.website);
			stmt.setString(ordinal++, this.otherInfo);
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
	
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getBestProduct() {
		return bestProduct;
	}
	public void setBestProduct(String bestProduct) {
		this.bestProduct = bestProduct;
	}
	public String getBestPrice() {
		return bestPrice;
	}
	public void setBestPrice(String bestPrice) {
		this.bestPrice = bestPrice;
	}
	public String getSecondBestProduct() {
		return secondBestProduct;
	}
	public void setSecondBestProduct(String secondBestProduct) {
		this.secondBestProduct = secondBestProduct;
	}
	public String getSecondBestPrice() {
		return secondBestPrice;
	}
	public void setSecondBestPrice(String secondBestPrice) {
		this.secondBestPrice = secondBestPrice;
	}
	public long getAddrId() {
		return addrId;
	}
	public void setAddrId(long addrId) {
		this.addrId = addrId;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getOtherInfo() {
		return otherInfo;
	}
	public void setOtherInfo(String otherInfo) {
		this.otherInfo = otherInfo;
	}
	
	
}
