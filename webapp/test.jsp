<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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

<title>测试</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript" src="js/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript">
	function btnSubmit() {
		var data="";
		if ($('#paramTypeJson').prop('checked')) {
			data=$('#dataJson').val();
			$.ajax({
				type : 'POST',
				url : 'getDesMw.do',
				data : {
					data : data
				},
				success : function(rdata) {
					submitRest(rdata);
					$('#data').text(rdata);
				},
				error : function() {
					alert('加密error');
				}
			});
		} else {
			data=$('#data').val();
			submitRest(data);
		}		
	}
	function submitRest(data){
		detrp(data,'dataJson');
		$.ajax({
			type : 'POST',
			url : $('#action').val(),
			data : {
				data : data
			},
			success : function(data) {
				$('#result').text(data);
				detrp(data,'resultJson');
			},
			error : function() {
				alert('submit-error');
			}
		});
	}
	//解密串
	function detrp(data,id) {
		$.ajax({
			type : 'POST',
			url : 'getDecryptStr.do',
			data : {
				data : data
			},
			success : function(data) {
				$('#'+id).text(data);
			},
			error : function() {
				alert('解密error');
			}
		});
	}
	//加密串
	function getDesMw(data) {
		$.ajax({
			type : 'POST',
			url : 'getDesMw.do',
			data : {
				data : data
			},
			success : function(data) {
				$('#resultJson').text(data);
			},
			error : function() {
				alert('error');
			}
		});
	}
	function paramTypeClick(type){
		if (type==1) {
			$('#data').attr('readonly',true);
			$('#dataJson').attr('readonly',false);
			
			$('#dataJson').focus();
		} else {
			$('#data').attr('readonly',false);
			$('#dataJson').attr('readonly',true);
			
			$('#data').focus();
		}
	}
</script>
<style type="text/css">
div {
	width: 100%;
}

input {
	width: 500px;
}

textarea {
	width: 500px;
	height: 100px;
}

div label {
	width: 80px;
	display: inline-block;
}
</style>
</head>

<body>
	<div style="width: 100%;text-align: center;">
		<div style="text-align: center;">
			<h3>接口测试</h3>
		</div>
		<form id="fm" action="">
			<div>
				<label>URL:</label><input type="text" id="action" value="rest/" />
			</div>
			<div>
				<label>参数形式</label><input type="radio" style="width: 30px" id="paramTypeJson"
					name="paramType" onclick="paramTypeClick(1)"/>json参数<input type="radio" checked="checked"
					style="width: 30px" name="paramType" onclick="paramTypeClick(2)"/>加密参数
			</div>
			<div>
				<label>json参数：</label>
				<textarea id="dataJson" name="dataJson"></textarea>
			</div>
			<div>
				<label>加密参数：</label>
				<textarea id="data" name="data"></textarea>
			</div>
			<div style="text-align: center;">
				<input type="button" style="width: 120px;" onclick="btnSubmit()"
					value="提交" />
			</div>

			<div>
				<label>结果：</label>
				<textarea readonly="readonly" id="result" name="result"></textarea>
			</div>
			<div>
				<label>解析结果：</label>
				<textarea readonly="readonly" id="resultJson" name="resultJson"></textarea>
			</div>
		</form>
	</div>
</body>
</html>
