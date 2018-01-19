<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="/achievement/source/css/banner.css">
	<link rel="stylesheet" type="text/css" href="/achievement/source/bootstrap-3.3.7-dist/css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="/achievement/source/css/picture.css">
	
	
	<script type="text/javascript" src="/achievement/js/jquery-3.2.1.js"></script>
	<script type="text/javascript" src="/achievement/source/bootstrap-3.3.7-dist/js/bootstrap.js"></script>
	
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body style="background-color:#0F0D0D;">
	<div class="col-sm-10">
	<div class="banner" id="banner2">
		<div class="banner-view"></div>
		<div class="banner-btn"></div>
		<div class="banner-number"></div>
		<div class="banner-progres"></div>
	</div>
</div>
<div class="col-sm-2">
	<div class="aside_banner" id="aside_banner1" style="background: #0F0D0D;width:auto;height: auto;float: right;position: relative;margin: 5px;">
		<div><img src="images/a1.png" class="img-rounded img-thumbnail"></div>
		<div><img src="images/a2.png" class="img-rounded img-thumbnail"></div>
		<div><img src="images/a3.png" class="img-rounded img-thumbnail"></div>
		<div><img src="images/a4.png" class="img-rounded img-thumbnail"></div>
		<div>
			<button class="col-sm-6 btn btn-default btn-lg" style="height: 140px;"><span class="glyphicon glyphicon-fire" style="font-size: 35px;color: #C54D4D;"></span><br>最热</button>
			<button class="col-sm-6 btn btn-default btn-lg" style="height: 140px;"><span class="glyphicon glyphicon-flag" style="font-size: 35px;color: #C54D4D;"></span><br>最新</button>
		</div>
		
	</div>
	</div>
	<div class="col-sm-12">
	<div class="lower_banner " id="lower_banner1" style="background: #0F0D0D;float: left;">
		<div class="col-sm-2"><img src="images/a1.png" class="img-rounded img-thumbnail"></div>
		<div class="col-sm-2"><img src="images/a2.png" class="img-rounded img-thumbnail"></div>
		<div class="col-sm-2"><img src="images/a3.png" class="img-rounded img-thumbnail"></div>
		<div class="col-sm-2"><img src="images/a4.png" class="img-rounded img-thumbnail"></div>
		<div class="col-sm-2"><img src="images/a5.png" class="img-rounded img-thumbnail"></div>
		<div class="col-sm-2"><img src="images/a5.png" class="img-rounded img-thumbnail"></div>
	</div>
	</div>
	
<script type="text/javascript" src="js/banner.js"></script>
<script type="text/javascript">
	var banenr2 = new FragmentBanner({
		container : "#banner2",//选择容器 必选
		imgs : ['images/a1.png','images/a2.png','images/a3.png','images/a4.png','images/a5.png'],//图片集合 
		size : {
			width : 1580,
			height : 800
		},
	});
</script>
  ${hotAchievements }
  <br>
   ${newAchievements }
  <br>
   ${slideShow }
  <br>
   一级界面，展示成果图片
  </body>
</html>
