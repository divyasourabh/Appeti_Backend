package com.appeti.database.table.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.appeti.database.table.utils.Attribute;
import com.appeti.database.utils.DBUtils;
import com.appeti.utils.ExceptionUtils;

public class ProductAttribute {
	private long attrId;
	private long productId;
	private long ptitleId;
	private String name;
	private String value;
	private String description;
	private boolean isShared; // shared by all ptitles
	
	private final static String TABLENAME = "prod_attribute";
	
	private ProductAttribute(ResultSet rs) throws SQLException{
		this.attrId = rs.getLong("attr_id");
		this.productId = rs.getLong("product_id");
		this.ptitleId = rs.getLong("ptitle_id");
		this.name = rs.getString("name");
		this.value = rs.getString("value_");
		this.description = rs.getString("description");
		this.isShared = rs.getBoolean("is_shared");
	}
	
	public static List<Attribute> getAttributesByProductId(long id){
		List<Attribute> list = new ArrayList<Attribute>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery("name,value_", TABLENAME , "product_id = ? and is_shared = true");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, id);
			rs = stmt.executeQuery();
			while(rs.next())
				list.add(new Attribute(rs.getString("name"),rs.getString("value_")));
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return list;
	}
	
	public static List<Attribute> getAttributesByPtitleId(long productId, long ptitleId){
		List<Attribute> list = new ArrayList<Attribute>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery("name,value_", TABLENAME , "product_id = ? and ptitle_id = ?");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, productId);
			stmt.setLong(2, ptitleId);
			rs = stmt.executeQuery();
			while(rs.next())
				list.add(new Attribute(rs.getString("name"),rs.getString("value_")));
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return list;
	}

	public static List<Attribute> getAllAttributesByPtitleId(long productId, long ptitleId){
		List<Attribute> list = new ArrayList<Attribute>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery("name,value_", TABLENAME , "(product_id = ? and is_shared = 1) or ptitle_id = ?");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, productId);
			stmt.setLong(2, ptitleId);
			rs = stmt.executeQuery();
			while(rs.next())
				list.add(new Attribute(rs.getString("name"),rs.getString("value_")));
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return list;
	}

	public long getId() {
		return attrId;
	}
	public void setId(long id) {
		this.attrId = id;
	}
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public long getPtitleId() {
		return ptitleId;
	}
	public void setPtitleId(long ptitleId) {
		this.ptitleId = ptitleId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isDefault() {
		return isShared;
	}
	public void setDefault(boolean isDefault) {
		this.isShared = isDefault;
	}
}
