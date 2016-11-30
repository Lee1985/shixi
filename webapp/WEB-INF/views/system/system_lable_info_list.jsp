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

<title>标签管理</title>

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
			lableName : $('#lableName').val(),
			lableType : $('#lableType').combobox('getValue'),
			typeId : $('#typeId').combobox('getValue')
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
	
	function formatterLable(value, row){
		if(value == 1){
			return '角色';
		}
		if(value == 2){
			return '技能';
		}
	}
	function formatStatus(value, row) {
		var view = (value == 1) ? '启用' : '禁用';
		return view;
	}
	function formatterType(value, row){
		if(row.systemLableType!=null){
			return row.systemLableType.typeName;
		}
		return;
	}
</script>
</head>

<body>

	<div id="content" region="center" title="列表" style="padding:5px;width: 98%;height: 98%">
		<table id="dg" class="easyui-datagrid"
			style="" url="system/systemLableInfoAjaxPage.do"
			iconCls="icon-save" rownumbers="true" pagination="true" fit="true"
			singleSelect="true" toolbar="#toolbar">
			<thead>
				<tr>
					<th field="lableName" align="center" width="120">标签名称</th>
					<th field="lableType" align="center" width="120" formatter="formatterLable">类型</th>
					<th field="typeId" align="center" width="120" formatter="formatterType">标签类型</th>
					<th field="lableContent" align="center" width="200">标签描述</th>
					<th field="status" align="center" width="100"  formatter="formatStatus">状态</th>
					<th field="createDate" align="center" width="150">创建时间</th>
				</tr>
			</thead>
		</table>
		<div id="toolbar">
			<div>
				标签名称: <input id="lableName" class="easyui-textbox" style="width:120px">
				类型： <select class="easyui-combobox" id="lableType"
								name="lableType" style="width:120px;"
								data-options="panelHeight:'auto',editable:false,
										onSelect:function(record){
							                 $('#typeId').combobox({
							                     disabled:false,   
							                     url:'system/systemLableTypeAjaxAll.do?sort=orderList&order=asc&status=1&type='+record.value,   
							                     valueField:'id',   
							                     textField:'typeName' 
							                 });
             							}">
								<option value="">全部</option>
								<option value="1">角色</option>
								<option value="2">技能</option>
							</select>
				标签类型: <input class="easyui-combobox" id="typeId" style="width:120px;"
					name="typeId" data-options="url:'system/systemLableTypeAjaxAll.do?sort=orderList&order=asc&status=1&type='+$('#lableType').combobox('getValue'),
							method:'get',valueField:'id',textField:'typeName',panelHeight:'150',
							editable:false,required:false"/>
							
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
						onclick="doDelete('system/systemLableInfoAjaxDelete.do')">删除</a>
				</security:act>
			</div>
		</div>
		<div id="dlg" class="easyui-dialog"
			data-options="iconCls:'icon-save',resizable:true,modal:true"
			style="width:600px;height:450px;padding:10px 20px" closed="true"
			buttons="#dlg-buttons">
			<div class="ftitle">请完善以下信息！</div>
			<form id="fm" name="fm" method="post" action="system/systemLableInfoAjaxSave.do"
				data-options="novalidate:true">
				<input type="hidden" id="id" name="id">
				<div class="fitem">
					<label>标签名称:</label> <input id="lableName" name="lableName"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>类型:</label> <select class="easyui-combobox" id="lableType"
								name="lableType" style="width:200px;"
								data-options="panelHeight:'auto',required:true,editable:false,
										onSelect:function(record){
							                 $('#typeId2').combobox({
							                     disabled:false,   
							                     url:'system/systemLableTypeAjaxAll.do?sort=orderList&order=asc&status=1&type='+record.value,   
							                     valueField:'id',   
							                     textField:'typeName' 
							                 });
             							}">
								<option value="1">角色</option>
								<option value="2">技能</option>
							</select>
				</div>
				<div class="fitem">
					<label>标签类型:</label> <input class="easyui-combobox" id="typeId2" style="width:200px;"
								name="typeId" data-options="url:'system/systemLableTypeAjaxAll.do?sort=orderList&order=asc&status=1&type='+$('#lableType').combobox('getValue'),
										method:'get',valueField:'id',textField:'typeName',panelHeight:'150',
										editable:false,required:false"/>
				</div>
				<div class="fitem">
					<label>状态:</label> <select class="easyui-combobox" id="status"
								name="status" style="width:200px;"
								data-options="panelHeight:'auto',required:true,editable:false">
								<option value="1">启用</option>
								<option value="0">禁用</option>
							</select>
				</div>
				<div class="fitem">
					<label>标签描述:</label> <input id="lableContent" name="lableContent"
								class="easyui-textbox"
								data-options="prompt:'标签描述',multiline:true,required:true,validType:'length[0,200]'"
								style="height:150px;width: 200">
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
