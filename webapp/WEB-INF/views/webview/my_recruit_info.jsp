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
		<link rel="stylesheet" type="text/css" href="css/characterlist.css"/>
		<script src="js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
		<!-- <script src="js/characterlist.js" type="text/javascript" charset="utf-8"></script> -->
		<title>招募详情</title>
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
		<script type="text/javascript">
			var basePath = "<%=basePath%>"; 
			function chooseRole(roleId){
				location.href = basePath + 'rest/view/recruitRoleInfo.do?roleId='+roleId;
			}		
		</script>		
	</head>
	<body class="gray">
		<div class="p_title">
			<span class="close"></span>
			<h1 class="title_new">戏的事，戏里说！</h1>
			<a href="qrcode.do">立即下载</a>
		</div>
		<div class="c_person_info">
			<div class="c_photo">
				<img src="${recruitInfo.memberInfo.pictureInfo.urlPath }"/>
			</div>
			<div class="c_name_info">
				<div class="c_name">${recruitInfo.memberInfo.nickname }
				<c:if test="${recruitInfo.memberInfo.vip == '1' }">
				<span class="c_icon">V</span>
				</c:if>
				</div>
				<div class="c_time" >关注：${follows }</div>
				<div class="c_time" >身份认证：导演</div>
			</div>
		</div>
		<!-- -->
		<div class="add">
			<h1 class="c_title">${recruitInfo.title }</h1>
			<span class="green" style="background-color: ${recruitInfo.category.color};">广告</span>
			<span class="blue">上海</span>
			<span class="white">${recruitInfo.viewNum }浏览</span>
		</div>
		<!-- 剧目信息 -->
		<h2 class="c_title1">
			剧目信息
		</h2>
		<div class="info_list">
			<ul>
				<li>导演：${recruitInfo.director }</li>
				<li>编剧：${recruitInfo.screenwriter }</li>
			</ul>
		</div>
		<!-- 拍摄周期及试戏截止时间 -->
			<h2 class="c_title1">
			拍摄周期及试戏截止时间
		</h2>
		<div class="info_list">
			<ul>
				<li>拍摄周期：${recruitInfo.startDate }-${recruitInfo.endDate }</li>
				<li>试戏截止时间：${recruitInfo.deadline }<span class="deadline"><i></i>还有${remainTime }天</span></li>
			</ul>
		</div>
		<!--剧本大纲-->
		<div class="script c_1">
			<h2 class="c_title01 down">剧本大纲</h2>
			<div class="script_detail">
			${recruitInfo.scriptOutline }
		</div>
		</div>
		<!--剧本大纲-->
		<div class="script c_2">
			<h2 class="c_title02 down">备注说明</h2>
			<div class="script_detail">
			${recruitInfo.remark }
		</div>
		</div>
		<!--角色招募-->
		<div class="actor">
			<h2 class="c_title03">角色招募</h2>
			<c:forEach items="${resultList }" var="role">
			<div class="actorlist" onclick="chooseRole('${role.id }')">
				<div class="actor_pic">
					<img src="${role.urlPath }"/>
				</div>
				<div class="actor_name_box">
					<span class="actor_name 
					<c:if test="${role.sex == '1' }">
					man
					</c:if>
					<c:if test="${role.sex == '2' }">
					woman
					</c:if>
				">${role.name }</span>
				</div>
				<div class="actor_detail">
				   <div class="wage">${role.paid }</div>
					<div class="actor_item">
						<c:forEach items="${role.nameList }" var="names">
							<span>${names }</span>
						</c:forEach>
					</div>
					</div>
			</div>
			</c:forEach>
		</div>
	</body>
</html>
