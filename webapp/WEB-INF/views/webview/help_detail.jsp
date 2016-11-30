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
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="format-detection" content="telephone=no"/>
		<meta name="format-detection" content="email=no"/>
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<link rel="stylesheet" type="text/css" href="css/app/common.css"/>
		<link rel="stylesheet" type="text/css" href="css/app/style.css"/>
		<title></title>
	</head>
	<body>
		<div class="q_pic">
			<img src="images/app/commonquestion.png"/>
		</div>
		<c:forEach var="question" items="${list }">
			<div class="q_block">
			<h2 class="q_title"><span>Q:</span>${question.question }</h2>
			<div class="q_detail">
				${question.answer }
			</div>
		</div>
		</c:forEach>
	</body>
</html>
