package com.appeti.database.table.testimonial;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.appeti.database.utils.DBUtils;

public class ProductSuggestion {
	private String name;
	private String email;
	private String phone;
	private String product;
	private String brand;
	private String city;
	
	private static final String TABNAME = "product_suggestion";
	
	public boolean add(){
		try{
			this.dbInsert();
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	private long dbInsert() throws SQLException{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareInsertQuery(TABNAME, "name, email, phone, product, brand, city", "?,?,?,?,?,?");
		try{
			con = DBUtils.getConnection();
			con.setAutoCommit(false);
			stmt = con.prepareStatement(query);
			int i = 1;
			stmt.setString(i++, this.name);
			stmt.setString(i++, this.email);
			stmt.setString(i++, this.phone);
			stmt.setString(i++, this.product);
			stmt.setString(i++, this.brand);
			stmt.setString(i++, this.city);
			
			stmt.executeUpdate();
			rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");
			if(rs.next()){
				con.commit();
				return rs.getLong(1);
			}else{
				throw new SQLException();
			}
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
}
