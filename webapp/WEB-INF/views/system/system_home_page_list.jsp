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
	function searchData() {
		$('#dg').datagrid('load', {
			name : $('#name').val()
		});
	}
	function setUp() {
		var row = $('#dg').datagrid('getSelected');
		if (row) {
			$('#dlg').dialog('open').dialog('setTitle', '修改');
			$('#fm').form('clear');
			$('#fm').form('load', row);
			$("#sysImgs").attr("src", row.pictureInfo != null ? row.pictureInfo.urlPath : "");
			$('.drecruitId').hide();
			$('.dactivityId').hide();
			$('.dcompanyActivityId').hide();
			if(row.type == '1'){
				$('.dpic').hide();
			}else{
				$('.dpic').show();
			}
			if(row.type == '3'){
				$('.drecruitId').show();
			}
			if(row.type == '4'){
				$('.dactivityId').show();
			}
			if(row.type == '5'){
				$('.dcompanyActivityId').show();
			}
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
	function formatStatus(value, row) {
		if (value==1) {
			return "启用";
		} else {
			return "禁用";
		}
	}
	function formatHome(value, row) {
		if (row.type==1) {
			return value;
		} else if (row.type == 2) {
			return "";
		} else if (row.type == 3) {
			if (row.recruitInfo != null) {
				return row.recruitInfo.title;
			}
		} else if (row.type == 4) {
			if (row.activityInfo != null) {
				return row.activityInfo.title;
			}
		} else if (row.type == 5) {
			if (row.companyInternal != null) {
				return row.companyInternal.title;
			}  
		} else {
			return;
		}
	}
	
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
</script>
</head>

<body>

	<div id="content" region="center" title="列表" style="padding:5px;width: 98%;height: 98%">
		<table id="dg" class="easyui-datagrid"
			style="" url="system/systemHomePageAjaxPage.do"
			iconCls="icon-save" rownumbers="true" pagination="true" fit="true"
			singleSelect="true" toolbar="#toolbar">
			<thead>
				<tr>
					<th field="imgUuid" align="center" width="150" formatter="headPic">图片</th>
					<th field="title" align="center" width="120">位置</th>
					<th field="orderList" align="center" width="100">排序</th>
					<th field="status" align="center" width="100" formatter="formatStatus">状态</th>
					<th field="createDate" align="center" width="150">发布时间</th>
					<th field="homeStr" align="center" width="200" formatter="formatHome">首页显示</th>
				</tr>
			</thead>
		</table>
		<div id="toolbar">
			<div style="margin-bottom:5px">
				<security:act optCode="isYes">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-lock_open" plain="true"
						onclick="doUpdateStatus('system/systemHomePageUpdateStatus.do',1)">启用</a>
				</security:act>
				<security:act optCode="isNo">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-lock" plain="true"
						onclick="doUpdateStatus('system/systemHomePageUpdateStatus.do',0)">禁用</a>
				</security:act>
				<security:act optCode="orderBy">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-edit" plain="true"
					onclick="setUp('system/systemHomePageAjaxDelete.do')">设置</a>
				</security:act> 
			</div>
		</div>
		<div id="dlg" class="easyui-dialog"
			data-options="iconCls:'icon-save',resizable:true,modal:true"
			style="width:600px;height:400px;padding:10px 20px" closed="true"
			buttons="#dlg-buttons">
			<div class="ftitle">请完善以下信息！</div>
			<form id="fm" name="fm" method="post" action="system/systemHomePageAjaxSave.do"
				data-options="novalidate:true" enctype="multipart/form-data">
				<input type="hidden" id="id" name="id">
				<div class="fitem dpic">
					<label>图片(建议比例 2:1)</label> <img id="sysImgs" alt="" src=""
						style="width: 100px;height: 100px" onclick="sysImg.click()">
					<label>&nbsp;</label> <input type="file" id="sysImg"
						name="sysImg" style="width:200px;display: none"
						onChange="onChange(this)"
						data-options="prompt:'选择图片...',required:true">
				</div>
				<div class="fitem">
					<label>排序:</label> <input id="orderList" name="orderList"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem drecruitId">
					<label>招募:</label> <input class="easyui-combobox" id="recruitId"
								name="recruitId" style="width: 200px"
								data-options="url:'recruit/recruitInfoAjaxAll.do?status=1&isDelete=0',
										method:'get',valueField:'id',textField:'title',panelHeight:'150',
										editable:false,required:false">
				</div>
				<div class="fitem dactivityId">
					<label>活动:</label> <input class="easyui-combobox" id="activityId"
								name="activityId" style="width: 200px"
								data-options="url:'activity/activityInfoAjaxAll.do?status=1',
										method:'get',valueField:'id',textField:'title',panelHeight:'150',
										editable:false,required:false">
				</div>
				<div class="fitem dcompanyActivityId">
					<label>内部活动:</label> <input class="easyui-combobox" id="companyActivityId"
								name="companyActivityId" style="width: 200px"
								data-options="url:'activity/activityCompanyInternalAjaxAll.do',
										method:'get',valueField:'id',textField:'title',panelHeight:'150',
										editable:false,required:false">
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
