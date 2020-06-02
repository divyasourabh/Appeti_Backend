package com.appeti.database.table.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.appeti.database.utils.DBUtils;
import com.appeti.database.utils.Image;
import com.appeti.utils.ExceptionUtils;

public class PtitleImage {
	private long id;
	private long ptitleId;
	private String name;
	private String url;
	private boolean isDefault;
	
	private static final String TABLENAME = "ptitle_image";
	
	private PtitleImage(ResultSet rs) throws SQLException{
		this.id = rs.getLong("id");
		this.ptitleId = rs.getLong("ptitle_id");
		this.name = rs.getString("name");
		this.url = rs.getString("url");
		this.isDefault = rs.getBoolean("is_default");
	}
	
	private static Image getImage(ResultSet rs) throws SQLException{
		return new Image(rs.getString("url"), rs.getString("name"));
	}
	
	public static Image getDefaultImageByPtitleId(long id){
		Image image = null;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery(null, TABLENAME, "ptitle_id = ? and is_default = true");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, id);
			rs = stmt.executeQuery();
			if(rs.next())
				image = getImage(rs);
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return image;
	}
	
	public static List<Image> getAllImagesByPtitleId(long id){
		return getAllImagesByPtitleId(id, false);
	}
	
	public static List<Image> getAllImagesByPtitleId(long id,boolean excludeDefault){
		List<Image> list = new ArrayList<Image>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery(null, TABLENAME, "ptitle_id = ?" + (excludeDefault ? " and is_default != 1" : ""));
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, id);
			rs = stmt.executeQuery();
			while(rs.next())
				list.add(getImage(rs));
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return list;
	}

	
	public static String getImageUrl(long id){
		String url = null;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery("url", "image", "id = ?");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, id);
			rs = stmt.executeQuery();
			if(rs.next())
				url = rs.getString("url");
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return url;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
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

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
}
