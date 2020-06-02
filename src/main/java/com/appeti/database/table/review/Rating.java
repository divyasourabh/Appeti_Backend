package com.appeti.database.table.review;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Rating {
	protected long id;
	protected long productId;
	protected long userId;
	protected int ratingType;
	protected boolean thumbsUp;
	protected boolean thumbsDown;
	protected int rating;
	protected long reviewId;
	protected String comment;
	protected boolean verified;
	protected Date dateAdded;
	protected Date dateModified;
	
	protected Rating(){}
	protected Rating(ResultSet rs) throws SQLException{
		this.userId = rs.getLong("user_id");
		this.ratingType = rs.getInt("rating_type");
		this.thumbsUp = rs.getBoolean("thumbs_up");
		this.thumbsDown = rs.getBoolean("thumbs_down");
		this.rating = rs.getInt("rating");
		this.comment = rs.getString("comment");
		this.reviewId = rs.getLong("review_id");
		this.verified = rs.getBoolean("verified");
		this.dateAdded = rs.getDate("date_added");
		this.dateModified = rs.getDate("date_modified");
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getRatingType() {
		return ratingType;
	}

	public void setRatingType(int ratingType) {
		this.ratingType = ratingType;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}
}
