<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div id="x-content-band-10"
	class="x-content-band border-top border-bottom man"
	style="background-color: #fff;">
	<div class="x-container max width">
		<h2 class="h-custom-headline center-text h2 accent">
			<span>Popular Products</span>
		</h2>
		<div id="x-content-band-11" class="x-content-band man"
			style="background-color: transparent; padding-top: 30px; padding-bottom: 30px;">
			<div class="x-container max width"></div>
		</div>
		<!-- THE ESSENTIAL GRID 2.0.9 -->
		<%@ include file="containerProductsCss.jsp"%>

		<!-- GRID WRAPPER FOR CONTAINER SIZING - HERE YOU CAN SET THE CONTAINER SIZE AND CONTAINER SKIN -->
		<article class="myportfolio-container flat-dark" id="productgrid"
			style="position: relative; z-index: 0; min-height: 100px; height: auto;">

			<!-- THE GRID ITSELF WITH FILTERS, PAGINATION, SORTING ETC... -->
			<div class="esg-container-fullscreen-forcer"
				style="position: relative; left: 0px; top: 0px; width: auto; height: auto;">
				<div id="esg-grid-1-1"
					class="esg-grid esg-layout-even esg-container"
					style="background-color: #ffffff; padding: 10px 10px 10px 10px; box-sizing: border-box; -moz-box-sizing: border-box; -webkit-box-sizing: border-box;">
					<!-- ############################ -->
					<!-- THE GRID ITSELF WITH ENTRIES -->
					<!-- ############################ -->
					<div class="esg-overflowtrick"
						style="width: 100%; height: 805px; overflow: hidden;">
						<ul style="display: block; height: 805px;" class="mainul">
							<!-- PORTFOLIO ITEM 21 -->
							<c:forEach items="${homeBean.popularProducts}" var="product">
							<li
								class="filterall filter-variable filter-maharashtra filter-western filter-mumbai filter-sweets filter-1-kg eg-arthur-wrapper eg-post-id-596 tp-esg-item itemtoshow isvisiblenow"
								data-regular-price="700" data-total-sales=""
								style="opacity: 1; visibility: inherit; transform: matrix3d(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, -0.00083, 0, 0, 0, 1); padding: 0px; border-width: 0px; height: 255px; width: 340px; display: block; top: 0px; left: 0px; transform-origin: center center 0px; z-index: 5;">
								<!-- THE CONTENT PART OF THE ENTRIES -->
								<div class="esg-entry-content eg-arthur-content"
									style="display: none;">
									<div class="esg-content eg-post-596 eg-arthur-element-0-a">
										<a class="eg-arthur-element-0 eg-post-596"
											href="${pageContext.request.contextPath}/product/${product.productId}-${product.ptitleId}/${fn:replace(fn:toLowerCase(product.productName),' ','-')}" target="_self">${product.productName}</a>
									</div>
									<div
										class="esg-content eg-arthur-element-26 esg-none esg-clear"
										style="height: 5px; visibility: inherit; opacity: 1; transform-style: flat; transform: matrix3d(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, -0.00083, 0, 0, 1, 0.999166666666667);"></div>
									<div class="esg-content eg-post-596 eg-arthur-element-1-a">
										<c:forEach items="${product.nodeMap}" var="node">
											<a class="eg-arthur-element-1 eg-post-596"
											href="${pageContext.request.contextPath}/shop/node/${node.key}/${fn:replace(fn:toLowerCase(node.value),' ','-')}"
											rel="tag">${node.value}</a>,
											
										</c:forEach>
										
									</div>
									<div
										class="esg-content eg-arthur-element-31 esg-none esg-clear"
										style="height: 5px; visibility: inherit; opacity: 1; transform-style: flat; transform: matrix3d(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, -0.00083, 0, 0, 1, 0.999166666666667);"></div>
								</div> <!-- END OF CONTENR PART OF THE ENTRIES --> <!-- THE CONTAINER FOR THE MEDIA AND THE COVER EFFECTS -->
								<div class="esg-media-cover-wrapper">
									<!-- THE MEDIA OF THE ENTRY -->
									<div class="esg-entry-media-wrapper"
										style="width: 100%; height: 100%; overflow: hidden; position: relative;">
										<div class="esg-entry-media" style="height: 100%;">
											<img
												src="${product.image.url}"
												alt="${product.image.text}"
												style="top: 0%; left: 0%; width: auto; height: 101%; visibility: visible; display: block; position: absolute;">
										</div>
									</div>

									<!-- THE CONTENT OF THE ENTRY -->
									<div class="esg-entry-cover esg-fade" data-delay="0"
										style="transform-style: flat; visibility: hidden; opacity: 0; height: 255px; transform: matrix3d(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, -0.001, 0, 0, -0.002, 1.000002);">

										<!-- THE COLORED OVERLAY -->
										<div class="esg-overlay esg-fade eg-arthur-container"
											data-delay="0"
											style="visibility: hidden; opacity: 0; transform-style: flat; transform: matrix3d(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, -0.001, 0, 0, -0.002, 1.000002);"></div>

										<div class="esg-cc eec" style="top: 108px;">
											<div
												class="esg-center eg-post-596 eg-arthur-element-28-a esg-flipup"
												data-delay="0.1"
												style="visibility: hidden; opacity: 0; transform-origin: 50% 100% 0px; transform-style: flat; transform: matrix3d(0.8, 0, 0, 0, 0, 0, 0.8, -0.0008, 0, -1, 0, 0, 0, 0, 0.001, 0.999999);">
												<a href="${pageContext.request.contextPath}/product/${product.productId}/${fn:replace(fn:toLowerCase(product.productName),' ','-')}"
													rel="nofollow" data-product_id="596" data-product_sku=""
													class="eg-arthur-element-28 eg-post-596 button add_to_cart_button product_type_variable">Select
													options</a>
											</div>
											<div></div>
										</div>
									</div>
									<!-- END OF THE CONTENT IN THE ENTRY -->
								</div> <!-- END OF THE CONTAINER FOR THE MEDIA AND COVER/HOVER EFFECTS -->

							</li>
							
							</c:forEach>
							<!-- END OF PORTFOLIO ITEM -->
							
						</ul>
					</div>
					<!-- ############################ -->
					<!--      END OF THE GRID         -->
					<!-- ############################ -->
				</div>
			</div>
			<!-- END OF THE GRID -->

			<div class="esg-loader spinner0"
				style="visibility: hidden; opacity: 0;">
				<div class="dot1"></div>
				<div class="dot2"></div>
				<div class="bounce1"></div>
				<div class="bounce2"></div>
				<div class="bounce3"></div>
			</div>
			<div class="esg-relative-placeholder"
				style="width: 100%; height: auto"></div>
		</article>
		<!-- END OF THE GRID WRAPPER -->

		<div class="clear"></div>
		<script type="text/javascript">
