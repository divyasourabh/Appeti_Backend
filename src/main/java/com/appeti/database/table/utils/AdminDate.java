package com.appeti.database.table.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import com.appeti.database.utils.DBUtils;
import com.appeti.utils.ExceptionUtils;

public class AdminDate {
	
	public static Date get(String name){
		String query = "Select value from admin_date where name = ?";
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setString(1, name);
			rs = stmt.executeQuery();
			if(rs.next()){
				return rs.getTimestamp("value");
			}
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}finally{
			DBUtils.closeResultSet(rs);
			DBUtils.closeStatement(stmt);
			DBUtils.closeConnection(con);
		}
		return null;
	}
	
	public static int set(String name,Date value){
		String query = "Update admin_date set value = ? where name = ?";
		Connection con = null;
		PreparedStatement stmt = null;
		int count = 0;
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setDate(1, new java.sql.Date(value.getTime()));
			stmt.setString(2, name);
			count = stmt.executeUpdate();
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}finally{
			DBUtils.closeStatement(stmt);
			DBUtils.closeConnection(con);
		}
		return count;
	}
	
	public static int add(String name,Date value){
		String query = "Insert into admin_date(name,value) values (?,?)";
		Connection con = null;
		PreparedStatement stmt = null;
		int count = 0;
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setDate(2, new java.sql.Date(value.getTime()));
			stmt.setString(1, name);
			count = stmt.executeUpdate();
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}finally{
			DBUtils.closeStatement(stmt);
			DBUtils.closeConnection(con);
		}
		return count;
	}
	
	public static int incrementAdminDate(String key, int num){
		return set(key, DateUtils.addDays(get(key), num));
	}
}
