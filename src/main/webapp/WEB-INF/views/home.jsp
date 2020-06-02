<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/resources/images/favicon.png">

<title>Appet-i</title>

</head>

<body>


	<%@ include file="homeHeader.jsp"%>
	<script type='text/javascript'
		src='${pageContext.request.contextPath}/resources/script_old/jquery.js?ver=1.11.2'></script>

	<script type="text/javascript">
		jQuery(function($) {
			$("#add-suggestion-form").submit(function(e) {
				e.preventDefault(); //prevent default form submit
			});
			$("#add-suggestion-form").submit(
					function(e) {
						$('#sug_err').text("");
						var ok = true;
						var name = $('#sug_name').val();
						var email = $('#sug_email').val();
						var phone = $('#sug_phone').val();
						var prod = $('#sug_product_name').val();
						var brand = $('#sug_brand_name').val();
						var city = $('#sug_city').val();

						if (name == "" || email == "" || phone == ""
								|| prod == "" || brand == "" || city == ""
								|| validateEmail(email)) {

							$("#sug_err").text(
									"Information incomplete or invalid");
							$("#sug_err").css("color", "red");
							ok = false;
						}
						if (ok == true) {
							var postData = $("#add-suggestion-form")
									.serializeArray();
							var formURL = $("#add-suggestion-form").attr(
									"action");
							$
									.ajax({
										url : formURL,
										type : "POST",
										data : postData,
										success : function(response) {
											var obj = JSON.parse(response);
											if (obj["status"] == true) {
												$("#sug_err").text(
														obj["message"]);
												$("#sug_err").css("color",
														"green");
											} else {
												$("#sug_err").text(
														obj["message"]);
												$("#sug_err").css("color",
														"red");
											}
										},
										error : function(jqXHR, textStatus,
												errorThrown) {
											$("#sug_err").text(textStatus);
											$("#sug_err").css("color", "red");
										}
									});

						}
						e.preventDefault();
					});
		});
	</script>
	<div class="content">
		<div class="container">
			<div class="row">
				<div class="col-lg-4 col-sm-4 col-md-4 col-xs-12 green">
					<div class="icon-box">
						<div class="circle-border">
							<img
								src="${pageContext.request.contextPath}/resources/images/ordering.png"
								alt="">
						</div>
					</div>
					<div class="right-detail-box">
						<h2>Easy Ordering</h2>
						<p>It's quick, convenient, and free to use.</p>
					</div>
				</div>

				<div class="col-lg-4 col-sm-4 col-md-4 col-xs-12 blue">
					<div class="icon-box">
						<div class="circle-border">
							<img
								src="${pageContext.request.contextPath}/resources/images/choices-arrow.png"
								alt="">
						</div>
					</div>
					<div class="right-detail-box">
						<h2>More Choices</h2>
						<p>Everything you want, including sweets, snacks & pickles in
							one place.</p>
					</div>
				</div>

				<div class="col-lg-4 col-sm-4 col-md-4 col-xs-12 orange">
					<div class="icon-box">
						<div class="circle-border">
							<img
								src="${pageContext.request.contextPath}/resources/images/delivery.png"
								alt="">
						</div>
					</div>
					<div class="right-detail-box">
						<h2>Home Delivery</h2>
						<p>We deliver at your doorstep right from origin.</p>
					</div>
				</div>

			</div>
			<hr>
			<section class="flavours">
				<h1 class="main-heading">Browse by Category</h1>
				<div class="row">
					<c:forEach items="${headerBean.categoryTree.childCategories}"
						var="node">
						<div
							class="col-lg-4 col-sm-4 col-md-4 col-xs-12 homeCategAnimation">
							<div class="flavour-type">
								<a
									href="${pageContext.request.contextPath}/shop/category/${node.categoryId}/${fn:replace(fn:toLowerCase(node.name),' ','-')}">
									<img src="${pageContext.request.contextPath}/${node.image.url}"
									width="263" height="196" alt="${fn:toUpperCase(node.name)}"
									class="img-responsive">
								</a>
								<p>${fn:toUpperCase(node.name)}</p>
							</div>
						</div>
					</c:forEach>


				</div>
			</section>
			<hr>
			<section class="delicacies">
				<h1 class="main-heading">Browse by Location</h1>
				<div class="row">
					<c:forEach begin="0" end="1"
						items="${headerBean.nodeTree.childNodes}" var="node">
						<c:set var="len" value="${2 * fn:length(node.childNodes)}"></c:set>
						<c:if test="${len == 2}">
							<c:set var="len" value="4"></c:set>
						</c:if>
						<div class="col-lg-${len} col-md-${len} col-sm-${len} col-xs-12">
							<div class="gray-box">
								<ul class="list-city">
									<c:forEach items="${node.childNodes}" var="childNode">
										<li><a
											href="${pageContext.request.contextPath}/shop/node/${childNode.nodeId}/${fn:replace(fn:toLowerCase(childNode.nodeName),' ','-')}">
												<img
												src="${pageContext.request.contextPath}/${childNode.image.url}"
												alt="" class="img-responsive">
										</a></li>
									</c:forEach>
								</ul>
								<h2>${node.nodeName}</h2>
							</div>
						</div>
					</c:forEach>


				</div>

				<div class="second-row">
					<div class="row">
						<c:forEach begin="0" end="3" varStatus="loop"
							items="${headerBean.nodeTree.childNodes}" var="node">
							<c:if test="${loop.index >1}">
								<c:set var="len" value="${2 * fn:length(node.childNodes)}"></c:set>
								<c:if test="${len == 2}">
									<c:set var="len" value="4"></c:set>
								</c:if>
								<div class="col-lg-${len} col-md-${len} col-sm-${len} col-xs-12">
									<div class="gray-box other">
										<ul class="list-city">
											<c:forEach items="${node.childNodes}" var="childNode">
												<li><a
													href="${pageContext.request.contextPath}/shop/node/${childNode.nodeId}/${fn:replace(fn:toLowerCase(childNode.nodeName),' ','-')}">
														<img
														src="${pageContext.request.contextPath}/${childNode.image.url}"
														alt="" class="img-responsive">
												</a></li>
											</c:forEach>
										</ul>
										<h2>${node.nodeName}</h2>
									</div>
								</div>
							</c:if>
						</c:forEach>

					</div>
				</div>
			</section>

			<div class="second-navigation-list">
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="list-nav">
					<c:set var="index" value="0"></c:set>
					<tr>
						<c:forEach items="${headerBean.nodeTree.childNodes}" var="node">
							<c:forEach items="${node.childNodes}" var="childNode">

								<c:if test="${index <6}">
									<td align="left" valign="top"><a
										href="${pageContext.request.contextPath}/shop/node/${childNode.nodeId}/${fn:replace(fn:toLowerCase(childNode.nodeName),' ','-')}">
											${childNode.nodeName}</a></td>
								</c:if>
								<c:set var="index" value="${index+1}"></c:set>

							</c:forEach>
						</c:forEach>
					</tr>
					<tr>
						<c:set var="index" value="0"></c:set>
						<c:forEach items="${headerBean.nodeTree.childNodes}" var="node">
							<c:forEach items="${node.childNodes}" var="childNode">

								<c:if test="${index>5 && index <12}">
									<td align="left" valign="top"><a
										href="${pageContext.request.contextPath}/shop/node/${childNode.nodeId}/${fn:replace(fn:toLowerCase(childNode.nodeName),' ','-')}">
											${childNode.nodeName}</a></td>
								</c:if>
								<c:set var="index" value="${index+1}"></c:set>

							</c:forEach>
						</c:forEach>
					</tr>
					<tr>
						<c:set var="index" value="0"></c:set>
						<c:forEach items="${headerBean.nodeTree.childNodes}" var="node">
							<c:forEach items="${node.childNodes}" var="childNode">

								<c:if test="${index>11 && index <18}">
									<td align="left" valign="top"><a
										href="${pageContext.request.contextPath}/shop/node/${childNode.nodeId}/${fn:replace(fn:toLowerCase(childNode.nodeName),' ','-')}">
											${childNode.nodeName}</a></td>
								</c:if>
								<c:set var="index" value="${index+1}"></c:set>

							</c:forEach>
						</c:forEach>
					</tr>
				</table>




			</div>
			<hr>

			<!-- 
        <section class="clients"><h1 class="main-heading">Browse by Vendor</h1>
        	<div id="clients-logo" class="owl-carousel">
                <div class="item"><img src="${pageContext.request.contextPath}/resources/images/client-logo1.jpg" alt=""></div>
                <div class="item"><img src="${pageContext.request.contextPath}/resources/images/client-logo2.jpg" alt=""></div>
                <div class="item"><img src="${pageContext.request.contextPath}/resources/images/client-logo3.jpg" alt=""></div>
                <div class="item"><img src="${pageContext.request.contextPath}/resources/images/client-logo4.jpg" alt=""></div>
                <div class="item"><img src="${pageContext.request.contextPath}/resources/images/client-logo5.jpg" alt=""></div>
                <div class="item"><img src="${pageContext.request.contextPath}/resources/images/client-logo6.jpg" alt=""></div>
                <div class="item"><img src="${pageContext.request.contextPath}/resources/images/client-logo1.jpg" alt=""></div>
                <div class="item"><img src="${pageContext.request.contextPath}/resources/images/client-logo2.jpg" alt=""></div>
                <div class="item"><img src="${pageContext.request.contextPath}/resources/images/client-logo3.jpg" alt=""></div>
                <div class="item"><img src="${pageContext.request.contextPath}/resources/images/client-logo4.jpg" alt=""></div>
                <div class="item"><img src="${pageContext.request.contextPath}/resources/images/client-logo5.jpg" alt=""></div>
              </div>
        </section><hr>
         -->
			<section class="contact-us-box">
				<h1 class="main-heading">Suggest a Product or Brand</h1>

				<form class="form clearfix" method="post"
					action="${pageContext.request.contextPath}/add-suggestion"
					id="add-suggestion-form">
					<div class="left-form-box">
						<div>
							<input type="text" id="sug_name" name="sug_name"
								placeholder="Your Name">
						</div>
						<div>
							<input type="text" id="sug_email" name="sug_email"
								placeholder="Your Email">
						</div>
						<div>
							<input type="text" id="sug_phone" name="sug_phone"
								placeholder="Your Phone">
						</div>
						<div>
							<input type="text" id="sug_product_name" name="sug_product_name"
								placeholder="Product Name">
						</div>
						<div>
							<input type="text" id="sug_brand_name" name="sug_brand_name"
								placeholder="Brand Name">
						</div>
						<div>
							<input type="text" id="sug_city" name="sug_city"
								placeholder="City">
						</div>
						<i id="sug_err"></i>
					</div>
					<div class="right-form-box">
						<button class="btn btn-primary" type="submit">Submit your
							Suggestion</button>
					</div>
				</form>
		</div>
		</section>
		<hr>

		<!-- 
        <section class="flavours testimonialsHome">
        	<h1 class="main-heading">Testimonials</h1>
            <p>See what our existing customers have to say about us </p>
            <div class="row">
                <c:forEach items="${homeBean.testimonials}" var="testimonial">
            	<c:set var="url" value="${testimonial.imageUrl}"></c:set>
                    	<c:if test="${empty testimonial.imageUrl}">	<c:set var="url" value="resources/images/no-product-img.jpg"></c:set> </c:if>
                
                <div class="col-lg-2 col-sm-2 col-md-2 col-xs-12 homeCategAnimation">
                    <div class="flavour-type"> <a href="#"><img src="${pageContext.request.contextPath}/${url}" alt="USER" class="img-responsive"></a>
                      <p>${testimonial.userName}</p>
                      <p class="testimonials">"${testimonial.description}"</p>
                    </div>    
                </div>
                
                </c:forEach>
                
            </div> 
            <div class="text-center marginT10">
        	<a href="${pageContext.request.contextPath}/testimonials" class="btn btn-primary btn-xxs">Click here to view all testimonials</a>
        </div>   
        </section>
        <hr>
         -->

		<section class="container">
			<h1 class="main-heading">In News</h1>
			<div class="row">
				<a href="http://www.iimcip.org/incubation/our-incubatees/"
					target="_blank">
					<div class="col-lg-4 col-sm-4 col-md-4 col-xs-12 green">
						<div class="icon-box">
							<div>
								<img
									src="${pageContext.request.contextPath}/resources/images/testimonials/iimcinnovationpark.png"
									alt="" height=90 weight=90>
							</div>
						</div>
						<div class="right-detail-box">
							<h2>IIM Calcutta</h2>
							<p><!-- We are stoked to be chosen to be incubated at the IIM
								Calcutta Innovation Park. --></p>
						</div>
					</div>
				</a> <a href="http://indiablooms.com/ibns_new/life-details/E/1112/iim-calcutta-welcomes-manjhi-director-cast-to-their-campus.html"
					target="_blank">
					<div class="col-lg-4 col-sm-4 col-md-4 col-xs-12 blue">
						<div class="icon-box">
							<div class="circle-border">
								<img
									src="${pageContext.request.contextPath}/resources/images/testimonials/indiabloomslogo.gif"
									alt="" height=90 weight=90>
							</div>
						</div>
						<div class="right-detail-box">
							<h2><!-- More Choices --></h2>
							<p><!-- Everything you want, including sweets, snacks & pickles in
								one place. --></p>
						</div>
					</div>
				</a> <a href="http://www.collegedekho.com/news/manjhi-cast-visited-iim-calcutta-campus-6356/"
					target="_blank">

					<div class="col-lg-4 col-sm-4 col-md-4 col-xs-12 orange">
						<div class="icon-box">
							<div class="circle-border">
								<img
									src="${pageContext.request.contextPath}/resources/images/testimonials/collegedekho.png"
									alt="" height=90 weight=90>
							</div>
						</div>
						<div class="right-detail-box">
							<h2><!-- Home Delivery --></h2>
							<p><!-- We deliver at your doorstep right from origin. --></p>
						</div>
					</div>
				</a>


			</div>
		</section>
		<hr>


	</div>
	<!--end of container-->
	</div>
	<!--end of content-->



	<%@ include file="homeFooter.jsp"%>

	<script>
		$("#commentForm")
				.validate(
						{

							rules : {
								name : "required",
								email : "required",
								phone : "required",
								product_name : "required",
								brand_name : "required",
								city : "required"
							},
							messages : {
								name : "Please enter your name",
								product_name : "Please enter your product name",
								brand_name : {
									required : "Please enter a brand name",
									minlength : "Your brand name must consist of at least 2 characters"
								},
								city : {
									required : "Please provide a city"
								},
								email : "Please enter a valid email address"
							}
						});
	</script>
</body>
</html>