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

<link rel="stylesheet" type="text/css"
	href="/achievement/source/bootstrap-3.3.7-dist/css/bootstrap.css">

<script type="text/javascript" src="/achievement/source/jquery-3.2.1/jquery.js"></script>
<script type="text/javascript" src="/achievement/source/bootstrap-3.3.7-dist/js/bootstrap.js"></script>
</head>
<style type="text/css">
	body{
		background-image:url('/achievement/source/images/pic03.jpg');
		background-size:cover;
    	transition-duration:5s;
    	
	}
	body:hover{
		background-image:url('/achievement/source/images/pic04.jpg');
		background-size:cover;
	}	
</style>
<body>
<body>
	<div class="container-fluid col-md-12 col-sm-12">
		<div class="col-sm-3 col-md-3"></div>
		<div class="col-sm-6 col-md-6"
			style="padding: 0px;margin-top:75px;">
			
			<video controls autoplay class="col-md-12 col-sm-12" id="video1"> <source
				src="${video }"></source> </video>
		</div>
		<div class="col-sm-3 col-md-3"></div>
	</div>

	<script type="text/javascript">
		myVid=document.getElementById("video1");
		myVid.addEventListener('ended',function(){
        		if(test="${roleId == 1 }"){
        			setTimeout('window.open("/achievement/back/user/modules/${achId}","_self")',1000);
        			
        			}
        		else if(test="${roleId == 2 }"){
        			setTimeout('window.open("/achievement/back/auditor/modules/${achId}","_self")',1000);
        			
        			}
        		else{
        			setTimeout('window.open("/achievement/front/modules/${achId}","_self")',1000);
        			
        			}
    	});	
    	myVid.addEventListener('click',function(){
    			if(test="${roleId == 1 }"){
        			window.open("/achievement/back/user/modules/${achId}","_self");
        			}
        		else if(test="${roleId == 2 }"){
        			window.open("/achievement/back/auditor/modules/${achId}","_self");
        			}
        		else{
        			window.open("/achievement/front/modules/${achId}","_self");
        			}
    	});
	</script>


</body>
</html>
