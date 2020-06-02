package com.appeti.database.table.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.WordUtils;

import com.appeti.database.utils.DBUtils;
import com.appeti.utils.Cache;
import com.appeti.utils.ExceptionUtils;

public class Ptitle {
	private long ptitleId;
	private long productId;
	private String name;
	private String description;
	private boolean isDefault;
	private Date dateAdded;
	private Date dateModified;
	
	private static final String TABLENAME = "ptitle";
	
	//private static boolean useCache = true;
	private static Cache<Long, Ptitle> ptitleCache = new Cache<Long, Ptitle>();
	private static Cache<Long, Ptitle> defaultPtitleCache = new Cache<Long, Ptitle>();
	private static Cache<Long, List<Ptitle>> ptitleListCache = new Cache<Long, List<Ptitle>>();
	
	
	private Ptitle(ResultSet rs) throws SQLException{
		this.ptitleId = rs.getLong("ptitle_id");
		this.productId = rs.getLong("product_id");
		this.name = WordUtils.capitalizeFully(rs.getString("name"));
		this.description = rs.getString("description");
		this.isDefault = rs.getBoolean("is_default");
		this.dateAdded = rs.getDate("date_added");
		this.dateModified = rs.getDate("date_modified");
	}
	
	public static List<Ptitle> getPtitlesByProductId(long id){
		List<Ptitle> list = ptitleListCache.get(id);
		if(list != null)
			return list;
		
		list = new ArrayList<Ptitle>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery(null, TABLENAME , "product_id = ?");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, id);
			rs = stmt.executeQuery();
			while(rs.next())
				list.add(new Ptitle(rs));
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		
		if(list != null)
			ptitleListCache.put(id, list);
		
		return list;
	}
	
	public static Ptitle getPtitleById(long id){
		Ptitle ptitle = ptitleCache.get(id);
		if(ptitle != null)
			return ptitle;
		
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery(null, TABLENAME , "ptitle_id = ?");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, id);
			rs = stmt.executeQuery();
			if(rs.next())
				ptitle = new Ptitle(rs);
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		
		if(ptitle != null)
			ptitleCache.put(id, ptitle);
		return ptitle;
	}
	
	public static Ptitle getDefaultPtitle(long productId){
		Ptitle ptitle = defaultPtitleCache.get(productId);
		if(ptitle != null)
			return ptitle;
		
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery(null, TABLENAME , "product_id = ? and is_default = 1");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, productId);
			rs = stmt.executeQuery();
			if(rs.next())
				ptitle = new Ptitle(rs);
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		
		if(ptitle != null)
			defaultPtitleCache.put(productId, ptitle);
		
		return ptitle;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}
}
