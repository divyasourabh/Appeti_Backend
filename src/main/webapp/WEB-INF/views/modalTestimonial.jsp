<script type="text/javascript">

jQuery(function($){
	$("#add-testimonial-form").submit(function (e) {
    e.preventDefault(); //prevent default form submit
	});
	$("#add-testimonial-form").submit(function (e) {	
	$('#add_err').text("");
	var ok = false;
	var name = $('#user_name').val();
	if (name!="") {
		if($("#desc").val()!=""){
			ok = true;
		}else{
			$("#add_err").text("Please write the content");
			$("#add_err").css("color","red");
			$("#desc").focus();
			}
		}
	else {
		$("#add_err").text("Please mention your name");
		$("#add_err").css("color","red");
		$("#user_name").focus();
	}
	if(ok==true){
		var postData = $("#add-testimonial-form").serializeArray();
	    var formURL = $("#add-testimonial-form").attr("action");
	    $.ajax(
	    {
	        url : formURL,
	        type: "POST",
	        data : postData,
	       	success:function(response) 
	        {
	       		var obj = JSON.parse(response);
	       		if(obj["status"]==true){
	       			jQuery("#alert-type").removeClass("alert-warning");
					jQuery("#alert-type").addClass("alert-success");
					jQuery("#alert-div").attr('style','display:block');
					jQuery("#alert_msg").html('<strong>' + name + '</strong>, ' + obj["message"]);
					jQuery("#modal-close").trigger('click');
	            }else{
	            	$("#add_err").text(obj["message"]);
	            	$("#add_err").css("color", "red");
	            }
	        },
	        error: function(jqXHR, textStatus, errorThrown) 
	        {
	        	$("#add_err").text(textStatus);
            	$("#add_err").css("color", "red");
	        }
	    });
	     
	}
	e.preventDefault();
	});
});
</script>

<div class="modal commentModals" id="myCommentModal" tabindex="-1" role="dialog" aria-labelledby="myCommentModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <a href="javascript:;" class="close" data-dismiss="modal" id="modal-close"aria-label="Close"><img src="${pageContext.request.contextPath}/resources/images/cross.png" alt=""></a>
      <div class="modal-body">
       	<div class="loginSection">
        	<div class="row">
            	
                <div class="col-md-12">
                
                	<form class="customFormClass information_container custom-inputs" method="post" action="${pageContext.request.contextPath}/add-testimonial" id="add-testimonial-form">
                    <h1 class="main-heading">Write a Testimonial</h1>
					<fieldset>
					<legend><span class="pull-right mandatoryMsg"> <span class="asterisk-imp">&#10033; </span> Mandatory fields </span></legend>
						<div class="row">
								<div class="col-md-12 col-sm-12 form-group">
									<label for="NameEE" class="">Name <sup class="asterisk-imp">&#10033; </sup></label> 
									<input type="text" placeholder="Enter your name" id="user_name" name="user_name" class="form-control">
								</div>
						</div>
						<div class="row">
							<div class="col-md-12 col-sm-12 form-group">
								<label class="">Title </label> 
       							<input class="form-control" type="text" id="title" name="title" placeholder="Title of your comment (optional)"/>
							</div>
						</div>
                        <div class="row">
							<div class="col-md-12 col-sm-12 form-group">
								<label class="">Testimonial Content <sup class="asterisk-imp">&#10033; </sup></label> 
       							<textarea class="form-control" id="desc" name="desc"></textarea>
							</div>
						</div>
						<i id="add_err"></i>
                    	
       					<hr />
                        <p><button class="btn btn-primary" type="submit">Send Message</button></p>
       				</fieldset>	
       				</form>
                </div>
            </div>
        </div>
        
        
      </div>
      
     
    </div>
  </div>
</div>
