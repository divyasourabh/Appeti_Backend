<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="x-visibility x-hidden-phone">
	<div id="x-content-band-8" class="x-content-band bg-image man"
		data-x-element="content_band"
		data-x-params="{&quot;type&quot;:&quot;image&quot;,&quot;parallax&quot;:false}"
		style="background-image: url(//theme.co/media/x-home-5-bg-timeline-gray-right-comp.png); background-color: #33CCFF;">
		<div class="x-container max width">
			<div class="x-column x-sm x-1-2" style="">
				<h4>Southern India</h4>
				<hr class="x-hr">
				Presenting a unique way of life so dissimilar from each other yet so
				evocatively southern, states like <strong>Karnataka</strong>,
				Telangana, <strong>Andhra Pradesh</strong>, <strong>Kerala</strong>
				and <strong>Tamil Nadu</strong> are known for their temples and
				traditions in equal measure. The fire-worshipping devotees of Tamil
				Nadu meld with its IT culture, its mellifluous music of nagasvara
				and tavil is as harmonic as its curry leaves and mustard in dishes
				like tamarind rice and sweet pongal. Known for its coconut, tea,
				coffee, cashew and spices, Kerala is a haven on the Malabar coast
				and famous for its Post Colonial architecture, fisheries and temple
				festivals like Thrissur Pooram as it is for its cuisine, which
				includes lovely seafood among other delicacies. The birthplace of
				some of the most formidable empires of ancient India, Karnataka is
				home to amazing Kannad authors and musicians of the Carnatic
				tradition, both of which are known the world over. Its Mysore style
				paintings are as legendary as its sweet offerings like Mysore pak
				and Dharwad pedha!
				<p></p>
				<hr class="x-gap" style="margin: 2.313em 0 0 0;">
				<c:forEach items="${homeBean.southernNodes}" var="node">
				<a class="x-btn x-btn-transparent x-btn-rounded x-btn-mini"
					href="${pageContext.request.contextPath}/shop/node/${node.nodeId}/${fn:replace(fn:toLowerCase(node.nodeName),' ','-')}" 
					title="${node.nodeName }" data-options="thumbnail: ''">
					<span style="color: #000000;">${node.nodeName}</span>
				</a>
				</c:forEach>
			</div>
			<div class="x-column x-sm center-text x-1-2 last"
				style="right: 0px; opacity: 1;" data-x-element="column"
				data-x-params="{&quot;fade&quot;:true,&quot;animation&quot;:&quot;in-from-right&quot;}"
				data-fade="true">
				<img class="x-img mbn"
					src="resources/images/South_India.png"
					alt="South India">
			</div>
		</div>
	</div>
</div>

<div class="x-visibility x-visible-phone">
	<div id="x-content-band-9" class="x-content-band center-text man"
		style="background-color: #33CCFF;">
		<div class="x-container max width">
			<h4>Southern India</h4>
			<p>
				Presenting a unique way of life so dissimilar from each other yet so
				evocatively southern, states like <strong>Karnataka</strong>,
				Telangana, <strong>Andhra Pradesh</strong>, <strong>Kerala</strong>
				and <strong>Tamil Nadu</strong> are known for their temples and
				traditions in equal measure. The fire-worshipping devotees of Tamil
				Nadu meld with its IT culture, its mellifluous music of nagasvara
				and tavil is as harmonic as its curry leaves and mustard in dishes
				like tamarind rice and sweet pongal. Known for its coconut, tea,
				coffee, cashew and spices, Kerala is a haven on the Malabar coast
				and famous for its Post Colonial architecture, fisheries and temple
				festivals like Thrissur Pooram as it is for its cuisine, which
				includes lovely seafood among other delicacies. The birthplace of
				some of the most formidable empires of ancient India, Karnataka is
				home to amazing Kannad authors and musicians of the Carnatic
				tradition, both of which are known the world over. Its Mysore style
				paintings are as legendary as its sweet offerings like Mysore pak
				and Dharwad pedha!<br> <img class="x-img mbn"
					src="resources/images/South_India.png"
					alt="South India"> 
					<c:forEach items="${homeBean.northernNodes}" var="node">
				<a class="x-btn x-btn-transparent x-btn-rounded x-btn-mini"
					href="${pageContext.request.contextPath}/shop/node/${node.nodeId}/${fn:replace(fn:toLowerCase(node.nodeName),' ','-')}" 
					title="${node.nodeName }" data-options="thumbnail: ''">
					<span style="color: #000000;">${node.nodeName}</span>
				</a>
				</c:forEach>
			</p>
		</div>
	</div>
</div>
