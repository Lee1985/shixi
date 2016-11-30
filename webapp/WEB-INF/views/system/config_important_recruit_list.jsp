<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="security" uri="http://www.bluemobi.com"%>
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

<title>招募信息</title>

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
}

.fitem label {
	display: inline-block;
	width: 80px;
}

.fitem input {
	width: 160px;
}
</style>

<script type="text/javascript">
	var basePath = "<%=basePath%>";
	
	function formatCity(value, row) {
		if (row.city !=null){
			return row.city.cityName;
		}
		else{
			return;
		}
	}
	
	function formatType(value, row){
		if (value==1) {
			return "官网招募";
		} else {
			return "私人招募";
		}
	}
	
	function formatMemberName(value,row){
		return row.memberInfo.nickname;
	}
	
	function getCheckValue(){
		var rows = $('#dg').datagrid('getSelections');
		var ids = '';
		$.each(rows,function(index,row){
			var id = row.id;
			ids = id + ',' +ids;			
		});		
		if(ids != ''){
			ids = ids.substring(0,ids.length - 1);
		}
		return ids;
	}
</script>
</head>

<body>
	<div id="content" region="center" title="列表" style="padding:5px;width: 98%;height: 98%">
		<table id="dg" class="easyui-datagrid"
			style="" url="system/configImportantRecruitDoList.do"  fit="true"
			iconCls="icon-save" rownumbers="true" pagination="true" singleSelect="false" 
			singleSelect="true">
			<thead>
				<tr>
					<th data-options="field:'id',checkbox:true" align="center" width="50"></th>
					<th field="title" align="center" width="200">标题</th>
					<th field="memberId" align="center" width="100" formatter="formatMemberName">发布者</th>
					<th field="type" align="center" width="100" formatter="formatType">类型</th>
					<th field="cityCode" align="center" width="100" formatter="formatCity">地点</th>
					<th field="createDate" align="center" width="150">发布时间</th>
				</tr>
			</thead>
		</table>						
	</div>
</body>
</html>
