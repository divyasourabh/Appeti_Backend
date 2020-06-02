package com.appeti.main;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appeti.database.table.order.wrap.OrderWrap;
import com.appeti.database.table.payment.MidTrans;
import com.appeti.database.table.product.Product;
import com.appeti.database.table.product.Ptitle;
import com.appeti.database.table.product.Tag;
import com.appeti.database.table.product.wrap.ProductPageWrap;
import com.appeti.database.table.product.wrap.SearchResultWrap;
import com.appeti.database.table.product.wrap.TagWrap;
import com.appeti.main.management.AccountManagement;
import com.appeti.main.management.AdminManagement;
import com.appeti.main.management.CartManagement;
import com.appeti.main.management.CheckoutManagement;
import com.appeti.main.management.HomeManagement;
import com.appeti.main.management.Management;
import com.appeti.main.management.MarketingManagement;
import com.appeti.main.management.ProductManagement;
import com.appeti.main.management.ShopManagement;
import com.appeti.main.services.Service;
import com.appeti.payment.CCAvenue;
import com.appeti.utils.BooleanWithReason;
import com.appeti.utils.Constants;
import com.appeti.utils.Constants.ClickSaleSource;
import com.appeti.utils.ExceptionUtils;
import com.appeti.utils.JSONListWrapper;

/**
 * Handles requests for the application home page.
 */
@org.springframework.stereotype.Controller
public class Controller {
	
	private static final Logger logger = LoggerFactory.getLogger(Controller.class);
	private Management mgt = new Management();
	private HomeManagement homeMgt = new HomeManagement();
	private ShopManagement shopMgt = new ShopManagement();
	private ProductManagement prodMgt = new ProductManagement();
	private CartManagement cartMgt = new CartManagement();
	private CheckoutManagement checkoutMgt = new CheckoutManagement();
	private AccountManagement accMgt = new AccountManagement();
	private MarketingManagement mktMgt = new MarketingManagement();
	
	private AdminManagement adminMgt = new AdminManagement();
	
