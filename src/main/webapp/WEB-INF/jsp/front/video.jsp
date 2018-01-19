<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'video.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="/achievement/source/bootstrap-3.3.7-dist/css/bootstrap.css">
	
	<script type="text/javascript" src="/achievement/js/jquery-3.2.1.js"></script>
	<script type="text/javascript" src="/achievement/source/bootstrap-3.3.7-dist/js/bootstrap.js"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  <body style="background-color: #FCFCFC">
	<div class="container-fluid">
	<div class="col-sm-3"></div>
	<div class="col-sm-6" style="background:#BEAFF3;padding: 0px;margin-top:150px;">
		<div style="font-size: 3em;font-style: italic;font">成果展示<br></div>
		<div ><a href="userpage4.html" class="pull-right" style="font-size: 1.25em;text-decoration:none;"><span class="glyphicon glyphicon-circle-arrow-right"></span> 进入</a></div>
		
		<video controls autoplay height="550px" width="944px;">
		</video>
</div>
	<div class="col-sm-3"></div>
</div>
  <br>
    展示一个成果的视屏。
    <c:choose>
    <c:when test="${roleId == 1 }">
    <input type="button" value="点击进入"onclick='window.open("/achievement/back/user/modules/${achId}","_self");'>
    
    </c:when>
    <c:when test="${roleId == 2 }">
    <input type="button" value="点击进入"onclick='window.open("/achievement/back/auditor/modules/${achId}","_self");'>
    
    </c:when>
    <c:otherwise>
    <input type="button" value="点击进入"onclick='window.open("/achievement/front/modules/${achId}","_self");'>
    
    
    </c:otherwise>
    </c:choose>
    
    
  </body>
</html>
