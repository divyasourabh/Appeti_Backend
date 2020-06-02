package com.appeti.database.table.order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.appeti.database.utils.DBUtils;
import com.appeti.utils.ExceptionUtils;

public class Tax {
	private long id;
	private String name;
	private double percent;
	
	private static final String TABNAME = "tax";
	
	private Tax(ResultSet rs) throws SQLException{
		this.id = rs.getLong("id");
		this.name = rs.getString("name");
		this.percent = rs.getDouble("percent");
	}
	
	public static Tax getById(long id){
		Tax tax = null;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery(null, TABNAME , "id = ?");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, id);
			rs = stmt.executeQuery();
			if(rs.next())
				tax = new Tax(rs);
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return tax;
	}
	
	public static List<Tax> getAllTaxes(){
		List<Tax> list = new ArrayList<Tax>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery(null, TABNAME , null);
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();
			while(rs.next())
				list.add(new Tax(rs));
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return list;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPercent() {
		return percent;
	}
	public void setPercent(double percent) {
		this.percent = percent;
	}
}
