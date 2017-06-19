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

<title>活动视频</title>

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

	$(function(){
		$.ajax({
			url:"activity/activityInfoAjaxAll.do?sort=orderList&order=asc&status=0",
			type:'POST',
			dataType:'json',
			success:function(data){
				 var defaultVal = {'id':'','title':'全部'};
				 data.splice(0, 0, defaultVal);
				 $('#activityId').combobox('loadData',data);
			}
		});
	});

	function searchData() {
		$('#dg').datagrid('load', {
			memberName : $('#memberName').val(),
			activityId : $('#activityId').combobox('getValue'),
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
	
	function doUpdateStatus(id,status) {
		var msg="";
		if (status==1) {
			msg="通过";
		}
		if (status==2) {
			msg="拒绝";
		}
		$.messager.confirm('Confirm', '你确定要'+msg+'吗?', function(r) {
			if (r) {
				$.post('activity/activityVideoInfoUpdateStatus.do', {
					id : id,
					status:status
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
				}, 'json');
			}
		});
	}
	
	
	function formatMember(value, row) {
		if (row.memberInfo !=null){
		var view = decodeURI(row.memberInfo.nickname);
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
		if (value==1) {
			return "通过";
		} else if (value==2) {
			return "拒绝";
		} else if (value==0) {
			return "未审核";
		}
	}
	function formatOper(value, row) {
		return '<a class="detailok" href="javascript:void(0);" style="cursor: pointer;" onclick="doUpdateStatus(\''
				+ row.id + '\',1)">'+ '通过' +'</a>'
				+'<a class="detailerror" href="javascript:void(0);" style="cursor: pointer;" onclick="doUpdateStatus(\''
				+ row.id + '\',2)">'+ '拒绝' +'</a>';
	}
	
	
	function formatView(value, row) {
		if(row.fileInfo != null){
			return '<a class="detailcls" href="javascript:void(0);" style="cursor: pointer;" onclick="viewVideo(\''
				+ row.fileInfo.urlPath + '\')">'+ '观看' +'</a>';
		}
	}
	
	function viewVideo(url){
		$("#videoFrame").attr('src', url);
		$("#filesave").attr('href', 'system/fileDownload.do?url='+url);
		$('#dlg_video').dialog({    
			modal:true,
			shadow:true				
		});
		$('#dlg_video').dialog('open');
	}
	function formatActivity(value, row){
		return row.activityInfo.title;
	}
</script>
</head>

<body>

	<div id="content" region="center" title="列表" style="padding:5px;width: 98%;height: 98%">
		<table id="dg" class="easyui-datagrid"
			style="" url="activity/activityVideoInfoAjaxPage.do?sort=status"
			iconCls="icon-save" rownumbers="true" pagination="true" fit="true"
			singleSelect="true" toolbar="#toolbar" data-options="onLoadSuccess:function(data){ 
	         $('.detailcls').linkbutton(
	         	{ 
		         	plain:true, 
		         	iconCls:'icon-search' 
		         }
	         );
	         $('.detailok').linkbutton(
	         	{ 
		         	plain:true, 
		         	iconCls:'icon-ok' 
		         }
	         );
	         $('.detailerror').linkbutton(
	         	{ 
		         	plain:true, 
		         	iconCls:'icon-no' 
		         }
	         );
  		}">
			<thead>
				<tr>
					<th field="videoId" align="center" width="100" formatter="formatView">视频</th>
					<th field="activityId" align="center" width="150" formatter="formatActivity">活动</th>
					<th field="memberId" align="center" width="150" formatter="formatMember">上传用户</th>
					<th field="status" align="center" width="150" formatter="formatStatus">状态</th>
					<th field="createDate" align="center" width="150">创建时间</th>
					<th field="option" align="center" width="200" formatter="formatOper">操作</th>
				</tr>
			</thead>
		</table>
		<div id="toolbar">
			<div>
				活动: <input id="activityId" class="easyui-combobox" style="width:180px"
							name="activityId" data-options="panelHeight:'auto',editable:false,valueField:'id',textField:'title'" />
				上传用户: <input id="memberName" class="easyui-textbox" style="width:180px">
				类型： <select class="easyui-combobox" id="status"
								name="type" style="width:150px;"
								data-options="panelHeight:'auto',editable:false">
								<option value="">全部</option>
								<option value="0">未审核</option>
								<option value="1">通过</option>
								<option value="2">拒绝</option>
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
		
		<div id="dlg_video" class="easyui-dialog" title="视频"
		style="width:620px;height:500px;" data-options="closed:'true',onBeforeClose:function(){ 
	         	$('video').trigger('pause');
	         }"
		buttons="#dlg_video-buttons">
			<video style="width: 580px;height:400px" id="videoFrame" src="${entity.fileInfo.urlPath }" controls="controls"></video>
		</div>
		<div id="dlg_video-buttons">
			<a href="system/fileDownload.do?url=" class="easyui-linkbutton" id="filesave"
				iconCls="icon-save"
				style="width:90px">下载</a>
		</div>
	</div>
</body>
</html>
