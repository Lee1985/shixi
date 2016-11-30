<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sh" uri="/shFunction"%>
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
<style type="text/css">
table tr th{
font-size: 11pt;
border: 1px solid black;
background-color: #E8E8E8;
width: 200px;
line-height: 30px;
font-family: inherit;
font-weight: normal;
}
table tr td{
font-size: 11pt;
border: 1px solid black;
width: 200px;
text-align: center;
word-break: break-all;
}
.ftitle{
width: 788px;
background-color: #4F94CD;
line-height: 30px;
}
</style>
<script type="text/javascript">
var basePath = "<%=basePath%>"; 
	var type = '${type}';
	function goBack(){
		if(type == '-1'){
			location.href = basePath + $('#backUrl').val();
		}else{
			location.href = basePath + "recruit/recruitRoleInfoList.do?recruitId="+$('#recruitId').val()
			+"&type="+$('#type').val()+"&backUrl="+$('#backUrl').val();
		}
	}
</script>

</head>

<body>
<div class="content" style="width:100%;height:400px">
		<div style="margin-bottom:5px">
				<a href="javascript:void(0);" class="easyui-linkbutton" 
				iconCls="icon-back" plain="true" onclick="goBack()">返回</a>
			</div>
			<form id="fm" name="fm" method="post" action="" data-options="novalidate:true">
				<input type="hidden" id="recruitId" name="recruitId" value="${entity.recruitId }"/>
				<input type="hidden" id="type" name="type" value="${type }"/>
				<input type="hidden" id="backUrl" name="backUrl" value="${backUrl }"/>
				<table cellpadding="0" cellspacing="0">
					<tr>
						<th>角色头像</th>
						<td><img id="headImg" alt=""
							src="${entity.pictureInfo.urlPath }"
							style="width: 100px;height: 100px"></td>
					</tr>
					<tr>
						<th>角色姓名</th>
						<td>${sh:decode(entity.name)}</td>
					</tr>
					<tr>
						<th>性别</th>
						<td><c:if test="${entity.sex=='1' }">男</c:if>
						<c:if test="${entity.sex=='2' }">女</c:if></td>
					</tr>
					<tr>
						<th>标签</th>
						<td>${sh:decode(entity.lableCode)}</td>
					</tr>
					<tr>
						<th>片酬</th>
						<td>
						<c:if test="${entity.paidType == '面议'}">${entity.paidType }</c:if>
						<c:if test="${entity.paidType != '面议'}">${entity.paidMin }
						<c:if test="${entity.paidMax != ''}">-${entity.paidMax}</c:if>
						/${entity.paidType}</c:if>
						</td>
					</tr>
					<tr>
						<th>所属剧组</th>
						<td>${sh:decode(entity.recruitInfo.title)}</td>
					</tr>
					<tr>
						<th>发布人</th>
						<td>${sh:decode(entity.recruitInfo.memberInfo.realName)}</td>
					</tr>
					<tr>
						<th>发布时间</th>
						<td>${entity.createDate }</td>
					</tr>
					<tr>
						<th>导演要求</th>
						<td>${sh:decode(entity.requirement)}</td>
					</tr>
					<tr>
						<th>试戏台词</th>
						<td>${sh:decode(entity.dialogue)}</td>
					</tr>
				</table>
			</form>
		</div>
</body>
</html>
