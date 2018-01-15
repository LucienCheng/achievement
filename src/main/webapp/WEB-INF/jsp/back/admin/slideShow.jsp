<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta name="generator"
	content="HTML Tidy for HTML5 (experimental) for Windows https://github.com/w3c/tidy-html5/tree/c63cc39" />
<title>湖南大学成果展示系统</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<link href="/achievement/source/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" media="screen" />
<link
	href="/achievement/source/bootstrap/css/bootstrap-responsive.min.css"
	rel="stylesheet" media="screen" />
<link
	href="/achievement/source/vendors/easypiechart/jquery.easy-pie-chart.css"
	rel="stylesheet" media="screen" />
<link href="/achievement/source/assets/styles.css" rel="stylesheet"
	media="screen" />
<link
	href="/achievement/source/bootstrap/css/bootstrap-datetimepicker.css"
	rel="stylesheet" media="screen" />
<link
	href="/achievement/source/bootstrap/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet" media="screen" />

<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
            <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->
<script
	src="/achievement/source/vendors/modernizr-2.6.2-respond-1.1.0.min.js"></script>
<script src="/achievement/source/vendors/jquery-1.9.1.min.js"></script>
<script src="/achievement/source/bootstrap/js/bootstrap.min.js"></script>
<script
	src="/achievement/source/vendors/easypiechart/jquery.easy-pie-chart.js"></script>
<script src="/achievement/source/assets/scripts.js"></script>
<script
	src="/achievement/source/bootstrap/js/bootstrap-datetimepicker.js"></script>
<script
	src="/achievement/source/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
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
			url : '/achievement/back/admin/slideShow/delete',
			dataType : 'json',
			contentType : "application/json",
			data : JSON.stringify(saveDataAry),
			success : function(data, statue) {
				window.open("/achievement/back/admin/slideShow", "_self");
			}
		});
	}
	//获取子窗口的元素
	$(function() {
		$("#myIframe").load(function() {
			$("#myIframe").contents().find(".addSlideShow").click(function() {
				console.log($(this).val());
				window.open("/achievement/back/admin/slideShow/add?achId="+$(this).val(),"_self");
			});
		});

	});
</script>
</head>
<body>
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
		<div class="row-fluid">
			<div class="span2" id="sidebar">
				<ul class="nav nav-list bs-docs-sidenav nav-collapse collapse">
					<li><a href="/achievement/back/admin">用户管理</a></li>
					<li><a href="/achievement/back/admin/personInfo">个人消息修改</a></li>
					<li class="active"><a href="javascript:void(0);">设置轮播图</a></li>
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
					<button class="btn btn-danger " onclick="deleteUser();">删除</button>
					<button class="btn btn-primary " data-toggle="modal"
						data-target="#myModal">添加</button>
				</div>
				<div class="row-fluid">
					<!-- block -->
					<div class="block">
						<div class="navbar navbar-inner block-header">
							<div class="muted pull-left">成果</div>
							<div class="pull-right">
								<span class="badge badge-info">${fn:length(achievements) }</span>
							</div>
						</div>
						<div class="block-content collapse in">
							<div class="span12">
								<table class="table table-hover">
									<thead>
										<tr>
											<th><input type="checkbox" /></th>
											<th>成果名称</th>
											<th colspan="2">成果内容</th>
											<th>作者</th>
											<th>时间</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="achievement" items="${achievements }">

											<tr>
												<td><input type="checkbox"  onclick="if(this.checked == true) {addCheck('${achievement.achId }');} else { removeCheck('${achievement.achId }'); }" /></td>
												<td>${achievement.achName }</td>
												<td class="media" colspan="2"><a class="pull-left"
													href="#"> <img class="media-object"
														src='/achievement/${achievement.achImagePath }' />
												</a>
													<div class="media-body">${achievement.achDescribe }</div></td>
												<td>${achievement.user.userName }</td>
												<td>${achievement.achDate }</td>
											</tr>
										</c:forEach>

									</tbody>
								</table>
							</div>
							</tbody>
							</table>
						</div>
					</div>
				</div>

			</div>
			<!-- /block -->
		</div>

	</div>
	</div>
	</div>







	<!-- 模态框（Modal） -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true"  
		style="display:none;width:80%; left:30%;">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">选择首页轮播图</h4>
				</div>
				<div class="modal-body" style="overflow-y:hidden;">
					<iframe id="myIframe"src="/achievement/back/admin/slideShow/search/1"  width="100%" height="100%"></iframe>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary">提交更改</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>
</body>
</html>
