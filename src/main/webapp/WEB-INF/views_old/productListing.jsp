<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<style type="text/css">
span.no-change { width:75%; display:inline-block; }
span.align-right { text-align:right; width:22%;}
</style>
<script type="text/javascript">

jQuery(function ($) {
	$("a.add_to_cart_button").click(function (e) {
	    e.preventDefault(); //prevent default
		});
		
});

function addToCart(prod, ptitle, tag, name) {
	var formURL = "${pageContext.request.contextPath}/cart/add";
	jQuery.ajax({
		url : formURL,
		type : "POST",
		data : {productId:prod,ptitleId:ptitle,tagId:tag,quantity:1},
		success : function(response) {
			var obj = JSON.parse(response);
			if (obj["status"] == true) {
				jQuery("#alert-div").removeClass("x-alert-danger");
				jQuery("#alert-div").addClass("x-alert-info");
				jQuery("#alert-div").attr('style','display:block');
				jQuery("#alert-div").html('<a class="button wc-forward" href="${pageContext.request.contextPath}/cart">View Cart</a>' + obj["message"] + " :" + name);
				jQuery("#top-button").trigger('click');
			} else {
				jQuery("#alert-div").removeClass("x-alert-info");
				jQuery("#alert-div").addClass("x-alert-danger");
				jQuery("#alert-div").attr('style','display:block');
				jQuery("#alert-div").html(obj["message"]);
				jQuery("#top-button").trigger('click');
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			jQuery("#alert-div").removeClass("x-alert-info");
			jQuery("#alert-div").addClass("x-alert-danger");
			jQuery("#alert-div").attr('style','display:block');
			jQuery("#alert-div").html(obj["message"]);
			jQuery("#top-button").trigger('click');
		}
	});
}
</script>

<ul class="products cols-3">
	<c:set var="pos" value="0" scope="page"></c:set>
	<c:forEach items="${searchBean.results}" var="result">
		<c:choose>
		<c:when test="${pos==0}">
		<c:set var="class1" value="first"></c:set>
		</c:when>
		<c:when test="${pos==2}">
		<c:set var="class1" value="last"></c:set>
		</c:when>
		<c:otherwise>
		<c:set var="class1" value=""></c:set>
		</c:otherwise>
		</c:choose>
		
		<c:set var="productLink" value="${pageContext.request.contextPath}/product/${result.productId}-${result.ptitleId}-${result.tagId}
						/${fn:replace(fn:toLowerCase(result.productName),' ','-')}-${fn:replace(fn:toLowerCase(result.ptitleName),' ','-')}
						-${fn:replace(fn:toLowerCase(result.unit),' ','-')}">
		</c:set>
		<c:set var="productName" value="${result.ptitleName}"></c:set>
		<li
			class="${class1} post-125 product type-product status-publish has-post-thumbnail instock">

			<div class="entry-product">
				<div class="entry-featured">
					<a
						href="${productLink}"><img
						width="413" height="275"
						src="${pageContext.request.contextPath}/${result.image.url}"
						class="attachment-entry-full-renew wp-post-image"
						alt="${result.image.text}"></a>
				</div>
				<div class="entry-wrap">
					<header class="entry-header">
						<h3>
							<span class="no-change"><a href="${productLink}">${productName}</a></span>
							<span class="align-right">Rs&nbsp;${result.pricePerUnit}</span>
							<span class="no-change"><a href="${productLink}">${result.unit} </a></span>
							
						</h3>

						<c:if test="${result.inStock ==true }">
						<a href="#" onclick="addToCart(${result.productId},${result.ptitleId},${result.tagId},'${productName} (${result.unit})');"
							rel="nofollow" data-product_id="125" data-product_sku=""
							data-quantity="1"
							class="button add_to_cart_button product_type_variable">Add to cart</a>
						</c:if>
						<c:if test="${result.inStock == false }">
						<a href="#"
							rel="nofollow" data-product_id="125" data-product_sku=""
							data-quantity="1"
							class="button add_to_cart_button product_type_variable">${result.availability}</a>
						</c:if>
					</header>
				</div>
			</div>
		</li>
		<c:set var="pos" value="${(pos + 1) % 3}" scope="page"/>
	</c:forEach>
						
		
</ul>