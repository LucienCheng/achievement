<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
					var achStatus = '${condition.achStatus}';
					var achStartTime = '${condition.achStartTime}';
					var achEndTime = '${condition.achEndTime}';
					var u = "/achievement/back/auditor/" + rel + "?achName="
							+ achName + "&achStartTime=" + achStartTime
							+ "&achEndTime=" + achEndTime + "&achStatus="
							+ achStatus;
					if (rel != undefined) {
						window.open(u, "_self");

					}
				});

	});

	var m = new Array();
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
	function addCheck(achId) {
		saveDataAry.push(achId);
		console.log(saveDataAry);
	}
	function removeCheck(achId) {
		saveDataAry.remove(achId);
		console.log(saveDataAry);
	}

	function passAchievement() {
		$.ajax({
			type : 'post',
			url : '/achievement/back/auditor/pass',
			dataType : 'json',
			contentType : "application/json",
			data : JSON.stringify(saveDataAry),
			success : function(data, statue) {
				window.open('/achievement/back/auditor/${curPage}?achStatus='
						+ '${condition.achStatus}', "_self");
			}
		});
	}
	function addAudit(achId) {
		console.log(achId);
		$("#achId").val(achId);
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
				<div class="nav-collapse collapse">
					<ul class="nav pull-right">
						<li><a href="/achievement/back/loginOut">登出</a></li>
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
					<li
						<c:if test="${condition.achStatus == 0}"> class="active" </c:if>><a
						href="/achievement/back/auditor/1?achStatus=0">待审核</a></li>
					<li
						<c:if test="${condition.achStatus == 1}"> class="active" </c:if>><a
						href="/achievement/back/auditor/audited/1?achStatus=1">审核通过 </a></li>
					<li
						<c:if test="${condition.achStatus == 2}"> class="active" </c:if>><a
						href="/achievement/back/auditor/audited/1?achStatus=2">审核未通过 </a></li>
					<li><a href="/achievement/back/auditor/personInfo">个人资料修改</a></li>
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
					<c:if test="${condition.achStatus == 0}">
						<button class="btn btn-primary " onclick="passAchievement();">通过</button>
					</c:if>

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
							<form class="input-group pull-left block-content"
								<c:if test="${condition.achStatus == 0}"> action="/achievement/back/auditor/1"</c:if>
								<c:if test="${condition.achStatus == 1}"> action="/achievement/back/auditor/audited/1?achStatus=1"</c:if>
								<c:if test="${condition.achStatus == 2}"> action="/achievement/back/auditor/audited/1?achStatus=2"</c:if>>
								<label style="display:inline">成果名字</label> <input type="text"
									style="margin-bottom:0px;width:150px" placeholder="成果名字"
									name="achName" value='${condition.achName }' /> <label
									style="display:inline">起止时间--</label> <label
									style="display:inline">终止时间</label>
								<div class="input-append date form_datetime "
									data-date="2017-12-31" style="margin-bottom:0px;">

									<input size="16" type="text" readonly
										style="margin-bottom:0px;width:100px" name="achStartTime"
										value='${condition.achStartTime }'> <span
										class="add-on"><i class="icon-remove"></i></span> <span
										class="add-on"><i class="icon-calendar"></i></span>
								</div>
								<label style="display:inline">--</label>
								<div class="input-append date form_datetime "
									data-date="2017-12-31" style="margin-bottom:0px;">

									<input size="16" type="text" readonly
										style="margin-bottom:0px;width:100px" name="achEndTime"
										value='${condition.achEndTime }'> <span class="add-on"><i
										class="icon-remove"></i></span> <span class="add-on"><i
										class="icon-calendar"></i></span>
								</div>
								<input type="hidden" value='${condition.achStatus}'
									name="achStatus">
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
							</form>
						</div>
					</div>
				</div>
				<div class="row-fluid">
					<!-- block -->
					<div class="block">
						<div class="navbar navbar-inner block-header">
							<div class="muted pull-left">成果</div>
							<div class="pull-right">
								<span class="badge badge-info">${totalCount }</span>
							</div>
						</div>
						<div class="block-content collapse in">
							<div class="span12">
								<table class="table table-hover">
									<thead>
										<tr>
											<c:if test="${condition.achStatus == 0}">
												<th><input type="checkbox" /></th>
											</c:if>
											<th>成果名称</th>
											<th colspan="2">成果内容</th>
											<th>时间</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="achievement" items="${achievements }">

											<tr>
												<c:if test="${condition.achStatus == 0}">
													<td><input type="checkbox"
														onclick="if(this.checked == true) {addCheck(${achievement.achId });} else { removeCheck(${achievement.achId }); }" /></td>
												</c:if>
												<td>${achievement.achName }</td>
												<td class="media" colspan="2"><a class="pull-left"
													href="#"> <img class="media-object"
														src='/achievement/${achievement.achImagePath }' />
												</a>
													<div class="media-body">${achievement.achDescribe }</div></td>
												<td>${achievement.achDate }</td>

												<td><c:if test="${condition.achStatus == 0 }">
														<button class="btn btn-warning addSlideShow"
															data-toggle="modal" data-target="#myModal"
															onclick='addAudit(${achievement.achId });'>未通过</button>
													</c:if>
													<button class="btn btn-primative addSlideShow"
														 onclick="window.open('/achievement/back/auditor/${achievement.achId }/video');"
														value='${achievement.achId }'>预览</button></td>


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

		</div>
	</div>


	<!-- 模态框（Modal） -->
	<form class="modal fade" id="myModal" tabindex="-1" role="dialog"
		method="post" aria-labelledby="myModalLabel" aria-hidden="true"
		style="display: none;" action="/achievement/back/auditor/unpass">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">模态框（Modal）标题</h4>
				</div>
				<div class="modal-body">
					修改建议：
					<textarea rows="3" cols="20" name="opinion"></textarea>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<input type="hidden" id="achId" name="achId"> <input
						type="submit" class="btn btn-primary" value="提交更改">
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</form>
</body>
</html>
