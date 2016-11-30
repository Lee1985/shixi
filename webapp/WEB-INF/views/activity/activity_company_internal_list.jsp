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
<script>
	var editor;
	KindEditor.ready(function(K) {
		editor = K.create('#contentHtml', {
			width : '400px',
			height:'400px',
			cssPath : '<%=basePath%>js/kingeditor/plugins/code/prettify.css',
			uploadJson : '<%=basePath%>jsp/upload_json.jsp',
			fileManagerJson : '<%=basePath%>jsp/file_manager_json.jsp',
			allowFileManager : true,
			items : [ 'source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template', 'code', 'cut', 'copy', 'paste',
        'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
        'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
        'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
        'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
        'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image', 'multiimage',
        'table', 'hr', 'emoticons', 'baidumap', 'pagebreak',
        'anchor', 'link', 'unlink', 'media'],
			afterChange : function() {
				this.sync();// 这个是必须的,如果你要覆盖afterChange事件的话,请记得最好把这句加上.
			},
			afterCreate : function() {
				var self = this;
				K.ctrl(document, 13, function() {
					self.sync();
					document.forms['fm'].submit();
				});
				K.ctrl(self.edit.doc, 13, function() {
					self.sync();
					document.forms['fm'].submit();
				});
			}
		});
		prettyPrint();
	});
	
</script>
<script type="text/javascript">
	function searchData() {
		$('#dg').datagrid('load', {
			title : $('#title').val()
		});
	}
	function doAdd() {
		$('#dlg').dialog('open').dialog('setTitle', '新建');
		$('#fm').form('clear');
		$('#sysImgs').attr('src','images/add.jpg');
		editor.html('');
	}
	function doEdit() {
		var row = $('#dg').datagrid('getSelected');
		if (row) {
			$('#dlg').dialog('open').dialog('setTitle', '修改');
			$('#fm').form('clear');
			$('#fm').form('load', row);
			$("#sysImgs").attr("src", row.pictureInfo.urlPath);
			editor.html(row.content);
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
</script>
</head>

<body>

	<div id="content" region="center" title="列表" style="padding:5px;width: 98%;height: 98%">
		<table id="dg" class="easyui-datagrid"
			style="" url="activity/activityCompanyInternalAjaxPage.do"
			iconCls="icon-save" rownumbers="true" pagination="true" fit="true"
			singleSelect="true" toolbar="#toolbar">
			<thead>
				<tr>
					<th field="title" align="center" width="200">标题</th>
					<th field="imgUuid" align="center" width="200" formatter="headPic">图片</th>
					<th field="createDate" align="center" width="200">发布时间</th>
				</tr>
			</thead>
		</table>
		<div id="toolbar">
			<div>
				标题: <input id="title" class="easyui-textbox" style="width:180px">

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
					onclick="doDelete('activity/activityCompanyInternalAjaxDelete.do')">删除</a>
				</security:act> 
			</div>
		</div>
		<div id="dlg" class="easyui-dialog"
			data-options="iconCls:'icon-save',resizable:true,modal:true"
			style="width:800px;height:400px;padding:10px 20px" closed="true"
			buttons="#dlg-buttons">
			<div class="ftitle">请完善以下信息！</div>
			<form id="fm" name="fm" method="post" action="activity/activityCompanyInternalAjaxSave.do"
				data-options="novalidate:true" enctype="multipart/form-data">
				<input type="hidden" id="id" name="id">
				<div class="fitem">
					<label>图片(建议比例 2:1)</label> <img id="sysImgs" alt="" src=""
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
					<label>内容:</label> <textarea id="contentHtml" name="content"
						style="height:80px;width: 100px"></textarea>
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
