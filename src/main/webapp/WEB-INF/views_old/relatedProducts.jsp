<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

					<div class="related products cols-4">

						<h2>Related Products</h2>



						<ul class="products">
							
								<c:set var="pos" value="0" scope="page"></c:set>
							<c:forEach items="${productBean.product.relatedProducts}" var="product">
							<c:choose>
							<c:when test="${pos==0}">
							<c:set var="class1" value="first"></c:set>
							</c:when>
							<c:when test="${pos==3}">
							<c:set var="class1" value="last"></c:set>
							</c:when>
							<c:otherwise>
							<c:set var="class1" value=""></c:set>
							</c:otherwise>
							</c:choose>
							
							<c:set var="productLink" value="${pageContext.request.contextPath}/shop/product/${product.productId}
						/${fn:replace(fn:toLowerCase(product.productName),' ','-')}">
		</c:set>
		<c:set var="productName" value="${product.productName}"></c:set>
		
							<li
								class="${class1} post-233 product type-product ${productName} status-publish has-post-thumbnail instock">

								<div class="entry-product">
									<div class="entry-featured">
										<a href="${productLink}"><img
											width="414" height="275"
											src="${pageContext.request.contextPath}/${product.image.url}"
											class="attachment-entry-full-renew wp-post-image"
											alt="${product.image.text}"></a>
									</div>
									<div class="entry-wrap">
										<header class="entry-header">
											<h3>
												<a href="${productLink}">${productName}</a>
											</h3>


											<a href="${productLink}"
												rel="nofollow" data-product_id="233" data-product_sku=""
												data-quantity="1"
												class="button add_to_cart_button product_type_variable">Select
												options</a>
										</header>
									</div>
								</div>
							</li>
							<c:set var="pos" value="${(pos + 1) % 4}" scope="page"/>
							</c:forEach>
							
						</ul>
					</div>