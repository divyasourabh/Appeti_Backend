<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="x-visibility x-hidden-phone">
	<div id="x-content-band-6" class="x-content-band bg-image"
		data-x-element="content_band"
		data-x-params="{&quot;type&quot;:&quot;image&quot;,&quot;parallax&quot;:false}"
		style="background-image: url(//theme.co/media/x-home-5-bg-timeline-gray-left-comp.png); background-color: #FFCC00; margin: -80px 0 0;">
		<div class="x-container max width">
			<div class="x-column x-sm center-text x-1-2"
				style="left: 0px; opacity: 1;" data-x-element="column"
				data-x-params="{&quot;fade&quot;:true,&quot;animation&quot;:&quot;in-from-left&quot;}"
				data-fade="true">
				<img class="x-img mbn"
					src="resources/images/North_India.png"
					alt="North India">
			</div>
			<div class="x-column x-sm right-text x-1-2 last" style="">
				<h4>Northern India</h4>
				<hr class="x-hr">
				While <strong>Punjab</strong>, the Land of Five Rivers, ushers in
				images of the Golden Temple, and of course, makki di roti and
				rajma-chawal, it is its people's large hearts and hospitality that
				stays with you. <strong>Jammu and Kashmir</strong> will forever be
				etched in our hearts as the place where Chakri (traditional music)
				and Roof (traditional dance performed by boys) are enjoyed in
				abundance, where one can sip on Noon or Sheer Chai (salty or sweet
				tea), enjoy mutton in all its glory and taste flaky breads like the
				sheermal and tsot. Home to the famous Kumbh Mela, <strong>Uttar
					Pradesh</strong> is an absolute delight when it comes to food, offering
				everything from Lucknowi chaat to Banarasi paan. Full of gusto, <strong>Haryana</strong>‘s
				people celebrate festivals with fanfare and, like the rest of the
				North, place great emphasis on good food.
				<p></p>
				<hr class="x-gap" style="margin: 2.313em 0 0 0;">
				<c:forEach items="${homeBean.northernNodes}" var="node">
				<a class="x-btn x-btn-transparent x-btn-rounded x-btn-mini"
					href="${pageContext.request.contextPath}/shop/node/${node.nodeId}/${fn:replace(fn:toLowerCase(node.nodeName),' ','-')}" 
					title="${node.nodeName }" data-options="thumbnail: ''">
					<span style="color: #000000;">${node.nodeName}</span>
				</a>
				</c:forEach>
				
			</div>
		</div>
	</div>
</div>

<div class="x-visibility x-visible-phone">
	<div id="x-content-band-7" class="x-content-band center-text man"
		style="background-color: #FFCC00;">
		<div class="x-container max width">
			<h4>Northern India</h4>
			<p>
				While <strong>Punjab</strong>, the Land of Five Rivers, ushers in
				images of the Golden Temple, and of course, makki di roti and
				rajma-chawal, it is its people's large hearts and hospitality that
				stays with you. <strong>Jammu and Kashmir</strong> will forever be
				etched in our hearts as the place where Chakri (traditional music)
				and Roof (traditional dance performed by boys) are enjoyed in
				abundance, where one can sip on Noon or Sheer Chai (salty or sweet
				tea), enjoy mutton in all its glory and taste flaky breads like the
				sheermal and tsot. Home to the famous Kumbh Mela, <strong>Uttar
					Pradesh</strong> is an absolute delight when it comes to food, offering
				everything from Lucknowi chaat to Banarasi paan. Full of gusto, <strong>Haryana</strong>'s
				people celebrate festivals with fanfare and, like the rest of the
				North, place great emphasis on good food.
			</p>
			<img class="x-img mbn"
				src="resources/images/North_India.png"
				alt="Alt Text"> 
				<c:forEach items="${homeBean.northernNodes}" var="node">
				<a class="x-btn x-btn-transparent x-btn-rounded x-btn-mini"
					href="${pageContext.request.contextPath}/shop/node/${node.nodeId}/${fn:replace(fn:toLowerCase(node.nodeName),' ','-')}" 
					title="${node.nodeName }" data-options="thumbnail: ''">
					<span style="color: #000000;">${node.nodeName}</span>
				</a>
				</c:forEach>
		</div>
	</div>
</div>
