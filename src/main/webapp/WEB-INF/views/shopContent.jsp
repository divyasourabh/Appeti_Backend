<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type='text/javascript' src='${pageContext.request.contextPath}/resources/script_old/jquery.js?ver=1.11.2'></script>

<script type="text/javascript">
jQuery(function($){
	$("#orderBy").change(function() {
		$("#applySort").submit();
		});
	});
	
function addToCartWoq(prod, ptitle, tag, name){
	return addToCart(prod, ptitle, tag, name,1);
}

function addToCartWq(prod, ptitle, tag, name){
	var quantity = $('#mquantity').val();
	addToCart(prod, ptitle, tag, name,quantity);
	jQuery("#quick-view-close").trigger('click');
}

function addToCart(prod, ptitle, tag, name,quantity) {
	var formURL = "${pageContext.request.contextPath}/cart/add";
	jQuery.ajax({
		url : formURL,
		type : "POST",
		data : {productId:prod,ptitleId:ptitle,tagId:tag,quantity:quantity},
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


function renderView(tid){
	clearModal();
	var formURL = "${pageContext.request.contextPath}/searchResult";
	jQuery.ajax({
		url : formURL,
		type : "POST",
		data : {tid:tid},
		success : function(response) {
			var obj = JSON.parse(response);
			$('#product_name').html(obj["ptitleName"]);
			$('#seller_name').html('<span>Sold by:</span> '+ obj["sellerName"] + ', ' + obj["sellerLocation"]);
			$('#description').html(obj["description"]);
			$('#weight').html(obj["pricePerUnit"] + ' - ' + obj["unit"]);
			$('#link').attr("href",'${pageContext.request.contextPath}/product/'+obj["productId"]+'-'+obj["ptitleId"]+'-'+obj["tagId"]+'/'+obj["productName"].trim().toLowerCase().replace(' ','-')+''+obj["ptitleName"].trim().toLowerCase().replace(' ','-')+'-'+obj["unit"].trim().replace(' ','-')+'-'+obj["sellerName"].trim().toLowerCase().replace(' ','-')+'-'+obj["sellerLocation"].trim().toLowerCase().replace(' ','-'));
			var images = obj["images"];
			var arrayLength = images.length;
			var row1 = '';
			var row2 = '';
			for (var i = 0; i < arrayLength; i++) {
				var image = images[i];
				var url = image["url"];
				var text = image["text"];
				row1 += '<li><img src="${pageContext.request.contextPath}/' + url + '" width="514" height="336"/></li>';
				row2 += '<a data-slide-index="'+i+'" href=""><img src="${pageContext.request.contextPath}/'+ url + '" width="133" height="89" class="img-responsive" /></a>'
			}
			$('#images1').html(row1);
			$('#images2').html(row2);
			
			var tags = obj["nodeMap"];
			var tagsLength = tags.length;
			var tagRow = '';
			for (var t in tags){
				var value = tags[t];
				tagRow += '<a href="${pageContext.request.contextPath}/shop/node/'+t+'/'+value.toLowerCase().replace(' ','-')+'"><span class="label label-primary">' + value + '</span></a>\n';
			}
			$('#tags').html(tagRow);
			
			var cats = obj["categoryMap"];
			var catsLength = cats.length;
			var catRow = '';
			for (var c in cats){
				var value = cats[c];
				catRow += '<a href="${pageContext.request.contextPath}/shop/category/'+c+'/'+value.toLowerCase().replace(' ','-')+'"><span class="label label-primary">' + value + '</span></a>\n';
			}
			$('#categories').html(catRow);
			
			$('#add_to_cart').click(function(){addToCartWq(obj["productId"],obj["ptitleId"],obj["tagId"],obj["ptitleName"]); });
			},
		error : function(jqXHR, textStatus, errorThrown) {
			
		}
	});
}

function clearModal(){
	$("#product_name").html('');
	$("#seller_name").html('');
	$("#description").html('');
	$("#weight").html('');
	$('#link').attr("href",'#');
	$('#images1').html('');
	$('#images2').html('');
	$('#tags').html('');
	$('#categories').html('');
	$('#mquantity').val(1);
}
</script>
<div class="content1">
	<div class="container">
    	<div class="breadcrumb-box">
            <ol class="breadcrumb">
              <li><a href="#">The Shop</a></li>
              <c:forEach items="${searchBean.nodeMap}" var="node">
              <li><a href="${pageContext.request.contextPath}/shop/node/${node.key}/${fn:replace(fn:toLowerCase(node.value),' ','-')}">
              ${node.value}</a></li>
              </c:forEach>
              
            </ol>
    	</div>
        
        <!--  <div class="row blog-post-view">
       	  <div class="col-lg-12 col-sm-12 col-md-12 col-xs-12 green postContent">
            	<div class="blog-img-big">
                	<img src="${pageContext.request.contextPath}/resources/images/seller_banners/vsweets-brand-banner.jpg" width="1199" height="366">
              </div>
                <div class="blog-contents-method">
                <h5>ABOUT Sri Venkateshwara SweetMeat Stall <span class="socialListingPage pull-right"><span class="marginR5">Share:</span>
                    <a title="Facebook" data-placement="top" data-toggle="tooltip" class="tooltips" href="#"><i class="fa fa-facebook-square"></i></a>
                    <a title="GooglePlus" data-placement="top" data-toggle="tooltip" class="tooltips" href="#"><i class="fa fa-google-plus-square"></i></a>
                    <a title="Twitter" data-placement="top" data-toggle="tooltip" class="tooltips" href="#"><i class="fa fa-twitter-square"></i></a>
                    <a title="LinkedIn" data-placement="top" data-toggle="tooltip" class="tooltips" href="#"><i class="fa fa-linkedin-square"></i></a>
                    <a title="Pinterest" data-placement="top" data-toggle="tooltip" class="tooltips" href="#"><i class="fa fa-pinterest-square"></i></a>
                </span></h5>
                	
                    <p class="listingText">

To make the best Mysore pak is the singlemost passion that drives Sri Venkateshwara SweetMeat Stall on the Balepet main road. The taste of the Mysore pak is unlike any other. The credit for this goes to Mr.V A. Venkatachalapathy Setty, who started the shop in 1954, on Rangaswamy Temple Street. In 1972 he moved to Balepet main road its current location. Mr.Setty at the age of 16 used to sell sweets and spicy mixtures at temple festivals and jatres in and around Chikkaballapur when started in 1935.
</p><hr>
                </div>
            </div>
            
            
          </div>
           -->
        <div class="row orderDetailsAlert" id="alert-div" style="display:none;">  <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
          <div class="alert alert-success alert-dismissible" role="alert" id="alert-type">
  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
  <span id="alert_msg"></span>
</div></div>
</div>  
        <div class="clearfix heading-filter"> 
          		     <h2 class="product-name pull-left">${searchBean.nodeMap[searchBean.nodeId]}<span>Showing ${fn:length(searchBean.results)} results</span></h2>
          		     <div class="filter-box">
        		 <form id="applySort" method="get" action='${pageContext.request.contextPath}${requestScope['javax.servlet.forward.servlet_path']}'>
				<input type="hidden" id="query" name="query" value="${searchBean.query}">           	
        		 <select name="orderBy" id="orderBy">
                	<option value="1" <c:if test="${searchBean.orderBy ==1 }">selected</c:if>>Sort by popularity</option>
					<option value="2" <c:if test="${searchBean.orderBy ==2 }">selected</c:if>>Sort by average rating</option>
					<option value="3" <c:if test="${searchBean.orderBy ==3 }">selected</c:if>>Sort by newness</option>
					<option value="4" <c:if test="${searchBean.orderBy ==4 }">selected</c:if>>Sort by price: low to high</option>
					<option value="5" <c:if test="${searchBean.orderBy ==5 }">selected</c:if>>Sort by price: high to low</option>
                </select>
                </form>
            </div>
        </div>
        
        
        <ul class="product-list clearfix">
        <c:forEach items="${searchBean.results}" var="result">	
        	<c:set var="productLink" value="${pageContext.request.contextPath}/product/${result.productId}-${result.ptitleId}-${result.tagId}/${fn:replace(fn:toLowerCase(result.productName),' ','-')}-${fn:replace(fn:toLowerCase(result.ptitleName),' ','-')}-${fn:replace(fn:toLowerCase(result.unit),' ','-')}">
			</c:set>
			<c:set var="productName" value="${result.ptitleName}"></c:set>
			<li>
			<c:choose>
			<c:when test="${result.inStock == true }">
				<div class="product-detail ">
                	<div class="product-image" style="height:237">
                    <c:if test="${isNew}"><div class="new_prod"></div></c:if>
                    <c:if test="${isOffer}"><span class="offerBadge">OFFER</span></c:if>
                    	<c:set var="url" value="${result.images[0].url}"></c:set>
                    	<c:if test="${empty result.images[0].url}">	<c:set var="url" value="resources/images/no-product-img.jpg"></c:set> </c:if>
                    	<a href="${productLink}"><img src="${pageContext.request.contextPath}/${url}"
                    	 width="363" height="237" alt="" class="img-responsive"></a>
                        <div class="quick-wiew"><a href="javascript:void(0);"  onclick="renderView(${result.tagId});" class="dropdown-toggle js-quickView"   data-toggle="modal" data-target=".model-quick-view">Quick View</a></div>
                    </div>
                    <div class="product-info">
                    	<h3>${productName}</h3>
                        <div class="moreInfo"><p class="pull-left">${result.sellerName}, ${result.sellerLocation}</p></div>
                    </div>
                    <div class="product-price clearfix">
                    	<div class="price-box">${result.pricePerUnit} - ${result.unit} </div>
                        <a href="#" class="add-to-cart" onclick="addToCartWoq(${result.productId},${result.ptitleId},${result.tagId},'${productName} (${result.unit})');">Add To Cart</a>
                    </div>
                </div>
			</c:when>
			<c:otherwise>
				<div class="product-detail ">
                	<a class="soldout-tag btn btn-default" href="javascript:;">${result.availability}</a><div class="prodSoldout">
                	<div class="product-image">
                    <div class="new_prod"></div><span class="offerBadge">OFFER</span>
                    	<a href="#"><img src="${pageContext.request.contextPath}/${result.images[0].url}" alt="" width="363" height="237" class="img-responsive"></a>
                        <div class="quick-wiew"><a href="javascript:;"  class="dropdown-toggle js-quickView"   data-toggle="modal" data-target=".model-quick-view">Quick View</a></div>
                    </div>
                    <div class="product-info">
                    	<h3>${productName}</h3>
                        <div class="moreInfo"><p class="pull-left">${result.sellerName}, ${result.sellerLocation}</p></div>
                    </div>
                    <div class="product-price clearfix">
                    	<div class="price-box">${result.pricePerUnit} - ${result.unit} </div>
                        <a href="#" class="add-to-cart" >Add To Cart</a>
                    </div>
                </div></div>
			</c:otherwise>
			</c:choose>
			</li>
         </c:forEach>
            
        </ul>
        
        
    </div><!--end of container-->
</div><!--end of content-->

