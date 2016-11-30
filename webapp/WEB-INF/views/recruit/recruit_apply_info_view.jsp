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
<!DOCTYPE html>
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
}
.ftitle{
width: 788px;
background-color: #4F94CD;
line-height: 30px;
}
.fdiv{
	display: inline-block;
	float: left;
	margin-right: 20px;
	max-width: 50%;
	height: auto;
}
</style>
<script type="text/javascript">
var basePath = "<%=basePath%>"; 
	function goBack(){
		location.href = basePath + "recruit/recruitApplyInfoList.do";
	}
	function doUpdateStatus(status) {
		$.post('recruit/recruitApplyInfoUpdateStatus.do', {
			id : $('#id').val(),
			videoStatus : status,
		}, function(result) {
			if (result.success) {
				$.messager.show({ // show error message
					title : '提示',
					msg : result.msg
				});
				location.href = basePath + "recruit/recruitApplyInfoNext.do?id="+$('#id').val();
			} else {
				$.messager.show({ // show error message
					title : '提示',
					msg : result.msg
				});
			}
		}, 'json');
	}
</script>

</head>

<body>
<div class="content" style="width: 1375px;">
			<div class="fdiv">
			<form id="fm" name="fm" method="post" action="" data-options="novalidate:true">
				<input type="hidden" id="id" name="id" value="${info.id }"/>
				<input type="hidden" id="recruitId" name="recruitId" value="${entity.recruitId }"/>
				<table cellpadding="0" cellspacing="0">
					<tr>
						<th>角色头像</th>
						<td><img id="headImg" alt=""
							src="${entity.pictureInfo.urlPath }"
							style="width: 100px;height: 100px"></td>
					</tr>
					<tr>
						<th>角色姓名</th>
						<td>${entity.name }</td>
					</tr>
					<tr>
						<th>性别</th>
						<td><c:if test="${entity.sex=='1' }">男</c:if>
						<c:if test="${entity.sex=='2' }">女</c:if></td>
					</tr>
					<tr>
						<th>标签</th>
						<td>${entity.lableNames }</td>
					</tr>
					<tr>
						<th>片酬</th>
						<td>
						<c:if test="${entity.paidType == '面议'}">${entity.paidType }</c:if>
						<c:if test="${entity.paidType != '面议'}">${entity.paidMin }-${entity.paidMax}/${entity.paidType}</c:if>
						</td>
					</tr>
					<tr>
						<th>所属剧组</th>
						<td>${entity.recruitInfo.title }</td>
					</tr>
					<tr>
						<th>发布人</th>
						<td>${entity.recruitInfo.memberInfo.realName }</td>
					</tr>
					<tr>
						<th>发布时间</th>
						<td>${entity.createDate }</td>
					</tr>
				</table>
			</form>
			</div>
			<div class="fdiv"><video style="width: 460px;height: 329px;" src="${info.fileInfo.urlPath }" controls="controls"></video></div>
			<div class="fdiv">
				<table cellpadding="0" cellspacing="0">
					<tr>
						<th>导演要求</th>
						<th>试戏台词</th>
					</tr>
					<tr style="height: 298px;">
						<td>${entity.requirement }</td>
						<td>${entity.dialogue }</td>
					</tr>
				</table>
			</div>
		</div>
</body>
</html>
