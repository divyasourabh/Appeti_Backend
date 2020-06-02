<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page session="true"%>

<!DOCTYPE html>
<!--[if IE 9]><html class="no-js ie9" lang="en-US"><![endif]-->
<!--[if gt IE 9]><!-->
<html class="no-js" lang="en-US">
<!--<![endif]--><head>
<title>Email Verification - Appeti</title>
</head>
<body
	class="error404 logged-in x-renew x-navbar-fixed-top-active x-full-width-layout-active x-full-width-active x-post-meta-disabled x-portfolio-meta-disabled wpb-js-composer js-comp-ver-4.3.5 vc_responsive x-v3_2_1 x-shortcodes-v3_0_2">


	<div id="top" class="site">
		<%@ include file="header.jsp"%>
		
		<div class="x-container max width offset">
    <div class="x-main full" role="main">
			<article id="post-37"
				class="post-37 page type-page status-publish hentry no-post-thumbnail">
				<div class="entry-content content">
					<c:choose>
						<c:when test="${status.status == true}">
							<div
								class="woocommerce-message x-alert x-alert-info x-alert-block"
								id="message_div" style="display: block;">
								${status.message} <a class="button wc-forward"
									href="${pageContext.request.contextPath}/shop">Start
									Shopping</a>
							</div>
						</c:when>
						<c:otherwise>
							<div
								class="woocommerce-message x-alert x-alert-info x-alert-block"
								id="message_div" style="display: block;">
								${status.message}</div>
						</c:otherwise>
					</c:choose>
				</div>
			</article>
		</div>
</div>

		<%@ include file="footer.jsp"%>
	</div>
<%@ include file="postLoadScripts.jsp"%>
</body>
</html>
