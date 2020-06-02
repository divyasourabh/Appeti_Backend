package com.appeti.main.beans;

import java.util.List;

import com.appeti.database.table.cart.wrap.CouponWrap;
import com.appeti.database.table.node.wrap.NodeParentChildWrap;
import com.appeti.database.table.product.CategoryWrap;
import com.appeti.database.table.product.wrap.ProductWrap;
import com.appeti.database.table.testimonial.Testimonial;
import com.appeti.utils.BooleanWithReason;

public class HomeBean {
	List<NodeParentChildWrap> popularNodes;
	List<CategoryWrap> popularCategories;
	List<NodeParentChildWrap> northernNodes;
	List<NodeParentChildWrap> southernNodes;
	List<NodeParentChildWrap> westernNodes;
	List<ProductWrap> popularProducts;
	List<CouponWrap> activeCoupons;
	List<Testimonial> testimonials;
	BooleanWithReason result;
	
	public List<NodeParentChildWrap> getPopularNodes() {
		return popularNodes;
	}

	public void setPopularNodes(List<NodeParentChildWrap> popularNodes) {
		this.popularNodes = popularNodes;
	}

	public List<NodeParentChildWrap> getWesternNodes() {
		return westernNodes;
	}

	public void setWesternNodes(List<NodeParentChildWrap> westernNodes) {
		this.westernNodes = westernNodes;
	}

	public List<NodeParentChildWrap> getNorthernNodes() {
		return northernNodes;
	}

	public void setNorthernNodes(List<NodeParentChildWrap> northernNodes) {
		this.northernNodes = northernNodes;
	}

	public List<NodeParentChildWrap> getSouthernNodes() {
		return southernNodes;
	}

	public void setSouthernNodes(List<NodeParentChildWrap> southernNodes) {
		this.southernNodes = southernNodes;
	}

	public List<ProductWrap> getPopularProducts() {
		return popularProducts;
	}

	public void setPopularProducts(List<ProductWrap> popularProducts) {
		this.popularProducts = popularProducts;
	}

	public List<CategoryWrap> getPopularCategories() {
		return popularCategories;
	}

	public void setPopularCategories(List<CategoryWrap> popularCategories) {
		this.popularCategories = popularCategories;
	}

	public List<CouponWrap> getActiveCoupons() {
		return activeCoupons;
	}

	public void setActiveCoupons(List<CouponWrap> activeCoupons) {
		this.activeCoupons = activeCoupons;
	}

	public List<Testimonial> getTestimonials() {
		return testimonials;
	}

	public void setTestimonials(List<Testimonial> testimonials) {
		this.testimonials = testimonials;
	}

	public BooleanWithReason getResult() {
		return result;
	}

	public void setResult(BooleanWithReason result) {
		this.result = result;
	}
	
}
