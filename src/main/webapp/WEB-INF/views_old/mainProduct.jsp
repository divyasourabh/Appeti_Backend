<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript">
jQuery(function ($) {
	$("#add_to_cart_form").submit(function (e) {
	    e.preventDefault(); //prevent default form submit
		});
	$("#type").change(function() {

		var val = $("#type").val();
		if (val != -1 && val.split("-")[0] != "${productBean.product.ptitleId}") {
			$("#ptitleSelection").submit();
		}
	});
	
	$("#add_to_cart_form").submit(function(e) {
			var postData = $("#add_to_cart_form").serializeArray();
			var formURL = $("#add_to_cart_form").attr("action");
			$.ajax({
				url : formURL,
				type : "POST",
				data : postData,
				success : function(response) {
					var obj = JSON.parse(response);
					if (obj["status"] == true) {
						$("#alert-div").removeClass("x-alert-danger");
						$("#alert-div").addClass("x-alert-info");
						$("#alert-div").attr('style','display:block');
						$("#alert-div").html('<a class="button wc-forward" href="${pageContext.request.contextPath}/cart">View Cart</a>' + obj["message"] + " : ${productBean.product.ptitleName}");
						$("#top-button").trigger('click');
					} else {
						$("#alert-div").removeClass("x-alert-info");
						$("#alert-div").addClass("x-alert-danger");
						$("#alert-div").attr('style','display:block');
						$("#alert-div").html(obj["message"]);
						$("#top-button").trigger('click');
					}
				},
				error : function(jqXHR, textStatus, errorThrown) {
					$("#alert-div").removeClass("x-alert-info");
					$("#alert-div").addClass("x-alert-danger");
					$("#alert-div").attr('style','display:block');
					$("#alert-div").html(obj["message"]);
					$("#top-button").trigger('click');
				}
			});

			e.preventDefault();
		});
	});
