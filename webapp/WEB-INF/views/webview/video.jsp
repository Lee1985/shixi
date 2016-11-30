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
		
		<link rel="stylesheet" type="text/css" href="css/video.css"/>

		<script src="js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>

		<script src="js/function.js" type="text/javascript" charset="utf-8"></script>
		<style type="text/css">
		.p_title {
			overflow: hidden;
			background: #000;
		}
		
		.p_title span,.p_title a {
			display: inline-block;
			float: left;
		}
		
		.close {
			width: 2.5rem;
			height: 2.5rem;
			background: url(img/close.png) no-repeat left center;
			background-size: 2.5rem 2.5rem;
			margin-top: 1.2rem;
			margin-left: 0.5rem;
		}
		
		.title_new {
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
		
		.p_title a {
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
<title></title>		
	</head>	
	<body class="gray">
		<div class="p_title">
			<span class="close"></span>
			<h1 class="title_new">戏的事，戏里说！</h1>
			<a href="qrcode.do">立即下载</a>
		</div>				
		<div class="video_block">
			<h2>${activityInfo.title }</h2>

			<div class="introduce">
				${activityInfo.content }
			</div>
		</div>

		<div class="person_info b1">
			<div class="photo">
				<img src="${memberInfo.pictureInfo.urlPath }"/>
			</div>

			<div class="name_info">
				<div class="name">${memberInfo.nickname }
				<c:if test="${memberInfo.realNameStatus == '1'}">
				<span class="icon">V</span>
				</c:if>
				</div>

				<div class="time tele" >${activityInfo.createDate }  来自 <span>${videoInfo.phoneType }</span></div>
			</div>
		</div>

		<div class="video_box">
			<video controls="controls" width="100%" height="200" src="${videoInfo.fileInfo.urlPath }"></video>
		    <div class="sharebox">
			<a href="javascript:void(0);"><i class="a1"></i><span class="words">${videoInfo.likeNum }</span></a>
			<a href="javascript:void(0);"><i class="a2"></i><span class="words">${videoInfo.viewNum }</span></a>
			<a href="javascript:void(0);"><i class="a3"></i><span class="words">${videoInfo.commentNum }</span></a>
			<a href="javascript:void(0);"><i class="a4"></i><span class="words">${videoInfo.shareNum }</span></a>
         </div>
		</div>


    <!--评论列表-->

   <ul class="comment">
   <c:forEach items="${commentPage }" var="comment">
	<li>
		<div class="person_info comment_block">
			<div class="photo">
				<img src="${comment.urlPath }" />
			</div>
			<div class="name_info b2">
				<div class="name">${comment.nickname }
				<c:if test="${comment.realNameStatus == '1'}">
				<span class="icon">V</span>
				</c:if>
				</div>
				<div class="time">${comment.createDate }</div>
			</div>
			<div class="comment_box">
				${comment.content }
			</div>
		</div>
	</li>
	</c:forEach>
</ul>

	</body>

</html>

