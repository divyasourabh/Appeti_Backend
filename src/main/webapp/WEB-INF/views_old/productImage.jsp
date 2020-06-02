<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="images">

						<a
							href="${pageContext.request.contextPath}/${productBean.product.images[0].url}"
							itemprop="image"
							class="woocommerce-main-image zoom x-img x-img-link x-img-thumbnail man"
							title="${productBean.product.images[0].text}"
							data-rel="prettyPhoto[product-gallery]"
							data-o_href="${pageContext.request.contextPath}/${productBean.product.images[0].url}"><img
							width="1024" height="768"
							src="${pageContext.request.contextPath}/${productBean.product.images[0].url}"
							class="attachment-entry-full-integrity wp-post-image"
							alt="${productBean.product.images[0].text}"
							title="${productBean.product.images[0].text}"
							data-o_src="${pageContext.request.contextPath}/${productBean.product.images[0].url}"
							data-o_title="${productBean.product.images[0].text}"
							data-o_alt="${productBean.product.images[0].text}"></a>

						<div class="thumbnails">
						
							<c:forEach items="${productBean.product.images}" var="image" varStatus = "status">
								<c:if test="${status.first == false}">
								<a href="${pageContext.request.contextPath}/${image.url}"
									class="zoom first x-img x-img-link x-img-thumbnail"
									title="${image.text}"
									data-rel="prettyPhoto[product-gallery]"><img width="600"
									height="600"
									src="${pageContext.request.contextPath}/${image.url}"
									class="attachment-shop_single"
									alt="${image.text}"></a>
								</c:if>
							</c:forEach>
						</div>


					</div>