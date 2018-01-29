<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>轮播图搜索</title>
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
	$(function() {

		var curPage = ${curPage};
		var totalPage = ${totalPage};
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
					s += "<li class='active'><a href='javascript:void(0)'>" + i
							+ "</a></li>";
				} else {
					s += "<li ><a href='javascript:void(0)'rel=" + i + ">" + i
							+ "</a></li>";
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
					s += "<li class='active'><a href='javascript:void(0)'>" + i
							+ "</a></li>";
				} else {
					s += "<li ><a href='javascript:void(0)'rel=" + i + ">" + i
							+ "</a></li>";
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
					var achName = '${condition.achName}';
					var authorName = '${condition.authorName}';
					var achStartTime = '${condition.achStartTime}';
					var achEndTime = '${condition.achEndTime}';
					var u = "/achievement/back/admin/slideShow/search/" + rel + "?achName="
							+ achName + "&authorName=" + authorName+ "&achStartTime=" + achStartTime
							+ "&achEndTime=" + achEndTime;
					if (rel != undefined) {
						window.open(u, "_self");

					}
				});

	});
</script>
</head>

<body>
	<div class="row-fluid ">
		<div class="collapse in" id="collapseExample">
			<div
				style="background-color:#fff;border:1px solid #d4d4d4;height:auto;"
				class="block pull-left span12">
				<form class="input-group pull-left block-content" >
					<label style="display:inline">成果名字</label> <input type="text"
						style="margin-bottom:0px;width:150px" placeholder="成果名字" name="achName" value='${condition.achName }'/> <label
						style="display:inline">成果作者</label> <input type="text"
						style="margin-bottom:0px;width:150px" placeholder="成果作者" name="authorName"value='${condition.authorName }'/> <label
						style="display:inline">起止时间--</label> <label
						style="display:inline">终止时间</label>
					<div class="input-append date form_datetime "
						data-date="2017-12-31" style="margin-bottom:0px;">

						<input size="16" type="text"  readonly
							style="margin-bottom:0px;width:100px" name="achStartTime"value='${condition.achStartTime }'> <span
							class="add-on"><i class="icon-remove"></i></span> <span
							class="add-on"><i class="icon-calendar"></i></span>
					</div>
					<label style="display:inline">--</label>
					<div class="input-append date form_datetime "
						data-date="2017-12-31" style="margin-bottom:0px;">

						<input size="16" type="text"  readonly
							style="margin-bottom:0px;width:100px" name="achEndTime"value='${condition.achEndTime }'> <span
							class="add-on"><i class="icon-remove"></i></span> <span
							class="add-on"><i class="icon-calendar"></i></span>
					</div>
					<script type="text/javascript">
						$(".form_datetime").datetimepicker({
							format : "yyyy-mm-dd hh:ii:ss",
							autoclose : true,
							todayBtn : true,
							startDate : "2017-01-31 10:00",
							minuteStep : 10
						});
					</script>
					<button class="btn btn-default" type="submit">搜索</button>
				</div>
			</form>
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
									<th>成果名称</th>
									<th colspan="2">成果内容</th>
									<th>作者</th>
									<th>时间</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="achievement" items="${achievements }">

									<tr>
										<td>${achievement.achName }</td>
										<td class="media" colspan="2"><a class="pull-left"
											href="#"> <img class="media-object"
												src='/achievement/${achievement.achImagePath }' />
										</a>
											<div class="media-body">${achievement.achDescribe }</div></td>
										<td>${achievement.user.userName }</td>
										<td>${achievement.achDate }</td>
										<td><button class="btn btn-primary addSlideShow"
												value='${achievement.achId }'>添加</button>
												<button class="btn  "
														 onclick="window.open('/achievement/back/admin/modules/${achievement.achId }');"
														value='${achievement.achId }'>预览</button>
												</td>
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
		</div>
	</div>

	${achievementsSearch }
</body>
</html>
