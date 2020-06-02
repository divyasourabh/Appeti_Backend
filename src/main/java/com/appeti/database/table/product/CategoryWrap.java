package com.appeti.database.table.product;

import com.appeti.database.utils.Image;

public class CategoryWrap {
	private long categoryId;
	private String categoryName;
	private Image image;
	
	public static CategoryWrap getWrap(Category cat){
		if(cat == null)
			return null;
		CategoryWrap wrap = new CategoryWrap();
		wrap.setCategoryId(cat.getCategoryId());
		wrap.setCategoryName(cat.getName());
		wrap.setImage(CategoryImage.getDefaultImageByCategoryId(cat.getCategoryId()));
		return wrap;
	}
	
	public long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	
}
