package com.appeti.database.table.product.wrap;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.appeti.database.table.aggregation.BaseCtr;
import com.appeti.database.table.product.Product;
import com.appeti.database.table.product.ProductImage;
import com.appeti.database.table.product.Ptitle;
import com.appeti.database.table.product.PtitleImage;
import com.appeti.database.table.product.Tag;
import com.appeti.database.utils.Image;
import com.appeti.main.services.Service;

public class ProductWrap {
	long productId;
	long ptitleId;
	Map<Long,String> nodeMap = new LinkedHashMap<Long, String>();
	String productName;
	Image image;
	String ratingPercent; // multiple of 5
	double rating; // out of five
	double ctr;
	Date dateAdded;
	
	public ProductWrap(){
		
	}

	public static ProductWrap getWrap(Product product){
		if(product == null)
			return null;
		ProductWrap wrap = new ProductWrap();
		if(Tag.getValidTagsByProductId(product.getProductId()).size() <= 0)
			return null;
		wrap.productId = product.getProductId();
		wrap.nodeMap = Service.createNodeMap(product.getNodeId());
		wrap.productName = product.getName();
		wrap.image = ProductImage.getDefaultImageByProductId(product.getProductId());
		wrap.ctr = BaseCtr.getCtrForProductId(product.getProductId());
		wrap.dateAdded = product.getDateAdded(); // business logic to use date added or modified
		return wrap;
	}
	
	public static List<ProductWrap> getWrapList(List<Ptitle> ptitles){
		List<ProductWrap> list = new ArrayList<ProductWrap>();
		if(ptitles != null && ptitles.size()>0){
			Product product = Product.getProductById(ptitles.get(0).getProductId());
			Map<Long,String> nodeMap = Service.createNodeMap(product.getNodeId());
			for(Ptitle ptitle : ptitles){
				if(Tag.getValidTagsByPtitleId(ptitle.getPtitleId()).size()<=0)
					continue;
				ProductWrap wrap = new ProductWrap();
				wrap.setProductId(product.getProductId());
				wrap.setPtitleId(ptitle.getPtitleId());
				wrap.setProductName(ptitle.getName());
				wrap.setCtr(BaseCtr.getCtrForPtitleId(ptitle.getPtitleId()));
				wrap.setDateAdded(ptitle.getDateAdded()); // business logic to use date added or modified
				wrap.setNodeMap(nodeMap);
				wrap.setImage(PtitleImage.getDefaultImageByPtitleId(ptitle.getPtitleId()));
				list.add(wrap);
			}
		}
		return list;
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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getRatingPercent() {
		return ratingPercent;
	}

	public void setRatingPercent(String ratingPercent) {
		this.ratingPercent = ratingPercent;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public Map<Long, String> getNodeMap() {
		return nodeMap;
	}

	public void setNodeMap(Map<Long, String> nodeMap) {
		this.nodeMap = nodeMap;
	}

	public double getCtr() {
		return ctr;
	}

	public void setCtr(double ctr) {
		this.ctr = ctr;
	}

	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
}
