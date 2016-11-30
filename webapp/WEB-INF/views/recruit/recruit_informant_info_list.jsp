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

<title>招募举报管理</title>

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
	
	function formatMember(value, row) {
		if (row.informantsInfo !=null){
		var view = decodeURI(row.informantsInfo.nickname);
			return '<a class="detailcls" href="javascript:void(0);" style="cursor: pointer;" onclick="doDetail(\''
				+ row.informantsId + '\')">'+ view +'</a>';
		}
		else{
			return;
		}
	}
	function formatBeMember(value, row) {
		if (row.beInformantsInfo !=null){
		var view = decodeURI(row.beInformantsInfo.nickname);
			return '<a class="detailcls" href="javascript:void(0);" style="cursor: pointer;" onclick="doDetail(\''
				+ row.beInformantsId + '\')">'+ view +'</a>';
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
	function formatRecruit(value, row) {
		if (row.recruitInfo !=null){
		var view = decodeURI(row.recruitInfo.title);
			return '<a class="detailcls" href="javascript:void(0);" style="cursor: pointer;" onclick="doRecruitInfo(\''
				+ row.recruitId + '\')">'+ view +'</a>';
		}
		else{
			return;
		}
	}
	function doRecruitInfo(id){
		var backUrl = "recruit/recruitInformantInfoList.do";
		location.href = basePath + "recruit/recruitRoleInfoList.do?type=2&recruitId="+id+"&backUrl="+backUrl;
	}
	
	function formatType(value, row) {
		if (row.informantType !=null){
			return row.informantType.title;
		}else{
			return;
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
			style="" url="recruit/recruitInformantInfoAjaxPage.do"
			iconCls="icon-save" rownumbers="true" pagination="true" fit="true"
			singleSelect="false" toolbar="#toolbar" data-options="onLoadSuccess:function(data){ 
	         $('.detailcls').linkbutton(
	         	{ 
		         	plain:true, 
		         	iconCls:'icon-search' 
		         }
	         );
  		}">
			<thead>
				<tr>
					<th field="informantsId" align="center" width="150" formatter="formatMember">举报用户</th>
					<th field="beInformantsId" align="center" width="150" formatter="formatBeMember">被举报用户</th>
					<th field="recruitId" align="center" width="200" formatter="formatRecruit">招募信息标题</th>
					<th field="typeId" align="center" width="200" formatter="formatType">举报类型</th>
					<th field="createDate" align="center" width="150">举报时间</th>
					<th field="content" align="center" width="200" formatter="formatContent">举报描述</th>
				</tr>
			</thead>
		</table>
		<div id="toolbar">
<!-- 			<div> -->
<!-- 				名称: <input id="name" class="easyui-textbox" style="width:180px"> -->

<!-- 				<a href="javaScript:void()" onclick="searchData()" -->
<!-- 					class="easyui-linkbutton" iconCls="icon-search">搜索</a> -->
<!-- 			</div> -->
			<div style="margin-bottom:5px">
				<security:act optCode="delete">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-remove" plain="true"
					onclick="doDelete('recruit/recruitInformantInfoAjaxDelete.do')">删除</a>
				</security:act> 
			</div>
		</div>
		<div id="dlg" class="easyui-dialog"
			data-options="iconCls:'icon-save',resizable:true,modal:true"
			style="width:600px;height:400px;padding:10px 20px" closed="true"
			buttons="#dlg-buttons">
			<div class="ftitle">请完善以下信息！</div>
			<form id="fm" name="fm" method="post" action="recruit/recruitInformantInfoAjaxSave.do"
				data-options="novalidate:true">
				<input type="hidden" id="id" name="id">
				<div class="fitem">
					<label>:</label> <input id="id" name="id"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>举报人:</label> <input id="informantsId" name="informantsId"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>被举报人:</label> <input id="beInformantsId" name="beInformantsId"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>被举报招募ID:</label> <input id="recruitId" name="recruitId"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>举报类型:</label> <input id="typeId" name="typeId"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>举报内容:</label> <input id="content" name="content"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>:</label> <input id="createDate" name="createDate"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>状态:</label> <input id="status" name="status"
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
