<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

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

</head>

<body>
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
		<div class="row-fluid">
			<!-- block -->
			<div class="block">
				<div class="navbar navbar-inner block-header">
					<div class="muted pull-left">成果</div>
					<div class="pull-right">
						<span class="badge badge-info"></span>
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
										<td><input type="checkbox" /></td>
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

		${achievementsSearch }
</body>
</html>
