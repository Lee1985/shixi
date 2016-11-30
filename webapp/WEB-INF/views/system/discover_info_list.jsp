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

<title>首页管理</title>

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
	function doUpdateStatus(url,status) {
		var row = $('#dg').datagrid('getSelected');
		var msg="";
		if (row) {
			if (status==1) {
				msg="启用";
				if (row.status==1) {
					$.messager.show({ // show error message
						title : '提示',
						msg : '已启用！'
					});
					return;
				}
			}
			if (status==0) {
				msg="禁用";
				if (row.status==0) {
					$.messager.show({ // show error message
						title : '提示',
						msg : '已禁用！'
					});
					return;
				}
			}
			$.messager.confirm('Confirm', '你确定要'+msg+'吗?', function(r) {
				if (r) {
					$.post(url, {
						id : row.id,
						status:status
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
	
	function formatType(value,row){
		if (value == 1){
			return "广告";
		}else if(value == 2){
			return "招募推荐";
		}else if(value == 3){
			return "急聘信息";
		}else if(value == 4){
			return "彪戏活动";
		}else if(value == 5){
			return "公司内部活动";
		}
	}
	
	function formatStatus(value, row) {
		if (value==1) {
			return "启用";
		} else {
			return "禁用";
		}
	}
	
	function formatOperation(value,row){		
		if (row.type == 2){
			//招募推荐
			if(typeof(row.cover) == 'undefined' || row.cover == 'undefined'){
				row.cover = '';
			}			
			return '<a href="javascript:void(0);" onclick="toConfigCover(\''+row.id+'\',\''+row.cover+'\')">设置封面</a>';			
		}else if(row.type == 3){
			if(typeof(row.cover) == 'undefined' || row.cover == 'undefined'){
				row.cover = '';
			}
			console.log(row);
			return "<a href='javascript:void(0);' onclick='removeItem(\"" + row.id + "\")'>移除</a>&nbsp;&nbsp;&nbsp;&nbsp;" + 
				   '<a href="javascript:void(0);" onclick="toConfigCover(\''+row.id+'\',\''+row.cover+'\')">设置封面</a>';
		} else if(row.type == 5 || row.type == 4){
			return "<a href='javascript:void(0);' onclick='removeItem(\"" + row.id + "\")'>移除</a>";
		}
	}
	
	function formatCover(value,row){
		if(value != '' && typeof(value) != 'undefined'){
			return "<img src="+ value +" style=\"height:50px;background-color:#434343\"/>";	
		}	
		return '';
	}
	
	function addInnerActivity(){
		$('#innerActivity_dlg_frm').attr('src','system/configActivityCompanyInternalList.do');
		$('#innerActivity_dlg').dialog('open').dialog('setTitle', '设置公司内部活动');		
	}
	
	function configInnerActivity(){				
		 var ids = $("#innerActivity_dlg_frm")[0].contentWindow.getCheckValue();
		 if(ids == ''){
			 $.messager.show({title : '提示',msg : '请至少选择一个内部活动!'});
			 return;
		 }
		 var ajaxOptions = {
		    url: 'system/configActivityCompany.do',
		    type: 'post',
		    async: false,
		    data: 'ids=' + ids,
		    dataType: 'json',
		    success: function(data) {
		      if (data.success) {		        
		    	  //$.messager.show({title : '提示',msg : '设置内部活动成功!'});	
		    	  $('#innerActivity_dlg').dialog('close');
		    	  $('#dg').datagrid('reload');
		      } else {
		    	  $.messager.show({title : '提示',msg : data.msg});
		    	  return;
		      }
		    }
		  };
		  $.ajax(ajaxOptions);
	}
	
	function removeItem(id){
		$.messager.confirm('Confirm', '是否要移除该选项?', function(r) {
			var ajaxOptions = {
			    url: 'system/removeDiscoverItem.do',
			    type: 'post',
			    async: false,
			    data: 'id=' + id,
			    dataType: 'json',
			    success: function(data) {
			      if (data.success) {		        
			    	  $('#innerActivity_dlg').dialog('close');
			    	  $('#dg').datagrid('reload');
			      } else {
			    	  $.messager.show({title : '提示',msg : data.msg});
			    	  return;
			      }
			    }
			  };
			  $.ajax(ajaxOptions);
		});		
	}
	
	function addImportRecruit(){
		$('#importRecruit_dlg_frm').attr('src','system/configImportantRecruitList.do');
		$('#importRecruit_dlg').dialog('open').dialog('setTitle', '设置急聘信息');
	}
	
	function configImportantRecruit(){				
		 var ids = $("#importRecruit_dlg_frm")[0].contentWindow.getCheckValue();
		 if(ids == ''){
			 $.messager.show({title : '提示',msg : '请至少选择一个内部活动!'});
			 return;
		 }
		 var ajaxOptions = {
		    url: 'system/configImportantRecruit.do',
		    type: 'post',
		    async: false,
		    data: 'ids=' + ids,
		    dataType: 'json',
		    success: function(data) {
		      if (data.success) {		        
		    	  $('#importRecruit_dlg').dialog('close');
		    	  $('#dg').datagrid('reload');
		      } else {
		    	  $.messager.show({title : '提示',msg : data.msg});
		    	  return;
		      }
		    }
		  };
		  $.ajax(ajaxOptions);
	}
	
	function addActingActivity(){
		$('#actingActivity_dlg_frm').attr('src','system/configActingActivityList.do');
		$('#actingActivity_dlg').dialog('open').dialog('setTitle', '设置彪戏活动');
	}
	
	function configActingActivity(){
		 var ids = $("#actingActivity_dlg_frm")[0].contentWindow.getCheckValue();
		 if(ids == ''){
			 $.messager.show({title : '提示',msg : '请至少选择一个彪戏活动!'});
			 return;
		 }
		 var ajaxOptions = {
		    url: 'system/configActingActivity.do',
		    type: 'post',
		    async: false,
		    data: 'ids=' + ids,
		    dataType: 'json',
		    success: function(data) {
		      if (data.success) {		        
		    	  $('#actingActivity_dlg').dialog('close');
		    	  $('#dg').datagrid('reload');
		      } else {
		    	  $.messager.show({title : '提示',msg : data.msg});
		    	  return;
		      }
		    }
		  };
		  $.ajax(ajaxOptions);
	}
	
	function toConfigCover(id,cover){
		var row = {};
		row.id = id;
		row.cover = cover;
		if (row) {
			$('#dlg').dialog('open').dialog('setTitle', '设置封面');
			$('#fm').form('clear');
			$('#fm').form('load', row);
			$("#sysImgs").attr("src", row.cover != null ? row.cover : "");
		}
	}
	
	function onChange(fileObj) {
		var reader = new FileReader();
		reader.readAsDataURL(fileObj.files[0]);
		reader.onload = function(e) {
			$("#sysImgs").attr("src", e.target.result);
		};
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
</script>
</head>

<body>

	<div id="content" region="center" title="列表" style="padding:5px;width: 98%;height: 98%">
	
		<div id="toolbar">
			<div style="margin-bottom:5px">
				<security:act optCode="isYes">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-lock_open" plain="true"
						onclick="doUpdateStatus('system/discoverUpdateStatus.do',1)" >启用</a>
				</security:act>
				
				<security:act optCode="isNo">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-lock" plain="true"
						onclick="doUpdateStatus('system/discoverUpdateStatus.do',0)">禁用</a>
				</security:act>
				
				<security:act optCode="addCompanyInternalActivity">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"
						onclick="addInnerActivity()">添加内部活动</a>
				</security:act>
				
				<security:act optCode="addImportantRecruit">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addImportRecruit()">添加急聘</a>
				</security:act>
				
				<security:act optCode="addActingActivity">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addActingActivity()">添加彪戏活动</a>
				</security:act>								
			</div>
		</div>
	
		<table id="dg" class="easyui-datagrid"
			style="" url="system/discoverInfoAjaxPage.do"
			iconCls="icon-save" rownumbers="true" pagination="true" fit="true"
			singleSelect="true" toolbar="#toolbar">
			<thead>
				<tr>
					<th field="cover" align="center" width="150" formatter="formatCover">封面</th>
					<th field="type" align="center" width="120" formatter="formatType">类型</th>
					<th field="orderList" align="center" width="100">排序</th>
					<th field="status" align="center" width="100" formatter="formatStatus">状态</th>
					<th field="createTimeStr" align="center" width="150">创建时间</th>
					<th field="operation" align="center" width="150" formatter="formatOperation">操作</th>		
				</tr>
			</thead>
		</table>
		
		<div id="innerActivity_dlg" class="easyui-dialog" data-options="iconCls:'icon-save',resizable:true,modal:true" style="width:70%;height:95%;" closed="true" buttons="#innerActivity_dlg-buttons">
			<iframe id="innerActivity_dlg_frm" name="innerActivity_dlg_frm" frameborder="0" height="100%" width="100%" align="center"></iframe>		
		</div>
		<div id="innerActivity_dlg-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton c6"
				iconCls="icon-ok" onclick="configInnerActivity()" style="width:90px">确定</a> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="javascript:$('#innerActivity_dlg').dialog('close')"
				style="width:90px">取消</a>
		</div>
		
		<div id="importRecruit_dlg" class="easyui-dialog" data-options="iconCls:'icon-save',resizable:true,modal:true" style="width:70%;height:95%;" closed="true" buttons="#importRecruit_dlg-buttons">
			<iframe id="importRecruit_dlg_frm" name="importRecruit_dlg_frm" frameborder="0" height="100%" width="100%" align="center"></iframe>		
		</div>
		<div id="importRecruit_dlg-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton c6"
				iconCls="icon-ok" onclick="configImportantRecruit()" style="width:90px">确定</a> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="javascript:$('#importRecruit_dlg').dialog('close')"
				style="width:90px">取消</a>
		</div>
						
		<div id="actingActivity_dlg" class="easyui-dialog" data-options="iconCls:'icon-save',resizable:true,modal:true" style="width:70%;height:95%;" closed="true" buttons="#actingActivity_dlg-buttons">
			<iframe id="actingActivity_dlg_frm" name="actingActivity_dlg_frm" frameborder="0" height="100%" width="100%" align="center"></iframe>		
		</div>
		<div id="actingActivity_dlg-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton c6"
				iconCls="icon-ok" onclick="configActingActivity()" style="width:90px">确定</a> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="javascript:$('#actingActivity_dlg').dialog('close')"
				style="width:90px">取消</a>
		</div>
		
		<div id="dlg" class="easyui-dialog"
			data-options="iconCls:'icon-save',resizable:true,modal:true"
			style="width:400px;height:400px;padding:10px 20px" closed="true"
			buttons="#dlg-buttons">
			<div class="ftitle">请完善以下信息！</div>
			<form id="fm" name="fm" method="post" action="system/configCover.do" data-options="novalidate:true" enctype="multipart/form-data">
				<input type="hidden" id="id" name="id">
				<div class="fitem dpic">
					<label>图片(2:1)</label> <img id="sysImgs" alt="" src="" style="width: 200px;height: 100px" onclick="sysImg.click()">
					<label>&nbsp;</label>
					<input type="file" id="sysImg" name="sysImg" style="width:200px;display: none" onChange="onChange(this)" data-options="prompt:'选择图片...',required:true">
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
