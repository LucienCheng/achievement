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
	
	<link href="/achievement/source/bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen" />
    <link href="/achievement/source/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet" media="screen" />
    <link href="/achievement/source/vendors/easypiechart/jquery.easy-pie-chart.css" rel="stylesheet" media="screen" />
    <link href="/achievement/source/assets/styles.css" rel="stylesheet" media="screen" />   
	  <link href="/achievement/source/bootstrap/css/bootstrap-datetimepicker.css" rel="stylesheet" media="screen" />
    <link href="/achievement/source/bootstrap/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen" />
	
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
    <script src="/achievement/source/vendors/modernizr-2.6.2-respond-1.1.0.min.js"></script>
    <script src="/achievement/source/vendors/jquery-1.9.1.min.js"></script>
    <script src="/achievement/source/bootstrap/js/bootstrap.min.js"></script>
    <script src="/achievement/source/vendors/easypiechart/jquery.easy-pie-chart.js"></script>
    <script src="/achievement/source/assets/scripts.js"></script>
	 <script src="/achievement/source/bootstrap/js/bootstrap-datetimepicker.js"></script>
	<script src="/achievement/source/bootstrap/js/bootstrap-datetimepicker.min.js"></script>

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
    <div class="navbar navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container-fluid">
		 <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse"> <span class="icon-bar"></span>
                     <span class="icon-bar"></span>
                     <span class="icon-bar"></span>
                    </a>
          <ol class="breadcrumb">
              <li><a href="#">首页</a> <span class="divider">/</span></li>
              <li><a href="#">展示</a> <span class="divider">/</span></li>
            </ol>
          <div class="nav-collapse collapse">
          </div>
        </div>
      </div>
    </div>
    <div class="container-fluid">
      <div class="row-fluid">
        <div class="span1" id="sidebar"></div>
        <div class="span10" id="content">
          <div class="row-fluid"></div>
          <div class="row-fluid">
		  </div>
          <div class="row-fluid ">
            <div class="collapse in" id="collapseExample" >
			<div style="background-color:#fff;border:1px solid #d4d4d4;height:auto;" class="block pull-left span12" >
              <div class="input-group pull-left block-content">
			  <label style="display:inline">成果名字</label>
			  <input type="text" style="margin-bottom:0px;width:150px" placeholder="成果名字" /> 
			  <label style="display:inline">成果作者</label>
              <input type="text" style="margin-bottom:0px;width:150px" placeholder="成果作者" /> 
			   <label style="display:inline">起止时间--</label>
			   <label style="display:inline">终止时间</label>
			 <div class="input-append date form_datetime " data-date="2013-02-21T15:25:00Z" style="margin-bottom:0px;">
			
    <input size="16" type="text" value="" readonly style="margin-bottom:0px;width:100px">
    <span class="add-on"><i class="icon-remove"></i></span>
    <span class="add-on"><i class="icon-calendar"></i></span>
</div>
<label style="display:inline">--</label>
 <div class="input-append date form_datetime " data-date="2013-02-21T15:25:00Z" style="margin-bottom:0px;">
			
    <input size="16" type="text" value="" readonly style="margin-bottom:0px;width:100px">
    <span class="add-on"><i class="icon-remove"></i></span>
    <span class="add-on"><i class="icon-calendar"></i></span>
</div>
<script type="text/javascript">
    $(".form_datetime").datetimepicker({
        format: "dd MM yyyy - hh:ii",
        autoclose: true,
        todayBtn: true,
        startDate: "2013-02-14 10:00",
        minuteStep: 10
    });
</script>    
			  
			  <button class="btn btn-default" type="button">搜索</button>
			  </div>
			</div>
          </div>
          <div class="row-fluid">
            <div class="block">
              <div class="navbar navbar-inner block-header">
                <div class="muted pull-left">成果</div>

              <div style="padding:0px 0px 0px 40px;">
                  <button class="col-sm-6 btn btn-default btn-xs">最热</button>
                  <button class="col-sm-6 btn btn-default btn-xs">最新</button>
              </div>
              </div>
              <div class="block-content collapse in">
                <div class="span12">
                  <table class="table table-hover">
                    <thead>
                      <tr>
                        <th></th>
                        <th>成果</th>
						 <th>作者</th>
						  <th>时间</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr>
                        <td>
                          <input type="checkbox" name="checkbox" />
                        </td>
                        <td  class="media">
                          <a class="pull-left" href="userpage3.html">
                            <img class="media-object" src="images/下载.png" />
                          </a>
                          <div class="media-body">
                          <h4 class="media-heading">肖申克的救赎 The Shawshank Redemption
                          (1994)</h4>银行家安迪因被当作杀害妻子与情夫的凶手，被判终身监禁。安迪在监狱中一方面帮监狱长做假账，一方面精心策划了一出越狱好戏。</div>
                        </td>
						<td>
						author
						</td>
						<td colspan="3">
						time
						</td>
                      </tr>
                      <tr>
                        <td>
                          <input type="checkbox" name="checkbox" />
                        </td>
                        <td  class="media">
                          <a class="pull-left" href="#">
                            <img class="media-object" src="images/下载.png" />
                          </a>
                          <div class="media-body">
                          <h4 class="media-heading">肖申克的救赎 The Shawshank Redemption
                          (1994)</h4>银行家安迪因被当作杀害妻子与情夫的凶手，被判终身监禁。安迪在监狱中一方面帮监狱长做假账，一方面精心策划了一出越狱好戏。</div>
                        </td>
						<td>
						author
						</td>
						<td>
						time
						</td>
                      </tr>
                     
                    </tbody>
                  </table>
                </div>
                <div class="span12">
                  <div class="pagination">
                    <ul>
                      <li>
                        <a href="#">Prev</a>
                      </li>
                      <li class="active">
                        <a href="#">1</a>
                      </li>
                      <li>
                        <a href="#">2</a>
                      </li>
                      <li>
                        <a href="#">3</a>
                      </li>
                      <li>
                        <a href="#">4</a>
                      </li>
                      <li>
                        <a href="#">Next</a>
                      </li>
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
