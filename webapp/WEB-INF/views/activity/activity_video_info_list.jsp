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
	function searchData() {
		$('#dg').datagrid('load', {
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
	function goBack(){
		location.href = basePath+"activity/activityInfoList.do";
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
	function formatComment(value, row){
		return '<a class="detailcls" href="javascript:void(0);" style="cursor: pointer;" onclick="doComment(\''
			+ row.id + '\')">'+ value +'</a>';
	}
	function doComment(id){
		$("#commentFrame").attr('src', "activity/activityVideoCommentList.do?videoInfoId=" + id);
		$('#dlg_comment').dialog({    
			modal:true,
			shadow:true				
		});
		$('#dlg_comment').dialog('open');
	}
	
	function formatStatus(value, row) {
		if (value==1) {
			return "是";
		} else {
			return "否";
		}
	}
	function formatOper(value, row) {
		if (row.isHot == 1) {
			return '<a class="oper" href="javascript:void(0);" style="cursor: pointer;" onclick="changeHot(\''
					+ row.id + '\',0)">'+ '取消热门' +'</a>';
		} else {
			return '<a class="oper" href="javascript:void(0);" style="cursor: pointer;" onclick="changeHot(\''
					+ row.id + '\',1)">'+ '设置热门 ' +'</a>';
		}
	}
	
	function changeHot(id,isHot) {
		$.post('activity/activityVideoInfoUpdateStatus.do', {
			id : id,
			isHot : isHot
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
	
	function formatView(value, row) {
		if(row.fileInfo != null){
			return '<a class="detailcls" href="javascript:void(0);" style="cursor: pointer;" onclick="viewVideo(\''
				+ row.fileInfo.urlPath + '\')">'+ '观看' +'</a>';
		}
	}
	
	function viewVideo(url){
		$("#videoFrame").attr('src', url);
		$('#dlg_video').dialog({    
			modal:true,
			shadow:true				
		});
		$('#dlg_video').dialog('open');
	}
</script>
</head>

<body>

	<div id="content" region="center" title="列表" style="padding:5px;width: 98%;height: 98%">
		<table id="dg" class="easyui-datagrid"
			style="" url="activity/activityVideoInfoAjaxPage.do?status=1&activityId=${activityId }"
			iconCls="icon-save" rownumbers="true" pagination="true" fit="true"
			singleSelect="true" toolbar="#toolbar" data-options="onLoadSuccess:function(data){ 
	         $('.detailcls').linkbutton(
	         	{ 
		         	plain:true, 
		         	iconCls:'icon-search' 
		         }
	         );
	         $('.oper').linkbutton(
	         	{ 
		         	plain:true, 
		         	iconCls:'icon-remove' 
		         }
	         );
  		}">
			<thead>
				<tr>
					<th field="videoId" align="center" width="100" formatter="formatView">视频</th>
					<th field="memberId" align="center" width="150" formatter="formatMember">上传用户</th>
					<th field="createDate" align="center" width="150">创建时间</th>
					<th field="viewNum" align="center" width="100">浏览量</th>
					<th field="shareNum" align="center" width="100">分享次数</th>
					<th field="likeNum" align="center" width="100">点赞数</th>
					<th field="commentNum" align="center" width="100" formatter="formatComment">评论数</th>
					<th field="isHot" align="center" width="100" formatter="formatStatus">是否热门</th>
					<th field="option" align="center" width="100" formatter="formatOper">操作</th>
				</tr>
			</thead>
		</table>
		<div id="toolbar">
			<div>
				<a href="javascript:void(0);" class="easyui-linkbutton" 
				iconCls="icon-back" plain="true" onclick="goBack()">返回</a>
				上传用户: <input id="memberName" class="easyui-textbox" style="width:180px">
				<a href="javaScript:void()" onclick="searchData()"
					class="easyui-linkbutton" iconCls="icon-search">搜索</a>
			</div>
			<div style="margin-bottom:5px">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-remove" plain="true"
					onclick="doDelete('activity/activityVideoInfoAjaxDelete.do')">删除</a>
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
		
		<div id="dlg_comment" class="easyui-dialog" title="评论列表"
		style="width:100%;height:100%;" data-options="closed:'true'"
		buttons="#dlg_comment-buttons">
		<iframe id="commentFrame" name="commentFrame" frameborder="0"
			height="100%" width="100%" align="center"></iframe>
		</div>
		<div id="dlg_comment-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="javascript:$('#dlg_comment').dialog('close');$('#dg').datagrid('reload');"
				style="width:90px">关闭</a>
		</div>
		
		<div id="dlg_video" class="easyui-dialog" title="视频"
		style="width:620px;height:450px;" data-options="closed:'true'"
		buttons="#dlg_video-buttons">
			<video style="width: 580px;height:400px" id="videoFrame" src="${entity.fileInfo.urlPath }" controls="controls"></video>
		</div>
	</div>
</body>
</html>
