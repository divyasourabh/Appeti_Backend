    
    
    <header class="header height-auto">
	<!--<div class="top-header">
    	<div class="container">
        	<div class="left-top-box">
				<ul class="list-contact clearfix">
                	<li><a href="#"><i class="phone"></i> +91 - 9820297657</a></li>
                    <li><a href="mailto:contact@appeti.in"><i class="mail"></i> contact@appeti.in</a></li>
                </ul>            	
            </div>
            
            <div class="right-top-box">
            	<ul class="top-action-list clearfix">
                	<li><a href="#"><img src="${pageContext.request.contextPath}/resources/images/help-icon.png" alt="FAQ" class="faq"></a></li>
                	<li><a href="cart.html"><div class="cart"><span>3</span></div></a></li>
                    <li class="profile dropdown"><a href="#" class="dropdown-toggle"   data-toggle="modal" data-target=".modals"><i class="profile-icon"></i> Login / Register <span></span></a>
                    </li>
                </ul>
            </div>
        </div>
    </div>-->
	
    <div class="container inner">
        <div class="navbar navbar-default">
            <div class="navbar-header">
              <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
              </button>
              <a class="navbar-brand" href="${pageContext.request.contextPath}/"><img height=70 src="${pageContext.request.contextPath}/resources/images/redlogo.png" alt="Appet-i"></a>
            </div>
            <div class="navbar-collapse collapse">
              <ul class="nav navbar-nav navbar-right top-action-list">
                <li class="active"><a href="${pageContext.request.contextPath}/shop/">SHOP</a></li>
                <li><a href="${pageContext.request.contextPath}/latest-offers">OFFERS</a></li>
                <li><a href="${pageContext.request.contextPath}/blogs/">BLOGS</a></li>
                <li><a href="${pageContext.request.contextPath}/cart"> <div class="cartinner"></div></a></li>
                <% if(session != null && session.getAttribute("user") != null){ %>
                <li class="profile dropdown"><a href="#" class="dropdown-toggle"  data-toggle="dropdown"><i class="profile-icon"></i> Welcome User! <span><b class="caret"></b> </span></a>
                    <ul class="dropdown-menu">
                        <!-- <li><a href="#">My Account</a></li> -->
                        <li><a href="#" onclick="signOut();">Logout</a></li>
                      </ul>
                </li>
                <% } else {%>
                <li class="profile dropdown"><a href="#" class="dropdown-toggle"  id="login-window" data-toggle="modal" data-target=".modals"><i class="profile-icon-inner"></i> Login / Register <span></span></a>
                </li>
                
                <%} %>
              </ul>
            </div><!--/.nav-collapse -->
          </div>
      
        
    </div>
    
    <div class="search-box">
	<div class="container">
    	<div class="address-search">
                	<form class="form clearfix" id="header-search-form" method="get" action="${pageContext.request.contextPath}/shop/search">
                        <div class="form-box">
                    	<div  class="location-search-box">
                        	<input type="text" class="" name="place" placeholder="Please type a Place, City, State">
                        </div>
                        <div class="item-search-box">
                        	<input type="text" class="" name="item" placeholder="Search for a item">
                        </div>
                        <a href="#" onclick="$('#header-search-form').submit();" class="btn btn-primary" type="submit">Search</a>
                        
                        </div>
                    </form>
            	</div>
    </div>
</div>
</header>
