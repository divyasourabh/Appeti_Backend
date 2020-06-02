<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

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

<script type='text/javascript' src='${pageContext.request.contextPath}/resources/script_old/jquery.js?ver=1.11.2'></script>

<div class="content1">
	<div class="container">
    	<div class="breadcrumb-box">
            <ol class="breadcrumb">
              <li><a href="${pageContext.request.contextPath}">Home</a></li>
              <li class="active">Testimonials</li>
            </ol>
    	</div>
        <div class="row blog-post-view">
        	<div class="col-lg-12 col-sm-12 col-md-12 col-xs-12 green postContent">
            	<div class="blog-img-big">
                	<img src="${pageContext.request.contextPath}/resources/images/banner-individual.jpg" width="1496" height="540">
                </div>
        	</div>
            
            
          </div>
        <div class="row orderDetailsAlert" id="alert-div" style="display:none;">  <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
          <div class="alert alert-success alert-dismissible" role="alert" id="alert-type">
  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
  <span id="alert_msg"></span>
</div></div>
</div>
        <section class="rating-review-box">
       	  <%if(session != null && session.getAttribute("user") != null){%>
       	  <h1 class="main-heading">Testimonials <a href="#" class="dropdown-toggle" data-toggle="modal" data-target=".commentModals">Write a Testimonial</a></h1>
          <%}else{%>
		  <h1 class="main-heading">Testimonials <a href="#" class="dropdown-toggle" onclick="$('#login-window').click();" data-toggle="modal" data-target="">Write a Testimonial</a></h1>
          <%}%>
			
            <ul class="review-list">
            	
            	<c:forEach items="${mktBean.testimonials}" var="testimonial">
            	<c:set var="url" value="${testimonial.imageUrl}"></c:set>
                    	<c:if test="${empty testimonial.imageUrl}">	<c:set var="url" value="resources/images/no-product-img.jpg"></c:set> </c:if>
                    	
            	
            	<li>
                	<div class="imageClass"><a href="javascript:void(0);" class="pull-left">
              <img alt="" src="${pageContext.request.contextPath}/${url}" width="103" height="103" class="media-object thumbnail">
            </a></div>
                  <div class="review-info marginLeft140">
                    	<span class="box-arrow"></span>
                    	<h2 class="testHeadName">${testimonial.userName}</h2>
                      <p>${testimonial.description}</p>
                    </div>
                </li>
                </c:forEach>
             </ul>
        </section>
        
        
    </div><!--end of container-->
</div><!--end of content-->

<%@ include file="footer.jsp"%>

<%@ include file="modalTestimonial.jsp"%>

<a href="#" class="scrollup"><i class="icon-angle-up"></i></a>
<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery.imagesloaded.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/owl.carousel.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery.bxslider.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/script.js"></script>
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
<style>
.commentModals .modal-dialog {	
	width:500px;
}
.commentModals .modal-dialog textarea {
	height:120px;
}
</style>
</body>
</html>