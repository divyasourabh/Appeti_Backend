<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript">
jQuery(function ($) {
	
	var unit = "${productBean.product.unitString}";
	setSellers(unit);
	var ppu = "${productBean.product.ppu}";
	setPPU(ppu);
	var tag = "${productBean.product.tagId}";
	setTag(tag);
	var inStock = "${productBean.product.inStock}";
	var avl = "${productBean.product.availability}";
	setAvl(inStock,avl);
	
	$("#weight").change(function() {
		var val = $("#weight").val();
		var custom = val.split("-");
		setPPU(custom[2]);
		setSellers(custom[1]);
		setTag(custom[0]);
		setAvl(custom[3],custom[4]);
	});
	
	function setPPU(val){
		$("#ppu").text("Rs. " + val);
	}
	
	function setTag(value){
		$("#tagId").val(value);
	}
	
	function setAvl(val1,val2){
		$("#avl").text(val2);
		if(val1=='false')
			$("#a2cmain").hide();
		else
			$("#a2cmain").show();
	}
	
	window.addToCart = function(tagId,quantity) {
		var formURL = "${pageContext.request.contextPath}/cart/add";
		$.ajax({
			url : formURL,
			type : "POST",
			data : {tagId:tagId,quantity:quantity},
			success : function(response) {
				var obj = JSON.parse(response);
				if (obj["status"] == true) {
					$("#alert-div").removeClass("x-alert-danger");
					$("#alert-div").addClass("x-alert-info");
					$("#alert-div").attr('style','display:block');
					$("#alert-div").html('<a class="button wc-forward" href="${pageContext.request.contextPath}/cart">View Cart</a>' + obj["message"] + " : ${productBean.product.ptitleName}");
					$("#top-button").trigger('click');
				} else {
					$("#alert-div").removeClass("x-alert-info");
					$("#alert-div").addClass("x-alert-danger");
					$("#alert-div").attr('style','display:block');
					$("#alert-div").html(obj["message"]);
					$("#top-button").trigger('click');
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				$("#alert-div").removeClass("x-alert-info");
				$("#alert-div").addClass("x-alert-danger");
				$("#alert-div").attr('style','display:block');
				$("#alert-div").html(obj["message"]);
				$("#top-button").trigger('click');
			}
		});
	}
	
	function setSellers(val){
		var map = ${productBean.product.otherSellers};
	    var custom = map[val];
	    
	    var arrayLength = custom.length;
		var sellerMap = ${productBean.product.sellerDescriptionMap };
		
		$('#other-sellers > tbody').children().remove();
		for (var i = 0; i < arrayLength; i++) {
			var tObj = custom[i];
			var sObj = sellerMap[JSON.stringify(tObj['sellerId'])];
			var name = sObj['brandName'];
			var location = sObj["location"];
			if(i==0){
				$("#seller_name").html("Sold By: " + name);
			}
			if(typeof location == 'undefined')
				location = '';
			else
				location = ' (' + location + ') '
			var rating = sObj["rating"];
			var ratingStr = ""+rating+"/5";
			if(typeof rating == 'undefined')
				ratingStr = "New Seller";
			var price = tObj["pricePerUnit"];
			var tagId = tObj["tagId"];
			var avl = tObj["inStock"];
			var row = '<tr class="alt"> <td> ' + name + location + '</td>  <td> <p>' + ratingStr + ' </p></td>  <td><span class="price"><span class="amount">Rs. ' 
			+ price + '</span></span></td> <td><span><p class="stars" style="margin:0;"><span><a class="star-1" href="#">1</a><a class="star-2"href="#">2</a><a class="star-3" href="#">3</a><a class="star-4" href="#">4</a><a class="star-5" href="#">5</a></span></p><select name="rating" id="rating" style="display: none;"><option value="">Rate…</option><option value="5">Perfect</option><option value="4">Good</option><option value="3">Average</option><option value="2">Not that bad</option><option value="1">Very Poor</option></select></p></span></td><td align="right"><button type="submit"'
			+ 'class="single_add_to_cart_button button alt" style="float:right" onclick="addToCart('+tagId+',1);">Add to'
			+ ' cart</button></td> </tr>';
			if(avl!=false)
				$('#other-sellers > tbody:last-child').append(row);
		}
	}

});


</script>

<div class="woocommerce-tabs">
					<ul class="tabs x-nav x-nav-tabs one ">

							<li class="additional_information_tab x-nav-tabs-item active" id="other-sellers">
								<a href="#tab-other-sellers">Sellers</a>
							</li>
					</ul>
<div class="x-tab-content">
<div class="panel x-tab-pane" id="tab-other-sellers"
								style="display: block;">

								
								<table class="shop_attributes" id="other-sellers">

									<thead>
										<tr class="shop_attributes_thead">
											<th>Seller</th>
											<th>Rating</th>
											<th>Price</th>
											<th>Rate Seller</th>
											<th></th>
										</tr>
									</thead>
									
									<tbody>
										
										
										
										
									</tbody>
								</table>

</div>
</div>
</div>