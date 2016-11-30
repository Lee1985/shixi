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

<title>招募信息</title>

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
		var view = row.memberInfo.nickname;
			return '<a class="detailcls" href="javascript:void(0);" style="cursor: pointer;" onclick="doDetail(\''
				+ row.memberId + '\')">'+ view +'</a>';
		}
		else{
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
		} else {
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
		} else if (value==5) {
			return "已禁用";
		}
	}
	function formatTitle(value, row) {
		return '<a class="editcls" href="javascript:void(0);" style="cursor: pointer;" onclick="edit(\''
				+ row.id + '\',0)">'+ value +'</a>';
	}
	
	function edit(id){
		var backUrl = "recruit/recruitInfoList.do";
		location.href = basePath + "recruit/recruitRoleInfoList.do?recruitId="+id+"&backUrl="+backUrl;
	}
	function view(id){
		var backUrl = "recruit/recruitInfoList.do";
		location.href = basePath + "recruit/recruitRoleInfoList.do?type=1&recruitId="+id+"&backUrl="+backUrl;
	}
	function formatOper(value, row) {
		return '<a class="detailcls" href="javascript:void(0);" style="cursor: pointer;" onclick="view(\''
				+ row.id + '\',0)">'+ '查看' +'</a>';
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
					$.post('recruit/recruitInfoUpdateStatus.do', {
						ids : ids,
						publishStatus : publishStatus,
						status : status,
						isDelete : isDelete
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
</script>
</head>

<body>

	<div id="content" region="center" title="列表" style="padding:5px;width: 98%;height: 98%">
		<table id="dg" class="easyui-datagrid"
			style="" url="recruit/recruitInfoAjaxPage.do"  fit="true"
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
					<th field="publishStatus" align="center" width="100" formatter="formatPublishStatus">加急状态</th>
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
				发布者: <input id="memberName" class="easyui-textbox" style="width:180px">

				<a href="javaScript:void()" onclick="searchData()"
					class="easyui-linkbutton" iconCls="icon-search">搜索</a>
			</div>
			<div style="margin-bottom:5px">
				<security:act optCode="add">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-add" plain="true" onclick="toPublish()">发布招募</a>
				</security:act>
				<security:act optCode="delete">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-remove" plain="true"
					onclick="doUpdateStatus('','','1')">批量删除</a>
				</security:act> 
				<security:act optCode="approvalYes">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-lock_open" plain="true" onclick="doUpdateStatus('','1','')">批量启用</a>
				</security:act> 
				<security:act optCode="approvalNo">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-lock" plain="true" onclick="doUpdateStatus('','5','')">批量禁用</a>
				</security:act> 
				<security:act optCode="urgent">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-add" plain="true" onclick="doUpdateStatus('1','','')">批量加急</a>
				</security:act> 
				<security:act optCode="urgentNo">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-remove" plain="true" onclick="doUpdateStatus('0','','')">取消加急</a>
				</security:act> 
			</div>
		</div>
		<div id="dlg" class="easyui-dialog"
			data-options="iconCls:'icon-save',resizable:true,modal:true"
			style="width:600px;height:400px;padding:10px 20px" closed="true"
			buttons="#dlg-buttons">
			<div class="ftitle">请完善以下信息！</div>
			<form id="fm" name="fm" method="post" action="recruit/recruitInfoAjaxSave.do"
				data-options="novalidate:true">
				<input type="hidden" id="id" name="id">
				<div class="fitem">
					<label>:</label> <input id="id" name="id"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>发布人:</label> <input id="memberId" name="memberId"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>标题:</label> <input id="title" name="title"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>类型 1官方招募 2私人招募:</label> <input id="type" name="type"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>拍摄地:</label> <input id="cityCode" name="cityCode"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>发布人:</label> <input id="publishUser" name="publishUser"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>标签:</label> <input id="lableCode" name="lableCode"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>导演:</label> <input id="director" name="director"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>编剧:</label> <input id="screenwriter" name="screenwriter"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>拍摄开始时间:</label> <input id="startDate" name="startDate"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>拍摄结束时间:</label> <input id="endDate" name="endDate"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>试戏截止时间:</label> <input id="deadline" name="deadline"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>审核状态:</label> <input id="status" name="status"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>剧本大纲:</label> <input id="scriptOutline" name="scriptOutline"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>备注:</label> <input id="remark" name="remark"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>创建时间:</label> <input id="createDate" name="createDate"
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
