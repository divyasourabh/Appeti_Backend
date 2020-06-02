package com.appeti.main.beans;

import java.util.List;

import com.appeti.database.table.testimonial.Testimonial;

public class MarketingBean {
	private List<Testimonial> testimonials;

	public List<Testimonial> getTestimonials() {
		return testimonials;
	}

	public void setTestimonials(List<Testimonial> testimonials) {
		this.testimonials = testimonials;
	}
}
