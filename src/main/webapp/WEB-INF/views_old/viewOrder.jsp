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
<title>View Order - Appeti</title>
</head>
<body
	class="page page-id-44 page-template page-template-template-blank-1 page-template-template-blank-1-php logged-in woocommerce-account woocommerce-page x-renew x-navbar-fixed-top-active x-full-width-layout-active x-sidebar-content-active x-post-meta-disabled x-portfolio-meta-disabled wpb-js-composer js-comp-ver-4.3.5 vc_responsive x-v3_2_1 x-shortcodes-v3_0_2">


	<div id="top" class="site">
		<%@ include file="header.jsp"%>
		<div class="x-container max width offset">
		<div class="x-main full" role="main">

			<div class="woocommerce">
				<c:choose>
				<c:when test="${accBean.result.status == false }"><div class="woocommerce-error">${accBean.result.message} <a href="${pageContext.request.contextPath}/my-account/" class="wc-forward">My Account</a></div>
				</c:when>
				<c:otherwise>
				<c:set var="order" value="${accBean.order}"/>
				
				<p class="order-info">
					Order #
					<mark class="order-number">${order.orderId}</mark>
					was placed on
					<mark class="order-date">
					<fmt:formatDate type="date" value="${order.date}" /></mark>
					and is currently
					<mark class="order-status">${order.state}</mark>
					.
				</p>


				<h2>Order Details</h2>
				<table class="shop_table order_details">
					<thead>
						<tr>
							<th class="product-name">Product</th>
							<th class="product-total">Total</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${order.items}" var="item">
							<c:set var="productLink" value="${pageContext.request.contextPath}/product/${item.productId}-${item.ptitleId}-${item.tagId}
						/${fn:replace(fn:toLowerCase(item.productName),' ','-')}-${fn:replace(fn:toLowerCase(item.ptitleName),' ','-')}
						-${fn:replace(fn:toLowerCase(item.unit),' ','-')}">
					</c:set>
					
						<tr class="order_item">
							<td class="product-name"><a
								href="${productLink}">${item.productName}</a> <strong class="product-quantity">X ${item.quantity}</strong>
							<dl class="variation">
									<dt class="variation-type">Type:</dt>
									<dd class="variation-type">
										<p>${item.ptitleName}</p>
									</dd>

									<dt class="variation-numbers">Numbers:</dt>
									<dd class="variation-numbers">
										<p>${item.unit}</p>
									</dd>
								</dl></td>
							<td class="product-total"><span class="amount">Rs. ${item.totalPrice}</span>
							</td>
						</tr>
						
						</c:forEach>

					</tbody>
					<tfoot>
						<tr>
							<th scope="row">Subtotal:</th>
							<td><span class="amount">Rs. ${order.totalAmount}</span></td>
						</tr>
						<tr>
							<th scope="row">Discount:</th>
							<td><span class="amount">Rs. ${order.discount}</span></td>
						</tr>
						<tr>
							<th scope="row">Shipping:</th>
							<td><span class="amount">
						<c:choose>
						<c:when test="${order.deliveryCharge == 0}">
						FREE SHIPPING
						</c:when>
						<c:otherwise>
						Rs. ${order.deliveryCharge}
						</c:otherwise>
						</c:choose>
						</span>
						</td>
						</tr>
						<c:if test="${order.trackingId != 0}"><tr>
							<th scope="row">Payment Method:</th>
							<td>Online Payment, PaymentId#${order.trackingId}</td>
						</tr>
						</c:if>
						<tr>
							<th scope="row">Total:</th>
							<td><span class="amount">Rs.${order.amountPaid}</span></td>
						</tr>
					</tfoot>
				</table>


				<header>
					<h2>Customer details</h2>
				</header>
<dl class="customer_details">
									<dt>Email:</dt>
									<dd>${order.billingAddr.emailAddr}</dd>
									<dt>Telephone:</dt>
									<dd>${order.billingAddr.phoneNumber}</dd>
								</dl>

				<div class="col2-set addresses">

					<div class="col-1">
								
								<header class="title">
							<h3>Billing Address</h3>
						</header>
						<address>
							${order.billingAddr.firstName} ${order.billingAddr.lastName}<br>
							${order.billingAddr.addr1}, ${order.billingAddr.addr2}<br>
							${order.billingAddr.city} - ${order.billingAddr.zipCode}<br>
							${order.billingAddr.state}
						</address>


					</div>
					<!-- /.col-1 -->

					<div class="col-2">

						<header class="title">
							<h3>Shipping Address</h3>
						</header>
						<address>
							${order.shippingAddr.firstName} ${order.shippingAddr.lastName}<br>
							${order.shippingAddr.addr1}, ${order.shippingAddr.addr2}<br>
							${order.shippingAddr.city} - ${order.shippingAddr.zipCode}<br>
							${order.shippingAddr.state}
						</address>

					</div>
					<!-- /.col-2 -->

				</div>
				<!-- /.col2-set -->


				<div class="clear"></div>
				</c:otherwise>
				</c:choose>
			</div>


			</div>

		</div>


		<%@ include file="footer.jsp"%>
	</div>
	<%@ include file="postLoadScripts.jsp"%>
</body>
</html>
