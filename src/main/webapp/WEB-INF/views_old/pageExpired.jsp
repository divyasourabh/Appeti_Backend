<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page session="true"%>

<!DOCTYPE html>
<!--[if IE 9]><html class="no-js ie9" lang="en-US"><![endif]-->
<!--[if gt IE 9]><!-->
<html class="no-js" lang="en-US">
<!--<![endif]--><head>
<title>Page-Expired</title>
</head>
<body
	class="error404 logged-in x-renew x-navbar-fixed-top-active x-full-width-layout-active x-full-width-active x-post-meta-disabled x-portfolio-meta-disabled wpb-js-composer js-comp-ver-4.3.5 vc_responsive x-v3_2_1 x-shortcodes-v3_0_2">


	<div id="top" class="site">
		<%@ include file="header.jsp"%>
		<header class="x-header-landmark">
      <div class="x-container max width">
        <div class="x-landmark-breadcrumbs-wrap">
          <div class="x-landmark">

          
            <h1 class="h-landmark"><span>Page Expired</span></h1>

          
          </div>

                                    <div class="x-breadcrumbs-wrap">
                <div class="x-breadcrumbs"><a href="http://www.appeti.in"><span class="home"><i class="x-icon-home"></i></span></a> <span class="delimiter"><i class="x-icon-angle-right"></i></span> <span class="current">Page-Expired</span></div>              </div>
                      
          
        </div>
      </div>
    </header>
    <div class="x-container max width offset">
    <div class="x-main full" role="main">
      <div class="entry-404">

        
<p class="center-text">Sorry, the page you are looking for has expired.</p>

<div class="x-container max width">
							<a class="x-btn x-btn-real x-btn-pill x-btn-x-large"
								href="${pageContext.request.contextPath}/shop" title="Start Shopping"
								data-options="thumbnail: ''"><i
								class="x-icon x-icon-play"></i>Continue Shopping</a>
						</div>
      </div>
    </div>
  </div>
		
		<%@ include file="footer.jsp"%>
	</div>
<%@ include file="postLoadScripts.jsp"%>
</body>
</html>
