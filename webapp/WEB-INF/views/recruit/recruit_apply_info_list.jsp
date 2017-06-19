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
	text-align: right;
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
			videoStatus : $('#videoStatus').combobox('getValue'),
			recruitName : $('#recruitName').val(),
			roleName : $('#roleName').val(),
			publishName : $('#publishName').val(),
			realname : $('#realname').val(),
			nickname : $('#nickname').val()
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
		}
		else{
			return;
		}
	}
	function formatPublisher(value, row) {
		if (row.roleInfo !=null && row.roleInfo.recruitInfo !=null && row.roleInfo.recruitInfo.memberInfo != null){
		var view = decodeURI(row.roleInfo.recruitInfo.memberInfo.nickname);
			return '<a class="detailcls" href="javascript:void(0);" style="cursor: pointer;" onclick="doDetail(\''
				+ row.roleInfo.recruitInfo.memberId + '\')">'+ view +'</a>';
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
			return "通过";
		} else if (value==2) {
			return "拒绝";
		}
	}
	function formatResume(value, row) {
		if (row.memberInfo !=null){
			var view = decodeURI(row.memberInfo.realName);
			return '<a class="detailcls" href="javascript:void(0);" style="cursor: pointer;" onclick="viewResume(\''
				+ row.resumeId + '\')">'+ view +'</a>';
		}else{
			return;
		}
	}
	function viewResume(id) {
		$("#resumeFrame").attr('src', "member/memberResumeTemplateById.do?id=" + id);
		$('#dlg_resume').dialog({    
			modal:true,
			shadow:true				
		});
		$('#dlg_resume').dialog('open');
	}
	
	function formatRecruit(value, row) {
		if (row.roleInfo !=null && row.roleInfo.recruitInfo !=null){
		var view = row.roleInfo.recruitInfo.title;
			return '<a class="detailcls" href="javascript:void(0);" style="cursor: pointer;" onclick="doRecruitInfo(\''
				+ row.roleInfo.recruitId + '\')">'+ view +'</a>';
		}
		else{
			return;
		}
	}
	function doRecruitInfo(id){
		var backUrl = "recruit/recruitApplyInfoList.do";
		location.href = basePath + "recruit/recruitRoleInfoList.do?type=2&recruitId="+id+"&backUrl="+backUrl;
	}
	function formatRole(value, row) {
		if (row.roleInfo !=null){
		var view = decodeURI(row.roleInfo.name);
			return '<a class="detailcls" href="javascript:void(0);" style="cursor: pointer;" onclick="viewRole(\''
				+ row.roleId + '\')">'+ view +'</a>';
		}
		else{
			return;
		}
	}
	function viewRole(id){
		var backUrl = "recruit/recruitApplyInfoList.do";
		location.href = basePath + "recruit/recruitRoleInfoById.do?type=-1&id="+id+"&backUrl="+backUrl;
	}
	function formatOper(value, row) {
		var urlPath = '';
		if(row.fileInfo != null){
			urlPath = row.fileInfo.urlPath;
		}
		return '<a class="detailcls" href="javascript:void(0);" style="cursor: pointer;" onclick="viewInfo(\''
			+ row.id + '\')">'+ '审核视频' +'</a>'
			+'<a class="detailplay" href="javascript:void(0);" style="cursor: pointer;" onclick="viewVideo(\''
			+ urlPath + '\')"></a>'
			+'<a href="javascript:void(0)" class="detailok"'
			+'iconCls="icon-ok" onclick="doUpdateStatus2(\'recruit/recruitApplyInfoUpdateStatus.do\',1,\''+row.id+'\')"'
			+'>通过</a>'
			+'<a href="javascript:void(0)" class="detailno"'
			+'iconCls="icon-cancel" onclick="doUpdateStatus2(\'recruit/recruitApplyInfoUpdateStatus.do\',2,\''+row.id+'\')"'
			+'>拒绝</a>';
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
	function viewInfo(id){
		location.href = basePath + "recruit/recruitApplyInfoById.do?id="+id;
	}
	function view(id, applyId) {
		$("#resumeFrame2").attr('src', "member/memberResumeTemplateById.do?id=" + id);
		$('#dlg_resume2').dialog({    
			modal:true,
			shadow:true
		});
		$('#id').val(id);
		$('#applyId').val(applyId);
		$('#dlg_resume2').dialog('open');
	}
	function doUpdateStatus2(url,status,id,applyId){
		$('#id').val(id);
		if(status == '1'){
			doUpdateStatus(url,status);
		}else if(status == '2'){
			doRefuse2();
		}
	}
	
	function doUpdateStatus(url,status) {
		if(status == '2' && $('#reply2').textbox('getValue') == ''){
			$.messager.show({
					title : '提示',
					msg : '请填写反馈！'
				});
				return;
		}
		$.post(url, {
			ids : $('#id').val(),
			videoStatus : status,
			reply : $('#reply2').textbox('getValue')
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
			$('#dlg_reply2').dialog('close');
			$('#reply2').textbox('setValue','');
			$('#replySelect2').combobox('setValue','');
		}, 'json');
	}
	function doUpdateStatusAll(status) {
		if(status == '2' && $('#reply').textbox('getValue') == ''){
			$.messager.show({
					title : '提示',
					msg : '请填写反馈！'
				});
				return;
		}
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
					$.post('recruit/recruitApplyInfoUpdateStatus.do', {
						ids : ids,
						videoStatus : status,
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
	
	function doRefuse() {
		$('#dlg_reply').dialog('open').dialog('setTitle', '回复');
	}
	function doRefuse2() {
		$('#dlg_reply2').dialog('open').dialog('setTitle', '回复');
	}
</script>
</head>

<body>

	<div id="content" region="center" title="列表" style="padding:5px;width: 98%;height: 98%">
		<table id="dg" class="easyui-datagrid"
			style="" url="recruit/recruitVideoAuditAjaxPage.do"
			iconCls="icon-save" rownumbers="true" pagination="true" fit="true" singleSelect="false" 
			singleSelect="true" toolbar="#toolbar" nowrap="false" data-options="onLoadSuccess:function(data){ 			 						
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
	          $('.detailno').linkbutton(
	         	{ 
		         	plain:true, 
		         	iconCls:'icon-no' 
		         }
	         );
	         $('.detailplay').linkbutton(
	         	{ 
		         	plain:true, 
		         	iconCls:'icon-play' 
		         }
	         );
	         $(this).datagrid('resize');
  		}">
			<thead>
				<tr>
					<th field="resumeId" align="center" width="8%" formatter="formatResume">真实姓名</th>
					<th field="memberId" align="center" width="10%" formatter="formatMember">用户名</th>
					<th field="recruit" align="center" width="10%" formatter="formatRecruit">剧组</th>
					<th field="role" align="center" width="10%" formatter="formatRole">角色</th>
					<th field="publisher" align="center" width="10%" formatter="formatPublisher">发布人</th>
					<th field="videoStatus" align="center" width="6%" formatter="formatStatus">视频审核</th>
					<th field="createDate" align="center" width="12%" sortable="true">投递时间</th>
					<th field="updateDate" align="center" width="12%" sortable="true">修改时间</th>
					<th field="oper" width="21%" nowrap="nowrap" formatter="formatOper">操作</th>
				</tr>
			</thead>
		</table>
		<div id="toolbar">
			<div>
				<table style="width: 80%;">
					<tr>
						<td class="ftitle">真实姓名:</td><td><input id="realname" class="easyui-textbox" style="width:120px"/></td>
						<td class="ftitle">用户名:</td><td><input id="nickname" class="easyui-textbox" style="width:120px"></td>
						<td class="ftitle">简历状态:</td>
						<td>
							<select class="easyui-combobox" id="status" name="status" style="width:120px;" data-options="panelHeight:'auto',editable:false,required:false">
								<option value="">全部</option>
								<option value="0">未审核</option>
								<option value="1">通过</option>
								<option value="2">拒绝</option>
							</select>
						</td>
						<td class="ftitle">视频状态:</td>
						<td>
							<select class="easyui-combobox" id="videoStatus" name="videoStatus" style="width:120px;" data-options="panelHeight:'auto',editable:false,required:false">
								<option value="">全部</option>
								<option value="0">未审核</option>
								<option value="1">通过</option>
								<option value="2">拒绝</option>
							</select>
						</td>
					</tr>
					
					<tr>
						<td class="ftitle">剧组名称:</td><td><input id="recruitName" class="easyui-textbox" style="width:120px"/></td>
						<td class="ftitle">投递角色:</td><td><input id="roleName" class="easyui-textbox" style="width:120px"/></td>
						<td class="ftitle">发布人:</td><td><input id="publishName" class="easyui-textbox" style="width:120px"/></td>
						<td><a href="javaScript:void()" onclick="searchData()" class="easyui-linkbutton" iconCls="icon-search">搜索</a></td>
						<td></td>
					</tr>
				</table>				
			</div>
			<div style="margin-bottom:5px">
				<security:act optCode="batchPass">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-ok" plain="true" onclick="doUpdateStatusAll('1')">批量通过</a>
				</security:act>
				<security:act optCode="batchRefuse">
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
		
		<div id="dlg_resume" class="easyui-dialog" title="简历详情"
		style="width:100%;height:100%;top:10px;" data-options="closed:'true'"
		buttons="#dlg_resume-buttons">
		<iframe id="resumeFrame" name="resumeFrame" frameborder="0"
			height="100%" width="100%" align="center"></iframe>
		</div>
		<div id="dlg_resume-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="javascript:$('#dlg_resume').dialog('close')"
				style="width:90px">关闭</a>
		</div>
				
		<div id="dlg_resume2" class="easyui-dialog" title="简历详情"
		style="width:100%;height:100%;top:10px;" data-options="closed:'true'"
		buttons="#dlg_resume2-buttons">
		<iframe id="resumeFrame2" name="resumeFrame2" frameborder="0"
			height="100%" width="100%" align="center"></iframe>
		</div>
		<div id="dlg_resume2-buttons">
			<input type="hidden" name="id" id="id">
			<input type="hidden" name="applyId" id="applyId">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-ok" onclick="doUpdateStatus('member/memberResumeApplyUpdateStatus.do',1)"
				style="width:90px">通过</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="doUpdateStatus('member/memberResumeApplyUpdateStatus.do',2)"
				style="width:90px">拒绝</a>
		</div>
	</div>

	<div id="dlg_reply" class="easyui-dialog"
			data-options="iconCls:'icon-save',resizable:true,modal:true"
			style="width:600px;height:400px;padding:10px 20px;top:10px;" closed="true"
			buttons="#dlg_reply-buttons">
			<form id="fm" name="fm" method="post" action="recruit/recruitInfoTempUpdateStatus.do"
				data-options="novalidate:true">
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
				iconCls="icon-ok" onclick="doUpdateStatusAll('2')"
				style="width:90px">发送</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="javascript:$('#dlg_reply').dialog('close')"
				style="width:90px">取消</a>
		</div>
		
		<div id="dlg_reply2" class="easyui-dialog"
			data-options="iconCls:'icon-save',resizable:true,modal:true"
			style="width:600px;height:400px;padding:10px 20px;top:10px;" closed="true"
			buttons="#dlg_reply-buttons2">
			<form id="fm" name="fm" method="post" action="recruit/recruitInfoTempUpdateStatus.do"
				data-options="novalidate:true">
				<div class="fitem">
					<label>选择:</label> <input class="easyui-combobox" id="replySelect2"
								name="replySelect2" style="width: 300px"
								data-options="url:'system/systemCommonReplyAjaxAll.do',
										method:'get',valueField:'id',textField:'content',panelHeight:'150',
										editable:false,required:false,
										onSelect:function(record){
											$('#reply2').textbox('setValue',record.content);
             						}">
				</div>
				<div class="fitem">
					<label>内容:</label> <input id="reply2" name="reply2"
								class="easyui-textbox"
								data-options="prompt:'回复内容',multiline:true,required:true,validType:'length[0,200]'"
								style="height:150px;width: 300px">
				</div>
			</form>
		</div>
		<div id="dlg_reply-buttons2">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-ok" onclick="doUpdateStatus('recruit/recruitApplyInfoUpdateStatus.do',2)"
				style="width:90px">发送</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="javascript:$('#dlg_reply2').dialog('close')"
				style="width:90px">取消</a>
		</div>
		
		<div id="dlg_video" class="easyui-dialog" title="视频"
		style="width:620px;height:500px;" data-options="closed:'true',onBeforeClose:function(){ 
	         	$('video').trigger('pause');
	         }"
		buttons="#dlg_video-buttons">
			<video style="width: 580px;height:400px" id="videoFrame" src="" controls="controls"></video>
		</div>
		<div id="dlg_video-buttons">
			<a href="system/fileDownload.do?url=" class="easyui-linkbutton" id="filesave"
				iconCls="icon-save"
				style="width:90px">下载</a>
		</div>
		
		
		<div id="dlg_refuse_reply" class="easyui-dialog"
			data-options="iconCls:'icon-save',resizable:true,modal:true"
			style="width:600px;height:400px;padding:10px 20px;top:10px;" closed="true"
			buttons="#dlg_refuse_reply-buttons">		
			<table id="refuse_pg" class="easyui-propertygrid" style="width:300px" 
				data-options="
					showGroup:false,
					scrollbarSize:0,
					columns: replyColumns"
			></table>
			<script>
				var replyColumns = [[
		    		{field:'name',title:'',width:150},
		   		    {field:'value',title:'',width:150}
		        ]];
       		</script>									
		</div>
		<div id="dlg_refuse_reply-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-ok" onclick="doUpdateStatus('recruit/recruitApplyInfoUpdateStatus.do',2)"
				style="width:90px">发送</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="javascript:$('#dlg_reply2').dialog('close')"
				style="width:90px">取消</a>
		</div>
</body>
</html>
