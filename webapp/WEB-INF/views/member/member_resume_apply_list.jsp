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

<title>简历申请</title>

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
			realName : $('#realName').val(),
			memberName : $('#memberName').val(),
			status : $('#status').combobox('getValue')
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
		$.post(url, {
			id : $('#id').val(),
			status:status
		}, function(result) {
			if (result.success) {
				$('#dg').datagrid('reload'); // reload the user data
				$.messager.show({ // show error message
					title : '提示',
					msg : result.msg
				});
				$('#dlg_resume').dialog('close');
			} else {
				$.messager.show({ // show error message
					title : '提示',
					msg : result.msg
				});
				$('#dlg_resume').dialog('close');
			}
		}, 'json');
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
	function formatStatus(value, row) {
		if (value==0) {
			return "未审核";
		} else if (value==1) {
			return "已审核";
		} else{
			return "";
		}
	}
	function formatOper(value, row) {
		return '<a class="detailcls" href="javascript:void(0);" style="cursor: pointer;" onclick="view(\''
				+ row.id + '\',0)">'+ '查看' +'</a>';
	}
	function view(id) {
		$("#resumeFrame").attr('src', "member/memberResumeTemplateById.do?id=" + id);
		$('#dlg_resume').dialog({
			modal:true,
			shadow:true
		});
		$('#id').val(id);
		$('#dlg_resume').dialog('open');
	}
</script>
</head>

<body>

	<div id="content" region="center" title="列表" style="padding:5px;">
		<table id="dg" class="easyui-datagrid"
			style="width:98%;min-height:400px" url="member/memberResumeApplyAjaxPage.do"
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
					<th field="realName" align="center" width="120">真实姓名</th>
					<th field="memberId" align="center" width="120" formatter="formatMember">用户名</th>
					<th field="status" align="center" width="120" formatter="formatStatus">认证状态</th>
					<th field="createDate" align="center" width="150">创建时间</th>
					<th field="updateDate" align="center" width="150">修改时间</th>
					<th field="oper" align="center" width="150" formatter="formatOper">操作</th>
				</tr>
			</thead>
		</table>
		<div id="toolbar">
			<div>
				姓名: <input id="realName" class="easyui-textbox" style="width:120px">
				用户名: <input id="memberName" class="easyui-textbox" style="width:120px">
				状态: <select class="easyui-combobox" id="status" name="status"
					style="width:120px;"
					data-options="panelHeight:'auto',editable:false,required:false">
					<option value="">全部</option>
					<option value="0" selected="selected">未审核</option>
					<option value="1">已审核</option>
				</select>

				<a href="javaScript:void()" onclick="searchData()"
					class="easyui-linkbutton" iconCls="icon-search">搜索</a>
			</div>
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
		
		<div id="dlg_resume" class="easyui-dialog" title="简历详情"
		style="width:100%;height:100%;top:10px;" data-options="closed:'true'"
		buttons="#dlg_resume-buttons">
		<iframe id="resumeFrame" name="resumeFrame" frameborder="0"
			height="100%" width="100%" align="center"></iframe>
		</div>
		<div id="dlg_resume-buttons">
			<input type="hidden" name="id" id="id">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-ok" onclick="doUpdateStatus('member/memberResumeApplyUpdateStatus.do',1)"
				style="width:90px">通过</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="doUpdateStatus('member/memberResumeApplyUpdateStatus.do',2)"
				style="width:90px">拒绝</a>
		</div>
	</div>

</body>
</html>
