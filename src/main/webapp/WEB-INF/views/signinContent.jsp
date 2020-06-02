<script type='text/javascript' src='${pageContext.request.contextPath}/resources/script_old/jquery.js?ver=1.11.2'></script>


<script>

jQuery(function($){
	$("#user-login-form1").submit(function (e) {
    e.preventDefault(); //prevent default form submit
	});
	$("#forgot_password_form1").submit(function (e) {
	    e.preventDefault(); //prevent default form submit
	});
	$("#user-register-form1").submit(function (e) {
	    e.preventDefault(); //prevent default form submit
		});
	$("#user-login-form1").submit(function (e) {	
	clearMessages();
	var ok = false;
	var sEmail = $('#l_email1').val();
	if (sEmail!="" && validateEmail(sEmail)) {
		if($("#pwd1").val()!=""){
			ok = true;
		}else{
			$("#login_err1").text("Please enter the password");
			$("#login_err1").css("color","red");
			$("#pwd1").focus();
			}
		}
	else {
		$("#login_err1").text("Please enter valid email address");
		$("#login_err1").css("color","red");
		$("#l_email1").focus();
	}
	if(ok==true){
		var postData = $("#user-login-form1").serializeArray();
	    var formURL = $("#user-login-form1").attr("action");
	    $.ajax(
	    {
	        url : formURL,
	        type: "POST",
	        data : postData,
	       	success:function(response) 
	        {
	       		var obj = JSON.parse(response);
	       		if(obj["status"]==true){
	       			var uri = $("#redirect_url1").val();
	            	if(uri == '')
	            		window.location.reload();
	            	else
	            		window.location = uri;
	            }else{
	            	$("#login_err1").text(obj["message"]);
	            	$("#login_err1").css("color", "red");
	            }
	        },
	        error: function(jqXHR, textStatus, errorThrown) 
	        {
	        	$("#login_err1").text(textStatus);
            	$("#login_err1").css("color", "red");
	        }
	    });
	     
	}
	e.preventDefault();
	});

$("#user-register-form1").submit(function (e) {	
	clearMessages();
	var ok = false;
	var sEmail = $('#r_email').val();
	if (sEmail!="" && validateEmail(sEmail)) {
		if($("#r_pwd1").val()!="" && $("#r_pwd1").val()==$("#r_c_pwd1").val()){
			ok = true;
		}else{
			$("#reg_err").text("Passwords do not match");
			$("#reg_err").css("color","red");
			$("#r_pwd1").focus();
			}
		}
	else {
		$("#reg_err").text("Please enter valid email address");
		$("#reg_err").css("color","red");
		$("#r_email").focus();
	}
	if(ok==true){
		var postData = $("#user-register-form1").serializeArray();
	    var formURL = $("#user-register-form1").attr("action");
	    $.ajax(
	    {
	        url : formURL,
	        type: "POST",
	        data : postData,
	       	success:function(response) 
	        {
	       		var obj = JSON.parse(response);
	       		alert(response);
			    if(obj["status"]==true){
	            	
	            	alert(obj["message"]);
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
	$("#login_err1").text("");
	$("#reg_err").text("");
	
}

function forgotPassword(){
	var postData = $("#forgot_password_form1").serializeArray();
    var formURL = $("#forgot_password_form1").attr("action");
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
<div class="content1">
	<div class="container">
    	<div class="breadcrumb-box">
            <ol class="breadcrumb">
              <li><a href="#">Home</a></li>
              <li class="active">Login/ Signup</li>
            </ol>
    	</div>
                
        <div class="loginSection noMargin">
        	<div class="row">
            	<div style="min-height: 231px;" class="col-md-5  orEqual">
                	<form class="login-form" method="post" action="${pageContext.request.contextPath}/loginService" id="user-login-form1">
                        <h2>Sign in for Returning User</h2>
                        <p>If you have an account with us, please login.</p>
                        <input type="text" class="form-control" placeholder="Email" name="email" id="l_email1">
                        <input type="password" class="form-control" placeholder="Password" id="pwd1" name="pwd1">
                        <i id="login_err1"></i>
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
                    	
                       	<form class="form-horizontal" method="post" action="${pageContext.request.contextPath}/registerService" id="user-register-form1">
                        	<h2 class="form-group">Create an Account with us!</h2>
                            
                       		<div class="form-group">
                            	<label for="email" class=" control-label">Email:</label>
                            	<input type="text" class="form-control" id="r_email" name="email" placeholder="Email">
							</div>
                            <div class="form-group">
                            	<label for="password" class=" control-label">Password:</label>
                            	<input type="password" class="form-control" id="r_pwd1" name="pwd1" placeholder="Enter your password">
							</div>
                            <div class="form-group">
                            	<label for="name" class=" control-label">Confirm Password:</label>
                            	<input type="password" class="form-control" id="r_c_pwd1" name="c_pwd1" placeholder="Renter your password">
							</div>
                        
                        	<i id="reg_err"></i>
                    		<div class="signup-box">
                        		<button class="btn btn-primary" type="submit" >Register</button>
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
				<form class="" id="forgot_password_form1" method="POST" action="${pageContext.request.contextPath}/lostPasswordService" >
					<div class="form-group marginT20">
						<label for="filedEmailAdd"> Email address</label>
						<input type="text" placeholder="Enter email" class="form-control" id="forgot_email_address" name="forgot_email_address">
					</div>
					<div class="clearfix">
						<button class="btn btn-primary" onclick="javascript:forgotPassword();">Password Retrieve</button>
						<a class="js-CancelBtn" href="javascript:void(0);"><strong>Cancel</strong></a>
					</div>
				</form>
			</div>
      </div>
      <input type="hidden" class="form-control" placeholder="Email" name="redirect_url1" id="redirect_url1" value="">
                      
          
        
        
        
        
        
    </div>
</div><!--end of content-->