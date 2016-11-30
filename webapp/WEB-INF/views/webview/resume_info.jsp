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
		<base href="<%=basePath%>">
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="format-detection" content="telephone=no"/>
		<meta name="format-detection" content="email=no"/>
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<meta name="description" content="我的简历">
		<link rel="stylesheet" type="text/css" href="css/common.css"/>
		<link rel="stylesheet" type="text/css" href="css/profile.css"/>
		<link rel="stylesheet" type="text/css" href="css/jquery.fs.boxer.css"/>
		<script src="js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="js/jquery.fs.boxer.js" type="text/javascript" charset="utf-8"></script>
		<script src="js/profile.js" type="text/javascript" charset="utf-8"></script>
		<script src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js" type="text/javascript" charset="utf-8"></script>
		<title>${memberResumeTemplate.realName }</title>
		<script>
		
		wx.ready(function() {
			alert('我的简历');
		wx.onMenuShareAppMessage({
		      title: '${memberResumeTemplate.realName }',
		      desc: '我的简历',
		      link: '<%=basePath%>'+'rest/view/ResumeInfo.do?memberId='+'${memberResumeTemplate.id }',
		      imgUrl: '${memberInfo.pictureInfo.urlPath }',
		      trigger: function (res) {
		    	  alert('trigger');
		      },
		      success: function (res) {
		    	  alert('success');
		      },
		      cancel: function (res) {
		    	  alert('cancel');
		      },
		      fail: function (res) {
		    	  alert('fail');
		      }
		    });
		 });
		</script>
		
			<style type="text/css">
	    .p_title{overflow: hidden; background: #000;}	
		.p_title span,.p_title a{display: inline-block;float: left;}
		.close{
			width: 2.5rem;height:2.5rem;
	       	background: url(img/close.png) no-repeat left center;
	       	background-size: 2.5rem 2.5rem;
	       	margin-top: 1.2rem;
	       	margin-left: 0.5rem;
	    }
		.title_new{
			font-size: 1.6rem;
	        color: #FFFFFF;
	        background: url(img/sslogo.png) no-repeat left center;
	        background-size: 4rem 4rem;
	        height: 4rem;
	        line-height: 4rem;
	        margin-top: 0.5rem;
	        margin-left: 0.5rem;
	        padding-left: 4.5rem;
	        float: left;
	    }
		.p_title a{
		   float: right;
           width: 10rem;
           height: 5rem;
           line-height: 5rem;
           background: #b29362;
           color: #FFFFFF;
           text-align: center;
           font-size: 1.8rem;
	    }
	</style>
		<script >
			$(function () {
				$(".close").click(function(){
					$(".p_title").fadeOut();
				});
			})
		</script>
	</head>
	<body>
		<div class="p_title">
			<span class="close"></span>
			<h1 class="title_new">戏的事，戏里说！</h1>
			<a href="qrcode.do">立即下载</a>
		</div>
		<div class="basic_info">
			<h1 class="b_title"><span class="cube"></span>基本信息 PERSONAL INFORMATION</h1>
			<div class="info">
				<ul>
					<li>
						<span class="item_name">姓名：</span>${memberResumeTemplate.realName }
					</li>
					<li>
						<span class="item_name">性别：</span><c:if test="${memberResumeTemplate.sex == '1' }">男</c:if><c:if test="${memberResumeTemplate.sex == '2' }">女</c:if>
					</li>
					<li>
						<span class="item_name">身高：</span>${memberResumeTemplate.height }cm
					</li>
					<li>
						<span class="item_name">体重：</span>${memberResumeTemplate.weight }kg
					</li>
					<li>
						<span class="item_name">生日：</span>${memberResumeTemplate.birthdayStr }
					</li>
					<li>
						<span class="item_name">毕业学校：</span>${memberResumeTemplate.school }
						<c:if test="${memberInfo.educationStatus == '1'}"><img style="width:14px;display: inline-block;" src="images/educationImg.png"/></c:if>
					</li>
					
					<li>
						<span class="item_name">经常出现的城市：</span><span class="items">${memberResumeTemplate.cityCode }</span>
					</li>
				</ul>
				<div class="personal_photo">
					<div class="photo_pic">
						<img src="${memberInfo.pictureInfo.urlPath }"/>
					</div>
					<div class="name">
						${memberResumeTemplate.realName } 
						<c:if test="${memberInfo.realNameStatus == '1' }">
						<span class="c_icon1">V</span>
						</c:if>
						<c:if test="${memberInfo.realNameStatus != '1' }">
						<span class="c_icon2">V</span>
						</c:if>	
					</div>
					<div class="age">
						<c:if test="${memberInfo.sex == '1' }">
						<i class="sex1"></i>
						</c:if>
						<c:if test="${memberInfo.sex == '2' }">
						<i class="sex2"></i>
						</c:if>
						<span>${age }岁</span><span>${memberResumeTemplate.height }cm</span><span>${memberResumeTemplate.weight }kg</span>
					</div>
				</div>
			</div>
		</div>
		<div class="character_item">
			<h1 class="b_title"><span class="cube"></span>角色标签</h1>
			<div class="actor_item1">
				<c:forEach items="${roleList }" var="role">
					<span>${role }</span>
				</c:forEach>
						
					</div>
		</div>
		<div class="character_item">
			<h1 class="b_title"><span class="cube"></span>技能标签</h1>
			<div class="actor_item1">
				<c:forEach items="${skillList }" var="skill">
					<span>${skill }</span>
				</c:forEach>
					</div>
		</div>
		<div class="act_photo">
			<h1 class="b_title"><span class="cube"></span>见组照</h1>
			<div class="c_photos">
				<a title="正面" rel="gallery" class="boxer" href="${memberResumeTemplate.pictureInfo1.urlPath }"><img alt="" src="${memberResumeTemplate.pictureInfo1.urlPath }"><span>正面</span></a>
				<a title="左侧" rel="gallery" class="boxer" href="${memberResumeTemplate.pictureInfo2.urlPath }"><img alt="" src="${memberResumeTemplate.pictureInfo2.urlPath }"><span>左侧</span></a>
				<a title="左45度" rel="gallery" class="boxer" href="${memberResumeTemplate.pictureInfo3.urlPath }"><img alt="" src="${memberResumeTemplate.pictureInfo3.urlPath }"><span>左45度</span></a>
				<a title="右侧" rel="gallery" class="boxer" href="${memberResumeTemplate.pictureInfo4.urlPath }"><img alt="" src="${memberResumeTemplate.pictureInfo4.urlPath }"><span>右侧</span></a>
				<a title="右45度" rel="gallery" class="boxer" href="${memberResumeTemplate.pictureInfo5.urlPath }"><img alt="" src="${memberResumeTemplate.pictureInfo5.urlPath }"><span>右45度</span></a>
				<a title="全身照" rel="gallery" class="boxer" href="${memberResumeTemplate.pictureInfo6.urlPath }"><img alt="" src="${memberResumeTemplate.pictureInfo6.urlPath }"><span>全身照</span></a>
                <script>
					$(function(){
						$('.boxer').boxer({
							mobile: true
						});
					});
				</script>
			</div>
		</div>		
		<div class="video">
			<h1 class="b_title"><span class="cube"></span>自我介绍</h1>
			<div class="videobox">
			<video width="100%" id="video">
				<source src="${memberResumeTemplate.fileInfo.urlPath }" type="video/mp4"></source>
				<source src="myvideo.ogv" type="video/ogg"></source>
				<source src="myvideo.webm" type="video/webm"></source>
				<object width="" height="" type="application/x-shockwave-flash" data="myvideo.swf">
					<param name="movie" value="myvideo.swf" />
					<param name="flashvars" value="autostart=true&amp;file=myvideo.swf" />
				</object>
				当前浏览器不支持 video直接播放，点击这里下载视频： <a href="myvideo.webm">下载视频</a>
			</video>
			</div>
		</div>
		<div class="video_photo">
			<h1 class="b_title"><span class="cube"></span>剧照/生活照</h1>
			
			<c:forEach items="${photoList }" var="photo" varStatus="index">
			<div class="description">
				${photo.title }
			</div>
			<div class="v_photo">
				<a title="" rel="gallery${index.index }" class="boxer${index.index }" href="${photo.pictureInfoAll.urlPath }"><img alt="" src="${photo.pictureInfoAll.urlPath }"></a>
				<a title="" rel="gallery${index.index }" class="boxer${index.index } y" href="${photo.pictureInfo1.urlPath }"><img alt="" src="${photo.pictureInfo1.urlPath }"></a>
				<c:if test="${photo.pictureInfo2 != null && photo.pictureInfo2.urlPath != null }">
					<a title="" rel="gallery${index.index }" class="boxer${index.index } y" href="${photo.pictureInfo2.urlPath }"><img alt="" src="${photo.pictureInfo2.urlPath }"></a>
				</c:if>
				<c:if test="${photo.pictureInfo3 != null && photo.pictureInfo3.urlPath != null }">
					<a title="" rel="gallery${index.index }" class="boxer${index.index } y" href="${photo.pictureInfo3.urlPath }"><img alt="" src="${photo.pictureInfo3.urlPath }"></a>
				</c:if>
				<c:if test="${photo.pictureInfo4 != null && photo.pictureInfo4.urlPath != null }">
					<a title="" rel="gallery${index.index }" class="boxer${index.index } y" href="${photo.pictureInfo4.urlPath }"><img alt="" src="${photo.pictureInfo4.urlPath }"></a>
				</c:if>
				<c:if test="${photo.pictureInfo5 != null && photo.pictureInfo5.urlPath != null }">
					<a title="" rel="gallery${index.index }" class="boxer${index.index } y" href="${photo.pictureInfo5.urlPath }"><img alt="" src="${photo.pictureInfo5.urlPath }"></a>
				</c:if>
                <script>
			$(function(){
				$('.boxer${index.index }').boxer({
					mobile: true
				});
			});
			</script>
			</div>
			</c:forEach>			
		</div>		
	</body>
</html>