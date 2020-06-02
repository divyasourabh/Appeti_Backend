package com.appeti.database.table.review;

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

public class Review {
	private long id;
	private long userId;
	private String reviewType;
	private String title;
	private String description;
	private int thumbsUp;
	private int thumbsDown;
	private Date dateAdded;
	private Date dateModified;
	
	private static final String TABNAME = "review";
	
	public Review(long userId, String title, String desc, String type){
		this.userId = userId;
		this.title = title;
		this.description = desc;
		this.reviewType = type;
	}
	private Review(ResultSet rs) throws SQLException{
		this.id = rs.getLong("id");
		this.userId = rs.getLong("user_id");
		this.reviewType = rs.getString("review_type");
		this.title = rs.getString("title");
		this.description = rs.getString("description");
		this.thumbsUp = rs.getInt("thumbs_up");
		this.thumbsDown = rs.getInt("thumbs_down");
		this.dateAdded = rs.getTimestamp("date_added");
		this.dateModified = rs.getTimestamp("date_modified");
	}
	
	public static Review getReviewById(long id) {
		Review review = null;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery(null, TABNAME, "id = ?");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, id);
			rs = stmt.executeQuery();
			if(rs.next())
				review = new Review(rs);
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return review;
	}
	
	public static List<Review> getReviewByType(String type) {
		List<Review> list = new ArrayList<Review>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery(null, TABNAME, "review_type = ?");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setString(1, type);
			rs = stmt.executeQuery();
			while(rs.next())
				list.add(new Review(rs));
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return list;
	}
	
	public static Review insertReview(long userId, String title, String desc, String type){
		Review review = null;
		if(StringUtils.isNotBlank(title) && StringUtils.isNotBlank(desc)){
			try{
				review = new Review(userId, title, desc, type);
				long id = review.dbInsert();
				return getReviewById(id);
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
		
		String query = DBUtils.prepareInsertQuery(TABNAME, "user_id, title ,description, review_type, date_modified", "?,?,?,?,CURRENT_TIMESTAMP");
		try{
			con = DBUtils.getConnection();
			con.setAutoCommit(false);
			stmt = con.prepareStatement(query);
			stmt.setLong(1, this.userId);
			stmt.setString(2, this.title);
			stmt.setString(3, this.description);
			stmt.setString(4, this.reviewType);
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
	public String getReviewType() {
		return reviewType;
	}
	public void setReviewType(String reviewType) {
		this.reviewType = reviewType;
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
	public int getThumbsUp() {
		return thumbsUp;
	}
	public void setThumbsUp(int thumbsUp) {
		this.thumbsUp = thumbsUp;
	}
	public int getThumbsDown() {
		return thumbsDown;
	}
	public void setThumbsDown(int thumbsDown) {
		this.thumbsDown = thumbsDown;
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
}
