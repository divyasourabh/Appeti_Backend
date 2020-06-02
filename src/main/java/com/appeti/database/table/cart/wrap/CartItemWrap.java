package com.appeti.database.table.cart.wrap;

import com.appeti.database.table.cart.CartItem;
import com.appeti.database.table.product.Product;
import com.appeti.database.table.product.ProductImage;
import com.appeti.database.table.product.Ptitle;
import com.appeti.database.table.product.PtitleImage;
import com.appeti.database.table.product.Tag;
import com.appeti.database.utils.Image;


public class CartItemWrap {
	private long cartItemId;
	private long cartId;
	private long tagId;
	private long ptitleId;
	private long productId;
	private Image image;
	private String productName;
	private String ptitleName;
	private String unit;
	private long quantity;
	private long perUnitPrice;
	private long totalAmount;
	
	public static CartItemWrap getWrap(CartItem item){
		if(item == null)
			return null;
		CartItemWrap wrap = new CartItemWrap();
		wrap.setCartItemId(item.getCartItemId());
		wrap.setCartId(item.getCartId());
		wrap.setTagId(item.getTagId());
		wrap.setPtitleId(item.getPtitleId());
		Tag tag = Tag.getTagById(item.getTagId());
		if(tag != null){
			wrap.setProductId(tag.getProductId());
			Image image = PtitleImage.getDefaultImageByPtitleId(item.getPtitleId());
			if(image == null)
				image = ProductImage.getDefaultImageByProductId(tag.getProductId());
			wrap.setImage(image);
			Product prod = Product.getProductById(tag.getProductId());
			wrap.setProductName(prod != null ? prod.getName() : "");
			Ptitle ptitle = Ptitle.getPtitleById(item.getPtitleId());
			wrap.setPtitleName(ptitle != null ? ptitle.getName() : "");
			wrap.setUnit(tag.getUnitString());
		}
		wrap.setQuantity(item.getQuantity());
		wrap.setPerUnitPrice(item.getPerUnitPrice());
		wrap.setTotalAmount(wrap.getQuantity()*wrap.getPerUnitPrice());
		return wrap;
	}
	public long getCartItemId() {
		return cartItemId;
	}
	public void setCartItemId(long cartItemId) {
		this.cartItemId = cartItemId;
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
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
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
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public long getQuantity() {
		return quantity;
	}
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
	public long getPerUnitPrice() {
		return perUnitPrice;
	}
	public void setPerUnitPrice(long perUnitPrice) {
		this.perUnitPrice = perUnitPrice;
	}
	public long getCartId() {
		return cartId;
	}
	public void setCartId(long cartId) {
		this.cartId = cartId;
	}
	public long getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(long totalAmount) {
		this.totalAmount = totalAmount;
	}
}
