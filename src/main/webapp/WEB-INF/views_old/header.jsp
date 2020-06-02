<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

	<script type="text/javascript">
var ua = navigator.userAgent.toLowerCase();
var isAndroid = ua.indexOf("android") > -1; //&& ua.indexOf("mobile");
if(isAndroid) {
  // Do something!
  // Redirect to Android-site?
  window.location = 'https://play.google.com/store/apps/details?id=ds.com.appeti';
}
	</script>
	
	<%@ include file="cssInclude.jsp"%>
	<%@ include file="jsInclude.jsp"%>
<script type="text/javascript">
jQuery(function ($) {
	$(".signout").click(function (e) {
	    e.preventDefault(); //prevent default form submit
		});
});
</script>


<c:if test="${is_app != true}">
<%@ include file="fbLogin.jsp"%>
<%@ include file="gLogin.jsp"%>
<header class="masthead masthead-inline" role="banner">
<style type="text/css">
.searchbar select, textarea, input[type="text"], input[type="password"], input[type="datetime"],
	input[type="datetime-local"], input[type="date"], input[type="month"],
	input[type="time"], input[type="week"], input[type="number"], input[type="email"],
	input[type="url"], input[type="search"], input[type="tel"], input[type="color"],
	.uneditable-input {
	display: inline-block;
	height: 2.65em;
	margin-bottom: 9px;
	border: 2px solid #ddd;
	padding: 0 0.65em;
	font-size: 13px;
	font-size: 1.3rem;
	line-height: 1;
	color: #555;
	background-color: #fff
}
label, input, button, select, textarea {
	font-size: 100%;
	line-height: 1.8
}	
</style>

	<div class="x-topbar">
		<div class="x-topbar-inner x-container max width">
			<p class="p-info">
				${headerBean.phoneNumber } | <a href="mailto:${headerBean.email}">${headerBean.email}</a> | <a
					href="${pageContext.request.contextPath}/about/"> About Us</a> | <a
					href="${pageContext.request.contextPath}/contact-us/"> Reach Us</a> 
					<% if(session != null && session.getAttribute("user") == null){ %>
					| <a
					href="${pageContext.request.contextPath}/signin/"> Signup</a>
					<%} else {%>
					| <a
					onclick="signOut();" href="#" class="signout"> Logout</a>
					<%} %>
			</p>
			<div class="x-social-global">
				<c:choose><c:when test="${showTopSearch == false}">
				
				<a href="https://www.facebook.com/TheAppeti" class="facebook"
					title="Facebook" target="_blank"><i
					class="x-icon-facebook-square"></i></a><a
					href="https://twitter.com/TheAppeti" class="twitter"
					title="Twitter" target="_blank"><i
					class="x-icon-twitter-square"></i></a><a
					href="https://www.linkedin.com/company/appeti" class="linkedin"
					title="LinkedIn" target="_blank"><i
					class="x-icon-linkedin-square"></i></a><a
					href="https://www.pinterest.com/niteshkp/appeti/" class="pinterest"
					title="Pinterest" target="_blank"><i
					class="x-icon-pinterest-square"></i></a>
				
				</c:when>
				
				<c:otherwise>
				<form method="get" style="margin:0;" id="searchform1" class="form-search"
						action="${pageContext.request.contextPath}/shop/search" >
						
						<label for="s" class="visually-hidden">Search Appet-i</label> 
							<span role="status"
							aria-live="polite"
							class="isp_polite_powered_by_id ui-helper-hidden-accessible"></span><input
							type="text" id="query" class="typeahead search-query ui-autocomplete-input"
							name="query"
							placeholder="Search Appet-i"
							style="width: 200px;margin:0;">
							
						
							
				<a href="https://www.facebook.com/TheAppeti" class="facebook"
					title="Facebook" target="_blank"><i
					class="x-icon-facebook-square"></i></a><a
					href="https://twitter.com/TheAppeti" class="twitter"
					title="Twitter" target="_blank"><i
					class="x-icon-twitter-square"></i></a><a
					href="https://www.linkedin.com/company/appeti" class="linkedin"
					title="LinkedIn" target="_blank"><i
					class="x-icon-linkedin-square"></i></a><a
					href="https://www.pinterest.com/niteshkp/appeti/" class="pinterest"
					title="Pinterest" target="_blank"><i
					class="x-icon-pinterest-square"></i></a>
					</form>
				</c:otherwise>
				</c:choose>
				
				
					
			</div>
		</div>
	</div>



	<div class="x-navbar-wrap">
		<div class="x-navbar">
			<div class="x-navbar-inner">
				<div class="x-container max width">

					<h1 class="visually-hidden">Appeti</h1>
					<a href="${pageContext.request.contextPath}/" class="x-brand img"
						title="Shop the authentic regional delicacies online."> <img
						src="${pageContext.request.contextPath}/resources/images/Capture2.png"
						alt="Shop the authentic regional delicacies online."></a> <a
						href="#" class="x-btn-navbar collapsed" data-toggle="collapse"
						data-target=".x-nav-wrap.mobile"> <i class="x-icon-bars"></i>
						<span class="visually-hidden">Navigation</span>
					</a>

						<nav class="x-nav-wrap desktop" role="navigation">
							<ul id="menu-main-navigation-menu" class="x-nav">
								<li id="menu-item-437"
									class="x-megamenu col-4 menu-item menu-item-type-custom menu-item-object-custom menu-item-has-children menu-item-437"><a
									href="${pageContext.request.contextPath}/shop/"><span>Delicacies</span></a>
									<ul class="sub-menu">

										<c:forEach items="${headerBean.nodeTree.childNodes}"
											var="node">
											<li id="menu-item-433"
												class="menu-item menu-item-type-taxonomy menu-item-object-product_cat menu-item-has-children menu-item-433 tax-item tax-item-154"><a
												href="${pageContext.request.contextPath}/shop/node/${node.nodeId}/${fn:replace(fn:toLowerCase(node.nodeName),' ','-')}">
													<span>${node.nodeName}</span>
											</a>

												<ul class="sub-menu">
													<c:forEach items="${node.childNodes}" var="childNode">
														<li id="menu-item-434"
															class="menu-item menu-item-type-taxonomy menu-item-object-product_cat menu-item-434 tax-item tax-item-165"><a
															href="${pageContext.request.contextPath}/shop/node/${childNode.nodeId}/${fn:replace(fn:toLowerCase(childNode.nodeName),' ','-')}">
																<span>${childNode.nodeName}</span>
														</a></li>

													</c:forEach>

												</ul></li>
										</c:forEach>

									</ul></li>
								<li id="menu-item-860"
									class="x-megamenu col-${fn:length(headerBean.categoryTree.childCategories)} menu-item menu-item-type-custom menu-item-object-custom menu-item-has-children menu-item-860"><a
									href="${pageContext.request.contextPath}/shop/"><span>Flavours</span></a>
									<ul class="sub-menu">

										<c:forEach items="${headerBean.categoryTree.childCategories}"
											var="node">
											<li id="menu-item-861"
												class="menu-item menu-item-type-taxonomy menu-item-object-product_tag menu-item-has-children menu-item-861 tax-item tax-item-163"><a
												href="${pageContext.request.contextPath}/shop/category/${node.categoryId}/${fn:replace(fn:toLowerCase(node.name),' ','-')}">
													<span>${node.name}</span>
											</a>
												<ul class="sub-menu">
													<c:forEach items="${node.products}" var="product">
														<li id="menu-item-865"
															class="menu-item menu-item-type-post_type menu-item-object-product menu-item-865"><a
															href="${pageContext.request.contextPath}/shop/category/${node.categoryId}/product/${product.productId}/${fn:replace(fn:toLowerCase(product.productName),' ','-')}">
																<span>${product.productName}</span>
														</a></li>
													</c:forEach>

												</ul></li>
										</c:forEach>


									</ul></li>
								<li id="menu-item-562"
									class="x-megamenu col-2 menu-item menu-item-type-custom menu-item-object-custom menu-item-has-children menu-item-562"><a
									href="${pageContext.request.contextPath}/contact-us/"><span>Information</span></a>
									<ul class="sub-menu">
										<li id="menu-item-889"
											class="menu-item menu-item-type-custom menu-item-object-custom menu-item-has-children menu-item-889"><a
											href="http://#"><span>Company</span></a>
											<ul class="sub-menu">
												<li id="menu-item-563"
													class="menu-item menu-item-type-post_type menu-item-object-page menu-item-563"><a
													href="${pageContext.request.contextPath}/about/"><span>About
															Us</span></a></li>
												<li id="menu-item-563"
													class="menu-item menu-item-type-post_type menu-item-object-page menu-item-563"><a
													href="${pageContext.request.contextPath}/appet-i-team/"><span>Team</span></a></li>

												<li id="menu-item-972"
													class="menu-item menu-item-type-post_type menu-item-object-page menu-item-972"><a
													href="${pageContext.request.contextPath}/vendor-sourcing-form/"><span>Vendor
															Sourcing Form</span></a></li>
												<li id="menu-item-972"
													class="menu-item menu-item-type-post_type menu-item-object-page menu-item-972"><a
													href="${pageContext.request.contextPath}/contact-us/"><span>Contact
															us</span></a></li>
												<li id="menu-item-873"
													class="menu-item menu-item-type-post_type menu-item-object-page menu-item-972"><a
													href="${pageContext.request.contextPath}/we-are-hiring/"><span>We
															are hiring!</span></a></li>

											</ul></li>
										<li id="menu-item-890"
											class="menu-item menu-item-type-custom menu-item-object-custom menu-item-has-children menu-item-890"><a
											href="http://#"><span>Terms &#038; Policies</span></a>
											<ul class="sub-menu">
												<li id="menu-item-568"
													class="menu-item menu-item-type-post_type menu-item-object-page menu-item-568"><a
													href="${pageContext.request.contextPath}/terms-of-service/"><span>Terms
															of Service</span></a></li>
												<li id="menu-item-566"
													class="menu-item menu-item-type-post_type menu-item-object-page menu-item-566"><a
													href="${pageContext.request.contextPath}/privacy-policy/"><span>Privacy
															Policy</span></a></li>
												<li id="menu-item-565"
													class="menu-item menu-item-type-post_type menu-item-object-page menu-item-565"><a
													href="${pageContext.request.contextPath}/delivery-shipping/"><span>Delivery
															&#038; Shipping</span></a></li>
												<li id="menu-item-567"
													class="menu-item menu-item-type-post_type menu-item-object-page menu-item-567"><a
													href="${pageContext.request.contextPath}/refunds-cancellations/"><span>Cancellation
															&#038; Refunds</span></a></li>
											</ul></li>
									</ul></li>
								<li id="menu-item-888"
									class="menu-item menu-item-type-custom menu-item-object-custom menu-item-has-children menu-item-888"><a
									href="${pageContext.request.contextPath}/my-account/"><span>My
											Account</span></a>
									<ul class="sub-menu">
										<li id="menu-item-48"
											class="menu-item menu-item-type-post_type menu-item-object-page current-menu-item page_item page-item-44 current_page_item menu-item-has-children menu-item-48"><a
											href="${pageContext.request.contextPath}/my-account/"><span>Account</span></a>
											<ul class="sub-menu">
												<li id="menu-item-1153"
													class="menu-item menu-item-type-custom menu-item-object-custom menu-item-1153"><a
													href="${pageContext.request.contextPath}/my-account/"><span>My
															Orders</span></a></li>
												<li id="menu-item-1155"
													class="menu-item menu-item-type-custom menu-item-object-custom current-menu-item menu-item-1155"><a
													href="${pageContext.request.contextPath}/my-account/lost-password/"><span>Lost-password</span></a></li>
												<% if(session != null && session.getAttribute("user") != null){ %>
												<li id="signout"
													class="signout menu-item menu-item-type-custom menu-item-object-custom current-menu-item menu-item-1155">
													<a onclick="signOut();" href="#"><span>Sign-out</span></a>
												</li>
												<% } %>
											</ul></li>
										<li id="menu-item-50"
											class="menu-item menu-item-type-post_type menu-item-object-page menu-item-50"><a
											href="${pageContext.request.contextPath}/cart/"><span>Cart</span></a></li>
										<li id="menu-item-49"
											class="menu-item menu-item-type-post_type menu-item-object-page menu-item-49"><a
											href="${pageContext.request.contextPath}/checkout/"><span>Checkout</span></a></li>
										<li id="menu-item-886"
											class="menu-item menu-item-type-post_type menu-item-object-page menu-item-886"><a
											href="${pageContext.request.contextPath}/track-order/"><span>Track
													Order</span></a></li>
									</ul></li>
								<li id="menu-item-873"
									class="menu-item menu-item-type-post_type menu-item-object-page menu-item-873"><a
									href="${pageContext.request.contextPath}/shop/category/90909/combos"><span style="color: #FF5C33;"><i
											class="x-icon x-icon-gift"></i>Combos</span></a></li>
								<li id="menu-item-873"
									class="menu-item menu-item-type-post_type menu-item-object-page menu-item-873"><a
									href="${pageContext.request.contextPath}/latest-offers/"><span><i
											class="x-icon x-icon-gift"></i>Offers</span></a></li>
								<li id="menu-item-873"
									class="menu-item menu-item-type-post_type menu-item-object-page menu-item-873"><a
									href="${pageContext.request.contextPath}/cart/"><span><i
											class="x-icon x-icon-shopping-cart"></i>&nbsp;Cart</span></a></li>
							</ul>
						</nav>








						<!-- 
					<div class="x-nav-wrap mobile collapse">
						<ul id="menu-main-navigation-menu-1" class="x-nav">
							<li
								class="x-megamenu col-4 menu-item menu-item-type-custom menu-item-object-custom menu-item-has-children menu-item-437"><a
								href="${pageContext.request.contextPath}/shop/"><span>Delicacies</span></a>
								<ul class="sub-menu">
									<li
										class="menu-item menu-item-type-taxonomy menu-item-object-product_cat menu-item-has-children menu-item-433 tax-item tax-item-154"><a
										href="${pageContext.request.contextPath}/product-category/western/"><span>Western
												India</span></a>
										<ul class="sub-menu">
											<li
												class="menu-item menu-item-type-taxonomy menu-item-object-product_cat menu-item-434 tax-item tax-item-165"><a
												href="${pageContext.request.contextPath}/product-category/western/rajasthan/"><span>Rajasthan</span></a></li>
											<li
												class="menu-item menu-item-type-taxonomy menu-item-object-product_cat menu-item-462 tax-item tax-item-173"><a
												href="${pageContext.request.contextPath}/product-category/western/gujarat/"><span>Gujarat</span></a></li>
											<li
												class="menu-item menu-item-type-taxonomy menu-item-object-product_cat menu-item-471 tax-item tax-item-179"><a
												href="${pageContext.request.contextPath}/product-category/western/maharashtra/"><span>Maharashtra</span></a></li>
										</ul></li>
									<li
										class="menu-item menu-item-type-taxonomy menu-item-object-product_cat menu-item-has-children menu-item-429 tax-item tax-item-155"><a
										href="${pageContext.request.contextPath}/product-category/north-india/"><span>Northern
												India</span></a>
										<ul class="sub-menu">
											<li
												class="menu-item menu-item-type-taxonomy menu-item-object-product_cat menu-item-463 tax-item tax-item-167"><a
												href="${pageContext.request.contextPath}/product-category/north-india/uttar-pradesh/"><span>Uttar
														Pradesh</span></a></li>
											<li
												class="menu-item menu-item-type-taxonomy menu-item-object-product_cat menu-item-818 tax-item tax-item-187"><a
												href="${pageContext.request.contextPath}/product-category/north-india/haryana/"><span>Haryana</span></a></li>
											<li
												class="menu-item menu-item-type-taxonomy menu-item-object-product_cat menu-item-430 tax-item tax-item-161"><a
												href="${pageContext.request.contextPath}/product-category/north-india/jammu-kashmir/"><span>Jammu
														&#038; Kashmir</span></a></li>
										</ul></li>
									<li
										class="menu-item menu-item-type-taxonomy menu-item-object-product_cat menu-item-has-children menu-item-431 tax-item tax-item-159"><a
										href="${pageContext.request.contextPath}/product-category/south-india/"><span>Southern
												India</span></a>
										<ul class="sub-menu">
											<li
												class="menu-item menu-item-type-taxonomy menu-item-object-product_cat menu-item-432 tax-item tax-item-162"><a
												href="${pageContext.request.contextPath}/product-category/south-india/karnataka/"><span>Karnataka</span></a></li>
											<li
												class="menu-item menu-item-type-taxonomy menu-item-object-product_cat menu-item-469 tax-item tax-item-181"><a
												href="${pageContext.request.contextPath}/product-category/south-india/andhra-pradesh/"><span>Andhra
														Pradesh</span></a></li>
											<li
												class="menu-item menu-item-type-taxonomy menu-item-object-product_cat menu-item-817 tax-item tax-item-180"><a
												href="${pageContext.request.contextPath}/product-category/south-india/kerala/"><span>Kerala</span></a></li>
										</ul></li>
									<li
										class="menu-item menu-item-type-taxonomy menu-item-object-product_cat menu-item-has-children menu-item-427 tax-item tax-item-160"><a
										href="${pageContext.request.contextPath}/product-category/central-india/"><span>Central
												India</span></a>
										<ul class="sub-menu">
											<li
												class="menu-item menu-item-type-taxonomy menu-item-object-product_cat menu-item-465 tax-item tax-item-176"><a
												href="${pageContext.request.contextPath}/product-category/central-india/madhya-pradesh/"><span>Madhya
														Pradesh</span></a></li>
										</ul></li>
								</ul></li>
							<li
								class="x-megamenu col-3 menu-item menu-item-type-custom menu-item-object-custom menu-item-has-children menu-item-860"><a
								href="${pageContext.request.contextPath}/shop/"><span>Flavours</span></a>
								<ul class="sub-menu">
									<li
										class="menu-item menu-item-type-taxonomy menu-item-object-product_tag menu-item-has-children menu-item-861 tax-item tax-item-163"><a
										href="${pageContext.request.contextPath}/product-tag/sweets/"><span>Sweets</span></a>
										<ul class="sub-menu">
											<li
												class="menu-item menu-item-type-post_type menu-item-object-product menu-item-865"><a
												href="${pageContext.request.contextPath}/product/ice-halwa/"><span>Ice
														Halwa</span></a></li>
											<li
												class="menu-item menu-item-type-post_type menu-item-object-product menu-item-866"><a
												href="${pageContext.request.contextPath}/product/paneer-ghewar/"><span>Paneer
														Ghewar</span></a></li>
											<li
												class="menu-item menu-item-type-post_type menu-item-object-product menu-item-867"><a
												href="${pageContext.request.contextPath}/product/mysore-pak/"><span>Mysore
														Pak</span></a></li>
											<li
												class="menu-item menu-item-type-post_type menu-item-object-product menu-item-868"><a
												href="${pageContext.request.contextPath}/product/kalakand/"><span>Kalakand</span></a></li>
											<li
												class="menu-item menu-item-type-post_type menu-item-object-product menu-item-871"><a
												href="${pageContext.request.contextPath}/product/agra-petha-500gms/"><span>Kesar
														Angoori Petha</span></a></li>
										</ul></li>
									<li
										class="menu-item menu-item-type-taxonomy menu-item-object-product_tag menu-item-has-children menu-item-862 tax-item tax-item-172"><a
										href="${pageContext.request.contextPath}/product-tag/snacks/"><span>Snacks</span></a>
										<ul class="sub-menu">
											<li
												class="menu-item menu-item-type-post_type menu-item-object-product menu-item-872"><a
												href="${pageContext.request.contextPath}/product/gujarati-khakhara-50pcs/"><span>Gujarati
														Khakhra</span></a></li>
											<li
												class="menu-item menu-item-type-post_type menu-item-object-product menu-item-869"><a
												href="${pageContext.request.contextPath}/product/til-ke-laddoo/"><span>Til
														ke laddoo</span></a></li>
										</ul></li>
									<li
										class="menu-item menu-item-type-taxonomy menu-item-object-product_tag menu-item-has-children menu-item-863 tax-item tax-item-185"><a
										href="${pageContext.request.contextPath}/product-tag/namkeen/"><span>Namkeen</span></a>
										<ul class="sub-menu">
											<li
												class="menu-item menu-item-type-post_type menu-item-object-product menu-item-864"><a
												href="${pageContext.request.contextPath}/product/ratlami-sev-namkeen/"><span>Ratlami
														Sev Namkeen</span></a></li>
										</ul></li>
								</ul></li>
							<li
								class="x-megamenu col-2 menu-item menu-item-type-custom menu-item-object-custom menu-item-has-children menu-item-562"><a
								href="${pageContext.request.contextPath}/contact-us/"><span>Information</span></a>
								<ul class="sub-menu">
									<li
										class="menu-item menu-item-type-custom menu-item-object-custom menu-item-has-children menu-item-889"><a
										href="http://#"><span>Company</span></a>
										<ul class="sub-menu">
											<li
												class="menu-item menu-item-type-post_type menu-item-object-page menu-item-563"><a
												href="${pageContext.request.contextPath}/about/"><span>About Us</span></a></li>
											<li
												class="menu-item menu-item-type-post_type menu-item-object-page menu-item-564"><a
												href="${pageContext.request.contextPath}/contact-us/"><span>Contact
														Us</span></a></li>
											<li
												class="menu-item menu-item-type-post_type menu-item-object-page menu-item-972"><a
												href="${pageContext.request.contextPath}/vendor-sourcing-form/"><span>Vendor
														Sourcing Form</span></a></li>
										</ul></li>
									<li
										class="menu-item menu-item-type-custom menu-item-object-custom menu-item-has-children menu-item-890"><a
										href="http://#"><span>Terms &#038; Policies</span></a>
										<ul class="sub-menu">
											<li
												class="menu-item menu-item-type-post_type menu-item-object-page menu-item-568"><a
												href="${pageContext.request.contextPath}/terms-of-service/"><span>Terms
														of Service</span></a></li>
											<li
												class="menu-item menu-item-type-post_type menu-item-object-page menu-item-566"><a
												href="${pageContext.request.contextPath}/privacy-policy/"><span>Privacy
														Policy</span></a></li>
											<li
												class="menu-item menu-item-type-post_type menu-item-object-page menu-item-565"><a
												href="${pageContext.request.contextPath}/delivery-shipping/"><span>Delivery
														&#038; Shipping</span></a></li>
											<li
												class="menu-item menu-item-type-post_type menu-item-object-page menu-item-567"><a
												href="${pageContext.request.contextPath}/refunds-cancellations/"><span>Cancellation
														&#038; Refunds</span></a></li>
										</ul></li>
								</ul></li>
							<li
								class="menu-item menu-item-type-custom menu-item-object-custom menu-item-has-children menu-item-888"><a
								href="${pageContext.request.contextPath}/my-account/"><span>My
										Account</span></a>
								<ul class="sub-menu">
									<li
										class="menu-item menu-item-type-post_type menu-item-object-page menu-item-48"><a
										href="${pageContext.request.contextPath}/my-account/"><span>Account</span></a></li>
									<li
										class="menu-item menu-item-type-post_type menu-item-object-page menu-item-50"><a
										href="${pageContext.request.contextPath}/cart/"><span>Cart</span></a></li>
									<li
										class="menu-item menu-item-type-post_type menu-item-object-page menu-item-49"><a
										href="${pageContext.request.contextPath}/checkout/"><span>Checkout</span></a></li>
									<li
										class="menu-item menu-item-type-post_type menu-item-object-page menu-item-886"><a
										href="${pageContext.request.contextPath}/track-order-3/"><span>Track
												Order</span></a></li>
								</ul></li>
							<li
								class="menu-item menu-item-type-post_type menu-item-object-page menu-item-873"><a
								href="${pageContext.request.contextPath}/we-are-hiring/"><span>We
										are hiring!</span></a></li>
							<li
								class="menu-item menu-item-type-taxonomy menu-item-object-category menu-item-has-children menu-item-1041 tax-item tax-item-190"><a
								href="${pageContext.request.contextPath}/category/blogs/"><span>Blogs</span></a>
								<ul class="sub-menu">
									<li
										class="menu-item menu-item-type-post_type menu-item-object-post menu-item-1042"><a
										href="${pageContext.request.contextPath}/indian-taste/"><span>Indian
												Taste!</span></a></li>
								</ul></li>
							<li class="menu-item x-menu-item x-menu-item-search"><a
								href="#" class="x-btn-navbar-search"><span><i
										class="x-icon x-icon-search"></i><span
										class="x-hidden-desktop"> Search</span></span></a></li>
						</ul>
					</div>
					 -->
				</div>
			</div>
		</div>
	</div>

</header>
</c:if>