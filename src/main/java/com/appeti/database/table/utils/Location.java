package com.appeti.database.table.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.appeti.database.table.product.Tag;
import com.appeti.database.utils.DBUtils;
import com.appeti.mail.MailOutputStream;
import com.appeti.utils.ExceptionUtils;

public class Location {
	private long id;
	private String city;
	private String state;
	private String country;
	private static Logger LOG = Logger.getLogger(Location.class);
	
	private static final String TABNAME = "location";
	
	private Location(ResultSet rs) throws SQLException{
		this.id = rs.getLong("id");
		this.city = rs.getString("city");
		this.state = rs.getString("state");
		this.country = rs.getString("country");
	}
	
	public static Location getById(long id){
		Location loc = null;
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
				loc = new Location(rs);
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return loc;
	}
	
	public static String getCityById(long id){
		Connection con = null;
		Statement stmt =null;
		ResultSet rs =null;
		String query = "SELECT city from location where id = " + id;
		try{
			con = DBUtils.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			if(rs.next()){
				return rs.getString("city");
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
	
	public static Map<Integer,String> getAllCities(){
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query = "SELECT location_id,city FROM location ORDER BY city asc";
		Map<Integer,String> city = new LinkedHashMap<Integer,String>();
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();
			while(rs.next()){
				city.put(rs.getInt("location_id"), rs.getString("city"));
			}
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}finally{
			DBUtils.closeResultSet(rs);
			DBUtils.closeStatement(stmt);
			DBUtils.closeConnection(con);
		}
		return city;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
}
