<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page session="true"%>

<!DOCTYPE html>
<!--[if IE 9]><html class="no-js ie9" lang="en-US"><![endif]-->
<!--[if gt IE 9]><!-->
<html class="no-js" lang="en-US">
<!--<![endif]--><head>
<title>Processing-Order - Appeti</title>
</head>
<body
	class="home page page-id-37 page-template page-template-template-blank-4 page-template-template-blank-4-php logged-in x-renew x-navbar-fixed-top-active x-full-width-layout-active x-sidebar-content-active x-post-meta-disabled x-portfolio-meta-disabled wpb-js-composer js-comp-ver-4.3.5 vc_responsive x-v3_2_1 x-shortcodes-v3_0_2">


	<div id="top" class="site">
	<%@ include file="header.jsp"%>
<script type="text/javascript">
jQuery(function($) {

    $.blockUI({
        message: "Thank you for your order. We are now redirecting you to CcAvenue to make payment.",
        baseZ: 99999,
        overlayCSS:
        {
            background: "#fff",
            opacity: 0.6
        },
        css: {
            padding:        "20px",
            zindex:         "9999999",
            textAlign:      "center",
            color:          "#555",
            border:         "3px solid #aaa",
            backgroundColor:"#fff",
            cursor:         "wait",
            lineHeight:     "24px",
        }
    });
    
    $('#nonseamless').submit(function (e) {
        var form = this;
        e.preventDefault();
        setTimeout(function () {
            form.submit();
        }, 2000);
    });
    jQuery("#nonseamless").submit();
});
</script>
		<form id="nonseamless" method="post" name="redirect" action="${formUrl}"> 
		<input type="hidden" id="encRequest" name="encRequest" value="${encRequest}">
		<input type="hidden" name="access_code" id="access_code" value="${access_code}">
		<input type="hidden" id="hash" name="hash" value="${hash}">
		<c:forEach items="${params}" var="entry">
			<input type="hidden" id="${entry.key}" name="${entry.key}" value="${entry.value}">
		</c:forEach>
		</form>
		
	</div>
<%@ include file="postLoadScripts.jsp"%>
</body>
</html>
