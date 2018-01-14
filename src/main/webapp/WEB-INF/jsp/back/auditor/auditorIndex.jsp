<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
<script src="/achievement/source/assets/scripts.js"></script>
<script
	src="/achievement/source/bootstrap/js/bootstrap-datetimepicker.js"></script>
<script
	src="/achievement/source/bootstrap/js/bootstrap-datetimepicker.min.js"></script>

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
					<li class="active"><a href="#">待审核 <span
							class="badge badge-important  pull-right">17</span></a></li>
					<li><a href="#">审核通过 <span class="badge  pull-right">17</span></a>
					</li>
					<li><a href="#">审核未通过 <span
							class="badge badge-important  pull-right">17</span></a></li>
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
					<button class="btn btn-primary ">全部通过</button>
					<button class="btn btn-warning ">未通过</button>


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
								<label style="display:inline">成果名字</label> <input type="text"
									style="margin-bottom:0px;width:150px" placeholder="成果名字" /> <label
									style="display:inline">成果作者</label> <input type="text"
									style="margin-bottom:0px;width:150px" placeholder="成果作者" /> <label
									style="display:inline">起止时间--</label> <label
									style="display:inline">终止时间</label>
								<div class="input-append date form_datetime "
									data-date="2013-02-21T15:25:00Z" style="margin-bottom:0px;">

									<input size="16" type="text" value="" readonly
										style="margin-bottom:0px;width:100px"> <span
										class="add-on"><i class="icon-remove"></i></span> <span
										class="add-on"><i class="icon-calendar"></i></span>
								</div>
								<label style="display:inline">--</label>
								<div class="input-append date form_datetime "
									data-date="2013-02-21T15:25:00Z" style="margin-bottom:0px;">

									<input size="16" type="text" value="" readonly
										style="margin-bottom:0px;width:100px"> <span
										class="add-on"><i class="icon-remove"></i></span> <span
										class="add-on"><i class="icon-calendar"></i></span>
								</div>
								<script type="text/javascript">
									$(".form_datetime").datetimepicker({
										format : "dd MM yyyy - hh:ii",
										autoclose : true,
										todayBtn : true,
										startDate : "2013-02-14 10:00",
										minuteStep : 10
									});
								</script>

								<button class="btn btn-default" type="button">搜索</button>
							</div>
						</div>
					</div>

					<!-- block -->
					<div class="block">
						<div class="navbar navbar-inner block-header">
							<div class="muted pull-left">成果</div>
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
											<th>成果</th>
											<th>作者</th>
											<th>时间</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td><input type="checkbox" /></td>
											<td class="media"><a class="pull-left" href="#"> <img
													class="media-object" src="images/下载.png" />
											</a>
												<div class="media-body">
													<h4 class="media-heading">肖申克的救赎 The Shawshank
														Redemption (1994)</h4>
													银行家安迪因被当作杀害妻子与情夫的凶手，被判终身监禁。安迪在监狱中一方面帮监狱长做假账，一方面精心策划了一出越狱好戏。
												</div></td>
											<td>author</td>
											<td colspan="3">银行家安迪因被当作杀害妻子与情夫的凶手，被判终身监禁</td>
										</tr>
										<tr>
											<td><input type="checkbox" /></td>
											<td class="media"><a class="pull-left" href="#"> <img
													class="media-object" src="images/下载.png" />
											</a>
												<div class="media-body">
													<h4 class="media-heading">肖申克的救赎 The Shawshank
														Redemption (1994)</h4>
													银行家安迪因被当作杀害妻子与情夫的凶手，被判终身监禁。安迪在监狱中一方面帮监狱长做假账，一方面精心策划了一出越狱好戏。
												</div></td>
											<td>author</td>
											<td>time</td>
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
		${achievements } ${totalCount } <br>
		审核人员的首页默认是展示待审核的界面，可以执行批量通过，单个不通过并且编写建议。
</body>
</html>

