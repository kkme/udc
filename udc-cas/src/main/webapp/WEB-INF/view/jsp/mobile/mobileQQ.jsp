<script language="javascript">
	window.onload=function(){
		var currentUrl = window.location.href;
		var changeUrl;
		if(currentUrl.indexOf("?")==-1){
			changeUrl = currentUrl.replace("#","?").replace("tencent_qq","tencent_qq_v2");
		}else{
			changeUrl = currentUrl.replace("#","").replace("tencent_qq","tencent_qq_v2");
		}
		window.location.replace(changeUrl);
	}
</script>