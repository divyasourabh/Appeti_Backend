package com.appeti.main.management;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appeti.database.table.product.wrap.SearchResultWrap;
import com.appeti.main.beans.SearchBean;
import com.appeti.main.services.SearchService;
import com.appeti.utils.ExceptionUtils;

public class ShopManagement extends Management{

private static final String DEAFAULT_SEARCH_BEAN = "searchBean";
	
	public List<SearchResultWrap> setSearchPage(HttpServletRequest request, HttpServletResponse response){
		return setNodeCategoryProductSearch(request, response, null, null, null);
	}
	
	public List<SearchResultWrap> setNodeSearch(HttpServletRequest request, HttpServletResponse response, String nodeIdStr){
		return setNodeCategoryProductSearch(request, response, nodeIdStr, null, null);
	}
	
	public List<SearchResultWrap> setCategorySearch(HttpServletRequest request, HttpServletResponse response, String categoryIdStr){
		return setNodeCategoryProductSearch(request, response, null, categoryIdStr, null);
	}
	
	public List<SearchResultWrap> setProductSearch(HttpServletRequest request, HttpServletResponse response, String productIdStr){
		return setNodeCategoryProductSearch(request, response, null, null, productIdStr);
	}
	
	public List<SearchResultWrap> setNodeCategorySearch(HttpServletRequest request, HttpServletResponse response, String nodeIdStr, String categoryIdStr){
		return setNodeCategoryProductSearch(request, response, nodeIdStr, categoryIdStr, null);
	}
	
	public List<SearchResultWrap> setProductCategorySearch(HttpServletRequest request, HttpServletResponse response, String categoryIdStr, String productIdStr){
		return setNodeCategoryProductSearch(request, response, null, categoryIdStr, productIdStr);
		
	}
	
	public List<SearchResultWrap> setNodeCategoryProductSearch(HttpServletRequest request, HttpServletResponse response, String nodeIdStr, String categoryIdStr, String productIdStr){
		SearchBean bean = new SearchBean();
		SearchService service = new SearchService(request);
		try{
			bean.setNodeId(nodeIdStr != null ? Long.valueOf(nodeIdStr) : -1);
			bean.setCategoryId(categoryIdStr != null ? Long.valueOf(categoryIdStr) : -1);
			bean.setProductId(productIdStr != null ? Long.valueOf(productIdStr) : -1);
			try{
				bean.setOrderBy(request.getParameter("orderBy") != null ? Integer.valueOf(request.getParameter("orderBy")) : 1);
				bean.setLowLimit(request.getParameter("low_limit") != null ? Integer.valueOf(request.getParameter("low_limit")) : -1);
				bean.setHighLimit(request.getParameter("high_limit") != null ? Integer.valueOf(request.getParameter("high_limit")) : -1);
			}catch(Exception e){
				ExceptionUtils.logException(e, log);
			}
		}catch(Exception e){
			ExceptionUtils.logException(e, log);
		}
		List<SearchResultWrap> list = nodeCategorySearch(service, bean);
		bean.setResults(list);
		bean.setNodeMap(SearchService.createNodeMap(bean.getNodeId(), true));
		request.setAttribute(DEAFAULT_SEARCH_BEAN, bean);
		prepareHeaderBean(request, response);
		log.info("node: " + nodeIdStr + " category: " + categoryIdStr + " results->" + list.size());
		return list;
	}
	
	public List<SearchResultWrap> nodeCategorySearch(SearchService service, SearchBean bean){
		return SearchService.getNodeCategorySearchResults(bean.getNodeId(), bean.getCategoryId(),bean.getProductId(), bean.getOrderBy(), bean.getLowLimit(), bean.getHighLimit());
	}
	
	public List<SearchResultWrap> setQuerySearch(HttpServletRequest request, HttpServletResponse response){
		String place = request.getParameter("place");
		String query = request.getParameter("item");
		query = query == null ? request.getParameter("query") : query;
		SearchBean bean = new SearchBean();
		try{
			try{
				bean.setOrderBy(request.getParameter("orderBy") != null ? Integer.valueOf(request.getParameter("orderBy")) : -1);
				bean.setLowLimit(request.getParameter("low_limit") != null ? Integer.valueOf(request.getParameter("low_limit")) : -1);
				bean.setHighLimit(request.getParameter("high_limit") != null ? Integer.valueOf(request.getParameter("high_limit")) : -1);
			}catch(Exception e){
				ExceptionUtils.logException(e, log);
			}
		}catch(Exception e){
			ExceptionUtils.logException(e, log);
		}
		List<SearchResultWrap> list = SearchService.getQuerySearchResults(query, bean.getOrderBy(), bean.getLowLimit(), bean.getHighLimit());
		if(list == null)
			return setNodeCategoryProductSearch(request, response, null, null, null);
		bean.setQuery(query);
		bean.setResults(list);
		request.setAttribute(DEAFAULT_SEARCH_BEAN, bean);
		prepareHeaderBean(request, response);
		log.info("query: " + query + " results->" + list.size());
		return list;
	}
}
