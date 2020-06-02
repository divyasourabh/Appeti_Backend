package com.appeti.main.management;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.appeti.database.table.aggregation.Click;
import com.appeti.database.table.product.Product;
import com.appeti.database.table.product.ProductAttribute;
import com.appeti.database.table.product.Ptitle;
import com.appeti.database.table.product.PtitleRating;
import com.appeti.database.table.product.Tag;
import com.appeti.database.table.product.wrap.ProductPageWrap;
import com.appeti.database.table.product.wrap.PtitleWrap;
import com.appeti.database.table.product.wrap.TagWrap;
import com.appeti.database.table.review.ReviewWrap;
import com.appeti.database.table.user.Seller;
import com.appeti.database.table.user.wrap.SellerWrap;
import com.appeti.main.beans.ProductBean;
import com.appeti.main.services.ProductService;
import com.appeti.main.services.Service;
import com.appeti.utils.BooleanWithReason;
import com.appeti.utils.Constants;
import com.appeti.utils.ExceptionUtils;

public class ProductManagement extends Management{
	private static final String DEAFAULT_PRODUCT_BEAN = "productBean";
	private static final String DELIMITER = "-";

	public ProductPageWrap setProductPage(HttpServletRequest request, HttpServletResponse response, String productPtitleTagsIdStr){
		ProductBean bean = new ProductBean();
		prepareHeaderBean(request, response);
		String[] ids = productPtitleTagsIdStr.split(DELIMITER);
		long productId = -1;
		long ptitleId = -1;
		long tagId = -1;
		ProductPageWrap product = null;
		if(ids.length >= 1 && ids.length <= 3){
			try{
				productId = Long.parseLong(ids[0]);
				ptitleId = ids.length > 1 ? Long.parseLong(ids[1]) : -1;
				tagId = ids.length > 2 ? Long.parseLong(ids[2]) : -1;
				
			}catch(NumberFormatException e){
				System.out.println("Product String with Exception: "+productPtitleTagsIdStr);
				return null;
			}
			product = setProduct(request, response,productId, ptitleId, tagId, Constants.ClickSaleSource.WEB);
			bean.setProduct(product);
			long userId = request.getSession(false) != null ? request.getSession(false).getAttribute("userId") != null ? (Long)(request.getSession(false).getAttribute("userId")) : -1 : -1;
			bean.setReviews(getReviews(productId,ptitleId,userId));
			request.setAttribute(DEAFAULT_PRODUCT_BEAN, bean);
		}
		return product;
	}
	public ProductPageWrap setProduct(HttpServletRequest request, HttpServletResponse response, long productId, long ptitleId, long tagId, String clickSource){
		ProductService service = new ProductService(request);
		ProductPageWrap result = null;

		Product product = Product.getProductById(productId);
		if(product == null){
			ExceptionUtils.logException(new IllegalStateException("Invalid product " + productId), log);
			return result;
		}
		Click.record(productId, ptitleId, tagId, clickSource);
		Ptitle ptitle = Ptitle.getPtitleById(ptitleId);
		if(ptitle == null)
			ptitle = Ptitle.getDefaultPtitle(productId);
		if(ptitle == null){
			List<Ptitle> ptitles = Ptitle.getPtitlesByProductId(productId);
			if(ptitles.size() > 0)
				ptitle = ptitles.get(0);
		}
		if(ptitle == null){
			ExceptionUtils.logException(new IllegalStateException("Invalid ptitle " + ptitleId  + " for product: " + product.getProductId() + "and no default ptitle set"), log);
			return result;
		}
		Tag tag = Tag.getTagById(tagId);
		if(tag == null)
			tag = Tag.getBestConversionTag(productId, ptitle.getPtitleId());
		if(tag == null){
			ExceptionUtils.logException(new IllegalStateException("Invalid tag " + tagId + " and no best tag found"), log);
			return result;
		}
		result = new ProductPageWrap();
		result.setProductId(product.getProductId());
		result.setPtitleId(ptitle.getPtitleId());
		result.setTagId(tag.getTagId());
		result.setSellerInfo(SellerWrap.getSeller(Seller.getById(tag.getSellerId())));
		result.setNodeMap(Service.createNodeMap(product.getNodeId()));
		result.setCategoryMap(Service.createCategoryMap(product.getCategoryId()));
		result.setProductName(product.getName());
		result.setPtitleName(ptitle.getName());
		result.setPtitleDesc(ptitle.getDescription());
		result.setProductDescription(product.getDescription());
		result.setUnitString(tag.getCompressedUnitString());
		result.setPpu(tag.getPricePerUnit());
		result.setInStock(tag.isInStock());
		result.setAvailability(tag.isInStock() ? Constants.ProductAvailability.IN_STOCK : Constants.ProductAvailability.OUT_OF_STOCK);
		result.setAttrs(ProductAttribute.getAllAttributesByPtitleId(product.getProductId(), ptitle.getPtitleId()));
		result.setImages(service.getImages(product.getProductId(),ptitle.getPtitleId(), clickSource));
		Map<String,List<TagWrap>> buckets = Tag.getUnitBuckets(product.getProductId(), ptitle.getPtitleId());
		//result.setOtherSellers(new JSONObject(buckets));
		List<TagWrap> tags = Tag.getBestTags(buckets, tag);
		Collections.sort(tags , new Comparator<TagWrap>() {

			@Override
			public int compare(TagWrap o1, TagWrap o2) {
				// check by unit gm<kg<pc
				int compare = o1.getUnitString().split(" ")[1].compareToIgnoreCase(o2.getUnitString().split(" ")[1]);
				if(compare != 0){
					return compare;
				}else{
					return (int)(o1.getUnitSize() - o2.getUnitSize());
				}
			}
		});

		result.setBestTags(tags);
		result.setAllPtitles(PtitleWrap.getWrapList(Ptitle.getPtitlesByProductId(product.getProductId())));
		result.setSellerDescriptionMap(new JSONObject(service.getSellerDescMap(product.getProductId(), ptitle.getPtitleId())));
		result.setRelatedProducts(ProductService.getRelatedProducts(product,4));
		result.setRating(PtitleRating.getProductRating(product.getProductId()));
		result.setNumReviews(PtitleRating.getAllRatings(product.getProductId(), ptitle.getPtitleId()).size());
		return result;
	}

