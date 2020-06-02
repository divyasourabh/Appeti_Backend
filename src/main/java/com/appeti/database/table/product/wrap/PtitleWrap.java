package com.appeti.database.table.product.wrap;

import java.util.ArrayList;
import java.util.List;

import com.appeti.database.table.product.ProductImage;
import com.appeti.database.table.product.Ptitle;
import com.appeti.database.table.product.PtitleImage;
import com.appeti.database.utils.Image;

public class PtitleWrap {
	private long ptitleId;
	private long productId;
	private String name;
	private Image image;
	
	public static PtitleWrap getWrap(Ptitle ptitle){
		if(ptitle == null)
			return null;
		PtitleWrap wrap = new PtitleWrap();
		wrap.setPtitleId(ptitle.getPtitleId());
		wrap.setProductId(ptitle.getProductId());
		wrap.setName(ptitle.getName());
		Image image = PtitleImage.getDefaultImageByPtitleId(ptitle.getPtitleId());
		if(image == null){
			List<Image> allImages = PtitleImage.getAllImagesByPtitleId(ptitle.getPtitleId());
			if(allImages != null && allImages.size() > 0)
				image = allImages.get(0);
		}
		if(image == null)
			image = ProductImage.getDefaultImageByProductId(ptitle.getProductId());
		if(image != null){
			wrap.setImage(image);
		}
		return wrap;
	}
	
	public static List<PtitleWrap> getWrapList(List<Ptitle> list){
		List<PtitleWrap> wrapList = new ArrayList<PtitleWrap>();
		if(list != null){
			for(Ptitle ptitle : list){
				PtitleWrap wrap = getWrap(ptitle);
				if(wrap != null)
					wrapList.add(wrap);
			}
		}
		return wrapList;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	
	
}
