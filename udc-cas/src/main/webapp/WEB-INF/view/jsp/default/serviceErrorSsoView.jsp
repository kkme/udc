<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@include file="../includes/top-ui.jsp"%>

<c:url var="url" value="/login">
    <c:param name="service" value="${param.service}" />
    <c:param name="renew" value="true" />
</c:url>
<div id="status" class="errors">
	<h2><spring:message code="screen.service.sso.error.header" /></h2>
	<p><spring:message code="screen.service.sso.error.message"  arguments="${url}" /></p>
</div>

<%@include file="../includes/bottom-ui.jsp"%>
