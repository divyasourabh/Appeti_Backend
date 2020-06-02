package com.appeti.database.table.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.lang.StringEscapeUtils;

import com.appeti.database.utils.DBUtils;
import com.appeti.mail.MailOutputStream;
import com.appeti.utils.CryptWithMD5;
import com.appeti.utils.ExceptionUtils;

public class LoginInfo {
	private long id;
	private long userId;
	private String emailAddress;
	private String password;
	private String ques1 = "";
	private String ans1 = "";
	private String ques2 = "";
	private String ans2 = "";
	private boolean active;
	private String hashCode;
	private Date creationDate;
	
	private static final String TABNAME = "login_info";
	
	private LoginInfo(ResultSet rs) throws SQLException{
		this.id = rs.getLong("id");
		this.userId = rs.getLong("user_id");
		this.password = rs.getString("password");
		this.emailAddress = rs.getString("email_address");
		this.ques1 = rs.getString("ques1");
		this.ans1 = rs.getString("ans1");
		this.ques2 = rs.getString("ques2");
		this.ans2 = rs.getString("ans2");
		this.active = rs.getBoolean("active");
		this.hashCode = rs.getString("hash_code");
		this.creationDate = rs.getTimestamp("creation_date");
	}

	private LoginInfo(long userId, String emailAddress, String password){
		this.userId = userId;
		this.emailAddress = emailAddress;
		this.password = password;
		this.hashCode = CryptWithMD5.cryptWithMD5("" + userId + emailAddress);
		this.active = true;
	}
	
	public static LoginInfo createLogin(long userId, String emailAddress, String password){
		LoginInfo login = new LoginInfo(userId, emailAddress, password);
		try{
			long id = login.dbInsert();
			return getById(id);
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}
		return null;
	}
	
	public static LoginInfo getById(long id){
		LoginInfo info = null;
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
				info = new LoginInfo(rs);
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return info;
	}

	public static LoginInfo getByUserId(long userId){
		LoginInfo info = null;
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
				info = new LoginInfo(rs);
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return info;
	}

	public static long validateUser(String email, String password){
		String query = "Select user_id from login_info where email_address = ? and password = ?";
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setString(1, StringEscapeUtils.escapeSql(email));
			stmt.setString(2, password);
			rs = stmt.executeQuery();
			if(rs.next()){
				return rs.getLong("user_id");
			}
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}finally{
			DBUtils.closeResultSet(rs);
			DBUtils.closeStatement(stmt);
			DBUtils.closeConnection(con);
		}
		return -1;
	}
	
	public static boolean validatePassword(long userId, String password){
		String query = "Select * from login_info where user_id = ? and password = ?";
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, userId);
			stmt.setString(2, password);
			rs = stmt.executeQuery();
			if(rs.next()){
				return true;
			}
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}finally{
			DBUtils.closeResultSet(rs);
			DBUtils.closeStatement(stmt);
			DBUtils.closeConnection(con);
		}
		return false;
	}
	
	public static LoginInfo getInfo(String emailAddress){
		String query = "Select * from login_info where email_address = ?";
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			con = DBUtils.getConnection();
			stmt= con.prepareStatement(query);
			stmt.setString(1, StringEscapeUtils.escapeSql(emailAddress));
			rs = stmt.executeQuery();
			if(rs.next()){
				return new LoginInfo(rs);
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
	
	public static LoginInfo getInfo(long userId){
		String query = "Select * from login_info where user_id = ?";
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			con = DBUtils.getConnection();
			stmt= con.prepareStatement(query);
			stmt.setLong(1, userId);
			rs = stmt.executeQuery();
			if(rs.next()){
				return new LoginInfo(rs);
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
		
		String query = DBUtils.prepareInsertQuery(TABNAME, "user_id, email_address,password,hash_code", "?,?,?,?");
		try{
			con = DBUtils.getConnection();
			con.setAutoCommit(false);
			stmt = con.prepareStatement(query);
			stmt.setLong(1, this.userId);
			stmt.setString(2, this.emailAddress);
			stmt.setString(3, this.password);
			stmt.setString(4, this.hashCode);
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
		
		String query = DBUtils.prepareUpdateQuery(TABNAME, "password = ? , active = ?", "id = ?");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			int ordinal = 1;
			stmt.setString(ordinal++, this.password);
			stmt.setBoolean(ordinal++, this.active);
			stmt.setLong(ordinal++, this.id);
			return stmt.executeUpdate();
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
	}	
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getQues1() {
		return ques1;
	}

	public void setQues1(String ques1) {
		this.ques1 = ques1;
	}

	public String getAns1() {
		return ans1;
	}

	public void setAns1(String ans1) {
		this.ans1 = ans1;
	}

	public String getQues2() {
		return ques2;
	}

	public void setQues2(String ques2) {
		this.ques2 = ques2;
	}

	public String getAns2() {
		return ans2;
	}

	public void setAns2(String ans2) {
		this.ans2 = ans2;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
