package com.appeti.database.table.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.log4j.Logger;

import com.appeti.database.utils.DBUtils;
import com.appeti.utils.Cache;
import com.appeti.utils.ExceptionUtils;

public class Product {
	private long productId;
	private long categoryId;
	private long nodeId; // present in category also but can be different ex. Barfi in India and Kaju Barfi in North India
	private String name;
	private String description;
	private Date dateAdded;
	private Date dateModified;
	
	private static final Logger log = Logger.getLogger(Product.class);
	public static final String TABNAME = "product";
	
	//private static int CACHE_CAPACITY = 50;
	private static Cache<Long, Product> productCache = new Cache<Long, Product>();
	
	private Product(ResultSet rs) throws SQLException{
		this.productId = rs.getLong("product_id");
		this.categoryId = rs.getLong("category_id");
		this.nodeId = rs.getLong("node_id");
		this.name = WordUtils.capitalizeFully(rs.getString("name"));
		this.description = rs.getString("description");
		this.dateAdded = rs.getDate("date_added");
		this.dateModified = rs.getDate("date_modified");
	}
	
	public static Product getProductById(long productId, boolean useCache) {
		Product product = null;
		if(useCache)
			product = getFromCache(productId);
		
		if(product == null){
			Connection con = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;

			String query = DBUtils.prepareSelectQuery(null, TABNAME, "product_id = ?");
			try{
				con = DBUtils.getConnection();
				stmt = con.prepareStatement(query);
				stmt.setLong(1, productId);
				rs = stmt.executeQuery();
				if(rs.next())
					product = new Product(rs);
			}catch(Exception e){
				ExceptionUtils.logException(e,query);
			}finally{
				DBUtils.closeAll(rs,stmt,con);
			}
			addToCache(product);
		}
		return product;
	}
	
	public static Product getProductById(long productId) {
		return getProductById(productId, true);
	}
	
	public static List<Product> getLatestProducts() {
		List<Product> list = new ArrayList<Product>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		String query = DBUtils.prepareSelectQuery(null, TABNAME, null, "order by date_added desc");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();
			while(rs.next())
				list.add(new Product(rs));
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return list;
	}
	
	public static List<Product> getProducts(Set<Long> nodeIds, Set<Long> categoryIds){
		List<Product> list = new ArrayList<Product>();
		String where1 = "";
		String where2 = "";
		String nodeStr = "";
		String catStr = "";
		if(nodeIds != null){
			Iterator<Long> iterator = nodeIds.iterator();
			while(iterator.hasNext()){
				nodeStr += iterator.next() + ",";
			}
		}
		if(StringUtils.isNotBlank(nodeStr)){
			nodeStr = nodeStr.substring(0, nodeStr.lastIndexOf(","));
			where1 = "node_id in ( "+ nodeStr +" )";
		}
		if(categoryIds != null){
			Iterator<Long> iterator = categoryIds.iterator();
			while(iterator.hasNext()){
				catStr += iterator.next() + ",";
			}
		}
		if(StringUtils.isNotBlank(catStr)){
			catStr = catStr.substring(0, catStr.lastIndexOf(","));
			where2 = "category_id in ( "+ catStr +" )";
		}
		if(StringUtils.isNotBlank(where1) && StringUtils.isNotBlank(where2))
			where2 = " and " + where2;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery(null, TABNAME, where1 + where2);
		log.info("query " + query);
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();
			while(rs.next())
				list.add(new Product(rs));
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		log.info("list" + list.size());
		return list;
	}
	
	private static Product getFromCache(long productId){
		return productCache.get(productId);
	}
	
	private static void addToCache(Product product){
		if(product != null)
			productCache.put(product.getProductId(), product);
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

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public long getNodeId() {
		return nodeId;
	}

	public void setNodeId(long nodeId) {
		this.nodeId = nodeId;
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
