<!-- Modal -->
<script type='text/javascript' src='${pageContext.request.contextPath}/resources/script_old/jquery.js?ver=1.11.2'></script>

<script type="text/javascript">
jQuery(function ($) {
	$("#add_address_form").submit(function (e) {
	   	 e.preventDefault(); //prevent default form submit
		});
});
</script>		
<div class="modal addAddresModals" id="addressNewModal" tabindex="-1" role="dialog" aria-labelledby="addressNewModalModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <a href="javascript:;" class="close" data-dismiss="modal" aria-label="Close"><img src="images/cross.png" alt=""></a>
      <div class="modal-body">
       	<div class="loginSection">
        	<div class="row">
            	<div class="col-md-12">
                	<form class="customFormClass information_container custom-inputs" id="add_address_form" method="post" action="">
                    <h1 class="main-heading">Add a New Address</h1>
					<fieldset>
					<legend><span class="pull-right mandatoryMsg"> <span class="asterisk-imp">&#10033; </span> Mandatory fields </span></legend>
                        <div class="row">
								<div class="col-md-4 col-sm-4 form-group">
									<label for="fisrtNameEE" class="">First Name <sup class="asterisk-imp">&#10033; </sup></label> 
									<input type="text" id="first_name" name="first_name" class="form-control">
								</div>
								<div class="col-md-4 col-sm-4">
								<label for="lastNameEE" class="">Last Name <sup class="asterisk-imp">&#10033; </sup></label> 
                                <input type="text" id="last_name" name="last_name" class="form-control"> 
								</div>
                                <div class="col-md-4 col-sm-4 form-group">
								<label for="fisrtName" class="">Company Name</label> 
								<input type="text" placeholder="Company Information" id="company" name="company" class="form-control">
							</div>
						</div>
						<div class="row">
							
							<div class="col-md-6 col-sm-6 form-group">
       							<label for="" class=""> Address Line 1 <sup class="asterisk-imp">&#10033; </sup></label> 
       							<input id="address_1" class="form-control" type="text" name="address_1" placeholder="Street Address"/>
       						</div>
                            <div class="col-md-6 col-sm-6 form-group">
       							<label for="" class=""> Address Line 2 </label> 
       							<input id="address_2" class="form-control" type="text" name="address_2" placeholder="Apartment, suite, unit etc. (optional)"/>
       						</div>
							
							
						</div>
						<div class="row">
                        <div class="col-md-4 col-sm-4 form-group">
       							<label for="" class="">Town / City  <sup class="asterisk-imp">&#10033; </sup></label> 
       							<input id="city" class="form-control" type="text" name="city" placeholder=" "/>
       						</div>
							<div class="col-md-4 col-sm-4 form-group">
								<label for="" class="">State </label> <sup class="asterisk-imp">&#10033; </sup>
								<input id="state" class="form-control" type="text" name="state" placeholder="State "/>
							</div>
							<div class="col-md-4 col-sm-4 form-group">
       							<label for="" class="">Postcode / Zip <sup class="asterisk-imp">&#10033; </sup></label> 
       							<input id="postcode" class="form-control" type="text" name="postcode" placeholder=""/>
       						</div>
						</div>
       					<hr />
						<div class="row">
       						<div class="col-md-4 col-sm-4 form-group">
       							<label class="">Email <sup class="asterisk-imp">&#10033; </sup></label> 
       							<input class="form-control" id="add_email" type="email" name="email" placeholder=""/>
       						</div>
       						<div class="col-md-4 col-sm-4 form-group">
       							<label for="" class=""> Phone Number <sup class="asterisk-imp">&#10033; </sup></label> 
       							<input id="phone" class="form-control" type="text" name="phone" placeholder="Mobile (+91)"/>
       						</div>
							<div class="col-md-4 col-sm-4 form-group">
       							<label for="" class=""> Alternate Number</label> 
       							<input id="" class="form-control" type="text" name="alt_phone" placeholder="Optional"/>
       						</div>
                            
       					
						</div>
						<div class="row">
       						<div class="col-md-12 col-sm-12 form-group">
									<label class="customCheckBox"><input type="checkbox" class="marginR0" name="mark_address_as_default"/> Mark As Default</label>
       						</div>
							</div>
							<i id="add_err"></i>
       					<hr />
                        <p><button class="btn btn-primary" type="button" onclick="javascript:addAdress();">Deliver to this Address</button></p>
       				</fieldset>	
       				</form>
                </div>
            </div>
        </div>
        
        
      </div>
      
     
    </div>
  </div>
</div>

