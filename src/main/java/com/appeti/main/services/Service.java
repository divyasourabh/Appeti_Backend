package com.appeti.main.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.appeti.database.table.aggregation.BaseCtr;
import com.appeti.database.table.node.Node;
import com.appeti.database.table.node.wrap.NodeTree;
import com.appeti.database.table.order.Order;
import com.appeti.database.table.order.wrap.OrderViewWrap;
import com.appeti.database.table.product.Category;
import com.appeti.database.table.product.CategoryTree;
import com.appeti.database.table.product.Product;
import com.appeti.database.table.product.ProductMinWrap;
import com.appeti.database.table.product.Ptitle;
import com.appeti.database.table.product.Tag;
import com.appeti.database.table.product.wrap.ProductWrap;
import com.appeti.database.table.user.User;
import com.appeti.utils.ExceptionUtils;
import com.appeti.utils.Property;

public class Service {
	protected static final Logger log = Logger.getLogger(Service.class);
	protected static final String DELIMITER = ",";
	private static NodeTree nodeTree = null;
	private static CategoryTree categoryTree = null;
	
	public Properties prop;
	
	public Service(HttpServletRequest request){
		this.prop = new Properties();
		InputStream inputStream = request.getSession().getServletContext().getResourceAsStream("/resources/instance.properties");
		try{
			this.prop.load(inputStream);
		}catch(IOException e)
		{
			ExceptionUtils.logException(e);
		}
	}
	
	public NodeTree getNodeTree(int level){
		if(nodeTree != null)
			return nodeTree;
		long root = Property.DEFAULT_ROOT_NODE;
		try{
			root = Long.valueOf(prop.getProperty(Property.ROOT_NODE));
		}catch(Exception e){}
		nodeTree = NodeTree.prepareNodeTree(root, level);
		return nodeTree;
	}
	
	public NodeTree getNodeTree(){
		return getNodeTree(Property.MAX_LEVEL_NODES);
	}
	
	public CategoryTree getCategoryTree(int level, boolean sendProducts){
		if(categoryTree != null)
			return categoryTree;
		long root = Property.DEFAULT_ROOT_CATEGORY;
		try{
			root = Long.valueOf(prop.getProperty(Property.ROOT_CATEGORY));
		}catch(Exception e){}
		categoryTree = CategoryTree.prepareCategoryTree(root, level, true); // set true if its to be created one time or CACHE it
		return categoryTree;
	}
	
	public CategoryTree getCategoryTree(int level){
		return getCategoryTree(level, true);
	}
	
	public CategoryTree getCategoryTree(){
		return getCategoryTree(Property.MAX_LEVEL_CATEGORIES, true);
	}
	
	public String getContactNumber(){
		return prop.getProperty(Property.CONTACT_PHONE_NUMBER,Property.DEFAULT_PHONE_NUMBER);
	}
	
	public String getContactEmail(){
		return prop.getProperty(Property.CONTACT_EMAIL,Property.DEFAULT_EMAIL);
	}
	
	public List<ProductWrap> getPopularProducts(){
		List<Long> topProducts = BaseCtr.getTopNProductIds(Property.DEFAULT_NUMBER_OF_POPULAR_PRODUCTS);
		List<ProductWrap> list = new LinkedList<ProductWrap>();
		for(long pid : topProducts){
			if(Tag.getValidTagsByProductId(pid).size() >0)
				list.add(ProductWrap.getWrap(Product.getProductById(pid)));
		}
		return list;
	}
	
	public List<ProductWrap> getPopularPtitles(){
		List<Long> topProducts = BaseCtr.getTopNProductIds(Property.DEFAULT_NUMBER_OF_POPULAR_PRODUCTS);
		List<ProductWrap> list = new ArrayList<ProductWrap>();
		for(long pid : topProducts){
			List<Ptitle> ptm=Ptitle.getPtitlesByProductId(pid);
			List<ProductWrap> tmp = ProductWrap.getWrapList(ptm);
			list.addAll(tmp);
		}
		long seed = System.nanoTime();
		Collections.shuffle(list, new Random(seed));
		return list;
	}
	
