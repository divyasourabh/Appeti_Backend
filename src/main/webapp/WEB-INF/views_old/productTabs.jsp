<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript">
				jQuery(function ($) {
					$(".astar").click(function(event) {
						event.preventDefault();	
					});
					window.rating = function (rate){
						$("#rating option[value='"+rate+"']").attr('selected','selected');
					}
					$("#tab-info").click(function(event) {
						event.preventDefault();
						$("#tab-review").removeClass("active");
						$("#tab-info").addClass("active");
						$("#tab-reviews").attr('style','display: none;');
						$("#tab-additional_information").attr('style','display: block;');
						
					});
					$("#tab-review").click(function(event) {
						event.preventDefault();
						$("#tab-info").removeClass("active");
						$("#tab-review").addClass("active");
						$("#tab-additional_information").attr('style','display: none;');
						$("#tab-reviews").attr('style','display: block;');

					});

					$("#reviewProduct").submit(function(e) {
						e.preventDefault();
						if($("#rating").val() == 0) {
							alert("Please select a rating");
							return false;
						}
						var postData = $("#reviewProduct").serializeArray();
								var formURL = $("#reviewProduct").attr("action");
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
											$("#alert-div").html(obj["message"]);
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

								
							});
						
				});
				</script>

<div class="woocommerce-tabs">
	<ul class="tabs x-nav x-nav-tabs two-up top">

		<li class="additional_information_tab x-nav-tabs-item active"
			id="tab-info"><a href="#tab-additional_information">Additional
				Information</a></li>
		<li class="reviews_tab x-nav-tabs-item" id="tab-review"><a
			href="#tab-reviews">Reviews (${fn:length(productBean.reviews)})</a></li>

	</ul>
	<div class="x-tab-content">

		<div class="panel x-tab-pane" id="tab-additional_information"
			style="display: block;">

			<h2>Additional Information</h2>


			<table class="shop_attributes">

				<thead>
					<tr class="shop_attributes_thead">
						<th>Attribute</th>
						<th>Information</th>
					</tr>
				</thead>

				<tbody>
					<c:forEach items="${productBean.product.attrs}" var="attr">
						<tr class="alt">
						<td>${attr.name}</td>
						<td><p>${attr.value}</p></td>
						</tr>
						
					</c:forEach>
				
				</tbody>
			</table>

		</div>
		<div class="panel x-tab-pane" id="tab-reviews" style="display: none;">

			<div id="reviews">
				<div id="comments" class="x-comments-area">

					
						<c:if test="${fn:length(productBean.reviews) == 0}">
					<h2>Reviews</h2>
					<p class="noreviews">There are no reviews yet.</p>
					</c:if>

				</div>

				<div class="x-comments-area" id="comments1">

    				<c:choose>
    			    <c:when test="${fn:length(productBean.reviews) == 1} ">
    				<h2>${fn:length(productBean.reviews)} review for ${productBean.product.ptitleName}</h2>
    				</c:when>
    				<c:when test="${fn:length(productBean.reviews) > 1}">
    				<h2>${fn:length(productBean.reviews)} reviews for ${productBean.product.ptitleName}</h2>
    				</c:when>
    				</c:choose>

					<ol class="x-comments-list">

						<c:forEach items="${productBean.reviews}" var="review">
							<li
								class="comment byuser even thread-even depth-1"
								id="li-comment-${review.id}">
								<article class="comment" id="comment-${review.id}">
									 <div class="x-comment-img">
										<span class="avatar-wrap cf"><img width="240"
											height="240" class="avatar avatar-240 photo"
											srcset="http://1.gravatar.com/avatar/db2a38ff09327000b0199465b37e58ce?s=480&amp;d=mm&amp;r=g 2x"
											src="http://1.gravatar.com/avatar/db2a38ff09327000b0199465b37e58ce?s=240&amp;d=mm&amp;r=g"
											alt=""></span>
									</div> 
									<div class="x-comment-wrap">
										<header class="x-comment-header">
											<cite class="x-comment-author">${review.title}</cite>
											<div class="star-rating-container">
												<div title="Rated ${review.rating} out of 5" class="star-rating"
													itemtype="http://schema.org/Rating" itemscope=""
													itemprop="reviewRating">
													<span style="width: ${review.rating*20}%; align:right;"><strong
														itemprop="ratingValue">${review.rating}</strong> out of 5</span>
												</div>
											</div>
											<div>
												<a class="x-comment-time"
													href="http://www.appeti.in/shop/rajasthan/paneer-ghewar/#comment-${review.id}">
													<fmt:formatDate pattern="MM.dd.yyyy"  value="${review.dateAdded}" />&nbsp;by ${review.userName}</a>
											</div>
										</header>
										<section class="x-comment-content">
											<p>${review.description}</p>
										</section>
									</div>
								</article>
							</li>
						</c:forEach>

						<!-- #comment-## -->
					</ol>

				</div>
				<div id="review_form_wrapper">
					<div id="review_form">

						<div id="respond" class="comment-respond">
							<c:choose>
    			    <c:when test="${fn:length(productBean.reviews) == 0} ">
    				<h3 id="reply-title" class="comment-reply-title">
								Be the First to Review "${productBean.product.ptitleName}" <small></small>
							</h3>
    				<h2>${fn:length(productBean.reviews)} review for ${productBean.product.ptitleName}</h2>
    				</c:when>
    				<c:when test="${fn:length(productBean.reviews) >= 1}">
    				<h3 id="reply-title" class="comment-reply-title">
								Write a Review<small></small>
							</h3></c:when>
    				</c:choose>
    				
							<form action="${pageContext.request.contextPath}/product/add-review"
								method="post" id="reviewProduct" class="comment-form">
								<p class="comment-form-rating">
									<label for="rating">Rating</label>
								<p class="stars">
									<span><a class="star-1 astar" href="#" onclick="rating(1);">1</a><a class="star-2 astar"
										href="#" onclick="rating(2);">2</a><a class="star-3 astar" href="#" onclick="rating(3);">3</a><a
										class="star-4 astar" href="#" onclick="rating(4);">4</a><a class="star-5 astar" href="#" onclick="rating(5);">5</a></span>
								</p>
								<select name="rating" id="rating" style="display: none;">
									<option value="0">Rate…</option>
									<option value="5">Perfect</option>
									<option value="4">Good</option>
									<option value="3">Average</option>
									<option value="2">Not that bad</option>
									<option value="1">Very Poor</option>
								</select>
								
								<p class="comment-form-comment">
									<label for="comment">Your Review</label>
									<input type="text" id="title" name="title" value="" maxlength="50" style="width: 100%;"placeholder="Title"/>
									<textarea id="desc" name="desc" cols="45" rows="8"
										aria-required="true" placeholder="Description" maxlength="2000"></textarea>
									<input type="hidden" id="productId" name="productId" value="${productBean.product.productId }">
									<input type="hidden" id="ptitleId" name="ptitleId" value="${productBean.product.ptitleId }">
									<input type="hidden" id="tagId" name="tagId" value="${productBean.product.tagId }">
								</p>
								<p class="form-submit">
									<input name="submit" type="submit" id="submit" class="submit"
										value="Submit Review"/>
									
								</p>
							</form>
						</div>
						<!-- #respond -->

					</div>
				</div>


				<div class="clear"></div>
			</div>
		</div>

	</div>
</div>