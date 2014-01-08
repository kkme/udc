<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page language="java"  session="false" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
	<head>
		<link href="css/main.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
	   <div class="wrapper">
		<div class="header" id="header">
	        <div class="brand">
	            <h1>口袋购物</h1>
	            <p>美图购物精选</p>
	            <p>www.koudai.com</p>
	        </div>
	    </div>
		<div class="main">
	        <table id="J-wrapperTable">
	            <tr>
	                <td>
	                    <!-- 这里输出内容 -->
	                    <p class="error">登陆异常！</p>
	                    <a href=""><img src="images/btn_relogin.png" alt="重新登录" /></a>
	                </td>
	            </tr>
	        </table>
	    </div>
	    <script type="text/javascript">
	        function setMainHeight(){
	            var oMain = document.getElementById('J-wrapperTable');
	            if ( oMain !== null && typeof oMain.style !== 'undefined') {
	                oMain.style.height = document.documentElement.clientHeight - 180 + 'px';
	            }
	        }
	        setMainHeight();
	        window.onresize = function(){
	            setMainHeight();
	        }
	    </script>
<%@include file="../custom/bottom.jsp"%>
