package com.appeti.database.table.node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appeti.database.utils.DBUtils;
import com.appeti.utils.ExceptionUtils;

public class Node {
	private long nodeId;
	private long parentNodeId; // -1 for root
	private String nodeName;
	private String description;
	private long numProducts;
	private boolean isValid;
	private boolean displayInHeader;
	private int order;
	
	public static final String TABNAME = "node";
	public static Logger log = LoggerFactory.getLogger(Node.class);
	public static final String headerExtraWhereClause = " and display_in_header = 1";
	
	public Node(ResultSet rs) throws SQLException{
		this.nodeId = rs.getLong("node_id");
		this.parentNodeId = rs.getLong("parent_node_id");
		this.nodeName = rs.getString("name");
		this.description = rs.getString("description");
		this.numProducts = rs.getLong("num_products");
		this.isValid = rs.getBoolean("is_valid");
		this.displayInHeader = rs.getBoolean("display_in_header");
		this.order = rs.getInt("order_no");
	}
	
	public static Node getNodeById(long nodeId) {
		Node node = null;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery(null, TABNAME, "node_id = ?");
		try{
			log.info("get connection");
			con = DBUtils.getConnection();
			log.info("got connection " + con);
			stmt = con.prepareStatement(query);
			stmt.setLong(1, nodeId);
			rs = stmt.executeQuery();
			if(rs.next())
				node = new Node(rs);
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return node;
	}
	
	public static long getParentNodeId(long nodeId) {
		long parentId = -1;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery("parent_node_id", TABNAME, "node_id = ?");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, nodeId);
			rs = stmt.executeQuery();
			if(rs.next())
				parentId = rs.getLong("parent_node_id");
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return parentId;
	}
	
	public static Set<Long> getImediateChildNodeIds(long nodeId, String extraWhere) {
		Set<Long> set = new LinkedHashSet<Long>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery("node_id", TABNAME, "parent_node_id = ? " + extraWhere, "order by order_no asc");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, nodeId);
			rs = stmt.executeQuery();
			while(rs.next())
				set.add(rs.getLong("node_id"));
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		log.info("query result "+set.size());
		return set;
	}
	
	public static Set<Long> getImediateChildNodeIds(long nodeId) {
		return getImediateChildNodeIds(nodeId, "");
	}
	
	public static Set<Node> getImediateChildNodes(long nodeId) {
		Set<Node> set = new HashSet<Node>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery(null, TABNAME, "parent_node_id = ?");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, nodeId);
			rs = stmt.executeQuery();
			while(rs.next())
				set.add(new Node(rs));
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		log.info("query result "+set.size());
		return set;
	}
	
	public static Set<Long> getParentIds(long nodeId){
		Set<Long> childIds = new HashSet<Long>();
		getParentIds(nodeId, childIds);
		return childIds;
	}
	
	public static void getParentIds(long nodeId, Set<Long> set){
		if(nodeId == -1)
			return;
		long parentId = Node.getParentNodeId(nodeId);
		set.add(parentId);
		getParentIds(parentId,set);
	}
	
	public static Set<Long> getAllChildIds(long nodeId){
		log.info("at node");
		Set<Long> childIds = new HashSet<Long>();
		getAllChildIds(nodeId, childIds);
		log.info("size: " + childIds.size());
		return childIds;
	}
	
	public static void getAllChildIds(long nodeId, Set<Long> set){
		Set<Long> iSet = getImediateChildNodeIds(nodeId);
		log.info(nodeId + " child set : " + iSet.size());
		Iterator<Long> iterator = iSet.iterator();
		while(iterator.hasNext()){
			long id = iterator.next();
			log.info("processing node : " + id);
			getAllChildIds(id, set);
		}
		set.add(nodeId);
	}
	
	public long getNodeId() {
		return nodeId;
	}
	public void setNodeId(long nodeId) {
		this.nodeId = nodeId;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getNumProducts() {
		return numProducts;
	}
	public void setNumProducts(long numProducts) {
		this.numProducts = numProducts;
	}
	public boolean isValid() {
		return isValid;
	}
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public long getParentNodeId() {
		return parentNodeId;
	}

	public void setParentNodeId(long parentNodeId) {
		this.parentNodeId = parentNodeId;
	}

	public boolean isDisplayInHeader() {
		return displayInHeader;
	}

	public void setDisplayInHeader(boolean displayInHeader) {
		this.displayInHeader = displayInHeader;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
	
}
