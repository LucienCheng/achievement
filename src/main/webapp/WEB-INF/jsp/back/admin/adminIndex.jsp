
<%@page import="com.entity.User"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE >
<html>
<head>
<meta name="generator"
	content="HTML Tidy for HTML5 (experimental) for Windows https://github.com/w3c/tidy-html5/tree/c63cc39" />
<title>湖南大学成果展示系统</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<link href="/achievement/source/bootstrap/css/bootstrap.min.css" rel="stylesheet"
	media="screen" />
<link href="/achievement/source/bootstrap/css/bootstrap-responsive.min.css"
	rel="stylesheet" media="screen" />
<link href="/achievement/source/vendors/easypiechart/jquery.easy-pie-chart.css"
	rel="stylesheet" media="screen" />
<link href="/achievement/source/assets/styles.css" rel="stylesheet" media="screen" />
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
            <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->
<script src="/achievement/source/vendors/modernizr-2.6.2-respond-1.1.0.min.js"></script>
<script src="/achievement/source/vendors/jquery-1.9.1.min.js"></script>
<script src="/achievement/source/bootstrap/js/bootstrap.min.js"></script>
<script src="/achievement/source/vendors/easypiechart/jquery.easy-pie-chart.js"></script>
<script src="/achievement/source/assets/scripts.js"></script>



<script type="text/javascript">

	var m = new Array();
	m = ${userJson};
	$(function() {
		var curPage=${curPage};
		var totalPage=${totalPage};
		var s = "<ul>";
		if (${curPage} == 1)
			s += "<li class='active'><a href='javascript:void(0) '>上一页</a></li>";
		else {
			s += "<li ><a  href='javascript:void(0)'rel=" + (${curPage} - 1)
					+ ">上一页</a></li>";
		}
		
			//如果总的页数在6页只能就可以这样
		if (totalPage <= 6) {
			for (var i = 1; i <= totalPage; i++) {
				if (${curPage} == i) {
				s += "<li class='active'><a href='javascript:void(0)'>" + i+ "</a></li>";
			} else {
				s += "<li ><a href='javascript:void(0)'rel=" + i + ">" + i+ "</a></li>";
			}
			}

		}
		//这个是页面大于6的时候
		else {
			var base = 0;
			if ((curPage - 3 >= 0) && (curPage + 3 <= totalPage))
				base = curPage - 3;
			else if ((curPage + 3) > totalPage) {

				base = totalPage - 6;

			}

			for (var i = base + 1; i <= base + 6; i++) {
				if (${curPage} == i) {
				s += "<li class='active'><a href='javascript:void(0)'>" + i+ "</a></li>";
			} else {
				s += "<li ><a href='javascript:void(0)'rel=" + i + ">" + i+ "</a></li>";
			}
			}
		}
		
		if (${curPage} >= ${totalPage})
			s += "<li class='active'><a href='javascript:void(0)'>下一页</a></li>";
		else {
			s += "<li ><a  href='javascript:void(0)'rel=" + (${curPage} + 1)
					+ ">下一页</a></li>";
		}

		s += "</ul>";
		$("#pagecount").html(s);

		$("#pagecount ul li a").bind(
				'click',
				function() {

					var rel = $(this).attr("rel");
					var userWorkNum = '${userCondition.userWorkNum}';
					var userName = '${userCondition.userName}';
					var u = "/achievement/back/admin/" + rel + "?userWorkNum="
							+ userWorkNum + "&userName=" + userName;
					if (rel != undefined) {
						window.open(u, "_self");

					}
				});
	});

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
	var saveDataAry = [];
	function addCheck(userId) {
		saveDataAry.push(userId);
		console.log(saveDataAry);
	}
	function removeCheck(userId) {
		saveDataAry.remove(userId);
		console.log(saveDataAry);
	}
	function deleteUser() {
		$.ajax({
			type : 'post',
			url : '/achievement/back/admin/deleteUser/',
			dataType : 'json',
			contentType : "application/json",
			data : JSON.stringify(saveDataAry),
			success : function(data, statue) {
				window.open("/achievement/back/admin/", "_self");
			}
		});

	}
</script>
</head>
<body>

