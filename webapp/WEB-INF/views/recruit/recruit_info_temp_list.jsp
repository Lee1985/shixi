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

<title>招募修改信息</title>

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
			title : $('#title').val(),
			type : $('#type').combobox('getValue'),
			status : $('#status').combobox('getValue'),
			memberName : $('#memberName').val()
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
		if (row.memberInfo !=null){
		var view = decodeURI(row.memberInfo.nickname);
			return '<a class="detailcls" href="javascript:void(0);" style="cursor: pointer;" onclick="doDetail(\''
				+ row.memberId + '\')">'+ view +'</a>';
		}else{
			return;
		}
	}
	function formatCity(value, row) {
		if (row.city !=null){
			return row.city.cityName;
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
	function formatComment(value, row){
		return '<a class="detailcls" href="javascript:void(0);" style="cursor: pointer;" onclick="doComment(\''
			+ row.id + '\')">'+ value +'</a>';
	}
	function formatPublishStatus(value, row){
		if (value==1) {
			return "加急";
		} else {
			return "";
		}
	}
	function formatType(value, row){
		if (value==1) {
			return "官网招募";
		} else if (value==2) {
			return "私人招募";
		}
	}
	
	function formatStatus(value, row) {
		if (value==1) {
			return "已发布";
		} else if (value==0) {
			return "待审核";
		} else if (value==2) {
			return "已拒绝";
		} else if (value==3) {
			return "修改未发布";
		}else if(value==5){
			return "已禁用";
		}
	}
	function view(id){
		var backUrl = "recruit/recruitInfoTempList.do";
		location.href = basePath + "recruit/recruitRoleInfoTempList.do?type=1&recruitId="+id+"&backUrl="+backUrl;
	}
	function formatOper(value, row) {
		return '<a class="detailcls" href="javascript:void(0);" style="cursor: pointer;" onclick="view(\''
				+ row.id + '\',0)">'+ '查看' +'</a>';
	}
	
	function doRefuse() {
		$('#dlg_reply').dialog('open').dialog('setTitle', '回复');
	}
		
	function doUpdateStatus(publishStatus, status, isDelete) {
		var row = $('#dg').datagrid('getSelections');
		if (row.length==0) {
			$.messager.show({
				title : '提示',
				msg : '请选择要操作的行！'
			});
			return;
		} else {
			$.messager.confirm('Confirm', '你确定要执行操作吗?', function(r) {
				if (r) {
					var ids='';
			        for(var i=0;i<row.length;i++){  
			            ids=ids+row[i].id+',';
			            if(ids!=''){  
			                ids=ids.substring(0, ids.length); 
			            }
			        }  
					$.post('recruit/recruitInfoTempUpdateStatus.do', {
						ids : ids,
						publishStatus : publishStatus,
						status : status,
						isDelete : isDelete,
						reply : $('#reply').textbox('getValue')
					}, function(result) {
						if (result.success) {
							$('#dg').datagrid('reload'); // reload the user data
							$.messager.show({ // show error message
								title : '提示',
								msg : result.msg
							});
						} else {
							$.messager.show({ // show error message
								title : '提示',
								msg : result.msg
							});
						}
						$('#dlg_reply').dialog('close');
						$('#reply').textbox('setValue','');
						$('#replySelect').combobox('setValue','');
					}, 'json');
				}
			});
		}
	}
	function doBachApproval() {
		var row = $('#dg').datagrid('getSelections');
        if (row.length==0) {
			$.messager.show({
				title : '提示',
				msg : '请选择要操作的行！'
			});
			return;
		} else {
			$('#dlg_approval').dialog('open').dialog('setTitle', '批量审核');
		}
	}
	function toPublish(){
		var backUrl = "recruit/recruitInfoList.do";
		location.href = basePath + "recruit/recruitRoleInfoList.do?backUrl="+backUrl;
	}
	
	function formatTitle(value,row){
		return decodeURI(value);
	}
</script>
</head>

<body>

	<div id="content" region="center" title="列表" style="padding:5px;width: 98%;height: 98%">
		<table id="dg" class="easyui-datagrid"
			style="" url="recruit/recruitInfoTempAjaxPage.do"
			iconCls="icon-save" rownumbers="true" pagination="true" singleSelect="false" 
			singleSelect="true" toolbar="#toolbar" data-options="fit:true,onLoadSuccess:function(data){ 
	         $('.detailcls').linkbutton(
	         	{ 
		         	plain:true, 
		         	iconCls:'icon-search' 
		         }
	         );
	         $('.editcls').linkbutton(
	         	{ 
		         	plain:true, 
		         	iconCls:'icon-edit' 
		         }
	         );
  		}">
			<thead>
				<tr>
					<th field="title" align="center" width="200" formatter="formatTitle">标题</th>
					<th field="memberId" align="center" width="100" formatter="formatMember">发布者</th>
					<th field="type" align="center" width="100" formatter="formatType">类型</th>
					<th field="cityCode" align="center" width="100" formatter="formatCity">地点</th>
					<th field="status" align="center" width="100" formatter="formatStatus">状态</th>
					<th field="createDate" align="center" width="150">发布时间</th>
					<th field="oper" align="center" width="150" formatter="formatOper">操作</th>
				</tr>
			</thead>
		</table>
		<div id="toolbar">
			<div>
				标题: <input id="title" class="easyui-textbox" style="width:180px">
				招募类型: <select class="easyui-combobox" id="type" name="type"
					style="width:120px;"
					data-options="panelHeight:'auto',editable:false,required:false">
					<option value="">全部</option>
					<option value="1">官网招募</option>
					<option value="2">私人招募</option>
				</select>
				状态: <select class="easyui-combobox" id="status" name="status"
					style="width:120px;"
					data-options="panelHeight:'auto',editable:false,required:false">
					<option value="">全部</option>
					<option value="0">待审核</option>
					<option value="1">已发布</option>
					<option value="2">已拒绝</option>
					<option value="3">修改未发布</option>
					<option value="5">已禁用</option>
				</select>
				发布者: <input id="memberName" class="easyui-textbox" style="width:180px">

				<a href="javaScript:void()" onclick="searchData()"
					class="easyui-linkbutton" iconCls="icon-search">搜索</a>
			</div>
			<div style="margin-bottom:5px">
				<security:act optCode="approvalYes">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-ok" plain="true" onclick="doUpdateStatus('','1','')">批量通过</a>
				</security:act> 
				<security:act optCode="approvalNo">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-cancel" plain="true" onclick="doRefuse()">批量拒绝</a>
				</security:act> 
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
		
		<div id="dlg_reply" class="easyui-dialog"
			data-options="iconCls:'icon-save',resizable:true,modal:true"
			style="width:600px;height:400px;padding:10px 20px;top:10px;" closed="true"
			buttons="#dlg_reply-buttons">
			<form id="fm" name="fm" method="post" action="recruit/recruitInfoTempUpdateStatus.do"
				data-options="novalidate:true">
				<input type="hidden" id="ids" name="ids">
				<input type="hidden" id="status" name="status" value="0">
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
				iconCls="icon-ok" onclick="doUpdateStatus('','2','')"
				style="width:90px">发送</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="javascript:$('#dlg_reply').dialog('close')"
				style="width:90px">取消</a>
		</div>
	</div>

</body>
</html>
