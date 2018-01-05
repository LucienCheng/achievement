<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>My JSP 'dividePage.jsp' starting page</title>
<script type="text/javascript" src="js/jquery-3.2.1.js"></script>
<!-- <script type="text/javascript">
	var curPage = 1; //当前页码 
	var total, pageSize, totalPage; //总记录数，每页显示数，总页数 
	//获取分页条 
	function getPageBar() {
		//页码大于最大页数 
		if (curPage > totalPage)
			curPage = totalPage;
		//页码小于1 
		if (curPage < 1)
			curPage = 1;
		pageStr = "<span>共" + total + "条</span><span>" + curPage + "/"
				+ totalPage + "</span>";
		//如果是第一页 
		if (curPage == 1) {
			pageStr += "<span>首页</span><span>上一页</span>";
		} else {
			pageStr += "<span><a href='javascript:void(0)' rel='1'>首页</a></span><span><a href='javascript:void(0)' rel='"
					+ (curPage - 1) + "'>上一页</a></span>";
		}
		//如果总的页数在6页只能就可以这样
		if (totalPage <= 6) {
			for (var i = 1; i <= totalPage; i++) {
				if (curPage == i) {
					pageStr += " <span>" + (i) + "</span>";
					continue;
				}
				pageStr += "<span><a href='javascript:void(0)' rel='" + (i)
						+ "'>" + (i) + "</a></span>"
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
				if (curPage == i) {
					pageStr += " <span>" + (i) + "</span>";
					continue;
				}
				pageStr += "<span><a href='javascript:void(0)' rel='" + i
						+ "'>" + i + "</a></span>"
			}
		}
		//如果是最后页 
		if (curPage >= totalPage) {
			pageStr += "<span>下一页</span><span>尾页</span>";
		} else {
			pageStr += "<span><a href='javascript:void(0)' rel='"
					+ (parseInt(curPage) + 1)
					+ "'> 下一页</a></span><span><a href='javascript:void(0)' rel='"
					+ totalPage + "'>尾页</a></span>";
		}
		$("#pagecount").html(pageStr);
	}
	function fun() {
		$("#pagecount span a").bind('click', function() {
			var rel = $(this).attr("rel");
			if (rel) {
				dividePage(rel);
			}
		});
	}

	//分页的函数
	function dividePage(page) {
	
		$.ajax({
			url : "ajax/dividePage/"+(page-1),
			type : "get",
			/* data : {
				'pageNum' : page - 1
			}, */
			/* 执行执行的是dom对象 ，不需要转化信息*/
			/*  processData:false,
			 contentType:false, */

			/* 指定返回类型为json */
			dataType : 'json',
			beforeSend : function() {
				console.log("这是一个beforeSend");
				$("#list ul").append("<li id='loading'>loading...</li>");//显示加载动画 
			},
			//成果返回結果時，这里是一个json对象
			success : function(map) {
				console.log("这是一个success");
				curPage = parseInt(page);
				total = parseInt(map.total);
				pageSize = parseInt(map.pageSize);
				totalPage = parseInt(map.totalPage);
				var users = map.users;
				var li = "";
				$.each(users, function(index, user) {
					li += "<li><a href='#'>" + user.userId + "</a></li>";
				});
				$("#list ul").empty();
				$("#list ul").append(li);
			},
			//发生错误时，
			error : function(e) {
				console.log("失败");
			},
			//无论是否发生错误，都会执行的操作
			complete : function() {
				getPageBar();
				fun();
			}
		});
	}
</script> -->
</head>
<body >
	<div id="list">
		<ul></ul>
	</div>
	<div id="pagecount"></div>
</body>
</html>
