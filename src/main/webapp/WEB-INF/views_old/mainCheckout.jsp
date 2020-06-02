<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript">
jQuery(function ($) {
	$("#checkout-form").submit(function (e) {	
		var rs = !0;
		$('#billing-fields .validate-required :input').each(function(index, item) {
			var r = validate_field($(item));
			if(!r){rs=r;}
		});
		if($("#ship-to-different-address-checkbox").is(':checked')){
			$('#shipping-fields .validate-required :input').each(function(index, item) {
				var r = validate_field($(item));
				if(!r){rs=r;}
			});
		}
		if(!rs) return false;
		if(!$("#terms").is(':checked')){
			alert("Please accept terms & conditions");
			return false;
		}
		return true;
	});
	function validate_field(b) {
	    var c = b.closest(".form-row"),
	    d = !0;
	    if ("" === b.val() && (c.removeClass("woocommerce-validated").addClass("woocommerce-invalid woocommerce-invalid-required-field"), d = !1), c.is(".validate-email") && b.val()) {
	        var e = new RegExp(/^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i);
	        e.test(b.val()) || (c.removeClass("woocommerce-validated").addClass("woocommerce-invalid woocommerce-invalid-email"), d = !1)
	       
	    }
	    d && c.removeClass("woocommerce-invalid woocommerce-invalid-required-field").addClass("woocommerce-validated")
	    return d;
	}

});



</script>

<div class="woocommerce">
	<c:choose>
	<c:when test="${fn:length(checkoutBean.order.items) == 0}">
		<div class="woocommerce-message x-alert x-alert-info x-alert-block" id="message_div" style="display:block;">
		There are no items in your cart
		<a class="button wc-forward" href="${pageContext.request.contextPath}/shop">Continue Shopping</a>
		</div>
	</c:when>
	<c:otherwise>
	<form name="checkout" id="checkout-form" method="post"
		class="checkout woocommerce-checkout"
		action="${pageContext.request.contextPath}/checkout">
		<input type="hidden" name="oid" id="oid" value="${checkoutBean.order.orderId}">
		<%@ include file="shippingCheckout.jsp"%>		
		
		<%@ include file="orderCheckout.jsp"%>

	</form>
</c:otherwise>
</c:choose>
</div>