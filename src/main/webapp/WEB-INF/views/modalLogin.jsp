<%@ include file="fbLogin.jsp"%>
<%@ include file="gLogin.jsp"%>
<script type='text/javascript' src='${pageContext.request.contextPath}/resources/script_old/jquery.js?ver=1.11.2'></script>

<script type="text/javascript">

function validateEmail(sEmail) {
	var filter = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	return filter.test(sEmail);
}

function setVal(uri){
	$("#redirect_url").val(uri);	
}

function soclogin(fname,lname,email,token){
	var formURL = "${pageContext.request.contextPath}/socialLogin";
	jQuery.ajax(
	    	{
		        url : formURL,
		        type: "POST",
		        data: {firstName:fname,last_name:lname,email:email,token:token},
		        success:function(response) 
		        {
		        	var obj = JSON.parse(response);
					if (obj["status"] == true) {
						var uri = $("#redirect_url").val();
		            	if(uri == '')
		            		window.location.reload();
		            	else
		            		window.location = uri;
					} else {
						
					}
		        }
		    });
}
jQuery(function($){
	$("#user-login-form").submit(function (e) {
    e.preventDefault(); //prevent default form submit
	});
	$("#forgot_password_form").submit(function (e) {
	    e.preventDefault(); //prevent default form submit
	});
	$("#user-register-form").submit(function (e) {
	    e.preventDefault(); //prevent default form submit
		});
	$("#user-login-form").submit(function (e) {	
	clearMessages();
	var ok = false;
	var sEmail = $('#l_email').val();
	if (sEmail!="" && validateEmail(sEmail)) {
		if($("#pwd").val()!=""){
			ok = true;
		}else{
			$("#login_err").text("Please enter the password");
			$("#login_err").css("color","red");
			$("#pwd").focus();
			}
		}
	else {
		$("#login_err").text("Please enter valid email address");
		$("#login_err").css("color","red");
		$("#l_email").focus();
	}
	if(ok==true){
		var postData = $("#user-login-form").serializeArray();
	    var formURL = $("#user-login-form").attr("action");
	    $.ajax(
	    {
	        url : formURL,
	        type: "POST",
	        data : postData,
	       	success:function(response) 
	        {
	       		var obj = JSON.parse(response);
	       		if(obj["status"]==true){
	       			var uri = $("#redirect_url").val();
	            	if(uri == '')
	            		window.location.reload();
	            	else
	            		window.location = uri;
	            }else{
	            	$("#login_err").text(obj["message"]);
	            	$("#login_err").css("color", "red");
	            }
	        },
	        error: function(jqXHR, textStatus, errorThrown) 
	        {
	        	$("#login_err").text(textStatus);
            	$("#login_err").css("color", "red");
	        }
	    });
	     
	}
	e.preventDefault();
	});

$("#user-register-form").submit(function (e) {	
	clearMessages();
	var ok = false;
	var sEmail = $('#r_email').val();
	if (sEmail!="" && validateEmail(sEmail)) {
		if($("#r_pwd").val()!="" && $("#r_pwd").val()==$("#r_c_pwd").val()){
			ok = true;
		}else{
			$("#reg_err").text("Passwords do not match");
			$("#reg_err").css("color","red");
			$("#r_pwd").focus();
			}
		}
	else {
		$("#reg_err").text("Please enter valid email address");
		$("#reg_err").css("color","red");
		$("#r_email").focus();
	}
	if(ok==true){
		var postData = $("#user-register-form").serializeArray();
	    var formURL = $("#user-register-form").attr("action");
	    $.ajax(
	    {
	        url : formURL,
	        type: "POST",
	        data : postData,
	       	success:function(response) 
	        {
	       		var obj = JSON.parse(response);
	       		if(obj["status"]==true){
	            	
	            	window.location.reload();  
	            	
	            }else{
	            	$("#reg_err").text(obj["message"]);
	            	$("#reg_err").css("color", "red");
	            }
	        },
	        error: function(jqXHR, textStatus, errorThrown) 
	        {
	        	$("#reg_err").text(textStatus);
            	$("#reg_err").css("color", "red");
	        }
	    });
	     
	}
	e.preventDefault();});
});

