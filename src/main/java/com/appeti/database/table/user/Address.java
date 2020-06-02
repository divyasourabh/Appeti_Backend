package com.appeti.database.table.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.appeti.database.utils.DBUtils;
import com.appeti.utils.ExceptionUtils;

public class Address {
	private long id;
	private long userId;
	private String firstName;
	private String lastName;
	private String name1;
	private String name2;
	private String addr1;
	private String addr2;
	private String city;
	private String state;
	private String country;
	private String zipCode;
	private String emailAddr;
	private String phoneNumber;
	private String altPhoneNumber;
	private boolean isDefault;
	private boolean isRemoved;
	private Date lastUsed;
	
	private static final String TABNAME = "address";
	private static final String INDIA = "India";
	
	private Address(ResultSet rs) throws SQLException{
		this.id = rs.getLong("id");
		this.userId = rs.getLong("user_id");
		this.firstName = StringUtils.defaultIfEmpty(rs.getString("first_name"),"");
		this.lastName = StringUtils.defaultIfEmpty(rs.getString("last_name"),"");
		this.name1 = StringUtils.defaultIfEmpty(rs.getString("name1"),"");
		this.name2 = StringUtils.defaultIfEmpty(rs.getString("name2"),"");
		this.addr1 = StringUtils.defaultIfEmpty(rs.getString("addr1"),"");
		this.addr2 = StringUtils.defaultIfEmpty(rs.getString("addr2"),"");
		this.city = StringUtils.defaultIfEmpty(rs.getString("city"),"");
		this.state = StringUtils.defaultIfEmpty(rs.getString("state"),"");
		this.country = StringUtils.defaultIfEmpty(rs.getString("country"),"");
		this.zipCode = StringUtils.defaultIfEmpty(rs.getString("zip_code"),"");
		this.emailAddr = StringUtils.defaultIfEmpty(rs.getString("email_addr"),"");
		this.isDefault = rs.getBoolean("is_default");
		this.phoneNumber = StringUtils.defaultIfEmpty(rs.getString("phone_number"),"");
		this.altPhoneNumber = StringUtils.defaultIfEmpty(rs.getString("alt_phone_number"),"");
		this.isRemoved = rs.getBoolean("is_removed");
		this.lastUsed = rs.getTimestamp("last_used");
	}
	
	public Address() {}

	public static Address getById(long id){
		Address addr = null;
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
				addr = new Address(rs);
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return addr;
	}
	
