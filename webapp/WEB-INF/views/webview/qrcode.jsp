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
	</head>
	<script type="text/javascript">
		$(function(){
			$('body').css('height',$('body').width() + $('body').width() * 0.56);
		});			
	</script>
	<body class="bg_8f8">
	
		<div class="content" style="left: 0;right: 0;width: 100%;height: 100%;">			
			<img alt="qr" src="img/web/qr_icon.png" width="32.5%" style="position:absolute;top: 10.1%;left: 34%;"/>						
			<img alt="qr_txt_1" src="img/web/qr_txt_1.png" width="56.6%" style="position:absolute;top: 32.2%;left:21.6%;"/>			
			<img alt="qr_txt_2" src="img/web/qr_txt_2.png" width="44.3%" style="position:absolute;top: 38.3%;left:28%;"/>			
			<img alt="qrcode_code" src="img/web/qrcode_code.png" width="56.7%" style="position:absolute;top: 48%;left:21.5%;"/>
		</div>
	</body>
</html>
