package com.appeti.database.table.order.wrap;

import java.util.ArrayList;
import java.util.List;

import com.appeti.database.table.order.OrderItem;
import com.appeti.database.table.product.Product;
import com.appeti.database.table.product.ProductImage;
import com.appeti.database.table.product.Ptitle;
import com.appeti.database.table.product.PtitleImage;
import com.appeti.database.table.product.Tag;
import com.appeti.database.utils.Image;

public class OrderItemWrap {
	private long orderItemId;
	private long orderId;
	private long productId;
	private long ptitleId;
	private long tagId;
	private long sellerId;
	private String ptitleName;
	private String productName;
	private String unit;
	private long quantity;
	private long perUnitPrice;
	private long totalPrice;
	private Image image;
	
	public static OrderItemWrap getWrap(OrderItem item){
		if(item == null)
			return null;
		OrderItemWrap wrap = new OrderItemWrap();
		wrap.setOrderItemId(item.getOrderItemId());
		wrap.setOrderId(item.getOrderId());
		Ptitle ptitle = Ptitle.getPtitleById(item.getPtitleId());
		if(ptitle != null)
			wrap.setPtitleName(ptitle.getName());
		Tag tag = Tag.getTagById(item.getTagId());
		if(tag != null){
			wrap.setProductId(tag.getProductId());
			wrap.setPtitleId(tag.getPtitleId());
			wrap.setTagId(tag.getTagId());
			wrap.setSellerId(tag.getSellerId());
			Product prod= Product.getProductById(tag.getProductId());
			if(prod != null)
				wrap.setProductName(prod.getName());
			wrap.setUnit(tag.getUnitString());
		}
		wrap.setQuantity(item.getQuantity());
		wrap.setPerUnitPrice(item.getPerUnitPrice());
		wrap.setTotalPrice(item.getTotalPrice());
		Image image = PtitleImage.getDefaultImageByPtitleId(item.getPtitleId());
		if(image == null){
			image = ProductImage.getDefaultImageByProductId(tag.getProductId());
		}
		wrap.setImage(image);
		return wrap;
	}
	
	public static List<OrderItemWrap> getWrapList(List<OrderItem> items){
		List<OrderItemWrap> list = new ArrayList<OrderItemWrap>();
		if(items == null || items.size() <= 0)
			return list;
		for(OrderItem item : items){
			OrderItemWrap wrap = getWrap(item);
			if(wrap != null)
				list.add(wrap);
		}
		return list;
	}
	public long getOrderItemId() {
		return orderItemId;
	}
	public void setOrderItemId(long orderItemId) {
		this.orderItemId = orderItemId;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public String getPtitleName() {
		return ptitleName;
	}
	public void setPtitleName(String ptitleName) {
		this.ptitleName = ptitleName;
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
	public long getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(long totalPrice) {
		this.totalPrice = totalPrice;
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

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
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
	
}
