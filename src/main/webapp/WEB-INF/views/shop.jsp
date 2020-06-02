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
    <link href="${pageContext.request.contextPath}/resources/css/jquery.bxslider.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
    
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="${pageContext.request.contextPath}/resources/js/html5shiv.js"></script>
      <script src="${pageContext.request.contextPath}/resources/js/respond.min.js"></script>
    <![endif]-->
    <script src="${pageContext.request.contextPath}/resources/js/modernizr.custom.js"></script>
</head>

<body>

<%@ include file="header.jsp"%>

<%@ include file="shopContent.jsp"%>


<%@ include file="footer.jsp"%>

<%@ include file="shopModals.jsp"%>

<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery.imagesloaded.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/owl.carousel.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery.bxslider.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/script.js"></script>

<script src="${pageContext.request.contextPath}/resources/js/jquery.bxslider.js"></script>
<script>
/*$('.bxslider').bxSlider({
  pagerCustom: '#bx-pager',
  auto: true
});*/
$(document).on('click', '.js-quickView', function(){
	  $('.bx-viewport').css("height","336"); 
	  $('.bxslider').bxSlider({
		  controls: true,
		  pagerCustom: '#bx-pager',
		  auto: true
		});
});


$(window).scroll(function() {
	if ($(this).scrollTop() > 116){  
		$('.header').addClass("sticky");
	  }
	  else{
		$('.header').removeClass("sticky");
	  }
});


</script>

</body>
</html>