</div>	
	<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container-fluid">
				<a class="btn btn-navbar" data-toggle="collapse"
					data-target=".nav-collapse"> <span class="icon-bar"></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span>
				</a> <a class="brand" href="#">首页</a>
				<div class="nav-collapse collapse">
					<ul class="nav pull-right">
						<li><a href="#">预览</a></li>
						<li><a href="#">登出</a></li>
					</ul>
				</div>
				<!--/.nav-collapse -->
			</div>
		</div>
	</div>
	<div class="container-fluid">
		<div class="row-fluid span12">
			<div class="span2" id="sidebar">
				<ul class="nav nav-list bs-docs-sidenav nav-collapse collapse">
					<li class="active"><a href="javascript:void(0);">用户管理</a></li>
					<li><a href="/achievement/back/admin/personInfo">个人消息修改</a></li>
					<li><a href="/achievement/back/admin/slideShow">设置轮播图</a></li>
				</ul>
			</div>
			<!--/span-->
			<div class="span10" id="content">
				<div class="row-fluid">
					<div class="alert alert-success alert-dismissable">
						<button type="button" class="close" data-dismiss="alert">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4>成功提示</h4>
						操作已经成功了！
					</div>
				</div>
				<div class="row-fluid">
					<button class="btn btn-danger " onclick="deleteUser()">删除</button>
					<button class="btn btn-primary " data-toggle="modal" data-target="#add">添加</button>
					
					<!-- /input-group -->
					<form id="import" style="display:inline" action="/achievement/back/admin/importExcel"
						method="post" enctype="multipart/form-data">
						<input id="lefile" type="file" style="display:none" name="file">
						<div class="input-append" style="margin-bottom:0px;">
							<input id="photoCover" class="input-large" type="text"
								disabled="disabled"> <a class="btn"
								onclick="$('input[id=lefile]').click();">选择</a> <input
								value="批量导入" class="btn" type="submit">
					</form>

					<script type="text/javascript">
						$('input[id=lefile]').change(function() {
							$('#photoCover').val($(this).val());
						});
					</script>
				</div>

				<a class="btn  " type="button" href="admin/exportModel"> 导入模板下载</span>
				</a>
				<button class="btn  " type="button" data-toggle="collapse"
					data-target="#collapseExample" aria-expanded="true"
					aria-controls="collapseExample">
					检索条件<span class="caret pull-right"></span>
				</button>
			</div>
			<div class="row-fluid ">
				<div class="collapse in" id="collapseExample">
					<div
						style="background-color:#fff;border:1px solid #d4d4d4;height:auto;"
						class="block pull-left span12">
						<form class="input-group pull-left block-content" id="search" action="/achievement/back/admin">
						
							<label style="display:inline">用户名</label> <input type="text"
								style="margin-bottom:0px;" placeholder="输入用户名" name="userName"
								id="searchUserName" value='${userCondition.userName }'/> <label style="display:inline" >工号</label>
							<input type="text" style="margin-bottom:0px;" placeholder="输入工号"
								id="searchWorkNum" name="userWorkNum" value='${userCondition.userWorkNum}'/>
							<input class="btn btn-default" type="submit" value="搜索">
						</form>
					</div>
				</div>
				<div class="row-fluid">
					<!-- block -->
					<div class="block">
						<div class="navbar navbar-inner block-header">
							<div class="muted pull-left">用户管理</div>
							<div class="pull-right">
								<span class="badge badge-info">${totalCount }</span>
							</div>
						</div>
						<div class="block-content collapse in">
							<div class="span12">
								<table class="table table-hover">
									<thead>
										<tr>
											<th><input type="checkbox" /></th>
											<th>工号</th>
											<th>用户名</th>
											<th>电话</th>
											<th>角色</th>
											<th>职称</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="user" items="${users }">
											<tr>
												<td><input type="checkbox"  onclick="if(this.checked == true) {addCheck('${user.userId }');} else { removeCheck('${user.userId }'); }" /></td>
												<td>${user.userWorkNum }</td>
												<td>${user.userName }</td>
												<td>${user.userPhone }</td>
												<td>${user.role.roleName }</td>
												<td>${user.userPos }</td>
												<td><button class="btn btn-warning " data-toggle="modal" data-target="#modify" onclick="modify('${user.userId}')">修改</button></td>
											</tr>

										</c:forEach>

									</tbody>
								</table>
							</div>
							<div class="span12">
								<div class="pagination" id="pagecount"></div>
							</div>
						</div>
					</div>
					<!-- /block -->
				</div>
			</div>
		</div>
	</div>
</div>


