<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script>
jQuery(function($){
	$("#add-subscriber1-form").submit(function (e) {
    e.preventDefault(); //prevent default form submit
	});
	$("#add-subscriber1-form").submit(function (e) {	
	$('#sub1_err').text("");
	var ok = false;
	var name = $('#sub1_email').val();
	if (name!="" && validateEmail(name)) {
			ok = true;
	}
	else {
		$("#sub1_err").text("Please enter a valid email id");
		$("#sub1_err").css("color","red");
		$("#sub1_email").focus();
	}
	if(ok==true){
		var postData = $("#add-subscriber1-form").serializeArray();
	    var formURL = $("#add-subscriber1-form").attr("action");
	    $.ajax(
	    {
	        url : formURL,
	        type: "POST",
	        data : postData,
	       	success:function(response) 
	        {
	       		var obj = JSON.parse(response);
	       		if(obj["status"]==true){
	       			$("#sub1_err").text(obj["message"]);
	            	$("#sub1_err").css("color", "green");
	            }else{
	            	$("#sub1_err").text(obj["message"]);
	            	$("#sub1_err").css("color", "red");
	            }
	        },
	        error: function(jqXHR, textStatus, errorThrown) 
	        {
	        	$("#sub1_err").text(textStatus);
            	$("#sub1_err").css("color", "red");
	        }
	    });
	     
	}
	e.preventDefault();
	});
});
</script>


<div class="col-lg-4 col-sm-4 col-md-4 col-xs-12">
            	<div class="newsletter-detail-box margin-bottom-40">
                	<h2>Sign Up for Newsletter</h2>
                    <p>Sign up for our newsletter, and we'll send you news and tutorials on web designs, coding. You will also received free gifts vouchers.</p>
                    <form class="information_container custom-inputs" method="post" action="${pageContext.request.contextPath}/add-subscriber" id="add-subscriber1-form">
					<fieldset>
					
						<div class="row">
								<div class="col-md-12 col-sm-12 form-group">
									<input type="email" class="form-control" id="sub1_email" name="sub1_email" placeholder="Email Addres">
								<i id="sub1_err"></i>
                        </div>
						</div>
						<div class="row">
       						<div class="col-md-12 col-sm-12 form-group">
       							<button class="btn btn-primary" type="submit">Subscribe</button></div>
                        </div>
       				</fieldset>	
       				</form>
                </div>
                
                
            </div>