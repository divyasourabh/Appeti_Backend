<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page session="true"%>

<!DOCTYPE html>
<!--[if IE 9]><html class="no-js ie9" lang="en-US"><![endif]-->
<!--[if gt IE 9]><!-->
<html class="no-js" lang="en-US">
<!--<![endif]-->
<head>
<title>Home</title>
</head>
<body
	class="page page-id-44 page-template page-template-template-blank-1 page-template-template-blank-1-php woocommerce-account woocommerce-page x-renew x-navbar-fixed-top-active x-full-width-layout-active x-sidebar-content-active x-post-meta-disabled x-portfolio-meta-disabled wpb-js-composer js-comp-ver-4.3.5 vc_responsive x-v3_2_1 x-shortcodes-v3_0_2">


	<div id="top" class="site">
		<%@ include file="header.jsp"%>

		<div class="x-container max width offset">
			<div class="x-main full" role="main">


				<article id="post-44"
					class="post-44 page type-page status-publish hentry no-post-thumbnail">


					<div class="entry-content content">
						
						<c:choose>
						<c:when test="${result.status == true}">
							<div
								class="woocommerce-message x-alert x-alert-info x-alert-block"
								id="message_div" style="display: block;">
								${result.message} <a class="button wc-forward"
									href="${pageContext.request.contextPath}/signin">Sign-In</a>
							</div>
						</c:when>
						<c:otherwise>
							<c:if test="${not empty result.message}">
							<div
								class="woocommerce-message x-alert x-alert-info x-alert-danger"
								id="message_div" style="display: block;">
								${result.message}</div>
						</c:if>

						<div class="woocommerce">

							<form method="post" class="lost_reset_password" action="${pageContext.request.contextPath}/my-account/lost-password">


								<p>Lost your password? Please enter your email
									address.</p>

								<p class="form-row form-row-first">
									<label for="user_login">Email</label> <input
										class="input-text" type="text" name="forgot_email_address"
										id="forgot_email_address">
								</p>


								<div class="clear"></div>

								<p class="form-row">
									<input type="submit" class="button" value="Reset Password">
								</p>

							</form>
						</div>
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
