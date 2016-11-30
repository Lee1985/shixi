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
var flag = 0;
	function searchData() {
		$('#dg').datagrid('load', {
			nickname : $('#nickname').val(),
			realName : $('#realName').val(),
			mobile : $('#mobile').val()
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
	
	function doEditVipIdentity(id,vipInfo){
		var msg = "";
		var status = "";
		if(vipInfo == "0"){
			status = "1";
			msg = "确定要将该用户认证为会员吗?";
		}else{
			status = "0";
			msg = "确定要将该用户的会员认证取消吗?";
		}
		$.messager.confirm("操作提示", msg, function (data) {
			if(data){
				var url = "member/memberInfoUpdateVip.do";
				$.post(url, {
					ids : id,
					vip:status
				}, function(result) {
					if (result.success) {
						$('#dg').datagrid('reload'); // reload the user data
					} else {
						$.messager.show({
							title : '提示',
							msg : result.msg
						});
					}
				}, 'json');
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
		$.post(url, {
			ids : $('#id').val(),
			identityInfo : $('#identityInfoview').val(),
			identityStatus:status,
			reply:$('#reply').textbox('getValue')
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
			$('#dlg_reply').dialog('close');
			$('#reply').textbox('setValue','');
			$('#replySelect').combobox('setValue','');
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
		$.post(url, {
			ids : $('#id').val(),
			realNameStatus : status,
			reply:$('#reply').textbox('getValue')
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
			$('#dlg_reply').dialog('close');
			$('#reply').textbox('setValue','');
			$('#replySelect').combobox('setValue','');
		}, 'json');
	}
	function doEditEducation(id,realName,urlPathdegreep,urlPathdiploma,school) {
// 		var row = $('#dg').datagrid('getSelected');
// 		if (row) {
			$('#dlgEducation').dialog('open').dialog('setTitle', '学历认证');
			$('#degreeImg').attr('src',urlPathdegreep);
			$('#diplomaImg').attr('src',urlPathdiploma);
			$('#degreeImgboxer').attr('href',urlPathdegreep);
			$('#diplomaImgboxer').attr('href',urlPathdiploma);
			$('#id').val(id);
			$('#realNameE').html(realName);
			$('#school').html(school);
// 		}
	}
	function doUpdateEducation(url,status) {
		$.post(url, {
			ids : $('#id').val(),
			educationStatus : status,
			reply:$('#reply').textbox('getValue')
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
			$('#dlg_reply').dialog('close');
			$('#reply').textbox('setValue','');
			$('#replySelect').combobox('setValue','');
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
			return "<a href=\"javascript:void(0)\" class=\"detailcls\" onclick=\"doEditIdentity('"+row.id+"','"+identityInfo+"')\">待审核</a>";
		}
	}
	function formatRealNameStatus(value, row) {
		if (value==1) {
			return "<a href=\"javascript:void(0)\" class=\"detailok\" onclick=\"doEditRealName('"+row.id+"','"+row.nickname+"','"+row.realName+"','"+row.idCardpictureInfoA.urlPath+"','"+row.idCardpictureInfoB.urlPath+"')\">通过</a>";
		} else if (value==2) {
			return "<a href=\"javascript:void(0)\" class=\"detailerror\" onclick=\"doEditRealName('"+row.id+"','"+row.nickname+"','"+row.realName+"','"+row.idCardpictureInfoA.urlPath+"','"+row.idCardpictureInfoB.urlPath+"')\">拒绝</a>";
		} else if (value==3) {
			return "<a href=\"javascript:void(0)\" class=\"detailcls\" onclick=\"doEditRealName('"+row.id+"','"+row.nickname+"','"+row.realName+"','"+row.idCardpictureInfoA.urlPath+"','"+row.idCardpictureInfoB.urlPath+"')\">待审核</a>";
		} else {
			return "未提交";
		}
	}
	function formatEducationStatus(value, row) {
		if (value==1) {
			return "<a href=\"javascript:void(0)\" class=\"detailok\" onclick=\"doEditEducation('"+row.id+"','"+row.realName+"','"+row.degreepictureInfo.urlPath+"','"+row.diplomapictureInfo.urlPath+"','"+row.school+"')\">通过</a>";
		} else if (value==2) {
			return "<a href=\"javascript:void(0)\" class=\"detailerror\" onclick=\"doEditEducation('"+row.id+"','"+row.realName+"','"+row.degreepictureInfo.urlPath+"','"+row.diplomapictureInfo.urlPath+"','"+row.school+"')\">拒绝</a>";
		} else if (value==3) {
			return "<a href=\"javascript:void(0)\" class=\"detailcls\" onclick=\"doEditEducation('"+row.id+"','"+row.realName+"','"+row.degreepictureInfo.urlPath+"','"+row.diplomapictureInfo.urlPath+"','"+row.school+"')\">待审核</a>";
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
	function formatVipStatus(value,row){
		var vip = "";
		if(row.vip){
			vip = row.vip;
		}
		if (value==1) {
			return "<a href=\"javascript:void(0)\" class=\"detailok\" onclick=\"doEditVipIdentity('"+row.id+"','"+vip+"')\">已认证</a>";
		} else {
			return "<a href=\"javascript:void(0)\" class=\"detailerror\" onclick=\"doEditVipIdentity('"+row.id+"','"+vip+"')\">未认证</a>";
		}
	}
	function doDetail() {
		var row = $('#dg').datagrid('getSelected');
		$("#detailFrame").attr('src', "member/memberInfoById.do?id=" + row.id);
		$('#dlg_detail').dialog({    
			modal:true,
			shadow:true				
		});
		$('#dlg_detail').dialog('open');
	}
	
	function viewResume() {
		var row = $('#dg').datagrid('getSelected');
		if(row){
			$("#resumeFrame").attr('src', "member/memberResumeInfoByMemberId.do?memberId=" + row.id);
			$('#dlg_resume').dialog({    
				modal:true,
				shadow:true				
			});
			$('#dlg_resume').dialog('open');
		}
	}
	function viewRecord(){
		var row = $('#dg').datagrid('getSelected');
		if(row){
			$("#recordFrame").attr('src', "recruit/recruitVideoInfoList.do?memberId=" + row.id);
			$('#dlg_record').dialog({    
				modal:true,
				shadow:true				
			});
			$('#dlg_record').dialog('open');
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
	
	function doRefuse(flags) {
		$('#dlg_reply').dialog('open').dialog('setTitle', '回复');
		flag = flags;
	}
	
	function doSend(){
		if($('#reply').textbox('getValue') != '' && $('#reply').textbox('getValue') != null){
			if(flag == 1){
				doUpdateIdentity('member/memberInfoUpdateIdentity.do','2');
			}else if(flag == 2){
				doUpdateRealName('member/memberInfoUpdateRealName.do','2');
			}else if(flag == 3){
				doUpdateEducation('member/memberInfoUpdateEducation.do','2');
			}else{
				return false;
			}
		}else{
			$.messager.show({ // show error message
				title : '提示',
				msg : '请回复拒绝原因'
			});
			return false;
		}
	}
</script>
</head>

<body>

	<div id="content" region="center" title="列表" style="padding:5px;width: 98%;height: 98%">
		<table id="dg" class="easyui-datagrid"
			style="" url="member/memberInfoAjaxPage.do"
			iconCls="icon-save" rownumbers="true" pagination="true" fit="true"
			singleSelect="true" nowrap="false" toolbar="#toolbar" data-options="onLoadSuccess:function(data){ 
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
					<th field="imgUuid" align="center" width="5%" formatter="headPic">头像</th>
					<th field="nickname" align="center" width="10%">昵称</th>
					<th field="realName" align="center" width="7%">真实姓名</th>
					<th field="sex" align="center" width="5%" formatter="formatSex">性别</th>
					<th field="mobile" align="center" width="10%">手机</th>
					<th field="email" align="center" width="13%">邮箱</th>
					<th field="identityStatus" align="center" width="7%" formatter="formatIdentityStatus">身份认证</th>
					<th field="realNameStatus" align="center" width="7%" formatter="formatRealNameStatus">实名认证状态</th>
					<th field="educationStatus" align="center" width="7%" formatter="formatEducationStatus">学历认证状态</th>
					<th field="vip" align="center" width="7%" formatter="formatVipStatus">会员认证状态</th>
					<th field="status" align="center" width="5%" formatter="formatStatusBlack">黑名单</th>
					<th field="createDate" align="center" width="15%">创建时间</th>
				</tr>
			</thead>
		</table>
		<div id="toolbar">
			<div>
				昵称: <input id="nickname" class="easyui-textbox" style="width:180px">
				真实姓名: <input id="realName" class="easyui-textbox" style="width:180px">
				手机: <input id="mobile" class="easyui-textbox" style="width:180px">
				<a href="javaScript:void()" onclick="searchData()"
					class="easyui-linkbutton" iconCls="icon-search">搜索</a>
			</div>
			<div style="margin-bottom:5px">
				<security:act optCode="view">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-search" plain="true" onclick="doDetail()">用户信息</a>
				</security:act>
				<security:act optCode="resume">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-search" plain="true" onclick="viewResume()">详细简历</a>
				</security:act> 
				<security:act optCode="record">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-search" plain="true"
					onclick="viewRecord()">试戏记录</a>
				</security:act> 
				<security:act optCode="isYes">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-lock_open" plain="true"
						onclick="doUpdateStatus('member/doUpdateStatus.do',1)">取消黑名单</a>
				</security:act>
				<security:act optCode="isNo">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-lock" plain="true"
						onclick="doUpdateStatus('member/doUpdateStatus.do',0)">设置黑名单</a>
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
				<div class="fitem">
					<label>真实姓名:</label> <input id="realName" name="realName"
						style="width: 200px" class="easyui-textbox" readonly="readonly">
				</div>
				<div class="fitem">
					<label>昵称:</label> <input id="nickname" name="nickname"
						style="width: 200px" class="easyui-textbox" readonly="readonly">
				</div>
				<div class="fitem">
					<label>手机:</label> <input id="mobile" name="mobile"
						style="width: 200px" class="easyui-textbox" readonly="readonly">
				</div>
				<div class="fitem">
					<label>邮箱:</label> <input id="email" name="email"
						style="width: 200px" class="easyui-textbox" readonly="readonly">
				</div>
				<div class="fitem">
					<label>身份认证:</label> <input id="identityInfo" name="identityInfo"
						style="width: 200px" class="easyui-textbox" readonly="readonly">
				</div>
				<div class="fitem">
					<label>实名认证 :</label> <input id="realNameStatus" name="realNameStatus"
						style="width: 200px" class="easyui-textbox" readonly="readonly">
				</div>
				<div class="fitem">
					<label>学历认证 :</label> <input id="educationStatus" name="educationStatus"
						style="width: 200px" class="easyui-textbox" readonly="readonly">
				</div>
				<div class="fitem">
					<label>黑名单:</label> <input id="status" name="status"
						style="width: 200px" class="easyui-textbox" readonly="readonly">
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
				iconCls="icon-no" onclick="doRefuse('1')" style="width:90px">拒绝</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="javascript:$('#dlgIdentity').dialog('close')"
				style="width:90px">取消</a>
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
				iconCls="icon-no" onclick="doRefuse('2')" style="width:90px">拒绝</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="javascript:$('#dlgRealName').dialog('close')"
				style="width:90px">取消</a>
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
					<label>毕业学校:</label> <span id="school"></span>
				</div>
<!-- 				<div class="fitem"> -->
<!-- 					<label>学位证:</label> <a id="degreeImgboxer" title="学位证" rel="gallery1" class="boxer1" href=""><img id="degreeImg" alt="" -->
<!-- 							src="" style="width: 150px;height: 100px"></a> -->
<!-- 				</div> -->
				<div class="fitem">
					<label>毕业证（学生证）:</label> <a id="diplomaImgboxer" title="毕业证" rel="gallery1" class="boxer1" href=""><img id="diplomaImg" alt=""
							src="" style="width: 150px;height: 100px"></a>
				</div>
				<script>
						$(function(){
							$('.boxer1').boxer();
						});
			</script>
		</div>
		<div id="dlgEducation-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton c6"
				iconCls="icon-ok" onclick="doUpdateEducation('member/memberInfoUpdateEducation.do','1')" style="width:90px">通过</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton c6"
				iconCls="icon-no" onclick="doRefuse('3')" style="width:90px">拒绝</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="javascript:$('#dlgEducation').dialog('close')"
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
		
		<div id="dlg_record" class="easyui-dialog" title="试戏详情"
		style="width:100%;height:100%;top:10px;" data-options="closed:'true'"
		buttons="#dlg_record-buttons">
		<iframe id="recordFrame" name="resumeFrame" frameborder="0"
			height="100%" width="100%" align="center"></iframe>
		</div>
		<div id="dlg_record-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="javascript:$('#dlg_record').dialog('close')"
				style="width:90px">关闭</a>
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
				iconCls="icon-ok" onclick="doSend()"
				style="width:90px">发送</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="javascript:$('#dlg_reply').dialog('close')"
				style="width:90px">取消</a>
		</div>
	</div>

</body>
</html>
