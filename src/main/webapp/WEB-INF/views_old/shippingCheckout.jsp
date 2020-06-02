<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript">
jQuery(function ($) {
	$("#ship-to-different-address-checkbox").change(
		    function(){
		        if ($(this).is(':checked')) {
		        	$("#shipping_address").attr('style','display:block');
		        }else{
		        	$("#shipping_address").attr('style','display:none');
		        }
		    });
});
</script>
		<div class="woocommerce-message x-alert x-alert-danger x-alert-block" id="message_div" style="display:none;">
		</div>
		<div class="col2-set" id="customer_details">
			<div class="col-1">
				<div class="woocommerce-billing-fields" id="billing-fields">

					<h3>Billing Address</h3>
					<p
						class="form-row form-row-wide address-field update_totals_on_change validate-required"
						id="billing_country_field">
						<label class="">Country</label><strong>India</strong><input
							type="hidden" name="billing_country" id="billing_country"
							value="IN" class="country_to_state">
					</p>

					<p class="form-row form-row-first validate-required"
						id="billing_first_name_field">
						<label for="billing_first_name" class="">First Name <abbr
							class="required" title="required">*</abbr></label><input type="text"
							class="input-text " name="billing_first_name"
							id="billing_first_name" placeholder="" value="${checkoutBean.defaultAddr.firstName }">
					</p>

					<p class="form-row form-row-last validate-required"
						id="billing_last_name_field">
						<label for="billing_last_name" class="">Last Name <abbr
							class="required" title="required">*</abbr></label><input type="text"
							class="input-text " name="billing_last_name"
							id="billing_last_name" placeholder="" value="${checkoutBean.defaultAddr.lastName}">
					</p>
					<div class="clear"></div>

					<p class="form-row form-row-wide" id="billing_company_field">
						<label for="billing_company" class="">Company Name</label><input
							type="text" class="input-text " name="billing_company"
							id="billing_company" placeholder="" value="${checkoutBean.defaultAddr.name1}">
					</p>

					<p class="form-row form-row-wide address-field validate-required"
						id="billing_address_1_field">
						<label for="billing_address_1" class="">Address <abbr
							class="required" title="required">*</abbr></label><input type="text"
							class="input-text " name="billing_address_1"
							id="billing_address_1" placeholder="Street address" value="${checkoutBean.defaultAddr.addr1}">
					</p>

					<p class="form-row form-row-wide address-field"
						id="billing_address_2_field">
						<input type="text" class="input-text " name="billing_address_2"
							id="billing_address_2"
							placeholder="Apartment, suite, unit etc. (optional)" value="${checkoutBean.defaultAddr.addr2}">
					</p>

					<p class="form-row form-row-wide address-field validate-required"
						id="billing_city_field"
						data-o_class="form-row form-row-wide address-field validate-required">
						<label for="billing_city" class="">Town / City <abbr
							class="required" title="required">*</abbr></label><input type="text"
							class="input-text " name="billing_city" id="billing_city"
							placeholder="Town / City" value="${checkoutBean.defaultAddr.city}">
					</p>
					
					<p class="form-row form-row-first address-field validate-required"
						id="billing_city_field"
						data-o_class="form-row form-row-wide address-field validate-required">
						<label for="billing_state" class="">State / County <abbr
							class="required" title="required">*</abbr></label>
							<input type="text"
							class="input-text " name="billing_state" id="billing_city"
							placeholder="State / County " value="${checkoutBean.defaultAddr.state}">
					</p>
					
					
					<p
						class="form-row form-row-last address-field validate-required validate-postcode "
						id="billing_postcode_field"
						data-o_class="form-row form-row-last address-field validate-required validate-postcode">
						<label for="billing_postcode" class="">Postcode / Zip <abbr
							class="required" title="required">*</abbr></label><input type="text"
							class="input-text " name="billing_postcode" id="billing_postcode"
							placeholder="Postcode / Zip" value="${checkoutBean.defaultAddr.zipCode}">
					</p>

					<div class="clear"></div>

					<p class="form-row form-row-first validate-required validate-email"
						id="billing_email_field">
						<label for="billing_email" class="">Email Address <abbr
							class="required" title="required">*</abbr></label><input type="text"
							class="input-text " name="billing_email" id="billing_email"
							placeholder="" value="${checkoutBean.defaultAddr.emailAddr}">
					</p>

					<p class="form-row form-row-last validate-required validate-phone"
						id="billing_phone_field">
						<label for="billing_phone" class="">Phone Number (10 digits)<abbr
							class="required" title="required">*</abbr></label><input type="text"
							class="input-text " name="billing_phone" id="billing_phone"
							pattern="^\d{10}$" placeholder="" value="${checkoutBean.defaultAddr.phoneNumber}">
					</p>
					<div class="clear"></div>

					<input id="mark_address_as_default"
							class="input-checkbox" type="checkbox"
							<c:if test="${checkoutBean.defaultAddr.isDefault==true}"> checked</c:if>
							name="mark_address_as_default" value="1"> <label
							for="ship-to-different-address-checkbox" class="checkbox" >Mark address as default</label>

				</div>
			</div>

			<div class="col-2">

				<div class="woocommerce-shipping-fields" id="shipping-fields">

					<h3 id="ship-to-different-address"
						class="ship-to-different-address">
						<input id="ship-to-different-address-checkbox"
							class="input-checkbox" type="checkbox"
							name="ship_to_different_address" value="1"> <label
							for="ship-to-different-address-checkbox" class="checkbox">Ship
							to a different address?</label>
					</h3>

					<div id="shipping_address" class="shipping_address" style="display: none;">
						<p
							class="form-row form-row-wide address-field update_totals_on_change validate-required"
							id="shipping_country_field">
							<label class="">Country</label><strong>India</strong><input
								type="hidden" name="shipping_country" id="shipping_country"
								value="IN" class="country_to_state">
						</p>
						<p class="form-row form-row-first validate-required"
							id="shipping_first_name_field">
							<label for="shipping_first_name" class="">First Name <abbr
								class="required" title="required">*</abbr></label><input type="text"
								class="input-text " name="shipping_first_name"
								id="shipping_first_name" placeholder="" value="">
						</p>
						<p class="form-row form-row-last validate-required"
							id="shipping_last_name_field">
							<label for="shipping_last_name" class="">Last Name <abbr
								class="required" title="required">*</abbr></label><input type="text"
								class="input-text " name="shipping_last_name"
								id="shipping_last_name" placeholder="" value="">
						</p>
						<div class="clear"></div>
						<p class="form-row form-row-wide" id="shipping_company_field">
							<label for="shipping_company" class="">Company Name</label><input
								type="text" class="input-text " name="shipping_company"
								id="shipping_company" placeholder="" value="">
						</p>
						<p class="form-row form-row-wide address-field validate-required"
							id="shipping_address_1_field">
							<label for="shipping_address_1" class="">Address <abbr
								class="required" title="required">*</abbr></label><input type="text"
								class="input-text " name="shipping_address_1"
								id="shipping_address_1" placeholder="Street address" value="">
						</p>
						<p class="form-row form-row-wide address-field"
							id="shipping_address_2_field">
							<input type="text" class="input-text " name="shipping_address_2"
								id="shipping_address_2"
								placeholder="Apartment, suite, unit etc. (optional)" value="">
						</p>
						<p class="form-row form-row-wide address-field validate-required"
							id="shipping_city_field"
							data-o_class="form-row form-row-wide address-field validate-required">
							<label for="shipping_city" class="">Town / City <abbr
								class="required" title="required">*</abbr></label><input type="text"
								class="input-text " name="shipping_city" id="shipping_city"
								placeholder="Town / City" value="">
						</p>
						<p
							class="form-row form-row-first address-field validate-required validate-state "
							id="shipping_state_field"
							data-o_class="form-row form-row-first address-field validate-required validate-state">
							<label for="shipping_state" class="">State / County <abbr
								class="required" title="required">*</abbr></label>
								<input type="text"
							class="input-text " name="shipping_state" id="shipping_state"
							placeholder="State / County " value="">

						</p>
						<p
							class="form-row form-row-last address-field validate-required validate-postcode"
							id="shipping_postcode_field"
							data-o_class="form-row form-row-last address-field validate-required validate-postcode">
							<label for="shipping_postcode" class="">Postcode / Zip <abbr
								class="required" title="required">*</abbr></label><input type="text"
								class="input-text " name="shipping_postcode"
								id="shipping_postcode" placeholder="Postcode / Zip"
								value="">
						</p>
						<div class="clear"></div>
					</div>





					<p class="form-row notes" id="order_comments_field">
						<label for="order_comments" class="">Order Notes</label>
						<textarea name="order_comments" class="input-text "
							id="order_comments"
							placeholder="Notes about your order, e.g. special notes for delivery."
							rows="2" cols="5"></textarea>
					</p>

				</div>
			</div>
		</div>