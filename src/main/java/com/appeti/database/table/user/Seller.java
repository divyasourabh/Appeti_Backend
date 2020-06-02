package com.appeti.database.table.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.appeti.database.utils.DBUtils;
import com.appeti.utils.ExceptionUtils;

public class Seller {
	private long sellerId;
	private String brandName;
	private long locationId;
	private long addrId;
	private String website;
	private String description;
	private String imageUrl;
	
	public static final String TABNAME = "seller";
	
	private Seller(ResultSet rs) throws SQLException{
		this.sellerId = rs.getLong("seller_id");
		this.brandName = rs.getString("brand_name");
		this.locationId = rs.getLong("location_id");
		this.addrId = rs.getLong("address_id");
		this.website = rs.getString("website");
		this.description = rs.getString("description");
	}
	
	public static Seller getById(long id){
		Seller seller = null;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery(null, TABNAME , "seller_id = ?");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, id);
			rs = stmt.executeQuery();
			if(rs.next())
				seller = new Seller(rs);
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return seller;
	}
	
	public long getSellerId() {
		return sellerId;
	}
	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getLocationId() {
		return locationId;
	}
	public void setLocationId(long locationId) {
		this.locationId = locationId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
