package com.appeti.database.table.product.wrap;

import java.util.Date;

import com.appeti.database.table.product.Tag;
import com.appeti.utils.Constants;

public class TagWrap {
	private long tagId;
	private long ptitleId;
	private long productId;
	private long sellerId;
	private long unitSize;
	private String unitString; // not in DB
	private long pricePerUnit;
	private boolean inStock;
	private String availability;
	private String bucketKey;
	private Date dateAdded;
	
	private double score;
	
	public static TagWrap getWrap(Tag tag, double score){
		if(tag == null)
			return null;
		TagWrap wrap = new TagWrap();
		wrap.setTagId(tag.getTagId());
		wrap.setPtitleId(tag.getPtitleId());
		wrap.setProductId(tag.getProductId());
		wrap.setSellerId(tag.getSellerId());
		wrap.setUnitSize(tag.getUnitSize());
		wrap.setUnitString(tag.getUnitString());
		wrap.setPricePerUnit(tag.getPricePerUnit());
		wrap.setInStock(tag.isInStock());
		wrap.setAvailability(tag.isInStock() ? Constants.ProductAvailability.IN_STOCK : Constants.ProductAvailability.OUT_OF_STOCK);
		wrap.setDateAdded(tag.getDateAdded());
		wrap.setBucketKey(tag.getCompressedUnitString());
		wrap.setScore(score);
		return wrap;
	}
	
	public long getTagId() {
		return tagId;
	}
	public void setTagId(long tagId) {
		this.tagId = tagId;
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
	public long getSellerId() {
		return sellerId;
	}
	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}
	public String getUnitString() {
		return unitString;
	}
	public void setUnitString(String unitString) {
		this.unitString = unitString;
	}
	public String getBucketKey() {
		return bucketKey;
	}

	public void setBucketKey(String bucketKey) {
		this.bucketKey = bucketKey;
	}

	public Date getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}

	public long getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(long pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	public long getUnitSize() {
		return unitSize;
	}

	public void setUnitSize(long unitSize) {
		this.unitSize = unitSize;
	}

	
	public boolean getInStock() {
		return inStock;
	}

	public void setInStock(boolean inStock) {
		this.inStock = inStock;
	}

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}
	
}
