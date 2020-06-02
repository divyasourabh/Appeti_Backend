<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript">
jQuery(function ($) {
	$("#sourcing-form").submit(function (e) {	
		var rs = !0;
		$('#billing-fields .validate-required :input').each(function(index, item) {
			var r = validate_field($(item));
			if(!r){rs=r;}
		});
		if(!rs) return false;
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
<form name="sourcing" id="sourcing-form" method="post"
		class="checkout woocommerce-checkout"
		action="${pageContext.request.contextPath}/vendor-sourcing-form">
		
		<c:if test="${homeBean.result.status == true}">
		<div class="woocommerce-message x-alert x-alert-info x-alert-block" id="message_div" style="display:block;">
		${homeBean.result.message}</div></c:if>
		
		<c:if test="${homeBean.result.status == false}">
		<div class="woocommerce-message x-alert x-alert-danger x-alert-block" id="message_div" style="display:block;">
		${homeBean.result.message}</div></c:if>
		
		<div class="col2-set" id="customer_details">
			<div class="col-1">
				<div class="woocommerce-billing-fields" id="billing-fields">

					<h3>Vendor Sourcing Form</h3>
					<p></p>
					<p class="form-row form-row-wide address-field validate-required"
							id="brand_name_field">
							<label for="brand_name" class="">Name of brand/shop/vendor<abbr
								class="required" title="required">*</abbr></label><input type="text"
								class="input-text " name="brand_name"
								id="brand_name" placeholder="" value="">
						</p>
						<p class="form-row form-row-first address-field validate-required"
							id="best_price_field">
							<label for="best_product" class="">Best Selling Product<abbr
								class="required" title="required">*</abbr></label><input type="text"
								class="input-text " name="best_product"
								id="best_product" placeholder="The product which generates most sales to you"
								value="">
						</p>
						
						<p class="form-row form-row-last address-field validate-required "
							id="best_price_field">
							<label for="best_price_field" class="">Price /qty of the best selling product<abbr
								class="required" title="required">*</abbr></label><input type="text"
								class="input-text " name="best_product_price"
								id="best_price" placeholder="Price at which you sell (eg: Rs 500/kg)"
								value="">
						</p>
						<p class="form-row form-row-first address-field"
							id="second_best_product_field">
							<label for="best_product" class="">Second Best Selling Product</label><input type="text" class="input-text " name="second_best_product"
								id="second_best_product"
								placeholder="optional" value="">
						</p>
						<p class="form-row form-row-last address-field"
							id="second_best_price_field">
							<label for="second_best_price_field" class="">Price /qty of the second best selling product</label><input type="text"
								class="input-text " name="second_best_product_price"
								id="second_best_price" placeholder="optional"
								value="">
						</p>
						
						<p class="form-row form-row-wide address-field validate-required"
						id="billing_address_1_field">
						<label for="address_1" class="">Address <abbr
							class="required" title="required">*</abbr></label><input type="text"
							class="input-text " name="address_1"
							id="address_1" placeholder="Street address" value="${checkoutBean.defaultAddr.addr1}">
					</p>

					<p class="form-row form-row-wide address-field"
						id="address_2_field">
						<input type="text" class="input-text " name="address_2"
							id="address_2"
							placeholder="optional" value="${checkoutBean.defaultAddr.addr2}">
					</p>

					<p class="form-row form-row-wide address-field validate-required"
						id="city_field"
						data-o_class="form-row form-row-wide address-field validate-required">
						<label for="city" class="">Town / City <abbr
							class="required" title="required">*</abbr></label><input type="text"
							class="input-text " name="city" id="city"
							placeholder="Town / City" value="${checkoutBean.defaultAddr.city}">
					</p>
					
					<p class="form-row form-row-first address-field validate-required"
						id="state_field"
						data-o_class="form-row form-row-wide address-field validate-required">
						<label for="state" class="">State / County <abbr
							class="required" title="required">*</abbr></label>
							<input type="text"
							class="input-text " name="state" id="state"
							placeholder="State / County " value="${checkoutBean.defaultAddr.state}">
					</p>
					
					
					<p
						class="form-row form-row-last address-field validate-required validate-postcode "
						id="postcode_field"
						data-o_class="form-row form-row-last address-field validate-required validate-postcode">
						<label for="postcode" class="">Postcode / Zip <abbr
							class="required" title="required">*</abbr></label><input type="text"
							class="input-text " name="postcode" id="postcode"
							placeholder="Postcode / Zip" value="${checkoutBean.defaultAddr.zipCode}">
					</p>

					<div class="clear"></div>

					<p class="form-row form-row-first validate-required validate-email"
						id="email_field">
						<label for="email" class="">Email Address <abbr
							class="required" title="required">*</abbr></label><input type="text"
							class="input-text " name="email" id="email"
							placeholder="" value="${checkoutBean.defaultAddr.emailAddr}">
					</p>

					<p class="form-row form-row-last validate-required validate-phone"
						id="billing_phone_field">
						<label for="billing_phone" class="">Phone <abbr
							class="required" title="required">*</abbr></label><input type="text"
							class="input-text " name="phone" id="phone"
							placeholder="" value="${checkoutBean.defaultAddr.phoneNumber}">
					</p>
					
					<p class="form-row form-row-wide "
						id="website_field">
						<label for="website" class="">Website (if any) </label><input type="text"
							class="input-text " name="website" id="website"
							placeholder="Website url" value="${checkoutBean.defaultAddr.phoneNumber}">
					</p>
					
					<p class="form-row form-row-wide address-field validate-required"
						id="other_field"
						data-o_class="form-row form-row-wide address-field validate-required">
						<label for="city" class="">Other Information (if any)</label>
							
							<textarea id="other" name="other" cols="45" rows="5"
										aria-required="true" placeholder="Anything else which you want to highlight in order to reach out to massive online cutomer base through our platform."></textarea>
							
							
					</p>
						<div class="clear"></div>
						<p>
							<input type="submit" class="button" name="submit"
								value="Submit">
						</p>

				</div>
			</div>

			
		</div>
		</form>
		</div>
		