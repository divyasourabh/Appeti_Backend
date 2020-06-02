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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/images/plugins/parallax-slider/css/parallax-slider.css" type="text/css" />
    
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="${pageContext.request.contextPath}/resources/js/html5shiv.js"></script>
      <script src="${pageContext.request.contextPath}/resources/js/respond.min.js"></script>
    <![endif]-->
    <script src="${pageContext.request.contextPath}/resources/js/modernizr.custom.js"></script>
    <script type='text/javascript' src='${pageContext.request.contextPath}/resources/script_old/jquery.js?ver=1.11.2'></script>
    
</head>

<body>

<%@ include file="header.jsp"%>


<div class="content1">
	<div class="container">
    	<div class="breadcrumb-box">
            <ol class="breadcrumb">
              <li><a href="#">Home</a></li>
              <li class="active">Blog</li>
            </ol>
    	</div>
        
        <h2 class="product-name">Bong Connection<span>09/29/2015,  Posted By Admin</span></h2>
        
        <div class="row blog-post-view">
        	<div class="col-lg-8 col-sm-8 col-md-8 col-xs-12 green postContent">
            	<div class="blog-img-big">
                	<div class="slider-inner">
    <div id="da-slider" class="da-slider">
        <div class="da-slide">
            <h2><i>BONG CONNECTION</i> <br /></h2>
            <div class="da-img"></div>
        </div>
        <div class="da-slide">
            <h2><i>BONG CONNECTION</i> <br /></h2>
            
        </div>
        <nav class="da-arrows">
            <span class="da-arrows-prev"></span>
            <span class="da-arrows-next"></span>		
        </nav>
    </div><!--/da-slider-->
</div>
                </div>
                <div class="blog-contents-method">
                
                <h5></h5>
                <p>For all the people who have a sweet tooth, well the season for you all is surely knocking at the 

door. We all know that Durga Puja kicks off the festive season in spirit with the most important 

element of all the Indian festivals - Food! Durga Puja is a time when Bengalis eat, dress up and 

celebrate in Bengal and all parts of the world. When it comes to food, Bengali genes are 

indisputably to be blamed for the sweet cravings. Top 10 sweetmeats have already been 

highlighted in our previous post, so now let’s throw light upon the emotions attached with all 

the people who hail from Bengal but are away (due to whatever reason), during all the 

festivities. 

Durga Puja is an occasion that is the most loved and the most missed for all the Bengalis out of 

Bengal. Other cities surely have their own Durga Pujas celebration here and there, but the 

devoutness that Bengal puts on during the five-day long festival is nowhere to be found. During 

Durga Puja, not just Bengalis, but the entire city gorges on traditional Bengali mithaai. 

Though Appeti cannot deliver “Bengal” to all the people away from home, we here offer a 

doorway which will surely kill the distance between you and the famous sweets of Bengal. You 

can relish on mithaais like Rasgulla by the famous K C DAS, Jal Bhora, Kaju Honton, Elisher Petti, 

Khirer Roll etc by the renowned Hindustan Sweets & Kadapak, Chandrapuli, Monopuli by the 

legendary Girish Chandra Dey & Nakur Chandra Nandy, via us, Appeti. 

The glitz, glamour and charm of Durga Puja simply doesn’t change, inspite of all the years that 

have gone by, or even keeping in mind the distance, hundreds and hundreds of miles that try to 

dispirit one. The feel of Pujas is what we want to brighten and enhance by bringing all the 

yummy “puja” delicacies to your doorstep!</p>
                </div>
            </div>
            
            <%@ include file="newsletterRight.jsp"%>
            
          </div>
          
          
        
        
    </div><!--end of container-->
</div><!--end of content-->






<%@ include file="footer.jsp"%>


<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery.imagesloaded.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/owl.carousel.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/script.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/images/plugins/parallax-slider/js/modernizr.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/images/plugins/parallax-slider/js/jquery.cslider.js"></script> 

<script>
/*$('.bxslider').bxSlider({
  pagerCustom: '#bx-pager',
  auto: true
});*/
jQuery(document).ready(function() {
  	Index.initParallaxSlider();
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
