package com.appeti.database.table.product;

public class ProductMinWrap {
	private long productId;
	private String productName;
	
	public static ProductMinWrap getWrap(Product product){
		if(product == null)
			return null;
		ProductMinWrap wrap = new ProductMinWrap();
		wrap.setProductId(product.getProductId());
		wrap.setProductName(product.getName());
		return wrap;
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
}