	private List<ProductWrap> getProducts(String property){
		List<ProductWrap> list = new ArrayList<ProductWrap>();
		String prodStr = prop.getProperty(property);
		String[] productIds = prodStr.split(DELIMITER); 
		for(String idStr : productIds){
			long id = -1;
			try{
				id = Long.valueOf(idStr);
				Product prod = Product.getProductById(id);
				if(prod != null && Tag.getValidTagsByProductId(id).size() >0)
					list.add(ProductWrap.getWrap(prod));
			}catch(Exception e){
				ExceptionUtils.logException(e, idStr);
			}
		}
		return list;
	}
	
	public  List<ProductWrap> getSeasonalProducts(){
		return getProducts(Property.SEASONAL_PRODUCTS);
	}
	
	public  List<ProductWrap> getNewProducts(){
		List<ProductWrap> list = new ArrayList<ProductWrap>();
		int limit = Property.DEFAULT_NEW_PRODUCTS_COUNT;
		try{
			limit = Integer.valueOf(prop.getProperty(Property.NEW_PRODUCTS_COUNT));
		}catch(Exception e){};
		List<Product> products = Product.getLatestProducts();
		for(Product prod : products){
			ProductWrap wrap = ProductWrap.getWrap(prod);
			if(wrap != null && Tag.getValidTagsByProductId(prod.getProductId()).size() >0)
				list.add(wrap);
		}
		return list.subList(0, limit < list.size() ? limit : list.size());
	}
	
	
	public static List<ProductMinWrap> getCategoryProducts(long categoryId, int lowLimit, int highLimit){
		List<ProductMinWrap> list = new ArrayList<ProductMinWrap>();
		List<Product> products = Product.getProducts(null, Category.getAllSubCategoryIds(categoryId));
		if(products != null){
			for(Product prod : products){
				if(Tag.getValidTagsByProductId(prod.getProductId()).size() <= 0)
					continue;
				ProductMinWrap wrap = ProductMinWrap.getWrap(prod);
				if(wrap != null)
					list.add(ProductMinWrap.getWrap(prod));
			}
		}
		if(lowLimit < 1 || lowLimit > list.size())
			lowLimit = 1;
		if(highLimit < 1 || highLimit > list.size())
			highLimit = list.size();
		return list.subList(lowLimit -1, highLimit);
	}
	
	
	
	public static Map<Long, String> createNodeMap(long nodeId){
		return createNodeMap(nodeId, false);
	}
	
	public static Map<Long, String> createNodeMap(long nodeId, boolean reverse){
		Map<Long,String> nodeMap = new LinkedHashMap<Long, String>();
		Node node = Node.getNodeById(nodeId);
		while(node != null && node.getParentNodeId() != -1){ // not null and not root
			nodeMap.put(node.getNodeId(), node.getNodeName());
			node = Node.getNodeById(node.getParentNodeId());
		}
		if(reverse){
			Map<Long,String> reverseNodeMap = new LinkedHashMap<Long, String>();
			List<Long> keyList = new ArrayList<Long>(nodeMap.keySet());
			Collections.reverse(keyList);
			for(Long key : keyList){
				reverseNodeMap.put(key, nodeMap.get(key));
			}
			nodeMap = reverseNodeMap;
		}
		return nodeMap;
	}
	
	public static Map<Long, String> createCategoryMap(long categoryId){
		return createCategoryMap(categoryId, false);
	}
	
	public static Map<Long, String> createCategoryMap(long categoryId, boolean reverse){
		Map<Long,String> catMap = new LinkedHashMap<Long, String>();
		Category cat = Category.getCategoryById(categoryId);
		while(cat != null && cat.getParentId() != -1){ // not null and not root
			catMap.put(cat.getCategoryId(), cat.getName());
			cat = Category.getCategoryById(cat.getParentId());
		}
		if(reverse){
			Map<Long,String> reverseNodeMap = new LinkedHashMap<Long, String>();
			List<Long> keyList = new ArrayList<Long>(catMap.keySet());
			Collections.reverse(keyList);
			for(Long key : keyList){
				reverseNodeMap.put(key, catMap.get(key));
			}
			catMap = reverseNodeMap;
		}
		return catMap;
	}
	
	public static OrderViewWrap viewOrder(long userId, long orderId){
		Order order = Order.getOrderById(userId, orderId);
		return OrderViewWrap.getWrap(order);
	}

	public static OrderViewWrap viewOrder(Order order){
		return OrderViewWrap.getWrap(order);
	}
	
	public static void setUser(HttpServletRequest request, User user){
		request.getSession(true).setAttribute("user",user);
		request.getSession(true).setAttribute("userId",user.getUserId());
	}
}
