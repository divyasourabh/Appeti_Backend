<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page session="true"%>

<!DOCTYPE html>
<!--[if IE 9]><html class="no-js ie9" lang="en-US"><![endif]-->
<!--[if gt IE 9]><!-->
<html class="no-js" lang="en-US">
<!--<![endif]-->
<head>
<title>Appeti - Track Order</title>
</head>
<body
	class="home page page-id-37 page-template page-template-template-blank-4 page-template-template-blank-4-php logged-in x-renew x-navbar-fixed-top-active x-full-width-layout-active x-sidebar-content-active x-post-meta-disabled x-portfolio-meta-disabled wpb-js-composer js-comp-ver-4.3.5 vc_responsive x-v3_2_1 x-shortcodes-v3_0_2">


	<div id="top" class="site">
		<%@ include file="header.jsp"%>

		<div class="x-container max width offset">
			<div class="x-main full" role="main">


				<article id="post-838"
					class="post-838 page type-page status-publish hentry no-post-thumbnail">


					<div class="entry-content content">


						<div>
							<a
								href="${pageContext.request.contextPath}/resources/images/banner-track-my-order-755x122.jpg"><img
								class="alignnone size-full wp-image-853"
								src="${pageContext.request.contextPath}/resources/images/banner-track-my-order-755x122.jpg"
								alt="banner-track-my-order-755x122" width="755" height="122"></a>
						</div>
						<div class="woocommerce">
							<c:if test="${homeBean.result.status == true}">
		<div class="woocommerce-message x-alert x-alert-info x-alert-block" id="message_div" style="display:block;">
		${homeBean.result.message}</div></c:if>
		
		<c:if test="${homeBean.result.status == false}">
		<div class="woocommerce-message x-alert x-alert-danger x-alert-block" id="message_div" style="display:block;">
		${homeBean.result.message}</div></c:if>
		
		
		<form action="${pageContext.request.contextPath}/track-order/" method="post"
								class="track_order">

								<p>To track your order please enter your Order ID in the box
									below and press the "Track" button. This was given to you on
									your receipt and in the confirmation email you should have
									received.</p>

								<p class="form-row form-row-first">
									<label for="orderid">Order Id</label> <input class="input-text"
										type="text" name="orderId" id="orderId"
										placeholder="Found in your order confirmation email.">
								</p>
								<p class="form-row form-row-last">
									<label for="order_email">Billing Email</label> <input
										class="input-text" type="text" name="email"
										id="email" placeholder="Email you used during checkout.">
								</p>
								<div class="clear"></div>

								<p class="form-row">
									<input type="submit" class="button" name="track" value="Track">
								</p>
								
							</form>
						</div>
						<div id="as-root">
							For more assistance, feel free to drop us a mail at <a
								href="mailto:${headerBean.email}">${headerBean.email}</a>.
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
