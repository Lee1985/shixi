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
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="format-detection" content="telephone=no"/>
		<meta name="format-detection" content="email=no"/>
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<link rel="stylesheet" type="text/css" href="css/common.css"/>
		<link rel="stylesheet" type="text/css" href="css/profile.css"/>
		<link rel="stylesheet" type="text/css" href="css/character.css"/>
		<script src="js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
		<!-- <script src="js/character.js" type="text/javascript" charset="utf-8"></script> -->
		<title>角色介绍</title>
	</head>
	<style type="text/css">
	    .p_title{overflow: hidden; background: #000;}	
		.p_title span,.p_title a{display: inline-block;float: left;}
		.close{
			width: 2.5rem;height:2.5rem;
	       	background: url(img/close.png) no-repeat left center;
	       	background-size: 2.5rem 2.5rem;
	       	margin-top: 1.2rem;
	       	margin-left: 0.5rem;
	    }
		.title_new{
			font-size: 1.6rem;
	        color: #FFFFFF;
	        background: url(img/sslogo.png) no-repeat left center;
	        background-size: 4rem 4rem;
	        height: 4rem;
	        line-height: 4rem;
	        margin-top: 0.5rem;
	        margin-left: 0.5rem;
	        padding-left: 4.5rem;
	        float: left;
	    }
		.p_title a{
		   float: right;
           width: 10rem;
           height: 5rem;
           line-height: 5rem;
           background: #b29362;
           color: #FFFFFF;
           text-align: center;
           font-size: 1.8rem;
	    }
	</style>
	<body class="gray">
		<div class="p_title">
			<span class="close"></span>
			<h1 class="title_new">戏的事，戏里说！</h1>
			<a href="qrcode.do">立即下载</a>
		</div>
		<div class="character">
				<div class="c_photo">
					<img src="${roleInfo.pictureInfo.urlPath }"/>
				</div>
				<div class="character_detail">
					<span class="character_name 
					<c:if test="${roleInfo.sex == '1' }">
					men
					</c:if>
					<c:if test="${roleInfo.sex == '2' }">
					women
					</c:if>
					">
					${roleInfo.name }
				   </span>
				<div class="characteritic">
					<c:forEach items="${nameList }" var="names">
						<span>${names }</span>
					</c:forEach>
				</div>
				</div>
				<div class="character_words">
					<span>导演要求：</span>
					${roleInfo.requirement }
				</div>
		</div>
		<div class="actors_line">
			<span>试镜台词</span>
			<div class="lines">
				${roleInfo.dialogue }
			</div>
		</div>
	</body>
</html>
