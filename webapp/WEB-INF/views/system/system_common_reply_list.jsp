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

<title>常用回复</title>

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
			name : $('#name').val(),
			type : $('#replyType').combobox('getValue')
		});
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
	
	function doAdd(){
		$('#dlg').dialog('open').dialog('setTitle', '新建');
		$('#fm').form('clear');
		var data = $('#type').combobox('getData');
		$('#type').combobox('select',data[0].type);		
	}
</script>
</head>

<body>
	<div id="content" region="center" title="列表" style="padding:5px;width: 98%;height: 98%">
		<table id="dg" class="easyui-datagrid"
			style="" url="system/systemCommonReplyAjaxPage.do"
			iconCls="icon-save" rownumbers="true" pagination="true" fit="true"
			singleSelect="true" toolbar="#toolbar" nowrap="false">
			<thead>
				<tr>
					<th field="typeName" align="center" width="200">类别</th>
					<th field="content" align="center" width="200">内容</th>
					<th field="orderList" align="center" width="200">排序</th>
					<th field="createDate" align="center" width="200">创建时间</th>
				</tr>
			</thead>
		</table>
		<div id="toolbar">
			<div>
				类别：<select id="replyType" class="easyui-combobox" data-options="panelHeight:'auto',editable:false,
						width:'100px',equired:true,valueField:'type',textField:'name'">
						<option value="">全部</option>
						<c:forEach items="${replyTypes}" var="replyType" varStatus="status">
							<option value="${replyType.code}">${replyType.name}</option>							
						</c:forEach>
					</select>
				内容: <input id="content" class="easyui-textbox" style="width:180px">
				<a href="javaScript:void()" onclick="searchData()"
					class="easyui-linkbutton" iconCls="icon-search">搜索</a>
			</div>
			<div style="margin-bottom:5px">
				<security:act optCode="add">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-add" plain="true" onclick="doAdd()">新建</a>
				</security:act>
				<security:act optCode="edit">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-edit" plain="true" onclick="doEdit()">修改</a>
				</security:act> 
				<security:act optCode="delete">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-remove" plain="true"
					onclick="doDelete('system/systemCommonReplyAjaxDelete.do')">删除</a>
				</security:act> 
			</div>
		</div>
		<div id="dlg" class="easyui-dialog"
			data-options="iconCls:'icon-save',resizable:true,modal:true"
			style="width:600px;height:400px;padding:10px 20px" closed="true"
			buttons="#dlg-buttons">
			<div class="ftitle">请完善以下信息！</div>
			<form id="fm" name="fm" method="post" action="system/systemCommonReplyAjaxSave.do"
				data-options="novalidate:true">
				<input type="hidden" id="id" name="id">
				
				<div class="fitem">
					<label>类别:</label>
					<select id="type" name="type" class="easyui-combobox" data-options="panelHeight:'auto',editable:false,
						width:'100px',equired:true,valueField:'type',textField:'name'">							
						<c:forEach items="${replyTypes}" var="replyType" varStatus="status">
							<option value="${replyType.code}">${replyType.name}</option>							
						</c:forEach>
					</select>
				</div>
													
				<div class="fitem">													
					<label>内容:</label> <input id="content" name="content"
								class="easyui-textbox"
								data-options="prompt:'回复内容',multiline:true,required:true,validType:'length[0,200]'"
								style="height:150px;width: 400px">
				</div>
				<div class="fitem">
					<label>排序:</label> <input id="orderList" name="orderList"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
			</form>
		</div>
		<div id="dlg-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton c6"
				iconCls="icon-ok" onclick="save()" style="width:90px">确定</a> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')"
				style="width:90px">取消</a>
		</div>
	</div>

</body>
</html>
