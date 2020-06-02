<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<a class="x-scroll-top right fade in" id="top-button" href="#top" title="Back to Top">
	<i class="x-icon-angle-up"></i>
</a>
<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');
  ga('create', 'UA-60475304-1', 'auto');
  ga('send', 'pageview');
</script>
<c:if test="${is_app != true}">
<footer class="x-colophon top" role="contentinfo">
	<div class="x-container max width">

		<div class="x-column x-md x-1-2"></div>
		<div class="x-column x-md x-1-2 last"></div>
	</div>
</footer>

<footer class="x-colophon bottom" role="contentinfo">
	<div class="x-container max width">

		<div class="x-social-global">
			<a href="https://www.facebook.com/TheAppeti" class="facebook"
				title="Facebook" target="_blank"><i
				class="x-icon-facebook-square"></i></a><a
				href="https://twitter.com/TheAppeti" class="twitter" title="Twitter"
				target="_blank"><i class="x-icon-twitter-square"></i></a><a
				href="https://www.linkedin.com/company/appeti" class="linkedin"
				title="LinkedIn" target="_blank"><i
				class="x-icon-linkedin-square"></i></a><a
				href="https://www.pinterest.com/niteshkp/appeti/" class="pinterest"
				title="Pinterest" target="_blank"><i
				class="x-icon-pinterest-square"></i></a>
		</div>

		<ul id="menu-footer-menu" class="x-nav">
			<li id="menu-item-516"
				class="menu-item menu-item-type-post_type menu-item-object-page current-menu-item page_item page-item-37 current_page_item menu-item-516"><a
				href="${pageContext.request.contextPath}">Home</a></li>
			<li id="menu-item-515"
				class="menu-item menu-item-type-post_type menu-item-object-page menu-item-515"><a
				href="${pageContext.request.contextPath}/about">About Us</a></li>
			<li id="menu-item-514"
				class="menu-item menu-item-type-post_type menu-item-object-page menu-item-514"><a
				href="${pageContext.request.contextPath}/appet-i-team">Team</a></li>
			<li id="menu-item-514"
				class="menu-item menu-item-type-post_type menu-item-object-page menu-item-514"><a
				href="${pageContext.request.contextPath}/contact-us">Contact Us</a></li>
			<li id="menu-item-531"
				class="menu-item menu-item-type-post_type menu-item-object-page menu-item-531"><a
				href="${pageContext.request.contextPath}/terms-of-service">Terms
					of Service</a></li>
			<li id="menu-item-570"
				class="menu-item menu-item-type-post_type menu-item-object-page menu-item-570"><a
				href="${pageContext.request.contextPath}/privacy-policy">Privacy
					Policy</a></li>
			<li id="menu-item-667"
				class="menu-item menu-item-type-post_type menu-item-object-page menu-item-667"><a
				href="${pageContext.request.contextPath}/we-are-hiring">We are
					hiring!</a></li>
			<li id="menu-item-667"
				class="menu-item menu-item-type-post_type menu-item-object-page menu-item-667"><a
				href="http://www.coupondunia.in" target="_blank">Find Our Coupons on CouponDunia</a></li>
		</ul>
		<div class="x-colophon-content">
			<p>
				Copyrights &copy;<a href="www.appeti.in" title="Appeti">Appet-i</a> 2015
			</p>
		</div>

	</div>
</footer>
</c:if>