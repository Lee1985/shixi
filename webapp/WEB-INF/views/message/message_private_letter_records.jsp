<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="security" uri="http://www.bluemobi.com"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

<title>聊天记录</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link rel="stylesheet" type="text/css" href="css/privateMessage.css">
<script type="text/javascript" src="js/jquery-easyui-1.4.1/jquery.min.js"></script>
</head>
<style type="text/css">
	.right-time{
		position:absolute;
		left: 112px;
		top: -19px;
		font-size:12px;
		color:#BFBFBF;
		float: right;
		width: 125px;
	}
	
	.left-time{
		position:absolute;
		top: -19px;
    	left:3px;
    	font-size:12px;
    	color:#BFBFBF;
    	width: 125px;
	}
	
	.receive:before{
		background-image:url('${recordResult.contactorImageUrl}');
		background-size:100% 100%;
	}
	
	.send:before{
		background-image:url('${recordResult.memberImageUrl}');
		background-size:100% 100%;
	}
</style>
<script type="text/javascript">
	$(function(){
		$('html,body').animate({scrollTop: $('#last').offset().top},1000);
		return false;
	});
</script>
<body>
	<div data-role="content" id="convo" data-from="Sonu Joshi">			
		<ul class="chat-thread">			
			<c:forEach var="record" items="${recordResult.messageRecords }" varStatus="state">																					
				<c:choose>
					<c:when test="${!state.last}">
						<li <c:choose><c:when test="${record.sender eq '1'}">class="send"</c:when><c:otherwise>class="receive"</c:otherwise></c:choose> ><span class="time">${record.sendDate }</span>${record.sendContent }</li>  		
					</c:when>
					<c:otherwise>
						<li id="last" <c:choose><c:when test="${record.sender eq '1'}">class="send"</c:when><c:otherwise>class="receive"</c:otherwise></c:choose> ><span class="time">${record.sendDate }</span>${record.sendContent }</li>	
					</c:otherwise>
				</c:choose>
			</c:forEach>			
		</ul>
 	</div>
	<div style="text-align:center;margin:50px 0; font:normal 14px/24px 'MicroSoft YaHei';">
	</div>
</body>
</html>