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

<title>招募剧目种类</title>

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
			name : $('#name').val()
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
	function doUpdateStatus(url,status) {
		var row = $('#dg').datagrid('getSelected');
		var msg="";
		if (row) {
			if (status==1) {
				msg="启用";
				if (row.status==1) {
					$.messager.show({ // show error message
						title : '提示',
						msg : '已启用！'
					});
					return;
				}
			}
			if (status==0) {
				msg="禁用";
				if (row.status==0) {
					$.messager.show({ // show error message
						title : '提示',
						msg : '已禁用！'
					});
					return;
				}
			}
			$.messager.confirm('Confirm', '你确定要'+msg+'吗?', function(r) {
				if (r) {
					$.post(url, {
						id : row.id,
						status:status
					}, function(result) {
						if (result.success) {
							$('#dg').datagrid('reload'); // reload the user data
						} else {
							$.messager.show({ // show error message
								title : '提示',
								msg : result.msg
							});
						}
					}, 'json');
				}
			});
		}
	}
	function formatStatus(value, row) {
		if (value==1) {
			return "启用";
		} else {
			return "禁用";
		}
	}
	
</script>
</head>

<body>

	<div id="content" region="center" title="列表" style="padding:5px;width: 98%;height: 98%">
		<table id="dg" class="easyui-datagrid"
			style="" url="recruit/recruitCategoryAjaxPage.do"
			iconCls="icon-save" rownumbers="true" pagination="true" fit="true"
			singleSelect="true" toolbar="#toolbar">
			<thead>
				<tr>
					<th field="name" align="center" width="200">名称</th>
					<th field="orderList" align="center" width="200">排序</th>
					<th field="color" align="center" width="200">颜色值</th>
					<th field="status" align="center" width="200" formatter="formatStatus">状态</th>
					<th field="createDate" align="center" width="200">创建时间</th>
				</tr>
			</thead>
		</table>
		<div id="toolbar">
			<div>
				名称: <input id="name" class="easyui-textbox" style="width:180px">

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
					onclick="doDelete('recruit/recruitCategoryAjaxDelete.do')">删除</a>
				</security:act> 
				<security:act optCode="isYes">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-lock_open" plain="true"
						onclick="doUpdateStatus('recruit/recruitCategoryUpdateStatus.do',1)">启用</a>
				</security:act>
				<security:act optCode="isNo">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-lock" plain="true"
						onclick="doUpdateStatus('recruit/recruitCategoryUpdateStatus.do',0)">禁用</a>
				</security:act>
			</div>
		</div>
		<div id="dlg" class="easyui-dialog"
			data-options="iconCls:'icon-save',resizable:true,modal:true"
			style="width:400px;height:300px;padding:10px 20px" closed="true"
			buttons="#dlg-buttons">
			<div class="ftitle">请完善以下信息！</div>
			<form id="fm" name="fm" method="post" action="recruit/recruitCategoryAjaxSave.do"
				data-options="novalidate:true">
				<input type="hidden" id="id" name="id">
				<div class="fitem">
					<label>名称:</label> <input id="name" name="name"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>颜色值:</label> <input id="color" name="color"
						style="width: 200px" class="easyui-textbox" required="true">
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
