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

<title>私信信息</title>

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
	function searchData() {
		$('#dg').datagrid('load', {
			nickname : $('#nickname').val()			
		});
	}	
	function formatMember(value, row) {
		if (row.nickname !=null){
		var view = row.nickname;
			return '<a class="detailcls" href="javascript:void(0);" style="cursor: pointer;" onclick="doDetail(\''
				+ row.memberId + '\')">'+ view +'</a>';
		}
		else{
			return;
		}
	}
	function doDetail(id) {
		$("#detailFrame").attr('src', "member/memberInfoById.do?id=" + id);
		$('#dlg_detail').dialog({    
			modal:true,
			shadow:true				
		});
		$('#dlg_detail').dialog('open');
	}	
	function formatType(value, row) {
		if (value == 1){
			return "官方招募";
		}else if (value == 2){
			return "私人招募";
		}
	}
	
	function formatContactNumber(value,row){
		return '<a href="javascript:void(0);" onclick="doViewContactUser(\'' + row.memberId + '\')">' + value + '</a>';
	}
	
	function doViewContactUser(memberId){
		location.href = basePath + 'message/contactUsers.do?memberId=' + memberId;
	}
</script>
</head>

<body>

	<div id="content" region="center" title="列表" style="padding:5px;width: 98%;height: 98%">
		<table id="dg" class="easyui-datagrid"
			style="" url="message/messagePrivateLetterAjaxPage.do"
			iconCls="icon-save" rownumbers="true" pagination="true" fit="true"
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
					<th field="nickname" align="center" width="30%" formatter="formatMember">用户名称</th>
					<th field="contactNumber" align="center" width="20%" formatter="formatContactNumber">联系人</th>
					<th field="type" align="center" width="10%" formatter="formatType">私信类型</th>
					<th field="recentContactDateStr" align="center" width="35%">最近一次联系时间</th>					
				</tr>
			</thead>
		</table>
		<div id="toolbar">
			<div>
				用户: <input id="nickname" class="easyui-textbox" style="width:180px">					
				<a href="javaScript:void()" onclick="searchData()"
					class="easyui-linkbutton" iconCls="icon-search">搜索</a>
			</div>			
		</div>		
		
		<div id="dlg_detail" class="easyui-dialog" title="用户详情"
			style="width:100%;height:100%;top:10px;" data-options="closed:'true'"
			buttons="#dlg_detail-buttons">
			<iframe id="detailFrame" name="detailFrame" frameborder="0" height="100%" width="100%" align="center"></iframe>
		</div>
		<div id="dlg_detail-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_detail').dialog('close')" style="width:90px">关闭</a>
		</div>
	</div>

</body>
</html>
