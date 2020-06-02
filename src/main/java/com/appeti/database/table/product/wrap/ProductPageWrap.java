package com.appeti.database.table.product.wrap;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.appeti.database.table.user.wrap.SellerWrap;
import com.appeti.database.table.utils.Attribute;
import com.appeti.database.utils.Image;

// new object prepared for every ptitle change

public class ProductPageWrap {
	private long productId;
	private long ptitleId;
	private long tagId;
	private String productName;
	private String ptitleName;
	private String ptitleDesc;
	private String productDescription;
	private String unitString;
	private long ppu;
	private boolean inStock;
	private String availability;
	private Map<Long,String> nodeMap;
	private Map<Long,String> categoryMap;
	private List<Image> images; // list of images for this ptitle and product image
	private List<TagWrap> bestTags; // list of best tag one for each bucket including the selected tag as preselected based on tagid above
	private JSONObject otherSellers; // unit to tags map // tags are all tags for this ptitle and unit including shown above // mark it as selected
	private List<PtitleWrap> allPtitles; // should ideally contain ptitle id and name to display in dropdown
	private JSONObject sellerDescriptionMap;
	private SellerWrap sellerInfo;
	private List<Attribute> attrs;
	private List<RelatedProductWrap> relatedProducts;
	private double rating;
	private int numReviews;
	
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
	public String getPtitleDesc() {
		return ptitleDesc;
	}
	public void setPtitleDesc(String ptitleDesc) {
		this.ptitleDesc = ptitleDesc;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	public String getUnitString() {
		return unitString;
	}
	public void setUnitString(String unitString) {
		this.unitString = unitString;
	}
	public Map<Long, String> getNodeMap() {
		return nodeMap;
	}
	public void setNodeMap(Map<Long, String> nodeMap) {
		this.nodeMap = nodeMap;
	}
	public Map<Long, String> getCategoryMap() {
		return categoryMap;
	}
	public void setCategoryMap(Map<Long, String> categoryMap) {
		this.categoryMap = categoryMap;
	}
	public List<Image> getImages() {
		return images;
	}
	public void setImages(List<Image> images) {
		this.images = images;
	}
	public List<TagWrap> getBestTags() {
		return bestTags;
	}
	public void setBestTags(List<TagWrap> bestTags) {
		this.bestTags = bestTags;
	}
	public JSONObject getOtherSellers() {
		return otherSellers;
	}
	public void setOtherSellers(JSONObject otherSellers) {
		this.otherSellers = otherSellers;
	}
	public List<PtitleWrap> getAllPtitles() {
		return allPtitles;
	}
	public void setAllPtitles(List<PtitleWrap> allPtitles) {
		this.allPtitles = allPtitles;
	}
	public JSONObject getSellerDescriptionMap() {
		return sellerDescriptionMap;
	}
	public void setSellerDescriptionMap(JSONObject sellerDescriptionMap) {
		this.sellerDescriptionMap = sellerDescriptionMap;
	}
	public long getPpu() {
		return ppu;
	}
	public void setPpu(long ppu) {
		this.ppu = ppu;
	}
	public List<Attribute> getAttrs() {
		return attrs;
	}
	public void setAttrs(List<Attribute> attrs) {
		this.attrs = attrs;
	}
	public List<RelatedProductWrap> getRelatedProducts() {
		return relatedProducts;
	}
	public void setRelatedProducts(List<RelatedProductWrap> relatedProducts) {
		this.relatedProducts = relatedProducts;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	public int getNumReviews() {
		return numReviews;
	}
	public void setNumReviews(int numReviews) {
		this.numReviews = numReviews;
	}
	public String getAvailability() {
		return availability;
	}
	public void setAvailability(String availability) {
		this.availability = availability;
	}
	public boolean getInStock() {
		return inStock;
	}
	public void setInStock(boolean inStock) {
		this.inStock = inStock;
	}
	public SellerWrap getSellerInfo() {
		return sellerInfo;
	}
	public void setSellerInfo(SellerWrap sellerInfo) {
		this.sellerInfo = sellerInfo;
	}
}
