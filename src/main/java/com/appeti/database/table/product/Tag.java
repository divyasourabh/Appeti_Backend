package com.appeti.database.table.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.appeti.database.table.product.wrap.TagWrap;
import com.appeti.database.table.user.SellerRating;
import com.appeti.database.utils.DBUtils;
import com.appeti.utils.Cache;
import com.appeti.utils.ExceptionUtils;

public class Tag {
	private long tagId;
	private long ptitleId;
	private long productId;
	private long sellerId;
	private long unitSize;
	private long unitTypeId;
	private String unitString; // not in DB
	private long pricePerUnit;
	private double marginPercent;
	private boolean isValid;
	private boolean inStock;
	private Date dateAdded;
	private Date dateModified;
	
	private static final String TABLENAME = "tag";
	private static final double MAX_TAG_SCORE = 1.0;
	
	private static Cache<Long,Tag> tagCache = new Cache<Long,Tag>();
	private static Cache<Long,List<Tag>> ptitleTagsCache = new Cache<Long,List<Tag>>();
	private static Cache<Long,List<Tag>> productTagsCache = new Cache<Long,List<Tag>>();
	private static Cache<Long,List<Tag>> sellerTagsCache = new Cache<Long,List<Tag>>();
	private static Cache<String,Tag> bestTagCache = new Cache<String,Tag>();
	private static Cache<String,List<Tag>> productPtitleTagCache = new Cache<String,List<Tag>>();
	private static Cache<Long,List<Tag>> activeTagsCache = new Cache<Long,List<Tag>>();
	
	private Tag(ResultSet rs) throws SQLException{
		this.tagId = rs.getLong("tag_id");
		this.ptitleId = rs.getLong("ptitle_id");
		this.productId = rs.getLong("product_id");
		this.sellerId = rs.getLong("seller_id");
		this.unitSize = rs.getLong("unit_size");
		this.unitTypeId = rs.getLong("unit_type_id");
		this.pricePerUnit = rs.getLong("price_per_unit");
		this.marginPercent = rs.getDouble("margin_per_cent");
		this.isValid = rs.getBoolean("is_valid");
		this.inStock = rs.getBoolean("in_stock");
		this.dateAdded = rs.getDate("date_added");
		this.dateModified = rs.getDate("date_modified");
		this.unitString = this.unitSize + " " + UnitType.getUnitName(this.unitTypeId);
	}
	
	public static Tag getTagById(long id){
		Tag tag = tagCache.get(id);
		if(tag != null)
			return tag;
		
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery(null, TABLENAME , "tag_id = ?");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, id);
			rs = stmt.executeQuery();
			if(rs.next())
				tag = new Tag(rs);
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		
		if(tag != null)
			tagCache.put(id, tag);
		
