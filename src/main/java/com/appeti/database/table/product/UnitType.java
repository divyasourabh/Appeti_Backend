package com.appeti.database.table.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.appeti.database.utils.DBUtils;
import com.appeti.utils.ExceptionUtils;

public class UnitType {
	private long id;
	private String name;
	
	private static final String TABLENAME = "unit_type";
	
	private UnitType(ResultSet rs) throws SQLException{
		this.id = rs.getLong("id");
		this.name = rs.getString("name");
	}

	public static String getUnitName(long id){
		String name = null;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery("name", TABLENAME, "id = ?");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, id);
			rs = stmt.executeQuery();
			if(rs.next())
				name = rs.getString("name");
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return name;
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
}
