<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="security" uri="http://www.bluemobi.com"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sh" uri="/shFunction"%>
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

<title>用户简历详情</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
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
		width: 250px;
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
				<input type="hidden" id="id" name="id">
				<table cellpadding="0" cellspacing="0">
					<tr>
						<th>头像</th>
						<td><a id="headImgboxer" rel="gallery" class="boxer" href="${entity.memberInfo.pictureInfo.urlPath }">
						<img id="headImg" alt=""
							src="${entity.memberInfo.pictureInfo.urlPath }"
							style="width: 100px;height: 100px"></a></td>
						<script>
									$(function(){
										$('.boxer').boxer();
									});
						</script>
						<th>认证信息</th>
						<td>
						<p>身份认证:${entity.memberInfo.identityInfo }</p>
						<p>实名认证:<c:if test="${entity.memberInfo.realNameStatus==1 }">已认证</c:if><c:if test="${entity.memberInfo.realNameStatus!=1 }">未认证</c:if></p>
						<p>学历认证:<c:if test="${entity.memberInfo.educationStatus==1 }">已认证</c:if><c:if test="${entity.memberInfo.educationStatus!=1 }">未认证</c:if></p>
						</td>
					</tr>
					<tr>
						<th>姓名</th>
						<td>${sh:decode(entity.realName)}</td>
						<th>性别</th>
						<td>
							<c:if test="${entity.sex eq 1 }">男</c:if> 
							<c:if test="${entity.sex eq 2 }">女</c:if>
						</td>
					</tr>
					<tr>
						<th>身高</th>
						<td>${entity.height }cm</td>
						<th>体重</th>
						<td>${entity.weight }kg</td>
					</tr>
					<tr>
						<th>生日</th>
						<td>${entity.birthdayStr }</td>
						<th>经常出现的城市</th>
						<td>${entity.cityName }</td>
					</tr>
					<tr>
						<th>角色标签</th>
						<td>${sh:decode(entity.roleLabel)}</td>
						<th>技能标签</th>
						<td>${sh:decode(entity.skillLabel)}</td>
					</tr>
				</table>
			</form>
		</div>
		<div id="pic" title="见组照" style="padding:10px">
			<div>
				<form id="fmmeeting" name="fmmeeting" method="post" action="" data-options="novalidate:true">
				<table cellpadding="0" cellspacing="0">
					<tr>
						<th>正面</th>
						<th>左侧</th>
						<th>左45度</th>
					</tr>
					<tr>
						<td><a rel="gallery0" class="boxer0" href="${entity.pictureInfo1.urlPath }"><img id="headImg" alt=""
							src="${entity.pictureInfo1.urlPath }"
							style="width: 120px;height: auto"></a></td>
						<td><a rel="gallery0" class="boxer0" href="${entity.pictureInfo2.urlPath }"><img id="headImg" alt=""
							src="${entity.pictureInfo2.urlPath }"
							style="width: 120px;height: auto"></a></td>
						<td><a rel="gallery0" class="boxer0" href="${entity.pictureInfo3.urlPath }"><img id="headImg" alt=""
							src="${entity.pictureInfo3.urlPath }"
							style="width: 120px;height: auto"></a></td>
					</tr>
					<tr>
						<th>右侧</th>
						<th>右45度</th>
						<th>全身</th>
					</tr>
					<tr>
						<td><a rel="gallery0" class="boxer0" href="${entity.pictureInfo4.urlPath }"><img id="headImg" alt=""
							src="${entity.pictureInfo4.urlPath }"
							style="width: 120px;height: auto"></a></td>
						<td><a rel="gallery0" class="boxer0" href="${entity.pictureInfo5.urlPath }"><img id="headImg" alt=""
							src="${entity.pictureInfo5.urlPath }"
							style="width: 120px;height: auto"></a></td>
						<td><a rel="gallery0" class="boxer0" href="${entity.pictureInfo6.urlPath }"><img id="headImg" alt=""
							src="${entity.pictureInfo6.urlPath }"
							style="width: 120px;height: auto"></a></td>
					</tr>
					<script>
						$(function(){
							$('.boxer0').boxer({
								margin:10
							});
						});
			</script>
					</table>
				</form>
			</div>
		</div>
		<div id="video" title="自我介绍视频" style="padding:10px">
			<div >
				<form id="fmscore" name="fmscore" method="post" action="" data-options="novalidate:true">
				<table cellpadding="0" cellspacing="0">
					<tr>
						<th>自我介绍
						<a href="system/fileDownload.do?url=${entity.fileInfo.urlPath }" class="easyui-linkbutton" id="filesave"
						iconCls="icon-save"
						style="width:90px">下载</a></th>
					</tr>
					<tr>
						<td><video style="width: 580px;height: 326px;" src="${entity.fileInfo.urlPath }" controls="controls"></video></td>
					</tr>
					</table>
				</form>
			</div>
		</div>
		<div id="crew" title="剧照/生活照" style="padding:10px">
			<div >
				<form id="fmmessage" name="fmmessage" method="post" action="" data-options="novalidate:true">
				<c:forEach items="${photosList }" var="photo" varStatus="index">
					<table cellpadding="0" cellspacing="0">
						<tr>
							<th colspan="3">${sh:decode(photo.title)}</th>
						</tr>
						<tr>
							<td>
							<c:if test="${photo.pictureInfoAll != null }">
							<a rel="gallery${index.index+1 }" class="boxer${index.index+1 }" href="${photo.pictureInfoAll.urlPath }">
							<img id="headImg" alt="" src="${photo.pictureInfoAll.urlPath }"
							style="width: 120px;height: auto"></a>
							</c:if>
							</td>
							<td>
							<c:if test="${photo.pictureInfo1 != null }">
							<a rel="gallery${index.index+1 }" class="boxer${index.index+1 }" href="${photo.pictureInfo1.urlPath }">
							<img id="headImg" alt="" src="${photo.pictureInfo1.urlPath }"
							style="width: 120px;height: auto"></a>
							</c:if>
							</td>
							<td>
							<c:if test="${photo.pictureInfo2 != null }">
							<a rel="gallery${index.index+1 }" class="boxer${index.index+1 }" href="${photo.pictureInfo2.urlPath }">
							<img id="headImg" alt="" src="${photo.pictureInfo2.urlPath }"
							style="width: 120px;height: auto"></a>
							</c:if>
							</td>
						</tr>
						<c:if test="${photo.pictureInfo3 != null }">
						<tr>
							<td>
							<c:if test="${photo.pictureInfo3 != null }">
							<a rel="gallery${index.index+1 }" class="boxer${index.index+1 }" href="${photo.pictureInfo3.urlPath }">
							<img id="headImg" alt="" src="${photo.pictureInfo3.urlPath }"
							style="width: 120px;height: auto"></a>
							</c:if>
							</td>
							<td>
							<c:if test="${photo.pictureInfo4 != null }">
							<a rel="gallery${index.index+1 }" class="boxer${index.index+1 }" href="${photo.pictureInfo4.urlPath }">
							<img id="headImg" alt="" src="${photo.pictureInfo4.urlPath }"
							style="width: 120px;height: auto"></a>
							</c:if>
							</td>
							<td>
							<c:if test="${photo.pictureInfo5 != null }">
							<a rel="gallery${index.index+1 }" class="boxer${index.index+1 }" href="${photo.pictureInfo5.urlPath }">
							<img id="headImg" alt="" src="${photo.pictureInfo5.urlPath }"
							style="width: 120px;height: auto"></a>
							</c:if>
							</td>
						</tr>
						  <script>
							$(function(){
								$('.boxer${index.index+1 }').boxer({
									margin:10
								});
							});
							</script>
						</c:if>
					</table>
				</c:forEach>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
