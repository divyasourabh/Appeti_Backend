package com.appeti.main.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.appeti.database.table.node.Node;
import com.appeti.database.table.product.Category;
import com.appeti.database.table.product.Product;
import com.appeti.database.table.product.Ptitle;
import com.appeti.database.table.product.PtitleRating;
import com.appeti.database.table.product.Tag;
import com.appeti.database.table.product.wrap.SearchResultWrap;
import com.appeti.database.table.product.wrap.TagWrap;
import com.appeti.utils.Cache;
import com.appeti.utils.Constants;
import com.appeti.utils.KeywordMatcher;
import com.appeti.utils.SearchKeyword;

public class SearchService extends Service {
	
	private static class Match{
		SearchKeyword keyword;
		double score;
		Match(SearchKeyword keyword, double score){
			this.keyword = keyword;
			this.score = score;
		}
	}
	
	private static Cache<String,List<SearchResultWrap>> searchCache = new Cache<String, List<SearchResultWrap>>();
	private static List<SearchKeyword> keywordsList = null;
	
	static{
		loadFlatTable();
	}
	
	public SearchService(HttpServletRequest request){
		super(request);
	}
	
	public static List<SearchResultWrap> getQuerySearchResults(String query, int orderBy, int lowLimit, int highLimit){
		List<Match> matches = new ArrayList<Match>();
		for(SearchKeyword keyword : keywordsList){
			double score = KeywordMatcher.match(query, keyword);
			if(score > 0)
				matches.add(new Match(keyword, score));
		}
		double sum = 0;
		for(Match match : matches){
			sum += match.score;
		}
		double avg = matches.size() > 0 ? sum/matches.size() : 0;
		List<Match> list = new ArrayList<Match>();
		for(Match match : matches){
			if(match.score >= avg)
				list.add(match);
		}
		matches = list;
		Collections.sort(matches, new Comparator<Match>() {
			@Override
			public int compare(Match o1, Match o2) {
				if(o1.score > o2.score)
					return -1;
				if(o1.score < o2.score)
					return 1;
				return 0;
			}
		});
		List<SearchResultWrap> searchResults = prepareQuerySearchResults(matches);
		log.info("query search results -> " + searchResults.size());
		Comparator<SearchResultWrap> comparator = getComparator(orderBy, false);
		if(comparator != null)
			Collections.sort(searchResults,comparator);
		if(lowLimit < 1 || lowLimit > searchResults.size())
			lowLimit = 1;
		if(highLimit < 1 || highLimit > searchResults.size())
			highLimit = searchResults.size();
		return searchResults.subList(lowLimit -1, highLimit);
	}
	
	public static List<SearchResultWrap> getNodeCategorySearchResults(long nodeId, long categoryId, long productId, int orderBy, int lowLimit, int highLimit){
		List<SearchResultWrap> searchResults = getResults(nodeId, categoryId, productId);
		log.info("results -> " + searchResults.size());
		Comparator<SearchResultWrap> comparator = getComparator(orderBy, true);
		if(comparator != null)
			Collections.sort(searchResults,comparator);
		if(lowLimit < 1 || lowLimit > searchResults.size())
			lowLimit = 1;
		if(highLimit < 1 || highLimit > searchResults.size())
			highLimit = searchResults.size();
		return searchResults.subList(lowLimit -1, highLimit);
	}
	
	public static List<SearchResultWrap> prepareSearchResults(List<Product> list){
		List<SearchResultWrap> searchResults = new ArrayList<SearchResultWrap>();
		for(Product product : list){
			for(Ptitle ptitle : Ptitle.getPtitlesByProductId(product.getProductId())){
				List<TagWrap> tags =  Tag.getBestTags(product.getProductId(), ptitle.getPtitleId());
				for(TagWrap tag : tags){
					double score = (tag.getScore() + 
							PtitleRating.getPtitleRating(ptitle.getPtitleId()) + 
							PtitleRating.getProductRating(product.getProductId())) / 3;
					searchResults.add(SearchResultWrap.getSearchResult(product, ptitle, tag, score));
				}
			}
		}
		return searchResults;
	}
	
	public static List<SearchResultWrap> getResults(long nodeId, long categoryId, long productId){
		String cacheKey = "" + nodeId + "-" + categoryId + "-" + productId;
		List<SearchResultWrap> searchResults = searchCache.get(cacheKey);
		
		if(searchResults != null)
			return searchResults;
		
		Set<Long> childNodeIds = Node.getAllChildIds(nodeId);
		List<Product> list;
		Product prod = Product.getProductById(productId);
		if(prod != null){
			list = new ArrayList<Product>();
			list.add(prod);
		}else{
		 list = Product.getProducts(childNodeIds,Category.getAllSubCategoryIds(categoryId));
		}
		searchResults = prepareSearchResults(list);
		
		if(searchResults != null)
			searchCache.put(cacheKey, searchResults);
		
		return searchResults;
	}
	
