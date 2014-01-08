<script type="text/javascript" src="/js/porthole.min.js"></script>
<script type="text/javascript">
	var windowProxy;
	window.onload=function(){ 
		windowProxy = new Porthole.WindowProxy("proxy.html"); 
		windowProxy.post({'color':'blue', name: window.location.href});
	}
</script>
error