package com.appeti.main.beans;

import java.util.List;

import com.appeti.database.table.product.wrap.ProductPageWrap;
import com.appeti.database.table.review.ReviewWrap;

public class ProductBean {
	private ProductPageWrap product;
	private List<ReviewWrap> reviews;

	public ProductPageWrap getProduct() {
		return product;
	}

	public void setProduct(ProductPageWrap product) {
		this.product = product;
	}

	public List<ReviewWrap> getReviews() {
		return reviews;
	}

	public void setReviews(List<ReviewWrap> reviews) {
		this.reviews = reviews;
	}
}
