package com.appeti.database.table.review;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.appeti.database.table.product.PtitleRating;
import com.appeti.database.table.user.User;

public class ReviewWrap {
	private long id;
	private long productId;
	private long ptitleId;
	private long sellerId;
	private String title;
	private String description;
	private int rating;
	private int thumbsUp;
	private int thumbsDown;
	private String userName;
	private boolean byMe = false; // default false
	private Date dateAdded;
	
	public static ReviewWrap getReviewWrap(PtitleRating rating){
		if(rating == null)
			return null;
		ReviewWrap wrap = new ReviewWrap();
		wrap.setId(rating.getReviewId());
		wrap.setProductId(rating.getProductId());
		wrap.setPtitleId(rating.getPtitleId());
		Review review = Review.getReviewById(rating.getReviewId());
		wrap.setTitle(review.getTitle());
		wrap.setDescription(review.getDescription());
		wrap.setRating(rating.getRating());
		wrap.setThumbsUp(review.getThumbsUp());
		wrap.setThumbsDown(review.getThumbsDown());
		User user = User.getUserById(rating.getUserId());
		String userName = "anonymous";
		if(user != null){
			String name = "";
			if(StringUtils.isNotBlank(user.getUserName())){
				name = user.getUserName();
			}else{
				if(StringUtils.isNotBlank(user.getFirstName()))
					name += user.getFirstName();
				if(StringUtils.isNotBlank(user.getLastName()))
					name += " "+user.getLastName();
			}
			if(StringUtils.isBlank(name))
				name = user.getEmailAddress().split("@")[0];
			if(StringUtils.isNotBlank(name))
				userName = name;
		}
		wrap.setUserName(userName);
		wrap.setDateAdded(rating.getDateAdded());
		return wrap;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public long getPtitleId() {
		return ptitleId;
	}
	public void setPtitleId(long ptitleId) {
		this.ptitleId = ptitleId;
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
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public boolean isByMe() {
		return byMe;
	}
	public void setByMe(boolean byMe) {
		this.byMe = byMe;
	}
	public Date getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}
	public long getSellerId() {
		return sellerId;
	}
	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}
}
