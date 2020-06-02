<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery.imagesloaded.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/vegas.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/owl.carousel.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery.validate.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/script.js"></script>
<script>
	$(".header").vegas({
		slides: [
			{ src: "${pageContext.request.contextPath}/resources/images/homepagecarousal/Appeti_karachi_Biscuits.jpg" },
			{ src: "${pageContext.request.contextPath}/resources/images/homepagecarousal/Appeti_bombay_tiffany.jpg" },
			{ src: "${pageContext.request.contextPath}/resources/images/homepagecarousal/Appeti_homepage_carousal_dhodha_house.jpg" },
			{ src: "${pageContext.request.contextPath}/resources/images/homepagecarousal/Appeti_homepage_carousal.jpg" },
			{ src: "${pageContext.request.contextPath}/resources/images/homepagecarousal/Appeti_homepage_carousal_ooty_chocolates.jpg" }
		]
	});
	
	
	var lastScrollTop = 0;
	$(window).scroll(function(event){
	   var st = $(this).scrollTop();
	   if (st > lastScrollTop){
		   var divHeight = parseInt($('.header').height());
			if ($(this).scrollTop() > divHeight){  
			$('.header').addClass("sticky");
			$('.header').addClass("min-height-home");
			$('.content').css("margin-top", '650px');
			$('div.tab-box ').removeClass("tab-box-homebg");
			}
	   } else {
		 $('.header').removeClass("sticky");
		 $('.content').css("margin-top", '0px');
		 $('.header').removeClass("min-height-home");
		 $('div.tab-box ').addClass("tab-box-homebg");
	   }
	   lastScrollTop = st;
	});
	
</script>
