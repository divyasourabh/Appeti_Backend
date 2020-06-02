<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type='text/javascript' src='${pageContext.request.contextPath}/resources/script_old/jquery.js?ver=1.11.2'></script>

<script type="text/javascript">
jQuery(function ($) {
	setAmount("${cartBean.cart.totalAmount}","${cartBean.cart.discount}","${cartBean.cart.discountedAmount}");
	$("#coupon_code").val("${cartBean.cart.couponCode}");
	
	$("a.remove").click(function (e) {
	    e.preventDefault(); //prevent default form submit
		});	
	$("#apply_coupon_form").submit(function (e) {
	    e.preventDefault(); //prevent default form submit
		});
});

function setAmount(val1,val2,val3){
	jQuery("#subtotal").html("Rs. " + val1);
	jQuery("#discount").html("(-) Rs. " + val2);
	jQuery("#total").html("Rs. " + val3);
}
function updateItem(item,action){
	var formURL = "${pageContext.request.contextPath}/cart/remove-item";
	jQuery.ajax(
	    	{
		        url : formURL,
		        type: "POST",
		        data: {id:item,cid:'${cartBean.cart.cartId}',action:action},
		        success:function(response) 
		        {
		        	var obj = JSON.parse(response);
					if (obj["status"] == true) {
						jQuery("#cart-item-"+item).attr('style','display:none');
						setAmount(obj["cartAmount"],obj["discount"],obj["totalAmount"]);
					} else {
						
					}
		        }
		    });
	}
	
function validateCoupon(){
	var formURL = "${pageContext.request.contextPath}/cart/validate-coupon";
	var code = jQuery("#coupon_code").val();
	jQuery.ajax(
	    	{
		        url : formURL,
		        type: "POST",
		        data: {coupon_code:code,action:formURL},
		        success:function(response) 
		        {
		        	var obj = JSON.parse(response);
					if (obj["status"] == true) {
						jQuery("#apply_coupon_form")[0].submit();
					} else {
						$("#error").text(obj["message"]);
						$("#error").css("color","red");
						$("#coupon_code").focus();
						
					}
		        }
		    });
}

function updateCart(){
	jQuery("#cart-update-form").submit();
}


</script>
<c:if test="${fn:length(cartBean.cart.items) == 0}">
<div class="content1">
	<div class="container">
    	<h1 class="marginT20 main-heading-innerPages text-left"> Shopping Cart</h1>
	<div id="primary">
		<div class="tab-body padding0 review-plan-table">
		</div>
	</div>
    <div class="marginT20">
					<div class="lg-text text-center"> There are no items in this cart. <br><a href="${pageContext.request.contextPath}/shop" class="btn btn-primary marginT20">Continue Shopping</a></div>
		</div>   
    </div>   
</div>
</c:if>
<c:if test="${fn:length(cartBean.cart.items) != 0}">
<div class="content1">
	<div class="container">
    	<h1 class="marginT20 main-heading-innerPages text-left"> Shopping Cart 
    	<a href="${pageContext.request.contextPath}/shop" ><button class="btn btn-secondary btn-sm pull-right">Continue Shopping</button></a></h1>

		  
	
	<div id="primary">
		<div class="tab-body padding0 review-plan-table">
			<table class="table">
				<thead>
					<tr>
						<th class="text-left"></th>
						<th class="text-left">Item Details</th>
                        <th class="text-left">Price</th>
                        <th class="text-left">Quantity</th>
						<th class="text-left">Subtotal</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${cartBean.cart.items}" var="item">
	
					<c:set var="productLink" value="${pageContext.request.contextPath}/product/${item.productId}-${item.ptitleId}-${item.tagId}
						/${fn:replace(fn:toLowerCase(item.productName),' ','-')}-${fn:replace(fn:toLowerCase(item.ptitleName),' ','-')}
						-${fn:replace(fn:toLowerCase(item.unit),' ','-')}">
					</c:set>
					
					<tr id="cart-item-${item.cartItemId}">
						<td>
							<img width="200" height="125" src="${pageContext.request.contextPath}/${item.image.url}" class="attachment-shop_thumbnail wp-post-image" alt="${item.image.text}">
						</td>
						<td colspan="">
							<div class="js-plan-add">
								<div class="row">
									<div class="col-md-12 col-sm-12">
										<h2 class="plan-heading"><a href="${productLink}">${item.ptitleName}</a></h2>
									</div>
								</div>
								
								<div class="row">
									<div class="col-md-12 col-sm-12">
										
										<p class="js-details-more marginT5">
											Weight: ${item.unit}
											</p>
									</div>
									
								</div>
							
										
									
								
								<p class="marginT10"><a href="#" onclick="updateItem(${item.cartItemId},1);" class="js-remove-plan">[Remove this item]</a></p>
							</div>
						</td>
                        <td>${item.perUnitPrice}</td>
                        <td><label class="marginT10"> <input type="text" class="qtyInput form-control" value="${item.quantity}"> </label></td>
                        <td><span class="plan-price">${item.totalAmount}</span></td>
					</tr>
					</c:forEach>
                    
					<tr>
						<td colspan="3">
							<div class="row">
                      <div class="col-md-12">
                      <form action="${pageContext.request.contextPath}/cart/apply-coupon" class="applyBox" method="post" id="apply_coupon_form">
                      <fieldset>
                      <div class="row">
                      <div class="col-md-4 col-sm-4">		                
                       <input type="text" placeholder="Coupon Code" name="coupon_code" id="coupon_code" class="form-control">		                	
                       </div>
                       <div class="col-md-4 col-sm-4 noMargin">
                       <button class="btn btn-sm btn-default" onclick="validateCoupon();" name="apply_coupon">Apply</button>							
                       </div>
                       <div class="col-md-4 col-sm-4">       							
       						</div> 
                      </div>									
							                          
						<p class="error-msg" id="error"></p>
       				</fieldset>	
       				</form>
                </div>
                </div>
            
            
							
							
						</td>
                        <td class="text-right" colspan=""> 							Cart Subtotal:<br><span class="small color-gray">Discounts:</span> 						</td>
                        <td colspan="">
							<span id="subtotal"></span><br><span class="small color-gray" id="discount"></span>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
    <div class="marginT20">
			
				<div class="pull-right ">
					<div class="lg-text amountPay">Amount Payable: <span class="amountPay" id="total"></span></div>
					
				</div>
		
		</div>
        
        
        
        
    </div><!--end of container-->
</div><!--end of content-->
</c:if>
<div class="form-bottom-strip"> 
		<div class="container  steps">
			<%if(session != null && session.getAttribute("user") != null){%>
			<button class="btn btn-primary marginT7 pull-right btn-lg" onclick="document.location.href='${pageContext.request.contextPath}/checkout'" type="button">Proceed to Checkout</button>
			<%}else{%>
			<button class="btn btn-primary marginT7 pull-right btn-lg" data-toggle="modal" data-target=".modals" type="button" onclick="setVal('${pageContext.request.contextPath}/checkout');">Proceed to Checkout</button>
			<%}%>
			
		</div>
	</div>
	
	