	private static final String STATUS_MESSAGE = "status_message";
	private static final String STATUS = "status";
	private static final String IS_APP = "is_app";
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpServletRequest request,HttpServletResponse response) {
		logger.info("home");
		homeMgt.setHomePage(request, response);
		return "home";
	}
	
	// ---------------------------- SHOP -----------------------------//
	
	@RequestMapping(value = "/shop", method = RequestMethod.GET)
	public String shop(HttpServletRequest request,HttpServletResponse response) {
		logger.info("shop");
		shopMgt.setSearchPage(request, response);
		return "shop";
	}
										
	@RequestMapping(value = "/shop/search", method = RequestMethod.GET)
	public String querySearch(HttpServletRequest request,HttpServletResponse response){
		logger.info("query search");
		shopMgt.setQuerySearch(request, response);
		return "shop";
	}

	@RequestMapping(value = "/shop/category/*/{categoryId}/search", method = RequestMethod.GET)
	public String categoryQuerySearch(HttpServletRequest request,HttpServletResponse response){
		logger.info(" category query search");
		shopMgt.setQuerySearch(request, response);
		return "shop";
	}
	
	@RequestMapping(value = "/shop/node/{nodeId}/*", method = RequestMethod.GET)
	public String nodeSearchGet(@PathVariable("nodeId") String nodeId,HttpServletRequest request,HttpServletResponse response){
		logger.info("node search");
		shopMgt.setNodeSearch(request,response,nodeId);
		return "shop";
	}
	
	@RequestMapping(value = "/shop/category/{categoryId}/*", method = RequestMethod.GET)
	public String categorySearch(@PathVariable("categoryId") String categoryId,HttpServletRequest request,HttpServletResponse response){
		logger.info("category search");
		shopMgt.setCategorySearch(request,response,categoryId);
		return "shop";
	}
	
	@RequestMapping(value = "/shop/node/{nodeId}/category/{categoryId}/*", method = RequestMethod.GET)
	public String nodeCategorySearch(@PathVariable("nodeId") String nodeId,@PathVariable("categoryId") String categoryId,HttpServletRequest request,HttpServletResponse response){
		logger.info("node category search");
		shopMgt.setNodeCategorySearch(request,response,nodeId, categoryId);
		return "shop";
	}
	
	@RequestMapping(value = "/shop/category/{categoryId}/product/{productId}/*", method = RequestMethod.GET)
	public String productCategorySearch(@PathVariable("categoryId") String categoryId,@PathVariable("productId") String productId,HttpServletRequest request,HttpServletResponse response){
		logger.info("category product search");
		shopMgt.setProductCategorySearch(request,response,categoryId, productId);
		return "shop";
	}
	
	@RequestMapping(value = "/shop/product/{productId}/*", method = RequestMethod.GET)
	public String productSearch(@PathVariable("productId") String productId,HttpServletRequest request,HttpServletResponse response){
		logger.info("product search");
		shopMgt.setProductSearch(request,response,productId);
		return "shop";
	}
	
	// ------------------------------- PRODUCT ---------------------------------//
	
	@RequestMapping(value = "/product/ptitleSelection", method = RequestMethod.GET)
	public String ptitleSelection(HttpServletRequest request,HttpServletResponse response){
		String productId = request.getParameter("productId") != null ? request.getParameter("productId") : "";
		String productName = request.getParameter("productName") != null ? request.getParameter("productName") : "";
		String type = request.getParameter("type") != null ? request.getParameter("type") : "";
		String ptitleId = "";
		String text = "";
		if(StringUtils.isNotBlank(type)){
			text = type.split("-").length > 0 ? (productName + "-" + type.split("-")[0]).toLowerCase().replaceAll(" ", "-") : "";
			ptitleId = type.split("-").length > 1 ? type.split("-")[1] : "";
		}
		String productPtitleTagIdStr = productId + "-" + ptitleId;
		return "redirect:/product/" + productPtitleTagIdStr + "/" + text;
	}

	@RequestMapping(value = "/product/{productPtitleTagIdStr}/*", method = RequestMethod.GET)
	public String product(@PathVariable("productPtitleTagIdStr") String productPtitleTagIdStr,HttpServletRequest request,HttpServletResponse response){
		logger.info("product page");
		if(prodMgt.setProductPage(request, response, productPtitleTagIdStr) != null)
			return "product";
		else
			return "redirect:/page-not-found";
	}
	
	// ------------------------------- REVIEW ----------------------------------//
	
	@RequestMapping(value = "/product/add-review", method = RequestMethod.POST)
	@ResponseBody
	public String postReview(HttpServletRequest request,HttpServletResponse response){
		logger.info("add-review");
		return new JSONObject(prodMgt.addReviewWeb(request, response)).toString();
	}
	
	// ------------------------------- LOGIN -----------------------------------//
	
	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public String login(HttpServletRequest request,HttpServletResponse response){
		logger.info("signin form");
		if(homeMgt.isLoggedIn(request)){
			return redirectIfAny(request, "shop", null);
		}
		BooleanWithReason result = homeMgt.login(request,response);
		if(result.getStatus()){
			return redirectIfAny(request, "shop", null);
		}else{
			request.setAttribute("redirectUrl", getRedirect(request));
			request.setAttribute(STATUS_MESSAGE, result.getMessage());
		}
		request.setAttribute("showTopSearch", false);
		return "signin";
	}
	
	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	public String signin(HttpServletRequest request,HttpServletResponse response){
		logger.info("signin");
		if(homeMgt.isLoggedIn(request)){
			return redirectIfAny(request, "shop", null);
		}
		else{
			homeMgt.prepareHeaderBean(request, response);
			request.setAttribute("redirectUrl", getRedirect(request));
			request.setAttribute("showTopSearch", false);
			return "signin";
		}
	}
	
	@RequestMapping(value = "/socialLogin", method = RequestMethod.POST)
	@ResponseBody
	public String fbloginPost(HttpServletRequest request,HttpServletResponse response){
		logger.info("social login post form");
		return new JSONObject(homeMgt.sociallogin(request,response,ClickSaleSource.WEB)).toString();		
	}
	
	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request,HttpServletResponse response) {
		logger.info("logout");
		if(request.getSession() != null){
			request.getSession().invalidate();
		}
		return redirectIfAny(request, "", null);
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register(HttpServletRequest request,HttpServletResponse response){
		logger.info("register");
		if(homeMgt.isLoggedIn(request)){
			return redirectIfAny(request, "shop", null);
		}
		else{
			homeMgt.prepareHeaderBean(request, response);
			request.setAttribute("redirectUrl", getRedirect(request));
			request.setAttribute("showTopSearch", false);
			return "register";
		}
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerPost(HttpServletRequest request,HttpServletResponse response){
		logger.info("register form");
		request.setAttribute(STATUS,homeMgt.signUp(request,response));
		request.setAttribute("showTopSearch", false);
		return "register";
	}
	
	@RequestMapping(value = "/page-not-found", method = RequestMethod.GET)
	public String pageNotFound(HttpServletRequest request,HttpServletResponse response){
		logger.info("404 page");
		mgt.prepareHeaderBean(request, response);
		return "pageNotFound";
	}
	
	@RequestMapping(value = "/verifyEmail")
	public String verifyEmail(HttpServletRequest request,HttpServletResponse response) {
		mgt.prepareHeaderBean(request, response);
		request.setAttribute(STATUS,homeMgt.verifyEmail(request,response));
		return "verifyEmail"; 
	}
	
	@RequestMapping(value = "/my-account/lost-password")
	public String forgotPassword(HttpServletRequest request,HttpServletResponse response){
		logger.info("forgotPassword");
		mgt.prepareHeaderBean(request, response);
		return "forgotPassword";
	}

	@RequestMapping(value = "/my-account/lost-password", method = RequestMethod.POST)
	public String forgotPasswordForm(HttpServletRequest request,HttpServletResponse response){
		logger.info("forgotPassword");
		mgt.prepareHeaderBean(request, response);
		request.setAttribute("result", homeMgt.forgotPassword(request,response));
		return "forgotPassword";
	}

	// -------------------------------- CART ------------------------------------//
	
	@RequestMapping(value = "/cart", method = RequestMethod.GET)
	public String viewCart(HttpServletRequest request,HttpServletResponse response){
		logger.info("view cart");
		cartMgt.viewCart(request, response);
		return "cart";
	}

	@RequestMapping(value = "/cart", method = RequestMethod.POST)
	public String viewCartPost(HttpServletRequest request,HttpServletResponse response){
		logger.info("view cart");
		cartMgt.viewCart(request, response);
		return "cart";
	}

	@RequestMapping(value = "/cart/add", method = RequestMethod.POST)
	@ResponseBody
	public String addToCart(HttpServletRequest request,HttpServletResponse response){
		logger.info("add to cart");
		return new JSONObject(cartMgt.addToCart(request, response)).toString();
	}
	
	@RequestMapping(value = "/cart/remove-item", method = RequestMethod.POST)
	@ResponseBody
	public String removeFromCart(HttpServletRequest request,HttpServletResponse response){
		logger.info("remove from cart");
		return new JSONObject(cartMgt.removeFromCart(request, response)).toString();
	}
	
	@RequestMapping(value = "/cart/validate-coupon", method = RequestMethod.POST)
	@ResponseBody
	public String isValidCoupon(HttpServletRequest request,HttpServletResponse response){
		logger.info("validate coupon");
		return new JSONObject(cartMgt.validateCoupon(request, response)).toString();
	}
	
	@RequestMapping(value = "/cart/apply-coupon", method = RequestMethod.POST)
	public String applyCoupon(HttpServletRequest request,HttpServletResponse response){
		logger.info("apply coupon");
		cartMgt.applyCoupon(request, response);
		return "redirect:/cart";
	}
	
	@RequestMapping(value = "/cart/update", method = RequestMethod.POST)
	public String updateCart(HttpServletRequest request,HttpServletResponse response){
		logger.info("update cart");
		cartMgt.updateCart(request, response);
		return "redirect:/cart";
	}
	
	// -------------------------------- CHECKOUT -------------------------------------- //
	
	@RequestMapping(value = "/checkout", method = RequestMethod.GET)
	public String checkout(HttpServletRequest request,HttpServletResponse response){
		logger.info("checkout cart");
		if(!checkoutMgt.isLoggedIn(request)){
			return "redirect:/signin?redirectUrl=checkout";
		}
		checkoutMgt.prepareCheckoutPage(request, response);
		return "checkout";
		
	}
	
	@RequestMapping(value = "/checkout", method = RequestMethod.POST)
	public String checkoutSubmit(HttpServletRequest request,HttpServletResponse response){
		logger.info("checkout post");
		if(!homeMgt.isLoggedIn(request)){
			return "redirect:/signin?redirectUrl=checkout";
		}
		BooleanWithReason result = checkoutMgt.checkout(request, response);
		if(result.getStatus())
			return "paymentBlockUI";
		else
			return "redirect:/checkout";
	}
	
	@RequestMapping(value = "/checkout/cc-avenue-order-response", method = RequestMethod.POST)
	public String ccAvenuePaymentResponse(HttpServletRequest request,HttpServletResponse response){
		logger.info("order-response");
		BooleanWithReason result = checkoutMgt.paymentResponse(request, response, Constants.Source.CC_AVENUE, Constants.ClickSaleSource.WEB);
		if(result.getStatus())
			return "orderConfirmation";
		else
			return "redirect:/cart?code=" + Constants.PaymentResponse.FAILURE;
	}
	
	@RequestMapping(value = "/checkout/cc-avenue-order-response", method = RequestMethod.GET)
	public String ccAvenuePaymentResponseGET(HttpServletRequest request,HttpServletResponse response){
		return "redirect:/page-expired";
	}
	
	@RequestMapping(value = "/checkout/payu-order-response", method = RequestMethod.POST)
	public String payuPaymentResponse(HttpServletRequest request,HttpServletResponse response){
		logger.info("order-response");
		BooleanWithReason result = checkoutMgt.paymentResponse(request, response, Constants.Source.PAY_U, Constants.ClickSaleSource.WEB);
		if(result.getStatus())
			return "orderConfirmation";
		else
			return "redirect:/cart";
	}

	@RequestMapping(value = "/vendor-sourcing-form", method = RequestMethod.GET)
	public String vendorSourcingForm(HttpServletRequest request,HttpServletResponse response){
		mgt.prepareHeaderBean(request, response);
		return "vendorSourcingForm";
	}
	
	@RequestMapping(value = "/vendor-sourcing-form", method = RequestMethod.POST)
	public String vendorSourcingFormPost(HttpServletRequest request,HttpServletResponse response){
		homeMgt.vendorSourcing(request, response);
		return "vendorSourcingForm";
	}
	
	@RequestMapping(value = "/testimonials", method = RequestMethod.GET)
	public String testimonials(HttpServletRequest request,HttpServletResponse response){
		mgt.prepareHeaderBean(request, response);
		mktMgt.prepareTestimonials(request, response);
		return "testimonials";
	}
	
	@RequestMapping(value = "/add-testimonial", method = RequestMethod.POST)
	@ResponseBody
	public String addTestimonial(HttpServletRequest request,HttpServletResponse response){
		logger.info("addTestimonial");
		BooleanWithReason result = mktMgt.addTestimonial(request, response);
		return new JSONObject(result).toString();
	}
	
	@RequestMapping(value = "/add-subscriber", method = RequestMethod.POST)
	@ResponseBody
	public String addSubscriber(HttpServletRequest request,HttpServletResponse response){
		logger.info("addSubscriber");
		BooleanWithReason result = mktMgt.addSubscriber(request, response);
		return new JSONObject(result).toString();
	}	
	
	@RequestMapping(value = "/add-suggestion", method = RequestMethod.POST)
	@ResponseBody
	public String addSuggestion(HttpServletRequest request,HttpServletResponse response){
		logger.info("addSuggestion");
		BooleanWithReason result = mktMgt.addSuggestion(request, response);
		return new JSONObject(result).toString();
	}	
	
	@RequestMapping(value = "/page-expired", method = RequestMethod.GET)
	public String pageExpired(HttpServletRequest request,HttpServletResponse response){
		mgt.prepareHeaderBean(request, response);
		return "pageExpired";
	}
	
	@RequestMapping(value = "/track-order", method = RequestMethod.GET)
	public String trackOrder(HttpServletRequest request,HttpServletResponse response){
		mgt.prepareHeaderBean(request, response);
		return "trackOrder";
	}
	
	@RequestMapping(value = "/track-order", method = RequestMethod.POST)
	public String trackOrderFormPost(HttpServletRequest request,HttpServletResponse response){
		homeMgt.trackOrder(request, response);
		return "trackOrder";
	}
	
	@RequestMapping(value = "/latest-offers", method = RequestMethod.GET)
	public String offers(HttpServletRequest request,HttpServletResponse response){
		homeMgt.offers(request, response);
		return "offers";
	}
	// << ----------------------------- ACCOUNT --------------------------------------->> //
	
	@RequestMapping(value = "/my-account", method = RequestMethod.GET)
	public String myaccount(HttpServletRequest request,HttpServletResponse response){
		logger.info("view-order");
		if(!accMgt.isLoggedIn(request)){
			return "redirect:/signin?redirectUrl=my-account";
		}
		accMgt.setAccountPage(request, response);
		return "myAccount";
	}

	@RequestMapping(value = "/my-account/view-order/{orderId}", method = RequestMethod.GET)
	public String viewOrder(@PathVariable("orderId") String orderId,HttpServletRequest request,HttpServletResponse response){
		logger.info("view-order");
		if(!accMgt.isLoggedIn(request)){
			return "redirect:/signin?redirectUrl=my-account";
		}
		accMgt.viewOrder(request, response, orderId);
		return "viewOrder";
	}

	@RequestMapping(value = "/my-account/edit-account", method = RequestMethod.GET)
	public String myaccountEdit(HttpServletRequest request,HttpServletResponse response){
		logger.info("editAccount");
		if(!accMgt.isLoggedIn(request)){
			return "redirect:/signin?redirectUrl="+ URLEncoder.encode("my-account/edit-account");
		}
		accMgt.setEditAccountPage(request, response);
		return "editAccount";
	}
	
	@RequestMapping(value = "/my-account/edit-account", method = RequestMethod.POST)
	public String myaccountEditPost(HttpServletRequest request,HttpServletResponse response){
		logger.info("editAccount form submit");
		if(!accMgt.isLoggedIn(request)){
			return "redirect:/signin?redirectUrl=" + URLEncoder.encode("my-account/edit-account");
		}
		accMgt.editAccountWeb(request, response);
		return "editAccount";
	}
	
	// <<-------------------------- SEARCH SERVICES ---------------------------------->> //
	
	
	@RequestMapping(value = "/defaultNodeService", method = RequestMethod.POST)
	@ResponseBody
	public String defaultNodeService(HttpServletRequest request,HttpServletResponse response){
		logger.info("defaultNodeService");
		return new JSONObject(new JSONListWrapper(homeMgt.getNLevelNodes(request, response, 2))).toString();
	}
	
	@RequestMapping(value = "/defaultCategoryService", method = RequestMethod.POST)
	@ResponseBody
	public String defaultCategoryService(HttpServletRequest request,HttpServletResponse response){
		logger.info("defaultCategoryService");
		return new JSONObject(new JSONListWrapper(homeMgt.getNLevelCategories(request, response, 1, true))).toString();
	}
	
	@RequestMapping(value = "/shopService", method = RequestMethod.POST)
	@ResponseBody
	public String shopService(HttpServletRequest request,HttpServletResponse response) {
		logger.info("shop service");
		return new JSONObject(new JSONListWrapper(shopMgt.setNodeCategorySearch(request, response, request.getParameter("nodeId"), request.getParameter("categoryId")))).toString();
	}
	
	@RequestMapping(value = "/searchResult", method = RequestMethod.POST)
	@ResponseBody
	public String searchResultForTag(HttpServletRequest request,HttpServletResponse response) {
		logger.info("shop service");
		long tagId = Long.valueOf(request.getParameter("tid"));
		Tag tag = Tag.getTagById(tagId);
		if(tag!= null)
			return new JSONObject(SearchResultWrap.getSearchResult(Product.getProductById(tag.getProductId()), Ptitle.getPtitleById(tag.getPtitleId()), TagWrap.getWrap(tag,0))).toString();
		else
			return "";
	}
	
	@RequestMapping(value = "/querySearchService", method = RequestMethod.POST)
	@ResponseBody
	public String querySearchService(HttpServletRequest request,HttpServletResponse response){
		logger.info("query search");
		return new JSONObject(new JSONListWrapper(shopMgt.setQuerySearch(request, response))).toString();
	}

	// ------------------------------ PRODUCT SERVICES ---------------------------- //
	
	@RequestMapping(value = "/product", method = RequestMethod.POST)
	@ResponseBody
	public String productService(HttpServletRequest request,HttpServletResponse response){
		logger.info("webservice product");
		ProductPageWrap prod = prodMgt.getProduct(request, response);
		if(prod != null)
			return new JSONObject(prod).toString();
		else
			return "";
	}
	
	@RequestMapping(value = "/addReviewService", method = RequestMethod.POST)
	@ResponseBody
	public String addReviewService(HttpServletRequest request,HttpServletResponse response){
		logger.info("addReviewService product");
		return new JSONObject(prodMgt.addReviewService(request, response)).toString();
	}
	
	@RequestMapping(value = "/getProductReviewsService", method = RequestMethod.POST)
	@ResponseBody
	public String getProductReviewsService(HttpServletRequest request,HttpServletResponse response){
		logger.info("getProductReviewsService");
		return new JSONObject(new JSONListWrapper(prodMgt.getReviewsService(request, response))).toString();
	}
	
	// ------------------------------ CART SERVICES ---------------------------- //
	
	@RequestMapping(value = "/cartViewService", method = RequestMethod.POST)
	@ResponseBody
	public String viewAppCart(HttpServletRequest request,HttpServletResponse response){
		logger.info("view cart service");
		return new JSONObject(cartMgt.viewAppCart(request, response)).toString();
	}
	
	@RequestMapping(value = "/cartItemAddService", method = RequestMethod.POST)
	@ResponseBody
	public String addToAppCart(HttpServletRequest request,HttpServletResponse response){
		logger.info("add to cart service");
		return new JSONObject(cartMgt.addToAppCart(request, response)).toString();
	}
	
	@RequestMapping(value = "/cartItemRemoveService", method = RequestMethod.POST)
	@ResponseBody
	public String removeFromAppCart(HttpServletRequest request,HttpServletResponse response){
		logger.info("remove from cart service");
		return new JSONObject(cartMgt.removeFromAppCart(request, response)).toString();
	}
	
	@RequestMapping(value = "/cartItemUpdateService", method = RequestMethod.POST)
	@ResponseBody
	public String updateItemAppCart(HttpServletRequest request,HttpServletResponse response){
		logger.info("update cart item service");
		return new JSONObject(cartMgt.updateAppCart(request, response)).toString();
	}
	
	@RequestMapping(value = "/apply-coupon", method = RequestMethod.POST)
	@ResponseBody
	public String applyAppCoupon(HttpServletRequest request,HttpServletResponse response){
		logger.info("apply coupon service");
		return new JSONObject(cartMgt.applyCoupon(request, response)).toString();
	}
	
	@RequestMapping(value = "/mapCartUserService", method = RequestMethod.POST)
	@ResponseBody
	public String mapCartUserService(HttpServletRequest request,HttpServletResponse response){
		logger.info("mapCartUserService");
		return new JSONObject(checkoutMgt.mapCartToUser(request, response)).toString();
	}
	
	// ------------------------------ CHECKOUT SERVICES ---------------------------- //
	
	@RequestMapping(value = "/orderViewService", method = RequestMethod.POST)
	@ResponseBody
	public String viewAppOrder(HttpServletRequest request,HttpServletResponse response){
		logger.info("view order");
		OrderWrap order = checkoutMgt.getOrder(request, response);
		return new JSONObject(order != null ? order : "").toString();
	}
	
	@RequestMapping(value = "/userAddressService", method = RequestMethod.POST)
	@ResponseBody
	public String viewUserAddress(HttpServletRequest request,HttpServletResponse response){
		logger.info("view user address");
		return new JSONObject(new JSONListWrapper(checkoutMgt.getAddressForUser(request, response))).toString();
	}
	
	@RequestMapping(value = "/mapOrderAddressService", method = RequestMethod.POST)
	@ResponseBody
	public String mapOrderAddressService(HttpServletRequest request,HttpServletResponse response){
		logger.info("mapOrderAddressService");
		return new JSONObject(checkoutMgt.mapAddress(request, response)).toString();
	}
	
	@RequestMapping(value = "/markDefaultAddressService", method = RequestMethod.POST)
	@ResponseBody
	public String markDefaultAddressService(HttpServletRequest request,HttpServletResponse response){
		logger.info("markDefaultAddressService");
		return new JSONObject(checkoutMgt.markDefaultAddress(request, response)).toString();
	}
	
	@RequestMapping(value = "/editAddressService", method = RequestMethod.POST)
	@ResponseBody
	public String editAddressService(HttpServletRequest request,HttpServletResponse response){
		logger.info("editAddressService");
		return new JSONObject(new JSONListWrapper(checkoutMgt.editAddress(request, response))).toString();
	}
	
	@RequestMapping(value = "/removeAddressService", method = RequestMethod.POST)
	@ResponseBody
	public String removeAddressService(HttpServletRequest request,HttpServletResponse response){
		logger.info("removeAddressService");
		return new JSONObject(new JSONListWrapper(checkoutMgt.removeAddress(request, response))).toString();
	}
	
	// ------------------------------- PAYMENT GATEWAY SERVICES ------------------------- //
	
	@RequestMapping(value = "/ccAvenuePaymentService", method = RequestMethod.POST)
	@ResponseBody
	public String ccAvenueKeyService(HttpServletRequest request,HttpServletResponse response){
		logger.info("ccAvenuePaymentService");
		return new JSONObject(checkoutMgt.ccAvenueKeys(request, response)).toString();
	}
	
	@RequestMapping(value = "/payuPaymentService", method = RequestMethod.POST)
	@ResponseBody
	public String payuPaymentService(HttpServletRequest request,HttpServletResponse response){
		logger.info("payuPaymentService");
		return new JSONObject(checkoutMgt.payuKeys(request, response)).toString();
	}
	
	@RequestMapping(value = "/service/cc-avenue-order-response", method = RequestMethod.POST)
	public String ccAvenuePaymentResponseService(HttpServletRequest request,HttpServletResponse response){
		logger.info("ccAvenue order-response service");
		checkoutMgt.paymentResponse(request, response, Constants.Source.CC_AVENUE, Constants.ClickSaleSource.APP);
		return "orderConfirmation";
	}
	
	@RequestMapping(value = "/service/rsakey", method = RequestMethod.POST)
	@ResponseBody
	public String ccAvenueRSAService(HttpServletRequest request,HttpServletResponse response){
		logger.info("ccAvenueRSAService service");
		String result = CCAvenue.getRSA((long)(MidTrans.createTrans(2, 2, "", 101)));
		return new JSONObject(new BooleanWithReason(true, result)).toString();
	}
	
	@RequestMapping(value = "/service/payu-order-response", method = RequestMethod.POST)
	@ResponseBody
	public String payuPaymentResponseService(HttpServletRequest request,HttpServletResponse response){
		logger.info("payu order-response service");
		BooleanWithReason result = checkoutMgt.paymentResponse(request, response, Constants.Source.PAY_U, Constants.ClickSaleSource.APP);
		return new JSONObject(result).toString();
	}
	
	// ----------------------------------- ACCOUNT SERVICES --------------------------------- //
	
	@RequestMapping(value = "/myOrdersService", method = RequestMethod.POST)
	@ResponseBody
	public String myOrdersService(HttpServletRequest request,HttpServletResponse response){
		logger.info("myOrders Service");
		return new JSONObject(new JSONListWrapper(accMgt.getPreviousOrders(request, response))).toString();
	}
	
	@RequestMapping(value = "/socialLoginService", method = RequestMethod.POST)
	@ResponseBody
	public String socialLoginService(HttpServletRequest request,HttpServletResponse response){
		logger.info("socialLogin service");
		BooleanWithReason result = homeMgt.sociallogin(request, response, Constants.ClickSaleSource.APP);
		return new JSONObject(result).toString();
	}

	@RequestMapping(value = "/loginService", method = RequestMethod.POST)
	@ResponseBody
	public String loginService(HttpServletRequest request,HttpServletResponse response){
		logger.info("login service");
		BooleanWithReason result = homeMgt.loginService(request, response);
		return new JSONObject(result).toString();
	}

	@RequestMapping(value = "/registerService", method = RequestMethod.POST)
	@ResponseBody
	public String registerService(HttpServletRequest request,HttpServletResponse response){
		logger.info("register service");
		BooleanWithReason result = homeMgt.signUpService(request, response);
		return new JSONObject(result).toString();
	}
	
	@RequestMapping(value = "/lostPasswordService", method = RequestMethod.POST)
	@ResponseBody
	public String forgotPasswordService(HttpServletRequest request,HttpServletResponse response){
		logger.info("forgotPasswordService");
		return new JSONObject(homeMgt.forgotPassword(request,response)).toString();
	}
	
	// ------------------------------- FALTU SERVICES ------------------------------- //
	
	@RequestMapping(value = "/seasonalProductsService", method = RequestMethod.POST)
	@ResponseBody
	public String seasonalProductsService(HttpServletRequest request,HttpServletResponse response){
		logger.info("seasonalProductsService");
		Service service = new Service(request);
		return new JSONObject(new JSONListWrapper(service.getSeasonalProducts())).toString();
	}
	
	@RequestMapping(value = "/newProductsService", method = RequestMethod.POST)
	@ResponseBody
	public String newProductsService(HttpServletRequest request,HttpServletResponse response){
		logger.info("newProductsService");
		Service service = new Service(request);
		return new JSONObject(new JSONListWrapper(service.getNewProducts())).toString();
	}

	@RequestMapping(value = "/recommendedProductsService", method = RequestMethod.POST)
	@ResponseBody
	public String recommendedProductsService(HttpServletRequest request,HttpServletResponse response){
		logger.info("recommendedProductsService");
		return "";
		//JSONObject(accMgt.viewOrderServ(request, response)).toString();
	}

	@RequestMapping(value = "/popularProductsService", method = RequestMethod.POST)
	@ResponseBody
	public String popularProductsService(HttpServletRequest request,HttpServletResponse response){
		logger.info("popularProductsService");
		Service service = new Service(request);
		return new JSONObject(new JSONListWrapper(service.getPopularProducts())).toString();
	}
	
	@RequestMapping(value = "/recordErrorService", method = RequestMethod.POST)
	@ResponseBody
	public void recordErrorService(HttpServletRequest request,HttpServletResponse response){
		logger.info("recordErrorService");
		if(StringUtils.isNotBlank(request.getParameter("stacktrace")))
				ExceptionUtils.logException(request.getParameter("stacktrace"));
	}

	@RequestMapping(value = "/offerService", method = RequestMethod.POST)
	@ResponseBody
	public String offersService(HttpServletRequest request,HttpServletResponse response){
		return new JSONObject(new JSONListWrapper(homeMgt.offers(request, response))).toString();
	}
	
	// ------------------------------- STATIC PAGES ---------------------------------- //
	
	@RequestMapping(value = "/about", method = RequestMethod.GET)
	public String staticAbout(HttpServletRequest request,HttpServletResponse response){
		setHeaderForStatic(request, response);
		return "about";
	}

	@RequestMapping(value = "/appet-i-team", method = RequestMethod.GET)
	public String staticTeam(HttpServletRequest request,HttpServletResponse response){
		setHeaderForStatic(request, response);
		return "team";
	}

	@RequestMapping(value = "/terms-of-service", method = RequestMethod.GET)
	public String staticTermsOfService(HttpServletRequest request,HttpServletResponse response){
		setHeaderForStatic(request, response);
		return "terms";
	}

	@RequestMapping(value = "/privacy-policy", method = RequestMethod.GET)
	public String staticPrivacyPolicy(HttpServletRequest request,HttpServletResponse response){
		setHeaderForStatic(request, response);
		return "privacyPolicy";
	}

	@RequestMapping(value = "/delivery-shipping", method = RequestMethod.GET)
	public String staticDeliveryShipping(HttpServletRequest request,HttpServletResponse response){
		setHeaderForStatic(request, response);
		return "deliveryPolicy";
	}

	@RequestMapping(value = "/refunds-cancellations", method = RequestMethod.GET)
	public String staticRefundsCancellations(HttpServletRequest request,HttpServletResponse response){
		setHeaderForStatic(request, response);
		return "refundPolicy";
	}

	@RequestMapping(value = "/we-are-hiring", method = RequestMethod.GET)
	public String staticHiring(HttpServletRequest request,HttpServletResponse response){
		setHeaderForStatic(request, response);
		return "hiring";
	}

	@RequestMapping(value = "/blogs", method = RequestMethod.GET)
	public String staticBlogse(HttpServletRequest request,HttpServletResponse response){
		setHeaderForStatic(request, response);
		return "blogs";
	}
	
	@RequestMapping(value = "/blogs/top-10-delicacies-of-bengal", method = RequestMethod.GET)
	public String staticIndianTaste(HttpServletRequest request,HttpServletResponse response){
		setHeaderForStatic(request, response);
		return "blog1";
	}
	
	@RequestMapping(value = "/blogs/bong-connection", method = RequestMethod.GET)
	public String staticIndianTaste1(HttpServletRequest request,HttpServletResponse response){
		setHeaderForStatic(request, response);
		return "blog2";
	}
	
	@RequestMapping(value = "/contact-us", method = RequestMethod.GET)
	public String contactus(HttpServletRequest request,HttpServletResponse response){
		setHeaderForStatic(request, response);
		return "contactUs";
	}

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String admin(HttpServletRequest request,HttpServletResponse response){
		if(!adminMgt.isLoggedIn(request))
			return "redirect:/page-not-found";
		return "redirect:/admin/promo-mail";
	}

	@RequestMapping(value = "/admin/promo-mail", method = RequestMethod.GET)
	public String promoMail(HttpServletRequest request,HttpServletResponse response){
		if(!adminMgt.isLoggedIn(request))
			return "redirect:/page-not-found";
		mgt.prepareHeaderBean(request, response);
		return "promoEmail";
	}

	@RequestMapping(value = "/admin/promo-mail", method = RequestMethod.POST)
	public String sendPromoMail(HttpServletRequest request,HttpServletResponse response){
		if(!adminMgt.isLoggedIn(request))
			return "redirect:/page-not-found";
		adminMgt.sendPromoMail(request, response);
		return "promoEmail";
	}
	
	
	
	
	
	public String redirectIfAny(HttpServletRequest request, String defaultRedirect, final String defaultPage){
		String redirectUrl = request.getParameter("redirectUrl");
		if(redirectUrl == null)
			redirectUrl = defaultRedirect;
			
		if(redirectUrl != null)
			return "redirect:/"+redirectUrl;
		else
			return defaultPage;
	}
	
	public String getRedirect(HttpServletRequest request){
		return request.getParameter("redirectUrl");
	}
	
	public void setHeaderForStatic(HttpServletRequest request,HttpServletResponse response){
		if(!getAppParameter(request))
			mgt.prepareHeaderBean(request, response);
		else
			request.setAttribute(IS_APP, true);
	}
	
	public boolean getAppParameter(HttpServletRequest request){
		try{
			return Boolean.parseBoolean(request.getParameter("app"));
		}catch(Exception e){
			return false;
		}
	}
}
