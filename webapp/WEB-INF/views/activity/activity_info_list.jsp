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

<title>活动信息</title>

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
			title : $('#title').val()
		});
	}
	function doAdd() {
		$('#dlg').dialog('open').dialog('setTitle', '新建');
		$('#fm').form('clear');
		$('#sysImgs').attr('src','images/add.jpg');
		$(".videoVideos").hide();
	}
	function doEdit() {
		var row = $('#dg').datagrid('getSelected');
		if (row) {
			$('#dlg').dialog('open').dialog('setTitle', '修改');
			$('#fm').form('clear');
			$('#fm').form('load', row);
			$("#sysImgs").attr("src", row.pictureInfo.urlPath);
			$(".videoVideos").show();
			$(".videoVideos").html('<video width="420" height="340" controls="controls"><source src="/i/movie.ogg" type="video/ogg"><source src="'+new Object(row["fileInfo"]).urlPath+'" type="video/mp4" id="videoVideos"></video>');
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
	function formatTitle(value, row){
		return '<a class="detailcls" href="javascript:void(0);" style="cursor: pointer;" onclick="doVideoList(\''
			+ row.id + '\')">'+ value +'</a>';
	}
	function doVideoList(id){
		location.href = basePath + "activity/activityVideoInfoList.do?activityId="+id;
	}
	function onChangeVideo(fileObj) {
		var r = isVideo(fileObj.value);
		if(!r){
			$.messager.show({
				title : '提示',
				msg : '视频格式不正确，请传.mp4格式的视频'
			});
			fileObj.value="";
			return ;
		}
	}
</script>
</head>

<body>

	<div id="content" region="center" title="列表" style="padding:5px;width: 98%;height: 98%">
		<table id="dg" class="easyui-datagrid"
			style="" url="activity/activityInfoAjaxPage.do"
			iconCls="icon-save" rownumbers="true" pagination="true" fit="true"
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
					<th field="imgUuid" align="center" width="200" formatter="headPic">图片</th>
					<th field="title" align="center" width="200" formatter="formatTitle">标题</th>
					<th field="orderList" align="center" width="200">排序</th>
					<th field="createDate" align="center" width="200">创建时间</th>
					<th field="status" align="center" width="200" formatter="formatStatus">状态</th>
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
						iconCls="icon-add" plain="true" onclick="doAdd()">新建</a>
				</security:act>
				<security:act optCode="edit">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-edit" plain="true" onclick="doEdit()">修改</a>
				</security:act> 
				<security:act optCode="delete">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-remove" plain="true"
					onclick="doDelete('activity/activityInfoAjaxDelete.do')">删除</a>
				</security:act> 
				<security:act optCode="isYes">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-lock_open" plain="true"
						onclick="doUpdateStatus('activity/activityInfoUpdateStatus.do',1)">启用</a>
				</security:act>
				<security:act optCode="isNo">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-lock" plain="true"
						onclick="doUpdateStatus('activity/activityInfoUpdateStatus.do',0)">禁用</a>
				</security:act>
			</div>
		</div>
		<div id="dlg" class="easyui-dialog"
			data-options="iconCls:'icon-save',resizable:true,modal:true"
			style="width:800px;height:400px;padding:10px 20px" closed="true"
			buttons="#dlg-buttons">
			<div class="ftitle">请完善以下信息！</div>
			<form id="fm" name="fm" method="post" action="activity/activityInfoAjaxSave.do"
				data-options="novalidate:true" enctype="multipart/form-data">
				<input type="hidden" id="id" name="id">
				<div class="fitem">
					<label>图片(建议比例 2:1)</label> <img id="sysImgs" alt="" src=""
						style="width: 100px;height: 100px" onclick="sysImg.click()">
					<label>&nbsp;</label> <input type="file" id="sysImg"
						name="sysImg" style="width:200px;display: none"
						onChange="onChange(this)"
						data-options="prompt:'选择图片...',required:true">
				</div>
				<div class="fitem">
					<label>标题:</label> <input id="title" name="title"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
				<label>视频:</label> 
					<input type="file" id="videoVideo"
						name="videoVideo" style="width:200px" onChange="onChangeVideo(this)"
						data-options="prompt:'选择文件...'">
					<input type="hidden" name="videoUuid" id="videouuid"> 
					<div style="margin-left: 60px;" class="videoVideos">
					</div>
				</div>
				<div class="fitem">
					<label>排序:</label> <input id="orderList" name="orderList"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>内容:</label> <input id="content" name="content"
								class="easyui-textbox"
								data-options="prompt:'标签描述',multiline:true,required:true,validType:'length[0,500]'"
								style="height:150px;width: 200">
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
