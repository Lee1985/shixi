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

<title>投递招募简历信息</title>

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
			status : $('#status').combobox('getValue'),
			videoStatus : $('#videoStatus').combobox('getValue'),
			realname : $('#realname').val(),
			nickname : $('#nickname').val(),
			identityType : $('#identityType').combobox('getValue')
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
		var view = row.roleInfo.recruitInfo.memberInfo.nickname;
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
			return "未提交";
		} else if (value==1) {
			return "新建审核中";
		} else if (value==2) {
			return "审核通过";
		} else if (value==3) {
			return "审核拒绝";
		}else if(value==4){
			return "修改审核中";
		}
	}	
	
	function formatResume(value, row) {
		value = decodeURI(value);
		if (row !=null){
			return '<a class="detailcls" href="javascript:void(0);" style="cursor: pointer;" onclick="viewResume(\''
				+ row.id + '\')">'+ value +'</a>';
		}
		else{
			return;
		}
	}
	
	function viewResume(id) {
		
		//memberResumeTemplateById
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
		var view = row.roleInfo.name;
			return '<a class="detailcls" href="javascript:void(0);" style="cursor: pointer;" onclick="viewRole(\''
				+ row.roleId + '\')">'+ view +'</a>';
		}
		else{
			return;
		}
	}
	function formatCompletion(value, row){
		return value + "%";
	}
	function formatType(value, row){		
		if(row.memberInfo.identityStatus=='1'){
			return '官方认证';
		}else{
			return '私人';
		}
	}
	
	function viewRole(id){
		var backUrl = "recruit/recruitApplyInfoList.do";
		location.href = basePath + "recruit/recruitRoleInfoById.do?type=-1&id="+id+"&backUrl="+backUrl;
	}
	function formatOper(value, row) {
		return '<a class="detailcls" href="javascript:void(0);" style="cursor: pointer;" onclick="view(\''
			+ row.id + '\' , \'' + row.id + '\')">'+ '审核简历' +'</a>'
			+'<a href="javascript:void(0)" class="detailok"'
			+'iconCls="icon-ok" onclick="doBatchAudit(\'member/memberResumeAudit.do\',\'1\',\'1\',\''+ row.id +'\')"'
			+'>通过</a>'
			+'<a href="javascript:void(0)" class="detailno"'
			+'iconCls="icon-cancel" onclick="doBatchAudit(\'\',\'1\',\'2\',\'' + row.id + '\')"'
			+'>拒绝</a>';
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
		$('#applyId').val(applyId);
		if(status == '1'){
			doUpdateStatus(url,status);
		}else if(status == '2'){
			doRefuse2();
		}
	}
	
	function doUpdateStatus(status) {
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
					var applyIds='';
			        for(var i=0;i<row.length;i++){  
			            ids=ids+row[i].resumeId+',';
			            applyIds=applyIds+row[i].id+',';
			            if(ids!=''){  
			                ids=ids.substring(0, ids.length); 
			            }
			            if(applyIds!=''){  
			                applyIds=applyIds.substring(0, applyIds.length); 
			            }
			        }  
					$.post('member/memberInfoUpdateEducation.do', {
						ids : ids,
						applyIds : applyIds,
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
			status:status,
			applyIds:$('#applyId').val(),
			reply:$('#reply2').textbox('getValue')
		}, function(result) {
			if (result.success) {
				$('#dg').datagrid('reload'); // reload the user data
				$.messager.show({ // show error message
					title : '提示',
					msg : result.msg
				});
				$('#dlg_resume2').dialog('close');
			} else {
				$.messager.show({ // show error message
					title : '提示',
					msg : result.msg
				});
				$('#dlg_resume2').dialog('close');
			}
			$('#dlg_reply2').dialog('close');
			$('#reply2').textbox('setValue','');
			$('#replySelect2').combobox('setValue','');
		}, 'json');
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
		<input type="hidden" name="id" id="ids">
		<input type="hidden" name="applyId" id="relationIds">
		<table id="dg" class="easyui-datagrid"
			style="" url="recruit/resumeTemplateInfoAjaxPage.do"
			iconCls="icon-save" rownumbers="true" pagination="true" fit="true" singleSelect="false" 
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
	          $('.detailno').linkbutton(
	         	{ 
		         	plain:true, 
		         	iconCls:'icon-no' 
		         }
	         );
  		}">
			<thead>
				<tr>
					<th field="realName" align="center" width="120" formatter="formatResume">真实姓名</th>
					<th field="memberId" align="center" width="120" formatter="formatMember">用户名</th>
					<th field="type" align="center" width="120" formatter="formatType">身份类型</th>
					<th field="completion" align="center" width="120" formatter="formatCompletion" sortable="true">简历完成度</th>
					<th field="status" align="center" width="120" formatter="formatStatus">简历状态</th>
					<th field="updateDate" align="center" width="150" sortable="true">修改时间</th>
					<th field="oper" align="center" width="200" formatter="formatOper">操作</th>
				</tr>
			</thead>
		</table>
		<div id="toolbar">
			<div>
				真实姓名: <input id="realname" class="easyui-textbox" style="width:120px">
				用户名: <input id="nickname" class="easyui-textbox" style="width:120px">
				简历状态: <select class="easyui-combobox" id="status" name="status"
					style="width:120px;"
					data-options="panelHeight:'auto',editable:false,required:false">
					<option value="">全部</option>
					<option value="0">未审核</option>
					<option value="1">通过</option>
					<option value="2">拒绝</option>
				</select>
				身份类型: <select class="easyui-combobox" id="identityType" name="identityType"
					style="width:120px;" data-options="panelHeight:'auto',editable:false,required:false">
					<option value="">全部</option>
					<option value="1">官方认证</option>
					<option value="2">私人</option>
				</select>
				<a href="javascript:void();" onclick="searchData()" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
				
				
			</div>
			<div style="margin-bottom:5px">
				<security:act optCode="batchPass">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-ok" plain="true" onclick="doBatchAudit('member/memberResumeAudit.do','1','1','')">批量通过</a>
				</security:act>
				<security:act optCode="batchRefuse">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-cancel" plain="true" onclick="doBatchAudit('','1','2','')">批量拒绝</a>
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
				iconCls="icon-cancel" onclick="doRefuse2()"
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
				iconCls="icon-ok" onclick="doUpdateStatus('2')"
				style="width:90px">发送</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="javascript:$('#dlg_reply').dialog('close')"
				style="width:90px">取消</a>
		</div>
				
		<div id="dlg_refuse_reply" class="easyui-dialog"
			data-options="iconCls:'icon-save',resizable:true,modal:true"
			style="width:auto;height:auto;padding:10px 20px;top:10px;" closed="true"
			buttons="#dlg_refuse_reply-buttons">
			<table id="refuse_pg" class="easyui-propertygrid" style="width:300px" 
				data-options="
					showGroup:false,
					scrollbarSize:0,
					showHeader:false,
					nowrap:false,
					columns: replyColumns,
					onClickRow: function (index,rowData) {
                    	$(this).propertygrid('unselectAll');
                    },
                    onEndEdit:function(rowIndex, rowData, changes){
                    	$(this).propertygrid('beginEdit',index);
                    }"
			></table>
			<script>
				var replyColumns = [[
		   		    {field:'value',title:'',width:150}
		        ]];
       		</script>									
		</div>
		<div id="dlg_refuse_reply-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-ok" onclick="doBatchRefuse('member/memberResumeAudit.do','2')"
				style="width:90px">发送</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="javascript:$('#dlg_refuse_reply').dialog('close')"
				style="width:90px">取消</a>
		</div>
</body>
</html>
