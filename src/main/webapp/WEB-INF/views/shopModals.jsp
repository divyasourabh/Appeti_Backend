<script>


</script>

<!--Modle Quick View-->
<div class="modal model-quick-view" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog second-model" role="document">
    <div class="modal-content">
      <a href="javascript:;" class="close" id="quick-view-close" data-dismiss="modal" aria-label="Close"><img src="${pageContext.request.contextPath}/resources/images/cross.png" alt=""></a>
      <div class="modal-body">
       	<section class="quick-view-box">
        	<div class="row">
                <div class="col-lg-7 col-md-7 col-sm-7 col-xs-12">
                	<ul class="bxslider" id="images1"></ul>
                    
                    <div id="bx-pager" >
                      <div id="images2"></div>
                    </div>
                    <div class="marginT150">Share: <p class="socialQuickView">
                    <a title="Facebook" data-placement="top" data-toggle="tooltip" class="tooltips" href="#"><i class="fa fa-facebook-square"></i></a>
                    <a title="GooglePlus" data-placement="top" data-toggle="tooltip" class="tooltips" href="#"><i class="fa fa-google-plus-square"></i></a>
                    <a title="Twitter" data-placement="top" data-toggle="tooltip" class="tooltips" href="#"><i class="fa fa-twitter-square"></i></a>
                    <a title="LinkedIn" data-placement="top" data-toggle="tooltip" class="tooltips" href="#"><i class="fa fa-linkedin-square"></i></a>
                    <a title="Skype" data-placement="top" data-toggle="tooltip" class="tooltips" href="#"><i class="fa fa-skype"></i></a>
                    <a title="Pinterest" data-placement="top" data-toggle="tooltip" class="tooltips" href="#"><i class="fa fa-pinterest-square"></i></a>
                </p></div>
                </div>
                
                <div class="col-lg-5 col-md-5 col-sm-5 col-xs-12">
                    <div class="product-header">
                        <h3 id="product_name"></h3>
                        <p id="seller_name"><span>Sold by:</span></p>
                    </div>  
                    
                    <br><p id="description"></p><br>
                    
                    <div class="product-price-box clearfix">
                        <div class="weight-price">
                            <label for="weight">Weight</label>
                            <div class="price-box" id="weight">
                               
                            </div>
                        </div>
                        <div class="quantity">
                            <label class="quantity">Qty</label>
                            <input type="text" name="quantity" id="mquantity" value="1">
                        </div>
                        <a id="add_to_cart" href="#" class="add-to-cart" >ADD  TO CART</a>
            	            
                    </div>
                    <div class="popupmoreInfo"><p class="see-detail"><a href="#" id="link" class="btn btn-primary">SEE PRODUCT DETAILS</a></p></div>
                    
                    <div class="tags-box">
                        <p >Tags</p>
                        <div id="tags">
                        <span class="label label-primary"></span>
                    	</div>
                        <p class="marginT20">Categories</p>
                        <div id="categories"><span class="label label-primary"></span>
                    </div>
                      
                </div>
            
          	</div>  
        </section>
        
        
      </div>
      
     
    </div>
  </div>
</div>
