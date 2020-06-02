package com.appeti.database.table.user;

import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.lang.StringEscapeUtils;

import com.appeti.database.utils.DBUtils;

public class UserLog {
	private long id;
	private long userId;
	private int action;
	private String value;
	private String comment = "";
	private Date date;
	
	public UserLog(long userId,int action,String value,String comment){
		this.userId = userId;
		this.action = action;
		this.value = value;
		this.comment = comment;
	}
	
	public UserLog(long userId,int action,String value){
		this.userId = userId;
		this.action = action;
		this.value = value;
	}
	
	public void log() throws SQLException{
		String query = "INSERT INTO user_log (user_id,action,value,comment) VALUES (" + 
				this.userId + "," + this.action + ",'" + this.value + "','" + StringEscapeUtils.escapeSql(this.comment) + "')";
		DBUtils.insertQuery(query);
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public int getAction() {
		return action;
	}
	public void setAction(int action) {
		this.action = action;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
