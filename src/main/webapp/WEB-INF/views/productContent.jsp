<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type='text/javascript' src='${pageContext.request.contextPath}/resources/script_old/jquery.js?ver=1.11.2'></script>
    
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
						jQuery("#alert-type").removeClass("alert-warning");
						jQuery("#alert-type").addClass("alert-success");
						jQuery("#alert-div").attr('style','display:block');
						jQuery("#alert_msg").html('<strong>' + name + '</strong> ' + obj["message"]);
						jQuery("#top-button").trigger('click');
					} else {
						jQuery("#alert-type").removeClass("alert-success");
						jQuery("#alert-type").addClass("alert-warning");
						jQuery("#alert-div").attr('style','display:block');
						jQuery("#alert_msg").html(obj["message"]);
						jQuery("#top-button").trigger('click');
					}
				},
				error : function(jqXHR, textStatus, errorThrown) {
					jQuery("#alert-type").removeClass("alert-success");
					jQuery("#alert-type").addClass("alert-warning");
					jQuery("#alert-div").attr('style','display:block');
					jQuery("#alert_msg").html(obj["message"]);
					jQuery("#top-button").trigger('click');
				}
			});

			e.preventDefault();
		});
	});
</script>

<script type="text/javascript">
jQuery(function ($) {
	
	
	window.addToCart = function(name) {
		var str = $('#weight').val();
		var tagId = str.split("-")[0];
		var quantity = $('#quantity').val();
		var formURL = "${pageContext.request.contextPath}/cart/add";
		$.ajax({
			url : formURL,
			type : "POST",
			data : {tagId:tagId,quantity:quantity},
			success : function(response) {
				var obj = JSON.parse(response);
				if (obj["status"] == true) {
					jQuery("#alert-type").removeClass("alert-warning");
					jQuery("#alert-type").addClass("alert-success");
					jQuery("#alert-div").attr('style','display:block');
					jQuery("#alert_msg").html('<strong>' + name + '</strong> ' + obj["message"] + '&nbsp;&nbsp;&nbsp;&nbsp;<a class="button center" href="${pageContext.request.contextPath}/cart">View Cart</a>');
					jQuery("#top-button").trigger('click');
				} else {
					jQuery("#alert-type").removeClass("alert-success");
					jQuery("#alert-type").addClass("alert-warning");
					jQuery("#alert-div").attr('style','display:block');
					jQuery("#alert_msg").html(obj["message"]);
					jQuery("#top-button").trigger('click');
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				jQuery("#alert-type").removeClass("alert-success");
				jQuery("#alert-type").addClass("alert-warning");
				jQuery("#alert-div").attr('style','display:block');
				jQuery("#alert_msg").html(obj["message"]);
				jQuery("#top-button").trigger('click');
			}
		});
	}
	
	

});


</script>

 

<div class="content1">
	<div class="container">
    	<div class="row orderDetailsAlert" id="alert-div" style="display:none;">  <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
          <div class="alert alert-success alert-dismissible" role="alert" id="alert-type">
  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
  <span id="alert_msg"></span>
</div></div>
</div> 
        <section class="detail-view-box">
        	<div class="row">
                <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
                	<ul class="bxslider">
                      <c:forEach items="${productBean.product.images}" var="image">
                      <li><img src="${pageContext.request.contextPath}/${image.url}"/></li>
                      
                      </c:forEach>
                      
                    </ul>
                    
                    <!-- <div id="bx-pager" class="margin-bottom-40">
                      <c:forEach items="${productBean.product.images}" var="image" varStatus="loop">
                      	<a data-slide-index="${loop.index}" href=""><img src="${pageContext.request.contextPath}/${image.url}" class="img-responsive" /></a>
                      </c:forEach>
                    </div>
                     -->
                    <!-- <span class="socialListingPage marginT50"><span class="marginR5">Share:</span>
                    <a title="Facebook" data-placement="top" data-toggle="tooltip" class="tooltips" href="#"><i class="fa fa-facebook-square"></i></a>
                    <a title="GooglePlus" data-placement="top" data-toggle="tooltip" class="tooltips" href="#"><i class="fa fa-google-plus-square"></i></a>
                    <a title="Twitter" data-placement="top" data-toggle="tooltip" class="tooltips" href="#"><i class="fa fa-twitter-square"></i></a>
                    <a title="LinkedIn" data-placement="top" data-toggle="tooltip" class="tooltips" href="#"><i class="fa fa-linkedin-square"></i></a>
                    <a title="Pinterest" data-placement="top" data-toggle="tooltip" class="tooltips" href="#"><i class="fa fa-pinterest-square"></i></a>
                </span> -->
                </div>
                
                <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
                    <div class="product-header">
                        <h3>${productBean.product.ptitleName}</h3>
                        <p id="seller_name" ><span>Sold by: ${productBean.product.sellerInfo.brandName}</span></p>
                    </div>  
                    <p class="product-description">${productBean.product.ptitleDesc}</p>
					<form
							id="ptitleSelection"
							action="${pageContext.request.contextPath}/product/ptitleSelection"
							method="get">
							<input type="hidden" id="productId" name="productId" value="${productBean.product.productId}">
							<input type="hidden" id="productName" name="productName" value="${productBean.product.productName}"><div class="product-type clearfix">
                    	<label for="type">Type</label>
                        <div class="price-box">
                        	<select id="type" name="type">
                            	<option value="-1">Choose an option...</option>
												<c:forEach items="${productBean.product.allPtitles}"
													var="ptitle">
													<option value="${ptitle.name}-${ptitle.ptitleId}" <c:if test="${ptitle.ptitleId == productBean.product.ptitleId}">selected</c:if>>
														${ptitle.name}</option>
													</c:forEach>
													
                            </select>
                        </div>
                    </div>
                    </form>
                    <div class="product-price-box clearfix">
                        <div class="weight-price">
                            <label for="weight">Select Weight</label>
                            <div class="price-box">
                                <select id="weight">
                                    <option value="">Choose an option...</option>
												<c:forEach items="${productBean.product.bestTags}" var="tag">
												<option  value="${tag.tagId}-${tag.bucketKey}-${tag.pricePerUnit}-${tag.inStock}-${tag.availability}"
														<c:if test="${tag.tagId == productBean.product.tagId}">selected</c:if>>${tag.pricePerUnit} - ${tag.unitString}</option>
														</c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="quantity">
                            <label class="quantity">Qty</label>
                            <input type="text" name="" id="quantity" value=1>
                        </div>
                        <a href="#" class="add-to-cart" onclick="addToCart('${productBean.product.ptitleName}');">ADD  TO CART</a>
                        
                    </div>
                    <div class="popupmoreInfo marginT10"></div>
                    
                    <div class="tags-box">
                        <p>Tags</p>
                        <c:forEach
									items="${productBean.product.nodeMap}" var="node">
									<a href = "${pageContext.request.contextPath}/shop/node/${node.key}/${fn:replace(fn:toLowerCase(node.value),' ','-')}">
									<span class="label label-primary">${node.value}</span></a>
									</c:forEach>
                       <p class="marginT20">Categories</p>
                        <c:forEach
									items="${productBean.product.categoryMap}" var="cat">
									<a href = "${pageContext.request.contextPath}/shop/category/${cat.key}/${fn:replace(fn:toLowerCase(cat.value),' ','-')}">
									<span class="label label-primary">${cat.value}</span></a>
									</c:forEach>
                        
                    </div>
                      
                </div>
            
          	</div>  
        </section>
        
        <section class="other-info">
        	<div class="row">
            	<div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
                	<h1 class="main-heading">Additional Information</h1>
                    
                    <!-- Nav tabs -->
                      <ul class="nav nav-tabs" role="tablist">
                        <li role="presentation" class="active"><a href="#first" aria-controls="first" role="tab" data-toggle="tab">About Brand</a></li>
                         <li role="presentation"><a href="#second" aria-controls="second" role="tab" data-toggle="tab">About Product</a></li>
                      </ul>
                    
                      <!-- Tab panes -->
                      <div class="tab-content tab-details">
                        <div role="tabpanel" class="tab-pane active" id="first">
                        	<h3>${productBean.product.sellerInfo.brandName} </h3>
                            <span class="place-name">${productBean.product.sellerInfo.location}</span>
                            
                            <div class="logo-brand"><a href="#"><img src="${pageContext.request.contextPath}/${productBean.product.sellerInfo.image.url}" alt=""></a></div>
                            
                            <ul class="list-detail">
                            	<li><span>Located:</span> ${productBean.product.sellerInfo.location}  </li>
                            </ul>
                            <p>${productBean.product.sellerInfo.description} </p>
                            
                            <!-- <p><a href="#">Learn more</a></p> -->
                        
                         </div> 
                        
                        <div role="tabpanel " class="tab-pane" id="second">
                        	<table class="table">
							<thead>
							<tr class="shop_attributes_thead">
						<th>Attribute</th>
						<th>Information</th>
							</tr>
							</thead>
						<tbody>
						<c:forEach items="${productBean.product.attrs}" var="attr">
						<tr class="">
						<td>${attr.name}</td>
						<td>${attr.value}</td>
						</tr>
						</c:forEach>
					</tbody>
					</table>                        
                        </div>
                       
                    
                    
                </div>
                
                
                
            </div>
        </section>
        
        <section class="product-like">
        	<h1 class="main-heading">You may also like</h1>
        	<ul class="product-list clearfix">
        	<c:forEach items="${productBean.product.relatedProducts}" var="product">
        	<li>
            	
            	<c:set var="productLink" value="${pageContext.request.contextPath}/shop/product/${product.productId}
						/${fn:replace(fn:toLowerCase(product.productName),' ','-')}">
				</c:set>
				<div class="product-detail">
                	<div class="product-image">
                    	<a href="${productLink}"><img class="img-responsive" alt="" src="${pageContext.request.contextPath}/${product.image.url}"></a>
                        <!-- <div class="quick-wiew"><a data-target=".model-quick-view" data-toggle="modal" class="dropdown-toggle js-quickView" href="javascript:;">Quick View	</a></div> -->
                    </div>
                    <div class="product-info">
                    	<h3>${product.productName}</h3>
                        <p>${product.sellerName}, ${product.sellerLocation}</p>
                    </div>
                   
                </div>
               
            </li>
             </c:forEach>
            
            
        </ul>
        </section><!--product like-->
        
        <section class="rating-review-box">
        	<h1 class="main-heading">Rating &amp; Reviews <a href="#" class="dropdown-toggle" data-toggle="modal" data-target=".commentModals">Write a Review</a></h1>
            
            <ul class="review-list">
            	<c:forEach items="${productBean.reviews}" var="review">
            	<li>
                	<div class="imageClass"><a href="" class="pull-left">
              <img alt="" src="${pageContext.request.contextPath}/resources/images/photos/user3.png" class="media-object thumbnail">
            </a></div>
                    <div class="review-info marginLeft140">
                    	<span class="box-arrow"></span>
                         <h2 class="testHeadName">${review.userName}</h2>
                    	<div class="rating"><input value="${review.rating}" class="rating1" data-size="xs"  ></div>
                        <h3>${review.title}</h3>
                        <p>${review.description}</p>
                    </div>
                </li>
                </c:forEach>
                
            </ul>
        </section>
        
        
    </div><!--end of container-->
</div><!--end of content-->


