package com.appeti.database.table.testimonial;

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

public class Testimonial {
	private long id;
	private long userId;
	private String userName;
	private String title;
	private String description;
	private String imageUrl;
	private int seqNo;
	private Date dateAdded;
	private Date dateModified;
	
	private static final String TABNAME = "testimonial";
	
	public Testimonial(long userId, String userName, String title, String desc){
		this.userId = userId;
		this.setUserName(userName);
		this.title = title;
		this.description = desc;
	}
	private Testimonial(ResultSet rs) throws SQLException{
		this.id = rs.getLong("id");
		this.userId = rs.getLong("user_id");
		this.title = rs.getString("title");
		this.userName = rs.getString("user_name");
		this.description = rs.getString("description");
		this.imageUrl = rs.getString("image_url");
		this.setSeqNo(rs.getInt("seq_no"));
		this.dateAdded = rs.getTimestamp("date_added");
		this.dateModified = rs.getTimestamp("date_modified");
	}
	
	public static List<Testimonial> getTestimonials() {
		return getTestimonials(-1);
	}
	
	public static List<Testimonial> getTestimonials(int limit) {
		List<Testimonial> list = new ArrayList<Testimonial>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String limitStr = "";
		if(limit > 0){
			limitStr = " limit " + limit;
		}
		String query = DBUtils.prepareSelectQuery(null, TABNAME, "verified = 1" , "order by seq_no asc" + limitStr);
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();
			while(rs.next())
				list.add(new Testimonial(rs));
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return list;
	}
	
	public static Testimonial insertTestimonial(long userId, String userName, String title, String desc){
		Testimonial review = null;
		if(StringUtils.isNotBlank(desc)){
			try{
				review = new Testimonial(userId, userName, title, desc);
				review.dbInsert();
			}catch(Exception e){
				ExceptionUtils.logException(e);
				return null;
			}
		}
		return review;
	}
	
	private long dbInsert() throws SQLException{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareInsertQuery(TABNAME, "user_id, user_name, title ,description, date_modified", "?,?,?,?,CURRENT_TIMESTAMP");
		try{
			con = DBUtils.getConnection();
			con.setAutoCommit(false);
			stmt = con.prepareStatement(query);
			stmt.setLong(1, this.userId);
			stmt.setString(2, this.userName);
			stmt.setString(3, this.title);
			stmt.setString(4, this.description);
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}
	public Date getDateModified() {
		return dateModified;
	}
	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}
	public int getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
}
