<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="security" uri="http://www.bluemobi.com"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

<title>招募角色信息</title>

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

.fi{
	width: 50%;
	float:left;
	font-size: 12px;
/* 	border-bottom: 1px solid #A8A8A8; */
}

.fitem label {
	display: inline-block;
	width: 120px;
}

.bb {
	font-weight:bold;
}

.fitem input {
	width: 160px;
}
.picdiv{
	display: inline-block;
	width: 100px;
    height: 100px;
    padding: 5px;
}
.picimg{
	width: 100px;
    height: 100px;
}
</style>

<script type="text/javascript">
var basePath = "<%=basePath%>"; 
	var recruitId = '${recruitId }';
	var backUrl = '${backUrl}';
	function goBack(){
		location.href = basePath + 'recruit/recruitInfoList.do';
	}
	function searchData() {
		$('#dg').datagrid('load', {
			name : $('#name').val()
		});
	}
	function searchData1() {
		$('#dg1').datagrid('load', {
			nickname : $('#nickname').val()
		});
	}
	function searchData2() {
		$('#dg2').datagrid('load', {
			cityName : $('#cityName').val()
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
					$('#contentRole').show();
					recruitId = result.id;
					$('#dg').datagrid('options').url = 'recruit/recruitRoleInfoAjaxPage.do?recruitId='+recruitId;
            		$('#dg').datagrid('reload'); 
            		$('#idd').val(recruitId);
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
			}
		});
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
	function formatSex(value, row) {
		if (value==1) {
			return "男";
		} else {
			return "女";
		}
	}
	function formatPaid(value, row) {
		if(row.paidType == '面议'){
			return row.paidType;
		}else if(row.paidMax == null || row.paidMax == ''){
			return row.paidMin+"/"+row.paidType;
		}else{
			return row.paidMin+"-"+row.paidMax+"/"+row.paidType;
		}
	}
	function doAdd() {
		$('#dlg').dialog('open').dialog('setTitle', '新建');
		$('#fmRole').form('clear');
		$('#sysImgs').attr('src','images/add.jpg');
		$('#recruitId').val(recruitId);
	}
	function doEdit() {
		var row = $('#dg').datagrid('getSelected');
		if (row) {
			$('#dlg').dialog('open').dialog('setTitle', '修改');
			$('#fmRole').form('load', row);
			$("#sysImgs").attr("src", row.pictureInfo.urlPath);
		}
	}
	
	function saveRole() {
		var index ;
		$('#fmRole').form('submit', {
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
	
	$(document).ready(function(){
		if(recruitId == ''){
			$('#contentRole').hide();
		}else{
			$('#type').combobox('setValue','${entity.type }');
			$('#lableCode').combobox('setValue','${entity.lableCode }');
		}
	});
	function formatOper(value, row){
		return '<a class="detailcls" href="javascript:void(0);" style="cursor: pointer;" onclick="doViewRole(\''
			+ row.id + '\')">'+ '查看' +'</a>';
	}
	function doViewRole(id){
		location.href = basePath + "recruit/recruitRoleInfoById.do?id="+id+"&backUrl="+backUrl;
	}
	
	function memberChoose(){
		$('#dlg_member').dialog('open');
	}
	function cityChoose(){
		$('#dlg_city').dialog('open');
	}
	function onClickRowRecommend(row){
		if(row){
			$('#memberId').val(row.id);
			$('#memberName').textbox('setValue',row.nickname);
			$('#dlg_member').dialog('close');
		}
		return;
	}
	function onClickRowRecommend2(row){
		if(row){
			$('#cityCode').val(row.id);
			$('#cityNames').textbox('setValue',row.cityName);
			$('#dlg_city').dialog('close');
		}
		return;
	}
	function formatCityStatus(value, row) {
		if (value==2) {
			return "是";
		} else {
			return "";
		}
	}
	function doSysPic(){
		$('#dlg_pic').dialog({    
			modal:true,
			shadow:true				
		});
		$('#dlg_pic').dialog('open');
	}
	
	function checkedPic(uuid,url){
		$('#imgUuid').val(uuid);
		$('#sysImgs').attr('src',url);
		$('#dlg_pic').dialog('close');
	}
</script>
</head>

<body>

	<div id="content" region="center" title="列表" style="padding:5px;">
			<div style="margin-bottom:5px">
				<a href="javascript:void(0);" class="easyui-linkbutton" 
				iconCls="icon-back" plain="true" onclick="goBack()">返回</a>
				<a href="javascript:void(0);" class="easyui-linkbutton" 
				iconCls="icon-ok" plain="true" onclick="save()">保存</a>
			</div>
			<form id="fm" name="fm" method="post" action="recruit/recruitInfoAjaxSave.do"
				data-options="novalidate:true">
				<input type="hidden" id="idd" name="id" value="${entity.id }">
				<div class="fitem fi">
					<label class="bb">标题:</label> <input id="title" name="title"
						style="width: 200px" class="easyui-textbox" required="true" value="${entity.title }">
				</div>
				<div class="fitem fi">
					<label class="bb">招募类型:</label> <select class="easyui-combobox" id="type"
								name="type" style="width:200px;"
								data-options="panelHeight:'auto',required:true,editable:false">
								<option value="1">官方招募</option>
								<option value="2">私人招募</option>
							</select>
				</div>
				<div class="fitem fi">
					<label class="bb">发布人:</label> <input type="hidden" id="memberId" name="memberId" value="${entity.memberId }">
					<input id="memberName" name="memberName" required="true"
						style="width: 100px" class="easyui-textbox" readonly="readonly" value="${entity.memberInfo.nickname }">
						<a href="javascript:void(0);" class="easyui-linkbutton" 
						iconCls="icon-search" plain="true" onclick="memberChoose()">选择</a>
				</div>
				<div class="fitem fi">
					<label class="bb">拍摄地:</label> <input type="hidden" id="cityCode" name="cityCode" value="${entity.cityCode }">
					<input id="cityNames" name="cityNames" required="true"
						style="width: 100px" class="easyui-textbox" readonly="readonly" value="${entity.city.cityName }">
						<a href="javascript:void(0);" class="easyui-linkbutton" 
						iconCls="icon-search" plain="true" onclick="cityChoose()">选择</a>
				</div>
				<div class="fitem fi">
					<label class="bb">类型:</label> <input class="easyui-combobox" id="lableCode"
								name="lableCode" style="width: 200px"
								data-options="url:'recruit/recruitCategoryAjaxAll.do?status=1',
										method:'get',valueField:'id',textField:'name',panelHeight:'150',
										editable:false,required:true">
				</div>
				<div class="fitem fi">
					<label class="bb">导演:</label> <input id="director" name="director"
						style="width: 200px" class="easyui-textbox" required="true" value="${entity.director }">
				</div>
				<div class="fitem fi">
					<label class="bb">编剧:</label> <input id="screenwriter" name="screenwriter"
						style="width: 200px" class="easyui-textbox" required="true" value="${entity.screenwriter }">
				</div>
				<div class="fitem fi">
					<label class="bb">拍摄开始时间:</label> <input id="startDate" name="startDate"
						style="width: 200px" class="easyui-datebox" required="true" value="${entity.startDate }">
				</div>
				<div class="fitem fi">
					<label class="bb">拍摄结束时间:</label> <input id="endDate" name="endDate"
						style="width: 200px" class="easyui-datebox" required="true" value="${entity.endDate }">
				</div>
				<div class="fitem fi">
					<label class="bb">试戏截止时间:</label> <input id="deadline" name="deadline"
						style="width: 200px" class="easyui-datebox" required="true" value="${entity.deadline }">
				</div>
				<div class="fitem fi">
					<label class="bb" style="display: block;margin-bottom: 5px">剧本大纲:</label> <input id="scriptOutline" name="scriptOutline"
								class="easyui-textbox" value="${entity.scriptOutline }"
								data-options="prompt:'剧本大纲',multiline:true,required:true"
								style="height:300px;width: 95%">
				</div>
				<div class="fitem fi">
					<label class="bb" style="display: block;margin-bottom: 5px">备注:</label> <input id="remark" name="remark"
								class="easyui-textbox" value="${entity.remark }"
								data-options="prompt:'备注',multiline:true"
								style="height:300px;width: 95%">
				</div>
			</form>
	</div>
	<div id="contentRole" region="center" title="列表" style="padding:5px;">
		<table id="dg" class="easyui-datagrid"
			style="width:100%;min-height:400px" url="recruit/recruitRoleInfoAjaxPage.do?recruitId=${recruitId }"
			iconCls="icon-save" rownumbers="true" pagination="true" nowrap="false"
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
					<th field="imgUuid" align="center" width="10%" formatter="headPic">角色图片</th>
					<th field="name" align="center" width="10%">角色名称</th>
					<th field="sex" align="center" width="5%" formatter="formatSex">性别</th>
					<th field="paidMin" align="center" width="10%" formatter="formatPaid">片酬</th>
					<th field="lableCode" align="center" width="10%">标签</th>
					<th field="requirement" align="center" width="18%">导演要求</th>
					<th field="dialogue" align="center" width="18%">试戏台词</th>
					<th field="oper" align="center" width="10%" formatter="formatOper">操作</th>
				</tr>
			</thead>
		</table>
		<div id="toolbar">
			<div>
				角色名称: <input id="name" class="easyui-textbox" style="width:180px">
				<a href="javaScript:void()" onclick="searchData()"
					class="easyui-linkbutton" iconCls="icon-search">搜索</a>
			</div>
			<div style="margin-bottom:5px">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-add" plain="true" onclick="doAdd()">新建</a>
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-edit" plain="true" onclick="doEdit()">修改</a>
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-remove" plain="true"
					onclick="doDelete('recruit/recruitRoleInfoAjaxDelete.do')">删除</a>
			</div>
		</div>
		<div id="dlg" class="easyui-dialog"
			data-options="iconCls:'icon-save',resizable:true,modal:true"
			style="width:100%;height:400px;padding:10px 20px;top:10px;" closed="true"
			buttons="#dlg-buttons">
			<div class="ftitle">请完善以下信息！</div>
			<form id="fmRole" name="fmRole" method="post" action="recruit/recruitRoleInfoAjaxSave.do"
				data-options="novalidate:true" enctype="multipart/form-data">
				<input type="hidden" id="id" name="id">
				<input type="hidden" id="recruitId" name="recruitId">
				<input type="hidden" id="imgUuid" name="imgUuid">
				<div class="fitem">
					<label>图片(建议比例 1:1)</label> <img id="sysImgs" alt="" src=""
						style="width: 100px;height: 100px" onclick="sysImg.click()">
					<label>&nbsp;</label> <input type="file" id="sysImg"
						name="sysImg" style="width:200px;display: none"
						onChange="onChange(this)"
						data-options="prompt:'选择图片...',required:true">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-add" plain="true" onclick="doSysPic()">系统选择</a>
				</div>
				<div class="fitem">
					<label>角色名称:</label> <input id="name" name="name"
						style="width: 200px" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>性别:</label> <select class="easyui-combobox" id="sex"
								name="sex" style="width:200px;"
								data-options="panelHeight:'auto',required:true,editable:false">
								<option value="1">男</option>
								<option value="2">女</option>
							</select>
				</div>
				<div class="fitem">
					<label>标签(最多3个):</label> <input id="lableCode" name="lableCode"
						style="width: 200px" class="easyui-combobox" required="true"
						 data-options="url:'system/systemLableInfoAjaxAll.do?lableType=1&status=1',
							method:'get',valueField:'lableName',textField:'lableName',groupField:'group',panelHeight:'150',
							editable:false,required:false,multiple:true">
				</div>
				<div class="fitem">
					<label>结算单位:</label> <select class="easyui-combobox" id="paidType"
								name="paidType" style="width:200px;"
								data-options="panelHeight:'auto',required:true,editable:false,
								onSelect:function(record){
										if(record.value == '面议'){
											$('.dpaid').hide();
											$('#paidMin').textbox('setValue','');
											$('#paidMax').textbox('setValue','');
											$('#paidMin').textbox({ required: false });
											$('#paidMax').textbox({ required: false });
										}else{
											$('.dpaid').show();
											$('#paidMin').textbox({ required: true });
											$('#paidMax').textbox({ required: true });
										}
             						}">
								<option value="天">天</option>
								<option value="周">周</option>
								<option value="月">月</option>
								<option value="集">集</option>
								<option value="场">场</option>
								<option value="面议">面议</option>
							</select>
				</div>
				<div class="fitem dpaid">
					<label>片酬范围:</label> <input id="paidMin" name="paidMin"
						style="width: 100px" class="easyui-numberbox">
						-<input id="paidMax" name="paidMax"
						style="width: 100px" class="easyui-numberbox">
				</div>
				<div class="fitem">
					<label>导演要求:</label> <input id="requirement" name="requirement"
								class="easyui-textbox"
								data-options="prompt:'导演要求',multiline:true,required:true"
								style="height:150px;width: 200">
				</div>
				<div class="fitem">
					<label>试戏台词:</label> <input id="dialogue" name="dialogue"
								class="easyui-textbox"
								data-options="prompt:'试戏台词',multiline:true,required:true"
								style="height:150px;width: 200">
				</div>
			</form>
		</div>
		<div id="dlg-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton c6"
				iconCls="icon-ok" onclick="saveRole()" style="width:90px">确定</a> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')"
				style="width:90px">取消</a>
		</div>
		
		
		<div id="dlg_member" class="easyui-dialog" title="用户"
			data-options="iconCls:'icon-save',resizable:true,modal:true"
			style="width:800px;height:400px;padding:10px 20px;top:50px" closed="true">
		<table id="dg1" class="easyui-datagrid" style="width:100%;height:100%"  
		data-options="
		url:'member/memberInfoAjaxPage.do?status=1',
		iconCls:'icon-save',
		rownumbers:true,
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar1',
		onClickRow:function(rowIndex,rowData){
		  onClickRowRecommend(rowData);
		}
		">
		<thead>
			<tr>
				<th field="nickname" align="center" width="100">昵称</th>
				<th field="realName" align="center" width="100">真实姓名</th>
				<th field="sex" align="center" width="100" formatter="formatSex">性别</th>
				<th field="identityInfo" align="center" width="120">身份认证</th>
			</tr>
		</thead>
	</table>
	<div id="toolbar1">
		<div>
			昵称 : <input id="nickname" class="easyui-textbox" style="width:180px">
			<a href="javaScript:void()" onclick="searchData1()"
				class="easyui-linkbutton" data-options="iconCls:'icon-search'">搜索</a>
		</div>
	</div>
	</div>
	
	<div id="dlg_city" class="easyui-dialog" title="城市"
			data-options="iconCls:'icon-save',resizable:true,modal:true"
			style="width:800px;height:400px;padding:10px 20px;top:50px" closed="true">
		<table id="dg2" class="easyui-datagrid"
		style="width:100%;height:100%"  
		data-options="
		url:'system/systemHotspotCityAjaxPage.do',
		iconCls:'icon-save',
		rownumbers:true,
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar2',
		onClickRow:function(rowIndex,rowData){
		  onClickRowRecommend2(rowData);
		}
		">
		<thead>
			<tr>
				<th field="cityName" align="center" width="200">城市名</th>
				<th field="orderList" align="center" width="200">排序</th>
				<th field="zipcode" align="center" width="200">城市编码</th>
				<th field="status" align="center" width="200" formatter="formatCityStatus">热点</th>
			</tr>
		</thead>
	</table>
	<div id="toolbar2">
		<div>
			城市名: <input id="cityName" class="easyui-textbox" style="width:180px">
				<a href="javaScript:void()" onclick="searchData2()"
					class="easyui-linkbutton" iconCls="icon-search">搜索</a>
		</div>
	</div>
	</div>
	
	<div id="dlg_pic" class="easyui-dialog" title="系统头像"
		style="width:600px;height:400px;top:50px" data-options="closed:'true'"
		buttons="#dlg_pic-buttons">
		<c:forEach items="${listPic }" var="pic">
		<div class="picdiv"><img class="picimg" src="${pic.pictureInfo.urlPath }" onclick="checkedPic('${pic.pictureInfo.uuid }','${pic.pictureInfo.urlPath }')"></img></div>
		</c:forEach>
	</div>
	<div id="dlg_pic-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel" onclick="javascript:$('#dlg_pic').dialog('close')"
			style="width:90px">关闭</a>
	</div>
		
	</div>
</body>
</html>
