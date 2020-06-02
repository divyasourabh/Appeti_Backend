<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div id="x-content-band-2"
	class="x-content-band border-top border-bottom man"
	style="background-color: #fff;">
	<div class="x-container max width">
		<h2 class="h-custom-headline center-text h2 accent">
			<span>Browse by location</span>
		</h2>
		<div id="x-content-band-3" class="x-content-band man"
			style="background-color: transparent; padding-top: 30px; padding-bottom: 30px;">
			<div class="x-container max width"></div>
		</div>
		
		<%@ include file="containerByLocationCss.jsp" %>
		
		<!-- GRID WRAPPER FOR CONTAINER SIZING - HERE YOU CAN SET THE CONTAINER SIZE AND CONTAINER SKIN -->
		<article class="myportfolio-container text-dark" id="locationgrid">

			<!-- THE GRID ITSELF WITH FILTERS, PAGINATION, SORTING ETC... -->
			<div id="esg-grid-2-1" class="esg-grid"
				style="background-color: transparent; padding: 0px 0px 0px 0px; box-sizing: border-box; -moz-box-sizing: border-box; -webkit-box-sizing: border-box;">
				<!-- ############################ -->
				<!-- THE GRID ITSELF WITH ENTRIES -->
				<!-- ############################ -->
				<ul>
					<!-- PORTFOLIO ITEM 27 -->
					
					<c:forEach items="${homeBean.popularNodes}" var="node">
					
					<li
						class="filterall filter-uttar-pradesh eg-woodrowwilson-wrapper eg-post-id-1052"
						data-date="1432222397">
						<!-- THE CONTAINER FOR THE MEDIA AND THE COVER EFFECTS -->
						<div class="esg-media-cover-wrapper">
							<!-- THE MEDIA OF THE ENTRY -->
							<div class="esg-entry-media">
								<img
									src="${node.image.url}"
									alt="${node.image.text}">
							</div>

							<!-- THE CONTENT OF THE ENTRY -->
							<div class="esg-entry-cover">

								<!-- THE COLORED OVERLAY -->
								<div class="esg-overlay esg-fade eg-woodrowwilson-container"
									data-delay="0"></div>

								<div
									class="esg-center eg-post-1052 eg-woodrowwilson-element-0-a esg-slideleft"
									data-delay="0.15">
									<a class="eg-woodrowwilson-element-0 eg-post-1052"
										href="${pageContext.request.contextPath}/shop/node/${node.nodeId}/${fn:replace(fn:toLowerCase(node.nodeName),' ','-')}"
										target="_self">${node.nodeName}</a>
								</div>
								<div
									class="esg-center eg-woodrowwilson-element-1 esg-none esg-clear"
									style="height: 5px; visibility: hidden;"></div>
								<div
									class="esg-center eg-post-1052 eg-woodrowwilson-element-2 esg-zoomfront"
									data-delay="0.25">anemptytextlline</div>
								<div
									class="esg-center eg-woodrowwilson-element-3 esg-none esg-clear"
									style="height: 5px; visibility: hidden;"></div>
								<div
									class="esg-center eg-post-1052 eg-woodrowwilson-element-4-a esg-slideright"
									data-delay="0.2">
									<a class="eg-woodrowwilson-element-4 eg-post-1052"
										href="${pageContext.request.contextPath}/shop/node/${node.parentId}/${fn:replace(fn:toLowerCase(node.parentName),' ','-')}"
										title="View all products in ${node.parentName}" rel="category tag">${node.parentName}</a>
								</div>
							</div>
							<!-- END OF THE CONTENT IN THE ENTRY -->
						</div> <!-- END OF THE CONTAINER FOR THE MEDIA AND COVER/HOVER EFFECTS -->

					</li>
					
					
					</c:forEach>					
					
					<!-- END OF PORTFOLIO ITEM -->
				</ul>
				<!-- ############################ -->
				<!--      END OF THE GRID         -->
				<!-- ############################ -->
			</div>
			<!-- END OF THE GRID -->

		</article>
		<!-- END OF THE GRID WRAPPER -->

		<div class="clear"></div>
		<%@ include file="containerByLocationScript.jsp" %>
	</div>
</div>