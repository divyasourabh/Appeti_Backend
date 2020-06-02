$(document).ready(function() {
      $("#clients-logo").owlCarousel({
        autoPlay: 3000,
        items : 6,
        itemsDesktop : [1199,6],
        itemsDesktopSmall : [979,4]
      });
	  
	  $('.delivery-pickup span').on('click', function(){
		  $('.delivery-pickup span').addClass('inactive');
		  $(this).removeClass('inactive');
		  
	  });
	  $('.delivery-pickup .delivery-select').on('click', function(){
		  $('.pickup-toggle-round').prop( "checked", false );
		   $(this).next().prop( "checked", false );
		  $('.delivery-pickup input.pickup-toggle-round + label::after').css('margin-left','5px');;
		  $('.pickup-toggle-round').val( "Delivery");
	  });
	  $('.delivery-pickup .pickup-select').on('click', function(){
		  $('.pickup-toggle-round').prop( "checked", true );
		  $('.pickup-toggle-round').val( "Pickup");
	  });
	  
	  $('.forgot-password').on('click', function(){
		  $('.loginSection').hide();;
		  $('.forget-password-box').show();
		  
	  });
	  $('.js-CancelBtn').on('click',function(){
		  $('.loginSection').show();;
		  $('.forget-password-box').hide();
	  });
	  
});

 $(function() {
	 // Binding elements to document
	 //$("body").on('DOMNodeInserted', function(){
		$('input[type=radio]').customRadio();
		$('input[type=checkbox]').customCheckBox();
	 });
//Custom Radio
$.fn.customRadio = function() {
    return this.each(function(i,e) {
		var radioName = $(e).attr('name');
		
		$(document).on('change click', 'input[type=radio]', function(){
			var radioNameLocal = $(e).attr('name');
			$('input[type=radio][name='+radioNameLocal+']').each(function(){
				if($(this).is(':checked')){
					$(this).parent('label').addClass('active');
				}else{
					$(this).parent('label').removeClass('active');
				}
			});
		});
		$('input[type=radio][name='+radioName+']').each(function(){
			if($(this).is(':checked')){
				$(this).parent('label').addClass('active');
			}else{
				$(this).parent('label').removeClass('active');
			}
		});
	});
};

//Custom CheckBox
$.fn.customCheckBox = function() {
	
    return this.each(function(i,e) {
		var elm = $(e);
		$(document).on('change click', 'input[type=checkbox]', function(){			
			if($(this).is(':checked')){
				$(this).parent('label').addClass('active');
			}else{
				$(this).parent('label').removeClass('active');
			}
		});
		if($(this).is(':checked')){
			$(this).parent('label').addClass('active');
		}else{
			$(this).parent('label').removeClass('active');
		}
	});
};
    $('.add-to-cart').on('click', function () {
		var cart = $('.shopping-cart');
		jQuery("html, body").animate({
				scrollTop: 0
			}, 600);
			setTimeout(function () {
                cart.effect("shake", {
                    times: 2
                }, 400);
            }, 900);
			return false;
	});

var Index = function () {
    return {
        //Parallax Slider
        initParallaxSlider: function () {
			$(function() {
				$('#da-slider').cslider();
			});
        },
    };
}();

jQuery(document).ready(function () {
	
		jQuery(window).scroll(function () {
			if (jQuery(this).scrollTop() > 100) {
				jQuery('.scrollup').fadeIn();
			} else {
				jQuery('.scrollup').fadeOut();
			}
		});
	
		jQuery('.scrollup').click(function () {
			jQuery("html, body").animate({
				scrollTop: 0
			}, 600);
			return false;
		});
		
		jQuery(window).scroll(function () {
			if (jQuery(this).scrollTop() < 200) {
				//var scrollheight = $('.whiteBox').innerHeight();
				jQuery('.scrollDown').fadeIn();
				jQuery('.scrollDownBott').fadeOut();
			} else {
				//var scrollheight = $('.whiteBox').innerHeight();
				jQuery('.scrollDownBott').fadeIn();
				jQuery('.scrollDown').fadeOut();
			}
		});
	});	