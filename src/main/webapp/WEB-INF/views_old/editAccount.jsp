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
<title>Appeti - Edit Account</title>
</head>
<body
	class="home page page-id-37 page-template page-template-template-blank-4 page-template-template-blank-4-php logged-in x-renew x-navbar-fixed-top-active x-full-width-layout-active x-sidebar-content-active x-post-meta-disabled x-portfolio-meta-disabled wpb-js-composer js-comp-ver-4.3.5 vc_responsive x-v3_2_1 x-shortcodes-v3_0_2">


	<div id="top" class="site">
		<%@ include file="header.jsp"%>

		<div class="x-container max width offset">
			<div class="x-main full" role="main">


				<article id="post-44"
					class="post-44 page type-page status-publish hentry no-post-thumbnail">


					<div class="entry-content content">


						<div class="woocommerce">
			<c:choose>
				<c:when test="${accBean.result.status == false }">
				<div class="woocommerce-message x-alert x-alert-info x-alert-danger" id="message_div" style="display:block;">
		${accBean.result.message}
		</div>
		</c:when>
		<c:when test="${accBean.result.status == true }">
				<div class="woocommerce-message x-alert x-alert-info x-alert-block" id="message_div" style="display:block;">
		${accBean.result.message}
		</div>
		</c:when>
		</c:choose>
							<form action="${pageContext.request.contextPath}/my-account/edit-account" method="post">


								<p class="form-row form-row-first">
									<label for="account_first_name">First name <span
										class="required">*</span></label> <input type="text"
										class="input-text" name="first_name"
										id="first_name" value="${accBean.fName}">
								</p>
								<p class="form-row form-row-last">
									<label for="account_last_name">Last name <span
										class="required">*</span></label> <input type="text"
										class="input-text" name="last_name"
										id="last_name" value="${accBean.lName}">
								</p>
								<div class="clear"></div>

								<p class="form-row form-row-wide">
									<label for="account_email">Email address <span
										class="required">*</span></label> <input type="email"
										class="input-text" name="email" id="email" disabled
										value="${accBean.email}">
								</p>

								<fieldset>
									<legend>Password Change</legend>

									<p class="form-row form-row-wide">
										<label for="password_current">Current Password (leave
											blank to leave unchanged)</label> <input type="password"
											class="input-text" name="cur_pwd"
											id="cur_pwd">
									</p>
									<p class="form-row form-row-wide">
										<label for="password_1">New Password (leave blank to
											leave unchanged)</label> <input type="password" class="input-text"
											name="pwd" id="pwd">
									</p>
									<p class="form-row form-row-wide">
										<label for="password_2">Confirm New Password</label> <input
											type="password" class="input-text" name="c_pwd"
											id="c_pwd">
									</p>
								</fieldset>
								<div class="clear"></div>


								<p>
									
									<input type="submit" class="button" name="save_account_details"
										value="Save changes">
								</p>


							</form>
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
