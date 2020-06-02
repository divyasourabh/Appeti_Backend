package com.appeti.database.table.cart.wrap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.appeti.database.table.cart.Coupon;
import com.appeti.database.table.product.Product;
import com.appeti.database.utils.Image;
import com.appeti.utils.Constants;

public class CouponWrap {
	private String code;
	private int discount;
	private long productId;
	private String productName;
	private Image image;
	private Date validTill;
	
	private CouponWrap(){}
	
	public static CouponWrap getWrap(Coupon coupon){
		if(coupon == null)
			return null;
		CouponWrap wrap = new CouponWrap();
		wrap.code = coupon.getCode();
		wrap.discount = coupon.getDiscount();
		if(Constants.CouponScope.PRODUCT.equals(coupon.getScope())){
			wrap.productId = coupon.getMappingId();
			Product prod = Product.getProductById(wrap.productId);
			if(prod == null)
				return null;
			wrap.productName = prod.getName();
		}else{
			wrap.productId = -1;
		}
		wrap.validTill = coupon.getTo();
		return wrap;
	}
	
	public static List<CouponWrap> getWrap(List<Coupon> coupons){
		List<CouponWrap> list = new ArrayList<CouponWrap>();
		if(coupons != null){
			for(Coupon coupon : coupons){
				CouponWrap wrap = getWrap(coupon);
				if(wrap != null)
					list.add(wrap);
			}
		}	
		return list;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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

	public Date getValidTill() {
		return validTill;
	}

	public void setValidTill(Date validTill) {
		this.validTill = validTill;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
	
	
}