	public static Address getById(long userId, long id){
		Address addr = null;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery(null, TABNAME , "user_id = ? and id = ? and is_removed = 0");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, userId);
			stmt.setLong(2, id);
			rs = stmt.executeQuery();
			if(rs.next())
				addr = new Address(rs);
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return addr;
	}
	
	public static Address getDefaultForUser(long userId){
		Address addr = null;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery(null, TABNAME , "user_id = ? and is_default = 1 and is_removed = 0");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, userId);
			rs = stmt.executeQuery();
			if(rs.next())
				addr = new Address(rs);
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return addr;
	}
	
	public static List<Address> getAddressesForUser(long userId){
		List<Address> list = new ArrayList<Address>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery(null, TABNAME , "user_id = ? and is_removed = 0", "order by last_used desc");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, userId);
			rs = stmt.executeQuery();
			while(rs.next())
				list.add(new Address(rs));
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return list;
	}
	
	public static Address insert(Address addr){
		if(addr == null)
			return null;
		List<Address> list = getAddressesForUser(addr.userId);
		Address newAddress = null;
		for(Address item : list){
			if(item.equals(addr)){
				newAddress = item;
				break;
			}
		}
		if(newAddress == null){
			try{
				long id = addr.dbInsert();
				newAddress = getById(id);
			}catch(Exception e){
				ExceptionUtils.logException(e);
			}
		}else{
			try{
				newAddress.dbUpdate();
			}catch(Exception e){
				ExceptionUtils.logException(e);
			}
		}
		return newAddress;
	}
	
	public static boolean markDefaultAs(Address addr, boolean flag){
		try{
			if(addr != null){
				addr.setDefault(flag);
				addr.dbUpdate();
				return true;
			}
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}
		return false;
	}
	
	public static boolean markRemoved(Address addr){
		try{
			if(addr != null){
				addr.setRemoved(true);
				addr.dbUpdate();
				return true;
			}
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}
		return false;
	}
	
	public long dbInsert() throws SQLException{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareInsertQuery(TABNAME, "user_id, first_name, last_name, name1, name2, addr1, addr2, city, state, country, zip_code, email_addr, phone_number, is_default, last_used",
				"?,?,?,?,?,?,?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP");
		try{
			con = DBUtils.getConnection();
			con.setAutoCommit(false);
			stmt = con.prepareStatement(query);
			int ordinal = 1;
			stmt.setLong(ordinal++, this.userId);
			stmt.setString(ordinal++, this.firstName);
			stmt.setString(ordinal++, this.lastName);
			stmt.setString(ordinal++, this.name1);
			stmt.setString(ordinal++, this.name2);
			stmt.setString(ordinal++, this.addr1);
			stmt.setString(ordinal++, this.addr2);
			stmt.setString(ordinal++, this.city);
			stmt.setString(ordinal++, this.state);
			stmt.setString(ordinal++, INDIA);
			stmt.setString(ordinal++, this.zipCode);
			stmt.setString(ordinal++, this.emailAddr);
			stmt.setString(ordinal++, this.phoneNumber);
			stmt.setBoolean(ordinal++, this.isDefault);
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
	
	public int dbUpdate() throws SQLException{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareUpdateQuery(TABNAME, "is_default = ? , is_removed = ? , last_used = CURRENT_TIMESTAMP", "id = ?");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setBoolean(1, this.isDefault);
			stmt.setBoolean(2, this.isRemoved);
			stmt.setLong(3, this.id);
			return stmt.executeUpdate();
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
	}

	private boolean equals(Address addr){
		if(addr == null)
			return false;
		
		if(this.userId == addr.userId &&
				StringUtils.defaultIfEmpty(this.firstName, "").equalsIgnoreCase(StringUtils.defaultIfEmpty(addr.firstName, "")) &&
				StringUtils.defaultIfEmpty(this.lastName, "").equalsIgnoreCase(StringUtils.defaultIfEmpty(addr.lastName, "")) &&
				StringUtils.defaultIfEmpty(this.name1, "").equalsIgnoreCase(StringUtils.defaultIfEmpty(addr.name1, "")) &&
				StringUtils.defaultIfEmpty(this.name2, "").equalsIgnoreCase(StringUtils.defaultIfEmpty(addr.name2, "")) &&
				StringUtils.defaultIfEmpty(this.addr1, "").equalsIgnoreCase(StringUtils.defaultIfEmpty(addr1, "")) &&
				StringUtils.defaultIfEmpty(this.addr2, "").equalsIgnoreCase(StringUtils.defaultIfEmpty(addr.addr2, "")) &&
				StringUtils.defaultIfEmpty(this.city, "").equalsIgnoreCase(StringUtils.defaultIfEmpty(addr.city, "")) &&
				StringUtils.defaultIfEmpty(this.state, "").equalsIgnoreCase(StringUtils.defaultIfEmpty(addr.state, "")) &&
				StringUtils.defaultIfEmpty(this.zipCode, "").equalsIgnoreCase(StringUtils.defaultIfEmpty(addr.zipCode, "")) &&
				StringUtils.defaultIfEmpty(this.emailAddr, "").equalsIgnoreCase(StringUtils.defaultIfEmpty(addr.emailAddr, "")) &&
				StringUtils.defaultIfEmpty(this.phoneNumber, "").equalsIgnoreCase(StringUtils.defaultIfEmpty(addr.phoneNumber, "")))
			return true;
		return false;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getName1() {
		return name1;
	}
	public void setName1(String name1) {
		this.name1 = name1;
	}
	public String getName2() {
		return name2;
	}
	public void setName2(String name2) {
		this.name2 = name2;
	}
	public String getAddr1() {
		return addr1;
	}
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}
	public String getAddr2() {
		return addr2;
	}
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
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
	public String getEmailAddr() {
		return emailAddr;
	}
	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public boolean isRemoved() {
		return isRemoved;
	}
	
	public boolean getIsRemoved() {
		return isRemoved;
	}

	public void setRemoved(boolean isRemoved) {
		this.isRemoved = isRemoved;
	}

	public boolean isDefault() {
		return isDefault;
	}
	
	public boolean getIsDefault() {
		return isDefault;
	}


	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Date getLastUsed() {
		return lastUsed;
	}

	public void setLastUsed(Date lastUsed) {
		this.lastUsed = lastUsed;
	}

	public String getAltPhoneNumber() {
		return altPhoneNumber;
	}

	public void setAltPhoneNumber(String altPhoneNumber) {
		this.altPhoneNumber = altPhoneNumber;
	}
}
