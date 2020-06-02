package com.appeti.database.table.product;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.appeti.database.utils.Image;
import com.appeti.main.services.SearchService;

public class CategoryTree {
	private long categoryId;
	private String name;
	private Image image;
	private List<Image> images = new ArrayList<Image>();
	private List<CategoryTree> childCategories = null;
	private List<ProductMinWrap> products = null;
	
	private static final int MAX_PRODUCTS = 5;
	private static final Logger logger = Logger.getLogger(CategoryTree.class);
	
	public static CategoryTree prepareCategoryTree(long categoryId, int level, boolean sendProducts){
		if(level < 0)
			return null;
		Category cat = Category.getCategoryById(categoryId);
		if(cat == null)
			return null;
		CategoryTree treeNode = new CategoryTree();
		treeNode.setCategoryId(cat.getCategoryId());
		treeNode.setName(cat.getName());
		treeNode.setImage(CategoryImage.getImageByCategoryId(cat.getCategoryId()));
		if(sendProducts);
		treeNode.products = SearchService.getCategoryProducts(categoryId, 1, MAX_PRODUCTS);
		if(level == 0 && (treeNode.products == null || treeNode.products.size() <=0)) // don't show categories with no tags
			return null;
		Set<Long> children = Category.getImediateSubCategoryIds(cat.getCategoryId());
		Iterator<Long> iterator = children.iterator();
		while(iterator.hasNext()){
			CategoryTree childTreeNode = prepareCategoryTree(iterator.next(), level-1, sendProducts);
			if(childTreeNode != null){
				if(treeNode.getChildCategories() == null)
					treeNode.setChildCategories(new ArrayList<CategoryTree>());
				
				treeNode.getChildCategories().add(childTreeNode);
			}
		}
		return treeNode;
	}
	
	public static CategoryTree prepareCategoryTree(long categoryId, int level){
		return prepareCategoryTree(categoryId, level, true);
	}
	
	public long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<CategoryTree> getChildCategories() {
		return childCategories;
	}
	public void setChildCategories(List<CategoryTree> childCategories) {
		this.childCategories = childCategories;
	}

	public List<ProductMinWrap> getProducts() {
		return products;
	}

	public void setProducts(List<ProductMinWrap> products) {
		this.products = products;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image images) {
		this.image = images;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}
}
