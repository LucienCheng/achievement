<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<!-- Bootstrap -->
<link href="/achievement/source/bootstrap/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="/achievement/source/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet"
	media="screen">
<link href="/achievement/source/assets/styles.css" rel="stylesheet" media="screen">
<!--[if lte IE 8]><script language="javascript" type="text/javascript" src="vendors/flot/excanvas.min.js"></script><![endif]-->
<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
            <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->
<script src="/achievement/source/vendors/modernizr-2.6.2-respond-1.1.0.min.js"></script>

<style type="text/css">
	.control-group .controls .focused{
		height:30px;
		width:220px;
	}
	.control-group .controls{
		height:30px;
		width:500px;
	}
</style>
</head>

<body>
	<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container-fluid">
				<a class="brand" href="/achievement/front/index" target="_blank">首页</a>
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
		
		<c:if test="${roleId == 3 }">
			<div class="span2" id="sidebar">
				<ul class="nav nav-list bs-docs-sidenav nav-collapse collapse">
					<li><a href="/achievement/back/admin"> 用户管理</a></li>
					<li class="active"><a href="javascript:void(0);"> 个人消息修改</a></li>
					<li ><a href="/achievement/back/admin/slideShow"> 首页轮播图</a></li>
				</ul>
			</div>
		</c:if>
		
			<c:if test="${roleId == 2 }">
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
					
					<li class="active"><a href="/achievement/back/user/personInfo">个人资料修改</a></li>
				</ul>
			</div>
		</c:if>
			
		<c:if test="${roleId == 1 }">
			<div class="span2" id="sidebar">
				<ul class="nav nav-list bs-docs-sidenav nav-collapse collapse">
					<li <c:if test="${condition.achStatus == 1}"> class="active" </c:if> ><a href="/achievement/back/user?achStatus=1">审核通过</a></li>
					<li<c:if test="${condition.achStatus == 2}"> class="active" </c:if> ><a href="/achievement/back/user?achStatus=2">审核未通过 </a></li>
					<li<c:if test="${condition.achStatus == -1}"> class="active" </c:if> ><a href="/achievement/back/user?achStatus=-1">待编辑 </a></li>
					<li<c:if test="${condition.achStatus == 0}"> class="active" </c:if> ><a href="/achievement/back/user?achStatus=0">待审核 </a></li>
					<li class="active"><a href="/achievement/back/user/personInfo">个人资料修改</a></li>
				</ul>
			</div>
		</c:if>
			
			
			<!--/span-->
			<div class="span10" id="content">
				<!-- morris stacked chart -->
				<div class="row-fluid">
					<!-- block -->
					<div class="block">
						<div class="navbar navbar-inner block-header">
							<div class="muted pull-left">个人信息</div>
						</div>
						<div class="block-content collapse in">
							<div class="span12">
								<form class="form-horizontal" action="/achievement/back/admin/savePerson" method="post">
									<fieldset>
										<legend>个人信息修改</legend>
										<div class="control-group">
											<label class="control-label" for="focusedInput">工号</label>
											<div class="controls">
												<input class="input-xlarge focused" id="focusedInput"
													type="text" placeholder="输入工号" value='${user.userWorkNum }' name="userWorkNum">
											</div>
										</div>
										<div class="control-group">
											<label class="control-label" for="focusedInput">密码</label>
											<div class="controls">
												<input class="input-xlarge focused" id="focusedInput"
													type="text" placeholder="输入密码"value='${user.userPassword }' name="userPassword">
											</div>
										</div>
										<div class="control-group">
											<label class="control-label" for="focusedInput">手机号</label>
											<div class="controls">
												<input class="input-xlarge focused" id="focusedInput"
													type="text" placeholder="输入手机号" value='${user.userPhone }' name="userPhone">
											</div>
										</div>
										<div class="control-group">
											<label class="control-label" for="focusedInput">名字</label>
											<div class="controls">
												<input class="input-xlarge focused" id="focusedInput"
													type="text" placeholder="输入名字"value='${user.userName }' name="userName">
											</div>
										</div>
										<div class="control-group">
											<label class="control-label" for="focusedInput">用户角色</label>
											<div class="controls">
												<input class="input-xlarge focused" id="focusedInput"
													type="text" placeholder="用户角色" disabled value='${user.role.roleName }'>
											</div>
										</div>


										<div class="control-group">
											<label class="control-label" >性别</label>
											<div class="controls" >
											<c:if test="${ user.userSex == 1 }">
											<label> <input type="radio" name="userSex" value="1" checked>
													男 <input type="radio" name="userSex" value="0">
													女
												</label>
											</c:if>
													<c:if test="${ user.userSex == 0 }">
											<label> <input type="radio" name="userSex" value="1" >
													男 <input type="radio" name="userSex" value="0" checked>
													女
												</label>
											</c:if>
											</div>
										</div>

										<div class="control-group ">
											<label class="control-label" for="selectError">职称</label>
											<div class="controls">
											
												<select id="selectError" name="userPos">
													<option value="教授" <c:if test="${user.userPos == '教授' }"> selected</c:if> >教授</option>
													<option value="副教授" <c:if test="${user.userPos == '副教授' }"> selected</c:if> >副教授</option>
													<option value="助理教授"<c:if test="${user.userPos == '助理教授' }"> selected</c:if> >助理教授</option>
													<option value="讲师" <c:if test="${user.userPos == '讲师' }"> selected</c:if> >讲师</option>
												</select>
											</div>
										</div>
										


										<div class="form-actions">
											<button type="submit" class="btn btn-primary">保存</button>
											
										</div>
									</fieldset>
								</form>

							</div>
						</div>
					</div>
					<!-- /block -->
				</div>

			</div>
		</div>
		<hr>
	</div>

	<!--/.fluid-container-->
	<link href="/achievement/source/vendors/datepicker.css" rel="stylesheet" media="screen">
	<link href="/achievement/source/vendors/uniform.default.css" rel="stylesheet"
		media="screen">
	<link href="/achievement/source/vendors/chosen.min.css" rel="stylesheet" media="screen">

	<link href="/achievement/source/vendors/wysiwyg/bootstrap-wysihtml5.css" rel="stylesheet"
		media="screen">

	<script src="/achievement/source/vendors/jquery-1.9.1.js"></script>
	<script src="/achievement/source/bootstrap/js/bootstrap.min.js"></script>
	<script src="/achievement/source/vendors/jquery.uniform.min.js"></script>
	<script src="/achievement/source/vendors/chosen.jquery.min.js"></script>
	<script src="/achievement/source/vendors/bootstrap-datepicker.js"></script>

	<script src="/achievement/source/vendors/wysiwyg/wysihtml5-0.3.0.js"></script>
	<script src="/achievement/source/vendors/wysiwyg/bootstrap-wysihtml5.js"></script>

	<script src="/achievement/source/vendors/wizard/jquery.bootstrap.wizard.min.js"></script>
	<script src="/achievement/source/assets/scripts.js"></script>
	<script>
        $(function() {
            $(".datepicker").datepicker();
            $(".uniform_on").uniform();
            $(".chzn-select").chosen();
            $('.textarea').wysihtml5();

            $('#rootwizard').bootstrapWizard({onTabShow: function(tab, navigation, index) {
                var $total = navigation.find('li').length;
                var $current = index+1;
                var $percent = ($current/$total) * 100;
                $('#rootwizard').find('.bar').css({width:$percent+'%'});
                // If it's the last tab then hide the last button and show the finish instead
                if($current >= $total) {
                    $('#rootwizard').find('.pager .next').hide();
                    $('#rootwizard').find('.pager .finish').show();
                    $('#rootwizard').find('.pager .finish').removeClass('disabled');
                } else {
                    $('#rootwizard').find('.pager .next').show();
                    $('#rootwizard').find('.pager .finish').hide();
                }
            }});
            $('#rootwizard .finish').click(function() {
                alert('Finished!, Starting over!');
                $('#rootwizard').find("a[href*='tab1']").trigger('click');
            });
        });
        </script>
</body>



</html>
