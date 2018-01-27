<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>编辑成果</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link href="/achievement/source/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" media="screen" />

<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<script type="text/javascript" src="/achievement/source/jquery-3.2.1/jquery-3.2.1.min.js"></script>
<script src="/achievement/source/bootstrap/js/bootstrap.js"></script>
<script src="/achievement/source/bootstrap/js/bootstrap.min.js"></script>
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
			function bounce(){
			var form=new FormData(document.getElementById("achievementForm"));
				$.ajax({
 				url:'/achievement/back/user/achievement/saveAjax',
                type:"post",
                data:form ,
                /* 执行执行的是dom对象 ，不需要转化信息*/
                processData:false,
                contentType:false,
                /* 指定返回类型为json */
                dataType:'json',
				success:function(){
						console.log("test");
						window.open(" /achievement/back/user/addModule?achId="+${achievement.achId },"_self");
						},
				error:function(e){
						console.log("失败");
						}
						});



}
</script>
</head>

<body>

	<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container-fluid">
				<a class="btn btn-navbar" data-toggle="collapse"
					data-target=".nav-collapse"> <span class="icon-bar"></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span>
				</a> <a class="brand" href="/achievement/front/index" target="_blank">首页</a>
				

			</div>
		</div>
	</div>
	<div style="height: 60px;"></div>
	<div class="container">
		<div class="col-md-10">
			<ul id="mytab" class="nav nav-tabs">
				<li class="active"><a href="#xw1" data-toggle='tab'>基本信息</a>
				</li>
				<li><a href="#xw2" data-toggle='tab'>栏目信息</a></li>
			</ul>
		</div>
		<div class="tab-content">
			<div class="tab-pane active fade in" id="xw1">

				<form action="/achievement/back/user/achievement/save"
					enctype="multipart/form-data" method="post" id="achievementForm">
					<div class="col-md-12">
						<span>成果名字：</span> <input value="${achievement.achName }"
							type="text" name="achName">
					</div>
					<div class="col-md-12" style="margin: 10px 0px 10px 0px;">
						<span>成果描述：</span> <input value="${achievement.achDescribe }"
							type="text" name="achDescribe">
					</div>
					<div>
						<img alt="" src="${achievement.achImagePath }">
						<sapn>成果图片：</sapn>
						<input type="file" name="image">
					</div>
					<div>
						<video width="320" height="240" controls> <source
							src="${achievement.achVideoPath }" type="video/mp4"></video>
						<span>成果视频：</span> <input type="file" name="video">
					</div>
					<div style="maigin: 10px 0px 10px 0px;">
						<span>成果分类：</span> <select name="achClassify">
							<option value="科研">科研</option>
							<option value="生活">生活</option>
						</select>
					</div>
					<input type="hidden" value="${achievement.achId }" name="achId">
					<input value="保存并退出" type="submit" class="btn btn-success">
					<input value="不保存退出" type="button" class="btn btn-warning"
						<c:if test="${operator == 'add'}"> onclick="deleteAch('${achievement.achId }');" </c:if>
						<c:if test="${operator == 'modify'}"> onclick="window.open('/achievement/back/user?achStatus=0','_self');"</c:if> >
				</form>
			</div>


			<div class="tab-pane fade in" id="xw2">

				<hr width="100%">
				下面是一个关于模块页面 <input type="button" value="添加模块" onclick="bounce();">
				<input type="button" value="删除模块" onclick="deleteModule();">
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
								<td>${module.modName }</td>
								<td colspan="2">${module.modDescribe }</td>
								<td><input type="button"
									class="btn btn-warning addSlideShow" value='修改'
									onclick="window.open('/achievement/back/user/modifyModule?achId=${achievement.achId }&modId='+'${module.modId }','_self');">
								</button></td>
							</tr>
						</c:forEach>

					</tbody>
				</table>
			</div>
		</div>
</body>
</html>
