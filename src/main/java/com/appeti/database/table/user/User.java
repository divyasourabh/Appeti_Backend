package com.appeti.database.table.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.appeti.database.utils.DBUtils;
import com.appeti.mail.MailOutputStream;
import com.appeti.utils.ExceptionUtils;

public class User {
	private long userId;
	private String userName;
	private String firstName;
	private String lastName;
	private String emailAddress;
	private long roleId;
	private String phoneNumber;
	private String hashCode;
	private boolean active;
	private Date lastViewed;
	private long visitCount;
	private static Logger LOG = Logger.getLogger(User.class);
	
	private static final String TABNAME = "user_";
	
	private User(ResultSet rs) throws SQLException{
		this.userId = rs.getLong("user_id");
		this.userName = rs.getString("user_name");
		this.firstName = rs.getString("first_name");
		this.lastName = rs.getString("last_name");
		this.emailAddress = rs.getString("email_address");
		this.roleId = rs.getLong("role_id");
		this.phoneNumber = rs.getString("phone_number");
		this.hashCode = rs.getString("hash_code");
		this.active = rs.getBoolean("active");
		this.lastViewed = rs.getTimestamp("last_viewed");
		this.visitCount = rs.getLong("visit_count");
	}
	
	public User(String emailAddress,String firstName,String lastName,String phoneNumber){
		this.emailAddress = emailAddress;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
	}

	public User(String emailAddress,String firstName,String lastName){
		this.emailAddress = emailAddress;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public User(String emailAddress){
		this.emailAddress = emailAddress;
	}

	public static User createUser(String firstName,String lastName, String emailAddress, String token){
		try{
			User user = new User(emailAddress,firstName,lastName);
			user.setHashCode(token);
			user.setRoleId(2);
			long userId = user.dbInsert();
			return getUserById(userId);
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}
		return null;
	}
	
	public static User createUser(String email){
		try{
			User user = new User(email);
			user.setRoleId(2);
			long userId = user.dbInsert();
			return getUserById(userId);
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}
		return null;
	}

	public static User getUserById(long userId){
		User user = null;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery(null, TABNAME , "user_id = ?");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, userId);
			rs = stmt.executeQuery();
			if(rs.next())
				user = new User(rs);
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return user;
	}
	
	public static User getUserByEmail(String emailAddress){
		User user = null;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery(null, TABNAME , "email_address = ?");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setString(1, emailAddress);
			rs = stmt.executeQuery();
			if(rs.next())
				user = new User(rs);
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return user;
	}
	
	public static Date getLastVisited(long userId){
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query = "SELECT last_viewed FROM user_ WHERE user_id = ?";
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, userId);
			rs = stmt.executeQuery();
			if(rs.next()){
				return rs.getTimestamp(1);
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
	
	public long dbInsert() throws SQLException{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareInsertQuery(TABNAME, "first_name, last_name, email_address, role_id, phone_number, hash_code, active", "?,?,?,?,?,?,?");
		try{
			con = DBUtils.getConnection();
			con.setAutoCommit(false);
			stmt = con.prepareStatement(query);
			stmt.setString(1, this.firstName);
			stmt.setString(2,this.lastName);
			stmt.setString(3,this.emailAddress);
			stmt.setLong(4,this.roleId);
			stmt.setString(5,this.phoneNumber);
			stmt.setString(6,this.hashCode);
			stmt.setBoolean(7,true);
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
	
	public void updateDB(){
		Connection con = null;
		PreparedStatement stmt = null;
		String query = "UPDATE user_ SET email_address = ? , user_name = ? , first_name = ? , last_name = ? , phone_number = ? ,hash_code = ? ,active = ? WHERE user_id = ?";
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			int ordinal = 1;
			stmt.setString(ordinal++, StringEscapeUtils.escapeSql(emailAddress));
			stmt.setString(ordinal++, StringEscapeUtils.escapeSql(userName));
			stmt.setString(ordinal++, StringEscapeUtils.escapeSql(firstName));
			stmt.setString(ordinal++, StringEscapeUtils.escapeSql(lastName));
			stmt.setString(ordinal++, StringEscapeUtils.escapeSql(phoneNumber));
			stmt.setString(ordinal++, hashCode);
			stmt.setBoolean(ordinal++, active);
			stmt.setLong(ordinal++, userId);
			stmt.executeUpdate();
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}finally{
			DBUtils.closeStatement(stmt);
			DBUtils.closeConnection(con);
		}
	}
	
	public static void updateVisitInfo(long userId){
		User user = User.getUserById(userId);
		long visitCount = user.getVisitCount()+1;
		Connection con = null;
		PreparedStatement stmt = null;
		String query = "UPDATE user_ SET visit_count = ? , last_viewed = CURTIME() WHERE user_id = ?";
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			int ordinal = 1;
			stmt.setLong(ordinal++, visitCount);
			stmt.setLong(ordinal, userId);
			stmt.executeUpdate();
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}finally{
			DBUtils.closeStatement(stmt);
			DBUtils.closeConnection(con);
		}
	}
	
	public long getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Date getLastViewed() {
		return lastViewed;
	}

	public void setLastViewed(Date lastViewed) {
		this.lastViewed = lastViewed;
	}

	public long getVisitCount() {
		return visitCount;
	}

	public void setVisitCount(long visitCount) {
		this.visitCount = visitCount;
	}

	public String getHashCode() {
		return hashCode;
	}

	public void setHashCode(String hashCode) {
		this.hashCode = hashCode;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
}
