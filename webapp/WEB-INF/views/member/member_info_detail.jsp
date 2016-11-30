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
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1" />
<title>${title}</title>

<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<script type="text/javascript" src="js/system/easy.js"></script>
<script type="text/javascript" src="js/system/base.js"></script>
<script src="js/jquery.fs.boxer.js" type="text/javascript" charset="utf-8"></script>
<link rel="stylesheet" type="text/css" href="css/jquery.fs.boxer.css"/>
<style type="text/css">
table tr th{
font-size: 11pt;
border: 1px solid black;
background-color: #E8E8E8;
width: 200px;
line-height: 30px;
font-family: inherit;
font-weight: normal;
text-align: center;
}
table tr td{
font-size: 11pt;
border: 1px solid black;
width: 200px;
text-align: center;
}
.ftitle{
width: 788px;
background-color: #4F94CD;
line-height: 30px;
}
	#boxer{
	z-index:10001 !important;
	}
	#boxer-overlay{
		z-index:10000 !important;
	}
</style>
</head>

<body>
<div class="easyui-tabs" style="width:100%;height:400px">
	<div title="基本信息"  style="padding:10px">
			<form id="fm" name="fm" method="post" action="" data-options="novalidate:true">
				<table cellpadding="0" cellspacing="0">
					<tr>
						<th>头像</th>
						<td><a rel="gallery" class="boxer" href="${entity.pictureInfo.urlPath }">
						<img id="headImg" alt=""
							src="${entity.pictureInfo.urlPath }"
							style="width: 100px;height: 100px"></img></td>
							<script>
									$(function(){
										$('.boxer').boxer();
									});
						</script>
					</tr>
					<tr>
						<th>真实姓名</th>
						<td>${entity.realName }</td>
					</tr>
					<tr>
						<th>昵称</th>
						<td>${entity.nickname }</td>
					</tr>
					<tr>
						<th>性别</th>
						<td><c:if test="${entity.sex=='1' }">男</c:if>
						<c:if test="${entity.sex=='2' }">女</c:if></td>
					</tr>
					<tr>
						<th>手机</th>
						<td>${entity.mobile }</td>
					</tr>
					<tr>
						<th>邮箱</th>
						<td>${entity.email }</td>
					</tr>
					<tr>
						<th>身份认证</th>
						<td>${entity.identityInfo }</td>
					</tr>
					<tr>
						<th>实名认证</th>
						<td><c:if test="${entity.realNameStatus==1 }">已认证</c:if>
						<c:if test="${entity.realNameStatus!=1 }">未认证</c:if></td>
					</tr>
					<tr>
						<th>学历认证</th>
						<td><c:if test="${entity.educationStatus==1 }">已认证</c:if>
						<c:if test="${entity.educationStatus!=1 }">未认证</c:if></td>
					</tr>
					<tr>
						<th>黑名单</th>
						<td><c:if test="${entity.status==0 }">是</c:if>
						<c:if test="${entity.status==1 }">否</c:if></td>
					</tr>
				</table>
			</form>
		</div>
		</div>
</body>
</html>
