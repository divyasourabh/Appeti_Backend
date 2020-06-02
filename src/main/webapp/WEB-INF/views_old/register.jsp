<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page session="true"%>

<!DOCTYPE html>
<!--[if IE 9]><html class="no-js ie9" lang="en-US"><![endif]-->
<!--[if gt IE 9]><!-->
<html class="no-js" lang="en-US">
<!--<![endif]--><head>
<title>Register - Appeti</title>

</head>
<body
	class="home page page-id-37 login login-action-login wp-core-ui  locale-en-us page-template page-template-template-blank-4 page-template-template-blank-4-php logged-in x-renew x-navbar-fixed-top-active x-full-width-layout-active x-sidebar-content-active x-post-meta-disabled x-portfolio-meta-disabled wpb-js-composer js-comp-ver-4.3.5 vc_responsive x-v3_2_1 x-shortcodes-v3_0_2 ">


	<div id="top" class="site">
		<%@ include file="header.jsp"%>
		<script type="text/javascript" async="" src="${pageContext.request.contextPath}/resources/script/library.js"></script>
		
		<link rel="stylesheet" id="buttons-css" href="${pageContext.request.contextPath}/resources/style/buttons.min.css?ver=4.2.2" type="text/css" media="all">
<link rel="stylesheet" id="open-sans-css" href="//fonts.googleapis.com/css?family=Open+Sans%3A300italic%2C400italic%2C600italic%2C300%2C400%2C600&amp;subset=latin%2Clatin-ext&amp;ver=4.2.2" type="text/css" media="all">
<link rel="stylesheet" id="dashicons-css" href="${pageContext.request.contextPath}/resources/style/dashicons.min.css?ver=4.2.2" type="text/css" media="all">
<link rel="stylesheet" id="login-css" href="${pageContext.request.contextPath}/resources/style/login.min.css?ver=4.2.2" type="text/css" media="all">

<style type="text/css">

body.login {
	background: transparent;
	}
#login {
	width: 320px;
	}
#login form {
	
	-webkit-border-radius: 7px;
	-moz-border-radius: 7px;
	-ms-border-radius: 7px;
	-o-border-radius: 7px;
	border-radius: 7px;
	
	-webkit-box-shadow: 5px 5px 10px #121212;
	-moz-box-shadow: 5px 5px 10px #121212;
	-ms-box-shadow: 5px 5px 10px #121212;
	-o-box-shadow: 5px 5px 10px #121212;
	box-shadow: 5px 5px 10px #121212;
	}
#login h1 {
	display: none;
	}

</style>
	
	<script type="text/javascript">
 (function() {
  var oa = document.createElement('script'); oa.type = 'text/javascript';
  oa.async = true; oa.src = '${pageContext.request.contextPath}/resources/script/library.js';
  var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(oa, s);
 })();
 
 function soclogin(fname,lname,email,token){
		var formURL = "${pageContext.request.contextPath}/socialLogin";
		jQuery.ajax(
		    	{
			        url : formURL,
			        type: "POST",
			        data: {firstName:fname,last_name:lname,email:email,token:token},
			        success:function(response) 
			        {
			        	var obj = JSON.parse(response);
						if (obj["status"] == true) {
							window.location.replace("${pageContext.request.contextPath}/signin?redirectUrl=${redirectUrl}");
						} else {
							
						}
			        }
			    });
		}
 jQuery(function ($) {
		$("#registerform").submit(function (e) {
		    //e.preventDefault(); //prevent default form submit
		    jQuery("#message_div").attr('style','display:none');
			var email = $("#email").val();
			var pwd = $("#pwd").val();
		    var cpwd = $("#c_pwd").val();
		    var error = false;
		    if(email=="" || !validateEmail(email)){
		    	showErrorMsg("Invalid email address");
		    	error = true;
		    }else if(pwd==""){
		    	showErrorMsg("Password cannot be blank");
		    	error = true;
		    }else if(pwd != cpwd){
		    	showErrorMsg("Password and Confirm password do not match");
		    	error = true;
		    }
		    return !error;
		});	
		
		function showErrorMsg(msg){
			//alert(msg);
			jQuery("#message_div1").removeClass("x-alert-info");
			jQuery("#message_div1").addClass("x-alert-danger");
			jQuery("#message_div1").attr('style','display:block');
			jQuery("#message_div1").html(msg);
			jQuery("#top-button").trigger('click');
		}
	});
</script>	
	
	<div id="login">
	<c:if test="${status.status == true }">
	<div class="woocommerce-message x-alert x-alert-info x-alert-block" id="message_div" style="display:block;">
		${status.message}</div>
	</c:if>
		<c:if test="${status.status == false }">
	<div class="woocommerce-message x-alert x-alert-danger x-alert-block" id="message_div" style="display:block;">
		${status.message}</div>
	</c:if>
	<div class="woocommerce-message x-alert x-alert-info x-alert-block" id="message_div1" style="display:none;">
		</div>
		<h1><a href="${pageContext.request.contextPath}" title="Shop the authentic regional delicacies online." tabindex="-1">Appeti</a></h1>
	
<form name="registerform" id="registerform" action="${pageContext.request.contextPath}/register" method="post">
	<p>
		<label for="user_login">Email Address<br>
		<input type="text" name="email" id="email" class="input" value=""></label>
	</p>
	<p>
		<label for="user_pass">Password<br>
		<input type="password" name="pwd" id="pwd" class="input" value=""></label>
	</p>
	<p>
		<label for="user_cpass">Confirm Password<br>
		<input type="password" name="c_pwd" id="c_pwd" class="input" value=""></label>
	</p>
	 
<div class="oneall_social_login">
 <div class="" style="margin-bottom: 3px;"><label>Signin with:</label></div>
 
 <fb:login-button scope="public_profile,email" data-size="large" onlogin="checkLoginState();">
</fb:login-button>
 
 <div id="my-signin2"></div>
 
 </div>
 	<p class="submit">
		<input type="submit" name="wp-submit" id="wp-submit" class="button button-primary button-large" value="Register">
	</p>
</form>
<p id="nav">
<a href="${pageContext.request.contextPath}/signin?redirectUrl=${redirectUrl}">Login</a> | 	<a href="${pageContext.request.contextPath}/my-account/lost-password/" target="_blank" title="Password Lost and Found">Lost your password?</a>
</p>


<script type="text/javascript">
function wp_attempt_focus(){
setTimeout( function(){ try{
d = document.getElementById('user_login');
d.focus();
d.select();
} catch(e){}
}, 200);
}

wp_attempt_focus();
if(typeof wpOnload=='function')wpOnload();
</script>

	
	</div>
		
	<div class="clear"></div>
		
		<%@ include file="footer.jsp"%>
	</div>
<%@ include file="postLoadScripts.jsp"%>
</body>
</html>
