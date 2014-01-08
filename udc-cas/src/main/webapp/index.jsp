<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page language="java"  session="false" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="org.apache.commons.lang.StringUtils"%>

<html>
	<head>
		<link href="css/main.css" rel="stylesheet" type="text/css" />
		<meta property="qc:admins" content="22736147641663754116375" />
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
		    <%	
			Cookie[] cookies = request.getCookies();
			String user = null;
			if (cookies != null) {
				for (int i = 0; i < cookies.length; i++) {
					Cookie c = cookies[i];
					if (c.getName().equals("show_nick")) {
						user = c.getValue();
						break;
					}
				}
			}
			if ( StringUtils.isNotBlank(user)) {
			%>
			<tr>
	            <td>
	            	您好，<%=URLDecoder.decode(user, "utf-8")%>!</br>
	            	您已经登录成功了！</br>
	            	在您使用网站服务之后，请注销并关闭浏览器，这样可以让您的帐户更安全。</br>
	            </td>
		        </tr>
			<%
				} else {
			%>
			<tr>
                <td>
                    <!-- 这里输出内容 -->
                   <div class="login" id="login">
                        <a href="./loginTaoBao.html"><img src="../images/btn_taobao.gif" id="ddd" alt="淘宝登录" class="btn_taobao"/></a>
                        <a href="./loginSina.html"><img src="../images/btn_sina.gif" alt="新浪微博登录" class="btn_sina"/></a>
                        <a href="./loginQQ.html"><img src="../images/btn_qq.gif" alt="qq登录" class="btn_qq"/></a>
                    </div>
                </td>
            </tr>
			<%
				}
			%>
		    </table>
		    <div class="links">
	            	直接进入：
	            <a class="link" id="gouwu" href="http://www.koudai.com/">口袋购物</a>
	            <a class="link" id="bijia" href="http://bijia.koudai.com/">口袋比价</a>
	        </div>
		    
		</div>
		<script type="text/javascript">
        var oMain = document.getElementById('J-wrapperTable');
        function setMainHeight(){
            if ( oMain !== null && typeof oMain.style !== 'undefined') {
                oMain.style.height = document.documentElement.clientHeight - 218 + 'px';
                document.getElementById("login").style.overflow="hidden";
            }
        }
        setMainHeight();
        window.onresize = function(){
            setMainHeight();
        }

    </script>
<%@include file="WEB-INF/view/jsp/custom/bottom.jsp"%>
		

