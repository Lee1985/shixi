<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
	<head>
		<base href="<%=basePath%>">
		<meta charset="utf-8" />
		<meta name="format-detection" content="telephone=no"/>
		<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
        <link href="css/fcapp_style.css" rel="stylesheet" type="text/css" />
        <script src="js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
        <style type="text/css">
        	img,video {
        		vertical-align:top;
        	}
        </style>
        <script type="text/javascript">
        	$(function(){
        		$('video').attr("webkit-playsinline","true");
        	});
        </script>
	</head>
	<body class="bg_7f7">		
		<div class="content" style="left: 0;right: 0;width: 100%;">
			${internal.content }			
		</div>
	</body>
</html>
