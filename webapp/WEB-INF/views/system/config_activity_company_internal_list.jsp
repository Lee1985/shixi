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

<title>内部活动管理</title>

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
<script type="text/javascript" src="js/system/keditor.js"></script>
<style type="text/css">
#fm {
	margin: 0;
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
	function headPic(value , row , index) {
		if (row.pictureInfo !=null){
		var view = row.pictureInfo.urlPath;
			return "<img src="+ view +" style=\"height:50px;background-color:#434343\"/>";
		}else{
			return;
		}
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
	
	function formatUseStatus(value,row){
		if(value == 1){
			return "使用中";
		}else if(value == 0){
			return "未使用";
		}
	}
	
</script>
</head>

<body>
	<div id="content" region="center" title="列表" style="width: 99%;height: 99%">
		<table id="dg" class="easyui-datagrid" data-options="idField: 'id'"
			url="system/configActivityCompanyInternalDoList.do"
			iconCls="icon-save" rownumbers="true" fit="true"
			singleSelect="false">
			<thead>
				<tr>
					<th data-options="field:'id',checkbox:true" align="center" width="50"></th>
					<th field="title" align="center" width="200">标题</th>
					<th field="imgUuid" align="center" width="200" formatter="headPic">图片</th>
					<th field="createDate" align="center" width="200">发布时间</th>
				</tr>
			</thead>
		</table>		
	</div>
</body>
</html>
