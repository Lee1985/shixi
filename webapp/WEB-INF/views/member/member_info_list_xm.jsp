
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

<title>用户管理</title>

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
<script src="js/jquery.fs.boxer.js" type="text/javascript" charset="utf-8"></script>
<link rel="stylesheet" type="text/css" href="css/jquery.fs.boxer.css"/>
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
#boxer{
	z-index:10001 !important;
}
#boxer-overlay{
	z-index:10000 !important;
}
</style>

<script type="text/javascript">
	function searchData() {
		$('#dg').datagrid('load', {
			nickname : $('#nickname').val(),
			realName : $('#realName').val(),
			mobile : $('#mobile').val(),
			realNameStatus:$('#realNameStatus').combobox('getValue')
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
	function doEditIdentity(id,identityInfo) {
// 		var row = $('#dg').datagrid('getSelected');
// 		if (row) {
			$('#dlgIdentity').dialog('open').dialog('setTitle', '身份认证');
			$('#identityInfoview').textbox('setValue',identityInfo);
			$('#id').val(id);
// 		}
	}
	function doUpdateIdentity(url,status) {
		if(status == '2' && $('#reply2').textbox('getValue') == ''){
			$.messager.show({
					title : '提示',
					msg : '请填写反馈！'
				});
				return;
		}
		$.post(url, {
			ids : $('#id').val(),
			identityInfo : $('#identityInfoview').val(),
			identityStatus:status,
			reply : $('#reply2').textbox('getValue')
		}, function(result) {
			if (result.success) {
				$('#dlgIdentity').dialog('close');
				$('#dg').datagrid('reload'); // reload the user data
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
	function doEditRealName(id,nickname,realName,urlPathA,urlPathB) {
// 		var row = $('#dg').datagrid('getSelected');
// 		$('#identityInfo').val(row.identityInfo);
// 		if (row) {
			$('#dlgRealName').dialog('open').dialog('setTitle', '实名认证');
			$('#idCardA').attr('src',urlPathA);
			$('#idCardB').attr('src',urlPathB);
			$('#idCardAboxer').attr('href',urlPathA);
			$('#idCardBboxer').attr('href',urlPathB);
			$('#id').val(id);
			$('#nicknameR').html(nickname);
			$('#realNameR').html(realName);
			
// 		}
	}
	function doUpdateRealName(url,status) {
		if(status == '2' && $('#reply2').textbox('getValue') == ''){
			$.messager.show({
					title : '提示',
					msg : '请填写反馈！'
				});
				return;
		}
		$.post(url, {
			ids : $('#id').val(),
			realNameStatus : status,
			reply : $('#reply2').textbox('getValue')
		}, function(result) {
			if (result.success) {
				$('#dlgRealName').dialog('close');
				$('#dg').datagrid('reload'); // reload the user data
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
	function doEditEducation(id,realName,urlPathdegreep,urlPathdiploma) {
// 		var row = $('#dg').datagrid('getSelected');
// 		if (row) {
			$('#dlgEducation').dialog('open').dialog('setTitle', '学历认证');
			$('#degreeImg').attr('src',urlPathdegreep);
			$('#diplomaImg').attr('src',urlPathdiploma);
			$('#id').val(id);
			$('#realNameE').html(realName);
// 		}
	}
	function doUpdateEducation(url,status) {
		$.post(url, {
			ids : $('#id').val(),
			educationStatus : status
		}, function(result) {
			if (result.success) {
				$('#dlgEducation').dialog('close');
				$('#dg').datagrid('reload'); // reload the user data
			} else {
				$.messager.show({ // show error message
					title : '提示',
					msg : result.msg
				});
			}
		}, 'json');
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
	function formatIdentityStatus(value, row) {
	var identityInfo = "";
	if(row.identityInfo){
		identityInfo = row.identityInfo;
	}
		if (value==1) {
			return "<a href=\"javascript:void(0)\" class=\"detailok\" onclick=\"doEditIdentity('"+row.id+"','"+identityInfo+"')\">通过</a>";
		} else if (value==2) {
			return "<a href=\"javascript:void(0)\" class=\"detailerror\" onclick=\"doEditIdentity('"+row.id+"','"+identityInfo+"')\">拒绝</a>";
		} else {
			return "<a href=\"javascript:void(0)\" class=\"detailcls\" onclick=\"doEditIdentity('"+row.id+"','"+identityInfo+"')\">审核中</a>";
		}
	}
	function formatRealNameStatus(value, row) {
		if (value==1) {
			return "<a href=\"javascript:void(0)\" class=\"detailok\" onclick=\"doEditRealName('"+row.id+"','"+row.nickname+"','"+row.realName+"','"+row.idCardpictureInfoA.urlPath+"','"+row.idCardpictureInfoB.urlPath+"')\">通过</a>";
		} else if (value==2) {
			return "<a href=\"javascript:void(0)\" class=\"detailerror\" onclick=\"doEditRealName('"+row.id+"','"+row.nickname+"','"+row.realName+"','"+row.idCardpictureInfoA.urlPath+"','"+row.idCardpictureInfoB.urlPath+"')\">拒绝</a>";
		} else if (value==3) {
			return "<a href=\"javascript:void(0)\" class=\"detailcls\" onclick=\"doEditRealName('"+row.id+"','"+row.nickname+"','"+row.realName+"','"+row.idCardpictureInfoA.urlPath+"','"+row.idCardpictureInfoB.urlPath+"')\">审核中</a>";
		} else {
			return "未提交";
		}
	}
	function formatEducationStatus(value, row) {
		if (value==1) {
			return "<a href=\"javascript:void(0)\" class=\"detailok\" onclick=\"doEditEducation('"+row.id+"','"+row.realName+"','"+row.degreepictureInfo.urlPath+"','"+row.diplomapictureInfo.urlPath+"')\">通过</a>";
		} else if (value==2) {
			return "<a href=\"javascript:void(0)\" class=\"detailerror\" onclick=\"doEditEducation('"+row.id+"','"+row.realName+"','"+row.degreepictureInfo.urlPath+"','"+row.diplomapictureInfo.urlPath+"')\">拒绝</a>";
		} else if (value==3) {
			return "<a href=\"javascript:void(0)\" class=\"detailcls\" onclick=\"doEditEducation('"+row.id+"','"+row.realName+"','"+row.degreepictureInfo.urlPath+"','"+row.diplomapictureInfo.urlPath+"')\">待审核</a>";
		} else {
			return "未提交";
		}
	}
	function formatStatusBlack(value, row) {
		if (value==0) {
			return "是";
		} else {
			return "否";
		}
	}
	function formatSex(value, row) {
		if (value=='1') {
			return "男";
		} else {
			return "女";
		}
	}
	
	function doUpdateStatus(url,status) {
		var row = $('#dg').datagrid('getSelected');
		$.post(url, {
			id : row.id,
			status : status
		}, function(result) {
			if (result.success) {
				$('#dlgRealName').dialog('close');
				$('#dg').datagrid('reload'); // reload the user data
			} else {
				$.messager.show({ // show error message
					title : '提示',
					msg : result.msg
				});
			}
		}, 'json');
	}
	function doUpdateStatus(realNameStatus) {
		if(realNameStatus == '2' && $('#reply').textbox('getValue') == ''){
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
					$.post('member/memberInfoUpdateRealName.do', {
						ids : ids,
						realNameStatus : realNameStatus,
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
	function formatContent(value,row){
		if(value != null){
			return decodeURI(value);	
		}
		return "";
	}
</script>
</head>

<body>

	<div id="content" region="center" title="列表" style="padding:5px;width: 98%;height: 98%">
		<table id="dg" class="easyui-datagrid"
			style="" url="member/memberInfoAjaxPage.do?type=2"
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
	         $('.detailerror').linkbutton(
	         	{ 
		         	plain:true, 
		         	iconCls:'icon-no' 
		         }
	         );
  		}">
			<thead>
				<tr>
					<th field="imgUuid" align="center" width="100" formatter="headPic">头像</th>
					<th field="nickname" align="center" width="100" formatter="formatContent">昵称</th>
					<th field="realName" align="center" width="100" formatter="formatContent">真实姓名</th>
					<th field="sex" align="center" width="100" formatter="formatSex">性别</th>
					<th field="realNameStatus" align="center" width="100" formatter="formatRealNameStatus">实名认证状态</th>
					<th field="createDate" align="center" width="200">创建时间</th>
				</tr>
			</thead>
		</table>
		<div id="toolbar">
			<div>
				昵称: <input id="nickname" class="easyui-textbox" style="width:180px">
				真实姓名: <input id="realName" class="easyui-textbox" style="width:180px">
				手机: <input id="mobile" class="easyui-textbox" style="width:180px">
				状态:<select class="easyui-combobox" id="realNameStatus" name="realNameStatus"
						style="width:120px;"
						data-options="panelHeight:'auto',editable:false,required:false">
						<option value="99">全部</option>
						<option value="0">未提交</option>
						<option value="3">审核中</option>
						<option value="1">通过</option>
						<option value="2">拒绝</option>
					</select>
				
				<a href="javaScript:void()" onclick="searchData()"
					class="easyui-linkbutton" iconCls="icon-search">搜索</a>
			</div>
			<div style="margin-bottom:5px">
				<security:act optCode="batchPass">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-ok" plain="true" onclick="doUpdateStatus('1')">批量通过</a>
				</security:act>
				<security:act optCode="batchRefuse">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-cancel" plain="true" onclick="doRefuse()">批量拒绝</a>
				</security:act> 
			</div>
		</div>
		<div id="dlg" class="easyui-dialog"
			data-options="iconCls:'icon-save',resizable:true,modal:true"
			style="width:600px;height:400px;top:30px;padding:10px 20px" closed="true"
			buttons="#dlg-buttons">
			<form id="fm" name="fm" method="post" action="member/memberInfoAjaxSave.do"
				data-options="novalidate:true">
				<input type="hidden" id="id" name="id">
				<div class="fitem">
					<label>头像:</label> <img id="imgUuid" alt=""
							src="" style="width: 150px;height: 100px">
				</div>
			</form>
		</div>
		<div id="dlg-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton c6"
				iconCls="icon-ok" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">确定</a> 
		</div>
		
		
		
		
		<div id="dlgIdentity" class="easyui-dialog"
			data-options="iconCls:'icon-save',resizable:true,modal:true"
			style="width:100%;height:100%;top:10px;padding:10px 20px" closed="true"
			buttons="#dlgIdentity-buttons">
			<div class="ftitle">身份认证</div>
				<div class="fitem">
					<label>身份信息:</label> <input id="identityInfoview" name="identityInfo"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
		</div>
		<div id="dlgIdentity-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton c6"
				iconCls="icon-ok" onclick="doUpdateIdentity('member/memberInfoUpdateIdentity.do','1')" style="width:90px">通过</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton c6"
				iconCls="icon-no" onclick="doRefuse2()" style="width:90px">拒绝</a> 
		</div>
		
		<div id="dlgRealName" class="easyui-dialog"
			data-options="iconCls:'icon-save',resizable:true,modal:true"
			style="width:100%;height:100%;top:10px;padding:10px 20px" closed="true"
			buttons="#dlgRealName-buttons">
			<div class="ftitle">实名认证</div>
				<div class="fitem">
					<label>昵称:</label> <span id="nicknameR"></span>
				</div>
				<div class="fitem">
					<label>真实姓名:</label> <span id="realNameR"></span>
				</div>
				<div class="fitem">
					<label>身份证正面:</label> <a id="idCardAboxer" title="身份证正面" rel="gallery" class="boxer" href=""><img id="idCardA" alt=""
							src="" style="width: 150px;height: 100px"></a>
				</div>
				<div class="fitem">
					<label>身份证反面:</label> <a id="idCardBboxer" title="身份证反面" rel="gallery" class="boxer" href=""><img id="idCardB" alt=""
							src="" style="width: 150px;height: 100px"></a>
				</div>
				<script>
						$(function(){
							$('.boxer').boxer();
						});
			</script>
		</div>
		<div id="dlgRealName-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton c6"
				iconCls="icon-ok" onclick="doUpdateRealName('member/memberInfoUpdateRealName.do','1')" style="width:90px">通过</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton c6"
				iconCls="icon-no" onclick="doRefuse2()" style="width:90px">拒绝</a> 
		</div>
		
		<div id="dlgEducation" class="easyui-dialog"
			data-options="iconCls:'icon-save',resizable:true,modal:true"
			style="width:100%;height:100%;top:10px;padding:10px 20px" closed="true"
			buttons="#dlgEducation-buttons">
			<div class="ftitle">学历认证</div>
				<div class="fitem">
					<label>真实姓名:</label> <span id="realNameE"></span>
				</div>
				<div class="fitem">
					<label>学位证:</label> <img id="degreeImg" alt=""
							src="" style="width: 150px;height: 100px">
				</div>
				<div class="fitem">
					<label>毕业证照片:</label> <img id="diplomaImg" alt=""
							src="" style="width: 150px;height: 100px">
				</div>
		</div>
		<div id="dlgEducation-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton c6"
				iconCls="icon-ok" onclick="doUpdateEducation('member/memberInfoUpdateEducation.do','1')" style="width:90px">通过</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton c6"
				iconCls="icon-no" onclick="doUpdateEducation('member/memberInfoUpdateEducation.do','2')" style="width:90px">拒绝</a> 
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
				iconCls="icon-ok" onclick="doUpdateRealName('member/memberInfoUpdateRealName.do','2')"
				style="width:90px">发送</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="javascript:$('#dlg_reply2').dialog('close')"
				style="width:90px">取消</a>
		</div>
		
</body>
</html>
