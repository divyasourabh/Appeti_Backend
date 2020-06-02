<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

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
	jQuery("#discount").html("Rs. " + val2);
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
						jQuery("#message_div").removeClass("x-alert-info");
						jQuery("#message_div").addClass("x-alert-danger");
						jQuery("#message_div").html(obj["message"]);
						jQuery("#message_div").attr('style','display:block');
						jQuery("#top-button").trigger('click');
					}
		        }
		    });
}

function updateCart(){
	jQuery("#cart-update-form").submit();
}


</script>
<div class="woocommerce">
	<c:if test="${result.status == false}">
		<div class="woocommerce-message x-alert x-alert-danger x-alert-block" id="message_div" style="display:block;">
		${result.message}
		</div>
	</c:if>
	<c:choose>
	<c:when test="${fn:length(cartBean.cart.items) == 0}">
		<div class="woocommerce-message x-alert x-alert-info x-alert-block" id="message_div" style="display:block;">
		There are no items in your cart
		<a class="button wc-forward" href="${pageContext.request.contextPath}/shop">Continue Shopping</a>
		</div>
	</c:when>
	<c:otherwise>
	<div class="woocommerce-message x-alert x-alert-info x-alert-block" id="message_div" style="display:none;">
		</div>

	

<form action="${pageContext.request.contextPath}/cart/update"
		method="post" class="cart-form" id="cart-update-form">
		<input type="hidden" name="cartId" value="${cartBean.cart.cartId}">
							
		<table class="shop_table cart" cellspacing="0">
			<thead>
				<tr>
					<th class="product-remove">&nbsp;</th>
					<th class="product-thumbnail">&nbsp;</th>
					<th class="product-name">Product</th>
					<th class="product-price">Price</th>
					<th class="product-quantity">Quantity</th>
					<th class="product-subtotal">Total</th>
				</tr>
			</thead>
			<tbody>
				

				<c:forEach items="${cartBean.cart.items}" var="item">
	
					<c:set var="productLink" value="${pageContext.request.contextPath}/product/${item.productId}-${item.ptitleId}-${item.tagId}
						/${fn:replace(fn:toLowerCase(item.productName),' ','-')}-${fn:replace(fn:toLowerCase(item.ptitleName),' ','-')}
						-${fn:replace(fn:toLowerCase(item.unit),' ','-')}">
					</c:set>
					
					<tr class="cart_item" id="cart-item-${item.cartItemId}">
						<td class="product-remove"><a
							href="#" onclick="updateItem(${item.cartItemId},1);"
							class="remove" title="Remove this item">X</a></td>
						<td class="product-thumbnail"><a
							href="${productLink}"><img
								width="180" height="180"
								src="${pageContext.request.contextPath}/${item.image.url}"
								class="attachment-shop_thumbnail wp-post-image" alt="${item.image.text}"></a>
						</td>
						<td class="product-name"><a
							href="${productLink}">${item.ptitleName}</a>
						<dl class="variation">
								<dt class="variation-Weight">Weight:</dt>
								<dd class="variation-Weight">
									<p>${item.unit}</p>
								</dd>
							</dl></td>
						<td class="product-price"><span class="amount">${item.perUnitPrice}</span>
						</td>
						<td class="product-quantity">
							<div class="quantity">
								<input type="number" step="1" min="0"
									name="cart-item-quantity-${item.cartItemId}" value="${item.quantity}"
									title="Qty" class="input-text qty text" size="4">
							</div>
						</td>
						<td class="product-subtotal"><span class="amount">Rs.${item.totalAmount}</span>
						</td>
					</tr>


				</c:forEach>
</tbody>
		</table>
		</form>
<table class="shop_table cart" cellspacing="0">
<tbody>
				<tr>
					<td colspan="6" class="actions">

						<div class="coupon action-group">
							<form action="${pageContext.request.contextPath}/cart/apply-coupon" method="post" id="apply_coupon_form" class="cart-form">
							<input type="hidden" name="cid" value="${cartBean.cart.cartId}">
							<label for="coupon_code">Coupon:</label> <input
								name="coupon_code" type="text" class="input-text"
								id="coupon_code" value="" placeholder="Coupon code"> <input
								type="submit" class="button" name="apply_coupon" onclick="validateCoupon();"
								value="Apply Coupon">
							</form>
						</div>

						<div class="update action-group">
							<input type="submit" class="button" name="update_cart" onclick="updateCart();"
								value="Update Cart">
						</div>
					</td>
				</tr>

			</tbody>
		</table>



	<div class="cart-collaterals">

		<div class="cart_totals ">

			<table cellspacing="0">

				<tbody>
					<tr class="cart-subtotal">
						<th>Cart Subtotal</th>
						<td><span class="amount" id="subtotal"></span></td>
					</tr>
					<tr class="cart-subtotal">
						<th>Discount</th>
						<td><span class="amount" id="discount"></span></td>
					</tr>
					<tr class="order-total">
						<th>Order Total</th>
						<td><strong><span class="amount" id="total"></span></strong></td>
					</tr>


				</tbody>
			</table>

			<div class="wc-proceed-to-checkout">
				<a href="${pageContext.request.contextPath}/checkout"
					class="checkout-button button alt wc-forward">Proceed to
					Checkout</a>
			</div>

		</div>

	</div>
</c:otherwise>
</c:choose>
</div>