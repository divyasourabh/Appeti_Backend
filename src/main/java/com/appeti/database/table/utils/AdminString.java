package com.appeti.database.table.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.appeti.database.utils.DBUtils;
import com.appeti.utils.ExceptionUtils;

public class AdminString {
	
	private static final String TABNAME = "admin_string";
	
	public static String get(String name){
		String query = DBUtils.prepareSelectQuery("value", TABNAME, "name = ?");
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			con = DBUtils.getBackupConnection();
			stmt = con.prepareStatement(query);
			stmt.setString(1, name);
			rs = stmt.executeQuery();
			if(rs.next()){
				return rs.getString("value");
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
}
