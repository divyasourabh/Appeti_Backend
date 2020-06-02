<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page session="true"%>

<!DOCTYPE html>
<!--[if IE 9]><html class="no-js ie9" lang="en-US"><![endif]-->
<!--[if gt IE 9]><!-->
<html class="no-js" lang="en-US">
<!--<![endif]--><head>
<title>Shop - Appeti</title>



</head>
<body
	class="archive post-type-archive post-type-archive-product logged-in woocommerce woocommerce-page x-renew x-navbar-fixed-top-active x-full-width-layout-active x-full-width-active x-post-meta-disabled x-portfolio-meta-disabled wpb-js-composer js-comp-ver-4.3.5 vc_responsive x-v3_2_1 x-shortcodes-v3_0_2">

	<div id="top" class="site">
		<%@ include file="header.jsp"%>
		<%@ include file="shopLandmark.jsp"%>
		
		<div class="x-container max width offset">
		<div class="x-main full" role="main">
			<%@ include file="mainShop.jsp"%>
		</div>
		</div>
		<%@ include file="footer.jsp"%>
	</div>
<%@ include file="postLoadScripts.jsp"%>
</body>
</html>
