<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="x-visibility x-hidden-phone">
	<div id="x-content-band-4" class="x-content-band bg-image man"
		data-x-element="content_band"
		data-x-params="{&quot;type&quot;:&quot;image&quot;,&quot;parallax&quot;:false}"
		style="background-image: url(//theme.co/media/x-home-5-bg-timeline-gray-right-comp.png); background-color: #EBEBE0;">
		<div class="x-container max width">
			<div class="x-column x-sm x-1-2" style="">
				<h4>Western India</h4>
				<hr class="x-hr">
				If the desert region of <strong>Rajasthan</strong> lends itself to
				erstwhile palaces, Manganiyar musicians, Kalbeliya dancers with
				their many-coloured skirts and of course ghewar and ghee-laden
				sweets, <strong>Gujarat</strong>, the Jewel of West India, is known
				for its well-developed infrastructure, chaniya choli and garba and
				of course its mouth-watering farsan. The land of 'Mi Maratha' i.e. <strong>Maharashtra</strong>
				holds its own with its prevalence of Bollywood, its port culture,
				its Ellora caves, its Natya Sangeet and its unique food from the
				ubiquitous solkadi to the lovely Bhakhri as well as delicious
				pickles and condiments.
				<p></p>
				<p>
					<em>West India, in its many-flavored splendor truly caters to
						diverse palettes-from spicy to sweet, from crunchy to doughy, and
						everything in between-and that is the beauty of it.</em>
				</p>
				<c:forEach items="${homeBean.westernNodes}" var="node">
				<a class="x-btn x-btn-transparent x-btn-rounded x-btn-mini"
					href="${pageContext.request.contextPath}/shop/node/${node.nodeId}/${fn:replace(fn:toLowerCase(node.nodeName),' ','-')}" 
					title="${node.nodeName }" data-options="thumbnail: ''">
					<span style="color: #000000;">${node.nodeName}</span>
				</a>
				</c:forEach>
				
				<hr class="x-gap" style="margin: 3.313em 0 0 0;">
			</div>
			<div class="x-column x-sm center-text x-1-2 last"
				style="right: 0px; opacity: 1;" data-x-element="column"
				data-x-params="{&quot;fade&quot;:true,&quot;animation&quot;:&quot;in-from-right&quot;}"
				data-fade="true">
				<img class="x-img mbn"
					src="resources/images/West_India.png"
					alt="West India">
				<hr class="x-gap" style="margin: 4.313em 0 0 0;">
			</div>
		</div>
	</div>
</div>

<div class="x-visibility x-visible-phone">
	<div id="x-content-band-5" class="x-content-band center-text man"
		style="background-color: #EBEBE0;">
		<div class="x-container max width">
			<h4>Western India</h4>
			<p>
				If the desert region of <strong>Rajasthan</strong> lends itself to
				erstwhile palaces, Manganiyar musicians, Kalbeliya dancers with
				their many-coloured skirts and of course ghewar and ghee-laden
				sweets, <strong>Gujarat</strong>, the Jewel of West India, is known
				for its well-developed infrastructure, chaniya choli and garba and
				of course its mouth-watering farsan. The land of 'Mi Maratha' i.e. <strong>Maharashtra</strong>
				holds its own with its prevalence of Bollywood, its port culture,
				its Ellora caves, its Natya Sangeet and its unique food from the
				ubiquitous solkadi to the lovely Bhakhri as well as delicious
				pickles and condiments.
			</p>
			<p>
				<em>West India, in its many-flavored splendor truly caters to
					diverse palettes-from spicy to sweet, from crunchy to doughy, and
					everything in between-and that is the beauty of it.</em><br> <a
					class="x-btn x-btn-real x-btn-rounded x-btn-mini" href="#example"
					title="Example" data-options="thumbnail: ''">Tell me more…</a> <img
					class="x-img mbn"
					src="resources/images/West_India.png"
					alt="West India">
			</p>
		</div>
	</div>
</div>