	private static List<SearchResultWrap> prepareQuerySearchResults(List<Match> matches){
		List<SearchResultWrap> searchResults = new ArrayList<SearchResultWrap>();
		for(Match match : matches){
			Product product = Product.getProductById(match.keyword.getProductId());
			Ptitle ptitle = Ptitle.getPtitleById(match.keyword.getPtitleId());
			TagWrap tag = TagWrap.getWrap(Tag.getTagById(match.keyword.getTagId()), match.score);
			searchResults.add(SearchResultWrap.getSearchResult(product, ptitle, tag, match.score));
		}
		return searchResults;
	}
	public static List<SearchKeyword> loadFlatTable(){
		if(keywordsList != null)
			return keywordsList;
		List<SearchKeyword> keywords = new ArrayList<SearchKeyword>();
		List<Tag> activeTags = Tag.getActiveTags(); // does not handle multiple sellers
		for(Tag tag : activeTags){
			SearchKeyword keyword = new SearchKeyword();
			Product prod = Product.getProductById(tag.getProductId());
			Ptitle ptitle = Ptitle.getPtitleById(tag.getPtitleId());
			if(prod != null && ptitle != null){
				keyword.setTagId(tag.getTagId());
				keyword.setProductId(tag.getProductId());
				keyword.setPtitleId(tag.getPtitleId());
				keyword.setProductName(prod.getName());
				keyword.setPtitleName(ptitle.getName());
				keyword.setUnit(tag.getUnitString());
				Set<Long> nodeIds = Node.getParentIds(prod.getNodeId());
				nodeIds.add(prod.getNodeId());
				String location = "";
				for(Long nodeId: nodeIds){
					Node node = Node.getNodeById(nodeId);
					if(node != null)
						location += node.getNodeName() + " ";
				}
				keyword.setLocation(location);
				Category cat = Category.getCategoryById(prod.getCategoryId());
				if(cat != null)
					keyword.setCategory(cat.getName());
				keywords.add(keyword);
			}
		}
		keywordsList = keywords;
		return keywordsList;
	}
	
	public static Comparator<SearchResultWrap> getComparator(int orderBy, boolean useDefault){
		if(Constants.OrderBy.RELEVANCE == orderBy){
			return new Comparator<SearchResultWrap>() {

				@Override
				public int compare(SearchResultWrap o1, SearchResultWrap o2) {
					if(o1.getScore() > o2.getScore())
						return 1;
					else if (o1.getScore() < o2.getScore())
						return -1;
					else{
						if(o1.getPricePerUnit() > o2.getPricePerUnit())
							return 1;
						else if (o1.getPricePerUnit() < o2.getPricePerUnit())
							return -1;
						else
							return 0;
					}
				}
			};
		}else if(Constants.OrderBy.RATING == orderBy){
			return new Comparator<SearchResultWrap>() {

				@Override
				public int compare(SearchResultWrap o1, SearchResultWrap o2) {
					if(o1.getRating() > o2.getRating())
						return 1;
					else if (o1.getRating() < o2.getRating())
						return -1;
					else{
						if(o1.getPricePerUnit() > o2.getPricePerUnit())
							return 1;
						else if (o1.getPricePerUnit() < o2.getPricePerUnit())
							return -1;
						else
							return 0;
					}
				}
			};
		}else if(Constants.OrderBy.FRESHNESS == orderBy){
			return new Comparator<SearchResultWrap>() {

				@Override
				public int compare(SearchResultWrap o1, SearchResultWrap o2) {
					if(o1.getDateAdded().after(o2.getDateAdded()))
						return 1;
					else if (o2.getDateAdded().after(o1.getDateAdded()))
						return -1;
					else{
						if(o1.getPricePerUnit() > o2.getPricePerUnit())
							return 1;
						else if (o1.getPricePerUnit() < o2.getPricePerUnit())
							return -1;
						else
							return 0;
					}
				}
			};
		}else if(Constants.OrderBy.PRICE_H2L == orderBy){
			return new Comparator<SearchResultWrap>() {

				@Override
				public int compare(SearchResultWrap o1, SearchResultWrap o2) {
					if(o1.getPricePerUnit() < o2.getPricePerUnit())
						return 1;
					else if (o1.getPricePerUnit() > o2.getPricePerUnit())
						return -1;
					else
						return 0;
				}
			};
		}else if(Constants.OrderBy.PRICE_L2H == orderBy || useDefault){
			return new Comparator<SearchResultWrap>() {

				@Override
				public int compare(SearchResultWrap o1, SearchResultWrap o2) {
					if(o1.getPricePerUnit() > o2.getPricePerUnit())
						return 1;
					else if (o1.getPricePerUnit() < o2.getPricePerUnit())
						return -1;
					else
						return 0;
				}
			};
		}
		return null;
	}
}
