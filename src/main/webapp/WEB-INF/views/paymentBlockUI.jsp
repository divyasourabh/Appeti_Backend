<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page session="true"%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/favicon.png">

    <title>Appet-i</title>
	<link href='https://fonts.googleapis.com/css?family=Open+Sans:300,400,500,700,400italic' rel='stylesheet' type='text/css'>
    <!-- Bootstrap core CSS -->
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/owl.carousel.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
    
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="${pageContext.request.contextPath}/resources/js/html5shiv.js"></script>
      <script src="${pageContext.request.contextPath}/resources/js/respond.min.js"></script>
    <![endif]-->
    <script src="${pageContext.request.contextPath}/resources/js/modernizr.custom.js"></script>
</head>

<body>
<script type='text/javascript' src='${pageContext.request.contextPath}/resources/script_old/jquery.js?ver=1.11.2'></script>


<script type="text/javascript">
jQuery(function($) {
	$('#nonseamless').submit(function (e) {
        var form = this;
        e.preventDefault();
        setTimeout(function () {
            form.submit();
        }, 4000);
    });
    jQuery("#nonseamless").submit();
});
</script>
	<div>	
		<form id="nonseamless" method="post" name="redirect" action="${formUrl}"> 
		<input type="hidden" id="encRequest" name="encRequest" value="${encRequest}">
		<input type="hidden" name="access_code" id="access_code" value="${access_code}">
		<input type="hidden" id="hash" name="hash" value="${hash}">
		<c:forEach items="${params}" var="entry">
			<input type="hidden" id="${entry.key}" name="${entry.key}" value="${entry.value}">
		</c:forEach>
		</form>
		
	</div>

<div class="content1">
	<div class="container">
        <div class="container-popup"> <img class="margin-bottom-20" src="${pageContext.request.contextPath}/resources/images/Loading_Animation.gif">
          <h2 class="failureHeaderLine">Please wait while we process your request</h2>
          <p class="marginT20">Thank you for your order. We are now redirecting you to CcAvenue to make payment.</p>
        </div>      
    </div>   
</div>

<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery.imagesloaded.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/owl.carousel.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/script.js"></script>

</body>
</html>