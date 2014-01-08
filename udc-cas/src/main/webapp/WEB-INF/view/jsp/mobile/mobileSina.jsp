<script language="javascript">
	window.onload=function(){
		var currentUrl = window.location.href;
		var changeUrl = currentUrl.replace("#","?").replace("sina_weibo","sina_weibo_v2");
		window.location.replace(changeUrl);
	}
</script>