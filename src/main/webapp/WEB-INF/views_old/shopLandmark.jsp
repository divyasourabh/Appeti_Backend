<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type='text/javascript' src='${pageContext.request.contextPath}/resources/script/typeahead.js'></script>

<script type="text/javascript">
var products = new Bloodhound({
	  datumTokenizer: Bloodhound.tokenizers.obj.whitespace('value'),
	  queryTokenizer: Bloodhound.tokenizers.whitespace,
	  prefetch: '../data/films/post_1960.json',
	  remote: {
	    url: '../data/films/queries/%QUERY.json',
	    wildcard: '%QUERY'
	  }
	});

	$('#remote .typeahead').typeahead(null, {
	  name: 'best-pictures',
	  display: 'value',
	  source: products
	});
</script>


<header class="x-header-landmark">
	<div class="x-container max width">
		<div class="x-landmark-breadcrumbs-wrap">
			<div class="x-landmark">


				<div class="h-landmark ">
					 <form method="get" id="searchform" class="form-search"
						action="${pageContext.request.contextPath}/shop/search">
						
						<div id="remote" class="searchbar">
  							

						<label for="s" class="visually-hidden">Search for best
							regional delicacies of India</label> 
							<span role="status"
							aria-live="polite"
							class="isp_polite_powered_by_id ui-helper-hidden-accessible"></span><input
							type="text" id="query" class="typeahead search-query ui-autocomplete-input"
							name="query" value="${searchBean.query}"
							placeholder="Search for best regional delicacies of India"
							style="width: 80%;"> <a href=""
							class="button view" onclick="document.getElementById('searchform').submit(); return false;">Search</a>
							</div> 
					</form>
				</div>


			</div>

			<div class="x-breadcrumbs-wrap">
				<div class="x-breadcrumbs">
					<a href=""><span class="home"><i
							class="x-icon-home"></i></span></a> 
					<span class="delimiter"><i
						class="x-icon-angle-right"></i></span> 
					<a href="${pageContext.request.contextPath}/shop">The Shop</a>
					<c:forEach items="${searchBean.nodeMap}" var="node">
					<span class="delimiter"><i
						class="x-icon-angle-right"></i></span> 
					<a href="${pageContext.request.contextPath}/shop/node/${node.key}/${fn:replace(fn:toLowerCase(node.value),' ','-')}">${node.value}</a>
					</c:forEach>
				</div>
			</div>


		</div>
	</div>
</header>