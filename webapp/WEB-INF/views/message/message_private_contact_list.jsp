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
				+ row.rencentContactId + '\')">'+ view +'</a>';
		}else{
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
	function formatOper(value,row){
		return '<a class="detailcls" href="javascript:void(0);" onclick="doLetterRecords(\'' + row.memberId + '\',\''+ row.rencentContactId + '\',\''+ row.nickname +'\')">查看聊天记录</a>';
	}
	function doLetterRecords(memberId,rencentContactId,nickname){
		$("#recordFrame").attr('src', "message/letterRecords.do?memberId=" + memberId + '&contactMemberId=' + rencentContactId);
		$('#dlg_records').dialog({
			modal:true,
			shadow:true,
			title:'${memberInfo.nickname}与'+nickname+'的聊天记录'
		});
		$('#dlg_records').dialog('open');
	}
	function doBack(){
		location.href = basePath + "message/messagePrivateLetterList.do";
	}
		
</script>
</head>

<body>

	<div id="content" region="center" title="列表" style="padding:5px;width: 98%;height: 98%">
		<table id="dg" class="easyui-datagrid"
			style="" url="message/messagePrivateContactAjaxPage.do?memberId=${param.memberId }"
			title="${memberInfo.nickname }的联系人" iconCls="icon-save" rownumbers="true" pagination="true" fit="true" nowrap="false"
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
					<th field="nickname" align="center" width="15%" formatter="formatMember">联系人</th>
					<th field="recentContent" align="center" width="30%">最近一次消息记录</th>
					<th field="recentContactDateStr" align="center" width="20%">最近一次联系时间</th>
					<th field="operation" align="center" width="30%" formatter="formatOper">操作</th>					
				</tr>
			</thead>
		</table>
		<div id="toolbar">
			<div>
				<a href="javaScript:void();" onclick="doBack()" class="easyui-linkbutton" iconCls="icon-back">返回</a>
				&nbsp;&nbsp;&nbsp;&nbsp;
				联系人: <input id="nickname" class="easyui-textbox" style="width:180px">					
				<a href="javaScript:void();" onclick="searchData()" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
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
		
		<div id="dlg_records" class="easyui-dialog" title="聊天记录"
			style="width:100%;height:100%;top:10px;" data-options="closed:'true'"
			buttons="#dlg_records-buttons">
			<iframe id="recordFrame" name="recordFrame" frameborder="0" height="100%" width="100%" align="center"></iframe>
		</div>
		<div id="dlg_records-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_records').dialog('close')" style="width:90px">关闭</a>
		</div>
	</div>

</body>
</html>