</script>
<div class="woocommerce-message x-alert x-alert-info x-alert-block" id="alert-div" style="display:none;"></div>

		<div class="entry-wrap">
			<div class="entry-content">
				<div itemscope="" itemtype="http://schema.org/Product"
					id="product-92"
					class="post-92 product type-product status-publish has-post-thumbnail product_cat-gujarat product_cat-western product_tag-ahmedabad product_tag-gujarat product_tag-snacks shipping-taxable purchasable product-type-variable product-cat-gujarat product-cat-western product-tag-ahmedabad product-tag-gujarat product-tag-snacks instock">


					<%@ include file="productImage.jsp"%>
		
					<div class="summary entry-summary">

						<h1 itemprop="name" class="product_title">${productBean.product.ptitleName}</h1>
						<h5 itemprop="" id="seller_name" class="">Sold by : </h5>
						<div itemprop="description">
							<p>${productBean.product.ptitleDesc}</p>
						</div>


						<form class="variations_form cart"
							id="ptitleSelection"
							data-product_id="92"
							data-product_variations="[{&quot;variation_id&quot;:455,&quot;variation_is_visible&quot;:true,&quot;variation_is_active&quot;:true,&quot;is_purchasable&quot;:true,&quot;display_price&quot;:80,&quot;display_regular_price&quot;:80,&quot;attributes&quot;:{&quot;attribute_weight&quot;:&quot;500-gms&quot;,&quot;attribute_type&quot;:&quot;sada-khakra&quot;},&quot;image_src&quot;:&quot;&quot;,&quot;image_link&quot;:&quot;&quot;,&quot;image_title&quot;:&quot;&quot;,&quot;image_alt&quot;:&quot;&quot;,&quot;price_html&quot;:&quot;<span class=\&quot;price\&quot;><span class=\&quot;amount\&quot;>Rs.80.00<\/span><\/span>&quot;,&quot;availability_html&quot;:&quot;&quot;,&quot;sku&quot;:&quot;&quot;,&quot;weight&quot;:&quot; kg&quot;,&quot;dimensions&quot;:&quot;&quot;,&quot;min_qty&quot;:1,&quot;max_qty&quot;:&quot;&quot;,&quot;backorders_allowed&quot;:false,&quot;is_in_stock&quot;:true,&quot;is_downloadable&quot;:false,&quot;is_virtual&quot;:false,&quot;is_sold_individually&quot;:&quot;no&quot;},{&quot;variation_id&quot;:456,&quot;variation_is_visible&quot;:true,&quot;variation_is_active&quot;:true,&quot;is_purchasable&quot;:true,&quot;display_price&quot;:80,&quot;display_regular_price&quot;:80,&quot;attributes&quot;:{&quot;attribute_weight&quot;:&quot;500-gms&quot;,&quot;attribute_type&quot;:&quot;masala-khakra&quot;},&quot;image_src&quot;:&quot;&quot;,&quot;image_link&quot;:&quot;&quot;,&quot;image_title&quot;:&quot;&quot;,&quot;image_alt&quot;:&quot;&quot;,&quot;price_html&quot;:&quot;<span class=\&quot;price\&quot;><span class=\&quot;amount\&quot;>Rs.80.00<\/span><\/span>&quot;,&quot;availability_html&quot;:&quot;&quot;,&quot;sku&quot;:&quot;&quot;,&quot;weight&quot;:&quot; kg&quot;,&quot;dimensions&quot;:&quot;&quot;,&quot;min_qty&quot;:1,&quot;max_qty&quot;:&quot;&quot;,&quot;backorders_allowed&quot;:false,&quot;is_in_stock&quot;:true,&quot;is_downloadable&quot;:false,&quot;is_virtual&quot;:false,&quot;is_sold_individually&quot;:&quot;no&quot;},{&quot;variation_id&quot;:457,&quot;variation_is_visible&quot;:true,&quot;variation_is_active&quot;:true,&quot;is_purchasable&quot;:true,&quot;display_price&quot;:90,&quot;display_regular_price&quot;:90,&quot;attributes&quot;:{&quot;attribute_weight&quot;:&quot;500-gms&quot;,&quot;attribute_type&quot;:&quot;diet-sada-khakra&quot;},&quot;image_src&quot;:&quot;&quot;,&quot;image_link&quot;:&quot;&quot;,&quot;image_title&quot;:&quot;&quot;,&quot;image_alt&quot;:&quot;&quot;,&quot;price_html&quot;:&quot;<span class=\&quot;price\&quot;><span class=\&quot;amount\&quot;>Rs.90.00<\/span><\/span>&quot;,&quot;availability_html&quot;:&quot;&quot;,&quot;sku&quot;:&quot;&quot;,&quot;weight&quot;:&quot; kg&quot;,&quot;dimensions&quot;:&quot;&quot;,&quot;min_qty&quot;:1,&quot;max_qty&quot;:&quot;&quot;,&quot;backorders_allowed&quot;:false,&quot;is_in_stock&quot;:true,&quot;is_downloadable&quot;:false,&quot;is_virtual&quot;:false,&quot;is_sold_individually&quot;:&quot;no&quot;},{&quot;variation_id&quot;:458,&quot;variation_is_visible&quot;:true,&quot;variation_is_active&quot;:true,&quot;is_purchasable&quot;:true,&quot;display_price&quot;:90,&quot;display_regular_price&quot;:90,&quot;attributes&quot;:{&quot;attribute_weight&quot;:&quot;500-gms&quot;,&quot;attribute_type&quot;:&quot;diet-masala-khakra&quot;},&quot;image_src&quot;:&quot;&quot;,&quot;image_link&quot;:&quot;&quot;,&quot;image_title&quot;:&quot;&quot;,&quot;image_alt&quot;:&quot;&quot;,&quot;price_html&quot;:&quot;<span class=\&quot;price\&quot;><span class=\&quot;amount\&quot;>Rs.90.00<\/span><\/span>&quot;,&quot;availability_html&quot;:&quot;&quot;,&quot;sku&quot;:&quot;&quot;,&quot;weight&quot;:&quot; kg&quot;,&quot;dimensions&quot;:&quot;&quot;,&quot;min_qty&quot;:1,&quot;max_qty&quot;:&quot;&quot;,&quot;backorders_allowed&quot;:false,&quot;is_in_stock&quot;:true,&quot;is_downloadable&quot;:false,&quot;is_virtual&quot;:false,&quot;is_sold_individually&quot;:&quot;no&quot;},{&quot;variation_id&quot;:459,&quot;variation_is_visible&quot;:true,&quot;variation_is_active&quot;:true,&quot;is_purchasable&quot;:true,&quot;display_price&quot;:80,&quot;display_regular_price&quot;:80,&quot;attributes&quot;:{&quot;attribute_weight&quot;:&quot;400-gms&quot;,&quot;attribute_type&quot;:&quot;special-garlic-khakhra&quot;},&quot;image_src&quot;:&quot;&quot;,&quot;image_link&quot;:&quot;&quot;,&quot;image_title&quot;:&quot;&quot;,&quot;image_alt&quot;:&quot;&quot;,&quot;price_html&quot;:&quot;<span class=\&quot;price\&quot;><span class=\&quot;amount\&quot;>Rs.80.00<\/span><\/span>&quot;,&quot;availability_html&quot;:&quot;&quot;,&quot;sku&quot;:&quot;&quot;,&quot;weight&quot;:&quot; kg&quot;,&quot;dimensions&quot;:&quot;&quot;,&quot;min_qty&quot;:1,&quot;max_qty&quot;:&quot;&quot;,&quot;backorders_allowed&quot;:false,&quot;is_in_stock&quot;:true,&quot;is_downloadable&quot;:false,&quot;is_virtual&quot;:false,&quot;is_sold_individually&quot;:&quot;no&quot;}]"
							action="${pageContext.request.contextPath}/product/ptitleSelection"
							method="get">
							<input type="hidden" id="productId" name="productId" value="${productBean.product.productId}">
							<input type="hidden" id="productName" name="productName" value="${productBean.product.productName}">
							<table class="variations" cellspacing="0">
								<tbody>
									
									<tr>
										<td class="label"><label for="type">Type</label></td>
										<td class="value">
										<select id="type" name="type"
											data-attribute_name="attribute_type">
												<option value="-1">Choose an option...</option>
												<c:forEach items="${productBean.product.allPtitles}"
													var="ptitle">
													<option value="${ptitle.name}-${ptitle.ptitleId}" class="attached enabled"
														<c:if test="${ptitle.ptitleId == productBean.product.ptitleId}">selected</c:if>>
														${ptitle.name}</option>
												</c:forEach>

										</select>
										</td>
									</tr>
									
									<tr>
										<td class="label"><label for="weight">Weight</label></td>
										<td class="value"><select id="weight"
											name="weight"
											data-attribute_name="attribute_weight">
												<option value="">Choose an option...</option>
												<c:forEach items="${productBean.product.bestTags}" var="tag">
													<option value="${tag.tagId}-${tag.bucketKey}-${tag.pricePerUnit}-${tag.inStock}-${tag.availability}"
														<c:if test="${tag.tagId == productBean.product.tagId}">selected</c:if>
														class="attached enabled">${tag.unitString}</option>
												</c:forEach>
										</select></td>
									</tr>

								</tbody>
							</table>
					</form>
							<form action="${pageContext.request.contextPath}/cart/add" method="post" id="add_to_cart_form">
							<div class="single_variation_wrap" style="">
								<div class="single_variation">
									<span class="price"><span class="amount" id="avl" style="float:left;"></span>
									<span class="amount" id="ppu"></span></span>
								</div>
								<div class="variations_button">
									<div class="quantity">
										<input type="number" step="1" name="quantity" value="1"
											title="Qty" class="input-text qty text" size="4" min="1">
									
									</div>
									<button type="submit" id="a2cmain"
										class="single_add_to_cart_button button alt">Add to
										cart</button>
								</div>
								<input type="hidden" name="productId" value="${productBean.product.productId }"> 
								<input type="hidden" name="ptitleId" value="${productBean.product.ptitleId}"> 
								<input type="hidden" name="tagId" id="tagId" value="${productBean.product.tagId}">
							</div>
							</form>


						

						<div class="product_meta">



							<span class="posted_in">Tags: <c:forEach
									items="${productBean.product.nodeMap}" var="node">
									<a
										href="${pageContext.request.contextPath}/shop/node/${node.key}/${fn:replace(fn:toLowerCase(node.value),' ','-')}"
										rel="tag">${node.value}</a>
								</c:forEach> <br>
							</span> <span class="tagged_as">Categories: <c:forEach
									items="${productBean.product.categoryMap}" var="cat">
									<a
										href="${pageContext.request.contextPath}/shop/category/${cat.key}/${fn:replace(fn:toLowerCase(cat.value),' ','-')}">${cat.value}</a>
								</c:forEach>

							</span>

						</div>


					</div>
					<!-- .summary -->
					<%@ include file="otherSellers.jsp"%>
					
					<%@ include file="productTabs.jsp"%>
					
					<%@ include file="relatedProducts.jsp"%>
					

				</div>
				<!-- #product-92 -->

			</div>
		</div>