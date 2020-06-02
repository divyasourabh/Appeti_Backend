package com.appeti.database.table.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.appeti.database.utils.DBUtils;
import com.appeti.utils.ExceptionUtils;

public class PtitleRating{
	
	private long ptitleId;
	private long productId;
	private long tagId;
	private long userId;
	private boolean thumbsUp;
	private boolean thumbsDown;
	private int rating;
	private long reviewId;
	private String comment;
	private boolean verified;
	private Date dateAdded;
	private Date dateModified;
	
	private static final String TABNAME = "ptitle_rating";
	private static final int COUNT_THRESHOLD = 5;
	
	private PtitleRating(long productId, long ptitleId, long tagId, long userId, int rating, long reviewId){
		this.ptitleId = ptitleId;
		this.productId = productId;
		this.tagId = tagId;
		this.userId = userId;
		this.rating = rating;
		this.reviewId = reviewId;
	}
	private PtitleRating(ResultSet rs) throws SQLException{
		this.ptitleId = rs.getLong("ptitle_id");
		this.productId = rs.getLong("product_id");
		this.tagId = rs.getLong("tag_id");
		this.userId = rs.getLong("user_id");
		this.thumbsUp = rs.getBoolean("thumbs_up");
		this.thumbsDown = rs.getBoolean("thumbs_down");
		this.rating = rs.getInt("rating");
		this.comment = rs.getString("comment");
		this.reviewId = rs.getLong("review_id");
		this.verified = rs.getBoolean("verified");
		this.dateAdded = rs.getDate("date_added");
		this.dateModified = rs.getDate("date_modified");
	
	}
	
	public static double getPtitleRating(long ptitleId){
		List<PtitleRating> ratings = getPtitleRatings(ptitleId);
		int count = 0;
		int thumbs = 0;
		int totalRating = 0;
		for(PtitleRating rating : ratings){
			count ++;
			if(rating.getRating() != -1)
				totalRating = totalRating + rating.getRating();
			if(rating.isThumbsUp())
				thumbs ++;
			if(rating.isThumbsDown())
				thumbs --;
		}
		return getScore(count,thumbs,totalRating);
	}

	public static double getProductRating(long productId){
		List<PtitleRating> ratings = getProductRatingsExcept(productId, 0);
		int count = 0;
		int thumbs = 0;
		int totalRating = 0;
		for(PtitleRating rating : ratings){
			count ++;
			if(rating.getRating() != -1)
				totalRating = totalRating + rating.getRating();
			if(rating.isThumbsUp())
				thumbs ++;
			if(rating.isThumbsDown())
				thumbs --;
		}
		return getScore(count,thumbs,totalRating);
	}

	public static List<PtitleRating> getAllRatings(long productId, long ptitleId){
		List<PtitleRating> list = new ArrayList<PtitleRating>();
		list.addAll(getPtitleRatings(ptitleId));
		list.addAll(getProductRatingsExcept(productId, ptitleId));
		return list;
	}
	
	private static List<PtitleRating> getPtitleRatings(long ptitleId){
		List<PtitleRating> list = new ArrayList<PtitleRating>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery(null, TABNAME , "ptitle_id = ? and verified = 1");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, ptitleId);
			rs = stmt.executeQuery();
			while(rs.next())
				list.add(new PtitleRating(rs));
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return list;
	}

	private static List<PtitleRating> getProductRatingsExcept(long productId, long ptitleId){
		List<PtitleRating> list = new ArrayList<PtitleRating>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery(null, TABNAME , "product_id = ? and ptitle_id != ? and verified = 1");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, productId);
			stmt.setLong(2, ptitleId);
			rs = stmt.executeQuery();
			while(rs.next())
				list.add(new PtitleRating(rs));
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return list;
	}

	public static long insertRating(long productId, long ptitleId, long tagId, long userId, int rating, long reviewId){
		try{
			PtitleRating ratingObj = new PtitleRating(productId, ptitleId, tagId, userId, rating, reviewId);
			return ratingObj.dbInsert();
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}
		return -1;
	}
	
	private long dbInsert() throws SQLException{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareInsertQuery(TABNAME, "ptitle_id, product_id, tag_id, user_id, rating, review_id, date_modified", "?,?,?,?,?,?,CURRENT_TIMESTAMP");
		try{
			con = DBUtils.getConnection();
			con.setAutoCommit(false);
			stmt = con.prepareStatement(query);
			int ordinal = 1;
			stmt.setLong(ordinal++, this.ptitleId);
			stmt.setLong(ordinal++, this.productId);
			stmt.setLong(ordinal++, this.tagId);
			stmt.setLong(ordinal++, this.userId);
			stmt.setInt(ordinal++, this.rating);
			stmt.setLong(ordinal++, this.reviewId);
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
	
	private static double getScore(int count, int thumbs, int rating){
		double score = 0;
		if(count >= COUNT_THRESHOLD){
			score = thumbs/count + rating/(5*count) + Math.log10(count)/5;
		}
		return score;
	}
	public long getPtitleId() {
		return ptitleId;
	}
	public void setPtitleId(long ptitleId) {
		this.ptitleId = ptitleId;
	}
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public long getTagId() {
		return tagId;
	}
	public void setTagId(long tagId) {
		this.tagId = tagId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public boolean isThumbsUp() {
		return thumbsUp;
	}
	public void setThumbsUp(boolean thumbsUp) {
		this.thumbsUp = thumbsUp;
	}
	public boolean isThumbsDown() {
		return thumbsDown;
	}
	public void setThumbsDown(boolean thumbsDown) {
		this.thumbsDown = thumbsDown;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public long getReviewId() {
		return reviewId;
	}
	public void setReviewId(long reviewId) {
		this.reviewId = reviewId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public boolean isVerified() {
		return verified;
	}
	public void setVerified(boolean verified) {
		this.verified = verified;
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