	public List<ReviewWrap> getReviews(long productId, long ptitleId, long userId){
		return ProductService.getReviews(productId,ptitleId, userId);
	}

	public ProductPageWrap getProduct(HttpServletRequest request, HttpServletResponse response){
		long productId = -1;
		long ptitleId = -1;
		long tagId = -1;
		try{
			productId = request.getParameter("productId") != null ? Long.valueOf(request.getParameter("productId")) : -1;
			ptitleId = request.getParameter("ptitleId") != null ? Long.valueOf(request.getParameter("ptitleId")) : -1;
			tagId = request.getParameter("tagId") != null ? Long.valueOf(request.getParameter("tagId")) : -1;
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}
		return setProduct(request, response,productId,ptitleId,tagId,Constants.ClickSaleSource.APP);
	}

	public List<ReviewWrap> getReviews(HttpServletRequest request, HttpServletResponse response){
		long productId = request.getParameter("productId") != null ? Long.valueOf(request.getParameter("productId")) : -1;
		long ptitleId = request.getParameter("ptitleId") != null ? Long.valueOf(request.getParameter("ptitleId")) : -1;
		long userId = request.getSession(false) != null ? Long.valueOf((String)request.getSession(false).getAttribute("userId")) : -1;
		return getReviews(productId,ptitleId,userId);
	}
	
	public BooleanWithReason addReview(HttpServletRequest request, HttpServletResponse response, long userId){
		int rating = -1;
		long productId = -1;
		long ptitleId = -1;
		long tagId = -1;
		String title = request.getParameter("title");
		String desc = request.getParameter("desc");
		try{
			rating = request.getParameter("rating") != null ? Integer.valueOf(request.getParameter("rating")) : -1;
			productId = request.getParameter("productId") != null ? Long.valueOf(request.getParameter("productId")) : -1;
			ptitleId = request.getParameter("ptitleId") != null ? Long.valueOf(request.getParameter("ptitleId")) : -1;
			tagId = request.getParameter("tagId") != null ? Long.valueOf(request.getParameter("tagId")) : -1;
			System.out.println(userId);
			if(productId == -1 || ptitleId == -1 || tagId == -1){
				return new BooleanWithReason(false, "Invalid request.");
			}else if(userId == -1){
				//return new BooleanWithReason(false, "Please login to post a review.");
			}
			return ProductService.postReview(productId, ptitleId, tagId, userId, rating, title, desc);
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}
		return new BooleanWithReason(false, "Invalid request");
	}
	
	public BooleanWithReason addReviewWeb(HttpServletRequest request, HttpServletResponse response){
		long userId = getUserId(request);
		return addReview(request, response, userId);
	}
	
	public BooleanWithReason addReviewService(HttpServletRequest request, HttpServletResponse response){
		long userId = request.getParameter("userId") != null ? Long.valueOf(request.getParameter("userId")) : -1;
		return addReview(request, response, userId);
	}

	public List<ReviewWrap> getReviewsService(HttpServletRequest request, HttpServletResponse response){
		long productId = request.getParameter("productId") != null ? Long.valueOf(request.getParameter("productId")) : -1;
		long ptitleId = request.getParameter("ptitleId") != null ? Long.valueOf(request.getParameter("ptitleId")) : -1;
		long userId = request.getParameter("userId") != null ? Long.valueOf(request.getParameter("userId")) : -1;
		return getReviews(productId,ptitleId,userId);
	}
	
}
