<%@ page session="false" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<script language="JavaScript">
<!--
var service = "${param.service}";
service = service.replace(/j_acegi_cas_security_check|j_acegi_security_check/,"");
window.location.replace("${tgtExpiredlogoutUrl}" + service);
//-->
</script>
