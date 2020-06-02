<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page session="true"%>

<!DOCTYPE html>
<!--[if IE 9]><html class="no-js ie9" lang="en-US"><![endif]-->
<!--[if gt IE 9]><!-->
<html class="no-js" lang="en-US">
<!--<![endif]--><head>
<title>Checkout - Appeti</title>
</head>
<body
	class="page page-id-43 page-template page-template-template-blank-1 page-template-template-blank-1-php logged-in woocommerce-checkout woocommerce-page woocommerce-demo-store x-renew x-navbar-fixed-top-active x-full-width-layout-active x-sidebar-content-active x-post-meta-disabled x-portfolio-meta-disabled wpb-js-composer js-comp-ver-4.3.5 vc_responsive x-v3_2_1 x-shortcodes-v3_0_2">
<style type="text/css">
select, textarea, input[type="text"], input[type="password"], input[type="datetime"],
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


	<div id="top" class="site">
		<%@ include file="header.jsp"%>
		<div class="x-container max width offset">
		<div class="x-main full" role="main">
			<article id="post-42"
				class="post-42 page type-page status-publish hentry no-post-thumbnail">
				<div class="entry-content content">
					<%@ include file="mainCheckout.jsp"%>
				</div>
			</article>
		</div>
		</div>
		
		<%@ include file="footer.jsp"%>
	</div>
<%@ include file="postLoadScripts.jsp"%>
</body>
</html>
