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
<title>My Account - Appeti</title>
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
							<p class="myaccount_user">
								Hello <strong>${accBean.email}</strong> (not ${accBean.email}? <a
									href="#" onclick="signOut();">Sign
									out</a>). From your account dashboard you can view your recent
								orders, manage your shipping and billing addresses and <a
									href="${pageContext.request.contextPath}/my-account/edit-account/">edit
									your password and account details</a>.
							</p>

							<h2>Recent Orders</h2>

							<table class="shop_table shop_table_responsive my_account_orders">

								<thead>
									<tr>
										<th class="order-number"><span class="nobr">Order</span></th>
										<th class="order-date"><span class="nobr">Date</span></th>
										<th class="order-status"><span class="nobr">Status</span></th>
										<th class="order-total"><span class="nobr">Total</span></th>
										<th class="order-actions">&nbsp;</th>
									</tr>
								</thead>

								<tbody>
									<c:forEach items="${accBean.orderList}" var="order">
										
										<tr class="order">
										<td class="order-number" data-title="Order Number"><a
											href="${pageContext.request.contextPath}/my-account/view-order/${order.orderId}">
												#${order.orderId} </a></td>
										<td class="order-date" data-title="Date">	
										<fmt:formatDate type="date" value="${order.date}"/></td>
										<td class="order-status" data-title="Status"
											style="text-align: left; white-space: nowrap;">
											${order.status}</td>
										<td class="order-total" data-title="Total"><span
											class="amount">Rs.${order.amount}</span> for ${order.numItems} items</td>
										<td class="order-actions"><a
											href="${pageContext.request.contextPath}/my-account/view-order/${order.orderId}"
											class="button view">View</a></td>
									</tr>
										
									</c:forEach>
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
