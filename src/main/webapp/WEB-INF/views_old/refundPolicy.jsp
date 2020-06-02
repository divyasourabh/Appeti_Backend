<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page session="true"%>

<!DOCTYPE html>
<!--[if IE 9]><html class="no-js ie9" lang="en-US"><![endif]-->
<!--[if gt IE 9]><!-->
<html class="no-js" lang="en-US">
<!--<![endif]-->
<head>
<title>Refund Policy - Appeti</title>
</head>
<body
	class="home page page-id-37 page-template page-template-template-blank-4 page-template-template-blank-4-php logged-in x-renew x-navbar-fixed-top-active x-full-width-layout-active x-sidebar-content-active x-post-meta-disabled x-portfolio-meta-disabled wpb-js-composer js-comp-ver-4.3.5 vc_responsive x-v3_2_1 x-shortcodes-v3_0_2">


	<div id="top" class="site">
		<%@ include file="header.jsp"%>
		<div class="x-container max width offset">
			<div class="x-main full" role="main">


				<article id="post-559"
					class="post-559 page type-page status-publish hentry no-post-thumbnail">


					<div class="entry-content content">


						<h3 class="h-custom-headline center-text h3 accent">
							<span>Cancellation &amp; Refund Policy</span>
						</h3>
						<p>We at appeti.in believes in helping our customers as far as
							possible, and has therefore a liberal cancellation policy.</p>
						<p>
							<strong>How do I cancel an order on appeti.in?</strong><br>
							If unfortunately you have to cancel an order, please email us at
							<a href="mailto:${headerBean.email}">${headerBean.email}</a><br>
							For outright cancellations by you:
						</p>
						<ul>
							<li>If you cancel your order before your food items have
								been sourced, we will refund the entire amount.<br> If the
								cancellation is after your food has shipped:
							</li>
							<li>If you received the food, it will be eligible for
								exchange or refund only in case it is dis-satisfactory and the
								claim is genuine. The decision of <a href="${pageContext.request.contextPath}">Appeti.in</a>
								will be final in this case.
							</li>
						</ul>
						<p>
							<strong>Refund Policy</strong><br> Refunds will be made in
							the same method that the payment is received within 15 working
							days from the date of cancellation of order.
						</p>
						<p>
							<strong>Return Policy</strong><br> In case of complaints
							regarding foods that come with a warranty from Brand Owners,
							please refer the issue to the concerned Brand Owner. Let us know
							&amp; we’ll help you regarding the same.<br> If you think,
							you have received the food items in a bad condition or if the
							packaging is tampered with or damaged before delivery, please
							email us at <a href="mailto:${headerBean.email}">${headerBean.email}</a>
							mentioning your Order ID within 24 hours of accepting the
							package. We will request you for the pictures of the shipment and
							individual pictures of the boxes which will be provided to the
							Food Vendors and the courier partner within 48 hours of shipment
							delivery. If the claim is genuine as found by us, the Food Vendor
							and the Courier Partner, we will get the shipment picked back and
							initiate exchange or refund for the same.<br> <a
								href="${pageContext.request.contextPath}">Appeti.in</a> cannot offer refund
							for the following reasons<br> 1) Shipment cannot be
							delivered because of the buyers unavailability.<br> 2)
							Shipment cannot be delivered because of the wrong address.<br>
							3) Wrong orders placed by the customer and once the product is
							shipped.<br> 4) User didn't intimate us in the mentioned
							time frame.
						</p>


					</div>

				</article>


			</div>
		</div>
		<%@ include file="footer.jsp"%>
	</div>
	<%@ include file="postLoadScripts.jsp"%>
</body>
</html>
