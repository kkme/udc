<%@ page session="false" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@page import="org.springframework.util.StringUtils"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
	<head>
		<title>登录-口袋登录中心</title>
	 	<script type="text/javascript" src="/js/jquery.min.js"></script>
		<script type="text/javascript">
			var arrElmt = new Array();
			var arrFlag = new Array();
			var arrURL = new Array();
			
			<c:forEach var="url" varStatus="urlStatus" items="${logoutUrlList}">
				arrURL[${urlStatus.index}]='${url}'
			</c:forEach>
			
			function loadScript(v_url, v_idx){
			    arrElmt[v_idx] = document.createElement('SCRIPT');
			    arrElmt[v_idx].setAttribute('type', 'text/javascript');
			    arrElmt[v_idx].setAttribute('src', v_url+"?random="+ Math.random());
			    document.body.appendChild(arrElmt[v_idx]);
			}

			function loadImage(v_url, v_idx){
			    arrElmt[v_idx] = new Image();
			    arrElmt[v_idx].style.visibility = "hidden";
			    arrElmt[v_idx].src = v_url+"?random="+ Math.random();
			    document.body.appendChild(arrElmt[v_idx]);
			}
			
			var APP_NUM=arrURL.length;
			
			function myLogout(){
		        for(var i=0; i<APP_NUM; i++){
		            try{
		                if (arrURL[i] != ""){
			                if (document.all) { // IE
			                	loadImage(arrURL[i], i);
			                } else {
			                	loadScript(arrURL[i], i);
			                }
		                }else{
		                    arrFlag[i] = true;
		                }
		            }catch(e){
		                arrFlag[i] = true;
		            }
		        }
		    }
		</script>
		<script type="text/javascript">
			window.onload=function(){
				window.location.href="${targetUrl}";
			}
		</script>
	</head>
	
	<body>
		<script type="text/javascript">
			myLogout();
		</script>
		<div style="margin:0 auto; width:250px;height:200px;padding-top:200px;">
			<span><span>正在退出，请耐心等待</span></span><br/>
			<img src="images/logout.gif" />
		</div>
	</body>
</html>
