<%@include file="../includes/top-ui.jsp"%>

<div class="info">
	<p>
		<spring:message code="screen.confirmation.message" arguments="${fn:escapeXml(param.service)}${fn:indexOf(param.service, '?') eq -1 ? '?' : '&'}ticket=${serviceTicketId}" />
	</p>
</div>

<%@include file="../includes/bottom-ui.jsp"%>
