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

<title>My JSP 'modifyAchievement.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!-- -------------------------------------添加的link------------------------------------------------------------------- -->
<link href="/achievement/source/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" media="screen" />

<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<!-- -------------------------------------添加的script------------------------------------------------------------------- -->

<script src="/achievement/source/vendors/jquery-1.9.1.min.js"></script>
	src="/achievement/source/bootstrap/js/bootstrap.js"></script>
<script
	src="/achievement/source/bootstrap/js/bootstrap.min.js"></script>
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript">
	Array.prototype.indexOf = function(val) {
		for (var i = 0; i < this.length; i++) {
			if (this[i] == val)
				return i;
		}
		return -1;
	};

	Array.prototype.remove = function(val) {
		var index = this.indexOf(val);
		if (index > -1) {
			this.splice(index, 1);
		}
	};
function deleteAch(achId) {

		saveDataAry=[];
		saveDataAry.push(achId);
		console.log(saveDataAry);
		$.ajax({
			type : 'post',
			url : '/achievement/back/user/achievement/delete',
			dataType : 'json',
			contentType : "application/json",
			data : JSON.stringify(saveDataAry),
			success : function(data, statue) {
				window.open("/achievement/back/user?achStatus=0", "_self");
			}
		});
	}
	saveModAry=[];
	function addCheck(modId) {
		saveModAry.push(modId);
		console.log(saveModAry);
	}
	function removeCheck(modId) {
		saveModAry.remove(modId);
		console.log(saveModAry);
	}
function deleteModule() {
		console.log(saveModAry);
		$.ajax({
			type : 'post',
			url : '/achievement/back/user/deleteModule',
			dataType : 'json',
			contentType : "application/json",
			data : JSON.stringify(saveModAry),
			success : function(data, statue) {
				window.open("/achievement/back/user/achievement/modify?achId="+'${achievement.achId }', "_self");
			}
		});
	}

</script>
</head>

<body>

	这是成果修改成果和模块设置页面
	<form action="/achievement/back/user/achievement/save"
		enctype="multipart/form-data" method="post">
		<input type="hidden" value="${achievement.achId }" name="achId">
		<input value="保存并退出" type="submit"> <input value="不保存退出"
			type="button"
			<c:if test="${operator == 'add'}">onclick="deleteAch('${achievement.achId }');" </c:if>
			<c:if test="${operator == 'modify'}">onclick="window.open('/achievement/back/user?achStatus=0','_self');"</c:if>><br>
		成果名字：<input value="${achievement.achName }" type="text" name="achName"><br>
		成果描述：<input value="${achievement.achDescribe }" type="text"
			name="achDescribe"><br> <img alt=""
			src="${achievement.achImagePath }"> 成果图片：<input type="file"
			name="image"><br>
		<video width="320" height="240" controls> <source
			src="${achievement.achVideoPath }" type="video/mp4"></video>
		成果视屏：<input type="file" name="video"><br> 成果分类： <select
			name="achClassify">
			<option value="科研">科研</option>
			<option value="生活">生活</option>
		</select>
	</form>




	<hr width="100%">
	下面是一个关于模块页面
	<button onclick="window.open('/achievement/back/user/addModule?achId='+'${achievement.achId }','_self');">添加模块</button>
	<button onclick="deleteModule();">删除模块</button>
	<table class="table table-hover">
		<thead>
			<tr>
			<th><input type="checkbox" /></th>
				<th>模块名称</th>
				<th colspan="2">模块描述</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="module" items="${modules }">

				<tr>
					<td><input type="checkbox"
onclick="if(this.checked == true) {addCheck('${module.modId }');} else { removeCheck('${module.modId }'); }" /></td>
					<td >${module.modName }</td>
					<td colspan="2">${module.modDescribe }</td>
					<td><button class="btn btn-warning addSlideShow"
							value='${module.modId }'
							onclick="window.open('/achievement/back/user/modifyModule?modId='+'${module.modId }','_self');">修改</button></td>
				</tr>
			</c:forEach>

		</tbody>
	</table>



</body>
</html>
