package com.appeti.main.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.appeti.database.table.aggregation.BaseCtr;
import com.appeti.database.table.product.Category;
import com.appeti.database.table.product.Product;
import com.appeti.database.table.product.ProductImage;
import com.appeti.database.table.product.Ptitle;
import com.appeti.database.table.product.PtitleImage;
import com.appeti.database.table.product.PtitleRating;
import com.appeti.database.table.product.Tag;
import com.appeti.database.table.product.wrap.RelatedProductWrap;
import com.appeti.database.table.product.wrap.TagWrap;
import com.appeti.database.table.review.Review;
import com.appeti.database.table.review.ReviewWrap;
import com.appeti.database.table.user.Seller;
import com.appeti.database.table.user.wrap.SellerWrap;
import com.appeti.database.utils.Image;
import com.appeti.utils.BooleanWithReason;
import com.appeti.utils.Constants;

public class ProductService extends Service {
	public ProductService(HttpServletRequest request){
		super(request);
	}
	
	public Map<Long,Image> getPtitleImageMap(long productId){
		Map<Long,Image> map = new HashMap<Long, Image>();
		List<Ptitle> ptitles = Ptitle.getPtitlesByProductId(productId);
		for(Ptitle ptitle : ptitles){
			Image image = PtitleImage.getDefaultImageByPtitleId(ptitle.getPtitleId());
			if(image == null)
				image = ProductImage.getDefaultImageByProductId(productId); // fallback to product image
			map.put(ptitle.getPtitleId(), image);
		}
		return map;
	}
	
	public Map<Long,List<TagWrap>> getPtitleTagsMap(long productId,List<Ptitle> ptitles){
		return getPtitleTagsMap(productId, ptitles, null);
	}
	
	public Map<Long,List<TagWrap>> getPtitleTagsMap(long productId,List<Ptitle> ptitles,Tag selectedTag){
		Map<Long,List<TagWrap>> map = new HashMap<Long,List<TagWrap>>();
		for(Ptitle ptitle : ptitles){
			List<TagWrap> bestUniqueTags = Tag.getBestTags(productId, ptitle.getPtitleId(),selectedTag);
			map.put(ptitle.getPtitleId(), bestUniqueTags);
		}
		return map;
	}
	
	public Map<Long,Map<String,List<TagWrap>>> getOtherSellersTagsMap(List<Ptitle> ptitles){
		Map<Long,Map<String,List<TagWrap>>> map = new HashMap<Long,Map<String,List<TagWrap>>>();
		for(Ptitle ptitle : ptitles){
			List<Tag> tags = Tag.getValidTagsByPtitleId(ptitle.getPtitleId());
			Map<String, List<TagWrap>> buckets = Tag.getUnitBuckets(tags);
			map.put(ptitle.getPtitleId(), buckets);
		}
		return map;
	}
	
	public Map<Long,SellerWrap> getSellerDescMap(long productId){
		Map<Long,SellerWrap> map = new HashMap<Long,SellerWrap>();
		List<Long> sellerIds = Tag.getDistinctSellersForProduct(productId);
		for(long sellerId : sellerIds){
			map.put(sellerId, SellerWrap.getSeller(Seller.getById(sellerId)));
		}
		return map;
	}
	
	public Map<Long,SellerWrap> getSellerDescMap(long productId, long ptitleId){
		Map<Long,SellerWrap> map = new HashMap<Long,SellerWrap>();
		List<Long> sellerIds = Tag.getDistinctSellers(productId, ptitleId);
		for(long sellerId : sellerIds){
			map.put(sellerId, SellerWrap.getSeller(Seller.getById(sellerId)));
		}
		return map;
	}
	public List<Image> getImages(long productId, long ptitleId, String clickSource){
		List<Image> list = new LinkedList<Image>();
		Image defaultPtitleImage = PtitleImage.getDefaultImageByPtitleId(ptitleId);
		if(defaultPtitleImage != null)
			list.add(defaultPtitleImage);
		Image defaultProductImage = ProductImage.getDefaultImageByProductId(productId);
		if(defaultProductImage != null)
			list.add(defaultProductImage);
		List<Image> ptitleImages = PtitleImage.getAllImagesByPtitleId(ptitleId, true);
		list.addAll(ptitleImages);
		/*if(Constants.ClickSaleSource.APP.equals(clickSource)){
			List<Image> newList = new LinkedList<Image>();
			for(Image image : list){
				String url = image.getUrl();
				url = url.replace("images", "images1017");
				image.setUrl(url);	
				newList.add(image);
			}
			list = newList;
		}*/
		return list;
	}
	
	public static List<RelatedProductWrap> getRelatedProducts(Product product, int num){
		List<RelatedProductWrap> list = new ArrayList<RelatedProductWrap>();
		for(Product relProduct : Product.getProducts(null, Category.getAllSubCategoryIds(product.getCategoryId()))){
			if(relProduct.getProductId() == product.getProductId())
				continue;
			double ctr = BaseCtr.getCtrForProductId(relProduct.getProductId());
			list.add(new RelatedProductWrap(relProduct,ctr));
		}
		Collections.sort(list,new Comparator<RelatedProductWrap>() {
			@Override
			public int compare(RelatedProductWrap o1, RelatedProductWrap o2) {
				return (int)(o1.getCtr() - o2.getCtr());
			}
		});
		if(num < 1 || num > list.size())
			num = list.size();
		return list.subList(0, num);
	}
	
	public static List<ReviewWrap> getReviews(long productId, long ptitleId, long userId){
		List<PtitleRating> productRatings = PtitleRating.getAllRatings(productId, ptitleId);
		List<ReviewWrap> list = new ArrayList<ReviewWrap>();
		for(PtitleRating rating : productRatings){
			ReviewWrap review = ReviewWrap.getReviewWrap(rating);
			if(review == null)
				continue;
			if(userId != -1 && rating.getUserId() == userId)
				review.setByMe(true);
			list.add(review);
		}
		Collections.sort(list, new Comparator<ReviewWrap>() {

			@Override
			public int compare(ReviewWrap o1, ReviewWrap o2) {
				if(o1.getDateAdded().before(o2.getDateAdded()))
					return -1;
				return 1;
			}
		});
		return list;
	}
	
	public static BooleanWithReason postReview(long productId, long ptitleId, long tagId, long userId, int rating, String title, String desc){
		long id = -1;
		Review review = Review.insertReview(userId, title, desc, Constants.ReviewType.PRODUCT);
		if(review != null){
			id = PtitleRating.insertRating(productId, ptitleId, tagId, userId, rating, review.getId());
		}
		if(id != -1){
			return new BooleanWithReason(true, "Thank you for reviewing this product. Your review will start appearing in some time.");
		}else{
			return new BooleanWithReason(false, "Some problem occurred while adding your review. Please try again later.");
		}
	}
}
