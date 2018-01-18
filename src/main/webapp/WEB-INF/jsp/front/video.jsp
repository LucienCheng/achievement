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
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  <video width="320" height="240" controls> 
			<source src="${video }" type="video/mp4"></video>

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
