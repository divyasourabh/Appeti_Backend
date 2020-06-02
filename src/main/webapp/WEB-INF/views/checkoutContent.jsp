<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type='text/javascript' src='${pageContext.request.contextPath}/resources/script_old/jquery.js?ver=1.11.2'></script>

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

function addAdress(){
	var fName = $('#first_name').val();
	var lName = $('#last_name').val();
	var city = $('#city').val();
	var state = $('#state').val();
	var code = $('#postcode').val();
	var email = $('#email').val();
	var addr1 = $('#address_1').val();
	var phone = $('#phone').val();
	
	var ok = true;
	var err = "";
	if (email == "") {
		ok = false;
		err = "Please enter valid email address";
	}else if(fName == "" || lName == "" || city == "" || state == "" || code == "" || addr1 == "" || phone == ""){
		ok = false;
		err = "Required fields missing";
	}
	if(ok == true){
	var postData = $("#add_address_form").serializeArray();
	var formURL = "${pageContext.request.contextPath}/editAddressService";
	jQuery.ajax({
		url : formURL,
		type : "POST",
		data : postData,
		success : function(response) {
			window.location.reload();
		},
		error : function(jqXHR, textStatus, errorThrown) {
			
		}
	});
	}
	else{
		$('#add_err').text(err);
		$("#add_err").css("color","red");
		
	}
		
}

function deleteAddr(item){
	var formURL = "${pageContext.request.contextPath}/removeAddressService";
	jQuery.ajax(
	    	{
		        url : formURL,
		        type: "POST",
		        data: {addrId:item},
		        success:function(response) 
		        {
		        	jQuery("#addr-item-"+item).attr('style','display:none');
						
		        }
		    });
	}
	
</script>

<c:choose>
		<c:when test="${checkoutBean.order.sellerMap.size() == 0}"></c:when>        
        <c:otherwise>
		
