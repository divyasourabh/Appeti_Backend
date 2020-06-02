package com.appeti.main.beans;

import java.util.List;
import java.util.Map;

import com.appeti.database.table.product.wrap.SearchResultWrap;

public class SearchBean {
	List<SearchResultWrap> results;
	Map<Long,String> nodeMap;
	long resultCount;
	long nodeId = -1l;
	long categoryId = -1l;
	long productId = -1l;
	int orderBy = 1;
	int lowLimit = -1;
	int highLimit = -1;
	String query;
	
	public List<SearchResultWrap> getResults() {
		return results;
	}

	public void setResults(List<SearchResultWrap> results) {
		this.results = results;
	}

	public Map<Long, String> getNodeMap() {
		return nodeMap;
	}

	public void setNodeMap(Map<Long, String> nodeMap) {
		this.nodeMap = nodeMap;
	}

	public long getResultCount() {
		return resultCount;
	}

	public void setResultCount(long resultCount) {
		this.resultCount = resultCount;
	}

	public long getNodeId() {
		return nodeId;
	}

	public void setNodeId(long nodeId) {
		this.nodeId = nodeId;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public int getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(int orderBy) {
		this.orderBy = orderBy;
	}

	public int getLowLimit() {
		return lowLimit;
	}

	public void setLowLimit(int lowLimit) {
		this.lowLimit = lowLimit;
	}

	public int getHighLimit() {
		return highLimit;
	}

	public void setHighLimit(int highLimit) {
		this.highLimit = highLimit;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}
}
