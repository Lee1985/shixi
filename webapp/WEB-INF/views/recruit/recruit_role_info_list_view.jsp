<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="security" uri="http://www.bluemobi.com"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	width: 49%;
	float:left;
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
		if(row.paidType == '面议'){
			return row.paidType;
		}else if(row.paidMax == null || row.paidMax == ''){
			return row.paidMin+"/"+row.paidType;
		}else{
			return row.paidMin+"-"+row.paidMax+"/"+row.paidType;
		}
	}
	function formatOper(value, row){
		return '<a class="detailcls" href="javascript:void(0);" style="cursor: pointer;" onclick="doViewRole(\''
			+ row.id + '\')">'+ '查看' +'</a>';
	}
	function doViewRole(id){
		location.href = basePath + "recruit/recruitRoleInfoById.do?id="+id+"&type=1&backUrl="+backUrl;
	}
	function doUpdateStatus(status) {
		$.post('recruit/recruitInfoUpdateStatus.do', {
			ids : $('#id').val(),
			status : status,
		}, function(result) {
			if (result.success) {
				$.messager.show({ // show error message
					title : '提示',
					msg : result.msg
				});
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

	<div id="content" region="center" title="列表" style="padding:5px;">
			<div style="margin-bottom:5px">
				<a href="javascript:void(0);" class="easyui-linkbutton" 
				iconCls="icon-back" plain="true" onclick="goBack()">返回</a>
			</div>
			<div style="padding:5px;">
				<input type="hidden" id="id" name="id" value="${entity.id }">
				<div class="fitem">
					<label>剧名:</label> ${entity.title }
				</div>
				<div class="fitem right">
					<label>招募类型:</label> 
					<c:if test="${entity.type == '1' }">官方招募</c:if>
					<c:if test="${entity.type == '2' }">私人招募</c:if>
				</div>
				<div class="fitem right">
					<label>发布人:</label> ${entity.memberInfo.nickname }
				</div>
				<div class="fitem">
					<label>拍摄地:</label> ${entity.city.cityName }
				</div>
				<div class="fitem">
					<label>类型:</label> ${entity.category.name }
				</div>
				<div class="fitem right">
					<label>导演:</label> ${entity.director }
				</div>
				<div class="fitem">
					<label>编剧:</label> ${entity.screenwriter }
				</div>
				<div class="fitem right">
					<label>拍摄开始时间:</label> ${entity.startDate }
				</div>
				<div class="fitem">
					<label>拍摄结束时间:</label> ${entity.endDate }
				</div>
				<div class="fitem right">
					<label>试戏截止时间:</label> ${entity.deadline }
				</div>
				<div class="fitem" style="line-height: 20px">
					<label>剧本大纲:</label> ${entity.scriptOutline }
				</div>
				<div class="fitem right" style="line-height: 20px">
					<label>备注:</label> ${entity.remark }
				</div>
			</div>
	</div>
	<div id="content" region="center" title="列表" style="padding:5px;">
		<table id="dg" class="easyui-datagrid"
			style="width:98%;min-height:400px" url="recruit/recruitRoleInfoAjaxPage.do?recruitId=${recruitId }"
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
					<th field="imgUuid" align="center" width="150" formatter="headPic">角色图片</th>
					<th field="name" align="center" width="100">角色名称</th>
					<th field="sex" align="center" width="100" formatter="formatSex">性别</th>
					<th field="paidMin" align="center" width="150" formatter="formatPaid">片酬</th>
					<th field="lableCode" align="center" width="100">标签</th>
					<th field="requirement" align="center" width="200">导演要求</th>
					<th field="dialogue" align="center" width="200">试戏台词</th>
					<th field="oper" align="center" width="150" formatter="formatOper">操作</th>
				</tr>
			</thead>
		</table>
	</div>
</body>
</html>
