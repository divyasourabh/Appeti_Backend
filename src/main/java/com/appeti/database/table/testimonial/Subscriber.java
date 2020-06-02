package com.appeti.database.table.testimonial;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.appeti.database.utils.DBUtils;
import com.appeti.utils.ExceptionUtils;

public class Subscriber {
	private String email;
	private String subscriptionType;
	private String subscribedTo;
	private Date when;
	
	private static final String TABNAME = "subscriber";
	
	private Subscriber(ResultSet rs) throws SQLException{
		this.email = rs.getString("email");
		this.subscriptionType = rs.getString("subscription_type");
		this.subscribedTo = rs.getString("subscribed_to");
	}

	private Subscriber(String email){
		this.email = email;
	}
	
	public static boolean addSubsciber(String email){
		Subscriber sub = new Subscriber(email);
		try{
			sub.dbInsert();
			return true;
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}
		return false;
	}
	
	private long dbInsert() throws SQLException{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareInsertQuery(TABNAME, "email, subscription_type, subscribed_to", "?,?,?");
		try{
			con = DBUtils.getConnection();
			con.setAutoCommit(false);
			stmt = con.prepareStatement(query);
			stmt.setString(1, this.email);
			stmt.setString(2, this.subscriptionType);
			stmt.setString(3, this.subscribedTo);
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
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSubscriptionType() {
		return subscriptionType;
	}

	public void setSubscriptionType(String subscriptionType) {
		this.subscriptionType = subscriptionType;
	}

	public String getSubscribedTo() {
		return subscribedTo;
	}

	public void setSubscribedTo(String subscribedTo) {
		this.subscribedTo = subscribedTo;
	}

	public Date getWhen() {
		return when;
	}

	public void setWhen(Date when) {
		this.when = when;
	}
	
	
}