function eggbfc(winw,resultoption) {
	var lasttop = winw,
	lastbottom = 0,
	smallest =9999,
	largest = 0,
	samount = 0,
	lamoung = 0,
	lastamount = 0,
	resultid = 0,
	resultidb = 0,
	responsiveEntries = [
						{ width:1400,amount:3},
						{ width:1170,amount:3},
						{ width:1024,amount:3},
						{ width:960,amount:3},
						{ width:778,amount:2},
						{ width:640,amount:2},
						{ width:480,amount:1}
						];
	if (responsiveEntries!=undefined && responsiveEntries.length>0)
		jQuery.each(responsiveEntries, function(index,obj) {
			var curw = obj.width != undefined ? obj.width : 0,
				cura = obj.amount != undefined ? obj.amount : 0;
			if (smallest>curw) {
				smallest = curw;
				samount = cura;
				resultidb = index;
			}
			if (largest<curw) {
				largest = curw;
				lamount = cura;
			}
			if (curw>lastbottom && curw<=lasttop) {
				lastbottom = curw;
				lastamount = cura;
				resultid = index;
			}
		})
		if (smallest>winw) {
			lastamount = samount;
			resultid = resultidb;
		}
		var obj = new Object;
		obj.index = resultid;
		obj.column = lastamount;
		if (resultoption=="id")
			return obj;
		else
			return lastamount;
	}
if ("even"=="even") {
	var coh=0,
		container = jQuery("#esg-grid-1-1");
	var	cwidth = container.width(),
		ar = "4:3",
		gbfc = eggbfc(jQuery(window).width(),"id"),
	row = 7;
ar = ar.split(":");
aratio=parseInt(ar[0],0) / parseInt(ar[1],0);
coh = cwidth / aratio;
coh = coh/gbfc.column*row;
	var ul = container.find("ul").first();
	ul.css({display:"block",height:coh+"px"});
}
var essapi_1;
jQuery(document).ready(function() {
	essapi_1 = jQuery("#esg-grid-1-1").tpessential({
        gridID:1,
        layout:"even",
        forceFullWidth:"off",
        lazyLoad:"off",
        row:39,
        loadMoreAjaxToken:"475a66aea6",
        loadMoreAjaxAction:"Essential_Grid_Front_request_ajax",
        ajaxContentTarget:"ess-grid-ajax-container-",
        ajaxScrollToOffset:"0",
        ajaxCloseButton:"off",
        ajaxContentSliding:"on",
        ajaxScrollToOnLoad:"on",
        ajaxNavButton:"off",
        ajaxCloseType:"type1",
        ajaxCloseInner:"false",
        ajaxCloseStyle:"light",
        ajaxClosePosition:"tr",
        space:20,
        pageAnimation:"fade",
        paginationScrollToTop:"off",
        spinner:"spinner0",
        evenGridMasonrySkinPusher:"on",
        lightBoxMode:"single",
        animSpeed:1000,
        delayBasic:1,
        mainhoverdelay:1,
        filterType:"single",
        showDropFilter:"hover",
        filterGroupClass:"esg-fgc-1",
        googleFonts:['Open+Sans:300,400,600,700,800','Raleway:100,200,300,400,500,600,700,800,900','Droid+Serif:400,700'],
        aspectratio:"4:3",
        responsiveEntries: [
						{ width:1400,amount:3},
						{ width:1170,amount:3},
						{ width:1024,amount:3},
						{ width:960,amount:3},
						{ width:778,amount:2},
						{ width:640,amount:2},
						{ width:480,amount:1}
						]
	});

});
</script>

	</div>
</div>