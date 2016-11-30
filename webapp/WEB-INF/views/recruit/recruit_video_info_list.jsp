<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="security" uri="http://www.bluemobi.com"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">

<title>投递招募信息</title>

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
			recruitName : $('#recruitName').val(),
			roleName : $('#roleName').val(),
			publishName : $('#publishName').val()
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
	function formatRecruit(value, row) {
		if (row.roleInfo !=null && row.roleInfo.recruitInfo !=null){
			return decodeURI(row.roleInfo.recruitInfo.title);
		} else{
			return;
		}
	}
	function formatRole(value, row) {
		if (row.roleInfo !=null){
			return decodeURI(row.roleInfo.name);
		} else{
			return;
		}
	}
	function formatPublisher(value, row) {
		if (row.roleInfo !=null && row.roleInfo.recruitInfo !=null && row.roleInfo.recruitInfo.memberInfo != null){
			return decodeURI(row.roleInfo.recruitInfo.memberInfo.nickname);
		} else{
			return;
		}
	}
	function formatOper(value, row) {
		var str = '';
			str = str+'<a class="detailcls" href="javascript:void(0);" style="cursor: pointer;" onclick="viewVideo(\''
			+ row.id + '\')">'+ '查看视频' +'</a>';
// 		}
		return str;
	}
	function viewVideo(id){
		$("#videoFrame").attr('src', 'recruit/recruitApplyInfoViewById.do?id='+id);
		$('#dlg_video').dialog({    
			modal:true,
			shadow:true				
		});
		$('#dlg_video').dialog('open');
	}
	
	function formatLevel(value, row){
		if(value == '1'){
			return '简历审核中';
		}else if(value == '2'){
			return '简历投递成功';
		}else if(value == '3'){
			return '剧组已查看简历';
		}else if(value == '4'){
			return '剧组关注了你';
		}else if(value == '5'){
			return '剧组联系了你';
		}else{
			return '';
		}
	}
	
</script>
</head>

<body>

	<div id="content" region="center" title="列表" style="padding:5px;">
		<table id="dg" class="easyui-datagrid"
			style="width:98%;min-height:400px" url="recruit/recruitVideoInfoAjaxPage.do?memberId=${memberId }"
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
					<th field="recruit" align="center" width="150" formatter="formatRecruit">剧组</th>
					<th field="role" align="center" width="120" formatter="formatRole">角色</th>
					<th field="role" align="center" width="120" formatter="formatRole">片酬</th>
					<th field="publisher" align="center" width="120" formatter="formatPublisher">发布人</th>
					<th field="createDate" align="center" width="150">发布时间</th>
					<th field="level" align="center" width="120" formatter="formatLevel">进度</th>
					<th field="oper" align="center" width="200" formatter="formatOper">操作</th>
				</tr>
			</thead>
		</table>
		<div id="toolbar">
			<div>
				剧组名称: <input id="recruitName" class="easyui-textbox" style="width:180px">
				投递角色: <input id="roleName" class="easyui-textbox" style="width:120px">
				发布人: <input id="publishName" class="easyui-textbox" style="width:120px">
				<a href="javaScript:void()" onclick="searchData()"
					class="easyui-linkbutton" iconCls="icon-search">搜索</a>
			</div>
		</div>
		
		<div id="dlg_video" class="easyui-dialog" title="试戏视频"
		style="width:100%;height:100%;top:10px;" data-options="closed:'true',onBeforeClose:function(){ 
	         	$('#videoFrame').attr('src','');
	         }"
		buttons="#dlg_video-buttons">
		<iframe id="videoFrame" name="videoFrame" frameborder="0"
			height="100%" width="100%" align="center"></iframe>
		</div>
		<div id="dlg_video-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="javascript:$('#dlg_video').dialog('close')"
				style="width:90px">关闭</a>
		</div>
	</div>

</body>
</html>