function clearMessages(){
	$("#login_err").text("");
	$("#reg_err").text("");
	
}

function forgotPassword(){
	var postData = $("#forgot_password_form").serializeArray();
    var formURL = $("#forgot_password_form").attr("action");
    $.ajax(
    {
        url : formURL,
        type: "POST",
        data : postData,
       	success:function(response) 
        {
       		var obj = JSON.parse(response);
       		$('#modal_close').trigger('click');
			alert(obj["message"]);
       		
        },
        error: function(jqXHR, textStatus, errorThrown) 
        {
        	alert("Some problem occured while processing your request.Please try again later.");
        }
    });
}


</script>


<div class="modal modals" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <a href="javascript:;" class="close" data-dismiss="modal" aria-label="Close" id="modal_close"><img src="${pageContext.request.contextPath}/resources/images/cross.png" alt=""></a>
      <div class="modal-body">
       	<div class="loginSection">
        	<div class="row">
            	<div style="min-height: 231px;" class="col-md-5  orEqual">
                	<form class="login-form" method="post" action="${pageContext.request.contextPath}/loginService" id="user-login-form">
                        <h2>Sign in for Returning User</h2>
                        <p>If you have an account with us, please login.</p>
                        <input type="text" class="form-control" placeholder="Email" name="email" id="l_email">
                        <input type="password" class="form-control" placeholder="Password" id="pwd" name="pwd">
                        <i id="login_err"></i>
                    	<div class="forgot">
                           <button class="btn btn-primary" type="submit" >Sign in</button>
                           
                            <a href="javascript:;" class="forgot-password pull-right">Forgot Password?</a>
                        </div>
                       
                        <div class="social-login">
                         	<p>Recover your social account</p>
                        	<a href="javascript:;" onclick="checkLoginState();" class="left-btn"><img src="${pageContext.request.contextPath}/resources/images/facenook-btn.jpg" alt="" class="img-responsive"></a>
                        </div>
                   </form>
                </div>
				<div class="col-md-2 orEqual">
						<div style="min-height: 231px;" class="orBG">
							 <span class="icon-or"> </span>
						</div>

                    
                </div>
                <div class="col-md-5">
                	<div class="registernow">
                    	
                       	<form class="form-horizontal" method="post" action="${pageContext.request.contextPath}/registerService" id="user-register-form">
                        	<h2 class="form-group">Create an Account with us!</h2>
                            
                       		<div class="form-group">
                            	<label for="email" class=" control-label">Email:</label>
                            	<input type="text" class="form-control" id="r_email" name="email" placeholder="Email">
							</div>
                            <div class="form-group">
                            	<label for="password" class=" control-label">Password:</label>
                            	<input type="password" class="form-control" id="r_pwd" name="pwd" placeholder="Enter your password">
							</div>
                            <div class="form-group">
                            	<label for="name" class=" control-label">Confirm Password:</label>
                            	<input type="password" class="form-control" id="r_c_pwd" name="c_pwd" placeholder="Renter your password">
							</div>
                        
                        	<i id="reg_err"></i>
                    		<div class="signup-box">
                        		<button class="btn btn-primary" type="submit">Register</button>
                       		</div>
                       </form>
                      
                    </div>
                </div>
            </div>
        </div>
        
        <div class="forget-password-box">
				<h1>Having trouble signing in?</h1>
				<p>To retrieve your Username/Password, enter the email address <br> 
				you entered while creating your account with Appet-i.</p> 
				<form class="" id="forgot_password_form" method="POST" action="${pageContext.request.contextPath}/lostPasswordService" >
					<div class="form-group marginT20">
						<label for="filedEmailAdd"> Email address</label>
						<input type="text" placeholder="Enter email" class="form-control" id="forgot_email_address" name="forgot_email_address">
					</div>
					<div class="clearfix">
						<button class="btn btn-primary" onclick="javascript:forgotPassword();">Password Retrieve</button>
						<a class="js-CancelBtn" href="#"><strong>Cancel</strong></a>
					</div>
				</form>
			</div>
      </div>
      <input type="hidden" class="form-control" placeholder="Email" name="redirect_url" id="redirect_url" value="">
                       
     
    </div>
  </div>
</div>
