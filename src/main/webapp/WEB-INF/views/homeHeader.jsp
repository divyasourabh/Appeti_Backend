<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="headerInclude.jsp"%>

<script>
jQuery(function ($) {
	window.setCategorySelection = function(id,cval){

	$("#catsearch").attr("action", "${pageContext.request.contextPath}/shop/category/" + cval + "/" + id + "/search");
	}
});
</script>
<header class="header">
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
                    <li class="profile dropdown"><a href="#" class="dropdown-toggle"  data-toggle="dropdown"><i class="profile-icon"></i> Welcome Punasch! <span><b class="caret"></b> </span></a>
                    <ul class="dropdown-menu">
                        <li><a href="my-account.html">My Account</a></li>
                        <li><a href="#">Logout</a></li>
                      </ul>
                    </li>
                </ul>
            </div>
        </div>
    </div>-->
	
    <div class="container">
        <div class="navbar navbar-default">
            <div class="navbar-header">
              <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
              </button>
              <a class="navbar-brand" href="${pageContext.request.contextPath}"><img height= 70 src="${pageContext.request.contextPath}/resources/images/whitelogo.png" alt="Appet-i"></a>
            </div>
            <div class="navbar-collapse collapse">
              <ul class="nav navbar-nav navbar-right top-action-list">
                <li class="active"><a href="${pageContext.request.contextPath}/shop/">SHOP</a></li>
                <li><a href="${pageContext.request.contextPath}/latest-offers"><font color="#FF4040">OFFERS</font></a></li>
                <li><a href="${pageContext.request.contextPath}/blogs/">BLOGS</a></li>
                <li><a href="${pageContext.request.contextPath}/cart/"><div class="cart"></div></a></li>
                <% if(session != null && session.getAttribute("user") != null){ %>
                <li class="profile dropdown"><a href="#" class="dropdown-toggle"  data-toggle="dropdown"><i class="profile-icon"></i> Welcome User! <span><b class="caret"></b> </span></a>
                    <ul class="dropdown-menu">
                        <!-- <li><a href="#">My Account</a></li> -->
                        <li><a href="#" onclick="signOut();">Logout</a></li>
                      </ul>
                </li>
                <% } else {%>
                <li class="profile dropdown"><a href="#" class="dropdown-toggle"   data-toggle="modal" data-target=".modals"><i class="profile-icon-inner"></i> Login / Register <span></span></a>
                </li>
                
                <%} %>
              </ul>
            </div><!--/.nav-collapse -->
          </div>
          <br>
         <br>
                   <br>
         <br>
         <br>
         <br>
                   <br>
         <br>
        <h1><!-- Order food Online from 50+ cities in India! --></h1>  
                <h1><!-- Order food Online from 50+ cities in India! --></h1>  
        
        <div class="tab-box tab-box-homebg">

          <!-- Nav tabs -->
          <ul class="nav nav-tabs" role="tablist">
           	<c:set var="myclass" value="active"></c:set>
           	<c:forEach items="${headerBean.categoryTree.childCategories}"
											var="node"> 
			<li role="presentation" class="${myclass}"><a href="#" onclick="setCategorySelection(${node.categoryId},'${fn:replace(fn:toLowerCase(node.name),' ','-')}')" aria-controls="" role="tab" data-toggle="tab">${node.name}</a></li>
			<c:set var="myclass" value=""></c:set>
           	
            
            </c:forEach>
            </ul>
        
          <!-- Tab panes -->
          <div class="tab-content">
            <div role="tabpanel" class="tab-pane active" id="first">
            	<div class="address-search">
                	<form class="form" method="get" action="${pageContext.request.contextPath}/shop/search" id="catsearch">
                    	<!--<div class="delivery-pickup">
                        	<span class="delivery-select">Delivery</span>
							<input id="pickup-checkbox" class="pickup-toggle pickup-toggle-round" type="checkbox">
							<label id="label-pickup-checkbox" for="pickup-checkbox"></label>
							<span class="pickup-select inactive">Pickup</span>
                        </div>-->
                        <div class="form-box">
                    	<div  class="location-search-box">
                        	<input type="text" class="" value="" id="place" name="place" placeholder="Please type a Place, City, State">
                        </div>
                        <div class="item-search-box">
                        	<input type="text" class="" value="" id="item" name="item" placeholder="Search for a item">
                        </div>
                        <a href="" onclick="document.getElementById('catsearch').submit(); return false;" class="btn btn-primary" type="submit">Search</a>
                        
                        </div>
                    </form>
            	</div>
            </div>
            
          </div>
        
        </div><!--end of tab box-->
        
    </div>
</header>