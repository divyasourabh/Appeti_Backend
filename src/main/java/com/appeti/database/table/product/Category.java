package com.appeti.database.table.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

import com.appeti.database.utils.DBUtils;
import com.appeti.utils.ExceptionUtils;

public class Category {
	private long categoryId;
	private long parentId;
	private long nodeId;
	private String name;
	private String description;
	
	public static final String TABNAME = "category";
	private Logger logger = Logger.getLogger(Category.class);
	
	public Category(ResultSet rs) throws SQLException{
		this.categoryId = rs.getLong("category_id");
		this.parentId = rs.getLong("parent_id");
		this.nodeId = rs.getLong("node_id");
		this.name = rs.getString("name");
		this.description = rs.getString("description");
	}
	
	public static Category getCategoryById(long categoryId) {
		Category cat = null;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery(null, TABNAME, "category_id = ?");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, categoryId);
			rs = stmt.executeQuery();
			if(rs.next())
				cat = new Category(rs);
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return cat;
	}

	public static Set<Long> getImediateSubCategoryIds(long categoryId) {
		Set<Long> set = new HashSet<Long>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery("category_id", TABNAME, "parent_id = ?");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, categoryId);
			rs = stmt.executeQuery();
			while(rs.next())
				set.add(rs.getLong("category_id"));
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return set;
	}
	
	public static Set<Long> getAllSubCategoryIds(long categoryId) {
		Set<Long> catIds = new HashSet<Long>();
		catIds.add(categoryId);
		Set<Long> children = getImediateSubCategoryIds(categoryId);
		Iterator<Long> iterator = children.iterator();
		while(iterator.hasNext()){
			catIds.addAll(getAllSubCategoryIds(iterator.next()));
		}
		return catIds;
	}
	
	public long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}
	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
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

	public long getNodeId() {
		return nodeId;
	}

	public void setNodeId(long nodeId) {
		this.nodeId = nodeId;
	}
}
