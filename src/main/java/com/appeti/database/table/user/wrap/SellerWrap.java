package com.appeti.database.table.user.wrap;

import com.appeti.database.table.user.Seller;
import com.appeti.database.table.utils.Location;
import com.appeti.database.utils.Image;

public class SellerWrap {
	private long sellerId;
	private String brandName;
	private String location;
	private String website;
	private Image image;
	private String description;
	
	private SellerWrap(Seller seller){
		this.sellerId = seller.getSellerId();
		this.brandName = seller.getBrandName();
		Location loc = Location.getById(seller.getLocationId());
		if(loc != null)
			this.location = loc.getCity();
		//this.website = seller.getWebsite();
		this.image = new Image(seller.getImageUrl(),"");
		this.description = seller.getDescription();
	}
	
	public static SellerWrap getSeller(Seller seller){
		if(seller == null)
			return null;
		else
			return new SellerWrap(seller);
	}
	public long getSellerId() {
		return sellerId;
	}
	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
}
