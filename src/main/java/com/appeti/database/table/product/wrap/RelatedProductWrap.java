package com.appeti.database.table.product.wrap;

import com.appeti.database.table.product.Product;
import com.appeti.database.table.product.ProductImage;
import com.appeti.database.utils.Image;

public class RelatedProductWrap {
	private long productId;
	private String productName;
	private String sellerName;
	private String sellerLocation;
	private Image image;
	private double ctr;
	String ratingPercent; // multiple of 5
	double rating; // out of five
	
	public RelatedProductWrap(Product product){
		this.productId = product.getProductId();
		this.productName = product.getName();
		this.image = ProductImage.getDefaultImageByProductId(product.getProductId());
	}
	
	public RelatedProductWrap(Product product, double ctr){
		this.productId = product.getProductId();
		this.productName = product.getName();
		this.image = ProductImage.getDefaultImageByProductId(product.getProductId());
		this.ctr = ctr;
	}
	
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	public String getRatingPercent() {
		return ratingPercent;
	}
	public void setRatingPercent(String ratingPercent) {
		this.ratingPercent = ratingPercent;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}

	public double getCtr() {
		return ctr;
	}

	public void setCtr(double ctr) {
		this.ctr = ctr;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getSellerLocation() {
		return sellerLocation;
	}

	public void setSellerLocation(String sellerLocation) {
		this.sellerLocation = sellerLocation;
	}
}
