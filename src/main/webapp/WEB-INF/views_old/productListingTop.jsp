<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript">
jQuery(function ($) {
$("#orderBy").change(function() {
	$("#applySort").submit();
	});
});
</script>
<div class="woocommerce-message x-alert x-alert-info x-alert-block" id="alert-div" style="display:none;"></div>

<p class="woocommerce-result-count">
	Showing ${fn:length(searchBean.results)} results</p>
	
	<form id="applySort" class="woocommerce-ordering" method="get" action=
	'${pageContext.request.contextPath}${requestScope['javax.servlet.forward.servlet_path']}'>
	<input type="hidden" id="query" name="query" value="${searchBean.query}">
	<select name="orderBy" id="orderBy" class="orderby">
					<option value="1" <c:if test="${searchBean.orderBy ==1 }">selected</c:if>>Sort by popularity</option>
					<option value="2" <c:if test="${searchBean.orderBy ==2 }">selected</c:if>>Sort by average rating</option>
					<option value="3" <c:if test="${searchBean.orderBy ==3 }">selected</c:if>>Sort by newness</option>
					<option value="4" <c:if test="${searchBean.orderBy ==4 }">selected</c:if>>Sort by price: low to high</option>
					<option value="5" <c:if test="${searchBean.orderBy ==5 }">selected</c:if>>Sort by price: high to low</option>
			</select>
	</form>