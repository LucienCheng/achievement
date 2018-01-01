<%@page import="com.entity.TestJson"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'test.jsp' starting page</title>
    <script type="text/javascript" src="js/jquery-3.2.1.js"></script>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  <script type="text/javascript">
          var saveDataAry=[];  
        var data1={"userName":"test","address":"gz"};  
        var data2={"userName":"ququ","address":"gr"};  
        saveDataAry.push(data1);  
        saveDataAry.push(data2); 
        console.log(saveDataAry);
  function testJson(){
 
  $.ajax({
  type:'post',
  url:'ajax/testJson',
  dataType:'json',
  contentType:"application/json",  
  data:JSON.stringify(saveDataAry),
  success:function(data,statue){
  console.log(data);
  console.log(statue);
  }
  });
  }
  
  
  </script>
  <body>
  <a href="javascript:void(0)" onclick="testJson()"> test</a>
  <form:form modelAttribute="testJson" method="post" action="ajax/testAdd">
  <form:input path="userName"/><form:errors path="userName"></form:errors><br>
  <form:input path="address"/>
  <input type="submit">
  </form:form>
 ${testJson }
  </body>
</html>
