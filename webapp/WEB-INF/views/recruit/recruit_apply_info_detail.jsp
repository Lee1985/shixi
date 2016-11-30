<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sh" uri="/shFunction"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1" />
<title>${title}</title>

<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<script type="text/javascript" src="js/system/easy.js"></script>
<style type="text/css">
table tr th{
font-size: 11pt;
border: 1px solid black;
background-color: #E8E8E8;
width: 200px;
line-height: 30px;
font-family: inherit;
font-weight: normal;
}
table tr td{
font-size: 11pt;
border: 1px solid black;
width: 200px;
text-align: center;
word-break: break-all;
}
.ftitle{
width: 788px;
background-color: #4F94CD;
line-height: 30px;
}
.fdiv{
	display: inline-block;
	float: left;
	margin-right: 20px;
	max-width: 50%;
	height: auto;
}
</style>
<script type="text/javascript">
var basePath = "<%=basePath%>"; 
	function goBack(){
		location.href = basePath + "recruit/recruitApplyInfoList.do";
	}
	function doUpdateStatus(status) {
		$.post('recruit/recruitApplyInfoUpdateStatus.do', {
			ids : $('#id').val(),
			videoStatus : status,
			reply : $('#reply').textbox('getValue')
		}, function(result) {
			if (result.success) {
				$.messager.show({ // show error message
					title : '提示',
					msg : result.msg
				});
				location.href = basePath + "recruit/recruitApplyInfoNext.do?id="+$('#id').val();
			} else {
				$.messager.show({ // show error message
					title : '提示',
					msg : result.msg
				});
			}
		}, 'json');
	}
	function doRefuse() {
		$('#dlg_reply').dialog('open').dialog('setTitle', '回复');
	}
</script>

</head>

<body>
<div class="content" style="width: 1375px;">
		<div style="margin-bottom:5px">
				<a href="javascript:void(0);" class="easyui-linkbutton" 
				iconCls="icon-back" plain="true" onclick="goBack()">返回</a>
				<a href="javascript:void(0);" class="easyui-linkbutton" 
				iconCls="icon-ok" plain="true" onclick="doUpdateStatus('1')">通过</a>
				<a href="javascript:void(0);" class="easyui-linkbutton" 
				iconCls="icon-no" plain="true" onclick="doRefuse()">拒绝</a>
			</div>
			<div class="fdiv">
			<form id="fm" name="fm" method="post" action="" data-options="novalidate:true">
				<input type="hidden" id="id" name="id" value="${info.id }"/>
				<input type="hidden" id="recruitId" name="recruitId" value="${entity.recruitId }"/>
				<table cellpadding="0" cellspacing="0">
					<tr>
						<th>角色头像</th>
						<td><img id="headImg" alt=""
							src="${entity.pictureInfo.urlPath }"
							style="width: 100px;height: 100px"></td>
					</tr>
					<tr>
						<th>角色姓名</th>
						<td>${sh:decode(entity.name)}</td>
					</tr>
					<tr>
						<th>性别</th>
						<td><c:if test="${entity.sex=='1' }">男</c:if>
						<c:if test="${entity.sex=='2' }">女</c:if></td>
					</tr>
					<tr>
						<th>标签</th>
						<td>${sh:decode(entity.lableNames)}</td>
					</tr>
					<tr>
						<th>片酬</th>
						<td>
						<c:if test="${entity.paidType == '面议'}">${entity.paidType }</c:if>
						<c:if test="${entity.paidType != '面议'}">${entity.paidMin }-${entity.paidMax}/${entity.paidType}</c:if>
						</td>
					</tr>
					<tr>
						<th>所属剧组</th>
						<td>${sh:decode(entity.recruitInfo.title)}</td>
					</tr>
					<tr>
						<th>发布人</th>
						<td>${sh:decode(entity.recruitInfo.memberInfo.realName)}</td>
					</tr>
					<tr>
						<th>发布时间</th>
						<td>${entity.createDate }</td>
					</tr>
				</table>
			</form>
			</div>
			<div class="fdiv"><video style="width: 460px;height: 329px;" src="${info.fileInfo.urlPath }" controls="controls"></video></div>
			<div class="fdiv">
				<table cellpadding="0" cellspacing="0">
					<tr>
						<th>导演要求</th>
						<th>试戏台词</th>
					</tr>
					<tr style="height: 298px;">
						<td>${sh:decode(entity.requirement)}</td>
						<td>${sh:decode(entity.dialogue)}</td>
					</tr>
				</table>
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
		</div>
</body>
</html>
