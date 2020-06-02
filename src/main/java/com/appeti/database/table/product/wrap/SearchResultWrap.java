package com.appeti.database.table.product.wrap;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.appeti.database.table.node.Node;
import com.appeti.database.table.node.NodeImage;
import com.appeti.database.table.product.Product;
import com.appeti.database.table.product.ProductImage;
import com.appeti.database.table.product.Ptitle;
import com.appeti.database.table.product.PtitleImage;
import com.appeti.database.table.user.Seller;
import com.appeti.database.table.utils.Location;
import com.appeti.database.utils.Image;
import com.appeti.main.services.Service;
import com.appeti.utils.Constants;

public class SearchResultWrap {
	private long productId;
	private String productName;
	private long ptitleId;
	private String ptitleName;
	private long nodeId;
	private String nodeName;
	private List<Image> images;
	private Image image;
	private float rating; // not set yet
	private int numReviews; // not set yet
	private long tagId;
	private String description;
	private Map<Long,String> nodeMap;
	private Map<Long,String> categoryMap;
	private long sellerId;
	private String sellerName;
	private String sellerLocation;
	private String unit;
	private long pricePerUnit;
	private String availability;
	private boolean inStock;
	private boolean isOffer;
	private boolean isNew;
	private double score;
	private Date dateAdded;
	
	public static SearchResultWrap getSearchResult(Product product, Ptitle ptitle, TagWrap tag){
		return getSearchResult(product, ptitle, tag, 0);
	}
	
	public static SearchResultWrap getSearchResult(Product product, Ptitle ptitle, TagWrap tag, double score){
		SearchResultWrap result = new SearchResultWrap();
		result.setProductId(product.getProductId());
		result.setProductName(product.getName());
		result.setPtitleId(ptitle.getPtitleId());
		result.setPtitleName(ptitle.getName());
		result.setNodeId(product.getNodeId());
		Node node = Node.getNodeById(product.getNodeId());
		if(node != null)
			result.setNodeName(node.getNodeName());
		List<Image> images = PtitleImage.getAllImagesByPtitleId(ptitle.getPtitleId());
		if(images == null)
			images = ProductImage.getAllImagesByProductId(product.getProductId());
		if(images == null && node != null)
			images = NodeImage.getAllImagesByNodeId(node.getNodeId());
		result.setImages(images);
		if(images.size() >0){
			result.setImage(images.get(0));
		}
		result.setTagId(tag.getTagId());
		result.setDescription(ptitle.getDescription());
		result.setNodeMap(Service.createNodeMap(product.getNodeId()));
		result.setCategoryMap(Service.createCategoryMap(product.getCategoryId()));
		result.setSellerId(tag.getSellerId());
		Seller seller = Seller.getById(tag.getSellerId());
		if(seller != null){
			result.setSellerName(seller.getBrandName());
			result.setSellerLocation(Location.getCityById(seller.getLocationId()));
		}
		result.setUnit(tag.getUnitString()); //
		result.setPricePerUnit(tag.getPricePerUnit());
		result.setInStock(tag.getInStock());
		result.setAvailability(tag.getInStock() ? Constants.ProductAvailability.IN_STOCK : Constants.ProductAvailability.OUT_OF_STOCK);
		result.setScore(score);
		result.setDateAdded(ptitle.getDateAdded());
		return result;
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
	public long getPtitleId() {
		return ptitleId;
	}
	public void setPtitleId(long ptitleId) {
		this.ptitleId = ptitleId;
	}
	public String getPtitleName() {
		return ptitleName;
	}
	public void setPtitleName(String ptitleName) {
		this.ptitleName = ptitleName;
	}
	public long getNodeId() {
		return nodeId;
	}
	public void setNodeId(long nodeId) {
		this.nodeId = nodeId;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public List<Image> getImages() {
		return images;
	}
	public void setImages(List<Image> image) {
		this.images = image;
	}
	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}
	public int getNumReviews() {
		return numReviews;
	}
	public void setNumReviews(int numReviews) {
		this.numReviews = numReviews;
	}
	public long getTagId() {
		return tagId;
	}
	public void setTagId(long tagId) {
		this.tagId = tagId;
	}
	public long getSellerId() {
		return sellerId;
	}
	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public long getPricePerUnit() {
		return pricePerUnit;
	}
	public void setPricePerUnit(long pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public Date getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
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
	public boolean getIsOffer() {
		return isOffer;
	}
	public void setOffer(boolean isOffer) {
		this.isOffer = isOffer;
	}
	public boolean getIsNew() {
		return isNew;
	}
	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
}
