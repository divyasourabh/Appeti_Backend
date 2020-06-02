<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="true"%>

<!DOCTYPE html>
<!--[if IE 9]><html class="no-js ie9" lang="en-US"><![endif]-->
<!--[if gt IE 9]><!-->
<html class="no-js" lang="en-US">
<!--<![endif]-->
<head>
<title>Offers - Appeti</title>
</head>
<body
	class="page page-id-44 page-template page-template-template-blank-1 page-template-template-blank-1-php logged-in woocommerce-account woocommerce-page x-renew x-navbar-fixed-top-active x-full-width-layout-active x-sidebar-content-active x-post-meta-disabled x-portfolio-meta-disabled wpb-js-composer js-comp-ver-4.3.5 vc_responsive x-v3_2_1 x-shortcodes-v3_0_2">

	<div id="top" class="site">
		<%@ include file="header.jsp"%>

		<div class="x-container max width offset">
			<div class="x-main full" role="main">

				<article id="post-44"
					class="post-44 page type-page status-publish hentry no-post-thumbnail">

					<div class="entry-content content">

						<div class="woocommerce">
							
							<h2>Latest Offers</h2><br>	
							
							<table class="shop_table shop_table_responsive my_account_orders">

								<thead>
									<tr>
										<th class=""><span class="nobr">Coupon Code</span></th>
										<th class=""><span class="nobr">Offer</span></th>
										<th class="order-date"><span class="nobr">Valid Till</span></th>									</tr>
								</thead>

								<tbody>
									<c:forEach items="${homeBean.activeCoupons}" var="coupon">
										
										<tr class="order">
										<td class="" data-title="Code">${fn:toUpperCase(coupon.code)}</td>
										<c:if test="${coupon.productId == -1}"><td class="order-status" data-title="Applicable"
											style="text-align: left; white-space: nowrap;">${coupon.discount}% off on 
											<a href="${pageContext.request.contextPath}/shop">All Products</a></td>
										</c:if>
										<c:if test="${coupon.productId != -1}"><td class="order-number" data-title="Applicable"
											style="text-align: left; white-space: nowrap;">${coupon.discount}% off on 
											<a href="${pageContext.request.contextPath}/product/${coupon.productId}/
											${fn:replace(fn:toLowerCase(coupon.productName),' ','-')}"><b>${coupon.productName}</b></a></td>
										</c:if>
										<td class="order-date" data-title="Date">	
										<fmt:formatDate type="date" value="${coupon.validTill}"/></td>
										
									</tr>
										
									</c:forEach>
									
									<tr class="order" >
									<td colspan=3><font color=#21751D size=5px><br><br>First time on Appet-i? <a href="${pageContext.request.contextPath}/register" style="color: brown;">Register now,</a> and get 10% off on your first order !!</font>
									<div class="x-container">
							<a class="x-btn x-btn-real x-btn-pill x-btn-x-large"
								href="${pageContext.request.contextPath}/shop" title="Shop Now"
								data-options="thumbnail: ''"><i
								class="x-icon x-icon-shopping-cart"></i>Shop Now</a>
						</div></td>
									</tr>
								</tbody>

							</table>

		
						</div>


					</div>

				</article>


			</div>
		</div>


		<%@ include file="footer.jsp"%>
	</div>
	<%@ include file="postLoadScripts.jsp"%>
</body>
</html>
