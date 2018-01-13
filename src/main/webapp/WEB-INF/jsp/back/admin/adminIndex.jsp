<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<link href="../source/bootstrap/css/bootstrap.min.css" rel="stylesheet"
	media="screen" />
<link href="../source/bootstrap/css/bootstrap-responsive.min.css"
	rel="stylesheet" media="screen" />
<link href="../source/vendors/easypiechart/jquery.easy-pie-chart.css"
	rel="stylesheet" media="screen" />
<link href="../source/assets/styles.css" rel="stylesheet" media="screen" />
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
            <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->
<script src="../source/vendors/modernizr-2.6.2-respond-1.1.0.min.js"></script>
<script src="../source/vendors/jquery-1.9.1.min.js"></script>
<script src="../source/bootstrap/js/bootstrap.min.js"></script>
<script src="../source/vendors/easypiechart/jquery.easy-pie-chart.js"></script>
<script src="../source/assets/scripts.js"></script>

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
					<li class="active"><a href="admin.html">用户管理</a></li>
					<li><a href="personInfo.html">个人消息修改</a></li>
					<li><a href="#">设置轮播图</a></li>
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
					<button class="btn btn-danger ">删除</button>
					<button class="btn btn-primary ">添加</button>
					<button class="btn btn-warning ">修改</button>
					<!-- /input-group -->
					<form id="import"style="display:inline" action="admin/importExcel"method="post" enctype="multipart/form-data" >
						<input id="lefile" type="file" style="display:none" name="file">
						<div class="input-append" style="margin-bottom:0px;">
							<input id="photoCover" class="input-large" type="text"
								disabled="disabled"> 
								<a class="btn"
								onclick="$('input[id=lefile]').click();">选择</a>
								<input value="批量导入" class="btn" type="submit">
						</form>

<script type="text/javascript">  
$('input[id=lefile]').change(function() {  
$('#photoCover').val($(this).val());  
});  
</script>
					</div>
					
					<a class="btn  " type="button" href="admin/exportModel">
						导出模板下载</span>
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
							<div class="input-group pull-left block-content">
								<label style="display:inline">用户名</label> <input type="text"
									style="margin-bottom:0px;" placeholder="输入用户名" /> <label
									style="display:inline">工号</label> <input type="text"
									style="margin-bottom:0px;" placeholder="输入工号" />
								<button class="btn btn-default" type="button">搜索</button>
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<!-- block -->
						<div class="block">
							<div class="navbar navbar-inner block-header">
								<div class="muted pull-left">用户管理</div>
								<div class="pull-right">
									<span class="badge badge-info">17</span>
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
												<th>学院</th>
												<th>角色</th>
												<th>职称</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td><input type="checkbox" /></td>
												<td>Mark</td>
												<td>Otto</td>
												<td>@mdo</td>
												<td>Mark</td>
												<td>Otto</td>
												<td>@mdo</td>
											</tr>
											<tr>
												<td><input type="checkbox" /></td>
												<td>Mark</td>
												<td>Otto</td>
												<td>@mdo</td>
												<td>Mark</td>
												<td>Otto</td>
												<td>@mdo</td>
											</tr>
											<tr>
												<td><input type="checkbox" /></td>
												<td>Mark</td>
												<td>Otto</td>
												<td>@mdo</td>
												<td>Mark</td>
												<td>Otto</td>
												<td>@mdo</td>
											</tr>
										</tbody>
									</table>
								</div>
								<div class="span12">
									<div class="pagination">
										<ul>
											<li><a href="#">Prev</a></li>
											<li class="active"><a href="#">1</a></li>
											<li><a href="#">2</a></li>
											<li><a href="#">3</a></li>
											<li><a href="#">4</a></li>
											<li><a href="#">Next</a></li>
										</ul>
									</div>
								</div>
							</div>
						</div>
						<!-- /block -->
					</div>
				</div>
			</div>
		</div>


		${userId } ${roleId } ${totalCount } ${users }
		这里是管理员界面，会有ajax请求查询用户。/back/admin/{start}这个链接请求数据，获取到json。然后重新表格。
</body>



</html>
