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

<title>用户反馈</title>

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
	function searchData() {
		$('#dg').datagrid('load', {
			memberName : $('#memberName').val(),
			mobile : $('#mobile').val(),
			startDate : $('#startDate').datebox('getValue'),
			endDate : $('#endDate').datebox('getValue')
		});
	}
	function doEdit() {
		var row = $('#dg').datagrid('getSelected');
		if (row) {
			$('#dlg').dialog('open').dialog('setTitle', '查看');
			$('#fm').form('load', row);
			$('#memberId').textbox('setValue',row.memberInfo.nickname);
		}
	}
	function save() {
		var index ;
		$('#fm').form('submit', {
			onSubmit : function() {
				var rr=$(this).form('enableValidation').form('validate');
				if (rr) {
					index=layer.load('操作中...请等待！', 0);
				}else{
					return false;
				}
			},
			dataType:'json',
			success : function(result) {
				var result = eval('(' + result + ')');
				layer.close(index);
				if (result.success) {
					$('#dlg').dialog('close'); // close the dialog
					$('#dg').datagrid('reload'); // reload the user data
				} else {
					$.messager.show({
						title : '提示',
						msg : result.msg
					});
				}
			}
		});
	}
	function doReply() {
		var row = $('#dg').datagrid('getSelected');
		if (row) {
			$('#dlg_reply').dialog('open').dialog('setTitle', '回复');
			$('#id').val(row.id);
		}
	}
	function reply() {
		var index ;
		$('#fm2').form('submit', {
			onSubmit : function() {
				var rr=$(this).form('enableValidation').form('validate');
				if (rr) {
					index=layer.load('操作中...请等待！', 0);
				}else{
					return false;
				}
			},
			dataType:'json',
			success : function(result) {
				var result = eval('(' + result + ')');
				layer.close(index);
				if (result.success) {
					$('#dlg_reply').dialog('close'); // close the dialog
					$('#dg').datagrid('reload'); // reload the user data
				} else {
					$.messager.show({
						title : '提示',
						msg : result.msg
					});
				}
			}
		});
	}
	function formatUser(value , row , index){
		if (row.memberInfo !=null){
		var view = decodeURI(row.memberInfo.nickname);
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
	function formatStatus(value , row){
		if (value==1) {
			return "已回复";
		} else {
			return "未回复";
		}
	}
	
	function formatContent(value,row){
		return decodeURI(value);
	}
</script>
</head>

<body>

	<div id="content" region="center" title="列表" style="padding:5px;width: 98%;height: 98%">
		<table id="dg" class="easyui-datagrid"
			style="" url="member/memberFeedbackAjaxPage.do"
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
					<th field="memberId" align="center" width="120" formatter="formatUser">用户名</th>
					<th field="mobile" align="center" width="200">手机号</th>
					<th field="content" align="center" width="200" formatter="formatContent">反馈内容</th>
					<th field="status" align="center" width="100" formatter="formatStatus">状态</th>
					<th field="createDate" align="center" width="150">反馈时间</th>
				</tr>
			</thead>
		</table>
		<div id="toolbar">
			<div>
				用户名: <input id="memberName" class="easyui-textbox" style="width:180px">
				手机号: <input id="mobile" class="easyui-textbox" style="width:180px">
				开始时间: <input id="startDate" class="easyui-datebox" style="width:180px">
				结束时间: <input id="endDate" class="easyui-datebox" style="width:180px">
				<a href="javaScript:void()" onclick="searchData()"
					class="easyui-linkbutton" iconCls="icon-search">搜索</a>
			</div>
			<div style="margin-bottom:5px">
				<security:act optCode="view">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-search" plain="true" onclick="doEdit()">查看</a>
				</security:act>
				<security:act optCode="record">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-edit" plain="true" onclick="doReply()">回复</a>
				</security:act> 
				<security:act optCode="delete">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-remove" plain="true"
					onclick="doDelete('member/memberFeedbackAjaxDelete.do')">删除</a>
				</security:act> 
			</div>
		</div>
		<div id="dlg" class="easyui-dialog"
			data-options="iconCls:'icon-save',resizable:true,modal:true"
			style="width:600px;height:400px;padding:10px 20px;top:10px;" closed="true"
			buttons="#dlg-buttons">
			<form id="fm" name="fm" method="post" action="member/memberFeedbackAjaxSave.do"
				data-options="novalidate:true">
				<div class="fitem">
					<label>用户名:</label> <input id="memberId" name="memberId"
						style="width: 200px" class="easyui-textbox" readonly="readonly">
				</div>
				<div class="fitem">
					<label>手机:</label> <input id="mobile" name="mobile"
						style="width: 200px" class="easyui-textbox" readonly="readonly">
				</div>
				<div class="fitem">
					<label>反馈时间:</label> <input id="createDate" name="createDate"
						style="width: 200px" class="easyui-textbox" readonly="readonly">
				</div>
				<div class="fitem">
					<label>内容:</label> <input id="content" name="content"
								class="easyui-textbox"
								data-options="prompt:'标签描述',multiline:true,readonly:true,validType:'length[0,200]'"
								style="height:150px;width: 200">
				</div>
			</form>
		</div>
		<div id="dlg-buttons">
			<a
				href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')"
				style="width:90px">关闭</a>
		</div>
		
		
		<div id="dlg_reply" class="easyui-dialog"
			data-options="iconCls:'icon-save',resizable:true,modal:true"
			style="width:600px;height:400px;padding:10px 20px;top:10px;" closed="true"
			buttons="#dlg_reply-buttons">
			<form id="fm2" name="fm2" method="post" action="member/memberFeedbackReply.do"
				data-options="novalidate:true">
				<input type="hidden" id="id" name="id">
				<div class="fitem">
					<label>选择:</label> <input class="easyui-combobox" id="replySelect"
								name="replySelect" style="width: 300px"
								data-options="url:'system/systemCommonReplyAjaxAll.do',
										method:'get',valueField:'id',textField:'content',panelHeight:'150',
										editable:false,required:false,
										onSelect:function(record){
											$('#reply').textbox('setValue',record.content);
             						}">
				</div>
				<div class="fitem">
					<label>内容:</label> <input id="reply" name="reply"
								class="easyui-textbox"
								data-options="prompt:'回复内容',multiline:true,required:true,validType:'length[0,200]'"
								style="height:150px;width: 300px">
				</div>
			</form>
		</div>
		<div id="dlg_reply-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-ok" onclick="reply()"
				style="width:90px">发送</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="javascript:$('#dlg_reply').dialog('close')"
				style="width:90px">取消</a>
		</div>
		
		
		
		<div id="dlg_detail" class="easyui-dialog" title="用户详情"
		style="width:100%;height:100%;top:10px;" data-options="closed:'true'"
		buttons="#dlg_detail-buttons">
		<iframe id="detailFrame" name="detailFrame" frameborder="0"
			height="100%" width="100%" align="center"></iframe>
		</div>
		<div id="dlg_detail-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="javascript:$('#dlg_detail').dialog('close')"
				style="width:90px">关闭</a>
		</div>
	</div>

</body>
</html>
