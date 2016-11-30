<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="security" uri="http://www.bluemobi.com"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sh" uri="/shFunction"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>招募角色信息</title>

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
<style type="text/css">
#fm {
	margin: 0;
	padding: 10px 5px;
}

.ftitle {
	font-size: 12px;
	font-weight: bold;
	padding: 5px 0;
	margin-bottom: 10px;
	border-bottom: 1px solid #ccc;
}

.fitem {
	margin-bottom: 5px;
	width: 13%;
	border-bottom: 1px dotted #A8A8A8;
	padding-bottom: 4px;
	font-size: 12px;
}
.right{
	float:right;
}

.fitem label {
	display: inline-block;
	width: 120px;
	font-weight:bold;
}

.fitem input {
	width: 160px;
}
</style>

<script type="text/javascript">
	var basePath = "<%=basePath%>"; 
	var backUrl = '${backUrl}';
	function goBack(){
		location.href = basePath + backUrl;
	}
	function headPic(value , row , index) {
		if (row.pictureInfo !=null){
		var view = row.pictureInfo.urlPath;
			return "<img src="+ view +" style=\"height:50px;background-color:#434343\"/>";
		}
		else{
			return;
		}
	}
	function formatSex(value, row) {
		if (value==1) {
			return "男";
		} else {
			return "女";
		}
	}
	function formatPaid(value, row) {
		return row.paidMin+"-"+row.paidMax+"/"+row.paidType;
	}
	function formatOper(value, row){
		return '<a class="detailcls" href="javascript:void(0);" style="cursor: pointer;" onclick="doViewRole(\''
			+ row.id + '\')">'+ '查看' +'</a>';
	}
	function doViewRole(id){
		location.href = basePath + "recruit/recruitRoleInfoTempById.do?id="+id+"&type=1&backUrl="+backUrl;
	}
	function doUpdateStatus(status) {
		$.post('recruit/recruitInfoTempUpdateStatus.do', {
			ids : $('#id').val(),
			status : status,
		}, function(result) {
			if (result.success) {
				$.messager.show({ // show error message
					title : '提示',
					msg : result.msg
				});
				location.href = basePath + "recruit/recruitRoleInfoTempNextList.do?type=1&createDate="+$('#createDate').val()+"&backUrl="+backUrl;
			} else {
				$.messager.show({ // show error message
					title : '提示',
					msg : result.msg
				});
			}
		}, 'json');
	}
	
	function formatContent(value,row){
		return decodeURI(value);
	}
</script>
</head>

<body>

	<div id="content" region="center" title="列表" style="padding:5px;">
			<div style="margin-bottom:5px">
				<a href="javascript:void(0);" class="easyui-linkbutton" 
				iconCls="icon-back" plain="true" onclick="goBack()">返回</a>
				<a href="javascript:void(0);" class="easyui-linkbutton" 
				iconCls="icon-ok" plain="true" onclick="doUpdateStatus('1')">通过</a>
				<a href="javascript:void(0);" class="easyui-linkbutton" 
				iconCls="icon-no" plain="true" onclick="doUpdateStatus('2')">拒绝</a>
			</div>
			<div style="padding:5px;">
				<input type="hidden" id="id" name="id" value="${entity.id }">
				<input type="hidden" id="createDate" name="createDate" value="${entity.createDate }">
				<table width="100%">
					<tr>
						<td class="fitem"><label>剧名:</label></td>
						<td class="fitem">${sh:decode(entity.title)}</td>
						<td class="fitem"><label>招募类型:</label></td>
						<td class="fitem">
							<c:if test="${entity.type == '1' }">官方招募</c:if>
							<c:if test="${entity.type == '2' }">私人招募</c:if>
						</td>
						<td class="fitem" style="border: none;width: 23%;"><label>剧本大纲:</label></td>
						<td class="fitem" style="border: none;width: 23%;"><label>备注:</label></td>						
					</tr>					
					<tr>
						<td class="fitem"><label>拍摄地:</label></td>
						<td class="fitem">${entity.city.cityName }</td>
						<td class="fitem"><label>发布人:</label></td>
						<td class="fitem">${sh:decode(entity.memberInfo.realName)}</td>
						<td class="fitem" rowspan="4" style="border: none;width: 23%;vertical-align:top;">${sh:decode(entity.scriptOutline)}</td><!-- 剧本大纲显示 -->
						<td class="fitem" rowspan="4" style="border: none;width: 23%;vertical-align:top;">${sh:decode(entity.remark)}</td><!-- 备注显示 -->
					</tr>					
					<tr>
						<td class="fitem"><label>类型:</label></td>
						<td class="fitem">${entity.category.name }</td>
						<td class="fitem"><label>导演:</label></td>
						<td class="fitem">${sh:decode(entity.director)}</td>
					</tr>					
					<tr>
						<td class="fitem"><label>编剧:</label></td>
						<td class="fitem">${sh:decode(entity.screenwriter)}</td>
						<td class="fitem"><label>拍摄开始时间:</label></td>
						<td class="fitem">${entity.startDate }</td>
					</tr>					
					<tr>
						<td class="fitem"><label>拍摄结束时间:</label></td>
						<td class="fitem">${entity.endDate }</td>
						<td class="fitem"><label>试戏截止时间:</label></td>
						<td class="fitem">${entity.deadline }</td>
					</tr>
				</table>				
			</div>
	</div>
	<div id="content" region="center" title="列表" style="padding:5px;">
		<table id="dg" class="easyui-datagrid"
			style="width:98%;min-height:400px" url="recruit/recruitRoleInfoTempAjaxPage.do?recruitId=${recruitId }"
			iconCls="icon-save" rownumbers="true" pagination="true" nowrap="false"
			singleSelect="true" toolbar="#toolbar" data-options="onLoadSuccess:function(data){ 
	         $('.detailcls').linkbutton(
	         	{ 
		         	plain:true, 
		         	iconCls:'icon-search' 
		         }
	         );
  		}">
			<thead>
				<tr>
					<th field="imgUuid" align="center" width="5%" formatter="headPic">角色图片</th>
					<th field="name" align="center" width="10%" formatter=formatContent>角色名称</th>
					<th field="sex" align="center" width="5%" formatter="formatSex">性别</th>
					<th field="paidMin" align="center" width="10%" formatter="formatPaid">片酬</th>
					<th field="lableCode" align="center" width="10%" formatter=formatContent>标签</th>
					<th field="requirement" align="center" width="25%" formatter=formatContent>导演要求</th>
					<th field="dialogue" align="center" width="25%" formatter=formatContent>试戏台词</th>
					<th field="oper" align="center" width="9%" formatter="formatOper">操作</th>
				</tr>
			</thead>
		</table>
	</div>
</body>
</html>
