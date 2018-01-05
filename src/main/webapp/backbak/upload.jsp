<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'upload.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!-- <script type="text/javascript" charset="utf-8"
	src="ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8"
	src="ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8"
	src="ueditor/lang/zh-cn/zh-cn.js"></script> -->
<script type="text/javascript" src="js/jquery-3.2.1.js"></script>
<style type="text/css">
div {
	width: 100%;
}
</style>
<!-- <script type="text/javascript">
 var ue = UE.getEditor('modContent');
</script> -->
<script >
function test1(){
				var form=new FormData(document.getElementById("test"));
				$.ajax({
 				url:'<c:url value="/ajax/test"/>',
                type:"post",
                data:form ,
                /* 执行执行的是dom对象 ，不需要转化信息*/
                processData:false,
                contentType:false,
                /* 指定返回类型为json */
                dataType:'json',
				success:function(data){
						$("#img").attr("src",data.image);
						console.log(data.image);
						},
				error:function(e){
						console.log("失败");
						}
						});

				}
</script>
</head>
<body>
	<!-- 导入功能 -->
	<%--    <form method="POST" action='<c:url value="/admin/importExcel"/>' enctype="multipart/form-data" >
           File to upload: <input type="file" name="file" accept=".xls,.xlsx"/>
           <input type="submit" value="Upload" > Press here to upload the file!
        </form>
        <br>
         <form method="get" action='<c:url value="/admin/exportModel"/>' enctype="multipart/form-data" >
           <input type="submit" value="download" > Press here to upload the file!
        </form> --%>
	<!-- 上传图片和视频 -->
	<%-- <form method="POST" action='<c:url value="/uploader/upload"/>' enctype="multipart/form-data" >
           image to upload: <input type="file" name="image" />
           video to upload: <input type="file" name="video" />
           <input type="submit" value="Upload" > Press here to upload the file!
        </form> --%>
	<!-- 一个模块的导入 -->
	<%-- <form method="post" action='<c:url value="/uploader/module/saveMod"/>'>
		<label>模块名称：</label><input type="text" name="modName"><br>
		<label>模块描述：</label><input type="text" name="modDescribe"><br>
		<label>模块内容：</label>
		<script id="modContent" type="text/plain"
			style="width:100%;height:700px;" name="modContent"></script>
		<input type="submit" value="提交"> Press here to upload the
		file!
	</form> --%>
	<!-- 测试ajax -->
	<form id="test">
		<label>角色id：</label><input type="text" name="roleId" value="1"><br>
		<label>角色名字：</label><input type="text" name="roleName" value="1"><br>
		<input type="file" name="image">
		<input type="button" onclick="test1()">
	</form>
<img alt="" src="" id="img">
</body>
</html>
