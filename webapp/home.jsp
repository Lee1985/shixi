<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>试戏</title>
		<link rel="stylesheet" type="text/css" href="css/web/home/common.css" />
		<link rel="stylesheet" type="text/css" href="css/web/home/style.css" />
		<script src="js/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript">
			$(window).on("load resize", function() {
				var h1 = $(window).height();
				var h2 = $(".wrapper").height();
				if(h1 >= h2) {
					$(".footer").addClass("footerposition");
				} else {
					$(".footer").removeClass("footerposition");
				}

			});
		</script>
	</head>

	<body>
		<div class="wrapper">
			<!--header start-->
			<div class="header">
				<div class="h_first">
					试戏APP
				</div>
			</div>
			<div class="logo_box">
				<div class="logo_center">
					<h1 class="logo"><img src="img/web/home/top_07.png"/></h1>
				</div>
				<div class="keywords">
					<img src="img/web/home/top_10.png" />
				</div>
			</div>
			<!--header end-->

			<!--content start-->
			<div class="main_box">
				<div class="m_left">
					<img src="img/web/home/top_15.png" />
				</div>
				<div class="m_right">
					<img src="img/web/home/top_18.png" alt="试戏网演员的专业招聘平台。到处奔波，不如 秀 出真本事" />
					<ul>
						<li>
							<img src="img/web/home/top_22.png" alt="App Store" />
							<p class="config"><span class="icon1">App Store</span></p>
						</li>
						<li class="second">
							<img src="img/web/home/top_24.png" alt="微博主页" />
							<p class="config"><span class="icon2">微博主页</span></p>
						</li>
						<li>
							<img src="img/web/home/top_26.png" alt="微信公众号" />
							<p class="config"><span class="icon3">微信公众号</span></p>
						</li>
					</ul>
					<img src="img/web/home/top_48.png" alt="随时更新剧组招募，跟踪简历动态，无需奔波，简单便捷" />
				</div>
			</div>
			<!--content end-->

			<!--footer start-->
			<div class="footer"></div>
			<!--footer end-->
		</div>
	</body>
</html>