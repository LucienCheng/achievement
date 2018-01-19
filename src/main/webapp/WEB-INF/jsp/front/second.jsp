<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'second.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<script src="/achievement/source/vendors/jquery-1.9.1.min.js"></script>
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript">

function getUrl(){
var url = document.location.toString();
var arrUrl=url.split("/achievement");
return arrUrl[1];
}
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
</script>
</head>
<body>
	<c:if test="${error == 'error' }">
  访问的成果已经更新，不能查看
  </c:if>
	<hr width="100%">
	<table class="table table-hover">
		<thead>
			<tr>
				<c:if test="${condition.achStatus == 0}">
					<th><input type="checkbox" /></th>
				</c:if>
				<th>成果名称</th>
				<th colspan="2">成果内容</th>
				<th>时间</th>
				<th>作者</th>
				<th>点击量</th>
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
					<td class="media" colspan="2"><a class="pull-left" href="#">
							<img class="media-object"
							src='/achievement/${achievement.achImagePath }' />
					</a>
						<div class="media-body">${achievement.achDescribe }</div></td>
					<td>${achievement.achDate }</td>
					<td>${achievement.user.userName }</td>
					<td>${achievement.achCTR }</td>
			</c:forEach>

		</tbody>
	</table>
	<div class="span12">
		<div class="pagination" id="pagecount"></div>
	</div>
	<br>
</body>
</html>
