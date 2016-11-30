<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1" />
<title>招募详情</title>

<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<link rel="stylesheet" href="css/common.css" type="text/css"></link>
<link rel="stylesheet" href="css/layout.css" type="text/css"></link>
</head>

<body>
	<div class="w tc f20 mt10">${data.title}</div>
	<div class="w mt30 h30">
		<div class="fl pl5">${data.marineSysUnit.unitname}</div>
		<div class="fr pr5 f12 color">
			${data.issueBegtime}
		</div>
	</div>
	<c:if test="${!empty data.marinePictureInfoList && data.marinePictureInfoList.size()>0}">
	<!--焦点图开始-->
	<div class="swiper-container overflow">
		<div class="swiper-wrapper" id="swiper-wrapper">
			<c:forEach var="marinePictureInfo"
				items="${data.marinePictureInfoList}">
				<div class="swiper-slide">
					<a href="#"><img src="${marinePictureInfo.path}" class="wb98">
					</a>
				</div>
			</c:forEach>
		</div>
		<div class="pagination"></div>
	</div>
	</c:if>
	<div class="p10 mb30">${data.articleContent}</div>
	<c:if
		test="${!empty data.attAliasurl || !empty data.videoattAliasurl }">
		<div class="p5 download">
			<div class="pt5">
				<c:if test="${!empty data.attAliasurl}">
					<a href="dowload?fileName=${data.attAliasurl }"><img
						src="images/sys_02.gif" /> </a>
				</c:if>
				<c:if test="${!empty data.videoattAliasurl}">
					<a href="${data.videoattAliasurl }"><img
						src="images/sys_01.gif" class="ml15" /> </a>
				</c:if>
			</div>
		</div>
	</c:if>
</body>
</html>
