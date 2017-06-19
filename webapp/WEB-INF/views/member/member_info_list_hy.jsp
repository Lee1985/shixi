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
			nickname : $('#nickname').val(),
			realName : $('#realName').val(),
			mobile : $('#mobile').val(),
			vip:$('#vip').combobox('getValue')
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
			vip:status,
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
			$('#id').val(id);
			$('#nicknameR').html(nickname);
			$('#realNameR').html(realName);
			
// 		}
	}
	function doUpdateRealName(url,status) {
		
		$.post(url, {
			ids : $('#id').val(),
			realNameStatus : status
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
		if(status == '2' && $('#reply2').textbox('getValue') == ''){
			$.messager.show({
					title : '提示',
					msg : '请填写反馈！'
				});
				return;
		}
		$.post(url, {
			ids : $('#id').val(),
			educationStatus : status,
			reply : $('#reply2').textbox('getValue')
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
			$('#dlg_reply2').dialog('close');
			$('#reply2').textbox('setValue','');
			$('#replySelect2').combobox('setValue','');
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
	function formatVip(value, row) {
		if (value==1) {
			return "会员";
		} else {
			return "非会员";
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
	
	function doUpdateStatus2(url,status,id) {
		$.post(url, {
			ids : id,
			vip : status
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
	function doUpdateStatus(status) {
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
					$.post('member/memberInfoUpdateVip.do', {
						ids : ids,
						vip : status
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
	}
	function formatOper(value, row) {
		return '<a href="javascript:void(0)" class="detailok"'
			+'iconCls="icon-ok" onclick="doUpdateStatus2(\'member/memberInfoUpdateVip.do\',1,\''+row.id+'\')"'
			+'>设为会员</a>'
			+'<a href="javascript:void(0)" class="detailno"'
			+'iconCls="icon-cancel" onclick="doUpdateStatus2(\'member/memberInfoUpdateVip.do\',0,\''+row.id+'\')"'
			+'>取消会员</a>';
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
			style="" url="member/memberInfoAjaxPage.do?type=4"
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
					<th field="imgUuid" align="center" width="100" formatter="headPic">头像</th>
					<th field="nickname" align="center" width="100" formatter="formatContent">昵称</th>
					<th field="realName" align="center" width="100" formatter="formatContent">真实姓名</th>
					<th field="sex" align="center" width="100" formatter="formatSex">性别</th>
					<th field="vip" align="center" width="100" formatter="formatVip">会员认证状态</th>
					<th field="createDate" align="center" width="200">创建时间</th>
					<th field="oper" align="center" width="200" formatter="formatOper">操作</th>
				</tr>
			</thead>
		</table>
		<div id="toolbar">
			<div>
				昵称: <input id="nickname" class="easyui-textbox" style="width:180px">
				真实姓名: <input id="realName" class="easyui-textbox" style="width:180px">
				手机: <input id="mobile" class="easyui-textbox" style="width:180px">
				<select class="easyui-combobox" id="vip" name="vip"
						style="width:120px;"
						data-options="panelHeight:'auto',editable:false,required:false">
						<option value="99">全部</option>
						<option value="0">非会员</option>
						<option value="1">会员</option>
					</select>
				
				<a href="javaScript:void()" onclick="searchData()"
					class="easyui-linkbutton" iconCls="icon-search">搜索</a>
			</div>
			<div style="margin-bottom:5px">
				<security:act optCode="batchPass">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-ok" plain="true" onclick="doUpdateStatus('1')">批量设置</a>
				</security:act>
				<security:act optCode="batchRefuse">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-cancel" plain="true" onclick="doUpdateStatus('0')">批量取消</a>
				</security:act> 
			</div>
		</div>
</body>
</html>