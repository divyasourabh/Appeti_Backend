<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page session="true"%>

		<h3 id="order_review_heading">Your order</h3>



		<div id="order_review" class="woocommerce-checkout-review-order">
			<table class="shop_table woocommerce-checkout-review-order-table">
				<thead>
					<tr>
						<th class="product-name">Product</th>
						<th class="product-total">Total</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${checkoutBean.order.items}" var="item">
					<tr class="cart_item">
						<td class="product-name">${item.productName} <strong
							class="product-quantity">X ${item.quantity}</strong>
							<dl class="variation">
								<dt class="variation-Type">Type:</dt>
								<dd class="variation-Type">
									<p>${item.ptitleName}</p>
								</dd>
								<dt class="variation-Numbers">Numbers:</dt>
								<dd class="variation-Numbers">
									<p>${item.unit}</p>
								</dd>
							</dl>
						</td>
						<td class="product-total"><span class="amount">Rs. ${item.totalPrice}</span>
						</td>
					</tr>
					</c:forEach>
					
					
				</tbody>
				<tfoot>

					<tr class="cart-subtotal">
						<th>Subtotal</th>
						<td><span class="amount">Rs. ${checkoutBean.order.totalAmount}</span></td>
					</tr>

					<tr class="cart-subtotal">
						<th>Discount</th>
						<td><span class="amount">Rs. ${checkoutBean.order.discount}</span></td>
					</tr>
					<!-- 
					<tr class="cart-subtotal">
						<th>Tax</th>
						<td><span class="amount">Rs.${checkoutBean.order.taxAmount}</span></td>
					</tr>
					 -->
					<tr class="cart-subtotal">
						<th>Delivery Charges</th>
						<td><span class="amount">
						<c:choose>
						<c:when test="${checkoutBean.order.deliveryCharge == 0}">
						FREE SHIPPING
						</c:when>
						<c:otherwise>
						Rs. ${checkoutBean.order.deliveryCharge} (Free Shipping above Rs. 300)
						</c:otherwise>
						</c:choose>
						</span></td>
					</tr>


					<tr class="order-total">
						<th>Total</th>
						<td><strong><span class="amount">Rs. ${checkoutBean.order.amountPaid}</span></strong>
						</td>
					</tr>


				</tfoot>
			</table>

			<div id="payment" class="woocommerce-checkout-payment">
				<p class="form-row terms">
						 <input type="checkbox" class="input-checkbox" name="terms"
							id="terms"><label for="terms" class="checkbox">&nbsp;I've read and accept
							the <a href="${pageContext.request.contextPath}/terms-of-service/"
							target="_blank">terms &amp; conditions</a>
						</label>
					</p>
					<br>
					<!-- <ul class="payment_methods methods">
					<li class="payment_method_ccavenue"><input
						id="payment_method_ccavenue" type="radio" class="input-radio"
						name="p_gate" value="2" checked="checked" data-order_button_text="">

						<label for="payment_method_ccavenue"> Online Payments (CC
							Avenue) <img
							src="http://www.appeti.in/wp-content/plugins/ccavenue-payment-gateway-woocommerce/images/logo.gif"
							alt="Online Payments (CC Avenue)">
					</label>
						<div class="payment_box payment_method_ccavenue"
							style="display: none;">
							<p>Pay securely by Credit or Debit card or internet banking
								through CCAvenue Secure Servers.</p>
						</div></li>
				</ul>
 				-->
				<div class="form-row place-order">

					<input type="submit" class="button alt"
						name="woocommerce_checkout_place_order" id="place_order"
						value="Confirm & Pay" data-value="Confirm & Pay">
					


				</div>

				<div class="clear"></div>
			</div>

		</div>