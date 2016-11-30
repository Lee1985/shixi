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

<title>角色头像管理</title>

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
	function doAdd() {
		$('#dlg').dialog('open').dialog('setTitle', '新建');
		$('#fm').form('clear');
		$('#sysImgs').attr('src','images/add.jpg');
	}
	function doEdit() {
		var row = $('#dg').datagrid('getSelected');
		if (row) {
			$('#dlg').dialog('open').dialog('setTitle', '修改');
			$('#fm').form('clear');
			$('#fm').form('load', row);
			$("#sysImgs").attr("src", row.pictureInfo.urlPath);
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
	function onChange(fileObj) {
		var reader = new FileReader();
		reader.readAsDataURL(fileObj.files[0]);
		reader.onload = function(e) {
			$("#sysImgs").attr("src", e.target.result);
		};
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
	function formatterType(value, row){
		if(row.roleType!=null){
			return row.roleType.typeName;
		}
		return;
	}
</script>
</head>

<body>

	<div id="content" region="center" title="列表" style="padding:5px;width: 98%;height: 98%">
		<table id="dg" class="easyui-datagrid"
			style="" url="system/systemRolePicAjaxPage.do"
			iconCls="icon-save" rownumbers="true" pagination="true" fit="true"
			singleSelect="true" toolbar="#toolbar">
			<thead>
				<tr>
					<th field="imgUuid" align="center" width="150" formatter="headPic">图片</th>
					<th field="title" align="center" width="200">标题</th>
					<th field="typeId" align="center" width="120" formatter="formatterType">类型</th>
					<th field="orderList" align="center" width="100">排序</th>
					<th field="createDate" align="center" width="150">创建时间</th>
				</tr>
			</thead>
		</table>
		<div id="toolbar">
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
					onclick="doDelete('system/systemRolePicAjaxDelete.do')">删除</a>
				</security:act> 
			</div>
		</div>
		<div id="dlg" class="easyui-dialog"
			data-options="iconCls:'icon-save',resizable:true,modal:true"
			style="width:600px;height:400px;padding:10px 20px" closed="true"
			buttons="#dlg-buttons">
			<div class="ftitle">请完善以下信息！</div>
			<form id="fm" name="fm" method="post" action="system/systemRolePicAjaxSave.do"
				data-options="novalidate:true" enctype="multipart/form-data">
				<input type="hidden" id="id" name="id">
				<div class="fitem">
					<label>图片(建议比例 1:1)</label> <img id="sysImgs" alt="" src=""
						style="width: 100px;height: 100px" onclick="sysImg.click()">
					<label>&nbsp;</label> <input type="file" id="sysImg"
						name="sysImg" style="width:200px;display: none"
						onChange="onChange(this)"
						data-options="prompt:'选择图片...',required:true">
				</div>
				<div class="fitem">
					<label>标题:</label> <input id="title" name="title"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>类型:</label> <input class="easyui-combobox" id="typeId" style="width:200px;"
								name="typeId" data-options="url:'system/systemRoleTypeAjaxAll.do?status=1',
										method:'get',valueField:'id',textField:'typeName',panelHeight:'100px',
										editable:false,required:false"/>
				</div>
				<div class="fitem">
					<label>排序:</label> <input id="orderList" name="orderList"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>内容:</label> <input id="content" name="content"
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
