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

<title>简历管理</title>

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
	function formatName(value, row) {
		return '<a class="detailcls" href="javascript:void(0);" style="cursor: pointer;" onclick="doDetail(\''
			+ row.id + '\')">'+ value +'</a>';
	}
	
	function formatMember(value, row) {
		if (row.memberInfo !=null){
		var view = row.memberInfo.nickname;
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
</script>
</head>

<body>

	<div id="content" region="center" title="列表" style="padding:5px;">
		<table id="dg" class="easyui-datagrid"
			style="width:98%;min-height:400px" url="member/memberResumeInfoAjaxPage.do"
			iconCls="icon-save" rownumbers="true" pagination="true"
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
					<th field="realName" align="center" width="200" formatter="formatName">真实姓名</th>
					<th field="memberId" align="center" width="200" formatter="formatMember">用户名</th>
					<th field="status" align="center" width="200" formatter="formatStatus">认证状态</th>
					<th field="createDate" align="center" width="200">创建时间</th>
					<th field="updateDate" align="center" width="200">修改时间</th>
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
					onclick="doDelete('member/memberResumeInfoAjaxDelete.do')">删除</a>
				</security:act> 
			</div>
		</div>
		<div id="dlg" class="easyui-dialog"
			data-options="iconCls:'icon-save',resizable:true,modal:true"
			style="width:600px;height:400px;padding:10px 20px" closed="true"
			buttons="#dlg-buttons">
			<div class="ftitle">请完善以下信息！</div>
			<form id="fm" name="fm" method="post" action="member/memberResumeInfoAjaxSave.do"
				data-options="novalidate:true">
				<input type="hidden" id="id" name="id">
				<div class="fitem">
					<label>:</label> <input id="id" name="id"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>用户ID:</label> <input id="memberId" name="memberId"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>真实姓名:</label> <input id="realName" name="realName"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>性别:</label> <input id="sex" name="sex"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>身高:</label> <input id="height" name="height"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>体重:</label> <input id="weight" name="weight"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>毕业院校:</label> <input id="school" name="school"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>经常出现的城市:</label> <input id="cityCode" name="cityCode"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>角色标签:</label> <input id="roleLabel" name="roleLabel"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>技能标签:</label> <input id="skillLabel" name="skillLabel"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>头像uuid:</label> <input id="imgUuid" name="imgUuid"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>认证状态:</label> <input id="status" name="status"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>:</label> <input id="imgUuid1" name="imgUuid1"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>:</label> <input id="imgUuid2" name="imgUuid2"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>:</label> <input id="imgUuid3" name="imgUuid3"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>:</label> <input id="imgUuid4" name="imgUuid4"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>:</label> <input id="imgUuid5" name="imgUuid5"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>:</label> <input id="imgUuid6" name="imgUuid6"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>自我介绍UUID:</label> <input id="videoUuid" name="videoUuid"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>:</label> <input id="createDate" name="createDate"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>:</label> <input id="updateDate" name="updateDate"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>胸围:</label> <input id="chest" name="chest"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>腰围:</label> <input id="waist" name="waist"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>臀围:</label> <input id="hipline" name="hipline"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>生日:</label> <input id="birthday" name="birthday"
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