<div class="content1">
	<div class="container">
    	<div class="breadcrumb-box">
            <ol class="breadcrumb">
              <li><a href="${pageContext.request.contextPath}/shop">Shop</a></li>
              <li><a href="${pageContext.request.contextPath}/cart">View Cart</a></li>
              <li class="active">Secure Checkout</li>
            </ol>
    	</div>
        
        
        <div class="row">
        	<div class="col-lg-8 col-sm-8 col-md-8 col-xs-12">
            	        <h1 class="marginT20 main-heading-innerPages text-left"> Secured Checkout</h1>
                <form class="customFormClass information_container custom-inputs" method="post" id="checkout-form" action="${pageContext.request.contextPath}/checkout">
					<fieldset>
					<legend>Select Address</legend>
                    
                    
                    <div class="clearfix">
					<span class="pull-left marginL5">${fn:length(checkoutBean.address)} Addresses Found</span>
				 <a data-toggle="modal" data-target=".addAddresModals" href="" class="dropdown-toggle btn btn-default pull-right btn-sm"><strong>Add A New Address</strong></a>
				</div>
				<hr />
				<div class="row">
					<div class="col-md-12 col-sm-12">
						<c:forEach items="${checkoutBean.address}" var="addr">
							<div class="row marginT20 listAddress adrressRow <c:if test="${addr.isDefault}">select-address</c:if>" id="addr-item-${addr.id}">
							<div class="col-md-4 col-sm-4">
								<div class="row">
									<div class="col-md-2 col-sm-2 col-xs-2">
										<label class="customRadio"><input class="" type="radio" checked name="address" value="${addr.id}"></label>
									</div>
									<div class="col-md-10 col-sm-10 paddingL0">
										<strong>${addr.firstName} ${addr.lastName}</strong>
										<p><c:if test="${addr.isDefault}">(Default)</c:if></p>
									</div>
								</div>
							</div>
							<div class="col-md-4 col-sm-4">
								<c:if test="${not empty addr.name1 || not empty addr.name2}"><c:if test="${not empty addr.name1}">${addr.name1},</c:if>${addr.name2}<br></c:if>
								<c:if test="${not empty addr.addr1 || not empty addr.addr2}"><c:if test="${not empty addr.addr1}">${addr.addr1},</c:if>${addr.addr2}<br></c:if>
								<c:if test="${not empty addr.city}">${addr.city}<br></c:if>
								<c:if test="${not empty addr.state}">${addr.state}<br></c:if>
								<c:if test="${not empty addr.zipCode}">${addr.zipCode}<br></c:if>
								Mobile: ${addr.phoneNumber}
							</div>
                            <div class="col-md-4 col-sm-4">
								<a class="btn btn-primary" onclick="deleteAddr(${addr.id});">Delete</a>
							</div>
						</div>
						</c:forEach>
						
						
					</div>      
						
				</div>
						
       					<hr />
						<h2 class="marginT20 legend">Order Notes</h2>
       					<div class="row">
       						<div class="col-md-12 col-sm-12 form-group">
       							<label for="" class=""> Please Type Below</label> 
       							<textarea class="form-control" name="order_comments" placeholder="Notes about your order, e.g. special notes for delivery."></textarea>
       						</div>
							
						</div>
                        <h2 class="legend">Choose Payment Mode</h2>
                        <div class="row marginT10">
       						<div class="col-md-4 col-sm-4 form-group">
       							<div class="row">
									<div class="col-md-2 col-sm-2 col-xs-2">
										<label class="customRadio"><input class="" type="radio" checked name="selectPaymentMethodRadio" value="3"></label>
									</div>
									<div class="col-md-10 col-sm-10 paddingL0">
										<strong>CCAvenue Payment Gateway</strong>
										<p><img src="${pageContext.request.contextPath}/resources/images/ccavenue.jpg"></p>
									</div>
								</div>
       						</div>
							
						</div>
                        <hr>
                        <div class="row">
       						<div class="col-md-12 col-sm-12 form-group">
									<label class="">Please note: You might be redirected to 3-D secure page to complete your transaction. By placing this order, you agree to the <a href="">Terms of Use</a> and <a href="">Privacy Policy</a> of Appeti.in</label>
       						</div>
							</div>
       					
       				</fieldset>	
       				<input type="hidden" name="oid" id="oid" value="${checkoutBean.order.orderId}">
					</form>
            </div>
            
            <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 side-margin">
       
                    <div class="seller-detail-box">
                     <h1 class="marginT20 main-heading-innerPages"> Order Summary</h1>
                    	<c:forEach items="${checkoutBean.order.sellerMap}" var="seller">
                    	<h3>${seller.value.name}</h3>
                        <ul class="seller-list">
                        	<c:forEach items="${seller.value.items}" var="item">
                        	<li>
                            	<div class="seller-detail-left">
                                	<h4>${item.ptitleName} X ${item.quantity}</h4>
                                    <p>${item.unit}</p>
                                </div>
                                <div class="prise-right">${item.totalPrice}</div>
                            </li>
                            </c:forEach>
                        </ul>
                        </c:forEach>
                        <ul class="calcColumn">
                        	<li>
                            	<div class="seller-detail-left">
                                	<h4>Subtotal</h4>
                                </div>
                                <div class="price-right">${checkoutBean.order.totalAmount}</div>
                            </li>
                            <li>
                            	<div class="seller-detail-left">
                                	<h4>Discount</h4>
                                </div>
                                <div class="price-right">${checkoutBean.order.discount}</div>
                            </li>
                            <li class="border">
                            	<div class="seller-detail-left">
                                	<h4>Delivery Charges</h4>
                                </div>
                                <c:choose>
						<c:when test="${checkoutBean.order.deliveryCharge == 0}">
						<div class="price-right"><span class="freeshipping">FREE SHIPPING</span></div>
						</c:when>
						<c:otherwise>
						<div class="price-right"><span class="price-right">${checkoutBean.order.deliveryCharge}</span></div>
						<p class="shippingCharges"> <span class="infoShipping"><i class="fa fa-info-circle"></i></span>FREE SHIPPING above <span class="a-color-price">Rs.300</span></p></c:otherwise>
						</c:choose>
                            </li>
                            <li>
                            	<div class="seller-detail-left">
                                	<h4><strong>Total</strong></h4>
                                </div>
                                <div class="price-right"><strong>${checkoutBean.order.amountPaid}</strong></div>
                            </li>
                         </ul>
                    </div>
                </div>
          </div>
          
          
        
        
        
        
        
    </div>
</div><!--end of content-->
<div class="form-bottom-strip"> 
		<div class="container  steps">
			
			<a href="#" onclick="$('#checkout-form').submit();" class="btn btn-primary marginT7 pull-left btn-lg">Confirm & Pay</a>
			
		</div>
	</div>
</c:otherwise>
</c:choose>	

<%@ include file="modalAddress.jsp"%>