		return tag;
	}
	public static List<Tag> getValidTagsByPtitleId(long id){
		List<Tag> list = ptitleTagsCache.get(id);
		if(list != null)
			return list;
		
		list = new ArrayList<Tag>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery(null, TABLENAME , "ptitle_id = ? and is_valid = 1");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, id);
			rs = stmt.executeQuery();
			while(rs.next())
				list.add(new Tag(rs));
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		
		if(list != null && list.size()>0)
			ptitleTagsCache.put(id, list);
		
		return list;
	}

	public static List<Tag> getValidTagsByProductId(long id){
		List<Tag> list = productTagsCache.get(id);
		if(list != null)
			return list;
		
		list = new ArrayList<Tag>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery(null, TABLENAME , "product_id = ? and is_valid = 1");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, id);
			rs = stmt.executeQuery();
			while(rs.next())
				list.add(new Tag(rs));
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		
		if(list != null && list.size()>0)
			productTagsCache.put(id, list);
		
		return list;
	}

	public static List<Tag> getValidTagsBySellerId(long id){
		List<Tag> list = sellerTagsCache.get(id);
		if(list != null)
			return list;
		
		list = new ArrayList<Tag>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery(null, TABLENAME , "seller_id = ? and is_valid = 1");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, id);
			rs = stmt.executeQuery();
			while(rs.next())
				list.add(new Tag(rs));
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		
		if(list != null && list.size()>0)
			sellerTagsCache.put(id, list);
		
		return list;
	}
	
	public static List<Tag> getActiveTags(){
		List<Tag> list = activeTagsCache.get(1l);
		if(list != null)
			return list;
		
		list = new ArrayList<Tag>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery(null, TABLENAME , "is_valid = 1");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();
			while(rs.next())
				list.add(new Tag(rs));
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		
		if(list != null && list.size()>0)
			activeTagsCache.put(1l, list);
		
		return list;
	}
	
	// main logic to be added margin*ctr max
	public static Tag getBestConversionTag(long productId, long ptitleId){
		String cacheKey = "" + productId + "-" + ptitleId;
		Tag tag = bestTagCache.get(cacheKey);
		if(tag != null)
			return tag;
		
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery(null, TABLENAME , "product_id = ? and ptitle_id = ? and is_valid = 1");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, productId);
			stmt.setLong(2, ptitleId);
			rs = stmt.executeQuery();
			if(rs.next())
				tag = new Tag(rs);
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		
		if(tag != null)
			bestTagCache.put(cacheKey, tag);
		
		return tag;
	}
	
	public static List<Tag> getValidTags(long productId, long ptitleId){
		String cacheKey = "" + productId + "-" + ptitleId;
		List<Tag> list = productPtitleTagCache.get(cacheKey);
		if(list != null)
			return list;
		
		list = new ArrayList<Tag>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery(null, TABLENAME , "product_id = ? and ptitle_id = ? and is_valid = 1");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, productId);
			stmt.setLong(2, ptitleId);
			rs = stmt.executeQuery();
			while(rs.next())
				list.add(new Tag(rs));
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		
		if(list != null && list.size()>0)
			productPtitleTagCache.put(cacheKey, list);
		
		return list;
	}
	
	public static List<TagWrap> getBestTags(long productId, long ptitleId){
		return getBestTags(productId,ptitleId,null);
	}

	public static List<TagWrap> getBestTags(long productId, long ptitleId, Tag selectedTag){
		List<Tag> tags = getValidTags(productId, ptitleId);
		return getBestTags(tags,selectedTag);
	}
	
	public static List<TagWrap> getBestTags(List<Tag> tags, Tag selectedTag){
		return getBestTags(getUnitBuckets(tags), selectedTag);
	}
	
	public static List<TagWrap> getBestTags(Map<String, List<TagWrap>> buckets, Tag selectedTag){
		List<TagWrap> bestTags = new ArrayList<TagWrap>();
		// get best tag from each bucket
		Set<Entry<String,List<TagWrap>>> entrySet = buckets.entrySet();
		Iterator<Entry<String,List<TagWrap>>> iterator = entrySet.iterator();
		while(iterator.hasNext()){
			List<TagWrap> tagList = iterator.next().getValue();
			double maxScore = 0;
			TagWrap bestTag = null;
			for(TagWrap tag: tagList){
				if(selectedTag != null && selectedTag.getTagId() == tag.getTagId()){
					bestTag = TagWrap.getWrap(selectedTag,MAX_TAG_SCORE);
					break;
				}
				double tagScore = tag.getScore();
				if(tagScore > maxScore){
					maxScore = tagScore;
					bestTag = tag;
				}
			}
			if(bestTag != null)
				bestTags.add(bestTag);
		}
		return bestTags;
	}
	public static Map<String, List<TagWrap>> getUnitBuckets(long productId, long ptitleId){
		return getUnitBuckets(getValidTags(productId, ptitleId));
	}
	
	public static Map<String, List<TagWrap>> getUnitBuckets(List<Tag> tags){
		Map<String, List<TagWrap>> buckets = new HashMap<String,List<TagWrap>>();
		for(Tag tag : tags){
			String unitStr = tag.getCompressedUnitString();
			List<TagWrap> bucket = buckets.get(unitStr);
			if(bucket == null)
				bucket = new ArrayList<TagWrap>();
			double tagScore = getTagScore(tag);
			bucket.add(TagWrap.getWrap(tag,tagScore));
			buckets.put(unitStr, bucket);
		}
		return buckets;
	}
	
	public static List<Long> getDistinctSellersForProduct(long productId){
		List<Long> list = new ArrayList<Long>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery("distinct seller_id as seller_id", TABLENAME , "product_id = ? and is_valid = 1");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, productId);
			rs = stmt.executeQuery();
			while(rs.next())
				list.add(rs.getLong("seller_id"));
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return list;
	}
	
	public static List<Long> getDistinctSellers(long productId, long ptitleId){
		List<Long> list = new ArrayList<Long>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery("distinct seller_id as seller_id", TABLENAME , "product_id = ? and ptitle_id = ? and is_valid = 1");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, productId);
			stmt.setLong(2, ptitleId);
			rs = stmt.executeQuery();
			while(rs.next())
				list.add(rs.getLong("seller_id"));
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return list;
	}
	
	public static boolean isValidTag(long tagId){
		Tag tag = Tag.getTagById(tagId);
		if(tag != null)
			return tag.isValid;
		return false;
	}

	private static double getTagScore(Tag tag){
		double sellerRating = SellerRating.getSellerRating(tag.getSellerId());
		double ppu = tag.getPricePerUnit();
		return (sellerRating + Math.log10(ppu)/5)/2;
	}
	
	public String getUnitString(){
		return this.unitString;
	}
	
	public String getCompressedUnitString(){
		return StringUtils.deleteWhitespace(this.unitString);
	}

	public long getTagId() {
		return tagId;
	}
	public void setTagId(long tagId) {
		this.tagId = tagId;
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
	public long getSellerId() {
		return sellerId;
	}
	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}
	public long getUnitSize() {
		return unitSize;
	}
	public void setUnitSize(long unitSize) {
		this.unitSize = unitSize;
	}
	public long getUnitTypeId() {
		return unitTypeId;
	}
	public void setUnitTypeId(long unitTypeId) {
		this.unitTypeId = unitTypeId;
	}
	public long getPricePerUnit() {
		return pricePerUnit;
	}
	public void setPricePerUnit(long pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}
	public double getMarginPercent() {
		return marginPercent;
	}
	public void setMarginPercent(double marginPercent) {
		this.marginPercent = marginPercent;
	}
	public boolean isValid() {
		return isValid;
	}
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	public boolean isInStock() {
		return inStock;
	}
	public void setInStock(boolean inStock) {
		this.inStock = inStock;
	}

	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}
}
