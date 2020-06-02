<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type='text/javascript' src='${pageContext.request.contextPath}/resources/script_old/jquery.js?ver=1.11.2'></script>

<script>
jQuery(function($){
	$("#add-subscriber-form").submit(function (e) {
    e.preventDefault(); //prevent default form submit
	});
	$("#add-subscriber-form").submit(function (e) {	
	$('#sub_err').text("");
	var ok = false;
	var name = $('#sub_email').val();
	if (name!="" && validateEmail(name)) {
			ok = true;
	}
	else {
		$("#sub_err").text("Please enter a valid emailId");
		$("#sub_err").css("color","red");
		$("#sub_email").focus();
	}
	if(ok==true){
		var postData = $("#add-subscriber-form").serializeArray();
	    var formURL = $("#add-subscriber-form").attr("action");
	    $.ajax(
	    {
	        url : formURL,
	        type: "POST",
	        data : postData,
	       	success:function(response) 
	        {
	       		var obj = JSON.parse(response);
	       		if(obj["status"]==true){
	       			$("#sub_err").text(obj["message"]);
	            	$("#sub_err").css("color", "green");
	            }else{
	            	$("#sub_err").text(obj["message"]);
	            	$("#sub_err").css("color", "red");
	            }
	        },
	        error: function(jqXHR, textStatus, errorThrown) 
	        {
	        	$("#sub_err").text(textStatus);
            	$("#sub_err").css("color", "red");
	        }
	    });
	     
	}
	e.preventDefault();
	});
});
</script>

<footer class="footer">
	<div class="container">
		<div class="row">
        	<div class="col-lg-2 col-md-2 col-sm-2 col-xs-6">
            	<h4>delicacies</h4>
                <ul class="footer-list">
                	<c:forEach items="${headerBean.nodeTree.childNodes}"
											var="node">
					<c:forEach items="${node.childNodes}" var="childNode">
					<li><a href="${pageContext.request.contextPath}/shop/node/${childNode.nodeId}/${fn:replace(fn:toLowerCase(childNode.nodeName),' ','-')}">
					${childNode.nodeName}</a></li>
                    </c:forEach>
				</c:forEach>
					
                </ul>
            </div>
            
            <div class="col-lg-2 col-md-2 col-sm-2 col-xs-6">
            	<h4>flavours</h4>
                <ul class="footer-list">
                	<c:forEach items="${headerBean.categoryTree.childCategories}"
											var="node">
					<li><a href="${pageContext.request.contextPath}/shop/category/${node.categoryId}/${fn:replace(fn:toLowerCase(node.name),' ','-')}">
					${node.name}</a></li>
                    </c:forEach>
                </ul>
            </div>
            
            <div class="col-lg-2 col-md-2 col-sm-2 col-xs-6">
            	<h4>Appeti</h4>
                <ul class="footer-list">
                	<li><a href="${pageContext.request.contextPath}/about">About Us</a></li>
                    <%-- <li><a href="${pageContext.request.contextPath}/appet-i-team">Team</a></li> --%>
                    <!-- <li><a href="${pageContext.request.contextPath}/we-are-hiring">Jobs</a></li> -->
                    <li><a href="${pageContext.request.contextPath}/contact-us">Contact Us</a></li>
                </ul>
            </div>
            
            <div class="col-lg-2 col-md-2 col-sm-2 col-xs-6">
            	<h4>Terms & policies</h4>
                <ul class="footer-list">
                	<li><a href="${pageContext.request.contextPath}/terms-of-service">Terms of Service</a></li>
                    <li><a href="${pageContext.request.contextPath}/privacy-policy">Privacy Policy</a></li>
                    <li><a href="${pageContext.request.contextPath}/delivery-shipping/">Delivery & Shipping</a></li>
                    <li><a href="${pageContext.request.contextPath}/refunds-cancellations/">Cancellations & Refunds</a></li>
                </ul>
            </div>
            
            <div class="col-lg-2 col-md-2 col-sm-2 col-xs-6">
            	<h4>support</h4>
                <p>For help, send email to
				<a href="mailto:contact@appeti.in">contact@appeti.in</a></p>
                <p>Call us on <a href="#">022-26500935/8</a></p>
                 <form class="form clearfix subscriptionBox" method="post" action="${pageContext.request.contextPath}/add-subscriber" id="add-subscriber-form">
                    <h4>Subscribe Newsletter</h4>
                        <div class="left-form-box">
                            <input type="text" class="form-control" id="sub_email" name="sub_email" placeholder="Your email addres">
                        </div>
                        <i id="sub_err"></i>
                        <div class="right-form-box">
                            <button class="btn btn-primary" type="submit">Subscribe</button>
                        </div>
            	</form>
            </div>
            
            <div class="col-lg-2 col-md-2 col-sm-2 col-xs-6">
            	<h4>Download App</h4>
               
                <p class="font11">Download the Appeti app & discover foods around you! <a href="https://play.google.com/store/apps/details?id=ds.com.appeti" target="_blank"><img src="${pageContext.request.contextPath}/resources/images/android-app-on-google-play-256.png" alt="Play Store"></a></p>
            </div> 
            
        </div><hr>
        <div class="row">
          <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 text-center">
       	    <h4>join us on</h4>
                <p class="social">
                    <a title="Facebook" data-placement="top" data-toggle="tooltip" class="tooltips" target="_blank" href="https://www.facebook.com/appetidelicacies"><i class="fa fa-facebook-square"></i></a>
                    <a title="GooglePlus" data-placement="top" data-toggle="tooltip" class="tooltips" href="#"><i class="fa fa-google-plus-square"></i></a>
                    <a title="Twitter" data-placement="top" data-toggle="tooltip" class="tooltips" href="#"><i class="fa fa-twitter-square"></i></a>
                    <a title="LinkedIn" data-placement="top" data-toggle="tooltip" class="tooltips" href="#"><i class="fa fa-linkedin-square"></i></a>
                    <a title="Skype" data-placement="top" data-toggle="tooltip" class="tooltips" href="#"><i class="fa fa-skype"></i></a>
                    <a title="Pinterest" data-placement="top" data-toggle="tooltip" class="tooltips" href="#"><i class="fa fa-pinterest-square"></i></a>
                </p>
                
          </div> 
            
      </div>
    </div>
</footer>
<div class="bottom-footer">
	<div class="container">
		<p class="footer-left">All trademarks are properties of their respective owners.</p>
    	<p class="footer-right">&copy; 2015 Appeti. All rights reserved.</p>
    </div>   
</div>
<a href="#" class="scrollup"><i class="icon-angle-up"></i></a>
<%@ include file="modalLogin.jsp"%>
