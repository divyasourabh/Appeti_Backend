package com.appeti.utils;

public class SearchKeyword {
	private long productId;
	private long ptitleId;
	private long tagId;
	
	private String productName;
	private String ptitleName;
	private String unit;
	private String category;
	private String location;
	
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
	public long getTagId() {
		return tagId;
	}
	public void setTagId(long tagId) {
		this.tagId = tagId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getPtitleName() {
		return ptitleName;
	}
	public void setPtitleName(String ptitleName) {
		this.ptitleName = ptitleName;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String toString(){
		return "product:" + productName + " ptitle:" + ptitleName + "unit:" + unit + "location:" + location + " category:" + category;
	}
}