<div class="modal fade " id="add" aria-labelledby="add" aria-hidden="true" style="display:none;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">
					用户添加
				</h4>
			</div>
			<form class="form-horizontal" action="/achievement/back/admin/addUser" method="post">
			<div class="modal-body">
								
										
										<div class="control-group">
											<label class="control-label" for="focusedInput">工号</label>
											<div class="controls">
												<input class="input-xlarge focused" id="focusedInput"
													type="text" placeholder="输入工号"  name="userWorkNum">
											</div>
										</div>
										<div class="control-group">
											<label class="control-label" for="focusedInput">密码</label>
											<div class="controls">
												<input class="input-xlarge focused" id="focusedInput"
													type="text" placeholder="输入密码" name="userPassword">
											</div>
										</div>
										<div class="control-group">
											<label class="control-label" for="focusedInput">手机号</label>
											<div class="controls">
												<input class="input-xlarge focused" id="focusedInput"
													type="text" placeholder="输入手机号"  name="userPhone">
											</div>
										</div>
										<div class="control-group">
											<label class="control-label" for="focusedInput">名字</label>
											<div class="controls">
												<input class="input-xlarge focused" id="focusedInput"
													type="text" placeholder="输入名字" name="userName">
											</div>
										</div>
										<div class="control-group">
											<label class="control-label" for="focusedInput">用户角色</label>
											<div class="controls">
												<select id="selectError" name="role.roleId">
													<option value="1" selected>成果上传者</option>
													<option value="2" >成果审核员</option>
													<option value="3" >系统管理员</option>
												</select>
											</div>
										</div>


										<div class="control-group">
											<label class="control-label" >性别</label>
											<div class="controls" >
											
											<label> <input type="radio" name="userSex" value="1" checked>
													男 <input type="radio" name="userSex" value="0">
													女
												</label>
											</div>
										</div>

										<div class="control-group ">
											<label class="control-label" for="selectError">职称</label>
											<div class="controls">
											
												<select id="selectError" name="userPos">
													<option value="教授" selected>教授</option>
													<option value="副教授"  >副教授</option>
													<option value="助理教授">助理教授</option>
													<option value="讲师" >讲师</option>
												</select>
											</div>
										</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭
				</button>
				<input class="btn btn-primary" value="保存" type="submit">
			</div>
			</form>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
	
	
	<!-- 修改用户的模态框 -->
	
	
<div class="modal fade " id="modify"   role="dialog"  aria-labelledby="modify" aria-hidden="true" style="display:none;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">
					用户修改
				</h4>
			</div>
			<form class="form-horizontal" action="/achievement/back/admin/saveUser" method="post">
			<div class="modal-body">
								
										
										<div class="control-group">
											<label class="control-label" for="focusedInput">工号</label>
											<div class="controls">
												<input class="input-xlarge focused" id="mUserWorkNum"
													type="text" placeholder="输入工号"  name="userWorkNum">
											</div>
										</div>
										<div class="control-group">
											<label class="control-label" for="focusedInput">密码</label>
											<div class="controls">
												<input class="input-xlarge focused" id="mUserPassword"
													type="text" placeholder="输入密码" name="userPassword">
											</div>
										</div>
										<div class="control-group">
											<label class="control-label" for="focusedInput">手机号</label>
											<div class="controls">
												<input class="input-xlarge focused" id="mUserPhone"
													type="text" placeholder="输入手机号"  name="userPhone">
											</div>
										</div>
										<div class="control-group">
											<label class="control-label" for="focusedInput">名字</label>
											<div class="controls">
												<input class="input-xlarge focused" id="mUserName"
													type="text" placeholder="输入名字" name="userName">
											</div>
										</div>
										<div class="control-group">
											<label class="control-label" for="focusedInput">用户角色</label>
											<div class="controls">
												<select id="roleId" name="role.roleId">
													<option value="1" selected>成果上传者</option>
													<option value="2" >成果审核员</option>
													<option value="3" >系统管理员</option>
												</select>
											</div>
										</div>


										<div class="control-group">
											<label class="control-label" >性别</label>
											<div class="controls" >
											
											<label> <input id="male"type="radio" name="userSex" value="1" >
													男 <input id="feMale"type="radio" name="userSex" value="0" >
													女
												</label>
											</div>
										</div>

										<div class="control-group ">
											<label class="control-label" for="selectError">职称</label>
											<div class="controls">
											
												<select id="mUserPos" name="userPos">
													<option value="教授" >教授</option>
													<option value="副教授"  >副教授</option>
													<option value="助理教授">助理教授</option>
													<option value="讲师" >讲师</option>
												</select>
											</div>
										</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭
				</button>
				<input type="hidden" id="mUserId" name="userId">
				<input class="btn btn-primary" value="保存" type="submit">
			</div>
			</form>
			
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
<script type="text/javascript">

function modify(userId){
for(var i=0;i<m.length;i++){
if(m[i].userId==userId){
$("#mUserWorkNum").val(m[i].userWorkNum);
$("#mUserPassword").val(m[i].userPassword);
$("#mUserPhone").val(m[i].userPhone);
$("#mUserName").val(m[i].userName);

$("#roleId").val(m[i].role.roleId);
if(m[i].userSex=='1'){

$("#feMale").prop("checked",false);
$("#male").prop("checked","checked");

}
else{
$("#male").prop("checked",false);
$("#feMale").prop("checked","checked");

}
$("#mUserId").val(m[i].userId);
$("#mUserPos").val(m[i].userPos);
}








}



}
</script>
</body>



</html>
