<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page session="true"%>

<!DOCTYPE html>
<!--[if IE 9]><html class="no-js ie9" lang="en-US"><![endif]-->
<!--[if gt IE 9]><!-->
<html class="no-js" lang="en-US">
<!--<![endif]--><head>
<title>Appeti - Home</title>
</head>
<body
	class="home page page-id-37 page-template page-template-template-blank-4 page-template-template-blank-4-php logged-in x-renew x-navbar-fixed-top-active x-full-width-layout-active x-sidebar-content-active x-post-meta-disabled x-portfolio-meta-disabled wpb-js-composer js-comp-ver-4.3.5 vc_responsive x-v3_2_1 x-shortcodes-v3_0_2">


	<div id="top" class="site">
		<%@ include file="header.jsp"%>
		<%@ include file="slider.jsp"%>

		<div class="x-main full" role="main">
			<article id="post-37"
				class="post-37 page type-page status-publish hentry no-post-thumbnail">
				<div class="entry-content content">
					<%@ include file="mainHome.jsp"%>
				</div>
			</article>
		</div>

		
		<%@ include file="footer.jsp"%>
	</div>
<%@ include file="postLoadScripts.jsp"%>
</body>
</html>
