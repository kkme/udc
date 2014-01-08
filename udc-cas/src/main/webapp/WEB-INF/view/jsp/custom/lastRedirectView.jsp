<%@ page session="false" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<script language="JavaScript">
<!--
var serviceUrl = "${param.service}";
if(serviceUrl.indexOf("?") >= 0 || serviceUrl.indexOf("%3F") >= 0){
	serviceUrl = serviceUrl + "&ticket=" + "${ticketId}";
}else{
	serviceUrl = serviceUrl + "?ticket=" + "${ticketId}";
}
window.location.replace(serviceUrl);
//-->
</script>
