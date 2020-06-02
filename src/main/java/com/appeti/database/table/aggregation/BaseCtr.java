package com.appeti.database.table.aggregation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.appeti.database.utils.DBUtils;
import com.appeti.utils.ExceptionUtils;

public class BaseCtr {
	private long ptitleId;
	private long productId;
	private long tagId;
	private long clicks;
	private long sales;
	private double ctr;
	private Date lastModified;
	
	private BaseCtr(long productId, long ptitleId, long tagId, long clicks, long sales){
		this.productId = productId;
		this.ptitleId = ptitleId;
		this.tagId = tagId;
		this.clicks = clicks;
		this.sales = sales;
		this.ctr = 0D;
	}
	
	private BaseCtr(ResultSet rs) throws SQLException{
		this.productId = rs.getLong("product_id");
		this.ptitleId = rs.getLong("ptitle_id");
		this.tagId = rs.getLong("tag_id");
		this.clicks = rs.getLong("clicks");
		this.sales = rs.getLong("sales");
		this.ctr = rs.getDouble("ctr");
		this.lastModified = rs.getTimestamp("last_modified");
	}
	
	public static final String TABNAME = "base_ctr";
	
	public static BaseCtr getCtr(long productId, long ptitleId, long tagId){
		BaseCtr ctr = null;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery(null, TABNAME, "product_id = ? and ptitle_id = ? and tag_id = ?");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, productId);
			stmt.setLong(2, ptitleId);
			stmt.setLong(3, tagId);
			rs = stmt.executeQuery();
			if(rs.next())
				ctr = new BaseCtr(rs);
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return ctr;
	}
	
	public static double getCtrForProductId(long productId){
		double ctr = -1;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery("max(ctr) AS ctr", TABNAME, "product_id = ?");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, productId);
			rs = stmt.executeQuery();
			if(rs.next())
				ctr = rs.getDouble("ctr");
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return ctr;
	}
	
	public static double getCtrForPtitleId(long ptitleId){
		double ctr = -1;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery("ctr", TABNAME, "ptitle_id = ?");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, ptitleId);
			rs = stmt.executeQuery();
			if(rs.next())
				ctr = rs.getDouble("ctr");
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return ctr;
	}
	
	public static List<Long> getTopNProductIds(int number){
		List<Long> list = new LinkedList<Long>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery("product_id,max(ctr) max_ctr", TABNAME, null, "group by product_id order by max_ctr desc limit " + number);
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();
			while(rs.next())
				list.add(rs.getLong("product_id"));
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return list;
	}
	
	public static void upsertCtr(long productId, long ptitleId, long tagId, long clicks, long sales){
		BaseCtr ctr = getCtr(productId, ptitleId, tagId);
		try{
			if(ctr == null){
				ctr = new BaseCtr(productId,ptitleId,tagId,clicks,sales);
				ctr.dbInsert();
			}else{
				ctr.clicks += clicks;
				ctr.sales += sales;
				ctr.dbUpdate();
			}
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}
	}
	
	private void dbInsert() throws SQLException{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareInsertQuery(TABNAME, "product_id, ptitle_id, tag_id, clicks, sales, ctr", "?,?,?,?,?,?");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, this.productId);
			stmt.setLong(2, this.ptitleId);
			stmt.setLong(3, this.tagId);
			stmt.setLong(4, this.clicks);
			stmt.setLong(5, this.sales);
			stmt.setDouble(6, getCtr(this.clicks, this.sales));
			stmt.executeUpdate();
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
	}
	
	private void dbUpdate() throws SQLException{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareUpdateQuery(TABNAME, "clicks=?, sales=?, ctr=?, last_modified=CURRENT_TIMESTAMP", "product_id =? and ptitle_id=? and tag_id=?");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			int ordinal = 1;
			stmt.setLong(ordinal++, this.clicks);
			stmt.setLong(ordinal++, this.sales);
			stmt.setDouble(ordinal++, getCtr(this.clicks, this.sales));
			stmt.setLong(ordinal++, this.productId);
			stmt.setLong(ordinal++, this.ptitleId);
			stmt.setLong(ordinal++, this.tagId);
			stmt.executeUpdate();
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
	}

	private static double getCtr(long clicks, long sales){
		return (sales+1)/(clicks+2);
	}
}
