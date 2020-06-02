<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="true"%>

<div class="content1">
	<div class="container">
    	<div class="breadcrumb-box">
            <ol class="breadcrumb">
              <li><a href="${pageContext.request.contextPath}">Home</a></li>
              <li><a href="${pageContext.request.contextPath}/cart">View Cart</a></li>
              <li class="active">Payment Status</li>
            </ol>
    	</div>
        
        <c:choose>
				<c:when test="${checkoutBean.result.status == true }">
				<c:set var="order" value="${checkoutBean.orderView}"/>
				
				<div class="row">
        	<div class="col-lg-8 col-sm-8 col-md-8 col-xs-12">
<section class="container share-package marginT50 marginB130">
	<div class=" marginT20  text-center">
	
			<p class="congr-text"><img src="${pageContext.request.contextPath}/resources/images/successIcon.png" width="34" height="33"> Congratulations!</p>
			<p class="congr-text-sub marginB30">You have successfully placed your order at Appet-i.</p>
			
			<p>Your order number is #${order.orderId} <br />
			Placed On <fmt:formatDate type="date" value="${order.date}" /></p><hr>
		
            <p><strong>Shipping Information:</strong> ${order.shippingAddr.firstName} ${order.shippingAddr.lastName}<br>
${order.shippingAddr.addr1}, ${order.shippingAddr.addr2}<br>
							${order.shippingAddr.city} - ${order.shippingAddr.zipCode}<br>
							${order.shippingAddr.state}<br>
<br>Mobile: ${order.billingAddr.phoneNumber}</p>

			<a class="btn btn-primary marginT50 marginB20" href="${pageContext.request.contextPath}/shop">Continue Shopping</a>

		
	</div>
</section>                
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
                                <div class="price-right"><c:choose>
						<c:when test="${checkoutBean.order.deliveryCharge == 0}">
						<span class="freeshipping">FREE SHIPPING</span>
						</c:when>
						<c:otherwise>
						${checkoutBean.order.deliveryCharge}
						</c:otherwise>
						</c:choose></div>
                            </li>
                           	<c:if test="${order.trackingId != 0}">
                           	<li> 
                           		<div class="seller-detail-left">
                                	<h4>Transaction ID#</h4>
                                </div>
                               <div class="price-right">
								${order.trackingId}
							</div>
						    </li>
                        	</c:if>
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
          </c:when>
          <c:when test="${checkoutBean.result.status == false }">
          
          <div class="row">
        	<div class="col-lg-8 col-sm-8 col-md-8 col-xs-12">
<section class="container share-package marginT50 marginB130">
	<div class=" marginT20  text-center">
	
			<p class="congr-text"><font color="red">Payment Failed</font></p>
			<p class="congr-text-sub marginB30">Failure message: ${checkoutBean.result.message}</p>
			
			<a class="btn btn-primary marginT50 marginB20" href="${pageContext.request.contextPath}/checkout">Try again</a>

		
	</div>
</section>                
            </div>
          </div>
          
          </c:when>
          </c:choose>
        
        
        
        
        
    </div>
</div><!--end of content-->