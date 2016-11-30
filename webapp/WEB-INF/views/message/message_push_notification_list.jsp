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

<title>推送消息</title>

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
			title : $('#title').val()
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
	function formatReceive(value, row) {
		if (row.receiveInfo !=null){
		var view = row.receiveInfo.nickname;
			return '<a class="detailcls" href="javascript:void(0);" style="cursor: pointer;" onclick="doDetail(\''
				+ row.receiveId + '\')">'+ view +'</a>';
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
	function formatType(value, row){
		if(value==1){
			return "用户反馈回复";
		}else if(value==2){
			return "身份认证";
		}else if(value==3){
			return "实名认证";
		}else if(value==4){
			return "学历认证";
		}else if(value==5){
			return "招募审核";
		}else if(value==6){
			return "剧组回复";
		}else {
			return "其他";
		}
	}
	
	function memberChoose(){
		$('#dlg_member').dialog('open');
	}
	
	function onClickRowRecommend(row){
		if(row){
			$('#receiveId').val(row.id);
			$('#receiveName').textbox('setValue',row.nickname);
			$('#dlg_member').dialog('close');
		}
		return;
	}
	function formatSex(value, row) {
		if (value==1) {
			return "男";
		} else {
			return "女";
		}
	}
</script>
</head>

<body>

	<div id="content" region="center" title="列表" style="padding:5px;width: 98%;height: 98%">
		<table id="dg" class="easyui-datagrid"
			style="" url="message/messagePushNotificationAjaxPage.do"
			iconCls="icon-save" rownumbers="true" pagination="true" fit="true"
			singleSelect="true" toolbar="#toolbar" nowrap="false" data-options="onLoadSuccess:function(data){ 
	         $('.detailcls').linkbutton(
	         	{ 
		         	plain:true, 
		         	iconCls:'icon-search' 
		         }
	         );
  		}">
			<thead>
				<tr>
					<th field="title" align="center" width="200">标题</th>
					<th field="type" align="center" width="200" formatter="formatType">消息类型</th>
					<th field="receiveId" align="center" width="200" formatter="formatReceive">接收人</th>
					<th field="content" align="center" width="200">内容</th>
					<th field="createDate" align="center" width="200">创建时间</th>
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
						iconCls="icon-add" plain="true" onclick="doAdd()">发送</a>
				</security:act>
				<security:act optCode="delete">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-remove" plain="true"
					onclick="doDelete('message/messagePushNotificationAjaxDelete.do')">删除</a>
				</security:act> 
			</div>
		</div>
		<div id="dlg" class="easyui-dialog"
			data-options="iconCls:'icon-save',resizable:true,modal:true"
			style="width:600px;height:400px;padding:10px 20px;top:10px;" closed="true"
			buttons="#dlg-buttons">
			<div class="ftitle">请完善以下信息！</div>
			<form id="fm" name="fm" method="post" action="message/messagePushNotificationAjaxSave.do"
				data-options="novalidate:true">
				<input type="hidden" id="id" name="id">
				<div class="fitem">
					<label>标题:</label> <input id="title" name="title"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>类型:</label> <select class="easyui-combobox" id="type"
								name="type" style="width:200px;"
								data-options="panelHeight:'auto',required:true,editable:false">
								<option value="1">用户反馈回复</option>
								<option value="2">身份认证</option>
								<option value="3">实名认证</option>
								<option value="4">学历认证</option>
								<option value="5">招募审核</option>
								<option value="6">剧组回复</option>
								<option value="7">其他</option>
							</select>
				</div>
				<div class="fitem">
					<label>接收人:</label> <input type="hidden" id="receiveId" name="receiveId">
					<input id="receiveName" name="receiveName"
						style="width: 100px" class="easyui-textbox" readonly="readonly" >
						<a href="javascript:void(0);" class="easyui-linkbutton" 
						iconCls="icon-search" plain="true" onclick="memberChoose()">选择</a>
				</div>
				<div class="fitem">
					<label>内容:</label> <input id="content" name="content"
								class="easyui-textbox" required="true"
								data-options="prompt:'内容',multiline:true,required:true,validType:'length[0,200]'"
								style="height:100px;width: 200px">
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
		
		<div id="dlg_member" class="easyui-dialog" title="用户"
			data-options="iconCls:'icon-save',resizable:true,modal:true"
			style="width:800px;height:400px;padding:10px 20px;top:50px" closed="true">
		<table id="dg1" class="easyui-datagrid"
		style="width:100%;height:100%"  
		data-options="
		url:'member/memberInfoAjaxPage.do?status=1',
		iconCls:'icon-save',
		rownumbers:true,
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar1',
		onClickRow:function(rowIndex,rowData){
		  onClickRowRecommend(rowData);
		}
		">
		<thead>
			<tr>
				<th field="nickname" align="center" width="100">昵称</th>
				<th field="realName" align="center" width="100">真实姓名</th>
				<th field="sex" align="center" width="100" formatter="formatSex">性别</th>
				<th field="identityInfo" align="center" width="120">身份认证</th>
			</tr>
		</thead>
	</table>
	<div id="toolbar1">
		<div>
			昵称 : <input id="nickname" class="easyui-textbox" style="width:180px">
			<a href="javaScript:void()" onclick="searchData1()"
				class="easyui-linkbutton" data-options="iconCls:'icon-search'">搜索</a>
		</div>
	</div>
	</div>
	</div>

</body>
</html>
