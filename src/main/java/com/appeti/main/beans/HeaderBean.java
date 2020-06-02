package com.appeti.main.beans;

import com.appeti.database.table.node.wrap.NodeTree;
import com.appeti.database.table.product.CategoryTree;

public class HeaderBean {
	NodeTree nodeTree;
	CategoryTree categoryTree;
	String phoneNumber;
	String email;
	
	public NodeTree getNodeTree() {
		return nodeTree;
	}
	public void setNodeTree(NodeTree nodeTree) {
		this.nodeTree = nodeTree;
	}
	public CategoryTree getCategoryTree() {
		return categoryTree;
	}
	public void setCategoryTree(CategoryTree categoryTree) {
		this.categoryTree = categoryTree;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
